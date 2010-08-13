package test;

import javax.swing.*;

import com.intrinsarc.backbone.runtime.api.*;

public class Gui2Runner
{
// start generated code
	private Attribute<String> title;
	public Attribute<String> getTitle() { return title; }
	public void setTitle(Attribute<String> title) { this.title = title;}
	private com.intrinsarc.backbone.runtime.api.IRun run_IRunProvided = new IRunRunImpl();
	public com.intrinsarc.backbone.runtime.api.IRun getRun_IRun(Class<?> required) { return run_IRunProvided; }

	private javax.swing.JComponent component_JComponentRequired;
	public void setComponent_JComponent(javax.swing.JComponent component_JComponentRequired) { this.component_JComponentRequired = component_JComponentRequired; }
// end generated code

	private class IRunRunImpl implements IRun
	{
		public int run(String[] args)
		{
			JFrame frame = new JFrame(title.get());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(component_JComponentRequired);
			frame.pack();
			frame.setVisible(true);
			return 0;
		}
	}

}
