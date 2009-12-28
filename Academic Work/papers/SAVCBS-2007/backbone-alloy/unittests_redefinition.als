module unittests_redefinition

open structure
open facts
open stratum_help


run conflictAndResolution for 4 but
	exactly 5 Stratum,
	exactly 6 Element,
	exactly 5 Component,
	exactly 1 Interface,
	5 structure/Ports/Deltas,
	5 structure/Attributes/Deltas,
	5 structure/Parts/Deltas,
	5 structure/Connectors/Deltas,
	10 LinkEnd

run conflictAndResolution for 5 but exactly 6 Element, exactly 5 Component, exactly 1 Interface, 10 LinkEnd

pred conflictAndResolution
{
	no isRelaxed.False
	some disj s1, s2, s3, s4, s5: Stratum
	{
		s1.dependsOn = s2
		s2.dependsOn = s3 + s4
		s3.dependsOn = s5
		s4.dependsOn = s5
		no s2.ownedElements
		
		some disj c5a, c5b: s5.ownedElements & Component |
		let i5 = s5.ownedElements & Interface,
			c4 = s4.ownedElements,
			c3 = s3.ownedElements,
			c1 = s1.ownedElements
		{
			isTrue[c5a.isComposite]
			isFalse[c5b.isComposite]
			s5.ownedElements & Component = c5a + c5b
			one i5
			c5a.parts.s5.partType = c5b
			one c4
			one c3
			no c3.myParts.deleteObjects
			no c3.myParts.newObjects
			no c3.myPorts.deleteObjects
			no c3.myPorts.newObjects
			no c3.myAttributes.deleteObjects
			no c3.myAttributes.newObjects
			no c3.myConnectors.deleteObjects
			no c3.myConnectors.newObjects
			one c1
			all c: c1 + c3 + c4 |
				c.redefines = c5a and c.resembles = c5a
		}		
		
		-- only one stratum can have errors, and top must resolve...
		Model::errorsOnlyAllowedIn[s2]
		Model::forceErrors[s2]
		Model::providesIsNotOptional[]
		Port.mandatory = Zero
		no optional
	}
}

run conflictAndResolution2 for 5 but exactly 6 Element, exactly 5 Component, exactly 1 Interface

pred conflictAndResolution2
{
	one s: Stratum - isTop.True |
		Model::errorsOnlyAllowedIn[s] and Model::forceErrors[s]
}

run onlyLeaf for 4 but exactly 2 Port
pred onlyLeaf()
{
	Model::noErrorsAllowed[]
	one Component
	one Stratum
	one Interface
	no isComposite.True
	some links
}


run leaf for 6 but exactly 2 Component, exactly 1 Stratum, 14 LinkEnd, 8 ConnectorEnd
pred leaf()
{
	Model::noErrorsAllowed[]
	some isComposite.True
	some c: Component | isFalse[c.isComposite] && some c.links
	some c: Component
	{
		isTrue[c.isComposite]
		some c.inferredLinks
	}
--	no optional
	all p: Port, c: Component, s: Stratum |
		no p.required.c.s or no p.provided.c.s
		
	one Interface
	no resembles
	no redefines
	no Attribute
}

-- i want to get a case where a single part is exchanged in the resembling component
-- and the new part type is different, requiring a port remap
run leaf2 for 5 but exactly 2 Stratum, 14 LinkEnd
pred leaf2()
{
	Model::errorsAllowedInTopOnly[]
	some isComposite.True
	-- ensure that the part replace is for a diffent type
	some c: Component |
	let ids = dom[c.myParts.replaceObjects],
	    newTypes = ran[c.myParts.replaceObjects].partType,
	    oldTypes = c.myParts.oldObjects_e[Stratum][ids].partType
	{
		no newTypes & oldTypes
		some c.parts.Stratum.portRemap
		no c.resembles & c.parts.Stratum.partType
		no c.myParts.addObjects
		no c.myParts.deleteObjects
		one c.myParts.replaceObjects
	}
	no myConnectors.replaceObjects
	no myConnectors.deleteObjects
	one redefines
	some resembles
}

