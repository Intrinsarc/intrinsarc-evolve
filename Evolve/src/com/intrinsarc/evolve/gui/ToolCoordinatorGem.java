package com.intrinsarc.evolve.gui;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.concurrent.*;

import javax.swing.*;
import javax.swing.border.*;

import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.easydock.*;
import com.intrinsarc.evolve.freeform.grouper.*;
import com.intrinsarc.evolve.freeform.image.*;
import com.intrinsarc.evolve.freeform.measurebox.*;
import com.intrinsarc.evolve.umldiagrams.associationarc.*;
import com.intrinsarc.evolve.umldiagrams.baseline.*;
import com.intrinsarc.evolve.umldiagrams.classifiernode.*;
import com.intrinsarc.evolve.umldiagrams.connectorarc.*;
import com.intrinsarc.evolve.umldiagrams.containmentarc.*;
import com.intrinsarc.evolve.umldiagrams.dependencyarc.*;
import com.intrinsarc.evolve.umldiagrams.featurenode.*;
import com.intrinsarc.evolve.umldiagrams.freetext.*;
import com.intrinsarc.evolve.umldiagrams.implementationarc.*;
import com.intrinsarc.evolve.umldiagrams.inheritancearc.*;
import com.intrinsarc.evolve.umldiagrams.lifeline.*;
import com.intrinsarc.evolve.umldiagrams.linkedtextnode.*;
import com.intrinsarc.evolve.umldiagrams.messagearc.*;
import com.intrinsarc.evolve.umldiagrams.narynode.*;
import com.intrinsarc.evolve.umldiagrams.nodenode.*;
import com.intrinsarc.evolve.umldiagrams.notelinkarc.*;
import com.intrinsarc.evolve.umldiagrams.notenode.*;
import com.intrinsarc.evolve.umldiagrams.packagenode.*;
import com.intrinsarc.evolve.umldiagrams.portnode.*;
import com.intrinsarc.evolve.umldiagrams.requirementsfeaturenode.*;
import com.intrinsarc.evolve.umldiagrams.sequencesection.*;
import com.intrinsarc.evolve.umldiagrams.slotnode.*;
import com.intrinsarc.evolve.umldiagrams.stereotypenode.*;
import com.intrinsarc.evolve.umldiagrams.tracearc.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.diagramsupport.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.figures.simplecontainernode.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.lookandfeel.*;
import com.intrinsarc.uml2deltaengine.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

public final class ToolCoordinatorGem implements Gem
{
	public static final int MSEC_VIEW_UPDATE_DELAY = 100;
	public static Preference UNDO_REDO_SIZE = new Preference(
			"Advanced",
			"Size of undo/redo history",
			100);
	private static final Font POPUP_FONT = new Font("Arial", Font.BOLD, 16);
	
	private ToolCoordinatorFacet coordinatorFacet = new ToolCoordinatorFacetImpl();
  private JFrame frame;
  private PaletteManagerFacet paletteFacet;
  private JPopupMenu popupFrame;
  private Object popupMutex = new Object();
  private int currentPopupNumber = 0;
  private ZCanvas currentCanvas;
  private IEasyDock dock;
  private Semaphore sema = new Semaphore(1);
  private Semaphore sema2 = new Semaphore(1);
  private boolean inTransaction;
  private Dialog currentDialog;
  
  public ToolCoordinatorGem()
  {
  	registerRecreators();
  }

	public ToolCoordinatorFacet getToolCoordinatorFacet()
	{
		return coordinatorFacet;
	}
	
	private class ToolCoordinatorFacetImpl implements ToolCoordinatorFacet
	{
		public void attachTo(DiagramViewFacet diagramView, ZCanvas newCanvas, ZNode newMouseNode)
	  {
	  	currentCanvas = newCanvas;
	    if (paletteFacet != null)
	      paletteFacet.attachTo(diagramView, newCanvas, newMouseNode);
	  }
	
	  public void toolFinished(ZMouseEvent e, boolean stopMultiTool)
	  {
	    paletteFacet.toolFinished(e, stopMultiTool);
	  }
	  
