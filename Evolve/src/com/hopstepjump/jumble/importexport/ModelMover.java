package com.hopstepjump.jumble.importexport;

import java.net.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.idraw.diagramsupport.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repository.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;


public class ModelMover
{
	public static final ImageIcon IMPORT_ICON = IconLoader.loadIcon("import.png");
	public static final ImageIcon EXPORT_ICON = IconLoader.loadIcon("export.png");
	private static final Pattern UNNAMED_SENSIBLE = Pattern.compile("org\\.eclipse\\.uml2\\.impl\\.(.*)Impl@.*\\ (\\(j_.*)", Pattern.MULTILINE | Pattern.DOTALL);
	private static final Pattern NAMED_SENSIBLE = Pattern.compile("org\\.eclipse\\.uml2\\.impl\\.(.*)Impl@.*\\ (\\(j_.*)name\\:\\ ([^,]*)(.*)", Pattern.MULTILINE | Pattern.DOTALL);
	
  public static final String UML2_EXPORT = "uml2_export";
	public static final String UML2_EXPORT_FILES = "evolve export files (.uml2_export)";
	private DiagramViewFacet diagramView;
  /** save any references for later fixing */
  private List<TransientSavedReference> savedReferences;
  /** a from -> to relation */
  private Map<String /*UUID*/, Element> translate;
  private Model topLevelOfFrom;
  private JFrame frame;

  
  public ModelMover(JFrame frame, DiagramViewFacet diagramView)
  {
    this.diagramView = diagramView;
    this.frame = frame;
  }
  
  public static String getSensibleDetail(Element element)
  {
  	String detail = element.toString();
  	Matcher match = NAMED_SENSIBLE.matcher(detail);
  	if (match.matches())
  	{
			return "<html>" + match.group(1) + " <b>" + match.group(3) + "</b> " + match.group(2) + "name: " + match.group(3) + match.group(4);
  	}
  	else
  	{
  		match = UNNAMED_SENSIBLE.matcher(detail);
  		if (match.matches())
    	{
  			return "<html>" + match.group(1) + " " + match.group(2);
    	}
  		
  		return detail;
  	}
  }

  public Set<Package> getSelectedTopLevelPackages()
  {
    // collect the top level elements we've been asked to export
    DiagramFacet diagram = diagramView.getDiagram();
    Set<String> selected = CopyToDiagramHelper.getFigureIdsIncludedInSelection(diagramView, false);
    Collection<String> topLevel = CopyToDiagramHelper.getTopLevelFigureIdsOnly(diagramView.getDiagram(), selected, 0, true);
    
    // turn these into elements
    Set<Package> elements = new HashSet<Package>();
    for (String id : topLevel)
    {
      Element subject = (Element) diagram.retrieveFigure(id).getSubject();
      if (subject != null && !subject.isThisDeleted() && subject instanceof Package)
        elements.add((Package) subject);
    }
    return elements;
  }

