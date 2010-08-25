package com.intrinsarc.repositorybase;

import java.util.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;

public class CommonRepositoryFunctions
{
	public static final String SAVE_DATE_FORMAT = "EEE MMM d, yyyy 'at' hh:mm:ss z";  
	public static final String MODEL                    = "model";
	public static final String COMPONENT                = "component";	
	public static final String INTERFACE                = "interface";
	public static final String STRATUM                  = "stratum";
	public static final String PORT                     = "port";
	public static final String CONNECTOR                = "connector";
	public static final String ATTRIBUTE                = "attribute";
	public static final String STATE                    = "state";
	public static final String COMPOSITE_STATE_CLASS    = "CompositeState";
	public static final String STATE_CLASS              = "State";
	public static final String START_STATE_CLASS        = "Start";
	public static final String END_STATE_CLASS          = "End";
  public static final String FACTORY_BASE             = "FactoryBase";

  public static final String PRIMITIVE_TYPE           = "primitive-type";
  public static final String TRACE                    = "trace";
  public static final String LIFECYCLE_CALLBACKS      = "lifecycle-callbacks";
	public static final String SUPPRESS_INHERITANCE     = "no-inheritance";
	public static final String SUPPRESS_GENERATION      = "no-generation";
	public static final String SUPPRESS_GENERATION_PORT = "no-generation-port";
	public static final String PORT_BEAN_MAIN           = "bean-main";
	public static final String PORT_BEAN_NO_NAME        = "bean-no-name";
	public static final String PORT_BEAN_NOT_MAIN       = "force-not-bean-main";
	public static final String PORT_WANTS_REQUIRED      = "wants-required-when-providing";
  public static final String STRATUM_PREAMBLE         = "preamble";
  public static final String STRATUM_EXPORT_VERSION   = "export-version";
  public static final String STRATUM_EXPORT_INFO      = "export-info";
  public static final String PLACEHOLDER              = DEComponent.PLACEHOLDER_STEREOTYPE_PROPERTY;
  public static final String FACTORY                  = DEComponent.FACTORY_STEREOTYPE_PROPERTY;
  public static final String LEGACY_BEAN              = DEComponent.LEGACY_BEAN_STEREOTYPE_PROPERTY;

  public static final String IMPLEMENTATION_CLASS     = "implementation-class";
  public static final String PROTOCOLS                = "protocols";
  public static final String RELAXED                  = "relaxed";
  public static final String CHECK_ONCE_IF_READ_ONLY  = "check-once-if-read-only";
  public static final String DESTRUCTIVE              = "destructive";
  public static final String DIRECTED                 = "directed";
  public static final String SLOT                     = "slot";
  public static final String ACTUAL_VALUE             = "actual-value";
  public static final String ACTUAL_SLOT_VALUE        = "actual-slot-value";
  
  // the next three are just so we can display Alloy-generated errors more gracefully
  public static final String OVERRIDDEN_SLOT          = "backbone-overriden-slot";
  public static final String OVERRIDDEN_SLOT_TEXT     = "overriddenSlotText";
  public static final String OVERRIDDEN_SLOT_ALIAS    = "overriddenSlotAlias";

  // changes in a subcomponent
  public static final String DELTA                    = "backbone-delta";
  
  // documentation stereotypes
  public static final String DOCUMENTATION_TOP           = "documentation-top";
  public static final String DOCUMENTATION_INCLUDED      = "documentation-included";
  public static final String DOCUMENTATION_FIGURE        = "documentation-figure";
  public static final String DOCUMENTATION_SEE_ALSO      = "documentation-see-also";
	public static final String DOCUMENTATION_IMAGE_GALLERY = "documentation-image-gallery";
  public static final String DOCUMENTATION_DOCUMENT_NAME = "documentName";
  public static final String DOCUMENTATION_OWNER         = "owner";
  public static final String DOCUMENTATION_EMAIL         = "email";
  public static final String DOCUMENTATION_YEARS         = "copyrightYears";
  public static final String DOCUMENTATION_SPACE_PADDING = "numberOfSpacesForPadding";
  public static final String DOCUMENTATION_TITLE_PREFIX  = "pageTitlePrefix";
  public static final String DOCUMENTATION_SITE_INDEX    = "siteIndex";
  
  // error stereotype
  public static final String ERROR                       = "error"; 
  public static final String ERROR_DESCRIPTION           = "error-description";
	public static final String BACKBONE_STRATUM_NAME       = "backbone";
	
	// backbone code generation options
  public static final String JAVA_SOURCE_FOLDER = "bb-java-folder";
  public static final String COMPOSITE_PACKAGE = "bb-composite-package";
  public static final String GENERATION_PROFILE = "generation-profile";
  public static final String SUPPRESS_JAVA_SOURCE = "bb-java-suppress";
	public static final String BACKBONE_SOURCE_FOLDER = "bb-source-folder";
  public static final String SUPPRESS_BACKBONE_SOURCE = "bb-source-suppress";
	public static final String BACKBONE_CLASSPATH = "bb-classpath";
	public static final String BACKBONE_RUN_STRATUM = "bb-run-stratum";
	public static final String BACKBONE_RUN_COMPONENT = "bb-run-component";
	public static final String BACKBONE_RUN_PORT = "bb-run-port";

