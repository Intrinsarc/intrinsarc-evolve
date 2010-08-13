/*
 * Created on Dec 2, 2002 by Andrew McVeigh
 */
package com.intrinsarc.idraw.foundation;




/**
 * @author Andrew
 */
public interface SmartMenuBarFacet
{
	public void addSmartMenuContributorFacet(String name, SmartMenuContributorFacet contributor);
	public void removeSmartMenuContributorFacet(String name);
	public void rebuild();

	public void addMenuOrderingHint(String menuName, String orderingHint);
	public void addSectionOrderingHint(String menuName, String sectionName, String orderingHint);
}
