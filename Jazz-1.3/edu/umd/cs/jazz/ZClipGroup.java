/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import edu.umd.cs.jazz.io.*;

import edu.umd.cs.jazz.util.*;
import edu.umd.cs.jazz.component.*;

/**
 * <b>ZClipGroup</b> applies a clip to the current ZRenderContext before rendering its
 * children. This clip is specified by a ZShape. The clip can optionaly be set to visible. This
 * means that the ZShape used to specify the clip will be drawn before the ZClipGroup's children
 * are drawn. This has the effect of giving the clip a background. The clip can also be optionaly
 * set to pickable.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Jesse Grosjean
 */
public class ZClipGroup extends ZGroup {
    protected ZShape clip = null;
    protected boolean isClipVisible = true;
    protected boolean isClipPickable = true;

    /**
     * ZClipGroup constructor comment.
     */
    public ZClipGroup() {
        super();
    }

    /**
     * ZClipGroup constructor comment.
     * @param child edu.umd.cs.jazz.ZNode
     */
    public ZClipGroup(ZNode child) {
        super(child);
    }

    /**
     * Used to query if the clip is visible or not.
     */
    public boolean isClipVisible() {
        return isClipVisible;
    }

    /**
     * If a the clip is set to visible then then ZShape used to define the clip will
     * be drawn after the clip has been applied to the ZRenderContext, but before the
     * children have been drawn. This has the affect of drawing a background in the clip.
     */
    public void setClipVisible(boolean aBoolean) {
        isClipVisible = aBoolean;
        repaint();
    }

    /**
     * Used to query if the clip is pickable or not.
     */
    public boolean isClipPickable() {
        return isClipPickable;
    }

    /**
     * If the clip is set to pickable, then if we fail to pick any children we next
     * see if we can pick the ZShape that defines the clip. If the ZShape returns true
     * then we set the ZClipGroup as the picked object, and return true.
     */
    public void setClipPickable(boolean aBoolean) {
        isClipPickable = aBoolean;
    }

    protected void computeBounds() {
        if (clip != null) {
            bounds.setFrame(clip.getBounds());
        } else {
            super.computeBounds();
        }
    }

    /**
     * ZClipGroup constructor comment.
     * @param child edu.umd.cs.jazz.ZNode
     */
    public ZShape getClip() {
        return clip;
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZClipGroup newObject = (ZClipGroup)super.duplicateObject();

        if (clip != null) {
            newObject.clip = (ZShape) clip.clone();
        }

        return newObject;
    }

    /**
     * Pick is overiden so that we can take the clip into
     * consideration when picking.
     */
    public boolean pick(Rectangle2D rect, ZSceneGraphPath path) {
        if (clip != null && !clip.getShape().intersects(rect)) {
            return false;
        }

        if (!super.pick(rect, path)) {
            if (isClipPickable && clip != null && clip.pick(rect, path)) {
                path.push(this);
                path.setObject(this);
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * ZClipGroup constructor comment.
     * @param child edu.umd.cs.jazz.ZNode
     */
    public void render(ZRenderContext renderContext) {
        Graphics2D g2 = renderContext.getGraphics2D();

        if (clip != null) {
            Shape currentClip = g2.getClip();
            g2.clip(clip.getShape());

            if (isClipVisible) {
                clip.render(renderContext);
            }

            super.render(renderContext);

            g2.setClip(currentClip);
        } else {
            super.render(renderContext);
        }
    }

    /**
     * Set the current clip to a ZShape.
     */
    public void setClip(ZShape aClip) {
        if (clip != null) {
            clip.removeParent(this);
        }

        clip = aClip;

        if (clip != null) {
            clip.addParent(this);
        }

        reshape();
    }

    /////////////////////////////////////////////////////////////////////////
    //
    // Saving
    //
    /////////////////////////////////////////////////////////////////////////

    /**
     * Set some state of this object as it gets read back in.
     * After the object is created with its default no-arg constructor,
     * this method will be called on the object once for each bit of state
     * that was written out through calls to ZObjectOutputStream.writeState()
     * within the writeObject method.
     * @param fieldType The fully qualified type of the field
     * @param fieldName The name of the field
     * @param fieldValue The value of the field
     */
    public void setState(String fieldType, String fieldName, Object fieldValue) {
        super.setState(fieldType, fieldName, fieldValue);

        if (fieldName.compareTo("clip") == 0) {
            setClip((ZShape)fieldValue);
        }
    }

    /**
     * Write out all of this object's state.
     * @param out The stream that this object writes into
     */
    public void writeObject(ZObjectOutputStream out) throws IOException {
        super.writeObject(out);

        if (clip != null) {
            out.writeState("ZShape", "clip", clip);
        }
    }

    /**
     * Specify which objects this object references in order to write out the scenegraph properly
     * @param out The stream that this object writes into
     */
    public void writeObjectRecurse(ZObjectOutputStream out) throws IOException {
        super.writeObjectRecurse(out);

                                // Add clip
        if (clip != null) {
            out.addObject(clip);
        }
    }
}