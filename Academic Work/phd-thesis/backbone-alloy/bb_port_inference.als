module bb_port_inference

open bb_structure
open bb_inference_help

pred setupLeafLinks(s: Stratum, c: Component)
{
	-- make sure we have enough link ends
	ensureLinkEndsExist[s, c]

	let
		idToPorts = c.myPorts.objects_e[s],
		inferred =
			{ p1, p2: ran[idToPorts] |
				idToPorts.p1 -> idToPorts.p2 in c.links.s.linkEnds }
	{
		-- copy over the links
		c.inferredLinks.s = inferred

		-- copy over the sets
		all cport: c.ports.s |
		let
			end = getComponentLinkEnd[idToPorts.cport],
			-- rule: WF_LINK_COMPLEMENTARY
			errors =
				some other: c.ports.s |
					cport -> other in c.inferredLinks.s and
						(cport.required.c.s != other.provided.c.s or
						 cport.provided.c.s != other.required.c.s)
		{
			-- propagate the set value into the inferred value
			-- rule: WF_COMPONENT_LEAF_PORTS
			cport.required.c.s = cport.setRequired
			cport.provided.c.s = cport.setProvided
	
			-- we have no errors if all linked match up exactly
			s -> c in end.linkError <=> errors
		}
	}
}

////open setuplinks
pred setupCompositeLinks(s: Stratum, c: Component)
////close setuplinks
{
	ensureLinkEndsExist[s, c]

	let
		allPorts = c.ports.s,
		allParts = c.parts.s,
		idToPorts = c.myPorts.objects_e[s],
		idToParts = c.myParts.objects_e[s],
		-- flatten everything into a LinkEnd->LinkEnd structure so we can
		-- use transitive closure to navigate
		portToPort =   makePortToPort[s, c],
		partInternal = makePartInternal[s, c],
		partToPart =   makePartToPart[s, c],
		portToPart =   makePortToPart[s, c],
		partToPort =   ~portToPart,
		-- we connect by going from a port to a port,
		-- or from a port to part to possibly the other side of the part
		-- and then onto another part etc, until we get to a final part,
		-- or to a final port
////open portnavigation
		fromPortToPart = portToPart.*(partInternal.partToPart),
		fromPartToPort = ~fromPortToPart,
		fromPartToPart = partToPart.*(partInternal.partToPart),
////close portnavigation
		-- harsh allows us to bounce around looking for any possibly connected other elements.
		-- used to disallow inferredLinks via tainting
		harshFromPortToAny =
			portToPart.*(portToPart + partToPort + portToPort + partInternal + partToPart),
		fromPortToPort = portToPort + fromPortToPart.partInternal.partToPort
	{
		-- set up the inferred links, propagating the constraints to the next level
		propagateInferredCompositeLinks[
			s, c, harshFromPortToAny, fromPortToPort,
			partInternal, portToPort]

		-- get the provided and required interfaces of ports
		all cport: allPorts |
		let
			end = getComponentLinkEnd[idToPorts.cport],
			
			infReq = cport.required.c.s,
			reqEnds = end.fromPortToPart,
			requiresFromEnds =
				{ r: Interface  |
					some ce: reqEnds |
						r in ce.getPortInstanceRequired[s, c] },
		matchingRequires = extractLowestCommonSubtypes[s, requiresFromEnds],
			
			infProv = cport.provided.c.s,
			provEnds = end.fromPortToPort - end +
				{ e: PartLinkEnd |
					e in end.fromPortToPart and no e.partInternal
				},
			providesFromEnds =
				{ p: Interface |
					{
						(some e: provEnds & ComponentLinkEnd |
							p in e.getPort[s, c].required.c.s)
						or
						(some e: provEnds & PartLinkEnd |
							p in e.getPortInstanceProvided[s, c])
					}
				},
		matchingProvides = extractHighestCommonSupertypes[s, providesFromEnds]
		{
			infReq = matchingRequires
			infProv = matchingProvides

			-- rule: WF_CONNECTOR_ONE_TO_ONE
			s -> c not in end.linkError <=>
				(oneToOneProvidedMappingExists[s, c, infProv, reqEnds] and
				 oneToOneRequiredMappingExists[s, c, infReq, reqEnds])
		}

		-- enforce the constraints for each port instance
		all cpart: allParts,
			cport: cpart.partType.ports.s |
		let end = getPartLinkEnd[cpart.portMap[s].cport, idToParts.cpart]
		{
			
			{
				let
					infReq = end.getPortInstanceRequired[s, c],				
					infProv = end.getPortInstanceProvided[s, c],
					terminalEnds = end.fromPartToPort +
						{ e: PartLinkEnd |
							e in end.fromPartToPart and no e.partInternal
						},
					provFromTerminalEnds =
						{ p: Interface |
							{
								(some e: terminalEnds & ComponentLinkEnd |
									p in e.getPort[s, c].provided.c.s)
								or
								(some e: terminalEnds & PartLinkEnd |
									p in e.getPortInstanceRequired[s, c])
							}
						},
					allEnds = end.fromPartToPort +
						{ e: PartLinkEnd |
							e in end.fromPartToPart
						},
					matchingTerminalProvides =
						extractLowestCommonSubtypes[s, provFromTerminalEnds]
				{
////open connectionok
					s -> c not in end.linkError <=>
					{
						-- rule: WF_CONNECTOR_PROVIDES_ENOUGH
						no end.partInternal =>
							providesEnough[s, infProv, matchingTerminalProvides]
						oneToOneRequiredMappingExists[s, c, infProv, allEnds]
						oneToOneProvidedMappingExists[s, c, infReq, allEnds]
					}
////close connectionok
				}
			}
		}
	}
}

