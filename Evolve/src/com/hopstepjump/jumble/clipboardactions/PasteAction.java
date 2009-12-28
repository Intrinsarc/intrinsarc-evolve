package com.hopstepjump.jumble.clipboardactions;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

/**
 *
 * (c) Andrew McVeigh 22-Jan-03
 *
 */
public class PasteAction extends AbstractAction
{
	private DiagramViewFacet diagramView;
	private ToolCoordinatorFacet coordinator;
	private DiagramFacet clipboard;
	
	public PasteAction(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, DiagramFacet clipboard)
	{
		super("Paste");
		this.coordinator = coordinator;
		this.diagramView = diagramView;
		this.clipboard = clipboard;
	}
	
	public void actionPerformed(ActionEvent e)
	{
	  if (diagramView.getDiagram().isReadOnly())
	    return;
	  
		boolean fromKeyBoard = (e.getModifiers() & InputEvent.CTRL_MASK) != 0;
		
		Collection figureIdsToCopy = CopyToDiagramHelper.getAllFigureIdsInDiagram(clipboard);
		if (!figureIdsToCopy.isEmpty())
		{
			DiagramFacet destination = diagramView.getDiagram();			
			// work out the offset
			UDimension offset = Grid.roundToGrid(new UPoint(10, 10).subtract(CopyAction.CLIPBOARD_START_POINT));
			if (fromKeyBoard)
				offset = Grid.roundToGrid(diagramView.getCursorPoint().subtract(CopyAction.CLIPBOARD_START_POINT));
			
			PasteCommandGenerator paste =
			  new PasteCommandGenerator(
			      clipboard.getDiagramReference(),
			      destination.getDiagramReference(),
            figureIdsToCopy,
            offset);
			
			coordinator.executeCommandAndUpdateViews(paste.generateCommand());
			
      final Collection<String> topLevelIds = paste.getPasteTopLevelFigureIds();      
			diagramView.runWhenModificationsHaveBeenProcessed(new Runnable()
			{
				public void run()
				{
					// select the top level figures of the pasted items on the screen
					CopyToDiagramHelper.selectFigures(diagramView, topLevelIds);
				}
			});
		}
	}

  public static class PasteCommandGenerator
	{
		private DiagramReference clipboardReference;
		private DiagramReference destinationReference;
		private Collection figureIdsToCopy;
		private UDimension offset;
		private Collection<PersistentFigure> persistentFigures;
		
		public PasteCommandGenerator(DiagramReference clipboardReference, DiagramReference destinationReference, Collection figureIdsToCopy, UDimension offset)
		{
			this.clipboardReference = clipboardReference;
			this.destinationReference = destinationReference;
			this.figureIdsToCopy = figureIdsToCopy;
			this.offset = offset;
		}
		
		/*
		 * @see com.hopstepjump.idraw.foundation.CommandGeneratorFacet#generateCommand()
		 */
		public Command generateCommand()
		{
			DiagramFacet clipboard = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(clipboardReference);
			DiagramFacet destination = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(destinationReference);
			persistentFigures = CopyToDiagramHelper.makePersistentFiguresAndAssignNewIds(clipboard, figureIdsToCopy, destination, true);
			return
				new CopyToDiagramCommand("pasted from clipboard to diagram", "undid paste",
																 offset, destinationReference, persistentFigures);
		}

		/*
		 * @see com.hopstepjump.idraw.foundation.CommandGeneratorFacet#getReturnForClient()
		 */
		public Collection<String> getPasteTopLevelFigureIds()
		{
			// return the ids of the new elements to select the newly pasted elements
			DiagramFacet destination = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(destinationReference);
			Set<String> newFigures = new HashSet<String>();
			for (Iterator iter = persistentFigures.iterator(); iter.hasNext();)
			{
				PersistentFigure persistentFigure = (PersistentFigure) iter.next();
				newFigures.add(persistentFigure.getId());
			}
			return CopyToDiagramHelper.getTopLevelFigureIdsOnly(destination, newFigures, CopyToDiagramHelper.WANT_ALL, false);
		}

	}
	
	public boolean wantsKey(KeyEvent event)
	{
		return event.getKeyCode() == KeyEvent.VK_V && event.isControlDown();
	}
}
