module facts

open util/boolean as boolean
open util/relation as relation
open util/ternary as ternary
open structure
open port_inference
open wellformed_components
open wellformed_interfaces
open stratum_help
open redefinition_types

--------------------------------------------------------------------

fact StratumFacts
{
	invalidateUnseenPorts[]
	one isTop.True
	all s: Stratum |
	-- a stratum depends on any external it explicitly declares, plus any nested strata
	let fullDependsOn = dependsOn + nestedStrata + ^parent.dependsOn |
	{
		-- dependsOn cannot be nested packages
		all d : s.dependsOn | s not in d.*parent
		
		-- dependsOnNested must be nested packages.  these can be deeply nested also.
		all n : s.dependsOnNested | s in n.^parent
	
		-- for relaxed, expose what it depends on and their exposures
		-- for strict and relaxed, expose any direct nested dependencies
		isTrue[s.isRelaxed] =>
			s.exposesStrata = s + s.(dependsOn + dependsOnNested).exposesStrata
		else
			s.exposesStrata = s + s.dependsOnNested.exposesStrata

		-- used for partial ordering
		-- contains nothing the children already depend on
		s.simpleDependsOn = s.fullDependsOn - s.fullDependsOn.transitive

		-- no cycles
		s not in s.transitive

		-- can only see what others expose
		-- NOTE: if a nested stratum only exposes a public package, then this is 
		--       all that the parent can see
		s.canSee = s.fullDependsOn.exposesStrata
	
		-- the strata we can see using the dependency graph
		s.transitive = s.^fullDependsOn

		-- a stratum is called top if no stratum depends on it
		isTrue[s.isTop] <=> no simpleDependsOn.s
		
		-- have max of one redefinition of an element per stratum
		all e: Element |
			lone s.ownedElements & redefines.e
		-- ties up redefining and redefines
		s.redefining = s.ownedElements & dom[redefines]
	}
}

pred independent[a, b: Stratum]
{
	b not in a.*dependsOn
}

pred mutuallyIndependent[a, b: Stratum]
{
	independent[a, b] and independent[b, a]
}

fun stratumPerspective[stratum: Stratum]: set Stratum
{
  stratum.*dependsOn
}

--------------------------------------------------------------------
-- handle the basics of resemblance(specialisation) and redefinition
--------------------------------------------------------------------

fact ElementFacts
{
	-- nothing can resemble a redefinition -- check to see that the things we resemble don't redefine
	no resembles.redefines
	
	all
		e: Element |
	let
		owner = e.home,
		-- strata that can see the component
		resemblingOwningStratum = e.resembles.home,
		redefiningOwningStratum = e.redefines.home
	{
		-- no circularities in resemblance or redefinition, and must be visible
		e not in (e.^resembles + e.redefines)
		resemblingOwningStratum in owner.canSeePlusMe
		redefiningOwningStratum in owner.canSee
		
		-- tie up the owning stratum and the elements owned by that stratum
		e.home = ownedElements.e

		-- we only need to form a definition for stratum that can see us
		all s: Stratum |
		let
			-- who should I resemble
			-- (taking redefinition into account)
			iResemble = e.resembles_e.s,
			-- if we resemble what we are redefining,
			-- look for the original under here
			topmostOfRedefined = getTopmost[
				owner.simpleDependsOn,
				e.redefines & e.resembles],
			-- look for any other resembled components
			-- from here down
			topmostOfResemblances = getTopmost[
				s,
				e.resembles - e.redefines]
		{
			-- who do we act as in this stratum?
			e.actsAs_e.s =
				{ real: Element | no real.redefines and e in getTopmost[s, real] }
			-- rewrite the resemblance graph to handle redefinition
			owner not in s.transitivePlusMe =>
				no iResemble
			else
				iResemble = topmostOfRedefined + topmostOfResemblances
		}
	}
}

