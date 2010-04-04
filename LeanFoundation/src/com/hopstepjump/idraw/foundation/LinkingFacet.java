package com.hopstepjump.idraw.foundation;

import java.util.*;

import com.hopstepjump.geometry.*;

public interface LinkingFacet extends ArcAcceptanceFacet
{
	/** persistence */
	public void persistence_setAnchors(AnchorFacet anchor1, AnchorFacet anchor2);
	
	/** used to write out the anchors to a persistent store */
	public AnchorFacet getAnchor1();
	public AnchorFacet getAnchor2();
	
	public Object adjust(CalculatedArcPoints calculatedPoints);  
  public Object move(CalculatedArcPoints calculatedPoints);
  
  public CalculatedArcPoints getCalculated();
	
  public boolean hasOutgoingsToPeripheral(PreviewCacheFacet previewFigures);
  public void detachFromAnchors();
  public FigureFacet getFigureFacet();
  
  public Set<String> getPossibleDisplayStyles(AnchorFacet anchor);
  public UPoint getMajorPoint(int majorPoint);
  
  public void makeReanchorAction(AnchorFacet start, AnchorFacet end);
}