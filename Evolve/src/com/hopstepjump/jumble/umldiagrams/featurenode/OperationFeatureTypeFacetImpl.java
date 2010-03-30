package com.hopstepjump.jumble.umldiagrams.featurenode;

import java.awt.event.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;

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

public final class OperationFeatureTypeFacetImpl implements FeatureTypeFacet
{
	public static final String FIGURE_NAME = "operation";  // for the creator
  // regexps:
  // operations: (\w+)\s*\( (?: \s*
  //             (inout|in|out) \s* (\w+) \s* (?: : \s* (\w+) \s*(=\s*.\s*)?)?,)* \) (?: \s* : \s* (\w*))? \s*
  private Pattern opPattern = Pattern.compile(
      "(\\w+)\\s*\\((.*)\\)(?:\\s*:\\s*(\\w*))?\\s*");
  private Pattern bodyPattern = Pattern.compile(
      "\\s*(inout|in|out)?\\s*(\\w+)\\s*(?::\\s*(\\w+)\\s*(?:=\\s*(.*?)\\s*)?)?[,)](.*)");

  private BasicNodeFigureFacet figureFacet;
  private TextableFacet textableFacet;

  public OperationFeatureTypeFacetImpl(BasicNodeFigureFacet figureFacet, TextableFacet textableFacet)
  {
    this.figureFacet = figureFacet;
    this.textableFacet = textableFacet;
  }
  
	/**
	 * @see com.giroway.jumble.umldiagrams.classdiagram.featurenode.FeatureFigure#getFeatureType()
	 */
	public int getFeatureType()
	{
		return OperationCreatorGem.FEATURE_TYPE;
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
    return UMLNodeText.makeNameFromOperation((Operation) figureFacet.getSubject(), true);
  }

  public String setText(final String text, Object listSelection)
  {
    final Operation typed = getSubject();
    
    final SubjectRepositoryFacet repository = GlobalSubjectRepository.repository; 

    // remove all current parameters
    final ArrayList<Parameter> existing = typed.undeleted_getReturnResults();
    // delete all parameters
    for (Parameter param : existing)
      repository.incrementPersistentDelete(param);

    // get the new name and type
    Matcher matcher = opPattern.matcher(text);
    if (matcher.matches())
    {
      final String newName = matcher.group(1);
      typed.setName(newName);
      
      // find or create the return parameter and type
      final String newTypeName = matcher.groupCount() >= 3 ? matcher.group(3) : null;
      if (newTypeName != null)
      {
        {
          final Type type = findOrCreateType(newTypeName);
          final Parameter newParameter = makeOperationReturn(type);
          // if the type has just been created, then it has been marked as deleted
          final boolean madeNew = type.getJ_deleted() > 0;
          
          // we've made a new type
          repository.decrementPersistentDelete(newParameter);
          if (madeNew)
            repository.decrementPersistentDelete(type);
        }
                
        // handle any new parameters
        String left = matcher.group(2) + ")"; // need the bracket as a terminator
        for (;;)
        {
          Matcher bodyMatcher = bodyPattern.matcher(left);
          if (bodyMatcher.matches())
          {
            String inout = bodyMatcher.group(1);
            String name = bodyMatcher.group(2);
            String paramTypeName = bodyMatcher.group(3);
            String defaultValue = bodyMatcher.group(4);
            
            final Type type = findOrCreateType(paramTypeName);
            final Parameter newParameter = makeOperationFormalParameter(inout, name, type, defaultValue);

            // if the type has just been created, then it has been marked as deleted
            final boolean madeNew = type.getJ_deleted() > 0;
            
            // we've made a new type
            repository.decrementPersistentDelete(newParameter);
            if (madeNew)
              repository.decrementPersistentDelete(type);
            left = bodyMatcher.group(5);  // this is the remainder
          }
          else
            break;
        }
      }
    }
    else
    	typed.setName(text);
    
    
    // resize
    String finalText = makeNameFromSubject();
    figureFacet.performResizingTransaction(textableFacet.vetTextResizedExtent(finalText));
    return finalText;
  }
  
