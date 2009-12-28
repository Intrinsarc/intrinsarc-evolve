/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.jumble.umldiagrams.featurenode;

import org.eclipse.uml2.*;

import com.hopstepjump.idraw.foundation.*;

/**
 * @version 	1.0
 * @author
 */
public final class SetVisibilityCommand extends AbstractCommand
{
  private FigureReference visible;
  private Object memento;
  private VisibilityKind accessType;

  public SetVisibilityCommand(FigureReference visible, VisibilityKind accessType, String executeDescription, String unExecuteDescription)
  {
		super(executeDescription, unExecuteDescription);
    this.visible = visible;
    this.accessType = accessType;
  }

  public void execute(boolean isTop)
  {
    memento = getVisible().setVisibility(accessType);
  }

  public void unExecute()
  {
    getVisible().unSetVisibility(memento);
  }

  private VisibilityFacet getVisible()
  {
    return (VisibilityFacet) GlobalDiagramRegistry.registry.retrieveFigure(visible).getDynamicFacet(VisibilityFacet.class);
  }
}
