package com.hopstepjump.idraw.foundation;

import com.hopstepjump.gem.*;

public interface ClipboardCommandsFacet extends Facet
{
  /** is there a specific delete command, or can we use the standard one? */
  public boolean hasSpecificDeleteCommand();
  public Command makeSpecificDeleteCommand();
  public Command performPostDeleteTransaction();
  
  /** is there a specific kill command, or can we use the standard one? */
  public boolean hasSpecificKillCommand();
  public Command makeSpecificKillCommand(ToolCoordinatorFacet coordinator);
}
