package com.hopstepjump.jumble.alloy;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

import javax.xml.xpath.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Comment;
import org.eclipse.uml2.Element;
import org.eclipse.uml2.Package;
import org.w3c.dom.*;
import org.w3c.dom.Node;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.creation.*;
import com.hopstepjump.idraw.arcfacilities.creationbase.*;
import com.hopstepjump.idraw.figurefacilities.containment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creation.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.jumble.umldiagrams.classifiernode.*;
import com.hopstepjump.jumble.umldiagrams.connectorarc.*;
import com.hopstepjump.jumble.umldiagrams.dependencyarc.*;
import com.hopstepjump.jumble.umldiagrams.featurenode.*;
import com.hopstepjump.jumble.umldiagrams.implementationarc.*;
import com.hopstepjump.jumble.umldiagrams.inheritancearc.*;
import com.hopstepjump.jumble.umldiagrams.notenode.*;
import com.hopstepjump.jumble.umldiagrams.packagenode.*;
import com.hopstepjump.jumble.umldiagrams.portnode.*;
import com.hopstepjump.jumble.umldiagrams.slotnode.*;
import com.hopstepjump.jumble.umldiagrams.substitutionarc.*;
import com.hopstepjump.repositorybase.*;


public abstract class InterpretAlloyCommand extends AbstractCommand
{
  protected ToolCoordinatorFacet toolCoordinator;
  protected DiagramFacet diagram;
	protected boolean newStyleNaming;

  protected static final String  EXTRACT_NAME_OLD = "(.*)/(\\w*)\\[(.*)\\]";
  protected static final String  EXTRACT_NAME_NEW = "(.*)/(\\w*)\\$(.*)";

  protected CompositeCommand commandStack = new CompositeCommand("", "");
  protected Map<String, FigureReference> figureReferences = new HashMap<String, FigureReference>();
  protected Map<String, Element> elements = new HashMap<String, Element>();
  protected Map<String, Integer> counters = new HashMap<String, Integer>();
  protected boolean haveCreatedElements;
  protected XPathFactory xpathFactory = XPathFactory.newInstance();
  protected Document document;
  protected Set<String> originalNames = new HashSet<String>();
  protected Map<String, String> newNames = new HashMap<String, String>();
  protected Map<String, String> ownedToStratum = new HashMap<String, String>();
  protected List<Stratum> sorted;
  protected Map<String, NameRecord> partTypes = new HashMap<String, NameRecord>();
  protected Map<String, String> portRemap = new HashMap<String, String>();
  protected Map<String, String> portTypesP = new HashMap<String, String>();
  protected Map<String, String> attrTypes = new HashMap<String, String>();
  protected Map<String, Stratum> strata = new HashMap<String, Stratum>();
  protected Map<String, Boolean> strataRelaxed = new HashMap<String, Boolean>();
  protected Set<NameRecord> composites = new HashSet<NameRecord>();
  protected Map<NameRecord, String> portMultiplicity = new HashMap<NameRecord, String>();

  public InterpretAlloyCommand(ToolCoordinatorFacet toolCoordinator, DiagramFacet diagram, Document document, boolean newStyleNaming)
  {
    super("Add alloy representation", "Remove alloy representation");
    this.toolCoordinator = toolCoordinator;
    this.document = document;
    this.diagram = diagram;
    this.newStyleNaming = newStyleNaming;
  }

  protected int bumpCount(String counter)
  {
    if (!counters.containsKey(counter))
      counters.put(counter, 0);
    int result = counters.get(counter);
    counters.put(counter, result + 1);
    return result;
  }

  public void execute(boolean isTop)
  {
    try
    {
      execute();
    }
    catch (InterpretAlloyException ex)
    {
      toolCoordinator.invokeErrorDialog(
          "Problem interpreting Alloy output",
          ex.getMessage());
    }
  }

  private void execute() throws InterpretAlloyException
  {
    try
    {
      graphOriginals();
      graphResemblance();
    }
    catch (PersistentFigureRecreatorNotFoundException e)
    {
      throw new InterpretAlloyException(e.getMessage(), e);
    }
    catch (RepositoryPersistenceException e)
    {
      throw new InterpretAlloyException(e.getMessage(), e);
    }
  }
  
