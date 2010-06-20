package com.hopstepjump.backbonetests.logic;

import java.util.*;

import org.junit.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.deltaengine.base.*;


public class StereotypeTests extends TestBase
{
	private BBComponent stereo;
	private BBComponent simpleComp;
	private BBComponent comp;
	private BBAttribute stereoAttr;
	private BBAppliedStereotype applied;
	
	@Override
	protected void postSetup()
	{
		stereo = new BBComponent("stereo");
		stereo.setComponentKind(ComponentKindEnum.STEREOTYPE);
		stereoAttr = new BBAttribute("stereoAttr");
		stereo.settable_getAddedAttributes().add(stereoAttr);
		
		stereo.settable_getRawResembles().add(componentStereo);
		applied = new BBAppliedStereotype();
		applied.setStereotype(stereo);
		applied.settable_getProperties().put(stereoAttr, "bar");
		applied.settable_getProperties().put(impl, "zoo");
		
		comp = new BBComponent("Test");
		comp.setRawName("Test");
		comp.settable_getReplacedAppliedStereotypes().add(applied);
		a.settable_getElements().add(comp);

		simpleComp = new BBComponent("Test2");
		simpleComp.setRawName("Test2");
		simpleComp.settable_getReplacedAppliedStereotypes().add(appliedComponentStereo);
		a.settable_getElements().add(simpleComp);

		super.postSetup();
	}
	
	@Test
	public void simple()
	{
		// check in the current applied stereotype
		Assert.assertEquals("bar", comp.extractStringAppliedStereotypeProperty(a, "stereo", "stereoAttr"));
		Assert.assertEquals("foo", simpleComp.extractStringAppliedStereotypeProperty(a, "component", DEElement.IMPLEMENTATION_STEREOTYPE_PROPERTY));
	}
	
	@Test
	public void checkResemblanceClosure()
	{
		Set<DEElement> expected = new HashSet<DEElement>();
		expected.add(componentStereo);
		Assert.assertEquals(expected, stereo.getSuperElementClosure(a, false));
	}
	
	@Test
	public void checkStereotypeResemblanceForStereo()
	{
		// ask for component, should get stereo back
		Assert.assertEquals(stereo, comp.extractAppliedStereotype(a, "component").getStereotype());
		// ask for stereo, should get stereo back
		Assert.assertEquals(stereo, comp.extractAppliedStereotype(a, "stereo").getStereotype());
		// ask for something that isn't there, should get nothing back
		Assert.assertNull(comp.extractAppliedStereotype(a, "interface"));		
	}
	
	@Test
	public void simpleStereotypeResemblanceForProperty()
	{
		Assert.assertEquals("zoo", comp.extractStringAppliedStereotypeProperty(a, "component", DEComponent.IMPLEMENTATION_STEREOTYPE_PROPERTY));
	}
}
