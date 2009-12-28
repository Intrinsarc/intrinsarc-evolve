package com.hopstepjump.jumble.umldiagrams.base;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.constituenthelpers.*;
import com.hopstepjump.repositorybase.*;

public abstract class ClassifierConstituentHelper
{
  private BasicNodeFigureFacet figure;
  /** this is what the figure is added to.  if it is null, then the diagram is used; e.g. for connectors */
  private FigureFacet container;
  /** should we be showing these constituents */
  private boolean showing;
  /** the figures that are currently visible */
  private Set<FigureFacet> currentlyDisplayed;
  private boolean top;
  private ConstituentTypeEnum type;
  private SimpleDeletedUuidsFacet deleted;

  public ClassifierConstituentHelper(
      BasicNodeFigureFacet classFigure,
      FigureFacet container,
      boolean showing,
      Iterator<FigureFacet> figures,
      ConstituentTypeEnum type,
      SimpleDeletedUuidsFacet deleted,
      boolean top)
  {
    this.figure = classFigure;
    this.container = container;
    this.showing = showing;
    this.currentlyDisplayed = getCurrentlyDisplayed(figures);
    this.top = top;
    this.deleted = deleted;
    this.type = type;
  }

  /**
   * add the figure to the display
   * @param perspective 
   * @param currentInContainerIgnoringDeletes
   * @param classifierFigure 
   * @param containerReference
   * @param object
   * @param pair 
   * @param top 
   * @return
   */
  public abstract Command makeAddCommand(
      DEStratum perspective,
      Set<FigureFacet> currentInContainerIgnoringDeletes,
      BasicNodeFigureFacet classifierFigure,
      FigureFacet container,
      DeltaPair addOrReplace,
      boolean top);
  
  public boolean isShowingAllConstituents()
  {
    Set<DeltaPair> constituents = getConstituents(getPerspective(), type);
    for (DeltaPair pair : constituents)
    {
      if (!containedWithin(currentlyDisplayed, pair))
        return false;
    }
    return true;
  }
  
  public Map<String, String> getHiddenConstituents()
  {
  	Map<String /* UUID */, String /* name */> hidden = new LinkedHashMap<String, String>();
  	
    Set<DeltaPair> constituents = getConstituents(getPerspective(), type);
    
    for (DeltaPair pair : constituents)
    {
      if (!containedWithin(currentlyDisplayed, pair))
      	hidden.put(pair.getConstituent().getUuid(), pair.getConstituent().getName());
    }
    return hidden;
  }
  
  public Set<String> getConstituentUuids()
  {
    Set<String> uuids = new HashSet<String>();
    for (DeltaPair pair : getConstituents(getPerspective(), type))
      uuids.add(pair.getUuid());
    return uuids;
  }
  
  public Set<String> getConstituentUuids(ConstituentTypeEnum type)
  {
    Set<String> uuids = new HashSet<String>();
    for (DeltaPair pair : getConstituents(getPerspective(), type))
      uuids.add(pair.getUuid());
    return uuids;
  }
  
  /**
   * work out the perspective of this classifier
   * @return
   */
  public DEStratum getPerspective()
  {
  	return PerspectiveHelper.extractDEStratum(figure.getDiagram(), figure.getContainedFacet().getContainer());
  }
  
  public CompositeCommand makeUpdateCommand(boolean locked)
  {
    // if the container isn't visible, don't bother
    if (!showing)
      return null;
    
    IDeltaEngine engine = GlobalDeltaEngine.engine;

    // work out the perspective
    DEStratum perspective = getPerspective();    
    Set<DeltaPair> constituents = getConstituents(perspective, type);
    
    // work out what we need to delete
    CompositeCommand cmd = new CompositeCommand("", "");
    List<DEObject> existing = new ArrayList<DEObject>();
    
    // delete if this shouldn't be here
    Set<String> suppressed = getVisuallySuppressed(perspective, GlobalDeltaEngine.engine.locateObject(figure.getSubject()).asElement(), type);
    Set<DEObject> visualProblems = new HashSet<DEObject>();
    for (FigureFacet f : currentlyDisplayed)
    {
      // don't delete if this is deleted -- this is covered elsewhere
      Element subject = (Element) f.getSubject();
      
      if (subject != null && !subject.isThisDeleted())
      {
        DEObject constituent = engine.locateObject(subject);
        boolean visualProblem = hasVisualProblem(figure, f, constituent.asConstituent()); 
        if (deleted.isDeleted(suppressed, constituent.getUuid()) ||
        		!containedWithin(constituents, constituent) ||
        		existing.contains(constituent) ||
        		visualProblem)
        {
          cmd.addCommand(f.formDeleteCommand());
          if (visualProblem)
          	visualProblems.add(constituent);
        }
        else
          existing.add(constituent);
      }
    }
     
    // work out what we need to add
    if (!locked)
	    for (DeltaPair pair : constituents)
	    {
	      if ((visualProblems.contains(pair.getConstituent()) ||
	      		 !containedWithin(currentlyDisplayed, pair)) && !deleted.isDeleted(suppressed, pair.getConstituent().getUuid()))
	      {
	        cmd.addCommand(
	            makeAddCommand(
	                perspective,
	                currentlyDisplayed,
	                figure,
	                container,
	                pair,
	                top));
	      }
	    }
    
    return cmd;
  }
  
