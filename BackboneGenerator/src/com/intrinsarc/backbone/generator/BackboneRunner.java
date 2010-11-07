package com.intrinsarc.backbone.generator;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.intrinsarc.backbone.generator.hardcoded.common.*;
import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.easydock.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.repositorybase.*;
import com.sun.org.apache.xerces.internal.impl.dtd.models.*;

/**
 * this class needs work to make it obey the Swing EDT rules.  Seems to work nicely, but it's not conforming to
 * the Swing threading rules
 * @author andrew, 19/11/2008
 */

public class BackboneRunner
{
  private static final String BACKBONE_INTERPRETER = "com.intrinsarc.backbone.BackboneInterpreter";

  /** to match against any errors */
  private static final Pattern ERROR_PATTERN = Pattern.compile("(.*)\\|(.*) at (.*)\\|.*", Pattern.DOTALL);
  private BackboneGenerationChoice choice;
  private BackboneRunnerFacet runner = new BackboneRunnerFacetImpl();
  private JTextArea output = new JTextArea();
  private JTextArea error = new JTextArea();
  private Process proc;
  private IEasyDockable frame;
  private JPanel insetPanel;
  private boolean errorsShown = false;
  private JButton regenerate;
  private JButton rerun;
  private JButton kill;
  private JButton locateInBrowser;
  private JButton locateInDiagram;
  private NamedElement namedElementInError;
  private ErrorLocatorFacet errorLocator;
  private boolean analyseProtocol;
  private String name;
	private BackboneGeneratorFacet generator;
  
  public BackboneRunner(String name, BackboneGenerationChoice choice, boolean analyseProtocol)
  {
    this.choice = choice;
    this.analyseProtocol = analyseProtocol;
    this.name = name;
  }

  public void setDockable(IEasyDock desktop, IEasyDockable frame)
  {
  	this.frame = frame;
  }
  
  public void setBackboneGeneratorFacet(BackboneGeneratorFacet generator)
  {
  	this.generator = generator;
  }
  
  public BackboneRunnerFacet getBackboneRunnerFacet()
  {
    return runner;
  }

  public void connectErrorLocatorFacet(ErrorLocatorFacet errorLocator)
  {
  	this.errorLocator = errorLocator;
  }
  
  private class BackboneRunnerFacetImpl implements BackboneRunnerFacet
  {
    public JComponent getVisualComponent()
    {
      GridBagLayout gridBag = new GridBagLayout();
      insetPanel = new JPanel(gridBag);
      constructPanel(insetPanel, false);
      return insetPanel;
    }

    public void haveClosed()
    {
      // kill the process if it is still active
      killProcess();
    }

    public void run()
    {
      redo(false);
    }
  }
  
  /**
   * regenerate the source and trap possible errors
   *
   */
  private void regenerate()
  {
    showErrors(false);
    new Thread(new Runnable()
    {
    	public void run()
    	{    		
      	if (redo(true))
          output.setText(output.getText() + "Regeneration successful.\n");
    	}
    }).start();
  }
  
  private boolean redo(boolean generateOnly)
  {
    try
    {
      output.setText("");
      error.setText("");

      // generate the code
      List<String> translatedClasspaths = new ArrayList<String>();
      long start = System.currentTimeMillis();
      output.setText("Checking model and regenerating code...");
      int modified[] = {0};
      File loadList = generator.generate(choice, translatedClasspaths, null, true, modified);
      long end = System.currentTimeMillis();
      if (loadList == null)
      	return false;
      
      output.setText(output.getText() + "\nChecked, and regenerated " + modified[0] + " files in " + (end - start) + "ms\n");
      
      // run the system
      File generationDir = loadList.getParentFile();
      List<String> exec = writeRunFile(generationDir, loadList, translatedClasspaths);
      if (!generateOnly)
      	externallyRunBackboneProgram(generationDir, exec);

      return true;
    }
    catch (BackboneGenerationException ex)
    {
      Object elementInError = ex.getElementInError();
      if (elementInError instanceof NamedElement)
      {
      	String uuid = ((NamedElement) elementInError).getUuid(); 
      	error.setText("GENERATION | " + ex.getMessage() + " at " + uuid + " | ");
      }
      else
      	error.setText(ex.getMessage());
      showErrors(true);
    }
    catch (IOException ex)
    {
      error.setText(ex.getMessage() + "\n" + getStackTrace(ex));        
      showErrors(true);
    }
    return false;
  }

  /**
   * regenerate and rerun
   *
   */
  private void rerun()
  {
  	// possibly kill off a running process
  	killProcess();
  	
    showErrors(false);
    new Thread(new Runnable()
    {
    	public void run()
    	{    		
      	redo(false);
    	}
    }).start();
  }
  

