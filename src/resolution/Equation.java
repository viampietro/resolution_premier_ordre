package resolution;

import resolution.formule.Terme;

/**
 * 
 * Couple de termes représentant l'affectation d'un terme à un autre. La
 * fonction unifier prend en argument une liste d'affectations, c'est à dire une
 * liste d'équations de la forme : {f(x) = f(a), g(y) = g(b)...}
 */
public class Equation {

	public Terme gauche;
	public Terme droit;

	public Equation(Terme gauche, Terme droit) {
		this.gauche = gauche;
		this.droit = droit;
	}

	@Override
	public String toString() {

		return gauche.toString() + " = " + droit.toString();
	}

}