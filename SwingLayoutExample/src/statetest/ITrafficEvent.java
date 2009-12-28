package statetest;

import com.hopstepjump.backbone.runtime.api.*;

public interface ITrafficEvent extends IEvent
{
	void go();
	void pause();
	void stop();
	void turnOff();
}