run basic for 8 but exactly 6 Component, exactly 1 Stratum
pred basic()
{
	Model::errorsAllowedInTopOnly[]
	some parts
	no resembles
	no redefines
	some p: Port | no p.optional
	some p: Port | some p.optional
}

	
run someInterfaceResemblanceAndRedefinition for 4 but exactly 2 Stratum, 3 Interface, 8 ConnectorEnd
pred someInterfaceResemblanceAndRedefinition()
{
	Model::errorsAllowedInTopOnly[]
	-- want to see the redefinition of an interface
	some i: Interface | some i.resembles and no i.redefines and some i.superTypes
	some i: Interface | some i.resembles and some i.redefines
	some p: Port | no p.optional
	some p: Port | some p.optional
}


run simpleRedefinition for 3 but 3 Component, 1 Interface, 8 ConnectorEnd, 12 LinkEnd
pred simpleRedefinition()
{
	Model::noErrorsAllowed[]
	some parts
	some c: Component |
		one c.redefines and c.redefines = c.resembles and some c.myParts.replaceObjects
	some portRemap
	no optional
}

run simpleResemblance for 4 but exactly 2 Stratum, 4 Component, exactly 2 Interface, 8 ConnectorEnd, 8 LinkEnd
pred simpleResemblance()
{
	Model::noErrorsAllowed[]
	all c: Component |
		isTrue[c.isComposite] => no c.ports.Stratum.(setRequired + setProvided)
	some c: Component | one c.resembles
	some parts
	some defaultValue
	some attributeValues
	some attributeAliases
	some attributeCopyValues
}

run simpleResemblanceWithPartReplace for 4 but exactly 2 Stratum, 4 Component, exactly 2 Interface
pred simpleResemblanceWithPartReplace()
{
	Model::noErrorsAllowed[]
	all c: Component |
		isTrue[c.isComposite] => no c.ports.Stratum.(setRequired + setProvided)
	no optional
	
	some disj s1, s2: Stratum
	{
		s2.dependsOn = s1
		some disj i1, i2: s1.ownedElements & Interface
		{
			no i1.resembles and no i2.resembles
			
			some disj l1, l2, c1: s1.ownedElements & Component
			{
				let port = l1.ports.s1
				{
					one port
					port.setRequired = i1
					no port.setProvided
				}

				let port = l2.ports.s1
				{
					one port
					port.setRequired = i2
					no port.setProvided
				}
				
				let parts = c1.parts.s1 |
					one parts and parts.partType = l1

				some c2: s2.ownedElements & Component | let parts = c2.parts.s2
				{
					one parts
					c2.resembles = c1
					parts.partType = l2
					no c2.myConnectors.replaceObjects
					no c2.myConnectors.deleteObjects
					no c2.myPorts.replaceObjects
					no c2.myPorts.deleteObjects
				}
			}			
		}
	}
	Port.mandatory = Zero
	no redefines
	no attributes
	some portRemap
}

run simpleResemblanceWithPartReplaceAndPassThru for 4 but exactly 2 Stratum, 4 Component, exactly 2 Interface, 6 Port, 6 PortID, 4 ConnectorEnd, 8 LinkEnd
pred simpleResemblanceWithPartReplaceAndPassThru()
{
	Model::noErrorsAllowed[]
	all c: Component |
		isTrue[c.isComposite] =>
			no c.ports.Stratum.(setRequired + setProvided)
	
	some disj s1, s2: Stratum
	{
		s2.dependsOn = s1
		some disj i1, i2: s1.ownedElements & Interface
		{
			no i1.resembles
			no i2.resembles
			
			some disj l1, l2, c1: s1.ownedElements & Component
			{
				some disj port1, port2: l1.ports.s1
				{
					isFalse[l1.isComposite]
					l1.ports.s1 = port1 + port2
					port1.setRequired = i1
					no port1.setProvided
					port2.setProvided = i1
					no port2.setRequired
					no l1.links
				}

				some disj port1, port2: l2.ports.s1
				{
					isFalse[l2.isComposite]
					l2.ports.s1 = port1 + port2
					port1.setRequired = i2
					no port1.setProvided
					port2.setProvided = i2
					no port2.setRequired
					some l2.links
				}
				
				let parts = c1.parts.s1 |
					one parts and parts.partType = l1 and isTrue[c1.isComposite]

				some c2: s2.ownedElements & Component | let parts = c2.parts.s2
				{
					isTrue[c2.isComposite]
					one parts
					c2.resembles = c1
					parts.partType = l2
					no c2.myConnectors.replaceObjects
					no c2.myConnectors.deleteObjects
					no c2.myPorts.replaceObjects
					no c2.myPorts.deleteObjects
				}
			}			
		}
	}
	Port.mandatory = Zero
	no optional
	no redefines
	no attributes
	some portRemap
}


