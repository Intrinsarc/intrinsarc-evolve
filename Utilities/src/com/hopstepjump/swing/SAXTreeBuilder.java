package com.hopstepjump.swing;

import javax.swing.tree.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * adapted from source taken from
 * http://www.java2s.com/Code/Java/XML/XMLTreeView.htm, Java, XML, and Web
 * Services Bible, ISBN: 0-7645-4847-6
 * with fairly serious changes by Andrew McVeigh
 * 
 * @author Mike Jasnowsk, Andrew McVeigh
 */

public class SAXTreeBuilder extends DefaultHandler
{

	private DefaultMutableTreeNode currentNode = null;
	private DefaultMutableTreeNode currentSubnode = null;
	private DefaultMutableTreeNode previousNode = null;
	private DefaultMutableTreeNode rootNode = null;

	public SAXTreeBuilder(DefaultMutableTreeNode root)
	{
		rootNode = root;
	}

	public void startDocument()
	{
		currentNode = rootNode;
	}

	public void endDocument()
	{
	}

	@Override
	public void characters(char[] data, int start, int end)
	{
		String str = new String(data, start, end);
		
		if (!str.trim().equals(""))
		{
			if (currentSubnode == null)
			{
				currentSubnode = new DefaultMutableTreeNode(str);
				currentNode.add(currentSubnode);
			}
			else
				currentSubnode.setUserObject(((String) currentSubnode.getUserObject()) + str);
		}
	}

	public void startElement(String uri, String qName, String lName, Attributes atts)
	{
		previousNode = currentNode;
		currentNode = new DefaultMutableTreeNode(lName);
		currentSubnode = null;
		// Add attributes as child nodes //
		attachAttributeList(currentNode, atts);
		previousNode.add(currentNode);
	}

	public void endElement(String uri, String qName, String lName)
	{
		if (((String) currentNode.getUserObject()).startsWith(lName))
			currentNode = (DefaultMutableTreeNode) currentNode.getParent();
	}

	public DefaultMutableTreeNode getTree()
	{
		return rootNode;
	}

	private void attachAttributeList(DefaultMutableTreeNode node, Attributes atts)
	{
		for (int i = 0; i < atts.getLength(); i++)
		{
			String name = atts.getQName(i);
			String value = atts.getValue(name);

			if (name.equals("name"))
				node.setUserObject(((String) node.getUserObject()) + " " + value);
			else
			if (name.equals("uuid"))
				node.setUserObject(((String) node.getUserObject()) + " @" + value);
			else
				node.setUserObject(((String) node.getUserObject()) + ", " + name + " = "+ value);
		}
	}

}