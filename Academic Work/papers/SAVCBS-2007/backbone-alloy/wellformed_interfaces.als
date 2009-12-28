module wellformed_interfaces

open util/boolean as boolean
open util/relation as relation
open util/ternary as ternary
open structure

-- check that the interface is well formed
pred interfaceIsWellFormed(s: Stratum, i: Interface)
{
	-- no circular resemblance for the interface, from the perspective of this stratum
	i not in i.^(resembles_e.s)
	-- must have some operations
	some i.myOperations.objects.s
	-- should have only 1 operation definition per id
	i.myOperations.oneObjectPerID[s]
	-- don't necessarily have to introduce a new implementation
	lone i.myImplementation.newObjects	
	-- we should only have one implementation, so if we resemble something
	-- we must replace the implementation
	one i.implementation.s
}
