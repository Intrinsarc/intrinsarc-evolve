package com.intrinsarc.beanimporter;

import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.objectweb.asm.Type;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;
import com.intrinsarc.uml2deltaengine.*;


public class BeanSubjectCreator
{
	public static final ImageIcon BEAN_IMPORT_ICON = IconLoader.loadIcon("bean_import.png");

	private List<BeanClass> toCreate;
	private org.eclipse.uml2.Package in;
	private BeanFinder finder;
	private LongRunningTaskProgressMonitorFacet monitor;

	private Stereotype componentStereo;
	private Stereotype interfaceStereo;
	private Stereotype primitiveStereo;
	private Stereotype portStereo;
	
	public BeanSubjectCreator(List<BeanClass> toCreate, org.eclipse.uml2.Package in, BeanFinder finder, LongRunningTaskProgressMonitorFacet monitor)
	{
		this.toCreate = toCreate;
		this.in = in;
		this.finder = finder;
		this.monitor = monitor;	
		
		// get the needed stereotypes
		SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
		componentStereo = repository.findStereotype(UML2Package.eINSTANCE.getClass_(), CommonRepositoryFunctions.COMPONENT);
		interfaceStereo = repository.findStereotype(UML2Package.eINSTANCE.getInterface(), CommonRepositoryFunctions.INTERFACE);
		primitiveStereo = repository.findStereotype(UML2Package.eINSTANCE.getClass_(), CommonRepositoryFunctions.PRIMITIVE_TYPE);
		portStereo = repository.findStereotype(UML2Package.eINSTANCE.getPort(), CommonRepositoryFunctions.PORT);
	}
	
	public BeanCreatedSubjects createSubjects()
	{
		GlobalSubjectRepository.repository.startTransaction("created beans", "removed beans");
		try
		{
			Map<String, Class> classes = new HashMap<String, Class>();
			Map<String, Interface> interfaces = new HashMap<String, Interface>();
			
			BeanCreatedSubjects created = createOutlines(classes, interfaces);
			
			// make the attributes, super-leaves and super-interfaces
			createInternals(classes, interfaces);
			// handle any ports
			createInternals2(classes, interfaces);
			
			// remove any duplicates
			removeDuplicates(classes);
			removeRedundantRequireds(classes);
			return created;
		}
		finally
		{
			GlobalSubjectRepository.repository.commitTransaction();
		}
	}
	
	private void removeDuplicates(Map<String, Class> classes)
	{
		// need to clear this out to pick up new constituents just made
		GlobalDeltaEngine.engine = new UML2DeltaEngine();
		DEStratum perspective = GlobalDeltaEngine.engine.locateObject(in).asStratum();
		
		for (BeanClass cls : toCreate)
		{
			if (cls.getType() == BeanTypeEnum.BEAN)
			{
				Class me = classes.get(cls.getNode().name);
				DEComponent comp = GlobalDeltaEngine.engine.locateObject(me).asComponent();
				
				removeDuplicateConstituents(perspective, comp);
			}
		}
	}

	private void removeDuplicateConstituents(DEStratum perspective, DEComponent comp)
	{
		// get the names that existed before this
		Set<String> existingNames = new HashSet<String>();
		for (DeltaPair pair : comp.getDeltas(ConstituentTypeEnum.DELTA_PORT).getConstituents(perspective))
		{
			if (pair.getConstituent().getParent() != comp)
				existingNames.add(pair.getConstituent().getName());
		}
		for (DeltaPair pair : comp.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getConstituents(perspective))
		{
			if (pair.getConstituent().getParent() != comp)
				existingNames.add(pair.getConstituent().getName());
		}
		
		// if we have these now, then remove
		for (DeltaPair add : comp.getDeltas(ConstituentTypeEnum.DELTA_PORT).getAddObjects())
		{
			if (existingNames.contains(add.getConstituent().getName()))
				GlobalSubjectRepository.repository.incrementPersistentDelete((Element) add.getConstituent().getRepositoryObject());
		}
		for (DeltaPair add : comp.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getAddObjects())
		{
			if (existingNames.contains(add.getConstituent().getName()))
				GlobalSubjectRepository.repository.incrementPersistentDelete((Element) add.getConstituent().getRepositoryObject());
		}
	}

