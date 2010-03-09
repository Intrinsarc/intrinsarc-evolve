package com.hopstepjump.jumble.alloy;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.w3c.dom.*;
import org.w3c.dom.Node;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

class ConnectorEnd
{
  private NameRecord component;
  private NameRecord port;
  private NameRecord part;
  
  public ConnectorEnd(NameRecord component, NameRecord port, NameRecord part)
  {
    this.component = component;
    this.port = port;
    this.part = part;
  }
  
  public boolean equals(Object o)
  {
    if (!(o instanceof ConnectorEnd))
      return false;
    ConnectorEnd other = (ConnectorEnd) o;
    if (part != other.part && (part == null || other.part == null))
      return false;
    
    return
      component.equals(other.component) &&
      port.equals(other.port) &&
      (part == other.part || part.equals(other.part));
  }
  
  public int hashCode()
  {
    return component.hashCode() ^ (part == null ? 0 : part.hashCode()) ^ port.hashCode();
  }
}

class ComponentPort
{
  private NameRecord component;
  private NameRecord port;
  
  public ComponentPort(NameRecord component, NameRecord port)
  {
    this.component = component;
    this.port = port;
  }
  
  public boolean equals(Object o)
  {
    if (!(o instanceof ComponentPort))
      return false;
    ComponentPort other = (ComponentPort) o;
    return component.equals(other.component) && port.equals(other.port);
  }
  
  public int hashCode()
  {
    return component.hashCode() ^ port.hashCode();
  }
}

public class GraphExpandedInterpretAlloyCommand extends GraphResemblanceInterpretAlloyCommand
{
  private static final int SPACING = 300;
  private static final int PER_ROW = 3;
  private static final int X_START = 100;
  private static final int Y_START = 100;
  
  public GraphExpandedInterpretAlloyCommand(ToolCoordinatorFacet toolCoordinator, DiagramFacet diagram, Document document, boolean newStyleNaming)
  {
    super(toolCoordinator, diagram, document, newStyleNaming);
  }
  
