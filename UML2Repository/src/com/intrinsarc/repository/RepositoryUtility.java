package com.intrinsarc.repository;

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import org.eclipse.emf.common.util.*;

import com.intrinsarc.deltaengine.errorchecking.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

public class RepositoryUtility
{
	public static final ImageIcon MAIN_FRAME_ICON = IconLoader.loadIcon("xml.png");
	public static final ImageIcon LOCALDB_ICON = IconLoader.loadIcon("database.png");

	public static void useObjectDbRepository(String hostName, String dbName, String baseModel) throws RepositoryOpeningException
	{
	  closeExisting();
	  EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
	  ObjectDbSubjectRepositoryGem repositoryGem = ObjectDbSubjectRepositoryGem.openRepository(hostName, dbName, baseModel);
	  EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = false;
	  SubjectRepositoryFacet repos = repositoryGem.getSubjectRepositoryFacet();
	  GlobalSubjectRepository.repository = repos;
	}
	
	public static void useObjectDbRepository(ObjectDbSubjectRepositoryGem repositoryGem)
	{
	  closeExisting();
	  EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
	  SubjectRepositoryFacet repos = repositoryGem.getSubjectRepositoryFacet();
	  EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = false;
	  GlobalSubjectRepository.repository = repos;
	}
	
	public static void useXMLRepository(String fileName, boolean rememberFileName) throws RepositoryOpeningException
	{
	  closeExisting();
	  EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
	  XMLSubjectRepositoryGem repositoryGem = XMLSubjectRepositoryGem.openFile(fileName, true, rememberFileName);
	  EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = false;
	  SubjectRepositoryFacet repos = repositoryGem.getSubjectRepositoryFacet();
	  GlobalSubjectRepository.repository = repos;
	  setModelVariable(fileName);
	}
	
	public static void setModelVariable(String fileName)
	{
		// set the evolve home directory as the EVOLVE variable
		GlobalPreferences.preferences.setVariableValue(
				"MODEL", 
				fileName == null ? "" : new File(fileName).getParent().toString());
	}

	private static void closeExisting()
	{
  	// clear out the checked once strata
  	CheckOnceStrata.clear();
  	
	  SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
	  if (repository != null)
	    repository.close();
	  repository = null;
	  setModelVariable(null);
	}
	
	public static String chooseFileNameToCreate(JFrame frame, String text, String extensionType, String extension, File startDirectory)
	{
	  return chooseFileNameToCreate(frame, text, new String[]{extensionType}, new String[]{extension}, startDirectory);
	}
	
	public static String chooseFileNameToCreate(JFrame frame, String text, String extensionType[], String extensions[], File startDirectory)
	{
	  // ask for a file to store the model into
	  JFileChooser chooser = new CustomisedFileChooser(startDirectory); 
	  chooser.setDialogTitle(text);
	  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	  List<FileFilter> filters = new ArrayList<FileFilter>();
	  for (int lp = 0; lp < extensionType.length; lp++)
	  {
	  	FileFilter next = new MyFileNameExtensionFilter(extensionType[lp], extensions[lp]);
		  filters.add(next);
	  	chooser.addChoosableFileFilter(next);
	  }
	  if (chooser.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION)
	    return null;
	
	  String fileName = chooser.getSelectedFile().getAbsolutePath();
	  int extIndex = filters.indexOf(chooser.getFileFilter());
	  String extension = extensions[extIndex];
	  if (!fileName.endsWith("." + extension))
	    fileName += "." + extension;
	  return fileName;
	}
	
	public static String chooseFileNameToOpen(JFrame frame, String text, String extensionType, String extension, File startDirectory)
	{
	  return chooseFileNameToOpen(frame, text, extensionType, new String[]{extension}, extension, startDirectory);
	}
	
	public static String chooseFileNameToOpen(JFrame frame, String text, String extensionType, String extensions[], String extension, File startDirectory)
	{
	  // ask for a file to store the model into
	  JFileChooser chooser = new CustomisedFileChooser(startDirectory); 
	  chooser.setDialogTitle(text);
	  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	  List<FileFilter> filters = new ArrayList<FileFilter>();
  	FileFilter next = new MyFileNameExtensionFilter(extensionType, extensions);
	  filters.add(next);
  	chooser.addChoosableFileFilter(next);
	  if (chooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION)
	    return null;
	
	  String fileName = chooser.getSelectedFile().getAbsolutePath();
	  int extIndex = filters.indexOf(chooser.getFileFilter());
	  boolean endsWith = false;
	  for (String ext : extensions)
	  	if (fileName.endsWith("." + ext))
	  		endsWith = true;
	  if (!endsWith)
	  	fileName += "." + extension;
	  return fileName;
	}
	
	public static String[] chooseRemoteDatabase() throws RepositoryOpeningException
	{
	  String choice =
	    (String) JOptionPane.showInputDialog(null, "Enter the database name", "Open remote database", JOptionPane.QUESTION_MESSAGE, null, null, "localhost:database.odb");
	  if (choice == null)
	    return null;
	  if (!choice.contains(":"))
	    throw new RepositoryOpeningException("Database must be in the form hostname:databasename");
	  
	  // create a dialog asking for a hostname (default localhost) and a database path
	  StringTokenizer tokenizer = new StringTokenizer(choice);
	  String host = tokenizer.nextToken(":");
	  String database = tokenizer.nextToken(":");
	  return new String[]{host, database};
	}
}
