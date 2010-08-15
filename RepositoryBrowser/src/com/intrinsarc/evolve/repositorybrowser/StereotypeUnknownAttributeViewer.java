package com.intrinsarc.evolve.repositorybrowser;

import java.awt.*;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.intrinsarc.idraw.foundation.*;

public class StereotypeUnknownAttributeViewer implements UMLAttributeViewer
{
  private Element element;
  private Property attribute;
  private JLabel label;

  public StereotypeUnknownAttributeViewer(Element element, Property attribute)
  {
    this.element = element;
    this.attribute = attribute;
  }
    
  public void setEnabled(boolean enabled)
  {
  }
  
  public void investigateChange()
  {
  }

  public void installAttributeEditor(String category, JPanel insetPanel, GridBagConstraints gbcLeft, GridBagConstraints gbcRight, boolean includeLabel, JButton okButton)
  {
    String name = attribute.getName();
    label = new JLabel(name.length() == 0 ? "(unnamed)" : name);
    label.setToolTipText(category);
    insetPanel.add(label, gbcLeft);
    
    // get the value
    Object value = StereotypeAttributeViewerBase.getAppliedValue(element, attribute);
    if (value instanceof Element && ((Element) value).isThisDeleted())
      value = null;
    
    JTextField field = new JTextField("(" + attribute.getName() + ") " + (value == null ? "null" : value.toString()));
    field.setBackground(Color.LIGHT_GRAY);
    field.setEditable(false);
    insetPanel.add(field, gbcRight);
    gbcLeft.gridy++;
    gbcRight.gridy++;
  }

  public boolean isModified()
  {
    return false;
  }


  public void applyAction()
  {
  }


  public JComponent getEditor()
  {
    return label;
  }


  public void revert()
  {
  }
}