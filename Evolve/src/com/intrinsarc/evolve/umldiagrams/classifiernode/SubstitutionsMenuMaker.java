package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.enhanced.*;

public class SubstitutionsMenuMaker
{
  public static JMenuItem getSubstitutionsMenuItem(FigureFacet figure, final ToolCoordinatorFacet coordinator)
  {
    // if this is a composite, it can be substituted
    final Type subject = (Type) figure.getSubject();
    JMenuItem substituters = new JMenuItem("Determine substitutions...");
    substituters.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        IDeltaEngine engine = GlobalDeltaEngine.engine;

        List<? extends DEElement> substituters = engine.locateObject(subject).asElement().getReplacers();
        
        String leadIn = "<html>Substitutions of " + qualName(subject) + "<hr>";
        JScrollPane scroll = makeTextPane(leadIn, substituters);
        
        coordinator.invokeAsDialog(
        		null,
            "Substitutions",
            substituters.isEmpty() ? new JLabel(leadIn + "No substitutions.") : scroll,
            null,
            0,
            null);
      }
    });
    return substituters;
  }

  public static JScrollPane makeTextPane(String title, Collection<? extends DEElement> elements)
  {
    JTextPane text = new JTextPane();
    StringBuilder b = new StringBuilder(title);
    
    // sort alphabetically
    List<DEElement> sorted = new ArrayList<DEElement>(elements);
    Collections.sort(sorted,
        new Comparator<DEElement>()
        {
          public int compare(DEElement n1, DEElement n2)
          {
            return n1.getFullyQualifiedName().compareTo(n2.getFullyQualifiedName());
          }
        });
    
    b.append("<table><tr>");
    for (DEElement st : sorted)
    {
      String name = st.getFullyQualifiedName();
      if (st instanceof Class)
        name += "<td>(Class)</td>";
      if (st instanceof Interface)
        name += "<td>(Interface)</td>";
      b.append("&nbsp;&nbsp;<td>" + name + "</td><tr>");
    }
    text.setEditable(false);
    text.setContentType("text/html");
    text.setText(b.toString());
    text.setOpaque(false);
    text.setBorder(null);
    JScrollPane scroll = new JScrollPane(text);
    scroll.setPreferredSize(new Dimension(400, 200));
    scroll.setBorder(null);
    return scroll;
  }
  
  private static String qualName(Element element)
  {
    final SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
    return repository.getFullyQualifiedName(element, DEObject.SEPARATOR);
  }
}
