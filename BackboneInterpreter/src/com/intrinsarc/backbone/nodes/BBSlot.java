package com.intrinsarc.backbone.nodes;

import java.io.*;
import java.util.*;

import com.intrinsarc.backbone.nodes.lazy.*;
import com.intrinsarc.backbone.parserbase.*;
import com.intrinsarc.deltaengine.base.*;

public class BBSlot extends DESlot implements INode, Serializable
{
	private DEObject parent;
	private LazyObject<DEAttribute> attribute = new LazyObject<DEAttribute>(DEAttribute.class);
	private LazyObject<DEAttribute> environmentAlias;
	private List<DEParameter> value;
	private List<DEAppliedStereotype> appliedStereotypes;

	public BBSlot(UuidReference attribute, List<DEParameter> value)
	{
		this.attribute.setReference(attribute);
		this.value = value;
	}
	
	public BBSlot(DEAttribute attr, DEAttribute alias)
	{
		this.attribute.setObject(attr);
		this.environmentAlias = new LazyObject<DEAttribute>(DEAttribute.class, alias);
	}
	
	public BBSlot(UuidReference attribute, UuidReference environmentAlias)
	{
		this.attribute.setReference(attribute);
		this.environmentAlias = new LazyObject<DEAttribute>(DEAttribute.class, environmentAlias);
	}
	
  @Override
	public DEAttribute getAttribute()
	{
		return attribute.getObject();
	}

	@Override
	public DEAttribute getEnvironmentAlias()
	{
		if (environmentAlias == null)
			return null;
		return environmentAlias.getObject();
	}

	@Override
	public List<DEParameter> getValue()
	{
		return value;
	}

	@Override
	public boolean isAliased()
	{
		return getEnvironmentAlias() != null;
	}

	@Override
	public String getName()
	{
		return "";
	}

	@Override
	public DEObject getParent()
	{
		return parent;
	}

	@Override
	public Object getRepositoryObject()
	{
		return this;
	}

	@Override
	public String getUuid()
	{
		return "";
	}

	public void setParent(DEObject parent)
	{
		this.parent = parent;
	}

	public void setAppliedStereotypes(List<DEAppliedStereotype> appliedStereotypes)
	{
		this.appliedStereotypes = appliedStereotypes.isEmpty() ? null : appliedStereotypes;
	}

	@Override
	public List<DEAppliedStereotype> getAppliedStereotypes()
	{
		return appliedStereotypes == null ? new ArrayList<DEAppliedStereotype>() : appliedStereotypes;
	}

	@Override
	public void resolveLazyReferences()
	{
		if (appliedStereotypes != null)
			for (DEAppliedStereotype app : appliedStereotypes)
				app.resolveLazyReferences();
		attribute.resolve();
		if (environmentAlias != null)
			environmentAlias.resolve();
		if (value != null)
			for (DEParameter p : value)
				p.resolveLazyReferences();
	}	
}
