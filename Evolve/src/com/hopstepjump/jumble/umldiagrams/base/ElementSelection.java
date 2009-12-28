package com.hopstepjump.jumble.umldiagrams.base;

import org.eclipse.uml2.*;

import com.hopstepjump.repositorybase.*;

public class ElementSelection implements Comparable<ElementSelection>
{
  private NamedElement element;
  
  public ElementSelection(NamedElement element)
  {
    this.element = element;
  }
  
  public String getName()
  {
    return element.getName();
  }
  
  public Element getElement()
  {
    // translate into the base...
  	if (element instanceof Classifier)
  		return CommonRepositoryFunctions.translateFromSubstitutingToSubstituted((Classifier) element);
  	return element;
  }
  
  public String getPackageName()
  {
    return GlobalSubjectRepository.repository.getFullyQualifiedName(element.getOwner(), "::");
  }

  @Override
  public boolean equals(Object obj)
  {
    if (!(obj instanceof ElementSelection))
      return false;
    return element == ((ElementSelection) obj).element;
  }

  @Override
  public int hashCode()
  {
    return element.hashCode();
  }

  public int compareTo(ElementSelection other)
  {
    // try the name first
    int compare = getName().compareTo(other.getName());
    if (compare != 0)
      return compare;
    
    // otherwise, use the package
    return getPackageName().compareTo(other.getPackageName());
  }
  
  @Override
  public String toString()
  {
    return "<html><b>" + getName() + "</b>    (from " + getPackageName() + ")";
  }
}
