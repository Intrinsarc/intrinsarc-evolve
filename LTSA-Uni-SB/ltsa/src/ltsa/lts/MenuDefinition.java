package ltsa.lts;

import java.util.*;

/* -----------------------------------------------------------------------*/

public class MenuDefinition {
	Symbol name;
	ActionLabels actions;
	Symbol params;
	Symbol target;
	Vector<RelabelDefn> actionMapDefn;
	Vector<RelabelDefn> controlMapDefn;
	Vector<AnimationPart> animations;

	public static Hashtable<String, MenuDefinition> definitions;

	public static void compile() {
		RunMenu.init();
		final Enumeration<MenuDefinition> e = definitions.elements();
		while (e.hasMoreElements()) {
			final MenuDefinition m = e.nextElement();
			RunMenu.add(m.makeRunMenu());
		}
	}

	public static String[] names() {
		if (definitions == null)
			return null;
		final int n = definitions.size();
		if (n == 0)
			return null;
		final String[] na = new String[n];
		final Enumeration<String> e = definitions.keys();
		int i = 0;
		while (e.hasMoreElements())
			na[i++] = e.nextElement();
		return na;
	}

	public static boolean[] enabled(String targ) {
		if (definitions == null || definitions.size() == 0)
			return null;
		final boolean[] na = new boolean[definitions.size()];
		final Enumeration<String> e = definitions.keys();
		int i = 0;
		while (e.hasMoreElements()) {
			final MenuDefinition m = definitions.get(e.nextElement());
			na[i++] = m.target == null ? true : targ
					.equals(m.target.toString());
		}
		return na;
	}

	public RunMenu makeRunMenu() {
		final String na = name.toString();
		if (params == null) {
			final Vector<String> a = actions.getActions(null, null);
			return new RunMenu(na, a);
		} else {
			Relation<String, String> a = RelabelDefn.getRelabels(actionMapDefn);
			Relation<String, String> c = RelabelDefn
					.getRelabels(controlMapDefn);
			a = a == null ? new Relation<String, String>() : a.inverse();
			c = c == null ? new Relation<String, String>() : c.inverse();
			includeParts(a, c);
			return new RunMenu(na, params == null ? null : params.toString(),
					a, c);
		}
	}

	protected void includeParts(Relation<String, String> actions,
			Relation<String, String> controls) {
		if (animations == null)
			return;
		final Enumeration<AnimationPart> e = animations.elements();
		while (e.hasMoreElements()) {
			final AnimationPart ap = e.nextElement();
			ap.makePart();
			actions.union(ap.getActions());
			controls.union(ap.getControls());
		}
	}

	public void addAnimationPart(Symbol n, Vector<RelabelDefn> r) {
		if (animations == null)
			animations = new Vector<AnimationPart>();
		animations.addElement(new AnimationPart(n, r));
	}

	private static class AnimationPart {
		Symbol name;
		Vector<RelabelDefn> relabels;
		RunMenu compiled;

		AnimationPart(Symbol n, Vector<RelabelDefn> r) {
			name = n;
			relabels = r;
		}

		void makePart() {
			final MenuDefinition m = definitions.get(name.toString());
			if (m == null) {
				Diagnostics.fatal("Animation not found: " + name, name);
				return;
			}
			if (m.params == null) {
				Diagnostics.fatal("Not an animation: " + name, name);
				return;
			}
			compiled = m.makeRunMenu();
			if (relabels != null) {
				final Relation<String, String> r = RelabelDefn
						.getRelabels(relabels);
				if (compiled.actions != null)
					compiled.actions.relabel(r);
				if (compiled.controls != null)
					compiled.controls.relabel(r);
			}
		}

		Relation<String, String> getActions() {
			if (compiled != null)
				return compiled.actions;
			else
				return null;
		}

		Relation<String, String> getControls() {
			if (compiled != null)
				return compiled.controls;
			else
				return null;
		}

	}// end AnimationPart

}