  protected void graphExpanded(final DiagramFacet diagram, final Stratum stratum, Set<NameRecord> transitivePlusMe) throws InterpretAlloyException
  {
    // determine anything that has been substituted, so we can exclude it
    final Set<NameRecord> substitutes = new HashSet<NameRecord>();
    xpathForEach("//field[@name='substitutes']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractTwoAtoms(node);
        substitutes.add(rec[0]);
      }
    });
    
    // work out if the component is invalid in this stratum
    final Set<NameRecord> invalid = new HashSet<NameRecord>();
    xpathForEach("//field[@name='isInvalid_e']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractTwoAtoms(node);
        if (stratum.getNumber().equals(rec[1].getNumber()))
            invalid.add(rec[0]);
      }
    });
    
    // graph the resemblance, from this stratum's perspective
    Map<ConnectorEnd, FigureFacet> ends = new HashMap<ConnectorEnd, FigureFacet>();
    Map<ComponentPort, FigureFacet> portMap = new HashMap<ComponentPort, FigureFacet>();
    graphComponentsAndInterfaces(diagram, stratum, substitutes, invalid, ends, transitivePlusMe);
    graphComponentOutsides(diagram, stratum, substitutes, invalid, portMap, ends, transitivePlusMe);
    graphComponentInsides(diagram, stratum, substitutes, invalid, portMap, ends, transitivePlusMe);
    
    // resize the diagram before drawing connectors and resemblance
    diagram.resizeEntireDiagram();
    
    graphComponentConnectors(diagram, stratum, substitutes, invalid, portMap, ends, transitivePlusMe);
    graphInferredPortLinks(diagram, stratum, substitutes, invalid, portMap, ends, transitivePlusMe);
    
    // handle interface subtyping
    xpathForEach("//field[@name='superTypes']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractThreeAtoms(node);
        
        if (rec[2].getNumber().equals(stratum.getNumber()))
        {
          // graph between the two
          FigureFacet from = retrieveFigure(diagram, "" + diagram + rec[0].getFullName());
          FigureFacet to = retrieveFigure(diagram, "" + diagram + rec[1].getFullName());
          createSpecialisation(diagram, from, to);
        }
      }
    });
  }

  private void graphComponentsAndInterfaces(final DiagramFacet diagram, final Stratum stratum, final Set<NameRecord> substitutes, final Set<NameRecord> invalid, Map<ConnectorEnd, FigureFacet> ends, final Set<NameRecord> transitivePlusMe) throws InterpretAlloyException
  {
    final int current[] = {0};
    xpathForEach("//field[@name='ownedElements']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractTwoAtoms(node);
        if (transitivePlusMe.contains(rec[0]))
        {
          final String elementName = rec[1].getFullName();

          if (!substitutes.contains(rec[1]))
          {
            // draw the element -- it is relevant here
            if (rec[1].getName().equals("Component"))
            {
              final FigureFacet component =
                createComponent(
                    diagram,
                    null,
                    elementName,
                    "" + diagram + elementName,
                    invalid.contains(rec[1]),
                    !composites.contains(rec[1]),
                    new UPoint(X_START + (current[0] % PER_ROW) * SPACING, X_START + (current[0] / PER_ROW) * SPACING),
                    null,
                    false);

              // add any attributes
              extractObjects_e(rec[1], "Attributes", stratum,
                new DeltaPrinter()
                {
                  public String printNew(NameRecord[] rec) throws InterpretAlloyException
                  {
                    // get the attribute type
                    NameRecord type =
                      xpathLocateName("//field[@name='attributeType']/tuple/atom[@name='FNAME/" + rec[3].getIndexedFullName() + "']/following-sibling::*");
                    NameRecord defaultValue =
                      xpathLocateName("//field[@name='defaultValue']/tuple/atom[@name='FNAME/" + rec[3].getIndexedFullName() + "']/following-sibling::*");
                    
                    // add an attribute
                    createAttribute(
                      diagram,
                      component,
                      elementName,
                      rec[2].getFullName(),
                      type.getFullName(),
                      defaultValue != null ? defaultValue.getFullName() : null,
                      new PersistentProperties());
                    return null;
                  }
                });              
            }
            else
            {
              final FigureFacet iface =
                createInterface(
                    diagram,
                    null,
                    elementName,
                    false,
                    "" + diagram + elementName,
                    invalid.contains(rec[1]),
                    new UPoint(X_START + (current[0] % PER_ROW) * SPACING, X_START + (current[0] / PER_ROW) * SPACING),
                    null);
              
              // add any operations
              extractObjects_e(rec[1], "Operations", stratum,
                new DeltaPrinter()
                {
                  public String printNew(NameRecord[] rec)
                  {
                    // add an operation
                    createOperation(
                        diagram,
                        iface,
                        elementName,
                        rec[2].getFullName() + "(...) -> " + rec[3].getFullName(),
                        new PersistentProperties());                    
                    return null;
                  }
                });
              // add any implementations (as attributes!)
              extractObjects_e(rec[1], "Implementation", stratum,
                new DeltaPrinter()
                {
                  public String printNew(NameRecord[] rec)
                  {
                    // add an implementation
                    createAttribute(
                        diagram,
                        iface,
                        elementName,
                        rec[2].getFullName(),
                        rec[3].getFullName(),
                        null,
                        new PersistentProperties());                    
                   
                    return null;
                  }
                });
            }
          }

          current[0]++;
        }
      }
    });
  }

  private void graphComponentOutsides(
      final DiagramFacet diagram,
      final Stratum stratum,
      final Set<NameRecord> substitutes,
      final Set<NameRecord> invalid,
      final Map<ComponentPort, FigureFacet> portMap,
      final Map<ConnectorEnd, FigureFacet> ends,
      final Set<NameRecord> transitivePlusMe) throws InterpretAlloyException
  {
    xpathForEach("//field[@name='ownedElements']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractTwoAtoms(node);
        if (transitivePlusMe.contains(rec[0]))
        {
          final String elementName = rec[1].getFullName();

          // draw the element -- it is relevant here
          if (rec[1].getName().equals("Component") && !substitutes.contains(rec[1]))
          {
            final NameRecord component = rec[1];
            final FigureFacet owningComponent = retrieveFigure(diagram, "" + diagram + elementName);

            // add any ports and interfaces
            final int[] portCount = {0};
            extractObjects_e(rec[1], "Ports", stratum,
              new DeltaPrinter()
              {
                public String printNew(NameRecord[] rec) throws InterpretAlloyException
                {
                  final NameRecord portRec = rec[3];
                  NameRecord portIDRec = rec[2];
                  final String portName = portIDRec.getFullName();
                  final FigureFacet portFigure =
                    createPort(
                        diagram,
                        owningComponent,
                        portName,
                        "" + component + diagram + portName,
                        portMultiplicity.get(portRec),
                        owningComponent.getFullBounds().getPoint().add(new UDimension(-20, portCount[0] * 20)), null);
                  portMap.put(new ComponentPort(component, portRec), portFigure);
                  ends.put(new ConnectorEnd(component, portRec, null), portFigure);
                  
                  // now graph any referred to interfaces and indicate if they are
                  // provided or required
                  for (int lp = 0; lp < 2; lp++)
                  {
                    final int type = lp;
                    String interfaceRelation = lp == 0 ? "provided" : "required";

                    String xpath = "//field[@name='" + interfaceRelation + "']/tuple";
                    xpathForEach(xpath, new XPathExpressionWatcher()
                    {
                      public void matchedExpression(Node node, NameRecord name, int nodeCount) throws InterpretAlloyException
                      {
                        final NameRecord rec[] = extractFourAtoms(node);

                        // only use if this is the correct stratum and component
                        if (rec[2].getNumber().equals(stratum.getNumber()) &&
                            rec[3].equals(component) && rec[0].equals(portRec))
                        {                        
                          String interfaceDisplayName = rec[1].getFullName();
                          FigureFacet interfaceFigure =
                            createInterface(
                              diagram,
                              null,
                              interfaceDisplayName,
                              true,
                               diagram + interfaceDisplayName + portName,
                              invalid.contains(rec[1]),
                              owningComponent.getFullBounds().getPoint().add(new UDimension(-40 + portCount[0] * 20,  -60)),
                              new PersistentProperties());
  
                          // create the provided interface
                          if (type == 0)
                            createProvidedInterface(diagram, portFigure, interfaceFigure);
                          else
                            createRequiredInterface(diagram, portFigure, interfaceFigure);
                        }
                      }
                    });
                  }
                  portCount[0]++;
                  return null;
                }
              });
          }
        }
      }
    });
  }
  
