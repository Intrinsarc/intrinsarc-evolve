package org.freehep.record.source;

import java.util.*;

public interface RecordReadyListener extends EventListener
{
  void nextRecordReady(RecordReadyEvent event);
}
