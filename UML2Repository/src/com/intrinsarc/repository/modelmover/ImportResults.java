package com.intrinsarc.repository.modelmover;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.repositorybase.*;

public class ImportResults
{
	private List<Element> tops = new ArrayList<Element>();
	private List<SavedReference> leftOverOutsideReferences = new ArrayList<SavedReference>();
	private Set<String> safelyRemoved = new HashSet<String>();
	private Set<String> unsafelyRemoved = new HashSet<String>();
	private Set<Package> replacedPackages = new LinkedHashSet<Package>();
	
	public ImportResults()
	{
	}
	
	public void addLeftOverOutsideReference(SavedReference ref)
	{
		leftOverOutsideReferences.add(ref);
	}

	public List<SavedReference> getLeftOverOutsideReferences()
	{
		return leftOverOutsideReferences;
	}

	public void addSafelyRemoved(String uuid)
	{
		safelyRemoved.add(uuid);
	}

	public Set<String> getSafelyRemoved()
	{
		return safelyRemoved;
	}

	public void addUnsafelyRemoved(String uuid)
	{
		unsafelyRemoved.add(uuid);
	}

	public Set<String> getUnsafelyRemoved()
	{
		return unsafelyRemoved;
	}
	
	public void addReplacedPackage(Package pkg)
	{
		replacedPackages.add(pkg);
	}
	
	public Set<Package> getReplacedPackages()
	{
		return replacedPackages;
	}

	public void deleteReplacedPackages(boolean delete)
	{
		for (Package p : replacedPackages)
			if (delete)
				GlobalSubjectRepository.repository.incrementPersistentDelete(p);
			else
				GlobalSubjectRepository.repository.decrementPersistentDelete(p);
	}

	public List<Element> getTops()
	{
		return tops;
	}
}
