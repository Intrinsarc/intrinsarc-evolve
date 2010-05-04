package com.hopstepjump.jumble.umldiagrams.featurenode;

import java.awt.event.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.repository.*;
import com.hopstepjump.repositorybase.*;


/**
 * @author Andrew
 */
public final class AttributeFeatureTypeFacetImpl implements FeatureTypeFacet
{
	public static final String FIGURE_NAME = "attribute";  // for the creator
  private BasicNodeFigureFacet figureFacet;
  private TextableFacet textableFacet;
  private static final String READ_ONLY = "{readonly}";
  private static final String WRITE_ONLY = "{writeonly}";
  private Pattern pattern = Pattern.compile(
      "(\\w+)" +                                                    // attribute name
            "(?:\\s*" +                                              // first bracket and whitespace
                  "(?:\\s*\\[(?:([0-9]+)\\s*\\.\\.\\s*)?([0-9]+|\\*)\\s*\\])?\\s*" +  // multiplicity     (optional)
                  ":\\s*(\\w+)\\s*(?:\\=\\s*([^\\{]*\\S))?" +             // type and default (optional)
            ")?\\s*" +                                               // last bracket and whitespace
            "(\\{readonly\\}|\\{writeonly\\})?\\s*");							   // readonly or writeonly

  public AttributeFeatureTypeFacetImpl(BasicNodeFigureFacet figureFacet, TextableFacet textableFacet)
  {
    this.figureFacet = figureFacet;
    this.textableFacet = textableFacet;
  }
  
	/**
	 * @see com.giroway.jumble.umldiagrams.classdiagram.featurenode.FeatureFigure#getFeatureType()
	 */
	public int getFeatureType()
	{
		return 0;
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
  	return name;
  }

  public String setText(String text, Object listSelection)
  {
    final SubjectRepositoryFacet repository = GlobalSubjectRepository.repository; 

    // make a command to effect the changes
    final Property typed = getSubject();
    
    // save the name and type
    final ValueSpecification oldLower = typed.getLowerValue();
    final ValueSpecification oldUpper = typed.getUpperValue();
    final Type oldType = getSubjectType(typed);    
    
    // get the new name and type
    Matcher matcher = pattern.matcher(text);
    final String newName;
    final String newLowerMult;
    final String newUpperMult;
    final String newTypeName;
    final String newDefaultText;
    final String readWriteOnly;
    if (matcher.matches())
    {
      newName = matcher.group(1);
      newLowerMult = matcher.group(2);
      newUpperMult = matcher.group(3);
      newTypeName = matcher.group(4);
      newDefaultText = matcher.group(5);
      readWriteOnly = matcher.group(6);
    }
    else
    {
      newName = text;
      newLowerMult = null;
      newUpperMult = null;
      newTypeName = null;
      newDefaultText = null;
      readWriteOnly = null;
    }
    
    // set the name
    typed.setName(newName);
    
    // set the start and end multiplicity
    if (newLowerMult != null)
    {
    	typed.setLowerBound(new Integer(newLowerMult));
    	final ValueSpecification lowerSpec = typed.getLowerValue();
    	repository.incrementPersistentDelete(lowerSpec);
  		repository.decrementPersistentDelete(lowerSpec);
  		typed.setLowerValue(lowerSpec);
    }
    
    PropertyAccessKind newReadWriteOnly = PropertyAccessKind.READ_WRITE_LITERAL;
    if (readWriteOnly == null)
      newReadWriteOnly = PropertyAccessKind.READ_WRITE_LITERAL;
    else
    if (readWriteOnly.equals(READ_ONLY))
    	newReadWriteOnly = PropertyAccessKind.READ_ONLY_LITERAL;
    else
    if (readWriteOnly.equals(WRITE_ONLY))
    	newReadWriteOnly = PropertyAccessKind.WRITE_ONLY_LITERAL;
    final PropertyAccessKind finalReadWrite = newReadWriteOnly;
    
    // only bother if this changes
		typed.setReadWrite(finalReadWrite);

    if (newUpperMult != null)
    {
    	final int newUpper = newUpperMult.equals("*") ? -1 : new Integer(newUpperMult); 
    	typed.setUpperBound(new Integer(newUpper));
    	final ValueSpecification upperSpec = typed.getUpperValue();
    	repository.incrementPersistentDelete(upperSpec);
  		repository.decrementPersistentDelete(upperSpec);
  		typed.setUpperValue(upperSpec);
    }

    // decrement the existing lower and upper value
    if (oldLower != null)
    	repository.incrementPersistentDelete(oldLower);
    if (oldUpper!= null)
    	repository.incrementPersistentDelete(oldUpper);

    // find or create the type
    if (newTypeName != null)
    {
    	Type type;
      if (listSelection != null)
        type = (Classifier) ((ElementSelection) listSelection).getElement();
      else
      {
      	// possibly keep old type
      	if (oldType != null && newTypeName.equals(oldType.getName()))
      		type = oldType;
      	else
      	{
	      	Vector<ElementSelection> possible = locatePrimitiveElements(newTypeName);
		      type = possible.isEmpty() ? null : (Type) possible.get(0).getElement();
      	}
      }
      final Type newType = type;
      
      if (newType == null)
      {
        // if we can't find the type, make a new one
        final Type createdType = ((Package) repository.findOwningElement(typed, Package.class)).createOwnedClass(newTypeName, false);
        repository.incrementPersistentDelete(createdType);
        repository.decrementPersistentDelete(createdType);
    		typed.setType(createdType);
      }
      else
      	typed.setType(newType);
    }
    else
    {
    	typed.setType(null);
    }
    
    // set the default value      
    if (newDefaultText == null)
    {
      List<ValueSpecification> defaultValue = typed.getDefaultValues();
      if (!defaultValue.isEmpty())
      	typed.settable_getDefaultValues().clear();
    }
    else
    {
    	Classifier owner = findOwningVisualElement();    	
    	Package perspective = repository.findVisuallyOwningStratum(figureFacet.getDiagram(), figureFacet.getContainerFacet());
    	final List<ValueSpecification> values = ValueParser.decodeParameters(newDefaultText, perspective, owner);

    	// handle the default value as an opaque expression
      final Expression defaultValue = (Expression) typed.createDefaultValues(UML2Package.eINSTANCE.getExpression());
      repository.incrementPersistentDelete(defaultValue);
  		typed.settable_getDefaultValues().clear(); typed.settable_getDefaultValues().addAll(0, values);
    }
    
    // resize
    text = makeNameFromSubject();
    figureFacet.performResizingTransaction(textableFacet.vetTextResizedExtent(text));
    return text;
  }
  
