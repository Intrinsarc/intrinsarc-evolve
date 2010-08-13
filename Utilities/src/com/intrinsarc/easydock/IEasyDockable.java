package com.intrinsarc.easydock;

import javax.swing.*;

public interface IEasyDockable
{
	void setTitleIcon(Icon icon);
	void setTitleText(String title);
	void addListener(IEasyDockableListener listener);
	void close();
	void restore();
}
