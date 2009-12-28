package ui;

import javax.swing.*;

import lts.*;

public interface IWindow
{
	public String getName();
	public boolean isActive();

	public void activate(CompositeState cs);
	public JComponent getComponent();
	public void copy();
	public void saveFile(String directory);
	
	public void deactivate();
}
