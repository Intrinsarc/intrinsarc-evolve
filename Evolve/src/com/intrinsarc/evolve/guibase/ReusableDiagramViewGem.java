package com.intrinsarc.evolve.guibase;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.easydock.*;
import com.intrinsarc.evolve.packageview.actions.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.diagramsupport.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;
import com.intrinsarc.swing.enhanced.*;

import edu.umd.cs.jazz.util.*;

/**
 * 
 * (c) Andrew McVeigh 12-Sep-02
 * 
 */
public class ReusableDiagramViewGem implements Gem
{
	private static int DIAGRAM_CONTENTION_POLL_MS = 1000;
	private static final ImageIcon GLASSES_ICON = IconLoader.loadIcon("glasses.png");
	private static final Icon UNMODIFIED_ICON = IconLoader.loadIcon("diagram.png");
	private static final Icon MODIFIED_ICON = IconLoader.loadIcon("diagram-modified.png");

	private static final int MAX_NAME_SIZE = 30;
	private static final ImageIcon MAGNIFY_ICON = IconLoader.loadIcon("magnifier.png");
	private ReusableDiagramViewFacet viewFacet = new ReusableDiagramViewFacetImpl();
	private DiagramFacet diagram;
	private DiagramViewFacet diagramView;
	private DiagramListenerFacet diagramListener;
	private IEasyDock desktop;
	private String listenerId;
	private ToolCoordinatorFacet coordinator;
	private IEasyDockable internalFrame;
	private ZCanvas canvas;
	private DiagramViewContextFacet diagramViewContext = new DiagramViewContextFacetImpl();
	private KeyInterpreterFacet keyInterpreterFacet;
	private String type;
	private ReusableDiagramViewContextFacet reusableContext;
	private String explicitName;
	private Color backgroundColor;
	private boolean isUsingTools;
	private org.eclipse.uml2.Package fixedPerspective;
	private boolean isModified;
	private List<DiagramFigureAdornerFacet> adorners;
	private SubjectRepositoryListenerFacet repositoryListener = new SubjectRepositoryListenerImpl();
	private DiagramStack stack = new DiagramStack();
	private boolean closed;

	public static final Preference DIAGRAM_BACKGROUND = new Preference(
			"Appearance", "Diagram background", new PersistentProperty(Color.WHITE));
	public static final Preference READONLY_DIAGRAM_BACKGROUND = new Preference(
			"Appearance", "Diagram background (read only)", new PersistentProperty(new Color(255, 255, 210)));

	static
	{
		GlobalPreferences.preferences.addPreferenceSlot(DIAGRAM_BACKGROUND,
				new PreferenceTypeColor(), "The background colour of diagrams.");

		GlobalPreferences.preferences.addPreferenceSlot(
				READONLY_DIAGRAM_BACKGROUND, new PreferenceTypeColor(),
				"The background colour of read only diagrams.");
	}

	public ReusableDiagramViewGem(ToolCoordinatorFacet coordinator,
			List<DiagramFigureAdornerFacet> adorners, IEasyDock desktop,
			String type, boolean closeable, Color backgroundColor,
			boolean isUsingTools, Package fixedPerspective)
	{
		this.coordinator = coordinator;
		this.desktop = desktop;
		this.listenerId = "" + System.currentTimeMillis() + ":" + Math.random();
		this.type = type;
		this.backgroundColor = backgroundColor;
		this.isUsingTools = isUsingTools;
		this.adorners = adorners;
		this.fixedPerspective = fixedPerspective;
		GlobalSubjectRepository.repository.addRepositoryListener(repositoryListener);
		
		// if we are in the team version, start monitoring for diagram updates
		final SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
		if (repository.isTeam())
			new Thread(new Runnable()
			{
				public void run()
				{
					while (!closed)
					{
						try
						{
							Thread.sleep(DIAGRAM_CONTENTION_POLL_MS);
						}
						catch (InterruptedException e)
						{
						}
						SwingUtilities.invokeLater(new Runnable()
						{
							public void run()
							{
								if (diagram != null && !diagram.isClipboard())
									refreshDiagramConflictDetails(repository);
							}
						});
					}
				}			
			}).start();
	}
	
