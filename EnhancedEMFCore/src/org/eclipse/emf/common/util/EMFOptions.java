package org.eclipse.emf.common.util;

import java.util.*;

import org.eclipse.emf.ecore.*;

import com.hopstepjump.deltaengine.base.*;

public class EMFOptions
{
  /**
   * if false, no lists are lazily created, and you must call settable_getXXX
   * if true, the lists are lazily created by just calling getXXX
   */
  public static boolean CREATE_LISTS_LAZILY_FOR_GET = false;
  
  /**
   * checks to see that an add exists somewhere in the hierarchy of classifier, from its home perspective
   * but it can't exist in classifier
   * @param uuidToFind
   * @param classifier
   * @return
   */
  public static boolean doesAddExistElsewhere(String uuidToFind, EModelElement classifier, EModelElement stratum)
  {
    // ensure that the replaced element exists somewhere in the hierarchy
    // be very defensive to avoid problems
    IDeltaEngine engine = GlobalDeltaEngine.engine;
    if (engine != null)
    {
      DEElement element = engine.locateObject(classifier).asElement();
      DEStratum perspective = engine.locateObject(stratum).asStratum();
      if (element != null && perspective != null)
      {
        for (ConstituentTypeEnum type : ConstituentTypeEnum.values())
          if (addExists(perspective, uuidToFind, element, type))
            return true;
      }
    }
    return false;
  }

  private static boolean addExists(DEStratum perspective, String uuidToFind, DEElement element, ConstituentTypeEnum type)
  {
    // look for the add here, otherwise use the filtered resembles_e to move down the resemblance graph
    for (DEElement check : element.getFilteredResemblance_eClosure(perspective))
    {
      Set<DeltaPair> pairs = check.getDeltas(type).getPairs(perspective, DeltaPairTypeEnum.ADD_OBJECTS);
      for (DeltaPair pair : pairs)
        if (pair.getUuid().equals(uuidToFind))
          return true;
    }
    return false;
  }

  /**
   * see if we have a replace which matches the uuid in this classifier
   * -- used to allow a replace to override a delete in the same definition
   * @param uuid
   * @param classifier
   * @return
   */
  public static boolean doesReplaceExistHere(String uuid, EModelElement classifier)
  {
    IDeltaEngine engine = GlobalDeltaEngine.engine;
    if (engine != null && classifier != null)
    {
      DEObject element = engine.locateObject(classifier);
      if (element != null)
      {
        Set<String> replaceUuids = element.asElement().getReplaceUuidsOnly();
        return replaceUuids.contains(uuid);
      }
    }
      return false;
  }

  public static boolean doesAttributeExistForSlot(EModelElement part, EModelElement slotType, String featureUuid)
  {
    // get the attributes of the type of the part, from the perspective of the classifier the part is contained within
    IDeltaEngine engine = GlobalDeltaEngine.engine;
    if (engine != null)
    {
      DEComponent element = engine.locateObject(slotType).asComponent();
      DEStratum perspective = engine.locateObject(part).getHomeStratum();
      
      if (element != null && perspective != null)
      {
        IDeltas deltas = element.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE);
        Set<DeltaPair> pairs = deltas.getConstituents(perspective);
        for (DeltaPair pair : pairs)
        {
          if (pair.getUuid().equals(featureUuid))
            return true;
        }
      }
      return false;
    }
    return true;
  }

  public static boolean doPortsExist(String originalPortUuid, EModelElement originalClassifier, String newPortUuid, EModelElement newClassifier, EModelElement stratum)
  {
    // ensure that the replaced element exists somewhere in the hierarchy
    // be very defensive to avoid problems
    IDeltaEngine engine = GlobalDeltaEngine.engine;
    if (engine != null)
    {
      DEElement origC = engine.locateObject(originalClassifier).asElement();
      DEElement newC = engine.locateObject(newClassifier).asElement();
      DEStratum perspective = engine.locateObject(stratum).asStratum();

      // make sure the original port is there
      if (origC == null || !exists(perspective, originalPortUuid, origC, ConstituentTypeEnum.DELTA_PORT))
        return false;

      // make sure the new port is there
      if (newC == null || !exists(perspective, newPortUuid, newC, ConstituentTypeEnum.DELTA_PORT))
        return false;
    }
    return true;
  }

  /**
   * does the uuid exist at this perspective?
   * @param perspective
   * @param uuidToFind
   * @param element
   * @param type
   * @return
   */
  private static boolean exists(DEStratum perspective, String uuidToFind, DEElement element, ConstituentTypeEnum type)
  {
    IDeltas deltas = element.getDeltas(type);
    Set<DeltaPair> pairs = deltas.getConstituents(perspective);
    for (DeltaPair pair : pairs)
      if (pair.getUuid().equals(uuidToFind))
        return true;
    return false;
  }
}
