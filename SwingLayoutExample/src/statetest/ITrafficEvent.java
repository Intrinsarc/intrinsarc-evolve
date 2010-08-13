package statetest;

import com.intrinsarc.backbone.runtime.api.*;

public interface ITrafficEvent extends IEvent
{
	void go();
	void pause();
	void stop();
	void turnOff();
}
