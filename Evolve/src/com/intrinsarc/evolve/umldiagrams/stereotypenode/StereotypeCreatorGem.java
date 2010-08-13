package com.intrinsarc.evolve.umldiagrams.stereotypenode;

import java.awt.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.intrinsarc.evolve.umldiagrams.classifiernode.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.repositorybase.*;

public class StereotypeCreatorGem implements Gem
{
	private static final Color INITIAL_FILL_COLOR = new Color(252-20, 255-20, 206-20);
	public static final String NAME = "Stereotype";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	private boolean suppressAttributes = false;
	private boolean suppressOperations = true;
	private boolean displayOnlyIcon = false;
	private boolean showStereotype = true;
	private boolean leaf = false;
	private boolean autoSized = true;
	private boolean suppressContents = true;
	
	public StereotypeCreatorGem()
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
			return "stereotype";
		}
	
    public Object createNewSubject(DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

      // nested classes live in the nested classifier link...
      Namespace owner = (Namespace) diagram.getLinkedObject();
      
      // see if we can find a better owner, based on the containing figure
      if (containingReference != null)
      {
        FigureFacet container = GlobalDiagramRegistry.registry.retrieveFigure(containingReference);
        owner = repository.findVisuallyOwningNamespace(diagram, container.getContainerFacet());
      }
      
      Stereotype cls = null;
      // get the package associated with this diagram, and add the new package to it
      if (owner instanceof Class)
      {
        cls = UML2Factory.eINSTANCE.createStereotype();
        ((Class) owner).getNestedClassifiers().add(cls);
      }
      else
        cls = (Stereotype) ((Package) owner).createOwnedMember(UML2Package.eINSTANCE.getStereotype());
      
      // add a component stereotype to it...
      Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
          UML2Package.eINSTANCE.getClass_(), "component");
      if (stereo != null)
        cls.getAppliedBasicStereotypes().add(stereo);
      
      // is this a leaf?
    	cls.setIsLeaf(properties.retrieve(">leaf", false).asBoolean());
      
      return cls;
    }

    public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
		{
			BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, true, false);
			PersistentProperties actualProperties = new PersistentProperties(properties);
			initialiseExtraProperties(actualProperties);
			ClassifierNodeGem classifierGem =
				new ClassifierNodeGem(
            diagram,
            INITIAL_FILL_COLOR,
            new PersistentFigure(figureId, null, subject, actualProperties),
            false);
			basicGem.connectBasicNodeAppearanceFacet(classifierGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(classifierGem.getBasicNodeContainerFacet());
			ClassifierClipboardActionsGem clip = new ClassifierClipboardActionsGem(false, true);
			clip.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectClipboardCommandsFacet(clip.getClipboardCommandsFacet());
			classifierGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());

      // add on the component appearance
      StereotypeMiniAppearanceGem appearanceGem = new StereotypeMiniAppearanceGem();
			classifierGem.connectClassifierMiniAppearanceFacet(appearanceGem.getClassifierMiniAppearanceFacet());
      appearanceGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
				
			diagram.add(basicGem.getBasicNodeFigureFacet());
		}
    
		/**
		 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
		 */
		public String getRecreatorName()
		{
			return NAME;
		}

		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
			BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
			ClassifierNodeGem classifierGem =
				new ClassifierNodeGem(INITIAL_FILL_COLOR, false, figure);
			basicGem.connectBasicNodeAppearanceFacet(classifierGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(classifierGem.getBasicNodeContainerFacet());
			ClassifierClipboardActionsGem clip = new ClassifierClipboardActionsGem(false, true);
			clip.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectClipboardCommandsFacet(clip.getClipboardCommandsFacet());
			classifierGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());

      // add on the component appearance
      StereotypeMiniAppearanceGem appearanceGem = new StereotypeMiniAppearanceGem();
      classifierGem.connectClassifierMiniAppearanceFacet(appearanceGem.getClassifierMiniAppearanceFacet());
      appearanceGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
        
			return basicGem.getBasicNodeFigureFacet();
		}
		
		public void initialiseExtraProperties(PersistentProperties properties)
		{
			properties.addIfNotThere(new PersistentProperty("supA", suppressAttributes, false));
			properties.addIfNotThere(new PersistentProperty("supO", suppressOperations, false));
			properties.addIfNotThere(new PersistentProperty("supC", suppressContents, false));
			properties.addIfNotThere(new PersistentProperty("auto", autoSized, true));
			properties.addIfNotThere(new PersistentProperty("icon", displayOnlyIcon, false));
      properties.addIfNotThere(new PersistentProperty("showStereo", showStereotype, true));
      properties.addIfNotThere(new PersistentProperty(">leaf", leaf, false));
		}
	}

	public void setDisplayOnlyIcon(boolean displayOnlyIcon)
	{
		this.displayOnlyIcon = displayOnlyIcon;
	}

	public void setShowStereotype(boolean showStereotype)
	{
		this.showStereotype = showStereotype;
	}

	public void setSuppressAttributes(boolean suppressAttributes)
	{
		this.suppressAttributes = suppressAttributes;
	}

	public void setSuppressOperations(boolean suppressOperations)
	{
		this.suppressOperations = suppressOperations;
	}

	public void setLeaf(boolean leaf)
	{
		this.leaf = leaf;
	}

	public void setAutoSized(boolean autoSized)
	{
		this.autoSized = autoSized;
	}
	
	public void setSuppressContents(boolean suppress)
	{
		this.suppressContents = suppress;
	}
}