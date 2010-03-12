package com.hopstepjump.jumble.umldiagrams.classifiernode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.packageview.base.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.enhanced.*;

import edu.umd.cs.jazz.*;

public class PartMiniAppearanceGem implements Gem
{
  private ClassifierMiniAppearanceFacet miniAppearanceFacet = new ClassifierMiniAppearanceFacetImpl();
  private BasicNodeFigureFacet figureFacet;
  private ClassifierMiniAppearanceFacet interfaceFacet;
  private ClassifierMiniAppearanceFacet componentFacet;
  
  public PartMiniAppearanceGem()
  {
  }
  
  public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
  {
    this.figureFacet = figureFacet;
  }
  
  public void connectComponentMiniAppearanceFacet(ClassifierMiniAppearanceFacet mini)
  {
    this.componentFacet = mini;
  }
  
  public void connectInterfaceMiniAppearanceFacet(ClassifierMiniAppearanceFacet mini)
  {
    this.interfaceFacet = mini;
  }
  
  public ClassifierMiniAppearanceFacet getClassifierMiniAppearanceFacet()
  {
    return miniAppearanceFacet;
  }
  
  private boolean isInterface()
  {
  	Element subject = (Element) figureFacet.getSubject();
  	if (subject == null)
  		return false;
  	return ((Property) subject).undeleted_getType() instanceof Interface;
  }
  
  private boolean isClass()
  {
  	Element subject = (Element) figureFacet.getSubject();
  	if (subject == null)
  		return false;
  	ComponentKindEnum kind = new ElementProperties(figureFacet).getComponentKind();
  	return kind == ComponentKindEnum.NORMAL;
  }

  private class ClassifierMiniAppearanceFacetImpl implements ClassifierMiniAppearanceFacet
  { 
    /**
     * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#formView(boolean, UBounds)
     */
    public ZNode formView(boolean displayAsIcon, UBounds bounds)
    {
      if (isClass())
        return componentFacet.formView(displayAsIcon, bounds);
      else
      if (isInterface())
        return interfaceFacet.formView(displayAsIcon, bounds);
      else
      	return null;
    }

    /**
     * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#haveMiniAppearance()
     */
    public boolean haveMiniAppearance()
    {
      return isClass() || isInterface();
    }

    /**
     * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#wantsLinkDisplayStyle(boolean, String)
     */
    public Set<String> getDisplayStyles(
      boolean displayingOnlyAsIcon,
      boolean anchorIsTarget)
    {
      if (isClass())
        return componentFacet.getDisplayStyles(displayingOnlyAsIcon, anchorIsTarget);
      else
        return interfaceFacet.getDisplayStyles(displayingOnlyAsIcon, anchorIsTarget);
    }
    
    /**
     * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#getMinimumDisplayOnlyAsIconExtent()
     */
    public UDimension getMinimumDisplayOnlyAsIconExtent()
    {
      if (isClass())
        return componentFacet.getMinimumDisplayOnlyAsIconExtent();
      else
        return interfaceFacet.getMinimumDisplayOnlyAsIconExtent();
    }
    
    /**
     * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#formShapeForPreview(UBounds)
     */
    public Shape formShapeForPreview(UBounds bounds)
    {
      if (isClass())
        return componentFacet.formShapeForPreview(bounds);
      else
        return interfaceFacet.formShapeForPreview(bounds);
    }

    public void addToContextMenu(JPopupMenu menu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
    {
    	Utilities.addSeparator(menu);
    	JMenuItem editType = new JMenuItem("Locate type in browser");
    	final Element type = ((Property) figureFacet.getSubject()).undeleted_getType();
    	editType.setEnabled(type != null);
    	
    	menu.add(editType);
    	editType.addActionListener(new ActionListener()
    	{
				public void actionPerformed(ActionEvent e)
				{
					GlobalPackageViewRegistry.activeBrowserRegistry.openBrowser(type);
				}    		
    	});
    }

    public JList formSelectionList(String textSoFar)
    {
      // get the class name after the colon
      int pos = textSoFar.indexOf(':');
      if (pos == -1)
        return null;
        
      String name = textSoFar.substring(pos + 1).trim();
      if (name.length() == 0)
        return null;
      
      Collection<NamedElement> elements = GlobalSubjectRepository.repository.findElementsStartingWithName(name, ClassImpl.class, true);
      Vector<ElementSelection> listElements = new Vector<ElementSelection>();
      for (NamedElement element : elements)
        listElements.add(new ElementSelection(element));
      Collections.sort(listElements);
      
      return new JList(listElements);
    }

    public SetTextPayload setText(TextableFacet textable, String text, Object listSelection, boolean unsuppress)
    {
      final Property part = (Property) figureFacet.getSubject();
      // make sure this has all the part bits still :-)
      final InstanceSpecification instance = UMLTypes.extractInstanceOfPart(part);
      if (instance == null)
        return null;
      
      // the user can change the name, or repoint this at another classifier
      final String oldName = parseOutName(part.getName());
      final Classifier oldType = (Classifier) part.getType(); 
      final String newName = parseOutName(text);
      String newClassifierName = parseOutClassifierName(text);
      
      Classifier newType = oldType;
      
      // don't change if the text hasn't changed
      if (listSelection != null)
        newType = (Classifier) ((ElementSelection) listSelection).getElement();
      else
      if (newClassifierName.length() != 0 && (oldType == null || !newClassifierName.equals(oldType.getName())))
        newType = parseOutClassifier(text);
      final Classifier newPartType = newType;
      
      // change the subject's name
      part.setName(newName);
      part.setType(newPartType);
      instance.getClassifiers().clear();
      if (newPartType != null)
        instance.getClassifiers().add(newPartType);
      
      return null;
    }

		public ToolFigureClassification getToolClassification(
				ClassifierSizes makeActualSizes,
				boolean displayOnlyIcon,
				boolean suppressAttributes,
				boolean suppressOperations,
				boolean suppressContents,
				boolean hasPorts,
				UPoint point)
		{
			return new ToolFigureClassification("part", null);
		}
  }
  
  /**
   * this is only used as a last resort
   * @param text
   */
  private String parseOutClassifierName(String text)
  {
    // parse the classifiers, by looking for ":" between the elements
    String[] names = text.split(":");
    String classifier = "";
    if (names.length > 1)
      classifier = names[1].trim();
    return classifier;
  }
    
  /**
   * this is only used as a last resort
   * @param text
   */
  private Class parseOutClassifier(String text)
  {
    String classifier = parseOutClassifierName(text);
    
    if (classifier.length() == 0)
      return null;
    else
    {
      return 
        (ClassImpl)
        CommonRepositoryFunctions.translateFromSubstitutingToSubstituted(
         (Classifier)
          GlobalSubjectRepository.repository.findElement(
            classifier,
            new java.lang.Class<?>[]{ClassImpl.class}));
    }
  }
    
  /**
   * @param text
   * @return
   */
  private String parseOutName(String text)
  {
    // take a trimmed name up to the text
    if (text.contains(":"))
    {
      String[] split = text.split(":");
      if (split.length == 0)
        return "";
      return text.split(":")[0].trim();
    }
    return text.trim();
  }
}
