package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;

import controller.FOLSolverController;
import mvc.view.View;
import resolution.FOLSolver;
import resolution.formule.Formule;
import resolution.logger.LogMessage;

public class FOLSolverView extends View<FOLSolver, FOLSolverController> {

	private MainWindow gui;

	public FOLSolverView() {

		/**
		 * SETTING UP GUI
		 */
		gui = new MainWindow();

		/**
		 * SETTING LISTENERS
		 */
		gui.getResolutionButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getController().launchResolution();
			}
		});

		gui.getCreateFormulaButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getController().createFormula(gui.getFormulaEntry().getText());
			}
		});

		gui.getClearConsoleButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getController().clearConsole();
			}
		});

		/**
		 * SETTING MVC COMPONENTS
		 */

		FOLSolver model = new FOLSolver();
		FOLSolverController controller = new FOLSolverController(this, model);

		setModel(model);
		setController(controller);

		model.addObserver(this);
		model.addObserver(controller);

	}

	public MainWindow getGui() {
		return gui;
	}

	public void setGui(MainWindow gui) {
		this.gui = gui;
	}

	public void startApp() {
		gui.getMainWindow().setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {

		/**
		 * La formula a été crée et assignée dans le modèle.
		 */
		if (arg instanceof Formule)
			gui.getLogTextArea()
					.setText(gui.getLogTextArea().getText() + "Formule courante : " + getModel().getFormula() + "\n");

		/**
		 * Si arg est un booléen, le modèle prévient la vue que l'algorithme de
		 * résolution a terminé.
		 */
		else if (arg instanceof Boolean) {

			/**
			 * Affichage de tous les messages du logger dans la console
			 */
			for (LogMessage m : getModel().getLogger().getLogMessages()) {
				gui.getLogTextArea().setText(gui.getLogTextArea().getText() + m.toString() + "\n");
			}

			/**
			 * Efface les messages de log enregistrés par le dernier lancement
			 * de l'algorithme de résolution.
			 */
			getModel().getLogger().getLogMessages().removeAll(getModel().getLogger().getLogMessages());

			if ((Boolean) arg)
				gui.getLogTextArea().setText(gui.getLogTextArea().getText() + "La formule courante "
						+ getModel().getFormula() + " est valide.\n");

			else
				gui.getLogTextArea().setText(gui.getLogTextArea().getText() + "La formule courante "
						+ getModel().getFormula() + " est satisfiable.\n");
		}

	}

}
