package com.hopstepjump.jumble.html;

import java.awt.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.repositorybase.*;

public class DocumentationPackage
{
  private int level;
  private Package pkg;
  private List<DocumentationPackage> children = new ArrayList<DocumentationPackage>();
  private Set<String> names;
  private String uniqueName;
  private DocumentationPackage parent;
  
  public DocumentationPackage(Package pkg)
  {
    this.pkg = pkg;
    this.level = 0;
    names = new HashSet<String>();
  }
  
  public DocumentationPackage(Package pkg, DocumentationPackage parent, int level, Set<String> names)
  {
    this.pkg = pkg;
    this.parent = parent;
    this.level = level;
    this.names = names;
  }
  
  public List<DocumentationPackage> getChildren()
  {
    return children;
  }
  
  public DocumentationPackage getParent()
  {
    return parent;
  }
  
  public String getName()
  {
    if (level == 0)
      return "Home";
    return pkg.getName();
  }  

  public String getUniqueName()
  {
    return uniqueName;
  }
  
  public int getLevel()
  {
    return level;
  }
  
  public boolean isParentedBy(DocumentationPackage possibleParent)
  {
    DocumentationPackage current = this;
    while (current != null & current != possibleParent)
      current = current.parent;
    return current != null;
  }
  
  public void visit(DocumentationPackageVisitor visitor) throws HTMLGenerationException, PersistentFigureRecreatorNotFoundException, RepositoryPersistenceException
  {
    visitor.visit(this);
    for (DocumentationPackage child : children)
      child.visit(visitor);
  }
  
  
  public void locateChildPackages(PopupMakerFacet popup, Icon icon) throws PersistentFigureRecreatorNotFoundException, RepositoryPersistenceException
  {
    // display a popup
    popup.displayPopup(
        icon,
        "HTML Generation",
        "Found html-included package: " + pkg.getName(),
        ScreenProperties.getUndoPopupColor(),
        Color.black,
        3000,
        true,
        0,
        1);
    
    // name this uniquely by removing any whitespace, and adding an underscore until unique
    // unless it is the top level, in which case it must be index
    if (level == 0)
      uniqueName = "index";
    else
    {
      uniqueName = "";
      int length = pkg.getName().length();
      for (int lp = 0; lp < length; lp++)
      {
        char ch = pkg.getName().charAt(lp);
        if (Character.isLetterOrDigit(ch))
          uniqueName += Character.toLowerCase(ch);
      }
    }
    // now add underscores until unique
    while (names.contains(uniqueName))
      uniqueName += "_";
    names.add(uniqueName);
    
    // load up the diagram, and find all the child packages which refer to this directly
    DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(new DiagramReference(pkg.getUuid()), true);
    
    // add any documentation-included package's figure
    List<FigureFacet> childFigures = new ArrayList<FigureFacet>();
    for (FigureFacet figure : diagram.getFigures())
    {
      // only take packages which are at the top level
      if (figure.getContainedFacet() != null && figure.getContainedFacet().getContainer() != null)
        continue;
      
      Object subject = figure.getSubject();
      if (isDocumentationPackage(subject))
        childFigures.add(figure);
    }
    
    // now sort based on the y position
    HTMLDocumentationGenerator.sortFiguresByVerticalPosition(childFigures);
    
    // now, save the children
    for (FigureFacet figure : childFigures)
    {
      DocumentationPackage childPkg = new DocumentationPackage((Package) figure.getSubject(), this, level + 1, names);
      children.add(childPkg);
      childPkg.locateChildPackages(popup, icon);
    }
  }
  
  public String toString()
  {
    StringBuilder childDescriptions = new StringBuilder();
    for (DocumentationPackage child : children)
      childDescriptions.append(child);
    return indent(level) + pkg.getName() + " (" + uniqueName + ")" + "\n" + childDescriptions;
  }

  private String indent(int level)
  {
    StringBuilder builder = new StringBuilder();
    for (int lp = 0; lp < level; lp++)
      builder.append("    ");
    return builder.toString();
  }
  
  public static boolean isDocumentationPackage(Object subject)
  {
    if (!(subject instanceof Package))
      return false;
    return StereotypeUtilities.isStereotypeApplied(
        (Package) subject,
        CommonRepositoryFunctions.DOCUMENTATION_INCLUDED);
  }

  public DocumentationPackage getFirstLevelParent()
  {
    // travel upwards until we get a first level parent
    if (level == 1)
      return this;
    if (parent == null)
      return null;
    return parent.getFirstLevelParent();
  }

  public List<DocumentationPackage> makeBreadcrumbList()
  {
    List<DocumentationPackage> pkgs = new ArrayList<DocumentationPackage>();
    makeBreadcrumbList(pkgs);
    return pkgs;
  }

  private void makeBreadcrumbList(List<DocumentationPackage> pkgs)
  {
    // ask parent to add themselves
    if (parent != null)
      parent.makeBreadcrumbList(pkgs);
    pkgs.add(this);
  }

  public List<FigureFacet> findYOrderedComments() throws PersistentFigureRecreatorNotFoundException
  {
    // add any documentation-included package's figure
    List<FigureFacet> childFigures = new ArrayList<FigureFacet>();
    DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(new DiagramReference(pkg.getUuid()), true);
    for (FigureFacet figure : diagram.getFigures())
    {
      // only take comments which are at the top level
      if (figure.getContainedFacet() != null && figure.getContainedFacet().getContainer() != null)
        continue;
      
      Object subject = figure.getSubject();
      if (subject instanceof Comment)
        childFigures.add(figure);
    }
    
    // now sort based on the y position
    HTMLDocumentationGenerator.sortFiguresByVerticalPosition(childFigures);
    return childFigures;
  }

  public Date getSiteUpdateTime()
  {
    // ask each child and compare recursively
    return getLatestUpdateTime();
  }

  private Date getLatestUpdateTime()
  {
    Date page = getPageUpdateTime();
    for (DocumentationPackage child : children)
    {
      Date childPage = child.getLatestUpdateTime();
      if (isSecondDateGreater(page, childPage))
        page = childPage;
    }
    return page;
  }

  private boolean isSecondDateGreater(Date first, Date second)
  {
    // if the 2nd date is null, don't bother
    if (second == null)
      return false;
    if (first == null)
      return true;
    return second.after(first);
  }

  public Date getPageUpdateTime()
  {
    // parse the update time for the diagram
    SimpleDateFormat formatter = new SimpleDateFormat(CommonRepositoryFunctions.SAVE_DATE_FORMAT);
    try
    {
      if (pkg.getJ_diagramHolder() == null)
        return null;
      
      return formatter.parse(pkg.getJ_diagramHolder().getSaveTime());
    }
    catch (ParseException e)
    {
      return null;
    }
  }

  public String getPageUpdater()
  {
    if (pkg.getJ_diagramHolder() == null)
      return null;
    return pkg.getJ_diagramHolder().getSavedBy();
  }

  public DocumentationPackage locateMatching(Package toFind)
  {
    if (pkg == toFind)
      return this;
    for (DocumentationPackage child : children)
    {
      DocumentationPackage possible = child.locateMatching(toFind);
      if (possible != null)
        return possible;
    }
    return null;
  }
  
  public int countHierarchyDownwards()
  {
    int count = 0;
    for (DocumentationPackage child : children)
      count+= child.countHierarchyDownwards();
    return count + 1;
    
  }
}
