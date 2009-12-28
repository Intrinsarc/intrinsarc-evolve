package com.hopstepjump.jumble.repositorybrowser;

import java.awt.*;

import javax.swing.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

import com.hopstepjump.idraw.foundation.*;

public class UMLUnknownAttributeViewer implements UMLAttributeViewer
{
  private Element element;
  private EStructuralFeature attribute;
  private JLabel label;
  private JTextField field;

  public UMLUnknownAttributeViewer(Element element, EStructuralFeature attribute)
  {
    this.element = element;
    this.attribute = attribute;
  }
  
  public void setEnabled(boolean enabled)
  {
    label.setEnabled(enabled);
  }
  
  public void investigateChange()
  {
    setField();
  }

  public void installAttributeEditor(String category, JPanel insetPanel, GridBagConstraints gbcLeft, GridBagConstraints gbcRight, boolean includeLabel)
  {
    label = new JLabel("    " + attribute.getName());
    label.setToolTipText(category);
    insetPanel.add(label, gbcLeft);
    
    // get the value
    field = new JTextField();
    
    setField();
    field.setBackground(Color.LIGHT_GRAY);
    field.setEditable(false);
    insetPanel.add(field, gbcRight);
    gbcLeft.gridy++;
    gbcRight.gridy++;
  }

  private void setField()
  {
    Object value = element.eGet(attribute);
    if (value instanceof Element && ((Element) value).isThisDeleted())
      value = null;
    
    field.setText("(" + attribute.getEType().getName() + ") " + (value == null ? "null" : value.toString()));
  }

  public boolean isModified()
  {
    return false;
  }


  public Command formApplyCommand()
  {
    return null;
  }


  public JComponent getEditor()
  {
    return label;
  }


  public void revert()
  {
  }
}