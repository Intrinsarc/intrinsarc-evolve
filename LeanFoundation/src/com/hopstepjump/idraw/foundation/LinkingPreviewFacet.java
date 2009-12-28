package com.hopstepjump.idraw.foundation;

import com.hopstepjump.gem.*;

public interface LinkingPreviewFacet extends Facet
{
  public void anchorPreviewHasChanged(AnchorPreviewFacet anchor);

  public PreviewFacet getPreviewFacet();
}