		/**
		 * @see com.intrinsarc.idraw.foundation.ToolCoordinatorFacet#displayWaitCursor()
		 */
		public Cursor displayWaitCursor()
		{
    	java.awt.Component top = frame.getGlassPane();
    	top.setVisible(true);
    	Cursor oldCursor = top.getCursor();
			top.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			return oldCursor;
		}

		public void restoreCursor(Cursor oldCursor)
		{
			java.awt.Component top = frame.getGlassPane();
			top.setCursor(oldCursor);
			top.setVisible(false);
		}
		
		/**
		 * @see com.intrinsarc.idraw.foundation.ToolCoordinatorFacet#displayCursorForAWhile(int, int)
		 */
		public Cursor displayCursorForAWhile(int cursor, int msecs)
		{
			java.awt.Component top = frame.getGlassPane();
    	top.setVisible(true);
    	Cursor oldCursor = top.getCursor();
			top.setCursor(new Cursor(cursor));
			try
			{
				Thread.sleep(msecs);
			}
			catch (InterruptedException ex)
			{
			}
			return oldCursor;
		}
		
		/**
		 * @see com.intrinsarc.idraw.foundation.ToolCoordinatorFacet#attachToFrame(JFrame)
		 */
		public void attachToFrame(JFrame newFrame, PaletteManagerFacet palette, IEasyDock easyDock)
		{
			frame = newFrame;
			paletteFacet = palette;
			dock = easyDock;
		}
		
    /* (non-Javadoc)
     * @see com.intrinsarc.idraw.foundation.ToolCoordinatorFacet#getMouseMenuLocation()
     */
    public UPoint getLastMouseLocation()
    {
      return paletteFacet.getLastMouseLocation();
    }

    public void blockInput()
    {
    	java.awt.Component top = frame.getGlassPane();
    	top.setVisible(true);
    }

    public void restoreInput()
    {
    	java.awt.Component top = frame.getGlassPane();
    	top.setVisible(false);
    }

    public int invokeAsDialog(ImageIcon icon, String title, JComponent contents, JComponent buttons[], int defaultButton, final Runnable runAfterShown)
    {
    	final JDialog dialog = new JDialog(frame);
    	currentDialog = dialog;
    	dialog.setResizable(true);
    	dialog.setLayout(new BorderLayout());
    	dialog.add(contents, BorderLayout.CENTER);
    	JPanel panel = new JPanel();
    	int lp = 0;
    	final int chosen[] = new int[]{-1};
    	
    	if (buttons == null)
    		buttons = new JButton[]{new JButton("OK")};
    	JButton last = null;
    	for (JComponent b : buttons)
    	{
    		panel.add(b);
    		if (b instanceof JButton)
    		{
	    		final int count = lp++;
	    		((JButton) b).addActionListener(new ActionListener()
	    		{
						public void actionPerformed(ActionEvent e)
						{
							chosen[0] = count;
							dialog.dispose();
						}
	    		});
	    		
	    		if (count == defaultButton)
	    			last = (JButton) b;
    		}
    	}
    	// must have this otherwise we leak a huge amount of memory
    	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    	
    	dialog.setTitle(title);
    	dialog.add(panel, BorderLayout.SOUTH);
    	dialog.setModal(true);
    	dialog.pack();
    	if (last != null)
    		last.requestFocusInWindow();
    	
    	// centre with the frame
    	UPoint frameCentre = new UPoint(frame.getLocation()).add(new UDimension(frame.getSize()).multiply(0.5));
    	UPoint dialogCentre = new UPoint(dialog.getLocation()).add(new UDimension(dialog.getSize()).multiply(0.5));
    	dialog.setLocation(new UPoint(dialog.getLocation()).add(frameCentre.subtract(dialogCentre)).asPoint());
    	
    	dialog.addComponentListener(new ComponentAdapter()
    	{
				public void componentShown(ComponentEvent e)
				{
					if (runAfterShown != null)
						runAfterShown.run();
				}
    	});
    	dialog.setVisible(true);
    	
			currentDialog = null;
      return chosen[0];
    }
    
