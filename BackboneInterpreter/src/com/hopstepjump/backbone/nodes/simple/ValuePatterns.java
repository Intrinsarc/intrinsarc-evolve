package com.hopstepjump.backbone.nodes.simple;

import java.util.regex.*;

public class ValuePatterns
{
	public static final String INTERNAL_PATTERN = ">[^\\{]*";
	public static final String STRING_PATTERN = "(?:(?:\\\\\")|[^\"])*";
	public static final String PLUS_MINUS="[\\+\\-]?";
	public static final String DOUBLE_PATTERN = PLUS_MINUS + "[0-9]*\\.[0-9]+(?:[eE]?[-+][0-9]+)?";
	public static final String FLOAT_PATTERN = DOUBLE_PATTERN + "[fF]";
	public static final String CHAR_PATTERN = "'[\\\\]?.'";
	public static final String INT_PATTERN = PLUS_MINUS + "[0-9]+";
	public static final String BOOLEAN_PATTERN = "true|false";
	public static final String NULL_PATTERN = "null";
	public static final String VAR_PATTERN = "[a-zA-Z_$\\!\\?][a-zA-Z0-9_$\\!\\?]*";
	public static final int NUM_PATTERNS = 9;
	public static final Pattern OR_PATTERN = Pattern.compile(
			"(\\s*(?:(?:\"(" +
				STRING_PATTERN +")\")|(" +
				FLOAT_PATTERN + ")|(" +
				DOUBLE_PATTERN + ")|(" +
				CHAR_PATTERN + ")|(" +
				INT_PATTERN + ")|(" +
				BOOLEAN_PATTERN + ")|(" +
				NULL_PATTERN + ")|(" +
				VAR_PATTERN + ")|(" +
				INTERNAL_PATTERN + "))).*");

	public static final String DEFAULT_PATTERN = "default";
}
