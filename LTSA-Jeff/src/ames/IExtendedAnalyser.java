package ames;

import java.util.*;

import lts.*;

public interface IExtendedAnalyser extends IAnalyser
{
	StateCodec getStateCodec();
	void printTracePath();
	CompactState getErrorStateMachine();
	List eligibleTransitions(int[] state);
	void makePossiblePartialOrderReduction(MyHashQueue hh);
	void outStatistics(int nTrans);
	LinkedList setTrace(MyHashQueue hh, MyHashQueueEntry qe);
	boolean isEND(int[] state);
	void setErrorMachine(int i);
	int getNmach();
	String getActionName(int i);
}
