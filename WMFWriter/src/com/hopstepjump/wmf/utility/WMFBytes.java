/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf.utility;

import java.io.*;

/**
 * @version 	1.0
 * @author
 */
public interface WMFBytes
{
	public int sizeInBytes();
	public void writeBytes(OutputStream out) throws IOException;
}
