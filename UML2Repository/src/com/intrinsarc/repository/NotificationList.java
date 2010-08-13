package com.intrinsarc.repository;

import java.util.*;

import org.eclipse.emf.common.notify.*;

public class NotificationList
{
	private List<Notification> notifications = new ArrayList<Notification>();
	private String redoDescription;
	private String undoDescription;
	
	public NotificationList(String redoDescription, String undoDescription)
	{
		this.redoDescription = redoDescription;
		this.undoDescription = undoDescription;
	}
	
	public void addNotification(Notification n)
	{
		notifications.add(n);
	}
	
	public List<Notification> getNotifications()
	{
		return notifications;
	}

	public String getRedoDescription()
	{
		return redoDescription;
	}

	public String getUndoDescription()
	{
		return undoDescription;
	}
}
