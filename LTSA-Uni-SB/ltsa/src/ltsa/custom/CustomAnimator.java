package ltsa.custom;

import java.awt.*;
import java.io.*;

import ltsa.lts.*;

public abstract class CustomAnimator extends Frame {

	public abstract void init(Animator a, File xml,
			Relation<String, String> actionMap,
			Relation<String, String> controlMap, boolean replay);

	public abstract void stop();

	@Override
	public void dispose() {
		stop();
		super.dispose();
	}

}