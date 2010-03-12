package com.hopstepjump.jumble.umldiagrams.constituenthelpers;

import java.util.*;

import org.eclipse.uml2.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.creation.*;
import com.hopstepjump.idraw.arcfacilities.creationbase.*;
import com.hopstepjump.idraw.arcfacilities.previewsupport.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.connectorarc.*;

public class ClassConnectorHelper extends ClassifierConstituentHelper
{

  public ClassConnectorHelper(BasicNodeFigureFacet classifierFigure, FigureFacet portsContainer, FigureFacet partsContainer, boolean portLinks, SimpleDeletedUuidsFacet deleted, boolean top)
  {
    super(
        classifierFigure,
        null,
        partsContainer.isShowing(),
        findConnectors(portsContainer, partsContainer, portLinks).iterator(),
        portLinks ? ConstituentTypeEnum.DELTA_PORT_LINK : ConstituentTypeEnum.DELTA_CONNECTOR,
        deleted,
        top);
  }

  @Override
  public Command makeAddCommand(
      DEStratum perspective,
      Set<FigureFacet> currentInContainerIgnoringDeletes,
      final BasicNodeFigureFacet classifierFigure,
      FigureFacet container,
      DeltaPair addOrReplace,
      boolean top)
  {
    // get the current sizes
    FigureFacet existing = null;

    // look to see if there was something there with that id, first
    for (FigureFacet f : currentInContainerIgnoringDeletes)
    {
      // don't delete if this is deleted -- this is covered elsewhere
      Element originalSubject = getOriginalSubject(f.getSubject());
      
      if (addOrReplace.getUuid().equals(originalSubject.getUuid()))
      {
        existing = f;
        break;
      }
    }
    
    // find the location, relative to the parent classifier by looking in this diagram,
    // and in the home package diagram
    if (existing == null)
      existing = findExisting(classifierFigure.getDiagram(), addOrReplace.getUuid());
    if (existing == null)
    {
      // look in the home diagram
      Classifier cls = (Classifier) (getPossibleDeltaSubject(addOrReplace.getConstituent().getRepositoryObject())).getOwner();
      
      DiagramFacet homeDiagram =
        GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(new DiagramReference(cls.getOwner().getUuid()));
      if (homeDiagram != null)
        existing = findExisting(homeDiagram, addOrReplace.getUuid());
    }
    
    // create the connector arc, using the existing subject
    
    // find the 2 ports inside this classifier
    DEComponent component = GlobalDeltaEngine.engine.locateObject(classifierFigure.getSubject()).asComponent();
    DEConnector connector = addOrReplace.getConstituent().asConnector(); 
    DEPort port1 = connector.getPort(perspective, component, 0);
    DEPart part1 = connector.getPart(perspective, component, 0);
    DEPort port2 = connector.getPort(perspective, component, 1);
    DEPart part2 = connector.getPart(perspective, component, 1);
    
    // if we can't find one of the ports, then it hasn't been remapped correctly
    if (port1 == null || port2 == null)
      return null;
    
    Element subject = (Element) classifierFigure.getSubject();
    FigureFacet anchor1 = findPortFigure(classifierFigure, subject, port1, part1);
    FigureFacet anchor2 = findPortFigure(classifierFigure, subject, port2, part2);
    
    // if we have no anchors, we can't display
    if (anchor1 == null || anchor2 == null)
      return null;

    ArcCreateFacet factory = new ConnectorCreatorGem().getArcCreateFacet();
    DiagramFacet diagram = classifierFigure.getDiagram();
    FigureReference reference = diagram.makeNewFigureReference();    
    UPoint start = anchor1.getFullBounds().getMiddlePoint();
    
    // work out the actual arc points, by possibly using the existing figure
    UDimension offset = new UDimension(0, 0);
    ActualArcPoints newActuals = new ActualArcPoints(diagram, anchor1.getAnchorFacet(), anchor2.getAnchorFacet(), start);
    if (existing != null)
    {
      CalculatedArcPoints calculated = new CalculatedArcPoints(
          existing.getLinkingFacet().getCalculated(),
          anchor1.getAnchorFacet(),
          anchor2.getAnchorFacet());
      newActuals = new ActualArcPoints(diagram, calculated);
      
      // adjust the internal points by an offset
      UPoint oldPt = extractVisualClassifierFigureFromConnector(existing).getFullBounds().getPoint();
      UPoint newPt = classifierFigure.getFullBounds().getPoint();
      offset = oldPt.subtract(newPt);
      List<UPoint> points = newActuals.getInternalPoints();
      int size = points.size();
      for (int lp = 0; lp < size; lp++)
        newActuals.replaceInternalPoint(lp, points.get(lp).subtract(offset));
    }
    
    newActuals.setNode1Preview(anchor1.getSinglePreview(diagram).getAnchorPreviewFacet());
    newActuals.setNode2Preview(anchor2.getSinglePreview(diagram).getAnchorPreviewFacet());

    // use a rectilinear preview to set up the arc
    BasicArcPreviewGem gem = new BasicArcPreviewGem(diagram, null, newActuals, new UPoint(0, 0), false, false);
    BasicArcPreviewFacet preview = gem.getBasicArcPreviewFacet();
    preview.formBetterVirtualPoint(preview.getActualPoints().getVirtualPoint().subtract(offset));
    ReferenceCalculatedArcPoints calculated = gem.getBasicArcPreviewFacet().getCalculatedPoints().getReferenceCalculatedArcPoints(diagram);

    ArcCreateFigureTransaction.create(
    		diagram,
        addOrReplace.getConstituent().getRepositoryObject(),
        reference,
        factory,
        calculated,
        new PersistentProperties());
    return null;
  }

