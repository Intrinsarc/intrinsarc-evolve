package com.hopstepjump.idraw.foundation;

import com.hopstepjump.gem.*;

public interface TransactionManagerFacet extends Facet
{
  public void startTransaction(String redo, String undo);
	public void undoTransaction();
	public void redoTransaction();
	public String getUndoTransactionDescription();
	public String getRedoTransactionDescription();
	public int getTransactionPosition();
	public int getTotalTransactions();
	public void clearTransactionHistory();
	public void enforceTransactionDepth(int globalCurrent, int desiredDepth);
  public void commitTransaction();
}
