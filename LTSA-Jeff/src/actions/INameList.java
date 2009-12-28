package actions;

import java.util.*;


public interface INameList
{
	public String getSelectedItem();
	public void removeAllItems();
	public void addItem(String name, boolean enabled);
	public void setSelectedItem(String oldChoice);
	public void notifyListeners();
	public Map<String, Boolean> getItems();
}
