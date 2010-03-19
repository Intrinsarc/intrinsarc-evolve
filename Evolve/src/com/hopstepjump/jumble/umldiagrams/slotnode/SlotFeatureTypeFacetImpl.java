package com.hopstepjump.jumble.umldiagrams.slotnode;

import java.util.*;
import java.util.regex.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.featurenode.*;
import com.hopstepjump.repository.*;
import com.hopstepjump.repositorybase.*;

public class SlotFeatureTypeFacetImpl implements FeatureTypeFacet
{
	public static final String FIGURE_NAME = "slot";  // for the creator
  private BasicNodeFigureFacet figureFacet;
  private Pattern pattern = Pattern.compile("(\\w+)\\s*(?:\\(\\s*(.*)\\s*\\)|=\\s*(.*))\\s*");

  public SlotFeatureTypeFacetImpl(BasicNodeFigureFacet figureFacet, TextableFacet textableFacet)
  {
    this.figureFacet = figureFacet;
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

  public Object setText(String text, Object listSelection)
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
    final boolean alias;
    if (matcher.matches())
    {
      newName = matcher.group(1);
      alias = matcher.group(2) != null;
      newValue = alias ? matcher.group(2) : matcher.group(3);
    }
    else
    {
    	newName = "";
    	newValue = "??";
    	alias = false;
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
    
    // find a possible attribute in the parent of the part
    if (alias)
    {
	    final List<ValueSpecification> values = resolveParameters(slot, newValue);
	    ValueSpecification spec = values.isEmpty() ? null : (ValueSpecification) values.get(0);
	    final PropertyValueSpecification valueSpec =
	    	(spec != null && spec instanceof PropertyValueSpecification) ? (PropertyValueSpecification) spec : null;
	    if (valueSpec != null)
	    	valueSpec.setAliased(true);
	    
	    // if the value matches a property, create a PropertyValueSpecification, which is
	    // possible aliased
			slot.setDefiningFeature(newAttribute);
			slot.settable_getValues().clear();
			if (valueSpec != null)
				slot.settable_getValues().add(valueSpec);
      return null;
    }
    else
    {
	    final List<ValueSpecification> values = resolveParameters(slot, newValue);
	    
			slot.setDefiningFeature(newAttribute);
  		slot.settable_getValues().clear();
  		slot.settable_getValues().addAll(0, values);
	    return null;
    }
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

	public void unSetText(Object memento)
  {
  	((Command) memento).unExecute();
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

  public Command generateDeleteDelta(ToolCoordinatorFacet coordinator, Classifier owner)
  {
    return null;
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
