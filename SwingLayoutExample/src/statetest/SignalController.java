package statetest;

import java.awt.*;

import com.hopstepjump.backbone.runtime.api.*;


public class SignalController implements ILifecycle
{
// start generated code
	private Attribute<String> name = new Attribute<String>("hello");
	public Attribute<String> getName() { return name; }
	public void setName(Attribute<String> name) { this.name = name;}

	private javax.swing.JPanel panel_JPanelRequired;
	public void setPanel_JPanel(javax.swing.JPanel panel_JPanelRequired) { this.panel_JPanelRequired = panel_JPanelRequired; }
	private statetest.ITrafficSignal signal_ITrafficSignalProvided = new ITrafficSignalSignalImpl();
	public statetest.ITrafficSignal getSignal_ITrafficSignal(Class<?> required) { return signal_ITrafficSignalProvided; }

	private com.hopstepjump.backbone.runtime.api.ITransition in_ITransitionRequired;
	public void setIn_ITransition(com.hopstepjump.backbone.runtime.api.ITransition in_ITransitionRequired) { this.in_ITransitionRequired = in_ITransitionRequired; }
// end generated code


	private class ITrafficSignalSignalImpl implements statetest.ITrafficSignal
	{
		public void green()
		{
			panel_JPanelRequired.setBackground(Color.GREEN);
		}

		public void off()
		{
			panel_JPanelRequired.setBackground(Color.DARK_GRAY);
		}

		public void orange()
		{
			panel_JPanelRequired.setBackground(Color.ORANGE);
		}

		public void red()
		{
			panel_JPanelRequired.setBackground(Color.RED);
		}
	}


	public void afterInit()
	{
		panel_JPanelRequired.setPreferredSize(new Dimension(400, 400));
		in_ITransitionRequired.enter();
	}


	public void beforeDelete()
	{
	}
}
