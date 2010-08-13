package com.intrinsarc.idraw.foundation;

import com.intrinsarc.gem.*;

import edu.umd.cs.jazz.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public interface PreviewCacheFacet extends Facet
{
  public ZNode formView();
  public void addPreviewToCache(FigureFacet moveable, PreviewFacet preview);
  public PreviewFacet getCachedPreview(FigureFacet previewable);
  public PreviewFacet getCachedPreviewOrMakeOne(FigureFacet previewable);
  public boolean nodeIsInFocusOfPreview(FigureFacet previewable);
  public void disconnect();
  public void end();
}