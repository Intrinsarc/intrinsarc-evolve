package com.intrinsarc.evolve.clipboardactions;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.freehep.graphicsio.emf.*;

import com.intrinsarc.evolve.freeform.grouper.*;
import com.intrinsarc.evolve.packageview.base.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.diagramsupport.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.utilities.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.repository.modelmover.*;

import edu.umd.cs.jazz.util.*;

/**
 *
 * (c) Andrew McVeigh 20-Jan-03
 *
 */


public class CopyAction extends AbstractAction
{
	private DiagramViewFacet diagramView;
	private ToolCoordinatorFacet coordinator;
  private DiagramFacet clipboard;

	public static final UPoint CLIPBOARD_START_POINT = Grid.roundToGrid(new UPoint(32, 32));
	
	public CopyAction(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, DiagramFacet clipboard)
	{
		super("Copy");
		this.coordinator = coordinator;
		this.diagramView = diagramView;
		this.clipboard = clipboard;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Collection<String> includedFigureIds = CopyToDiagramUtilities.getFigureIdsIncludedInSelectionCopy(diagramView);
		
		// only proceed if we actually have some figures to consider
		if (includedFigureIds.isEmpty())
			return;

		DiagramFacet src = diagramView.getDiagram();
		
		coordinator.startTransaction("copied figures to clipboard", "removed figures from clipboard");
		CopyCommandGenerator.copy(
		    includedFigureIds,
		    clipboard,
		    src);		
		coordinator.commitTransaction();

		// copy the clipboard diagram, as a metafile, to the system clipboard
		copyDiagramToClipboardAsMetafile(clipboard, diagramView.getAdorners());
	}

  public static void copyDiagramToClipboardAsMetafile(DiagramFacet diagram, List<DiagramFigureAdornerFacet> adorners)
  {
    // make a new canvas
    ZCanvas canvas = new ZCanvas();
  
    // diagram view -- we only need the canvas here
    DiagramViewFacet view = new BasicDiagramViewGem(diagram, adorners, canvas, new UDimension(1, 1), Color.white, false).getDiagramViewFacet();
  
    // make an image, and paint on it
    UBounds bounds = view.getDrawnBounds();
  
    // copy to clipboard
  	ClipboardViewContextGem.saveAsRTFMetafile(null, canvas, bounds);
  }

  public static class CopyCommandGenerator
	{
		public static void copy(Collection<String> includedFigureIds, DiagramFacet clipboard, DiagramFacet src)
		{
			if (includedFigureIds.size() != 0) 
				copyToClipboard(clipboard, src, includedFigureIds);
			else
				clipboard.revert();
		};
			
		public static void copyToClipboard(DiagramFacet clipboard, DiagramFacet src, Collection<String> includedFigureIds)
		{
			Collection<PersistentFigure> persistentFigures =
			  CopyToDiagramUtilities.makePersistentFiguresAndAssignNewIds(src, includedFigureIds, clipboard, true);
			UBounds bounds = CopyToDiagramUtilities.calculateFigureBounds(src, includedFigureIds);
		
			// clear the clipboard...
			clipboard.revert();

			// make sure that the clipboard has the same "frame of reference" as the source diagram
      clipboard.setLinkedObject(src.getLinkedObject());
      
			UDimension offset = Grid.roundToGrid(CLIPBOARD_START_POINT.subtract(bounds.getPoint()));
			clipboard.addPersistentFigures(persistentFigures, offset);
		}
	}
}
