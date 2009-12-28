package test;

import java.awt.*;

import com.hopstepjump.backbone.runtime.api.*;


public class BorderLayoutHandler implements ILifecycle
{
// start generated code

	private java.awt.Container handler_ContainerRequired;
	public void setHandler_Container(java.awt.Container handler_ContainerRequired) { this.handler_ContainerRequired = handler_ContainerRequired; }

	private test.ILogger logger_ILoggerRequired;
	public void setLogger_ILogger(test.ILogger logger_ILoggerRequired) { this.logger_ILoggerRequired = logger_ILoggerRequired; }
// end generated code
	private boolean setAsLayout;
	private BorderLayout layout = new BorderLayout();
	private Component north, south, east, west, center;
	
	private void setAsLayout()
	{
		if (!setAsLayout)
			handler_ContainerRequired.setLayout(layout);
		setAsLayout = true;
	}

	public void setNorth_Component(java.awt.Component set)
	{
		setAsLayout();
		if (north != null)
			handler_ContainerRequired.remove(north);
		north = set;
		handler_ContainerRequired.add(north, BorderLayout.NORTH);
	}

	public void setSouth_Component(java.awt.Component set)
	{
		setAsLayout();
		if (south != null)
			handler_ContainerRequired.remove(south);
		south = set;
		handler_ContainerRequired.add(south, BorderLayout.SOUTH);
	}

	public void setEast_Component(java.awt.Component set)
	{
		setAsLayout();
		if (east != null)
			handler_ContainerRequired.remove(east);
		east = set;
		handler_ContainerRequired.add(east, BorderLayout.EAST);
	}

	public void setWest_Component(java.awt.Component set)
	{
		setAsLayout();
		if (west != null)
			handler_ContainerRequired.remove(west);
		west = set;
		handler_ContainerRequired.add(west, BorderLayout.WEST);
	}

	public void setCenter_Component(java.awt.Component set)
	{
		setAsLayout();
		if (center != null)
			handler_ContainerRequired.remove(center);
		center = set;
		handler_ContainerRequired.add(center, BorderLayout.CENTER);
	}

	public void afterInit()
	{
		if (logger_ILoggerRequired != null)
			logger_ILoggerRequired.log("This is from border layout!");
		else
			System.out.println("$$ no logger found!");
	}

	public void beforeDelete()
	{
	}
}
