package com.hopstepjump.repository;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.jdo.*;
import javax.swing.*;

import org.eclipse.emf.common.notify.*;
import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.notifications.*;
import com.hopstepjump.repository.garbagecollector.*;
import com.hopstepjump.repositorybase.*;

public class ObjectDbSubjectRepositoryGem implements Gem
{
  public static final String UML2DB_SUFFIX_DESCRIPTION = "UML2 database files (.uml2db)";
	public static final String UML2DB_SUFFIX_NO_DOT = "uml2db";
	public static String UML2DB_SUFFIX = ".uml2db";
  private String hostName;
  private String dbName;
  private PersistenceManagerFactory pmf; 
  private PersistenceManager pm;
  private Model topLevel;
  private UndoRedoStackManager undoredo = new UndoRedoStackManager();
  
  private CommonRepositoryFunctions common = new CommonRepositoryFunctions();
  private SubjectRepositoryFacetImpl subjectFacet = new SubjectRepositoryFacetImpl();
  private CommandManagerListenerFacetImpl commandFacet = new CommandManagerListenerFacetImpl();
  private Set<SubjectRepositoryListenerFacet> listeners = new HashSet<SubjectRepositoryListenerFacet>();
  
	
  private Adapter adapter = new Adapter()
  {
    public void notifyChanged(Notification notification)
    {
      undoredo.addNotification(notification);
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
			undoredo.undo();
      for (SubjectRepositoryListenerFacet listener : listeners)
        listener.sendChanges();
		}
		
		public void redoTransaction()
		{
			undoredo.redo();
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
		
		public void enforceTransactionDepth(int depth)
		{
			undoredo.enforceDepth(depth);
		}
		
		////////////////////////////////////////////////////

		
    public Model getTopLevelModel()
    {
      return topLevel;
    }

    public boolean supportsSaveAs()
    {
      return false;
    }

    public String saveAs(JFrame frame, File recentDirectory)
    {
      return null;
    }

    public boolean supportsSaveTo()
    {
      return false;
    }

    public String saveTo(JFrame frame, String extensionType, String extension, File recentDirectory)
    {
      return null;
    }

    public String save(JFrame frame, boolean keepExistingModificationInfo, File recentDirectory)
    {
      int changed = 0;
      
      // save each diagram
      start();
      for (DiagramFacet diagram : GlobalDiagramRegistry.registry.getDiagrams())
      {
        if (!diagram.isClipboard() && diagram.isModified())
        {
          // delete the old diagram
          Package linkedPackage = (Package) diagram.getLinkedObject();
          pm.refresh(linkedPackage);
          J_DiagramHolder holder = linkedPackage.getJ_diagramHolder();
          if (holder != null)
            pm.refresh(holder);
          else
            holder = linkedPackage.createJ_diagramHolder();
          J_Diagram oldJDiagram = holder.getDiagram();

          // save the new diagram
          J_Diagram newJDiagram =new PersistentDiagramToDbDiagramTranslator(diagram.makePersistentDiagram()).translate(holder);
          holder.setDiagram(newJDiagram);

          // possibly delete the old diagram
          if (oldJDiagram != null)
            pm.deletePersistent(oldJDiagram);
          
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
      end();
      return ""; // null indicates failure...
    }

    public SaveInformation getSaveInformation()
    {
      int diagramsToSave = 0;
      for (DiagramFacet diagram : GlobalDiagramRegistry.registry.getDiagrams())
        if (!diagram.isClipboard() && diagram.isModified())
          diagramsToSave++;
      return new SaveInformation(false, diagramsToSave);
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
      if (hostName == null)
        return "localdb >> " + dbName;
      else
        return "remotedb >> " + hostName + ":" + dbName;
    }

    public void startTransaction(String redoName, String undoName)
    {
      start();
      pm.evictAll();
      undoredo.startTransaction(redoName, undoName);
    }

    public void commitTransaction()
    {
      end();
      undoredo.commitTransaction();
      for (SubjectRepositoryListenerFacet listener : listeners)
        listener.sendChanges();
    }

    public void commitTransactionAndForget()
    {
      end();
      undoredo.commitTransactionAndForget();
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
      String filter = "name == \"" + name + "\" && j_deleted == 0";
      return queryPossiblyUnextentedObjects(extentClass, filter, includeSubclasses, false);
    }

    public Collection<NamedElement> findElementsStartingWithName(String name, java.lang.Class< ? > extentClass, boolean includeSubclasses)
    {
      String filter = "name.startsWith(\"" + name + "\") && j_deleted == 0";
      return queryPossiblyUnextentedObjects(extentClass, filter, includeSubclasses, false);
    }

    public void refreshAll()
    {
      pmf.getDataStoreCache().evictAll();
      pm.evictAll();
    }

    public void close()
    {
      GlobalNotifier.getSingleton().removeObserver(adapter);
      pm.close();
    }
    
    public PersistentDiagram retrievePersistentDiagram(Package pkg)
    {
      return common.retrievePersistentDiagram(pkg);
    }

    public Command formUpdateDiagramsCommandAfterSubjectChanges(long commandExecutionTime, boolean isTop, ViewUpdatePassEnum pass, boolean initialRun)
    {
      return common.formUpdateDiagramsCommandAfterSubjectChanges(commandExecutionTime, isTop, pass, initialRun);
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

		public Set<Stereotype> findApplicableStereotypes(EClass ecls)
		{
	    // look for the top level model
	    String filter =
        "extendsMetaModelElement.indexOf(\"'" + ecls.getName() + "'\") != -1";
	    Set<Stereotype> matched = new HashSet<Stereotype>();
	    for (Object obj : queryPossiblyUnextentedObjects(StereotypeImpl.class, filter, false, false))
	    {
      	Stereotype st = (Stereotype) obj;
        if (!st.isAbstract())
	    		matched.add(st);
	    }
	    return matched;
	  }

		/**
		 * find the stereotype of a given name, for a given metamodel class
		 */
		public Stereotype findStereotype(EClass ecls, String uuid)
		{
			return common.findStereotype(this, ecls, uuid);
		}

		public NamedElement findNamedElementByUUID(String uuid)
		{
	    // look for the top level model
	    String filter = "uuid == \"" + uuid + "\"";
	    Collection c = queryPossiblyUnextentedObjects(NamedElementImpl.class, filter, true, false);
	    if (c.size() == 0)
	    	return null;
	    for (Object o : c)
	    	if (!((NamedElement) o).isThisDeleted())
	    		return (NamedElement) o;
	    return null;
	  }

		public Element findElementByUUID(String uuid)
		{
	    // look for the top level model
	    String filter = "uuid == \"" + uuid + "\"";
	    Collection c = queryPossiblyUnextentedObjects(ElementImpl.class, filter, true, false);
	    if (c.size() == 0)
	    	return null;
	    for (Object o : c)
	    	if (!((Element) o).isThisDeleted())
	    		return (Element) o;
	    return null;
	  }

    public void destroy(Element element)
    {
    	boolean current = EMFOptions.CREATE_LISTS_LAZILY_FOR_GET;
    	EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
      element.destroy();
    	EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = current;
      pm.deletePersistent(element);
    }

    public int collectGarbage(GarbageUpdaterFacet updater)
    {
      RepositoryGarbageCollector collector = new RepositoryGarbageCollector(this, updater);
      return collector.collectGarbage();
    }

    public Set<Package> findAllPackages()
    {
      return new HashSet<Package>(queryPossiblyUnextentedObjects(PackageImpl.class, null, false, false));
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
			// no need as changes are committed as they occur
		}
  }
  
  private class CommandManagerListenerFacetImpl implements CommandManagerListenerFacet
  {
    public void commandExecuted()
    {
      for (SubjectRepositoryListenerFacet listener : listeners)
        listener.sendChanges();
    }
  }
  
  public static ObjectDbSubjectRepositoryGem openRepository(String hostName, String dbName) throws RepositoryOpeningException
  {
    ObjectDbSubjectRepositoryGem repository = new ObjectDbSubjectRepositoryGem(hostName, dbName);
    repository.openDatabase();
    return repository;
  }
  
  private ObjectDbSubjectRepositoryGem(String hostName,String dbName)
  {
    this.hostName = hostName;
    this.dbName = dbName;
  }
  
  
  private void openDatabase() throws RepositoryOpeningException
  {
    // Obtain a database connection:
    Properties properties = new Properties();
    properties.setProperty("javax.jdo.PersistenceManagerFactoryClass", "com.objectdb.jdo.PMF");
    if (hostName != null)
    {
      properties.setProperty("javax.jdo.option.ConnectionURL", "objectdb://" + hostName + "/" + dbName);
      properties.setProperty("javax.jdo.option.ConnectionUserName", "admin");
      properties.setProperty("javax.jdo.option.ConnectionPassword", "admin");
    }
    else
    {
      properties.setProperty("javax.jdo.option.ConnectionURL", dbName);
    }
  
    pmf = JDOHelper.getPersistenceManagerFactory(properties, JDOHelper.class.getClassLoader());
    try
    {
      pm = pmf.getPersistenceManager();
    }
    catch (JDODataStoreException ex)
    {
      throw new RepositoryOpeningException(ex.getMessage());
    }
    
    // look for the top level model
    String filter = "name == \"" + CommonRepositoryFunctions.MODEL + "\"";
    Collection c = queryPossiblyUnextentedObjects(ModelImpl.class, filter, false, false);

    // if we don't have a model, make one, otherwise take it
    int size = c.size();
    if (size > 1)
    {
      throw new RepositoryOpeningException("Found " + size + " models called " + CommonRepositoryFunctions.MODEL);
    }
    if (size == 0)
    {
      start();
      topLevel = common.initialiseModel(true);
      pm.makePersistent(topLevel);
      end();
    }
    else
      topLevel = (Model) c.iterator().next();
    GlobalNotifier.getSingleton().addObserver(adapter);
  }

  private Collection queryPossiblyUnextentedObjects(java.lang.Class<?> cls, String filter, boolean includeSubclasses, boolean includeDeleted)
  {
    return queryPossiblyUnextentedObjects(cls, filter, null, null, includeSubclasses, includeDeleted);
  }

  private Collection queryPossiblyUnextentedObjects(java.lang.Class<?> cls, String filter, String parameterDeclaration, Object parameter, boolean includeSubclasses, boolean includeDeleted)
  {
  	if (cls == DependencyImpl.class)
  		return new ArrayList();
    try
    {
      Extent modelExtent = pm.getExtent(cls, true);
      Query q = pm.newQuery(modelExtent, filter);
      if (parameterDeclaration != null)
        q.declareParameters(parameterDeclaration);
      
      // now ask each in turn if they are really true
      ArrayList<Element> notDeleted = new ArrayList<Element>();
      Collection results = (Collection) (parameter != null ? q.execute(parameter) : q.execute());
      for (Object object : results)
      {
        Element element = (Element) object;
        if ((includeDeleted || !element.isThisDeleted()) && (includeSubclasses || element.getClass() == cls))
          notDeleted.add(element);
      }
      
      return notDeleted;
    }
    catch (JDOException ex)
    {
      // must catch this exception, as objectdb does load the schema in
      // until an object of that class has been created
//      System.out.println("Got a JDO exception when querying: " + ex);
//      ex.printStackTrace();
      return new ArrayList();
    }
  }


  private void end()
  {
    pm.currentTransaction().commit();
    EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = false;
  }

  private void middle()
  {
    end();
    start();
  }

  private void start()
  {
    EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
    // ODB2B33 sets retain values to true as a default, whereas ODB1 didn't
    pm.currentTransaction().setRetainValues(false);
    // ODB2B33 has optimistic transactions as a default, whereas ODB1 doesn't
    pm.currentTransaction().setOptimistic(true);
    pm.currentTransaction().begin();
  }

  public SubjectRepositoryFacet getSubjectRepositoryFacet()
  {
    return subjectFacet;
  }


  public CommandManagerListenerFacet getCommandManagerListenerFacet()
  {
    return commandFacet;
  }
}
