package ui;

import ac.ic.doc.mtstools.model.*;

public class RefinementOptions {
	private int refinedModel = -1;
	private int refinesModel = -1;
	private boolean bidirectionalCheck;
	private SemanticType refinementSemantic;

	/**
	 * @return the refinedModel
	 */
	protected int getRefinedModel() {
		return refinedModel;
	}

	/**
	 * @return the refinementSemantic
	 */
	protected SemanticType getRefinementSemantic() {
		return refinementSemantic;
	}

	/**
	 * @return the refinesModel
	 */
	protected int getRefinesModel() {
		return refinesModel;
	}

	/**
	 * @return the bidirectionalCheck
	 */
	protected boolean isBidirectionalCheck() {
		return bidirectionalCheck;
	}

	public boolean isValid() {
		return getRefinedModel() >= 0 && getRefinesModel() >= 0
				&& getRefinementSemantic() != null;
	}

	/**
	 * @param bidirectionalCheck
	 *            the bidirectionalCheck to set
	 */
	protected void setBidirectionalCheck(boolean bidirectionalCheck) {
		this.bidirectionalCheck = bidirectionalCheck;
	}

	/**
	 * @param refinedModel
	 *            the refinedModel to set
	 */
	protected void setRefinedModel(int refinedModel) {
		this.refinedModel = refinedModel;
	}

	/**
	 * @param refinementSemantic
	 *            the refinementSemantic to set
	 */
	protected void setRefinementSemantic(SemanticType refinementSemantic) {
		this.refinementSemantic = refinementSemantic;
	}

	/**
	 * @param refinesModel
	 *            the refinesModel to set
	 */
	protected void setRefinesModel(int refinesModel) {
		this.refinesModel = refinesModel;
	}

}