	// visual stereotypes
	public static final String VISUAL_EFFECT = "visual-effect";
  public static final String VISUALLY_SUPPRESS = "hide";
	public static final String STATE_PART = "state-part";
	
	
  private boolean isTypeOrSubtypeOf(Element owner, EClass[] classes)
  {
    EClass ownerClass = owner.eClass();
    
    // try for an easy win
    for (EClass ecls : classes)
      if (ownerClass == ecls)
        return true;
    
    // see if ownerclass is in the hierarchy of ecls
    for (EClass ecls : classes)
    {
      if (new UMLSubclassFinder(ecls).findSubClasses().contains(ownerClass))
        return true;
    }
    return false;
  }
  
  public Namespace findVisuallyOwningNamespace(DiagramFacet diagram, ContainerFacet container)
  {
    // locate to the diagram, or a possible nesting package
    // look upwards, until we find one that has a PackageFacet registered
    Namespace owner = (Namespace) diagram.getLinkedObject();
    while (container != null)
    {
      Element location = (Element) container.getFigureFacet().getSubject();
      if (location instanceof Namespace)
      {
        owner = (Namespace) location;
        break;
      }
      container = container.getContainedFacet().getContainer();
    }
    return owner;
  }
  
  public Package findVisuallyOwningPackage(DiagramFacet diagram, ContainerFacet container)
  {
    // locate to the diagram, or a possible nesting package
    // look upwards, until we find one that has a PackageFacet registered
    Namespace owner = (Namespace) diagram.getLinkedObject();
    while (container != null)
    {
      Element location = (Element) container.getFigureFacet().getSubject();
      if (location instanceof Package)
      {
        owner = (Package) location;
        break;
      }
      container = container.getContainedFacet().getContainer();
    }

    return (Package) owner;
  }
  
  public Package findVisuallyOwningStratum(DiagramFacet diagram, ContainerFacet container)
  {
    Element found = findVisuallyOwningPackage(diagram, container);
    // look upwards until this is a type of package
    while (found != null && !UMLTypes.isStratum(found))
      found = found.getOwner();
    return (Package) found;
  }


  public Element findOwningElement(Element element, java.lang.Class<?> cls)
  {
    Element found = element.getOwner();
    while (found != null && !(cls.isAssignableFrom(found.getClass())))
      found = found.getOwner();
    return found;
  }

  public String getFullyQualifiedName(Element element, String separator, Element stopAt)
  {
    // follow this up through its containment hierarchy
    ArrayList<String> names = new ArrayList<String>();
    for (; element != null && element != stopAt; element = element.getOwner())
    {
      if (element instanceof NamedElement)
        names.add(((NamedElement) element).getName());
      else
        names.add("???");
    }
    
    StringBuffer nameBuf = new StringBuffer();
    Collections.reverse(names);
    boolean nameSet = false;
    for (String name : names)
    {
      if (nameSet)
        nameBuf.append(separator);
      nameBuf.append(name);
      nameSet = true;
    }
    return nameBuf.toString();
  }
  
  public PersistentDiagram retrievePersistentDiagram(Package pkg)
  {
    // if the diagram doesn't exist, then create it
    J_DiagramHolder holder = pkg.getJ_diagramHolder();
    if (holder == null || holder.getDiagram() == null)
    {
      PersistentDiagram diagram = new PersistentDiagram(pkg, pkg.getUuid(), null, 0);
      return diagram;
    }      

    // otherwise, construct it by converting from the UML2 metamodel form into the Persistent_ form
    return new DbDiagramToPersistentDiagramTranslator(pkg, holder.getDiagram()).translate();      
  }

  public void setUuid(Set<String> uuids, Element elem, String uuid)
  {
  	if (uuids.contains(uuid))
  		throw new IllegalStateException("Found more than one uuid of " + uuid);
  	elem.setUuid(uuid);
  	uuids.add(uuid);
  }
  
  public void setUniqueUuid(Set<String> uuids, Element elem, String uuid)
  {
  	if (!uuids.contains(uuid))
  		setUuid(uuids, elem, uuid);
  	else
  	{
  		for (char ch = 'a'; ch <= 'z'; ch++)
  			if (!uuids.contains(uuid + ch))
  			{
  				setUuid(uuids, elem, uuid + ch);
  				return;
  			}
  		// should give an error
  		setUuid(uuids, elem, uuid);
  	}
  }
  
