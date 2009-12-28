package org.freehep.graphicsio;

import java.awt.*;
import java.io.*;

import org.freehep.graphics2d.*;

public interface MultiPageDocument
{

  public void setMultiPage(boolean isMultiPage);
  public boolean isMultiPage();

  /** Set the headline of all pages. */
  public void setHeader(Font font, TagString left, TagString center, TagString right, int underlineThickness);

  /** Set the footline of all pages. */
  public void setFooter(Font font, TagString left, TagString center, TagString right, int underlineThickness);

  /** Start the next page */
  public void openPage(Component component) throws IOException;
  public void openPage(Dimension size, String title) throws IOException;

  /** End the current page. */
  public void closePage() throws IOException;

}