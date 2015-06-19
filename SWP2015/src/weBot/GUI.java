package weBot;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtStatus;
	private JTextArea textArea;
	private LogicPopup logic;
	private String logicText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JButton btnNewGamelogic = new JButton("New GameLogic");
		btnNewGamelogic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logic = new LogicPopup();
				logic.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnNewGamelogic = new GridBagConstraints();
		gbc_btnNewGamelogic.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewGamelogic.gridx = 1;
		gbc_btnNewGamelogic.gridy = 1;
		contentPane.add(btnNewGamelogic, gbc_btnNewGamelogic);

		JButton btnStart = new JButton("Start");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.insets = new Insets(0, 0, 5, 5);
		gbc_btnStart.gridx = 3;
		gbc_btnStart.gridy = 1;
		contentPane.add(btnStart, gbc_btnStart);

		JButton btnStop = new JButton("Stop");
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.insets = new Insets(0, 0, 5, 5);
		gbc_btnStop.gridx = 5;
		gbc_btnStop.gridy = 1;
		contentPane.add(btnStop, gbc_btnStop);

		txtStatus = new JTextField();
		txtStatus.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatus.setEditable(false);
		txtStatus.setText("Status");
		GridBagConstraints gbc_txtStatus = new GridBagConstraints();
		gbc_txtStatus.anchor = GridBagConstraints.SOUTHWEST;
		gbc_txtStatus.insets = new Insets(0, 0, 5, 5);
		gbc_txtStatus.gridx = 1;
		gbc_txtStatus.gridy = 3;
		contentPane.add(txtStatus, gbc_txtStatus);
		txtStatus.setColumns(10);

		textArea = new JTextArea();
		textArea.setColumns(2);
		textArea.setRows(2);
		textArea.setEditable(false);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.anchor = GridBagConstraints.NORTHWEST;
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridheight = 3;
		gbc_textArea.gridwidth = 6;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 4;
		contentPane.add(textArea, gbc_textArea);
	}

	public void writeToStatusArea(String message) {
		textArea.append(message + "\n");
	}

	public void readLogic() throws Exception {
		if (logic != null) {
			logicText = logic.getLogic();
		} else {
			throw new Exception("no logic window open");
		}
	}

	public String getLogic() {
		return logicText;
	}
}
