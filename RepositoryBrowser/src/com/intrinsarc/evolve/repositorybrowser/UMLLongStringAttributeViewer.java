package com.intrinsarc.evolve.repositorybrowser;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

import com.intrinsarc.swing.lookandfeel.*;

public class UMLLongStringAttributeViewer extends UMLAttributeViewerBase
{
  private JTextArea text;
  private boolean readOnly;
  
  public UMLLongStringAttributeViewer(Element element, EStructuralFeature attribute, UMLAttributeModificationListener listener, boolean readOnly)
  {
    super(element, attribute, listener);
    this.readOnly = readOnly;
  }
  
  public JComponent installAttributeEditor(final JPanel insetPanel, GridBagConstraints gbcLeft, GridBagConstraints gbcRight)
  {
    text = new JTextArea((String) getModelValue());
    text.setFocusTraversalKeysEnabled(true);
    text.setWrapStyleWord(true);
    text.setLineWrap(true);
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

    // use a scroll pane to add a border around the text area if the look and feel requires it
    // -- it's not pretty code, but it works well visually
    final JScrollPane border = 
    	RegisteredGraphicalThemes.getInstance().getStartupTheme().drawsBoxAroundTextArea() ?
    		null : new JScrollPane(text);    		
    insetPanel.add(border != null ? border : text, gbcRight);
    
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
  
  protected boolean acceptsEnter()
  {
  	return true;
  }
}