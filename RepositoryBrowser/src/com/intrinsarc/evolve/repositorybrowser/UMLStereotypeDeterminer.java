package com.intrinsarc.evolve.repositorybrowser;

import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.repositorybase.*;

class UMLStereotypeDeterminer implements UMLIconDeterminer
{
  private String stereotypeUUID;
  private String propertyUUID;
  private boolean reverse;

  public UMLStereotypeDeterminer(String stereotypeUUID, String propertyUUID, boolean reverse)
  {
    this.stereotypeUUID = stereotypeUUID;
    this.propertyUUID = propertyUUID;
    this.reverse = reverse;
  }
  
  public boolean isRelevant(Element element)
  {
  	// take into account resemblance and redef if possible
  	if (element instanceof Classifier)
  	{
	  	DEElement elem = GlobalDeltaEngine.engine.locateObject(element).asElement();
	  	boolean result = 
	  		propertyUUID == null ? elem.extractAppliedStereotype(elem.getHomeStratum(), stereotypeUUID) != null :
	  													elem.extractBooleanAppliedStereotypeProperty(elem.getHomeStratum(), stereotypeUUID, propertyUUID);
	  	return reverse ? !result : result;
  	}
  	return isSimpleRelevant(element);
  }
  
  /**
   * simple elements that can't resemble can just use ordinary stereotype checking
   * @param element
   * @return
   */
  public boolean isSimpleRelevant(Element element)
  {
    if (stereotypeUUID != null && !StereotypeUtilities.isStereotypeApplied(element, stereotypeUUID))
      return false;
    
    if (propertyUUID != null)
    {
      boolean value = StereotypeUtilities.extractBooleanProperty(element, propertyUUID);
      if (reverse)
        value = !value;
      if (!value)
        return false;
    }
    return true;
  }

}