  protected boolean hasVisualProblem(BasicNodeFigureFacet container, FigureFacet sub, DEConstituent constituent)
	{
		return false;
	}

	public static Set<String> getVisuallySuppressed(DEStratum perspective, DEElement element, ConstituentTypeEnum type)
  {
  	// don't bother if the element is null
  	if (element == null)
  		return new HashSet<String>();
  	
	  // work out what is suppressed
		Set<String> suppressed = new HashSet<String>();
		for (DeltaPair pair : element.getDeltas(type).getConstituents(perspective))
		{
			DEConstituent cons = pair.getConstituent();
			if (isVisuallySuppressed(cons))
				suppressed.add(pair.getConstituent().getUuid());
		}
		return suppressed;
  }
  
	private static boolean isVisuallySuppressed(DEConstituent cons)
	{
		for (DEAppliedStereotype applied : cons.getAppliedStereotypes())
			if (applied.getStereotype().getUuid().equals(CommonRepositoryFunctions.VISUALLY_SUPPRESS))
				return true;
		return false;
	}

	private Set<FigureFacet> getCurrentlyDisplayed(Iterator<FigureFacet> figures)
  {
    Set<FigureFacet> current = new HashSet<FigureFacet>();
    for (Iterator<FigureFacet> iter = figures; iter.hasNext();)
      current.add(iter.next());
    return current;
  }

  private Set<DeltaPair> getConstituents(DEStratum perspective, ConstituentTypeEnum type)
  {
    IDeltaEngine engine = GlobalDeltaEngine.engine;
    Classifier classifier = (Classifier) figure.getSubject();
    
    // get the deltas for this perspective
    DEObject object = engine.locateObject(classifier);
    if (object == null)
      return new HashSet<DeltaPair>();
    DEElement element = object.asElement();
    
    IDeltas deltas = element.getDeltas(type);
    
    Set<DeltaPair> pairs = new LinkedHashSet<DeltaPair>();
    for (DeltaPair pair : deltas.getConstituents(perspective, true))
  		pairs.add(pair);
    
    return pairs;
  }

  private boolean containedWithin(Set<FigureFacet> current, DeltaPair pair)
  {
    IDeltaEngine engine = GlobalDeltaEngine.engine;

    for (FigureFacet figure : current)
    {
      if (figure.getSubject() != null)
      {
        DEObject constituent = engine.locateObject(figure.getSubject());
        if (pair.getConstituent() == constituent)
          return true;
      }
    }
    return false;
  }

  private boolean reportContainedWithin(Set<FigureFacet> current, DeltaPair pair)
  {
    IDeltaEngine engine = GlobalDeltaEngine.engine;

    String found = "";
    for (FigureFacet figure : current)
    {
      if (figure.getSubject() != null)
      {
        DEObject constituent = engine.locateObject(figure.getSubject());
        if (pair.getConstituent() == constituent)
          return true;
        found += constituent.getName() + " ";
      }
    }
    return false;
  }

  private boolean containedWithin(Set<DeltaPair> constituents, DEObject constituent)
  {
    for (DeltaPair pair : constituents)
      if (pair.getConstituent() == constituent)
        return true;
    return false;
  }
  
  public void cleanUuids()
  {
  	deleted.clean(getVisuallySuppressed(getPerspective(), GlobalDeltaEngine.engine.locateObject(figure.getSubject()).asElement(), type), getConstituentUuids());
  }

