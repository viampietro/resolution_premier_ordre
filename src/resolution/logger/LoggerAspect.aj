package resolution.logger;

import java.util.ArrayList;

import resolution.Equation;
import resolution.cnf.Clause;
import resolution.formule.Predicat;

public aspect LoggerAspect extends Logger {

	/**
	 * PointCut faisant référence à l'appel de la même resoudreAvec, qui indique
	 * la tentative de résolution entre deux clauses au cours de l'éxecution de
	 * l'algorithme.
	 * 
	 * @param caller
	 * @param param
	 */
	pointcut launchResolution(Clause caller, Clause param): 
		target(caller) &&
		args(param) &&
		call(Clause Clause.resoudreAvec(Clause));

	/**
	 * Pointcut faisant référence à l'appel genererEquations, qui advient lors
	 * de l'éxecution de l'algorithme de résolution lorsque deux atomes
	 * contraires ont été observés dans les clauses à résoudre.
	 */
	pointcut contraryAtomsFound():
		call(ArrayList<Equation> Predicat.genererEquations(Predicat));

	before(Clause caller, Clause param): contraryAtomsFound() && cflow(launchResolution(caller, param)) {

		addLogMessage(new LogMessage(LogMessageType.LAUNCH_RESOLUTION,
				"Résolution entre les clauses " + caller + " et " + param));

	}

	pointcut addUnifierFoundMessage(ArrayList<Equation> param):
		args(param) &&
		withincode(Clause Clause.resoudreAvec(Clause)) &&
		call(ArrayList<Equation> Equation.unifier(ArrayList<Equation>));

	after(ArrayList<Equation> param) 
	returning (ArrayList<Equation> unifier): addUnifierFoundMessage(param) {
		addLogMessage(new LogMessage(LogMessageType.UNIFIER_FOUND, "Unificateur trouvé " + unifier));
	}
}
