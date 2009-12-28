module redefinition_types

open util/boolean as boolean
open util/relation as relation
open util/ternary as ternary
open structure


-- indicate that a redefinition replaces the entire component by deleting all artefacts
pred isReplaceRedefinition(c: Component)
{
	let owner = c.home
	{
		one c.redefines
		c.myParts.cleanSlate[owner]
		c.myPorts.cleanSlate[owner]
		c.myConnectors.cleanSlate[owner]
		c.myAttributes.cleanSlate[owner]
	}
}

-- indicate that a redefinition replaces the insides of the component by deleting all parts and connectors
pred isInsidesReplaceRedefinition(c: Component)
{
	let owner = c.home
	{
		one c.redefines
		c.myParts.cleanSlate[owner]
		c.myConnectors.cleanSlate[owner]
		c.myPorts.onlyAdds[owner]
		c.myAttributes.onlyAdds[owner]
	}
}

pred isReplaceRedefinition(i: Interface)
{
	let owner = i.home
	{
		one i.redefines
		i.myOperations.cleanSlate[owner]
		i.myImplementation.cleanSlate[owner]
	}
}