/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 *
 * @author Ben Bederson
 */
package edu.umd.cs.jazz.util;

import java.awt.*;
import edu.umd.cs.jazz.*;

public interface ZRenderContextFactory {
    public ZRenderContext createRenderContext(ZCamera camera);
    public ZRenderContext createRenderContext(Graphics2D aG2, ZBounds visibleBounds, 
					      ZDrawingSurface aSurface, int qualityRequested);
}