run simpleRedefineWithPartReplaceAndPassThru for 4 but exactly 2 Stratum, 4 Component, exactly 2 Interface, 6 Port, 6 PortID, 4 ConnectorEnd, 8 LinkEnd
pred simpleRedefineWithPartReplaceAndPassThru()
{
	Model::noErrorsAllowed[]
	all c: Component |
		isTrue[c.isComposite] =>
			no c.ports.Stratum.(setRequired + setProvided)
	
	some disj s1, s2: Stratum
	{
		s2.dependsOn = s1
		some disj i1, i2: s1.ownedElements & Interface
		{
			no i1.resembles
			no i2.resembles
			
			some disj l1, l2, c1: s1.ownedElements & Component
			{
				some disj port1, port2: l1.ports.s1
				{
					isFalse[l1.isComposite]
					l1.ports.s1 = port1 + port2
					port1.setRequired = i1
					no port1.setProvided
					port2.setProvided = i1
					no port2.setRequired
				}

				some disj port1, port2: l2.ports.s1
				{
					isFalse[l2.isComposite]
					l2.ports.s1 = port1 + port2
					port1.setRequired = i2
					no port1.setProvided
					port2.setProvided = i2
					no port2.setRequired
				}
				
				let parts = c1.parts.s1 |
					one parts and parts.partType = l1 and isTrue[c1.isComposite]

				some c2: s2.ownedElements & Component, disj port1, port2: Port | let parts = c2.parts.s2
				{
					port1 + port2 = c2.ports.s2
					no port1.setProvided
					no port2.setRequired

					isTrue[c2.isComposite]
					c2.redefines = c1
					c2.resembles = c1
					one parts
					parts.partType = l2
					no c2.myConnectors.newObjects
					no c2.myConnectors.deleteObjects
					no c2.myPorts.newObjects
					no c2.myPorts.deleteObjects
				}
			}			
		}
	}
	Port.mandatory = Zero
	no optional
	no attributes
	some portRemap
}

run redefOfRedef for 4 but exactly 1 Interface, exactly 4 Component, exactly 5 Element, exactly 4 Stratum
pred redefOfRedef()
{
	Model::errorsAllowedInTopOnly[]
	some disj st0, st1, st2, st3: Stratum
	{
		-- stratum constraints
		isTrue[st3.isTop]
		st3.dependsOn = st2 + st1
		st2.dependsOn = st0
		st1.dependsOn = st0

		-- top level redefines
		one st0.ownedElements & Interface
		some
			c0: st0.ownedElements & Component,
			c1: st1.ownedElements,
			c2: st2.ownedElements,
			c3: st3.ownedElements
		{
			c1.redefines = c0
			c1.resembles = c0
			c2.redefines = c0
			c2.resembles = c0
			c3.redefines = c0
			c3.resembles = c0
		}
	}
	
}

run multipleResemblanceRedef for 5 but exactly 1 Interface, exactly 5 Component, exactly 3 Stratum, 18 LinkEnd
pred multipleResemblanceRedef()
{
	Model::errorsAllowedInTopOnly[]
	some st0, st1, st2: Stratum
	{
		-- stratum constraints
		isTrue[st2.isTop]
		st2.dependsOn = st1
		st1.dependsOn = st0

		-- top level redefines
		one st0.ownedElements & Interface
		some c2: st2.ownedElements |
		some disj c1a, c1b: st1.ownedElements & Component |
		some disj c0a, c0b: st0.ownedElements & Component
		{
			no c1b.redefines
			c1b.resembles = c0b
			one c1b.myParts.replaceObjects
			no c1b.myParts.deleteObjects
			no c1b.myParts.addObjects
			no c1b.myPorts.deleteObjects
			no c1b.myPorts.addObjects
			no c1b.myPorts.replaceObjects
			
			c1a.redefines = c0b
			c1a.resembles = c1b + c0b
			one c1a.myParts.replaceObjects
			no c1a.myParts.deleteObjects
			no c1a.myParts.addObjects
			one c1a.myPorts.newObjects
			no c1a.myPorts.deleteObjects
			no c1a.myPorts.replaceObjects

			c2.redefines = c1b
		}
	}
}

