package com.hopstepjump.jumble.repositorybrowser;

import java.awt.*;

import javax.swing.*;

public interface UMLAttributeViewer
{
  public void investigateChange();
  public void installAttributeEditor(String category, JPanel insetPanel, GridBagConstraints gbcLeft, GridBagConstraints gbcRight, boolean includeLabel);
  public boolean isModified();
  public void applyAction();
  public JComponent getEditor();
  public void revert();
}
