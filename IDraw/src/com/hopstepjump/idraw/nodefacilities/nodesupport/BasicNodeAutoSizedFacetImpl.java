package com.hopstepjump.idraw.nodefacilities.nodesupport;

import java.awt.event.*;

import javax.swing.*;

import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;

/**
 *
 * (c) Andrew McVeigh 02-Aug-02
 *
 */

public final class BasicNodeAutoSizedFacetImpl implements BasicNodeAutoSizedFacet
{
	private BasicNodeState state;
	private boolean autoSized;
	
	public BasicNodeAutoSizedFacetImpl(BasicNodeState state, boolean autoSized)
	{
		this.state = state;
		this.autoSized = autoSized;
	}
	
	public BasicNodeAutoSizedFacetImpl(PersistentProperties properties, BasicNodeState state)
	{
		this.state = state;
		PersistentProperty autoSizedProperty = properties.retrieve("auto", true);
		this.autoSized = autoSizedProperty.asBoolean();
	}
	
	/**
	 * @see com.giroway.jumble.nodefacilities.resizebase.CmdAutoSizeable#autoSize(boolean)
	 */
	public Object autoSize(boolean newAutoSized)
	{
		boolean oldAutoSized = autoSized;

		// make the change
		autoSized = newAutoSized;

		Command resizeCommand = null;
		if (autoSized)
		{
			// we are about to autosize, so need to make a resizings command
			ResizingFiguresFacet resizings = new ResizingFiguresGem(null, state.diagram).getResizingFiguresFacet();
			resizings.markForResizing(state.figureFacet);
			resizings.setFocusBounds(state.appearanceFacet.getAutoSizedBounds(autoSized));
			resizeCommand = resizings.end("resized to adjust for autoSized toggle", "restored sizes to adjust for undoing autoSized toggle");
			resizeCommand.execute(false);
		}
		state.figureFacet.adjusted();

		return new Object[] {new Boolean(oldAutoSized), resizeCommand};
	}

	/**
	 * @see com.giroway.jumble.nodefacilities.resizebase.CmdAutoSizeable#unAutoSize(Object)
	 */
	public void unAutoSize(Object memento)
	{
		Object[] array = (Object[]) memento;
		autoSized = ((Boolean) array[0]).booleanValue();

		Command resizeCommand = (Command) array[1];
		if (resizeCommand != null)
			resizeCommand.unExecute();
		state.figureFacet.adjusted();
	}
	
	public JMenuItem getAutoSizedMenuItem(final ToolCoordinatorFacet coordinator)
	{
		// for autosizing
		JCheckBoxMenuItem toggleAutoSizeItem = new JCheckBoxMenuItem("Autosized");
		toggleAutoSizeItem.setState(autoSized);
		toggleAutoSizeItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// toggle the autosized flag (as a command)
				Command autoSizeCommand = new NodeAutoSizeCommand(state.figureFacet.getFigureReference(), !autoSized, (autoSized ? "unautosized " : "autosized ") + state.figureFacet.getFigureName(), (!autoSized ? "unautosized " : "autosized ") + state.figureFacet.getFigureName());
				coordinator.executeCommandAndUpdateViews(autoSizeCommand);
			}
		});
		return toggleAutoSizeItem;
	}
	
	/**
	 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAutoSizedFacet#isAutoSized()
	 */
	public boolean isAutoSized()
	{
		return autoSized;
	}
}
