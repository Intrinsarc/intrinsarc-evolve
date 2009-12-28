/**
 * Copyright 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;

/**
 * Basic example of java drag and drop on a single jazz ZCanvas.
 * This program detects drag gestures. If the gesture starts over a
 * node in the scenegraph, it will create a new rectangle where and when
 * the gesture is completed.
 *
 * @author  James Mokwa
 */
public class DragAndDropExample extends AbstractExample {

    protected ZRoot root;
    protected ZLayerGroup layer;
    protected ZCanvas canvas;
    protected ZVisualLeaf leaf;
    protected ZVisualLeaf ddLeaf;

    public DragAndDropExample() {
        super("Drag and Drop example");
    }

    public String getExampleDescription() {
        return "Basic example of java drag and drop on a single jazz ZCanvas. " +
                   "This program detects drag gestures. it will create a new rectangle where and when the gesture is completed.";
    }

    public void initializeExample() {
        super.initializeExample();

        // Set up basic frame
        setBounds(100, 100, 400, 400);
        setVisible(true);
        canvas = new ZCanvas();
        getContentPane().add(canvas);
        validate();

        ZRectangle rect = new ZRectangle(50, 50, 50, 100);
        rect.setFillPaint(Color.blue);
        leaf = new ZVisualLeaf(rect);
        layer = canvas.getLayer();
        layer.addChild(leaf);

        ZVisualGroup topGroup = new ZVisualGroup(null, null);
        canvas.getLayer().addChild(topGroup);

        canvas.setNavEventHandlersActive(false);

        ////////////////////////////////////////////////////////////////////////
        // Inner Classes To Support Drag and Drop
        ////////////////////////////////////////////////////////////////////////

        class ViewTransferable implements Transferable {

        DataFlavor[] flavors = new DataFlavor[]{DataFlavor.stringFlavor};

        public ViewTransferable() {
        }

        public DataFlavor[] getTransferDataFlavors() {
            return flavors;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            if (flavor.equals(flavors[0])) {
                return true;
            }
            return false;
        }

        public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                System.out.println("unsuported flavor");
                return null;
            }
            if (flavor.equals(flavors[0])) {
                return(ddLeaf.toString());
            }
            return null;
        }
    }

    class CanvasDragSource implements DragGestureListener, DragSourceListener {

        CanvasDragSource() {
            DragSource dragSource = DragSource.getDefaultDragSource();
            dragSource.createDefaultDragGestureRecognizer(canvas,
                                                          DnDConstants.ACTION_COPY_OR_MOVE,this);
        }

        public void dragGestureRecognized(DragGestureEvent dge) {
            Point p = dge.getDragOrigin();
            int x = (int)p.getX();
            int y = (int)p.getY();

                                // find any node under the mouse at the start of a drag gesture
            Rectangle2D.Double rect2 = new Rectangle2D.Double(p.getX(), p.getY(), 1, 1);
            ZCamera camera = canvas.getCamera();
            ZSceneGraphPath path = new ZSceneGraphPath();
            if (camera.pick(rect2, path)) {
                ddLeaf = (ZVisualLeaf)path.getNode();
                Transferable transferable = new ViewTransferable();
                dge.startDrag(null,transferable,this);
            }
        }

        public void dropActionChanged(DragSourceDragEvent dsde){}
        public void dragEnter(DragSourceDragEvent dsde){}
        public void dragOver(DragSourceDragEvent dsde){}
        public void dragExit(DragSourceEvent dse){}
        public void dragDropEnd(DragSourceDropEvent dsde){}
    }

        class CanvasDropTarget implements DropTargetListener {
            DropTarget dropTarget;
            boolean acceptableType;

            CanvasDropTarget() {
                dropTarget = new DropTarget(canvas, DnDConstants.ACTION_COPY_OR_MOVE,
                                            this,true,null);
            }

            public void dragOver(DropTargetDragEvent dtde) {
                acceptOrRejectDrag(dtde);
            }

            public void dropActionChanged(DropTargetDragEvent dtde) {
                acceptOrRejectDrag(dtde);
            }

            public void dragExit(DropTargetEvent dte) {
            }

            public void drop(DropTargetDropEvent dtde) {
                if ((dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

                    dtde.acceptDrop(dtde.getDropAction());
                    dtde.getDropTargetContext().dropComplete(true);
                    double x = dtde.getLocation().getX();
                    double y = dtde.getLocation().getY();
                    Point2D.Double p = new Point2D.Double(x, y);

                                    // Translate dragAndDrop global coordinates to coordinates local to the layer.
                    canvas.getCamera().cameraToLocal(p, layer);

                                    // Create a new ZRectangle, and drop it where mouse is.
                    ZRectangle newRect = new ZRectangle(0, 0, 50, 100);
                    newRect.setFillPaint(Color.blue);
                    ZVisualLeaf newLeaf = new ZVisualLeaf(newRect);
                    layer.addChild(newLeaf);
                    ZTransformGroup tg = newLeaf.editor().getTransformGroup();
                    tg.setTranslation(p.getX(), p.getY());
                }
                else {
                    dtde.rejectDrop();
                }
            }

            public void dragEnter(DropTargetDragEvent dtde) {
                checkTransferType(dtde);
                acceptOrRejectDrag(dtde);
            }

            boolean acceptOrRejectDrag(DropTargetDragEvent dtde) {
                int dropAction = dtde.getDropAction();
                int sourceActions = dtde.getSourceActions();
                boolean acceptedDrag = false;

                if (!acceptableType || (sourceActions &
                                        DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
                        dtde.acceptDrag(DnDConstants.ACTION_COPY);
                        acceptedDrag = true;
                }
                else if ((dropAction & DnDConstants.ACTION_COPY_OR_MOVE ) == 0) {
                        dtde.acceptDrag(DnDConstants.ACTION_COPY);
                        acceptedDrag = true;

                }

                return acceptedDrag;
            }

            void checkTransferType(DropTargetDragEvent dtde)  {
                acceptableType = dtde.isDataFlavorSupported(DataFlavor.stringFlavor);
            }
        }

        new CanvasDragSource();
        new CanvasDropTarget();
    }
}