run multipleResemblanceRedef2 for 5 but exactly 1 Interface, exactly 5 Component, exactly 2 Stratum, 8 LinkEnd
pred multipleResemblanceRedef2()
{
	Model::errorsAllowedInTopOnly[]
	some st0, st1: Stratum
	{
		-- stratum constraints
		isTrue[st1.isTop]
		st1.dependsOn = st0

		-- top level redefines
		one st0.ownedElements & Interface
		some disj c1a, c1b, c1c: st1.ownedElements & Component |
		some disj c0a, c0b: st0.ownedElements & Component
		{
			no c1b.redefines
			c1b.resembles = c0b
			one c1b.myParts.replaceObjects
			no c1b.myParts.deleteObjects
			no c1b.myParts.addObjects
			no c1b.myPorts.deleteObjects
			no c1b.myPorts.addObjects
			no c1b.myPorts.replaceObjects
			
			c1a.redefines = c0b
			c1a.resembles = c0b + c1c
			one c1a.myParts.replaceObjects
			no c1a.myParts.deleteObjects
			no c1a.myParts.addObjects
			one c1a.myPorts.newObjects
			no c1a.myPorts.deleteObjects
			no c1a.myPorts.replaceObjects

			no c1c.redefines
			no c1c.resembles
			some c1c.myParts.newObjects
		}
	}
}

-- never gives an answer -- can't have a resemblance defined in terms of delegation to self
-- must use the rewriting way
run recursiveDelegatingRedef for 5 but exactly 1 Interface, exactly 5 Component
pred recursiveDelegatingRedef()
{
	Model::errorsAllowedInTopOnly[]
	some c: Component |
		one c.redefines and c.redefines in c.^(parts.(c.home).partType)
}

run subtypes for 4 but exactly 4 Interface
pred subtypes()
{
	Model::errorsAllowedInTopOnly[]
	some i: Interface |
		no i.redefines and one redefines.(i.resembles)
	some redefines.Interface
	some resembles.Interface - redefines.Interface
	all i: resembles.Interface | some i.myOperations.addObjects and no i.myOperations.deleteObjects
}


run conflict for 4 but exactly 4 Stratum, exactly 5 Element, exactly 4 Component, exactly 1 Interface
run conflict for 7
run conflict for 5 but exactly 4 Stratum, exactly 6 Element, exactly 1 Interface, exactly 5 Component
pred conflict()
{
	Model::errorsAllowedInTopOnly[]
--	no isTop.True.ownedElements
	some isInvalid_e
}

run interfaceconflict for 4 but exactly 4 Stratum
run interfaceconflict for 5 but exactly 5 Stratum, exactly 5 Element, exactly 1 Interface, exactly 4 Component
pred interfaceconflict()
{
	Model::errorsAllowedInTopOnly[]
	no isTop.True.ownedElements
	all s: Stratum, i: Interface | lone i.implementation.s
	some s: Stratum, i: Interface |
		s in i.isInvalid_e and no i.redefines
}


-- ask for a structure where the top level has 2 children
-- and each child descends on a final stratum
run simpleRedefine for 5 but exactly 3 Stratum, exactly 5 Element, exactly 1 Interface, exactly 4 Component, 10 ConnectorEnd, 16 LinkEnd
pred simpleRedefine()
{
	Model::errorsAllowedInTopOnly[]
	some st0, st1, st2: Stratum
	{
		-- stratum constraints
		isTrue[st2.isTop]
		st2.dependsOn = st1
		st1.dependsOn = st0

		-- top level redefines
		one st0.ownedElements & Interface
		let
			c2 = st2.ownedElements,
			c1 = st1.ownedElements |
		some disj c0a, c0b: st0.ownedElements & Component
		{
			isFalse[c0a.isComposite]
			isFalse[c0b.isComposite]
			#c0a.ports.st0 = 2
			some disj p1, p2: c0a.ports.st0 |
				no p1.required and no p2.provided
			one c0b.ports.st0
			no c0b.ports.st0.provided
			one c0a.myAttributes.addObjects
			one c0b.myAttributes.addObjects
			
			one c2
			one c2.myParts.addObjects
			c2.myParts.newObjects.partType = c0b
			one c2.myParts.deleteObjects
			one c2.myParts.replaceObjects
			c2.redefines = c1
			
			one c1
			no c1.resembles
			#c1.myParts.addObjects = 2
			c1.myParts.newObjects.partType = c0a + c0b
			let s = c1.home |
			some p : c1.parts.s |
				s->c1 not in p.linkedToOutside
			
			-- check now
--			isTrue[ModelState.valid] <=>
--			{
--				c1.parts.st1.partType = c0a
--				#c1.parts.st1 = 2
--				c2.parts.st2.partType = c0b
--				#c2.parts.st2 = 2
--			}
		}
	}

	some portRemap
	some defaultValue
	some attributeValues
	some attributeAliases
	some attributeCopyValues
	some Component.myConnectors.replaceObjects
	no optional
}

