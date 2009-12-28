module base_deltas[ID, Object]

open base_structure

////open deltas
sig Deltas
{
	-- newObjects is any objects added or replaced.  these fields allow new object creation to be controlled
	newObjects:      			set Object,
	addedObjects:    			set newObjects,
	replacedObjects: 			set Object,
	
	-- newIDs are any new IDs added
////open deltainputs
	newIDs:        				set ID,
	-- the deltas that are to be applied.  these 3 fields are the input to the merge
////comment deltas -- the adds, deletes and replaces 
	addObjects:     			newIDs one -> one addedObjects,
	deleteObjects:  			set ID,
	replaceObjects: 			ID one -> lone replacedObjects,
////close deltainputs

////comment deltas -- the expanded constituents, for each stratum perspective
////open fieldobjects_e
	objects_e: Stratum -> ID -> Object,
////close fieldobjects_e
////pause deltas
	-- the expanded objects for this stratum.  these 2 fields are the output of the merge!
	objects:      				Object -> Stratum,
	-- old objects is what was what was there before any replacing was done
	oldObjects_e:				Stratum -> ID -> Object,
	originalOldObjects_e:		Stratum -> ID -> Object,

	-- working variables to track the expansion of objects, and allow it to happen cumulatively
	-- note: we need to keep track of what has been deleted and replaced to handle the cumulative effects
	-- e.g. delete in one stream, not in the other.
	-- NOTE: original is taking only resemblance_e into account, non-original is the full definition
	--       taking element replacement into account also
	deletedObjects_e:          	Stratum -> ID,
	replacedObjects_e:         	Stratum -> ID -> Object,	
////open originals
	originalObjects_e:         	Stratum -> ID -> Object,
	originalDeletedObjects_e:  	Stratum -> ID,
	originalReplacedObjects_e: 	Stratum -> ID -> Object
////close originals	
////unpause deltas
}
{
	-- cannot delete and replace
	no dom[replaceObjects] & deleteObjects
	replacedObjects = newObjects - addedObjects
}
////close deltas

-- indicate that any new part/ID can only be introduced by one component
fact Owned
{
////open uniqueids
	all o: Object |
		one newObjects.o
	all n: ID |
		one newIDs.n
////close uniqueids
}


////open oneconstituent
pred Deltas::oneObjectPerID(s: Stratum)
{
	let objects = this.objects_e[s] |
		function[objects, dom[objects]]
}
////close oneconstituent

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
////open wellformeddeltas
	-- no overlap between deleted and replaced IDs
	let
		deleteIDs = this.deleteObjects,
		replaceIDs = dom[this.replaceObjects]
	{
		-- no overlap between deleted and replaced
		no deleteIDs & replaceIDs
		-- anything we delete or replace must be there already
		-- rule: DELTA_DELETE -- deleted object must be present
		-- rule: DELTA_REPLACE -- replaced object must be present
		deleteIDs + replaceIDs in dom[this.originalOldObjects_e[owner]]
	}
////close wellformeddeltas
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
////open mergeresemblance
pred Deltas::mergeAndApplyChangesForResemblance(
	s: Stratum,
	c: Element,
	-- who should I resemble, taking element replacement into account
	iResembleDeltas_e: set Deltas)
////close mergeresemblance
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
			
////open originalobjects_e
	this.originalObjects_e[s] = 
		(((iResembleDeltas_e.originalObjects_e[s] - this.originalDeletedObjects_e[s]->Object)
				++ this.originalReplacedObjects_e[s]) + this.addObjects)
					++ this.replaceObjects	
////close originalobjects_e
}

pred Deltas::mergeAndApplyChangesForElementReplacement(
	s: Stratum,
	c: Element,
	topmost: set Element,
	-- who should I resemble, taking element replacement into account
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

////open objects_e
	this.objects_e[s] = 
		(iResembleDeltas_e.originalObjects_e[s] - this.deletedObjects_e[s]->Object)
			++ this.replacedObjects_e[s]
////close objects_e
}

