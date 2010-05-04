package com.hopstepjump.jumble.repositorybrowser;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.foundation.*;

public abstract class StereotypeAttributeViewerBase implements UMLAttributeViewer
{
  protected Element element;
  protected Property constituent;
  protected Property original;
  protected JLabel label;
  protected UMLAttributeModificationListener listener;
  private boolean conflict;
  private Object oldValue;
  private JComponent editor;

  public StereotypeAttributeViewerBase(Element element, Property original, Property constituent, UMLAttributeModificationListener listener)
  {
    this.element = element;
    this.constituent = constituent;
    this.original = original;
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
      String name = constituent.getName();
      label = new JLabel(name.length() == 0 ? "(unnamed)" : name);
      label.setToolTipText(category);
      if (includeLabel)
        insetPanel.add(label, gbcLeft);

      editor = installAttributeEditor(insetPanel, gbcLeft, gbcRight);
      colourLine();
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

  public static List<String> getAppliedValue(Element element, Property attribute)
  {
    List<String> values = new ArrayList<String>();
    if (element instanceof Classifier && element.undeleted_getAppliedBasicStereotypes().isEmpty())
    {
      // find the possible matches
      DEElement elem = GlobalDeltaEngine.engine.locateObject(element).asElement();
      for (DeltaPair pair : elem.getDeltas(ConstituentTypeEnum.DELTA_APPLIED_STEREOTYPE).getConstituents(elem.getHomeStratum()))
      {
        DEAppliedStereotype applied = pair.getConstituent().asAppliedStereotype();
        Map<DEAttribute, String> props = applied.getProperties();
        if (props != null)
          for (DEAttribute attr : props.keySet())
            if (attr.getRepositoryObject() == attribute)
              values.add(props.get(attr));
      }
    }
    else
    for (Object obj : element.undeleted_getAppliedBasicStereotypeValues())
    {
      AppliedBasicStereotypeValue value = (AppliedBasicStereotypeValue) obj;
      if (value.getProperty() == attribute)
      {
        ValueSpecification spec = value.getValue();
        if (spec instanceof LiteralBoolean)
          values.add("" + ((LiteralBoolean) spec).isValue());
        else
        if (spec instanceof Expression)
          values.add(((Expression) spec).getBody());
      }
    }    
    return values;
  }
  
  public static AppliedBasicStereotypeValue getSingleAppliedValue(Element element, Property attribute)
  {
    for (Object obj : element.undeleted_getAppliedBasicStereotypeValues())
    {
      AppliedBasicStereotypeValue value = (AppliedBasicStereotypeValue) obj;
      if (value.getProperty() == attribute)
        return value;
    }    
    return null;
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
    	setValue(getCurrentValue());
  }
  
  protected void addOrReplaceValue(ValueSpecification value)
  {
    AppliedBasicStereotypeValue applied = getSingleAppliedValue(element, original);
    if (applied != null)
      applied.setValue(value);
    else
    {
      applied = element.createAppliedBasicStereotypeValues();
      applied.setProperty(original);
      applied.setValue(value);
    }
  }
  
  protected abstract JComponent installAttributeEditor(JPanel insetPanel, GridBagConstraints gbcLeft, GridBagConstraints gbcRight);
  protected abstract Object getCurrentValue();
  protected abstract void trackLatestValue(Object newValue);
  protected abstract void setValue(Object newValue);
  protected abstract Object getModelValue();
  public    abstract boolean isModified();
}
