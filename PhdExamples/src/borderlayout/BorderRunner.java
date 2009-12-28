package borderlayout;

import javax.swing.*;

import com.hopstepjump.backbone.runtime.api.*;

public class BorderRunner
{
// start generated code
	private com.hopstepjump.backbone.runtime.api.IRun run_IRunProvided = new IRunRunImpl();
	public com.hopstepjump.backbone.runtime.api.IRun getRun_IRun(Class<?> required) { return run_IRunProvided; }

	private javax.swing.JPanel panel_JPanelRequired;
	public void setPanel_JPanel(javax.swing.JPanel panel_JPanelRequired) { this.panel_JPanelRequired = panel_JPanelRequired; }
// end generated code


	private class IRunRunImpl implements IRun
	{
		public int run(String[] args)
		{
			JFrame f = new JFrame("Swing example");
			f.add(panel_JPanelRequired);
			f.pack();
			f.setVisible(true);
			return 0;
		}
	}
}
