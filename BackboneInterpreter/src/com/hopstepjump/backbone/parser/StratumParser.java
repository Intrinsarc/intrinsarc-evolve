package com.hopstepjump.backbone.parser;

import java.io.*;

import com.hopstepjump.backbone.nodes.*;

public class StratumParser
{
	private BBStratum bb = new BBStratum();
	private InputStream stream;
	
	public StratumParser(InputStream stream)
	{
		this.stream = stream;
	}
//	public BBStratum parseStratum()
//	{
//		expectString(stream, "stratum");
//		PName name = expectName(stream);
//		int choice;
//		while ((choice = expectOptionalString(stream, "is-relaxed", "is-destructive")) != -1)
//		{
//			if (choice == 0)
//				bb.setRelaxed(true);
//			if (choice == 1)
//				bb.setDestructive(true);
//		}
//		if (expectOptionalString(stream, "depends-on"))
//		{
//			PName dep = expectName(stream);
//			if (expectOptionalString(stream, ",") == -1)
//				break;
//		}
//		expectString(stream, "{");
//		
//		return bb;
//	}
}