  private static final String TITLE = "Importing...";
  public ImportResults importPackages(final LongRunningTaskProgressMonitorFacet monitor, final SubjectRepositoryFacet toImport) throws RepositoryOpeningException
  {
  	final ImportResults importResults = new ImportResults();
  	final RepositoryOpeningException savedException[] = {null};
  	final RuntimeException savedRuntimeException[] = {null};
   	monitor.invokeActivityAndMonitorProgress(new Runnable()
  	{
  		public void run()
  		{
  	  	// get the current linked package
  			try
  			{
	  	  	final Package importInto = (Package) diagramView.getDiagram().getLinkedObject();
	  	  	
	  	    // clear some variables
	  	    savedReferences = new ArrayList<TransientSavedReference>();
	  	    translate = new HashMap<String, Element>();
	  	    
	  	    SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
	  	    monitor.displayInterimPopup(IMPORT_ICON, TITLE, "Opened file " + toImport.getFileName(), null, -1);
	  	    Model importTop = toImport.getTopLevelModel();
	  	    Model currentTop = repository.getTopLevelModel();
	  	    topLevelOfFrom = importTop;
	  	    
	  	    // see if there is any problem with overlapping packages
	  	    Set<Package> importing = new HashSet<Package>();
	  	    collectAllPackagesContained(importTop, importing);
	  	    importing.remove(importTop);
	  	    Set<String> uuids = collectAllPackagesUp(importInto);
	  	    
	  	    // there must be no overlaps between the UUIDs of importInto and importing
	  	    for (Package p : importing)
	  	    {
	  	    	if (uuids.contains(p.getUuid()))
	  	    	{
	  	    		savedException[0] = new RepositoryOpeningException(
	  	    				"Cannot import repository here: import package '" + p.getName() + "' is in containment hierarchy");
	  	    		GlobalSubjectRepository.repository.resetModified();
	  	    		return;
	  	    	}
	  	    }
	  	    

	  	    // copy over into the new model
	  	    Map<String, Element> safe = new LinkedHashMap<String, Element>();
	  	    for (Object pkgObject : importTop.undeleted_getChildPackages())
	  	    {
	  	    	Package p = (Package) pkgObject;
	  	    	
	  	    	// delete any package in the current that conflicts
	  	    	Package existing = (Package) repository.findNamedElementByUUID(p.getUuid());
	  	    	if (existing != null)
	  	    		recordElements(safe, existing);
	  	    	
	    	    monitor.displayInterimPopup(IMPORT_ICON, TITLE, "Importing package " + p.getName(), null, -1);
	  	      Element element = copyElementAndContained(
	  	      		(Package) pkgObject,
	  	          currentTop,
	  	          UML2Package.eINSTANCE.getPackage_ChildPackages(),
	  	          false);
	  	      importInto.getChildPackages().add(element);
	  	      // the replaced package can now be deleted
	  	      if (existing != null)
	  	      {
	  	      	repository.incrementPersistentDelete(existing);
	  	      	importResults.addReplacedPackage(existing);
	  	      }
	  	    }
	  	    
	  	    // handle saved references: inside first and then outside
	  	    monitor.displayInterimPopup(IMPORT_ICON, TITLE, "Reestablishing references", null, -1);
	  	    reestablishReferences();
	  	    for (SavedReference bad : restoreOutsideReferences(importTop, currentTop))
	  	    	importResults.addLeftOverOutsideReference(bad);
	  	        
	  	    // repoint any existing elements to the new imports
	        monitor.displayInterimPopup(IMPORT_ICON, TITLE, "Repointing references", null, -1);
	  	    for (String uuid : translate.keySet())
	  	    	safe.remove(uuid);  // now contains all removed, both safe and unsafe
	  	    Set<Element> unsafe = new HashSet<Element>();
	        repoint(importResults.getReplacedPackages(), currentTop, new int[]{0}, unsafe, safe);

	        // work out save and unsafe deletions
	        for (Element r : safe.values())
	        	importResults.addSafelyRemoved(r.getUuid());
	        for (Element r : unsafe)
	        	importResults.addUnsafelyRemoved(r.getUuid());

	        // commit
	        repository.commitTransaction();
	        repository.startTransaction("", "");
  			}
  			catch (RuntimeException t)
  			{
  				savedRuntimeException[0] = t;
  			}
  		}

			private List<String> sort(Set<String> keySet)
			{
				List<String> l = new ArrayList<String>(keySet);
				Collections.sort(l);
				return l;
			}
  	});
   	if (savedException[0] != null)
   		throw savedException[0];
   	if (savedRuntimeException[0] != null)
   		throw savedRuntimeException[0];
   	return importResults;
  }
  
	private void recordElements(Map<String, Element> recorded, Element start)
  {
		// don't want to record anything that's already been deleted, or any diagram parts that have changed
		if (start.isThisDeleted() || start instanceof J_DiagramHolder)
			return;
		
		// record the deletion
		recorded.put(start.getUuid(), start);
		
    // create the new element
    EClass baseEClass = start.eClass();
    
    // handle any containments
    for (Object containedObject : baseEClass.getEAllContainments())
    {
      EReference contained = (EReference) containedObject;
      if (containmentCanBeSet(contained))
      {
        if (contained.isMany())
        {
          List fromElements = (List) start.eGet(contained);
          if (fromElements != null)
            for (Element element : (List<Element>) fromElements)
            	recordElements(recorded, element);
        }
        else
        {
          Element fromElement = (Element) start.eGet(contained);
          if (fromElement != null && !fromElement.isThisDeleted())
          	recordElements(recorded, fromElement);
        }
      }
    }    
  }
  
