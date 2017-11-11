package resolution.formule;

import java.util.HashMap;

public abstract class Terme {

	/**
	 * 
	 * @return Retourne la liste des variables qui sont pr�sentes dans le
	 *         terme. Si le terme est une variable une liste contenant la seule
	 *         variable est retourn�e. Sinon si le terme est une fonction,
	 *         l'union de toutes les variables argument de la fonction avec les
	 *         variables des fonctions imbriqu�es est retourn�e.
	 */
	public abstract HashMap<String, Variable> getVariables();

	/**
	 * 
	 * @param var,
	 *            la variable � substituer par le terme t si pr�sente dans le
	 *            terme courant.
	 * @param t,
	 *            le terme de substitution pour la variable var.
	 * 
	 * @return Retourne le terme courant dans lequel chaque occurrence de la
	 *         variable var a �t� remplac�e par le terme t.
	 */
	public abstract Terme substituerVariable(Variable var, Terme t);

	/**
	 * 
	 * @param t,
	 *            le terme dans lequel va �tre recherch� le terme courant pour
	 *            v�rifier.
	 * 
	 * @return Vrai si le terme courant est inclus ou �gal au terme t, faux
	 *         sinon.
	 */
	public abstract boolean estInclusDans(Terme t);

	/**
	 * Retourne vrai si la repr�sentation des deux termes par cha�ne de
	 * caract�res est �gale.
	 */
	@Override
	public boolean equals(Object obj) {
		Terme t = (Terme) obj;
		return toString().equals(t.toString());
	}

}
