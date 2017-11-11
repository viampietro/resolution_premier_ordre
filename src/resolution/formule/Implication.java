package resolution.formule;

import java.util.ArrayList;

import resolution.cnf.Clause;

/**
 * CLASSE IMPLICATION
 *
 */
public class Implication extends Formule {

	private Formule gauche;
	private Formule droit;

	public Implication(Formule gauche, Formule droit) {
		super();
		this.gauche = gauche;
		this.droit = droit;
	}

	@Override
	public ArrayList<Clause> clausifier() {

		return new Ou(new Non(gauche), droit).clausifier();
	}

	@Override
	public Formule nier() {
		return new Et(gauche, droit.nier());
	}

	@Override
	public Formule simplifier() {
		
		return new Ou(new Non(gauche), droit).simplifier();
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
		return new Ou(new Non(gauche), droit).skolemiser();
	}

	@Override
	public Formule herbrandiser() {
		return new Ou(new Non(gauche), droit).herbrandiser();
	}

	@Override
	public String toString() {
		return gauche.toString() + " => " + droit.toString();
	}

	@Override
	public String toStringWithParenthesis() {
		return "(" + gauche.toStringWithParenthesis() + " => " + droit.toStringWithParenthesis() + ")";
	}
}