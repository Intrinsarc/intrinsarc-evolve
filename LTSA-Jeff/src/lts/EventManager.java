package lts;

import java.util.*;

import com.hopstepjump.backbone.runtime.api.*;

// event distribution class

public class EventManager
{
// start generated code
// attributes
// required ports
	private java.util.List<lts.EventClient> clients = new java.util.ArrayList<lts.EventClient>();
// provided ports
	private IEventManagerEventsImpl events_IEventManagerProvided = new IEventManagerEventsImpl();
// setters and getters
	public void setClients_EventClient(lts.EventClient clients, int index) { PortHelper.fill(this.clients, clients, index); }
	public void removeClients_EventClient(lts.EventClient clients) { PortHelper.remove(this.clients, clients); }
	public lts.IEventManager getEvents_IEventManager(Class<?> required) { return events_IEventManagerProvided; }
// end generated code
	
	Hashtable clientMap = new Hashtable();
	Vector queue = new Vector(); // queued messages
	Thread athread;
	boolean stopped = false;

	public EventManager() {
		athread = new Thread(events_IEventManagerProvided);
		athread.start();
	}

	private class IEventManagerEventsImpl implements IEventManager, Runnable
	{
		public synchronized void addClient(EventClient c) {
			clientMap.put(c, c);
		}
	
		public synchronized void removeClient(EventClient c) {
			clientMap.remove(c);
		}
	
		public synchronized void post(LTSEvent le) {
			queue.addElement(le);
			notifyAll();
		}
		
		private synchronized void dopost() {
			while (queue.size() == 0) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			
			// old dog
			LTSEvent le = (LTSEvent) queue.firstElement();
			Enumeration e = clientMap.keys();
			while (e.hasMoreElements()) {
				EventClient c = (EventClient) e.nextElement();
				c.ltsAction(le);
			}
				
			// new dog
			int actual = 0;
			for (EventClient client : clients)
				if (client != null)
				{
					client.ltsAction(le);
					actual++;
				}
			
			queue.removeElement(le);
		}
		
		public void run() {
			while (!stopped) {
				dopost();
			}
		}
	}


	public void stop() {
		stopped = true;
	}
}