    public int invokeErrorDialog(String title, Object message)
    {
      int chosen =
      	JOptionPane.showOptionDialog(
            frame.getContentPane(),
            message,
            title,
            JOptionPane.CLOSED_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            null,
            null);
      return chosen;
    }
    
    public int invokeYesNoCancelDialog(String title, Object message)
    {
      int chosen =
      	JOptionPane.showOptionDialog(
            frame.getContentPane(),
            message,
            title,
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            null,
            null);
      return chosen;
    }
    
    public void displayPopup(
        Icon icon,
        String title,
        Object description,
        Color fillColor,
        Color textColor,
        int msecsToDisplayFor)
    {
      displayPopup(
          icon,
          title,
          description,
          fillColor,
          textColor,
          msecsToDisplayFor,
          false,
          0,
          0);
    }

    public void displayPopup(
        final Icon icon, 
        final String title,
        final Object description,
        final Color fillColor,
        final Color textColor,
        final int msecsToDisplayFor,
        final boolean displayProgressBar,
        final int currentProgressPosition,
        final int progressMax)
    {
      Runnable runnable = new Runnable()
      {
        public void run()
        {
          synchronized (popupMutex)
          {
            int progressWidth = 500;
            final JPopupMenu old = popupFrame;
    
            // I will only remove the popup if noone has created another in the interim...
            final int myPopupNumber = ++currentPopupNumber;
            
            // add the info to the lightweight popup
            if (old != null)
            	old.removeAll();
            popupFrame = old != null ? old : new JPopupMenu();
            popupFrame.setBorder(null);
            JPanel outer = new JPanel(new BorderLayout());
            outer.setBorder(
            		new CompoundBorder(
            				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            				BorderFactory.createLineBorder(ScreenProperties.getTransparentColor(), 20)));
            JLabel label = new JLabel(title, icon, SwingConstants.LEADING);
            label.setBorder(new EmptyBorder(0, 0, 5, 0));
            label.setFont(POPUP_FONT.deriveFont(Font.BOLD));
            outer.add(label, BorderLayout.NORTH);
            
            JPanel panel = new JPanel(new BorderLayout());
            outer.add(panel, BorderLayout.CENTER);
            panel.setLayout(new BorderLayout());
            JComponent text;
            if (description instanceof String)
              text = new JLabel((String) description);
            else
              text = (JComponent) description;
            if (text != null)
            {
            	text.setFont(POPUP_FONT.deriveFont(Font.PLAIN));
            	text.setBorder(new EmptyBorder(0, 0, 5, 0));
            }
    
            final JProgressBar bar = new JProgressBar(0, progressMax);
            // fix up the progress bar so it is constantly active
            RegisteredGraphicalThemes.getInstance().getStartupTheme().setProgressBarUI(bar);
            bar.setBorder(new LineBorder(Color.LIGHT_GRAY));
            bar.setValue(currentProgressPosition);
            bar.setPreferredSize(new Dimension(progressWidth, 20));
            
            // add to the panel
            if (text != null)
            	panel.add(text, BorderLayout.NORTH);
            if (displayProgressBar)
              panel.add(bar, BorderLayout.WEST);
            else
            {
            	JPanel sizingPanel = new JPanel();
            	sizingPanel.setPreferredSize(new Dimension(progressWidth, 0));
            	panel.add(sizingPanel, BorderLayout.WEST);
            }
    				ZSwing swingPanel = new ZSwing(currentCanvas, outer);
    				int width = (int) swingPanel.getBounds().getWidth();
    				int height = (int) swingPanel.getBounds().getHeight();
    				
            popupFrame.add(outer);

            popupFrame.setLocation(
            		frame.getLocationOnScreen().x + frame.getWidth() - width - 22,
            		frame.getLocationOnScreen().y + frame.getHeight() - height - 22);
          	popupFrame.setVisible(true);

            if (msecsToDisplayFor >= 0)
            {
	            new Thread(new Runnable()
	            {
	              public void run()
	              {
	                try
	                {
	                  Thread.sleep(msecsToDisplayFor);
	                }
	                catch (InterruptedException ex)
	                {
	                  // no need to do anything, remove popup anyway
	                }
	    
	                if (myPopupNumber == currentPopupNumber)
	                  try
	                  {
	                    SwingUtilities.invokeAndWait(new Runnable()
	                        {
	                          public void run()
	                          {
	                            removePopup();
	                          }
	                        });
	                  }
	                  catch (InterruptedException e)
	                  {
	                  }
	                  catch (InvocationTargetException e)
	                  {
	                  }
	              }
	            }).start();
            }
          }
        }
      };
      // make this safe for threaded activity
      if (SwingUtilities.isEventDispatchThread())
        runnable.run();
      else
        SwingUtilities.invokeLater(runnable);
    }
  
