package com.hopstepjump.idraw.diagramsupport.moveandresize;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 28-Jul-02
 *
 */
public interface ResizingFiguresFacet extends PreviewCacheFacet
{
  public void markForResizing(FigureFacet previewable);
  public void markForResizingWithoutContainer(FigureFacet previewable);
  public void forceDisplay();
  public PreviewFacet getFocusPreview();
  public void setFocusBounds(UBounds bounds);
  
	public void recalculateSizeForContainables();
  public void resizeToAddContainables(ContainedPreviewFacet[] adds, UPoint movePoint);
  public void resizeToRemoveContainables(ContainedPreviewFacet[] takes, UPoint movePoint);
  public void adjustForExistingContainables(ContainedPreviewFacet[] exists, UPoint movePoint);
  public void restoreSizeForContainables();
}
