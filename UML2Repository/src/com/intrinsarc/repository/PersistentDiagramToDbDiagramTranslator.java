package com.intrinsarc.repository;

import java.util.*;

import org.eclipse.uml2.*;

import com.intrinsarc.idraw.foundation.persistence.*;

public class PersistentDiagramToDbDiagramTranslator
{
  private PersistentDiagram diagram;
  
  public PersistentDiagramToDbDiagramTranslator(PersistentDiagram diagram)
  {
    this.diagram = diagram;
  }
  
  public J_Diagram translate(J_DiagramHolder holder)
  {
    UML2Factory factory = UML2Factory.eINSTANCE;
    J_Diagram persistentDiagram = holder.createDiagram();

    // set up the persistent diagram attribute
    persistentDiagram.setLastFigureId(diagram.getLastFigureId());
    makePersistentDiagramProperties(persistentDiagram, diagram);
    
    Map<String, J_Figure> pFigures = new HashMap<String, J_Figure>();
    for (Iterator iter = diagram.getFigures().iterator(); iter.hasNext();)
    {
      PersistentFigure figure = (PersistentFigure) iter.next();

      J_Figure persistentFigure = factory.createJ_Figure();
      persistentFigure.setId(figure.getId());
      persistentFigure.setRecreator(figure.getRecreator());
      persistentFigure.setSubject((Element) figure.getSubject());
      
      // add the properties
      makePersistentFigureProperties(persistentFigure, figure);
      
      // possible links
      persistentFigure.setAnchor1Id(figure.getAnchor1Id());
      persistentFigure.setAnchor2Id(figure.getAnchor2Id());
      
      pFigures.put(figure.getId(), persistentFigure);
    }
    
    // set up the containment hierarchy
    for (Iterator iter = diagram.getFigures().iterator(); iter.hasNext();)
    {
      PersistentFigure figure = (PersistentFigure) iter.next();
      String containerId = figure.getContainerId();
      J_Figure childFigure = pFigures.get(figure.getId());
      
      if (containerId != null)
      {
        J_Figure containerFigure = pFigures.get(containerId);
        containerFigure.getFigures().add(childFigure);
      }
      else
        persistentDiagram.getFigures().add(childFigure);
    }
    return persistentDiagram;
  }
  
  private void makePersistentFigureProperties(J_Figure persistentFigure, PersistentFigure figure)
  {
    PersistentProperties properties = figure.getProperties();
    
    for (Iterator propsIter = properties.iterator(); propsIter.hasNext();)
    {
      PersistentProperty property = (PersistentProperty) propsIter.next();
      if (!property.isDefaultValue())
      {
        boolean optimised = possiblyOptimiseProperty(persistentFigure, property);
        if (!optimised)
        {
          J_Property persistentProperty = persistentFigure.createProperties();
          persistentProperty.setName(property.getName());
          persistentProperty.setValue(property.asString());
        }
      }
    }
  }

  private void makePersistentDiagramProperties(J_Diagram persistentFigureContainer, PersistentDiagram diagram)
  {
    PersistentProperties properties = diagram.getProperties();
    
    for (Iterator propsIter = properties.iterator(); propsIter.hasNext();)
    {
      PersistentProperty property = (PersistentProperty) propsIter.next();
      if (!property.isDefaultValue())
      {
        J_Property persistentProperty = persistentFigureContainer.createProperties();
        persistentProperty.setName(property.getName());
        persistentProperty.setValue(property.asString());
      }
    }
  }   
  
  private boolean possiblyOptimiseProperty(J_Figure pfig, PersistentProperty prop)
  {
    // try to optimise the property by adding it as an attribute
    // this is just a convenience -- properties can either live as an optimised attribute or as
    // a real property -- both are interchangeable
    String name = prop.getName();
    String value = prop.asString();
    boolean optimised = true;
    
    if (name.equals("text"))        pfig.setText(value);               else
    if (name.equals("name"))        pfig.setName(value);               else
    if (name.equals("vtl"))         pfig.setVirtualPoint(value);       else
    if (name.equals("show"))        pfig.setShow(value);               else
    if (name.equals("pts"))         pfig.setPoints(value);             else
    if (name.equals("brOff"))       pfig.setBrOffset(value);           else
    if (name.equals("tlOff"))       pfig.setTlOffset(value);           else
    if (name.equals("auto"))        pfig.setAutosized(value);          else
    if (name.equals("icon"))        pfig.setIcon(value);               else
    if (name.equals("pt"))          pfig.setPoint(value);              else
    if (name.equals("dim"))         pfig.setDimensions(value);         else
    if (name.equals("supA"))        pfig.setSuppressAttributes(value); else
    if (name.equals("supO"))        pfig.setSuppressOperations(value); else
    if (name.equals("supC"))        pfig.setSuppressContents(value);   else
    if (name.equals("off"))         pfig.setOffset(value);             else
    if (name.equals("min"))         pfig.setMin(value);                else
    if (name.equals("acc"))         pfig.setAccessibility(value);      else
    if (name.equals("clSc"))        pfig.setClassifierScope(value);    else
    if (name.equals("type"))        pfig.setType(value);               else
    if (name.equals("contName"))    pfig.setContainedName(value);
    else
      optimised = false;
    return optimised;
  }
}