  protected abstract void graphOriginals() throws InterpretAlloyException;
  protected abstract void graphResemblance() throws InterpretAlloyException, PersistentFigureRecreatorNotFoundException, RepositoryPersistenceException;

  protected String makeOriginalName(Map<String, String> originals, String zName, String extra)
  {
    if (newNames.containsKey(zName))
      return newNames.get(zName);
    
    String originalName = originals.get(zName);
    for (int lp = 0; ; lp++)
    {
      String name = originalName + " (" + new Character((char)(lp + 'a'));
      if (extra != null)
        name += "_" + extra + ")";
      else
        name += ")";
      if (!originalNames.contains(name))
      {
        originalNames.add(name);
        newNames.put(zName, name);
        return name;
      }
    }
  }


  protected FigureFacet findOwningStratum(DiagramFacet diagram, final Map<String, String> ownedToStratum, NameRecord fullName)
  {
    return 
      diagram.possiblyRetrieveFigure(
          findOrCreateFigureReference(
              diagram, ownedToStratum.get(fullName.getFullName())).getId());
  }

  protected NameRecord xpathLocateName(String xpathExpression) throws InterpretAlloyException
  {
    XPathExpression xpathExpr;
    try
    {
      xpathExpr = makeOrRetrieveXPathExpression(xpathExpression);
      NodeList nodes = (NodeList) xpathExpr.evaluate(document, XPathConstants.NODESET);
      if (nodes.getLength() == 1)
      {
        return extractNameRecord(nodes.item(0));
      }
      else
        return null;
    }
    catch (XPathExpressionException e)
    {
      throw new InterpretAlloyException("XPath expression " + xpathExpression + " is incorrect");
    }
  }

  protected NameRecord[] extractTwoAtoms(Node node) throws InterpretAlloyException
  {
    return extractAtoms(node, 2);
  }

  protected NameRecord[] extractThreeAtoms(Node node) throws InterpretAlloyException
  {
    return extractAtoms(node, 3);
  }

  protected NameRecord[] extractFourAtoms(Node node) throws InterpretAlloyException
  {
    return extractAtoms(node, 4);
  }

  protected NameRecord[] extractAtoms(Node node, final int number) throws InterpretAlloyException
  {
    final NameRecord rec[] = new NameRecord[number];
    xpathForEach(node, "atom", new XPathExpressionWatcher()
    {
      public void matchedExpression(Node node, NameRecord name, int nodeCount)
      {
        rec[nodeCount] = name;
      }
    });
    return rec;
  }

  protected Pattern extractNamePattern = null; 
  protected NameRecord extractNameRecord(Node node)
  {
  	if (extractNamePattern == null)
  		extractNamePattern = Pattern.compile(newStyleNaming ? EXTRACT_NAME_NEW : EXTRACT_NAME_OLD);
  	
    // see if we can extract the name
    Node nameAttr = node.getAttributes().getNamedItem(newStyleNaming ? "label" : "name");
    if (nameAttr != null)
    {
      Matcher matcher = extractNamePattern.matcher(nameAttr.getNodeValue());
      if (matcher.matches())
        return new NameRecord(matcher.group(1), matcher.group(2), matcher.group(3), newStyleNaming);
    }
    return new NameRecord(null, null, null, newStyleNaming);
  }

  protected void xpathForEach(String xpathExpression, XPathExpressionWatcher watcher) throws InterpretAlloyException
  {
    xpathForEach(document, xpathExpression, watcher);
  }

  protected void xpathForEach(Node start, String xpathExpression, XPathExpressionWatcher watcher)
      throws InterpretAlloyException
  {
    XPathExpression xpathExpr = makeOrRetrieveXPathExpression(xpathExpression);
    try
    {
      NodeList nodes = (NodeList) xpathExpr.evaluate(start, XPathConstants.NODESET);
      for (int lp = 0; lp < nodes.getLength(); lp++)
      {
        Node node = nodes.item(lp);
        NameRecord name = extractNameRecord(node);
        watcher.matchedExpression(node, name, lp);
      }
    }
    catch (XPathExpressionException e)
    {
      throw new InterpretAlloyException("XPath expression " + xpathExpression + " is incorrect");
    }
  }

