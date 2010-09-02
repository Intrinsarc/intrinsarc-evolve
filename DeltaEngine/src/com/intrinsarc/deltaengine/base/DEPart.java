package com.intrinsarc.deltaengine.base;

import java.util.*;

public abstract class DEPart extends DEConstituent
{
  private Map<DEStratum, Set<DeltaPair>> remappedPorts;
  public abstract Set<DeltaPair> getPortRemaps();
  public abstract DEComponent getType();
  public abstract List<DESlot> getSlots();
  
  public DEPart()
  {
    super();
  }

  @Override
  public DEPart asPart()
  {
    return this;
  }
  
  public Set<DeltaPair> getPortsAfterRemap(DEStratum perspective)
  {
  	// lazily instantiate the remapped ports
  	if (remappedPorts == null)
      remappedPorts = new HashMap<DEStratum, Set<DeltaPair>>();  		
  	
    // if the cache holds the results, return immediately
    Set<DeltaPair> newPairs = remappedPorts.get(perspective);
    if (newPairs != null)
      return newPairs;
    
    DEComponent type = getType();
    // be defensive -- before design is completed, parts may have no types...
    if (type == null)
      return new HashSet<DeltaPair>();
    
    IDeltas deltas = type.getDeltas(ConstituentTypeEnum.DELTA_PORT);
    Set<DeltaPair> pairs = deltas.getConstituents(perspective);
    
    // remove any ports that have been remapped
    newPairs = new LinkedHashSet<DeltaPair>();
    for (DeltaPair pair : pairs)
    {
      DeltaPair remapped = pair;
      for (DeltaPair remapPair : getPortRemaps())
        if (pair.getConstituent() == remapPair.getConstituent())
        {
          remapped = remapPair;
          // make it look like a replace
          remapped.setOriginal(remapPair.getOriginal());
          break;
        }      
      newPairs.add(remapped);
    }
    
    // save in the cache
    remappedPorts.put(perspective, newPairs);    
    
    return newPairs;
  }
  
	public DEPort unRemap(DEPort port)
	{
		// look to see if this has been remapped, and take the remap value
		for (DeltaPair pair : getPortRemaps())
			if (pair.getConstituent() == port)
				return pair.getOriginal().asPort();
		return port;
	}
}