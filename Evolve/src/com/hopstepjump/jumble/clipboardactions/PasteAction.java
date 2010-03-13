package com.hopstepjump.jumble.clipboardactions;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.sun.xml.internal.bind.v2.runtime.*;

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
		
		Collection<String> figureIdsToCopy = CopyToDiagramUtilities.getAllFigureIdsInDiagram(clipboard);
		if (!figureIdsToCopy.isEmpty())
		{
			DiagramFacet destination = diagramView.getDiagram();			
			// work out the offset
			UDimension offset = Grid.roundToGrid(new UPoint(10, 10).subtract(CopyAction.CLIPBOARD_START_POINT));
			if (fromKeyBoard)
				offset = Grid.roundToGrid(diagramView.getCursorPoint().subtract(CopyAction.CLIPBOARD_START_POINT));
			
			coordinator.startTransaction("pasted figures from clipboard", "removed pasted figures");
			Set<String> topLevelIds = PasteCommandGenerator.paste(
			      clipboard,
			      destination,
            figureIdsToCopy,
            offset);			
			coordinator.commitTransaction();
			
			// select the top level figures of the pasted items on the screen
			CopyToDiagramUtilities.selectFigures(diagramView, topLevelIds);
		}
	}

  public static class PasteCommandGenerator
	{
		public static Set<String> paste(DiagramFacet clipboard, DiagramFacet destination, Collection<String> figureIdsToCopy, UDimension offset)
		{
			Collection<PersistentFigure> persistentFigures = CopyToDiagramUtilities.makePersistentFiguresAndAssignNewIds(clipboard, figureIdsToCopy, destination, true);
			destination.addPersistentFigures(persistentFigures, offset);

			Set<String> newFigures = new HashSet<String>();
			for (PersistentFigure persistentFigure : persistentFigures)
				newFigures.add(persistentFigure.getId());
			return CopyToDiagramUtilities.getTopLevelFigureIdsOnly(destination, newFigures, CopyToDiagramUtilities.WANT_ALL, false);
		}
	}
	
	public boolean wantsKey(KeyEvent event)
	{
		return event.getKeyCode() == KeyEvent.VK_V && event.isControlDown();
	}
}
