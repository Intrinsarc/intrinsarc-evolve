package com.intrinsarc.idraw.nodefacilities.nodesupport;

import java.io.*;
import java.util.*;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 28-Jul-02
 *
 */
public final class BasicNodeState implements Serializable
{
  public String id;
  /** each figure lives on one diagram only */
  public DiagramFacet diagram;
  public DiagramReference diagramReference;
  public UPoint pt;
  public UDimension resizedExtent;
  public Set<LinkingFacet> linked = new HashSet<LinkingFacet>();
  public boolean showing = true;
  
  /** for persistence */
  public String recreatorName;
  
  public ContainedFacet containedFacet;
  public BasicNodeFigureFacet figureFacet;
  public BasicNodeAppearanceFacet appearanceFacet;
  public BasicNodeAutoSizedFacet autoSizedFacet;

	/** anchor and container facets are optional */
  public AnchorFacet anchorFacet;
  public BasicNodeContainerFacet containerFacet;
  public ClipboardFacet clipboardFacet;
  public ClipboardActionsFacet clipboardCommandsFacet;
}
