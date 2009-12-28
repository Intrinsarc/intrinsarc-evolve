package com.hopstepjump.idraw.figurefacilities.containment;

import com.hopstepjump.idraw.foundation.*;


public final class ContainerRemoveCommand extends AbstractCommand
{
  private FigureReference container;
  private FigureReference[] containables;
  private Object memento;

  public ContainerRemoveCommand(FigureReference container, FigureReference[] containables, String executeDescription, String unExecuteDescription)
  {
  	super(executeDescription, unExecuteDescription);
    this.container = container;
    this.containables = containables;
  }

  public void execute(boolean isTop)
  {
    memento = getContainer().removeContents(getContainables());
  }

  public void unExecute()
  {
    getContainer().unRemoveContents(memento);
  }

  private DiagramFacet getDiagram()
  {
    // get the diagram
    DiagramRegistryFacet registry = GlobalDiagramRegistry.registry;
    return registry.retrieveOrMakeDiagram(container.getDiagramReference());
  }

  private ContainerFacet getContainer()
  {
    // get the container
    DiagramRegistryFacet registry = GlobalDiagramRegistry.registry;
    return registry.retrieveFigure(container).getContainerFacet();
  }

  private ContainedFacet[] getContainables()
  {
    // get the container
    DiagramRegistryFacet registry = GlobalDiagramRegistry.registry;
    // turn the containables into an array of CmdContainables
    ContainedFacet[] containableFigures = new ContainedFacet[containables.length];
    for (int lp = 0; lp < containables.length; lp++)
        containableFigures[lp] = registry.retrieveFigure(containables[lp]).getContainedFacet();
    return containableFigures;
  }
}