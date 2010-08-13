package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.awt.event.*;
import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.impl.*;

import com.intrinsarc.evolve.expander.*;
import com.intrinsarc.evolve.gui.*;
import com.intrinsarc.evolve.umldiagrams.requirementsfeaturenode.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.arcfacilities.creationbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;

public class DependencyExpander implements ActionListener
{
	private FigureFacet figureFacet;
	private ToolCoordinatorFacet coordinator;
	private ArcCreateFacet creator;
	private List<Element> links;

	public DependencyExpander(FigureFacet figureFacet, ToolCoordinatorFacet coordinator, ArcCreateFacet creator, List<Element> links)
	{
		this.figureFacet = figureFacet;
		this.coordinator = coordinator;
		this.creator = creator;
		this.links = links;
	}

	public void actionPerformed(ActionEvent e)
	{
		UBounds bounds = figureFacet.getFullBounds();
		final UPoint loc = new UPoint(bounds.getPoint().getX(), bounds.getBottomRightPoint().getY());
		ITargetResolver resolver = new ITargetResolver()
		{
			public List<Element> resolveTargets(Element relationship)
			{
				Dependency dep = (Dependency) relationship;
				return ((Dependency) relationship).getTargets();
			}
			
			public UPoint determineTargetLocation(Element target, int index)
			{
				return loc.add(new UDimension(-50 + 40 * index, 100 + index * 40));
			}
			
			public NodeCreateFacet getNodeCreator(Element target)
			{
				if (target instanceof RequirementsFeature)
					return new RequirementsFeatureCreatorGem().getNodeCreateFacet();
				if (target instanceof Interface)
					return new InterfaceCreatorGem().getNodeCreateFacet();
				if (target.getClass() == ClassImpl.class)
				{
					ElementProperties props = new ElementProperties(figureFacet, target);
					if (props.isPrimitive())
						return PaletteManagerGem.makePrimitiveCreator(false);
					if (props.isLeaf() || props.isComposite())
						return PaletteManagerGem.makeCompositeShortcutCreator();
					if (props.isFactory())
						return PaletteManagerGem.makeFactoryCreator(true);
					if (props.isPlaceholder())
						return PaletteManagerGem.makePlaceholderCreator(true);
					if (props.isState())
						return PaletteManagerGem.makeStateCreator(false);
					ClassCreatorGem gem = new ClassCreatorGem();
					gem.setDisplayOnlyIcon(false);
					return gem.getNodeCreateFacet();
				}
				return null;
			}
		};
	  
		new Expander(
				coordinator,
				figureFacet,
				links,
				resolver,
				creator).expand();
	}
}
