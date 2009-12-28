package com.hopstepjump.backbonegenerator;

import javax.swing.*;

import com.hopstepjump.gem.*;

public interface BackboneRunnerFacet extends Facet
{
  public JComponent getVisualComponent();
  public void haveClosed();
  public void run();
}