  private FigureFacet findPortFigure(FigureFacet container, Object clsRepositoryObject, DEPort port, DEPart part)
  {
    // look down into the contained elements recursively until we find the port
    Object containingSubject =
      part == null ?
          clsRepositoryObject : part.getRepositoryObject();

    return ClassifierConstituentHelper.lookForFigure(container, containingSubject, port, false);
  }
  
  private FigureFacet findExisting(DiagramFacet diagram, String uuid)
  {
    // look through all the diagram figures for the port in its home classifier
    for (FigureFacet figure : diagram.getFigures())
    {
      Object subject = figure.getSubject();
      if (subject instanceof Element)
      {
        Element element = (Element) subject;
        if (uuid.equals(element.getUuid()))
        {
          // is this in its home classifier
          if (getPossibleDeltaSubject(element).getOwner() == extractVisualClassifierFromConnector(figure))
            return figure;
        }
      }
    }
    return null;
  }

  public static void collectAtDepth(Set<FigureFacet> figures, FigureFacet figure, int depth)
  {
    // recurse down to the desired depth
    if (depth == 0)
    {
      figures.add(figure);
      return;
    }
    
    // otherwise, travel down into contained figures
    if (figure.getContainerFacet() != null)
      for (Iterator<FigureFacet> iter = figure.getContainerFacet().getContents(); iter.hasNext();)
        collectAtDepth(figures, iter.next(), depth - 1);
  }

  private static void addConnectors(Set<FigureFacet> links, FigureFacet container, int depth, boolean portLinks)
  {
    // collect the figures at the appropriate depth
    Set<FigureFacet> figures = new HashSet<FigureFacet>();
    ClassConnectorHelper.collectAtDepth(figures, container, depth);      
    
    for (FigureFacet figure : figures)
    {
      if (figure.getAnchorFacet() != null)
        for (Iterator<LinkingFacet> link = figure.getAnchorFacet().getLinks(); link.hasNext();)
        {
        	FigureFacet l = link.next().getFigureFacet();
        	if (l.getSubject() instanceof Connector)
        	{
        		Connector c = (Connector) l.getSubject();
        		ConnectorKind kind = c.getKind();
        		if (portLinks && kind.equals(ConnectorKind.PORT_LINK_LITERAL) ||
        				!portLinks && !kind.equals(ConnectorKind.PORT_LINK_LITERAL))
        			links.add(l);
        	}
        }
    }
  }

  public static Set<FigureFacet> findConnectors(FigureFacet ports, FigureFacet parts, boolean portLinks)
  {
    // look 1 deep off ports
    Set<FigureFacet> links = new HashSet<FigureFacet>();
    for (Iterator<FigureFacet> iter = ports.getContainerFacet().getContents(); iter.hasNext();)
      addConnectors(links, iter.next(), 0, portLinks);
  
    // look 2 deep off parts
    for (Iterator<FigureFacet> iter = parts.getContainerFacet().getContents(); iter.hasNext();)
      addConnectors(links, iter.next(), 2, portLinks);
  
    return links;
  }
   
  @Override
  protected boolean hasVisualProblem(BasicNodeFigureFacet container, FigureFacet sub, DEConstituent constituent)
	{
  	// if the connector points to the wrong port, then remove it and recreate
    DEComponent component = GlobalDeltaEngine.engine.locateObject(container.getSubject()).asComponent();
    DEConnector connector = constituent.asConnector();
    DEStratum perspective = getPerspective();
    DEPort port1 = connector.getPort(perspective, component, 0);
    DEPart part1 = connector.getPart(perspective, component, 0);
    DEPort port2 = connector.getPort(perspective, component, 1);
    DEPart part2 = connector.getPart(perspective, component, 1);

    Element subject = (Element) container.getSubject();
    FigureFacet anchor1 = findPortFigure(container, subject, port1, part1);
    FigureFacet anchor2 = findPortFigure(container, subject, port2, part2);
    
    FigureFacet screen1 = sub.getLinkingFacet().getAnchor1().getFigureFacet();
    FigureFacet screen2 = sub.getLinkingFacet().getAnchor2().getFigureFacet();
    
    if (anchor1 != screen1)
    {
    	FigureFacet temp = screen1;
    	screen1 = screen2;
    	screen2 = temp;
    }    
    
    return anchor1 != screen1 || anchor2 != screen2;
	}

	private String name(DEObject obj)
	{
		return obj == null ? "null" : obj.getName();
	}
}
