package statetest;

import com.intrinsarc.backbone.runtime.api.*;

public class Red
{
// start generated code
	private com.intrinsarc.backbone.runtime.api.ITransition in_ITransitionProvided = new ITransitionInImpl();
	public com.intrinsarc.backbone.runtime.api.ITransition getIn_ITransition(Class<?> required) { return in_ITransitionProvided; }

	private com.intrinsarc.backbone.runtime.api.ITransition out_ITransitionRequired;
	public void setOut_ITransition(com.intrinsarc.backbone.runtime.api.ITransition out_ITransitionRequired) { this.out_ITransitionRequired = out_ITransitionRequired; }

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
			signals_ITrafficSignalRequired.red();
			return true;
		}
	}

	private class ITrafficEventEventsImpl implements statetest.ITrafficEvent
	{

		public void go()
		{
			current = !out_ITransitionRequired.enter();
		}

		public void pause()
		{
			// ignore
		}

		public void stop()
		{
			// ignore
		}

		public void turnOff()
		{
			// ignore
		}

		public boolean isCurrent()
		{
			return current;
		}
	}
}
