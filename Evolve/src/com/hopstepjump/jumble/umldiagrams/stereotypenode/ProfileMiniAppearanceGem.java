package com.hopstepjump.jumble.umldiagrams.stereotypenode;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.backbone.generator.*;
import com.hopstepjump.backbone.printers.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.basicnamespacenode.*;
import com.hopstepjump.jumble.umldiagrams.classifiernode.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.enhanced.*;

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
		 * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#formView(boolean, UBounds)
		 */
		public ZNode formView(boolean displayAsIcon, UBounds bounds)
		{
			return StereotypeMiniAppearanceGem.makeGuillemetGroup(bounds);
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#haveMiniAppearance()
		 */
		public boolean haveMiniAppearance()
		{
			return true;
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#formShapeForPreview(UBounds)
		 */
		public Shape formShapeForPreview(UBounds bounds)
		{
			return new ZRectangle(bounds).getShape();
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#getMinimumDisplayOnlyAsIconExtent()
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
          unsuppress);
    }
	}
}