-- ask for a structure where the top level has 2 children
-- and each child descends on a final stratum
run simpleRedefine2 for 5
	but exactly 3 Stratum, exactly 7 Element,
	exactly 1 Interface, exactly 6 Component,
	exactly 6 structure/Ports/Deltas, exactly 8 Port, exactly 8 PortID,
	exactly 8 Connector, exactly 6 Part,
	exactly 1 structure/Operations/Deltas,
	exactly 1 structure/InterfaceImplementation/Deltas,
	exactly 1 Operation, exactly 1 OperationID, exactly 1 InterfaceImplementation,
	exactly 6 structure/Attributes/Deltas, exactly 6 Attribute, exactly 4 AttributeID,
	exactly 1 AttributeType, exactly 1 AttributeValue, exactly 1 ComponentImplementation, 16 ConnectorEnd
pred simpleRedefine2()
{
	Model::errorsAllowedInTopOnly[]
	some st0, st1, st2: Stratum
	{
		-- stratum constraints
		isTrue[st2.isTop]
		st2.dependsOn = st1
		st1.dependsOn = st0

		-- top level redefines
		one st0.ownedElements & Interface
		let
			c2 = st2.ownedElements,
			c1 = st1.ownedElements |
		some disj c0a, c0b, c0c, c0d: st0.ownedElements & Component
		{
			isFalse[c0a.isComposite]
			isFalse[c0b.isComposite]
			isTrue[c0c.isComposite]
			isTrue[c0c.isComposite]
			#c0a.ports.st0 = 2
			some disj p1, p2: c0a.ports.st0 |
				no p1.required and no p2.provided
			one c0b.ports.st0
			no c0b.ports.st0.provided
			one c0a.myAttributes.addObjects
			one c0b.myAttributes.addObjects
			
			one c2
			one c2.myParts.addObjects
			c2.myParts.newObjects.partType = c0b
			one c2.myParts.deleteObjects
			one c2.myParts.replaceObjects
			c2.redefines = c1
			
			one c1
			no c1.resembles
			#c1.myParts.addObjects = 2
			c1.myParts.newObjects.partType = c0a + c0b
			let s = c1.home |
			some p : c1.parts.s |
				s->c1 not in p.linkedToOutside
			
			-- check now
--			isTrue[ModelState.valid] <=>
--			{
--				c1.parts.st1.partType = c0a
--				#c1.parts.st1 = 2
--				c2.parts.st2.partType = c0b
--				#c2.parts.st2 = 2
--			}
		}
	}

	some portRemap
	some defaultValue
	some attributeValues
	some attributeAliases
	some attributeCopyValues
	some Component.myConnectors.replaceObjects
	no optional
}

