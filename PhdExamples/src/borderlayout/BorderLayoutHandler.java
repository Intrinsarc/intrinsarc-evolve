package borderlayout;

import java.awt.*;

import com.hopstepjump.backbone.runtime.api.*;

public class BorderLayoutHandler implements ILifecycle
{
// start generated code

	private java.awt.Container handler_ContainerRequired;
	public void setHandler_Container(java.awt.Container handler_ContainerRequired) { this.handler_ContainerRequired = handler_ContainerRequired; }

	private java.awt.Component east_ComponentRequired;
	public void setEast_Component(java.awt.Component east_ComponentRequired) { this.east_ComponentRequired = east_ComponentRequired; }

	private java.awt.Component north_ComponentRequired;
	public void setNorth_Component(java.awt.Component north_ComponentRequired) { this.north_ComponentRequired = north_ComponentRequired; }

	private java.awt.Component south_ComponentRequired;
	public void setSouth_Component(java.awt.Component south_ComponentRequired) { this.south_ComponentRequired = south_ComponentRequired; }

	private java.awt.Component center_ComponentRequired;
	public void setCenter_Component(java.awt.Component center_ComponentRequired) { this.center_ComponentRequired = center_ComponentRequired; }

	private java.awt.Component west_ComponentRequired;
	public void setWest_Component(java.awt.Component west_ComponentRequired) { this.west_ComponentRequired = west_ComponentRequired; }

	private java.awt.BorderLayout layout_BorderLayoutRequired;
	public void setLayout_BorderLayout(java.awt.BorderLayout layout_BorderLayoutRequired) { this.layout_BorderLayoutRequired = layout_BorderLayoutRequired; }
// end generated code
	
	public void afterInit()
	{
		handler_ContainerRequired.setLayout(layout_BorderLayoutRequired);
		if (north_ComponentRequired != null)
			handler_ContainerRequired.add(north_ComponentRequired, BorderLayout.NORTH);
		if (south_ComponentRequired != null)
			handler_ContainerRequired.add(south_ComponentRequired, BorderLayout.SOUTH);
		if (east_ComponentRequired != null)
			handler_ContainerRequired.add(east_ComponentRequired, BorderLayout.EAST);
		if (west_ComponentRequired != null)
			handler_ContainerRequired.add(west_ComponentRequired, BorderLayout.WEST);
		if (center_ComponentRequired != null)
			handler_ContainerRequired.add(center_ComponentRequired, BorderLayout.CENTER);
	}

	public void beforeDelete()
	{
	}
}
