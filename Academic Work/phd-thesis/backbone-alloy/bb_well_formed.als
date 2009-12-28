module bb_well_formed

open bb_structure


-- check that the interface is well formed
pred interfaceIsWellFormed(s: Stratum, i: Interface)
{
	-- should have only 1 operation definition per id
	-- rule: WF_INTERFACE_OPERATION_PER_UUID
	i.myOperations.oneObjectPerID[s]
	-- we should only have one implementation, so if we resemble something
	-- we must replace the implementation
	-- rule: WF_INTERFACE_ONE_IMPLEMENTATION
	one i.iimplementation.s
}

-- check that the primitive type is well formed
pred primitiveTypeIsWellFormed(s: Stratum, t: PrimitiveType)
{
  -- rule: WF_PRIMITIVE_IMPLEMENTATION
	one t.timplementation.s
}

-- check that the component is well formed
pred componentIsWellFormed(s: Stratum, c: Component)
{
	-- the original (either the thing being replaced or the original)
	-- is not in the composition hierarchy taking resemblance into account
	-- NOTE: if c cannot be composed if it is a replacement
	-- NOTE: a further constraint is that we cannot be composed of the thing we
	--       are replacing
	let
		resembling = resembles_e.s, partTypes = parts.s.partType,
		original = no c.replaces => c else c.replaces
	{
	  -- rule: WF_COMPONENT_NO_SELF_COMPOSITION
		original not in c.*(resembling + partTypes).partTypes
	}
	-- rule: WF_COMPONENT_PORTS
	some c.ports.s
	
	-- rule: WF_COMPONENT_PORT_PER_UUID -- max of one port per ID
	c.myPorts.oneObjectPerID[s]
	-- rule: WF_COMPONENT_ATTRIBUTE_PER_UUID -- max of one attr per ID
	c.myAttributes.oneObjectPerID[s]
	
	// if this is composite, ensure the ports, parts and connectors are well formed
	s in c.isComposite =>
	{
		-- note: we will always have parts because that is what defines a composite
		-- no implementation allowed: must be deleted or not there to begin with to be a well formed composite
		no c.cimplementation.s
		-- rule: WF_COMPONENT_LINKS_OK -- no links allowed
		no c.links.s
	
		-- rule: WF_COMPONENT_PART_PER_UUID -- max of one part per ID
		c.myParts.oneObjectPerID[s]
		-- rule: WF_COMPONENT_CONNECTOR_PER_UUID -- max of one connector per ID
		c.myConnectors.oneObjectPerID[s]

		-- internals must be well formed
		partsAreWellFormed[s, c]
		connectorsAreWellFormed[s, c]
		portAndPortInstancesAreConnected[s, c]
	}
	else
	{
		-- won't have parts, as this is the definition of a leaf
		-- can have a maximum of one implementation, either inherited or added, or replaced
		-- rule: WC_COMPONENT_LEAF_IMPLEMENTATION
		one c.cimplementation.s
		-- a leaf cannot have connectors
		no c.connectors.s
		
		-- to be well formed, we must have one element per ID
		c.myCImplementation.oneObjectPerID[s]
		-- rule: WF_COMPONENT_LINK_PER_UUID -- max of one link per ID
		c.myLinks.oneObjectPerID[s]		
		
		-- to be well formed, links must have no duplication and must refer to real ports in the stratum
		-- note that links are allowed to loop back, as connectors can loop back through parts...
		let l = c.links.s.linkEnds
		{
			no ~l & l
			dom[l] + ran[l] in dom[c.myPorts.objects_e[s]]
		}		
	}
}

pred portAndPortInstancesAreConnected(s: Stratum, c: Component)
{
	all port: c.ports.s |
		portIsConnected[s, c, port]
		
	all cpart: c.parts.s |
		all port: cpart.partType.ports.s |
			portInstanceIsConnected[s, c, port, cpart]
}


