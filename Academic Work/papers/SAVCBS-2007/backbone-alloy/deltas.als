module deltas[Stratum, Element, ID, Object]

open util/boolean as boolean
open util/relation as relation

////open delta-instructions
////open savcbs-delta-constituents-V
sig Deltas
{
////pause delta-instructions
////pause savcbs-delta-constituents-V
	-- the expanded objects for this stratum.  these 2 fields are the output of the merge!
	objects:      				Object -> Stratum,

	-- old objects is what was what was there before any replacing was done
	oldObjects_e:				Stratum -> ID -> Object,
	originalOldObjects_e:		Stratum -> ID -> Object,

	-- working variables to track the expansion of objects, and allow it to happen cumulatively
	-- note: we need to keep track of what has been deleted and replaced to handle the cumulative effects
	-- e.g. delete in one stream, not in the other.
	-- NOTE: original is taking only resemblance_e into account, non-original is the full definition
	--       taking redefinition into account also
////open objects_e
////unpause savcbs-delta-constituents-V
	objects_e: Stratum -> ID -> Object,
////close savcbs-delta-constituents-V
////close objects_e		
	deletedObjects_e:          	Stratum -> ID,
	replacedObjects_e:         	Stratum -> ID -> Object,	
	originalObjects_e:         	Stratum -> ID -> Object,
	originalDeletedObjects_e:  	Stratum -> ID,
	originalReplacedObjects_e: 	Stratum -> ID -> Object,
	
	-- newObjects is any objects added or replaced.  these fields allow new object creation to be controlled
	newObjects:      			set Object,
	addedObjects:    			set newObjects,
	replacedObjects: 			set Object,
////unpause delta-instructions
	-- newIDs are any new IDs added
	newIDs:        				set ID,	
	-- the deltas that are to be applied.  these 3 fields are the input to the merge
	deleteObjects:  			set ID,
	addObjects:     			newIDs one -> one addedObjects,
	replaceObjects: 			ID one -> lone replacedObjects
////close delta-instructions
}
{
	-- cannot delete and replace
	no dom[replaceObjects] & deleteObjects
	replacedObjects = newObjects - addedObjects
}

-- indicate that any new part/ID can only be introduced by one component
fact Owned
{
	all o: Object |
		one newObjects.o
	
	-- do we also test for IDs?
	all n: ID |
		one newIDs.n
}


////open savcbs-deltas-ok-V
pred Deltas::oneObjectPerID(s: Stratum)
{
	let objects = this.objects_e[s] |
		function[objects, dom[objects]]
}
////close savcbs-deltas-ok-V

pred Deltas::nothing(s: Stratum)
{
	no this.objects.s
	no this.objects_e[s]
	no this.oldObjects_e[s]
	no this.deletedObjects_e[s]
	no this.replacedObjects_e[s]
	no this.originalObjects_e[s]
	no this.originalDeletedObjects_e[s]
	no this.originalReplacedObjects_e[s]
}

-- ensure that deletes and replaces makes sense from the perspective of the original stratum
pred Deltas::deltasIsWellFormed(owner: Stratum)
{
	-- no overlap between deleted and replaced IDs
	let
		deleteIDs = this.deleteObjects,
		replaceIDs = dom[this.replaceObjects]
	{
		-- no overlap between deleted and replaced
		no deleteIDs & replaceIDs
		-- anything we delete or replace must be there already
		deleteIDs + replaceIDs in dom[this.originalOldObjects_e[owner]]
	}
}

-- ensures that this delta removes everything
pred Deltas::cleanSlate(owner: Stratum)
{
	this.deleteObjects = dom[this.oldObjects_e[owner]]
}

-- ensures that we only have adds, no deletes or replaces
pred Deltas::onlyAdds(owner: Stratum)
{
	no this.deleteObjects
	no this.replaceObjects
}


-- the predicate to merge any underlying resembled entities and apply current changes
-- this is driven off the newly computed resemblance graph for each component in each stratum
pred Deltas::mergeAndApplyChangesForResemblance(
	s: Stratum,
	c: Element,
	-- who should I resemble, taking redefinition into account
	iResembleDeltas_e: set Deltas)
{
	-- handle add, delete etc as if we are only taking resemblance into account
	-- nothing will ever resemble itself
	this.originalOldObjects_e[s] =
		(iResembleDeltas_e.originalObjects_e[s]
			- iResembleDeltas_e.originalDeletedObjects_e[s]->Object)
				++ iResembleDeltas_e.originalReplacedObjects_e[s]
				
	this.originalDeletedObjects_e[s] =
		iResembleDeltas_e.originalDeletedObjects_e[s]
			- dom[iResembleDeltas_e.originalReplacedObjects_e[s]] + this.deleteObjects
		
	this.originalReplacedObjects_e[s] =
		(iResembleDeltas_e.originalReplacedObjects_e[s] - this.deleteObjects->Object)
			++ this.replaceObjects
			
	this.originalObjects_e[s] = 
		(((iResembleDeltas_e.originalObjects_e[s] - this.originalDeletedObjects_e[s]->Object)
				++ this.originalReplacedObjects_e[s]) + this.addObjects)
					++ this.replaceObjects	
}

pred Deltas::mergeAndApplyChangesForRedefinition(
	s: Stratum,
	c: Element,
	topmost: set Element,
	-- who should I resemble, taking redefinition into account
	iResembleDeltas_e: set Deltas)
{
	-- expand out into a easier form for expressing well-formedness rule, where IDs don't count
	this.objects = {p: Object, s: Stratum |
		some n: ID | s->n->p in this.objects_e}

	-- handle add, delete etc as if we are only taking resemblance into account
	topmost = c =>
		this.oldObjects_e[s] = iResembleDeltas_e.originalOldObjects_e[s]
	else
		this.oldObjects_e[s] =
			(iResembleDeltas_e.originalObjects_e[s]
				- iResembleDeltas_e.originalDeletedObjects_e[s]->Object)
					++ iResembleDeltas_e.originalReplacedObjects_e[s]

	this.deletedObjects_e[s] =
		iResembleDeltas_e.originalDeletedObjects_e[s]
			- dom[iResembleDeltas_e.originalReplacedObjects_e[s]]
		
	this.replacedObjects_e[s] = iResembleDeltas_e.originalReplacedObjects_e[s]
			
////open savcbs-resolve-V
	this.objects_e[s] = 
		(iResembleDeltas_e.originalObjects_e[s] - this.deletedObjects_e[s]->Object)
			++ this.replacedObjects_e[s]
////close savcbs-resolve-V
}