  private Map<String, XPathExpression> xpathExpressions = new HashMap<String, XPathExpression>();
  protected javax.xml.xpath.XPathExpression makeOrRetrieveXPathExpression(String expression)
      throws InterpretAlloyException
  {
    String newExpr = expression.replace("FNAME", "bb/full").replace("BNAME", "bb/base");
    if (newStyleNaming)
    	newExpr = newExpr.replaceAll("@name", "@label");
    XPathExpression expr = xpathExpressions.get(newExpr);
    if (expr != null)
      return expr;
    XPath xpath = xpathFactory.newXPath();
    try
    {
      expr = xpath.compile(newExpr);
    }
    catch (XPathExpressionException ex)
    {
      throw new InterpretAlloyException("XPath expression " + newExpr + " is incorrect");
    }
    xpathExpressions.put(newExpr, expr);
    return expr;
  }

  protected void resurrectElements()
  {
    for (Element element : elements.values())
      GlobalSubjectRepository.repository.decrementPersistentDelete(element);
  }

  protected FigureFacet createInterface(DiagramFacet diagram, FigureFacet owner, String displayName, boolean suppressAttributes, String uuidName, boolean invalid, UPoint point,
      PersistentProperties properties)
  {
    FigureReference interfaceRef = findOrCreateFigureReference(diagram, uuidName);

    // create the interface
    NodeCreateFacet interfaceCreator = new InterfaceCreatorGem().getNodeCreateFacet();

    // set showVis so we can see if it is from a foreign package
    if (properties == null)
      properties = new PersistentProperties();
    properties.addIfNotThere(new PersistentProperty("supA", suppressAttributes, true));
    properties.addIfNotThere(new PersistentProperty("showVis", true, true));
    properties.addIfNotThere(new PersistentProperty("suppVis", false, false));
    properties.add(new PersistentProperty(">stereotype", CommonRepositoryFunctions.INTERFACE));
    if (invalid)
      properties.add(new PersistentProperty(">stereotype2", CommonRepositoryFunctions.ERROR));

    // see if we have already got the name
    Interface subject = null;
    if (findObject(uuidName) != null)
      subject = (Interface) findObject(uuidName);
    NodeCreateFigureCommand interfaceCreate = new NodeCreateFigureCommand(
        subject,
        interfaceRef,
        owner == null ? null : owner.getFigureReference(),
        interfaceCreator,
        point,
        false,
        properties,
        null,
        "",
        "");
    go(interfaceCreate);
    // get the package and change its name
    Interface iface = (Interface) interfaceCreate.getSubject();
    elements.put(uuidName, iface);
    iface.setName(displayName);

    // possibly insert into a container
    if (owner != null)
      insertFigure(owner, interfaceRef);
    return diagram.retrieveFigure(interfaceRef.getId());
  }


  protected FigureFacet createPort(
      DiagramFacet diagram, FigureFacet owner, String name,
      String uuid, String multiplicity, UPoint point,
      PersistentProperties properties)
  {
    FigureReference portRef = findOrCreateFigureReference(diagram, uuid);

    // create the port
    NodeCreateFacet portCreator = new PortCreatorGem().getNodeCreateFacet();

    if (properties == null)
      properties = new PersistentProperties();
    properties.addIfNotThere(new PersistentProperty("name", name + (multiplicity == null ? "" : " " + multiplicity)));
    properties.addIfNotThere(new PersistentProperty("classScope", true, false));
    Command portCreate = new NodeCreateFigureCommand(null, portRef, owner.getFigureReference(), portCreator, point,
        false, properties, null, "", "");
    go(portCreate);
    insertFigure(owner, portRef);

    FigureFacet figure = diagram.retrieveFigure(portRef.getId());
    return figure;
  }

  protected FigureFacet createPortInstance(
      DiagramFacet diagram,
      FigureFacet owner,
      String displayName,
      String uuid,
      UPoint point,
      Port port,
      PersistentProperties properties)
  {
    FigureReference portInstanceRef = findOrCreateFigureReference(diagram, uuid);

    // create the port
    NodeCreateFacet portCreator = new PortInstanceCreatorGem().getNodeCreateFacet();

    if (properties == null)
      properties = new PersistentProperties();
    properties.addIfNotThere(new PersistentProperty("extraText", displayName));
    // properties.addIfNotThere(new PersistentProperty("suppress", true, true));
    properties.addIfNotThere(new PersistentProperty("classScope", false, false));
    Command portCreate = new NodeCreateFigureCommand(null, portInstanceRef, owner.getFigureReference(), portCreator,
        point, false, properties, port, "", "");
    go(portCreate);
    insertFigure(owner, portInstanceRef);

    FigureFacet figure = diagram.retrieveFigure(portInstanceRef.getId());
    return figure;
  }

