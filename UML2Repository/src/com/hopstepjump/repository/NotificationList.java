package com.hopstepjump.repository;

import java.util.*;

import org.eclipse.emf.common.notify.*;

public class NotificationList
{
	private List<Notification> notifications = new ArrayList<Notification>();
	
	public NotificationList()
	{
	}
	
	public void addNotification(Notification n)
	{
		notifications.add(n);
	}
	
	public List<Notification> getNotifications()
	{
		return notifications;
	}
}
