<?xml version='1.0' encoding='ISO-8859-1' ?>
<!DOCTYPE helpset   
PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 2.0//EN"
         "http://java.sun.com/products/javahelp/helpset_2_0.dtd">

<helpset version="2.0">

  <!-- title -->
  <title>jUMbLe Help</title>

  <!-- maps -->
  <maps>
     <homeID>intro</homeID>
     <mapref location="AnimalsMap.jhm"/>
  </maps>
  
  <presentation default="true" displayviewimages="true">
    <size width="600" height="500" />
    <location x="200" y="200" />
    <image>icon</image>
    <toolbar>
      <helpaction>javax.help.SeparatorAction</helpaction>
    </toolbar>
  </presentation>

  <!-- views -->
  <view mergetype="javax.help.UniteAppendMerge">
    <name>TOC</name>
    <label>Contents</label>
    <type>javax.help.TOCView</type>
    <image>contents</image>
    <data>AnimalsTOC.xml</data>
  </view>

  <view mergetype="javax.help.SortMerge">
    <name>Index</name>
    <label>Index</label>
    <type>javax.help.IndexView</type>
    <image>index</image>
    <data>AnimalsIndex.xml</data>
  </view>

  <view >
    <name>Search</name>
    <label>Search</label>
    <type>javax.help.SearchView</type>
    <image>search</image>
    <data engine="com.sun.java.help.search.DefaultSearchEngine">
      JavaHelpSearch
    </data>
  </view>
  
</helpset>