  public Model initialiseModel(boolean initialiseWithFoundation)
  {
  	Set<String> uuids = new HashSet<String>();
    Model topLevel = UML2Factory.eINSTANCE.createModel();
    topLevel.setName(MODEL);
    if (!initialiseWithFoundation)
      return topLevel;
    
    // make the primitive types and the backbone profile
    // make the stereotypes
    Package backbone = topLevel.createChildPackages();
    Profile profile = (Profile) backbone.createChildPackages(UML2Package.eINSTANCE.getProfile());
    profile.setUuid("backbone-profile");
    profile.setName("backbone profile");
    profile.setDocumentation("The profile for Backbone modelling");

    Type typeType = createPrimitiveType(uuids, profile, "type");  // just for convenience
    Type booleanType = createPrimitiveType(uuids, profile, "boolean");
    Type colorType = createPrimitiveType(uuids, profile, "Color");
    Type intType = createPrimitiveType(uuids, profile, "int");
    Type byteType = createPrimitiveType(uuids, profile, "byte");
    Type shortType = createPrimitiveType(uuids, profile, "short");
    Type charType = createPrimitiveType(uuids, profile, "char");
    Type longType = createPrimitiveType(uuids, profile, "long");
    Type floatType = createPrimitiveType(uuids, profile, "float");
    Type doubleType = createPrimitiveType(uuids, profile, "double");
    Type stringType = createPrimitiveType(uuids, profile, "String");
    Type dateType = createPrimitiveType(uuids, profile, "Date");
    Type timeType = createPrimitiveType(uuids, profile, "Time");
    Type dateTimeType = createPrimitiveType(uuids, profile, "DateTime");
    Type intervalType = createPrimitiveType(uuids, profile, "Interval");
    Type objectType = createPrimitiveType(uuids, profile, "Object");
    
    Stereotype element = createStereotype(uuids, profile, "element", "'Class'", "The common ancestor of all backbone definitions that live in strata.");
    Property implClass = addAttribute(uuids, element, IMPLEMENTATION_CLASS, stringType, "The implementation class for this element");
    addAttribute(uuids, element, LEGACY_BEAN, booleanType, "Is this element related to a legacy JavaBean?");
    addAttribute(uuids, element, SUPPRESS_INHERITANCE, booleanType, "Suppress implementation inheritance when a leaf resembles another");

    element.setIsAbstract(true);
    
    Stereotype iface = createStereotype(uuids, profile, INTERFACE, "'Interface'", "A component which cannot or hasn't been decomposed further.  An interface which is visible to backbone.");
    createResemblance(uuids, element, iface);
    
    Stereotype component = createStereotype(uuids, profile, COMPONENT, "'Class'", "A Backbone component: composite or leaf");
    addAttribute(uuids, component, "navigable", booleanType, "Can we navigate to direct neighbours via this component?");
    addAttribute(uuids, component, PROTOCOLS, stringType, "The protocol(s) for this component");
    addAttribute(uuids, component, "cluster", booleanType, "Does this expose references to internal parts?");
    Property factory = addAttribute(uuids, component, FACTORY, booleanType, "Is this a factory?");
    addAttribute(uuids, component, PLACEHOLDER, booleanType, "Does this expose references to internal parts?");
    addAttribute(uuids, component, LIFECYCLE_CALLBACKS, booleanType, "Does this require lifecycle callbacks?");
    createResemblance(uuids, element, component);

    Stereotype primitive = createStereotype(uuids, profile, PRIMITIVE_TYPE, "'Class'", "A parameter or attribute.  It can be substituted, but only with another primitive type.");
    createResemblance(uuids, element, primitive);

    Stereotype stratum = createStereotype(uuids, profile, STRATUM, "'Package', 'Model'", "A (possibly hierarchical) container of Backbone definitions.");
    Property destructive = addAttribute(uuids, stratum, DESTRUCTIVE, booleanType, "Can this stratum contain destructive (replace, delete) substitutions?");
    addAttribute(uuids, stratum, RELAXED, booleanType, "If another stratum depends on this, can it also see this stratum's dependencies?) ?");
    addAttribute(uuids, stratum, CHECK_ONCE_IF_READ_ONLY, booleanType, "Only check once if this is read-only.  No elements in the stratum can be replaced.");
    addAttribute(uuids, stratum, STRATUM_PREAMBLE, stringType, "A preamble which is inserted into the Backbone code for this stratum");
    addAttribute(uuids, stratum, BACKBONE_CLASSPATH, stringType, "The classpath to use for Backbone for this stratum");
    addAttribute(uuids, stratum, BACKBONE_RUN_STRATUM, stringType, "The stratum of the component to use for running");
    addAttribute(uuids, stratum, BACKBONE_RUN_COMPONENT, stringType, "The component to use for running");
    addAttribute(uuids, stratum, BACKBONE_RUN_PORT, stringType, "The component port to use for running");
    addAttribute(uuids, stratum, BACKBONE_SOURCE_FOLDER, stringType, "The folder to generate Backbone source to");
    addAttribute(uuids, stratum, JAVA_SOURCE_FOLDER, stringType, "The folder to generate Java source to");
    addAttribute(uuids, stratum, COMPOSITE_PACKAGE, stringType, "The package to use for generating source code for composites into");
    addAttribute(uuids, stratum, GENERATION_PROFILE, stringType, "The profile to be used for generating hardcoded factories");
    addAttribute(uuids, stratum, SUPPRESS_BACKBONE_SOURCE, booleanType, "Force suppression of Backbone source for this stratum");
    addAttribute(uuids, stratum, SUPPRESS_JAVA_SOURCE, booleanType, "Force suppression of Java source for this stratum");
    addAttribute(uuids, stratum, STRATUM_EXPORT_VERSION, stringType, "A version number for the stratum.  Used for export.");
    addAttribute(uuids, stratum, STRATUM_EXPORT_INFO, stringType, "Used to store stratum export information");
    
    Stereotype stereoConn = createStereotype(uuids, profile, CONNECTOR, "'Connector'", "A connector joins two ports.");
    addAttribute(uuids, stereoConn, DIRECTED, booleanType, "Is this directional?");

    Stereotype stereoPort = createStereotype(uuids, profile, PORT, "'Port'", "A port allows a component to provide or require services.");
    Property suppressMethodGeneration = addAttribute(uuids, stereoPort, SUPPRESS_GENERATION_PORT, booleanType, "Should we not generate methods and fields for this?");
    addAttribute(uuids, stereoPort, PORT_BEAN_MAIN, booleanType, "Is this the main port of a bean?");
    addAttribute(uuids, stereoPort, PORT_BEAN_NO_NAME, booleanType, "Is this port from an add() method on a bean?");
    addAttribute(uuids, stereoPort, PORT_BEAN_NOT_MAIN, booleanType, "Force this port to not be a bean-main");
    addAttribute(uuids, stereoPort, PORT_WANTS_REQUIRED, booleanType, "Provide the inferred required interface when getting a provided interface");

    Stereotype stereoAttr = createStereotype(uuids, profile, ATTRIBUTE, "'Property'", "An attribute holds simple state of a component.");
    addAttribute(uuids, stereoAttr, SUPPRESS_GENERATION, booleanType, "Should we not generate fields and methods for this?");
    addAttribute(uuids, stereoAttr, ACTUAL_VALUE, stringType, "The actual value for an attribute initializer starting with >");

    Stereotype slotAttr = createStereotype(uuids, profile, SLOT, "'Slot'", "A slot holds the value for an attribute in a classifier instance");
    addAttribute(uuids, slotAttr, ACTUAL_SLOT_VALUE, stringType, "The actual value for a slot initializer starting with >");

    // create a set of stereotypes that won't get pushed over to backbone
    Stereotype visual = createStereotype(uuids, profile, VISUAL_EFFECT, "", "The superstereotype of visual stereotypes.  Not transferred to Backbone.");
    Stereotype suppress = createStereotype(uuids, profile, VISUALLY_SUPPRESS, "'Port', 'Property'", "Indicates that a port or attribute will be visually suppressed.");
    createResemblance(uuids, visual, suppress).setDocumentation("Supression of a port or attribute is a visual effect.");
    
    createStereotype(uuids, profile, DELTA, "'Class', 'Property', 'Interface'", "Indication that the item is a delta from the resembled definition.");

    Stereotype slot = createStereotype(uuids, profile, OVERRIDDEN_SLOT, "'Slot'", "The overide data for a slot so we can show alloy errors.");
    addAttribute(uuids, slot, OVERRIDDEN_SLOT_TEXT, stringType, "An override so we can control how the slot string displays.");
    addAttribute(uuids, slot, OVERRIDDEN_SLOT_ALIAS, booleanType, "An override so we can make the slot look aliased.");
    
    // state parts need to be explicitly indicated, so we can draw them differently
    Stereotype statePart = createStereotype(uuids, profile, STATE_PART, "'Property'", "Indicates that a property is a part of a composite state machine.");
    createResemblance(uuids, visual, statePart).setDocumentation("Showing a state part differently is a visual effect.");
    
    // assign type names to the primitive types
    // NOTE: don't assign string implementation type as it interferes with bootstrapping
    //       as primitive-type has a string attribute
    assignImplementationType(typeType, "Type", primitive, implClass);
    assignImplementationType(colorType, "java.awt.Color", primitive, implClass);
    assignImplementationType(intType, "java.lang.Integer", primitive, implClass);
    assignImplementationType(byteType, "java.lang.Byte", primitive, implClass);
    assignImplementationType(shortType, "java.lang.Short", primitive, implClass);
    assignImplementationType(charType, "java.lang.Char", primitive, implClass);
    assignImplementationType(longType, "java.lang.Long", primitive, implClass);
    assignImplementationType(floatType, "java.lang.Float", primitive, implClass);
    assignImplementationType(doubleType, "java.lang.Double", primitive, implClass);
    assignImplementationType(dateType, "java.util.Date", primitive, implClass);
    assignImplementationType(timeType, "java.lang.Long", primitive, implClass);
    assignImplementationType(dateTimeType, "java.util.Date", primitive, implClass);
    assignImplementationType(intervalType, "java.lang.Long", primitive, implClass);
    assignImplementationType(objectType, "java.lang.Object", primitive, implClass);

    // add the documentation profile
    Profile docProfile = (Profile) backbone.createChildPackages(UML2Package.eINSTANCE.getProfile());
    createDependency(uuids, docProfile, profile);
    setUuid(uuids, docProfile, "documentation-profile");
    docProfile.setName("documentation profile");
    docProfile.setDocumentation("The profile for documenting a model");

    Stereotype dTop = createStereotype(uuids, docProfile, DOCUMENTATION_TOP, "'Package'", "The top level documentation package");
    addAttribute(uuids, dTop, DOCUMENTATION_DOCUMENT_NAME, stringType, "The name of the document.");
    addAttribute(uuids, dTop, DOCUMENTATION_OWNER, stringType, "The owner of document.");
    addAttribute(uuids, dTop, DOCUMENTATION_EMAIL, stringType, "The email address of the owner.");
    addAttribute(uuids, dTop, DOCUMENTATION_YEARS, stringType, "The copyright period of the document.");
    addAttribute(uuids, dTop, DOCUMENTATION_SPACE_PADDING, stringType, "Number of non-breaking spaces to pad the home navigation entry with.");
    addAttribute(uuids, dTop, DOCUMENTATION_TITLE_PREFIX, stringType, "The prefix to add for each page title, apart from home.");
    addAttribute(uuids, dTop, DOCUMENTATION_SITE_INDEX, stringType, "The index to add to the htmlTemplate and htmlGenerateTo slots to extract the folders.  Allows more than one site to be generated from a given model.");
    
    createStereotype(uuids, docProfile, DOCUMENTATION_INCLUDED, "'Package'", "A sub-package of the top-level documentation package.");
    createStereotype(uuids, docProfile, DOCUMENTATION_FIGURE, "'Comment'", "A figure to reproduce in the documentation.");
    createStereotype(uuids, docProfile, DOCUMENTATION_SEE_ALSO, "'Comment'", "A comment visually containing a set of other documentation packages to refer to.");
    createStereotype(uuids, docProfile, DOCUMENTATION_IMAGE_GALLERY, "'Comment'", "A comment visually containing a set images to display.");

    // make a trace stereotype
    createStereotype(uuids, profile, TRACE, "'Dependency'", "A marker stereotype to indicate that this traces an element from one perspective to another.");

    // make the error stereotype
    Profile stdProfile = (Profile) backbone.createChildPackages(UML2Package.eINSTANCE.getProfile());
    createDependency(uuids, stdProfile, profile);
    setUuid(uuids, stdProfile, "standard-profile");
    stdProfile.setName("standard profile");
    stdProfile.setDocumentation("The profile for a standard jUMbLe model.");
    Stereotype error = createStereotype(uuids, stdProfile, ERROR, "'Class', 'Interface', 'Package', 'Property', 'Operation'", "Indicates that the element is in error.");
    addAttribute(uuids, error, ERROR_DESCRIPTION, stringType, "The explanation of this error.");

    // the model is the top level stratum
    topLevel.getAppliedBasicStereotypes().add(stratum);
    // set destructive stereotype property
		AppliedBasicStereotypeValue value = topLevel.createAppliedBasicStereotypeValues();
		value.setProperty(destructive);
		LiteralBoolean literal = (LiteralBoolean) value.createValue(UML2Package.eINSTANCE.getLiteralBoolean());
		literal.setValue(true);
    
    // make the backbone stratum
    setUuid(uuids, backbone, BACKBONE_STRATUM_NAME);
    backbone.setName(BACKBONE_STRATUM_NAME);
    backbone.setReadOnly(true);
    backbone.getAppliedBasicStereotypes().add(stratum);

    // add some standard definitions
    Package api = backbone.createChildPackages();
    api.getAppliedBasicStereotypes().add(stratum);
    setUuid(uuids, api, "api");
    api.setName("api");
    api.setReadOnly(true);
    createDependency(uuids, api, profile);

    addInterface(uuids, api, iface, "IRun", implClass, "com.intrinsarc.backbone.runtime.api.IRun");
    Interface iCreate = addInterface(uuids, api, iface, "ICreate", implClass, "com.intrinsarc.backbone.runtime.api.ICreate");
    addInterface(uuids, api, iface, "ILifecycle", implClass, "com.intrinsarc.backbone.runtime.api.ILifecycle");
    
    // implementation for the state pattern and other implementations
    Package impls = backbone.createChildPackages();
    impls.getAppliedBasicStereotypes().add(stratum);
    setUuid(uuids, impls, "implementation");
    impls.setName("implementation");
    impls.setReadOnly(true);
    createDependency(uuids, impls, profile);
    createDependency(uuids, impls, api);
    
    // export all the backbone contents
    for (Object o : backbone.undeleted_getChildPackages())
    	createDependency(uuids, backbone, (Package) o);
    
    // make a creator: uuid must be the same as the uuid the factory expander looks for
    Class creator = addLeaf(uuids, impls, component, "Creator", implClass, "com.intrinsarc.backbone.runtime.implementation.Creator");
    addAttribute(uuids, creator, "factoryNumber", intType, "The factory number for this factory");
    addPort(uuids, creator, "create", "create", iCreate, null, null, null);

    // create the state stereotype
    Stereotype stateStereo = createStereotype(uuids, profile, STATE, "'Class'", "Indicates a component is a state.");
    createResemblance(uuids, component, stateStereo).setDocumentation("A state is a type of component.");
    // create some interfaces
    Interface transition = addInterface(uuids, api, iface, "ITransition", implClass, "com.intrinsarc.backbone.runtime.api.ITransition");
    Interface terminal = addInterface(uuids, api, iface, "ITerminal", implClass, "com.intrinsarc.backbone.runtime.api.ITerminal");
    Interface event = addInterface(uuids, api, iface, "IEvent", implClass, "com.intrinsarc.backbone.runtime.api.IEvent");

    // create the base factory
    Class baseFactory = addLeaf(uuids, impls, component, FACTORY_BASE, factory, true);
    baseFactory.setIsAbstract(true);
    Port create = addPort(uuids, baseFactory, "creator", "creator", iCreate, null, null, null);
    create.setKind(PortKind.CREATE_LITERAL);
    
    // create the state classes
    Class state = addLeaf(uuids, impls, stateStereo, STATE_CLASS, implClass, "com.intrinsarc.backbone.runtime.implementation.State");
    state.setIsAbstract(true);
    Port in = addPort(uuids, state, "in", "in", transition, null, null, null);
    Port out = addPort(uuids, state, "out", "out", null, transition, 0, 1);
    Port events = addPort(uuids, state, "events", "events", event, null, null, null);
    // create the start and end terminals
    Class start = addLeaf(uuids, impls, stateStereo, START_STATE_CLASS, implClass, "com.intrinsarc.backbone.runtime.implementation.Terminal");
    createResemblance(uuids, state, start);
    addPort(uuids, start, "startTerminal", "startTerminal", terminal, null, null, null);
    Port startIn = replacePort(uuids, start, in, "in", transition, null, null, null);
    setUseMethods(uuids, stereoPort, suppressMethodGeneration, startIn);
    createPortLink(uuids, start, in, out);
    deltaDeletePort(uuids, start, events);
    Class end = addLeaf(uuids, impls, stateStereo, END_STATE_CLASS, implClass, "com.intrinsarc.backbone.runtime.implementation.Terminal");
    createResemblance(uuids, state, end);
    addPort(uuids, end, "endTerminal", "endTerminal", terminal, null, null, null);
    Port endIn = replacePort(uuids, end, in, "in", transition, null, null, null);
    setUseMethods(uuids, stereoPort, suppressMethodGeneration, endIn);
    createPortLink(uuids, end, in, out);
    deltaDeletePort(uuids, end, events);
    // create the composite state base class
    Class cState = addLeaf(uuids, impls, stateStereo, COMPOSITE_STATE_CLASS, implClass, "");
    createResemblance(uuids, state, cState);
    cState.setIsAbstract(true);
    addPart(uuids, cState, "start", "", start, statePart);
    addPart(uuids, cState, "end", "", end, statePart);
    // create the dispatcher
    Class stateDispatcher = addLeaf(uuids, impls, component, "StateDispatcher", implClass, "com.intrinsarc.backbone.runtime.implementation.StateDispatcher");
    Port dEvents = addPort(uuids, stateDispatcher, "dEvents", "dEvents", event, null, null, null);
    setUseMethods(uuids, stereoPort, suppressMethodGeneration, dEvents);
    Port dDispatch = addPort(uuids, stateDispatcher, "dDispatch", "dDispatch", null, event, 0, -1);
    createPortLink(uuids, stateDispatcher, dEvents, dDispatch);
    addPort(uuids, stateDispatcher, "dStart", "dStart", null, terminal, null, null);
    addPort(uuids, stateDispatcher, "dEnd", "dEnd", null, terminal, 0, -1);
    
    return topLevel;
  }
  
