package com.hopstepjump.jumble.umldiagrams.portnode;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.repositorybase.*;

/**
 * @author Andrew
 */
public final class PortCreatorGem implements Gem
{
	public static final String NAME = "Port";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	private PortKind portKind = PortKind.NORMAL_LITERAL;
	private String stereotype;
	
	public PortCreatorGem()
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
	    return PortNodeGem.FIGURE_NAME;
	  }
	 
	  public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, false, true);
	  	PortNodeGem portNodeGem =
	  		new PortNodeGem(
	  				diagram,
	  				location,
	  				true,
	  				new PersistentFigure(figureId, null, subject, properties),
	  				false);
			basicGem.connectBasicNodeAppearanceFacet(portNodeGem.getBasicNodeAppearanceFacet());
			portNodeGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
	    basicGem.connectClipboardCommandsFacet(portNodeGem.getClipboardCommandsFacet());
			basicGem.connectBasicNodeContainerFacet(portNodeGem.getBasicNodeContainerFacet());
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
		public FigureFacet createFigure(
			DiagramFacet diagram, PersistentFigure figure)
		{
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, true);
	  	PortNodeGem portNodeGem = new PortNodeGem(figure, false);
			basicGem.connectBasicNodeAppearanceFacet(portNodeGem.getBasicNodeAppearanceFacet());
			portNodeGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
	    basicGem.connectClipboardCommandsFacet(portNodeGem.getClipboardCommandsFacet());
			basicGem.connectBasicNodeContainerFacet(portNodeGem.getBasicNodeContainerFacet());

			return basicGem.getBasicNodeFigureFacet();
		}

    public Object createNewSubject(DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
	    SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

	    // get the class this is a part of and create a new port
      FigureFacet container = GlobalDiagramRegistry.registry.retrieveFigure(containingReference);
      Class owner = (Class) repository.findVisuallyOwningNamespace(diagram, container.getContainerFacet());
      
      // create an embedded port type
      Port port = owner.createOwnedPort();
	    Type type = port.createOwnedAnonymousType(UML2Package.eINSTANCE.getClass_());
      type.setName("(port type)");
	    port.setType(type);
      
      // handle optional name
      if (properties != null)
      {
        PersistentProperty name = properties.retrieve("name", (String) null);
        if (name != null)
          PortNodeGem.setNameAndMultiplicity(port, name.asString());
        
        // handle "optional" [0..1] multiplicity
        boolean optional = properties.retrieve("optional", false).asBoolean();
        if (optional)
        {
          port.setLowerBound(0);
          port.setUpperBound(1);
        }
      }
      
      // set the kind
      PersistentProperty k =
      	properties == null ? new PersistentProperty(">kind", 0, 0) : properties.retrieve(">kind", 0);
      PortKind kind = PortKind.get(k.asInteger());
      port.setKind(kind);
      
    	// should we set a stereotype?
    	String stereoName = properties.retrieve(">stereotype", (String) null).asString();
    	if (stereoName != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getPort(), stereoName);
        if (stereo != null)
          port.getAppliedBasicStereotypes().add(stereo);
      }      
      
	    return port;
	  }
    
		public void initialiseExtraProperties(PersistentProperties properties)
		{
			properties.addIfNotThere(new PersistentProperty(">kind", portKind.getValue(), 0));
			properties.addIfNotThere(new PersistentProperty(">stereotype", stereotype));
		}
	}
	
	public void setPortKind(PortKind portKind)
	{
		this.portKind = portKind;
	}
	
	public void setStereotype(String stereo)
	{
		this.stereotype = stereo;
	}
}
