package com.hopstepjump.idraw.foundation;

public interface DiagramListenerFacet
{
  public void haveModifications(DiagramChange[] changes);
  /** used to refresh view attributes, such as a title etc */
  public void refreshViewAttributes();
}