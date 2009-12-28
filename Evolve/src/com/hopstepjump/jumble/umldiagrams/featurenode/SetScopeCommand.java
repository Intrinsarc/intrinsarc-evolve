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
public final class SetScopeCommand extends AbstractCommand
{
  private FigureReference scopeable;
  private Object memento;
  private boolean classifierScope;

  public SetScopeCommand(FigureReference scopeable, boolean classifierScope, String executeDescription, String unExecuteDescription)
  {
		super(executeDescription, unExecuteDescription);
    this.scopeable = scopeable;
    this.classifierScope = classifierScope;
  }

  public void execute(boolean isTop)
  {
    memento = getScopeable().setScope(classifierScope);
  }

  public void unExecute()
  {
    getScopeable().unSetScope(memento);
  }

  private ScopeFacet getScopeable()
  {
    // necessary cast
    return (ScopeFacet) GlobalDiagramRegistry.registry.retrieveFigure(scopeable).getDynamicFacet(ScopeFacet.class);
  }
}
