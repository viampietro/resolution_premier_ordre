package resolution.formule;

import java.util.ArrayList;

import resolution.cnf.Clause;

public abstract class Formule {

	/**
	 * Pour l'instant, chaque variable, libre ou liée, composant une formule est
	 * nommée de manière unique. La redondance du nom des variables sera prise
	 * en compte plus tard.
	 */

	/**
	 * Liste des variables libres de la formule. Remplie par la méthode
	 * recolterVariablesLibres().
	 * 
	 */
	public static ArrayList<String> variablesLibres = new ArrayList<>();

	/**
	 * Liste des variables liées de la formule. Remplie par la méthode
	 * recolterVariables().
	 * 
	 */
	public static ArrayList<String> variablesLiees = new ArrayList<>();

	/**
	 * Remplit les attributs statiques variablesLibres et variablesLiees en
	 * parcourant la formule courante et en ajoutant les variables rencontrées
	 * soit dans la liste des variables libres, soit dans celle des variables
	 * liées.
	 */
	public abstract void recolterVariables();

	/**
	 * 
	 * Substitue toutes les occurences de la Variable var présentes dans
	 * la formule courante par le Terme t. 
	 * 
	 * @param var,
	 *            la variable à substituer par le terme t dans la formule
	 *            courante.
	 * @param t,
	 *            le terme par lequel substituer la variable var dans la formule
	 *            courante
	 */
	public abstract void substituerVariable(Variable var, Terme t);

	/**
	 * 
	 * @return retourne une formule correspondant à la formule courante à
	 *         laquelle a été appliqué un non.
	 */
	public abstract Formule nier();

	/**
	 * @pre la formule courante n'est pas un quantifieur.
	 * 
	 * @return retourne une formule correspondant à la formule courante à
	 *         laquelle a été appliquée les règles de transformations facilitant
	 *         la passage à la forme CNF. Lance une
	 *         CantSimplifyQuantifierException si la formule courante est un
	 *         quantifieur
	 */
	public abstract Formule simplifier();

	/**
	 * 
	 * @pre le formule courante a été simplifier avec la méthode simplifier()
	 *      avant d'être clausifier.
	 * 
	 * @return retourne une liste d'Objets de type Clause (contenant eux-mêmes
	 *         une liste d'atomes) qui correspond à la mise en forme CNF de la
	 *         formule courante. Lance une CantCNFQuantifierException si la
	 *         formule courante est un quantifieur.
	 */
	public abstract ArrayList<Clause> clausifier();

	/**
	 * 
	 * @return retourne une formule correspondant à la formule courante sous sa
	 *         forme universelle.
	 */
	public abstract Formule skolemiser();

	/**
	 * 
	 * @return retourne une formule correspondant à la formule courante sous sa
	 *         forme existentielle.
	 */
	public abstract Formule herbrandiser();

	/**
	 * 
	 * @return Retourne une chaîne de caractères représentant la formule
	 *         courante sous forme bien parenthésée.
	 */
	public abstract String toStringWithParenthesis();

}
