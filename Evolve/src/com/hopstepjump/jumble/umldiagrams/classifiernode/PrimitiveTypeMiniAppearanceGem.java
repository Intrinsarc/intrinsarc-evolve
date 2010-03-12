package com.hopstepjump.jumble.umldiagrams.classifiernode;

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
import com.hopstepjump.repositorybase.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;


public class PrimitiveTypeMiniAppearanceGem implements Gem
{
  private ClassifierMiniAppearanceFacet miniAppearanceFacet = new ClassifierMiniAppearanceFacetImpl();
  private FigureFacet figureFacet;
  
  public PrimitiveTypeMiniAppearanceGem()
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
      if (figureFacet.getSubject() == null || figureFacet.hasSubjectBeenDeleted())
        return new ZGroup();
      
      ZGroup group = new ZGroup();
      ZRectangle ell = new ZRectangle(bounds.getRectangle2D());
      ell.setFillPaint(Color.BLACK);
      group.addChild(new ZVisualLeaf(ell));

      return group;
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
      return new ZRectangle(x-0.5, y-0.5, width+0.9, height+0.9).getShape();
    }



    public void addToContextMenu(JPopupMenu menu, final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
    {
    }

    public Set<String> getDisplayStyles(boolean displayingOnlyAsIcon, boolean anchorIsTarget)
    {
      return null;
    }

    public JList formSelectionList(String textSoFar)
    {
      Collection<NamedElement> elements = GlobalSubjectRepository.repository.findElementsStartingWithName(textSoFar, PrimitiveTypeImpl.class, false);
      Vector<ElementSelection> listElements = new Vector<ElementSelection>();
      for (NamedElement element : elements)
        if (element != figureFacet.getSubject())
          listElements.add(new ElementSelection(element));
      Collections.sort(listElements);
      
      return new JList(listElements);
    }

    public SetTextPayload setText(TextableFacet textable, String text, Object listSelection, boolean unsuppress)
    {
      return setElementText(figureFacet, textable, text, listSelection, unsuppress);
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
					"class,classifier,element",
					sizes,
					displayOnlyIcon,
					suppressAttributes,
					suppressOperations,
					suppressContents,
					hasPorts,
					point);
		}
  }
  
  public static SetTextPayload setElementText(FigureFacet figure, TextableFacet textable, String text, Object listSelection, boolean unsuppress)
  {
    NamedElement subject = (NamedElement) figure.getSubject();
    String oldText = subject.getName();
    
    // 3 possibilities here      
    // 1) we have changed from an empty name to a new name
    // 2) we have changed from a non-empty name to something that exists
    //     -- point to the new subject, and don't delete the old one
    // 3) we have changed from an empty name to something that does exist
    //     -- point to the new one, and delete the old one
    
    if (listSelection == null)  // case (1)
    {
      // just change the name
      subject.setName(text);
      return new SetTextPayload(null, new Object[]{oldText, null, false, subject});
    }
    
    ElementSelection sel = (ElementSelection) listSelection;
    if (oldText.length() == 0)
    {
      // delete the previous subject
      GlobalSubjectRepository.repository.incrementPersistentDelete(subject);
      return new SetTextPayload(sel.getElement(), new Object[]{null, subject, true});
    }
    else
    {
      // just link to the new object
      return new SetTextPayload(sel.getElement(), new Object[]{null, subject, false});
    }
  }

  public static SetTextPayload unSetElementText(FigureFacet figure, Object memento)
  {
    SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;      
    Object[] saved = (Object[]) memento;
    NamedElement subject = (NamedElement) figure.getSubject();

    if (saved[0] != null)
      subject.setName((String) saved[0]);
    Classifier oldSubject = (Classifier) saved[1];
    Boolean oldDeleted = (Boolean) saved[2];
    if (oldDeleted)
      repository.decrementPersistentDelete(oldSubject);
    if (oldSubject != null)
      subject = oldSubject;
    
    return new SetTextPayload(subject, null);
  }
}
