package com.hopstepjump.beanimporter;

import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.swing.*;

public class BeanFinder
{
	public static final ImageIcon BEAN_ADD_ICON = IconLoader.loadIcon("bean_add.png");
//	private Collection<BeanPackage> pkgs;
	private DEStratum stratum;
	private LongRunningTaskProgressMonitorFacet monitor;
	// all Strings are dotted names
	Map<String, Interface> ifaces = new HashMap<String, Interface>();
	Map<String, Class> classes = new HashMap<String, Class>();
	Map<String, Interface> cifaces = new HashMap<String, Interface>();
	Map<String, Class> cclasses = new HashMap<String, Class>();
	Map<String, BeanClass> ifacesP = new HashMap<String, BeanClass>();
	Map<String, BeanClass> classesP = new HashMap<String, BeanClass>();

	public BeanFinder(DEStratum stratum, LongRunningTaskProgressMonitorFacet monitor)
	{
		this.stratum = stratum;
		this.monitor = monitor;
	}

	public void findBeans()
	{
		// get all existing interfaces and classes in the repository
		Set<? extends DEStratum> canSee = stratum.getCanSee();
		int count = 0;
		for (DEStratum see : canSee)
		{
			for (DEElement elem : see.getChildElements())
			{
				if (elem.asComponent() != null && !elem.isSubstitution())
				{
					String impl = elem.getImplementationClass(stratum);
					if (impl != null && impl.length() > 0)
					{
						classes.put(impl, (Class) elem.getRepositoryObject());
					}
				}

				if (elem.asInterface() != null && !elem.isSubstitution())
				{
					String impl = elem.getImplementationClass(stratum);
					if (impl != null && impl.length() > 0)
					{
						ifaces.put(impl, (Interface) elem.getRepositoryObject());
					}
				}
			}
			if (count++ % 50 == 0)
				monitor.displayInterimPopup(BEAN_ADD_ICON,
						"Analyzing Existing Repository...", "Examined " + (count - 1)
								+ " existing packages", null, -1);			
		}
		
		for (DEElement elem : stratum.getChildElements())
		{
			if (elem.asComponent() != null && !elem.isSubstitution())
			{
				String impl = elem.getImplementationClass(stratum);
				if (impl != null && impl.length() > 0)
				{
					cclasses.put(impl, (Class) elem.getRepositoryObject());
				}
			}

			if (elem.asInterface() != null && !elem.isSubstitution())
			{
				String impl = elem.getImplementationClass(stratum);
				if (impl != null && impl.length() > 0)
				{
					cifaces.put(impl, (Interface) elem.getRepositoryObject());
				}
			}
		}
	}

		
	public void examineBeanPackages(Collection<BeanPackage> pkgs)
	{
		// get all the existing interfaces and classes in the package list
		int cnt[] = new int[]
			{ 0 };
		for (BeanPackage pkg : pkgs)
			collectAll(pkg, cnt);
	}

	private void collectAll(BeanPackage pkg, int[] cnt)
	{
		for (BeanClass cls : pkg.getBeanClasses(true))
		{
			if (cls.getType() == BeanTypeEnum.INTERFACE)
				ifacesP.put(cls.getNode().name, cls);
			else
				classesP.put(cls.getNode().name, cls);
		}
		if (cnt[0]++ % 250 == 0)
			monitor.displayInterimPopup(BEAN_ADD_ICON,
					"Analyzing Imported Packages...", "Examined " + (cnt[0] - 1)
							+ " imported packages", null, -1);
		for (BeanPackage sub : pkg.getChildren())
			collectAll(sub, cnt);
	}

	/**
	 * finds the synthetic IXXX... element constructed as the bean interface
	 **/
	public String makeInterfaceTypeName(String className)
	{
		String iface = PrimitiveHelper.translateShortToLongPrimitive(className);

		// look in existing elements
		NamedElement elem = ifaces.get(iface);
		if (elem == null)
			elem = classes.get(iface);
		if (elem != null)
			return GlobalDeltaEngine.engine.locateObject(elem).asElement().getName(stratum);
		
		// otherwise, we need to move it into our import list
		BeanClass imp = ifacesP.get(iface);
		if (imp == null)
			imp = classesP.get(iface);

		if (imp != null)
			return imp.getName();
		return "<??>";
	}
	
