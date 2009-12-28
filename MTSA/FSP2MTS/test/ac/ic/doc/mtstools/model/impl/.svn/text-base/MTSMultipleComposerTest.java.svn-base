package ac.ic.doc.mtstools.model.impl;

import ac.ic.doc.mtstools.test.util.MTSTestBase;
import ac.ic.doc.mtstools.test.util.TestLTSOuput;

public class MTSMultipleComposerTest extends MTSTestBase {

	// public void testFilosofosOptimista() throws Exception {
	// String sourceString = "PHIL =
	// (sitdown?->right.get->left.get->eat->left.put->right.put->arise->PHIL).\n
	// FORK = (get -> put -> FORK).\n ||DINERS(N=2)=forall
	// [i:0..N-1](phil[i]:PHIL||{phil[i].left,phil[((i-1)+N)%N].right}::FORK).menu
	// RUN = {phil[0..4].{sitdown,eat}}";
	//
	// assertComposition(sourceString);
	// }
	//
	// public void testFilosofosPesimista() throws Exception {
	// String sourceString = "PHIL =
	// (sitdown?->right.get->left.get->eat->left.put->right.put->arise->PHIL).\n
	// FORK = (get -> put -> FORK).\n ||DINERS(N=2)=forall
	// [i:0..N-1](phil[i]:PHIL||{phil[i].left,phil[((i-1)+N)%N].right}::FORK).menu
	// RUN = {phil[0..4].{sitdown,eat}}";
	//
	// assertComposition(sourceString);
	//
	// }
	//
	// public void testTESTGARDEN() throws Exception {
	// String sourceString = "const False = 0 const True = 1\r\n" + "range Bool
	// = False..True\r\n"
	// + "const Nread = 2 // Maximum readers\r\n" + "const Nwrite= 2 // Maximum
	// writers\r\n" + "\r\n"
	// + "set Actions = {acquireRead,releaseRead,acquireWrite,releaseWrite}\r\n"
	// + "\r\n"
	// + "READER = (acquireRead->examine->releaseRead->READER)\r\n" + "
	// +Actions\r\n" + " \\{examine}.\r\n"
	// + "WRITER = (acquireWrite->modify->releaseWrite->WRITER)\r\n" + "
	// +Actions\r\n" + " \\{modify}.\r\n"
	// + "\r\n" + "RW_LOCK = RW[0][False],\r\n" +
	// "RW[readers:0..Nread][writing:Bool] =\r\n"
	// + " (when (!writing) \r\n" + " acquireRead -> RW[readers+1][writing]\r\n"
	// + " |releaseRead -> RW[readers-1][writing]\r\n" + " |when (readers==0 &&
	// !writing) \r\n"
	// + " acquireWrite -> RW[readers][True]\r\n" + " |releaseWrite ->
	// RW[readers][False]\r\n" + " ).\r\n"
	// + "\r\n" + "property SAFE_RW \r\n" + " = (acquireRead->READING[1] |
	// acquireWrite->WRITING),\r\n"
	// + "READING[i:1..Nread] \r\n" + " = (acquireRead -> READING[i+1]\r\n"
	// + " |when(i>1) releaseRead -> READING[i-1]\r\n" + " |when(i==1)
	// releaseRead -> SAFE_RW\r\n" + " ),\r\n"
	// + "WRITING \r\n" + " = (releaseWrite -> SAFE_RW).\r\n" + "\r\n"
	// + "||READWRITELOCK = (RW_LOCK || SAFE_RW).\r\n" + "\r\n" + "\r\n"
	// + "||READERS_WRITERS = ( reader[1..Nread] :READER \r\n" + " ||
	// writer[1..Nwrite]:WRITER \r\n"
	// + " || {reader[1..Nread],writer[1..Nwrite]}::READWRITELOCK).\r\n" +
	// "\r\n"
	// + "||RW_PROGRESS = READERS_WRITERS \r\n" + "
	// >>{reader[1..Nread].releaseRead,\r\n"
	// + " writer[1..Nread].releaseWrite}. \r\n" + "\r\n"
	// + "progress WRITE[i:1..Nwrite] = writer[i].acquireWrite\r\n"
	// + "progress READ[i:1..Nwrite] = reader[i].acquireRead\r\n";
	//
	// assertComposition(sourceString);
	//
	// }
	//
	// public void testAPRIMES() throws Exception {
	// String sourceString = "const N = 4 //compute first N primes\r\n" + "const
	// MAX = 9\r\n"
	// + "range NUM = 2..MAX\r\n" + "set S = {[NUM],eos}\r\n" + "\r\n" + "GEN =
	// GEN[2],\r\n"
	// + "GEN[x:NUM] = (out.put[x] -> \r\n" + " if x<MAX then \r\n"
	// + " GEN[x+1]\r\n" + " else\r\n"
	// + " (out.put.eos->end->GEN)\r\n" + " ).\r\n" + "\r\n"
	// + "FILTER = (in.get[p:NUM]->prime[p]->FILTER[p] \r\n" + "
	// |in.get.eos->ENDFILTER\r\n"
	// + " ),\r\n" + "FILTER[p:NUM] = (in.get[x:NUM] ->\r\n"
	// + " if x%p!=0 then \r\n" + " (out.put[x]->FILTER[p]) \r\n"
	// + " else \r\n" + " FILTER[p]\r\n"
	// + " |in.get.eos->ENDFILTER\r\n" + " ),\r\n"
	// + "ENDFILTER = (out.put.eos->end->FILTER).\r\n" + "\r\n" + "PIPE =
	// (put[x:S]->get[x]->PIPE).\r\n"
	// + "\r\n" + "||AGEN = GEN/{out.put/out.put[NUM]}.\r\n" + "\r\n"
	// + "||AFILTER = FILTER/{out.put/out.put[NUM],\r\n" + "
	// in.get/in.get.[NUM],\r\n"
	// + " prime/prime[NUM]\r\n" + " }.\r\n"
	// + "||APIPE = PIPE/{put/put[NUM],get/get[NUM]}.\r\n" + "\r\n" +
	// "||MPIPE(B=2) = if B==1 then\r\n"
	// + " APIPE\r\n" + " else\r\n" + " (APIPE/{mid/get} ||
	// MPIPE(B-1)/{mid/put})\r\n"
	// + " @{put,get}.\r\n" + "\r\n" + "||APRIMES(N=4,B=3) = \r\n" + " (gen:AGEN
	// || PRIMEP(N)\r\n"
	// + " || pipe[0..N-1]:MPIPE(B) \r\n" + " || filter[0..N-1]:AFILTER)\r\n"
	// + " /{ pipe[0]/gen.out,\r\n" + " pipe[i:0..N-1]/filter[i].in,\r\n"
	// + " pipe[i:1..N-1]/filter[i-1].out,\r\n" + "
	// end/{filter[0..N-1].end,gen.end}\r\n"
	// + " }.\r\n" + "\r\n" + "progress END = {end}\r\n" + "\r\n" + "property
	// PRIMEP(N=4) = PRIMEP[0],\r\n"
	// + "PRIMEP[i:0..N] = (when (i<N) filter[i].prime->PRIMEP[i+1]\r\n"
	// + " |end -> PRIMEP\r\n" + " ).\r\n";
	// assertComposition(sourceString);
	// }

//	public void testCAR() throws Exception {
//		String sourceString = "const N = 3 // number of each type of car\r\n"
//				+ "range T = 0..N // type of car count\r\n" + "range ID= 1..N // car identities\r\n" + "\r\n"
//				+ "BRIDGE = BRIDGE[0][0],  //initially empty\r\n"
//				+ "BRIDGE[nr:T][nb:T] =    //nr is the red count, nb the blue count\r\n" + "	(when (nb==0) \r\n"
//				+ "          red[ID].enter  -> BRIDGE[nr+1][nb]\r\n"
//				+ "        |red[ID].exit    -> BRIDGE[nr-1][nb]\r\n" + "        |when (nr==0) \r\n"
//				+ "          blue[ID].enter -> BRIDGE[nr][nb+1]\r\n"
//				+ "        |blue[ID].exit   -> BRIDGE[nr][nb-1]\r\n" + "	).\r\n" + "\r\n"
//				+ "CAR = (enter->exit->CAR).\r\n" + "\r\n" + "/* cars may not overtake each other */\r\n"
//				+ "NOPASS1   = C[1],\r\n" + "C[i:ID]   = ([i].enter -> C[i%N+1]).\r\n" + "\r\n"
//				+ "NOPASS2   = C[1],\r\n" + "C[i:ID]   = ([i].exit -> C[i%N+1]).\r\n" + "\r\n"
//				+ "||CONVOY = ([ID]:CAR || NOPASS1 || NOPASS2).\r\n" + "\r\n"
//				+ "||CARS = (red:CONVOY || blue:CONVOY).\r\n" + "\r\n"
//				+ "||SingleLaneBridge = (CARS || BRIDGE || ONEWAY ).\r\n" + "\r\n"
//				+ "property ONEWAY = (red[ID].enter  -> RED[1] \r\n" + "		  |blue[ID].enter -> BLUE[1]\r\n"
//				+ "		  ),\r\n" + "RED[i:ID] = (red[ID].enter -> RED[i+1]\r\n"
//				+ "            |when(i==1)red[ID].exit  -> ONEWAY\r\n"
//				+ "            |when( i>1)red[ID].exit  -> RED[i-1]\r\n" + "            ),\r\n"
//				+ "BLUE[i:ID] = (blue[ID].enter -> BLUE[i+1]\r\n"
//				+ "             |when(i==1)blue[ID].exit  -> ONEWAY\r\n"
//				+ "             |when( i>1)blue[ID].exit  -> BLUE[i-1]\r\n" + "             ).\r\n";
//		String sourceString = "const N = 3\r\n" + 
//				"range ID= 1..N // car identities\r\n" + 
//				"\r\n" + 
//				"property ONEWAY = (red[ID].enter  -> RED[1] \r\n" + 
//				"		  |blue[ID].enter -> BLUE[1]\r\n" + 
//				"		  ).\r\n";
		//con este string alcanza para ver que pasa
//		assertComposition(sourceString);
//	}
//
//	public void testA3() throws Exception {
//		String sourceString = "A_3 = ( x? -> A_3_1 |x-> A_3_2),\r\n" + 
//				"   A_3_1 = ( a? -> A_3_1 | b? -> A_3_1),\r\n" + 
//				"   A_3_2 = ( c? -> A_3_2 | d? -> A_3_2).\r\n" + 
//				"\r\n" + 
//				"B_3 = ( x? -> B_3_1 | x -> B_3_2),\r\n" + 
//				"   B_3_1 = ( a? -> B_3_1 | d? -> B_3_1),\r\n" + 
//				"   B_3_2 = ( c? -> B_3_2 | b? -> B_3_2).\r\n" + 
//				"\r\n" + 
//				"||CR_3 = ( A_3 || B_3).\r\n";
//		testUtils.assertComposition(sourceString, new TestLTSOuput());
//	}

//	public void testA2() throws Exception {
//		String sourceString = "A = (a?->A).\r\n" + 
//				"B = (a->B).\r\n" + 
//				"||AB = (A||B).\r\n";
//		testUtils.assertComposition(sourceString, new TestLTSOuput());
//	}

	// TODO - Dipi - revisar el siguiente test
	public void testComponeConError() throws Exception {
//		String sourceString = "A = (a->ERROR).\r\n" + 
//				"		B = (b->a?->holapepe->B).\r\n" + 
//				"		||AB = (A||B).\r\n";
//		testUtils.assertComposition(sourceString, new TestLTSOuput());
//		
	}
}