fun getTopmost(s: set Stratum, e: Element): set Element
{
	let redefined = redefines.e & s.transitivePlusMe.redefining,
		topmostRedefined = redefined - redefined.resembles_e.s
			{ some topmostRedefined => topmostRedefined else e }
}

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
			-- if we can see this component, test to see if it is valid in this stratum
			not visible =>
				invalidateUnseenInterface[s, i]
			else
			{
				-- ensure that the subtypes are set up correctly
				-- this is a subtype of an interface if we can reach it transitively and
				-- our set of operations are a super-set of the super type's
				-- of operationID -> Operation.  i.e. if you replace an operationID you are breaking subtype
				-- so the operationID is the name, and the Operation is the full spec which is assumed to have
				-- changed in a redef...
				-- NOTE: the super types are direct -- to follow use closure
				--       also note that we only need/want supertypes for non-primes
				no i.redefines =>
				{
					i.superTypes.s =
						{ super: i.resembles |
							super.myOperations.objects_e[s] in
								i.myOperations.objects_e[s] }
				}
				else
					no i.superTypes.s
			
				-- merge any parts and apply changes
				let topmost = getTopmost[s, i] & Interface
				{
					i.myOperations::mergeAndApplyChangesForResemblance[
						s, i, i.resembles_e.s.myOperations]
					i.myOperations::mergeAndApplyChangesForRedefinition[
						s, i, topmost, topmost.myOperations]
					i.myImplementation::mergeAndApplyChangesForResemblance[
						s, i, i.resembles_e.s.myImplementation]
					i.myImplementation::mergeAndApplyChangesForRedefinition[
						s, i, topmost, topmost.myImplementation]
				}

				-- the component must be valid in the place it was defined
				-- also, in any stratum (apart from the top one), a visible component must be valid
				(s = owner or s not in Model.errorsAllowed) => !invalid
				s = owner =>
				{
					i.myOperations.deltasIsWellFormed[s]
					i.myImplementation.deltasIsWellFormed[s]
				}
				
				-- a component is valid if it is well formed...
				!invalid <=> interfaceIsWellFormed[s, i]
			}
		}
	}
}


--------------------------------------------------------------------
-- handle any extra rules for components
--------------------------------------------------------------------

fact ComponentFacts
{
	-- no part or port can refer explicitly to a redefined component or interface
	no partType.redefines
	no (setProvided + setRequired).redefines

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

	
		isFalse[c.isComposite] =>
		{
			-- no resemblance, redef, parts + noone resembles or redefines it
			no c.myParts
			no c.myConnectors
			no resembles.c
			no c.resembles
		}
		else
			no c.links

		-- parts can only have types from here down, excluding myself...
		-- attribute types can only be from here down
		-- ports can only refer to interfaces from here down
		types.home in iCanSeePlusMe
		attributeTypes.attrTypes in iCanSeePlusMe
		interfaces.home in iCanSeePlusMe
		
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
				-- merge any parts and apply changes
				c.myParts::mergeAndApplyChangesForResemblance[
					s, c, c.resembles_e.s.myParts]
				c.myParts::mergeAndApplyChangesForRedefinition[
					s, c, topmost, topmost.myParts]
					
				-- merge any ports and apply changes
				c.myPorts::mergeAndApplyChangesForResemblance[
					s, c, c.resembles_e.s.myPorts]
				c.myPorts::mergeAndApplyChangesForRedefinition[
					s, c, topmost, topmost.myPorts]
					
				-- merge any connectors and apply changes
				c.myConnectors::mergeAndApplyChangesForResemblance[
					s, c, c.resembles_e.s.myConnectors]
				c.myConnectors::mergeAndApplyChangesForRedefinition[
					s, c, topmost, topmost.myConnectors]
					
				-- merge any attributes and apply changes
				c.myAttributes::mergeAndApplyChangesForResemblance[
					s, c, c.resembles_e.s.myAttributes]
				c.myAttributes::mergeAndApplyChangesForRedefinition[
					s, c, topmost, topmost.myAttributes]
					
				-- if we are "home", all the deltas must be well formed...
				-- this is not necessarily the case if we are not home
				s = owner =>
				{
					c.myParts.deltasIsWellFormed[s]
					c.myPorts.deltasIsWellFormed[s]
					c.myConnectors.deltasIsWellFormed[s]
					c.myAttributes.deltasIsWellFormed[s]
				}
				
				setupParts[s, c]
				setupConnectors[s, c]
				isTrue[c.isComposite] =>
					setupCompositeLinks[s, c]
				else
					setupLeafLinks[s, c]
				
				-- the component must be valid in the place it was defined
				-- also, in any stratum (apart from the top one), a visible
				-- component must be valid
				(s = owner or s not in Model.errorsAllowed) => !invalid
				
				-- a component is invalid iff it is not well formed
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
-- NOTE: if you move the valid setting up into the main body, it gets slow for some bizarre reason
pred invalidateUnseenComponent(s: Stratum, c: Component)
{
	s not in c.isInvalid_e -- it isn't valid here
	no c.parts.s
	no c.iDParts.s
	no c.ports.s
	no c.connectors.s
	no c.attributes.s
	no c.inferredLinks.s
	c.myParts::nothing[s]
	c.myAttributes::nothing[s]
	c.myPorts::nothing[s]
	c.myConnectors::nothing[s]
}

pred invalidateUnseenInterface(s: Stratum, i: Interface)
{
	s not in i.isInvalid_e -- it isn't valid here
	no i.operations.s
	no i.implementation.s
	i.myOperations::nothing[s]
	i.myImplementation::nothing[s]
	no i.superTypes.s
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
