package extend;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.backbone.runtime.api.*;

public class BooleanOption implements ILifecycle
{
// start generated code
// attributes
	private Attribute<java.lang.String> name;
	private Attribute<java.lang.Boolean> selected = new Attribute<java.lang.Boolean>(false);
// required ports
// provided ports
// setters and getters
	public void setName(Attribute<java.lang.String> name) { this.name = name;}
	public void setRawName(java.lang.String name) { this.name.set(name);}
	public Attribute<java.lang.Boolean> getSelected() { return selected; }
	public void setSelected(Attribute<java.lang.Boolean> selected) { this.selected = selected;}
	public void setRawSelected(java.lang.Boolean selected) { this.selected.set(selected);}
// end generated code
	private JCheckBoxMenuItem item = new JCheckBoxMenuItem();
	private Map<Integer, ActionListener> listeners = new HashMap<Integer, ActionListener>();
	
	public void removeOptions_ActionListener(ActionListener l)
	{
		item.removeActionListener(l);
	}
	
	public void setOptions_ActionListener(java.awt.event.ActionListener l, int index)
	{
		item.addActionListener(l);
		listeners.put(index, l);
	}
	
	public javax.swing.JCheckBoxMenuItem getOptions_JCheckBoxMenuItem(Class<?> required, int index)
	{
		return item;
	}

	@Override
	public void afterInit()
	{
		item.setText(name.get());
		item.setSelected(selected.get());
	}
	
	@Override
	public void beforeDelete()
	{
	}
}