pred partsAreWellFormed(s: Stratum, c: Component)
{
	all pPart: c.parts.s
	{
	  -- rule: WF_PART_NO_ISLANDS
		-- it must be possible to reach this part from a series of connections from the owning component
		-- otherwise, this part will be completely internally connected -- an island
		s -> c in pPart.*(linkedToParts.c.s).linkedToOutside
		-- check the attributes
		let
			valueIDs = dom[pPart.attributeValues],
			aliasIDs = dom[pPart.attributeAliases],
			copyIDs = dom[pPart.attributeCopyValues],
			parentAttrs = c.myAttributes.objects_e[s],
			partAttrs = pPart.partType.myAttributes.objects_e[s],
			partAttrIDs = dom[partAttrs]
		{
			-- should have no overlap between the different types of possibilities
			disj[valueIDs, aliasIDs, copyIDs]
			-- all the IDs must exist in the list of attributes
			(valueIDs + aliasIDs + copyIDs) in partAttrIDs

			-- rule: WF_PART_SLOT_LITERAL -- any new values must have the correct type
			all ID: valueIDs |
				pPart.attributeValues[ID].valueType = partAttrs[ID].attributeType

			-- rule: WF_PART_SLOT_ALIAS_COPY
			-- any aliased or copied attributes must exist and have the correct type
			all ID: aliasIDs |
				partAttrs[ID].attributeType =
					parentAttrs[pPart.attributeAliases[ID]].attributeType
			all ID: copyIDs |
				partAttrs[ID].attributeType =
					parentAttrs[pPart.attributeCopyValues[ID]].attributeType
				
			-- rule: WF_PART_SLOT_DEFAULT
			-- anything left over must have a default value or else the parts attribute is unspecified
			all ID: partAttrIDs - (valueIDs + aliasIDs + copyIDs) |
				one partAttrs[ID].defaultValue
		}
	}	
} 


pred setupConnectors(s: Stratum, c: Component)
{
	all end: c.connectors.s.ends |
	{
		-- if just one end of the connector goes to the component, it must be mandatory
		-- if the part end is mandatory
		end in ComponentConnectorEnd =>
		{
			end.port.c.s = (end & ComponentConnectorEnd)::getPort[s, c]
		}
		else
		{
			-- this is a part connector end, make sure we connect to a single port instance
			let
				portAndPart = (end & PartConnectorEnd)::getPortInstance[s, c],
				resolvedPort = dom[portAndPart]
				{
					end.port.c.s = resolvedPort
					end.cpart.c.s = ran[portAndPart]
				}
		}
	}
}

pred connectorsAreWellFormed(s: Stratum, c: Component)
{
	all end: c.connectors.s.ends |
	let other = end.otherEnd, aport = end.port.c.s, otherPort = other.port.c.s |
	{
		end.index in end.port.c.s.(mandatory + optional)
		one end.port.c.s
		
		-- if just one end of the connector goes to the component, it must be mandatory
		-- if the part end is mandatory
		end in ComponentConnectorEnd =>
		{
			-- note: other end must be a part connector end, as no component to component connectors are allowed
			-- if the outside is optional, the inside cannot be mandatory...
			-- rule: WF_CONNECTOR_OPTIONAL
			end.index in aport.optional => other.index in otherPort.optional
		}
		else
		{
			one end.cpart.c.s
			-- rule: WF_CONNECTOR_SAME
			end.index in aport.optional <=> other.index in otherPort.optional
		}
	}
}



----------- support predicates

pred portIsConnected(s: Stratum, c: Component, o: Port)
{
	-- ports on the component must always be connected internally
	all idx: o.mandatory + o.optional |
	one end: c.connectors.s.ends & ComponentConnectorEnd |
	{
		end.port.c.s = o
		idx = end.index
	}
}

pred portInstanceIsConnected(s: Stratum, c: Component, o: Port, p: Part)
{
	-- don't need to check any provided interfaces unless these are not optional
	(some o.required.c.s or isFalse[Model::providesIsOptional]) =>
	{
		-- match up any mandatory required interfaces on the port with a single connector
		-- rule: WF_CONNECTOR_INDEX
		all idx: o.mandatory |
			one end: c.connectors.s.ends & PartConnectorEnd |
				end.port.c.s = o and end.cpart.c.s = p and idx = end.index
		all idx: o.optional |
			lone end: c.connectors.s.ends & PartConnectorEnd |
				end.port.c.s = o and end.cpart.c.s = p and idx = end.index
	}
}

