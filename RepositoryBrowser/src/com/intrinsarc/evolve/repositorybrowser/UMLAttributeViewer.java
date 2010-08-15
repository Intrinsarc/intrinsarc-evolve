package com.intrinsarc.evolve.repositorybrowser;

import java.awt.*;

import javax.swing.*;

public interface UMLAttributeViewer
{
  public void investigateChange();
  public void installAttributeEditor(String category, JPanel insetPanel, GridBagConstraints gbcLeft, GridBagConstraints gbcRight, boolean includeLabel, JButton okButton);
  public boolean isModified();
  public void applyAction();
  public JComponent getEditor();
  public void revert();
}
