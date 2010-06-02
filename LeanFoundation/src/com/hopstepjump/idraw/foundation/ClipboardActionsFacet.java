package com.hopstepjump.idraw.foundation;

import com.hopstepjump.gem.*;

public interface ClipboardActionsFacet extends Facet
{
  /** is there a specific delete command, or can we use the standard one? */
  public boolean hasSpecificDeleteAction();
  public void makeSpecificDeleteAction();
  public void performPostDeleteAction();
  
  /** is there a specific kill command, or can we use the standard one? */
  public boolean hasSpecificKillAction();
  public void makeSpecificKillAction(ToolCoordinatorFacet coordinator);
}