  private synchronized void updateButtons()
  {
    if (proc != null)
    {
      regenerate.setEnabled(false);
      rerun.setEnabled(true);
      kill.setEnabled(true);
      output.setForeground(Color.BLUE);
      if (locateInBrowser != null)
      {
      	updateErrorButtons();
      }
    }
    else
    {
      regenerate.setEnabled(true);
      rerun.setEnabled(true);
      kill.setEnabled(false);
      output.setForeground(Color.BLACK);
      if (locateInBrowser != null)
      	updateErrorButtons();
    }
  }
  
  private void updateErrorButtons()
	{
  	// look for a pattern in the current error text
  	String text = error.getText();
  	Matcher matcher = ERROR_PATTERN.matcher(text);
  	if (matcher.matches())
  	{
  		String uuid = matcher.group(3).trim();
  		
  		// locate the element
  		namedElementInError = GlobalSubjectRepository.repository.findNamedElementByUUID(uuid);

  		if (namedElementInError == null)
  		{
  	    locateInBrowser.setEnabled(false);
  	    locateInDiagram.setEnabled(false);
  		}
  		else
  		{
  	    locateInBrowser.setEnabled(true);
  	    locateInDiagram.setEnabled(true);
  		}  		
  	}
  	else
  	{
  		namedElementInError = null;
  		if (errorsShown)
  		{
		    locateInBrowser.setEnabled(false);
		    locateInDiagram.setEnabled(false);
  		}
  	}
	}

