package com.intrinsarc.evolve.gui;

import java.text.*;
import java.util.*;

public class BetaExpiryHandler
{
	private static Calendar EXPIRES = new GregorianCalendar(2010, Calendar.OCTOBER, 15); 
	
	public static boolean isExpired()
	{
		return new Date().getTime() > EXPIRES.getTimeInMillis();
	}
	
	public static String getExpiryDate()
	{
		DateFormat df = DateFormat.getDateInstance();
    return df.format(EXPIRES.getTime());
	}
}
