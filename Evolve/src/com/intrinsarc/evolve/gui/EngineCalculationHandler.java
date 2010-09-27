package com.intrinsarc.evolve.gui;

import java.text.*;
import java.util.*;

import javax.swing.*;

public class EngineCalculationHandler
{
	private static Calendar WHEN = new GregorianCalendar(2010, Calendar.NOVEMBER, 1); 
	
	public static void checkCalculation()
	{
		if (new Date().getTime() > WHEN.getTimeInMillis())
		{
    	Object[] options = {"Exit"};
    	JOptionPane.showOptionDialog(null,
    	    "Please update to a newer beta at www.intrinsarc.com, or consider a purchase.",
    	    "Evolve beta copy expired on " + stringify(),
    	    JOptionPane.OK_OPTION,
    	    JOptionPane.ERROR_MESSAGE,
    	    null,
    	    options,
    	    options[0]);
    	System.exit(-1);
		}
	}
	
	private static String stringify()
	{
		DateFormat df = DateFormat.getDateInstance();
    return df.format(WHEN.getTime());
	}
}
