package statetest;

import net.java.dev.designgridlayout.*;

import com.intrinsarc.backbone.runtime.api.*;

public class DesignGridLayoutHandler implements ILifecycle
{
// start generated code

	private java.util.List<statetest.IRow> rows_IRowRequired = new java.util.ArrayList<statetest.IRow>();
	public void setRows_IRow(statetest.IRow rows_IRowRequired, int index) { PortHelper.fill(this.rows_IRowRequired, rows_IRowRequired, index); }

	private statetest.ILogger logger_ILoggerRequired;
	public void setLogger_ILogger(statetest.ILogger logger_ILoggerRequired) { this.logger_ILoggerRequired = logger_ILoggerRequired; }
// end generated code
	private DesignGridLayout designGrid;
	
	public void setLayout_Container(java.awt.Container set)
	{
		designGrid = new DesignGridLayout(set);
		set.setLayout(designGrid);
	}

	public void afterInit()
	{
		// ask each row to add itself in turn
		for (IRow row : rows_IRowRequired)
			row.addToLayout(designGrid);

		if (logger_ILoggerRequired != null)
			logger_ILoggerRequired.log("design grid layout handler initialized: " + this);
		else
			System.out.println("$$ logger is null for " + this);
	}

	public void beforeDelete()
	{
	}
}
