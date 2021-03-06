package com.intrinsarc.uml2deltaengine;

import java.util.*;
import java.util.regex.*;

import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;

public class UML2Connector extends DEConnector
{
  private Connector subject;
  private boolean takeNext[];
  private String index[];
  private boolean delegation;
	private List<DEAppliedStereotype> appliedStereotypes;
  
  public UML2Connector(Connector connector)
  {
    super();
    this.subject = connector;
		delegation = subject.getKind().equals(ConnectorKind.DELEGATION_LITERAL);
    appliedStereotypes = UML2Component.extractStereotypes(subject);
  }

  @Override
  public DEPort asPort()
  {
    return null;
  }

  @Override
  public String getName()
  {
    return subject.getName();
  }

  @Override
  public DEObject getParent()
  {
    // if this is a replacement, go a bit higher to get the owner
    if (subject.getOwner() instanceof DeltaReplacedConstituent)
      return getEngine().locateObject(subject.getOwner().getOwner());
    return getEngine().locateObject(subject.getOwner());
  }

  @Override
  public Object getRepositoryObject()
  {
    return subject;
  }

  @Override
  public String getUuid()
  {
    return subject.getUuid();
  }

  @Override
  public DEConnector asConnector()
  {
    return this;
  }

  public DEPort getOriginalPort(int index)
  {
    List<ConnectorEnd> ends = subject.undeleted_getEnds();
    if (ends.size() < index)
      return null;
    if (ends.size() <= index)
    	return null;
    return getEngine().locateObject(ends.get(index).undeleted_getRole()).asConstituent().asPort();
  }

  public DEPart getOriginalPart(int index)
  {
    List<ConnectorEnd> ends = subject.undeleted_getEnds();
    if (index > ends.size() - 1)
      return null;
    Property part = ends.get(index).undeleted_getPartWithPort();
    // part doesn't have to be set for a connector end
    if (part == null)
      return null;
    if (part.getOwner() instanceof DeltaReplacedAttribute)
      part = (Property) ((DeltaReplacedAttribute) part.getOwner()).getReplaced();
    if (part == null)
      return null;
    return getEngine().locateObject(part).asConstituent().asPart();
  }

  private static Pattern matchAlpha = Pattern.compile("\\s*\\[\\s*(\\w*)\\s*\\]\\s*");
  
	@Override
	public boolean isIndexOk(int ind)
	{
    List<ConnectorEnd> ends = subject.undeleted_getEnds();
    if (ends.size() > ind)
    {
    	ConnectorEnd end = ends.get(ind);
    	ValueSpecification spec = end.undeleted_getLowerValue();
    	if (spec instanceof Expression)
    	{
    		Expression e = (Expression) spec;
    		String txt = e.getBody();
    		
    		// match against the regular expression to see if it is ok
    		if (txt.trim().length() > 0 && !matchAlpha.matcher(txt).matches())
    			return false;
    	}    	
    }
    
    return true;
	}
	
	@Override
	public String getIndex(int ind)
	{
		if (index != null)
			return index[ind];
		
		index = new String[2];
		takeNext = new boolean[2];
    List<ConnectorEnd> ends = subject.undeleted_getEnds();
    int lp = 0;
    for (ConnectorEnd end : ends)
    {
    	ValueSpecification spec = end.undeleted_getLowerValue();
    	if (spec instanceof Expression)
    	{
    		Expression e = (Expression) spec;
    		String txt = e.getBody();

  			// extract out the inside of the index string
    		Matcher matcher = matchAlpha.matcher(txt);
    		if (matcher.matches())
    			index[lp] = matcher.group(1);
    	}
    	lp++;
    }
    
    return index[ind];
	}

	@Override
	public boolean getTakeNext(int ind)
	{
		// make sure we have the values cached
		getIndex(0);
		return takeNext[ind];
	}

	@Override
	public boolean isDelegate()
	{
		return delegation;
	}

	@Override
	public List<DEAppliedStereotype> getAppliedStereotypes()
	{
		return appliedStereotypes;
	}
}
