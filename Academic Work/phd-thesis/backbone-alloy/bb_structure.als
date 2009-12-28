module bb_structure

open base_deltas[PartID, Part] as Parts
open base_deltas[PortID, Port] as Ports
open base_deltas[ConnectorID, Connector] as Connectors
open base_deltas[AttributeID, Attribute] as Attributes
open base_deltas[OperationID, Operation] as Operations
open base_deltas[InterfaceImplementationID, InterfaceImplementation] as InterfaceImplementation
open base_deltas[ComponentImplementationID, ComponentImplementation] as ComponentImplementation
open base_deltas[PrimitiveTypeImplementationID, PrimitiveTypeImplementation] as PrimitiveTypeImplementation
open base_deltas[LinkID, Link] as Links

////open component
sig Component extends Element
{
	myParts: lone Parts/Deltas,
	myPorts: lone Ports/Deltas,
	myConnectors: lone Connectors/Deltas,
	myAttributes: lone Attributes/Deltas,
	myCImplementation: lone ComponentImplementation/Deltas,
	myLinks: lone Links/Deltas,
	
////comment component -- derived, perspective state
	-- the final result, after taking replacement + resemblance into account
	iDParts: PartID -> Part -> Stratum,
	-- composite or leaf?
	isComposite: set Stratum,
	parts: Part -> Stratum,
	ports: Port -> Stratum,
	connectors: Connector -> Stratum,
	attributes: Attribute -> Stratum,
	cimplementation: ComponentImplementation -> Stratum,
	links: Link -> Stratum,
	
////close component
	-- the internal links, used for port type inferencing
	inferredLinks: Port -> Port -> Stratum
}
{
	-- rule: COMPONENT_RESEMBLANCE -- for components
	replaces + resembles in Component
	-- propagate up the objects from the delta into the sig, to make it more convenient
	parts = myParts.objects
	ports = myPorts.objects
	connectors = myConnectors.objects
	attributes = myAttributes.objects
	cimplementation = myCImplementation.objects
	links = myLinks.objects

	-- form idParts
	iDParts = {n: PartID, p: Part, s: Stratum | s -> n -> p in myParts.objects_e}
}

-- ensure each delta is composed by only one component
fact
{
	all p: Parts/Deltas | one myParts.p
	all p: Ports/Deltas | one myPorts.p
	all c: Connectors/Deltas | one myConnectors.c
	all a: Attributes/Deltas | one myAttributes.a
	all i: ComponentImplementation/Deltas | one myCImplementation.i
	all l: Links/Deltas | one myLinks.l
}

sig PrimitiveType extends Element
{
	myTImplementation: lone PrimitiveTypeImplementation/Deltas,
	-- the expanded elements
	timplementation: PrimitiveTypeImplementation -> Stratum
}
{
	replaces + resembles in PrimitiveType
	-- propagate up the objects from the delta into the sig, to make it more convenient
	timplementation = myTImplementation.objects
}
-- ensure each delta is composed by only one primitive type
fact
{
	all t: PrimitiveTypeImplementation/Deltas | one myTImplementation.t
}

////open interface
sig Interface extends Element
{
	-- the deltas
	myOperations: lone Operations/Deltas,
	myIImplementation: lone InterfaceImplementation/Deltas,
////comment interface -- derived, perspective state
	-- the expanded elements
	operations: Operation -> Stratum,	
	iimplementation: InterfaceImplementation -> Stratum,	
	superTypes: Interface -> Stratum
}
////close interface
{
	-- for interfaces
	replaces + resembles in Interface
	-- propagate up the objects from the delta into the sig, to make it more convenient
	operations = myOperations.objects	
	iimplementation = myIImplementation.objects
}
-- ensure each delta is composed by only one interface
fact
{
	all p: Operations/Deltas | one myOperations.p
	all i: InterfaceImplementation/Deltas | one myIImplementation.i
}


-- each artifact must have a id, so it can be replaced or deleted
sig PartID, PortID, ConnectorID, AttributeID, OperationID, InterfaceImplementationID, ComponentImplementationID, PrimitiveTypeImplementationID, LinkID {}

sig Part
{
  -- rule: WF_PART_TYPE -- each part must have a type
	partType: Component,
	-- remap a port from this part onto the port of a part that we are replacing
	-- (new port -> old, replaced port)
	portRemap: PortID lone -> lone PortID,
	portMap: Stratum -> PortID lone -> lone Port,

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
	mandatory, optional: set Index
}
{
	-- mandatory indices start at 0, optional start from mandatory end, no overlap
	-- all contiguous and must have some indices
	-- rule: PORT_MULTIPLICITY
	isContiguousFromZero[mandatory] and
		isContiguousFromZero[mandatory + optional]
	no mandatory & optional			-- no overlap
	some mandatory + optional		-- but must have some indices
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
	-- rule: WF_ATTRIBUTE_TYPE -- an attribute must have a type
	attributeType: PrimitiveType,
	defaultValue: lone AttributeValue
}
{
	-- rule: WF_ATTRIBUTE_DEFAULT
	some defaultValue =>
		defaultValue.valueType = attributeType
}

sig AttributeValue
{
	valueType: PrimitiveType
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

sig PrimitiveTypeImplementation
{
    -- this indentified the implementation of a primitive type
}

-- links are used for port inference
-- a bit like a connector, but multiplicity and optionality don't count
sig Link
{
	linkEnds: PortID -> PortID
}
{
	lone linkEnds
}

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
