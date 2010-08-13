package com.intrinsarc.evolve.umldiagrams.classifiernode;

import com.intrinsarc.idraw.foundation.*;

public class SwitchSubjectTransaction implements TransactionFacet
{
  public static void switchSubject(FigureFacet switchable, Object newSubject)
  {
    getSwitchable(switchable).switchSubject(newSubject);
  }

  private static SwitchSubjectFacet getSwitchable(FigureFacet switchable)
  {
    return switchable.getDynamicFacet(SwitchSubjectFacet.class);
  }
}