	private void createInternals(Map<String, Class> classes, Map<String, Interface> interfaces)
	{
		for (BeanClass cls : toCreate)
		{
			if (cls.getType() == BeanTypeEnum.INTERFACE)
			{
				Interface me = interfaces.get(cls.getNode().name);
				
				List<Dependency> existing = me.undeleted_getOwnedAnonymousDependencies();
				for (String i : cls.getInterfaces())
				{
		      Interface other = finder.findInterface(interfaces, i);
		      Dependency exist = extractDependency(existing, other);
		      if (exist != null)
		      	existing.remove(exist);
		      else
		      {
						Dependency res = me.createOwnedAnonymousDependencies();
						res.setResemblance(true);
			      res.settable_getClients().add(me);
						res.setDependencyTarget(other);
		      }
				}
				delete(existing);
			}
			if (cls.getType() == BeanTypeEnum.BEAN)
			{
				Class me = classes.get(cls.getNode().name);

				// handle any super-beans
				String sup = cls.getSuperClass();
				List<Dependency> existing = me.undeleted_getOwnedAnonymousDependencies();				
				if (sup != null)
				{
					Class other = finder.findClass(classes, sup);
		      Dependency exist = extractDependency(existing, other);
		      if (exist != null)
		      	existing.remove(exist);
		      else
		      {
		      	Dependency res = me.createOwnedAnonymousDependencies();
		      	res.setResemblance(true);
		      	res.settable_getClients().add(me);
		      	res.setDependencyTarget(other);
		      }
				}
				delete(existing);
				
				// handle any attributes
				List<Property> existingAttrs = collectExistingAttributes(me);
				for (BeanField field : cls.getAttributes())
				{
					String className = field.getTypes().get(0).getClassName();
					org.eclipse.uml2.Type elem = finder.findClass(classes, className);
					if (elem == null)
						elem = finder.findInterface(interfaces, className);
					
					Property attr = extractAttribute(existingAttrs, field.getName());
					if (attr != null)
						existingAttrs.remove(attr);
					else
						attr = me.createOwnedAttribute();
					attr.setName(field.getName());
					attr.setType(elem);
					if (field.isWriteOnly())
						attr.setReadWrite(PropertyAccessKind.WRITE_ONLY_LITERAL);
					if (field.isReadOnly())
						attr.setReadWrite(PropertyAccessKind.READ_ONLY_LITERAL);
					
					// handle lower and upper
					if (!field.isMany() && attr.getUpperValue() != null && (attr.getUpper() > 1 || attr.getUpper() < 0))
					{
						attr.setUpperValue(null);
						attr.setLowerValue(null);
					}
					if (field.isMany() && (attr.getUpperValue() == null || attr.getUpper() <= 1))
					{
						attr.setLowerBound(0);
						attr.setUpperBound(-1);
					}
				}
				delete(existingAttrs);
			}
		}		
	}

	private List<Property> collectExistingAttributes(Class me)
	{
		List<Property> attrs = new ArrayList<Property>();
		for (Object obj : me.undeleted_getOwnedAttributes())
			attrs.add((Property) obj);
		for (Object obj : me.undeleted_getDeltaReplacedAttributes())
			attrs.add((Property) ((DeltaReplacedAttribute) obj).getReplacement());
		return attrs;
	}

	private List<Port> collectExistingPorts(Class me)
	{
		List<Port> ports = new ArrayList<Port>();
		for (Object obj : me.undeleted_getOwnedPorts())
			ports.add((Port) obj);
		for (Object obj : me.undeleted_getDeltaReplacedPorts())
			ports.add((Port) ((DeltaReplacedPort) obj).getReplacement());
		return ports;
	}

	private void delete(List<? extends Element> existing)
	{
		for (Element exist : existing)
			GlobalSubjectRepository.repository.incrementPersistentDelete(exist);
	}

	/**
	 * functions to find existing ports, attributes and resemblances
	 */
	private Port extractPort(List<Port> existing, String name)
	{
		for (Port p : existing)
			if (p.getName().equals(name))				
				return p;
		return null;
	}