-- test a complex chain of redefs where in one stream we del and then add on top of that
-- and in the other we just replace.  the delete and replace don't work on the same partname
-- also has a small test for "relaxed"
run complexRedefine for 5 but exactly 6 Stratum, exactly 7 Element, exactly 1 Interface, exactly 6 Component, exactly 6 structure/Attributes/Deltas, exactly 6 structure/Ports/Deltas, exactly 3 Attribute, exactly 1 AttributeType, exactly 3 AttributeValue, 8 ConnectorEnd
run complexRedefine for 6 but exactly 6 Stratum, exactly 7 Element, exactly 1 Interface, exactly 6 Component, 10 ConnectorEnd
pred complexRedefine()
{
	Model::errorsAllowedInTopOnly[]
	some st0, st1, st2, st3, st4, st5: Stratum
	{
		-- stratum constraints
		isTrue[st5.isTop]
		st5.dependsOn = st3 + st4
		st4.dependsOn = st2 + st1
		st3.dependsOn = st1
		st2.dependsOn = st1
		st1.dependsOn = st0
		isTrue[st5.isRelaxed]
		isTrue[st4.isRelaxed]
		isTrue[st3.isRelaxed]
		isFalse[st2.isRelaxed]
		isTrue[st1.isRelaxed]
		isTrue[st0.isRelaxed]

		-- top level redefines
		some portRemap
		one optional
		one st0.ownedElements & Interface
		let
			c5 = st5.ownedElements,
			c4 = st4.ownedElements,
			c3 = st3.ownedElements,
			c2 = st2.ownedElements,
			c1 = st1.ownedElements |
		some disj c0a, c0b: st0.ownedElements & Component
		{
			isFalse[c0a.isComposite]
			isFalse[c0b.isComposite]
			#c0a.ports.st0 = 2
			some disj p1, p2: c0a.ports.st0 |
				no p1.required and no p2.provided
			one c0b.ports.st0
			no c0b.ports.st0.provided
			
			one c1 one c2 one c3 one c4 no c5
			c2.redefines = c1
			c3.redefines  = c1
			c4.redefines = c1
	
			no c1.resembles
			#c1.myParts.addObjects = 2
			no c1.myParts.replaceObjects
			c1.myParts.newObjects.partType = c0a
			
			one c2.myParts.deleteObjects
			no c2.myParts.addObjects
			no c2.myParts.replaceObjects
			
			one c3.myParts.replaceObjects
			no c3.myParts.addObjects
			no c3.myParts.deleteObjects
			
			-- ensure that they are working on a different partname
			dom[c3.myParts.replaceObjects] != c2.myParts.deleteObjects
			
			one c4.myParts.addObjects
			no c4.myParts.deleteObjects
			no c4.myParts.replaceObjects
			
--			st5 not in c1.isValid_e
		}
	}
}

