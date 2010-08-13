package com.intrinsarc.idraw.arcfacilities.arcsupport;

import java.io.*;
import java.util.*;

import com.intrinsarc.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 28-Jul-02
 *
 */
final class BasicArcState implements Serializable
{
	DiagramFacet diagram;
	DiagramReference diagramReference;
  String id;
  boolean showing;
  boolean curved;
 	CalculatedArcPoints calculatedPoints;
 	Set<LinkingFacet> linked;
 	
 	/** for persistence */
 	PersistentFigureRecreatorFacet recreatorFacet;

	LinkingFacet            linkingFacet;
	FigureFacet             figureFacet;
	BasicArcAppearanceFacet appearanceFacet;
  
	/** anchor facet is optional */
  AnchorFacet             anchorFacet;
  /** container facet is optional */
  ContainerFacet          containerFacet;
  
  /** advanced arcs, optional */
  AdvancedArcFacet        advancedFacet;
  ClipboardActionsFacet  clipboardCommandsFacet;
}