  private ValueSpecification getFirst(EList defaultValues)
	{
  	if (defaultValues.isEmpty())
  		return null;
  	return (ValueSpecification) defaultValues.get(0);
	}

  private Property getSubject()
  {
    return (Property) figureFacet.getSubject();
  }

  public String makeNameFromSubject()
  {
  	DiagramFacet diagram = figureFacet.getDiagram();
  	ContainerFacet container = figureFacet.getContainedFacet().getContainer();
  	Classifier cls = (Classifier) GlobalSubjectRepository.repository.findVisuallyOwningNamespace(diagram, container);
  	Package pkg = GlobalSubjectRepository.repository.findVisuallyOwningPackage(diagram, container);
  	return UMLNodeText.makeNameFromAttribute(pkg, cls, (Property) figureFacet.getSubject());
  }
  	
  private Type getSubjectType(Property subject)
  {
    return subject.undeleted_getType();
  }
  
  private Classifier findOwningVisualElement()
  {
    SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
    return (Classifier)
      repository.findOwningElement(
          figureFacet.getFigureReference(), UML2Package.eINSTANCE.getClassifier());
  }

  public void performPostContainerDropTransaction()
  {
    Element subject = FeatureNodeGem.getPossibleDeltaSubject(figureFacet.getSubject());
    Classifier oldOwner = (Classifier) subject.getOwner();

    Classifier newOwner = findOwningVisualElement();
         
     if (newOwner != oldOwner)
     {
       if (subject instanceof DeltaReplacedConstituent)
       {
         if (oldOwner instanceof Interface)
           ((Interface) oldOwner).getDeltaReplacedAttributes().remove(subject);
         else if (oldOwner instanceof Class)
           ((Class) oldOwner).getDeltaReplacedAttributes().remove(subject);
         
         if (newOwner instanceof Interface)
           ((Interface) newOwner).getDeltaReplacedAttributes().add(subject);
         else if (newOwner instanceof Class)
           ((Class) newOwner).getDeltaReplacedAttributes().add(subject);             
       }
       else
       {
         if (oldOwner instanceof Interface)
           ((Interface) oldOwner).getOwnedAttributes().remove(subject);
         else if (oldOwner instanceof Class)
           ((Class) oldOwner).getOwnedAttributes().remove(subject);
         
         if (newOwner instanceof Interface)
           ((Interface) newOwner).getOwnedAttributes().add(subject);
         else if (newOwner instanceof Class)
           ((Class) newOwner).getOwnedAttributes().add(subject);
       }
     }
  }

  public void generateDeleteDelta(ToolCoordinatorFacet coordinator, final Classifier owner)
  {
    // add this to the classifier as a delete delta
    Element feature = FeatureNodeGem.getOriginalSubject(figureFacet.getSubject());
    
    DeltaDeletedConstituent delete;
    if (owner instanceof ClassImpl)
    {
      delete = ((Class) owner).createDeltaDeletedAttributes();
      delete.setDeleted(feature);
    }
    else
    if (owner instanceof InterfaceImpl)
    {
      delete = ((Interface) owner).createDeltaDeletedAttributes();
      delete.setDeleted(feature);
    }
  }

