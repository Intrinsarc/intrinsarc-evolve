package com.intrinsarc.deltaengine.errorchecking;

import com.intrinsarc.deltaengine.base.*;

public class ElementLocation
{
  private DEStratum perspective;
  private DEObject element;

  public ElementLocation(DEObject first)
  {
    this(null, first);
  }
  
  public ElementLocation(DEStratum perspective, DEObject element)
  {
    this.perspective = perspective;
    this.element = element;
  }

  public DEStratum getPerspective()
  {
    return perspective;
  }

  public DEObject getElement()
  {
    return element;
  }
}
