package com.intrinsarc.deltaengine.base;

import java.util.*;

public abstract class DEPort extends DEConstituent
{
  public DEPort()
  {
    super();
  }
  
  public abstract Set<? extends DEInterface> getSetProvidedInterfaces();
  public abstract Set<? extends DEInterface> getSetRequiredInterfaces();
  public abstract int getLowerBound();
  public abstract int getUpperBound();
  public boolean isMany()
  {
  	return getUpperBound() > 1 || getUpperBound() < 0;
  }

  @Override
  public DEPort asPort()
  {
    return this;
  }

	public abstract boolean isSuppressGeneration();
	public abstract boolean isForceBeanMain();
	public abstract boolean isForceBeanNoName();
	public abstract PortKindEnum getPortKind();
	public abstract boolean isOrdered();
}
