package com.intrinsarc.idraw.figurefacilities.containment;

import com.intrinsarc.idraw.foundation.*;


public final class ContainerRemoveTransaction implements TransactionFacet
{
  public static void remove(ContainerFacet container, ContainedFacet[] containables)
  {
  	container.removeContents(containables);
    ContainerAddTransaction.notifyContainers(container);
  }
}