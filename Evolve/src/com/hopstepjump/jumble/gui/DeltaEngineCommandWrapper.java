package com.hopstepjump.jumble.gui;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repository.*;
import com.hopstepjump.uml2deltaengine.*;

public class DeltaEngineCommandWrapper implements CommandWrapperFacet
{ 
  public DeltaEngineCommandWrapper()
  {
  }
  
  /**
   * @see com.hopstepjump.idraw.foundation.CommandWrapperFacet#wrapCommand(Command)
   */
  public Command wrapCommand(final Command command)
  {
    return new ViewUpdateWrapperCommand(command)
		{
      @Override
			public void clearCaches()
			{
        clearDeltaEngine();
			}
    };  
  }

  public static void clearDeltaEngine()
  {
    GlobalDeltaEngine.engine = new UML2DeltaEngine();
  }
}