package statetest;

import com.intrinsarc.backbone.runtime.api.*;

public class Green
{
// start generated code
	private com.intrinsarc.backbone.runtime.api.ITransition in_ITransitionProvided = new ITransitionInImpl();
	public com.intrinsarc.backbone.runtime.api.ITransition getIn_ITransition(Class<?> required) { return in_ITransitionProvided; }

	private com.intrinsarc.backbone.runtime.api.ITransition out_ITransitionRequired;
	public void setOut_ITransition(com.intrinsarc.backbone.runtime.api.ITransition out_ITransitionRequired) { this.out_ITransitionRequired = out_ITransitionRequired; }

	private com.intrinsarc.backbone.runtime.api.ITransition turnOff_ITransitionRequired;
	public void setTurnOff_ITransition(com.intrinsarc.backbone.runtime.api.ITransition turnOff_ITransitionRequired) { this.turnOff_ITransitionRequired = turnOff_ITransitionRequired; }

	private statetest.ITrafficSignal signals_ITrafficSignalRequired;
	public void setSignals_ITrafficSignal(statetest.ITrafficSignal signals_ITrafficSignalRequired) { this.signals_ITrafficSignalRequired = signals_ITrafficSignalRequired; }
	private statetest.ITrafficEvent events_ITrafficEventProvided = new ITrafficEventEventsImpl();
	public statetest.ITrafficEvent getEvents_ITrafficEvent(Class<?> required) { return events_ITrafficEventProvided; }
// end generated code
	private boolean current;

	private class ITransitionInImpl implements ITransition
	{
		public boolean enter()
		{
			current = true;
			signals_ITrafficSignalRequired.green();
			return true;
		}
	}

	private class ITrafficEventEventsImpl implements statetest.ITrafficEvent
	{
		public void go()
		{
			// ignore
		}

		public void pause()
		{
			// ignore
		}

		public void stop()
		{
			current = !out_ITransitionRequired.enter();
		}

		public void turnOff()
		{
			current = !turnOff_ITransitionRequired.enter();			
			signals_ITrafficSignalRequired.off();
		}

		public boolean isCurrent()
		{
			return current;
		}
	}

}
