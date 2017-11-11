package resolution.formule;

import java.util.ArrayList;

import resolution.cnf.Clause;

/*
 * CLASSE ET
 */
public class Et extends Formule {

	private Formule gauche;
	private Formule droit;

	public Et(Formule gauche, Formule droit) {
		super();
		this.gauche = gauche;
		this.droit = droit;
	}

	@Override
	public ArrayList<Clause> clausifier() {

		ArrayList<Clause> formeClausale;

		// Clausification des enfants gauche et droit et fusion des
		// resultats
		formeClausale = gauche.clausifier();
		formeClausale.addAll(droit.clausifier());

		return formeClausale;
	}

	@Override
	public Formule nier() {
		return new Ou(gauche.nier(), droit.nier());
	}

	@Override
	public Formule simplifier() {

		Formule gaucheSimplifie = gauche.simplifier();
		Formule droitSimplifie = droit.simplifier();

		// Si une des deux branches est un Bottom
		// la formule est fausse, on retourne Bottom
		if (gauche instanceof Bottom || droit instanceof Bottom)
			return new Bottom();

		// Sinon, si une des branches est un Top,
		// On retourne l'autre branche
		else if (gauche instanceof Top)
			return droit;
		else if (droit instanceof Top)
			return gauche;

		// Sinon, on retourne un nouveau Et avec deux branches simplifiees
		else
			return new Et(gaucheSimplifie, droitSimplifie);

	}
	
	@Override
	public void substituerVariable(Variable var, Terme t) {
		gauche.substituerVariable(var, t);
		droit.substituerVariable(var, t);
	}

	@Override
	public void recolterVariables() {
		gauche.recolterVariables();
		droit.recolterVariables();
	}

	@Override
	public Formule skolemiser() {
		return new Et(gauche.skolemiser(), droit.skolemiser());
	}

	@Override
	public Formule herbrandiser() {
		return new Et(gauche.herbrandiser(), droit.herbrandiser());
	}

	@Override
	public String toString() {
		return gauche.toString() + " /\\ " + droit.toString();
	}

	@Override
	public String toStringWithParenthesis() {
		return "(" + gauche.toStringWithParenthesis() + " /\\ " + droit.toStringWithParenthesis() + ")";
	}
}