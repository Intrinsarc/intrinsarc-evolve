module structure

open util/boolean as boolean
open util/relation as relation
open deltas[Stratum, Component, PartID, Part] as Parts
open deltas[Stratum, Component, PortID, Port] as Ports
open deltas[Stratum, Component, ConnectorID, Connector] as Connectors
open deltas[Stratum, Component, AttributeID, Attribute] as Attributes
open deltas[Stratum, Interface, OperationID, Operation] as Operations
open deltas[Stratum, Interface, InterfaceImplementationID, InterfaceImplementation] as InterfaceImplementation


one sig Model
{
	-- normally this should be set to none
	errorsAllowed: set Stratum,
	providesIsOptional: Bool
}

////open savcbs-stratum-V
sig Stratum
{
	-- strata that this directly depends on
	dependsOn: set Stratum,
////close savcbs-stratum-V
	-- does this expose stratum it depends on?
	isRelaxed: Bool,

	-- derived state -- exposes strata includes this and canSee
	exposesStrata: set Stratum,
	canSee: set Stratum,
	canSeePlusMe: set Stratum,

	-- simple is all that we directly depend on
	-- taking away what any children depend on
	simpleDependsOn: set Stratum,

	-- a single top exists which binds directly
	-- any independent stratum
	isTop: Bool,
	-- this is every stratum that can be seen from here down
	transitive: set Stratum,
	transitivePlusMe: set Stratum,
	ownedElements: set Element,
	-- each attribute type is owned by a single stratum
	attributeTypes: set AttributeType,

	-- elements that redefine another
	redefining: set Element,
	-- components that are new definitions
	defining: set Element
}
{
	defining = ownedElements - redefining
	canSeePlusMe = canSee + this
	transitivePlusMe = transitive + this
}

////open element-home
abstract sig Element
{
	home: Stratum,
////pause element-home
	redefines: lone Element,
	resembles: set Element,

	-- for a given stratum, a component resembles other components in a given stratum view
	resembles_e: Element -> Stratum,
	-- does this act as a non-primed for a particular stratum
	actsAs_e: Element -> Stratum,
	-- is this element valid for a given stratum?
	isInvalid_e: set Stratum
////unpause element-home
}
{
	-- owned by a single stratum
	home = ownedElements.this
}
////close element-home

////open component
////open savcbs-component-V
sig Component extends Element
{
////pause savcbs-component-V
////pause component
	-- composite or leaf?
	-- all components apart from direct impl components are always composite
	isComposite: Bool,
	implementation: lone ComponentImplementation,

	-- the final result, after taking redef + resemblance into account
	iDParts: PartID -> Part -> Stratum,
////unpause component
	parts: Part -> Stratum,
	ports: Port -> Stratum,
	connectors: Connector -> Stratum,
	attributes: Attribute -> Stratum,
	
////unpause savcbs-component-V
	myParts: lone Parts/Deltas,
	myPorts: lone Ports/Deltas,
	myConnectors: lone Connectors/Deltas,
	myAttributes: lone Attributes/Deltas,
////close savcbs-component-V
////close component
	
	-- the internal links, used for port type inferencing
	links: PortID -> PortID,
	inferredLinks: Port -> Port -> Stratum
}
{
	-- for components
	redefines + resembles in Component
	-- propagate up the objects from the delta into the sig, to make it more convenient
	parts = myParts.objects
	ports = myPorts.objects
	connectors = myConnectors.objects
	attributes = myAttributes.objects

	-- a leaf has an implementation
	isFalse[isComposite] <=> one implementation
	
	-- form idParts
	iDParts = {n: PartID, p: Part, s: Stratum | s -> n -> p in myParts.objects_e}
	
	-- make sure links has no duplication
	no ~links & links
	
	-- we only have links for leaves, so all ports must be new
	dom[links] + ran[links] in myPorts.newIDs
}

-- ensure each delta is composed by only one component
fact
{
	all p: Parts/Deltas | one myParts.p
	all p: Ports/Deltas | one myPorts.p
	all c: Connectors/Deltas | one myConnectors.c
	all a: Attributes/Deltas | one myAttributes.a
}

sig Interface extends Element
{
	-- the expanded elements
	operations: Operation -> Stratum,	
	implementation: InterfaceImplementation -> Stratum,	
	superTypes: Interface -> Stratum,

	-- the deltas
	myOperations: Operations/Deltas,
	myImplementation: InterfaceImplementation/Deltas
}
{
	-- for interfaces
	redefines + resembles in Interface
	-- propagate up the objects from the delta into the sig, to make it more convenient
	operations = myOperations.objects	
	implementation = myImplementation.objects
}
-- ensure each delta is composed by only one interface
fact
{
	all p: Operations/Deltas | one myOperations.p
	all i: InterfaceImplementation/Deltas | one myImplementation.i
}


-- each artifact must have a id, so it can be replaced or deleted
sig PartID, PortID, ConnectorID, AttributeID, OperationID, InterfaceImplementationID {}

