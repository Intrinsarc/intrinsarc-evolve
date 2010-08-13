package com.intrinsarc.evolve.packageview.base;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

import org.eclipse.uml2.Package;
import org.freehep.graphicsio.*;
import org.freehep.graphicsio.ps.*;
import org.freehep.swing.*;
import org.freehep.util.*;

import com.intrinsarc.evolve.clipboardactions.*;
import com.intrinsarc.evolve.guibase.*;
import com.intrinsarc.evolve.packageview.actions.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.diagramsupport.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;

import edu.umd.cs.jazz.util.*;

/**
 *
 * (c) Andrew McVeigh 09-Jan-03
 *
 */
public class ClipboardViewContextGem
{
  /** the emf export is offset by this much */
  private static final int offset = 32;
  /** we want to offset each side by this much */
  private static final int sidePad = 4;

	private ReusableDiagramViewContextFacet context = new ReusableDiagramViewContextFacetImpl();
	
	public ClipboardViewContextGem()
	{
	}
	
	public ReusableDiagramViewContextFacet getReusableDiagramViewContextFacet()
	{
		return context;
	}
	
	private class ReusableDiagramViewContextFacetImpl implements ReusableDiagramViewContextFacet
	{
		/**
		 * @see com.hopstepjump.jumble.packageview.ReusableDiagramViewContextFacet#getFrameTitle(DiagramFacet)
		 */
		public String getFrameTitle(DiagramFacet diagram)
		{
			return null;
		}
				
		/**
		 * @see com.hopstepjump.jumble.packageview.ReusableDiagramViewContextFacet#middleButtonPressed(DiagramFacet)
		 */
		public void middleButtonPressed(DiagramFacet diagram)
		{
		}
		
		/**
		 * @see com.hopstepjump.jumble.packageview.ReusableDiagramViewContextFacet#addToContextMenu(JPopupMenu)
		 */
		public void addToContextMenu(JPopupMenu popup, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			popup.add(getCopyAsImageItem(diagramView));
			popup.add(getCopyAsMetafileItem(diagramView));
      popup.add(saveToFileAsEPSItem(diagramView));
      popup.add(saveToFileAsImageItem(diagramView));
		}

