package com.hopstepjump.jumble.clipboardactions;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.freehep.graphicsio.emf.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.utility.*;

import edu.umd.cs.jazz.util.*;

/**
 *
 * (c) Andrew McVeigh 20-Jan-03
 *
 */


public class CopyAction extends AbstractAction
{
  public static boolean USE_FREEHEP = false;

  /** the emf export is offset by this much */
  private static final int offset = 32;
  /** we want to offset each side by this much */
  private static final int sidePad = 4;
	
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
  	copyToClipboardAsExtendedMetafile(canvas, bounds);
  }

  private static void copyToClipboardAsExtendedMetafile(ZCanvas canvas, UBounds bounds)
  {
    double x = bounds.getX() - 1;
    double y = bounds.getY() - 1;
    double width = bounds.getWidth() + 1;
    double height = bounds.getHeight() + 1;
    int rx = (int) Math.round(x);
    int ry = (int) Math.round(y);
    int rwidth = (int) Math.round(width);
    int rheight = (int) Math.round(height);
    canvas.setSize((int) Math.round(x + width), (int) Math.round(y + height));

    // move it to the clipboard
    String headerString =
      "{\\rtf1\\ansi\\ansicpg1252\\uc1 \\viewkind1\\viewscale100"
        + "{{\\*\\shppict{\\pict\\picscalex25\\picscaley25\\piccropl0"
        + "\\piccropr0\\piccropt0\\piccropb0\\emfblip{\\*}";
    String footerString = "}}\\par }}";
    try
    {
      final ByteArrayOutputStream out = new ByteArrayOutputStream();

      // place the data on the clipboards
      OutputStream test = new OutputStream()
      {
        public void write(int b) throws IOException
        {
          if (b < 0)
            b = 256 + b;
          HexUtility.writeRawString(out, HexUtility.int8ToHexString(b));
        }
      };

      out.write(headerString.getBytes());

      // the image is offset by about 30 twips
      EMFGraphics2D g =
        new EMFGraphics2D(test, new Dimension(rwidth + rx - offset + sidePad * 2, rheight + ry - offset + sidePad * 2));
      g.startExport();
      g.translate((double) - offset + sidePad, -offset + sidePad);
      g.clipRect(0, 0, rwidth + rx, rheight + ry);
      canvas.paint(g);
      g.endExport();

      out.write(footerString.getBytes());

      InputStream s = new ByteArrayInputStream(out.toByteArray());
      MetafileTransferable transferImage = new MetafileTransferable(s);
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferImage, transferImage);
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
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