  public String makeNameFromSubject()
  {
    return UMLNodeText.getNodeText((Element)figureFacet.getSubject());
  }

  private Operation getSubject()
  {
    return (Operation) figureFacet.getSubject();
  }

  
  private Classifier findOwningVisualElement()
  {
    SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
    return 
    	(Classifier) repository.findOwningElement(
          figureFacet.getFigureReference(), UML2Package.eINSTANCE.getClassifier());
  }

  public void performPostContainerDropTransaction()
  {
    Element subject = FeatureNodeGem.getPossibleDeltaSubject(figureFacet.getSubject());
    Classifier oldOwner = (Classifier) subject.getOwner();
       
    // work out the new owner from the visual nesting
    Classifier newOwner = findOwningVisualElement();
         
     if (newOwner != oldOwner)
     {
       if (subject instanceof DeltaReplacedConstituent)
       {
         if (oldOwner instanceof Interface)
           ((Interface) oldOwner).getDeltaReplacedOperations().remove(subject);
         else if (oldOwner instanceof Class)
           ((Class) oldOwner).getDeltaReplacedOperations().remove(subject);
         
         if (newOwner instanceof Interface)
           ((Interface) newOwner).getDeltaReplacedOperations().add(subject);
         else if (newOwner instanceof Class)
           ((Class) newOwner).getDeltaReplacedOperations().add(subject);             
       }
       else
       {
         if (oldOwner instanceof Interface)
           ((Interface) oldOwner).getOwnedOperations().remove(subject);
         else if (oldOwner instanceof Class)
           ((Class) oldOwner).getOwnedOperations().remove(subject);
         
         if (newOwner instanceof Interface)
           ((Interface) newOwner).getOwnedOperations().add(subject);
         else if (newOwner instanceof Class)
           ((Class) newOwner).getOwnedOperations().add(subject);
       }
     }
  }
  
  public Command generateDeleteDelta(ToolCoordinatorFacet coordinator, final Classifier owner)
  {
    // add this to the classifier as a delete delta
    final Element feature = FeatureNodeGem.getOriginalSubject(figureFacet.getSubject());
    
    return new AbstractCommand("Added delete delta", "Removed delete delta")
    {
      private DeltaDeletedConstituent delete;
      
      public void execute(boolean isTop)
      {
        SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
        
        // possibly resurrect
        if (delete != null)
        {
          repository.decrementPersistentDelete(delete);
        }
        else
        {
          if (owner instanceof ClassImpl)
            delete = ((Class) owner).createDeltaDeletedOperations();
          else
          if (owner instanceof InterfaceImpl)
            delete = ((Interface) owner).createDeltaDeletedOperations();
          delete.setDeleted(feature);
        }
      }

      public void unExecute()
      {
        GlobalSubjectRepository.repository.incrementPersistentDelete(delete);
      } 
    };
  }

