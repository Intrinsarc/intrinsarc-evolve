package com.hopstepjump.jumble.umldiagrams.classifiernode;

import com.hopstepjump.idraw.foundation.*;

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