  protected FigureFacet createAttribute(
      DiagramFacet diagram,
      FigureFacet owner,
      String ownerName,
      String name,
      String type,
      String value,
      PersistentProperties properties)
  {
    FigureReference attrRef = findOrCreateFigureReference(diagram, ownerName + DEObject.SEPARATOR + name);

    // create the component
    NodeCreateFacet objectCreator = new AttributeCreatorGem().getNodeCreateFacet();
    Command objectCreate = new NodeCreateFigureCommand(
        null,
        attrRef,
        owner.getFigureReference(),
        objectCreator,
        new UPoint(0, 0),
        false,
        properties,
        null, "", "");
    go(objectCreate);

    FigureFacet figure = diagram.retrieveFigure(attrRef.getId());
    // set up the details
    Property attr = (Property) figure.getSubject();
    attr.setName(name + ": " + type + (value != null ? (" = " + value) : ""));
    attr.setType(null);
    insertFigure(owner, attrRef);

    return figure;
  }

  protected FigureFacet createSlot(
      DiagramFacet diagram,
      FigureFacet owner,
      String ownerName,
      String name,
      boolean aliased,
      PersistentProperties properties)
  {
    FigureReference slotRef = findOrCreateFigureReference(diagram, ownerName + DEObject.SEPARATOR + name);

    // create the attribute
    properties.addIfNotThere(new PersistentProperty(">stereotype", CommonRepositoryFunctions.OVERRIDDEN_SLOT));
    NodeCreateFacet objectCreator = new SlotCreatorGem().getNodeCreateFacet();
    Command objectCreate = new NodeCreateFigureCommand(
        null,
        slotRef,
        owner.getFigureReference(),
        objectCreator,
        new UPoint(0, 0),
        false,
        properties,
        null, "", "");
    go(objectCreate);

    FigureFacet figure = diagram.retrieveFigure(slotRef.getId());
    
    // set up the details
    Slot slot = (Slot) figure.getSubject();
    StereotypeUtilities.formSetStringRawStereotypeAttributeCommand(
        slot,
        CommonRepositoryFunctions.OVERRIDDEN_SLOT_TEXT,
        name).execute(true);
    StereotypeUtilities.formSetBooleanRawStereotypeAttributeCommand(
        slot,
        CommonRepositoryFunctions.OVERRIDDEN_SLOT_ALIAS,
        aliased).execute(true);
    
    insertFigure(owner, slotRef);

    return figure;
  }

  protected FigureFacet createOperation(
      DiagramFacet diagram,
      FigureFacet owner,
      String ownerName,
      String operationString,
      PersistentProperties properties)
  {
    FigureReference attrRef = findOrCreateFigureReference(diagram, ownerName + DEObject.SEPARATOR + operationString);

    // create the component
    NodeCreateFacet objectCreator = new OperationCreatorGem().getNodeCreateFacet();
    Command objectCreate = new NodeCreateFigureCommand(
        null,
        attrRef,
        owner.getFigureReference(),
        objectCreator,
        new UPoint(0, 0),
        false,
        properties,
        null, "", "");
    go(objectCreate);

    FigureFacet figure = diagram.retrieveFigure(attrRef.getId());
    // set up the details
    Operation oper = (Operation) figure.getSubject();
    oper.setName(operationString);
    oper.setType(null);
    insertFigure(owner, attrRef);

    return figure;
  }


  
  protected FigureFacet createPart(DiagramFacet diagram, FigureFacet owner, Classifier componentType, String ownerName, String name, UPoint point,
      PersistentProperties properties)
  {
    FigureReference partRef = findOrCreateFigureReference(diagram, ownerName + DEObject.SEPARATOR + name + DEObject.SEPARATOR + componentType.getName());


    // make the name of the component
    properties.add(new PersistentProperty("name", name, null));

    // create the component
    NodeCreateFacet objectCreator = new PartCreatorGem().getNodeCreateFacet();
    Command objectCreate = new NodeCreateFigureCommand(null, partRef, owner.getFigureReference(), objectCreator, point,
        false, properties, componentType, "", "");
    go(objectCreate);

    FigureFacet figure = diagram.retrieveFigure(partRef.getId());

    // possibly insert into a container
    // find a willing subcontainer
    ContainerFacet container = owner.getContainerFacet();
    Iterator<FigureFacet> figures = container.getContents();
    while (figures.hasNext())
    {
      FigureFacet contained = figures.next();
      ContainerFacet c = contained.getContainerFacet();
      if (c != null && c.getAcceptingSubcontainer(new ContainedFacet[]{figure.getContainedFacet()}) != null)
      {
        insertFigure(c.getFigureFacet(), partRef);
        break;
      }
    }

    return figure;
  }

