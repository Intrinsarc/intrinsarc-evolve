/**
 * Copyright 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;

/**
 * Example shows how to show the handles of a morph.
 * @author: Jesse Grosjean
 */
class HandlesExample extends AbstractExample {
    protected ZCanvas canvas;

    public HandlesExample() {
        super("Handles Example");
    }

    public String getExampleDescription() {
        return "This shows a Jazz handles at work. Any ZSceneGraphObject can create handles for itself by overiding the " +
               "getHandles() method. See the class ZRectangle to see how the handles in this example are created.";
    }

    public void initializeExample() {
        super.initializeExample();

        // Set up basic frame
        setBounds(100, 100, 400, 400);
        setResizable(true);
        setBackground(null);
        setVisible(true);
        canvas = new ZCanvas();
        getContentPane().add(canvas);
        validate();

        ZVisualLeaf aLeaf = new ZVisualLeaf(new ZRectangle(10, 10, 100, 100));
        aLeaf.editor().getHandleGroup().refreshHandles();
        canvas.getLayer().addChild(aLeaf.editor().getTop());
    }
}