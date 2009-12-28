module test

/*open structure
open facts


run show for 8 but 5 int, 11 Element, 10 Port
pred show()
{
	-- ask for a structure where the top level has 2 children
	-- and each child descends on a final stratum
	some st0, st1, st2, st3, st4, st5: Stratum
	{
		-- stratum constraints
		isTrue[st5.isTop]
		st5.dependsOn = st3 + st4
		st4.dependsOn = st1
		st3.dependsOn = st2 + st1
		st2.dependsOn = st1
		st1.dependsOn = st0
		isTrue[st5.relaxed]
		isTrue[st4.relaxed]
		isTrue[st3.relaxed]
		isFalse[st2.relaxed]
		isTrue[st1.relaxed]
		
		#st5.ownedElements & Interface = 1
		#st5.ownedElements & Component = 1
--		st5.ownedElements.resembles = st1.ownedElements & Component
		no st5.ownedElements.redefines
--		(st5.ownedElements & Component).redefines = st1.ownedElements & Component
--		st5.ownedElements.specialises = st1.ownedElements & Interface
		let c = st5.ownedElements & Component
		{
			no c.myParts.addObjects
			no c.myParts.deleteObjects
		}
		
		#st4.ownedElements = 1
		#st4.ownedElements & Component = 1
		st4.ownedElements.redefines = st1.ownedElements & Component
		let c = st4.ownedElements & Component
		{
			one c.myParts.addObjects
			one c.myParts.replaceObjects
			no c.myParts.deleteObjects
		}

		#st3.ownedElements = 2
		#st3.ownedElements & Component = 2
		no st3.ownedElements.redefines
		some c1, c2: st3.ownedElements & Component |
			let c = st1.ownedElements & Component
		{
			disj[c1, c2]
			one c1.myParts.addObjects
			one c1.myParts.replaceObjects
			no c1.myParts.deleteObjects
			c1.resembles = c
			
			no c2.resembles
			c2.myParts.newObjects.partType = c
		}

		#st2.ownedElements = 2
		#st2.ownedElements & Interface = 1
		st2.ownedElements.redefines = st1.ownedElements & Component	
--		no st2.ownedElements.specialises
		let c = st2.ownedElements & Component
		{
			no c.myParts.addObjects
			one c.myParts.replaceObjects
			ran[c.myParts.replaceObjects].partType !=
				ran[(st1.ownedElements & Component).myParts.addObjects].partType
			no c.myParts.deleteObjects			
		}
		
		#st1.ownedElements = 2
		#st1.ownedElements & Interface = 1
		#st1.ownedElements & Component = 1
		let c = st1.ownedElements & Component
		{
			one c.myParts.addObjects
			no c.resembles
			st5 not in c.isValid_e			
		}

		#st0.ownedElements = 2
		#st0.ownedElements & Component = 2
		let c = st0.ownedElements & Component
		{
			no c.myParts.addObjects
			no c.resembles
			no resembles.c
		}
	}

	#Stratum = 6
	#Component = 8
	#Interface = 3
	#redefines = 2
--	#redefines = 3
	#resembles > 2
	some Port
}


pred show2()
{
	-- ask for a structure where the top level has 2 children
	-- and each child descends on a final stratum
	some st1, st2, st3, st4, st5: Stratum
	{
		-- stratum constraints
		st5.dependsOn = st3 + st4
		isTrue[st5.isTop]
		#st5.ownedElements = 1
		st5.ownedElements.resembles = st1.ownedElements
		
		st4.dependsOn = st1
		#st4.ownedElements = 1
		st4.ownedElements.redefines = st1.ownedElements

		st3.dependsOn = st1
		#st3.ownedElements = 2
		st3.dependsOn = st1
		st2.dependsOn = st1
		#st5.ownedElements = 1
		st5.ownedElements.resembles = st1.ownedElements
		no st5.ownedElements.redefines
		
		#st3.ownedElements = 1
		one st3.ownedElements.redefines
		st3.ownedElements.resembles = st1.ownedElements & Component

		#st2.ownedElements = 1
		one st2.ownedElements.redefines
		st2.ownedElements.resembles = st1.ownedElements & Component
		
		#st1.ownedElements = 1
		let c = st1.ownedElements & Component
		{
			one c.addObjects
		}
		no st3.ownedElements.redefines
		some c1, c2: st3.ownedElements |
			c1.resembles = c2 and c2.resembles = st1.ownedElements

		#st1.ownedElements = 1
	}
	
	#Stratum = 4
	#Component = 5
	#Interface = 0
	#redefines = 1
	#resembles > 1
}

pred show3()
{
	-- ask for a structure where the top level has 2 children
	-- and each child descends on a final stratum
	some st1, st2, st3, st4, st5: Stratum
	{
		-- stratum constraints
		isTrue[st5.isTop]
		st5.dependsOn = st3 + st4
		st4.dependsOn = st1
		st3.dependsOn = st2
		st2.dependsOn = st1
		#st5.ownedElements & Interface = 1
		#st5.ownedElements & Component = 1
		st5.ownedElements.resembles = st1.ownedElements & Component
		st5.ownedElements.specialises = st1.ownedElements & Interface
		
		#st4.ownedElements = 1
		#st4.ownedElements & Component = 1
		st4.ownedElements.redefines = st1.ownedElements & Component

		#st3.ownedElements = 1
		#st3.ownedElements & Component = 1
		no st3.ownedElements.redefines
		st3.ownedElements.resembles = st1.ownedElements & Component

		#st2.ownedElements = 1
		#st2.ownedElements & Interface = 1
		#st2.ownedElements.specialises = 0
		
		#st1.ownedElements = 2
		#st1.ownedElements & Interface = 1
		#st1.ownedElements & Component = 1
	}
	
	#Stratum = 5
	#Component = 4
	#Interface = 3
	#redefines = 1
	#resembles > 1
}


pred show4()
{
	-- ask for a structure where the top level has 2 children
	-- and each child descends on a final stratum
	some st1, st2, st3, st4, st5: Stratum
	{
		-- stratum constraints
		isTrue[st5.isTop]
		st5.dependsOn = st3 + st4
		st4.dependsOn = st1
		st3.dependsOn = st2
		st2.dependsOn = st1
		#st5.ownedElements & Interface = 1
		#st5.ownedElements & Component = 1
		st5.ownedElements.resembles = st1.ownedElements & Component
		st5.ownedElements.redefines = st1.ownedElements & Component
		st5.ownedElements.specialises = st1.ownedElements & Interface
		
		#st4.ownedElements = 1
		#st4.ownedElements & Component = 1
		st4.ownedElements.redefines = st1.ownedElements & Component

		#st3.ownedElements = 1
		#st3.ownedElements & Component = 1
		no st3.ownedElements.redefines
		st3.ownedElements.resembles = st1.ownedElements & Component

		#st2.ownedElements = 2
		#st2.ownedElements & Interface = 1
		st2.ownedElements.redefines = st1.ownedElements & Component	
		#st2.ownedElements.specialises = 0
		
		#st1.ownedElements = 2
		#st1.ownedElements & Interface = 1
		#st1.ownedElements & Component = 1
	}
	
	#Stratum = 5
	#Component = 5
	#Interface = 3
	#redefines = 3
	#resembles > 2
}

pred show5()
{
	-- ask for a structure where the top level has 2 children
	-- and each child descends on a final stratum
	some st1, st2, st3, st5: Stratum
	{
		-- stratum constraints
		isTrue[st5.isTop]
		st5.dependsOn = st3 + st2
		st3.dependsOn = st1
		st2.dependsOn = st1
		#st5.ownedElements = 1
		st5.ownedElements.resembles = st1.ownedElements
		no st5.ownedElements.redefines
		
		#st3.ownedElements = 1
		one st3.ownedElements.redefines
		st3.ownedElements.resembles = st1.ownedElements & Component

		#st2.ownedElements = 1
		one st2.ownedElements.redefines
		st2.ownedElements.resembles = st1.ownedElements & Component
		
		#st1.ownedElements = 1
		let c = st1.ownedElements & Component
		{
			one c.addObjects
		}
	}
	
	#Stratum = 5
	#Component = 3
	#Interface = 0
	#redefines = 2
	#resembles = 1
}

run show2 for 11
run show3 for 9
run show4 for 11


--assert NoResemblanceCycles_Z
--{
--	all z: Element_Z |
--		z not in z.^resembles_Z and
--		z not in z.^specialises_Z
--}
--check NoResemblanceCycles_Z for 5


run oshow for 12
pred oshow()
{
	-- ask for a structure where the top level has 2 children
	-- and each child descends on a final stratum
	some st1, st2, st3, st4, st5: Stratum
	{
		-- stratum constraints
		isTrue[st5.isTop]
		st5.dependsOn = st3 + st4
		st4.dependsOn = st1
		st3.dependsOn = st2
		st2.dependsOn = st1
		#st5.ownedElements & Interface = 1
		#st5.ownedElements & Component = 1
		st5.ownedElements.resembles = st1.ownedElements & Component
		no st5.ownedElements.redefines
		st5.ownedElements.specialises = st1.ownedElements & Interface
		
		#st4.ownedElements = 1
		#st4.ownedElements & Component = 1
		st4.ownedElements.redefines = st1.ownedElements & Component

		#st3.ownedElements = 1
		#st3.ownedElements & Component = 1
		no st3.ownedElements.redefines
		st3.ownedElements.resembles = st1.ownedElements & Component
		let c = st3.ownedElements & Component
		{
			one c.addObjects
			one c.replaceObjects
			no c.deleteObjects
		}

		#st2.ownedElements = 2
		#st2.ownedElements & Interface = 1
		st2.ownedElements.redefines = st1.ownedElements & Component	
		#st2.ownedElements.specialises = 0
		let c = st2.ownedElements & Component
		{
			no c.addObjects
			one c.replaceObjects
			no c.deleteObjects
		}
		
		#st1.ownedElements = 2
		#st1.ownedElements & Interface = 1
		#st1.ownedElements & Component = 1
		let c = st1.ownedElements & Component
		{
			one c.addObjects
		}
	}
	
	#Stratum = 5
	#Component = 5
	#Interface = 3
	#redefines = 2
	#resembles > 2
}
*/