  private Property addPart(Set<String> uuids, Class cls, String uuid, String name, Class type, Class stereo)
	{
    Property part = cls.createOwnedAttribute();
    setUuid(uuids, part, uuid);
    InstanceValue instanceValue = (InstanceValue) part.createDefaultValue(UML2Package.eINSTANCE.getInstanceValue());
    InstanceSpecification instanceSpecification = instanceValue.createOwnedAnonymousInstanceValue();
    instanceValue.setInstance(instanceSpecification);
    part.setName(name);
    part.setType(type);
    if (stereo != null)
    	part.settable_getAppliedBasicStereotypes().add(stereo);
    // parts have composite aggregation kind
    part.setAggregation(AggregationKind.COMPOSITE_LITERAL);
    return part;

	}

	private void deltaDeletePort(Set<String> uuids, Class cls, Port port)
	{
  	DeltaDeletedPort delete = cls.createDeltaDeletedPorts();
  	delete.setDeleted(port);
  	setUniqueUuid(uuids, delete, "delete-" + port.getUuid());
	}

	private Connector createPortLink(Set<String> uuids, Class cls, Port port1, Port port2)
	{
  	Connector c = cls.createOwnedConnector();
  	setUniqueUuid(uuids, c, port1.getUuid() + "-" + port2.getUuid());
		ConnectorEnd end1 = UML2Factory.eINSTANCE.createConnectorEnd();
		end1.setRole(port1);
		c.settable_getEnds().add(end1);
		ConnectorEnd end2 = UML2Factory.eINSTANCE.createConnectorEnd();
		end2.setRole(port2);
		c.settable_getEnds().add(end2);
		c.setKind(ConnectorKind.PORT_LINK_LITERAL);
		return c;
	}

