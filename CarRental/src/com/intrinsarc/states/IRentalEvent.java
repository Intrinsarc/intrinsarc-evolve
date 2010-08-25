package com.intrinsarc.states;

import com.intrinsarc.backbone.runtime.api.*;
import com.intrinsarc.base.*;

public interface IRentalEvent extends IEvent, IRenterDetails
{
	void rent();
	void setRenterName(String name);
	public String getRenterName();
	void returnRental();
}