    private void removePopup()
    {
      synchronized (popupMutex)
      {
        if (popupFrame != null)
        {
          popupFrame.setVisible(false);
          popupFrame = null;
        }
      }
    }

    public void reestablishCurrentTool()
    {
      paletteFacet.reestablishCurrentTool();
    }

    public DiagramViewFacet getCurrentDiagramView()
    {
      return paletteFacet.getCurrentDiagramView();
    }

    public void changedDiagram(DiagramFacet view)
    {
      paletteFacet.refreshEnabled();
    }

		public void clearPopup()
		{
      removePopup();
		}

		public IEasyDock getDock()
		{
			return dock;
		}

		public int getFrameXPreference(Preference preference)
		{
			return getIntegerPreference(preference) + frame.getLocationOnScreen().x;
		}

		public int getFrameYPreference(Preference preference)
		{
			return getIntegerPreference(preference) + frame.getLocationOnScreen().y;
		}

		public int getIntegerPreference(Preference preference)
		{
			return GlobalPreferences.preferences.getRawPreference(preference).asInteger();
		}

		private void semaBlock()
		{
			sema.acquireUninterruptibly();
			sema.release();			
		}
		
		public void startTransaction(String redoName, String undoName)
		{
			inTransaction = true;
			sema2.release();
			semaBlock();
			clearDeltaEngine();
			GlobalSubjectRepository.repository.startTransaction(redoName, undoName);
			for (DiagramFacet d : GlobalDiagramRegistry.registry.getDiagrams())
				d.startTransaction(redoName, undoName);
		}
		
		public void undoTransaction()
		{
			semaBlock();
			GlobalSubjectRepository.repository.undoTransaction();
			clearDeltaEngine();
			final DiagramFacet main = getCurrentDiagramView().getDiagram();
			main.undoTransaction();
			inBackground(
				false,
				new Runnable()
				{
					public void run()
					{
		  			for (DiagramFacet d : GlobalDiagramRegistry.registry.getDiagrams())
		  				if (d != main)
		  					d.undoTransaction();
		  			for (DiagramFacet d : GlobalDiagramRegistry.registry.getDiagrams())
		  				d.completeUndoTransaction();						
					}
				});
		}

		public void redoTransaction()
		{
			semaBlock();
			GlobalSubjectRepository.repository.redoTransaction();
			clearDeltaEngine();
			final DiagramFacet main = getCurrentDiagramView().getDiagram();
			main.redoTransaction();
			inBackground(
				false,
				new Runnable()
				{
					public void run()
					{
		  			for (DiagramFacet d : GlobalDiagramRegistry.registry.getDiagrams())
		  				if (d != main)
		  					d.redoTransaction();
		  			for (DiagramFacet d : GlobalDiagramRegistry.registry.getDiagrams())
		  				d.completeRedoTransaction();						
					}
				});
		}

		public void commitTransaction()
		{
			commitTransaction(GlobalSubjectRepository.repository.isLongRunningTransaction());
		}

