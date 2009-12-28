package com.hopstepjump.alloysnippets;

import java.io.*;
import java.util.*;

public class SnippetMaker
{
	private File in;
	private File dirOut;
	private Map<String, StringBuffer> snippets = new HashMap<String, StringBuffer>();
	private Set<String> paused = new HashSet<String>();
	private Set<String> unpaused = new HashSet<String>();
	private StringBuffer whole = new StringBuffer();
	private Map<String, Integer> startLine = new HashMap<String, Integer>();
	private Map<String, Integer> endLine = new HashMap<String, Integer>();
	private Map<String, Integer> leadingSpaces = new HashMap<String, Integer>();

	public static void main(String args[]) throws IOException
	{
		if (args.length < 2)
		{
			System.err.println("Usage: snippetmaker alloy-directory snippet-directory");
			System.exit(-1);
		}
		
		// open the directory and read
		File output = new File(args[1]);
		output.mkdirs();
		for (File file : new File(args[0]).listFiles())
		{
			if (file.getName().endsWith(".als"))
			new SnippetMaker(file, output).makeSnippets();
		}
	}

	public SnippetMaker(File in, File dirOut)
	{
		this.in = in;
		this.dirOut = dirOut;
	}

	private void makeSnippets() throws IOException
	{
		System.out.println("$$ writing snippets for " + in + " to directory " + dirOut);
		BufferedReader r = new BufferedReader(new FileReader(in));
		String line = "";
		int lineNum = 1;
		for (;;)
		{
			line = untab(r.readLine());
			if (line == null)
				break;
			
			// is this an open?
			if (line.startsWith("////open"))
			{
				String name = extractName(line); 
				startLine.put(name, lineNum);
				snippets.put(name, new StringBuffer());
			}
			else
			if (line.startsWith("////close"))
			{
				// write the snippet
				String name = extractName(line); 
				writeSnippet(name, startLine.get(name), endLine.get(name));
			}
			else
			if (line.startsWith("////pause"))
			{
				paused.add(extractName(line));
			}
			else
			if (line.startsWith("////unpause"))
			{
				String name = extractName(line);
				paused.remove(name);
				unpaused.add(name);
			}
			else
			if (line.startsWith("////comment"))
			{
				String name = extractName(line);				
				StringBuffer snip = snippets.get(name);
				snip.append(line.substring(11 + 2 + name.length()) + "\n");
			}
			else
			{
				whole.append(line + "\n");
				
				if (line.trim().startsWith("-- rule: "))
				{
					String name = new StringTokenizer(line.trim().substring(9)).nextToken();
					System.out.println("$$   writing rule " + name);
					
					// write out the location to a tex file
					BufferedWriter rw = new BufferedWriter(new FileWriter(new File(dirOut, "rule-" + name + ".tex")));
					rw.append("\\texttt{" + in.getName().replace("_", "\\_") + "}, line " + lineNum + "\\!\\!");
					rw.close();
				}
				
				// save the text away
				for (String name : snippets.keySet())
				{
					StringBuffer snip = snippets.get(name);
					int currentLeading = numberOfLeadingSpaces(line);
					if (!paused.contains(name) && !line.substring(currentLeading).startsWith("--") && line.trim().length() != 0)
					{
						// possibly save the number of leading spaces
						if (!leadingSpaces.containsKey(name))
						{
							leadingSpaces.put(name, currentLeading);
						}
						int firstLeading = leadingSpaces.get(name);
						
						if (unpaused.contains(name))
						{
							snip.append(makeSpaces(currentLeading - firstLeading) + "...\n");
							unpaused.remove(name);
						}
						snip.append(stripLeadingSpaces(line, firstLeading) + "\n");
						endLine.put(name, lineNum);
					}
				}
				lineNum++;
			}
		}
		writeWhole();
	}

	private String makeSpaces(int leading)
	{
		StringBuffer s = new StringBuffer();
		for (int lp = 0; lp < leading; lp++)
			s.append(' ');
		return s.toString();
	}

	private void writeWhole() throws IOException
	{
		String start = "\\lstset{frame=tb, aboveskip=12pt, belowskip=-3pt, breaklines=true, tabsize=2, mathescape=true}\n" +
		 "\\begin{lstlisting}[caption={$}, numbers=left]\n";
		String end = "\\end{lstlisting}\n";

		String inName = in.getName();
		File out = new File(dirOut, inName + ".tex");
		BufferedWriter writer = new BufferedWriter(new FileWriter(out));
		try
		{
			writer.write(start.replace("$", escapeUnderscores(inName)));
			writer.write(whole.toString());
			writer.write(end);
		}
		finally
		{
			writer.close();
		}
	}

	private void writeSnippet(String name, int startLine, int endLine) throws IOException
	{
		String start = "\\lstset{frame=tb, aboveskip=12pt, belowskip=-3pt, breaklines=true, basicstyle=\\small\\ttfamily, tabsize=2, mathescape=true}\n" +	
									 "\\begin{lstlisting}[caption={$, lines %-^}, label=alloy:&, captionpos=b]\n";
		String end = "\\end{lstlisting}\n";
		
		String inName = in.getName();
		File out =  new File(dirOut, "snip-" + name + ".tex");
		BufferedWriter writer = new BufferedWriter(new FileWriter(out));
		try
		{
			writer.write(start.replace("$", escapeUnderscores(inName)).replace("%", "" + startLine).replace("^", "" + endLine).replace("&", name));
			writer.write(snippets.get(name).toString());
			writer.write(end);
		}
		finally
		{
			writer.close();
		}		
	}

	private String escapeUnderscores(String name)
	{
		return name.replace("_", "\\_");
	}

	private String untab(String str)
	{
		if (str == null)
			return null;
		StringBuilder s = new StringBuilder();
		for (char c : str.toCharArray())
			if (c == '\t')
				s.append("  ");
			else
				s.append(c);
		return s.toString();
	}
	
	private int numberOfLeadingSpaces(String line)
	{
		int size = line.length();
		for (int lp = 0; lp < size; lp++)
			if (!Character.isWhitespace(line.charAt(lp)))
				return lp;
		return 0;
	}
	
	private String stripLeadingSpaces(String line, int leading)
	{
		if (leading < 0 || line.length() < leading)
			return line;
		return line.substring(leading);
	}
	
	private String extractName(String line)
	{
		StringTokenizer tok = new StringTokenizer(line);
		tok.nextToken();
		if (tok.hasMoreTokens())
			return tok.nextToken();
		return "";
	}
}
