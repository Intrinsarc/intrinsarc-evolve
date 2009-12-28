package lts;

public class MyHashQueueEntry {
	public byte[] key;
	int action;
	int level = 0;
	MyHashQueueEntry next; // for linking buckets in hash table
	MyHashQueueEntry link; // for queue linked list
	MyHashQueueEntry parent; // pointer to node above in BFS

	MyHashQueueEntry(byte[] l) {
		key = l;
		action = 0;
		next = null;
		link = null;
	}

	MyHashQueueEntry(byte[] l, int a, MyHashQueueEntry p) {
		key = l;
		action = a;
		next = null;
		link = null;
		parent = p;
	}

}

