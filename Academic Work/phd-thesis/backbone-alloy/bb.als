module bb

-- opening the two structure modules here is not strictly necessary, but it simplifies the naming
open base_structure as base
open bb_structure as full

open bb_well_formed
open bb_port_inference
open base_facts


--------------------------------------------------------------------
-- handle any extra rules for interfaces
--------------------------------------------------------------------

fact InterfaceFacts
{
	all i: Interface |
	let
		owner = i.home
	{
		-- we only need to form a definition for stratum that can see us
		all s: Stratum |
		let
			invalid = s in i.isInvalid_e,
			visible = owner in s.transitivePlusMe
		{
			-- if we can see this interface, test to see if it is valid in this stratum
			not visible =>
				invalidateUnseenInterface[s, i]
			else
			{
				-- ensure that the subtypes are set up correctly
				-- this is a subtype of an interface if we can reach it transitively and
				-- our set of operations are a super-set of the super type's
				-- of operationID -> Operation.  i.e. if you replace an operationID you are breaking subtype
				-- so the operationID is the name, and the Operation is the full spec which is assumed to have
				-- changed in a replacement...
				-- NOTE: the super types are direct -- to follow use closure
				--       also note that we only need/want supertypes for non-primes
				no i.replaces =>
				{
////open supertype
					i.superTypes.s =
						{ super: i.resembles |
							super.myOperations.objects_e[s] in
								i.myOperations.objects_e[s] }
////close supertype
				}
				else
					no i.superTypes.s
			
				-- merge any parts and apply changes
				let topmost = getTopmost[s, i] & Interface
				{
					i.myOperations::mergeAndApplyChangesForResemblance[
						s, i, i.resembles_e.s.myOperations]
					i.myOperations::mergeAndApplyChangesForElementReplacement[
						s, i, topmost, topmost.myOperations]
					i.myIImplementation::mergeAndApplyChangesForResemblance[
						s, i, i.resembles_e.s.myIImplementation]
					i.myIImplementation::mergeAndApplyChangesForElementReplacement[
						s, i, topmost, topmost.myIImplementation]
				}

				-- the interface must be valid in the place it was defined
				(s = owner or s not in Model.errorsAllowed) => !invalid
				s = owner =>
				{
					i.myOperations.deltasIsWellFormed[s]
					i.myIImplementation.deltasIsWellFormed[s]
				}
				
				-- a component is valid if it is well formed...
				!invalid <=> interfaceIsWellFormed[s, i]
			}
		}
	}
}


--------------------------------------------------------------------
-- handle any extra rules for primitive types
--------------------------------------------------------------------

fact PrimitiveTypeFacts
{
	all t: PrimitiveType |
	let
		owner = t.home
	{
		-- we only need to form a definition for stratum that can see us
		all s: Stratum |
		let
			invalid = s in t.isInvalid_e,
			visible = owner in s.transitivePlusMe
			{
				-- if we can see this interface, test to see if it is valid in this stratum
				not visible =>
					invalidateUnseenPrimitiveType[s, t]
				else
				{
					-- merge any parts and apply changes
					let topmost = getTopmost[s, t] & PrimitiveType
					{
						t.myTImplementation::mergeAndApplyChangesForResemblance[
							s, t, t.resembles_e.s.myTImplementation]
						t.myTImplementation::mergeAndApplyChangesForElementReplacement[
							s, t, topmost, topmost.myTImplementation]
					}
	
					-- the primitive type must be valid in the place it was defined
					(s = owner or s not in Model.errorsAllowed) => !invalid
					s = owner =>
						t.myTImplementation.deltasIsWellFormed[s]
					
					-- a component is valid if it is well formed...
					!invalid <=> primitiveTypeIsWellFormed[s, t]
				}
			}
	}
}

--------------------------------------------------------------------
-- handle any extra rules for components
--------------------------------------------------------------------