	private Port replacePort(Set<String> uuids, Class cls, Port replaced, String portName, Interface provided, Interface required, Integer lower, Integer upper)
  {
    DeltaReplacedPort repl = cls.createDeltaReplacedPorts();
    repl.setReplaced(replaced);
    Port port = (Port) repl.createReplacement(UML2Package.eINSTANCE.getPort());
    port.setName(portName);
    setUniqueUuid(uuids, port, "replaced-" + portName);
    Class type = (Class) port.createOwnedAnonymousType(UML2Package.eINSTANCE.getClass_());
    type.setName("(port type)");
    port.setType(type);
    if (provided != null)
    {
	    Implementation impl = type.createImplementation();
	  	impl.setRealizingClassifier(type);
	    impl.setContract(provided);
    }
    if (required != null)
    	createDependency(uuids, type, required);
    
    if (lower != null)
    	port.setLowerBound(lower);
    if (upper != null)
    	port.setUpperBound(upper);
    return port;
  }

	private Port addPort(Set<String> uuids, Class cls, String portName, String portUuid, Interface provided, Interface required, Integer lower, Integer upper)
  {
    Port port = cls.createOwnedPort();
    port.setName(portName);
    setUuid(uuids, port, portUuid);
    Class type = (Class) port.createOwnedAnonymousType(UML2Package.eINSTANCE.getClass_());
    type.setName("(port type)");
    port.setType(type);
    if (provided != null)
    {
	    Implementation impl = type.createImplementation();
	  	impl.setRealizingClassifier(type);
	    impl.setContract(provided);
    }
    if (required != null)
    	createDependency(uuids, type, required);
    
    if (lower != null)
    	port.setLowerBound(lower);
    if (upper != null)
    	port.setUpperBound(upper);    
    return port;
  }

