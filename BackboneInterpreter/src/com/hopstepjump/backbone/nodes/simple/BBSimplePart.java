package com.hopstepjump.backbone.nodes.simple;

import java.util.*;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("Part")
public class BBSimplePart extends BBSimpleObject
{
	@XStreamAsAttribute
	private String name;
	private transient String rawName;
	private transient int depth;
	private transient BBSimpleLevel level;
	@XStreamConverter(SimpleReferenceConverter.class)
	private BBSimpleComponent type;
	private List<BBSimpleSlot> slots;
	private transient boolean resolved;
	private transient DEPart complex;
	private transient int factory;
	private transient BBSimpleComponent owner;
	private transient List<DEPart> partStack;
	private transient Map<BBSimplePort, Map<String, Class<?>>> allProvidedToRequired;
	private transient Map<BBSimplePort, Map<String, String>> allProvidedToRequiredNames;	
	
	public BBSimplePart(BBSimpleElementRegistry registry, BBSimpleComponent component, DEPart complex, BBSimpleComponent owner)
	{
		rawName = complex.getName();
		name = registry.makeName(rawName);
		this.complex = complex;
		this.type = complex.getType() == null ? null : registry.retrieveComponent(complex.getType());
		
		// copy over the slots
		List<DESlot> cslots = complex.getSlots();
		if (!cslots.isEmpty())
		{
			slots = new ArrayList<BBSimpleSlot>();
			for (DESlot slot : cslots)
				slots.add(new BBSimpleSlot(registry, component, complex.getType(), slot));
		}
		this.owner = owner;
		partStack = new ArrayList<DEPart>();
		partStack.add(complex);
	}

	public BBSimplePart(BBSimpleElementRegistry registry, BBSimplePart p, BBSimplePart enclosingPart, Map<BBSimpleAttribute, BBSimpleAttribute> copied, int depth, int factory, int parentFactory, String fullName, DEComponent top)
	{
		partStack = new ArrayList<DEPart>();
		partStack.add(p.complex);
		partStack.addAll(enclosingPart.partStack);
		rawName = p.getRawName();
		name = registry.makeName(fullName == null ? "" : fullName + " :: ") + rawName;
		complex = p.complex;
		type = p.type;
		owner = p.owner;
		this.depth = depth;
		this.factory = p.complex.isPullUp() ? parentFactory : factory;
		
		// make new slots that reference the flattened attributes
		if (!p.getSlots().isEmpty())
		{
			slots = new ArrayList<BBSimpleSlot>();
			for (BBSimpleSlot s : p.slots)
				slots.add(new BBSimpleSlot(registry, s, copied));
		}		
		
		try
		{
			resolveProvidedToRequiredNames(registry, true, false);
		}
		catch (BBImplementationInstantiationException e)
		{
			// not actually thrown for resolving names only
		}
	}

	private void resolveProvidedToRequiredNames(BBSimpleElementRegistry registry, boolean names, boolean classes) throws BBImplementationInstantiationException
	{
		DEStratum perspective = registry.getPerspective();
		
		if (names)
			 allProvidedToRequiredNames = new HashMap<BBSimplePort, Map<String, String>>();
		if (classes)
			 allProvidedToRequired = new HashMap<BBSimplePort, Map<String, Class<?>>>();
		
		// for each port, get the required interfaces that the provided interfaces must satisfy
		if (type.getPorts() != null)
		{
			for (BBSimplePort p : type.getPorts())
			{
				Set<? extends DEInterface> reqs =
					registry.getTopComponent().getPortProvidedInterfacesInCompositionHierarchy(
								perspective,
								p.getComplexPort(),
								partStack);
				
				Map<String, String> providedToRequiredNames = new HashMap<String, String>();
				Map<String, Class<?>> providedToRequired = new HashMap<String, Class<?>>();
				for (BBSimpleInterface prov : p.getProvides())
				{
					DEInterface req = DEComponent.getOneMatch(perspective, prov.getComplex(), reqs);
					if (req != null)
					{
						String provClassName = prov.getImplementationClassName();
						if (names)
						{
							providedToRequiredNames.put(
									provClassName,
											req.getImplementationClass(perspective));
						}
						if (classes)
						{
							providedToRequired.put(
									provClassName,
										getImplementation(
											req.getImplementationClass(perspective), req));
						}
					}
				}
				if (names && !providedToRequiredNames.isEmpty())
					allProvidedToRequiredNames.put(p, providedToRequiredNames);
				if (classes && !providedToRequired.isEmpty())
					allProvidedToRequired.put(p, providedToRequired);
			}
		}
	}
	
	public void resolveImplementation(BBSimpleElementRegistry registry) throws BBImplementationInstantiationException
	{
		if (resolved)
			return;
		resolved = true;
		
		type.resolveImplementation(registry);
		if (slots != null)
			for (BBSimpleSlot slot : slots)
				slot.resolveImplementation(registry, type.getImplementationClass(), owner);
		
		// for each port, get the required interfaces that the provided interfaces must satisfy
		resolveProvidedToRequiredNames(registry, false, true);
	}

	private Class<?> getImplementation(String className, DEInterface iface) throws BBImplementationInstantiationException
	{
		try
		{
			return Class.forName(className);
		}
		catch (ClassNotFoundException e)
		{
			throw new BBImplementationInstantiationException(
					"Cannot locate class " + className + " for interface " + iface.getName(), owner);
		} 
	}

	public DEPart getComplexPart()
	{
		return complex;
	}

	public BBSimpleComponent getType()
	{
		return type;
	}

	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public String getRawName()
	{
		return rawName;
	}

	public int getDepth()
	{
		return depth;
	}

	public int getFactory()
	{
		return factory;
	}

	public BBSimpleSlot findSlot(BBSimpleAttribute attr)
	{
		if (slots == null)
			return null;
		for (BBSimpleSlot slot : slots)
			if (slot.getAttribute() == attr)
				return slot;
		return null;
	}

	public List<BBSimpleSlot> getSlots()
	{
		if (slots == null)
			return new ArrayList<BBSimpleSlot>();
		return slots;
	}
	
	public void remapAttributes(Map<BBSimpleAttribute, BBSimpleAttribute> redundant)
	{
		if (slots != null)
			for (BBSimpleSlot s : slots)
				s.remapAttributes(redundant);
	}

	public void setSlots(List<BBSimpleSlot> slots)
	{
		this.slots = slots;
	}

	public void setFactory(int factory)
	{
		this.factory = factory;
	}

	public String getRequiredImplementationNameForProvided(BBSimpleElementRegistry registry, BBSimplePort simplePort, BBSimpleInterface provided)
	{
		if (allProvidedToRequiredNames == null)
		{
			try
			{
				resolveProvidedToRequiredNames(registry, true, false);
			}
			catch (BBImplementationInstantiationException e)
			{
				// not actually thrown for resolving names only
			}
		}
		
		if (!allProvidedToRequiredNames.containsKey(simplePort))
			return null;
		return allProvidedToRequiredNames.get(simplePort).get(provided.getImplementationClassName());
	}

	public Class<?> getRequiredImplementationForProvided(BBSimplePort simplePort, BBSimpleInterface provided)
	{
		if (!allProvidedToRequired.containsKey(simplePort))
			return null;
		return allProvidedToRequired.get(simplePort).get(provided.getImplementationClassName());
	}

	public void setLevel(BBSimpleLevel level)
	{
		this.level = level;
	}
	
	public BBSimpleLevel getLevel()
	{
		return level;
	}
}
