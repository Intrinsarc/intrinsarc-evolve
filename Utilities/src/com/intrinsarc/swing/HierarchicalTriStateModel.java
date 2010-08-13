package com.intrinsarc.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class HierarchicalTriStateModel extends JToggleButton.ToggleButtonModel
{      
  private HierarchicalTriState state;  

  public HierarchicalTriStateModel(HierarchicalTriState state)
  {
    this.state = state;
  }
 
  public boolean isSelected()
  {      
    return state == HierarchicalTriState.ON;
  } 

  public HierarchicalTriState getState() {
    return state;
  }
  
  public void setState(HierarchicalTriState state) {
    this.state = state;
    fireStateChanged();
    fireActionPerformed(
        new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                        getActionCommand(),
                        EventQueue.getMostRecentEventTime(),
                        0));
  }
  
  public void setPressed(boolean pressed)
  {      
    if (pressed)
    {
      switch(state)
      {
	      case OFF:
	        setState(HierarchicalTriState.ON);
	        break;
	      case PASS_TO_PARENT: 
	        setState(state = HierarchicalTriState.OFF);
	        break;
	      case ON:
	        setState(HierarchicalTriState.PASS_TO_PARENT);
	        break;
      }
    }
  }
  
  public void setSelected(boolean selected)
  {       
    if (selected)
      setState(HierarchicalTriState.ON);
    else
      setState(state = HierarchicalTriState.OFF);
  }
} 
