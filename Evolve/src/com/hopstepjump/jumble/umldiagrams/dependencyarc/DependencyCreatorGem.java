package com.hopstepjump.jumble.umldiagrams.dependencyarc;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.arcfacilities.creationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;

/**
 * @author andrew
 */
public class DependencyCreatorGem implements Gem
{
	public static final ImageIcon INFO_ICON = IconLoader.loadIcon("information.png");
  public static final String NAME = "dependency";
  private ArcCreateFacet arcCreateFacet = new ArcCreateFacetImpl();
  private String stereotype;
  private String stereotype2;
  private Color color;
  private boolean resembles;
  private boolean substitutes;

  public DependencyCreatorGem()
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
      return DependencyArcGem.FIGURE_NAME;
    }
  
    public Object create(Object subject, DiagramFacet diagram, String figureId, ReferenceCalculatedArcPoints referencePoints, PersistentProperties properties)
    {
      // instantiate to use conventional facets
      BasicArcGem gem = new BasicArcGem(this, diagram, figureId, new CalculatedArcPoints(referencePoints));
      DependencyArcGem requiredGem = new DependencyArcGem(
      		new PersistentFigure(figureId, null, subject, properties));
      gem.connectBasicArcAppearanceFacet(requiredGem.getBasicArcAppearanceFacet());
      requiredGem.connectCurvableFacet(gem.getCurvableFacet());
	    gem.connectContainerFacet(requiredGem.getContainerFacet());
	    gem.connectAdvancedArcFacet(requiredGem.getAdvancedArcFacet());
	    requiredGem.connectFigureFacet(gem.getFigureFacet());
      
      diagram.add(gem.getFigureFacet());
      
      return new FigureReference(diagram, figureId);
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
    public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure pfig)
    {
      // instantiate to use conventional facets
      BasicArcGem gem = new BasicArcGem(this, diagram, pfig);
      DependencyArcGem requiredGem = new DependencyArcGem(pfig);
      requiredGem.connectCurvableFacet(gem.getCurvableFacet());
	    gem.connectContainerFacet(requiredGem.getContainerFacet());
	    gem.connectAdvancedArcFacet(requiredGem.getAdvancedArcFacet());
      requiredGem.connectFigureFacet(gem.getFigureFacet());
      
      gem.connectBasicArcAppearanceFacet(requiredGem.getBasicArcAppearanceFacet());

      return gem.getFigureFacet();
    }

    public void initialiseExtraProperties(PersistentProperties properties)
    {
    	properties.addIfNotThere(new PersistentProperty(">stereotype", stereotype));
      properties.addIfNotThere(new PersistentProperty(">stereotype2", stereotype2));
      properties.addIfNotThere(new PersistentProperty(">substitutes", substitutes, false));
      properties.addIfNotThere(new PersistentProperty(">resembles", resembles, false));
      if (color != null)
        properties.addIfNotThere(new PersistentProperty(">color", color, Color.BLACK));
    }
    
    public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
    {
      return acceptsOneOrBothAnchors(start, end);
    }
    
    public Object createNewSubject(DiagramFacet diagram, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

      // make an dependency and store it in the type
      CalculatedArcPoints points = new CalculatedArcPoints(calculatedPoints);
      NamedElement client = extractDependentClient(points.getNode1().getFigureFacet().getSubject());
      Dependency dependency = client.createOwnedAnonymousDependencies();

      // important to set resembles or substitutes before adding the target, as this triggers the backlink 
      boolean resembles = properties.retrieve(">resembles", false).asBoolean();
      if (resembles)
        dependency.setResemblance(true);
      boolean substitutes = properties.retrieve(">substitutes", false).asBoolean();
      if (substitutes)
        dependency.setReplacement(true);
      
      // add to the list of required interfaces, if it isn't there already
      NamedElement supplier = (NamedElement) points.getNode2().getFigureFacet().getSubject();
      
      dependency.settable_getClients().add(client);
      dependency.setDependencyTarget(supplier);
      
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
      
      // remove any stereotypes of the target
      List<Stereotype> stereos = null;
      List<AppliedBasicStereotypeValue> stereoValues = null;
      if (resembles)
      {
        stereos = client.undeleted_getAppliedBasicStereotypes();
        if (!stereos.isEmpty())
        	client.settable_getAppliedBasicStereotypes().clear();
        stereoValues = new ArrayList<AppliedBasicStereotypeValue>();
        for (Object obj : client.undeleted_getAppliedBasicStereotypes())
        {
        	repository.incrementPersistentDelete((Element) obj);
        	stereoValues.add((AppliedBasicStereotypeValue) obj);
        }
      }

      return dependency;
    }

		public void aboutToMakeTransaction(ToolCoordinatorFacet coordinator)
		{      
      // if this is resemblance, indicate we have cleared the stereotype of the target
      if (resembles)
      	coordinator.displayPopup(
      			INFO_ICON,
      			"Removed stereotype from resemblance target",
      			null,
						ScreenProperties.getUndoPopupColor(),
						Color.black, 1500);      
		}
		
		public Object extractRawSubject(Object previouslyCreated)
		{
			return ((Object[]) previouslyCreated)[0];
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

  public void setResembles(boolean resembles)
  {
    this.resembles = resembles;
  }

  public void setSubstitution(boolean substitutes)
  {
    this.substitutes = substitutes;
  }

  public static boolean acceptsOneOrBothAnchors(AnchorFacet start, AnchorFacet end)
  {
    // end must be something sensible, and cannot be circular
    boolean startReadOnly = start.getFigureFacet().isSubjectReadOnlyInDiagramContext(false);
    boolean startOk = extractDependentClient(start.getFigureFacet().getSubject()) != null && !startReadOnly;
    if (end == null)
      return startOk;
    return
      startOk &&
      end.getFigureFacet().getSubject() instanceof NamedElement && end.getFigureFacet().getSubject() != start.getFigureFacet().getSubject();
  }
}
