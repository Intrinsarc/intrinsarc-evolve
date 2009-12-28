package ltsa.ui;

/* Creates the Example menu
 */
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class Examples {

	JMenu parent;
	HPWindow out;

	public Examples(JMenu parent, HPWindow out) {
		this.parent = parent;
		this.out = out;
	}

	public void getExamples() {
		List<String> chapters = getContents("/example/contents.txt");
		for (String s : chapters) {
			JMenu chapter = new JMenu(s.substring(0, s.indexOf('_')));
			parent.add(chapter);
			List<String> examples = getContents("/example/" + s
					+ "/contents.txt");
			for (String es : examples) {
				int dot = es.indexOf('.');
				String exs = dot > 0 ? es.substring(0, dot) : es;
				JMenuItem example = new JMenuItem(exs);
				example.addActionListener(new ExampleAction("/example/" + s
						+ "/", es));
				chapter.add(example);
			}
		}
	}

	private List<String> getContents(String resource) {
		List<String> contents = new ArrayList<String>(16);
		try {
			InputStream fin = this.getClass().getResourceAsStream(resource);
			BufferedReader myInput = new BufferedReader(new InputStreamReader(
					fin));
			String thisLine;
			while ((thisLine = myInput.readLine()) != null) {
				if (!thisLine.equals(""))
					contents.add(thisLine);
			}
		} catch (Exception e) {
			out.outln("Error getting resource: " + resource);
		}
		return contents;
	}

	class ExampleAction implements ActionListener {
		String dir, ex;

		ExampleAction(String dir, String ex) {
			this.dir = dir;
			this.ex = ex;
		}

		public void actionPerformed(ActionEvent e) {
			out.newExample(dir, ex);
		}
	}

}
