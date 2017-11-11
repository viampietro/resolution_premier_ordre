package resolution.formule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
		 * r�colt�es et ajout�es ou non � la liste des variables libres ou
		 * li�es.
		 */
		for (Terme t : args)
			for (String var : t.getVariables().keySet())

				/*
				 * Si la variable n'est ni dans la liste des variables li��s ni
				 * dans celle des variables libres, alors c'est une variable
				 * libre.
				 */
				if (!variablesLibres.contains(var) && !variablesLiees.contains(var))
					variablesLibres.add(var);

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
