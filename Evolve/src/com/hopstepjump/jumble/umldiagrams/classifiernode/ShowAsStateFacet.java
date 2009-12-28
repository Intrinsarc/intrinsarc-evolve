package com.hopstepjump.jumble.umldiagrams.classifiernode;

import com.hopstepjump.gem.*;

public interface ShowAsStateFacet extends Facet
{
  public Object showAsState(boolean showAsState);
  public void unShowAsState(Object memento);}