private void graphComponentConnectors(
    final DiagramFacet diagram,
    final Stratum stratum,
    final Set<NameRecord> substitutes,
    final Set<NameRecord> invalid,
    final Map<ComponentPort, FigureFacet> portMap,
    final Map<ConnectorEnd, FigureFacet> ends,
    final Set<NameRecord> transitivePlusMe) throws InterpretAlloyException
{
  // now draw connectors
  xpathForEach("//field[@name='ownedElements']/tuple", new XPathExpressionWatcher()
  {
    public void matchedExpression(Node node, NameRecord name, int nodeCount)
        throws InterpretAlloyException
    {
      final NameRecord rec[] = extractTwoAtoms(node);
      if (transitivePlusMe.contains(rec[0]))
      {
        final String elementName = rec[1].getFullName();

        // draw the element -- it is relevant here
        if (rec[1].getName().equals("Component") && !substitutes.contains(rec[1]))
        {
          final NameRecord component = rec[1];
          final FigureFacet owningComponent = retrieveFigure(diagram, "" + diagram + elementName);
          
          // add any connectors
          extractObjects_e(rec[1], "Connectors", stratum,
            new DeltaPrinter()
            {
              public String printNew(NameRecord[] rec) throws InterpretAlloyException
              {
                NameRecord connectorID = rec[2];
                final NameRecord connector = rec[3];
                final NameRecord[] ports = new NameRecord[2];
                final NameRecord[] cparts = new NameRecord[2];
                
                final NameRecord cends[] = new NameRecord[2];
                final NameRecord index[] = new NameRecord[2];
                final int currentEnd[] = {0};

                // get any ends
                xpathForEach("//field[@name='ends']/tuple", new XPathExpressionWatcher()
                {
                  public void matchedExpression(Node node, NameRecord name, int nodeCount)
                      throws InterpretAlloyException
                  {
                    final NameRecord rec[] = extractTwoAtoms(node);
                    if (rec[0].equals(connector))
                    {
                      NameRecord ind =
                        xpathLocateName("//field[@name='index']/tuple/atom[@name='FNAME/" + rec[1].getIndexedFullName() + "']/following-sibling::*");

                      index[currentEnd[0]] = ind;
                      cends[currentEnd[0]++] = rec[1];
                    }
                  }
                });
                
                // extract the port of the connector end
                xpathForEach("//field[@name='port']/tuple", new XPathExpressionWatcher()
                {
                  public void matchedExpression(Node node, NameRecord name, int nodeCount)
                      throws InterpretAlloyException
                  {
                    final NameRecord rec[] = extractFourAtoms(node);
                    for (int lp = 0; lp < 2; lp++)
                    {
                      if (rec[0].equals(cends[lp]) &&
                          rec[2].getNumber().equals(stratum.getNumber()) &&
                          rec[3].equals(component))
                      {
                        ports[lp] = rec[1];
                        // if this is [1] for multiplicity then clear the index
                        String mult = portMultiplicity.get(ports[lp]);
                        if (mult == null)
                          index[lp] = null;
                      }
                    }
                  }
                });

                xpathForEach("//field[@name='cpart']/tuple", new XPathExpressionWatcher()
                {
                  public void matchedExpression(Node node, NameRecord name, int nodeCount)
                      throws InterpretAlloyException
                  {
                    final NameRecord rec[] = extractFourAtoms(node);
                    for (int lp = 0; lp < 2; lp++)
                      if (rec[0].equals(cends[lp]) &&
                          rec[2].getNumber().equals(stratum.getNumber()) &&
                          rec[3].equals(component))
                      {
                        cparts[lp] = rec[1];
                      }
                  }
                });
                
                FigureFacet endpoints[] = new FigureFacet[2];
                for (int lp = 0; lp < 2; lp++)
                {
                  endpoints[lp] = ends.get(new ConnectorEnd(component, ports[lp], cparts[lp]));
                }
                
                // we are ready to create the connector now
                if (endpoints[0] != null && endpoints[1] != null)
                {
                  createConnector(
                      diagram,
                      endpoints[0],
                      index[0] == null ? -1 : translateIndex(index[0]),
                      endpoints[1],
                      index[1] == null ? -1 : translateIndex(index[1]),
                      connectorID.getFullName(),
                      component.getFullName());
                }
                else
                {
                  // add an attribute to tell about the error...
                  createAttribute(
                      diagram,
                      owningComponent,
                      elementName,
                      connectorID.getFullName() + " is faulty",
                      null,
                      null,
                      new PersistentProperties());
                }
                return null;
              }
            });
        }
      }
    }
  });
}

  

