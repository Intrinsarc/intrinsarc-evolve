package org.freehep.record.source;

import java.util.*;

/**
 * 
 * @version $Id: RecordReadyEvent.java,v 1.1 2009-03-04 22:46:59 andrew Exp $
 */
public class RecordReadyEvent extends EventObject
{
  RecordReadyEvent(AsynchronousRecordSource source)
  {
    super(source);
  }
}
