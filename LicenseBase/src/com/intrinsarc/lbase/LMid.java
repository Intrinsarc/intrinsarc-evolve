package com.intrinsarc.lbase;

public class LMid
{
	public static String getId()
	{
		String macs = "";
		for (String str : LFinder.findAll())
				macs += str;
		return LUtils.encodeBytes(LFinder.fromHex(macs));
	}
}