	/**
   * show errors on the gui
   * @param b
   */
  private void showErrors(final boolean showErrors)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        constructPanel(insetPanel, showErrors);
      }
    });
    errorsShown = true;
  }

  private void killProcess()
  {
    if (proc != null)
    {
      proc.destroy();
      proc = null;
    }
  }


  private void externallyRunBackboneProgram(File generationDir, List<String> exec) throws IOException
  {
    Runtime runtime = Runtime.getRuntime();
    proc = runtime.exec(exec.toArray(new String[0]), null, generationDir);
    updateButtons();
    attachStreamListener(proc.getInputStream(), false);
    attachStreamListener(proc.getErrorStream(), true);
  
    // make a thread to give an indication as to when the program has terminated
    createEndProcessChecker();
  }
  
  // make the run file
  private List<String> writeRunFile(File generationDir, File translatedLoadList, List<String> translatedClasspaths) throws BackboneGenerationException, IOException
  {
    BufferedWriter writer = null;
    File runFile = new File(generationDir, "run-load-list.bat");
    String classpath = "";
    String separator = File.pathSeparator;
    for (String path : translatedClasspaths)
    {
    	if (classpath.length() != 0)
    		classpath += separator;
    	classpath += path;
    }
    
    // write out the batch/sh file for running outside of jumble
    List<String> exec = new ArrayList<String>();
    String[] cmds = choice.extractRunParameters();
    String javaCmd = GlobalPreferences.preferences.getRawPreference(BackboneWriter.BB_JAVA_CMD_PREF).asString();
    String target = "\"" + cmds[0] + "\" \"" + cmds[1] + "\" \"" + cmds[2] + "\"";
    String niceTarget = cmds[0] + "::" + cmds[1] + "." + cmds[2];
    if (analyseProtocol)
    	frame.setTitleText(name + ": " + cmds[0] + "::" + cmds[1]);
    else
    	frame.setTitleText(name + ": " + niceTarget);
    String cmd =
      "\"" + javaCmd + "\" -classpath " + classpath + " " +
      (analyseProtocol ? "-DanalyseProtocol " : "") +
      BACKBONE_INTERPRETER + " -nocheck " + translatedLoadList + " " + target;
    exec.add(javaCmd);
    exec.add("-classpath");
    String rclasspath = classpath.replace("\"", "");
    exec.add(rclasspath);
    if (analyseProtocol)
    	exec.add("-DanalyseProtocol");
    exec.add(BACKBONE_INTERPRETER);
    exec.add("-nocheck");
    exec.add(translatedLoadList.toString());
    
    List<String> cmdLine = new ArrayList<String>();
    String cmdLineString = "";
    try
		{
			String cmdLineArgs = StereotypeUtilities.extractStringProperty(
					(Element) choice.getSingleStratum().getRepositoryObject(),
					CommonRepositoryFunctions.BACKBONE_CMD_LINE);
			if (cmdLineArgs != null)
			{
				cmdLineString = cmdLineArgs;
				cmdLine = WriterHelper.expandOutAsArguments(cmdLineArgs);
			}
		}
    catch (BackboneGenerationException e)
		{
		}
    
    for (int lp = 0; lp < 3; lp++)
    	exec.add(cmds[lp]);
    for (String arg : cmdLine)
    	exec.add(arg);
    
    try
    {
      writer = new BufferedWriter(new FileWriter(runFile));
      writer.newLine();
      writer.write(cmd + " " + cmdLineString);
      writer.newLine();
    }
    finally
    {
      if (writer != null)
        writer.close();
    }

    return exec;
  }

	private synchronized void constructPanel(final JPanel insetPanel, boolean errors)
  {
  	// update whether we are showing errors or now to reflect the view
  	errorsShown = errors;
  	
    insetPanel.removeAll();
    // assemble the attributes, then ask them for their visual components
    GridBagConstraints gbcLeft = new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 0, 1, 0), 0, 0);
    GridBagConstraints gbcMiddle = new GridBagConstraints(2, 0, 2, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 0, 1, 0), 0, 0);
    GridBagConstraints gbcRight = new GridBagConstraints(4, 0, 2, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 0, 1, 0), 0, 0);
    GridBagConstraints gbcLeftX = new GridBagConstraints(0, 0, 3, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 0, 1, 0), 0, 0);
    GridBagConstraints gbcRightX = new GridBagConstraints(3, 0, 3, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 0, 1, 0), 0, 0);

    // add the rerunners
    regenerate = new JButton("Regenerate");
    regenerate.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            regenerate();
          }
        });
    insetPanel.add(regenerate, gbcLeft);
    rerun = new JButton("Rerun");
    rerun.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            rerun();
          }
        });
    insetPanel.add(rerun, gbcMiddle);
    kill = new JButton("Kill");
    kill.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            killProcess();
          }
        });
    insetPanel.add(kill, gbcRight);

    gbcLeft.gridy++;
    gbcRight.gridy++;
    
    // add the output
    JScrollPane outputScroller = new JScrollPane(output);
    gbcLeft.gridwidth = 6;
    gbcLeft.fill = GridBagConstraints.BOTH;
    gbcLeft.weighty = 1000;
    insetPanel.add(outputScroller, gbcLeft);
    gbcLeft.gridwidth = 2;
    gbcLeft.weighty = 1;
    gbcLeft.fill = GridBagConstraints.HORIZONTAL;
    gbcLeft.gridy++;
    gbcRight.gridy++;
    
    if (errors)
    {
      // add the error buttons
      gbcLeftX.gridy = gbcLeft.gridy;
      gbcRightX.gridy = gbcLeft.gridy;
      locateInBrowser = new JButton("Locate error in browser");
      locateInBrowser.addActionListener(new ActionListener()
	  		{
					public void actionPerformed(ActionEvent e)
					{
						errorLocator.locateErrorInBrowser(namedElementInError);
					}
	  		});
      insetPanel.add(locateInBrowser, gbcLeftX);
      locateInDiagram = new JButton("Locate error in diagram");
      locateInDiagram.addActionListener(new ActionListener()
      		{
						public void actionPerformed(ActionEvent e)
						{
							errorLocator.locateErrorInDiagram(namedElementInError);
						}
      		});
      insetPanel.add(locateInDiagram, gbcRightX);
      gbcLeft.gridy++;
      gbcRight.gridy++;

      // add the error area
      error.setForeground(Color.RED);
      JScrollPane errorScroller = new JScrollPane(error);
      gbcLeft.gridwidth = 6;
      gbcLeft.fill = GridBagConstraints.BOTH;
      gbcLeft.weighty = 1000;
      insetPanel.add(errorScroller, gbcLeft);
      gbcLeft.gridwidth = 2;
      gbcLeft.weighty = 1;
      gbcLeft.fill = GridBagConstraints.HORIZONTAL;
      gbcLeft.gridy++;
      gbcRight.gridy++;
    }

    insetPanel.revalidate();
    updateButtons();
    updateErrorButtons();
    // sometimes get some odd corruption -- repaint to remove this
    insetPanel.repaint();
  }
  
  
  private String getStackTrace(Throwable ex)
  {
    StringWriter swriter = new StringWriter();
    PrintWriter writer = new PrintWriter(swriter);
    ex.printStackTrace(writer);
    return swriter.toString();
  }

  private Thread attachStreamListener(final InputStream stream, final boolean isErrors)
  {
    Thread thread = new Thread()
    {
      public void run()
      {
        BufferedReader is = 
          new BufferedReader(new InputStreamReader(stream));
        
        JTextArea area = isErrors ? error : output;
       
        String line;
        try
        {
          while ((line = is.readLine()) != null)
          {
            if (isErrors && !errorsShown)
              showErrors(true);
            area.append(line + "\n");
          }
        }
        catch (IOException e)
        {
        }
      }
    };
    thread.start();
    return thread;
  }

  private void createEndProcessChecker()
  {
    new Thread()
    {
      public void run()
      {
        try
        {
          proc.waitFor();
        }
        catch (InterruptedException e)
        {
        }
        proc = null;
        updateButtons();
      }
    }.start();
  }
}
