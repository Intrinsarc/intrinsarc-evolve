package ltsa.editor;

import javax.swing.text.*;

/**
 * uses ColoredDocument to render text according to FSP syntax
 */
public class ColoredEditorKit extends DefaultEditorKit {

	private static final long serialVersionUID = 509636631175167628L;

	private ColoredContext d_Preferences;

	// .......................................................................

	// CONSTRUCTOR-DESTRUCTOR
	public ColoredEditorKit() {
		super();
		d_Preferences = new ColoredContext();
	}

	// .......................................................................

	// MANIPULATORS
	public void setStylePreferences(ColoredContext prefs) {
		d_Preferences = prefs;
	}

	public ColoredContext getStylePreferences() {
		return d_Preferences;
	}

	/**
	 * Fetches a factory that is suitable for producing views of any models that
	 * are produced by this kit. The default is to have the UI produce the
	 * factory, so this method has no implementation.
	 * 
	 * @return the view factory
	 */
	@Override
	public final ViewFactory getViewFactory() {
		return (getStylePreferences());
	}

	// .......................................................................

	// ACCESSORS
	/**
	 * Get the MIME type of the data that this kit represents support for. This
	 * kit supports the type <code>text/lts</code>.
	 */
	@Override
	public String getContentType() {
		return ("text/lts");
	}

	/**
	 * Create a copy of the editor kit. This allows an implementation to serve
	 * as a prototype for others, so that they can be quickly created.
	 */
	@Override
	public ColoredEditorKit clone() {
		ColoredEditorKit kit = new ColoredEditorKit();
		kit.d_Preferences = d_Preferences;
		return kit;
	}

	/**
	 * Creates an uninitialized text storage model that is appropriate for this
	 * type of editor.
	 * 
	 * @return the model
	 */
	@Override
	public Document createDefaultDocument() {
		return (new ColoredDocument());
	}
}