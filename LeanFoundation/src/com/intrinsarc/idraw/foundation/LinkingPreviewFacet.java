package com.intrinsarc.idraw.foundation;

import com.intrinsarc.gem.*;

public interface LinkingPreviewFacet extends Facet
{
  public void anchorPreviewHasChanged(AnchorPreviewFacet anchor);

  public PreviewFacet getPreviewFacet();
}