	private class SubjectRepositoryListenerImpl implements SubjectRepositoryListenerFacet
	{
		public void sendChanges()
		{
			// if the current diagram has a perspective and it shouldn't, or vice versa,
			// reopen the current diagram
			if (diagram.getPossiblePerspective() != null != isInTransitiveOfPerspective(diagram))
				viewFacet.viewDiagram(diagram.getSource() == null ? diagram : diagram.getSource(), null, false);
		}		
	}

	public ReusableDiagramViewFacet getReusableDiagramViewFacet()
	{
		return viewFacet;
	}

	public void connectReusableDiagramViewContextFacet(ReusableDiagramViewContextFacet reusableContext)
	{
		this.reusableContext = reusableContext;
	}

	public void connectKeyInterpreterFacet(KeyInterpreterFacet interpreterFacet)
	{
		this.keyInterpreterFacet = interpreterFacet;
	}

	private class ReusableDiagramViewFacetImpl implements ReusableDiagramViewFacet
	{
		public void removeAsDiagramListener()
		{
			diagram.removeListener(listenerId);
		}

		public void viewDiagram(DiagramFacet newDiagram, UBounds openRegionHint, boolean addToStack)
		{
			stack.addToStack(newDiagram.getDiagramReference());
			
			// if we are in fixed perspective mode, we may need to adjust slightly
			if (fixedPerspective != null)
			{
				if (isInTransitiveOfPerspective(newDiagram))
				{
					DiagramReference reference = new DiagramReference(newDiagram.getDiagramReference().getId() + "-" + fixedPerspective.getUuid());
					newDiagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(reference, newDiagram, fixedPerspective, new FixedPerspectiveDiagramPostProcessor());
				}
			}
							
			boolean newFrame = false;
			DiagramFacet oldDiagram = diagram;
			diagram = newDiagram;
			
			J_DiagramHolder holder = ((Package) newDiagram.getLinkedObject()).getJ_diagramHolder();
			DiagramSaveDetails saveDetails = new DiagramSaveDetails(holder.getSavedBy(), holder.getSaveTime());
			diagram.setSaveDetails(saveDetails);

			// if we need an internal frame, make one
			if (internalFrame == null)
			{
				JPanel panel = makeDiagramView(diagram);
				internalFrame = desktop.createWorkspaceDockable("", null, true, true, panel);

				newFrame = true;

				// make sure that we pick up when this is closed
				internalFrame.addListener(new IEasyDockableListener()
				{
					public void hasClosed()
					{
						// deregister the listener, and tell the view registry
						if (diagram != null)
							removeAsDiagramListener();
						reusableContext.haveClosed(viewFacet);
						GlobalSubjectRepository.repository.removeRepositoryListener(repositoryListener);
						closed = true;
					}

					public void hasFocus()
					{
					}
				});

				// listen for keys
				diagramView.addKeyListenerJustOnce(new KeyAdapter()
				{
					public void keyReleased(KeyEvent e)
					{
						if (keyInterpreterFacet != null)
							keyInterpreterFacet.keyReleased(e, coordinator, diagramView);
					}
				});

				// make sure we know when this has been focussed
				if (newFrame)
					addFocusListener(desktop, internalFrame, panel);
			}
			else
			{
				diagramView.transitionToDiagram(diagram, openRegionHint);
				// detach a possible old view from the diagram as a listener
				if (diagramView != null)
					oldDiagram.removeListener(listenerId);
				addDiagramListener(diagramView, diagramListener, diagram);
				diagram.sendChangesToListeners();
			}

			// pick up the correct title and modified flag
      diagram.sendChangesToListeners();
			diagramViewContext.refreshViewAttributes();
		}

