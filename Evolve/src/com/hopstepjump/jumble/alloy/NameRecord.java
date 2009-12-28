package com.hopstepjump.jumble.alloy;

public class NameRecord
{
  private String prefix;
  private String name;
  private String number;
	private boolean newStyleNaming;

  public NameRecord(String prefix, String name, String number, boolean newStyleNaming)
  {
    this.prefix = prefix;
    this.name = name;
    this.number = number;
    this.newStyleNaming = newStyleNaming;
  }
  
  public boolean prefixIncludes(String includes)
  {
    return prefix.indexOf(includes) != -1;
  }

  public String getFullName()
  {
    return name + number;
  }

  public String getName()
  {
    return name;
  }

  public String getNumber()
  {
    return number;
  }

  public String getIndexedFullName()
  {
    return newStyleNaming ? (name + "$" + number) : (name + "[" + number + "]");
  }

  public int getActualNumber()
  {
    return new Integer(number);
  }
  
  public String toString()
  {
    return prefix + "/" + getIndexedFullName();
  }
  
  public boolean equals(Object other)
  {
    if (!(other instanceof NameRecord))
      return false;
    NameRecord otherRecord = (NameRecord) other;
    return prefix.equals(otherRecord.prefix) && name.equals(otherRecord.name) && number.equals(otherRecord.number);
  }
  
  public int hashCode()
  {
    return prefix.hashCode() ^ name.hashCode() ^ number.hashCode();
  }
}

