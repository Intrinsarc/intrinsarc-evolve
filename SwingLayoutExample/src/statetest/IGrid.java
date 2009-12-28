package statetest;

import net.java.dev.designgridlayout.*;

public interface IGrid
{
	public IGridRow addToLayout(IRowCreator row, IGridRow subRow);
}
