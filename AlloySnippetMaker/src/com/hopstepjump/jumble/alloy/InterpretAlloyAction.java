package com.hopstepjump.jumble.alloy;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.gui.*;
import com.hopstepjump.jumble.packageview.base.*;
import com.hopstepjump.swing.*;


public class InterpretAlloyAction extends AbstractAction
{
	public static final ImageIcon ERROR_ICON = IconLoader.loadIcon("error.png");
	public static final ImageIcon ALLOY_ICON = IconLoader.loadIcon("alloy.png");

  private ToolCoordinatorFacet toolCoordinator;
	private CommandManagerFacet commandManager;
	private LongRunningTaskProgressMonitor monitor;

  public InterpretAlloyAction(ToolCoordinatorFacet toolCoordinator, CommandManagerFacet commandManager, LongRunningTaskProgressMonitor monitor)
  {
    super("Interpret Alloy");
    this.toolCoordinator = toolCoordinator;
    this.commandManager = commandManager;
    this.monitor = monitor;
  }
  
  public void actionPerformed(ActionEvent e)
  {
		// use an app modal dialog to show the status and prevent further input
		monitor.displayInterimPopup(ALLOY_ICON, "Alloy import", "Import in progress...", null, -1);
		monitor.invokeActivityAndMonitorProgress(new Runnable()
		{
			public void run()
			{
			}
		});
		monitor.waitForFinish();

		importAlloy(monitor);
  	commandManager.clearCommandHistory();
  }
  
  private void importAlloy(LongRunningTaskProgressMonitor monitor)
  {
  	// is this using "label=" instead of "name=" (switched between Alloy 4 rc5 and the final release of Alloy 4)
  	boolean newStyleNaming = false;
    Document document;
    try
    {
      // parse the xml first
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      String contents = getClipboardContents();
      if (contents.contains(" label="))
      	newStyleNaming = true;
      document = builder.parse(new ByteArrayInputStream(contents.getBytes()));
    }
    catch (Exception ex)
    {
     monitor.stopActivityAndDisplayPopup(ERROR_ICON, "Problem parsing Alloy output", "Ensure that the clipboard contains an XML Alloy instance", null, 3000, false);
      return;
    }
      
    // execute the command
    DiagramViewFacet diagramView = GlobalPackageViewRegistry.activeRegistry.getFocussedView().getCurrentDiagramView();
    if (diagramView != null)
    {
    	Throwable t = null;
    	try
    	{
    		Command cmd = new GraphExpandedInterpretAlloyCommand(toolCoordinator, diagramView.getDiagram(), document, newStyleNaming);
    		toolCoordinator.executeCommandAndUpdateViews(cmd);
    	}
    	catch (Throwable ex)
    	{
    		t = ex;
    		t.printStackTrace();
    	}
    	finally
    	{
  			if (t != null)
  		     monitor.stopActivityAndDisplayPopup(ERROR_ICON, "Alloy import error", t.getMessage(), null, 3000, false);
  			else
 		     monitor.stopActivityAndDisplayPopup(ALLOY_ICON, "Alloy import successful", "Import completed; cleared command history", null, 2000, false);
    	}
    }
  }

  private String getClipboardContents()
  {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
     //odd: the Object param of getContents is not currently used
     Transferable contents = clipboard.getContents(null);
     boolean hasTransferableText = (contents != null) &&
                                   contents.isDataFlavorSupported(DataFlavor.stringFlavor);
     if (hasTransferableText)
     {
       try
       {
         return (String) contents.getTransferData(DataFlavor.stringFlavor);
       }
       catch (UnsupportedFlavorException ex)
       {
         //highly unlikely since we are using a standard DataFlavor
       }
       catch (IOException ex)
       {
         //highly unlikely since we are using a standard DataFlavor
       }
     }
     return "";
   }
}
