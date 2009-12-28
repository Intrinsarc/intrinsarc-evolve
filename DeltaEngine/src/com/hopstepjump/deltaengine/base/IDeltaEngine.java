package com.hopstepjump.deltaengine.base;

import java.util.*;

public interface IDeltaEngine
{
  DEObject locateObject(Object repositoryObject);
  /** phase this out when proper stereotypes appear with links to components */
  DEObject locateObjectForStereotype(String uuid);
  
  /** for the top of the hierarchical tree.  must always have a "top" even if we force one */
  DEStratum getRoot();
	DEStratum forceArtificialParent(Set<DEStratum> strata);
	void expandForStereotypesAndFactories(DEStratum perspective, DEElement element);
}
