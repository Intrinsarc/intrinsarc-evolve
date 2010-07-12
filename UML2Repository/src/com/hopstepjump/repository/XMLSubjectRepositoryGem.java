package com.hopstepjump.repository;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.zip.*;

import javax.swing.*;

import org.eclipse.emf.common.notify.*;
import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.ecore.resource.impl.*;
import org.eclipse.emf.ecore.xmi.impl.*;
import org.eclipse.emf.edit.domain.*;
import org.eclipse.emf.edit.provider.*;
import org.eclipse.emf.edit.provider.resource.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.notifications.*;
import com.hopstepjump.repository.garbagecollector.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;

public class XMLSubjectRepositoryGem implements Gem
{
  public static String UML2_SUFFIX = ".uml2";
  public static String UML2Z_SUFFIX = ".uml2z";
  public static String UML2Z_SUFFIX_NO_DOT = "uml2z";
  public static String[] EXTENSION_TYPES = new String[]{"uml2z", "uml2"}; 
  public static String[] EXTENSION_DESCRIPTIONS = new String[]{"compressed UML2 files (.uml2z)", "uncompressed UML2 files (.uml2)"}; 
  public static String EXTENSION_DESCRIPTION = "UML2 files (.uml2 and .uml2z)"; 

  private String fileName;
  private Model topLevel;
  private XMIResourceImpl resource;
  
  private CommonRepositoryFunctions common = new CommonRepositoryFunctions();
  private SubjectRepositoryFacetImpl subjectFacet = new SubjectRepositoryFacetImpl();
  private Set<SubjectRepositoryListenerFacet> listeners = new HashSet<SubjectRepositoryListenerFacet>();
  private boolean modified;
  private UndoRedoStackManager undoredo = new UndoRedoStackManager();
	
  private Adapter adapter = new Adapter()
  {
    public void notifyChanged(Notification notification)
    {
    	if (!GlobalSubjectRepository.ignoreUpdates)
    	{
    		modified = true;
    		undoredo.addNotification(notification);
    	}
    }
    public Notifier getTarget()
    {
      return null;
    }
    public void setTarget(Notifier arg0)
    {
    }
    public boolean isAdapterForType(Object arg0)
    {
      return false;
    }
  };
  
	private class SubjectRepositoryFacetImpl implements SubjectRepositoryFacet
  {
		public String getRedoTransactionDescription()
		{
			return undoredo.getRedoDescription();
		}

		public String getUndoTransactionDescription()
		{
			return undoredo.getUndoDescription();
		}

		public void undoTransaction()
		{
    	EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
			undoredo.undo();
    	EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = false;
      for (SubjectRepositoryListenerFacet listener : listeners)
        listener.sendChanges();
		}
		
		public void redoTransaction()
		{
    	EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
			undoredo.redo();
    	EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = false;
      for (SubjectRepositoryListenerFacet listener : listeners)
        listener.sendChanges();
		}
		
		public int getTransactionPosition()
		{
			return undoredo.getCurrent();
		}
		
		public int getTotalTransactions()
		{
			return undoredo.getStackSize();
		}
		
		public void clearTransactionHistory()
		{
			undoredo.clearStack();
		}
		
		public void enforceTransactionDepth(int desiredDepth)
		{
			undoredo.enforceDepth(desiredDepth);
		}
		
		////////////////////////////////////////////////////
		
    public Model getTopLevelModel()
    {
      return topLevel;
    }

    public boolean supportsSaveAs()
    {
      return true;
    }
    
    public String saveAs(JFrame frame, File recentDirectory)
    {
      String newFileName = RepositoryUtility.chooseFileNameToCreate(
          frame,
          "Select file to save as", EXTENSION_DESCRIPTIONS, EXTENSION_TYPES, recentDirectory);
      if (newFileName == null)
        return null;
      fileName = newFileName; 
      resource = createXMIResource(fileName);
      resource.getContents().add(topLevel);
      performSave(resource, false);

      return fileName;
    }

    public boolean supportsSaveTo()
    {
      return true;
    }

    public String saveTo(JFrame frame, String extensionDescription, String extension, File recentDirectory)
    {
      String copyFileName = RepositoryUtility.chooseFileNameToCreate(
          frame,
          "Select file to copy into",
          extensionDescription == null ? EXTENSION_DESCRIPTIONS : new String[]{extensionDescription},
          extension == null ? EXTENSION_TYPES : new String[]{extension},
          recentDirectory);
      if (copyFileName == null)
        return null;
      Resource copyResource = createXMIResource(copyFileName);
      copyResource.getContents().add(topLevel);
      performSave(copyResource, false);
      resource.getContents().add(topLevel);
      
      return copyFileName;
    }

