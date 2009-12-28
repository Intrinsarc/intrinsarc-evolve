package com.hopstepjump.backbone.nodes.converters;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.*;

public class BBXStreamConverters
{
  // single references
  public static class ComponentReferenceConverter extends ReferenceConverter
  {
  	public ComponentReferenceConverter()
  	{ super(BBComponent.class); }

		@Override
		public Object cast(DEObject obj)
		{
			return new DEComponent[]{obj.asComponent()};
		}
	};
  public static class PortReferenceConverter extends ReferenceConverter
  {
  	public PortReferenceConverter()
  	{ super(BBPort.class); }
  	
		@Override
		public Object cast(DEObject obj)
		{
			return new DEPort[]{obj.asConstituent().asPort()};
		}
  };
  public static class PartReferenceConverter extends ReferenceConverter
  {
  	public PartReferenceConverter()
  	{ super(BBPart.class); }
  	
		@Override
		public Object cast(DEObject obj)
		{
			return new DEPart[]{obj.asConstituent().asPart()};
		}
  };
  public static class ElementReferenceConverter extends ReferenceConverter
  {
  	public ElementReferenceConverter()
  	{ super(DEElement.class); }
  	
		@Override
		public Object cast(DEObject obj)
		{
			return new DEElement[]{obj.asElement()};
		}
  };
  public static class AttributeReferenceConverter extends ReferenceConverter
  {
  	public AttributeReferenceConverter()
  	{ super(DEAttribute.class); }
  	
		@Override
		public Object cast(DEObject obj)
		{
			return new DEAttribute[]{obj.asConstituent().asAttribute()};
		}
  };
  
  // lists
  public static class ComponentReferencesConverter extends ReferencesConverter  { public ComponentReferencesConverter() { super(BBComponent.class); } };
  public static class InterfaceReferencesConverter extends ReferencesConverter  { public InterfaceReferencesConverter() { super(BBInterface.class); } };
  public static class PackageReferencesConverter   extends ReferencesConverter  { public PackageReferencesConverter()   { super(BBStratum.class); } };
  public static class ConstituentReferencesConverter extends ReferencesConverter  { public ConstituentReferencesConverter()   { super(DEConstituent.class); } };
  
  public static void registerConverters(XStream x)
  {
  	x.processAnnotations(BBLoadList.class);
    x.processAnnotations(BBStratumDirectory.class);
    x.processAnnotations(BBStratum.class);
    x.processAnnotations(BBComponent.class);
    x.processAnnotations(BBInterface.class);
    x.processAnnotations(BBAttribute.class);
    x.processAnnotations(BBPort.class);
    x.processAnnotations(BBPart.class);
    x.processAnnotations(BBConnector.class);
    x.processAnnotations(BBOperation.class);
    x.processAnnotations(BBAppliedStereotype.class);
    
    // insides too
  	x.processAnnotations(BBReplacedOperation.class);
  	x.processAnnotations(BBReplacedAttribute.class);
  	x.processAnnotations(BBReplacedPort.class);
  	x.processAnnotations(BBReplacedPart.class);
  	x.processAnnotations(BBReplacedConnector.class);
  	x.processAnnotations(BBPortRemap.class);
  	x.processAnnotations(BBSlot.class);
  	x.processAnnotations(BBParameter.class);
  }
}
