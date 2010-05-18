package com.hopstepjump.jumble.expander;

import java.util.*;

import javax.swing.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.creation.*;
import com.hopstepjump.idraw.arcfacilities.creationbase.*;
import com.hopstepjump.idraw.arcfacilities.previewsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creation.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.swing.*;

public class Expander
{
  public static final ImageIcon EXPAND_ICON = IconLoader.loadIcon("expand.png");
  private ToolCoordinatorFacet coordinator;
  private FigureFacet from;
  private List<Element> relations;
  private ITargetResolver resolver;
  private ArcCreateFacet arcCreator;
  private int index;

	public Expander(ToolCoordinatorFacet coordinator, FigureFacet from, List<Element> relations, ITargetResolver resolver, ArcCreateFacet arcCreator)
	{
		this.coordinator = coordinator;
		this.from = from;
		this.relations = relations;
		this.resolver = resolver;
		this.arcCreator = arcCreator;
	}
	
	public void expand()
	{
		String name = from.getFigureName();
		coordinator.startTransaction("expanded from " + name, "unexpanded from " + name);
		for (Element rel : relations)
		{
			if (!rel.isThisDeleted())
				expand(rel);
		}
		coordinator.commitTransaction();
	}

	private void expand(Element rel)
	{
		// don't expand if there is already a figure satisfying the relationship
		DiagramFacet diagram = from.getDiagram();
		for (FigureFacet link : findFiguresWithSubject(diagram, rel))
		{
			LinkingFacet l = link.getLinkingFacet();
			if (l != null && (l.getAnchor1() == from.getAnchorFacet() || l.getAnchor2() == from.getAnchorFacet()))
				return;
		}
		
		// handle any targets		
		Element target = resolver.resolveTarget(rel);
		if (target == null || target.isThisDeleted())
			return;
		
		// find any figures on the diagram that correspond to "t"
		List<FigureFacet> figures = findFiguresWithSubject(diagram, target);
		// pick the one closest to the source
		FigureFacet closest = chooseClosest(from, figures);
		// create the arc between figure and f
		if (closest != null)
			createArc(from, rel, closest, arcCreator, coordinator);
		else
		{
			// we cannot find, so the node figure must be created
			NodeCreateFacet creator = resolver.getNodeCreator(target);
		
			if (creator != null)
			{
				FigureFacet node = createNode(creator, target);
				createArc(from, rel, node, arcCreator, coordinator);
			}
		}
	}

	private FigureFacet createNode(NodeCreateFacet factory, Element target)
	{
    // make the figure
		DiagramFacet diagram = from.getDiagram();
    FigureReference reference = diagram.makeNewFigureReference();

    PersistentProperties properties = new PersistentProperties();
    factory.initialiseExtraProperties(properties);
    
    // ask for a point
    UPoint location = resolver.determineTargetLocation(target, index++);        
  	return NodeCreateFigureTransaction.create(diagram, target, reference, null, factory, location, properties, null);
	}

	private FigureFacet chooseClosest(FigureFacet figure, List<FigureFacet> figures)
	{
		double dist = 1e99;
		FigureFacet closest = null;
		UPoint mid = figure.getFullBounds().getMiddlePoint();
		for (FigureFacet f : figures)
		{
			double now = f.getFullBounds().getMiddlePoint().distance(mid);
			if (now < dist)
			{
				dist = now;
				closest = f;
			}
		}
		return closest;
	}

	private void createArc(FigureFacet from, Element rel, FigureFacet to, ArcCreateFacet creator, ToolCoordinatorFacet coordinator)
	{
		DiagramFacet diagram = from.getDiagram();
		AnchorFacet arcable = from.getAnchorFacet();
		AnchorFacet toArcable = to.getAnchorFacet();
    ActualArcPoints newActuals = new ActualArcPoints(diagram, arcable, toArcable, from.getFullBounds().getMiddlePoint());
    newActuals.setNode1Preview(arcable.getFigureFacet().getSinglePreview(diagram).getAnchorPreviewFacet());
    newActuals.setNode2Preview(toArcable.getFigureFacet().getSinglePreview(diagram).getAnchorPreviewFacet());
    
    // get the created figure via its reference
    final FigureReference reference = diagram.makeNewFigureReference();

		// use a rectilinear preview to set up the arc
		BasicArcPreviewGem gem = new BasicArcPreviewGem(diagram, null, newActuals, new UPoint(0, 0), false, false);
    BasicArcPreviewFacet previewFigure = gem.getBasicArcPreviewFacet();

    // make the arc
    PersistentProperties properties = new PersistentProperties();
    creator.initialiseExtraProperties(properties);
    creator.aboutToMakeTransaction(coordinator);
    ArcCreateFigureTransaction.create(
    		diagram,
      	rel,
        reference,
        creator,
        previewFigure.getReferenceCalculatedPoints(diagram),
        properties);
	}

	private List<FigureFacet> findFiguresWithSubject(DiagramFacet diagram, Element t)
	{
		List<FigureFacet> all = new ArrayList<FigureFacet>();
		for (FigureFacet f : diagram.getFigures())
			if (f.getSubject() == t)
				all.add(f);
		return all;
	}
}
