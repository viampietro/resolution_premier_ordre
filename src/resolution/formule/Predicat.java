package resolution.formule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import resolution.Equation;
import resolution.cnf.Atome;
import resolution.cnf.AtomeSimple;
import resolution.cnf.Clause;

/*
 * CLASSE PREDICAT
 */
public class Predicat extends Formule {

	String nom;
	ArrayList<Terme> args;

	/*
	 * CONSTRUCTORS
	 */
	public Predicat(Predicat p) {
		this.nom = p.nom;
		this.args = new ArrayList<>(p.args);
	}

	public Predicat(String nom, Terme... args) {
		super();
		this.nom = nom;

		this.args = new ArrayList<>();
		for (Terme t : args) {
			this.args.add(t);
		}
	}

	/*
	 * GETTERS AND SETTERS
	 */
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public ArrayList<Terme> getArgs() {
		return args;
	}

	public void setArgs(ArrayList<Terme> args) {
		this.args = args;
	}

	/*
	 * METHODS
	 */
	@Override
	public ArrayList<Clause> clausifier() {

		/**
		 * Retourne une liste contenant une unique Clause qui elle m�me contient
		 * un unique atome correspondant au pr�dicat courant
		 */
		Atome a = new AtomeSimple(false, this);

		Map<String, Atome> atomes = new HashMap<String, Atome>();
		atomes.put(a.toString(), a);

		Clause c = new Clause(atomes);

		ArrayList<Clause> formeClausale = new ArrayList<Clause>();
		formeClausale.add(c);

		return formeClausale;

	}

	@Override
	public Formule nier() {
		return new Non(this);

	}

	@Override
	public Formule simplifier() {
		return this;
	}

	@Override
	public void substituerVariable(Variable var, Terme t) {

		ArrayList<Terme> nouveauxArgs = new ArrayList<>();

		for (Terme terme : args)
			nouveauxArgs.add(terme.substituerVariable(var, t));

		args = nouveauxArgs;

	}

	@Override
	public void recolterVariables() {

		/*
		 * Pour chaque terme qui est argument du pr�dicat, ses variables sont
		 * récoltées et ajoutées ou non à la liste des variables libres ou
		 * liées.
		 */
		for (Terme t : args)
			for (String var : t.getVariables().keySet())

				/*
				 * Si la variable n'est ni dans la liste des variables liées ni
				 * dans celle des variables libres, alors c'est une variable
				 * libre.
				 */
				if (!variablesLibres.contains(var) && !variablesLiees.contains(var))
					variablesLibres.add(var);

	}

	/**
	 * 
	 * @param predicat,
	 *            le predicat qui sera comparer avec le predicat courant pour
	 *            appliquer le renommage des variables.
	 * 
	 *            Modifie le prédicat courant et le prédicat passé en argument,
	 *            tel qu'à la fin de l'opération les deux prédicats ne possèdent
	 *            plus aucune variable en commun.
	 */
	public void renommerVariables(Predicat predicat) {
		throw new RuntimeException("NYI method Predicat.renommerVariables(Predicat)");
	}

	/**
	 * 
	 * @param predicat,
	 *            le prédicat dont les arguments seront couplés avec ceux du
	 *            prédicat courant pour former la liste d'équations finale.
	 * 
	 * @return Une liste d'équations correspondant au couplage des arguments du
	 *         prédicat courant avec ceux du prédicat en paramètre. Par exemple,
	 *         pour P(x, a) et P(b, y), la liste d'équations générée est {x = b,
	 *         a = y}. Si le prédicat courant et le prédicat en paramètre ne
	 *         sont pas d'arité égale, null est retourné.
	 * 
	 */
	public ArrayList<Equation> genererEquations(Predicat predicat) {

		ArrayList<Equation> equations = new ArrayList<>();

		/*
		 * Si le predicat courant et le predicat passé en argument ne sont pas
		 * d'arité égale, alors null est renvoyé.
		 */
		if (getArgs().size() != predicat.getArgs().size())
			return null;

		/*
		 * Sinon, une équation est créée pour chaque couple de termes du
		 * predicat courant et du predicat passé en paramètre. Par exemple, pour
		 * P(x, a) et P(b, y), la liste d'équations générée est {x = b, a = y}
		 */
		else
			for (int i = 0; i < getArgs().size(); i++)
				equations.add(new Equation(getArgs().get(i), predicat.getArgs().get(i)));

		return equations;
	}

	@Override
	public Formule skolemiser() {
		return this;
	}

	@Override
	public Formule herbrandiser() {
		return this;
	}

	@Override
	public String toString() {
		String argsToString = "(";

		for (int i = 0; i < args.size(); i++) {

			if (i == args.size() - 1)
				argsToString += args.get(i);
			else
				argsToString += args.get(i) + ",";
		}

		return nom + argsToString + ")";

	}

	@Override
	public String toStringWithParenthesis() {
		return toString();
	}

}
