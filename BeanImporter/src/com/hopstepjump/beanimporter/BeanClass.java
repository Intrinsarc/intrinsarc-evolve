package com.hopstepjump.beanimporter;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.objectweb.asm.*;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import com.hopstepjump.repositorybase.*;

public class BeanClass
{
	private BeanTypeEnum type;
	private ClassNode node;
	private String name;
	private Map<String, BeanField> fields = new LinkedHashMap<String, BeanField>();
	private String error;
	private boolean synthetic;
	private boolean hidden;
	private boolean isAbstract;
	private BeanClass real;  // used for pretend interfaces to refer to their actual class
	private String originalName;
	private List<String> interfaces;
	private String superClass;
	private Set<BeanClass> wantsThis;

	public BeanClass(ClassNode node)
	{
		this.node = node;
		node.name = node.name.replace("/", ".");
		name = extractClassName(node.name);
		originalName = name;
		extractDetails();
	}
	
	public BeanClass(ClassNode node, BeanTypeEnum type)
	{
		this.node = node;
		node.name = node.name.replace("/", ".");
		name = extractClassName(node.name);
		originalName = name;
		this.type = type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ClassNode getNode()
	{
		return node;
	}
	
	public List<BeanField> getAttributes()
	{
		List<BeanField> attrs = new ArrayList<BeanField>();
		for (BeanField field : fields.values())
			if (!field.isPort())
				attrs.add(field);		
		return attrs;		
	}
	
	public List<BeanField> getPorts()
	{
		List<BeanField> ports = new ArrayList<BeanField>();
		for (BeanField field : fields.values())
			if (field.isPort())
				ports.add(field);		
		return ports;
	}

	private void extractDetails()
	{
		hidden = true;
		type = BeanTypeEnum.BAD;
		int access = node.access;
		if (!containsAnyOf(access, new int[]{Opcodes.ACC_PUBLIC}))
			return;
		if (containsAnyOf(access, new int[]{Opcodes.ACC_ENUM, Opcodes.ACC_ANNOTATION, Opcodes.ACC_NATIVE}))
			return;
		
		// unhide if the name doesn't have a $ in it
		if (!name.contains("$"))
			hidden = false;

		// get the interfaces and any subclasses
		for (Object i : node.interfaces)
		{
			if (interfaces == null)
				interfaces = new ArrayList<String>();
			interfaces.add(((String) i).replace("/", "."));
		}
		type = BeanTypeEnum.INTERFACE;
		if (containsAnyOf(access, new int[]{Opcodes.ACC_INTERFACE}))
		{
			// update the name to be of the form IXXX...
			name = updateInterfaceName(name);
			return;
		}
		type = BeanTypeEnum.PRIMITIVE;		

		// must contain a default constructor, unless it is abstract
		isAbstract = containsAnyOf(access, new int[]{Opcodes.ACC_ABSTRACT});
		boolean haveDefaultConstructor = false;
		for (Object obj : node.methods)
		{
			MethodNode m = (MethodNode) obj;
			if (m.name.equals("<init>") && containsAnyOf(m.access, new int[]{Opcodes.ACC_PUBLIC}) &&
					m.desc.equals("()V"))
				haveDefaultConstructor = true;
		}
		
		// extract a possible property
		for (Object obj : node.methods)
		{
			MethodNode m = (MethodNode) obj;
			if (containsAnyOf(m.access, new int[]{Opcodes.ACC_STATIC}))
				continue;
			if (!containsAnyOf(m.access, new int[]{Opcodes.ACC_PUBLIC}))
				continue;
			
			String mName = m.name;
			if (mName.length() >= 4)
				handleGetOrSet(m, mName);
			if (mName.length() >= 3)
				handleAdd(m, mName);
		}
		
		// add a "main" port
		List<Type> types = new ArrayList<Type>();
		types.add(Type.getType("L" + node.name + ";"));

		BeanField mainPort = new BeanField(this, "main", types);
		mainPort.setPort(true);
		mainPort.setMain(true);
		mainPort.setReadable(true);
		fields.put("main", mainPort);
		
		// we must have some valid fields and a default constructor for this to be a real bean
		if (haveDefaultConstructor)
			for (BeanField field : fields.values())
				if (field.isValid() && !field.isMain())
				{
					type = BeanTypeEnum.BEAN;
					break;
				}
		
		// need to set if this is a bean or primitive, even though we only want bean superclasses,
		// as we can toggle between primitives and beans
		if (node.superName != null && !node.superName.equals("java/lang/Object"))
			superClass = node.superName.replace("/", ".");
	}

	private String updateInterfaceName(String name)
	{
		if (name.length() == 1)
			return "I" + name;
		// make sure it doesn't have a capital I and then a capital something
		char start = name.charAt(0);
		char next = name.charAt(1);
		if (start == 'I' && (next >= 'A' && next <= 'Z'))
			return name;
		return "I" + name;
	}

	private void handleAdd(MethodNode m, String mName)
	{
		if (mName.startsWith("add"))
		{			
			// make sure we have a single parameter
			Type types[] = Type.getArgumentTypes(m.desc);
			if (types.length != 1)
				return;
			Type type = types[0];
			
			// possibly take the name from the type
			boolean noName = mName.length() <= 3; 
			String name = !noName ? firstLower(mName.substring(3)) + "s" : last(type.getClassName()) + "s";
			
			// if the type is an array type, ignore
			if (type.getSort() == Type.ARRAY)
				return;
			
			// if this is one of the interfaces we ignore, then ignore it
			if (areIgnoring(type.getClassName()))
				return;
			
			// look for an existing field
			BeanField field = fields.get(name);
			if (field != null)
			{
				// must be a port marked already as many
				if (!field.isMany() || !field.isPort())
					return;
			}
			else
			{
				// create a port
				field = new BeanField(this, name, type);
				field.setPort(true);
				field.setMany(true);
				field.setWriteable(true);
				if (noName)
					field.setNoName(true);
				fields.put(name, field);
			}
		}
	}
	
	private String last(String className)
	{
		int index = className.lastIndexOf('.');
		if (index == -1)
			return "_" + firstLower(className);
		return "_" + firstLower(className.substring(index + 1));
	}

	private void handleGetOrSet(MethodNode m, String mName)
	{
		Type type = null;
		boolean set = false;
		boolean get = false;
		boolean valid = true;
		boolean many = false;
		if (mName.startsWith("get"))
		{
			get = true;
			// get the type parameter
			type = Type.getReturnType(m.desc);
			Type types[] = Type.getArgumentTypes(m.desc);
			valid = types.length == 0;
			
			// is this possibly an array get?
			if (type.getSort() == Type.ARRAY)
			{
				many = true;
				type = type.getElementType();
			}
		}
		else
		if (mName.startsWith("set"))
		{
			set = true;
			// get the type parameter
			Type types[] = Type.getArgumentTypes(m.desc);
			if (types.length == 0 || types.length > 1)
				valid = false;
			else
				type = types[0];
			
			// if the type is an array type, ignore
			if (type != null && type.getSort() == Type.ARRAY)
				return;
		}
		
		if (type != null && (set || get))
		{
			String fieldName = firstLower(mName.substring(3));
			
			// locate or create
			BeanField field = fields.get(fieldName);
			if (!valid)
			{
				// possibly mark as invalid
				if (field != null)
					field.setValid(false);
			}
			else
			{
				if (field == null)
				{
					field = new BeanField(this, fieldName, type);
					fields.put(fieldName, field);
				}
				else
				{
					// types must match or we have an invalid field
					if (!type.getClassName().equals(field.getTypes().get(0).getClassName()))
						field.setValid(false);
				}
				
				// if this is one of the interfaces we ignore, then ignore it
				if (areIgnoring(type.getClassName()))
					return;

				// populate the field
				if (set)
					field.setWriteable(true);
				if (get)
					field.setReadable(true);
				field.setMany(many);
			}
		}
	}

	private boolean areIgnoring(String cls)
	{
		if (cls.equals("java.lang.Object") ||
				cls.equals("java.io.Serializable") ||
				cls.equals("java.lang.Cloneable") ||
				cls.equals("java.lang.Comparable"))
			return true;
		return false;
	}

	private boolean containsAnyOf(int access, int[] opcodes)
	{
		for (int op : opcodes)
			if ((access & op) == 0)
				return false;
		return true;
	}

	public boolean isBean()
	{
		return type == BeanTypeEnum.BEAN;
	}
	
	public BeanTypeEnum getType()
	{
		return type;
	}
	
	public static String extractClassName(String name)
	{
		int last = name.lastIndexOf('.');
		if (last == -1)
			return name;
		return name.substring(last + 1);
	}

	public static String firstLower(String name)
	{
		if (name.length() == 0)
			return "";
		return "" + Character.toLowerCase(name.charAt(0)) + name.substring(1);
	}

	public BeanClass makeInterfaceForBean()
	{
		BeanClass cls = new BeanClass(node);
		cls.type = BeanTypeEnum.INTERFACE;
		cls.name = "I" + cls.name;
		cls.real = this;
		
		// add any superclass to this
		// handle any interfaces
		cls.interfaces = new ArrayList<String>();
		for (Object obj : node.interfaces)
		{
			String iface = (String) obj;
			cls.interfaces.add(iface.replace("/", "."));
		}
		if (superClass != null)
			cls.interfaces.add(superClass);
		if (cls.interfaces.isEmpty())
			cls.interfaces = null;
		
		return cls;
	}
	
	public boolean isInterfaceForBean()
	{
		return real != null;
	}
	
	public BeanClass getRealBeanOfBeanInterface()
	{
		return real;
	}

	/** what interfaces are used?
	 */
	public List<String> getInterfaces()
	{
		return filter(interfaces);
	}
	
	/** what super class is used?
	 */
	public String getSuperClass()
	{
		return superClass;
	}
	
	/**
	 * returns dotted names
	 * @return
	 */
	public Set<String> getInterfacesNeeded()
	{
		Set<String> needed = new HashSet<String>();

		if (isBean())
		{
			needed.add(node.name);
			// handle each port
			for (BeanField field : fields.values())
			{
				if (!field.isIgnore() && field.isPort())
					needed.addAll(field.getTypesNeeded());
			}
		}
		else
		if (type == BeanTypeEnum.INTERFACE)
		{
			if (real != null)
				needed.addAll(filter(real.interfaces));
			else
				needed.addAll(filter(interfaces));
		}
		
		return needed;
	}
	
	

	private List<String> filter(List<String> interfaces)
	{
		List<String> ret = new ArrayList<String>();
		if (interfaces == null)
			return ret;
		for (String str : interfaces)
			if (!areIgnoring(str))
				ret.add(str);
		return ret;
	}

	/**
	 * returns dotted names
	 * @return
	 */
	public Set<String> getPrimitivesNeeded()
	{
		Set<String> needed = new HashSet<String>();
		
		// handle each attribute
		if (isBean())
			for (BeanField field : fields.values())
			{
				if (!field.isIgnore() && !field.isPort())
					needed.addAll(field.getTypesNeeded());
			}
		return needed;
	}
	

	public Set<String> getLeavesNeeded(BeanFinder finder)
	{
		// only if we are a bean or a fake bean interface
		Set<String> leaves = new HashSet<String>();
		if (isBean() && superClass != null)
			leaves.add(superClass);
		if (real != null && real.superClass != null)
			leaves.add(real.superClass);
		
		// add beans for any fields that refer to bean interfaces
		if (isBean())
		{
			for (BeanField field : fields.values())
			{
				if (!field.isIgnore())
				{
					for (String str : field.getTypesNeeded())
						if (finder.refersToPossibleImportBean(str))
							leaves.add(str);
				}
			}
		}
		
		return leaves;
	}
	
	public void setError(String error)
	{
		this.error = error;
	}
	
	public String getError(BeanFinder finder)
	{
		if (error != null)
			return error;
		
		String err = "";
		if (fieldsHaveErrors())
			err = "Field errors";
		if (hasTypeError(finder))
			err += (err.length() == 0 ? "S" : " and s") + "upertype " + superClass + " must be a bean";
			
		return err;
	}
	
	private boolean hasTypeError(BeanFinder finder)
	{
		if (isBean() && superClass != null)
		{
			BeanClass sup = finder.locatePossibleBeanClass(superClass, false);
			if (sup != null)
				return sup.type != BeanTypeEnum.BEAN;
		}
		return false;
	}

	public boolean isInError(BeanFinder finder)
	{
		return error != null || fieldsHaveErrors() || hasTypeError(finder);
	}

	public boolean isSynthetic()
	{
		return synthetic;
	}
	
	public void setSynthetic(boolean synthetic)
	{
		this.synthetic = synthetic;
	}
	
	public boolean isHidden()
	{
		return hidden;
	}

	public boolean isAbstract()
	{
		return isAbstract;
	}
	
	public void fixUpUsingFinder(BeanFinder finder)
	{
		// change a field to an attribute or a port depending on whether it refers to a bean or a primitive
		// and remove any "bad" fields
		for (BeanField field : fields.values())
			field.fixUpUsingFinder(finder);
		
		// remove any bad interfaces
		if (interfaces != null)
		{
			List<String> expunged = new ArrayList<String>(interfaces);
			for (String iface : interfaces)
			{
				BeanClass cls = finder.locatePossibleBeanClass(iface, true);
				if (cls != null && cls.getType() == BeanTypeEnum.BAD)
					expunged.remove(iface);
			}
			interfaces = expunged;
		}
		
		// remove any bad superclasses
		if (superClass != null)
		{
			BeanClass cls = finder.locatePossibleBeanClass(superClass, false);
			if (cls != null && cls.getType() == BeanTypeEnum.BAD)
				superClass = null;
		}
	}

	public boolean canToggleBeanOrPrimitive()
	{
		return real != null || type == BeanTypeEnum.BEAN || type == BeanTypeEnum.PRIMITIVE;
	}
	
	public void toggleBeanOrPrimitive()
	{
		if (real != null)
			real.toggleBeanOrPrimitive();
		else
		{
			if (type == BeanTypeEnum.BEAN)
				type = BeanTypeEnum.PRIMITIVE;
			else
			if (type == BeanTypeEnum.PRIMITIVE)
				type = BeanTypeEnum.BEAN;
		}
	}

	public void markFieldErrors(BeanFinder finder)
	{
		if (isBean())
			for (BeanField field : fields.values())
				if (!field.isIgnore())
					field.markErrors(finder);
	}
	
	public boolean fieldsHaveErrors()
	{
		for (BeanField field : fields.values())
			if (!field.isIgnore() && field.isInError())
				return true;
		return false;
	}

	public String getOriginalName()
	{
		return originalName;
	}

	public void fixUpBeanTypes(BeanFinder finder, int count[])
	{
		// can't change interfaces
		if (type == BeanTypeEnum.INTERFACE)
			return;
		
		// trace this back to a possible repository object
		Classifier trace = traceBackToRepository(finder);
		if (trace != null)
		{
			boolean shouldBeBean = StereotypeUtilities.isStereotypeApplied(trace, CommonRepositoryFunctions.COMPONENT); 
			if (shouldBeBean != isBean())
			{
				++count[0];
				type = shouldBeBean ? BeanTypeEnum.BEAN : BeanTypeEnum.PRIMITIVE;
			}
		}
		else
		// if this is a bean, and the superclass isn't then change the super
		if (isBean() && superClass != null)
		{
			BeanClass sup = finder.locatePossibleBeanClass(superClass, false);
			if (sup != null)
			{
				if (sup.type != BeanTypeEnum.BEAN && !isExceptionByName(sup))
				{
					++count[0];
					sup.type = BeanTypeEnum.BEAN;
				}
			}
		}
		
		// if this is an exception type by name, then enforce as primitive
		if (trace == null && isBean() && isExceptionByName(this))
		{
			++count[0];
			type = BeanTypeEnum.PRIMITIVE;
		}
		
		for (BeanField field : fields.values())
			field.fixUpDueToInterfaces(finder, count);
	}

	private Classifier traceBackToRepository(BeanFinder finder)
	{
		// look for superclasses as beans, then go into the repository if needed
		String sup = superClass;
		while (sup != null)
		{
			// look for a bean class
			BeanClass bean = finder.locatePossibleBeanClass(sup, true);
			if (bean != null)
				sup = bean.superClass;
			else
			{
				Class existing = finder.findClass(null, sup);
				return existing;
			}
		}
		return null;
	}

	private boolean isExceptionByName(BeanClass cls)
	{
		String name = cls.name;
		return name.endsWith("Exception") || name.endsWith("Error") || name.endsWith("Throwable");
	}

	@Override
	public String toString()
	{
		return "BeanClass(" + name + ")";
	}

	public void addWantsThis(BeanClass bean)
	{
		// no synthetic interfaces
		if (bean.isInterfaceForBean() || bean == this)
			return;
		if (wantsThis == null)
			wantsThis = new HashSet<BeanClass>();
		wantsThis.add(bean);
	}
	
	public Set<BeanClass> getWantsThis()
	{
		return wantsThis;
	}

	public void clearWantsThis()
	{
		wantsThis = null;
	}
}
