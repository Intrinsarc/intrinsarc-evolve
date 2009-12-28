package lts;

import java.util.*;

import lts.ltl.*;


public interface IAnalyser
{
	void activate(CompositeState current, LTSOutput outputImpl, boolean ignoreAsterisk);
	CompactState composeNoHide();
	void analyse();
	void analyse(FluentTrace tracer);
	List getErrorTrace();
	IAnimator getAnimator();
}
