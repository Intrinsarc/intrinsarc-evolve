package ltsa.lts;

import java.lang.reflect.*;
import java.util.*;

public class Relation<K, V> extends Hashtable<K, Vector<V>> {

	private static final long serialVersionUID = 5567155157181568126L;

	boolean isRelation = false; // true if 1-to-many

	// handle relations
	public synchronized Vector<V> put(K key, V value) {
		Vector<V> old = get(key);
		if (old == null) {
			old = new Vector<V>(1);
			old.add(value);
			put(key, old);
		} else if (!old.contains(value)) {
			// its a one to many
			isRelation = true;
			old.add(value);
		}
		return old;
	}

	@Override
	public synchronized Vector<V> put(K key, Vector<V> value) {
		if (value.size() == 0)
			return remove(key);
		if (value.size() > 1)
			isRelation = true;
		return super.put(key, value);
	}

	public boolean isRelation() {
		return isRelation;
	}

	public synchronized Relation<V, K> inverse() {
		Relation<V, K> inv = new Relation<V, K>();
		Enumeration<K> k = keys();
		while (k.hasMoreElements()) {
			K key = k.nextElement();
			Vector<V> val = get(key);
			Enumeration<V> v = val.elements();
			while (v.hasMoreElements())
				inv.put(v.nextElement(), key);
		}
		return inv;
	}

	public synchronized void union(Relation<? extends K, ? extends V> r) {
		if (r == null)
			return;
		Enumeration<? extends K> k = r.keys();
		while (k.hasMoreElements()) {
			K key = k.nextElement();
			Vector<? extends V> val = r.get(key);
			putValues(key, val);
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void relabel(Relation<K, ? extends K> r) { // r maps
		// oldkey to
		// new
		// key(s)
		Enumeration<K> k = keys();
		while (k.hasMoreElements()) {
			K oldkey = k.nextElement(); // old key
			Vector<V> values = get(oldkey);
			if (r.containsKey(oldkey)) {
				Vector<? extends K> newkeys = r.get(oldkey); // new keys
				remove(oldkey);
				Enumeration<? extends K> v = newkeys.elements();
				while (v.hasMoreElements()) {
					putValues(v.nextElement(), values);
				}
				// this is an ugly hack to specialize relabeling on Strings
			} else {
				TypeVariable<?>[] typeParameters = getClass()
						.getTypeParameters();
				if (typeParameters.length == 2) {
					TypeVariable<? extends GenericDeclaration> o = typeParameters[0];
					GenericDeclaration d = o.getGenericDeclaration();
					if (d instanceof Class<?>
							&& ((Class<?>) d).isInstance(String.class)) {
						String prefix = prefix((String) oldkey, r);
						if (prefix != null) {
							Vector<? extends K> newkeys = r.get(prefix);
							Enumeration<? extends K> v = newkeys.elements();
							while (v.hasMoreElements()) {
								K nextElement = v.nextElement();
								if (nextElement.getClass().equals(String.class)) {
									String nk = prefixReplace((String) oldkey,
											(String) nextElement, r);
									putValues((K) nk, values);
								}
							}
						}
					}
				}
			}
		}
	}

	public synchronized void putValues(K key, Vector<? extends V> values) {
		Enumeration<? extends V> v = values.elements();
		while (v.hasMoreElements()) {
			put(key, v.nextElement());
		}
	}

	public synchronized boolean remove(K key, V value) {
		Vector<V> values = get(key);
		if (values != null && values.remove(value)) {
			if (values.isEmpty())
				remove(key);
			return true;
		}
		return false;
	}

	private String prefixReplace(String s, String np,
			Relation<K, ? extends K> oldtonew) {
		int prefix_end = maximalPrefix(s, oldtonew);
		if (prefix_end < 0)
			return s;
		return np + s.substring(prefix_end);
	}

	private int maximalPrefix(String s, Relation<K, ? extends K> oldtonew) {
		int prefix_end = s.lastIndexOf('.');
		if (prefix_end < 0)
			return prefix_end;
		if (oldtonew.containsKey(s.substring(0, prefix_end)))
			return prefix_end;
		else
			return maximalPrefix(s.substring(0, prefix_end), oldtonew);
	}

	private String prefix(String s, Relation<K, ? extends K> oldtonew) {
		int maximalPrefix = maximalPrefix(s, oldtonew);
		return maximalPrefix < 0 ? null : s.substring(0, maximalPrefix);
	}

}