package com.hopstepjump.repository;

import java.util.*;

import org.eclipse.emf.common.notify.*;
import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

import com.hopstepjump.repositorybase.*;

public class UndoRedoStackManager
{
  /** undo/redo variables */
	private ArrayList<NotificationList> stack = new ArrayList<NotificationList>();
	private int current;
	private boolean ignoreNotifications;
	static int count = 0;
	
	public UndoRedoStackManager()
	{
	}
	
	public void addNotification(Notification n)
	{
		if (ignoreNotifications || n.getEventType() == 0)
			return;
		
		if (current >= stack.size())
			stack.add(new NotificationList());
		stack.get(current).addNotification(n);
	}
	
	public void undo()
	{
		if (current == 0)
			throw new IllegalStateException("Cannot undo any more");

		ignoreNotifications = true;
		SubjectRepositoryFacet rep = GlobalSubjectRepository.repository;

		NotificationList list = stack.get(--current);
		List<Notification> ns = list.getNotifications();
		int size = ns.size();
		
		for (int lp = size - 1; lp >= 0; lp--)
		{
			Notification n = ns.get(lp);
			switch (n.getEventType())
			{
			case 0:  // ignore
				break;
			case -1: // my special create event
				rep.incrementPersistentDelete((Element) n.getNewValue());
				break;
			case Notification.ADD:
				{
					Element e = (Element) n.getNotifier();
					EList el = (EList) e.eGet((EStructuralFeature) n.getFeature());
					el.remove(n.getPosition());
				}
				break;
			case Notification.REMOVE:
				{
					Element e = (Element) n.getNotifier();
					EList el = (EList) e.eGet((EStructuralFeature) n.getFeature());
					el.add(n.getPosition(), n.getOldValue());
				}
				break;
			case Notification.SET:
				{
					Element e = (Element) n.getNotifier();
					e.eSet((EStructuralFeature) n.getFeature(), n.getOldValue());
				}
				break;
			default:
				throw new IllegalStateException("Cannot handle undo notification: " + n);
			}
		}
		
		ignoreNotifications = false;
	}
	
	public void redo()
	{
		if (current >= stack.size())
			throw new IllegalStateException("Cannot redo any more");

		ignoreNotifications = true;
		SubjectRepositoryFacet rep = GlobalSubjectRepository.repository;

		NotificationList list = stack.get(current++);
		for (Notification n : list.getNotifications())
		{
			switch (n.getEventType())
			{
			case 0:  // ignore
				break;
			case -1: // my special create event
				rep.decrementPersistentDelete((Element) n.getNewValue());
				break;
			case Notification.ADD:
				{
					Element e = (Element) n.getNotifier();
					EList el = (EList) e.eGet((EStructuralFeature) n.getFeature());
					el.add(n.getPosition(), n.getNewValue());
				}
				break;
			case Notification.REMOVE:
				{
					Element e = (Element) n.getNotifier();
					EList el = (EList) e.eGet((EStructuralFeature) n.getFeature());
					el.remove(n.getPosition());
				}
				break;
			case Notification.SET:
				{
					Element e = (Element) n.getNotifier();
					e.eSet((EStructuralFeature) n.getFeature(), n.getNewValue());
				}
				break;
			default:
				throw new IllegalStateException("Cannot handle redo notification: " + n);
			}
		}
		
		ignoreNotifications = false;
	}

	public void startTransaction()
  {
  	int count = stack.size() - current;
  	for (int lp = 0; lp < count; lp++)
  		stack.remove(current);
  }
	
	public void commitTransaction()
	{
		current++;
	}
	
	public int getCurrent()
	{
		return current;
	}
	
	public int getStackSize()
	{
		return stack.size();
	}

	public void clearStack()
	{
		stack.clear();
		current = 0;
	}

	/**
	 * ensure the stack doesn't become too large
	 * @param depth
	 */
	public void enforceDepth(int depth)
	{
		int truncate = stack.size() - depth;
		for (int lp = 0; lp < truncate; lp++)
			stack.remove(0);		
	}
}