	private void repoint(Set<Package> replaced, Element from, int count[], Set<Element> unsafe, Map<String, Element> removed)
  {
		if (replaced.contains(from))
			return;
		if (count[0]++ % 10 == 0)
		{
      GlobalSubjectRepository.repository.commitTransaction();
      GlobalSubjectRepository.repository.startTransaction("", "");
		}
		
    // create the new element
    EClass baseEClass = from.eClass();

    // possibly delete the element
	  Element translated = translate(from);
	  if (translated != null && translated != from)
	  {
	  	GlobalSubjectRepository.repository.incrementPersistentDelete(from);
	  }
	  else
	  {
	    // handle any containments
	    for (Object containedObject : baseEClass.getEAllContainments())
	    {
	      EReference contained = (EReference) containedObject;
	      if (containmentCanBeSet(contained))
	      {
	        if (contained.isMany())
	        {
	          List fromElements = (List) from.eGet(contained);
	          if (fromElements != null)
	          {
	            for (Object element : fromElements)
	            {
	          		// don't transfer over saved references
	            	if (!(element instanceof SavedReference))
	            	{
		              Element fromElement = (Element) element;
	              	repoint(replaced, fromElement, count, unsafe, removed);
	            	}
	            }
	          }
	        }
	        else
	        {
	          Element fromElement = (Element) from.eGet(contained);
	          if (fromElement != null)
	          	repoint(replaced, fromElement, count, unsafe, removed);
	        }
	      }
	    
		    for (Object referredObject : baseEClass.getEAllReferences())
		    {
		      EReference referred = (EReference) referredObject;
		      if (referenceCanBeSet(referred))
		      {
		        if (referred.isMany())
		        {
		          List toElements = (List) from.eGet(referred);
		          List<Element> newElements = new ArrayList<Element>();
		          if (toElements != null)
		          {
		          	boolean replace = false;
		            for (Object toObject : toElements)
		            {
		            	// see if this is in the removed list: if so, it is unsafe
		            	Element elem = (Element) toObject;
		            	if (removed.values().contains(elem))
		            	{
		            		removed.remove(elem.getUuid());
		            		unsafe.add(elem);
		            	}
		            	
		              Element toElement = translate((Element) toObject);
		              if (toElement != null)
		              {
		              	newElements.add(toElement);
		              	replace = true;
		              }
		              else
		              	newElements.add((Element) toObject);
		            }
		            // only change if replace is set
		            if (replace)
		            {
		            	toElements.clear();
		            	toElements.addAll(newElements);
		            }
		          }
		        }
		        else
		        {
            	// see if this is in the removed list: if so, it is unsafe
            	Element elem = (Element) from.eGet(referred);
            	if (removed.values().contains(elem))
            	{
            		removed.remove(elem.getUuid());
            		unsafe.add(elem);
            	}
		        	
		          Element toElement = translate(elem);
		          if (toElement != null && from.eGet(referred) != toElement)
		            from.eSet(referred, toElement);
		        }
		      }
		    }
		  }
	  }
  }

	private Element translate(Element elem)
	{
		if (elem == null)
			return null;
		return translate.get(elem.getUuid());
	}

	private void collectAllPackagesContained(Package top, Set<Package> all)
	{
		all.add(top);
		for (Object obj : top.undeleted_getChildPackages())
			collectAllPackagesContained((Package) obj, all);
	}
	
	private Set<String> collectAllPackagesUp(Package here)
	{
		Set<String> all = new HashSet<String>();
		while (here != null)
		{
			all.add(here.getUuid());
			here = (Package) here.getOwner();
		}
		return all;
	}

