package ltlcheck;

import gov.nasa.ltl.graph.Edge;
import gov.nasa.ltl.graph.Graph;
import gov.nasa.ltl.graph.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DidCanTranslator {
	private final static boolean debug = false;

	public static Graph translate(Graph lts) {
		Graph g = new Graph();
		HashMap<Pair<String, Node>, Node> states_o2n = new HashMap<Pair<String, Node>, Node>();

		// for each node n in lts
		for (Node current :  lts.getNodes()) {

			// and each incoming edge labelled l
			for (Edge edge : current.getIncomingEdges()) {

				// add a node <l, n> in did/can-extended graph g
				Node ln = new Node(g);
				List<AtomicProposition> label = new ArrayList<AtomicProposition>();
				// with did set to l:
				label.add(new AtomicProposition(edge.getGuard(), AtomicProposition.PropositionType.Did));

				// and can set extracted from outgoing edges
				for (Iterator<Edge> lts_outgoing_edge_iterator = current.getOutgoingEdges().iterator(); lts_outgoing_edge_iterator.hasNext();) {
					label.add(new AtomicProposition(lts_outgoing_edge_iterator.next().getGuard(), AtomicProposition.PropositionType.Can));
				}

				ln.setAttribute("label",label);

				states_o2n.put(new Pair<String, Node> (edge.getGuard(), current), ln);
			}
		}
		// add start node for did/can-expanded graph
		Node start = new Node(g);
		List<AtomicProposition> start_label = new ArrayList<AtomicProposition>();
		start_label.add(new AtomicProposition("__init", AtomicProposition.PropositionType.Unknown));
		for (Edge edge : lts.getInit().getOutgoingEdges()) {
			Node to = edge.getNext();
			start_label.add(new AtomicProposition(edge.getGuard(), AtomicProposition.PropositionType.Can));
			// also add edges for start node
			new Edge(start, states_o2n.get(new Pair<String, Node>(edge.getGuard(), to)), edge.getGuard());
		}
		start.setAttribute("label", start_label);

		// set initial start state for did/can-expanded graph
		g.setInit(start);

		// build edges for did/can-expanded graph (initial state already handled !)
		//   therefore iterate over all nodes in did/can-expanded graph
		for (Pair<String,Node> current : states_o2n.keySet()) {
			Node from = states_o2n.get(current);
			// iterate over all outgoing edges of the original node that belongs to expanded node
			for (Iterator<Edge> lts_outgoing_edge_iterator = current.getSecond().getOutgoingEdges().iterator(); lts_outgoing_edge_iterator.hasNext();) {
				Edge edge = lts_outgoing_edge_iterator.next();
				String label = edge.getGuard();
				Node to = edge.getNext();
				new Edge(from, states_o2n.get(new Pair<String, Node>(label, to)), label);
			}
		}

		if (debug) {
			for (Node c : g.getNodes()) {
				System.out.println("Node [" + c.getId() + "] : " + c.getAttribute("label"));
			}
		}

		return g;
	}
}