    public String save(JFrame frame, boolean keepExistingModificationInfo, File recentDirectory)
    {
      if (fileName == null)
        return saveAs(frame, recentDirectory);
      else
        performSave(resource, keepExistingModificationInfo);
      return "";
    }
    
    private void performSave(Resource resource, boolean keepExistingModificationInfo)
    {
    	undoredo.ignoreNotifications();
    	EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
//      long start = System.currentTimeMillis();
      
      int changed = 0;
      
      // save each diagram
      for (DiagramFacet diagram : GlobalDiagramRegistry.registry.getDiagrams())
      {
        if (!diagram.isClipboard() && diagram.isModified())
        {
          // delete the old diagram
          Package linkedPackage = (Package) diagram.getLinkedObject();
          
          J_DiagramHolder holder = linkedPackage.getJ_diagramHolder();
          if (holder == null)
            holder = linkedPackage.createJ_diagramHolder();

          // save the new diagram -- old diagram should simply disappear at this point
          new PersistentDiagramToDbDiagramTranslator(diagram.makePersistentDiagram()).translate(holder);
          
          // set up the diagram information: time and user
          String who = holder.getSavedBy();
          if (who == null || who.length() == 0 || !keepExistingModificationInfo)
          {
            holder.setSavedBy("Andrew");          
            SimpleDateFormat formatter = new SimpleDateFormat(CommonRepositoryFunctions.SAVE_DATE_FORMAT);          
            holder.setSaveTime(formatter.format(new Date()));
          }
          
          changed++;          
          diagram.resetModified();
        }
      }
      
      // save the resource
      try
      {
      	// possibly use compression
      	String path = resource.getURI().toFileString();
      	if (path.endsWith(UML2Z_SUFFIX))
      	{
        	GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(path));
        	try
        	{
      			resource.save(out, null);
        	}
        	finally
        	{
      			out.close();      		
        	}
      	}
      	else
    			resource.save(null);
      }
      catch (IOException ex)
      {
        throw new IllegalStateException("Cannot save file: " + ex.getMessage(), ex);
      }
      modified = false;
    	EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
    	undoredo.noticeNotifications();
    }

    public void addRepositoryListener(SubjectRepositoryListenerFacet listener)
    {
      listeners.add(listener);
    }

    public void removeRepositoryListener(SubjectRepositoryListenerFacet listener)
    {
      listeners.remove(listener);
    }

    public String getFileName()
    {
      if (fileName == null)
        return "*new*";
      return fileName;
    }

    public void startTransaction(String redoName, String undoName)
    {
      EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
      undoredo.startTransaction(redoName, undoName);
    }

    public void commitTransaction()
    {
      EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = false;
      undoredo.commitTransaction();
      for (SubjectRepositoryListenerFacet listener : listeners)
        listener.sendChanges();
    }

    public void incrementPersistentDelete(Element element)
    {
      element.setJ_deleted(element.getJ_deleted() + 1);
    }


    public void decrementPersistentDelete(Element element)
    {
      int currentDelete = element.getJ_deleted();
      if (currentDelete > 0)
        element.setJ_deleted(currentDelete - 1);
    }

    public Collection<NamedElement> findElements(String name, java.lang.Class< ? > extentClass, boolean includeSubclasses)
    {
      return
        queryObjectsByName(
            extentClass,
            name,
            includeSubclasses,
            false);
    }

    public Collection<NamedElement> findElementsStartingWithName(String name, java.lang.Class< ? > extentClass, boolean includeSubclasses)
    {
      return
        queryObjectsByName(
            extentClass,
            name,
            includeSubclasses,
            true);
    }

    public void refreshAll()
    {
    }

    public void close()
    {
      GlobalNotifier.getSingleton().removeObserver(adapter);
    }

    public SaveInformation getSaveInformation()
    {
      int diagramsToSave = 0;
      for (DiagramFacet diagram : GlobalDiagramRegistry.registry.getDiagrams())
        if (!diagram.isClipboard() && diagram.isModified())
          diagramsToSave++;
      return new SaveInformation(modified, diagramsToSave);
    }

    public PersistentDiagram retrievePersistentDiagram(Package pkg)
    {
      return common.retrievePersistentDiagram(pkg);
    }

    public String getFullyQualifiedName(Element element, String separator)
    {
      return common.getFullyQualifiedName(element, separator, null);
    }

    public Namespace findVisuallyOwningNamespace(DiagramFacet diagram, ContainerFacet container)
    {
      return common.findVisuallyOwningNamespace(diagram, container);
    }

    public Package findVisuallyOwningStratum(DiagramFacet diagram, ContainerFacet container)
    {
      return common.findVisuallyOwningStratum(diagram, container);
    }