  private void setUseMethods(Set<String> uuids, Stereotype stereoPort, Property useMethods, Port port)
	{
    port.settable_getAppliedBasicStereotypes().add(stereoPort);
    AppliedBasicStereotypeValue value = port.createAppliedBasicStereotypeValues();
    setUuid(uuids, value, port.getUuid() + "-" + value.getUuid());
		value.setProperty(useMethods);
		LiteralBoolean literal = (LiteralBoolean) value.createValue(UML2Package.eINSTANCE.getLiteralBoolean());
		literal.setValue(true);
  }

	private Dependency createResemblance(Set<String> uuids, NamedElement base, NamedElement sub)
  {
    Dependency dependency = sub.createOwnedAnonymousDependencies();
    setUuid(uuids, dependency, base.getUuid() + "-" + sub.getUuid());
    dependency.settable_getClients().add(sub);
    dependency.setDependencyTarget(base);
    dependency.setResemblance(true);
    return dependency;
   }

  private void createDependency(Set<String> uuids, NamedElement client, NamedElement target)
  {
    Dependency dep = UML2Factory.eINSTANCE.createDependency();
    setUuid(uuids, dep, client.getUuid() + "-" + target.getUuid());
    dep.setDependencyTarget(target);
    dep.settable_getClients().add(client);
    client.settable_getOwnedAnonymousDependencies().add(dep);
  }
  
