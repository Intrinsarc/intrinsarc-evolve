package com.hopstepjump.backbone.parser;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class ParserUtilities
{
	public static void parseUUIDs(final Expect ex, final List<String> uuids)
	{
		ex.oneOrMore(
				",",
				new LiteralMatch(
						new IAction()
						{
							public void act()
							{
								String uuid[] = {""};
								String name[] = {""};
								ex.uuid(uuid, name);
								uuids.add(uuid[0]);
							}
						}));		
	}
	
	public static BBAppliedStereotype parseAppliedStereotype(final Expect ex)
	{
		BBAppliedStereotype applied = new BBAppliedStereotype();
		ex.guard("\u00ab",
			new IAction()
			{
				final String stereoUUID[] = {""};
				public void act()
				{
					ex.oneOrMore(",",
							new LiteralMatch(
									new IAction()
									{
										public void act()
										{
											ex.uuid(stereoUUID).
											guard(":",
												new IAction()
												{
													public void act()
													{
														ex.oneOrMore(",",
																new LiteralMatch(
																		new IAction()
																		{
																			public void act()
																			{
																				parseAppliedValue(ex);
																			}
																		}));
													}
												});
										}
									}));
					ex.literal("\u00bb");
				}
			});
		return applied;
	}
	

	private static void parseAppliedValue(final Expect ex)
	{
		String attrUUID[] = {""};
		ex.
			uuid(attrUUID).
			guard("=",
				new IAction()
				{
					public void act()
					{
						ex.parameter();
					}
				});					
	}
}
