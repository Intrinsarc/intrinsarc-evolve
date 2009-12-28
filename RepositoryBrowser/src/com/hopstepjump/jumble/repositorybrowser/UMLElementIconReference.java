package com.hopstepjump.jumble.repositorybrowser;

import org.eclipse.uml2.*;


public class UMLElementIconReference
{
  private java.lang.Class<?> type;
  private String name;
  private ShortCutType shortCutType = ShortCutType.NONE;
  private VisibilityKind visibility;
  private UMLIconDeterminer determiner;
  
  public UMLElementIconReference(
      java.lang.Class<?> type,
      String name,
      VisibilityKind visibility,
      ShortCutType shortCutType,
      UMLIconDeterminer determiner)
  {
    this.type = type;
    this.name = name;
    this.visibility = visibility;
    this.shortCutType = shortCutType;
    this.determiner = determiner;
  }
  
  public boolean matches(Element element, ShortCutType shortCutType)
  {
    if (type != element.getClass())
      return false;
    
    if (this.shortCutType != null && this.shortCutType != shortCutType)
      return false;
    
    if (visibility != null && !visibility.equals(getVisibility(element)))
      return false;
    
    if (determiner != null)
      return determiner.isRelevant(element);
    
    return true;
  }
  
  private VisibilityKind getVisibility(Element element)
  {
    if (element instanceof NamedElement)
      return ((NamedElement) element).getVisibility();
    if (element instanceof DeltaDeletedConstituent)
      return ((NamedElement) ((DeltaDeletedConstituent) element).getDeleted()).getVisibility();
    if (element instanceof DeltaReplacedConstituent)
      return ((NamedElement) ((DeltaReplacedConstituent) element).getReplaced()).getVisibility();
    return VisibilityKind.PUBLIC_LITERAL;
  }
  
  public String getName()
  {
  	return name;
  }
}


