
sig Stratum
{
	parent:				lone Stratum,
	dependsOn:			set Stratum,
	ownedElements:		set Element
}
{
	-- no cycles
	this not in dependsOn.*@dependsOn
}


sig Element
{
	home: 				Stratum,
	replaces: 			lone Element,
	resembles: 			set Element,
	
	-- deltas
	addedObjects:     	set Constituent,
	deletedObjects:  	set Constituent,
	replacedObjects: 	Constituent one -> lone Constituent,	
}
{
	-- owned by a single stratum
	home = ownedElements.this
	-- visibility
	(replaces + resembles).@home in home.*dependsOn
}

sig Constituent
{
}
{
	-- introduced exactly once in an add or replace
	one (addedObjects.this + replacedObjects.Constituent.this)
}

-- determine the expanded resemblance hierarchy, taking replacement into account
-- eresembles(s, e) = e' if e is replaced in s,
--                  = 
                      

-- independent strata
pred independent[a, b: Stratum] {
	a not in b + b.*dependsOn and b not in a + a.*dependsOn
}
pred independentOnCommonBase[a, b: Stratum] {
	independent[a, b] and some a.*dependsOn & b.*dependsOn
}
