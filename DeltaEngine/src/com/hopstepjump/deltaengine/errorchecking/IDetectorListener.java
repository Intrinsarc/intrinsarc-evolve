package com.hopstepjump.deltaengine.errorchecking;

import com.hopstepjump.deltaengine.base.*;

public interface IDetectorListener
{
	void haveChecked(DEStratum perspective, DEStratum current);
}
