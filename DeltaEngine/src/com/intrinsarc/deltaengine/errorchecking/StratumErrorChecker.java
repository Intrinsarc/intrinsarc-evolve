package com.intrinsarc.deltaengine.errorchecking;

import com.intrinsarc.deltaengine.base.*;

public class StratumErrorChecker
{
  private ErrorRegister errors;
  private DEStratum stratum;

  public StratumErrorChecker(
      DEStratum stratum,
      ErrorRegister errors)
  {
    this.stratum = stratum;
    this.errors = errors;
  }

  public void performCheck(boolean diagramCheck)
  {
  	IDeltaEngine engine = GlobalDeltaEngine.engine;
  	
    // check to see if we have a circularity amongst strata
    if (stratum.isCircular())
      errors.addError(
          new ErrorLocation(stratum), ErrorCatalog.STRATUM_CIRCULAR);
    
    // must have a name
    if (stratum.getName().length() == 0)
      errors.addError(
          new ErrorLocation(stratum), ErrorCatalog.STRATUM_NAME);
    
    // a destructive package can only be in another destructive package
    if (engine.getRoot() != stratum && stratum.isDestructive() && stratum.getParentStratum() != null && !stratum.getParentStratum().isDestructive())
    {
      errors.addError(
          new ErrorLocation(stratum), ErrorCatalog.PARENT_OF_DESTRUCTIVE_STRATUM_MUST_BE_DESTRUCTIVE_ALSO);
    }
  }
}