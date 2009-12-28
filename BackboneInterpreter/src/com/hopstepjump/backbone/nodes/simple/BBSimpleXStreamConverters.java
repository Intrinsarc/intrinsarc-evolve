package com.hopstepjump.backbone.nodes.simple;

import com.thoughtworks.xstream.*;

public class BBSimpleXStreamConverters
{
	public static void registerConverters(XStream x)
	{
		x.setMode(XStream.NO_REFERENCES);
  	x.processAnnotations(BBSimpleComponent.class);
  	x.processAnnotations(BBSimpleInterface.class);
  	x.processAnnotations(BBSimpleAttribute.class);
  	x.processAnnotations(BBSimplePort.class);
  	x.processAnnotations(BBSimplePart.class);
  	x.processAnnotations(BBSimpleConnector.class);
  	x.processAnnotations(BBSimpleSlot.class);
	}
}
