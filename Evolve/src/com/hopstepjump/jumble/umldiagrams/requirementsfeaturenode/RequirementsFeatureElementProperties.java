package com.hopstepjump.jumble.umldiagrams.requirementsfeaturenode;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repositorybase.*;

public class RequirementsFeatureElementProperties
{
  private boolean atHome;
  private DEStratum perspective;
  private DEElement element;
  
  public RequirementsFeatureElementProperties(FigureFacet figure)
  {
  	this(figure, (Element) figure.getSubject());
  }
  
  public RequirementsFeatureElementProperties(FigureFacet figureToCalculatePerspectiveFrom, Element elem)
  {
  	SubjectRepositoryFacet repos = GlobalSubjectRepository.repository;
  	Package pkg = repos.findVisuallyOwningPackage(figureToCalculatePerspectiveFrom.getDiagram(), figureToCalculatePerspectiveFrom.getContainerFacet());
  	Package stratum = repos.findVisuallyOwningStratum(figureToCalculatePerspectiveFrom.getDiagram(), figureToCalculatePerspectiveFrom.getContainerFacet());
  	if (stratum != repos.getTopLevelModel())
  		pkg = stratum;
  	
  	if (elem != null)
    {
      Classifier classifier = null;
      if (elem instanceof Property)
      {
        if (UMLTypes.extractInstanceOfPart(elem) != null)
          classifier = (Classifier) ((Property) elem).undeleted_getType();
      }
      else
        classifier = (Classifier) elem;
      
      if (classifier == null)
      	return;
      
      perspective = GlobalDeltaEngine.engine.locateObject(pkg).asStratum();
      element = GlobalDeltaEngine.engine.locateObject(classifier).asElement();
      atHome = element.getHomeStratum() == perspective;
    }
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
		return element.getSubstitutesForName(perspective);
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
