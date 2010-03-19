package com.hopstepjump.repositorybase;

import java.io.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

/**
 *
 * (c) Andrew McVeigh 05-Sep-02
 *
 */
public interface SubjectRepositoryFacet extends TransactionManagerFacet
{	
  /**
   * delete undelete and refresh
   */
  public void incrementPersistentDelete(Element element);
  public void decrementPersistentDelete(Element element);
  public void refreshAll();
  public void close();

  /**
   * finder methods
   */
  public Model getTopLevelModel();
  public Collection<NamedElement> findElements(String name, java.lang.Class<?> extentClass, boolean includeSubtypes);
  public Collection<NamedElement> findElementsStartingWithName(String name, java.lang.Class<?> extentClass, boolean includeSubtypes);
  public NamedElement findElement(String name, java.lang.Class<?>[] extentClasses);
  /** return null if uuid not found */
	public NamedElement findNamedElementByUUID(String uuid);
	public Element findElementByUUID(String uuid);
	
	public String save(JFrame frame, boolean keepExistingModificationInfo, File recentDirectory);
  public boolean supportsSaveAs();
  public String saveAs(JFrame frame, File recentDirectory);
  public boolean supportsSaveTo();
  public String saveTo(JFrame frame, String extensionType, String extension, File recentDirectory);
	public SaveInformation getSaveInformation();
	public void resetModified();
	public PersistentDiagram retrievePersistentDiagram(Package pkg) throws RepositoryPersistenceException;
	
	public Command formUpdateDiagramsCommandAfterSubjectChanges(long commandExecutionTime, ViewUpdatePassEnum pass, boolean initialRun);	
	public String getFullyQualifiedName(Element element, String separator);
  public String getFullStratumNames(Element element);

	
	/** listeners to the repository */
	public void addRepositoryListener(SubjectRepositoryListenerFacet listener);
	public void removeRepositoryListener(SubjectRepositoryListenerFacet listener);
  public String getFileName();

  public Package findVisuallyOwningStratum(DiagramFacet diagram, ContainerFacet container);
  public Package findVisuallyOwningPackage(DiagramFacet diagram, ContainerFacet container);
  public Namespace findVisuallyOwningNamespace(DiagramFacet diagram, ContainerFacet container);

  public Element findOwningElement(FigureReference containingReference, EClass class_);
  public Element findOwningElement(FigureReference containingReference, EClass[] classes_);
  public Element findOwningElement(Element element, java.lang.Class<?> cls);
  public Package findOwningStratum(Element element);
  public Package findOwningPackage(Element element);

  public Type findPrimitiveType(String name);
  public Set<Package> findAllPackages();
  
  /** stereotype management */
	public Set<Stereotype> findApplicableStereotypes(EClass ecls);
	public Stereotype findStereotype(EClass ecls, String uuid);
  
  /** permanently destructive operations */
  public void destroy(Element element);
  public int collectGarbage(GarbageUpdaterFacet updater);
  /** read-only handling */
  public boolean isReadOnly(Element element);
  public boolean isContainerContextReadOnly(FigureFacet figure);
  
  /** return a unique string identifying the model */
	public String getModelIdentifier();
}