	public String makeClassTypeName(String className)
	{
		String cls = PrimitiveHelper.translateShortToLongPrimitive(className);

		// look in existing elements
		NamedElement elem = classes.get(cls);
		if (elem == null)
			elem = ifaces.get(cls);
		if (elem != null)
			return GlobalDeltaEngine.engine.locateObject(elem).asElement().getName(stratum);
		
		// otherwise, we need to move it into our import list
		BeanClass imp = classesP.get(cls);
		if (imp == null)
			imp = ifacesP.get(cls);

		if (imp != null)
			return imp.getName();
		return "<??>";
	}
	
	public void constructInterfaces(List<BeanClass> all, BeanClass bean)
	{
		for (String i : bean.getInterfacesNeeded())
		{
			String iface = PrimitiveHelper.translateShortToLongPrimitive(i);

			// look for an interface first
			boolean found = false;
			for (BeanClass a : all)
			{
				if (a.getType() == BeanTypeEnum.INTERFACE && a.getNode().name.equals(iface))
				{
					found = true;
					a.addWantsThis(bean);
					break;
				}
			}
			if (found)
				continue;
			
			// look in ifaces
			if (ifaces.containsKey(iface))
				continue; // we have it in the repository -- don't go further

			// otherwise, we need to move it into our import list
			BeanClass imp = ifacesP.get(iface);
			if (imp != null)
			{
				all.add(imp);
				imp.setSynthetic(true);
				imp.addWantsThis(bean);
			}
			else
			{
				// otherwise, we need to make one, in error
				ClassNode errorNode = new ClassNode();
				errorNode.name = iface;
				imp = new BeanClass(errorNode, BeanTypeEnum.INTERFACE);
				imp.setError("Interface " + iface + " cannot be found.  First required by " + bean.getNode().name);
				all.add(imp);
				imp.setSynthetic(true);
				imp.addWantsThis(bean);
			}
		}
	}

	public void constructPrimitives(List<BeanClass> all, BeanClass bean)
	{
		for (String p : bean.getPrimitivesNeeded())
		{
			String prim = PrimitiveHelper.translateShortToLongPrimitive(p);
			
			// look for a class first
			boolean found = false;
			for (BeanClass a : all)
			{
				if ((a.getType() == BeanTypeEnum.PRIMITIVE || a.isBean()) && a.getNode().name.equals(prim))
				{
					found = true;
					a.addWantsThis(bean);
					break;
				}
			}
			if (found)
				continue;
			
			// look for anything next -- e.g. an interface is possible
			for (BeanClass a : all)
			{
				if (a.getNode().name.equals(prim))
				{
					found = true;
					a.addWantsThis(bean);
					break;
				}
			}
			if (found)
				continue;

			// look in classes first, and then ifaces
			if (classes.containsKey(prim))
				continue; // we have it in the repository -- don't go further
			if (ifaces.containsKey(prim))
				continue; // we have it in the repository -- don't go further

			// otherwise, we need to move it into our import list
			BeanClass imp = classesP.get(prim);
			if (imp == null)
				imp = ifacesP.get(prim);
			if (imp != null)
			{
				all.add(imp);
				imp.setSynthetic(true);
				imp.addWantsThis(bean);
			}
			else
			{
				// otherwise, we need to make one, in error
				ClassNode errorNode = new ClassNode();
				errorNode.name = prim;
				imp = new BeanClass(errorNode, BeanTypeEnum.PRIMITIVE);
				imp.setError("Primitive " + prim + " cannot be found.  First required by " + bean.getNode().name);
				all.add(imp);
				imp.setSynthetic(true);
				imp.addWantsThis(bean);
			}
		}
	}

	public void constructLeaves(List<BeanClass> all, BeanClass bean)
	{
		for (String p : bean.getLeavesNeeded(this))
		{
			String beanClass = PrimitiveHelper.translateShortToLongPrimitive(p);
			
			// look for a class first
			boolean found = false;
			for (BeanClass a : all)
			{
				if (a.getType() != BeanTypeEnum.INTERFACE && a.getNode().name.equals(beanClass))
				{
					found = true;
					// add this bean to the wants list
					a.addWantsThis(bean);
					break;
				}
			}
			if (found)
				continue;

			// look in classes first, and then ifaces
			if (classes.containsKey(beanClass))
				continue; // we have it in the repository -- don't go further

			// otherwise, we need to move it into our import list
			BeanClass imp = classesP.get(beanClass);
			if (imp != null)
			{
				all.add(imp);
				imp.setSynthetic(true);
				imp.addWantsThis(bean);				
			}
			else
			{
				// otherwise, we need to make one, in error
				ClassNode errorNode = new ClassNode();
				errorNode.name = beanClass;
				imp = new BeanClass(errorNode, BeanTypeEnum.BEAN);
				imp.setError("Bean " + beanClass + " cannot be found.  First required by " + bean.getNode().name);
				all.add(imp);
				imp.setSynthetic(true);
				imp.addWantsThis(bean);
			}
		}
	}
	
