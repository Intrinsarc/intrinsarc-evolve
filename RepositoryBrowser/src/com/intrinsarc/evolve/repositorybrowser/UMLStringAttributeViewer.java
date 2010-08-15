package com.intrinsarc.evolve.repositorybrowser;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

import com.intrinsarc.swing.lookandfeel.*;

public class UMLStringAttributeViewer extends UMLAttributeViewerBase
{
  private JTextField text;
  private boolean readOnly;
  
  public UMLStringAttributeViewer(Element element, EStructuralFeature attribute, UMLAttributeModificationListener listener, boolean readOnly)
  {
    super(element, attribute, listener);
    this.readOnly = readOnly;
  }
  
  public JComponent installAttributeEditor(final JPanel insetPanel, GridBagConstraints gbcLeft, GridBagConstraints gbcRight)
  {
    text = new JTextField((String) getModelValue());
    text.setFocusTraversalKeysEnabled(true);
    if (readOnly)
    	text.setEnabled(false);

    text.addKeyListener(new KeyAdapter()
    {
			public void keyTyped(KeyEvent e)
      {
        insetPanel.revalidate();
      }
      public void keyReleased(KeyEvent e)
      {
        listener.attributeModified();
        colourLine();
      }
    });
    insetPanel.add(text, gbcRight);
    
    gbcLeft.gridy++;
    gbcRight.gridy++;
    return text;
  }
  

  protected Object getCurrentValue()
  {
    return text.getText();
  }
  
  protected void trackLatestValue(Object newValue)
  {
    text.setText((String) newValue);
  }

  public JComponent getEditor()
  {
    return text;
  }

  public void revert()
  {
    text.setText((String) getModelValue());
    colourLine();
  }
}