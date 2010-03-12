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
import com.hopstepjump.swing.*;

public class Expander
{
  public static final ImageIcon EXPAND_ICON = IconLoader.loadIcon("expand.png");

	public Expander()
	{
	}
	
	public void expand(FigureFacet figure, Element from, EReference reference, ArcCreateFacet creator,  ToolCoordinatorFacet coordinator)
	{
		// get all the potential elements that we need to link to
		if (from == null)
			from = (Element) figure.getSubject();
		if (reference.isMany())
		{
			for (DirectedRelationship rel : (List<DirectedRelationship>) from.eGet(reference))
			{
				if (!rel.isThisDeleted())
					expand(figure, rel, creator, coordinator);
			}
		}
		else
		{
			DirectedRelationship rel = (DirectedRelationship) from.eGet(reference);
			if (rel != null && !rel.isThisDeleted())
				expand(figure, rel, creator, coordinator);
		}
	}

	private void expand(FigureFacet figure, DirectedRelationship rel, ArcCreateFacet creator,  ToolCoordinatorFacet coordinator)
	{
		// don't expand if there is already a figure satisfying the relationship
		DiagramFacet diagram = figure.getDiagram();
		for (FigureFacet link : findFiguresWithSubject(diagram, rel))
		{
			LinkingFacet l = link.getLinkingFacet();
			if (l != null && (l.getAnchor1() == figure.getAnchorFacet() || l.getAnchor2() == figure.getAnchorFacet()))
				return;
		}
		
		// handle any targets
		for (Element e : (List<Element>) rel.getTargets())
		{
			if (!e.isThisDeleted())
			{
				// find any figures on the diagram that correspond to "t"
				List<FigureFacet> figures = findFiguresWithSubject(diagram, e);
				// pick the one closest to the source
				FigureFacet closest = chooseClosest(figure, figures);
				// create the arc between figure and f
				if (closest != null)
					createArc(figure, rel, closest, creator, coordinator);
			}
		}
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

	private void createArc(FigureFacet from, DirectedRelationship rel, FigureFacet to, ArcCreateFacet creator, ToolCoordinatorFacet coordinator)
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
    creator.aboutToMakeCommand(coordinator);
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
