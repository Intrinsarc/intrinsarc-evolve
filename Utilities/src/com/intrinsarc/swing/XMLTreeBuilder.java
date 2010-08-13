package com.intrinsarc.swing;

import java.io.*;

import javax.swing.*;
import javax.swing.tree.*;
import javax.xml.parsers.*;

import org.xml.sax.*;

public class XMLTreeBuilder
{
	public XMLTreeBuilder()
	{
	}
	
	public JTree buildFromXML(InputStream xmlSource, int depth)
	{
    DefaultMutableTreeNode first = new DefaultMutableTreeNode();
    SAXTreeBuilder saxTree = new SAXTreeBuilder(first); 
    
    try
    {
		  SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
		  saxParser.parse(new InputSource(xmlSource), saxTree);
    }
    catch(Exception ex)
    {
     first.add(new DefaultMutableTreeNode(ex.getMessage()));
    }
    JTree tree = new JTree(saxTree.getTree()); 
    TreeExpander.expandTree(tree, null, true, null, null, depth);
    return tree;
	}
}
