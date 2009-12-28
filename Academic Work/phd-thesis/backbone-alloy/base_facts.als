module facts

open base_structure


fact StratumFacts
{
	one isTop.True
	all s: Stratum |
	-- a stratum depends on any external it explicitly declares, plus any nested strata
////open cansee
	let fullDependsOn = dependsOn + nestedStrata + ^parent.dependsOn {
////pause cansee
		-- dependsOn cannot be nested packages
		all d : s.dependsOn | s not in d.*parent
		
		-- dependsOnNested must be nested packages.  these can be deeply nested also.
		all n : s.dependsOnNested | s in n.^parent
	
		-- for relaxed, export what it depends on and their exports
		-- for strict and relaxed, export any direct nested dependencies
////open exports
		isTrue[s.isRelaxed] =>
			s.exportsStrata = s + s.(dependsOn + dependsOnNested).exportsStrata
		else
			s.exportsStrata = s + s.dependsOnNested.exportsStrata
////close exports

		-- used for partial ordering
		-- contains nothing the children already depend on
////open simple-depends
		s.simpleDependsOn = s.fullDependsOn - s.fullDependsOn.transitive

		-- no cycles
////comment simple-depends -- no cycles allowed
    -- rule: STRATUM_ACYCLIC
		s not in s.transitive
////close simple-depends

		-- can only see what others export
		-- NOTE: if a nested stratum only exports a public package, then this is 
		--       all that the parent can see
////unpause cansee
		-- rule: ELEMENT_VISIBILITY -- defined what can be seen.  augmented by other checks
		s.canSee = s.fullDependsOn.exportsStrata
	
		-- the strata we can see using the dependency graph
		s.transitive = s.^fullDependsOn
////close cansee

		-- a stratum is called top if no stratum depends on it
		isTrue[s.isTop] <=> no simpleDependsOn.s
		
		-- rule: STRATUM_ELEMENT_REPLACEMENT -- have max of one replacement of an element per stratum
		all e: Element |
			lone s.ownedElements & replaces.e
		-- ties up replacing and replaces
		s.replacing = s.ownedElements & dom[replaces]
	}
}


////open mutually-independent
pred independent[a, b: Stratum] {
	a not in b + b.transitive and b not in a + a.transitive
}
pred independentOnCommonBase[a, b: Stratum] {
	independent[a, b] and some a.transitive & b.transitive
}
////close mutually-independent

fun stratumPerspective[stratum: Stratum]: set Stratum
{
  stratum.*dependsOn
}

---------------------------------------------------
-- handle the basics of resemblance and replacement
---------------------------------------------------

fact ElementFacts
{
	-- nothing can resemble a replacement -- check to see that the things we resemble don't replace also
	no resembles.replaces
	
	all
		e: Element |
	let
		owner = e.home,
		-- strata that can see the component
		resemblingOwningStratum = e.resembles.home,
		replacingOwningStratum = e.replaces.home
	{
		-- no circularities in resemblance or replacement, and must be visible
		e not in (e.^resembles + e.replaces)
		resemblingOwningStratum in owner.canSeePlusMe
		replacingOwningStratum in owner.canSee
		
		-- tie up the owning stratum and the elements owned by that stratum
		e.home = ownedElements.e

		-- we only need to form a definition for stratum that can see us
		all s: Stratum |
		let
			-- who should I resemble
			-- (taking replacement into account)
			iResemble = e.resembles_e.s,
			-- if we resemble what we are replacing,
			-- look for the original under here
			topmostOfReplaced = getTopmost[
				owner.simpleDependsOn,
				e.replaces & e.resembles],
			-- look for any other resembled components
			-- from here down
			topmostOfResemblances = getTopmost[
				s,
				e.resembles - e.replaces]
		{
			-- rule: WF_ELEMENT_EXPANDED_RESEMBLANCE_ACYCLIC
			-- expanded resemblance graph must also be acyclic
			e not in e.^(resembles_e.s)
	
			owner not in s.transitivePlusMe =>
			{
				no iResemble
				no e.actsAs_e.s
			}	
			else
			{
				-- rewrite the resemblance graph to handle replacement
				iResemble = topmostOfReplaced + topmostOfResemblances
				-- who do we act as in this stratum?
				e.actsAs_e.s =
					{ real: Element | no real.replaces and e in getTopmost[s, real] }
			}	
		}
	}
}

fun getTopmost(s: set Stratum, e: Element): set Element
{
	let replaced = replaces.e & s.transitivePlusMe.replacing,
		topmostReplaced = replaced - replaced.resembles_e.s
			{ some topmostReplaced => topmostReplaced else e }
}