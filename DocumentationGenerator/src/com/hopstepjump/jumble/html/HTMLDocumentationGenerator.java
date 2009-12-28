package com.hopstepjump.jumble.html;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.Package;

import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.repositorybase.*;

public class HTMLDocumentationGenerator
{
  private ToolCoordinatorFacet coordinator;
  private CommonRepositoryFunctions common = new CommonRepositoryFunctions();
  
  public static void registerPreferenceSlots()
  {
    // declare the preference types
    PreferenceType directoryType = new PreferenceTypeDirectory();
    PreferenceType stringType = new PreferenceTypeString();
    
    // add slots for html generation
    GlobalPreferences.preferences.addPreferenceSlot(
        new Preference("HTML", "htmlTemplate1"),
        directoryType,
        "The first folder holding the html template.");
    GlobalPreferences.preferences.addPreferenceSlot(
        new Preference("HTML", "htmlTemplate2"),
        directoryType,
        "The second folder holding the html template.");
    GlobalPreferences.preferences.addPreferenceSlot(
        new Preference("HTML", "htmlTemplate3"),
        directoryType,
        "The third folder holding the hmtl template.");
    GlobalPreferences.preferences.addPreferenceSlot(
        new Preference("HTML", "htmlGenerateTo1"),
        directoryType,
        "The first folder to generate to.");
    GlobalPreferences.preferences.addPreferenceSlot(
        new Preference("HTML", "htmlPrefix1"),
        stringType,
        "The first prefix folder.");
    GlobalPreferences.preferences.addPreferenceSlot(
        new Preference("HTML", "htmlGenerateTo2"),
        directoryType,
        "The second folder to generate to.");
    GlobalPreferences.preferences.addPreferenceSlot(
        new Preference("HTML", "htmlPrefix2"),
        stringType,
        "The second prefix folder.");
    GlobalPreferences.preferences.addPreferenceSlot(
        new Preference("HTML", "htmlGenerateTo3"),
        directoryType,
        "The third folder to generate to.");
    GlobalPreferences.preferences.addPreferenceSlot(
        new Preference("HTML", "htmlPrefix3"),
        stringType,
        "The third prefix folder.");
  }
  
  public HTMLDocumentationGenerator(
      ToolCoordinatorFacet coordinator)
  {
    this.coordinator = coordinator;
  }
  
