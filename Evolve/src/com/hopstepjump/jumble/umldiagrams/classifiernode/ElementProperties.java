package com.hopstepjump.jumble.umldiagrams.classifiernode;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repositorybase.*;

public class ElementProperties
{
  private boolean factory;
  private boolean leaf;
  private boolean composite;
  private boolean placeholder;
  private boolean navigable;
  private boolean blackBox;
  private boolean atHome;
  private boolean state;
  private boolean startState;
  private boolean endState;
  private ComponentKindEnum kind;
  private DEStratum perspective;
  private DEElement element;
  
  public ElementProperties(FigureFacet figure)
  {
  	this(figure, (Element) figure.getSubject());
  }
  
  public ElementProperties(FigureFacet figureToCalculatePerspectiveFrom, Element elem)
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
      
      // past this point, they are component-only properties
      if (elem instanceof Interface)
      	return;
      
      VisibilityKind visibility = classifier.getVisibility();
      if (visibility.equals(VisibilityKind.PACKAGE_LITERAL) || visibility.equals(VisibilityKind.PUBLIC_LITERAL))
        blackBox = true;
      
      if (visibility.equals(VisibilityKind.PRIVATE_LITERAL) || visibility.equals(VisibilityKind.PACKAGE_LITERAL))
        navigable = true;      
      
      DEComponent comp = element.asComponent();
      leaf = comp.isLeaf(perspective);
      composite = comp.isComposite(perspective);
      factory = comp.isFactory(perspective);
      placeholder = comp.isPlaceholder(perspective);
      kind = comp.getComponentKind();
      DEAppliedStereotype applied = comp.getAppliedStereotype(perspective);
      state = applied != null && applied.getStereotype().getUuid().equals(CommonRepositoryFunctions.STATE);
      startState = classifier.getUuid().equals(CommonRepositoryFunctions.START_STATE_CLASS);
      endState = classifier.getUuid().equals(CommonRepositoryFunctions.END_STATE_CLASS);
    }
  }
  
  public boolean isLeaf()
  {
    return leaf;
  }
  public boolean isComposite()
  {
  	return composite;
  }
  public boolean isFactory()
  {
    return factory;
  }
  public boolean isPlaceholder()
  {
    return placeholder;
  }
  public ComponentKindEnum getComponentKind()
  {
  	return kind;
  }
  public boolean isBlackBox()
  {
    return (kind == ComponentKindEnum.NORMAL || kind == ComponentKindEnum.PRIMITIVE) && blackBox;
  }
  public boolean isNavigable()
  {
    return (kind == ComponentKindEnum.NORMAL || kind == ComponentKindEnum.PRIMITIVE) && navigable;
  }
  public boolean isAtHome()
  {
  	return atHome;
  }
  public boolean isState()
  {
  	return state;
  }
  public boolean isStartState()
  {
  	return startState;
  }
  public boolean isEndState()
  {
  	return endState;
  }

	@Override
	public String toString()
	{
		return
			"ComponentProperties[kind = " + kind + ", leaf = " + leaf +
			", composite = " + composite + ", factory = " + factory +
			", placeholder = " + placeholder + ", atHome = " + atHome +
			", blackbox = " + blackBox + ", navigable = " + navigable + "]";
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

	public boolean isPrimitive()
	{
		return ((Class) element.getRepositoryObject()).getComponentKind().equals(ComponentKind.PRIMITIVE_LITERAL);
	}
}
