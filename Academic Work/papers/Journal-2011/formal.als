open util/relation as relation

sig Stratum
{
	dependson: set Stratum,
	elements: set Element
}
{
	this not in this.^@dependson // no cycles
	elements.home = this // composition
}

sig Element
{
	home: Stratum,
	resembles: set Element,
	replaces: lone Element,
	deltas: lone Deltas // composition
}
{
	deltas.owner = this
	resembles.home in home.*dependson
	replaces.home in home.^dependson
}

sig Deltas
{
	owner: Element,
	add, delete: set Constituent,
	replace: Constituent -> Constituent
}
{
	add.parent = this
	Constituent.replace.parent = this
}

sig Constituent
{
	parent: Deltas
}

pred show()
{
	some Stratum
	some dependson
}
run show for 8