    public Package findVisuallyOwningPackage(DiagramFacet diagram, ContainerFacet container)
    {
      return common.findVisuallyOwningPackage(diagram, container);
    }

    public Element findOwningElement(Element element, java.lang.Class<?> cls)
    {
      return common.findOwningElement(element, cls);
    }

    public Package findOwningStratum(Element element)
    {
      return common.findOwningStratum(element);
    }

    public Type findPrimitiveType(String name)
    {
      return common.findPrimitiveType(subjectFacet, name);
    }

    public NamedElement findElement(String typeName, java.lang.Class< ? >[] extentClasses)
    {
      return common.findElement(subjectFacet, typeName, extentClasses);
    }

    public Element findOwningElement(FigureReference containingReference, EClass ecls)
    {
      return common.findOwningElement(containingReference, ecls);
    }

    public Element findOwningElement(FigureReference containingReference, EClass[] classes_)
    {
      return common.findOwningElement(containingReference, classes_);
    }

    /**
     * find the stereotypes which are applicable for the given meta model class
     * -- this could be made a lot more efficient than iterating over every model element ;-)
     */
		public Set<Stereotype> findApplicableStereotypes(EClass ecls)
		{
			EClass stereoEClass = UML2Package.eINSTANCE.getStereotype();
			String metaModelClassName = ecls.getName();
			
	    Set<Stereotype> matched = new HashSet<Stereotype>();
	    for (Package pkg : findAllPackages())
	    {
	    	if (pkg instanceof Profile)
	    	{
	    		// look inside the profile
	    		for (Object object : pkg.undeleted_getOwnedMembers())
	    		{
	    			Element element = (Element) object;
	  	      if (!element.isThisDeleted() && element.eClass() == stereoEClass)
	  	      {
	  	        Stereotype stereo = (Stereotype) element;
	  	        if (!stereo.isAbstract() &&
	                stereo.getExtendsMetaModelElement().indexOf("'" + metaModelClassName + "'") != -1)
	  	          matched.add(stereo);
	  	      }
	    			
	    		}
	    	}
	    }
	    return matched;
		}

		public Stereotype findStereotype(EClass ecls, String uuid)
		{
			return common.findStereotype(this, ecls, uuid);
		}

		public NamedElement findNamedElementByUUID(String uuid)
		{
	    for (TreeIterator treeElement = resource.getAllContents(); treeElement.hasNext();)
	    {
	      Object subject = treeElement.next();
	      if (subject instanceof NamedElement)
	      {
	      	NamedElement named = (NamedElement) subject;
	      	if (named.getUuid().equals(uuid) && !((NamedElement) subject).isThisDeleted())
	      		return named;
	      }
	    }
	    return null;
		}

		public Element findElementByUUID(String uuid)
		{
	    for (TreeIterator treeElement = resource.getAllContents(); treeElement.hasNext();)
	    {
	      Object subject = treeElement.next();
	      if (subject instanceof Element)
	      {
	      	Element elem = (Element) subject;
	      	if (elem.getUuid().equals(uuid) && !((Element) subject).isThisDeleted())
	      		return elem;
	      }
	    }
	    return null;
		}

    public void destroy(Element element)
    {
      resource.getContents().remove(element);
      // destroying all elements gets a bit slow... but we need to do it for connectors and some other things
      element.destroy();
    }
    
    public int collectGarbage(GarbageUpdaterFacet updater)
    {
      RepositoryGarbageCollector collector = new RepositoryGarbageCollector(this, updater);
      return collector.collectGarbage();
    }

    public Set<Package> findAllPackages()
    {
    	// start with the model and look for all undeleted nested packages
    	Set<Package> pkgs = new HashSet<Package>();
    	collectPackages(pkgs, getTopLevelModel());
      return pkgs;
    }

    private void collectPackages(Set<Package> pkgs, Package pkg)
		{
    	pkgs.add(pkg);
    	for (Object object : pkg.undeleted_getChildPackages())
    		collectPackages(pkgs, (Package) object);
		}

		public String getFullStratumNames(Element element)
    {
      return common.getFullStratumNames(element);
    }

    public boolean isReadOnly(Element element)
    {
      return common.isReadOnly(element);
    }
    
    public boolean isContainerContextReadOnly(FigureFacet figure)
    {
      return common.isContainerContextReadOnly(figure);
    }

		public String getModelIdentifier()
		{
			return CommonRepositoryFunctions.getModelIdentifier(topLevel);
		}

    public Package findOwningPackage(Element element)
    {
      return CommonRepositoryFunctions.findOwningPackage(element);
    }

