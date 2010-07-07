package com.hopstepjump.jumble.gui;

import java.awt.event.*;
import java.net.*;

import javax.swing.*;

import com.hopstepjump.idraw.environment.*;

public class URLAction extends AbstractAction
{
	private String url;
	
	public URLAction(String name, String url)
	{
		super(name);
		this.url = url;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			BrowserInvoker.openBrowser(new URL(url));
		}
		catch (MalformedURLException e1)
		{
			System.err.println("Malformed URL: " + url);
		}
	}
}
