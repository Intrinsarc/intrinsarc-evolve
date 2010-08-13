package extend;

import javax.swing.*;

import lts.*;

import com.intrinsarc.backbone.runtime.api.*;

public class WindowManager
{
// start generated code
// attributes
	private Attribute<java.lang.String> name;
// required ports
	private ui.IWindow window;
	private com.intrinsarc.backbone.runtime.api.ICreate create;
// provided ports
	private IWindowProxyImpl proxy_IWindowProvided = new IWindowProxyImpl();
// setters and getters
	public Attribute<java.lang.String> getName() { return name; }
	public void setName(Attribute<java.lang.String> name) { this.name = name;}
	public void setRawName(java.lang.String name) { this.name.set(name);}
	public void setWindow_IWindow(ui.IWindow window) { this.window = window; }
	public void setCreate_ICreate(com.intrinsarc.backbone.runtime.api.ICreate create) { this.create = create; }
	public ui.IWindow getProxy_IWindow(Class<?> required) { return proxy_IWindowProvided; }
// end generated code
	private Object createHandle;
	

	private class IWindowProxyImpl implements ui.IWindow
	{
		@Override
		public void activate(CompositeState cs)
		{
			// create it
			createHandle = create.create(null);
			window.activate(cs);
		}

		@Override
		public void copy()
		{
			window.copy();
		}

		@Override
		public void deactivate()
		{
			window.deactivate();
			create.destroy(createHandle);
		}

		@Override
		public JComponent getComponent() {
			return window.getComponent();
		}

		@Override
		public String getName() {
			return name.get();
		}

		@Override
		public boolean isActive() {
			return window != null;
		}

		@Override
		public void saveFile(String directory) {
			window.saveFile(directory);
		}
	}
}
