package com.intrinsarc.deltaengine.errorchecking;

import com.intrinsarc.deltaengine.base.*;

public interface IDetectorListener
{
	void haveChecked(DEStratum perspective, DEStratum current);
}
