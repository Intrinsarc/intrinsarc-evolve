package com.intrinsarc.hardcoded;

public class HardcodedCarsExampleRunner
{
	public static void main(String args[])
	{
		CarsExampleFactory factory = new CarsExampleFactory();
		
		// initialise the factory, invoke all afterInit() callbacks, and run the example
		factory.initialize(null, null).getRun_Provided().run(args);
		
		// invoke all the beforeDelete() lifecycle callbacks, and shut down the factory 
		factory.destroy();
	}
}
