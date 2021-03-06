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
////open top
	one isTop.True
////pause top
	all s: Stratum
	{
		-- for relaxed, expose what it depends on and their exposures
////open STRATUM_VISIBILITY
		isTrue[s.isRelaxed] =>
			s.exposesStrata = s + s.dependsOn.exposesStrata
		else
			s.exposesStrata = s
////close STRATUM_VISIBILITY

		-- used for partial ordering
		-- contains nothing the children already depend on
		s.simpleDependsOn = s.dependsOn -
			s.dependsOn.transitive

		-- no cycles
////open STRATUM_ACYCLIC
////open savcbs-stratum-acyclic-V
		s not in s.^dependsOn
////close savcbs-stratum-acyclic-V
////close STRATUM_ACYCLIC

		-- can only see what others expose
		s.canSee = s.dependsOn.exposesStrata
	
		-- the strata we can see using the dependency graph
		s.transitive = s.^dependsOn

		-- a stratum is called top if no stratum depends on it
////unpause top
		isTrue[s.isTop] <=> no simpleDependsOn.s
////close top
		
		-- have max of one redefinition of an element per stratum
		all e: Element |
			lone s.ownedElements & redefines.e
		-- ties up redefining and redefines
		s.redefining = s.ownedElements & dom[redefines]
	}
}

pred independent[stratum1, stratum2: Stratum]
{
////open independent
////open savcbs-independent-V
	stratum2 not in stratum1.*dependsOn
////close savcbs-independent-V
////close independent
}

pred mutuallyIndependent[a, b: Stratum]
{
////open mutually-independent
	independent[a, b] and independent[b, a]
////close mutually-independent
}

fun stratumPerspective[stratum: Stratum]: set Stratum
{
////open savcbs-perspective-V
  stratum.*dependsOn
////close savcbs-perspective-V
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
////open ELEMENT_VISIBIILITY_2
		resemblingOwningStratum in owner.canSeePlusMe
		redefiningOwningStratum in owner.canSee
////close ELEMENT_VISIBIILITY_2
		
		-- tie up the owning stratum and the elements owned by that stratum
		e.home = ownedElements.e

		-- we only need to form a definition for stratum that can see us
////open savcbs-rewrite-V
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
////pause savcbs-rewrite-V			
			-- who do we act as in this stratum?
			e.actsAs_e.s =
				{ real: Element | no real.redefines and e in getTopmost[s, real] }
			-- rewrite the resemblance graph to handle redefinition
			owner not in s.transitivePlusMe =>
				no iResemble
			else
////unpause savcbs-rewrite-V
////open ELEMENT_REDEF_RESEMBLE
				iResemble = topmostOfRedefined + topmostOfResemblances
////close ELEMENT_REDEF_RESEMBLE
		}
////close savcbs-rewrite-V
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
////open INTERFACE_SUBTYPE
					i.superTypes.s =
						{ super: i.resembles |
							super.myOperations.objects_e[s] in
								i.myOperations.objects_e[s] }
////close INTERFACE_SUBTYPE
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

	
		-- component implementations stay the same...
////open COMPONENT_NO_LEAF_REDEF_RESEMBLANCE
		isFalse[c.isComposite] =>
		{
////pause COMPONENT_NO_LEAF_REDEF_RESEMBLANCE
			-- no resemblance, redef, parts + noone resembles or redefines it
			no c.myParts
			no c.myConnectors
////unpause COMPONENT_NO_LEAF_REDEF_RESEMBLANCE
			no resembles.c
			no c.resembles
////close COMPONENT_NO_LEAF_REDEF_RESEMBLANCE
		}
		else
			no c.links

		-- parts can only have types from here down, excluding myself...
		-- attribute types can only be from here down
		-- ports can only refer to interfaces from here down
////open ELEMENT_VISIBILITY
		types.home in iCanSeePlusMe
		attributeTypes.attrTypes in iCanSeePlusMe
		interfaces.home in iCanSeePlusMe
////close ELEMENT_VISIBILITY
		
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