  protected FigureFacet createCommentComponent(DiagramFacet diagram, FigureFacet owner, String displayName, String uuidName, boolean leaf, UPoint point,
      PersistentProperties properties, Color col)
  {
    FigureReference componentRef = findOrCreateFigureReference(diagram, uuidName);

    // create the component
    if (properties == null)
      properties = new PersistentProperties();
    
    NoteCreatorGem creator = new NoteCreatorGem();
    properties.add(new PersistentProperty(">wordWrap", false, true));
    NodeCreateFacet noteCreator = creator.getNodeCreateFacet();
    final NodeCreateFigureCommand componentCreate = new NodeCreateFigureCommand(
        null, componentRef, owner == null ? null : owner.getFigureReference(), noteCreator, point, false, properties, null, "", "");
    go(componentCreate);

    // get the package and change its name
    Comment comment = (Comment) componentCreate.getSubject();
    comment.setBody(displayName);

    // add to the figures list
    elements.put(uuidName, (Element) componentCreate.getSubject());

    // possibly insert into a container
    if (owner != null)
      insertFigure(owner, componentRef);

    return diagram.retrieveFigure(componentRef.getId());
  }

  
  protected FigureFacet createComponent(
      DiagramFacet diagram, FigureFacet owner, String displayName,
      String uuidName, boolean invalid, boolean leaf, UPoint point,
      PersistentProperties properties, boolean suppressAttributes)
  {
    FigureReference componentRef = findOrCreateFigureReference(diagram, uuidName);

    // make sure we suppress operations
    if (properties == null)
      properties = new PersistentProperties();
    properties.addIfNotThere(new PersistentProperty("supO", true, true));
    if (suppressAttributes)
      properties.addIfNotThere(new PersistentProperty("supA", true, true));
    properties.add(new PersistentProperty(">stereotype", CommonRepositoryFunctions.COMPONENT));
    if (invalid)
      properties.add(new PersistentProperty(">stereotype2", CommonRepositoryFunctions.ERROR));
    properties.add(new PersistentProperty("auto", false, false));

    Class subject = null;
    if (findObject(uuidName) != null)
      subject = (Class) findObject(uuidName);

    // create the component
    NodeCreateFacet componentCreator = new ClassCreatorGem().getNodeCreateFacet();
    final NodeCreateFigureCommand componentCreate =
      new NodeCreateFigureCommand(
        subject, componentRef, owner == null ? null : owner.getFigureReference(),
        componentCreator, point, false, properties, null, "", "");
    go(componentCreate);

    // get the package and change its name
    Class component = (Class) componentCreate.getSubject();
    component.setName(displayName);

    // add to the figures list
    elements.put(uuidName, (Element) componentCreate.getSubject());

    // possibly insert into a container
    if (owner != null)
      insertFigure(owner, componentRef);

    if (leaf)
    {
      Command setVisibility = new AbstractCommand("", "")
      {
        public void execute(boolean isTop)
        {
          ((Class) componentCreate.getSubject()).setIsLeaf(true);
        }

        public void unExecute()
        {
        }
      };
      go(setVisibility);
    }

    return diagram.retrieveFigure(componentRef.getId());
  }


  protected void insertFigure(FigureFacet owner, FigureReference reference)
  {
    // insert the component into the package
    FigureFacet contained = resolveFigure(reference);
    ContainerFacet accepting = owner.getContainerFacet().getAcceptingSubcontainer(
        new ContainedFacet[]{contained.getContainedFacet()});
    if (accepting == null)
      System.err.println("$$ cannot insert figure into object: figure = " + reference);
    else
    {
      ContainerAddCommand addCmd = new ContainerAddCommand(accepting.getFigureFacet().getFigureReference(),
          new FigureReference[]{reference}, "", "");
      go(addCmd);
    }
  }

