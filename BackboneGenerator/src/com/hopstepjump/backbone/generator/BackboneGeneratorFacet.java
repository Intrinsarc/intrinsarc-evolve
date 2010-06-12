package com.hopstepjump.backbone.generator;

import java.io.*;
import java.util.*;

import com.hopstepjump.gem.*;

public interface BackboneGeneratorFacet extends Facet
{
  public File generate(
  		BackboneGenerationChoice choice,
  		List<String> classpaths,
  		List<String> untranslatedClasspaths,
  		boolean mixed,
  		int modifiedFiles[]) throws BackboneGenerationException;
}