  public JMenuItem getReplaceItem(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
  {
    // for adding operations
    JMenuItem replaceAttributeItem = new JMenuItem("Replace");
    replaceAttributeItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        Property replaced = (Property) figureFacet.getSubject();
        Property original = (Property) ClassifierConstituentHelper.getOriginalSubject(replaced);
        final FigureFacet clsFigure = ClassifierConstituentHelper.extractVisualClassifierFigureFromConstituent(figureFacet);
        Classifier cls = (Classifier) clsFigure.getSubject();
        
        coordinator.startTransaction("replaced attribute", "removed replaced attribute");
        final DeltaReplacedAttribute replacement = createDeltaReplacedAttribute(cls, replaced, original);
        coordinator.commitTransaction(true);
        FigureFacet createdFeature = ClassifierConstituentHelper.findSubfigure(clsFigure, replacement.getReplacement());
        diagramView.getSelection().clearAllSelection();
        diagramView.getSelection().addToSelection(createdFeature, true);
      }
    });

    return replaceAttributeItem;
  }
  
  public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
  {
    // pass this on up to the container, as we may not be in the place where we are defined
    ContainerFacet container = figureFacet.getContainedFacet().getContainer();
    if (container == null)
      return true;
    
    // only truly writeable/moveable if this is owned by the same visual classifier
    // however, for a kill, this is fine
    if (!kill)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
      if (repository.findVisuallyOwningNamespace(figureFacet.getDiagram(), figureFacet.getContainedFacet().getContainer()) !=
          FeatureNodeGem.getPossibleDeltaSubject(figureFacet.getSubject()).getOwner())
        return true;
    }
    
    // only writeable if the class is located correctly
    return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet);
  }
  
  public DeltaReplacedAttribute createDeltaReplacedAttribute(Classifier owner, Property replaced, Property original)
  {     
    DeltaReplacedAttribute replacement = null;
    Property attr = null;
    if (owner instanceof Interface)
    {
      replacement = ((Interface) owner).createDeltaReplacedAttributes();
      replacement.setReplaced(original);
      attr = (Property) replacement.createReplacement(UML2Package.eINSTANCE.getProperty());
    }
    else
    if (owner instanceof StructuredClassifier)
    {
      replacement = ((Class) owner).createDeltaReplacedAttributes();
      replacement.setReplaced(original);
      attr = (Property) replacement.createReplacement(UML2Package.eINSTANCE.getProperty());
    }
    attr.setName(replaced.getName());
    attr.setVisibility(replaced.getVisibility());
    attr.setType(replaced.getType());
    attr.setReadWrite(replaced.getReadWrite());

    // set upper and lower bounds
    if (replaced.getUpperValue() != null)
      attr.setUpperBound(new Integer(replaced.getUpper()));
    if (replaced.getLowerValue() != null)
      attr.setLowerBound(new Integer(replaced.getLower()));

    // set a possible default value
  	for (Object s : replaced.getDefaultValues())
  	{
    	ValueSpecification spec = (ValueSpecification) s;
    	if (spec instanceof PropertyValueSpecification)
    	{
				PropertyValueSpecification v = UML2Factory.eINSTANCE.createPropertyValueSpecification();
				v.setProperty(((PropertyValueSpecification) spec).getProperty());
				attr.settable_getDefaultValues().add(v);
    	}
    	else
    	{
				Expression v = UML2Factory.eINSTANCE.createExpression();
				v.setBody(((Expression) spec).getBody());
				attr.settable_getDefaultValues().add(v);
    	}
    }
    
    return replacement;
  }

	public JList formSelectionList(String textSoFar)
	{
    // get the class name after the colon
    int pos = textSoFar.indexOf(':');
    if (pos == -1)
      return null;
      
    String name = textSoFar.substring(pos + 1);
    StringTokenizer tok = new StringTokenizer(name);
    String nameSoFar = tok.hasMoreTokens() ? tok.nextToken() : "";
    
    if (nameSoFar.length() == 0)
      return null;
    
    return new JList(locatePrimitiveElements(nameSoFar));
  }
	
	private Vector<ElementSelection> locatePrimitiveElements(String nameSoFar)
	{
    Collection<NamedElement> elements = GlobalSubjectRepository.repository.findElementsStartingWithName(nameSoFar, ClassImpl.class, true);

    Vector<ElementSelection> listElements = new Vector<ElementSelection>();
    for (NamedElement element : elements)
    	if (StereotypeUtilities.isStereotypeApplied(element, CommonRepositoryFunctions.PRIMITIVE_TYPE) || element.getUuid().equals("String") || element.getUuid().equals("boolean"))
    		listElements.add(new ElementSelection(element));

    Collection<NamedElement> ielements = GlobalSubjectRepository.repository.findElementsStartingWithName(nameSoFar, InterfaceImpl.class, true);
    for (NamedElement element : ielements)
  		listElements.add(new ElementSelection(element));
    Collections.sort(listElements);
    return listElements;
	}
}
