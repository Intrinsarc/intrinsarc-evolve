package com.intrinsarc.evolve.repositorybrowser;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.*;

public class StereotypeBooleanAttributeViewer extends StereotypeAttributeViewerBase
{
  private JCheckBox check;

  public StereotypeBooleanAttributeViewer(Element element, Property original, Property constituent, UMLAttributeModificationListener listener)
  {
    super(element, original, constituent, listener);
  }

  @Override
  protected JComponent installAttributeEditor(final JPanel insetPanel, GridBagConstraints gbcLeft, GridBagConstraints gbcRight, JButton okButton)
  {
    check = new JCheckBox();
    check.setSelected(getModelValue());
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
    check.setSelected(getModelValue());
    colourLine();
  }
  
  @Override
  protected void setValue(Object newValue)
  {
    AppliedBasicStereotypeValue applied = getSingleAppliedValue(element, original);
    if (applied != null && applied.getValue() instanceof LiteralBoolean)
      ((LiteralBoolean) applied.getValue()).setValue((Boolean) newValue);
    else
    {
      LiteralBoolean boolExpression = UML2Factory.eINSTANCE.createLiteralBoolean();
      boolExpression.setValue((Boolean) newValue);
      addOrReplaceValue(boolExpression);
    }
  }

  @Override
  public boolean isModified()
  {
    return !getModelValue().equals(check.isSelected());
  }

  @Override
  protected Boolean getModelValue()
  {
    List<String> values = getAppliedValue(element, original);
    boolean val = false;
    for (String str : values)
      if (str.equals("true"))
        val = true;
    return val;
  }
}