private void graphInferredPortLinks(
    final DiagramFacet diagram,
    final Stratum stratum,
    final Set<NameRecord> substitutes,
    final Set<NameRecord> invalid,
    final Map<ComponentPort, FigureFacet> portMap,
    final Map<ConnectorEnd, FigureFacet> ends,
    final Set<NameRecord> transitivePlusMe) throws InterpretAlloyException
{
  // now draw connectors
  xpathForEach("//field[@name='ownedElements']/tuple", new XPathExpressionWatcher()
  {
    public void matchedExpression(Node node, NameRecord name, int nodeCount)
        throws InterpretAlloyException
    {
      final NameRecord rec[] = extractTwoAtoms(node);
      if (transitivePlusMe.contains(rec[0]))
      {
        // draw the element -- it is relevant here
        if (rec[1].getName().equals("Component") && !substitutes.contains(rec[1]))
        {
          final NameRecord component = rec[1];
          
          // add any inferred links
          xpathForEach("//field[@name='inferredLinks']/tuple", new XPathExpressionWatcher()
          {
            public void matchedExpression(Node node, NameRecord name, int nodeCount)
                throws InterpretAlloyException
            {
              final NameRecord rec2[] = extractFourAtoms(node);
              
              // is this for the correct stratum and component?
              if (rec2[0].equals(component) && rec2[3].getNumber().equals(stratum.getNumber()))
              {
                FigureFacet endpoints[] = new FigureFacet[2];
                for (int lp = 0; lp < 2; lp++)
                  endpoints[lp] = ends.get(new ConnectorEnd(component, rec2[lp+1], null));
                
                // we are ready to create the connector now
                createInferredPortLink(
                    diagram,
                    endpoints[0],
                    endpoints[1]);
              }
            }
          });          
        }
      }
    }
  });
}

  

