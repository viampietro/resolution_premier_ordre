package resolution.formule;

import java.util.ArrayList;

import resolution.cnf.Clause;

public abstract class Formule {

	/**
	 * Pour l'instant, chaque variable, libre ou li�e, composant une formule est
	 * nomm�e de mani�re unique. La redondance du nom des variables sera prise
	 * en compte plus tard.
	 */

	/**
	 * Liste des variables libres de la formule. Remplie par la m�thode
	 * recolterVariablesLibres().
	 * 
	 */
	public static ArrayList<String> variablesLibres = new ArrayList<>();

	/**
	 * Liste des variables li�es de la formule. Remplie par la m�thode
	 * recolterVariables().
	 * 
	 */
	public static ArrayList<String> variablesLiees = new ArrayList<>();

	/**
	 * Remplit les attributs statiques variablesLibres et variablesLiees en
	 * parcourant la formule courante et en ajoutant les variables rencontr�es
	 * soit dans la liste des variables libres, soit dans celle des variables
	 * li�es.
	 */
	public abstract void recolterVariables();

	/**
	 * 
	 * Substitue toutes les occurences de la Variable var pr�sentes dans
	 * la formule courante par le Terme t. 
	 * 
	 * @param var,
	 *            la variable � substituer par le terme t dans la formule
	 *            courante.
	 * @param t,
	 *            le terme par lequel substituer la variable var dans la formule
	 *            courante
	 */
	public abstract void substituerVariable(Variable var, Terme t);

	/**
	 * 
	 * @return retourne une formule correspondant � la formule courante �
	 *         laquelle a �t� appliqu� un non.
	 */
	public abstract Formule nier();

	/**
	 * @pre la formule courante n'est pas un quantifieur.
	 * 
	 * @return retourne une formule correspondant � la formule courante �
	 *         laquelle a �t� appliqu�e les r�gles de transformations facilitant
	 *         la passage � la forme CNF. Lance une
	 *         CantSimplifyQuantifierException si la formule courante est un
	 *         quantifieur
	 */
	public abstract Formule simplifier();

	/**
	 * 
	 * @pre le formule courante a �t� simplifier avec la m�thode simplifier()
	 *      avant d'�tre clausifier.
	 * 
	 * @return retourne une liste d'Objets de type Clause (contenant eux-m�mes
	 *         une liste d'atomes) qui correspond � la mise en forme CNF de la
	 *         formule courante. Lance une CantCNFQuantifierException si la
	 *         formule courante est un quantifieur.
	 */
	public abstract ArrayList<Clause> clausifier();

	/**
	 * 
	 * @return retourne une formule correspondant � la formule courante sous sa
	 *         forme universelle.
	 */
	public abstract Formule skolemiser();

	/**
	 * 
	 * @return retourne une formule correspondant � la formule courante sous sa
	 *         forme existentielle.
	 */
	public abstract Formule herbrandiser();

	/**
	 * 
	 * @return Retourne une cha�ne de caract�res repr�sentant la formule
	 *         courante sous forme bien parenth�s�e.
	 */
	public abstract String toStringWithParenthesis();

}
