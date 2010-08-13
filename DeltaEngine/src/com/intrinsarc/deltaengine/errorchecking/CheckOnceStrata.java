package com.intrinsarc.deltaengine.errorchecking;

import java.util.*;

import com.intrinsarc.deltaengine.base.*;

public class CheckOnceStrata
{
	private static Set<Object> checkedOk = new HashSet<Object>();
	
	public static void clear()
	{
		checkedOk.clear();
	}
	
	public static boolean isOmitCheck(DEStratum s)
	{
		if (isCheckOkStratum(s) && checkedOk.contains(s.getRepositoryObject()))
			return true;
		// remove from the check list as we are no longer "check once"
		checkedOk.remove(s.getRepositoryObject());
		return false;
	}
	
	public static void isNowWriteable(Package pkg)
	{
		checkedOk.remove(pkg);
	}
	
	public static void possiblySetOmitCheck(DEStratum s)
	{
		if (isCheckOkStratum(s))
			checkedOk.add(s.getRepositoryObject());
	}
	
	public static boolean isCheckOkStratum(DEStratum s)
	{
		return s.isCheckOnceIfReadOnly() && s.isReadOnly();
	}
}
