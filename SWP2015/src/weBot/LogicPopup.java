package weBot;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogicPopup extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel2 = new JPanel();
	private JTextField txtEnterLogic;
	private JTextArea txtrAsdf;
	private String logic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LogicPopup dialog = new LogicPopup();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LogicPopup() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel2.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel2, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		contentPanel2.setLayout(gbl_contentPanel);
		{
			txtEnterLogic = new JTextField();
			txtEnterLogic.setEditable(false);
			txtEnterLogic.setText("Enter logic:");
			GridBagConstraints gbc_txtEnterLogic = new GridBagConstraints();
			gbc_txtEnterLogic.insets = new Insets(0, 0, 5, 0);
			gbc_txtEnterLogic.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtEnterLogic.gridx = 0;
			gbc_txtEnterLogic.gridy = 0;
			contentPanel2.add(txtEnterLogic, gbc_txtEnterLogic);
			txtEnterLogic.setColumns(10);
		}
		{
			txtrAsdf = new JTextArea();
			GridBagConstraints gbc_txtrAsdf = new GridBagConstraints();
			gbc_txtrAsdf.fill = GridBagConstraints.BOTH;
			gbc_txtrAsdf.gridx = 0;
			gbc_txtrAsdf.gridy = 1;
			contentPanel2.add(txtrAsdf, gbc_txtrAsdf);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if ("Cancel".equals(e.getActionCommand())) {
			dispose();
		} else {
			if ("OK".equals(e.getActionCommand())) {
				logic = txtrAsdf.getText();
				// TODO
				// send logic to We-B-ot or LogicObject

				dispose();
			}
		}
	}

	public String getLogic() {
		return logic;
	}
}
