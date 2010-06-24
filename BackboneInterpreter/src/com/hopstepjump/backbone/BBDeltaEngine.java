package com.hopstepjump.backbone;

import java.util.*;

import com.hopstepjump.backbone.expanders.*;
import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.lazy.*;
import com.hopstepjump.deltaengine.base.*;

public class BBDeltaEngine implements IDeltaEngine
{
  public BBDeltaEngine()
  {
  }

  /**
   * returns null if object has no Backbone representation
   */
  public DEObject locateObject(Object repositoryObject)
  {
  	return (DEObject) repositoryObject;
  }

  public BBStratum getRoot()
  {
    return GlobalNodeRegistry.registry.getRoot();
  }
  
	public DEStratum forceArtificialParent(Set<DEStratum> strata)
	{
		// not used for this engine
		return null;
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
	
	public DEObject locateObjectForStereotype(String uuid)
	{
		return GlobalNodeRegistry.registry.getNode(uuid, DEComponent.class);
	}
}