private void graphComponentInsides(
      final DiagramFacet diagram,
      final Stratum stratum,
      final Set<NameRecord> substitutes,
      final Set<NameRecord> invalid,
      final Map<ComponentPort, FigureFacet> portMap,
      final Map<ConnectorEnd, FigureFacet> ends,
      final Set<NameRecord> transitivePlusMe) throws InterpretAlloyException
  {
    xpathForEach("//field[@name='ownedElements']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractTwoAtoms(node);
        if (transitivePlusMe.contains(rec[0]))
        {
          final String elementName = rec[1].getFullName();

          // draw the element -- it is relevant here
          if (rec[1].getName().equals("Component") && !substitutes.contains(rec[1]))
          {
            final NameRecord component = rec[1];
            final FigureFacet owningComponent = retrieveFigure(diagram, "" + diagram + elementName);
            final int[] partsInComponent = {0};

            // add any parts
            extractObjects_e(rec[1], "Parts", stratum,
              new DeltaPrinter()
              {
                public String printNew(NameRecord[] rec) throws InterpretAlloyException
                {
                  // get the attribute type
                  final NameRecord type =
                    xpathLocateName("//field[@name='partType']/tuple/atom[@name='FNAME/" + rec[3].getIndexedFullName() + "']/following-sibling::*");
                  // find the type in the current diagram
                  FigureFacet typeFigure = retrieveFigure(diagram, "" + diagram + type.getFullName());
                  Class classifierType = (Class) typeFigure.getSubject();
                  final NameRecord part = rec[3];

                  // add an part
                  final FigureFacet partFigure = 
                    createPart(
                      diagram,
                      owningComponent,
                      classifierType,
                      elementName,
                      rec[2].getFullName(),
                      new UPoint(owningComponent.getFullBounds().getPoint()
                          .add(new UDimension(50 + (partsInComponent[0] % 2) * 150, 50 + (partsInComponent[0] / 2) * 80))),
                          new PersistentProperties());
 
                  // graph the port instances, ensuring that the port remap info is used
                  final int portInstanceCount[] = {0};
                  extractObjects_e(type, "Ports", stratum,
                      new DeltaPrinter()
                      {
                        public String printNew(NameRecord[] rec) throws InterpretAlloyException
                        {
                          NameRecord portRec = rec[3];
                          final NameRecord portIDRec = rec[2];
                          
                          // find the portmap relating to this by looking at the remaps
                          final String oldID[] = {null};
                          xpathForEach("//field[@name='portRemap']/tuple", new XPathExpressionWatcher()
                          {
                            public void matchedExpression(Node node, NameRecord name, int nodeCount)
                                throws InterpretAlloyException
                            {
                              final NameRecord rec[] = extractThreeAtoms(node);
                              if (rec[0].equals(part) && rec[1].equals(portIDRec))
                                oldID[0] = rec[2].getFullName();
                            }
                          });

                          final FigureFacet portInstanceFigure =
                            createPortInstance(
                                diagram,
                                partFigure,
                                oldID[0] == null ? "" : " (now " + oldID[0] + ")",
                                "" + diagram + component + portRec + part,
                                partFigure.getFullBounds().getPoint().add(new UDimension(-40 + portInstanceCount[0] * 20,  -60)),
                                (Port) portMap.get(new ComponentPort(type, portRec)).getSubject(),
                                null);
                          portInstanceCount[0]++;
                          ends.put(new ConnectorEnd(component, portRec, part), portInstanceFigure);

                          return null;
                        }
                      });
                  
                  
                  // add any slots
                  xpathForEach("//field[@name='attributeValues']/tuple", new XPathExpressionWatcher()
                  {
                    public void matchedExpression(Node node, NameRecord name, int nodeCount)
                        throws InterpretAlloyException
                    {
                      final NameRecord rec[] = extractThreeAtoms(node);
                      if (rec[0].equals(part))
                      {
                        createSlot(
                            diagram,
                            partFigure,
                            part.getFullName(),
                            rec[1].getFullName() + " = " + rec[2].getFullName(),
                            false,
                            new PersistentProperties());
                      }
                    }
                  });
                  xpathForEach("//field[@name='attributeCopyValues']/tuple", new XPathExpressionWatcher()
                  {
                    public void matchedExpression(Node node, NameRecord name, int nodeCount)
                        throws InterpretAlloyException
                    {
                      final NameRecord rec[] = extractThreeAtoms(node);
                      if (rec[0].equals(part))
                      {
                        createSlot(
                            diagram,
                            partFigure,
                            part.getFullName(),
                            rec[1].getFullName() + " = " + rec[2].getFullName(),
                            false,
                            new PersistentProperties());
                      }
                    }
                  });
                  xpathForEach("//field[@name='attributeAliases']/tuple", new XPathExpressionWatcher()
                  {
                    public void matchedExpression(Node node, NameRecord name, int nodeCount)
                        throws InterpretAlloyException
                    {
                      final NameRecord rec[] = extractThreeAtoms(node);
                      if (rec[0].equals(part))
                      {
                        createSlot(
                            diagram,
                            partFigure,
                            part.getFullName(),
                            rec[1].getFullName() + " (" + rec[2].getFullName() + ")",
                            true,
                            new PersistentProperties());
                      }
                    }
                  });
                  
                  partsInComponent[0]++;
                  return null;
                }
              });

          }
        }
      }
    });
  }
      
  // name should have the first capital: e.g. Operations
  private void extractObjects_e(final NameRecord owned, String name, final Stratum s, final DeltaPrinter printer) throws InterpretAlloyException
  {
    // find the correct parts info
    final NameRecord delta =
      xpathLocateName("//field[@name='my" + name + "']/tuple/atom[@name='FNAME/" + owned.getIndexedFullName() + "']/following-sibling::*");
    if (delta == null)
      return;
    
    xpathForEach("//field[@name='objects_e']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        NameRecord rec[] = extractFourAtoms(node);
        if (rec[0].equals(delta) && rec[1].getNumber().equals(s.getNumber()))
          printer.printNew(rec);
      }
    });
  }
}
