package statetest;

import javax.swing.*;

import com.intrinsarc.backbone.runtime.api.*;

public class TrafficRunner
{
// start generated code
	private com.intrinsarc.backbone.runtime.api.IRun run_IRunProvided = new IRunRunImpl();
	public com.intrinsarc.backbone.runtime.api.IRun getRun_IRun(Class<?> required) { return run_IRunProvided; }

	private javax.swing.JPanel panel_JPanelRequired;
	public void setPanel_JPanel(javax.swing.JPanel panel_JPanelRequired) { this.panel_JPanelRequired = panel_JPanelRequired; }
// end generated code


	private class IRunRunImpl implements IRun
	{
		public int run(String[] args)
		{
			JFrame frame = new JFrame("Traffic Simulator");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(panel_JPanelRequired);
			frame.pack();
			frame.setVisible(true);
			return 0;
		}
	}

}
