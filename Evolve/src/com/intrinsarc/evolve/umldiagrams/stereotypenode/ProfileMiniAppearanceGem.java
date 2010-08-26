package com.intrinsarc.evolve.umldiagrams.stereotypenode;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.impl.*;

import com.intrinsarc.backbone.generator.*;
import com.intrinsarc.backbone.printers.*;
import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.umldiagrams.base.*;
import com.intrinsarc.evolve.umldiagrams.basicnamespacenode.*;
import com.intrinsarc.evolve.umldiagrams.classifiernode.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulationbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.enhanced.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public class ProfileMiniAppearanceGem implements Gem
{
	private BasicNamespaceMiniAppearanceFacet miniAppearanceFacet = new BasicNamespaceMiniAppearanceFacetImpl();
	private FigureFacet figureFacet;
	
	public ProfileMiniAppearanceGem()
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
			return StereotypeMiniAppearanceGem.makeGuillemetGroup(bounds);
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
      Utilities.addSeparator(menu);
      DEStratum me = GlobalDeltaEngine.engine.locateObject(figureFacet.getSubject()).asStratum();
      menu.add(PackageMiniAppearanceGem.makeShowBackboneCodeItem("Show Backbone code", coordinator, me, me, BackbonePrinterMode.REAL_NAMES));
    }

    public Set<String> getDisplayStyles(boolean displayingOnlyAsIcon, boolean anchorIsTarget)
    {
      return null;
    }

    public JList formSelectionList(String textSoFar)
    {
      Collection<NamedElement> elements = GlobalSubjectRepository.repository.findElementsStartingWithName(textSoFar, ProfileImpl.class, false);
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

