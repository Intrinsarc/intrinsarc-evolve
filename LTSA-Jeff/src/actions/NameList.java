package actions;

import java.util.*;

import com.hopstepjump.backbone.runtime.api.*;

public class NameList
{
// start generated code
// attributes
// required ports
// provided ports
	private INameListNamesImpl names_INameListProvided = new INameListNamesImpl();
// setters and getters
	public actions.INameList getNames_INameList(Class<?> required) { return names_INameListProvided; }
// end generated code
	private java.util.List<actions.INameListener> listeners_INameListenerRequired = new java.util.ArrayList<actions.INameListener>();
	public void setListeners_INameListener(actions.INameListener listeners_INameListenerRequired, int index) { PortHelper.fill(this.listeners_INameListenerRequired, listeners_INameListenerRequired, index); }
	public void removeListeners_INameListener(actions.INameListener listeners_INameListenerRequired) { this.listeners_INameListenerRequired.remove(listeners_INameListenerRequired); }
	public actions.INameList getListeners_INameList(Class<?> required, int index) { return names_INameListProvided; }

	private Map<String, Boolean> items = new HashMap<String, Boolean>();
	private String selected;
	
	private class INameListNamesImpl implements actions.INameList
	{
		@Override
		public void addItem(String name, boolean enabled)
		{
			items.put(name, enabled);
		}

		@Override
		public Map<String, Boolean> getItems()
		{
			return items;
		}

		@Override
		public String getSelectedItem()
		{
			return selected;
		}

		@Override
		public void notifyListeners()
		{
			for (INameListener l : listeners_INameListenerRequired)
				if (l != null)
					l.haveNewNames();
		}

		@Override
		public void removeAllItems()
		{
			items.clear();
			selected = null;
		}

		@Override
		public void setSelectedItem(String choice)
		{
			selected = choice;
		}
	}
}
