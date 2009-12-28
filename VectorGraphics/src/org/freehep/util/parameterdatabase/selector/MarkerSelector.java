package org.freehep.util.parameterdatabase.selector;

import java.util.*;

import org.freehep.graphics2d.*;

public class MarkerSelector extends Selector implements VectorGraphicsConstants
{

  private static int numMarkers = NUMBER_OF_SYMBOLS + 1;

  private static Vector selectors = new Vector(numMarkers);

  // These selectors describe the various markers.
  public static MarkerSelector NONE = new MarkerSelector("None", new Integer(Integer.MIN_VALUE));
  public static MarkerSelector VLINE = new MarkerSelector("Vert. Line", new Integer(SYMBOL_VLINE));
  public static MarkerSelector HLINE = new MarkerSelector("Horiz. Line", new Integer(SYMBOL_HLINE));
  public static MarkerSelector PLUS = new MarkerSelector("Plus", new Integer(SYMBOL_PLUS));
  public static MarkerSelector CROSS = new MarkerSelector("Cross", new Integer(SYMBOL_CROSS));
  public static MarkerSelector STAR = new MarkerSelector("Star", new Integer(SYMBOL_STAR));
  public static MarkerSelector CIRCLE = new MarkerSelector("Circle", new Integer(SYMBOL_CIRCLE));
  public static MarkerSelector BOX = new MarkerSelector("Box", new Integer(SYMBOL_BOX));
  public static MarkerSelector UP_TRIANGLE = new MarkerSelector("Up. Triangle", new Integer(SYMBOL_UP_TRIANGLE));
  public static MarkerSelector DN_TRIANGLE = new MarkerSelector("Dn. Triangle", new Integer(SYMBOL_DN_TRIANGLE));
  public static MarkerSelector DIAMOND = new MarkerSelector("Diamond", new Integer(SYMBOL_DIAMOND));

  // Statically initialize these vectors. This must be done before anything
  // else because the methods which access these data are called from the
  // public constructors.
  static
  {
    selectors.add(NONE);
    selectors.add(VLINE);
    selectors.add(HLINE);
    selectors.add(PLUS);
    selectors.add(CROSS);
    selectors.add(STAR);
    selectors.add(CIRCLE);
    selectors.add(BOX);
    selectors.add(UP_TRIANGLE);
    selectors.add(DN_TRIANGLE);
    selectors.add(DIAMOND);
  }

  protected MarkerSelector(String tag, Object value)
  {
    super(tag, value);
  }

  public MarkerSelector(Object value)
  {
    super(value);
  }

  public MarkerSelector(String tag)
  {
    super(tag);
  }

  public Iterator iterator()
  {
    return selectors.iterator();
  }

}
