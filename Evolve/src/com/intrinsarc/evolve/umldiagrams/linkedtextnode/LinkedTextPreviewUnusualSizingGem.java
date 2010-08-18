package com.intrinsarc.evolve.umldiagrams.linkedtextnode;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.nodefacilities.previewsupport.*;

/**
 * @author andrew
 */
public class LinkedTextPreviewUnusualSizingGem
{
  private PreviewFacet previewFacet;
  private BasicNodePreviewUnusualSizingFacet unusualSizingFacet =
    new BasicNodePreviewUnusualSizingFacetImpl();
  
  public LinkedTextPreviewUnusualSizingGem(PreviewFacet previewFacet)
  {
    this.previewFacet = previewFacet;
  }
  
  public BasicNodePreviewUnusualSizingFacet getBasicNodePreviewUnusualSizingFacet()
  {
    return unusualSizingFacet;
  }
  
  private class BasicNodePreviewUnusualSizingFacetImpl implements BasicNodePreviewUnusualSizingFacet
  {
    /**
     * only return the full bounds if the linked text is showing
     */
    public UBounds getFullBoundsForContainment()
    {
      if (!previewFacet.isPreviewFor().isShowing())
        return null;
      return previewFacet.getFullBounds();
    }
    
  }
}
