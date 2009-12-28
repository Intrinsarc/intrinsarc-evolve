module wellformed_components

open util/boolean as boolean
open util/relation as relation
open util/ternary as ternary
open structure


-- check that the component is well formed
pred componentIsWellFormed(s: Stratum, c: Component)
{
	-- ***RULE W4: the type of the part must not be in a cyclic relationship with itself through containment
	-- it also cannot be cyclical with respect to resemblance
	c not in c.^(resembles_e.s)
	-- the original (either the thing being redefined or the original)
	-- is not in the composition hierarchy taking resemblance into account
	-- NOTE: if c cannot be composed if it is a redefinition
	-- NOTE: a further constraint is that we cannot be composed of the thing we
	--       are redefining
////open WF_COMPONENT_CONTAINMENT
	let
		resembling = resembles_e.s, partTypes = parts.s.partType,
		original = no c.redefines => c else c.redefines
	{
		original not in c.*(resembling + partTypes).partTypes
	}
////close WF_COMPONENT_CONTAINMENT
	-- ***RULE W6: a component must have some ports
	some c.ports.s
	
	-- to be well formed, we must have one element per ID
////open WF_COMPONENT_ONE_CONSTITUENT_PER_UUID
	c.myPorts.oneObjectPerID[s]
	c.myAttributes.oneObjectPerID[s]
////pause WF_COMPONENT_ONE_CONSTITUENT_PER_UUID
	
	// if this is composite, ensure the ports, parts and connectors are well formed
////unpause WF_COMPONENT_ONE_CONSTITUENT_PER_UUID
	isTrue[c.isComposite] =>
	{
////pause WF_COMPONENT_ONE_CONSTITUENT_PER_UUID
		-- must always have some parts
		some c.parts.s
	
		-- to be well formed, we must have one element per ID
////unpause WF_COMPONENT_ONE_CONSTITUENT_PER_UUID
		c.myParts.oneObjectPerID[s]
		c.myConnectors.oneObjectPerID[s]
////close WF_COMPONENT_ONE_CONSTITUENT_PER_UUID

		-- don't require any parts -- e.g. junction components for altering connection interfaces
		partsAreWellFormed[s, c]
		connectorsAreWellFormed[s, c]
		portAndPortInstancesAreConnected[s, c]
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
		-- ***RULE C8: it must be possible to reach this part from a series of connections from the owning component
		-- otherwise, this part will be completely internally connected -- an island
////open WF_PART_NO_ISLANDS
		s -> c in pPart.*(linkedToParts.c.s).linkedToOutside
////close WF_PART_NO_ISLANDS	
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

			-- any new values must have the correct type
			all ID: valueIDs |
				pPart.attributeValues[ID].valueType = partAttrs[ID].attributeType

			-- any aliased or copied attributes must exist and have the correct type
			all ID: aliasIDs |
				partAttrs[ID].attributeType =
					parentAttrs[pPart.attributeAliases[ID]].attributeType
			all ID: copyIDs |
				partAttrs[ID].attributeType =
					parentAttrs[pPart.attributeCopyValues[ID]].attributeType
				
			-- anything left over must have a default value or else the parts attribute is unspecified
			all ID: partAttrIDs - (valueIDs + aliasIDs + copyIDs) |
				one partAttrs[ID].defaultValue
		}
	}	
} 


pred setupConnectors(s: Stratum, c: Component)
{
	all end: c.connectors.s.ends |
	let other = end.otherEnd, aport = end.port.c.s, otherPort = other.port.c.s |
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
			end.index in aport.optional => other.index in otherPort.optional
		}
		else
		{
			one end.cpart.c.s
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
	-- don't need to check any provided interfaces -- as these are always optional
	-- match up any mandatory required interfaces on the port with a single connector
	all idx: o.mandatory + o.optional |
	let ends =
	{ end: c.connectors.s.ends & PartConnectorEnd |
		end.port.c.s = o and end.cpart.c.s = p and idx = end.index
	}
	{
////open WF_PORT_PROVIDES_ONLY
		(some o.required.c.s or isFalse[Model::providesIsOptional]) =>
		{
			idx in o.mandatory =>
				-- must have a connection
				one ends
			else
				-- can only have at most one connection
				lone ends
		}
////close WF_PORT_PROVIDES_ONLY
	}
}

