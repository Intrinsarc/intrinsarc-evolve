package com.hopstepjump.idraw.nodefacilities.nodesupport;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

/**
 *
 * (c) Andrew McVeigh 28-Jul-02
 *
 */
public final class BasicNodeContainedFacetImpl implements ContainedFacet
{
	private BasicNodeState state;
	private ContainerFacet container = null;
	private String containedName;

	public BasicNodeContainedFacetImpl(BasicNodeState state, String containedName)
	{
		this.state = state;
		this.containedName = containedName;
	}
		
	public BasicNodeContainedFacetImpl(PersistentProperties properties, BasicNodeState state)
	{
		this.state = state;
		this.containedName = properties.retrieve("contName", (String) null).asString();
	}
		
	public boolean acceptsContainer(ContainerFacet container)
	{
		return state.appearanceFacet.acceptsContainer(container);
	}

	public void persistence_setContainer(ContainerFacet container)
	{
		this.container = container;
	}
	
	public void setContainer(ContainerFacet container)
	{
		state.figureFacet.aboutToAdjust();
		this.container = container;
	}
	
	public ContainerFacet getContainer()
	{
		return container;
	}

	public FigureFacet getFigureFacet()
	{
		return state.figureFacet;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ContainedFacet))
			return false;
			
		ContainedFacet other = (ContainedFacet) obj;
		return other.getFigureFacet().getId().equals(state.id);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		if (state == null || state.id == null)
			return super.hashCode();
		return state.id.hashCode();
	}
	
	/**
	 * @see com.hopstepjump.idraw.foundation.ContainedFacet#persistence_getContainedName()
	 */
	public String persistence_getContainedName()
	{
		return containedName;
	}

  public void performPostContainerDropTransaction()
  {
    state.appearanceFacet.performPostContainerDropTransaction();
  }

	public boolean canMoveContainers()
	{
    return state.appearanceFacet.canMoveContainers();
	}
}


