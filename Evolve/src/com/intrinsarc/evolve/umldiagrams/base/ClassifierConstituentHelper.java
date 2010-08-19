package com.intrinsarc.evolve.umldiagrams.base;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.packageview.base.*;
import com.intrinsarc.evolve.umldiagrams.constituenthelpers.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.diagramsupport.moveandresize.*;
import com.intrinsarc.idraw.figures.simplecontainernode.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.uml2deltaengine.*;

public abstract class ClassifierConstituentHelper
{
  private BasicNodeFigureFacet figure;
  /** this is what the figure is added to.  if it is null, then the diagram is used; e.g. for connectors */
  private FigureFacet container;
  /** should we be showing these constituents */
  private boolean showing;
  /** the figures that are currently visible */
  private Set<FigureFacet> currentlyDisplayed;
  private ConstituentTypeEnum type;
  private SimpleDeletedUuidsFacet deleted;

  public ClassifierConstituentHelper(
      BasicNodeFigureFacet classFigure,
      FigureFacet container,
      boolean showing,
      Iterator<FigureFacet> figures,
      ConstituentTypeEnum type,
      SimpleDeletedUuidsFacet deleted)
  {
    this.figure = classFigure;
    this.container = container;
    this.showing = showing;
    this.currentlyDisplayed = getCurrentlyDisplayed(figures);
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
  public abstract void makeAddTransaction(
      DEStratum perspective,
      Set<FigureFacet> currentInContainerIgnoringDeletes,
      BasicNodeFigureFacet classifierFigure,
      FigureFacet container,
      DeltaPair addOrReplace);
  
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
  	return getHiddenConstituents(null);
  }
  
