package com.intrinsarc.backbone.nodes.simple.internal;

import java.util.*;

import com.intrinsarc.backbone.exceptions.*;
import com.intrinsarc.backbone.nodes.insides.*;
import com.intrinsarc.backbone.nodes.simple.*;
import com.intrinsarc.backbone.runtime.api.*;
import com.intrinsarc.deltaengine.base.*;


public class BBSimpleInstantiatedFactory
{
	private BBSimpleInstantiatedFactory parent;
	private BBSimpleFactory factory;
	private Map<BBSimpleAttribute, Attribute<? extends Object>> iattributes = new HashMap<BBSimpleAttribute, Attribute<? extends Object>>();
	private Map<BBSimplePart, Object> iparts = new HashMap<BBSimplePart, Object>();
	private Map<CachedConnectorEnd, Map<BBSimpleInterface, Object>> cachedProvides = new HashMap<CachedConnectorEnd, Map<BBSimpleInterface,Object>>();
	private ArrayList<BBSimpleInstantiatedFactory> children;
	
	public BBSimpleInstantiatedFactory(BBSimpleInstantiatedFactory parent, BBSimpleFactory factory)
	{
		this.parent = parent;
		if (parent != null)
			parent.addChild(this);
		this.factory = factory;
	}
	
	private void addChild(BBSimpleInstantiatedFactory child)
	{
		if (children == null)
			children = new ArrayList<BBSimpleInstantiatedFactory>();
		children.add(child);
	}
	
	private void childDestroyed(BBSimpleInstantiatedFactory child)
	{
		children.remove(child);
	}
	
	public void instantiate(Map<String, Object> suppliedParameters) throws BBRuntimeException
	{
		Set<String> setNames = suppliedParameters == null ? null : new HashSet<String>(suppliedParameters.keySet());
		
		// instantiate the attributes, possibly taking the values from the parameters supplied
		List<BBSimpleAttribute> attrs = factory.getAttributes();
		if (attrs != null)
			for (BBSimpleAttribute attr : attrs)
				iattributes.put(attr, new Attribute<Object>(attr.instantiate(this, suppliedParameters, setNames)));
		
		// we shouldn't have any names left over
		if (setNames != null && setNames.size() != 0)
			throw new BBRuntimeException("Found extra values " + setNames + " when instantiating factory " + factory, null);			
		
		// instantiate the parts in reverse order
		List<BBSimplePart> parts = factory.getParts();

		for (int lp = parts.size() - 1; lp >= 0; lp--)
		{
			BBSimplePart part = parts.get(lp);
			
			// instantiate the part
			Object obj = part.getType().makeNewInstance(this);
			iparts.put(part, obj);
			
			// set up the attributes
			for (BBSimpleSlot s : part.getSlots())
			{
				Attribute<?> simple = iattributes.get(s.getEnvironmentAlias());
				if (simple == null)
					simple = parent.resolveAttributeValue(s.getEnvironmentAlias());
				s.setValue(obj, simple, part.getType());
			}
		}
		
		// connect up all the parts
		// must connect this in a way which allows numbered indices first, on both sides
		// this means breaking apart the provided and required sides

		// cache the provided for each port
		List<BBSimpleConnectorEnd> ends = factory.getInternalSortedConnectorEnds();
		for (BBSimpleConnectorEnd end : ends)
		{
			Map<BBSimpleInterface, Object> cached = end.getConnector().getProvides(this, end.getSide());
			if (cached != null)
			{
				cachedProvides.put(new CachedConnectorEnd(end.getConnector(), end.getSide()), cached);
			}
		}
		
		// set the required for each port
		for (BBSimpleConnectorEnd end : ends)
		{
			Map<BBSimpleInterface, Object> cache = cachedProvides.get(new CachedConnectorEnd(end.getConnector(), 1 - end.getSide()));
			if (cache != null && !cache.keySet().isEmpty())
			{
				end.getConnector().setRequires(this, cache, end.getSide());
			}
		}
			
		// inform any lifecycle parts that we have initialized
		for (BBSimplePart p : iparts.keySet())
			if (p.getType().hasLifecycleCallbacks())
				((ILifecycle) iparts.get(p)).afterInit();
	}
	