		public void commitTransaction(final boolean fullyCommitCurrentDiagramInForeground)
		{
			inTransaction = false;
	    final SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
	    
			WaitCursorDisplayer waiter = new WaitCursorDisplayer(this, 400 /* msecs */);
			waiter.displayWaitCursorAfterDelay();

//			long start = System.currentTimeMillis();
			clearDeltaEngine();
			final DiagramFacet main = getCurrentDiagramView().getDiagram();
			updateDiagramAfterSubjectChanges(main, !fullyCommitCurrentDiagramInForeground);
			main.checkpointCommitTransaction();
			repository.commitTransaction();
//			long end = System.currentTimeMillis();
//			System.out.println("$$ took " + (end - start) + "ms");

			// possibly update in the background
			inBackground(
				fullyCommitCurrentDiagramInForeground,
				new Runnable()
		    {
		    	public void run()
		    	{
		  			updateDiagramAfterSubjectChanges(main, false);
		  			main.commitTransaction();
		  			for (DiagramFacet diagram : GlobalDiagramRegistry.registry.getDiagrams())
		  			{
		  				if (diagram != main)
		  				{
		  					if (!fullyCommitCurrentDiagramInForeground)
		  						updateDiagramAfterSubjectChanges(diagram, false);
		  					diagram.commitTransaction();					
		  				}				
		  			}
		        GlobalDiagramRegistry.registry.enforceMaxUnmodifiedUnviewedDiagramsLimit();
		        enforceTransactionDepth(getIntegerPreference(UNDO_REDO_SIZE));
		    	}
		    });
			waiter.restoreOldCursor();
      paletteFacet.refreshEnabled();
		}
	