	private Property extractAttribute(List<Property> existing, String name)
	{
		for (Property a : existing)
			if (a.getName().equals(name))
				return a;				
		return null;
	}

	private Dependency extractDependency(List<Dependency> existing, Element other)
	{
		for (Dependency d : existing)
			if (d.getDependencyTarget() == other)
				return d;
		return null;
	}

	private Implementation extractImplementation(List<Implementation> existing, Element other)
	{
		for (Implementation i : existing)
			if (i.getContract() == other)
				return i;
		return null;
	}
	/////////////////////////////////////////////

	private void createInternals2(Map<String, Class> classes, Map<String, Interface> interfaces)
	{
		SimpleDirectedGraph<BeanClass> graph = new SimpleDirectedGraph<BeanClass>();
		for (BeanClass cls : toCreate)
			if (cls.getType() == BeanTypeEnum.BEAN)
				graph.addNode(cls);
		
		// add any superclass relationships
		for (BeanClass cls : toCreate)
			if (cls.getType() == BeanTypeEnum.BEAN && cls.getSuperClass() != null)
			{
				BeanClass other = finder.locatePossibleBeanClass(cls.getSuperClass(), false);
				if (other != null)
					graph.addEdge(cls, other);
			}

		// now we can process in order, knowing that any superclass has been processed before us
		for (BeanClass cls : graph.makeTopologicalSort(true))
		{
			Class me = classes.get(cls.getNode().name);
			
			// handle any ports
			List<Port> existingPorts = collectExistingPorts(me);
			for (BeanField field : cls.getPorts())
			{
				Port port = extractPort(existingPorts, field.getName());
				if (port != null)
					existingPorts.remove(port);

				// handle replacing main ports differently
				if (field.isMain() && cls.getSuperClass() != null && port == null)
				{
					Class superc = finder.findClass(classes, cls.getSuperClass());
					
					// we want to replace a main from the subclass
					DeltaReplacedPort	delta = me.createDeltaReplacedPorts();
					Port newMain = (Port) delta.createReplacement(UML2Package.eINSTANCE.getPort());
					setUpPort(cls, interfaces, field, newMain);
					delta.setReplaced(findOriginalMain(superc));
				}
				else
				{
					if (port == null)
						port = me.createOwnedPort();
					setUpPort(cls, interfaces, field, port);
				}
			}
			delete(existingPorts);
		}
	}

	private boolean isMain(Port port)
	{
		return StereotypeUtilities.extractBooleanProperty(port, CommonRepositoryFunctions.PORT_BEAN_MAIN);
	}

	private Port findOriginalMain(Class me)
	{
		for (Object p : me.undeleted_getDeltaReplacedPorts())
		{
			DeltaReplacedPort delta = (DeltaReplacedPort) p; 
			if (isMain((Port) delta.getReplacement()))
			return (Port) delta.getReplaced();
		}
		for (Object p : me.undeleted_getOwnedPorts())
		{
			Port port = (Port) p;
			if (isMain(port))
				return port;
		}
		return null;
	}

