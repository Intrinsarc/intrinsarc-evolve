package com.hopstepjump.jumble.umldiagrams.basicnamespacenode;

import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 01-Oct-02
 *
 */
public final class SuppressOwningPackageCommand extends AbstractCommand
{
  private FigureReference owned;
  private Object memento;
  private boolean showOwningPackage;

  public SuppressOwningPackageCommand(FigureReference visible, boolean showOwningPackage, String executeDescription, String unExecuteDescription)
  {
		super(executeDescription, unExecuteDescription);
    this.owned = visible;
    this.showOwningPackage = showOwningPackage;
  }

  public void execute(boolean isTop)
  {
    memento = getOwned().setSuppressOwningPackage(showOwningPackage);
  }

  public void unExecute()
  {
    getOwned().unSetSupressOwningPackage(memento);
  }

  private SuppressOwningPackageFacet getOwned()
  {
    return (SuppressOwningPackageFacet) GlobalDiagramRegistry.registry.retrieveFigure(owned).getDynamicFacet(SuppressOwningPackageFacet.class);
  }
}
