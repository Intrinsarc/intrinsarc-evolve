package com.hopstepjump.jumble.umldiagrams.classifiernode;

import java.awt.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.repositorybase.*;

/**
 *
 * (c) Andrew McVeigh 04-Sep-02
 *
 */
public class InterfaceCreatorGem implements Gem
{
  /** possible styles for the links and the interface */
  public static final String LINK_STYLE_DIRECT       = "LINK_STYLE_DIRECT";       // just a direct link
  public static final String LINK_STYLE_DIRECT_LARGE = "LINK_STYLE_DIRECT_LARGE"; // a direct link, but the anchor is invisible 
  
  public static final String NAME = "Interface";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	private boolean suppressAttributes;
	private boolean suppressOperations;
	private boolean displayOnlyIcon;
	private String stereotype;
  private String stereotype2;
  private boolean autoSized;
	
	public InterfaceCreatorGem()
	{
		this.suppressAttributes = true;
		this.displayOnlyIcon = true;
	}
	
	public NodeCreateFacet getNodeCreateFacet()
	{
		return nodeCreateFacet;
	}

	private class NodeCreateFacetImpl implements NodeCreateFacet
	{
		public String getFigureName()
		{
			return "Interface";
		}
	
    public Object createNewSubject(DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

      Namespace owner = (Namespace) diagram.getLinkedObject();
      
      // see if we can find a better owner, based on the containing figure
      if (containingReference != null)
      {
        FigureFacet container = GlobalDiagramRegistry.registry.retrieveFigure(containingReference);
        owner = repository.findVisuallyOwningNamespace(diagram, container.getContainerFacet());
      }
      
      Interface iface = null;
      // get the package associated with this diagram, and add the new package to it
      if (owner instanceof Class)
      {
        iface = UML2Factory.eINSTANCE.createInterface();
        ((Class) owner).getNestedClassifiers().add(iface);
      }
      else
        iface = (Interface) ((Package) owner).createOwnedMember(UML2Package.eINSTANCE.getInterface());
      
    	// should we set a stereotype?
    	String stereoName = properties.retrieve(">stereotype").asString();
    	if (stereoName != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getInterface(), stereoName);
        if (stereo != null)
        	iface.getAppliedBasicStereotypes().add(stereo);
      }
      
      // should we set a second stereotype?
      String stereoName2 = properties.retrieve(">stereotype2", (String) null).asString();
      if (stereoName2 != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getClass_(), stereoName2);
        if (stereo != null)
          iface.getAppliedBasicStereotypes().add(stereo);
      }

      return iface;
    }

    public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
		{
      InterfaceMiniAppearanceGem miniAppearanceGem = new InterfaceMiniAppearanceGem();
      ClassifierMiniAppearanceFacet miniAppearanceFacet =
        miniAppearanceGem.getClassifierMiniAppearanceFacet();
      PersistentProperties actualProperties = new PersistentProperties(properties);
      initialiseExtraProperties(actualProperties);

      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, true, false);
			ClassifierNodeGem classifierGem =
				new ClassifierNodeGem(
            (Interface) subject,
            diagram,
            figureId,
            Color.WHITE,
            actualProperties,
            false);
			basicGem.connectBasicNodeAppearanceFacet(classifierGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(classifierGem.getBasicNodeContainerFacet());
			ClassifierClipboardCommandsGem clip = new ClassifierClipboardCommandsGem(true, false);
			clip.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectClipboardCommandsFacet(clip.getClipboardCommandsFacet());
			classifierGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			classifierGem.connectClassifierMiniAppearanceFacet(miniAppearanceFacet);
      miniAppearanceGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
				
			diagram.add(basicGem.getBasicNodeFigureFacet());
		}
    
		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
		 */
		public String getRecreatorName()
		{
			return NAME;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
		 */
		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
      InterfaceMiniAppearanceGem miniAppearanceGem = new InterfaceMiniAppearanceGem();
      ClassifierMiniAppearanceFacet miniAppearanceFacet =
        miniAppearanceGem.getClassifierMiniAppearanceFacet(); 

			BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
			ClassifierNodeGem classifierGem =
				new ClassifierNodeGem(Color.WHITE, false, figure);
			basicGem.connectBasicNodeAppearanceFacet(classifierGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(classifierGem.getBasicNodeContainerFacet());
			ClassifierClipboardCommandsGem clip = new ClassifierClipboardCommandsGem(true, false);
			clip.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectClipboardCommandsFacet(clip.getClipboardCommandsFacet());
			classifierGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			classifierGem.connectClassifierMiniAppearanceFacet(miniAppearanceFacet);
      miniAppearanceGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
			
			return basicGem.getBasicNodeFigureFacet();
		}
		
		public void initialiseExtraProperties(PersistentProperties properties)
		{
      properties.addIfNotThere(new PersistentProperty("supA", suppressAttributes, false));
      properties.addIfNotThere(new PersistentProperty("supO", suppressOperations, false));
      properties.addIfNotThere(new PersistentProperty("icon", displayOnlyIcon, false));
      properties.addIfNotThere(new PersistentProperty(">stereotype", stereotype));
      properties.addIfNotThere(new PersistentProperty(">stereotype", stereotype2));
      properties.addIfNotThere(new PersistentProperty("auto", autoSized, true));
		}
	}

	public void setStereotype(String stereotype)
	{
		this.stereotype = stereotype;
	}

  public void setStereotype2(String stereotype2)
  {
    this.stereotype2 = stereotype2;
  }

  public void setDisplayOnlyIcon(boolean displayOnlyIcon)
  {
    this.displayOnlyIcon = displayOnlyIcon;
  }
  
  public void setSuppressOperations(boolean suppressOperations)
  {
    this.suppressOperations = suppressOperations;
  }

  public void setSuppressAttributes(boolean suppressAttributes)
  {
    this.suppressAttributes = suppressAttributes;
  }

  public void setAutosized(boolean autoSized)
  {
    this.autoSized = autoSized;
  }
}