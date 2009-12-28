package com.hopstepjump.backbone.expanders;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.deltaengine.base.*;

public class FactoryExpander implements StereotypeExpander
{
	public static final String CREATOR = "Creator";
	
	public void expand(DEStratum perspective, DEElement element)
	{
		DEComponent c = element.asComponent();
		if (c == null || !c.isFactory(perspective))
			return;
		
		// expand out any ports, and add in a part for any create ports
		for (DeltaPair pair : element.getDeltas(ConstituentTypeEnum.DELTA_PORT).getConstituents(perspective))
		{
			DEPort port = pair.getConstituent().asPort();
			if (port.getPortKind() == PortKindEnum.CREATE)
			{
				// add a factory number attribute
				BBAttribute factoryNumber = new BBAttribute(newUUID(), true, true);
				factoryNumber.setName("factoryNumber");
				factoryNumber.setReadOnly(true);
				DEComponent intType = GlobalDeltaEngine.engine.locateObjectForStereotype("int").asComponent();
				factoryNumber.setType(intType);
				List<DEParameter> defaultValue = new ArrayList<DEParameter>();				
				// set the default value -- note: this will get replaced when we flatten
				defaultValue.add(new BBParameter("var:factory#"));
				factoryNumber.setDefaultValue(defaultValue);
				DeltaPair attrPair = new DeltaPair(factoryNumber.getUuid(), factoryNumber, factoryNumber);
				element.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getConstituents(perspective).add(attrPair);
				
				// add a synthetic part of type creator
				DEComponent creator = GlobalDeltaEngine.engine.locateObjectForStereotype(CREATOR).asComponent();
				BBPart part = new BBPart(newUUID(), true, true);
				part.setType(creator);
				part.setName("factory");
				Set<DeltaPair> attrPairs = creator.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getAddObjects();
				DEAttribute factoryAttr = attrPairs.iterator().next().getConstituent().asAttribute();
				part.settable_getSlots().add(new BBSlot(factoryAttr, factoryNumber));
				DeltaPair partPair = new DeltaPair(part.getUuid(), part, part);
				element.getDeltas(ConstituentTypeEnum.DELTA_PART).getConstituents(perspective).add(partPair);
				
				// link the part to the outside port
				BBConnector conn = new BBConnector(newUUID(), true);
				conn.setDelegate(true);
				conn.setFromPart(part);
				// find the port of the create component
				for (DeltaPair portPair : creator.getDeltas(ConstituentTypeEnum.DELTA_PORT).getAddObjects())
					conn.setFromPort(portPair.getConstituent().asPort());
				conn.setToPart(null);
				conn.setToPort(port);
				DeltaPair connPair = new DeltaPair(conn.getUuid(), conn);
				element.getDeltas(ConstituentTypeEnum.DELTA_CONNECTOR).getConstituents(perspective).add(connPair);				
			}
		}
	}
	
	private String newUUID()
	{
		return "synthetic-" + UUID.randomUUID();
	}
}