fact ComponentFacts
{
	-- rule: COMPONENT_REPLACEMENT_NOT_REFERENCED
	-- no part or port can refer explicitly to a replaced component or interface
	no partType.replaces
	no (setProvided + setRequired).replaces

	all c: Component |
	let
		owner = c.home,
		-- strata that the component can see
		iCanSeePlusMe = owner.canSeePlusMe,
		types = c.myParts.newObjects.partType,
		attrTypes = c.myAttributes.newObjects.attributeType,
		interfaces = c.myPorts.addedObjects.(setRequired + setProvided)
	{
		-- resemblance has no redundancy
		c.resembles = c.resembles - c.resembles.^resembles

		-- attribute types, part types and port interfaces must be visible
		-- rule: PART_TYPE_VISIBLE
		types.home in iCanSeePlusMe
		-- rule: PORT_INTERFACE_VISIBIILITY
		interfaces.home in iCanSeePlusMe
		-- rule: ATTRIBUTE_TYPE_VISIBILITY
		attrTypes.home in iCanSeePlusMe
		
		-- ensure that the port remaps are correctly formed for the stratum they are owned by
		let delta = c.myParts |
		all p: delta.newObjects | -- parts of the delta
		let
			partID = delta.replaceObjects.p,
			oldPart = delta.oldObjects_e[owner][partID],
			remap = p.portRemap,
			newPortIDs = dom[remap],
			oldPortIDs = ran[remap]
		{
			-- we can only alias ports that we actually have
			newPortIDs in dom[p.partType.myPorts.objects_e[owner]]
			-- we can only use port ids of the component we are replacing
			oldPortIDs in dom[oldPart.partType.myPorts.objects_e[owner]]
			
			-- each port we remap should have a different id, or there's no point
			-- this is not strictly needed, but ensures nice witnesses
			bijection[remap, newPortIDs, oldPortIDs]
			
			-- can't map a port id onto the same id
			no remap & iden
		}
		
		-- we only need to form a definition for stratum that can see us
		all s: Stratum |
		let
			invalid = s in c.isInvalid_e,
			visible = owner in s.transitivePlusMe
		{
			-- if we can see this component, test to see if it is valid in this stratum
			not visible =>
				invalidateUnseenComponent[s, c]
			else
			let topmost = getTopmost[s, c] & Component
			{
				-- this is a composite in this stratum if it has parts
				s in c.isComposite <=> some c.parts.s
			
				-- merge any parts and apply changes
				c.myParts::mergeAndApplyChangesForResemblance[
					s, c, c.resembles_e.s.myParts]
				c.myParts::mergeAndApplyChangesForElementReplacement[
					s, c, topmost, topmost.myParts]
					
				-- merge any ports and apply changes
				c.myPorts::mergeAndApplyChangesForResemblance[
					s, c, c.resembles_e.s.myPorts]
				c.myPorts::mergeAndApplyChangesForElementReplacement[
					s, c, topmost, topmost.myPorts]
					
				-- merge any connectors and apply changes
				c.myConnectors::mergeAndApplyChangesForResemblance[
					s, c, c.resembles_e.s.myConnectors]
				c.myConnectors::mergeAndApplyChangesForElementReplacement[
					s, c, topmost, topmost.myConnectors]
					
				-- merge any attributes and apply changes
				c.myAttributes::mergeAndApplyChangesForResemblance[
					s, c, c.resembles_e.s.myAttributes]
				c.myAttributes::mergeAndApplyChangesForElementReplacement[
					s, c, topmost, topmost.myAttributes]
					
				-- merge any implementations and apply changes
				c.myCImplementation::mergeAndApplyChangesForResemblance[
					s, c, c.resembles_e.s.myCImplementation]
				c.myCImplementation::mergeAndApplyChangesForElementReplacement[
					s, c, topmost, topmost.myCImplementation]
					
				-- merge any implementations and apply changes
				c.myLinks::mergeAndApplyChangesForResemblance[
					s, c, c.resembles_e.s.myLinks]
				c.myLinks::mergeAndApplyChangesForElementReplacement[
					s, c, topmost, topmost.myLinks]
					
				-- if we are "home", all the deltas must be well formed...
				-- this is not necessarily the case if we are not home
				s = owner =>
				{
					c.myParts.deltasIsWellFormed[s]
					c.myPorts.deltasIsWellFormed[s]
					c.myConnectors.deltasIsWellFormed[s]
					c.myAttributes.deltasIsWellFormed[s]
					c.myCImplementation.deltasIsWellFormed[s]
					c.myLinks.deltasIsWellFormed[s]
				}
				
				setupParts[s, c]
				setupConnectors[s, c]
				s in c.isComposite =>
					setupCompositeLinks[s, c]
				else
					setupLeafLinks[s, c]
				
				-- the component must be valid in the place it was defined
				(s = owner or s not in Model.errorsAllowed) => !invalid
				
				-- a component is invalid iff it is not well formed
				-- rule: COMPONENT_OK_AT_HOME
				invalid <=>
					(!componentIsWellFormed[s, c] or !linksAreWellFormed[s, c])
			}
		}
	}
}

