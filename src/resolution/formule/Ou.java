package resolution.formule;

import java.util.ArrayList;

import resolution.cnf.Clause;

/*
 * CLASSE OU
 */

public class Ou extends Formule {

	private Formule gauche;
	private Formule droit;

	public Ou(Formule gauche, Formule droit) {
		super();
		this.gauche = gauche;
		this.droit = droit;
	}

	@Override
	public ArrayList<Clause> clausifier() {

		ArrayList<Clause> fcFinale = new ArrayList<>();

		ArrayList<Clause> fcGauche = gauche.clausifier();
		ArrayList<Clause> fcDroit = droit.clausifier();

		Clause clauseTampon;

		for (Clause cg : fcGauche) {

			// Passage par copie profonde pour ne pas repercuter les
			// changments de cg sur clauseTampon
			clauseTampon = new Clause(cg);

			for (Clause cd : fcDroit) {

				fcFinale.add(cg.fusionner(cd));

				// Reinitialise cg car la methode fusionner modifie l'etat
				// de la variable
				cg = clauseTampon;
			}
		}

		return fcFinale;
	}

	@Override
	public Formule nier() {

		return new Et(gauche.nier(), droit.nier());
	}

	@Override
	public Formule simplifier() {

		Formule gaucheSimplifie = gauche.simplifier();
		Formule droitSimplifie = droit.simplifier();

		// Si le ou contient un Top, on renvoie Top
		if (gaucheSimplifie instanceof Top || droitSimplifie instanceof Top)
			return new Top();

		// Sinon si une des deux branches est un Bottom,
		// on renvoie l'autre branche
		else if (gaucheSimplifie instanceof Bottom)
			return droitSimplifie;
		else if (droitSimplifie instanceof Bottom)
			return gaucheSimplifie;

		// Sinon on renvoie un Ou avec les branches simplifiees
		else
			return new Ou(gaucheSimplifie, droitSimplifie);

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
		return new Ou(gauche.skolemiser(), droit.skolemiser());
	}

	@Override
	public Formule herbrandiser() {
		return new Ou(gauche.herbrandiser(), droit.herbrandiser());
	}

	@Override
	public String toString() {
		return gauche.toString() + " \\/ " + droit.toString();
	}

	@Override
	public String toStringWithParenthesis() {
		return "(" + gauche.toStringWithParenthesis() + " \\/ " + droit.toStringWithParenthesis() + ")";
	}

}