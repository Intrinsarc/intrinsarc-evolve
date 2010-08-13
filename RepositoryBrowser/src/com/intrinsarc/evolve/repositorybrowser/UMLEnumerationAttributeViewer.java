package com.intrinsarc.evolve.repositorybrowser;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

public class UMLEnumerationAttributeViewer extends UMLAttributeViewerBase
{
  private JComboBox combo;
  
  public UMLEnumerationAttributeViewer(Element element, EStructuralFeature attribute, UMLAttributeModificationListener listener)
  {
    super(element, attribute, listener);
  }
  
  public void setEnabled(boolean enabled)
  {
    combo.setEnabled(enabled);
  }
  
  public JComponent installAttributeEditor(final JPanel insetPanel, GridBagConstraints gbcLeft, GridBagConstraints gbcRight)
  {
    combo = new JComboBox();
    // add to the model
    
    List<Object> values = getEnumerationValues();
    for (Object literal : values)
      ((DefaultComboBoxModel) combo.getModel()).addElement(literal);
    combo.setSelectedItem(getModelValue());
    
    combo.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            listener.attributeModified();
            colourLine();
          }
        });
    
    insetPanel.add(combo, gbcRight);
    gbcLeft.gridy++;
    gbcRight.gridy++;
    return combo;
  }

  /**
   * this is a horrible hack, but getting the EEnumLiterals off the EType doesn't work :-(
   * there must be an easier way...
   * @return
   */
  private List<Object> getEnumerationValues()
  {
    List<Object> values = null;
    Object value = element.eGet(attribute);
    java.lang.Class<?> cls = value.getClass();
    Field field = null;
    try
    {
      field = cls.getDeclaredField("VALUES");
      values = (List<Object>) field.get(value);
    }
    catch (Exception ex)
    {
      return new ArrayList<Object>();
    }
    return values;
  }
  
  protected Object getCurrentValue()
  {
    return combo.getSelectedItem();
  }
  
  protected void trackLatestValue(Object newValue)
  {
    combo.setSelectedItem(newValue);
  }

  public JComponent getEditor()
  {
    return combo;
  }

  public void revert()
  {
    combo.setSelectedItem(getModelValue());
    colourLine();
  }
}

