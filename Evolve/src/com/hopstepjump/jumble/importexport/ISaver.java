package com.hopstepjump.jumble.importexport;

import javax.swing.*;

public interface ISaver
{
	int askAboutSave(String message);
	AbstractAction makeRefreshAction();
	AbstractAction makeGarbageCollectAction();
}

