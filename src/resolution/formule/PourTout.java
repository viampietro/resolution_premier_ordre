package resolution.formule;

import java.util.ArrayList;

import resolution.cnf.Clause;

/*
 * CLASSE POUR TOUT
 */
public class PourTout extends Formule {

	Variable var;
	Formule cible;

	public PourTout(Variable var, Formule cible) {
		super();
		this.var = var;
		this.cible = cible;
	}

	@Override
	public Formule simplifier() {
		throw new CantSimplifyQuantifierException(this);
	}

	@Override
	public ArrayList<Clause> clausifier() {
		throw new CantCNFQuantifierException(this);
	}

	@Override
	public Formule nier() {
		return new Non(this);
	}

	@Override
	public void substituerVariable(Variable var, Terme t) {
		cible.substituerVariable(var, t);
	}

	@Override
	public void recolterVariables() {
		variablesLiees.add(var.toString());
		cible.recolterVariables();
	}

	@Override
	public Formule skolemiser() {
		return cible.skolemiser();
	}

	@Override
	public Formule herbrandiser() {

		/*
		 * Une nouvelle fonction est créée avec en arguments
		 * l'ensemble des variables libres du contexte.
		 */
		ArrayList<Terme> args = new ArrayList<>();

		for (String var : variablesLibres)
			args.add(new Variable(var));

		Fonction nouvelleFonction = new Fonction(args);
		
		/*
		 * Substitution de chaque occurrence de var
		 * par le nouveau symbole de fonction dans la formule cible.
		 */
		cible.substituerVariable(var, nouvelleFonction);
		
		return cible.herbrandiser();
	}

	@Override
	public String toString() {
		return "forall " + var + "." + cible;
	}

	@Override
	public String toStringWithParenthesis() {

		return "(forall " + var + "." + cible.toStringWithParenthesis() + ")";
	}

}