  /**
   * create a package on the current diagram, and return the figure facet
   * 
   * @param name
   * @param point
   * @param color 
   * @param properties
   * @return
   */
  public FigureFacet createStratum(DiagramFacet diagram, FigureFacet owner, String name, UPoint point, boolean relaxed)
  {
    FigureReference pkgRef = findOrCreateFigureReference(diagram, name);

    NodeCreateFacet pkgCreator = new PackageCreatorGem(false).getNodeCreateFacet();
    PersistentProperties properties = new PersistentProperties();
    properties.add(new PersistentProperty(">stereotype", CommonRepositoryFunctions.STRATUM));
    properties.add(new PersistentProperty(">relaxed", relaxed, relaxed));
    NodeCreateFigureCommand pkgCreate = new NodeCreateFigureCommand(null, pkgRef, null, pkgCreator, point, false,
        properties, null, "", "");
    go(pkgCreate);
    // get the package and change its name
    Package pkg = (Package) pkgCreate.getSubject();
    pkg.setName(name);

    // possibly insert into a container
    if (owner != null)
      insertFigure(owner, pkgRef);
    return diagram.retrieveFigure(pkgRef.getId());
  }

  /**
   * create a package on the current diagram, and return the figure facet
   * 
   * @param name
   * @param point
   * @param color 
   * @param properties
   * @return
   */
  public FigureFacet createPackage(DiagramFacet diagram, FigureFacet owner, String name, UPoint point)
  {
    FigureReference pkgRef = findOrCreateFigureReference(diagram, name);

    NodeCreateFacet pkgCreator = new PackageCreatorGem(false).getNodeCreateFacet();
    PersistentProperties properties = new PersistentProperties();
    NodeCreateFigureCommand pkgCreate = new NodeCreateFigureCommand(null, pkgRef, null, pkgCreator, point, false,
        properties, null, "", "");
    go(pkgCreate);
    // get the package and change its name
    Package pkg = (Package) pkgCreate.getSubject();
    pkg.setName(name);

    // possibly insert into a container
    if (owner != null)
      insertFigure(owner, pkgRef);
    return diagram.retrieveFigure(pkgRef.getId());
  }

  protected FigureReference findOrCreateFigureReference(DiagramFacet diagram, String name)
  {
    if (figureReferences.containsKey("" + diagram + name))
      return figureReferences.get(name);
    FigureReference reference = diagram.makeNewFigureReference();
    if (name != null)
      figureReferences.put(name, reference);
    return reference;
  }

  protected FigureFacet retrieveFigure(DiagramFacet diagram, String name)
  {
    return diagram.retrieveFigure(figureReferences.get(name).getId());
  }

  protected Element findObject(String name)
  {
    return elements.get(name);
  }

  protected FigureFacet createConnector(
      DiagramFacet diagram,
      FigureFacet from,
      int fromIndex,
      FigureFacet to,
      int toIndex,
      String name,
      String owner)
  {
    FigureReference connectorRef = findOrCreateFigureReference(diagram, owner + name);

    ArcCreateFacet arcCreatorFacet = new ConnectorCreatorGem().getArcCreateFacet();
    List<UPoint> points = new ArrayList<UPoint>();
    UPoint fromPoint = from.getFullBounds().getMiddlePoint();
    UPoint toPoint = to.getFullBounds().getMiddlePoint();
    UPoint middlePoint = new UBounds(fromPoint, toPoint).getMiddlePoint();
    points.add(fromPoint);
    if (toPoint.equals(fromPoint))
    {
      points.add(toPoint.add(new UDimension(-24, 0)));
      points.add(toPoint.add(new UDimension(-24, 8)));
      points.add(toPoint.add(new UDimension(0, 8)));
    }
    else
    {
      points.add(toPoint);
    }
    PersistentProperties properties = new PersistentProperties();
    if (fromIndex != -1)
      properties.add(new PersistentProperty(">end1Index", fromIndex, -1));
    if (toIndex != -1)
      properties.add(new PersistentProperty(">end2Index", toIndex, -1));
    ArcCreateFigureCommand arcCmd = new ArcCreateFigureCommand(null, connectorRef, arcCreatorFacet,
        new ReferenceCalculatedArcPoints(middlePoint, points, from.getFigureReference(), to.getFigureReference()),
        properties, "", "");
    go(arcCmd);
    
    FigureFacet figure = diagram.retrieveFigure(connectorRef.getId());
    Connector conn = (Connector) figure.getSubject();
    conn.setName(name);
    return figure;
  }