  public void generateHTML(final PopupMakerFacet popup, final Icon icon) throws HTMLGenerationException, PreferenceNotFoundException
  {
    // look upwards for the current documentation top
    final Package top = locateTop();
    
    // get the site index
    String index = StereotypeUtilities.extractStringProperty(top, CommonRepositoryFunctions.DOCUMENTATION_SITE_INDEX);
    if (index == null)
      throw new HTMLGenerationException("siteIndex attribute is not set on documentation top");
    
    final String prefix = GlobalPreferences.preferences.getPreference(new Preference("HTML", "htmlPrefix" + index)).asString();
    File htmlResources = new File(
        GlobalPreferences.preferences.getPreference(new Preference("HTML", "htmlTemplate" + index)).asString());    
    final File htmlGenerateTo = new File(
        GlobalPreferences.preferences.getPreference(new Preference("HTML", "htmlGenerateTo" + index)).asString(), prefix);    
    
    popup.displayPopup(
        icon,
        "HTML Generation",
        "Finding html packages",
        ScreenProperties.getUndoPopupColor(),
        Color.black,
        3000,
        true,
        0,
        1);

    // trace down looking for child packages
    final DocumentationPackage topPkg = new DocumentationPackage(top);
    try
    {
      topPkg.locateChildPackages(popup, icon);
    
      popup.displayPopup(
          icon,
          "HTML Generation",
          "Preparing generated directory",
          ScreenProperties.getUndoPopupColor(),
          Color.black,
          3000,
          true,
          0,
          1);

      // load up the html template
      final String htmlTemplateContents;
      try
      {
        htmlTemplateContents = common.loadFileIntoString(new File(htmlResources, "template.html"));

        // remove the generated directory
        common.deleteDir(htmlGenerateTo);
        htmlGenerateTo.mkdirs();

        // copy over the css file
        common.copyFile(
            new File(htmlResources, "site.css"),
            new File(htmlGenerateTo, "site.css"));

        // remove the entire generation directory, and copy over the images
        common.copyDirectory(
            new File(htmlResources, "images"),
            new File(htmlGenerateTo, "images"));
        
        // copy over other resources up to the top level
        common.copyDirectory(
            new File(htmlResources, "resources"),
            htmlGenerateTo);      

      }
      catch (FileNotFoundException ex)
      {
        throw new HTMLGenerationException("Cannot locate file: " + ex.getMessage());
      }
      catch (IOException ex)
      {
        throw new HTMLGenerationException("Cannot copy file or directory: " + ex.getMessage());
      }      
      
      final int totalCount = topPkg.countHierarchyDownwards();
      
      // generate each package in turn
      topPkg.visit(new DocumentationPackageVisitor()
          {
            private int currentCount = 0;

            public void visit(DocumentationPackage pkg) throws HTMLGenerationException, PersistentFigureRecreatorNotFoundException, RepositoryPersistenceException
            {
              try
              {
                popup.displayPopup(
                    icon,
                    "HTML Generation",
                    "Generating for page: " + pkg.getName(),
                    ScreenProperties.getUndoPopupColor(),
                    Color.black,
                    1500,
                    true,
                    currentCount++,
                    totalCount);

                new HTMLPageGenerator(
                    coordinator,
                    topPkg,
                    pkg,
                    htmlTemplateContents,
                    htmlGenerateTo,
                    prefix,
                    extractAttribute(top, CommonRepositoryFunctions.DOCUMENTATION_DOCUMENT_NAME),
                    extractAttribute(top, CommonRepositoryFunctions.DOCUMENTATION_OWNER),
                    extractAttribute(top, CommonRepositoryFunctions.DOCUMENTATION_EMAIL),
                    extractAttribute(top, CommonRepositoryFunctions.DOCUMENTATION_YEARS),
                    extractIntegerStringAttribute(top, CommonRepositoryFunctions.DOCUMENTATION_SPACE_PADDING),
                    extractAttribute(top, CommonRepositoryFunctions.DOCUMENTATION_TITLE_PREFIX)).generate();
              }
              catch (IOException ex)
              {
                throw new HTMLGenerationException("Problem writing file: " + ex.getMessage());
              } 
            }
          });
      
      popup.displayPopup(
          icon,
          "HTML Generation",
          "Finished generating site",
          ScreenProperties.getUndoPopupColor(),
          Color.black,
          1500,
          true,
          totalCount,
          totalCount);
    }
    catch (PersistentFigureRecreatorNotFoundException ex)
    {
      throw new HTMLGenerationException("Cannot find recreator: " + ex.getMessage());
    }
    catch (RepositoryPersistenceException ex)
    {
      throw new HTMLGenerationException("Problem with repository: " + ex.getMessage());
    }
  }
  
  private int extractIntegerStringAttribute(Package pkg, String attributeName)
  {
    String value = extractAttribute(pkg, attributeName);
    try
    {
      return new Integer(value);
    }
    catch (NumberFormatException ex)
    {
      return 0;
    }
  }

  private String extractAttribute(Package pkg, String attributeName)
  {
    String value = StereotypeUtilities.extractStringProperty(pkg, attributeName);
    if (value == null)
      value = "(unknown)";
    return value;
  }

  private Package locateTop() throws HTMLGenerationException
  {
    DiagramViewFacet diagramView = coordinator.getCurrentDiagramView();
    FigureFacet selected = diagramView.getSelection().getFirstSelectedFigure();
    Package current;
    // either take a selected package as the start point, or the diagram's linked package
    if (selected != null && selected.getSubject() instanceof Package)
      current = (Package) selected.getSubject();
    else
      current = (Package) diagramView.getDiagram().getLinkedObject();
    
    // look upwards until this is the top.  if there is no top, throw an exception
    while (current != null && !isTop(current))
      current = (Package) current.getOwner();
    if (current == null)
      throw new HTMLGenerationException("Cannot find a documentation-top package by looking upwards");
    return current;
  }

  private boolean isTop(Package pkg)
  {
    return StereotypeUtilities.isStereotypeApplied(pkg, CommonRepositoryFunctions.DOCUMENTATION_TOP);
  }
  
  public static void sortFiguresByVerticalPosition(List<FigureFacet> figures)
  {
    // now sort based on the y position
    Collections.sort(figures,
        new Comparator<FigureFacet>()
        {
          public int compare(FigureFacet f1, FigureFacet f2)
          {
            double y1 = f1.getFullBounds().getY();
            double y2 = f2.getFullBounds().getY();
            double x1 = f1.getFullBounds().getX();
            double x2 = f2.getFullBounds().getX();
            
            if (y1 == y2)
              return (int) Math.signum(x1 - x2);
            
            return (int) Math.signum(y1 - y2);
          }      
        });
  }
}