		private JPanel makeDiagramView(DiagramFacet diagram)
		{
			// --> make a drawing canvas
			canvas = new ZCanvas();
			canvas.getDrawingSurface().setUseFractionalMetrics(false);

			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();

			JPanel panel = new JPanel(gbl);
			final JScrollBar xScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
			xScrollBar.setAutoscrolls(true);
			xScrollBar.setUnitIncrement(25);
			final JScrollBar yScrollBar = new JScrollBar();
			yScrollBar.setAutoscrolls(true);
			yScrollBar.setUnitIncrement(25);
			
			panel.addMouseWheelListener(new MouseWheelListener()
			{
				public void mouseWheelMoved(MouseWheelEvent e)
				{
					if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
					{
						if (e.isShiftDown())
						{
							int totalScrollAmount = e.getUnitsToScroll() * xScrollBar.getUnitIncrement();
							xScrollBar.setValue(xScrollBar.getValue() + totalScrollAmount);
						}
						else
						{
							int totalScrollAmount = e.getUnitsToScroll() * yScrollBar.getUnitIncrement();
							yScrollBar.setValue(yScrollBar.getValue() + totalScrollAmount);
						}
					}
				}
			});

			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			panel.add(canvas, gbc);

			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.weightx = 0;
			gbc.weighty = 0;
			panel.add(yScrollBar, gbc);
			yScrollBar.setVisible(false);

			gbc.gridx = 0;
			gbc.gridy = 1;
			panel.add(xScrollBar, gbc);
			xScrollBar.setVisible(false);

			gbc.gridx = 1;
			gbc.gridy = 1;
			JLabel overviewArea = new JLabel(MAGNIFY_ICON);
			overviewArea.setToolTipText("View overview of entire diagram");
			panel.add(overviewArea, gbc);
			overviewArea.setVisible(false);

			gbl.setConstraints(panel, gbc);

			// diagram view
			BasicDiagramViewGem diagramViewGem =
				new BasicDiagramViewGem(
					diagram,
					adorners,
					canvas,
					new UDimension(1, 1),
					backgroundColor,
					isUsingTools);
			diagramViewGem.connectDiagramViewContextFacet(diagramViewContext);
			BasicDiagramScrollerGem scrollerGem =
				new BasicDiagramScrollerGem(
					xScrollBar,
					yScrollBar,
					overviewArea,
					backgroundColor);
			diagramViewGem.connectBasicDiagramScrollerFacet(
				scrollerGem.getBasicDiagramScrollerFacet());
			scrollerGem.connectBasicDiagramViewFacet(
			diagramViewGem.getBasicDiagramViewFacet());

			// need to do this to possibly re-activate the scrollbars in a new window
			// because we have made them invisible above
			DiagramViewFacet newDiagramView = diagramViewGem.getDiagramViewFacet();

			diagramView = newDiagramView;
			diagramListener = diagramViewGem.getDiagramListenerFacet();
			addDiagramListener(diagramView, diagramListener, diagram);
			
			canvas.addComponentListener(new ComponentAdapter()
			{
				@Override
				public void componentResized(ComponentEvent e)
				{
					if (internalFrame != null)
						diagramViewContext.refreshViewAttributes();
				}
			});
			return panel;
		}

		/**
		 * @param diagramViewGem
		 * @param diagram
		 */
		private void addDiagramListener(DiagramViewFacet diagramView,
				DiagramListenerFacet diagramListener, DiagramFacet diagram)
		{
			// add a listener
			diagram.addListener(listenerId, diagramListener);
		}

		private void addFocusListener(final IEasyDock desktop,
				final IEasyDockable internalFrame, JPanel panel)
		{
			refocus();

			internalFrame.addListener(new IEasyDockableListener()
			{
				public void hasClosed()
				{
				}

				public void hasFocus()
				{
					refocus();
				}				
			});
		}

		private void refocus()
		{
			coordinator.attachTo(diagramView, canvas, canvas.getCameraNode());
			reusableContext.haveFocus(viewFacet);
		}

		public String getId()
		{
			return listenerId;
		}

		public String getType()
		{
			return type;
		}

		public void setExplicitName(String name)
		{
			explicitName = name;
		}

		/**
		 * @see com.intrinsarc.evolve.guibase.ReusableDiagramViewFacet#attachToCoordinator()
		 */
		public void attachToCoordinator()
		{
			coordinator.attachTo(diagramView, canvas, canvas.getCameraNode());
			// need the following to regain the focus, for reasons I don't quite
			// understand...
			coordinator.reestablishCurrentTool();
			reusableContext.haveFocus(viewFacet);
		}

