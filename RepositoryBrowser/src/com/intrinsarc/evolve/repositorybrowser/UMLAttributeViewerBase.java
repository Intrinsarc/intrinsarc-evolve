package com.intrinsarc.evolve.repositorybrowser;

import java.awt.*;

import javax.swing.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

import com.intrinsarc.idraw.foundation.*;


public abstract class UMLAttributeViewerBase implements UMLAttributeViewer
{
  protected Element element;
  protected EStructuralFeature attribute;
  protected JLabel label;
  protected UMLAttributeModificationListener listener;
  private boolean conflict;
  private Object oldValue;
  private JComponent editor;

  public UMLAttributeViewerBase(Element element, EStructuralFeature attribute, UMLAttributeModificationListener listener)
  {
    this.element = element;
    this.attribute = attribute;
    this.listener = listener;
    oldValue = getModelValue();
  }

  public void investigateChange()
  {
  	Object newValue = getModelValue();

    // if the old value is the same as what we currently have, move to the new
    if (oldValue.equals(getCurrentValue()))
      trackLatestValue(newValue);
    oldValue = newValue;
    
    conflict = true;
    colourLine();
  }

  public void installAttributeEditor(String category, JPanel insetPanel, GridBagConstraints gbcLeft, GridBagConstraints gbcRight, boolean includeLabel)
  {
      String name = attribute.getName();
      label = new JLabel("    " + name);
      label.setToolTipText(category);
      if (includeLabel)
        insetPanel.add(label, gbcLeft);

    editor = installAttributeEditor(insetPanel, gbcLeft, gbcRight);
  }
  
  protected void colourLine()
  {
    // if we have an update and it is conflicting, colour in red
    if (isModified())
    {
      if (conflict)
      {
        label.setForeground(Color.RED);
        editor.setForeground(Color.RED);
      }
      else
      {
        label.setForeground(Color.BLUE);
        editor.setForeground(Color.BLUE);
      }
    }
    else
    {
      label.setForeground(Color.BLACK);
      editor.setForeground(Color.BLACK);
      conflict = false;
    }
  }

  public boolean isModified()
  {
    return !getModelValue().equals(getCurrentValue());
  }

  protected Object getModelValue()
  {
    return element.eGet(attribute);
  }
  
  protected Object getOldValue()
  {
  	return oldValue;
  }
  
  /**
   * make the command to set the attribute
   */
  public void applyAction()
  {
    if (isModified())
    	element.eSet(attribute, getCurrentValue());
  }
  
  protected abstract JComponent installAttributeEditor(JPanel insetPanel, GridBagConstraints gbcLeft, GridBagConstraints gbcRight);
  protected abstract Object getCurrentValue();
  protected abstract void trackLatestValue(Object newValue);
}