package ltsa.lts;

/* MyHash is a speciallized Hashtable/Stack for the composition in the analyser
 * it includes a stack structure through the hash table entries
 *  -- assumes no attempt to input duplicate key
 *
 */

public class MyHashStack implements StackCheck {

	private static class Entry {
		byte[] key;
		int stateNumber;
		boolean marked;
		Entry next; // for linking buckets in hash table
		Entry link; // for queue linked list

		Entry(byte[] l) {
			key = l;
			stateNumber = -1;
			next = null;
			link = null;
			marked = false;
		}

		Entry(byte[] l, int n) {
			key = l;
			stateNumber = n;
			next = null;
			link = null;
			marked = false;
		}

	}

	private final Entry[] table;
	private int count = 0;
	private int depth = 0;
	private Entry head = null;

	public MyHashStack(int size) {
		table = new Entry[size];
	}

	public void pushPut(byte[] key) {
		Entry entry = new Entry(key);
		// insert in hash table
		int hash = StateCodec.hash(key) % table.length;
		entry.next = table[hash];
		table[hash] = entry;
		++count;
		// insert in stack
		entry.link = head;
		head = entry;
		++depth;
	}

	public void pop() { // remove from head of queue
		if (head == null)
			return;
		head.marked = false;
		head = head.link;
		--depth;
	}

	public byte[] peek() { // remove from head of queue
		return head.key;
	}

	public void mark(int id) {
		head.marked = true;
		head.stateNumber = id;
	}

	public boolean marked() {
		return head.marked;
	}

	public boolean empty() {
		return head == null;
	}

	public boolean containsKey(byte[] key) {
		int hash = StateCodec.hash(key) % table.length;
		Entry entry = table[hash];
		while (entry != null) {
			if (StateCodec.equals(entry.key, key))
				return true;
			entry = entry.next;
		}
		return false;
	}

	public boolean onStack(byte[] key) {
		int hash = StateCodec.hash(key) % table.length;
		Entry entry = table[hash];
		while (entry != null) {
			if (StateCodec.equals(entry.key, key))
				return entry.marked;
			entry = entry.next;
		}
		return false;
	}

	public int get(byte[] key) {
		int hash = StateCodec.hash(key) % table.length;
		Entry entry = table[hash];
		while (entry != null) {
			if (StateCodec.equals(entry.key, key))
				return entry.stateNumber;
			entry = entry.next;
		}
		return -99999;
	}

	public int size() {
		return count;
	}

	public int getDepth() {
		return depth;
	}

}