sig Part
{
	partType: Component,
	-- remap a port from this part onto the port of a part that we are replacing
	-- (new port -> old, replaced port)
////open PART_PORT_REMAP
	portRemap: PortID lone -> lone PortID,
	portMap: Stratum -> PortID lone -> lone Port,
////close PART_PORT_REMAP

	-- the values of the attributes are set in the part   (child id -> parent id)
	-- although they don't have to be set if we want to take the default
	attributeValues: AttributeID -> lone AttributeValue,
	-- do we alias a parent attribute?
	attributeAliases: AttributeID -> lone AttributeID,
	-- or do we simply copy a parent attribute, but retain our own state?
	attributeCopyValues: AttributeID -> lone AttributeID,

	-- derived state -- the parts that the connectors link to
	linkedToParts: Part -> Stratum -> Component,
	-- derived state -- any componts that the connectors link to
	linkedToOutside: Stratum -> Component
}

abstract sig Index {}
one sig Zero, One, Two, Three extends Index {}

pred isContiguousFromZero(indices: set Index)
{
	indices = indices.*(Three->Two + Two->One + One->Zero)
}

sig Port
{
	-- set values are what the user has explicitly set
	setProvided, setRequired: set Interface,
	-- provided and required are inferred
	provided, required: Interface -> Stratum -> Component,
////open PORT_MULTIPLICITY
	mandatory, optional: set Index
////close PORT_MULTIPLICITY
}
{
	-- mandatory indices start at 0, optional start from mandatory end, no overlap
	-- all contiguous and must have some indices
////open PORT_MULTIPLICITY2
	isContiguousFromZero[mandatory] and
		isContiguousFromZero[mandatory + optional]
	no mandatory & optional			-- no overlap
	some mandatory + optional		-- but must have some indices
////close PORT_MULTIPLICITY2
}


sig Connector
{
    -- require 2 ends
    ends: set ConnectorEnd
}
{
	-- ensure 2 connector ends using a trick felix taught me
	some disj end1, end2: ConnectorEnd | ends = end1 + end2
    all end: ends |
        end.otherEnd = ends - end
}

abstract sig ConnectorEnd
{
    portID: PortID,
    port: Port -> Stratum -> Component,
    index: Index,
    otherEnd: ConnectorEnd
}
{
    -- an end is owned by one connector
    one ends.this
}

sig ComponentConnectorEnd extends ConnectorEnd
{
}

sig PartConnectorEnd extends ConnectorEnd
{
    partID: PartID,
    cpart: Part -> Stratum -> Component
}


sig Attribute
{
	attributeType: AttributeType,
	defaultValue: lone AttributeValue
}
{
	some defaultValue =>
		defaultValue.valueType = attributeType
}

sig AttributeValue
{
	valueType: AttributeType
}

sig AttributeType
{
}
{
	-- owned by one stratum
	one attributeTypes.this
}

sig Operation
{
	-- this identifies the impelementation id and signature
}

sig InterfaceImplementation
{
	-- this identifies the interface implementation clas or no s.dependsOns...
}

sig ComponentImplementation
{
	-- this identifies the component implementation class...
}

-- used for port inference
-- a bit like a connector, but multiplicity and optionality don't count
abstract sig LinkEnd
{
	linkPortID: PortID,
	linkError: Stratum -> Component
	-- the internal interfaces are the interfaces presented inside the component content area
	--   for a port, it is the interfaces seen internally (opposite)
	--   for a port instance, it is the interfaces seen externally (same)
}

sig ComponentLinkEnd extends LinkEnd
{
}

sig PartLinkEnd extends LinkEnd
{
	linkPartID: PartID
}

-- translate from port id to component link end -- guaranteed to be 1 per id
fun getComponentLinkEnd(id: PortID): one ComponentLinkEnd
{
	{ end: ComponentLinkEnd | end.linkPortID = id }
}

-- translate from a port/part to a part link end -- guaranteed to be 1 per pair
fun getPartLinkEnd(portID: PortID, partID: PartID): PartLinkEnd
{
	{ end: PartLinkEnd | end.linkPortID = portID and end.linkPartID = partID }
}

fun ComponentLinkEnd::getPort(s: Stratum, c: Component): one Port
{
	c.myPorts.objects_e[s][this.linkPortID]
}

fun PartLinkEnd::getPortInstance(s: Stratum, c: Component): Port -> Part
{
	let
		cpart = c.myParts.objects_e[s][this.linkPartID],
		cport = cpart.partType.myPorts.objects_e[s][this.linkPortID] |
	cport -> cpart
}

-- get the port of a component connector
fun ComponentConnectorEnd::getPort(s: Stratum, c: Component): lone Port
{
    c.myPorts.objects_e[s][this.portID]
}

-- should return only 1 Port, unless the component is invalid. NOTE: the component owns the part
fun PartConnectorEnd::getPortInstance(s: Stratum, c: Component): Port -> Part
{
    let
        ppart = c.myParts.objects_e[s][this.partID],
        port = ppart.portMap[s][this.portID] |
    port -> ppart
}

fun PartLinkEnd::getPortInstanceRequired(s: Stratum, c: Component): set Interface
{
	let portPart = this.getPortInstance[s, c],
		pport = dom[portPart],
		ppartType = ran[portPart].partType
	{
		pport.required.ppartType.s
	}
}

fun PartLinkEnd::getPortInstanceProvided(s: Stratum, c: Component): set Interface
{
	let portPart = this.getPortInstance[s, c],
		pport = dom[portPart],
		ppartType = ran[portPart].partType
	{
		pport.provided.ppartType.s
	}
}


