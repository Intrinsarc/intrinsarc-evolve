package com.hopstepjump.jumble.management;

public interface GUIManagerMBean
{
  // operations
  public void openDiagram(String path, boolean maximise, boolean closeable);
  public void openClipboard(boolean maximise, boolean closeable);
}