	private List<SavedReference> restoreOutsideReferences(Package top, Package current)
	{
		List<SavedReference> bad = new ArrayList<SavedReference>();
		for (Object obj : top.undeleted_getOwnedMembers())
		{
			if (obj instanceof SavedReference)
			{
				SavedReference reference = (SavedReference) obj;
	      EReference ref = reference.getFeature();
	      Element toInNew = GlobalSubjectRepository.repository.findElementByUUID(reference.getTo());
	      if (ref.isMany())
	      {
	      	lazyOn();
	      	if (toInNew != null)  // may have been subsequently removed
	      	{
	      		((List) translate.get(reference.getFrom()).eGet(ref)).add(toInNew);
	      	}
	      	else
	      	{
          	Element elem = current.createAnonymousDeletedImportPlaceholders(reference.getToEClass());
          	elem.setJ_deleted(1);
	      		((List) translate.get(reference.getFrom()).eGet(ref)).add(elem);
	      		// don't bother with reverse dependencies or refs from ports to connector ends, as these are remade
	      		if (reference.getFeature() != UML2Package.eINSTANCE.getNamedElement_ReverseDependencies() &&
	      				reference.getFeature() != UML2Package.eINSTANCE.getConnectableElement_End())
	      			bad.add(reference);
	      	}
	        lazyOff();
	      }
	      else
	      {
	      	if (toInNew != null)
          	translate.get(reference.getFrom()).eSet(ref, toInNew);
	      	else
          {
	      		lazyOn();
          	Element elem = current.createAnonymousDeletedImportPlaceholders(reference.getToEClass());
          	elem.setJ_deleted(1);
	      		translate.get(reference.getFrom()).eSet(ref, elem);
	      		bad.add(reference);
	      		lazyOff();
          }
	      }
			}
		}
		return bad;
	}

	public String exportPackages(final LongRunningTaskProgressMonitorFacet monitor)
  {
		final String savedName[] = {null};
		monitor.invokeActivityAndMonitorProgress(new Runnable()
		{
			public void run()
			{
		    // initialise any fields
		    savedReferences = new ArrayList<TransientSavedReference>();
		    translate = new HashMap<String, Element>();
		    topLevelOfFrom = GlobalSubjectRepository.repository.getTopLevelModel();
		    
		    XMLSubjectRepositoryGem repositoryGem;
		    boolean originalModified = GlobalSubjectRepository.repository.getSaveInformation().getRepositoryToSave();
		    try
		    {
		      repositoryGem = XMLSubjectRepositoryGem.openFile(null, false);
		    }
		    catch (RepositoryOpeningException e)
		    {
		      // will not happen, as we are creating a new repository
		      return;
		    }
		    SubjectRepositoryFacet to = repositoryGem.getSubjectRepositoryFacet();
		    
		    // copy each top level element to the model in the new repository
		    Set<Package> selected = getSelectedTopLevelPackages();
		    Model top = to.getTopLevelModel();
		    
		    // set some info
		    try
				{
					top.setDocumentation("Exported from " + InetAddress.getLocalHost().getHostName() + " on " + new Date());
				}
		    catch (UnknownHostException e)
				{
					top.setDocumentation("Exported on " + new Date());
				}
		    
		    for (Package pkg : selected)
		    {
		      monitor.displayInterimPopup(EXPORT_ICON, "Exporting...", "Exporting package " + pkg.getName(), null, -1);
		      Package newPkg = (Package) copyElementAndContained(
		      		pkg,
		          top,
		          UML2Package.eINSTANCE.getPackage_ChildPackages(),
		          true);
		      // make this read-only
		      newPkg.setReadOnly(true);
		      lazyOn();
		      top.getChildPackages().add(newPkg);		      
		      lazyOff();
		    }
		
		    // fix up the references and save
		    lazyOn();
	      monitor.displayInterimPopup(EXPORT_ICON, "Exporting...", "Reestablishing references", null, -1);
		    for (TransientSavedReference ref : reestablishReferences())
		    	createSavedReference(top, ref);
	      monitor.displayInterimPopup(null, "", "", null, 0);
		    String fileName = to.saveTo(
		    		frame,
		    		UML2_EXPORT_FILES, UML2_EXPORT,
		    		PreferenceTypeDirectory.recent.getLastVisitedDirectory());
		    lazyOff();
		    savedName[0] = fileName;
		    
		    // put the repository back to the original state
		    if (!originalModified)
		    	GlobalSubjectRepository.repository.resetModified();
			}
		});
		return savedName[0];
  }
  
  private List<TransientSavedReference> reestablishReferences()
  {
  	List<TransientSavedReference> outside = new ArrayList<TransientSavedReference>();
    lazyOn();
    for (TransientSavedReference reference : savedReferences)
    {
      // see if we can find the object in the new repository
      Element toInNew = translate.get(reference.getTo().getUuid());
      if (toInNew == null)
      {
				if (reference.getTo() != topLevelOfFrom)
					outside.add(reference);
      }
      else
      {
        EReference ref = reference.getReferred();
        if (ref.isMany())
          ((List) reference.getFrom().eGet(ref)).add(toInNew);
        else
          reference.getFrom().eSet(ref, toInNew);
      }
    }
    lazyOff();
    return outside;
  }

