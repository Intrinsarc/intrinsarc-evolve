package lts.util;

import java.util.*;

import lts.*;
import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.model.MTS.*;
import ac.ic.doc.mtstools.util.fsp.*;

/**
 * This class consists exclusively of static methods that operate on or return
 * CompactState and CompositeState.
 * 
 */
public class MTSUtils {

	public static final String MAYBE_MARK = "?";

	/**
	 * Removes the "?" from the <code>label</code> parameter.
	 * 
	 * @param label
	 * @return
	 */
	public static String getAction(String label) {
		return label.replace(MAYBE_MARK, "");
	}

	/**
	 * Adds the "?" to the <code>action</code>
	 * 
	 * @param action
	 * @return
	 */
	public static String getMaybeAction(String action) {
		return action.concat(MAYBE_MARK);
	}

	/**
	 * 
	 * @param label
	 * @return true if <code>label</code> contains a "?"
	 */
	public static boolean isMaybe(String label) {
		return label.contains(MAYBE_MARK);
	}

	public static byte[] encode(int state) {
		byte[] code = new byte[4];
		for (int i = 0; i < 4; ++i) {
			code[i] |= (byte) state;
			state = state >>> 8;
		}
		return code;
	}

	public static int decode(byte[] code) {
		int x = 0;
		for (int i = 3; i >= 0; --i) {
			x |= (int) (code[i]) & 0xFF;
			if (i > 0)
				x = x << 8;
		}
		return x;

	}

	/**
	 * Returns true in tow cases: if the composition field of
	 * <code>compositeState</code> it is not null and it's an MTS, or if there
	 * is at least one MTS in the machines field of <code>compositeState</code>
	 * 
	 * otherwise returns false
	 * 
	 * @param compositeState
	 */
	public static boolean isMTSRepresentation(CompositeState compositeState) {
		CompactState composition = compositeState.composition;
		if (composition != null) {
			if (isMTSRepresentation(composition)) {
				return true;
			}
		} else {
			for (Iterator it = compositeState.machines.iterator(); it.hasNext();) {
				CompactState compactState = (CompactState) it.next();
				if (isMTSRepresentation(compactState)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Rerturns true if there is at least one maybe transition in
	 * <code>compactState</code>
	 * 
	 * @param automata
	 */
	public static boolean isMTSRepresentation(CompactState compactState) {
		boolean isNotEmpty = false;
		for (int i = 0; i < compactState.states.length; i++) {
			MyList transitions = compactState
					.getTransitions(MTSUtils.encode(i));
			while (!transitions.empty()) {
				isNotEmpty = true;
				if (isMaybe(compactState.getAlphabet()[transitions.getAction()])) {
					return true;
				}
				transitions.next();
			}
		}
		if (isNotEmpty) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Build a new String array with the original Strings plus the same ones
	 * with maybe mark.
	 * 
	 * @param oldAlphabetParam
	 * @return the new alphabet
	 */
	public static String[] buildAlphabet(String[] oldAlphabetParam) {
		String[] alphabet = removeTauAndDulicates(oldAlphabetParam);
		String[] retValue = new String[alphabet.length * 2 + 2];

		retValue[0] = oldAlphabetParam[0];
		retValue[1] = getMaybeAction(oldAlphabetParam[0]);

		int half = retValue.length / 2;

		for (int i = 0; i < alphabet.length; i++) {
			retValue[i + 2] = alphabet[i];

			if (MTSUtils.isMaybe(alphabet[i])) {
				retValue[half + i + 1] = getAction(alphabet[i]);
			} else {
				retValue[half + i + 1] = getMaybeAction(alphabet[i]);
			}
		}
		return retValue;
	}

	/**
	 * Returns an array with the same elements but with "tau?" label added in
	 * the second place.
	 * 
	 * @param oldAlphabet
	 * @return the new alphabet
	 */
	public static String[] addTauMaybeAlphabet(String[] oldAlphabet) {
		String[] result = new String[oldAlphabet.length + 1];
		result[0] = oldAlphabet[0];
		result[1] = getMaybeAction(oldAlphabet[0]);
		for (int i = 1; i < oldAlphabet.length; i++) {
			result[i + 1] = oldAlphabet[i];
		}
		return result;
	}

	private static String[] removeTauAndDulicates(String[] oldAlphabet) {
		Set<String> labels = new HashSet<String>();
		for (int i = 0; i < oldAlphabet.length; i++) {
			String label = MTSUtils.getAction(oldAlphabet[i]);
			if (!label.equals(MTSConstants.TAU)) {
				labels.add(label);
			}
		}
		String result[] = new String[labels.size()];
		return labels.toArray(result);
	}

	protected <State, Action> boolean orphanState(State to,
			MTS<State, Action> mts, TransitionType type) {
		for (State state : mts.getStates()) {
			for (Pair<Action, State> transition : mts.getTransitions(state,
					type)) {
				if (transition.getSecond().equals(to)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Remove all actions in <code>actionNames</code> from the <code>mts</code>
	 * alphabet.
	 * 
	 */
	public static <State, Action> void removeActionsFromAlphabet(
			MTS<State, Action> mts, Set<String> actionNames) {
		Set<Action> toDelete = new HashSet<Action>();
		for (String actionName : actionNames) {
			for (Action action : mts.getActions()) {
				if (((String) action).indexOf(actionName) != -1) {
					toDelete.add(action);
				}
			}
		}

		for (Action actionToRemove : new ArrayList<Action>(toDelete)) {
			for (State stateFrom : mts.getStates()) {
				for (State stateTo : mts.getTransitions(stateFrom,
						TransitionType.POSSIBLE).getImage(actionToRemove)) {
					mts.removePossible(stateFrom, actionToRemove, stateTo);
				}
			}
			mts.removeAction(actionToRemove);
		}
	}

	/**
	 * Returns true if compactState has no transition from initial state.
	 * 
	 */
	public static boolean isEmptyMTS(CompactState compactState) {
		MTS<Long, String> mts = AutomataToMTSConverter.getInstance().convert(
				(CompactState) compactState);
		return mts.getTransitions(mts.getInitialState(),
				TransitionType.POSSIBLE).size() == 0;
	}

	/**
	 * Returns true if <code>model</code> has an error state
	 * 
	 * @param model
	 * @return
	 */
	public static boolean isPropertyModel(CompactState model) {
		return model.hasERROR();
	}
}