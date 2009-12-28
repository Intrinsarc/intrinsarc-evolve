package com.hopstepjump.backbone.nodes.converters;

import java.io.*;

public class JsonIndenter
{
  private Reader buffer;
  
  public JsonIndenter(Reader buffer)
  {
    this.buffer = buffer;
  }
  
  public String formString()
  {
    StringBuilder b = new StringBuilder();

    // start with each character and process until we get to a comma (new line/indent), a start quote (store string),
    // an end quote (print string), a start bracket (indent + newline/indent), an end bracket (de-indent)
    int indent = 0;
    boolean inQuotes = false;
    boolean escaped = false;
    String collected = "";
    
    int i;
    try
		{
			while ((i = buffer.read()) != -1)
			{
			  char c = (char) i;
			  
			  // mini-state machine
			  if (inQuotes && !escaped && c == '\\')
			  {
			    escaped = true;
			    collected += c;
			  }
			  else
			  if (escaped)
			  {
			    collected += c;
			    escaped = false;
			  }
			  else
			  if (c == '\"')
			  {
			    inQuotes = !inQuotes;
			    if (inQuotes)
			      collected = "";
			    else
			    {
			      // if this has whitespace, append without quotes
			      // otherwise, add quotes
			      if (containsBadCharacters(collected) || collected.length() == 0)
			        b.append("\"" + collected + "\"");
			      else
			        b.append(collected);
			    }
			  }
			  else
			  if (!inQuotes && c == ':')
			    b.append(": ");
			  else
			  if (!inQuotes && c == '{')
			    b.append(makeNewLine(indent) + c + makeNewLine(++indent));
			  else
			  if (!inQuotes && c == '[')
				 b.append(makeNewLine(indent++) + c);
			  else
			  if (!inQuotes && c == ',')
			    b.append(c + /*(previous == '}' ? "" : */makeNewLine(indent))/*)*/;
			  else
			  if (!inQuotes && (c == '}' || c == ']'))
			    b.append(makeNewLine(--indent) + c);
			  else
			  if (inQuotes)
			  {
			    collected += c;
			    escaped = false;
			  }
			  else
			    b.append(c);
			}
		}
    catch (IOException e)
		{
			// won't happen as strings are being used
		}
    
    return b.toString();
  }

	private boolean containsBadCharacters(String collected)
  {
    int size = collected.length();
    for (int lp = 0; lp < size; lp++)
    {
      char ch = collected.charAt(lp);
      if (ch == ',' || ch == '}' || ch == '\\' || ch == ':')
        return true;
    }
    return false;
  }

  private String makeNewLine(int i)
  {
    String str = "\n";
    for (int lp = 0; lp < i; lp++)
      str += "  ";
    return str;
  }
}
