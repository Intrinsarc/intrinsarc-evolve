/**
 * Copyright 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

import java.awt.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.animation.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;

/**
 * Example that applies various animations to four rectangles.
 * @author: Jesse Grosjean
 */
public class AnimationExample extends AbstractExample {

    private ZCanvas canvas;

    public AnimationExample() {
        super("Animation Example");
    }

    public String getExampleDescription() {
        return "This example demonstrates the Jazz animation system. " +
               "Jazz comes with animations that will change a ZNodes transform over time, and change a ZVisualComponents penColor, fillColor, and stroke over time. " +
               "Other custom animations can easily be set up by subclassing the class ZAnimation.";
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

        createSimpleTransformAnimations();
        createCompositeAnimation();

    }

    /**
     * Create three different rectangles that are being animate from one side of the screen
     * to and then back again to where they started. These examples are good to look at if you are
     * trying to figure out how the ZAlpha class works.
     */
    public void createSimpleTransformAnimations() {
        long triggerTime = System.currentTimeMillis();

        ZRectangle rect1 = new ZRectangle(0, 0, 10, 10);
        ZRectangle rect2 = new ZRectangle(0, 0, 10, 10);
        ZRectangle rect3 = new ZRectangle(0, 0, 10, 10);

        ZVisualLeaf leaf1 = new ZVisualLeaf(rect1);
        ZVisualLeaf leaf2 = new ZVisualLeaf(rect2);
        ZVisualLeaf leaf3 = new ZVisualLeaf(rect3);

        canvas.getLayer().addChild(leaf1);
        canvas.getLayer().addChild(leaf2);
        canvas.getLayer().addChild(leaf3);

        ZTransformAnimation transformAnimation1 = new ZTransformAnimation(AffineTransform.getTranslateInstance(0, 0),
                                                                          AffineTransform.getTranslateInstance(300, 0));
        ZAlpha animationAlpha1 = new ZAlpha(-1,             // loop indefinitaly
                                            triggerTime,    // start at shared trigger time, imeditatly
                                            0,              // no delay on start
                                            5000,           // take 5 seconds to go from 0 to 1
                                            0,              // accelerate durring the first 1.5 seconds, decelerate during the last 1.5 seconds.
                                            0);             // dont pause at value 1

        transformAnimation1.setAlpha(animationAlpha1);
        transformAnimation1.setTransformTarget(leaf1.editor().getTransformGroup());
        transformAnimation1.play();

        ZTransformAnimation transformAnimation2 = new ZTransformAnimation(AffineTransform.getTranslateInstance(0, 20),
                                                                          AffineTransform.getTranslateInstance(300, 20));
        ZAlpha animationAlpha2 = new ZAlpha(-1,             // loop indefinitaly
                                            triggerTime,    // start at shared trigger time, imeditatly
                                            0,              // no delay on start
                                            5000,           // take 5 seconds to go from 0 to 1
                                            2000,           // accelerate durring the first 2 seconds, decelerate during the last 2 seconds.
                                            0);             // dont pause at value 1

        transformAnimation2.setAlpha(animationAlpha2);
        transformAnimation2.setTransformTarget(leaf2.editor().getTransformGroup());
        transformAnimation2.play();


        ZTransformAnimation transformAnimation3 = new ZTransformAnimation(AffineTransform.getTranslateInstance(0, 40),
                                                                          AffineTransform.getTranslateInstance(300, 40));

        ZAlpha animationAlpha3 = new ZAlpha(-1,                             // loop indefinitaly
                                            ZAlpha.ALPHA_INCREASING_AND_DECREASING, // values go from 0 to 1 then 1 to 0
                                            triggerTime,                // start at shared trigger time, imeditatly
                                            0,                          // no delay on start
                                            2000,                       // take 2 seconds to go from 0 to 1
                                            1000,                       // accelerate durring the first 1 second, decelerate during the last 1 second of 0 to 1 phase.
                                            1000,                       // pause at value 1 for one second
                                            2000,                       // take 2 seconds to go from 1 to 0
                                            0,                          // go from 1 to 0 at constant velocity
                                            0);                         // dont pause at value 0

        transformAnimation3.setAlpha(animationAlpha3);
        transformAnimation3.setTransformTarget(leaf3.editor().getTransformGroup());
        transformAnimation3.play();

    }

    /**
     * Creates three different animations that all apply to the same object at the same time.
     */
    public void createCompositeAnimation() {
        ZRectangle rect = new ZRectangle(0, 0, 100, 100);
        ZVisualLeaf leaf = new ZVisualLeaf(rect);
        canvas.getLayer().addChild(leaf);

        ZTransformAnimation transformAnimation = new ZTransformAnimation(AffineTransform.getTranslateInstance(10, 100),
                                                                         AffineTransform.getTranslateInstance(250, 100));
        ZStrokeAnimation strokeAnimation = new ZStrokeAnimation(new BasicStroke(1), new BasicStroke(20));
        ZColorAnimation penColorAnimation = new ZColorAnimation(Color.red, Color.yellow);
        ZColorAnimation fillColorAnimation = new ZColorAnimation(Color.green, Color.blue);

        ZAlpha sharedAlpha = new ZAlpha(-1,
                                        ZAlpha.ALPHA_INCREASING_AND_DECREASING,
                                        System.currentTimeMillis(),
                                        0,
                                        10000,
                                        2500,
                                        0,
                                        5000,
                                        2500,
                                        0);

        // Note that many animations can share the same alpha.
        transformAnimation.setAlpha(sharedAlpha);
        strokeAnimation.setAlpha(sharedAlpha);
        penColorAnimation.setAlpha(sharedAlpha);
        fillColorAnimation.setAlpha(sharedAlpha);

        // Note that many animations can be applied to the same object.
        transformAnimation.setTransformTarget(leaf.editor().getTransformGroup());
        strokeAnimation.setStrokeTarget(rect);
        penColorAnimation.setPenPaintTarget(rect);
        fillColorAnimation.setFillPaintTarget(rect);

        transformAnimation.play();
        strokeAnimation.play();
        penColorAnimation.play();
        fillColorAnimation.play();
    }
}