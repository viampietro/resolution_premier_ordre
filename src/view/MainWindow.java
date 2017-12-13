package view;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainWindow {

	private JFrame mainWindowFrame;
	private JTextField formulaEntry;
	private JTextArea logTextArea;
	private JButton resolutionButton;
	private JButton createFormulaButton;
	private JButton clearConsoleButton;

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
		mainWindowFrame.setMinimumSize(new Dimension(553, 434));
		mainWindowFrame.setTitle("FOL Solver");
		mainWindowFrame.setBounds(100, 100, 553, 463);
		mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		formulaEntry.setFont(new Font("Myanmar Text", Font.PLAIN, 14));
		formulaEntry.setHorizontalAlignment(SwingConstants.LEFT);
		formulaPanel.add(formulaEntry);
		formulaEntry.setToolTipText("Entrez une formule bien formée de la logique du premier ordre");
		formulaEntry.setColumns(10);
		
		JPanel logPanel = new JPanel();
		logPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
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
		logTextArea.setFont(new Font("Myanmar Text", Font.PLAIN, 14));
		logScrollPane.setViewportView(logTextArea);
		logTextArea.setLineWrap(true);
		logTextArea.setToolTipText("Affiche la trace d'éxecution de l'algorithme de résolution");
		logTextArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		logTextArea.setAlignmentY(Component.TOP_ALIGNMENT);
		logTextArea.setColumns(10);
		logTextArea.setRows(10);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		FlowLayout fl_buttonPanel = new FlowLayout(FlowLayout.CENTER, 5, 5);
		buttonPanel.setLayout(fl_buttonPanel);
		
		resolutionButton = new JButton("Résoudre");	
		resolutionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(resolutionButton);
		GroupLayout groupLayout = new GroupLayout(mainWindowFrame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(formulaPanel, GroupLayout.PREFERRED_SIZE, 537, GroupLayout.PREFERRED_SIZE)
				.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 537, GroupLayout.PREFERRED_SIZE)
				.addComponent(logPanel, GroupLayout.PREFERRED_SIZE, 537, GroupLayout.PREFERRED_SIZE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(formulaPanel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(288)
							.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
						.addComponent(logPanel, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)))
		);
		
		clearConsoleButton = new JButton("Effacer Console");
		buttonPanel.add(clearConsoleButton);
		
		createFormulaButton = new JButton("Créer");
		formulaPanel.add(createFormulaButton);
		mainWindowFrame.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		mainWindowFrame.setJMenuBar(menuBar);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		menuBar.add(mntmHelp);
	
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
}
