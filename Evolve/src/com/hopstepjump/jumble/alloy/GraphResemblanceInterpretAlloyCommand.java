package com.hopstepjump.jumble.alloy;

import java.util.*;

import org.w3c.dom.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

public abstract class GraphResemblanceInterpretAlloyCommand extends GraphOriginalsInterpretAlloyCommand
{
  public GraphResemblanceInterpretAlloyCommand(ToolCoordinatorFacet toolCoordinator, DiagramFacet diagram, Document document, boolean newStyleNaming)
  {
    super(toolCoordinator, diagram, document, newStyleNaming);
  }
  
  protected abstract void graphExpanded(final DiagramFacet diagram, Stratum stratum, Set<NameRecord> transitivePlusMe) throws InterpretAlloyException;
  
  public void graphResemblance() throws InterpretAlloyException, PersistentFigureRecreatorNotFoundException
  {
    // make a package under each stratum with the correct resemblance
    for (final Stratum s: strata.values())
    {
      final Set<NameRecord> transitivePlusMe = new HashSet<NameRecord>();

      // get the diagram for the stratum
      final DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(new DiagramReference(s.getPackage().getUuid()), true);
      
      // make a package here
      FigureFacet expanded = createPackage(diagram, null, "expanded", new UPoint(10, 10));
      DiagramFacet expandedDiagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(
          new DiagramReference(((org.eclipse.uml2.Package) expanded.getSubject()).getUuid()), true);
      
      // find out which stratum are visible to this stratum
      xpathForEach("//field[@name='transitivePlusMe']/tuple", new XPathExpressionWatcher()
      {
        public void matchedExpression(Node node, NameRecord name, int nodeCount)
            throws InterpretAlloyException
        {
          final NameRecord rec[] = extractTwoAtoms(node);
          if (rec[0].getNumber().equals(s.getNumber()))
            transitivePlusMe.add(rec[1]);
        }
      });
      
      // create the expanded view
      graphExpanded(expandedDiagram, s, transitivePlusMe);

      // graph the resemblance, from this stratum's perspective
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

            final String actsAs[] = { " (--)" };
            xpathForEach("//field[@name='actsAs_e']/tuple", new XPathExpressionWatcher()
            {
              public void matchedExpression(Node node, NameRecord name, int nodeCount)
                  throws InterpretAlloyException
              {
                final NameRecord rec[] = extractThreeAtoms(node);
                // nothing can "masquerade" as more than one thing at a time because of single substitution
                if (rec[2].getNumber().equals(s.getNumber()) && rec[0].getFullName().equals(elementName))
                {
                  actsAs[0] = " (acts as " + rec[1].getFullName() + ")";
                }
              }
            });

            // draw the element -- it is relevant here
            if (rec[1].getName().equals("Component"))
              createComponent(
                  diagram,
                  null,
                  elementName + actsAs[0],
                  "" + diagram + elementName,
                  false,
                  !composites.contains(rec[1]),
                  new UPoint(200 + (current[0] % 3) * 150, 200 + (current[0] / 3) * 150),
                  null,
                  true);
            else
            {
              createInterface(
                  diagram,
                  null,
                  elementName + actsAs[0],
                  true,
                  "" + diagram + elementName,
                  false,
                  new UPoint(200 + (current[0] % 3) * 150, 200 + (current[0] / 3) * 150),
                  null);
            }
              
            current[0]++;
          }
        }
      });    
      
      // resize the diagram to ensure the correct sizes for drawing resemblance
      diagram.resizeEntireDiagram();
      
      // handle resemblance
      xpathForEach("//field[@name='resembles_e']/tuple", new XPathExpressionWatcher()
      {
        public void matchedExpression(Node node, NameRecord name, int nodeCount)
            throws InterpretAlloyException
        {
          final NameRecord rec[] = extractThreeAtoms(node);
          
          if (rec[2].getNumber().equals(s.getNumber()))
          {
            // graph between the two
            FigureFacet from = retrieveFigure(diagram, "" + diagram + rec[0].getFullName());
            FigureFacet to = retrieveFigure(diagram, "" + diagram + rec[1].getFullName());
            createResemblance(diagram, from, to);
          }
        }
      });        
    }
  }
}