	public Collection<BeanClass> getAllPotentialBeanImports()
	{
		return classesP.values();
	}

	public Collection<BeanClass> getAllPotentialInterfaceImports()
	{
		return ifacesP.values();
	}

	public boolean refersToPrimitive(Type type)
	{
		String className = PrimitiveHelper.translateShortToLongPrimitive(type.getClassName().replace("/", "."));
		
		Element elem = classes.get(className);
		if (elem != null && ((Class) elem).getComponentKind().equals(ComponentKind.PRIMITIVE_LITERAL))
			return true;
	
		BeanClass b = classesP.get(className);
		if (b != null && b.getType() == BeanTypeEnum.PRIMITIVE)
			return true;
		return false;
	}
	

	public boolean refersToBean(Type type)
	{
		String className = PrimitiveHelper.translateShortToLongPrimitive(type.getClassName().replace("/", "."));
		
		Element elem = classes.get(className);
		if (elem != null && ((Class) elem).getComponentKind().equals(ComponentKind.NORMAL_LITERAL))
			return true;
	
		BeanClass b = classesP.get(className);
		if (b != null && b.getType() == BeanTypeEnum.BEAN)
			return true;
		return false;
	}

	public boolean refersToRealInterface(Type type)
	{
		String className = PrimitiveHelper.translateShortToLongPrimitive(type.getClassName().replace("/", "."));
		
		Element elem = classes.get(className);
		if (elem != null)
			return false;
	
		BeanClass b = classesP.get(className);
		if (b != null)
			return false;
		return true;
	}

	public boolean refersToPossibleImportBean(String name)
	{
		String className = PrimitiveHelper.translateShortToLongPrimitive(name);
		
		BeanClass b = classesP.get(className);
		return b != null && b.getType() == BeanTypeEnum.BEAN;
	}

	public BeanClass locatePossibleBeanClass(Type type, boolean port)
	{
		String cls = PrimitiveHelper.translateShortToLongPrimitive(type.getClassName());
		
		// if we find this in the repository already, we don't have a beanclass
		if (classes.get(cls) != null || ifaces.get(cls) != null)
			return null;
		
		BeanClass imp = port ? ifacesP.get(cls) : classesP.get(cls);
		if (imp == null)
			imp = port ? classesP.get(cls) : ifacesP.get(cls);
		return imp;
	}

	public boolean beanExists(java.lang.Class<?> cls)
	{
		BeanClass imp = classesP.get(cls.getName());
		return imp != null && !imp.isBean();
	}

	public BeanClass locatePossibleBeanClass(String className, boolean port)
	{
		String cls = PrimitiveHelper.translateShortToLongPrimitive(className);
		
		// if we find this in the repository already, we don't have a beanclass
		if (classes.get(cls) != null || ifaces.get(cls) != null)
			return null;
		
		BeanClass imp = port ? ifacesP.get(cls) : classesP.get(cls);
		if (imp == null)
			imp = port ? classesP.get(cls) : ifacesP.get(cls);
		return imp;
	}

	public Interface findInterface(Map<String, Interface> interfaces, String name)
	{
		String className = PrimitiveHelper.translateShortToLongPrimitive(name);
		Interface iface = ifaces.get(className);
		if (iface == null && interfaces != null)
			iface = interfaces.get(className);
		return iface;
	}

	public Class findClass(Map<String, Class> cls, String name)
	{
		String className = PrimitiveHelper.translateShortToLongPrimitive(name);
		Class c = classes.get(className);
		if (c == null && cls != null)
			c = cls.get(className);
		return c;
	}

	public boolean isRefreshedClass(BeanClass bc)
	{
		return cclasses.containsKey(bc.getNode().name);
	}

	public boolean isRefreshedInterface(BeanClass bc)
	{
		return cifaces.containsKey(bc.getNode().name);
	}
	
	public Class getRefreshedClass(BeanClass bc)
	{
		return cclasses.get(bc.getNode().name);
	}

	public Interface getRefreshedInterface(BeanClass bc)
	{
		return cifaces.get(bc.getNode().name);
	}
}
