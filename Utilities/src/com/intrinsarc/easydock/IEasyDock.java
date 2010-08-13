package com.intrinsarc.easydock;

import java.awt.*;

import javax.swing.*;

public interface IEasyDock
{
	/** finishSetup() must be called after the frame has been made visible */
	public void finishSetup();
	
	public IEasyDockable createEmbeddedPaletteDockable(
			String title,
			Icon icon,
			EasyDockSideEnum side,
			double proportion,
			boolean canClose,
			boolean remember,
			JComponent component);
	
	public IEasyDockable createExternalPaletteDockable(
			String title,
			Icon icon,
			Point location,
			Dimension size,
			boolean canClose,
			boolean remember,
			JComponent component);
	
	public IEasyDockable createWorkspaceDockable(
			String title,
			Icon icon,
			boolean addAsTab,
			boolean remember,
			JComponent component);

	public void closeRememberedDockables();
}