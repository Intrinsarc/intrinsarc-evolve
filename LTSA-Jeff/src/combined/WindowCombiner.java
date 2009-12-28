package combined;

import javax.swing.*;

import sun.awt.*;
import ui.*;

import lts.*;

import com.hopstepjump.backbone.runtime.api.*;

public class WindowCombiner
{
// start generated code
// attributes
// required ports
	private java.util.List<ui.IWindow> windows = new java.util.ArrayList<ui.IWindow>();
// provided ports
	private IWindowMainImpl main_IWindowProvided = new IWindowMainImpl();
// setters and getters
	public void setWindows_IWindow(ui.IWindow windows, int index) { PortHelper.fill(this.windows, windows, index); }
	public void removeWindows_IWindow(ui.IWindow windows) { PortHelper.remove(this.windows, windows); }
	public ui.IWindow getMain_IWindow(Class<?> required) { return main_IWindowProvided; }
// end generated code


	private class IWindowMainImpl implements ui.IWindow
	{

		@Override
		public void activate(CompositeState cs)
		{
			for (IWindow w : windows)
				w.activate(cs);
		}

		@Override
		public void copy()
		{
			for (IWindow w : windows)
				w.copy();
		}

		@Override
		public void deactivate()
		{
			for (IWindow w : windows)
				w.deactivate();
		}

		@Override
		public JComponent getComponent()
		{
			JPanel panel = new JPanel();
			BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
			panel.setLayout(layout);
			
			for (IWindow w : windows)
				panel.add(w.getComponent());
			return panel;
		}

		@Override
		public String getName()
		{
			return null;
		}

		@Override
		public boolean isActive()
		{
			return true;
		}

		@Override
		public void saveFile(String directory)
		{
			for (IWindow w : windows)
				w.saveFile(directory);
		}
	}
}
