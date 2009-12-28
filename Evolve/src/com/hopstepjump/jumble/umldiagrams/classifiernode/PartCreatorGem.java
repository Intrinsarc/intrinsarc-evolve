package com.hopstepjump.jumble.umldiagrams.classifiernode;

import java.awt.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.repositorybase.*;

public class PartCreatorGem
{
  public static final Color INITIAL_FILL_COLOR = new Color(240, 240, 224);
  public static final String NAME = "Part";
  private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
  private boolean suppressAttributes = false;
  private boolean suppressOperations = true;
  private boolean displayOnlyIcon = false;
  
  public PartCreatorGem()
  {
  }

  public NodeCreateFacet getNodeCreateFacet()
  {
    return nodeCreateFacet;
  }

  private class NodeCreateFacetImpl implements NodeCreateFacet
  {
    public String getFigureName()
    {
      return "part";
    }
  
    public Object createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
    {
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, true, false);
      PersistentProperties actualProperties = new PersistentProperties();
      initialiseExtraProperties(actualProperties);
      ClassifierNodeGem classifierGem =
        new ClassifierNodeGem(
            (NamedElement) subject,
            diagram,
            figureId,
            INITIAL_FILL_COLOR,
            actualProperties,
            true);
      basicGem.connectBasicNodeAppearanceFacet(classifierGem.getBasicNodeAppearanceFacet());
      basicGem.connectBasicNodeContainerFacet(classifierGem.getBasicNodeContainerFacet());
      basicGem.connectBasicNodeAutoSizedFacet(classifierGem.getBasicNodeAutoSizedFacet());
      
      // connect up the clipboard facet
      PartClipboardCommandsGem clip = new PartClipboardCommandsGem();
      clip.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      basicGem.connectClipboardCommandsFacet(clip.getClipboardCommandsFacet());
      
      classifierGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());

      // add on the component appearance
      PartMiniAppearanceGem combinedGem = new PartMiniAppearanceGem();
      classifierGem.connectClassifierMiniAppearanceFacet(combinedGem.getClassifierMiniAppearanceFacet());
      combinedGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());

      ComponentMiniAppearanceGem componentGem = new ComponentMiniAppearanceGem();
      componentGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      combinedGem.connectComponentMiniAppearanceFacet(componentGem.getClassifierMiniAppearanceFacet());
        
      InterfaceMiniAppearanceGem interfaceGem = new InterfaceMiniAppearanceGem();
      interfaceGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      combinedGem.connectInterfaceMiniAppearanceFacet(interfaceGem.getClassifierMiniAppearanceFacet());
        
      diagram.add(basicGem.getBasicNodeFigureFacet());
  
      return new FigureReference(diagram, figureId);
    }
  
    public void unCreateFigure(Object memento)
    {
      FigureReference figureReference = (FigureReference) memento;
      DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(figureReference.getDiagramReference());
      FigureFacet figure = GlobalDiagramRegistry.registry.retrieveFigure(figureReference);
      diagram.remove(figure);
    }
    
    /**
     * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
     */
    public String getRecreatorName()
    {
      return NAME;
    }

    public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
    {
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
      ClassifierNodeGem classifierGem =
        new ClassifierNodeGem(INITIAL_FILL_COLOR, true, figure);
      basicGem.connectBasicNodeAppearanceFacet(classifierGem.getBasicNodeAppearanceFacet());
      basicGem.connectBasicNodeContainerFacet(classifierGem.getBasicNodeContainerFacet());
      basicGem.connectBasicNodeAutoSizedFacet(classifierGem.getBasicNodeAutoSizedFacet());
      classifierGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());

      // connect up the clipboard facet
      PartClipboardCommandsGem clip = new PartClipboardCommandsGem();
      clip.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      basicGem.connectClipboardCommandsFacet(clip.getClipboardCommandsFacet());
      
      // add on the component appearance
      PartMiniAppearanceGem combinedGem = new PartMiniAppearanceGem();
      classifierGem.connectClassifierMiniAppearanceFacet(combinedGem.getClassifierMiniAppearanceFacet());
      combinedGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());

      ComponentMiniAppearanceGem componentGem = new ComponentMiniAppearanceGem();
      componentGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      combinedGem.connectComponentMiniAppearanceFacet(componentGem.getClassifierMiniAppearanceFacet());
        
      InterfaceMiniAppearanceGem interfaceGem = new InterfaceMiniAppearanceGem();
      interfaceGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      combinedGem.connectInterfaceMiniAppearanceFacet(interfaceGem.getClassifierMiniAppearanceFacet());

      return basicGem.getBasicNodeFigureFacet();
    }

    public Object createNewSubject(Object previouslyCreated, DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

      // possibly resurrect
      if (previouslyCreated != null)
      {
        repository.decrementPersistentDelete((Element) previouslyCreated);
        return previouslyCreated;
      }

      // see if we can find the class owner
      Element possibleOwner = repository.findOwningElement(
      		containingReference,
      		new EClass[]{
      				UML2Package.eINSTANCE.getClass_(),
      				UML2Package.eINSTANCE.getProperty()});
      Class owner = null;
      if (possibleOwner instanceof Class)
        owner = (Class) possibleOwner;
      if (possibleOwner instanceof Property)
        owner = (Class) ((Property) possibleOwner).getType();
      
      // create the property with the appropriate instance specification
      Property part = owner.createOwnedAttribute();
      InstanceValue instanceValue = (InstanceValue) part.createDefaultValue(UML2Package.eINSTANCE.getInstanceValue());
      InstanceSpecification instanceSpecification = instanceValue.createOwnedAnonymousInstanceValue();
      instanceValue.setInstance(instanceSpecification);
      
      // possibly set the name
      String name = properties.retrieve("name", (String) null).asString();
      if (name != null)
        part.setName(name);
      
      // possibly set the type
      if (relatedSubject instanceof Type)
        part.setType((Type) relatedSubject);
      
      // parts have composite aggregation kind
      part.setAggregation(AggregationKind.COMPOSITE_LITERAL);
      
      return part;
    }

    public void uncreateNewSubject(Object previouslyCreated)
    {
      GlobalSubjectRepository.repository.incrementPersistentDelete((Element) previouslyCreated);
    }
    
		public void initialiseExtraProperties(PersistentProperties properties)
		{
      properties.addIfNotThere(new PersistentProperty("supA", suppressAttributes, false));
      properties.addIfNotThere(new PersistentProperty("supO", suppressOperations, false));
      properties.addIfNotThere(new PersistentProperty("icon", displayOnlyIcon, false));
			properties.addIfNotThere(new PersistentProperty("auto", false, true));
		}
  }
}