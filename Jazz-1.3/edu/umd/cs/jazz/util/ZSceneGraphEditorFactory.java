/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import edu.umd.cs.jazz.ZNode;
import edu.umd.cs.jazz.util.ZSceneGraphEditor;

public interface ZSceneGraphEditorFactory {
    public ZSceneGraphEditor createEditor(ZNode node);
}
