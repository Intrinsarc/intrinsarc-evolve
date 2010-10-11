package com.intrinsarc.lbase;

public class MachineId
{
	public static String getId()
	{
		String macs = "";
		for (String str : Finder.findAll())
				macs += str;
		return Utils.encodeBytes(Finder.fromHex(macs));
	}
}