  private void createSavedReference(Model top, TransientSavedReference reference)
	{
  	SavedReference saved = (SavedReference) top.createOwnedMember(UML2Package.eINSTANCE.getSavedReference());
  	saved.setFrom(reference.getFrom().getUuid());
  	saved.setToEClass(reference.getTo().eClass());
  	saved.setTo(reference.getTo().getUuid());
  	saved.setFeature(reference.getReferred());
  	saved.setDocumentation(name(reference.getReferred()) + " from " + name(reference.getFrom()) + " to " + name(reference.getTo()));
	}
  
	private String name(EReference ref)
	{
		return ref.getName();
	}

	private String name(Element elem)
	{
		return ((elem instanceof NamedElement) ? ((NamedElement) elem).getName() : "") + " (" + elem.eClass().getName() + ")";
	}

	private Element copyElementAndContained(Element from, Element toParent, EReference relationshipToParent, boolean checkDeletions)
  {
    // create the new element
    EClass baseEClass = from.eClass();
    Element exportedFrom = (Element) UML2Factory.eINSTANCE.create(baseEClass);
    exportedFrom.setUuid(from.getUuid());
    translate.put(from.getUuid(), exportedFrom);
    
    // handle any attributes, containments and references
    for (Object attrObject : baseEClass.getEAllAttributes())
    {
      EAttribute attr = (EAttribute) attrObject;
      if (attributeCanBeSet(attr))
        exportedFrom.eSet(attr, from.eGet(attr));
    }
    
    // handle any containments
    for (Object containedObject : baseEClass.getEAllContainments())
    {
      EReference contained = (EReference) containedObject;
      if (containmentCanBeSet(contained))
      {
        if (contained.isMany())
        {
          List fromElements = (List) from.eGet(contained);
          if (fromElements != null)
          {
            lazyOn();
            List toElements = (List) exportedFrom.eGet(contained);
            for (Object element : fromElements)
            {
          		// don't transfer over saved references
            	if (!(element instanceof SavedReference))
            	{
	              Element fromElement = (Element) element;
	              if (!checkDeletions || !fromElement.isThisDeleted())
	                toElements.add(copyElementAndContained((Element) element, exportedFrom, contained, checkDeletions));
            	}
            }
            lazyOff();
          }
        }
        else
        {
          Element fromElement = (Element) from.eGet(contained);
          if (fromElement != null && (!checkDeletions || !fromElement.isThisDeleted()))
            exportedFrom.eSet(
                contained,
                copyElementAndContained(fromElement, exportedFrom, contained, checkDeletions));
        }
      }
    }    

    for (Object referredObject : baseEClass.getEAllReferences())
    {
      EReference referred = (EReference) referredObject;
      if (referenceCanBeSet(referred))
      {
        if (referred.isMany())
        {
          List toElements = (List) from.eGet(referred);
          if (toElements != null)
          {
            for (Object toObject : toElements)
            {
              Element toElement = (Element) toObject;
              if (!checkDeletions || !toElement.isThisDeleted())
                savedReferences.add(new TransientSavedReference(exportedFrom, referred, toElement));
            }
          }
        }
        else
        {
          Element toElement = (Element) from.eGet(referred);
          if (toElement != null && (!checkDeletions || !toElement.isThisDeleted()))
            savedReferences.add(new TransientSavedReference(exportedFrom, referred, toElement));
        }
      }
    }

    return exportedFrom;
  }
  
  private boolean attributeCanBeSet(EAttribute attribute)
	{
  	return attribute.isChangeable() && !attribute.isDerived() && !attribute.isUnsettable();
	}

  private boolean referenceCanBeSet(EReference reference)
	{
  	return !reference.isContainment() && reference.isChangeable() && !reference.isDerived() && !reference.isUnsettable();
	}

  public static boolean containmentCanBeSet(EReference reference)
	{
  	return reference.isContainment() && reference.isChangeable() && !reference.isDerived() && !reference.isUnsettable();
	}

	private void lazyOn()
  {
    EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
  }

  private void lazyOff()
  {
    EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = false;
  }
}
