package test;

import net.java.dev.designgridlayout.*;

import com.intrinsarc.backbone.runtime.api.*;

public class DesignGridLayoutHandler implements ILifecycle
{
// start generated code

	private java.util.List<test.IRow> rows_IRowRequired = new java.util.ArrayList<test.IRow>();
	public void setRows_IRow(test.IRow rows_IRowRequired, int index) { PortHelper.fill(this.rows_IRowRequired, rows_IRowRequired, index); }

	private test.ILogger logger_ILoggerRequired;
	public void setLogger_ILogger(test.ILogger logger_ILoggerRequired) { this.logger_ILoggerRequired = logger_ILoggerRequired; }
	private test.IRegister register_IRegisterProvided = new IRegisterRegisterImpl();
	public test.IRegister getRegister_IRegister(Class<?> required) { return register_IRegisterProvided; }
// end generated code
	private DesignGridLayout designGrid;
	
	private class IRegisterRegisterImpl implements IRegister
	{
	}
	
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
			logger_ILoggerRequired.log("GUI initialized");
		else
			System.out.println("$$ logger is null for " + this);
	}

	public void beforeDelete()
	{
	}
}
