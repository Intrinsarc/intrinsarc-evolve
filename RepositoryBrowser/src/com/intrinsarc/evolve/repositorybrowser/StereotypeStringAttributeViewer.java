package com.intrinsarc.evolve.repositorybrowser;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.intrinsarc.swing.lookandfeel.*;


public class StereotypeStringAttributeViewer extends StereotypeAttributeViewerBase
{
  private JTextArea text;

  public StereotypeStringAttributeViewer(Element element, Property original, Property constituent, UMLAttributeModificationListener listener)
  {
    super(element, original, constituent, listener);
  }

  @Override
  protected JComponent installAttributeEditor(final JPanel insetPanel, GridBagConstraints gbcLeft, GridBagConstraints gbcRight)
  {
    if (text == null)
    {
      text = new JTextArea(getModelValue());
      text.setWrapStyleWord(true);
      text.setLineWrap(true);
    }
        
    // use a scroll pane to add a border around the text area if the look and feel requires it
    // -- it's not pretty code, but it works well visually
    final JScrollPane border = 
    	RegisteredGraphicalThemes.getInstance().getStartupTheme().drawsBoxAroundTextArea() ?
    		null : new JScrollPane(text);
    
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
    text.setText(getModelValue());
    colourLine();
  }
  
  @Override
  protected void setValue(Object newValue)
  {
    AppliedBasicStereotypeValue applied = getSingleAppliedValue(element, original);
    if (applied != null && applied.getValue() instanceof Expression)
      ((Expression) applied.getValue()).setBody((String) newValue);
    else
    {
      Expression expression = UML2Factory.eINSTANCE.createExpression();
      expression.setBody((String) newValue);
      addOrReplaceValue(expression);
    }
  }

  @Override
  public boolean isModified()
  {
    return !getModelValue().equals(text.getText());
  }

  @Override
  protected String getModelValue()
  {
    java.util.List<String> values = getAppliedValue(element, original);
    String val = "";
    boolean start = true;
    for (String str : values)
    {
      if (start)
        val = str;
      else
        val += " / " + str;
      start = false;
    }
    return val;
  }
}
