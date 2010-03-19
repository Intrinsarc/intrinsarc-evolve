package com.hopstepjump.idraw.arcfacilities.arcsupport;

import java.util.*;

import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 28-Jul-02
 *
 */
final class BasicArcAnchorFacetImpl implements AnchorFacet
{
	private BasicArcState state;
	
	public BasicArcAnchorFacetImpl(BasicArcState state)
	{
		this.state = state;
	}
	
	void setState(BasicArcState state)
	{
		this.state = state;
	}
	
  public void addLinked(LinkingFacet addLinked)
  {
    state.linked.add(addLinked);
    state.diagram.adjusted(getFigureFacet());    
  }

	public void persistence_addLinked(LinkingFacet addLinked)
	{
		state.linked.add(addLinked);
	}

  public void removeLinked(LinkingFacet removeLinked)
  {
  	Iterator<LinkingFacet> iter = state.linked.iterator();
  	while (iter.hasNext())
  	{
  		LinkingFacet linkingFacet = iter.next();
  		if (linkingFacet == removeLinked)
  			iter.remove();
  	}
    state.diagram.adjusted(getFigureFacet());
  }

  public boolean isInvolvedInMoving(PreviewCacheFacet previewFigures)
  {
    // ask each node
    AnchorFacet node1 = state.calculatedPoints.getNode1();
    AnchorFacet node2 = state.calculatedPoints.getNode2();
    return node1.isInvolvedInMoving(previewFigures) && node2.isInvolvedInMoving(previewFigures);
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
		if (!(obj instanceof AnchorFacet))
			return false;
			
		AnchorFacet other = (AnchorFacet) obj;
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
	 * @see com.hopstepjump.idraw.foundation.AnchorFacet#isAnAnchorForLinks()
	 */
	public boolean hasLinks()
	{
		return !state.linked.isEmpty();
	}

	/**
	 * @see com.hopstepjump.idraw.foundation.AnchorFacet#getLinks()
	 */
	public Iterator<LinkingFacet> getLinks()
	{
		Collection<LinkingFacet> links = new ArrayList<LinkingFacet>();
		for (LinkingFacet linking : state.linked)
			links.add(linking);
		return links.iterator();
	}

  public Set<String> getDisplayStyles(boolean anchorIsTarget)
  {
    return null;
  }

	public void cleanUp()
	{
		state.linked.clear();
	}

}
	
