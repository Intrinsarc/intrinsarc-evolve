module inference_help

open structure
open util/boolean as boolean


pred providesEnough(s: Stratum, provided: set Interface, required: set Interface)
{
	all prov: provided |
		one req: required |
			req in prov.*(superTypes.s)
			
	-- ensure that it works the other way around
	all req: required |
		one prov: provided |
			req in prov.*(superTypes.s)
}

pred oneToOneProvidedMappingExists[s: Stratum, c: Component, provided: set Interface, ends: LinkEnd]
{
	all end: ends & ComponentLinkEnd |
		oneToOneMappingExists[s, provided, end.getPort[s, c].required.c.s]

	all end: ends & PartLinkEnd |
		oneToOneMappingExists[s, provided, end.getPortInstanceProvided[s, c]]
}

pred oneToOneRequiredMappingExists[s: Stratum, c: Component, required: set Interface, ends: LinkEnd]
{
	all end: ends & ComponentLinkEnd |
		oneToOneMappingExists[s, required, end.getPort[s, c].provided.c.s]

	all end: ends & PartLinkEnd |
		oneToOneMappingExists[s, required, end.getPortInstanceRequired[s, c]]
}

pred oneToOneMappingExists[s: Stratum, a: set Interface, b: set Interface]
{
	all aa: a |
		one bb: b |
			bb in expand[s, aa]
			
	-- ensure that it works the other way around
	all bb: b |
		one aa: a |
			bb in expand[s, aa]
}

fun extractHighestCommonSupertypes(s: Stratum, require: Interface): set Interface
{
	let
		-- map is an interface in require (i) with all matching interfaces in require (e)
		map =
		{ i: require, e: require |
			some expand[s, i] & expand[s, e] },
		highestCommonSupertypes =
		{
			super: Interface |
				some i: require |
					super = highestCommonSupertype[s, map[i]]
		}
	{
		highestCommonSupertypes
	}
}

fun highestCommonSupertype(s: Stratum, required: set Interface): lone Interface
{
	{ i: Interface |
		{
			required in *(superTypes.s).i
			no sub: superTypes.s.i |
				required in *(superTypes.s).sub
		}
	}
}

fun extractLowestCommonSubtypes(s: Stratum, require: Interface): set Interface
{
	let
		-- map is an interface in require (i) with all matching interfaces in require (e)
		map =
		{ i: require, e: require |
			some expand[s, i] & expand[s, e] },
		lowestCommonSubtypes =
		{
			sub: Interface |
				some i: require |
					sub = lowestCommonSubtype[s, map[i]]
		}
	{
		lowestCommonSubtypes
	}
}

fun lowestCommonSubtype(s: Stratum, required: set Interface): lone Interface
{
	{ i: Interface |
		{
			required in i.*(superTypes.s)
			no super: i.superTypes.s |
				required in super.*(superTypes.s)
		}
	}
}

fun expand(s: Stratum, i: Interface): set Interface
{
	-- expand forms the full expanded supertype and subtype hierarchy
	i.*(superTypes.s) +  ^(superTypes.s).i
}


-- a generator axiom to ensure that we have a unique link end per port, or port instance
pred ensureLinkEndsExist(s: Stratum, c: Component)
{
	let
		idToPorts = c.myPorts.objects_e[s],
		idToParts = c.myParts.objects_e[s]
	{
		-- set up the linkends
		-- ensure all ports have a link end
		all portID: dom[idToPorts] |
			one l: ComponentLinkEnd |
				l.linkPortID = portID
				
		-- ensure all part/ports have a link end
		all ppart: c.parts.s |
			let partID = idToParts.ppart | 
				all portID: ppart.portMap[s].Port |
					one l: PartLinkEnd |
						l.linkPortID = portID and l.linkPartID = partID
	}
}

fun makePortToPort(s: Stratum, c: Component): ComponentLinkEnd -> ComponentLinkEnd
{
	isTrue[c.isComposite] =>
	{ p1, p2: ComponentLinkEnd |
		some end: c.connectors.s.ends | let other = end.otherEnd
		{
			disj[end, other]
			end + other in ComponentConnectorEnd
			end.portID = p1.linkPortID
			other.portID = p2.linkPortID
		}
	}
	else
	{ p1, p2: ComponentLinkEnd |
		some end: dom[c.links], other: c.links[end]
		{
			-- these are disjoint because of a clause in structure.als
			end = p1.linkPortID
			other = p2.linkPortID
		}	
	}	
}

fun makePartInternal(s: Stratum, c: Component): PartLinkEnd -> PartLinkEnd
{
	let
		idToParts = c.myParts.objects_e[s] |
	{ p1, p2: PartLinkEnd |
		some partID: dom[idToParts] |
		let
			realPart = idToParts[partID],
			realType = realPart.partType,
			inferredOneWay = realType.inferredLinks.s,
			inferred = inferredOneWay + ~inferredOneWay,
			idToPorts = realPart.portMap[s],
			realPort = idToPorts[p1.linkPortID]
		{
			disj[p1, p2]
			p1.linkPartID = partID
			p2.linkPartID = partID
			realPort in dom[inferred]
			idToPorts[p2.linkPortID] = inferred[realPort]
		}
	}
}

fun makePartToPart(s: Stratum, c: Component): PartLinkEnd -> PartLinkEnd
{
	{ p1, p2: PartLinkEnd |
		some end: c.connectors.s.ends | let other = end.otherEnd
		{
			disj[end, other]
			end.portID = p1.linkPortID
			end.partID = p1.linkPartID
			other.portID = p2.linkPortID
			other.partID = p2.linkPartID
		}	
	}
}


fun makePortToPart(s: Stratum, c: Component): ComponentLinkEnd -> PartLinkEnd
{
	{ p1: ComponentLinkEnd, p2: PartLinkEnd |
		some end: c.connectors.s.ends | let other = end.otherEnd
		{
			end in ComponentConnectorEnd
			end.portID = p1.linkPortID
			other.portID = p2.linkPortID
			other.partID = p2.linkPartID
		}	
	}
}

