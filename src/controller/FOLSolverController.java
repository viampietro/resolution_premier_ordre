package controller;

import java.util.Observable;

import mvc.controller.Controller;
import resolution.FOLSolver;
import resolution.parser.ParseException;
import view.FOLSolverView;

public class FOLSolverController extends Controller<FOLSolver, FOLSolverView> {

	public FOLSolverController(FOLSolverView view, FOLSolver model) {
		setView(view);
		setModel(model);
	}

	/**
	 * Lancement de l'algorithme de résolution sur la formule détenue par le
	 * modèle, si le formule a été créee.
	 */
	public void launchResolution() {

		if (getModel().getFormula() != null)
			getModel().resolveFormula();
		else
			getView().getGui().getLogTextArea()
					.setText(getView().getGui().getLogTextArea().getText() + "Aucune formule n'a été créee.\n");

	}

	public void createFormula(String formula) {

		try {
			getModel().createFormula(formula);
			getView().getGui().getLogTextArea()
					.setText(getView().getGui().getLogTextArea().getText() + "Votre formule est bien formée.\n");

		} catch (ParseException e) {

			getView().getGui().getLogTextArea()
					.setText(getView().getGui().getLogTextArea().getText() + e.getMessage() + "\n");

		}

	}

	public void clearConsole() {

		getView().getGui().getLogTextArea().setText("");
	}

	@Override
	public void update(Observable o, Object arg) {

	}

}
