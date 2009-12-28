package lts.util;

import lts.*;

public class LTSUtils {

	public static boolean isOrSymbol(Symbol current) {
		return current.kind == Symbol.OR && current.kind != Symbol.PLUS_CA
				&& current.kind != Symbol.PLUS_CR
				&& current.kind != Symbol.MERGE;
	}

	public static boolean isCompositionExpression(Symbol current) {
		return current.kind == Symbol.OR || current.kind == Symbol.PLUS_CA
				|| current.kind == Symbol.PLUS_CR
				|| current.kind == Symbol.MERGE;
	}
}
