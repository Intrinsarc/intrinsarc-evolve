package com.hopstepjump.swing;
import java.awt.*;

import javax.swing.*;

 
/**
 * originally found on http://forum.java.sun.com/thread.jspa?threadID=593755&messageID=3116647
 * made major changes, so much so that hardly any code is left
 */

public class HierarchicalTriStateCheckBox extends JCheckBox
{   
  public HierarchicalTriStateCheckBox()
  {
      this(null, HierarchicalTriState.PASS_TO_PARENT);
  }
  
  public HierarchicalTriStateCheckBox (String text, HierarchicalTriState initial)
  {      
    super.setText(text);
    setModel(new HierarchicalTriStateModel(initial));
  }
 
  /**
   * Set the new state -- this is the preferred way
   */
  public void setState(HierarchicalTriState state) 
  {
    ((HierarchicalTriStateModel) model).setState(state);
  }
  
  public HierarchicalTriState getState() 
  {
    return ((HierarchicalTriStateModel) model).getState();
  } 
  
  public void setSelected(boolean selected) 
  {
		((HierarchicalTriStateModel) model).setSelected(selected);
  } 
  

  /**
   * draw the button and add some descriptive text.  Note -- looks disabled when pass through is current state
   */
  public void paintComponent(Graphics g) 
  {
  	boolean enabled = isEnabled();
  	setEnabled(getState() != HierarchicalTriState.PASS_TO_PARENT);
  	switch (getState())
  	{
  	case PASS_TO_PARENT:
  		setText("look to parent");
  		break;
  	case ON:
  		setText("on");
  		break;
  	case OFF:
  		setText("off");
  		break;
  	}
  	super.paintComponent(g);
    setEnabled(enabled);
  } 
}
