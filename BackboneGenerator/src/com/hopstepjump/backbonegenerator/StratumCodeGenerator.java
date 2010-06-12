package com.hopstepjump.backbonegenerator;

import java.io.*;

import com.hopstepjump.deltaengine.base.*;

public class StratumCodeGenerator
{
  private DEStratum stratum;

  public StratumCodeGenerator(DEStratum stratum)
  {
    this.stratum = stratum;
  }

  public void generateBackboneSource(String name, File base) throws BackboneGenerationException, IOException
  {
    // generate the files for this stratum
    base.mkdirs();
    
    // create the stratum definition
    File sfile = new File(base, name + ".bb");
    BufferedWriter writer = null;
    try
    {
      writer = new BufferedWriter(new FileWriter(sfile));

      // write the XStream representation to the file
	    String str = null; //x.toXML(stratum);
	    writer.append(str);
    }
    catch (IOException e)
    {
      throw new BackboneGenerationException("Cannot write interfaces to " + sfile, null);
    }
    finally
    {
    	if (writer != null)
    		writer.close();
    }
  }
  
  public int generateInterfaceSource(File codeBase) throws BackboneGenerationException
  {
    codeBase.mkdirs();
    return generatePossibleImplementationInterfaces(stratum, codeBase);
  }
  
  public int generateLeafSource(File codeBase) throws BackboneGenerationException
  {
    codeBase.mkdirs();
    return refreshLeafImplementations(stratum, codeBase);
  }
  
  
  private int generatePossibleImplementationInterfaces(DEStratum stratum, File base) throws BackboneGenerationException
  {
    // start with this package
  	int count = 0;
    for (DEElement element : stratum.getChildElements())
    {
      // if this is a leaf, refresh the code
      DEInterface iface = element.asInterface();
      if (iface != null && !iface.isBean(iface.getHomeStratum()))
      {
      	// possibly repoint to what we are substituting
      	if (iface.isSubstitution())
      		iface = (DEInterface) iface.getSubstitutes().iterator().next();
      	
        InterfaceImplementationMaker maker = new InterfaceImplementationMaker(base, stratum, iface);
      	if (maker.possiblyMakeDefinition())
      		count++;
      }
    }
    return count;
  }
  
  private int refreshLeafImplementations(DEStratum pkg, File base) throws BackboneGenerationException
  {
    // start with this package
  	int count = 0;
    for (DEElement element : pkg.getChildElements())
      if (refreshLeaf(base, element))
      		count++;
    return count;
  }

	private boolean refreshLeaf(File base, DEElement element) throws BackboneGenerationException
	{
		// if this is a leaf, refresh the code
		DEStratum home = element.getHomeStratum();
		DEComponent comp = element.asComponent();
		if (comp != null &&
				comp.isLeaf(home) &&
				!comp.isPlaceholder(home) &&
				!comp.isBean(home) &&
				!comp.isRawAbstract() &&
				comp.getComponentKind() != ComponentKindEnum.STEREOTYPE)
		{
			// if this is a substituter, take what it substitutes...
			if (comp.isSubstitution())
				comp = (DEComponent) comp.getSubstitutes().iterator().next();
			LeafImplementationRefresher refresher = new LeafImplementationRefresher(base, stratum, comp);
			return refresher.refreshLeafCode();
		}
		return false;
	}
}