  private Type createPrimitiveType(Set<String> uuids, Package pkg, String name)
	{
  	Class type = pkg.createOwnedClass(name, false);
  	type.setComponentKind(ComponentKind.PRIMITIVE_LITERAL);
  	setUuid(uuids, type, name);
  	return type;
	}

	private void assignImplementationType(Type type, String implClassName, Stereotype attributeStereotype, Property attrImplClass)
	{
    type.getAppliedBasicStereotypes().add(attributeStereotype);
    AppliedBasicStereotypeValue applied = type.createAppliedBasicStereotypeValues();
    applied.setProperty(attrImplClass);
    Expression exp = (Expression) applied.createValue(UML2Package.eINSTANCE.getExpression());
    exp.setBody(implClassName);
	}


	private Class addLeaf(Set<String> uuids, Package backbone, Stereotype stereo, String leafName, Property implClass, String value)
  {
    Class leaf = backbone.createOwnedClass(leafName, false);
    leaf.setComponentKind(ComponentKind.NORMAL_LITERAL);
    setUuid(uuids, leaf, leafName);
    if (stereo != null)
    	leaf.getAppliedBasicStereotypes().add(stereo);
    if (implClass != null)
    {
    	AppliedBasicStereotypeValue applied = leaf.createAppliedBasicStereotypeValues();
    	applied.setProperty(implClass);
    	Expression exp = (Expression) applied.createValue(UML2Package.eINSTANCE.getExpression());
    	exp.setBody(value);
    }
    return leaf;
  }

	private Class addLeaf(Set<String> uuids, Package backbone, Stereotype stereo, String leafName, Property implClass, boolean value)
  {
    Class leaf = backbone.createOwnedClass(leafName, false);
    leaf.setComponentKind(ComponentKind.NORMAL_LITERAL);
    setUuid(uuids, leaf, leafName);
    if (stereo != null)
    	leaf.getAppliedBasicStereotypes().add(stereo);
    if (implClass != null)
    {
    	AppliedBasicStereotypeValue applied = leaf.createAppliedBasicStereotypeValues();
    	applied.setProperty(implClass);
    	LiteralBoolean bool = (LiteralBoolean) applied.createValue(UML2Package.eINSTANCE.getLiteralBoolean());
    	bool.setValue(value);
    }
    return leaf;
  }


  private Interface addInterface(Set<String> uuids, Package backbone, Stereotype stereo, String ifaceName, Property implInterface, String value)
  {
    Interface iface = (Interface) backbone.createOwnedMember(UML2Package.eINSTANCE.getInterface());
    setUuid(uuids, iface, ifaceName);
    iface.setName(ifaceName);
    iface.getAppliedBasicStereotypes().add(stereo);
    AppliedBasicStereotypeValue applied = iface.createAppliedBasicStereotypeValues();
    applied.setProperty(implInterface);
    Expression exp = (Expression) applied.createValue(UML2Package.eINSTANCE.getExpression());
    exp.setBody(value);
    return iface;
  }


  private Property addAttribute(Set<String> uuids, Class cls, String name, Type type, String documentation)
	{
  	Property property = cls.createOwnedAttribute();
  	setUuid(uuids, property, name);
  	property.setName(name);
  	property.setType(type);
  	property.setDocumentation(documentation);
  	return property;
	}

	private Stereotype createStereotype(Set<String> uuids, Profile profile, String name, String metaModelElementName, String documentation)
	{
		Stereotype stereo = profile.createOwnedStereotype(name, false);
		setUuid(uuids, stereo, name);
		stereo.setExtendsMetaModelElement(metaModelElementName);
		stereo.setDocumentation(documentation);
		return stereo;
	}


