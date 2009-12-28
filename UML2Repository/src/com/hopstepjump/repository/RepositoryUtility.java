package com.hopstepjump.repository;

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import org.eclipse.emf.common.util.*;

import com.hopstepjump.deltaengine.errorchecking.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;

public class RepositoryUtility
{
  public static CommandManagerListenerFacet useObjectDbRepository(String hostName, String dbName)
  throws RepositoryOpeningException
	{
	  closeExisting();
	  ObjectDbSubjectRepositoryGem repositoryGem = ObjectDbSubjectRepositoryGem.openRepository(hostName, dbName);
	  SubjectRepositoryFacet repos = repositoryGem.getSubjectRepositoryFacet();
	  GlobalSubjectRepository.repository = repos;
	  return repositoryGem.getCommandManagerListenerFacet();
	}
	
	public static CommandManagerListenerFacet useObjectDbRepository(ObjectDbSubjectRepositoryGem repositoryGem)
	{
	  closeExisting();
	  SubjectRepositoryFacet repos = repositoryGem.getSubjectRepositoryFacet();
	  GlobalSubjectRepository.repository = repos;
	  return repositoryGem.getCommandManagerListenerFacet();
	}
	
	public static CommandManagerListenerFacet useXMLRepository(String fileName) throws RepositoryOpeningException
	{
	  closeExisting();
	  EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
	  XMLSubjectRepositoryGem repositoryGem = XMLSubjectRepositoryGem.openFile(fileName, true);
	  EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = false;
	  SubjectRepositoryFacet repos = repositoryGem.getSubjectRepositoryFacet();
	  GlobalSubjectRepository.repository = repos;
	  return repositoryGem.getCommandManagerListenerFacet();
	}
	
	private static void closeExisting()
	{
  	// clear out the checked once strata
  	CheckOnceStrata.clear();
  	
	  SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
	  if (repository != null)
	    repository.close();
	  repository = null;
	}
	
	public static String chooseFileName(JFrame frame, String text, String extensionType, String extension, File startDirectory)
	{
	  // ask for a file to store the model into
	  JFileChooser chooser = new JFileChooser(startDirectory); 
	  chooser.setDialogTitle(text);
	  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	  FileFilter filter = new MyFileNameExtensionFilter(extensionType, new String[]{extension});
	  chooser.setFileFilter(filter);
	  if (chooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION)
	    return null;
	
	  String fileName = chooser.getSelectedFile().getAbsolutePath();
	  if (!fileName.endsWith("." + extension) && chooser.getFileFilter() == filter)
	    fileName += "." + extension;
	  return fileName;
	}
	
	public static String chooseFileName(JFrame frame, String text, String extensionType[], String extensions[], File startDirectory)
	{
	  // ask for a file to store the model into
	  JFileChooser chooser = new JFileChooser(startDirectory); 
	  chooser.setDialogTitle(text);
	  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	  List<FileFilter> filters = new ArrayList<FileFilter>();
	  for (int lp = 0; lp < extensionType.length; lp++)
	  {
	  	FileFilter next = new MyFileNameExtensionFilter(extensionType[lp], extensions[lp]);
		  filters.add(next);
	  	chooser.addChoosableFileFilter(next);
	  }
	  if (chooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION)
	    return null;
	
	  String fileName = chooser.getSelectedFile().getAbsolutePath();
	  int extIndex = filters.indexOf(chooser.getFileFilter());
	  String extension = extensions[extIndex];
	  if (!fileName.endsWith("." + extension))
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
