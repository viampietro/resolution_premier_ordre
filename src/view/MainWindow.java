package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainWindow {

	private JFrame mainWindowFrame;
	private JTextField formulaEntry;
	private JTextArea logTextArea;
	private JButton resolutionButton;
	private JButton createFormulaButton;
	private JButton clearConsoleButton;
	private UserManualWindow userManualWindow;
	private JMenu userManualMenu;

	/**
	 * Create the application.
	 */

	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		mainWindowFrame = new JFrame();
		mainWindowFrame.setMinimumSize(new Dimension(553, 440));
		mainWindowFrame.setTitle("FOL Solver");
		mainWindowFrame.setBounds(100, 100, 899, 562);
		mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 537 };
		gridBagLayout.rowHeights = new int[] { 10, 294, 47 };
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0 };
		mainWindowFrame.getContentPane().setLayout(gridBagLayout);

		JPanel formulaPanel = new JPanel();
		formulaPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		formulaPanel.setLayout(new BoxLayout(formulaPanel, BoxLayout.X_AXIS));

		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBorder(new EmptyBorder(5, 5, 5, 5));
		formulaPanel.add(horizontalBox);

		JLabel formulaLabel = new JLabel("Formule");
		horizontalBox.add(formulaLabel);
		formulaLabel.setHorizontalAlignment(SwingConstants.CENTER);

		formulaEntry = new JTextField();
		formulaEntry.setFont(new Font("Verdana", Font.PLAIN, 14));
		formulaEntry.setHorizontalAlignment(SwingConstants.LEFT);
		formulaPanel.add(formulaEntry);
		formulaEntry.setToolTipText("Entrez une formule bien formée de la logique du premier ordre");
		formulaEntry.setColumns(10);

		createFormulaButton = new JButton("Créer");
		formulaPanel.add(createFormulaButton);
		GridBagConstraints gbc_formulaPanel = new GridBagConstraints();
		gbc_formulaPanel.fill = GridBagConstraints.BOTH;
		gbc_formulaPanel.insets = new Insets(0, 0, 5, 0);
		gbc_formulaPanel.gridx = 0;
		gbc_formulaPanel.gridy = 0;
		mainWindowFrame.getContentPane().add(formulaPanel, gbc_formulaPanel);

		JPanel logPanel = new JPanel();
		logPanel.setBorder(new EmptyBorder(0, 10, 5, 10));
		logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.PAGE_AXIS));

		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBorder(new EmptyBorder(5, 5, 5, 5));
		logPanel.add(verticalBox);

		JLabel logLabel = new JLabel("Console :");
		verticalBox.add(logLabel);
		logLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		logLabel.setVerticalAlignment(SwingConstants.TOP);
		logLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JScrollPane logScrollPane = new JScrollPane();
		logScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		logScrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
		logPanel.add(logScrollPane);

		logTextArea = new JTextArea();
		logTextArea.setFont(new Font("Verdana", Font.PLAIN, 15));
		logScrollPane.setViewportView(logTextArea);
		logTextArea.setLineWrap(true);
		logTextArea.setToolTipText("Affiche la trace d'éxecution de l'algorithme de résolution");
		logTextArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		logTextArea.setAlignmentY(Component.TOP_ALIGNMENT);
		logTextArea.setColumns(10);
		logTextArea.setRows(10);
		GridBagConstraints gbc_logPanel = new GridBagConstraints();
		gbc_logPanel.fill = GridBagConstraints.BOTH;
		gbc_logPanel.insets = new Insets(0, 0, 5, 0);
		gbc_logPanel.gridx = 0;
		gbc_logPanel.gridy = 1;
		mainWindowFrame.getContentPane().add(logPanel, gbc_logPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(0, 5, 5, 5));
		FlowLayout fl_buttonPanel = new FlowLayout(FlowLayout.CENTER, 5, 5);
		buttonPanel.setLayout(fl_buttonPanel);

		resolutionButton = new JButton("Résoudre");
		resolutionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(resolutionButton);

		clearConsoleButton = new JButton("Effacer Console");
		buttonPanel.add(clearConsoleButton);
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPanel.gridx = 0;
		gbc_buttonPanel.gridy = 2;
		mainWindowFrame.getContentPane().add(buttonPanel, gbc_buttonPanel);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		mainWindowFrame.setJMenuBar(menuBar);
		
		userManualMenu = new JMenu("Manuel d'utilisation");
		menuBar.add(userManualMenu);

		setUserManualWindow(new UserManualWindow());

	}

	public JMenuItem getUserManualMenu() {
		return userManualMenu;
	}

	public void setUserManualMenu(JMenu userManualMenu) {
		this.userManualMenu = userManualMenu;
	}

	public JButton getClearConsoleButton() {
		return clearConsoleButton;
	}

	public void setClearConsoleButton(JButton clearConsoleButton) {
		this.clearConsoleButton = clearConsoleButton;
	}

	public JButton getCreateFormulaButton() {
		return createFormulaButton;
	}

	public void setCreateFormulaButton(JButton createFormulaButton) {
		this.createFormulaButton = createFormulaButton;
	}

	public JButton getResolutionButton() {
		return resolutionButton;
	}

	public void setResolutionButton(JButton resolutionButton) {
		this.resolutionButton = resolutionButton;
	}

	public JFrame getMainWindow() {
		return mainWindowFrame;
	}

	public void setMainWindow(JFrame mainWindow) {
		this.mainWindowFrame = mainWindow;
	}

	public JTextField getFormulaEntry() {
		return formulaEntry;
	}

	public void setFormulaEntry(JTextField formulaEntry) {
		this.formulaEntry = formulaEntry;
	}

	public JTextArea getLogTextArea() {
		return logTextArea;
	}

	public void setLogTextArea(JTextArea logTextArea) {
		this.logTextArea = logTextArea;
	}

	public UserManualWindow getUserManualWindow() {
		return userManualWindow;
	}

	public void setUserManualWindow(UserManualWindow userManualWindow) {
		this.userManualWindow = userManualWindow;
	}
}
