package com.hopstepjump.idraw.figurefacilities.containment;

import com.hopstepjump.idraw.foundation.*;


public final class ContainerRemoveTransaction implements TransactionFacet
{
  public static void remove(ContainerFacet container, ContainedFacet[] containables)
  {
  	container.removeContents(containables);
    ContainerAddTransaction.notifyContainers(container);
  }
}