		/*
		 * @see com.intrinsarc.jumble.guibase.ReusableDiagramViewFacet#getCurrentDiagramView()
		 */
		public DiagramViewFacet getCurrentDiagramView()
		{
			return diagramView;
		}

		public void resetToMovingPerspective()
		{
			fixedPerspective = null;
			viewDiagram(diagram, null, false);
		}

		public void setFixedPerspective(Package possibleFixedPerspective)
		{
			fixedPerspective = possibleFixedPerspective;
			viewDiagram(diagram, null, false);
		}

		public Package getFixedPerspective()
		{
			return fixedPerspective;
		}

		public DiagramStack getDiagramStack()
		{
			return stack;
		}
	}

	private boolean isInTransitiveOfPerspective(DiagramFacet diagram)
	{
		if (fixedPerspective == null)
			return false;
		IDeltaEngine engine = GlobalDeltaEngine.engine;
		DEStratum base = engine.locateObject(diagram.getLinkedObject()).asStratum();
		DEStratum fixed = engine.locateObject(fixedPerspective).asStratum();
		return fixed.getTransitive().contains(base);
	}

	private class DiagramViewContextFacetImpl implements DiagramViewContextFacet
	{
		public JPopupMenu makeContextMenu(final ToolCoordinatorFacet coordinator)
		{
			JPopupMenu popup = new JPopupMenu();
			reusableContext.addToContextMenu(popup, diagramView, coordinator);
			
      Utilities.addSeparator(popup);
			popup.add(new AbstractAction("Set as fixed perspective", GLASSES_ICON)
			{					
				public void actionPerformed(ActionEvent e)
				{
					fixedPerspective = (Package) diagramView.getDiagram().getLinkedObject();
					if (!UMLTypes.isStratum(fixedPerspective))
						fixedPerspective = GlobalSubjectRepository.repository.findOwningStratum(fixedPerspective);
			
					viewFacet.viewDiagram(diagram.getSource() == null ? diagram : diagram.getSource(), null, false);
				}
			}
			);
			popup.add(new AbstractAction("Reset back to moving perspective")
			{
				public void actionPerformed(ActionEvent e)
				{
					fixedPerspective = null;
					viewFacet.viewDiagram(diagram.getSource() == null ? diagram : diagram.getSource(), null, false);
				}
			});

			return popup;
		}

		public SmartMenuContributorFacet getSmartMenuContributorFacet()
		{
			return reusableContext.getSmartMenuContributorFacet();
		}

		public void middleButtonPressed()
		{
			reusableContext.middleButtonPressed(diagram);
		}

		/**
		 * @see com.intrinsarc.idraw.diagramsupport.DiagramViewContextFacet#refreshViewAttributes()
		 */
		public void refreshViewAttributes()
		{
			if (explicitName != null)
				internalFrame.setTitleText(truncate(explicitName));
			else
			{
				internalFrame.setTitleText(truncate(reusableContext.getFrameTitle(diagram)));
				reusableContext.setFrameName(diagramView, fixedPerspective);
			}

			// set the icon showing the modifications status
			isModified = reusableContext.isModified(diagram);
			if (isModified)
				internalFrame.setTitleIcon(MODIFIED_ICON);
			else
				internalFrame.setTitleIcon(UNMODIFIED_ICON);

			if (!diagram.isClipboard())
				if (diagram.isReadOnly())
					diagramView.setBackgroundColor(GlobalPreferences.preferences.getRawPreference(READONLY_DIAGRAM_BACKGROUND).asColor());
				else
					diagramView.setBackgroundColor(GlobalPreferences.preferences.getRawPreference(DIAGRAM_BACKGROUND).asColor());
		}
		
		public String truncate(String name)
		{
			int len = name.length();
			if (len <= MAX_NAME_SIZE)
				return name;
			return "..." + name.substring(len - MAX_NAME_SIZE);
		}
	}
	
	public void refreshDiagramConflictDetails(SubjectRepositoryFacet repository)
	{
		// refresh the save details
		Package pkg = (Package) diagram.getLinkedObject();
		DiagramSaveDetails current = repository.getDiagramSaveDetails(pkg);
		DiagramSaveDetails local = diagram.getSaveDetails();
		if (current != null)
			reusableContext.setTeamDetails(diagramView, local, current);
	}
}