  public Map<String, String> getHiddenConstituents(IConstituentPrinter printer)
  {
  	Map<String /* UUID */, String /* name */> hidden = new LinkedHashMap<String, String>();
  	
    Set<DeltaPair> constituents = getConstituents(getPerspective(), type);
    
    for (DeltaPair pair : constituents)
    {
      if (!containedWithin(currentlyDisplayed, pair))
      	hidden.put(
      			pair.getConstituent().getUuid(),
      			printer == null ? pair.getConstituent().getName() : printer.asString(pair.getConstituent()));
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
  
  public void makeUpdateCommand(boolean locked)
  {
    // if the container isn't visible, don't bother
    if (!showing)
      return;
    
    IDeltaEngine engine = GlobalDeltaEngine.engine;

    // work out the perspective
    DEStratum perspective = getPerspective();    
    Set<DeltaPair> constituents = getConstituents(perspective, type);
    
    // work out what we need to delete
    List<DEObject> existing = new ArrayList<DEObject>();
    
    // delete if this shouldn't be here
    Set<String> suppressed = getVisuallySuppressed(perspective, GlobalDeltaEngine.engine.locateObject(figure.getSubject()).asElement(), type);
    Set<DEObject> visualProblems = new HashSet<DEObject>();

    // work out what we need to add
    for (DeltaPair pair : constituents)
    {
      if ((visualProblems.contains(pair.getConstituent()) || !containedWithin(currentlyDisplayed, pair))
      		&& !deleted.isDeleted(suppressed, pair.getConstituent().getUuid()))
      {
      	FigureFacet toReplace = willReplaceCurrentlyDisplayed(currentlyDisplayed, pair);
      	if (toReplace != null)
      	{
      		// replace in-situ even if locked -- we are already displaying it after all...
					PersistentFigure pfig = toReplace.makePersistentFigure();
					pfig.setSubject(pair.getConstituent().getRepositoryObject());
					toReplace.acceptPersistentFigure(pfig);
          toReplace.getDiagram().forceAdjust(toReplace);
      	}
      	else
      	if (!locked)
          makeAddTransaction(
              perspective,
              currentlyDisplayed,
              figure,
              container,
              pair);
      }
    }

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
          f.formDeleteTransaction();
          if (visualProblem)
          	visualProblems.add(constituent);
        }
        else
          existing.add(constituent);
      }
    }
  }
  
	/**
	 * find something in the currently displayed set that will be replaced by the pair
	 * prevents visual locked things from disappearing when another view has replaced a displayed constituent 
	 */
  public static FigureFacet willReplaceCurrentlyDisplayed(Set<FigureFacet> currentlyDisplayed, DeltaPair pair)
	{
  	// NOTE: doesn't always find the thing to replace.  e.g. if we are displaying a replacement and we
  	// delete the replacement, it will not have a chance to revert the current port/port instance etc back
  	// to the original, as the replacement deletion will force the deletion of the figure first...
  	// AMcVeigh, 29th July 2010
		String originalUuid = pair.getOriginal().getUuid();
  	for (FigureFacet f : currentlyDisplayed)
  	{
  		Element subject = (Element) f.getSubject();
  		String uuid = subject.getUuid();
  		if (subject.getOwner() instanceof DeltaReplacedConstituent)
  			uuid = ((DeltaReplacedConstituent) subject.getOwner()).getReplaced().getUuid();
  		
  		if (uuid.equals(originalUuid))
  			return f;
  	}
  	return null;
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
    return new LinkedHashSet<DeltaPair>(deltas.getConstituents(perspective, true));
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

  private boolean containedWithin(Set<DeltaPair> constituents, DEObject constituent)
  {
    for (DeltaPair pair : constituents)
      if (pair.getConstituent() == constituent)
        return true;
    return false;
  }
  
  public void addDeletedUuid(String uuid)
  {
  	deleted.addDeleted(uuid);
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
  	// this is a hack -- i don't know why it is null sometimes
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
  
  /**
   * look through the top level
   * @param top
   * @param subject
   * @return
   */
  public static FigureFacet findArc(DiagramFacet diagram, Object subject)
  {
  	for (FigureFacet figure : diagram.getFigures())
  		if (figure.getSubject() == subject && figure.getContainedFacet() == null)
  			return figure;
  	return null;
  }
  
	////////////////////////////////////////////////////
	
	private static FigureFacet extractClassFigureFromConstituent(FigureFacet constituent, boolean allowParts)
	{
		// will be a port, attribute, part or connector
		while (constituent != null)
		{
			if (constituent.getContainedFacet() != null)
			{
				ContainerFacet container = constituent.getContainedFacet().getContainer();
				if (container != null && container.getContainedFacet() != null)
				{
					if (container.getContainedFacet() != null && container.getContainedFacet().getContainer() != null)
					{
						constituent = container.getContainedFacet().getContainer().getFigureFacet();
						if (constituent.getSubject() instanceof Class)
							return constituent;
						// invalidated for parts (stops ports on parts matching)
						if (!allowParts && constituent.getSubject() instanceof Property)
							return null;
					}
					else
						return null;
				}
				else
					return null;
			}
			else
				return null;
		}
		return null;
	}
	
	private static FigureFacet[] findFigures(FigureFacet avoid, DEComponent component, String uuid1, String uuid2, boolean foundClass[])
	{
		// get the actual home, which may be a package
		Package pkg = GlobalSubjectRepository.repository.findOwningPackage((Element) component.getRepositoryObject());
    DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(new DiagramReference(pkg.getUuid()));

		// look through all the diagram figures for the port in its home classifier
		for (FigureFacet figure : diagram.getFigures())
		{
			Object subject = figure.getSubject();

			if (subject instanceof Element)
			{
				FigureFacet cls = extractClassFigureFromConstituent(figure, false);
				Element element = (Element) subject;
		    String elementUUID = element.getUuid();

				boolean found = cls != null && cls.getSubject() == component.getRepositoryObject() && cls != avoid;
				foundClass[0] |= found;
				if (found && (elementUUID.equals(uuid1) || elementUUID.equals(uuid2)))
					return new FigureFacet[]{cls, figure};
				
				// consider connectors
				if (subject instanceof Port)
				{
					cls = extractClassFigureFromConstituent(figure, true);
					found = cls != null && cls.getSubject() == component.getRepositoryObject() && cls != avoid;
					for (Iterator<LinkingFacet> iter = figure.getAnchorFacet().getLinks(); iter.hasNext();)
					{
						LinkingFacet link = iter.next();
						Object lsubj = link.getFigureFacet().getSubject();
						if (lsubj != null)
						{
							String luuid = ((Element) lsubj).getUuid();
							if (found && (luuid.equals(uuid1) || luuid.equals(uuid2)))
								return new FigureFacet[]{cls, link.getFigureFacet()};
							}
					}
				}
			}
		}
		return null;
	}
	
	public static FigureFacet[] findClassAndConstituentFigure(
			FigureFacet avoid,
			DEStratum perspective,
			DEComponent component,
			DeltaPair addOrReplace,
			boolean shallowOnly)
	{
		return findClassAndConstituentFigure(
				avoid,
				perspective,
				component,
				addOrReplace.getOriginal().getUuid(),
				addOrReplace.getConstituent().getUuid(),
				shallowOnly);
	}

	public static FigureFacet[] findClassAndConstituentFigure(
			FigureFacet avoid,
			DEStratum perspective,
			DEComponent component,
			String uuid1,
			String uuid2,
			boolean shallowOnly)
	{
		List<DEElement> tops = component.getTopmost(perspective);
		return findClassAndConstituent(avoid, perspective, tops, uuid1, uuid2, shallowOnly);
	}

	public static Object[] findOwningDiagram(Package fromPackage, Element cls, boolean underHereOnly)
	{
		IDeltaEngine engine = GlobalDeltaEngine.engine;				
		DEElement component = engine.locateObject(cls).asElement();
		
		SubjectRepositoryFacet repos = GlobalSubjectRepository.repository;
		DEStratum perspective = GlobalDeltaEngine.engine.locateObject(fromPackage).asStratum();
		
		Set<DEStratum> simple = new HashSet<DEStratum>();
		simple.add(perspective);
		List<DEElement> tops = component.getTopmost(
				underHereOnly ? perspective.getSimpleDependsOn() : simple);
		if (tops.size() != 0)
		{
			// want to open the diagram for this package
			Element elem = (Element) tops.get(0).getRepositoryObject();
			return new Object[] {
					GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(new DiagramReference(repos.findOwningPackage(elem).getUuid())),
					tops.get(0).getRepositoryObject()
			};
		}
		return null;
	}
	
	public static FigureFacet findFigureInDiagram(DiagramFacet diagram, Object subject)
	{
		for (FigureFacet figure : diagram.getFigures())
			if (figure.getSubject() == subject)
				return figure;
		return null;
	}
	
	public static FigureFacet[] findClassAndConstituent(
			FigureFacet avoid,
			DEStratum perspective,
			List<DEElement> tops,
			String uuid1,
			String uuid2,
			boolean shallowOnly)
	{
		boolean foundClass[] = {false};
		for (DEElement top : tops)
		{
			if (top.asComponent() != null)
			{
				FigureFacet[] figures = findFigures(avoid, top.asComponent(), uuid1, uuid2, foundClass);
				if (figures != null)
					return figures;
			}
		}
		// didn't find the constituent but found one of the classes then we should leave
		if (shallowOnly && foundClass[0])
			return null;
		
		for (DEElement top : tops)
		{
			if (top.asComponent() != null)
			{
				// otherwise, look at the supercomponents
				for (DEElement elem : top.getFilteredResembles_e(perspective, false))
				{
					if (elem.asComponent() != null)
					{
						List<DEElement> nextTops = new ArrayList<DEElement>();
						nextTops.add(elem);
						FigureFacet[] next = findClassAndConstituent(avoid, perspective, nextTops, uuid1, uuid2, shallowOnly);
						if (next != null)
							return next;
					}
				}
			}
		}
		
		return null;
	}
		
	public static void makeResizingTransaction(FigureFacet figure, UBounds newBounds)
	{
			ResizingFiguresGem gem = new ResizingFiguresGem(null, figure.getDiagram());
			ResizingFiguresFacet facet = gem.getResizingFiguresFacet();
			facet.markForResizing(figure);
			facet.setFocusBounds(newBounds);
			facet.end();
	}

	public static void copyStereotypesAndValues(Element replaced, Element next)
	{
		// copy over any applied stereotypes
		for (Object st : replaced.undeleted_getAppliedBasicStereotypes())
		{
			Stereotype stereo = (Stereotype) st;
			next.settable_getAppliedBasicStereotypes().add(stereo);
		}
		// and applied values
		for (Object st : replaced.undeleted_getAppliedBasicStereotypeValues())
		{
			AppliedBasicStereotypeValue val = (AppliedBasicStereotypeValue) st;
			if (val.getValue() instanceof LiteralBoolean)
			{
				AppliedBasicStereotypeValue nval = next.createAppliedBasicStereotypeValues();			
				nval.setProperty(val.getProperty());
				nval.createValue(UML2Package.eINSTANCE.getLiteralBoolean());
				((LiteralBoolean) nval.getValue()).setValue(val.getValue().booleanValue());
			}
			else
			if (val.getValue() instanceof Expression)
			{
				AppliedBasicStereotypeValue nval = next.createAppliedBasicStereotypeValues();			
				nval.setProperty(val.getProperty());
				nval.createValue(UML2Package.eINSTANCE.getExpression());
				((Expression) nval.getValue()).setBody(val.getValue().stringValue());
			}				
		}		
	}
	
	/////////////////////////////////////////////////


}
