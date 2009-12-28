
run {} for 10 but exactly 2 Constituent

pred function[r: univ -> univ]
{
	all x: r.univ | one x.r
}

pred bijection[r: univ -> univ]
{
	all x: univ.r | one r.x
}

one sig Errors
{
	pathsTooBig: lone univ
}

sig ScopedIdentifier, Type {}

sig Path
{
	path: seq Constituent	// is in reverse order
}
fun emptyPath[]: Path
{
	{p: Path |
		no p.path}
}
fun Path::addParent[c: Constituent]: Path
{
	{p: Path |
		p.path = this.path.add[c]}
}
// poor man's recursion
fun Path::make[c: Constituent, current: Path]: Path
{ no c.parent => current else current.make2[c.parent, current.addParent[c]] }
fun Path::make2[c: Constituent, current: Path]: Path
{ no c.parent => current else current.make3[c.parent, current.addParent[c]] }
fun Path::make3[c: Constituent, current: Path]: Path
{ no c.parent => current else current.make4[c.parent, current.addParent[c]] }
fun Path::make4[c: Constituent, current: Path]: Path
{ no c.parent => current else none }






sig Constituent
{
	type: Type,
	id: ScopedIdentifier,
	// at most a single parent
	parent: lone Container
}
{
	parent = (added + replaced.replaceWith).this
	lone replaceWith.this
}


sig Replacement
{
	toReplace: ScopedIdentifier,
	replaceWith: Constituent,
	remaps: ScopedIdentifier -> ScopedIdentifier
}
{
	one replaced.this
	no remaps // xx
}

sig Container extends Constituent
{
	added: set Constituent,
	replaced: set Replacement,
	deleted: set ScopedIdentifier,
	name: lone univ,
	resembles: set ScopedIdentifier,
	
	// working data
	unmerged: Constituent -> ScopedIdentifier -> Constituent,
	merged: Path -> ScopedIdentifier -> Constituent,
	mergeError: lone univ,
	reallyResembles: set Container
}

fact
{
	// can't add something and somewhere else use it for replacement
	no Container.added & Container.replaced.replaceWith
	// ensure each id only introduces a single constituent
	bijection[id] // xx -- want to allow some ids to be duplicated
	some path
	no Path.path[4]
}