  protected FigureFacet createProvidedInterface(DiagramFacet diagram, FigureFacet from, FigureFacet to)
  {
    return createSimpleArc(diagram, new ImplementationCreatorGem().getArcCreateFacet(), from, to, null);
  }

  protected FigureFacet createSubstitution(DiagramFacet diagram, FigureFacet from, FigureFacet to)
  {
    return createSimpleArc(diagram, SubstitutionUtilities.makeSubstitutionCreator().getArcCreateFacet(), from, to, null);
  }

  protected FigureFacet createIncrementalSubstitution(DiagramFacet diagram, FigureFacet from, FigureFacet to)
  {
    return createSimpleArc(diagram, SubstitutionUtilities.makeIncrementalSubstitutionCreator().getArcCreateFacet(), from, to, null);
  }

  protected FigureFacet createResemblance(DiagramFacet diagram, FigureFacet from, FigureFacet to)
  {
    return createSimpleArc(diagram, SubstitutionUtilities.makeResemblanceCreator().getArcCreateFacet(), from, to, null);
  }

  protected FigureFacet createInferredPortLink(DiagramFacet diagram, FigureFacet from, FigureFacet to)
  {
    return createDependencyArc(diagram, from, to, Color.BLACK);
  }

  protected FigureFacet createSpecialisation(DiagramFacet diagram, FigureFacet from, FigureFacet to)
  {
    return createSimpleArc(diagram, new InheritanceCreatorGem().getArcCreateFacet(), from, to, null);
  }

  protected FigureFacet createDependencyArc(DiagramFacet diagram, FigureFacet from, FigureFacet to, Color color)
  {
    return createSimpleArc(diagram, new DependencyCreatorGem().getArcCreateFacet(), from, to, color);
  }

  protected FigureFacet createTraceArc(DiagramFacet diagram, FigureFacet from, FigureFacet to, Color color)
  {
    DependencyCreatorGem creator = new DependencyCreatorGem();
    creator.setStereotype(CommonRepositoryFunctions.TRACE);
    return createSimpleArc(diagram, creator.getArcCreateFacet(), from, to, color);
  }

  protected FigureFacet createRequiredInterface(DiagramFacet diagram, FigureFacet from, FigureFacet to)
  {
    return createSimpleArc(diagram, new DependencyCreatorGem().getArcCreateFacet(), from, to, null);
  }

  protected FigureFacet createSimpleArc(DiagramFacet diagram, ArcCreateFacet createFacet, FigureFacet from, FigureFacet to, Color color)
  {
    List<UPoint> points = new ArrayList<UPoint>();
    UPoint fromPoint = from.getFullBounds().getMiddlePoint();
    UPoint toPoint = to.getFullBounds().getMiddlePoint();
    points.add(fromPoint);
    points.add(toPoint);
    FigureReference arcReference = findOrCreateFigureReference(diagram, null);
    PersistentProperties properties = new PersistentProperties();
    createFacet.initialiseExtraProperties(properties);
    if (color != null)
      properties.add(new PersistentProperty("color", color, Color.BLACK));
    ArcCreateFigureCommand arcCmd = new ArcCreateFigureCommand(null, arcReference, createFacet,
        new ReferenceCalculatedArcPoints(new UBounds(fromPoint, toPoint).getMiddlePoint(), points, from
            .getFigureReference(), to.getFigureReference()), properties, "", "");
    go(arcCmd);
    return diagram.retrieveFigure(arcReference.getId());
  }

  protected FigureFacet resolveFigure(FigureReference ref)
  {
    DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(ref.getDiagramReference());
    return diagram.retrieveFigure(ref.getId());
  }

  protected void go(Command cmd)
  {
    commandStack.addCommand(cmd);
    toolCoordinator.executeForPreview(cmd, true, false);
  }

	public void unExecute()
	{
		// not used as we clear command history immediately after
	}
}
