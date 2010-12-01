package com.intrinsarc.repository.modelmover;

import java.util.*;

import javax.swing.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

public class ModelImporter extends ImportExportBase
{
	private static final ImageIcon IMPORT_ICON = IconLoader.loadIcon("import.png");
  private static final String TITLE = "Importing...";
	
  private Package importInto;
  private SubjectRepositoryFacet toImport;

  
  public ModelImporter(Package importInto, SubjectRepositoryFacet toImport)
  {
    this.importInto = importInto;
    this.toImport = toImport;
  }
  
  public ImportResults importPackages(final LongRunningTaskProgressMonitorFacet monitor) throws RepositoryOpeningException
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
	  	    SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
	        repository.startTransaction("", "");
	  	  	
	  	    // clear some variables
	  	    savedReferences = new ArrayList<TransientSavedReference>();
	  	    translate = new HashMap<String, Element>();
	  	    
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
	  	      importInto.settable_getChildPackages().add(element);

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
}
