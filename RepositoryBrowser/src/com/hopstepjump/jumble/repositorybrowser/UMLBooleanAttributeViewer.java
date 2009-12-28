package com.hopstepjump.jumble.repositorybrowser;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

public class UMLBooleanAttributeViewer extends UMLAttributeViewerBase
{
  private JCheckBox check;
	private boolean readOnly;
  
  public UMLBooleanAttributeViewer(Element element, EStructuralFeature attribute, UMLAttributeModificationListener listener, boolean readOnly)
  {
    super(element, attribute, listener);
    this.readOnly = readOnly;
  }
  
  
  public void setEnabled(boolean enabled)
  {
    check.setEnabled(enabled);
  }
  
  public JComponent installAttributeEditor(JPanel insetPanel, GridBagConstraints gbcLeft, GridBagConstraints gbcRight)
  {
    check = new JCheckBox();
    check.setSelected((Boolean) getModelValue());
    if (readOnly)
    	check.setEnabled(false);
    check.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          { listener.attributeModified(); colourLine(); }
        });
    insetPanel.add(check, gbcRight);
    gbcLeft.gridy++;
    gbcRight.gridy++;
    return check;
  }
  
  

  protected Object getCurrentValue()
  {
    return check.isSelected();
  }
  
  protected void trackLatestValue(Object newValue)
  {
    check.setSelected((Boolean) newValue);
  }


  public JComponent getEditor()
  {
    return check;
  }

  public void revert()
  {
    check.setSelected((Boolean) getModelValue());
    colourLine();
  }
}
