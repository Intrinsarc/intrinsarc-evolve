package com.hopstepjump.repository.garbagecollector;

import java.util.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.repositorybase.*;

class RemovalEntry
{
  private Element owner;
  private EReference reference;
  private Element remove;
  
  public RemovalEntry(Element owner, EReference reference, Element remove)
  {
    super();
    this.owner = owner;
    this.reference = reference;
    this.remove = remove;
  }

  public Element getOwner()
  {
    return owner;
  }

  public EReference getReference()
  {
    return reference;
  }

  public Element getRemove()
  {
    return remove;
  }
  
  public String toString()
  {
    return
      "RemovalEntry(owner = " + name(owner) +
      ", ref = " + reference.getName() + ", removed = " + name(remove) + ")";
  }

  private String name(Element element)
  {
    if (element instanceof NamedElement)
      return ((NamedElement) element).getName();
    return "???";
  }
}


public class RepositoryGarbageCollector
{
  private SubjectRepositoryFacet repository;
  private List<RemovalEntry> referencesToRemove = new ArrayList<RemovalEntry>();
  private List<RemovalEntry> containmentsToRemove = new ArrayList<RemovalEntry>();
	private GarbageUpdaterFacet updater;
  
  public RepositoryGarbageCollector(SubjectRepositoryFacet repository, GarbageUpdaterFacet updater)
  {
    this.repository = repository;
    this.updater = updater;
  }
  
  public int collectGarbage()
  {
    referencesToRemove.clear();
    containmentsToRemove.clear();
    
    // iterate over every package, from top to bottom
    // if the package is deleted, remove it
    // otherwise, consider deleting any elements in it which need to be collected
    // and iterate through sub-packages
    collect(repository.getTopLevelModel());
    return removeElements();
  }
  
  private int removeElements()
  {
    // don't bother if there is nothing to remove
    int removals = containmentsToRemove.size() + referencesToRemove.size();
    if (removals == 0)
      return 0;
    
    // start a transaction for this package
    repository.startTransaction();

    int lp = 0;
    try
    {
      // handle the reference first
      for (RemovalEntry entry : referencesToRemove)
      {
      	if (lp % 10 == 0)
      		updater.update("Removed " + lp + " redundant references");
      	lp++;
        Element owner = entry.getOwner();
        EReference ref = entry.getReference();
        Element remove = entry.getRemove();
        
        // handle a single element differently from multiple
        if (ref.isMany())
          ((List) owner.eGet(ref)).remove(remove);
        else
          owner.eSet(ref, null);
      }

      // handle the containment next
      lp = 0;
      for (RemovalEntry entry : containmentsToRemove)
      {
      	if (lp % 10 == 0)
      		updater.update("Removed " + lp + " redundant containments");
      	lp++;
        Element owner = entry.getOwner();
        EReference ref = entry.getReference();
        Element remove = entry.getRemove();
        
        // handle a single element differently from multiple
        if (ref.isMany())
        {
          repository.destroy(remove);
          ((List) owner.eGet(ref)).remove(remove);
        }
        else
        {
          repository.destroy(remove);
          owner.eSet(ref, null);
        }
      }
    }
    finally
    {
    	updater.update("Committing removals");
      repository.commitTransaction();
    }

    return removals;
  }

  private boolean collect(Package pkg)
  {
    // if this is null (can happen with the objectdb) then don't bother
    if (pkg == null || pkg.isThisDeleted())
      return true;
    
    // process each sub-element
    collectNestedElements(pkg);
    
    // now process each child package to see if we should remove it
    for (Object child : pkg.getChildPackages())
    {
      if (collect((Package) child))
        containmentsToRemove.add(
            new RemovalEntry(
                pkg,
                UML2Package.eINSTANCE.getPackage_ChildPackages(),
                (Package) child));
    }
    return false;
  }
  
  private void collectNestedElements(Element element)
  {
  	if (element instanceof Package)
  		updater.update("Collecting repository elements for package " + ((Package) element).getName());
    // process each nested element, apart from child packages, which are handled
    // by the collect(Package) method
    EClass ecls = element.eClass();
    for (Object obj : ecls.getEAllReferences())
    {
      EReference ref = (EReference) obj;
      if (!ref.isDerived())
      {
        if (!ref.isContainment())
          collectNestedReferences(element, ref);
        else
        if (!ref.getName().equals("childPackages"))
          collectNestedContainments(element, ref);
      }
    }
  }
  
  private void collectNestedReferences(Element element, EReference ref)
  {
    if (ref.isMany())
    {
      // get the list, but don't bother to go any further than checking the end of the reference
      EList refs = (EList) element.eGet(ref);
      if (refs != null)
        for (Object obj : refs)
        {
          Element referenced = (Element) obj;
          if (referenced.isThisDeleted())
          {
            referencesToRemove.add(
                new RemovalEntry(
                    element,
                    ref,
                    referenced));
          }
        }
    }
    else
    {
      Element referenced = (Element) element.eGet(ref);
      if (referenced != null && referenced.isThisDeleted())
      {
        referencesToRemove.add(
            new RemovalEntry(
                element,
                ref,
                referenced));
      }
    }
  }
  
  private void collectNestedContainments(Element element, EReference ref)
  {
    if (ref.isMany())
    {
      // get the list, but don't bother to go any further than checking the end of the reference
      List owned = (List) element.eGet(ref);
      if (owned != null)
        for (Object obj : owned)
        {
          Element contained = (Element) obj;
          boolean deleted = collect(contained);
          if (deleted)
          {
            containmentsToRemove.add(
                new RemovalEntry(
                    element,
                    ref,
                    contained));
          }
        }
    }
    else
    {
      Element contained = (Element) element.eGet(ref);
      if (contained != null && collect(contained))
      {
        containmentsToRemove.add(
            new RemovalEntry(
                element,
                ref,
                contained));
      }
    }
  }

  private boolean collect(Element element)
  {
    // if this is null (can happen with the objectdb) then don't bother
    if (element == null)
      return true;
        
    if (element.isThisDeleted())
      return true;

    // process each sub-element
    collectNestedElements(element);    
    return false;
  }
}
