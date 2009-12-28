/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;

/**
 * This creates a simple scene and allows switching between
 * traditional scrolling where the scrollbars control the view
 * and alternate scrolling where the scrollbars control the
 * document.  In laymans terms - in traditional window scrolling the stuff
 * in the window moves in the opposite direction of the scroll bars and in
 * document scrolling the stuff moves in the same direction as the scrollbars.
 *
 * Toggle buttons are provided to toggle between these two types
 * of scrolling.
 *
 * @author Lance Good
 * @author Ben Bederson
 */
public class ScrollingExample extends AbstractExample {
    public ScrollingExample() {
        super("Scrolling example");
    }
    public String getExampleDescription() {
        return "This creates a simple scene and allows switching between " +
	    "traditional scrolling where the scrollbars control the view " +
	    "and alternate scrolling where the scrollbars control the " +
	    "document. " +
	    "Toggle buttons are provided to toggle between these two types " +
            "of scrolling";
    }
    public void initializeExample() {
        super.initializeExample();

	final ZScrollPane scrollPane;
	final ZViewport viewport;
	final ZScrollDirector windowSD;
	final ZScrollDirector documentSD;
        final ZCanvas canvas;

        setBounds(100, 100, 400, 400);
        setResizable(true);
        setBackground(null);
        setVisible(true);
        canvas = new ZCanvas();
	scrollPane = new ZScrollPane(canvas);
        getContentPane().add(scrollPane);
	
	viewport = (ZViewport)scrollPane.getViewport();
	windowSD = viewport.getScrollDirector();
	documentSD = new DocumentScrollDirector();

                                // Make some rectangles on the surface so we can see where we are
	ZImage image = new ZImage(getClass().getClassLoader().getResource("HCIL-logo.gif"));
        ZRectangle rect;
	ZEllipse ellipse;
        for (int x=0; x<20; x++) {
            for (int y=0; y<20; y++) {
		if (((x + y) % 3) == 0) {
		    rect = new ZRectangle(50*x, 50*y, 40, 40);
		    rect.setFillPaint(Color.blue);
		    rect.setPenPaint(Color.black);
		    canvas.getLayer().addChild(new ZVisualLeaf(rect));
		}
		else if (((x + y) % 3) == 1) {
		    ellipse = new ZEllipse(50*x, 50*y, 40, 40);
		    ellipse.setFillPaint(Color.blue);
		    ellipse.setPenPaint(Color.black);
		    canvas.getLayer().addChild(new ZVisualLeaf(ellipse));
		}
		else if (((x + y) % 3) == 2) {
		    ZVisualLeaf leaf = new ZVisualLeaf(image);
		    canvas.getLayer().addChild(leaf);
		    ZBounds leafBounds = leaf.getBounds();
		    ZTransformGroup transform = leaf.editor().getTransformGroup();
		    transform.setScale(Math.min(40/leafBounds.getWidth(),40/leafBounds.getHeight()));
		    transform.setTranslation(50*x, 50*y);
		}
            }
        }
                                // Now, create the toolbar
        JToolBar toolBar = new JToolBar();
        JToggleButton window = new JToggleButton("Window Scrolling");
        JToggleButton document = new JToggleButton("Document Scrolling");
        ButtonGroup bg = new ButtonGroup();
        bg.add(window);
        bg.add(document);
        toolBar.add(window);
        toolBar.add(document);
        toolBar.setFloatable(false);
        window.setSelected(true);
        window.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
		viewport.setScrollDirector(windowSD);
		viewport.fireStateChanged();
		scrollPane.revalidate();
            }
        });
        document.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
		viewport.setScrollDirector(documentSD);
		viewport.fireStateChanged();
		scrollPane.revalidate();
            }
        });
        getContentPane().add(toolBar,BorderLayout.NORTH);
    }

    /**
     * A modified scroll director that performs document based scroling
     * rather than window based scrolling (ie. the scrollbars act in
     * the inverse direction as normal)
     */
    public class DocumentScrollDirector extends ZDefaultScrollDirector {

	/**
	 * Get the View position given the specified camera bounds - modified
	 * such that:
	 *
	 * Rather than finding the distance from the upper left corner
	 * of the window to the upper left corner of the document -
	 * we instead find the distance from the lower right corner
	 * of the window to the upper left corner of the document
	 * THEN we subtract that value from total document width so
	 * that the position is inverted
	 *
	 * @param viewBounds The bounds for which the view position will be computed
	 * @return The view position
	 */
	public Point getViewPosition(Rectangle2D viewBounds) {
	    Point pos = new Point();
	    if (camera != null) {
		// First we compute the union of all the layers
		ZBounds layerBounds = new ZBounds();
		ZLayerGroup[] layers = camera.getLayersReference();
		for(int i=0; i<camera.getNumLayers(); i++) {
		    layerBounds.add(layers[i].getBoundsReference());	    
		}

		// Then we put the bounds into camera coordinates and
		// union the camera bounds
		camera.localToCamera(layerBounds,null);
		layerBounds.add(viewBounds);

		// Rather than finding the distance from the upper left corner
		// of the window to the upper left corner of the document -
		// we instead find the distance from the lower right corner
		// of the window to the upper left corner of the document
		// THEN we measure the offset from the lower right corner
		// of the document
		pos.setLocation((int)(layerBounds.getWidth()-(viewBounds.getX()+viewBounds.getWidth()-layerBounds.getX())+0.5),
				(int)(layerBounds.getHeight()-(viewBounds.getY()+viewBounds.getHeight()-layerBounds.getY())+0.5));
	    }

	    return pos;
	
}

	/**
	 * We do the same thing we did in getViewPosition above to flip the
	 * document-window position relationship
	 *
	 * @param x The new x position
	 * @param y The new y position
	 */
	public void setViewPosition(double x, double y) {
	    if (camera != null) {
		// If a scroll is in progress - we ignore new scrolls -
		// if we didn't, since the scrollbars depend on the camera location
		// we can end up with an infinite loop
		if (!scrollInProgress) {
		    scrollInProgress = true;

		    // Get the union of all the layers' bounds
		    ZBounds layerBounds = new ZBounds();
		    ZLayerGroup[] layers = camera.getLayersReference();
		    for(int i=0; i<camera.getNumLayers(); i++) {
			layerBounds.add(layers[i].getBoundsReference());	    
		    }

		    AffineTransform at = camera.getViewTransform();
		    ZTransformGroup.transform(layerBounds,at);

		    // Union the camera view bounds
		    ZBounds viewBounds = camera.getBounds();
		    layerBounds.add(viewBounds);

		    // Now find the new view position in view coordinates -
		    // This is basically the distance from the lower right
		    // corner of the window to the upper left corner of the
		    // document 
		    // We then measure the offset from the lower right corner
		    // of the document
		    Point2D newPoint = new Point2D.Double(layerBounds.getX()+layerBounds.getWidth()-(x+viewBounds.getWidth()),
							  layerBounds.getY()+layerBounds.getHeight()-(y+viewBounds.getHeight()));

		    // Now transform the new view position into global coords
		    camera.cameraToLocal(newPoint,null);

		    // Compute the new matrix values to put the camera at the
		    // correct location
		    double newX = -(at.getScaleX()*newPoint.getX()+at.getShearX()*newPoint.getY());
		    double newY = -(at.getShearY()*newPoint.getX()+at.getScaleY()*newPoint.getY());

		    at.setTransform(at.getScaleX(),
				    at.getShearY(),
				    at.getShearX(),
				    at.getScaleY(),
				    newX,
				    newY);

		    // Now actually set the camera's transform
		    camera.setViewTransform(at);
		    scrollInProgress = false;
		}
	    }
	}
	
    }
}