	public void cleanUuids(ConstituentTypeEnum extraType)
	{
		DEStratum perspective = getPerspective();
		DEElement element = GlobalDeltaEngine.engine.locateObject(figure.getSubject()).asElement();
    Set<String> visual = getVisuallySuppressed(perspective, element, type);
    visual.addAll(getVisuallySuppressed(perspective, element, extraType));
    Set<String> uuids = getConstituentUuids();
    uuids.addAll(getConstituentUuids(extraType));
		deleted.clean(visual, uuids);
	}

	public static FigureFacet lookForFigure(FigureFacet figure, Object containingSubject, DEPort port, boolean found)
  {
    Element subject = (Element) figure.getSubject();
    
    if (port == null)
    	return null;
    boolean oldFound = found;
    if (subject == containingSubject)
      found = true;
    
    if (figure.getSubject() == port.getRepositoryObject() && found)
      return figure;
    
    // look through each child in turn, but only if we haven't found the subject yet
    if (!oldFound || subject == null)
	    if (figure.getContainerFacet() != null)
	    {
	      for (Iterator<FigureFacet> child = figure.getContainerFacet().getContents(); child.hasNext();)
	      {
	        FigureFacet childFigure = lookForFigure(child.next(), containingSubject, port, found);
	        if (childFigure != null)
	          return childFigure;
	      }
	    }

    // if we get here, we have lost :-(
    return null;
  }

  public static FigureFacet extractVisualClassifierFigureFromConnector(FigureFacet linking)
  {
    // start with an anchor, and look upwards to a class-like figure
    FigureFacet figure = linking.getLinkingFacet().getAnchor1().getFigureFacet();
    
    // traverse up the hierarchy until we find a classifier subject
    for (;;)
    {
      if (figure.getSubject() != null && figure.getSubject() instanceof Classifier)
        return figure;
      
      if (figure.getContainedFacet() == null)
        break;
      ContainerFacet container = figure.getContainedFacet().getContainer();
      if (container == null)
        break;
      figure = container.getFigureFacet();
    }
    // if we get here, no classifier was found
    return null;
  }

  public static Class extractVisualClassifierFromConnector(FigureFacet linking)
  {
  	FigureFacet f = extractVisualClassifierFigureFromConnector(linking);
  	// this is a hack -- i don't know why it is nill sometimes
  	if (f == null)
  		return null;
    return (Class) f.getSubject();
  }

  
  public static FigureFacet extractVisualClassifierFigureFromConstituent(FigureFacet constituent)
  {
    // travel up until we find a classifier
    for (;;)
    {
      if (constituent == null)
        return null;
      if (constituent.getSubject() instanceof Classifier)
        return constituent;
      if (constituent.getContainedFacet() == null || constituent.getContainedFacet().getContainer() == null)
        return null;
      constituent = constituent.getContainedFacet().getContainer().getFigureFacet();
    }
  }

  public static Classifier extractVisualClassifierFromConstituent(FigureFacet constituent)
  {
    return (Classifier) extractVisualClassifierFigureFromConstituent(constituent).getSubject();
  }  
  
  /** return the replaced element if this is a replacement, otherwise just return the element */
  public static Element getOriginalSubject(Object subject)
  {
   Element constituent = (Element) subject;
    if (constituent.getOwner() instanceof DeltaReplacedConstituent)
      return ((DeltaReplacedConstituent) constituent.getOwner()).getReplaced();
    return constituent;
  }
  
  public static Element getPossibleDeltaSubject(Object subject)
  {
    Element element = (Element) subject;
    if (element.getOwner() instanceof DeltaReplacedConstituent)
      return element.getOwner();
    return element;
  }
  
  /**
   * look through the subfigures until we find one with the correct subject
   * @param top
   * @param subject
   * @return
   */
  public static FigureFacet findSubfigure(FigureFacet top, Object subject)
  {
    if (top.getSubject() == subject)
      return top;
    
    // now consider contained subfigures
    if (top.getContainerFacet() != null)
      for (Iterator<FigureFacet> iter = top.getContainerFacet().getContents(); iter.hasNext();)
      {
        FigureFacet child = findSubfigure(iter.next(), subject);
        if (child != null)
          return child;
      }
    
    // consider any links also
    if (top.getAnchorFacet() != null)
      for (Iterator<LinkingFacet> iter = top.getAnchorFacet().getLinks(); iter.hasNext();)
      {
        FigureFacet child = findSubfigure(iter.next().getFigureFacet(), subject);
        if (child != null)
          return child;
      }
    
    return null;
  }
}
