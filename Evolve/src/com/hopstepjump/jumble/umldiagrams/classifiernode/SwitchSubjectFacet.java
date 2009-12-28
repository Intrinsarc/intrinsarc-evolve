package com.hopstepjump.jumble.umldiagrams.classifiernode;

import com.hopstepjump.gem.*;

public interface SwitchSubjectFacet extends Facet
{
	Object switchSubject(Object subject);
	void unSwitchSubject(Object memento);
}