/*
-- test a complex chain of redefs where in one stream we del and then add on top of that
-- and in the other we just replace.  the delete and replace don't work on the same partname
-- also has a small test for "relaxed"
-- however, the base component depends on another lower down component which is also redefined...
run complexRedefineWithChain for 8 but 5 int
pred complexRedefineWithChain()
{
	some st0, st1, st2, st3, st4, st5: Stratum
	{
		-- stratum constraints
		isTrue[st5.isTop]
		st5.dependsOn = st3 + st4
		st4.dependsOn = st2 + st1
		st3.dependsOn = st1
		st2.dependsOn = st1
		st1.dependsOn = st0
		isTrue[st5.relaxed]
		isTrue[st4.relaxed]
		isTrue[st3.relaxed]
		isFalse[st2.relaxed]
		isTrue[st1.relaxed]
		isTrue[st0.relaxed]

		-- top level redefines
		no st0.ownedElements & Interface
		let
			c5 = st5.ownedElements,
			c4 = st4.ownedElements,
			c2 = st2.ownedElements,
			c1 = st1.ownedElements |
		some c0a, c0b, c0c: st0.ownedElements & Component |
		some c3a, c3b: st3.ownedElements & Component
		{
			disj[c0a, c0b, c0c]
			isFalse[c0a.isComposite]
			isFalse[c0b.isComposite]

			isTrue[c0c.isComposite]
			one c0c.myParts.addObjects
			no c0c.myParts.deleteObjects
			no c0c.myParts.replaceObjects
			c0c.myParts.newObjects.partType = c0b
			
			one c1 one c2 one c4 no c5
			c2.redefines = c1
			c4.redefines = c1

			no c1.redefines
			c1.resembles = c0c
			isTrue[c1.isComposite]
			#c1.myParts.addObjects = 2
			no c1.myParts.replaceObjects
			c1.myParts.newObjects.partType = c0a
			
			one c2.myParts.deleteObjects
			isTrue[c2.isComposite]
			no c2.myParts.addObjects
			no c2.myParts.replaceObjects
			
			c3a.redefines = c1
			isTrue[c3a.isComposite]
			one c3a.myParts.replaceObjects
			no c3a.myParts.addObjects
			no c3a.myParts.deleteObjects

			c3b.redefines = c0c
			isTrue[c3b.isComposite]
			one c3b.myParts.replaceObjects
			no c3b.myParts.addObjects
			no c3b.myParts.deleteObjects
						
			-- ensure that they are working on a different partname
			dom[c3a.myParts.replaceObjects] != c2.myParts.deleteObjects
			
			isTrue[c4.isComposite]
			one c4.myParts.addObjects
			no c4.myParts.deleteObjects
			no c4.myParts.replaceObjects
			
			-- check now
			isTrue[ModelState.valid] <=>
			{
			}
		}
	}

	#Stratum = 6
	#Component = 8
	no Interface
}

-- test a complex chain of redefs where in one stream we del and then add on top of that
-- and in the other we just replace.  the delete and replace both work on the same partname
-- the replaced element should remain...
run complexRedefine2 for 6
pred complexRedefine2()
{
	some st0, st1, st2, st3, st4, st5: Stratum
	{
		-- stratum constraints
		isTrue[st5.isTop]
		st5.dependsOn = st3 + st4
		st4.dependsOn = st2
		st3.dependsOn = st1
		st2.dependsOn = st1
		st1.dependsOn = st0

		-- top level redefines
		let
			c5 = st5.ownedElements,
			c4 = st4.ownedElements,
			c3 = st3.ownedElements,
			c2 = st2.ownedElements,
			c1 = st1.ownedElements |
		some c0a, c0b: st0.ownedElements
		{
			c0a != c0b
			
			one c1 one c2 one c3 one c4 no c5
			c2.redefines = c1
			c3.redefines  = c1
			c4.redefines = c1
	
			no c1.resembles
			#c1.myParts.addObjects = 2
			c1.myParts.newObjects.partType = c0a
			
			one c2.myParts.deleteObjects
			no c2.myParts.addObjects
			no c2.myParts.replaceObjects
			
			one c3.myParts.replaceObjects
			no c3.myParts.addObjects
			no c3.myParts.deleteObjects
			
			-- ensure that they are working on the same partname
			dom[c3.myParts.replaceObjects] = c2.myParts.deleteObjects
			
			one c4.myParts.addObjects
			no c4.myParts.deleteObjects
			no c4.myParts.replaceObjects
			
			-- check now
			isTrue[ModelState.valid] <=>
			{
			}
		}
	}

	#Stratum = 6
	#Component = 6
	no Interface
}

-- test a complex chain of redefs where in one stream we replace and in the other we
-- also replace.  test this results in an error which is detected
run complexRedefine3 for 6
pred complexRedefine3()
{
	some st0, st1, st2, st3, st4, st5: Stratum
	{
		-- stratum constraints
		isTrue[st5.isTop]
		st5.dependsOn = st3 + st4
		st4.dependsOn = st2
		st3.dependsOn = st1
		st2.dependsOn = st1
		st1.dependsOn = st0

		-- top level redefines
		let
			c5 = st5.ownedElements,
			c4 = st4.ownedElements,
			c3 = st3.ownedElements,
			c2 = st2.ownedElements,
			c1 = st1.ownedElements |
		some c0a, c0b: st0.ownedElements
		{
			c0a != c0b
			
			one c1 one c2 one c3 one c4 no c5
			c2.redefines = c1
			c3.redefines  = c1
			c4.redefines = c1
	
			no c1.resembles
			#c1.myParts.addObjects = 2
			c1.myParts.newObjects.partType = c0a
			
			no c2.myParts.deleteObjects
			no c2.myParts.addObjects
			one c2.myParts.replaceObjects
			
			one c3.myParts.replaceObjects
			no c3.myParts.addObjects
			no c3.myParts.deleteObjects
			
			-- ensure that they are working on the same partname
			dom[c3.myParts.replaceObjects] = dom[c2.myParts.replaceObjects]
			
			one c4.myParts.addObjects
			no c4.myParts.deleteObjects
			no c4.myParts.replaceObjects
			
			-- check now
			isTrue[ModelState.valid] <=>
			{
				st5 not in c1.isValid_e
			}
		}
	}

	#Stratum = 6
	#Component = 6
	no Interface
}*/