	public Attribute<? extends Object> resolveAttributeValue(BBSimpleAttribute attr) throws BBRuntimeException
	{
		Attribute<? extends Object> value = iattributes.get(attr);
		if (value != null)
			return value;
		if (parent == null)
			throw new BBRuntimeException("Cannot find attribute value for " + attr, attr.getOwner());
		return parent.resolveAttributeValue(attr);
	}

	public Object getPartObject(BBSimplePart part) throws BBRuntimeException
	{
		Object actual = iparts.get(part);
		if (actual != null)
			return actual;
		if (parent == null)
			throw new BBRuntimeException("Cannot find part for " + part, part.getType());
		return parent.getPartObject(part);
	}

	public Object getPartObject(DEPart runPart) throws BBRuntimeException
	{
		for (BBSimplePart part : iparts.keySet())
			if (part.getComplexPart() == runPart)
				return iparts.get(part);
		throw new BBRuntimeException("Cannot find part for " + runPart, null);
	}

	public BBSimpleFactory getFactory(int factoryNumber)
	{
		return factory.getFactory(factoryNumber);
	}

	public void runViaPort(BBSimplePort provider, String[] args) throws BBRuntimeException, BBImplementationInstantiationException
	{
		// locate the connector that is connected to this port
		for (BBSimpleConnector conn : factory.getConnectors())
		{
			int end = conn.getEndNumber(null, provider);
			if (end != -1)
			{
				BBSimpleConnectorEnd connEnd = conn.makeSimpleConnectorEnd(1 - end);
				BBSimplePart part = connEnd.getPart();
				Object object = iparts.get(part);
				ReflectivePort field = BBSimpleConnector.getPortField(part.getType(), connEnd.getPort(), IRun.class, true);
				((IRun) field.getSingle(IRun.class, object)).run(args);
			}
		}
	}

	public void destroy() throws BBRuntimeException
	{
		// tell the parent
		if (parent != null)
			parent.childDestroyed(this);
		
		// clear any children in reverse order
		if (children != null)
		{
			int size = children.size();
			List<BBSimpleInstantiatedFactory> copy = new ArrayList<BBSimpleInstantiatedFactory>(children);
			for (int lp = size - 1; lp >= 0; lp--)
				copy.get(0).destroy();
		}
		
		// inform any lifecycle parts that we are about to delete them
		for (BBSimplePart p : iparts.keySet())
			if (p.getType().hasLifecycleCallbacks())
				((ILifecycle) iparts.get(p)).beforeDelete();		
		
		// clear out any connectors to detach this from the rest of the network and effectively destroy it
		// set the required for each port
		Set<BBSimplePort> ports = new HashSet<BBSimplePort>();
		for (BBSimpleConnector c : factory.getConnectors())
			for (int lp = 0; lp < 2; lp++)
				ports.add(c.makeSimpleConnectorEnd(lp).getPort());

		for (int pass = 0; pass < 2; pass++)
			for (BBSimplePort port : ports)
				for (int side = 0; side < 2; side++)
				{
					for (BBSimpleConnector conn : factory.getConnectors())
					{
						if (!conn.isRunConnector())
						{
							BBSimpleConnectorEnd end = conn.makeSimpleConnectorEnd(side);
							Map<BBSimpleInterface, Object> cache = cachedProvides.get(new CachedConnectorEnd(end.getConnector(), 1 - end.getSide()));
							if (end.getPort() == port && (pass == 0 && !end.isTakeNext() || pass == 1 && end.isTakeNext()))
								end.getConnector().clearRequires(this, cache, end.getSide());
						}
					}
				}
	}
}
