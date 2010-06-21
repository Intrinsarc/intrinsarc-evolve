package com.hopstepjump.backbone.parser;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class ParserUtilities
{
	public static <T> void parseUUIDs(final Expect ex, final LazyObjects<T> references)
	{
		ex.oneOrMore(
				",",
				new LiteralMatch(
						new IAction()
						{
							public void act()
							{
								UUIDReference reference = new UUIDReference();
								ex.uuid(reference);
								references.addReference(reference);
							}
						}));		
	}
	
	public static <T> void parseUUIDs(final Expect ex, final List<String> references)
	{
		ex.oneOrMore(
				",",
				new LiteralMatch(
						new IAction()
						{
							public void act()
							{
								UUIDReference reference = new UUIDReference();
								ex.uuid(reference);
								references.add(reference.getUUID());
							}
						}));		
	}
	
	public static List<BBAppliedStereotype> parseAppliedStereotype(final Expect ex)
	{
		List<BBAppliedStereotype> stereos = new ArrayList<BBAppliedStereotype>();
		ex.guard("\u00ab",
			new IAction()
			{
				final UUIDReference stereo = new UUIDReference();
				public void act()
				{
					ex.oneOrMore(",",
							new LiteralMatch(
									new IAction()
									{
										public void act()
										{
											BBAppliedStereotype applied = new BBAppliedStereotype();
											ex.uuid(stereo);
											ex.
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
		return stereos;
	}
	

	private static void parseAppliedValue(final Expect ex)
	{
		UUIDReference attr = new UUIDReference();
		ex.
			uuid(attr).
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
