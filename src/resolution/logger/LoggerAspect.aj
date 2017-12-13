package resolution.logger;

import java.util.ArrayList;

import resolution.CheckRuleException;
import resolution.ConflictRuleException;
import resolution.Equation;
import resolution.cnf.Clause;
import resolution.formule.Predicat;
import resolution.formule.Formule;;

public aspect LoggerAspect extends Logger {

	/**
	 * Point de coupure, appel à la fonction clausifier() dans la méthode
	 * résoudre().
	 */
	pointcut startingCnf():
		withincode(boolean Formule.resoudre()) &&
		call(ArrayList<Clause> Formule.clausifier());

	/**
	 * Advice, ajout d'un message de log présentant l'ensemble des clauses de
	 * départ avant le lancement de l'algortihme de résolution.
	 * 
	 * @param clauses
	 */
	after() 
	returning(ArrayList<Clause> clauses): startingCnf() {
		addLogMessage(new LogMessage(LogMessageType.STARTING_CNF, clauses.toString()));
	}

	/**
	 * Point de coupure faisant référence à l'appel de la même resoudreAvec, qui
	 * indique la tentative de résolution entre deux clauses au cours de
	 * l'éxecution de l'algorithme.
	 * 
	 * @param caller
	 * @param param
	 */
	pointcut launchResolution(Clause caller, Clause param): 
		target(caller) &&
		args(param) &&
		call(Clause Clause.resoudreAvec(Clause));

	/**
	 * Point de coupure faisant référence à l'appel genererEquations qui
	 * advient, au cours de l'éxecution de l'algorithme de résolution, lorsque
	 * deux atomes contraires ont été observés dans les clauses à résoudre.
	 */
	pointcut contraryAtomsFound():
		call(ArrayList<Equation> Predicat.genererEquations(Predicat));

	/**
	 * Advice, ajout d'un message de log annonçant le début de la résolution
	 * entre deux clauses.
	 * 
	 * @param caller
	 * @param param
	 */
	before(Clause caller, Clause param): contraryAtomsFound() && cflow(launchResolution(caller, param)) {

		addLogMessage(new LogMessage(LogMessageType.LAUNCH_RESOLUTION,
				"Résolution entre les clauses " + caller + " et " + param));

	}

	after(Clause caller, Clause param) 
	returning(Clause clauseResultante): launchResolution(caller, param) {

		if (clauseResultante != null) {
			if (clauseResultante.isEmpty())
				addLogMessage(new LogMessage(LogMessageType.EMPTY_SET_FOUND, ""));
			else
				addLogMessage(new LogMessage(LogMessageType.RESOLUTION_RESULT, clauseResultante.toString()));
		}

	}

	/**
	 * Point de coupure, appel à la méthode unifier(ArrayList<Equation>) au sein
	 * de la méthode resoudreAvec(Clause).
	 * 
	 * @param param
	 */
	pointcut unifierFound(ArrayList<Equation> param):
		args(param) &&
		withincode(Clause Clause.resoudreAvec(Clause)) &&
		call(ArrayList<Equation> Equation.unifier(ArrayList<Equation>));

	/**
	 * Advice, ajout d'un message de log annonçant la découverte d'un
	 * unificateur pour deux clauses à résoudre.
	 * 
	 * @param param
	 * @param unifier
	 */
	after(ArrayList<Equation> param) 
	returning (ArrayList<Equation> unifier): unifierFound(param) {
		addLogMessage(new LogMessage(LogMessageType.UNIFIER_FOUND, unifier.toString()));
	}
	
	after(ArrayList<Equation> param) throwing(ConflictRuleException e) : unifierFound(param) {
		addLogMessage(new LogMessage(LogMessageType.NO_UNIFIER, e.getMessage()));
	}
	
	after(ArrayList<Equation> param) throwing(CheckRuleException e) : unifierFound(param) {
		addLogMessage(new LogMessage(LogMessageType.NO_UNIFIER, e.getMessage()));
	}
	
	pointcut noMoreClauses():
		withincode(boolean Formule.resoudre()) &&
		call(boolean ArrayList.isEmpty());
	
	after() returning(boolean noMoreClauses) : noMoreClauses() {
		if (noMoreClauses)
			addLogMessage(new LogMessage(LogMessageType.NO_MORE_CLAUSES, ""));
	}
}