pred setupParts(s: Stratum, c: Component)
{
	-- reference the parts we are linked to and link to the outside if true
	let allParts = c.parts.s
	{
		no (Part - allParts).linkedToParts.c.s
		all pPart: allParts |
		{
			pPart.linkedToParts.c.s =
			{ p: allParts - pPart |
				some end: c.connectors.s.ends |
					end.cpart.c.s = pPart and end.otherEnd.cpart.c.s = p }
				
			-- reference if we are linked to the outside of the component
			s -> c in pPart.linkedToOutside <=>
			{
				some end: c.connectors.s.ends |
					end.cpart.c.s = pPart and end.otherEnd in ComponentConnectorEnd
			}
		}
	}

	-- form the full port map for this stratum, taking remap into account
	all p: c.myParts.newObjects |
	let
		-- turn the remap from id -> id to id -> port
		idToPort = p.partType.myPorts.objects_e[s],
		newPorts = idToPort[PortID],
		remap =
			{ newPort: newPorts, oldID: PortID |
				idToPort.newPort -> oldID in p.portRemap }
	{
		-- remove the existing ID of the port before adding the new one
		~(p.portMap[s]) =
			~(p.partType.myPorts.objects_e[s]) ++ remap
	}
}


-- if the connector is not visibile to a component in a stratum, it should be zeroed out to
-- make it easier to interpret the results and and zeroOutUnseenElement[s, e] cut back on the state space for performance reasons
fact ZeroOutUnseenConnectorsFact
{
	all conn: Connector, c: Component, s: Stratum |
	conn not in c.connectors.s =>
	{
		all end: conn.ends
		{
			no end.port.c.s
			no end.cpart.c.s
		}
	}
}

-- if the part is not visible to a component in a stratum, it should also be zeroed out
-- for understandability and performance reasons
fact ZeroOutUnseenPartsFact
{
	all p: Part, c: Component, s: Stratum |
	p not in c.parts.s =>
	{
		no p.linkedToParts.c.s
		s-> c not in p.linkedToOutside
	}
}

-- if the part is not visible to a component in a stratum, it should also be zeroed out
-- for understandability and performance reasons
-- NOTE: if you move the valid setting up into the main body, it gets slow
pred invalidateUnseenComponent(s: Stratum, c: Component)
{
	s not in c.isInvalid_e -- it isn't invalid here
	no c.parts.s
	no c.iDParts.s
	no c.ports.s
	no c.connectors.s
	no c.attributes.s
	no c.cimplementation.s
	no c.inferredLinks.s
	no c.links.s
	s not in c.isComposite
	c.myParts::nothing[s]
	c.myAttributes::nothing[s]
	c.myPorts::nothing[s]
	c.myConnectors::nothing[s]
	c.myCImplementation::nothing[s]
	c.myLinks::nothing[s]
}

pred invalidateUnseenInterface(s: Stratum, i: Interface)
{
	s not in i.isInvalid_e -- it isn't invalid here
	no i.operations.s
	no i.iimplementation.s
	i.myOperations::nothing[s]
	i.myIImplementation::nothing[s]
	no i.superTypes.s
}

pred invalidateUnseenPrimitiveType(s: Stratum, t: PrimitiveType)
{
	s not in t.isInvalid_e -- it isn't invalid here
	no t.timplementation.s
	t.myTImplementation::nothing[s]
}

pred invalidateUnseenPorts()
{
	all s: Stratum, c: Component
	{
		all p: Port |
			p not in c.ports.s =>
				no p.(provided + required).c.s
	}
}

-- some predicates to help with structuring a model
pred Model::providesIsOptional()
{
	Model.providesIsOptional = True
}

pred Model::providesIsNotOptional()
{
	Model.providesIsOptional = False
}

pred Model::noErrorsAllowed()
{
	no this.errorsAllowed
}

pred Model::errorsAllowedInTopOnly()
{
	this.errorsAllowed = isTop.True
}

pred Model::topDefinesNothing()
{
	no isTop.True.ownedElements
}

pred Model::definesNothing(s: Stratum)
{
	no s.ownedElements
}

pred Model::errorsOnlyAllowedInTopAndOthers(others: set Stratum)
{
	this.errorsAllowed = others + isTop.True
}

pred Model::errorsOnlyAllowedIn(others: set Stratum)
{
	this.errorsAllowed = others
}

pred Model::forceErrors(errorStrata: set Stratum)
{
	all e: errorStrata |
		some isInvalid_e.e
}

