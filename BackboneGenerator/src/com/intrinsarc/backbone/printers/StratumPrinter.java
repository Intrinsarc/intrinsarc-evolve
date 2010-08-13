package com.intrinsarc.backbone.printers;

import com.intrinsarc.deltaengine.base.*;

public class StratumPrinter
{
	private DEStratum perspective;
	private DEStratum stratum;
	/** how do we want to see the output? */
	private BackbonePrinterMode mode;
	private ReferenceHelper ref;

	public StratumPrinter(DEStratum perspective, DEStratum stratum, BackbonePrinterMode mode)
	{
		this.perspective = perspective;
		this.stratum = stratum;
		this.mode = mode;
		this.ref = new ReferenceHelper(stratum, mode);
	}
	
	public String makePrintString(String indent)
	{
		StringBuilder b = new StringBuilder();

		b.append(indent + "stratum " + ref.name(stratum));
		b.append(indent + "\n\tparent " + ref.name(stratum.getParent()) + "\n\t");
		if (stratum.isRelaxed())
			b.append(" is-relaxed");
		if (stratum.isDestructive())
			b.append(" is-destructive");
		int dSize = stratum.getRawDependsOn().size();
		if (dSize != 0)
		{
			b.append("\n\tdepends-on ");
			int lp = 0;
			for (DEStratum d : stratum.getRawDependsOn())
				b.append(ref.reference(d) + (++lp != dSize ? ", " : ""));
		}
		
		b.append("\n" + indent + "{\n");
		
		// do the elements in some sort of order
		for (DEElement elem : stratum.getChildElements())
			if (elem.asRequirementsFeature() != null)
				makeElementString(indent, b, elem);
		for (DEElement elem : stratum.getChildElements())
			if (elem.asInterface() != null)
				makeElementString(indent, b, elem);
		for (DEElement elem : stratum.getChildElements())
			if (elem.asComponent() != null)
				makeElementString(indent, b, elem);
		b.append(indent + "}\n\n");
		
		return b.toString().replace("\t", "    ");
	}

	private void makeElementString(String indent, StringBuilder b, DEElement elem)
	{
		b.append(new ElementPrinter(perspective, elem, mode).makePrintString(indent));
	}
}
