package com.hopstepjump.uml2deltaengine;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.backbone.expanders.*;
import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.repositorybase.*;

public class UML2DeltaEngine implements IDeltaEngine
{
  private Map<Object, DEObject> converted = new HashMap<Object, DEObject>();
  private Map<String, DEObject> uuidConverted = new HashMap<String, DEObject>();
  private DEStratum root;
  
  public UML2DeltaEngine()
  {
  }

  public synchronized DEObject locateObjectForStereotype(String uuid)
  {
    // if we converted before, just return this
    DEObject convert = uuidConverted.get(uuid);
    if (convert != null)
      return convert;
    NamedElement elem = GlobalSubjectRepository.repository.findNamedElementByUUID(uuid);
    if (elem == null)
    	return null;
    return locateObject(elem);
  }
  
  /**
   * returns null if object has no Backbone representation
   */
  public synchronized DEObject locateObject(Object repositoryObject)
  {
  	// allow this to also work with BBObjects...
  	if (repositoryObject instanceof DEObject)
  		return (DEObject) repositoryObject;
  	
    // if we converted before, just return this
    DEObject convert = converted.get(repositoryObject);
    if (convert != null)
      return convert;
    
    java.lang.Class<?> cls = repositoryObject.getClass();
    
    if (Package.class.isAssignableFrom(cls))
    {
    	// if this is a raw package, find the parent which is a stratum
    	Package pkg = (Package) repositoryObject;
    	while (isRawPackage(pkg))
    		pkg = pkg.undeleted_getParentPackage();
    	
      convert = new UML2Stratum(pkg);
    }
    else
  	if (cls == StereotypeImpl.class)
  	{
  		// stereotypes are converted to components also
  		convert = new UML2Component((Class) repositoryObject);
  	}
  	else
    if (cls == ClassImpl.class)
    {
    	convert = new UML2Component((Class) repositoryObject);
    }
    else
    if (cls == PropertyImpl.class)
    {
      if (UMLTypes.extractInstanceOfPart((Element) repositoryObject) == null)
        convert = new UML2Attribute((Property) repositoryObject);
      else
        convert = new UML2Part((Property) repositoryObject);
    }
    else
    if (cls == OperationImpl.class)
    {
      convert = new UML2Operation((Operation) repositoryObject);
    }
    else
    if (cls == InterfaceImpl.class)
    {
      convert = new UML2Interface((Interface) repositoryObject);
    }
    else
    if (cls == PortImpl.class)
    {
      convert = new UML2Port((Port) repositoryObject);
    }
    else
    if (cls == ConnectorImpl.class)
    {
      convert = new UML2Connector((Connector) repositoryObject);
    }
    else
    if (cls == SlotImpl.class)
    {
    	convert = new UML2Slot((Slot) repositoryObject);
    }
    
    // store it away in the map
    if (convert != null)
    {
      converted.put(repositoryObject, convert);
      uuidConverted.put(((Element) repositoryObject).getUuid(), convert);
    }
    return convert;
  }

	public static boolean isRawPackage(Element elem)
	{
		return
			elem.getClass() == PackageImpl.class && 
			!UMLTypes.isStratum(elem);
	}
  
  public DEStratum getRoot()
  {
  	if (root == null)
  		root = locateObject(GlobalSubjectRepository.repository.getTopLevelModel()).asStratum();
    return root;
  }

	public DEStratum forceArtificialParent(Set<DEStratum> strata)
	{
		BBStratum newRoot = new BBStratum("model");
		newRoot.setUuid("model");
		root = newRoot;
		converted.put(root, root);
		uuidConverted.put(root.getUuid(), root);
		
		// look at every package -- if the parent isn't in the strata set, then parent it with the new root
		List<DEStratum> nested = new ArrayList<DEStratum>();
		for (DEObject obj : uuidConverted.values())
		{
			DEStratum pkg = obj.asStratum();
			if (pkg != null && pkg.getParent() != null && !strata.contains(pkg.getParent()))
			{
				pkg.forceParent(root);
				nested.add(pkg);
			}
		}
		newRoot.settable_getChildPackages().addAll(nested);
		newRoot.setDestructive(true);
		return newRoot;
	}

	public void expandForStereotypesAndFactories(DEStratum perspective, DEElement element)
	{
		if (element.isExpanded(perspective))
			return;
		element.setExpanded(perspective);
		
		// expand
		new FactoryExpander().expand(perspective, element);
		element.clearCache(perspective);
		new StateExpander().expand(perspective, element);
		element.clearCache(perspective);
		new StandardComponentExpander().expand(perspective, element);
		element.clearCache(perspective);
	}
}