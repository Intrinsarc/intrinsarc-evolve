package com.hopstepjump.repositorybase;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.idraw.foundation.persistence.*;

public class DbDiagramToPersistentDiagramTranslator
{
  private Package pkg;
  private J_Diagram jDiagram;
  
  public DbDiagramToPersistentDiagramTranslator(Package pkg, J_Diagram jDiagram)
  {
    this.pkg = pkg;
    this.jDiagram = jDiagram;
  }
  
  public PersistentDiagram translate()
  {
    // now, turn the instances of the diagram meta-model into persistent classes
    PersistentDiagram diagram = new PersistentDiagram(pkg, pkg.getUuid(), jDiagram.getName(), jDiagram.getLastFigureId());
    copyProperties(jDiagram, diagram.getProperties());
    Map<String, PersistentFigure> figures = new HashMap<String, PersistentFigure>();
    for (Object obj : jDiagram.getFigures())
    {
      J_Figure pfig = (J_Figure) obj;
      addNewFigure(diagram, null, pfig, figures);
    }
    
    // handle any links
    for (Iterator linkIter = figures.values().iterator(); linkIter.hasNext();)
    {
      PersistentFigure fig = (PersistentFigure) linkIter.next();
      if (fig.getAnchor1Id() != null)
      {
        PersistentFigure anchor1 = figures.get(fig.getAnchor1Id());
        PersistentFigure anchor2 = figures.get(fig.getAnchor2Id());
        anchor1.getLinkIds().add(fig.getId());
        anchor2.getLinkIds().add(fig.getId());
      }
    }
    return diagram;
  }
  
  private void addNewFigure(PersistentDiagram diagram, String containerId, J_Figure jfig, Map<String, PersistentFigure> figures)
  {
    String id = jfig.getId();
    PersistentFigure fig = new PersistentFigure(id, jfig.getRecreator());
    fig.setSubject(jfig.getSubject());
    fig.setAnchor1Id(jfig.getAnchor1Id());
    fig.setAnchor2Id(jfig.getAnchor2Id());
    fig.setContainerId(containerId);
    copyProperties(jfig, fig.getProperties());
    diagram.addFigure(fig);
    figures.put(fig.getId(), fig);
    
    // handle any children
    for (Iterator childIter = jfig.getFigures().iterator(); childIter.hasNext();)
    {
      J_Figure child = (J_Figure) childIter.next();
      addNewFigure(diagram, id, child, figures);
      fig.getContentIds().add(child.getId());
    }
  }
  
  private void copyProperties(J_Diagram pdiag, PersistentProperties properties)
  {
    Collection pprops = pdiag.getProperties();
    Iterator propIter = pprops.iterator();
    while (propIter.hasNext())
    {
      J_Property prop = (J_Property) propIter.next();
      properties.add(new PersistentProperty(prop.getName(), prop.getValue()));
    }
  }
  
  private void copyProperties(J_Figure pfig, PersistentProperties properties)
  {
    Collection pprops = pfig.getProperties();
    Iterator propIter = pprops.iterator();
    while (propIter.hasNext())
    {
      J_Property prop = (J_Property) propIter.next();
      properties.add(new PersistentProperty(prop.getName(), prop.getValue()));
    }
    addOptimisedProperties(pfig, properties);
  }
  
  private void addOptimisedProperties(J_Figure pfig, PersistentProperties props)
  {
    possiblyAddOptimisedProperty(props, "text", pfig.getText());
    possiblyAddOptimisedProperty(props, "name", pfig.getName());
    possiblyAddOptimisedProperty(props, "vtl", pfig.getVirtualPoint());
    possiblyAddOptimisedProperty(props, "show", pfig.getShow());
    possiblyAddOptimisedProperty(props, "pts", pfig.getPoints());
    possiblyAddOptimisedProperty(props, "brOff", pfig.getBrOffset());
    possiblyAddOptimisedProperty(props, "tlOff", pfig.getTlOffset());
    possiblyAddOptimisedProperty(props, "auto", pfig.getAutosized());
    possiblyAddOptimisedProperty(props, "icon", pfig.getIcon());
    possiblyAddOptimisedProperty(props, "pt", pfig.getPoint());
    possiblyAddOptimisedProperty(props, "dim", pfig.getDimensions());
    possiblyAddOptimisedProperty(props, "supA", pfig.getSuppressAttributes());
    possiblyAddOptimisedProperty(props, "supO", pfig.getSuppressOperations());
    possiblyAddOptimisedProperty(props, "supC", pfig.getSuppressContents());
    possiblyAddOptimisedProperty(props, "off", pfig.getOffset());
    possiblyAddOptimisedProperty(props, "min", pfig.getMin());
    possiblyAddOptimisedProperty(props, "acc", pfig.getAccessibility());
    possiblyAddOptimisedProperty(props, "clSc", pfig.getClassifierScope());
    possiblyAddOptimisedProperty(props, "type", pfig.getType());
    possiblyAddOptimisedProperty(props, "contName", pfig.getContainedName());
  }
  
  private void possiblyAddOptimisedProperty(PersistentProperties props, String name, String value)
  {
    if (value != null)
      props.add(new PersistentProperty(name, value));
  }  
}
