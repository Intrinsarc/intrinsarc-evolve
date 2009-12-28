package com.hopstepjump.jumble.umldiagrams.classifiernode;

import com.hopstepjump.idraw.foundation.*;

public class SwitchSubjectCommand extends AbstractCommand
{
  private FigureReference stateable;
  private Object newSubject;
  private Object memento;

  public SwitchSubjectCommand(FigureReference switchable, Object newSubject, String executeDescription, String unExecuteDescription)
  {
    super(executeDescription, unExecuteDescription);
    this.stateable = switchable;
    this.newSubject = newSubject;
  }

  public void execute(boolean isTop)
  {
    memento = getSwitchable().switchSubject(newSubject);
  }

  public void unExecute()
  {
    getSwitchable().unSwitchSubject(memento);
  }

  private SwitchSubjectFacet getSwitchable()
  {
    return (SwitchSubjectFacet) GlobalDiagramRegistry.registry.retrieveFigure(stateable).getDynamicFacet(SwitchSubjectFacet.class);
  }
}
