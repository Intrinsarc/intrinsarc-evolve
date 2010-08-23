package com.intrinsarc.evolve.umldiagrams.slotnode;

import java.util.*;
import java.util.regex.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.umldiagrams.featurenode.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulationbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.repository.*;
import com.intrinsarc.repositorybase.*;

public class SlotFeatureTypeFacetImpl implements FeatureTypeFacet
{
	public static final String FIGURE_NAME = "slot";  // for the creator
  private BasicNodeFigureFacet figureFacet;
	private TextableFacet textableFacet;
  private Pattern pattern = Pattern.compile("(\\w+)\\s*=\\s*(.*)\\s*");

  public SlotFeatureTypeFacetImpl(BasicNodeFigureFacet figureFacet, TextableFacet textableFacet)
  {
    this.figureFacet = figureFacet;
    this.textableFacet = textableFacet;
  }
  
	/**
	 * @see com.giroway.jumble.umldiagrams.classdiagram.featurenode.FeatureFigure#getFeatureType()
	 */
	public int getFeatureType()
	{
		return 2;
	}

  public String getFigureName()
  {
    return FIGURE_NAME;
  }
  
    /**
   * the short name is the elided form of the entire operation name
   */
  public String makeShortName(String name)
  {
    // if we have a text override, use this
    Slot slot = (Slot) figureFacet.getSubject();
    String overriddenName =
      StereotypeUtilities.extractStringProperty(slot, CommonRepositoryFunctions.OVERRIDDEN_SLOT_TEXT);
    
  	return overriddenName != null ? overriddenName : name;
  }

  public String setText(String text, Object listSelection)
  {
    // make a command to effect the changes
    final Slot slot = getSubject();
    
    // save the old values
    final Property oldAttribute = (Property) slot.undeleted_getDefiningFeature();
    final ArrayList oldExpressions = slot.undeleted_getValues();
    
    // get the new name and type
    Matcher matcher = pattern.matcher(text);
    final String newName;
    final String newValue;
    if (matcher.matches())
    {
      newName = matcher.group(1);
      newValue = matcher.group(2);
    }
    else
    {
    	newName = "";
    	newValue = "??";
    }

    // look for the attribute in the class with the given name
    Class type = (Class) ((InstanceSpecification) slot.getOwner()).undeleted_getClassifiers().get(0);
    Property newProperty = null;
    if (type != null)
    {
      Package current = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), figureFacet.getContainerFacet());
    	for (DeltaPair pair : UMLNodeText.getPossibleAttributes(current, type))
    	{
    		if (pair.getConstituent().getName().equals(newName))
    		{
    			newProperty = (Property) pair.getOriginal().getRepositoryObject();
    			break;
    		}
    	}
    }
    
    // may be null if not found
    final Property newAttribute = newProperty;
    final List<ValueSpecification> values = resolveParameters(slot, newValue);
    
		slot.setDefiningFeature(newAttribute);
		slot.settable_getValues().clear();
		slot.settable_getValues().addAll(0, values);
    
    String finalText = makeNameFromSubject();
    figureFacet.performResizingTransaction(textableFacet.vetTextResizedExtent(finalText));
    return finalText;
  }
  
  private List<ValueSpecification> resolveParameters(Slot slot, String values)
	{
  	SubjectRepositoryFacet repos = GlobalSubjectRepository.repository;
  	Package pkg = repos.findVisuallyOwningStratum(figureFacet.getDiagram(), figureFacet.getContainedFacet().getContainer());
  	Classifier cls = (Classifier) repos.findOwningElement(slot, Classifier.class);

  	if (cls == null || pkg == null)
  		return null;
  	
  	return ValueParser.decodeParameters(values, pkg, cls);

	}
  
  private Slot getSubject()
  {
    return (Slot) figureFacet.getSubject();
  }

  public String makeNameFromSubject()
  {
    if (getSubject() == null)
      return "";

    ContainerFacet container = figureFacet.getContainedFacet().getContainer();
    Package current = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), container);
    Classifier cls = (Classifier) GlobalSubjectRepository.repository.findVisuallyOwningNamespace(figureFacet.getDiagram(), container);    
  	return UMLNodeText.getSlotText(current, cls, getSubject()); 
  }
  	
  private Type getSubjectType(Property subject)
  {
    return subject.undeleted_getType();
  }
  
  private Class findOwningVisualElement()
  {
    SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
    return (Class)
      repository.findOwningElement(
          figureFacet.getFigureReference(), UML2Package.eINSTANCE.getClass_());
  }

  public void performPostContainerDropTransaction()
  {
  }

  public void generateDeleteDelta(ToolCoordinatorFacet coordinator, Classifier owner)
  {
  }

  public JMenuItem getReplaceItem(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
  {
    return null;
  }
  
  public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
  {
    return figureFacet.getContainedFacet().getContainer().getFigureFacet().isSubjectReadOnlyInDiagramContext(kill);
  }

	public JList formSelectionList(String textSoFar)
	{
		return null;
	}
}
