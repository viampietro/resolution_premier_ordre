package resolution.formule;

import java.util.HashMap;

public abstract class Terme {

	/**
	 * 
	 * @return Retourne la liste des variables qui sont présentes dans le
	 *         terme. Si le terme est une variable une liste contenant la seule
	 *         variable est retournée. Sinon si le terme est une fonction,
	 *         l'union de toutes les variables argument de la fonction avec les
	 *         variables des fonctions imbriquées est retournée.
	 */
	public abstract HashMap<String, Variable> getVariables();

	/**
	 * 
	 * @param var,
	 *            la variable à substituer par le terme t si présente dans le
	 *            terme courant.
	 * @param t,
	 *            le terme de substitution pour la variable var.
	 * 
	 * @return Retourne le terme courant dans lequel chaque occurrence de la
	 *         variable var a été remplacée par le terme t.
	 */
	public abstract Terme substituerVariable(Variable var, Terme t);

	/**
	 * 
	 * @param t,
	 *            le terme dans lequel va être recherché le terme courant pour
	 *            vérifier.
	 * 
	 * @return Vrai si le terme courant est inclus ou égal au terme t, faux
	 *         sinon.
	 */
	public abstract boolean estInclusDans(Terme t);

	/**
	 * Retourne vrai si la représentation des deux termes par chaîne de
	 * caractères est égale.
	 */
	@Override
	public boolean equals(Object obj) {
		Terme t = (Terme) obj;
		return toString().equals(t.toString());
	}

}
