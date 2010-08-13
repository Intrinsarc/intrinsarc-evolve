package com.intrinsarc.backbone.generator;

import javax.swing.*;

import com.intrinsarc.gem.*;

public interface BackboneRunnerFacet extends Facet
{
  public JComponent getVisualComponent();
  public void haveClosed();
  public void run();
}
