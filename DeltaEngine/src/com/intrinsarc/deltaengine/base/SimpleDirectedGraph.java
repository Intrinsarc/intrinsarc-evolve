package com.intrinsarc.deltaengine.base;

import java.util.*;

public class SimpleDirectedGraph<N>
{
	private Set<N> nodes = new HashSet<N>();
	private Map<N, Set<N>> edges = new HashMap<N, Set<N>>();

	public SimpleDirectedGraph()
	{
	}

	public void addEdge(N start, N end)
	{
		if (!nodes.contains(start) || !nodes.contains(end))
			throw new IllegalStateException("Adding an edge without the nodes");
		Set<N> e = edges.get(start);
		if (e == null)
		{
			e = new HashSet<N>();
			edges.put(start, e);
		}
		e.add(end);
	}

	public Set<N> getOutgoing(N node)
	{
		Set<N> out = edges.get(node);
		return out == null ? new HashSet<N>() : new HashSet<N>(out);
	}

	public boolean pathExists(N start, N end)
	{
		return pathExists(new HashSet<N>(), start, end);
	}

	private boolean pathExists(HashSet<N> visited, N start, N end)
	{
		if (start == end)
			return true;
		if (visited.contains(start))
			throw new IllegalStateException("Graph is not directed!");
		visited.add(start);
		for (N next : getOutgoing(start))
			if (pathExists(visited, next, end))
				return true;
		return false;
	}

	public List<N> makeTopologicalSort()
	{
		List<N> sorted = new ArrayList<N>();
		makeTopologicalSort(new HashSet<N>(nodes), sorted);
		return sorted;
	}

	private void makeTopologicalSort(HashSet<N> left, List<N> sorted)
	{
		// find anything in left doesn't depend on left (apart from itself)
		while (left.size() != 0)
		{
			List<N> move = new ArrayList<N>();
			for (N l : left)
				if (getOutgoing(l).isEmpty() || !getOutgoing(l).removeAll(left))
					move.add(l);
			left.removeAll(move);
			sorted.addAll(move);
			if (move.isEmpty())
			{
				System.out.println("$$ graph = " + left);
				for (N n : edges.keySet())
					System.out.println("  $$ Node = " + n + ", edges = " + edges.get(n));
				throw new IllegalStateException("Graph is not directed!");
			}
		}
	}

	public void addNode(N node)
	{
		nodes.add(node);
	}

	public boolean containsNode(N node)
	{
		return nodes.contains(node);
	}
}