	private void setUpPort(BeanClass cls, Map<String, Interface> interfaces, BeanField field, Port port)
	{
		port.setName(field.getName());
		Class type = (Class) port.getOwnedAnonymousType();
		if (type == null)
			type = (Class) port.createOwnedAnonymousType(UML2Package.eINSTANCE.getClass_());
		type.setName("(port type)");
		port.setType(type);
		
		// if this is many, set the lower and upper bounds
		// and if it is required (i.e. writable) then set it at [0..1]
		if (field.isMany())
		{
			if (port.getLowerValue() == null)
				port.setLowerBound(0);
			if (port.getUpper() <= 1)
				port.setUpperBound(-1);
		}
		else
		{
			if (port.getUpper() > 1)
				port.setUpperBound(1);
		}
		
		List<Dependency> existingDeps = type.undeleted_getOwnedAnonymousDependencies();
		List<Implementation> existingImpls = type.undeleted_getImplementations();
		for (int lp = 0; lp < 2; lp++)
			for (Type fieldType : lp == 0 ? field.getProvidedTypes() : field.getRequiredTypes())
			{
				String className = fieldType.getClassName();
				Interface elem = finder.findInterface(interfaces, className);
	
				// add the readonly interfaces as provided
			  if (lp == 0)
			  {
			  	Implementation implementation = extractImplementation(existingImpls, elem);
			  	if (implementation != null)
			  	{
			  		existingImpls.remove(implementation);
			  	}
			  	else
			  		implementation = type.createImplementation();
			  	implementation.setRealizingClassifier(type);
			  	implementation.setContract(elem);
			  }
			  else
			  {
			  	Dependency dep = extractDependency(existingDeps, elem); 
			  	if (dep != null)
			  	{
			  		existingDeps.remove(dep);
			  	}
			  	else
			  		dep = type.createOwnedAnonymousDependencies();
			    dep.settable_getClients().clear();
			    dep.settable_getClients().add(type);
			    dep.setDependencyTarget(elem);
			  }
			}
		delete(existingImpls);
		delete(existingDeps);
		
		// add any needed stereotype properties
		setUpBeanPortStereotype(cls, field, port);
	}

	private void removeRedundantRequireds(Map<String, Class> classes)
	{
		// with legacy beans, it is possible to have setXXX(Foo) and setXXX(Bar) which make it look like the port has multiple required interfaces
		// remove all but one of these to make the bean valid again, just in case they overlap
		// this is fairly safe, as it is illegal for a legacy bean port to have multiple requireds and this is very rare
		for (BeanClass create : toCreate)
			if (create.isLegacyBean() && create.getType() == BeanTypeEnum.BEAN)
			{
				Class me = classes.get(create.getNode().name);
				for (Object po : me.undeleted_getOwnedPorts())
				{
					Port port = (Port) po;
					int lp = 0;
					if (port.getType() != null)
					for (Object depo : port.getType().undeleted_getOwnedAnonymousDependencies())
					{
						Dependency dep = (Dependency) depo;
						if (dep.undeleted_getDependencyTarget() instanceof Interface)
						{
							if (lp++ != 0)
								GlobalSubjectRepository.repository.incrementPersistentDelete(dep);
						}
					}
				}
			}
	}

	private BeanCreatedSubjects createOutlines(Map<String, Class> classes, Map<String, Interface> interfaces)
	{
		// keep track of what has been created
		BeanCreatedSubjects created = new BeanCreatedSubjects();
		
		// make the outlines
		for (BeanClass cls : toCreate)
		{
			if (cls.getType() == BeanTypeEnum.PRIMITIVE)
			{
				createPrimitive(classes, cls);
				created.bumpMadePrimitives();
			}
			else
			if (cls.getType() == BeanTypeEnum.BEAN)
			{
				createLeaf(classes, cls);
				created.bumpMadeLeaves();
			}
			else
			if (cls.getType() == BeanTypeEnum.INTERFACE)
			{
				createInterface(interfaces, cls);
				if (cls.isInterfaceForBean())
					created.bumpMadeLeafInterfaces();
				else
					created.bumpMadeInterfaces();
			}
			
			// report every 50 made
			if (created.getTotalMade() % 50 == 0)
				monitor.displayInterimPopup(BEAN_IMPORT_ICON, "Importing...", "Created " + (created.getTotalMade()) + " outlines", null, -1);
		}
		return created;
	}

	private void createInterface(Map<String, Interface> interfaces, BeanClass cls)
	{
		String name = cls.getName();
		Interface iface = finder.getRefreshedInterface(cls);
		if (iface == null)
			iface = (Interface) in.createOwnedMember(UML2Package.eINSTANCE.getInterface());
		iface.setName(name);
		iface.settable_getAppliedBasicStereotypes().clear();
		iface.settable_getAppliedBasicStereotypes().add(interfaceStereo);
		setImplementation(iface, cls.getNode().name);
		interfaces.put(cls.getNode().name, iface);
	}

