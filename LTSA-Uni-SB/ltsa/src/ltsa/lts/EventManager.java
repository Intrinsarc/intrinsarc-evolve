package ltsa.lts;

import java.util.*;

public class EventManager implements Runnable {
	Hashtable<EventClient, EventClient> clients = new Hashtable<EventClient, EventClient>();
	Vector<LTSEvent> queue = new Vector<LTSEvent>(); // queued messages
	Thread athread;
	boolean stopped = false;

	public EventManager() {
		athread = new Thread(this);
		athread.start();
	}

	public synchronized void addClient(EventClient c) {
		clients.put(c, c);
	}

	public synchronized void removeClient(EventClient c) {
		clients.remove(c);
	}

	public synchronized void post(LTSEvent le) {
		queue.addElement(le);
		notifyAll();
	}

	public void stop() {
		stopped = true;
	}

	private synchronized void dopost() {
		while (queue.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// ignore
			}
		}
		LTSEvent le = queue.firstElement();
		Enumeration<EventClient> e = clients.keys();
		while (e.hasMoreElements()) {
			EventClient c = e.nextElement();
			c.ltsAction(le);
		}
		queue.removeElement(le);
	}

	public void run() {
		while (!stopped) {
			dopost();
		}
	}
}