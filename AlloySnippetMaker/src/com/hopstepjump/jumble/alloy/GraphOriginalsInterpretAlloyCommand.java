package com.hopstepjump.jumble.alloy;

import java.awt.*;
import java.util.*;

import org.w3c.dom.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;


public abstract class GraphOriginalsInterpretAlloyCommand extends InterpretAlloyCommand
{
  public GraphOriginalsInterpretAlloyCommand(ToolCoordinatorFacet toolCoordinator, DiagramFacet diagram, Document document, boolean newStyleNaming)
  {
    super(toolCoordinator, diagram, document, newStyleNaming);
  }
  
  public void graphOriginals() throws InterpretAlloyException
  {
    // use the dependencies to calculate an order
    xpathForEach("//sig[@name='BNAME/Stratum']/atom", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
      {
        strata.put(name.getFullName(), new Stratum(name.getName(), name.getNumber()));
      }
    });

    // get relaxation information
    xpathForEach("//field[@name='isRelaxed']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractTwoAtoms(node);
        strataRelaxed.put(rec[0].getFullName(), rec[1].getName().equals("True"));
      }
    });

    xpathForEach("//field[@name='dependsOn']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractTwoAtoms(node);
        
        // get the stratum
        Stratum top = strata.get(rec[0].getFullName());
        Stratum bottom = strata.get(rec[1].getFullName());

        top.addDependency(bottom);
      }
    });

    sorted = new ArrayList<Stratum>(strata.values());
    Collections.sort(sorted);
    
    // graph the strata
    int lp = 0;
    for (Stratum stratum : sorted)
    {
      FigureFacet stratumFigure =
        createStratum(
          diagram,
          null,
          stratum.getFullName(),
          new UPoint(350 + lp * 50, lp++ * 175 + 20),
          strataRelaxed.get(stratum.getFullName()));
      stratum.setPackage((org.eclipse.uml2.Package) stratumFigure.getSubject());
    };

    // graph the strata dependencies
    xpathForEach("//field[@name='dependsOn']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractTwoAtoms(node);

        createDependencyArc(
             diagram,
             retrieveFigure(diagram,rec[0].getFullName()),
             retrieveFigure(diagram, rec[1].getFullName()),
             Color.BLACK);
      }
    });
    
    // make a part type map
    xpathForEach("//field[@name='partType']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractTwoAtoms(node);
        partTypes.put(rec[0].getFullName(), rec[1]);
      }
    });
    
    // make a part to port remap
    xpathForEach("//field[@name='portRemap']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractThreeAtoms(node);
        
        // get the map for the part
        final String part = rec[0].getFullName();
        String remap = portRemap.get(part);        
        if (remap == null)
          remap = "";
        else
          remap += ", ";
        
        remap += rec[1].getFullName() + "->" + rec[2].getFullName();
        portRemap.put(part, remap);
      }
    });
    
    // determine the multiplicity of each part
    xpathForEach("//sig[@name='FNAME/Port']/atom", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount) throws InterpretAlloyException
      {
        final NameRecord port = name;
        final int mandatoryEnd[] = {-1}, optionalEnd[] = {-1};
        
        // find all mandatory
        xpathForEach("//field[@name='mandatory']/tuple", new XPathExpressionWatcher()
        {
          public void matchedExpression(Node node, NameRecord name, int nodeCount)
              throws InterpretAlloyException
          {
            final NameRecord rec[] = extractTwoAtoms(node);
            if (rec[0].equals(port))
            {
              int index = translateIndex(rec[1]);
              if (index > mandatoryEnd[0])
                mandatoryEnd[0] = index;
            }
          }
        });
        // find all optional
        xpathForEach("//field[@name='optional']/tuple", new XPathExpressionWatcher()
        {
          public void matchedExpression(Node node, NameRecord name, int nodeCount)
              throws InterpretAlloyException
          {
            final NameRecord rec[] = extractTwoAtoms(node);
            if (rec[0].equals(port))
            {
              int index = translateIndex(rec[1]);
              if (index > optionalEnd[0])
                optionalEnd[0] = index;
            }
          }
        });
        
        // now turn into a multiplicity
        String mult = "[" + (mandatoryEnd[0] + 1);
        if (optionalEnd[0] != -1)
          mult += ".." + (optionalEnd[0] + 1);
        mult += "]";
        
        // don't save if the multiplicity is [1]
        if (!(mandatoryEnd[0] == 0 && optionalEnd[0] == -1))
          portMultiplicity.put(port, mult);
      }
    });

    
    
    
    // make an attribute type map: attr -> attr type
    xpathForEach("//field[@name='attributeType']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractTwoAtoms(node);
        attrTypes.put(rec[0].getFullName(), rec[1].getFullName());
      }
    });
    
    // make a port type map
    xpathForEach("//field[@name='setProvided']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractTwoAtoms(node);
        String port = rec[0].getFullName();
        String desc = portTypesP.get(port);
        if (desc == null)
          desc = "provides ";
        else
          desc += ", ";
        desc += rec[1].getFullName();
        portTypesP.put(port, desc);
      }
    });
    
    final Map<String, String> portTypesR = new HashMap<String, String>();
    xpathForEach("//field[@name='setRequired']/tuple", new XPathExpressionWatcher()
        {
          public void matchedExpression(Node node, NameRecord name, int nodeCount)
              throws InterpretAlloyException
          {
            final NameRecord rec[] = extractTwoAtoms(node);

            String port = rec[0].getFullName();
            String desc = portTypesR.get(port);
            if (desc == null)
              desc = "requires ";
            else
              desc += ", ";
            desc += rec[1].getFullName();
            portTypesR.put(port, desc);
          }
        });

    // get any composites
    xpathForEach("//field[@name='isComposite']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractTwoAtoms(node);
        if (rec[1].getName().equals("True"))
          composites.add(rec[0]);
      }
    });

    
    // graph the components as text
    xpathForEach("//field[@name='ownedElements']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
        final NameRecord rec[] = extractTwoAtoms(node);
        NameRecord owner = rec[0];
        final NameRecord owned = rec[1];
        ownedToStratum.put(rec[1].getFullName(), rec[0].getFullName());
        
        // handle substitution and resemblance
        final String substitutes[] = {null};
        xpathForEach("//field[@name='replaces']/tuple/atom[@name='FNAME/" + owned.getIndexedFullName() + "']/following-sibling::*", new XPathExpressionWatcher()
        {
          public void matchedExpression(Node node, NameRecord name, int nodeCount)
          {
              substitutes[0] = name.getFullName();
          }
        });

        final String resembles[] = {""};
        // does this resemble anything?
        xpathForEach("//field[@name='resembles']/tuple/atom[@name='FNAME/" + owned.getIndexedFullName() + "']/following-sibling::*", new XPathExpressionWatcher()
            {
              public void matchedExpression(Node node, NameRecord name, int nodeCount)
              {
                if (resembles[0].length() != 0)
                  resembles[0] += ", ";
                resembles[0] += name.getFullName();
              }
            });

        FigureFacet stratum = retrieveFigure(diagram, owner.getFullName());
        if (owned.getName().equals("Interface"))
        {
          final String iface = owned.getFullName();
          // handle specialisation in interfaces
          final String specialises[] = {""};
          xpathForEach("//field[@name='specialises']/tuple", new XPathExpressionWatcher()
          {
            public void matchedExpression(Node node, NameRecord name, int nodeCount)
                throws InterpretAlloyException
            {
              final NameRecord rec2[] = extractTwoAtoms(node);
              if (rec2[0].getFullName().equals(iface))
              {
                if (specialises[0].length() == 0)
                  specialises[0] += " specialises " + rec2[1].getFullName();
                else
                  specialises[0] += ", " + rec2[1].getFullName();
              }
            }
          });
          
          String definition;
            definition = "interface " + owned.getFullName();

          // add the resemblance
          if (resembles[0].length() != 0)
            definition += "\n  resembles " + resembles[0];
          if (substitutes[0] != null)
          {
          	if (resembles[0].length() != 0)
          		definition += ",";
            definition += "\n  replaces " + substitutes[0];
          }
            
          definition += "\n{\n";
          
          definition += extractDelta(owned, "Operations",
              new DeltaPrinter()
              {
                public String printNew(NameRecord[] rec)
                {
                  return "    " + rec[1].getFullName() + " " + rec[2].getFullName() + ";\n";
                }
              });
          
          definition += extractDelta(owned, "Implementation",
              new DeltaPrinter()
              {
                public String printNew(NameRecord[] rec)
                {
                  return "    " + rec[1].getFullName() + " " + rec[2].getFullName() + ";\n";
                }
              });
          
          createCommentComponent(diagram, stratum, definition + "}", iface, false, new UPoint(stratum.getFullBounds()
              .getPoint().add(new UDimension(20, 20 + bumpCount("InterfacesInStratum" + owner.getNumber()) * 70))),
              null, null);
        }
        else
        {
          final String componentName = owned.getFullName();
          
          String definition = "component " + componentName;

          // add the resemblance
          if (resembles[0].length() != 0)
            definition += "\n  resembles " + resembles[0];
          if (substitutes[0] != null)
          {
          	if (resembles[0].length() != 0)
          		definition += ",";
            definition += "\n  replace " + substitutes[0];
          }
          definition += "\n{\n";
          
          definition += extractDelta(owned, "Attributes",
              new DeltaPrinter()
              {
                public String printNew(NameRecord[] rec) throws InterpretAlloyException
                {
                  String attr = rec[1].getFullName();
                  String ret = "    " + attrTypes.get(rec[2].getFullName()) + " " + attr;
                  // look for a default value
                  final NameRecord defValue =
                    xpathLocateName("//field[@name='defaultValue']/tuple/atom[@name='FNAME/" + rec[2].getIndexedFullName() + "']/following-sibling::*");
                  if (defValue != null)
                    ret += " = " + defValue.getFullName();
                  return ret + ";\n";
                }
              });
          
          definition += extractDelta(owned, "Ports",
              new DeltaPrinter()
              {
                public String printNew(NameRecord[] rec)
                {
                  String req = portTypesR.get(rec[2].getFullName());
                  String prov = portTypesP.get(rec[2].getFullName());
                  String mult = portMultiplicity.get(rec[2]);
                  return "    " + rec[1].getFullName() + (mult != null ? mult : "") +
                    (prov == null ? "" : " " + prov)  + (req == null ? "" : " " + req) + ";\n";
                }
              });
    
          definition += extractDelta(owned, "Parts",
              new DeltaPrinter()
              {
                public String printNew(NameRecord[] rec) throws InterpretAlloyException
                {
                  String add = "    " + partTypes.get(rec[2].getFullName()).getFullName() + " " + rec[1].getFullName();
                  String remap = portRemap.get(rec[2].getFullName());
                  if (remap != null)
                    add += " (" + remap + ")";
                  
                  Map<String, String> attributeValues = new HashMap<String, String>();
                  Map<String, String> attributeAliases = new HashMap<String, String>();
                  Map<String, String> attributeCopies = new HashMap<String, String>();
                  
                  popuplateAttributeDetails("//field[@name='attributeValues']/tuple", attributeValues, rec[2]);
                  popuplateAttributeDetails("//field[@name='attributeAliases']/tuple", attributeAliases, rec[2]);
                  popuplateAttributeDetails("//field[@name='attributeCopyValues']/tuple", attributeCopies, rec[2]);
                  
                  if (!attributeValues.isEmpty() || !attributeAliases.isEmpty() || !attributeCopies.isEmpty())
                  {
                    add += "\n";
                    for (Map.Entry<String, String> attr : attributeValues.entrySet())
                      add += "        " + attr.getKey() + "(" + attr.getValue() + ");\n";
                    for (Map.Entry<String, String> attr : attributeAliases.entrySet())
                      add += "        " + attr.getKey() + " aliases-environment-attribute " + attr.getValue() + ";\n";
                    for (Map.Entry<String, String> attr : attributeCopies.entrySet())
                      add += "        " + attr.getKey() + "(" + attr.getValue() + ");\n";
                  }
                  else
                    add += ";\n";
                  return add;
                }

                private void popuplateAttributeDetails(String xpath, final Map<String, String> attributes, final NameRecord part)
                  throws InterpretAlloyException
                {
                  xpathForEach(xpath, new XPathExpressionWatcher()
                  {
                    public void matchedExpression(Node node, NameRecord name, int nodeCount)
                        throws InterpretAlloyException
                    {
                      final NameRecord rec2[] = extractThreeAtoms(node);
                      if (rec2[0].equals(part))
                      {
                        attributes.put(rec2[1].getFullName(), rec2[2].getFullName());
                      }
                    }
                  });
                }
              });

          definition += extractDelta(owned, "Connectors",
              new DeltaPrinter()
              {
                public String printNew(NameRecord[] rec) throws InterpretAlloyException
                {
                  final String connID = rec[1].getFullName();
                  final String connFull = rec[2].getFullName();
                  final String def[] = {"    " + connID + " joins "};

                  // handle each end
                  final int lp[] = {0};
                  xpathForEach("//field[@name='ends']/tuple", new XPathExpressionWatcher()
                  {
                    public void matchedExpression(Node node, NameRecord record, int nodeCount)
                        throws InterpretAlloyException
                    {
                        final NameRecord rec[] = extractTwoAtoms(node);
                        if (rec[0].getFullName().equals(connFull))
                        {
                          final String end = rec[1].getFullName();
                          
                          // get the portID
                          xpathForEach("//field[@name='portID']/tuple", new XPathExpressionWatcher()
                          {
                            public void matchedExpression(Node node, NameRecord record, int nodeCount)
                                throws InterpretAlloyException
                            {
                                final NameRecord rec[] = extractTwoAtoms(node);
                                if (rec[0].getFullName().equals(end))
                                  def[0] += rec[1].getFullName();
                            }
                          });
                          
                          // get the possible partID
                          xpathForEach("//field[@name='partID']/tuple", new XPathExpressionWatcher()
                          {
                            public void matchedExpression(Node node, NameRecord record, int nodeCount)
                                throws InterpretAlloyException
                            {
                                final NameRecord rec[] = extractTwoAtoms(node);
                                if (rec[0].getFullName().equals(end))
                                  def[0] += "@" + rec[1].getFullName();
                            }
                          });

                          // add the "to" bit
                          if (lp[0]++ == 0)
                            def[0] += " to " ;
                        }
                    }
                  });
                  return def[0] + ";\n";
                }
              });

          final String portLinks[] = {""};
          xpathForEach("//field[@name='links']/tuple", new XPathExpressionWatcher()
          {
            public void matchedExpression(Node node, NameRecord name, int nodeCount)
                throws InterpretAlloyException
            {
              final NameRecord rec[] = extractThreeAtoms(node);
              if (rec[0].getFullName().equals(componentName))
              {
                  portLinks[0] += "    " + rec[1].getFullName() + " is-linked-to " + rec[2].getFullName() + "\n";
              }
            }
          });
          if (portLinks[0].length() != 0)
            definition += "  port-links:\n" + portLinks[0];
          
          // create the comment now
          createCommentComponent(diagram, stratum, definition + "}", componentName, owned.getName().equals("Leaf"), new UPoint(stratum.getFullBounds()
              .getPoint().add(new UDimension(200, 20 + bumpCount("ComponentsInStratum" + owner.getNumber()) * 150))),
              null, null);
        }
      }

    });
  }


  
  // name should have the first capital: e.g. Operations
  private String extractDelta(final NameRecord owned, String name, final DeltaPrinter printer) throws InterpretAlloyException
  {
    String lowerName = name.toLowerCase();
    String definition = "";

    // find the correct parts info
    final NameRecord delta =
      xpathLocateName("//field[@name='my" + name + "']/tuple/atom[@name='FNAME/" + owned.getIndexedFullName() + "']/following-sibling::*");
    if (delta == null)
      return "";
    
    final String delete[] = {""};
    xpathForEach("//field[@name='deleteObjects']/tuple/atom[@name='FNAME/" + name + "/" + delta.getIndexedFullName() + "']/following-sibling::*", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
      {
        delete[0] += "    " + name.getFullName() + ";\n";
      }
    });
    final String replace[] = {""};
    xpathForEach("//field[@name='replaceObjects']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
          final NameRecord rec[] = extractThreeAtoms(node);
          if (rec[0].equals(delta))
            replace[0] += printer.printNew(rec);
      }
    });          
    final String add[] = {""};
    xpathForEach("//field[@name='addObjects']/tuple", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
          throws InterpretAlloyException
      {
          final NameRecord rec[] = extractThreeAtoms(node);
          if (rec[0].equals(delta))
            add[0] += printer.printNew(rec);
      }
    });
    if (delete[0].length() != 0)
      definition += "  delete-" + lowerName + ":\n" + delete[0];
    if (replace[0].length() != 0)
      definition += "  replace-" + lowerName + ":\n" + replace[0];
    if (add[0].length() != 0)
      definition += "  " + lowerName + ":\n" + add[0];
    return definition;
  }
  
  public static int translateIndex(NameRecord index)
  {
    String values[] = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
    for (int lp = 0; lp < values.length; lp++)
      if (index.getName().equals(values[lp]))
        return lp;
    return -1;
  }
}
