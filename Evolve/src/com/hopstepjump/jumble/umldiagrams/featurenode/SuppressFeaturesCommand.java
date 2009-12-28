package com.hopstepjump.jumble.umldiagrams.featurenode;

import com.hopstepjump.idraw.foundation.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class SuppressFeaturesCommand extends AbstractCommand
{
  private FigureReference suppressable;
  private boolean suppressed;
  private Object memento;
  private int featureType;

  public SuppressFeaturesCommand(FigureReference suppressable, int featureType, boolean suppressed, String executeDescription, String unExecuteDescription)
  {
		super(executeDescription, unExecuteDescription);
    this.suppressable = suppressable;
    this.suppressed = suppressed;
    this.featureType = featureType;
  }

  public void execute(boolean isTop)
  {
    memento = getSuppressable().suppressFeatures(featureType, suppressed);
  }

  public void unExecute()
  {
    getSuppressable().unSuppressFeatures(memento);
  }

  private SuppressFeaturesFacet getSuppressable()
  {
    return (SuppressFeaturesFacet) GlobalDiagramRegistry.registry.retrieveFigure(suppressable).getDynamicFacet(SuppressFeaturesFacet.class);
  }
}