/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import javax.swing.event.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;
import edu.umd.cs.jazz.event.*;

/**
 * An internal class used to implement slow-in, slow-out animation.
 * It provides a lerp time function that starts off slow at the beginning,
 * speeds up in the middle, and slows down at the end.
 */
class ZSISOLerp implements ZLerp, Serializable {
    public double lerpTime(double t) {
        double siso, t1, t2, l;

        t1 = t * t;
        t2 = 1 - (1 - t) * (1 - t);
        l = ZTransformGroup.lerp(t, t1, t2);
        siso = ZTransformGroup.lerp(l, t1, t2);

        return siso;
    }

}