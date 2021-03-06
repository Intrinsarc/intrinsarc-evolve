package com.intrinsarc.evolve.umldiagrams.packagenode;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.impl.*;

import com.intrinsarc.evolve.umldiagrams.base.*;
import com.intrinsarc.evolve.umldiagrams.basicnamespacenode.*;
import com.intrinsarc.evolve.umldiagrams.classifiernode.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulationbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 *
 * (c) Andrew McVeigh 27-Aug-02
 *
 */
public class ModelMiniAppearanceGem implements Gem
{
	private BasicNamespaceMiniAppearanceFacet miniAppearanceFacet = new BasicNamespaceMiniAppearanceFacetImpl();
	private FigureFacet figureFacet;
	
	public ModelMiniAppearanceGem()
	{
	}
	
	public BasicNamespaceMiniAppearanceFacet getBasicNamespaceMiniAppearanceFacet()
	{
		return miniAppearanceFacet;
	}
	
	public void connectFigureFacet(FigureFacet figureFacet)
	{
	  this.figureFacet = figureFacet;
	}
	
	private class BasicNamespaceMiniAppearanceFacetImpl implements BasicNamespaceMiniAppearanceFacet
	{	
		/**
		 * @see com.intrinsarc.evolve.umldiagrams.base.ClassifierMiniAppearanceFacet#formView(boolean, UBounds)
		 */
		public ZNode formView(boolean displayAsIcon, UBounds bounds)
		{
			// make a simple triangle
			ZPolygon poly = new ZPolygon(new UPoint(bounds.getMiddlePoint().getX(), bounds.getTopLeftPoint().getY()));
			poly.add(bounds.getBottomLeftPoint());
			poly.add(bounds.getBottomRightPoint());
			poly.setFillPaint(Color.white);
			poly.setPenPaint(Color.black);

			return new ZVisualLeaf(poly);
		}

		/**
		 * @see com.intrinsarc.evolve.umldiagrams.base.ClassifierMiniAppearanceFacet#haveMiniAppearance()
		 */
		public boolean haveMiniAppearance()
		{
			return true;
		}
		
		/**
		 * @see com.intrinsarc.evolve.umldiagrams.base.ClassifierMiniAppearanceFacet#formShapeForPreview(UBounds)
		 */
		public Shape formShapeForPreview(UBounds bounds)
		{
			return new ZRectangle(bounds).getShape();
		}

		/**
		 * @see com.intrinsarc.evolve.umldiagrams.base.ClassifierMiniAppearanceFacet#getMinimumDisplayOnlyAsIconExtent()
		 */
		public UDimension getMinimumDisplayOnlyAsIconExtent()
		{
			return new UDimension(48, 48);
		}

    public void addToContextMenu(JPopupMenu menu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
    {
    }

    public Set<String> getDisplayStyles(boolean displayingOnlyAsIcon, boolean anchorIsTarget)
    {
      return null;
    }

    public JList formSelectionList(String textSoFar)
    {
      Collection<NamedElement> elements = GlobalSubjectRepository.repository.findElementsStartingWithName(textSoFar, ModelImpl.class, false);
      Vector<ElementSelection> listElements = new Vector<ElementSelection>();
      for (NamedElement element : elements)
        if (element != figureFacet.getSubject())
          listElements.add(new ElementSelection(element));
      Collections.sort(listElements);
      
      return new JList(listElements);
    }

    public Object setText(TextableFacet textable, String text, Object listSelection, boolean unsuppress)
    {
      return ComponentMiniAppearanceGem.setElementText(
          figureFacet,
          textable,
          text,
          listSelection,
          unsuppress,
          false);
    }
	}
}

