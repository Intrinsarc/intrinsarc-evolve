package com.hopstepjump.idraw.arcfacilities.arcsupport;

import java.util.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

/**
 *
 * (c) Andrew McVeigh 28-Jul-02
 *
 */
final class BasicArcLinkingFacetImpl implements LinkingFacet
{
	private BasicArcState state;
	
	public BasicArcLinkingFacetImpl(BasicArcState state)
	{
		this.state = state;
		attachToAnchors();
	}
	
	/** used for persistence */
	public BasicArcLinkingFacetImpl(PersistentProperties properties, BasicArcState state)
	{
		this.state = state;
	}
	
	public CalculatedArcPoints getCalculatedPoints()
	{
		return state.calculatedPoints;
	}

  public Object adjust(CalculatedArcPoints newCalculatedPoints)
  {
    CalculatedArcPoints oldCalcs = new CalculatedArcPoints(state.calculatedPoints);
    detachFromAnchors();
    state.calculatedPoints = newCalculatedPoints;
    attachToAnchors();
    return oldCalcs;
  }

  public void unAdjust(Object memento)
  {
    detachFromAnchors();
    state.calculatedPoints = new CalculatedArcPoints((CalculatedArcPoints) memento);
    attachToAnchors();
  }

  public Object move(CalculatedArcPoints newCalculatedPoints)
  {
    CalculatedArcPoints oldCalcs = new CalculatedArcPoints(state.calculatedPoints);
    detachFromAnchors();
    state.calculatedPoints = newCalculatedPoints;
    attachToAnchors();
    return oldCalcs;
  }

  public void unMove(Object memento)
  {
    detachFromAnchors();
    state.calculatedPoints = new CalculatedArcPoints((CalculatedArcPoints) memento);
    attachToAnchors();
  }

  public boolean hasOutgoingsToPeripheral(PreviewCacheFacet previewFigures)
  {
    // work out node1 & node2
    AnchorFacet node1 = state.calculatedPoints.getNode1();
    AnchorFacet node2 = state.calculatedPoints.getNode2();
    boolean node1Outgoing = false;
    boolean node2Outgoing = false;
    boolean thisLinksToPeripheral = false;
    if (!node1.isInvolvedInMoving(previewFigures))
    {
      node2Outgoing = true;
      thisLinksToPeripheral = true;
    }
    if (!node2.isInvolvedInMoving(previewFigures))
    {
      node1Outgoing = true;
      thisLinksToPeripheral = true;
    }

    // see if this has any outgoings
    Iterator iter = state.linked.iterator();
    while (iter.hasNext())
    {
      LinkingFacet linking = (LinkingFacet) iter.next();
      previewFigures.getCachedPreviewOrMakeOne(linking.getFigureFacet());
      if (linking.hasOutgoingsToPeripheral(previewFigures))
      {
        node1Outgoing = true;
        node2Outgoing = true;
        thisLinksToPeripheral = true;
      }
    }

		// set the outgoings flag for each node
    if (node1Outgoing)
      previewFigures.getCachedPreviewOrMakeOne(node1.getFigureFacet()).setOutgoingsToPeripheral(node1Outgoing);
    if (node2Outgoing)
      previewFigures.getCachedPreviewOrMakeOne(node2.getFigureFacet()).setOutgoingsToPeripheral(node2Outgoing);

    return thisLinksToPeripheral;
  }
  
	public void detachFromAnchors()
  {
    state.calculatedPoints.getNode1().removeLinked(state.linkingFacet);
    state.calculatedPoints.getNode2().removeLinked(state.linkingFacet);
  }

  public void attachToAnchors()
  {
    state.calculatedPoints.getNode1().addLinked(this);
    state.calculatedPoints.getNode2().addLinked(this);
  }

	private void persistence_attachToAnchors()
	{
		state.calculatedPoints.getNode1().persistence_addLinked(this);
		state.calculatedPoints.getNode2().persistence_addLinked(this);    
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
		if (!(obj instanceof LinkingFacet))
			return false;
			
		LinkingFacet other = (LinkingFacet) obj;
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
	 * @see com.hopstepjump.idraw.foundation.LinkingFacet#getAnchor1()
	 */
	public AnchorFacet getAnchor1()
	{
		return state.calculatedPoints.getNode1();
	}

	/**
	 * @see com.hopstepjump.idraw.foundation.LinkingFacet#getAnchor2()
	 */
	public AnchorFacet getAnchor2()
	{
		return state.calculatedPoints.getNode2();
	}
	
	/**
	 * @see com.hopstepjump.idraw.foundation.LinkingFacet#persistence_setAnchor1(LinkingFacet)
	 */
	public void persistence_setAnchors(AnchorFacet anchor1, AnchorFacet anchor2)
	{
		state.calculatedPoints = new CalculatedArcPoints(state.calculatedPoints, anchor1, anchor2);
		persistence_attachToAnchors();
	}

  public Set<String> getPossibleDisplayStyles(AnchorFacet anchor)
  {
    return state.appearanceFacet.getPossibleDisplayStyles(anchor);
  }

  public UPoint getMajorPoint(int majorPoint)
  {
    return state.calculatedPoints.getMajorPoint(majorPoint, state.curved);
  }

	public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
	{
		return state.appearanceFacet.acceptsAnchors(start, end);
	}

	public Command makeReanchorCommand(AnchorFacet start, AnchorFacet end)
	{
		return state.appearanceFacet.makeReanchorCommand(start, end);
	}

  public CalculatedArcPoints getCalculated()
  {
    return new CalculatedArcPoints(state.calculatedPoints);
  }
}