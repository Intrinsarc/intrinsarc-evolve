package com.intrinsarc.evolve.umldiagrams.requirementsfeaturenode;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;

public class RequirementsFeatureProperties
{
  private boolean atHome;
  private DEStratum perspective;
  private DEElement element;
  
  public RequirementsFeatureProperties(FigureFacet figure)
  {
  	this(figure, (Element) figure.getSubject());
  }
  
  public RequirementsFeatureProperties(FigureFacet figureToCalculatePerspectiveFrom, Element elem)
  {
  	SubjectRepositoryFacet repos = GlobalSubjectRepository.repository;
  	ContainerFacet container = container(figureToCalculatePerspectiveFrom);
  	Package pkg = repos.findVisuallyOwningPackage(figureToCalculatePerspectiveFrom.getDiagram(), container);
  	Package stratum = repos.findVisuallyOwningStratum(figureToCalculatePerspectiveFrom.getDiagram(), container);
  	if (stratum != repos.getTopLevelModel())
  		pkg = stratum;
  	
  	if (elem != null)
    {
      RequirementsFeature feature = (RequirementsFeature) elem;
      
      if (feature == null)
      	return;
      
      perspective = GlobalDeltaEngine.engine.locateObject(pkg).asStratum();
      element = GlobalDeltaEngine.engine.locateObject(feature).asElement();
      atHome = element.getHomeStratum() == perspective;
    }
  }
  
  private ContainerFacet container(FigureFacet f)
	{
  	return f.getContainedFacet().getContainer();
	}

	public boolean isAtHome()
  {
  	return atHome;
  }

	public String getPerspectiveName()
	{
		if (element == null)
			return "";
		return element.getName(perspective);
	}

	public String getSubstitutesForName()
	{
		if (element == null)
			return "";
		return element.getReplacesForName(perspective);
	}

	public DEStratum getPerspective()
	{
		return perspective;
	}

	public DEStratum getHomePackage()
	{
		return element.getHomeStratum();
	}

	public DEElement getElement()
	{
		return element;
	} 
}
