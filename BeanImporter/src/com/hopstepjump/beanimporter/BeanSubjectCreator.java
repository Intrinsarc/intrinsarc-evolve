package com.hopstepjump.beanimporter;

import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.objectweb.asm.Type;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;


public class BeanSubjectCreator
{
	public static final ImageIcon BEAN_IMPORT_ICON = IconLoader.loadIcon("bean_import.png");
	public static final String MAIN_PORT = "main-port";

	private List<BeanClass> toCreate;
	private org.eclipse.uml2.Package in;
	private BeanFinder finder;
	private LongRunningTaskProgressMonitorFacet monitor;

	private Stereotype componentStereo;
	private Stereotype interfaceStereo;
	private Stereotype primitiveStereo;
	private Stereotype suppressStereo;
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
		suppressStereo = repository.findStereotype(UML2Package.eINSTANCE.getPort(), CommonRepositoryFunctions.VISUALLY_SUPPRESS);
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
			
			// make the ports, attributes, super-leaves and super-interfaces
			createInternals(classes, interfaces);
			// handle any replaced ports
			createInternals2(classes, interfaces);
			// remove any duplicates
			removeDuplicates(classes);
			
			return created;
		}
		finally
		{
			GlobalSubjectRepository.repository.commitTransaction();
		}
	}

	private void removeDuplicates(Map<String, Class> classes)
	{
		DEStratum perspective = GlobalDeltaEngine.engine.locateObject(in).asStratum();
		
		for (BeanClass cls : toCreate)
		{
			if (cls.getType() == BeanTypeEnum.BEAN)
			{
				Class me = classes.get(cls.getNode().name);
				DEComponent comp = GlobalDeltaEngine.engine.locateObject(me).asComponent();
				
				removeDuplicateConstituents(perspective, comp, ConstituentTypeEnum.DELTA_ATTRIBUTE);
				removeDuplicateConstituents(perspective, comp, ConstituentTypeEnum.DELTA_PORT);
			}
		}
	}

	private void removeDuplicateConstituents(DEStratum perspective, DEComponent comp, ConstituentTypeEnum type)
	{
		// get the names that existed before this
		Set<String> existingNames = new HashSet<String>();
		for (DeltaPair pair : comp.getDeltas(type).getConstituents(perspective))
		{
			if (pair.getConstituent().getParent() != comp)
				existingNames.add(pair.getConstituent().getName());
		}
		
		// if we have these now, then remove
		for (DeltaPair add : comp.getDeltas(type).getAddObjects())
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
				me.settable_getOwnedAnonymousDependencies().removeAll(existing);
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
				me.settable_getOwnedAnonymousDependencies().removeAll(existing);
				
				// handle any attributes
				List<Property> existingAttrs = me.undeleted_getOwnedAttributes(); 
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
					possiblyVisuallySuppress(field, attr);
				}
				me.settable_getOwnedAttributes().removeAll(existingAttrs);
				
				// handle any ports
				List<Port> existingPorts = me.undeleted_getOwnedPorts(); 
				for (BeanField field : cls.getPorts())
				{
					Port port= extractPort(existingPorts, field.getName());
					if (port != null)
						existingPorts.remove(port);

					// handle replacing main ports differently
					if (field.isMain() && cls.getSuperClass() != null)
						continue;
					
					if (port == null)
						port = me.createOwnedPort();
					setUpPort(interfaces, field, port);
				}
			}
		}		
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
			{
				System.out.println("$$ found existing attribute " + a);
				return a;				
			}
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
		for (BeanClass cls : graph.makeTopologicalSort())
		{
			Class me = classes.get(cls.getNode().name);
			
			// handle any ports
			for (BeanField field : cls.getPorts())
			{
				// if this is a main, and we have a super, then do a replace
				if (field.isMain() && cls.getSuperClass() != null)
				{						
					// get the replacement element
					Class other = finder.findClass(classes, cls.getSuperClass());
					// find the main port so we can replace
					boolean found = false;
					for (Object obj : other.getOwnedPorts())
					{
						Port p = (Port) obj;
						if (p.getUuid().equals(MAIN_PORT))
						{
							DeltaReplacedPort replace = me.createDeltaReplacedPorts();
							Port port = (Port) replace.createReplacement(UML2Package.eINSTANCE.getPort());
							replace.setReplaced(p);
							setUpPort(interfaces, field, port);
							found = true;
							break;
						}
					}
					if (!found)
					{
						for (Object obj : other.getDeltaReplacedPorts())
						{
							DeltaReplacedPort repl = (DeltaReplacedPort) obj;
							if (repl.getReplaced().getUuid().equals(MAIN_PORT))
							{
								DeltaReplacedPort replace = me.createDeltaReplacedPorts();
								Port port = (Port) replace.createReplacement(UML2Package.eINSTANCE.getPort());
								replace.setReplaced(repl.getReplaced());
								setUpPort(interfaces, field, port);
								found = true;
								break;
							}
						}
					}
					
					if (!found)
					{
						Port p = me.createOwnedPort();
						setUpPort(interfaces, field, p);
					}
				}
			}
		}
	}

	private void possiblyVisuallySuppress(BeanField field, Element element)
	{
		if (field.isVisuallySuppress())
			element.settable_getAppliedBasicStereotypes().add(suppressStereo);
	}
	
	private void setUpPort(Map<String, Interface> interfaces, BeanField field, Port port)
	{
		if (field.isMain())
			port.setUuid(MAIN_PORT);
		possiblyVisuallySuppress(field, port);
		port.setName(field.getName());
		Class type = (Class) port.getOwnedAnonymousType();
		if (type == null)
			type = (Class) port.createOwnedAnonymousType(UML2Package.eINSTANCE.getClass_());
		type.setName("(port type)");
		port.setType(type);
		
		// if this is many, set the lower and upper bounds
		// and if it is required (i.e. writable) then set it at [0..1]
		port.setLowerValue(null);
		port.setUpperValue(null);
		if (field.isMany())
		{
			port.setLowerBound(0);
			port.setUpperBound(-1);
		}
		else
		if (!field.isReadOnly())
		{
			port.setLowerBound(0);
			port.setUpperBound(1);
		}
		
		List<Dependency> existingDeps = port.undeleted_getOwnedAnonymousDependencies();
		List<Implementation> existingImpls = type.undeleted_getImplementations();
		for (Type fieldType : field.getTypes())
		{
			String className = fieldType.getClassName();
			Interface elem = finder.findInterface(interfaces, className);

			// add the readonly interfaces as provided
		  if (field.isReadOnly())
		  {
		  	Implementation implementation = extractImplementation(existingImpls, type);
		  	if (implementation != null)
		  		existingImpls.remove(implementation);
		  	else
		  		implementation = type.createImplementation();
		  	implementation.setRealizingClassifier(type);
		  	implementation.setContract(elem);
		  }
		  else
		  {
		  	Dependency dep = extractDependency(existingDeps, elem); 
		  	if (dep != null)
		  		existingDeps.remove(dep);
		  	else
		  		dep = type.createOwnedAnonymousDependencies();
		    dep.settable_getClients().clear();
		    dep.settable_getClients().add(type);
		    dep.setDependencyTarget(elem);
		  }
		}
		type.settable_getImplementations().removeAll(existingImpls);
		port.settable_getOwnedAnonymousDependencies().removeAll(existingDeps);
		
		// add any needed stereotype properties
		setUpBeanPortStereotype(field, port);
	}

	private List<BeanClass> getDependedUponInterfaces(List<BeanClass> toCreate, BeanClass cls)
	{
		List<BeanClass> ret = new ArrayList<BeanClass>();
		for (BeanClass create : toCreate)
		{
			if (create.getType() == BeanTypeEnum.INTERFACE)
			{
				for (String str : cls.getInterfaces())
					if (create.getNode().name.equals(str))
					{
						ret.add(create);
						break;
					}
			}
		}
		return ret;
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
		iface.settable_getAppliedBasicStereotypeValues().clear();
		iface.settable_getAppliedBasicStereotypes().add(interfaceStereo);
		setImplementation(iface, cls.getNode().name);
		setBooleanProperty(iface, CommonRepositoryFunctions.BEAN);
		interfaces.put(cls.getNode().name, iface);
	}

	private void createLeaf(Map<String, Class> classes, BeanClass cls)
	{
		Class cl = finder.getRefreshedClass(cls);
		if (cl == null)
			cl = in.createOwnedClass(cls.getName(), cls.isAbstract());
		cl.setComponentKind(ComponentKind.NORMAL_LITERAL);
		cl.settable_getAppliedBasicStereotypes().clear();
		cl.settable_getAppliedBasicStereotypeValues().clear();
		cl.settable_getAppliedBasicStereotypes().add(componentStereo);
		setImplementation(cl, cls.getNode().name);
		setBooleanProperty(cl, CommonRepositoryFunctions.BEAN);
		classes.put(cls.getNode().name, cl);
	}

	private void createPrimitive(Map<String, Class> classes, BeanClass cls)
	{
		Class cl = finder.getRefreshedClass(cls);
		if (finder == null)
			cl = in.createOwnedClass(cls.getName(), cls.isAbstract());
		cl.setComponentKind(ComponentKind.PRIMITIVE_LITERAL);
		cl.settable_getAppliedBasicStereotypes().clear();
		cl.settable_getAppliedBasicStereotypeValues().clear();
		cl.settable_getAppliedBasicStereotypes().add(primitiveStereo);
		setImplementation(cl, cls.getNode().name);
		classes.put(cls.getNode().name, cl);
	}

	private void setUpBeanPortStereotype(BeanField field, Port port)
	{
		if (!field.isNoName() && !field.isMain())
			return;
		port.settable_getAppliedBasicStereotypes().add(portStereo);
		if (field.isMain())
			setBooleanProperty(port, CommonRepositoryFunctions.PORT_BEAN_MAIN);
		if (field.isNoName())
		{
			setBooleanProperty(port, CommonRepositoryFunctions.PORT_BEAN_NO_NAME);
			port.setIsOrdered(true);
		}
	}

	private void setBooleanProperty(Element cl, String uuid)
	{
		AppliedBasicStereotypeValue value = cl.createAppliedBasicStereotypeValues();
    final Property property =
    	(Property) StereotypeUtilities.findAllStereotypePropertiesFromRawAppliedStereotypes(cl).get(uuid).getConstituent().getRepositoryObject();
    if (property == null)
      return;

		value.setProperty(property);
		LiteralBoolean literal = (LiteralBoolean) value.createValue(UML2Package.eINSTANCE.getLiteralBoolean());
		literal.setValue(true);
	}

	private void setStringProperty(Element cl, String uuid, String str)
	{
		AppliedBasicStereotypeValue value = cl.createAppliedBasicStereotypeValues();
    final Property property =
    	(Property) StereotypeUtilities.findAllStereotypePropertiesFromRawAppliedStereotypes(cl).get(uuid).getConstituent().getRepositoryObject();
    if (property == null)
      return;

		value.setProperty(property);
		Expression literal = (Expression) value.createValue(UML2Package.eINSTANCE.getExpression());
		literal.setBody(str);
	}

	private void setImplementation(Element cl, String className)
	{
		AppliedBasicStereotypeValue value = cl.createAppliedBasicStereotypeValues();
    final Property property =
    	(Property) StereotypeUtilities.findAllStereotypePropertiesFromRawAppliedStereotypes(cl).get(CommonRepositoryFunctions.IMPLEMENTATION_CLASS).getConstituent().getRepositoryObject();
    if (property == null)
      return;

		value.setProperty(property);
		Expression literal = (Expression) value.createValue(UML2Package.eINSTANCE.getExpression());
		literal.setBody(className);
	}
}