		private void inBackground(boolean forceForeground, final Runnable runnable)
		{
			boolean background = GlobalPreferences.preferences.getRawPreference(BasicDiagramGem.BACKGROUND_VIEW_UPDATES).asBoolean();

			if (forceForeground || !background)
				runnable.run();
			else
			{
  			sema.acquireUninterruptibly();
				sema2.acquireUninterruptibly();
				new Thread(new Runnable()
		    {
		    	public void run()
		    	{
		    		try
						{
							Thread.sleep(100);
						}
		    		catch (InterruptedException e)
						{
						}
						sema2.acquireUninterruptibly();
		    		runnable.run();
		    		sema2.release();
		  			sema.release();
		    	}
		    }).start();
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						sema2.release();
					}
				});
			}
		}
		
		private void updateDiagramAfterSubjectChanges(DiagramFacet diagram, boolean initialRun)
	  {
	    for (ViewUpdatePassEnum pass : ViewUpdatePassEnum.values())
		      diagram.formViewUpdate(pass, initialRun);
	  }		

		public void clearTransactionHistory()
		{
			for (DiagramFacet d : GlobalDiagramRegistry.registry.getDiagrams())
			{
		    for (ViewUpdatePassEnum pass : ViewUpdatePassEnum.values())
		    	d.formViewUpdate(pass, true);
				d.clearTransactionHistory();
			}
			GlobalSubjectRepository.repository.clearTransactionHistory();
			clearDeltaEngine();
		}

		public void enforceTransactionDepth(int desiredDepth)
		{
			for (DiagramFacet d : GlobalDiagramRegistry.registry.getDiagrams())
				d.enforceTransactionDepth(desiredDepth);
			GlobalSubjectRepository.repository.enforceTransactionDepth(desiredDepth);
			
		}

		public String getRedoTransactionDescription()
		{
			return GlobalSubjectRepository.repository.getRedoTransactionDescription();
		}

		public int getTotalTransactions()
		{
			return GlobalSubjectRepository.repository.getTotalTransactions();
		}

		public int getTransactionPosition()
		{
			return GlobalSubjectRepository.repository.getTransactionPosition();
		}

		public String getUndoTransactionDescription()
		{
			return GlobalSubjectRepository.repository.getUndoTransactionDescription();
		}

		public boolean inTransaction()
		{
			return inTransaction;
		}

		public void dismissCurrentDialog()
		{
			if (currentDialog != null)
				currentDialog.dispose();
			currentDialog = null;
		}

		@Override
		public void invokeLater(Runnable runnable)
		{
			inBackground(false, runnable);
		}
	}

	public static void clearDeltaEngine()
	{
	  GlobalDeltaEngine.engine = new UML2DeltaEngine();
	}

	private void registerRecreator(PersistentFigureRecreatorFacet recreatorFacet)
	{
		PersistentFigureRecreatorRegistry.registry.registerRecreator(recreatorFacet);
	}

	private void registerRecreators()
	{
  	BasicPersistentFigureRecreatorRegistry recreatorRegistry = new BasicPersistentFigureRecreatorRegistry();
  	PersistentFigureRecreatorRegistry.registry = recreatorRegistry;
		
		// register the recreators
		registerRecreator(new SimpleContainerCreatorGem().getNodeCreateFacet());
		registerRecreator(new PortCompartmentCreatorGem(true).getNodeCreateFacet());
		registerRecreator(new FeatureCompartmentCreatorGem(AttributeCreatorGem.FEATURE_TYPE, "Attributes", true).getNodeCreateFacet());
		registerRecreator(new FeatureCompartmentCreatorGem(OperationCreatorGem.FEATURE_TYPE, "Operations", true).getNodeCreateFacet());
		registerRecreator(new FeatureCompartmentCreatorGem(SlotCreatorGem.FEATURE_TYPE, "Slots", true).getNodeCreateFacet());
		registerRecreator(new AttributeCreatorGem().getNodeCreateFacet());
		registerRecreator(new OperationCreatorGem().getNodeCreateFacet());
		registerRecreator(new SlotCreatorGem().getNodeCreateFacet());
    registerRecreator(new FreetextCreatorGem().getNodeCreateFacet());
 		registerRecreator(new NoteCreatorGem().getNodeCreateFacet());
		registerRecreator(new NoteLinkCreatorGem().getArcCreateFacet());;
    registerRecreator(new ModelCreatorGem(false).getNodeCreateFacet());
		registerRecreator(new PackageCreatorGem(false).getNodeCreateFacet());
		registerRecreator(new ClassCreatorGem().getNodeCreateFacet());
		registerRecreator(new ProfileCreatorGem(false).getNodeCreateFacet());
		registerRecreator(new StereotypeCreatorGem().getNodeCreateFacet());
    registerRecreator(new PartCreatorGem(false).getNodeCreateFacet());
    registerRecreator(new PartCreatorGem(true).getNodeCreateFacet());
    registerRecreator(new ConnectorCreatorGem().getArcCreateFacet());
		registerRecreator(new AssociationCreatorGem().getArcCreateFacet());;
		registerRecreator(new PortCreatorGem().getNodeCreateFacet());
    registerRecreator(new PortInstanceCreatorGem().getNodeCreateFacet());
		registerRecreator(new LinkedTextCreatorGem().getNodeCreateFacet());
		registerRecreator(new InterfaceCreatorGem().getNodeCreateFacet());
		registerRecreator(new InheritanceCreatorGem().getArcCreateFacet());
    registerRecreator(new DependencyCreatorGem().getArcCreateFacet());
		registerRecreator(new ImplementationCreatorGem().getArcCreateFacet());
		registerRecreator(new ContainmentCreatorGem().getArcCreateFacet());
		registerRecreator(new NaryCreatorGem().getNodeCreateFacet());
		registerRecreator(new NodeCreatorGem(true).getNodeCreateFacet());
		registerRecreator(new MeasureBoxCreatorGem().getNodeCreateFacet());
    registerRecreator(new ImageCreatorGem().getNodeCreateFacet());
		registerRecreator(new GrouperCreatorGem().getNodeCreateFacet());
    registerRecreator(new BaselineCreatorGem().getNodeCreateFacet());
    registerRecreator(new RegionCreatorGem().getNodeCreateFacet());
    registerRecreator(new LifelineCreatorGem().getArcCreateFacet());
    registerRecreator(new MessageCreatorGem().getArcCreateFacet());
    registerRecreator(new SequenceSectionCreatorGem().getNodeCreateFacet());
    registerRecreator(new RequirementsFeatureCreatorGem().getNodeCreateFacet());
    registerRecreator(new TraceCreatorGem().getArcCreateFacet());
    registerRecreator(new RequirementsFeatureLinkCreatorGem(RequirementsLinkKind.MANDATORY_LITERAL).getArcCreateFacet());
	}
}
