package resolution;

import java.util.ArrayList;
import java.util.Observer;

import mvc.model.Model;
import resolution.formule.Formule;
import resolution.logger.LoggerAspect;
import resolution.parser.FOLParser;
import resolution.parser.ParseException;

/**
 * Classe faisant office de modèle pour l'application
 * @author Vincent
 *
 */
public class FOLSolver extends Model {

	private Formule formula = null;
	private boolean validFormula = false;
	private LoggerAspect logger = LoggerAspect.aspectOf();

	private ArrayList<Observer> observers;

	public FOLSolver() {
		super();
		observers = new ArrayList<>();
	}

	public void createFormula(String formula) throws ParseException {

		setFormula(FOLParser.parse(formula));
		getFormula().razVariables(); // Raz des variables libres et liées
		
		notifyObservers(formula);
	}

	public void resolveFormula() {

		if (getFormula() != null)
			validFormula = getFormula().resoudre();
		notifyObservers(validFormula);

	}

	public boolean isValidFormula() {
		return validFormula;
	}

	public void setValidFormula(boolean validFormula) {
		this.validFormula = validFormula;
	}

	public Formule getFormula() {
		return formula;
	}

	public void setFormula(Formule formula) {
		this.formula = formula;
		notifyObservers(formula);
	}

	public LoggerAspect getLogger() {
		return logger;
	}

	public void setLogger(LoggerAspect logger) {
		this.logger = logger;
	}

	@Override
	public synchronized void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public synchronized void deleteObserver(Observer o) {
		super.deleteObserver(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers)
			o.update(this, null);
	}

	@Override
	public void notifyObservers(Object arg) {
		for (Observer o : observers)
			o.update(this, arg);
	}

	@Override
	public synchronized void deleteObservers() {
		super.deleteObservers();
	}

	@Override
	public synchronized boolean hasChanged() {
		return super.hasChanged();
	}

	@Override
	public synchronized int countObservers() {
		return super.countObservers();
	}

}
