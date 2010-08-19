package com.intrinsarc.evolve.umldiagrams.tracearc;

import java.awt.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.arcfacilities.arcsupport.*;
import com.intrinsarc.idraw.arcfacilities.creationbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

/**
 * @author andrew
 */
public class TraceCreatorGem implements Gem
{
	public static final ImageIcon INFO_ICON = IconLoader.loadIcon("information.png");
  public static final String NAME = "trace";
  private ArcCreateFacet arcCreateFacet = new ArcCreateFacetImpl();
  private String stereotype;
  private String stereotype2;
  private Color color;

  public TraceCreatorGem()
  {
  }

  public ArcCreateFacet getArcCreateFacet()
  {
    return arcCreateFacet;
  }
  
  private class ArcCreateFacetImpl implements ArcCreateFacet
  {
    public String getFigureName()
    {
      return NAME;
    }
  
    public void create(Object subject, DiagramFacet diagram, String figureId, ReferenceCalculatedArcPoints referencePoints, PersistentProperties properties)
    {
      // instantiate to use conventional facets
      BasicArcGem gem = new BasicArcGem(this, diagram, figureId, new CalculatedArcPoints(referencePoints));
      TraceArcGem requiredGem = new TraceArcGem(
      		new PersistentFigure(figureId, null, subject, properties));
      gem.connectBasicArcAppearanceFacet(requiredGem.getBasicArcAppearanceFacet());
	    gem.connectContainerFacet(requiredGem.getContainerFacet());
	    gem.connectAdvancedArcFacet(requiredGem.getAdvancedArcFacet());

	    TraceClipboardActionsImpl clipActions = new TraceClipboardActionsImpl();
	    clipActions.connectFigureFacet(gem.getFigureFacet());
	    gem.connectClipboardActionsFacet(clipActions);

	    requiredGem.connectFigureFacet(gem.getFigureFacet());
      
      diagram.add(gem.getFigureFacet());
    }
  
    /**
     * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
     */
    public String getRecreatorName()
    {
      return NAME;
    }

    /**
     * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
     */
    public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure pfig)
    {
      // instantiate to use conventional facets
      BasicArcGem gem = new BasicArcGem(this, diagram, pfig);
      TraceArcGem requiredGem = new TraceArcGem(pfig);
	    gem.connectContainerFacet(requiredGem.getContainerFacet());
	    gem.connectAdvancedArcFacet(requiredGem.getAdvancedArcFacet());
      requiredGem.connectFigureFacet(gem.getFigureFacet());

	    TraceClipboardActionsImpl clipActions = new TraceClipboardActionsImpl();
	    clipActions.connectFigureFacet(gem.getFigureFacet());
	    gem.connectClipboardActionsFacet(clipActions);

      gem.connectBasicArcAppearanceFacet(requiredGem.getBasicArcAppearanceFacet());

      return gem.getFigureFacet();
    }

    public void initialiseExtraProperties(PersistentProperties properties)
    {
    	properties.addIfNotThere(new PersistentProperty(">stereotype", stereotype));
      properties.addIfNotThere(new PersistentProperty(">stereotype2", stereotype2));
      if (color != null)
        properties.addIfNotThere(new PersistentProperty(">color", color, Color.BLACK));
    }
    
    public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
    {
      return acceptsOneOrBothAnchors(start, end);
    }
    
    public Object createNewSubject(DiagramFacet diagram, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties)
    {
      // make an dependency and store it in the type
      CalculatedArcPoints points = new CalculatedArcPoints(calculatedPoints);
      NamedElement client = extractDependentClient(points.getNode1().getFigureFacet().getSubject());
      Dependency dependency = client.createOwnedAnonymousDependencies();
      dependency.setTrace(true);
      
      NamedElement supplier = (NamedElement) points.getNode2().getFigureFacet().getSubject();
      
      dependency.settable_getClients().add(client);
      dependency.setDependencyTarget(getRealTarget(supplier));
      
    	// should we set a stereotype?
    	String stereoName = properties.retrieve(">stereotype", (String) null).asString();
    	if (stereoName != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getDependency(), stereoName);
        if (stereo != null)
          dependency.settable_getAppliedBasicStereotypes().add(stereo);
      }
      stereoName = properties.retrieve(">stereotype2", (String) null).asString();
      if (stereoName != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getDependency(), stereoName);
        if (stereo != null)
          dependency.settable_getAppliedBasicStereotypes().add(stereo);
      }
      
      return dependency;
    }

		public void aboutToMakeTransaction(ToolCoordinatorFacet coordinator)
		{      
		}
  }
  
  public static NamedElement extractDependentClient(Object subject)
	{
  	// special case -- if this is a port, dig into the type
  	if (subject instanceof Port)
  		return ((Port) subject).getType();
  	
  	// else if this is a named element, attach directly
  	if (subject instanceof NamedElement)
  		return (NamedElement) subject;
  	return null; // can't have a dependency from
	}

	public void setStereotype(String stereotype)
	{
		this.stereotype = stereotype;
	}

  public void setStereotype2(String stereotype)
  {
    this.stereotype2 = stereotype;
  }
  
  public void setColor(Color color)
  {
    this.color = color;
  }

  public static boolean acceptsOneOrBothAnchors(AnchorFacet start, AnchorFacet end)
  {
    // end must be something sensible, and cannot be circular
    boolean startReadOnly = start.getFigureFacet().isSubjectReadOnlyInDiagramContext(false);
    Object startSubject = start.getFigureFacet().getSubject();
    boolean startOk = extractDependentClient(startSubject) != null && !startReadOnly;
    startOk &= startSubject instanceof Class;
    if (end == null)
      return startOk;
    return
      startOk &&
      end.getFigureFacet().getSubject() instanceof RequirementsFeature && end.getFigureFacet().getSubject() != start.getFigureFacet().getSubject();
	}

	public static NamedElement getRealTarget(NamedElement element)
  {
    // change the owner, but make sure we take the original
    DEElement elem = GlobalDeltaEngine.engine.locateObject(element).asElement().getReplacesOrSelf().iterator().next();
    return (NamedElement) elem.getRepositoryObject();  	
  }
}
