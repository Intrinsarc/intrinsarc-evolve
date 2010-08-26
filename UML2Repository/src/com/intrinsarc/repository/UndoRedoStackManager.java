package com.intrinsarc.repository;

import java.util.*;

import org.eclipse.emf.common.notify.*;
import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

import com.intrinsarc.repositorybase.*;

public class UndoRedoStackManager
{
  /** undo/redo variables */
	private ArrayList<NotificationList> stack = new ArrayList<NotificationList>();
	private int current;
	private boolean ignoreNotifications;
	private boolean inTransaction;
	private String redoDescription;
	private String undoDescription;
	
	public UndoRedoStackManager()
	{
	}
	
	public void addNotification(Notification n)
	{
		if (ignoreNotifications || n.getEventType() == 0)
			return;

		if (!inTransaction)
			throw new IllegalStateException("Have a repository event outside of undo/redo or a transaction: " + this);
		
		ensureCurrent();
		stack.get(current).addNotification(n);
	}

	private void ensureCurrent()
	{
		if (current >= stack.size())
			stack.add(new NotificationList(redoDescription, undoDescription));		
	}
	
	public void undo()
	{
		if (current == 0)
			return;

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
					el.remove(n.getNewValue());
				}
				break;
			case Notification.REMOVE:
				{
					Element e = (Element) n.getNotifier();
					EList el = (EList) e.eGet((EStructuralFeature) n.getFeature());
					el.add(n.getOldValue());
				}
				break;
			case Notification.SET:
				{
					Element e = (Element) n.getNotifier();
					e.eSet((EStructuralFeature) n.getFeature(), n.getOldValue());
				}
				break;
			case Notification.REMOVE_MANY:
				{
					Element e = (Element) n.getNotifier();				
					EList el = (EList) e.eGet((EStructuralFeature) n.getFeature());
					el.addAll((List) n.getOldValue());
				}
				break;
			case Notification.ADD_MANY:
			{
				Element e = (Element) n.getNotifier();				
				EList el = (EList) e.eGet((EStructuralFeature) n.getFeature());
				if (n.getOldValue() == null)
					el.clear();
				else
					el.removeAll((List) n.getOldValue());
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
			return;

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
					el.add(n.getNewValue());
				}
				break;
			case Notification.REMOVE:
				{
					Element e = (Element) n.getNotifier();
					EList el = (EList) e.eGet((EStructuralFeature) n.getFeature());
					el.remove(n.getOldValue());
				}
				break;
			case Notification.SET:
				{
					Element e = (Element) n.getNotifier();
					e.eSet((EStructuralFeature) n.getFeature(), n.getNewValue());
				}
				break;
			case Notification.REMOVE_MANY:
				{
					Element e = (Element) n.getNotifier();				
					EList el = (EList) e.eGet((EStructuralFeature) n.getFeature());
					if (n.getNewValue() == null)
						el.clear();
					else
						el.removeAll((List) n.getNewValue());
				}
				break;
			case Notification.ADD_MANY:
			{
				Element e = (Element) n.getNotifier();				
				EList el = (EList) e.eGet((EStructuralFeature) n.getFeature());
				el.addAll((List) n.getNewValue());
			}
			break;

			default:
				throw new IllegalStateException("Cannot handle redo notification: " + n);
			}
		}
		
		ignoreNotifications = false;
	}

	public void startTransaction(String redoName, String undoName)
  {
  	this.redoDescription = redoName;
  	this.undoDescription = undoName;
  	int count = stack.size() - current;
  	for (int lp = 0; lp < count; lp++)
  		stack.remove(current);
  	inTransaction = true;
  }
	
	public void commitTransaction()
	{
		ensureCurrent();
		current++;
  	inTransaction = false;
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
	public void enforceDepth(int desiredDepth)
	{
		int truncate = stack.size() - desiredDepth;
		if (truncate > 0)
		{
			for (int lp = 0; lp < truncate; lp++)
				stack.remove(0);
			current -= truncate;
		}
	}

	public String getRedoDescription()
	{
		return stack.get(current).getRedoDescription();
	}

	public String getUndoDescription()
	{
		return stack.get(current-1).getUndoDescription();
	}

	public void ignoreNotifications()
	{
		ignoreNotifications = true;
	}
	
	public void noticeNotifications()
	{
		ignoreNotifications = false;
	}
}
