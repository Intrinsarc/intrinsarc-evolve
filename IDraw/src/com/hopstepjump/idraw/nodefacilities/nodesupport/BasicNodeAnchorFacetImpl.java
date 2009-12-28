package com.hopstepjump.idraw.nodefacilities.nodesupport;

import java.util.*;

import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 28-Jul-02
 *
 */
public final class BasicNodeAnchorFacetImpl implements AnchorFacet
{
	BasicNodeState state;
	
	public BasicNodeAnchorFacetImpl(BasicNodeState state)
	{
		this.state = state;
	}
	
  public void addLinked(LinkingFacet addLinked)
  {
    state.linked.add(addLinked);
    adjustLinksAndFigure();
  }

	public void persistence_addLinked(LinkingFacet addLinked)
	{
		state.linked.add(addLinked);
    //adjustLinksAndFigure();
	}

  /**
   * 
   */
  private void adjustLinksAndFigure()
  {
    state.figureFacet.adjusted();
    for (Iterator<LinkingFacet> links = getLinks(); links.hasNext();)
    {
      LinkingFacet link = links.next();
      link.getFigureFacet().adjusted();
    }
  }

  public void removeLinked(LinkingFacet removeLinked)
  {
  	state.linked.remove(removeLinked);
    adjustLinksAndFigure();
  }

  public boolean isInvolvedInMoving(PreviewCacheFacet previewFigures)
  {
    return previewFigures.nodeIsInFocusOfPreview(getFigureFacet());
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
	
	public Set<String> getDisplayStyles(boolean anchorIsTarget)
	{
		return state.appearanceFacet.getDisplayStyles(anchorIsTarget);
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
		links.addAll(state.linked);
		return links.iterator();
	}
}

