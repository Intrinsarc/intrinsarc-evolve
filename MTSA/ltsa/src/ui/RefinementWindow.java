package ui;

import javax.swing.*;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import ac.ic.doc.mtstools.model.*;

/**
 * 
 * @author srdipi
 */
public class RefinementWindow extends JDialog { // JFrame {
	private RefinementOptions refinementOptions;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JButton buttonCheck;

	private JComboBox comboRefinedModelFrom;

	private JComboBox comboRefinedModel;

	private JComboBox comboRefinementSemantics;

	private JLabel jLlabelModelsToCheck;
	private JLabel labelRefines;
	private JLabel labelRefinementSemantic;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private javax.swing.JCheckBox jCheckBox1;
	// End of variables declaration//GEN-END:variables
	public RefinementWindow(HPWindow window,
			RefinementOptions refinementOptions, String[] models,
			SemanticType[] refinemetTypes) {
		super(window, true);
		initComponents(models, refinemetTypes);
		this.refinementOptions = refinementOptions;
	}
	private void buttonCheckActionPerformed(java.awt.event.ActionEvent evt) {
		int refinesModel = comboRefinedModelFrom.getSelectedIndex();
		int refinedModel = comboRefinedModel.getSelectedIndex();
		checkRefinement(refinesModel, refinedModel);
		this.dispose();
	}
	private void checkRefinement(int refinesModel, int refinedModel) {
		int refinementSemantic = comboRefinementSemantics.getSelectedIndex();
		this.refinementOptions.setBidirectionalCheck(jCheckBox1.isSelected());
		this.refinementOptions.setRefinedModel(refinedModel);
		this.refinementOptions.setRefinesModel(refinesModel);
		this.refinementOptions
				.setRefinementSemantic(SemanticType.values()[refinementSemantic]);
	}
	private void initComponents(String[] models, SemanticType[] refinemetTypes) {
		jCheckBox1 = new javax.swing.JCheckBox();
		jPanel2 = new JPanel();
		comboRefinedModelFrom = new JComboBox();
		comboRefinedModel = new JComboBox();
		jLlabelModelsToCheck = new JLabel();
		labelRefines = new JLabel();
		jPanel1 = new JPanel();
		comboRefinementSemantics = new JComboBox();
		labelRefinementSemantic = new JLabel();

		// Until we define different semantics properly.
		comboRefinementSemantics.setVisible(true);
		labelRefinementSemantic.setVisible(true);

		jPanel3 = new JPanel();
		buttonCheck = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		comboRefinedModelFrom.setModel(new DefaultComboBoxModel(models));

		comboRefinedModel.setModel(new DefaultComboBoxModel(models));

		jLlabelModelsToCheck.setText("Models to check");

		labelRefines.setText("refines...");

		jCheckBox1.setText("Check both directions");

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
				GroupLayout.LEADING).add(
				jPanel2Layout.createSequentialGroup().add(23, 23, 23).add(
						jPanel2Layout.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING).add(
								jPanel2Layout.createSequentialGroup().add(
										jLlabelModelsToCheck,
										GroupLayout.PREFERRED_SIZE, 125,
										GroupLayout.PREFERRED_SIZE)
										.addContainerGap()).add(
								jPanel2Layout.createSequentialGroup().add(
										comboRefinedModelFrom,
										GroupLayout.PREFERRED_SIZE, 97,
										GroupLayout.PREFERRED_SIZE).add(35, 35,
										35).add(labelRefines).addPreferredGap(
										LayoutStyle.RELATED, 35,
										Short.MAX_VALUE).add(comboRefinedModel,
										GroupLayout.PREFERRED_SIZE, 96,
										GroupLayout.PREFERRED_SIZE).add(49, 49,
										49).add(jCheckBox1).add(57, 57, 57)))));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				GroupLayout.LEADING).add(
				jPanel2Layout.createSequentialGroup().add(25, 25, 25).add(
						jLlabelModelsToCheck).add(29, 29, 29).add(
						jPanel2Layout.createParallelGroup(GroupLayout.BASELINE)
								.add(comboRefinedModel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).add(
										comboRefinedModelFrom,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).add(
										labelRefines).add(132, 132, 132).add(
										jCheckBox1)).addContainerGap(55,
						Short.MAX_VALUE)));

		comboRefinementSemantics.setModel(new DefaultComboBoxModel(
				refinemetTypes));

		labelRefinementSemantic.setText("Refinement semantic:");

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.LEADING).add(
				jPanel1Layout.createSequentialGroup().add(22, 22, 22).add(
						labelRefinementSemantic, GroupLayout.PREFERRED_SIZE,
						96, GroupLayout.PREFERRED_SIZE).addPreferredGap(
						LayoutStyle.RELATED).add(comboRefinementSemantics,
						GroupLayout.PREFERRED_SIZE, 82,
						GroupLayout.PREFERRED_SIZE).addContainerGap(153,
						Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.LEADING).add(
				jPanel1Layout.createSequentialGroup().add(30, 30, 30).add(
						jPanel1Layout.createParallelGroup(GroupLayout.BASELINE)
								.add(labelRefinementSemantic).add(
										comboRefinementSemantics,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(30, Short.MAX_VALUE)));

		buttonCheck.setText("Check");
		buttonCheck.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				buttonCheckActionPerformed(evt);
			}
		});

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(
				GroupLayout.LEADING).add(
				GroupLayout.TRAILING,
				jPanel3Layout.createSequentialGroup().addContainerGap(286,
						Short.MAX_VALUE).add(buttonCheck).addContainerGap()));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(
				GroupLayout.LEADING).add(
				GroupLayout.TRAILING,
				jPanel3Layout.createSequentialGroup().addContainerGap(23,
						Short.MAX_VALUE).add(buttonCheck).add(21, 21, 21)));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(GroupLayout.LEADING)
						.add(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												layout
														.createParallelGroup(
																GroupLayout.LEADING)
														.add(
																GroupLayout.TRAILING,
																layout
																		.createSequentialGroup()
																		.add(
																				jPanel2,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.add(
																GroupLayout.TRAILING,
																layout
																		.createSequentialGroup()
																		.add(
																				layout
																						.createParallelGroup(
																								GroupLayout.TRAILING)
																						.add(
																								GroupLayout.LEADING,
																								jPanel3,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.add(
																								jPanel1,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))
																		.add(
																				20,
																				20,
																				20)))));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.LEADING)
				.add(
						layout.createSequentialGroup().addContainerGap().add(
								jPanel2, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(
								LayoutStyle.RELATED).add(jPanel1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(
								LayoutStyle.RELATED).add(jPanel3,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addContainerGap(
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		pack();
	}// </editor-fold>//GEN-END:initComponents
}