	private void createLeaf(Map<String, Class> classes, BeanClass cls)
	{
		Class cl = finder.getRefreshedClass(cls);
		if (cl == null)
		{
			cl = in.createOwnedClass(cls.getName(), cls.isAbstract());
		}
		cl.setComponentKind(ComponentKind.NORMAL_LITERAL);
		cl.settable_getAppliedBasicStereotypes().clear();
		cl.settable_getAppliedBasicStereotypes().add(componentStereo);
		setBooleanProperty(cl, CommonRepositoryFunctions.LEGACY_BEAN, cls.isLegacyBean());
		setBooleanProperty(cl, CommonRepositoryFunctions.LIFECYCLE_CALLBACKS, cls.hasLifecycleCallbacks());
		setImplementation(cl, cls.getNode().name);
		classes.put(cls.getNode().name, cl);
	}

	private void createPrimitive(Map<String, Class> classes, BeanClass cls)
	{
		Class cl = finder.getRefreshedClass(cls);
		if (cl == null)
			cl = in.createOwnedClass(cls.getName(), cls.isAbstract());
		cl.setComponentKind(ComponentKind.PRIMITIVE_LITERAL);
		cl.settable_getAppliedBasicStereotypes().clear();
		cl.settable_getAppliedBasicStereotypes().add(primitiveStereo);
		setImplementation(cl, cls.getNode().name);
		classes.put(cls.getNode().name, cl);
	}

	private void setUpBeanPortStereotype(BeanClass cls, BeanField field, Port port)
	{
		port.settable_getAppliedBasicStereotypes().clear();
		port.settable_getAppliedBasicStereotypes().add(portStereo);
		setBooleanProperty(port, CommonRepositoryFunctions.PORT_BEAN_MAIN, cls.isLegacyBean() && field.isMain());
		setBooleanProperty(port, CommonRepositoryFunctions.PORT_BEAN_NO_NAME, field.isNoName());
	}

	private void setBooleanProperty(Element cl, String uuid, boolean on)
	{
		DeltaPair pair = StereotypeUtilities.findAllStereotypePropertiesFromRawAppliedStereotypes(cl).get(uuid);
    final Property property =
    	pair != null ? (Property) pair.getConstituent().getRepositoryObject() : null;
    if (property == null)
      return;

    AppliedBasicStereotypeValue value = findOrCreateAppliedValue(cl, property, !on);
    if (!on)
    {
    	if (value != null)
    		GlobalSubjectRepository.repository.incrementPersistentDelete(value);
    }
    else
    {
    	LiteralBoolean literal = (LiteralBoolean) value.createValue(UML2Package.eINSTANCE.getLiteralBoolean());
    	literal.setValue(true);
    }
	}

	private void setImplementation(Element cl, String className)
	{
		// what is the auto implementation?
		DEElement elem = GlobalDeltaEngine.engine.locateObject(cl).asElement();
		String auto = elem.getAutoImplementationClass(null);
		boolean same = className.equals(auto);

		DeltaPair pair = StereotypeUtilities.findAllStereotypePropertiesFromRawAppliedStereotypes(cl).get(CommonRepositoryFunctions.IMPLEMENTATION_CLASS);
    final Property property =
    	pair != null ? (Property) pair.getConstituent().getRepositoryObject() : null;
    if (property == null)
      return;
		AppliedBasicStereotypeValue value = findOrCreateAppliedValue(cl, property, same);
		
		// is this the same?
		if (same)
		{
			if (value != null)
				GlobalSubjectRepository.repository.incrementPersistentDelete(value);
			return;
		}
		Expression literal = (Expression) value.createValue(UML2Package.eINSTANCE.getExpression());
		literal.setBody(className);
	}

	private AppliedBasicStereotypeValue findOrCreateAppliedValue(Element cl, Property property, boolean findOnly)
	{
		for (Object obj : cl.undeleted_getAppliedBasicStereotypeValues())
		{
			AppliedBasicStereotypeValue val = (AppliedBasicStereotypeValue) obj;
			if (val.getProperty() == property)
			{
				return val;
			}
		}
		if (findOnly)
			return null;
		AppliedBasicStereotypeValue val = cl.createAppliedBasicStereotypeValues();
		val.setProperty(property);
		return val;
	}
}
