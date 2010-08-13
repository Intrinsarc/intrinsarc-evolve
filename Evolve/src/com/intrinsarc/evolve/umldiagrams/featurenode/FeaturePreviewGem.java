package com.intrinsarc.evolve.umldiagrams.featurenode;

import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.foundation.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class FeaturePreviewGem implements Gem
{
	private FeaturePreviewComparableFacetImpl comparableFacet = new FeaturePreviewComparableFacetImpl();
	private PreviewFacet previewFacet;
	
  public FeaturePreviewGem()
	{
	}
	
	private class FeaturePreviewComparableFacetImpl implements FeaturePreviewComparableFacet
	{
		private double sortPosition;
		private double originalSortPosition;
	
		public void setOriginalSortPosition(double sortPosition)
		{
			this.originalSortPosition = sortPosition;
			this.sortPosition = sortPosition;
		}
		
		public double getSortPosition()
		{
			return sortPosition;
		}
		
		public double getOriginalSortPosition()
		{
			return originalSortPosition;
		}
	
		public void setSortPosition(double sortPosition)
		{
			this.sortPosition = sortPosition;
		}
		
		public int compareTo(FeaturePreviewComparableFacet other)
		{
			return new Double(sortPosition).compareTo(new Double(other.getSortPosition()));
		}
	}
	
	public void connectPreviewFacet(PreviewFacet previewFacet)
	{
		this.previewFacet = previewFacet;
		previewFacet.registerDynamicFacet(comparableFacet, FeaturePreviewComparableFacet.class);
	}
}
