package com.intrinsarc.idraw.foundation;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;

import edu.umd.cs.jazz.*;

public interface PreviewFacet extends MainFacet
{
	/** this is a preview for the underlying */
  public FigureFacet isPreviewFor();

	/** form a view */
	public ZNode formView();

  /** the full bounds of this shape, for drawing handles around */
  public UBounds getFullBounds();
  /** the full bounds of this shape, taking all children into account */
  public UBounds getFullBoundsForContainment();
  public UPoint getTopLeftPoint();

	/** information used for preview display purposes */
  public boolean canChangeDueToMove();
  public boolean hasOutgoingsToPeripheral();
  public boolean isFullyMoving();            // true if the entire shape is moving, rather than just one part
  public void setOutgoingsToPeripheral(boolean outgoingsToPeripheral);

	/** all previews must be moveable */
  public void move(UPoint current);
  public void setFullBounds(UBounds newBounds, boolean resizedNotMoved);
  public void end();

	/** disconnect, about to discard */  
  public void disconnect();

  /** previews of nodes and containers can in turn be contained */
  public ContainedPreviewFacet getContainedPreviewFacet();
  /** previews of containers alone support containment */
  public ContainerPreviewFacet getContainerPreviewFacet();
  /** previews of arcs support the ability to be the target of a link.  This is optional for containers and nodes */
  public AnchorPreviewFacet getAnchorPreviewFacet();
  /** previews of arcs alone support linking */
  public LinkingPreviewFacet getLinkingPreviewFacet();
}