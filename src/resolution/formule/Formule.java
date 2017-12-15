package resolution.formule;

import java.util.ArrayList;

import resolution.cnf.Clause;

public abstract class Formule {

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
	 * Remise à zéro des listes de variables libres et liées
	 */
	public void razVariables() {
		variablesLibres = new ArrayList<>();
		variablesLiees = new ArrayList<>();
	}

	/**
	 * 
	 * Substitue toutes les occurences de la Variable var présentes dans la
	 * formule courante par le Terme t.
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
	 * @return retourne la formule courante dans son forme niée.
	 */
	public abstract Formule nier();

	/**
	 * @pre la formule courante n'est pas un quantificateur.
	 * 
	 * @return retourne une formule correspondant à la formule courante à
	 *         laquelle a été appliquée les règles de transformations facilitant
	 *         la passage à la forme CNF. Les règles de transformation ont la
	 *         plupart du temps pour effet de supprimer les Top et les Bottom de
	 *         la formule. Lance une CantSimplifyQuantifierException si la
	 *         formule courante est un quantificateur.
	 */
	public abstract Formule simplifier();

	/**
	 * 
	 * @pre le formule courante a été simplifiée avec la méthode simplifier()
	 *      avant d'être clausifier.
	 * 
	 * @return retourne une liste d'Objets de type Clause (contenant eux-mêmes
	 *         une liste d'atomes) qui correspond à la mise en forme CNF de la
	 *         formule courante. Lance une CantCNFQuantifierException si la
	 *         formule courante est un quantificateur.
	 */
	public abstract ArrayList<Clause> clausifier();

	/**
	 * 
	 * @return retourne la formule courante sous sa forme universelle, sans
	 *         quantificateur.
	 */
	public abstract Formule skolemiser();

	/**
	 * 
	 * @return retourne la formule courante sous sa forme existentielle, , sans
	 *         quantificateur.
	 */
	public abstract Formule herbrandiser();

	/**
	 * 
	 * @return Retourne vrai si la formule courante est valide; faux indique la
	 *         formule est satisfiable.
	 * 
	 */
	public boolean resoudre() {

		/**
		 * On récolte les variables libres avant de skolémiser,
		 * la skolémisation nécessitant la connaissance des variables libres.
		 */
		recolterVariables();
		
		ArrayList<Clause> clauses = nier().skolemiser().simplifier().clausifier();

		ArrayList<Clause> sat = new ArrayList<Clause>();
		ArrayList<Clause> unionSatClauseCourante = new ArrayList<>();
		Clause clauseCourante = null;
		Clause clauseResolvante = null;

		while (!clauses.isEmpty()) {

			/*
			 * Choisit la premiere clause et la supprime de la liste courante
			 */
			clauseCourante = clauses.get(0);
			clauses.remove(0);

			/*
			 * Si la clause courante est une clause vide, vrai est retourné
			 */
			if (clauseCourante.isEmpty())
				return true;

			/*
			 * Si la clause courante n'est ni une tautologie, ni incluse dans
			 * l'ensemble sat, alors le calcul des résolvants est lancé
			 */
			else if (!clauseCourante.estUneTautologie() && !clauseCourante.estIncluseDans(sat)) {

				/**
				 * Nouvel ensemble de clauses résultant de l'union de l'ensemble
				 * sat et de la variable clauseCourante.
				 */
				unionSatClauseCourante = new ArrayList<>(sat);
				unionSatClauseCourante.add(clauseCourante);

				for (Clause c : unionSatClauseCourante) {
					clauseResolvante = c.resoudreAvec(clauseCourante);

					if (clauseResolvante != null) {
						/*
						 * Si la clause vide a été retournée,
						 * c'est gagné, on retourne vrai.
						 */
						if (clauseResolvante.isEmpty())
							return true;
						
						/*
						 * Sinon, on ajoute la nouvelle clause à la fin
						 */
						else clauses.add(clauseResolvante);
					}
						

				}

			}

			// La clause courante est ajoutée à l'ensemble sat
			sat.add(clauseCourante);
		}

		return false;
	}

	/**
	 * 
	 * @return Retourne une chaîne de caractères représentant la formule
	 *         courante sous forme bien parenthésée.
	 */
	public abstract String toStringWithParenthesis();

}
