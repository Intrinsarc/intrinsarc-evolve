module base_structure

open util/boolean as boolean
open util/relation as relation


one sig Model
{
	-- normally this should be set to none
	errorsAllowed: set Stratum,
	providesIsOptional: Bool
}

////open stratum
sig Stratum
{
	parent: lone Stratum,
	
	-- external strata that this depends directly on
	dependsOn: set Stratum,
	-- any child packages that are explicitly depended on
	dependsOnNested: set Stratum,
	-- nested stratum are any with this as the direct parent
	nestedStrata: set Stratum,
	
	-- does this export stratum it depends on?
	isRelaxed: Bool,
	ownedElements: set Element,

////comment stratum     -- derived state
	-- derived state -- export strata includes this and canSee
	exportsStrata: set Stratum,
	canSee: set Stratum,

	-- simple is all that we directly depend on
	-- taking away what any children depend on
	simpleDependsOn: set Stratum,

	-- a single top exists which binds directly
	-- any independent stratum
	isTop: Bool,
	-- this is every stratum that can be seen from here down
	transitive: set Stratum,
	-- elements that replace others
	replacing: set Element,
////pause stratum
	canSeePlusMe: set Stratum,
	transitivePlusMe: set Stratum,

	-- components that are new definitions
	defining: set Element
////unpause stratum
}
////close stratum
{
	defining = ownedElements - replacing
	canSeePlusMe = canSee + this
	transitivePlusMe = transitive + this
	nestedStrata = {n: Stratum | n.@parent = this}
}

---------------------------------------------------
-- handle the basics of resemblance and replacement
---------------------------------------------------

////open element
abstract sig Element
{
	home: Stratum,
	replaces: lone Element,
	resembles: set Element,

////comment element -- derived state
	-- for a given stratum, a component resembles other components in a given stratum view
	resembles_e: Element -> Stratum,
	-- does this act as a non-primed for a particular stratum
	actsAs_e: Element -> Stratum,
	-- is this element valid for a given stratum?
	isInvalid_e: set Stratum
}
////close element
{
	-- rule: ELEMENT_HOME -- element is owned by a single stratum
	home = ownedElements.this
}
