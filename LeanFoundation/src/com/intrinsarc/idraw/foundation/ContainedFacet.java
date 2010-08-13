package com.intrinsarc.idraw.foundation;

import com.intrinsarc.gem.*;




public interface ContainedFacet extends Facet
{
  /**
   * persistence methods:
   * 	 the contained name is important to some containers, as they differentiate between their contained elements 
   */
  public String persistence_getContainedName();
	public void persistence_setContainer(ContainerFacet container);

  boolean acceptsContainer(ContainerFacet container);
  public void setContainer(ContainerFacet container);
  public ContainerFacet getContainer();
  public boolean canMoveContainers();
  
  public FigureFacet getFigureFacet();
  public void performPostContainerDropTransaction();
}