		public void resetModified()
		{
			modified = false;
		}
  }
  
  public static XMLSubjectRepositoryGem openFile(String fileName, boolean initialiseWithFoundation, boolean rememberFileName) throws RepositoryOpeningException
  {
    XMLSubjectRepositoryGem repository = new XMLSubjectRepositoryGem(fileName);
    repository.openFile(initialiseWithFoundation, rememberFileName);
    return repository;
  }

  
  private XMLSubjectRepositoryGem(String fileName)
  {
    this.fileName = fileName;
    
  }
  
  
  private void openFile(boolean initialiseWithFoundation, boolean rememberFileName) throws RepositoryOpeningException
  {
    setUpEditingDomain();
    if (fileName == null)
      resource = null;
    else
      resource = readXMIResource(fileName);

    // if the resource is null, and we have a file name
    if (fileName != null && resource == null)
      throw new RepositoryOpeningException("Found no sensible xml model in " + fileName);
    
    // look for the top level model
    if (resource == null)
    {
      topLevel = common.initialiseModel(initialiseWithFoundation);
      resource = createXMIResource(fileName);
      resource.getContents().add(topLevel);
    }
    else
    {
      Collection c = queryObjectsByName(ModelImpl.class, CommonRepositoryFunctions.MODEL, false, true);
      int size = c.size();
      if (size > 1)
        throw new RepositoryOpeningException("Found " + size + " models called Model in " + fileName);
      else
      if (size == 0)
        throw new RepositoryOpeningException("Found no top level model in " + fileName);

      topLevel = (Model) c.iterator().next();
    }
    // if we have just opened a new repository, there will be no changes
    modified = false;
    GlobalNotifier.getSingleton().addObserver(adapter);
    
    if (!rememberFileName)
    	fileName = null;
  }
  
  private Collection<NamedElement> queryObjectsByName(java.lang.Class<?> elementClass, String name, boolean includeSubclasses, boolean startsWith)
  {
    List<NamedElement> matched = new ArrayList<NamedElement>();
    for (TreeIterator treeElement = resource.getAllContents(); treeElement.hasNext();)
    {
      Object element = treeElement.next();
      if (element instanceof NamedElement &&
          (includeSubclasses && elementClass.isAssignableFrom(element.getClass()) ||
           !includeSubclasses && elementClass == element.getClass()))
      {
        NamedElement namedElement = (NamedElement) element;
        boolean nameOk =
          startsWith ?
              namedElement.getName().startsWith(name) :
              namedElement.getName().equals(name);
              
        if (!namedElement.isThisDeleted() && nameOk)
          matched.add(namedElement);
      }
    }
    return matched;
  }

  public SubjectRepositoryFacet getSubjectRepositoryFacet()
  {
    return subjectFacet;
  }

  /**
   * @return
   */
  private EditingDomain setUpEditingDomain()
  {
    // register the XMI factory
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
    
    // set up the factories and undo/redo infrastructure
    // Create an adapter factory that yields item providers.
    List factories = new ArrayList();
    factories.add(new ResourceItemProviderAdapterFactory());
    factories.add(new ReflectiveItemProviderAdapterFactory());
    ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(factories);
    EditingDomain domain = new AdapterFactoryEditingDomain(adapterFactory, null, new HashMap());
    return domain;
  }

  /**
   * @return
   */
  private XMIResourceImpl createXMIResource(String fileName)
  {
    // create the resource where Foo will live
    ResourceSet resourceSet = new ResourceSetImpl();
    String file = fileName == null ? "" : fileName;
    URI fileURI = URI.createFileURI(new File(file).getAbsolutePath());
    XMIResourceImpl resource = (XMIResourceImpl) resourceSet.createResource(fileURI);
    resource.setEncoding("utf-8");
    return resource;
  }

  /**
   * @return
   */
  private XMIResourceImpl readXMIResource(String fileName)
  {
  	File file = new File(fileName);
    if (!file.exists())
      return null;

    // create the resource where Foo will live
    ResourceSet resourceSet = new ResourceSetImpl();
    URI fileURI = URI.createFileURI(file.getAbsolutePath());

    File temp = null;
    try
    {
    	
      // if this ends in .uml2z, then ungzip it and repoint the resource
      if (fileName.endsWith(UML2Z_SUFFIX))
      {
      	temp = File.createTempFile("uml2", null);
      	FileUtilities.copyFile(file, temp, true);
      	fileURI = URI.createFileURI(temp.getAbsolutePath());
      }
      
      resourceSet.getPackageRegistry().put(UML2Package.eNS_URI, UML2Package.eINSTANCE);
      XMLResourceImpl read = (XMLResourceImpl) resourceSet.getResource(fileURI, true);
      read.setURI(fileURI);
      read.setEncoding("utf-8");
      return (XMIResourceImpl) read;
    }
    catch (IOException ex)
    {
    	ex.printStackTrace();
    	return null;
    }
    finally
    {
    	if (temp != null)
    		temp.delete();
    }
  }
}
