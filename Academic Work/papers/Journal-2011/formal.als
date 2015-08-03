open util/relation
fact Facts
{
// no strata cycles
all s: Stratum | s not in s.^dependson
// visibility
all e: Element |
let res = e.resembles, rep = e.replaces |
res.home in e.home.*dependson and
rep.home in e.home.^dependson and e not in res
all s: Stratum, e: Element
{
e.home not in s.*dependson =>
{
// not necessary, but makes debugging easier
no s.eresembles[e] and no s.edeltas[e]
and no s.full[e]
}
else
{
// topmost
let reps = replaces.e & s.elements |
some reps => s.topmost[e] = reps
else
e.home = s => s.topmost[e] = e
else
s.topmost[e] = s.dependson.topmost[e]
// expanded resembles
let joint = e.resembles & e.replaces,
rest = e.resembles - joint |
s.eresembles[e] =
e.home.dependson.topmost[joint]
+ s.topmost[rest]
// application of deltas
let lower = s.eresembles[e],
me = s.edeltas[e]
{
me.add = s.edeltas[lower].add + e.deltas.add
me.delete = s.edeltas[lower].delete
+ e.deltas.delete
me.replace = s.edeltas[lower].replace
++ e.deltas.replace
}
// final structure
let tops = s.topmost[e], me = s.edeltas[tops] |
some e.replaces => no s.full[e]
else
s.full[e] =
{ a, b: me.add | a = b }
++ me.replace
- { d: me.delete, allds: Constituent }
}
}
}
pred branch[a, b: Stratum]
{
let alla = a.*dependson, allb = b.*dependson |
a not in allb and b not in alla
and some alla & allb
}
pred conflict[perspective: Stratum, e: Element]
{
not functional[ perspective.full[e], Element ]
}
sig Stratum
{
name: lone String,
dependson: set Stratum,
elements: set Element,
topmost: Element -> Element,
eresembles: Element -> Element,
edeltas: Element -> lone Deltas,
full: Element -> Constituent -> Constituent
}
{ elements = home.this }
sig Element
{
name: lone String,
home: Stratum,
resembles: set Element,
replaces: lone Element,
deltas: lone Deltas
}
{
let others = Element.@deltas - deltas |
no deltas.add & others.add and
no deltas.replace[Constituent]
MCVEIGH ET AL.: SOFTWARE ARCHITECTURE EVOLUTION
& others.replace[Constituent]
}
sig Deltas
{
add, delete: set Constituent,
replace: Constituent -> Constituent
}
sig Constituent { name: lone String, parent: Deltas }