package com.hopstepjump.uml2deltaengine.converters;

import com.hopstepjump.uml2deltaengine.*;
import com.thoughtworks.xstream.*;

public class UML2XStreamConverters
{
  public static void registerConverters(XStream x)
  {
  	x.processAnnotations(UML2Stratum.class);
  	x.processAnnotations(UML2Component.class);
  	x.processAnnotations(UML2Interface.class);
  	x.processAnnotations(UML2Attribute.class);
  	x.processAnnotations(UML2Port.class);
  	x.processAnnotations(UML2Part.class);
  	x.processAnnotations(UML2Connector.class);
  	x.processAnnotations(UML2Operation.class);
  	x.processAnnotations(UML2Slot.class);
  	x.processAnnotations(UML2AppliedStereotype.class);
  	
  	x.registerConverter(new UML2PackageConverter());
  	x.registerConverter(new UML2ComponentConverter());
  	x.registerConverter(new UML2InterfaceConverter());
   	x.registerConverter(new UML2AttributeConverter());
  	x.registerConverter(new UML2PortConverter());
  	x.registerConverter(new UML2PartConverter());
  	x.registerConverter(new UML2ConnectorConverter());
  	x.registerConverter(new UML2OperationConverter());
  	x.registerConverter(new UML2SlotConverter());
  	x.registerConverter(new UML2AppliedStereotypeConverter());
  }
}
