package com.myapplication.client.widgets;

import java.util.*;

import com.google.gwt.user.client.ui.*;
import com.hopstepjump.backbone.runtime.api.*;


public class SimpleTabPanel extends TabPanel implements ILifecycle
{
	private List<Widget> widgets = new ArrayList<Widget>();
	private boolean inited;

	public SimpleTabPanel()
	{
	}

	public void addTitleTab(Widget w)
	{
		widgets.add(w);
		if (inited)
		{
			add(w, w.getTitle());
			selectTab(widgets.size() - 1);
		}
	}
	
	public void afterInit()
	{
		for (Widget w : widgets)
			add(w, w.getTitle());
		selectTab(0);
		inited = true;
	}

	public void beforeDelete()
	{
	}
}