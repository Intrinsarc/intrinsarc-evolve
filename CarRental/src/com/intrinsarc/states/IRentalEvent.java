package com.intrinsarc.states;

import java.util.*;

import com.intrinsarc.backbone.runtime.api.*;
import com.intrinsarc.cars.*;

public interface IRentalEvent extends IEvent, IRenterDetails
{
	void rent();
	void setRenter(String name, Date whenTo);
	void returnRental();
}