		private JMenuItem saveToFileAsEPSItem(final DiagramViewFacet diagramView)
    {
      // for adding operations
      JMenuItem copyAsImage = new JMenuItem("Save to file as EPS");

      copyAsImage.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          // make a new diagram view, and copy it to the system clipboard as an image
          ZCanvas canvas = new ZCanvas();

          // diagram view -- we only need the canvas here
          DiagramViewFacet view = new BasicDiagramViewGem(
              GlobalPackageViewRegistry.activeRegistry.getClipboardDiagram(PackageViewRegistryGem.CLIPBOARD_TYPE),
              diagramView.getAdorners(),
              canvas,
              new UDimension(1, 1),
              Color.white,
              false).getDiagramViewFacet();

          // make an image, and paint on it
          UBounds bounds = view.getDrawnBounds();

          // copy to clipboard
          askForFileAndSaveAsEPS(canvas, bounds);
        }
      });
      return copyAsImage;
    }

		private JMenuItem saveToFileAsImageItem(final DiagramViewFacet diagramView)
    {
      // for adding operations
      JMenuItem copyAsImage = new JMenuItem("Save to file as image");


			copyAsImage.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
          // make a new diagram view, and copy it to the system clipboard as an image
          ZCanvas canvas = new ZCanvas();

          // diagram view -- we only need the canvas here
          DiagramViewFacet view = new BasicDiagramViewGem(
              GlobalPackageViewRegistry.activeRegistry.getClipboardDiagram(PackageViewRegistryGem.CLIPBOARD_TYPE),
              diagramView.getAdorners(),
              canvas,
              new UDimension(1, 1),
              ScreenProperties.getTransparentColor(),
              false).getDiagramViewFacet();

          // make an image, and paint on it
          UBounds bounds = view.getDrawnBounds();

					// make a new diagram view, and copy it to the system clipboard as an image
          askForFileAndSaveAsImage(canvas, bounds);
				}
			});
      return copyAsImage;
    }

    private JMenuItem getCopyAsImageItem(final DiagramViewFacet diagramView)
		{
			// for adding operations
			JMenuItem copyAsImage = new JMenuItem("Copy to system clipboard as image");

			copyAsImage.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// make a new diagram view, and copy it to the system clipboard as an image
					copyToClipboardAsImage(diagramView);
				}
			});
			return copyAsImage;
		}

		private JMenuItem getCopyAsMetafileItem(final DiagramViewFacet diagramView)
		{
			// for adding operations
			JMenuItem copyAsImage = new JMenuItem("Copy to system clipboard as metafile");

			copyAsImage.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// make a new diagram view, and copy it to the system clipboard as a JPEG
					CopyAction.copyDiagramToClipboardAsMetafile(
							GlobalPackageViewRegistry.activeRegistry.getClipboardDiagram(PackageViewRegistryGem.CLIPBOARD_TYPE),
							diagramView.getAdorners());
				}
			});
			return copyAsImage;
		}
		
		/**
		 * @see com.intrinsarc.evolve.guibase.ReusableDiagramViewContextFacet#haveClosed(ReusableDiagramViewFacet)
		 */
		public void haveClosed(ReusableDiagramViewFacet viewFacet)
		{
			GlobalPackageViewRegistry.activeRegistry.haveClosed(viewFacet);
		}

		/**
		 * @see com.intrinsarc.evolve.guibase.ReusableDiagramViewContextFacet#haveFocus(ReusableDiagramViewFacet)
		 */
		public void haveFocus(ReusableDiagramViewFacet viewFacet)
		{
			GlobalPackageViewRegistry.activeRegistry.haveFocus(viewFacet);
		}

		public SmartMenuContributorFacet getSmartMenuContributorFacet()
		{
			return null;
		}

		public boolean isModified(DiagramFacet diagram)
		{
			return false;
		}

    public void setFrameName(DiagramViewFacet diagramView, Package fixedPerspective)
    {
    	PackageViewContextGem.setPerspectiveNameName(diagramView, fixedPerspective);
    }
	}
	
	private void copyToClipboardAsImage(DiagramViewFacet diagramView)
	{
		// get the diagram first
		DiagramFacet diagram = GlobalPackageViewRegistry.activeRegistry.getClipboardDiagram(PackageViewRegistryGem.CLIPBOARD_TYPE);

		// make a new canvas
		ZCanvas canvas = new ZCanvas();

		// diagram view -- we only need the canvas here
		DiagramViewFacet view =
			new BasicDiagramViewGem(
				diagram,
				diagramView.getAdorners(),
				canvas,
				new UDimension(1,1),
				Color.white,
				false).getDiagramViewFacet();
				
		// make an image, and paint on it
		UBounds bounds = view.getDrawnBounds();
		int width = (int) Math.round(bounds.getWidth() + 1);
		int height = (int) Math.round(bounds.getHeight() + 1);
		view.pan(-bounds.getX(), -bounds.getY());
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		canvas.setSize(new Dimension(width, height));
		canvas.paint(image.getGraphics());
		
		// copy the image to the clipboard
		ImageTransferable transferImage = new ImageTransferable(image);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferImage, transferImage);
	}

  public static void askForFileAndSaveAsEPS(ZCanvas canvas, UBounds bounds)
  {
    // get the file name
    JFileChooser chooser = new JFileChooser();
    // Note: source for ExampleFileFilter can be found in FileChooserDemo,
    // under the demo/jfc directory in the Java 2 SDK, Standard Edition.
    ExtensionFileFilter filter = new ExtensionFileFilter();
    filter.addExtension("ps");
    filter.addExtension("eps");
    filter.setDescription("Postscript files");
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(canvas);
    File fileChosen = null;
    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      fileChosen = chooser.getSelectedFile();
      saveAsEPS(fileChosen, canvas, bounds);
    }
  }

  public static void askForFileAndSaveAsImage(ZCanvas canvas, UBounds bounds)
  {
    // get the file name
    JFileChooser chooser = new JFileChooser();
    // Note: source for ExampleFileFilter can be found in FileChooserDemo,
    // under the demo/jfc directory in the Java 2 SDK, Standard Edition.
    ExtensionFileFilter filter = new ExtensionFileFilter();
    filter.addExtension("png");
    filter.setDescription("PNG files");
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(canvas);
    File fileChosen = null;
    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      fileChosen = chooser.getSelectedFile();
      saveAsImage(fileChosen, canvas, bounds, "png");
    }
  }

  public static void saveAsImage(File file, ZCanvas canvas, UBounds bounds, String type)
  {
    double x = Math.max(bounds.getX() - 1, 0);
    double y = Math.max(bounds.getY() - 1, 0);
    double width = bounds.getWidth() + 1;
    double height = bounds.getHeight() + 1;
    int rx = (int) Math.round(x);
    int ry = (int) Math.round(y);
    int rwidth = (int) Math.round(width);
    int rheight = (int) Math.round(height);
    canvas.setSize((int) Math.round(x + width), (int) Math.round(y + height));

    try
    {
      // the image is offset by about 30 twips
      setUserProperty(PageConstants.ORIENTATION, PageConstants.PORTRAIT);
      setUserProperty(PageConstants.FIT_TO_PAGE, false);
      setUserProperty("Preview", true);
      
      Dimension size = new Dimension(
          rwidth + rx - offset + sidePad * 2,
          rheight + ry - offset + sidePad * 2);
      BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
      image.getGraphics().fillRect(0, 0, size.width, size.height);

      Graphics2D g = (Graphics2D) image.getGraphics();
      g.translate((double) - offset + sidePad, -offset + sidePad);
      canvas.paint(g);
      
      // now export the file
      ImageIO.write(image, type, file);
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
  }
  

  public static void saveAsEPS(File file, ZCanvas canvas, UBounds bounds)
  {
    double x = Math.max(bounds.getX() - 1, 0);
    double y = Math.max(bounds.getY() - 1, 0);
    double width = bounds.getWidth() + 1;
    double height = bounds.getHeight() + 1;
    int rx = (int) Math.round(x);
    int ry = (int) Math.round(y);
    int rwidth = (int) Math.round(width);
    int rheight = (int) Math.round(height);
    canvas.setSize((int) Math.round(x + width), (int) Math.round(y + height));

    try
    {
      final FileOutputStream out = new FileOutputStream(file);

      // the image is offset by about 30 twips
      setUserProperty(PageConstants.ORIENTATION, PageConstants.PORTRAIT);
      setUserProperty(PageConstants.FIT_TO_PAGE, false);
      setUserProperty("Preview", true);
      
      PSGraphics2D g =
        new PSGraphics2D(out, new Dimension(rwidth + rx - offset + sidePad * 2, rheight + ry - offset + sidePad * 2));
      g.startExport();
      g.translate((double) - offset + sidePad, -offset + sidePad);
      g.clipRect(0, 0, rwidth + rx, rheight + ry);
      canvas.paint(g);
      g.endExport();
      out.close();
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
  }
  
  private static void setUserProperty(String name, String value)
  {
    UserProperties properties = (UserProperties) PSGraphics2D.getDefaultProperties();
    properties.setProperty(
        PSGraphics2D.class.getName() + "." + name, value);    
  }

  private static void setUserProperty(String name, boolean value)
  {
    UserProperties properties = (UserProperties) PSGraphics2D.getDefaultProperties();
    properties.setProperty(
        PSGraphics2D.class.getName() + "." + name, value);    
  }
}
