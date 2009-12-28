package com.hopstepjump.deltaengine.errorchecking;

import java.util.*;

import com.hopstepjump.deltaengine.base.*;

public class ElementErrorChecker
{
  private DEStratum perspective;
  private DEElement element;
  private ErrorRegister errors;

  public ElementErrorChecker(
      DEStratum perspective,
      DEElement element,
      ErrorRegister errors)
  {
    this.perspective = perspective;
    this.element = element;
    this.errors = errors;
  }

  public void performCheck(boolean diagramCheck)
  {
    DEStratum myHome = element.getHomeStratum();
    
  	// if this is replacing, and we are not at home, don't bother
  	if (element.isSubstitution() && perspective != myHome)
  		return;
    
    // element must be visible
    if (diagramCheck && !perspective.getCanSeePlusMe().contains(myHome))
      errors.addError(
          new ErrorLocation(perspective, element), ErrorCatalog.DIAGRAM_ELEMENT_NOT_VISIBLE);
    
    // element must not be retired
    if (diagramCheck && element.isRetired(perspective) && !(element.isRawRetired() && myHome == perspective))
      errors.addError(
          new ErrorLocation(perspective, element), ErrorCatalog.DIAGRAM_ELEMENT_REFERS_TO_RETIRED);

    // must have a name
    if ((element.getName() == null || element.getName().length() == 0) && !element.isSubstitution())
      errors.addError(
          new ErrorLocation(myHome, element), ErrorCatalog.ELEMENT_NAME);
    
    // component should not be declared at the top level
    if (element.getParent().asStratum() == GlobalDeltaEngine.engine.getRoot())
      errors.addError(
          new ErrorLocation(myHome, element), ErrorCatalog.ELEMENT_NOT_AT_TOPLEVEL);

    // anything we substitute must be visible, and there must only be 1 substitution
    Set<DEElement> redefs = element.getSubstitutes();
    if (redefs.size() > 1)
      errors.addError(
          new ErrorLocation(perspective, element), ErrorCatalog.MAX_ONE_SUBSTITUTION);

    // anything we resemble must be visible
    Set<DEStratum> visible = new LinkedHashSet<DEStratum>(myHome.getCanSeePlusMe());
    for (DEElement resemble : element.getRawResembles())
    {
      if (!visible.contains(resemble.getHomeStratum()))
        errors.addError(
            new ErrorLocation(perspective, element), ErrorCatalog.RESEMBLES_VISIBLE);
      
      // cannot refer directly to a substitution
      checkReferenceToSubstitution(resemble, element);
      
      // cannot refer to a retired element, unless we are retiring also
      if (!element.isRetired(perspective))
      {
      	if (resemble.isRetired(perspective))
      		errors.addError(
      				new ErrorLocation(perspective, element), ErrorCatalog.CANNOT_RESEMBLE_REDEF_RETIRED_ELEMENT);
      }
    }

    for (DEElement redef : redefs)
    {
      DEStratum redefHome = redef.getHomeStratum();
      if (!visible.contains(redefHome))
        errors.addError(
            new ErrorLocation(perspective, element), ErrorCatalog.SUBSTITUTION_VISIBLE);
      
      // cannot refer to a retired element, unless we are retiring also
      if (!element.isRetired(perspective))
      	if (redef.isRetired(perspective))
      		errors.addError(
      				new ErrorLocation(perspective, element), ErrorCatalog.CANNOT_RESEMBLE_REDEF_RETIRED_ELEMENT);

      // a redef can't be of a component in the same stratum
      if (redefHome == myHome)
        errors.addError(
            new ErrorLocation(perspective, element), ErrorCatalog.SUBSTITUTION_IN_ANOTHER_STRATUM);
      
      // cannot refer directly to a substitution
      checkReferenceToSubstitution(redef, element);
    }
    
    // if we are retiring an element, we must only evolve it, and involve no others
    if (perspective == myHome && element.isRawRetired())
    {
    	if (!element.getRawResembles().equals(new ArrayList<DEElement>(redefs)) || redefs.size() != 1)
    	{
    		errors.addError(
    				new ErrorLocation(perspective, element), ErrorCatalog.RETIRE_MUST_EVOLVE_ONLY);
    	}
    }
    
	  // ensure we don't have a resemblance circularity
	  if (element.hasDirectCircularResemblance(perspective))
	    errors.addError(
	        new ErrorLocation(perspective, element), ErrorCatalog.RESEMBLANCE_DIRECT_CIRCULARITY);
	  else
	  if (element.hasIndirectCircularResemblance(perspective))
	    errors.addError(
	        new ErrorLocation(perspective, element), ErrorCatalog.RESEMBLANCE_INDIRECT_CIRCULARITY);
	  
	  // ensure that we don't have 2 items with the same uuid
	  for (ConstituentTypeEnum type : ConstituentTypeEnum.values())
	  {
	    IDeltas deltas = element.getDeltas(type);
	    Set<DeltaPair> constituents = deltas.getConstituents(perspective);
	    for (DeltaPair pair : constituents)
	    {
	      for (DeltaPair other : constituents)
	      {
	        if (pair != other && pair.getUuid().equals(other.getUuid()))
	        {
	        	// errors for stereotypes should be raised on the actual element itself
		      	if (type != ConstituentTypeEnum.DELTA_APPLIED_STEREOTYPE)
		          errors.addError(
		              new ErrorLocation(
		                  perspective,
		                  element, 
		                  pair.getConstituent()),
		                  ErrorCatalog.REPLACE_CONFLICT);
		      	else
		          errors.addError(
		              new ErrorLocation(
		                  perspective,
		                  element),
		                  ErrorCatalog.STEREOTYPE_REPLACE_CONFLICT);		      		
	        }
	      }
	    }
	  }    
    
    // a destructive element must be in a destructive stratum
	  DEStratum home = element.getHomeStratum();
    if (perspective == home && element.isDestructive() && !element.getHomeStratum().isDestructive())
      errors.addError(
          new ErrorLocation(element), ErrorCatalog.HOME_OF_DESTRUCTIVE_ELEMENT_MUST_BE_DESTRUCTIVE_ALSO);
    
    // we cannot replace an element in a stratum with "check once if read only" set
    if (element.isSubstitution())
    {
    	for (DEElement replace : element.getSubstitutes())
    	{
    		DEStratum replaceHome = replace.getHomeStratum();
    		if (replaceHome.isReadOnly() && replaceHome.isCheckOnceIfReadOnly())
    			errors.addError(
    					new ErrorLocation(element), ErrorCatalog.CANNOT_REPLACE_ELEMENT_IN_CHECK_ONCE_STRATUM);
    	}
    }
  }

  private void checkReferenceToSubstitution(DEElement referenced, DEObject element)
  {
    if (referenced.isSubstitution())
      errors.addError(
          new ErrorLocation(element), ErrorCatalog.CANNOT_REFER_TO_SUBSTITUTION);          
  }
}