  public JMenuItem getReplaceItem(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
  {
    // for adding operations
    JMenuItem replaceOperationItem = new JMenuItem("Replace");
    replaceOperationItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        final Operation replaced = (Operation) figureFacet.getSubject();
        final Operation original = (Operation) ClassifierConstituentHelper.getOriginalSubject(replaced);
        final FigureFacet clsFigure = ClassifierConstituentHelper.extractVisualClassifierFigureFromConstituent(figureFacet);
        final Classifier cls = (Classifier) clsFigure.getSubject();
        final DeltaReplacedOperation replacement[] = new DeltaReplacedOperation[1];
        
        Command cmd = new AbstractCommand("replaced operation", "removed replaced operation")
        {          
          public void execute(boolean isTop)
          {
          	if (replacement[0] == null)
          		replacement[0] = createDeltaReplacedOperation(cls, replaced, original);
            GlobalSubjectRepository.repository.decrementPersistentDelete(replacement[0]);
          }

          public void unExecute()
          {
            GlobalSubjectRepository.repository.incrementPersistentDelete(replacement[0]);
          }            
        };
        coordinator.executeCommandAndUpdateViews(cmd);
        
        diagramView.runWhenModificationsHaveBeenProcessed(new Runnable()
        {
          public void run()
          {
            FigureFacet createdFeature = ClassifierConstituentHelper.findSubfigure(clsFigure, replacement[0].getReplacement());
            diagramView.getSelection().clearAllSelection();
            diagramView.getSelection().addToSelection(createdFeature, true);
          }
        });
      }
    });

    return replaceOperationItem;
  }
  
  public DeltaReplacedOperation createDeltaReplacedOperation(Classifier owner, Operation replaced, Operation original)
  {     
    DeltaReplacedOperation replacement = null;
    Operation oper = null;
    
    if (owner instanceof Interface)
    {
      replacement = ((Interface) owner).createDeltaReplacedOperations();
      replacement.setReplaced(FeatureNodeGem.getOriginalSubject(original));
      oper = (Operation) replacement.createReplacement(UML2Package.eINSTANCE.getOperation());
    }
    else
    if (owner instanceof StructuredClassifier)
    {
      replacement = ((Class) owner).createDeltaReplacedOperations();
      replacement.setReplaced(FeatureNodeGem.getOriginalSubject(original));
      oper = (Operation) replacement.createReplacement(UML2Package.eINSTANCE.getOperation());
    }
    oper.setName(replaced.getName());
    oper.setVisibility(replaced.getVisibility());
    oper.setType(replaced.getType());
    
    // copy over the parameters from return results
    for (Object obj : replaced.undeleted_getReturnResults())
    {
      Parameter oldParam = (Parameter) obj;
      Parameter param = oper.createReturnResult();
      
      param.setName(oldParam.getName());
      param.setType(oldParam.getType());
      if (oldParam.undeleted_getDefaultValue() != null)
        param.setStringDefault(oldParam.getDefault());
      param.setDirection(oldParam.getDirection());
    }
    
    // delete it so we can bring it back as part of the redo command
    GlobalSubjectRepository.repository.incrementPersistentDelete(replacement);
    
    return replacement;
  }
  
  private Type getSubjectType(Operation subject)
  {
    return subject.undeleted_getType();
  }
  
  private Type findOrCreateType(String name)
  {
    SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
    java.lang.Class<?>[] types = new java.lang.Class<?>[]{
        PrimitiveTypeImpl.class, ClassImpl.class, InterfaceImpl.class };
    Type newType = (Type) repository.findElement(name, types);
    
    // if we haven't found a new type, create one, but start it as deleted...
    if (newType == null)
    {
      newType = ((Package) repository.findOwningElement((Element) figureFacet.getSubject(), Package.class)).createOwnedClass(name, false);
      repository.incrementPersistentDelete(newType);
    }
    
    return newType;
  }
  
  private Parameter makeOperationReturn(Type type)
  {
    Operation op = (Operation) figureFacet.getSubject();
    Parameter result = op.createReturnResult();
    result.setDirection(ParameterDirectionKind.RETURN_LITERAL);
    result.setType(type);
    
    // pretend to delete it so it can be resurrected easily
    GlobalSubjectRepository.repository.incrementPersistentDelete(result);
    
    return result;
  }
  
  private Parameter makeOperationFormalParameter(String inout, String name, Type type, String defaultValue)
  {
    Operation op = (Operation) figureFacet.getSubject();
    Parameter result = op.createReturnResult();
    result.setName(name);
    result.setType(type);

    // possibly set a default value
    if (defaultValue != null)
      result.setStringDefault(defaultValue);
    
    // possible in/out?
    if (inout != null)
    {
      if (inout.equals("in"))
        result.setDirection(ParameterDirectionKind.IN_LITERAL);
      if (inout.equals("out"))
        result.setDirection(ParameterDirectionKind.OUT_LITERAL);
      if (inout.equals("inout"))
        result.setDirection(ParameterDirectionKind.INOUT_LITERAL);
    }
    
    // pretend to delete it so it can be resurrected easily
    GlobalSubjectRepository.repository.incrementPersistentDelete(result);
    
    return result;
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

	public JList formSelectionList(String textSoFar)
	{
		return null;
	}
}