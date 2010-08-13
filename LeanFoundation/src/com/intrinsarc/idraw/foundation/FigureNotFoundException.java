package com.intrinsarc.idraw.foundation;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class FigureNotFoundException extends RuntimeException
{
  public FigureNotFoundException(String errorMessage)
  {
    super(errorMessage);
  }
}