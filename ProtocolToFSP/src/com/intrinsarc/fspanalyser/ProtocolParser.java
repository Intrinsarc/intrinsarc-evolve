package com.intrinsarc.fspanalyser;

import java.util.*;

import com.intrinsarc.deltaengine.base.*;

public class ProtocolParser
{
	private DEStratum perspective;
	private DEComponent type;
	private String error;
	private String toParse;

	public ProtocolParser(DEStratum perspective, DEComponent type)
	{
		this.perspective = perspective;
		this.type = type;
		this.error = "Problem parsing protocol in " + perspective.getName() + "::" + type.getName(perspective);
		this.toParse = type.getLeafProtocol(perspective, type);
	}

	public List<BBProtocol> makeLeafProtocol() throws BackboneFSPTranslationException
	{
		if (toParse == null)
			return new ArrayList<BBProtocol>();
		
		// only currently handles 1 protocol
		BBProtocol protocol = new BBProtocol();
		protocol.setName(type.getFullyQualifiedName("_"));
		BBInteractionFragment interTop = makeRegion(null, "seq");
		protocol.setTopFragment(interTop);
		StringTokenizer tok = new StringTokenizer(toParse);
		parse(protocol, interTop, tok);
		
		List<BBProtocol> all = new ArrayList<BBProtocol>();
		all.add(protocol);
		return all;
	}

	private BBInteractionFragment makeRegion(BBInteractionFragment parent, String operator)
	{
		BBInteractionFragment region = new BBInteractionFragment();
		BBCombinedFragment combined = new BBCombinedFragment();
		combined.setOperator(operator);
		region.setCombined(combined);
		if (parent != null)
			parent.getCombined().addOperand(makeOperand(region));
		return region;
	}

	private BBOperand makeOperand(BBInteractionFragment region)
	{
		BBOperand operand = new BBOperand();
		operand.addConstituent(region);
		return operand;
	}

	private void parse(BBProtocol protocol, BBInteractionFragment parent, StringTokenizer tok) throws BackboneFSPTranslationException
	{
		List<BBInteractionFragment> stack = new ArrayList<BBInteractionFragment>();
		
		while (tok.hasMoreTokens())
		{
			String token = tok.nextToken();
			if (token.equals("}"))
				return;
			
			if (token.equals("seq") || token.equals("par") || token.equals("loop") || token.equals("opt") || token.equals("alt"))
			{
				consume(tok, "{");
				BBInteractionFragment frag = makeRegion(parent, token);
				parse(protocol, frag, tok);
			}
			else
			{
				// expect either return or "a" method "b"
				if (token.equals("return"))
					makeReturn(stack, parent);
				else
				{
					String start = token;
					String call = consume(tok);
					String end = consume(tok);
					makeCall(protocol, stack, parent, start, call, end);
				}
			}
		}
	}

	private void makeCall(
			BBProtocol protocol,
			List<BBInteractionFragment> stack,
			BBInteractionFragment parent,
			String start,
			String call,
			String end) throws BackboneFSPTranslationException
	{
		// find the port, interface and operation
		DEPort port[] = new DEPort[1];
		DEInterface iface[] = new DEInterface[1];
		DEOperation operation[] = new DEOperation[1];
		boolean required = start.equals("self");
		findPortInterfaceAndOperation(port, iface, operation, required ? end : start, call, required);
		BBProtocolActor startActor = findActor(protocol, required ? null : port[0], iface[0], required);
		BBProtocolActor endActor = findActor(protocol, required ? port[0] : null, iface[0], !required);
		
		BBInteractionFragment frag = new BBInteractionFragment();
		BBInteraction inter = new BBInteraction();
		frag.setInteraction(inter);
		inter.setFirst(startActor);
		inter.setSecond(endActor);
		inter.setMessage(operation[0]);
		parent.getCombined().addOperand(makeOperand(frag));
		stack.add(frag);
	}

	private BBProtocolActor findActor(BBProtocol protocol, DEPort port, DEInterface iface, boolean required)
	{
		// handle self
		if (port == null)
			return findUniqueActor(protocol, null, false, true, null, null);
		else
			return findUniqueActor(protocol, port.getName(), required, false, port, iface);
	}

	private BBProtocolActor findUniqueActor(BBProtocol protocol, String name, boolean required, boolean b, DEPort port, DEInterface iface)
	{
		BBProtocolActor actor = new BBProtocolActor();
		actor.setName(name);
		actor.setRequiredInterface(required);
		actor.setSelf(b);
		actor.setPort(port);
		actor.setIface(iface);
		// see if we have another actor like this
		for (BBProtocolActor a : protocol.getActors())
			if (a.equals(actor))
				return a;
		protocol.getActors().add(actor);
		return actor;
	}

	private void findPortInterfaceAndOperation(
			DEPort[] port,
			DEInterface[] iface,
			DEOperation[] operation,
			String portName,
			String call,
			boolean required) throws BackboneFSPTranslationException
	{
		// look for the port
		for (DeltaPair p : type.getDeltas(ConstituentTypeEnum.DELTA_PORT).getConstituents(perspective))
		{
			DEPort pt = p.getConstituent().asPort();
			if (portName.equals(pt.getName()))
				port[0] = pt;
		}
		if (port[0] == null)
			throw new BackboneFSPTranslationException(error + ": cannot find port " + portName);
		
		if (required)
		{
			for (DEInterface i : type.getRequiredInterfaces(perspective, port[0]))
				if (findMethod(iface, operation, i, call))
					return;
		}
		else
		{
			for (DEInterface i : type.getProvidedInterfaces(perspective, port[0]))
				if (findMethod(iface, operation, i, call))
					return;			
		}
		
		// if we have got to here, we have a problem
		throw new BackboneFSPTranslationException(error + ": cannot find operation " + call + " on any interfaces in port " + portName);		
	}

	private boolean findMethod(DEInterface[] iface, DEOperation[] operation, DEInterface i, String call)
	{
		for (DeltaPair p : i.getDeltas(ConstituentTypeEnum.DELTA_OPERATION).getConstituents(perspective))
		{
			DEOperation op = p.getConstituent().asOperation();
			if (call.equals(op.getName()))
			{
				iface[0] = i;
				operation[0] = op;
				return true;
			}
		}
		return false;
	}

	private String consume(StringTokenizer tok) throws BackboneFSPTranslationException
	{
		if (!tok.hasMoreTokens())
			throw new BackboneFSPTranslationException(error);
		return tok.nextToken();
	}

	private void makeReturn(List<BBInteractionFragment> stack, BBInteractionFragment parent) throws BackboneFSPTranslationException
	{
		if (stack.size() == 0)
			throw new BackboneFSPTranslationException(error);
		int lastIndex = stack.size() - 1;
		BBInteractionFragment last = stack.get(lastIndex);
		stack.remove(lastIndex);
		BBInteractionFragment ret = new BBInteractionFragment();
		BBInteraction inter = new BBInteraction();
		inter.setCall(false);
		inter.setFirst(last.getInteraction().getSecond());
		inter.setSecond(last.getInteraction().getSecond());
		inter.setMessage(last.getInteraction().getMessage());
		parent.getCombined().getOperands().add(makeOperand(ret));
	}

	private void consume(StringTokenizer tok, String expected) throws BackboneFSPTranslationException
	{
		String next = tok.hasMoreTokens() ? tok.nextToken() : null;
		if (!expected.equals(next))
			throw new BackboneFSPTranslationException(error + ": expected " + expected + ", but found " + next);
	}
}
