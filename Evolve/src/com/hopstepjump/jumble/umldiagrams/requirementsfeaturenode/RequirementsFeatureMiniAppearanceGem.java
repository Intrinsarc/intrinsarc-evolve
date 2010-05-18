package com.hopstepjump.jumble.umldiagrams.requirementsfeaturenode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public class RequirementsFeatureMiniAppearanceGem implements Gem
{
	public static final ImageIcon EVOLVE_ICON = IconLoader.loadIcon("monkey-icon.png");
	private RequirementsFeatureMiniAppearanceFacet miniAppearanceFacet = new RequirementsFeatureMiniAppearanceFacetImpl();
	private FigureFacet figureFacet;

	public RequirementsFeatureMiniAppearanceGem()
	{
	}

	public void connectFigureFacet(FigureFacet figureFacet)
	{
		this.figureFacet = figureFacet;
	}

	public RequirementsFeatureMiniAppearanceFacet getRequirementsFeatureMiniAppearanceFacet()
	{
		return miniAppearanceFacet;
	}

	private class RequirementsFeatureMiniAppearanceFacetImpl implements RequirementsFeatureMiniAppearanceFacet
	{
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#formView(boolean,
		 *      UBounds)
		 */
		public ZNode formView(boolean displayAsIcon, UBounds bounds)
		{
			// don't bother if the part has no type
			if (figureFacet.getSubject() == null || figureFacet.hasSubjectBeenDeleted() || !displayAsIcon)
				return new ZGroup();

			// set up the sizes
			double width = bounds.getWidth();
			double height = bounds.getHeight();
			double offset = width / 8;

			double left = bounds.getPoint().getX();
			double top = bounds.getPoint().getY();
			UPoint middle = new UPoint(left + width / 2, top + height * 0.3);
			UPoint bottomLeft = new UPoint(left + offset * 2, top + height * 0.7 - offset);
			UPoint bottomMiddle = new UPoint(middle.getX(), top + height * 0.9 - offset);
			UPoint bottomRight = new UPoint(left + width - offset * 2, top + height * 0.7 - offset);
						
			ZGroup group = new ZGroup();
			group.addChild(new ZVisualLeaf(new ZLine(middle, bottomLeft)));
			group.addChild(new ZVisualLeaf(new ZLine(middle, bottomMiddle)));
			group.addChild(new ZVisualLeaf(new ZLine(middle, bottomRight)));
			group.addChild(makeCircle(bottomLeft, offset, false));
			group.addChild(makeCircle(bottomMiddle, offset, true));
			group.addChild(makeCircle(bottomRight, offset, true));
			ZRectangle total = new ZRectangle(bounds);
			total.setFillPaint(ScreenProperties.getTransparentColor());
			total.setPenPaint(null);
			group.addChild(new ZVisualLeaf(total));

			return group;
		}

		private ZNode makeCircle(UPoint pt, double offset, boolean filled)
		{
			ZEllipse ell = new ZEllipse(pt.getX() - offset, pt.getY() - offset, offset * 2, offset * 2);
			if (filled)
				ell.setFillPaint(Color.BLACK);
			return new ZVisualLeaf(ell);
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#haveMiniAppearance()
		 */
		public boolean haveMiniAppearance(boolean displayAsIcon)
		{
			return displayAsIcon;
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#getMinimumDisplayOnlyAsIconExtent()
		 */
		public UDimension getMinimumDisplayOnlyAsIconExtent()
		{
			return new UDimension(48, 48);
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#formShapeForPreview(UBounds)
		 */
		public Shape formShapeForPreview(UBounds bounds)
		{
			double x = bounds.getX();
			double y = bounds.getY();
			double width = bounds.getWidth();
			double height = bounds.getHeight();
			return new ZRectangle(x - 0.5, y - 0.5, width + 0.9, height + 0.9)
					.getShape();
		}

		public void addToContextMenu(JPopupMenu menu,
				final DiagramViewFacet diagramView,
				final ToolCoordinatorFacet coordinator)
		{
			// add an evolution command to the menu
			JMenuItem evolve = makeEvolveCommand(coordinator, figureFacet, false, false); 
			menu.add(evolve);
		}

		public Set<String> getDisplayStyles(boolean displayingOnlyAsIcon,
				boolean anchorIsTarget)
		{
			return null;
		}

		public JList formSelectionList(String textSoFar)
		{
			Collection<NamedElement> elements = GlobalSubjectRepository.repository
					.findElementsStartingWithName(textSoFar, RequirementsFeatureImpl.class, false);
			Vector<ElementSelection> listElements = new Vector<ElementSelection>();
			for (NamedElement element : elements)
				if (element != figureFacet.getSubject())
					listElements.add(new ElementSelection(element));
			Collections.sort(listElements);

			return new JList(listElements);
		}

		public Object setText(TextableFacet textable, String text,
				Object listSelection, boolean unsuppress)
		{
			return setElementText(figureFacet, textable, text, listSelection,
					unsuppress);
		}

		public ToolFigureClassification getToolClassification(
				RequirementsFeatureSizes sizes,
				boolean displayOnlyIcon,
				UPoint point)
		{
			return new ToolFigureClassification("requirementsfeature,type,element", null);
		}
	}
	
	public static Object setElementText(FigureFacet figure,
			TextableFacet textable, String text, Object listSelection,
			boolean unsuppress)
	{
		NamedElement subject = (NamedElement) figure.getSubject();
		String oldText = subject.getName();

		// 3 possibilities here
		// 1) we have changed from an empty name to a new name
		// 2) we have changed from a non-empty name to something that exists
		// -- point to the new subject, and don't delete the old one
		// 3) we have changed from an empty name to something that does exist
		// -- point to the new one, and delete the old one

		if (listSelection == null) // case (1)
		{
			// just change the name
			subject.setName(text);
			return subject;
		}

		ElementSelection sel = (ElementSelection) listSelection;
		if (oldText.length() == 0)
		{
			// delete the previous subject
			GlobalSubjectRepository.repository.incrementPersistentDelete(subject);
			return sel.getElement();
		}
		else
		{
			// just link to the new object
			return sel.getElement();
		}
	}
	
	public static JMenuItem makeEvolveCommand(final ToolCoordinatorFacet coordinator, final FigureFacet figureFacet, final boolean iface, final boolean stereotype)
	{
		// if this is not at home, allow evolution
    JMenuItem evolve = new JMenuItem("Evolve", EVOLVE_ICON);
    final Package owner =
    	GlobalSubjectRepository.repository.findVisuallyOwningStratum(
    			figureFacet.getDiagram(),
    			figureFacet.getContainerFacet());
    final RequirementsFeature cls = (RequirementsFeature) figureFacet.getSubject();
    final DEStratum perspective =
    	GlobalDeltaEngine.engine.locateObject(owner).asStratum();
    DEElement me = GlobalDeltaEngine.engine.locateObject(cls).asElement();
    evolve.setEnabled(perspective.getTransitive().contains(me.getHomeStratum()));
    
    evolve.addActionListener(new ActionListener()
    {
			public void actionPerformed(ActionEvent event)
			{
				coordinator.startTransaction("Evolve component", "Remove evolution");
				RequirementsFeatureClipboardActionsGem.makeEvolveAction(owner, cls, figureFacet, false);
				coordinator.commitTransaction();
			}
    });
		return evolve;
	}
}
