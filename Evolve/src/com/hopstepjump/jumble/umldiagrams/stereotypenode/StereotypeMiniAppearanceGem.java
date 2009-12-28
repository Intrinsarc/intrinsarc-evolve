package com.hopstepjump.jumble.umldiagrams.stereotypenode;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.classifiernode.*;
import com.hopstepjump.repositorybase.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public class StereotypeMiniAppearanceGem implements Gem
{
  private ClassifierMiniAppearanceFacet miniAppearanceFacet = new ClassifierMiniAppearanceFacetImpl();
  private FigureFacet figureFacet;
  
  public StereotypeMiniAppearanceGem()
  {
  }
  
  public void connectFigureFacet(FigureFacet figureFacet)
  {
    this.figureFacet = figureFacet;
  }
  
  public ClassifierMiniAppearanceFacet getClassifierMiniAppearanceFacet()
  {
    return miniAppearanceFacet;
  }
  
  private class ClassifierMiniAppearanceFacetImpl implements ClassifierMiniAppearanceFacet
  { 
    /**
     * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#formView(boolean, UBounds)
     */
    public ZNode formView(boolean displayAsIcon, UBounds bounds)
    {
      // don't bother if the part has no type
      // don't bother if the part has no type
      if (figureFacet.getSubject() == null || figureFacet.hasSubjectBeenDeleted())
        return new ZGroup();
      return makeGuillemetGroup(bounds);
    }

    /**
     * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#haveMiniAppearance()
     */
    public boolean haveMiniAppearance()
    {
      return true;
    }
    
    /**
     * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#getMinimumDisplayOnlyAsIconExtent()
     */
    public UDimension getMinimumDisplayOnlyAsIconExtent()
    {
      return new UDimension(24, 24);
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
      ZEllipse ellipse = new ZEllipse(x-0.5, y-0.5, width+0.9, height+0.9);
      return ellipse.getShape();
    }

    public void addToContextMenu(JPopupMenu menu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
    {
			// add an evolution command to the menu
			JMenuItem evolve = ComponentMiniAppearanceGem.makeEvolveCommand(coordinator, figureFacet, false, true); 
			menu.add(evolve);
    }

    public Set<String> getDisplayStyles(boolean displayingOnlyAsIcon, boolean anchorIsTarget)
    {
      return null;
    }

    public JList formSelectionList(String textSoFar)
    {
      Collection<NamedElement> elements = GlobalSubjectRepository.repository.findElementsStartingWithName(textSoFar, StereotypeImpl.class, false);
      Vector<ElementSelection> listElements = new Vector<ElementSelection>();
      for (NamedElement element : elements)
        if (element != figureFacet.getSubject())
          listElements.add(new ElementSelection(element));
      Collections.sort(listElements);
      
      return new JList(listElements);
    }

    public SetTextPayload setText(TextableFacet textable, String text, Object listSelection, boolean unsuppress, Object oldMemento)
    {
      return ComponentMiniAppearanceGem.setElementText(
          figureFacet,
          textable,
          text,
          listSelection,
          unsuppress,
          oldMemento);
    }

    public SetTextPayload unSetText(Object memento)
    {
      return ComponentMiniAppearanceGem.unSetElementText(figureFacet, memento);
    }
    
		public ToolFigureClassification getToolClassification(
				ClassifierSizes sizes,
				boolean displayOnlyIcon,
				boolean suppressAttributes,
				boolean suppressOperations,
				boolean suppressContents,
				boolean hasPorts,
				UPoint point)
		{
			return ComponentMiniAppearanceGem.getTypedToolClassification(
					"stereotype,class,classifier,element",
					sizes,
					displayOnlyIcon,
					suppressAttributes,
					suppressOperations,
					suppressContents,
					hasPorts,
					point);
		}
  }
  
  public static ZGroup makeGuillemetGroup(UBounds bounds)
  {
    // set up the sizes
    double middleX = bounds.getCenterX();
    double middleY = bounds.getCenterY();
    double vOffset = Math.max((int) (bounds.getWidth() / 4), 3);
    double hOffset = Math.max((int) (bounds.getWidth() / 8), 1);
    double startY = bounds.getTopLeftPoint().getY() + vOffset/2;
    double endY = bounds.getBottomLeftPoint().getY() - vOffset/2;
    double startX = bounds.getTopLeftPoint().getX();
    double endX = bounds.getBottomRightPoint().getX();

    ZGroup group = new ZGroup();
    ZRectangle transparentRect = new ZRectangle(bounds);
    transparentRect.setPenPaint(null);
    transparentRect.setFillPaint(new Color(0,0,0,0));
    group.addChild(new ZVisualLeaf(transparentRect));
    group.addChild(new ZVisualLeaf(new ZLine(middleX - hOffset - vOffset, startY + vOffset, startX, middleY)));
    group.addChild(new ZVisualLeaf(new ZLine(startX, middleY, middleX - hOffset - vOffset, endY - vOffset)));
    group.addChild(new ZVisualLeaf(new ZLine(middleX - hOffset, startY + vOffset, startX + vOffset, middleY)));
    group.addChild(new ZVisualLeaf(new ZLine(startX + vOffset, middleY, middleX - hOffset, endY - vOffset)));

    group.addChild(new ZVisualLeaf(new ZLine(middleX + hOffset + vOffset, startY + vOffset, endX, middleY)));
    group.addChild(new ZVisualLeaf(new ZLine(endX, middleY, middleX + hOffset + vOffset, endY - vOffset)));
    group.addChild(new ZVisualLeaf(new ZLine(middleX + hOffset, startY + vOffset, endX - vOffset, middleY)));
    group.addChild(new ZVisualLeaf(new ZLine(endX - vOffset, middleY, middleX + hOffset, endY - vOffset)));

    return group;    	
  }
}