pred linksAreWellFormed(s: Stratum, c: Component)
{
	let
		allPorts = c.ports.s,
		allParts = c.parts.s,
		idToParts = c.myParts.objects_e[s],
		portToPort =   makePortToPort[s, c]
	{
		-- enforce that no ports connect directly to each other
		-- as this can lead to nondeterministic interface assigment
		-- rule: WF_CONNECTOR_NO_PORT_TO_PORT
		s in c.isComposite =>
			no portToPort

		-- enforce the constraints for each port
		all cport: allPorts |
		let
			infProv = cport.provided.c.s,
			infReq = cport.required.c.s,
			idToPorts = c.myPorts.objects_e[s],
			end = getComponentLinkEnd[idToPorts.cport],
			amHome = c.home = s,
			setInterfaces = cport.(setProvided + setRequired)
		{
			-- check any set values only if we are "home"
			(amHome and some setInterfaces) =>
			{
				infProv = cport.setProvided
				infReq = cport.setRequired
			}
			
			-- rule: WF_PORT_SOME_INTERFACES -- a port must have some interfaces
			some infProv + infReq
			s -> c not in end.linkError
		}

		-- enforce the constraints for each port instance
		all cpart: allParts,
			cport: cpart.partType.ports.s |
		let
			end = getPartLinkEnd[cpart.portMap[s].cport, idToParts.cpart],
			infReq = end.getPortInstanceRequired[s, c],
			infProv = end.getPortInstanceProvided[s, c]
		{
			-- must have some interfaces
			some infProv + infReq
			s -> c not in end.linkError
		}
	}
}


-- set up the inferred links for this component for a leaf, just use the links
-- for a composite, trace through from port to port, but only infer a link if there
-- is no terminal part involve anywhere
////open propagatelinks
pred propagateInferredCompositeLinks(
	s: Stratum,
	c: Component,	
	harshFromPortToAny:  ComponentLinkEnd -> LinkEnd,
	fromPortToPort:      ComponentLinkEnd -> ComponentLinkEnd,
	partInternal:        PartLinkEnd -> PartLinkEnd,
	portToPort:          ComponentLinkEnd -> ComponentLinkEnd)
////close propagatelinks
{
	let
		idToPorts = c.myPorts.objects_e[s],
		terminateInternallyIDs =
		{ id: dom[idToPorts] & PortID |
			some end: PartLinkEnd |
			let instance = end.getPortInstance[s, c],
				cport = dom[instance],
				cpart = ran[instance]
			{
				getComponentLinkEnd[id] in harshFromPortToAny.end
				no end.partInternal
				-- only provided terminals break linking
				some cport.provided.(cpart.partType).s
			}
		}
	{
		-- find all port->port combinations that go through a leaf part and link up
		-- but which don't have a termination on a provided port instance interface
		let inferred =
		{ p1, p2: Port |
			some end: dom[fromPortToPort] | let other = end.fromPortToPort
			{
				-- no connector loopbacks on port instances
				disj[end, other]
				p1 = idToPorts[end.linkPortID]
				p2 = idToPorts[other.linkPortID]
				-- if we can reach a port which links internally,
				-- do not create an alias
				no (end + other).*portToPort.linkPortID & terminateInternallyIDs
			}
		} |
		c.inferredLinks.s = inferred
	}
}
