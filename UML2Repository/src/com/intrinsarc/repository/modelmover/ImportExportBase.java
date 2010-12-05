package com.intrinsarc.repository.modelmover;

import java.util.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.util.*;
import org.eclipse.uml2.*;

import com.intrinsarc.repositorybase.*;

public abstract class ImportExportBase
{
  public static boolean containmentCanBeSet(EReference reference)
	{
  	return reference.isContainment() && reference.isChangeable() && !reference.isDerived() && !reference.isUnsettable();
	}
  
  protected List<TransientSavedReference> reestablishReferences(Model topLevelOfFrom, Map<String /*UUID*/, Element> translate, List<TransientSavedReference> savedReferences)
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

  public static Element copyElementAndContained(Map<String /*UUID*/, Element> translate, List<TransientSavedReference> savedReferences, Element from, Element toParent, EReference relationshipToParent, boolean checkDeletions)
  {
    // create the new element
    EClass baseEClass = from.eClass();
    
    GlobalSubjectRepository.ignoreUpdates = true;
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
	                toElements.add(copyElementAndContained(translate, savedReferences, (Element) element, exportedFrom, contained, checkDeletions));
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
                copyElementAndContained(translate, savedReferences, fromElement, exportedFrom, contained, checkDeletions));
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
  
	protected static boolean attributeCanBeSet(EAttribute attribute)
	{
  	return attribute.isChangeable() && !attribute.isDerived() && !attribute.isUnsettable();
	}

  protected static boolean referenceCanBeSet(EReference reference)
	{
  	return !reference.isContainment() && reference.isChangeable() && !reference.isDerived() && !reference.isUnsettable();
	}

	protected static void lazyOn()
  {
    EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
  }

	protected static void lazyOff()
  {
    EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = false;
  }
}
