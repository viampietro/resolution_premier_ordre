package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class UserManualWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public UserManualWindow() {
		
		setTitle("Manuel d'utilisation");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1072, 749);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 424, 0 };
		gbl_contentPane.rowHeights = new int[] { 251, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);

		JTextArea txtrManuelDutilisationDu = new JTextArea();
		txtrManuelDutilisationDu.setLineWrap(true);
		txtrManuelDutilisationDu.setText(
				"MANUEL D'UTILISATION DE FOL SOLVER:\r\n\r\nFOL Solver permet à l'utilisateur d'entrer une formule logique du premier ordre, obéissant à une syntaxe particulière, et de lancer l'algorithme de résolution sur cette formule.\r\n\r\nSYNTAXE:\r\n\r\nLa formule entrée par l'utilisateur doit respecter les règles syntaxiques suivantes.\r\n\r\nterme  ::= var | fun | fun termes\r\nterms ::= \"(\" terme \")\" | \"(\" terme \",\" termes \")\"\r\nformule  ::=   predicat\r\n          | predicat termes\r\n          | \"!\" formule\r\n          | \"(\" formule \"&\" formule \")\"\r\n          | \"(\" formule \"|\" formule \")\"\r\n          | \"(\" formule \"->\" formule \")\"\r\n          | \"exists \" var \".\" formule\r\n          | \"forall \" var \".\" form\r\npredicat ::= bottom | top | [a-z]+\r\nvar ::= [A-Z]{1}\r\nfun ::= (?!f$[0-9]+\\b)[a-z]+\r\n\r\n- Tout opérateur binaire (&, | et ->) et ses opérandes doivent être entourés de parenthèses pour que la formule soit bien formée.\r\n- De fait, la mise en série d'opérateurs n'est pas possible. Par exemple, on ne peut pas écrire (p(X) | p(Y) | p(Z)), mais on doit écrire\r\n((p(X) | p(Y)) | p(Z)). En effet, la syntaxe en est un peu alourdie.\r\n- Pour avoir une formule bien formée, les variables doivent être en majuscules (X, Y, Z...), et les prédicats et fonctions en minuscules (p(X), p(f(Y))).\r\n- Le nom des fonctions ne doit pas être de la forme f$ suivi d'un entier, car ce sont des symboles réservés à la production aléatoire de fonctions \r\nlors de l'herbrandisation ou de la skolémisation d'une formule.\r\n\r\n");
		scrollPane.setViewportView(txtrManuelDutilisationDu);
	}

}