	public Type findPrimitiveType(SubjectRepositoryFacet repository, String name)
  {
    // look for all elements of primitive types first
    Collection all = repository.findElements(name, ClassImpl.class, false);
    for (Object obj : all)
    {
    	if (StereotypeUtilities.isStereotypeApplied((Element) obj, CommonRepositoryFunctions.PRIMITIVE_TYPE) || ((Element) obj).getUuid().equals("String")  || ((Element) obj).getUuid().equals("boolean"))
    		return (Type) obj;
    }
    
    // check the interfaces next
    all = repository.findElements(name, InterfaceImpl.class, false);
    for (Object obj : all)
  		return (Type) obj;
    
    return null;
  }

  public NamedElement findElement(SubjectRepositoryFacet repository, String typeName, java.lang.Class< ? >[] types)
  {
    for (java.lang.Class<?> type : types)
    {
      for (NamedElement element : repository.findElements(typeName, type, false))
        return element;
    }
    return null;
  }

  public Element findOwningElement(FigureReference containingReference, EClass class_)
  {
    return findOwningElement(containingReference, new EClass[]{class_});
  }
  
	public Stereotype findStereotype(SubjectRepositoryFacet repository, EClass ecls, String uuid)
	{
		Set<Stereotype> stereos = repository.findApplicableStereotypes(ecls);
		for (Stereotype stereo : stereos)
			if (stereo.getUuid().equals(uuid) && !stereo.isThisDeleted())
				return stereo;
		return null;
	}


  public Element findOwningElement(FigureReference containingReference, EClass[] classes)
  {
    // use the containing reference to search upwards through the visual hierarchy to
    // find an owner associated with the appropriate subject
    if (containingReference == null)
      return null;
    
    FigureFacet container = GlobalDiagramRegistry.registry.retrieveFigure(containingReference);
    while (container != null)
    {
      Element owner = (Element) container.getSubject();
      if (owner != null && (classes == null || isTypeOrSubtypeOf(owner, classes)))
        return owner;
      if (container.getContainedFacet().getContainer() == null)
        container = null;
      else
        container = container.getContainedFacet().getContainer().getFigureFacet();
    }
    // if we got here it wasn't found
    return null;
  }

  public String getFullStratumNames(Element element)
  {
    // find the topmost stratum if possible, walking up to the top model
    String stratumDetails = "";
    while (element != GlobalSubjectRepository.repository.getTopLevelModel())
    {
      element = UMLTypes.findDefinitiveOwningStratum(element);
      if (element != null)
      {
        stratumDetails = ((NamedElement) element).getName() + (stratumDetails.length() == 0 ? "" : " / " + stratumDetails);
        element = element.getOwner();
      }
      else break;
    }
    return stratumDetails;
  }


  public Package findOwningStratum(Element element)
  {
    Element found = element.getOwner();
    while (found != null && !UMLTypes.isStratum(found))
      found = found.getOwner();
    return (Package) found;
  }
  

  /**
   * checks to see if the figure container hierarchy thinks it is writeable, and the
   * subject ownership hierarchy thinks it is too.
   * if anything says this is readonly, then return true.
   * NOTE: discounts the initial figure...
   * @param figure
   * @return
   */
  public boolean isContainerContextReadOnly(FigureFacet figure)
  {
    // traverse up the hierarchy until we find a subject
    FigureFacet first = figure;
    Object firstSubject = null;
    for (;;)
    {
      if (figure != first && figure.getSubject() != null && firstSubject == null)
        firstSubject = figure.getSubject();
      
      // is this figure's subject considered readonly?
      if (figure != first && figure.isSubjectReadOnlyInDiagramContext(false))
        return true;
      
      if (figure.getContainedFacet() == null)
        break;
      ContainerFacet container = figure.getContainedFacet().getContainer();
      if (container == null)
        break;
      figure = container.getFigureFacet();
    }
    
    // if we have no subject to check, use the diagram
    if (firstSubject == null)
      firstSubject = (Package) figure.getDiagram().getLinkedObject();
    
    // otherwise, check the package hierarchy    
    Element element = (Element) firstSubject;
    return isReadOnly(element);
  }

  public boolean isReadOnly(Element element)
  {
    // traverse up the hierarchy looking for a read-only package marking
    while (element != null)
    {
      if (element instanceof Package && ((Package) element).isReadOnly())
        return true;
      element = element.getOwner();
    }

    return false;
  }

  public static Collection<NamedElement> translateFromSubstitutingToSubstituted(Collection<NamedElement> elements)
  {
    List<NamedElement> translated = new ArrayList<NamedElement>();
    for (NamedElement element : elements)
    {
      NamedElement substitution = UMLTypes.extractSubstitutedClassifier(element); 
      translated.add(substitution == null ? element : substitution);
    }
    return translated;
  }

  public static NamedElement translateFromSubstitutingToSubstituted(NamedElement element)
  {
    NamedElement baseElement = UMLTypes.extractSubstitutedClassifier(element);
    if (baseElement == null)
      return element;
    return baseElement;
  }

	public static String getModelIdentifier(Model topLevel)
	{
		return "Model-" + topLevel.getUuid();
	}

  public static Package findOwningPackage(Element element)
  {
    element = element.getOwner();
    while (element != null && !(element instanceof Package))
      element = element.getOwner();
    return (Package) element;
  }
}
