package com.intrinsarc.uml2deltaengine;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.repositorybase.*;

public class UML2Stratum extends DEStratum
{
  private Package subject;
  private List<DEStratum> rawDependsOn;
  private Boolean relaxed;
  private Boolean destructive;
  private DEStratum parent;
  private Boolean readOnly;
  private Boolean checkOnceIfReadOnly;
    
  public UML2Stratum(Package subject)
  {
    super();
    this.subject = subject;
    
    // packages and profiles turn into strict, non-destructive strata
    if (StereotypeUtilities.extractBooleanProperty(subject, CommonRepositoryFunctions.RELAXED))
    	relaxed = true;
    if (subject.isReadOnly())
    	readOnly = true;      
    if (StereotypeUtilities.extractBooleanProperty(subject, CommonRepositoryFunctions.CHECK_ONCE_IF_READ_ONLY))
    	checkOnceIfReadOnly = true;      
    if (StereotypeUtilities.extractBooleanProperty(subject, CommonRepositoryFunctions.DESTRUCTIVE))
    	destructive = true;
    
    // look up to see if we can find a stratum parent
    Package modelParent = subject.undeleted_getParentPackage();
    parent = (modelParent == null) ? null : (DEStratum) getEngine().locateObject(modelParent);
  }

  @Override
  public boolean isDestructive()
  {
    return destructive == Boolean.TRUE; 
  }

  @Override
  public List<DEStratum> getRawDependsOn()
  {
    if (rawDependsOn != null)
      return rawDependsOn;
    
    // otherwise, construct the list
    rawDependsOn = new ArrayList<DEStratum>();
    @SuppressWarnings("unchecked")
    List<Dependency> owned = subject.undeleted_getOwnedAnonymousDependencies();
    
    for (Dependency dep : owned)
    {
      // only use this if the other end is also a stratum
      Object supplier = dep.undeleted_getDependencyTarget();
      if (supplier instanceof Package)
        rawDependsOn.add((DEStratum) getEngine().locateObject(supplier));
    }
    return rawDependsOn;
  }
  
  @Override
  public DEStratum getParent()
  {
  	return parent;
  }

  @Override
  public boolean isRelaxed()
  {
    return relaxed == Boolean.TRUE;
  }

  @Override
  public Object getRepositoryObject()
  {
    return subject;
  }

  @Override
  public String getUuid()
  {
    return subject.getUuid();
  }

  @Override
  public String getName()
  {
    return subject.getName();
  }

  @Override
  public List<DEStratum> getDirectlyNestedPackages()
  {
    List<DEStratum> nested = new ArrayList<DEStratum>();
    for (Object obj : subject.undeleted_getChildPackages())
    {
      Package pkg = (Package) obj;
      if (!UML2DeltaEngine.isRawPackage(pkg))
      nested.add(getEngine().locateObject(pkg).asStratum());
    }
    
    return nested;
  }

	@Override
	public List<DEElement> getChildElements()
	{
		List<DEElement> elements = new ArrayList<DEElement>();
		getChildElements(elements, subject);
		return elements;
	}
	
	private void getChildElements(List<DEElement> elements, Package pkg)
	{
		for (Object member : pkg.undeleted_getOwnedMembers())
			if (member instanceof Classifier || member instanceof RequirementsFeature)
			{
				DEObject obj = GlobalDeltaEngine.engine.locateObject(member);
				if (obj != null && obj.asElement() != null)
					elements.add(obj.asElement());
			}

		// get elements from any nested packages also
		for (Object child : pkg.undeleted_getChildPackages())
			if (UML2DeltaEngine.isRawPackage((Package) child))
				getChildElements(elements, (Package) child);
	}

	@Override
	public void forceParent(DEStratum parent)
	{
		this.parent = parent;
	}

	@Override
	public List<DEAppliedStereotype> getAppliedStereotypes()
	{
		throw new UnsupportedOperationException("Packages cannot have stereotypes");
	}

	@Override
	public boolean isCheckOnceIfReadOnly()
	{
		if (checkOnceIfReadOnly != null && checkOnceIfReadOnly)
			return true;
		if (parent == null)
			return false;
		return parent.isCheckOnceIfReadOnly();			
	}

	@Override
	public boolean isReadOnly()
	{
		if (readOnly != null && readOnly)
			return true;
		if (parent == null)
			return false;
		return parent.isReadOnly();			
	}
}
