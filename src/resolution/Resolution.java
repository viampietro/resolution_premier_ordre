package resolution;

import java.util.ArrayList;
import java.util.HashMap;

import resolution.cnf.Atome;
import resolution.cnf.Clause;
import resolution.formule.Fonction;
import resolution.formule.Terme;
import resolution.formule.Variable;

public class Resolution {

	/**
	 * 
	 * @param clauses,
	 *            l'ensemble des clauses sur lequel le calcul de resolution va
	 *            se baser.
	 * @return Retourne vrai si l'ensemble est insatisfiable, c'est a dire, la
	 *         clause vide a ete obtenue, faux sinon.
	 * 
	 */
	public static boolean resoudre(ArrayList<Clause> clauses) {

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
				unionSatClauseCourante = new ArrayList<>(sat);
				unionSatClauseCourante.add(clauseCourante);

				for (Clause c : unionSatClauseCourante) {
					clauseResolvante = c.resoudreAvec(clauseCourante);
					if (clauseResolvante != null)
						clauses.add(clauseResolvante);
				}

			}

			// La clause courante est ajoutée à l'ensemble sat
			sat.add(clauseCourante);
		}

		return false;
	}

	public static ArrayList<Equation> unifier(ArrayList<Equation> equations)
			throws ConflictRuleException, CheckRuleException {

		boolean aEteModifiee = true;
		Equation eqCourante = null;

		while (aEteModifiee) {

			/*
			 * aEteModifie est mis à faux afin de quitter la boucle si plus
			 * aucune modification n'est applicable à l'ensemble des équations.
			 * Auquel cas, une substitution unificatrice a été trouvée.
			 */
			aEteModifiee = false;

			/*
			 * Boucle à l'envers, pour ne pas être dérangé par les délétions sur
			 * l'ensemble des équations.
			 */
			for (int i = equations.size() - 1; i >= 0; i--) {
				eqCourante = equations.get(i);

				///////// DELETE \\\\\\\\\\
				if (eqCourante.gauche.toString().equals(eqCourante.droit.toString()))
					equations.remove(i);

				else if (eqCourante.gauche instanceof Fonction && eqCourante.droit instanceof Fonction) {

					Fonction f = (Fonction) eqCourante.gauche;
					Fonction g = (Fonction) eqCourante.droit;

					/////////// DECOMPOSE \\\\\\\\\\\
					if (f.getNom().equals(g.getNom())) {
						equations.addAll(decomposer(f, g));
						equations.remove(i);

						/*
						 * On spécifie qu'une modification a eu lieue pour
						 * continuer de boucler
						 */
						aEteModifiee = true;
					}

					///////////// CONFLICT \\\\\\\\\\\
					else
						throw new ConflictRuleException(f, g);

					///////////// SWAP \\\\\\\\\\\\\\\\
				} else if (eqCourante.gauche instanceof Fonction && eqCourante.droit instanceof Variable) {
					equations.add(new Equation(eqCourante.droit, eqCourante.gauche));
					equations.remove(i);

					/*
					 * On spécifie qu'une modification a eu lieue pour continuer
					 * de boucler
					 */
					aEteModifiee = true;

					//////////// ELIMINATE \\\\\\\\\\\\\\
				} else if (eqCourante.gauche instanceof Variable) {

					/*
					 * On procède à l'élimination si le variable à gauche de
					 * l'équation n'est pas incluse dans la partie droite
					 */
					if (!eqCourante.gauche.estInclusDans(eqCourante.droit)) {
						/*
						 * Enleve l'equation courante de la liste pendant la
						 * substitution
						 */
						equations.remove(i);

						/*
						 * aEteModifiee est à vrai si la methode eliminer a
						 * engendrée des changements dans la liste d'équations
						 */
						aEteModifiee = aEteModifiee
								|| eliminer(equations, (Variable) eqCourante.gauche, eqCourante.droit);

						/*
						 * Ajout de l'équation précédemment enlevée de la liste
						 * pour procéder à la substitution
						 */
						equations.add(eqCourante);
					} else
						throw new CheckRuleException(eqCourante);
				}
			}
		}

		return equations;

	}

	/**
	 * 
	 * @param f,
	 *            un fonction possédant le même nom et le même nombre
	 *            d'arguments que la fonction g.
	 * @param g,
	 *            un fonction possédant le même nom et le même nombre
	 *            d'arguments que la fonction f.
	 * 
	 * @return Retourne une liste d'équations correspondant à la mise en
	 *         équation de chaque couple d'arguments de f et g. Par exemple,
	 *         pour f(x,a) et g(b, y), on créé l'ensemble d'équations {x = b, a
	 *         = y} puis cet ensemble est unifié avant d'être retourné.
	 */
	private static ArrayList<Equation> decomposer(Fonction f, Fonction g)
			throws ConflictRuleException, CheckRuleException {
		/*
		 * On part du principe que f et g possÃ¨dent le mÃªme nombre d'arguments
		 * de la mÃªme maniÃ¨re qu'elles possÃ¨dent le mÃªme nom.
		 */

		ArrayList<Equation> equations = new ArrayList<>();
		Equation equation = null;

		for (int i = 0; i < f.getArgs().size(); i++) {

			/*
			 * On créé une équatiopn pour chaque couple d'arguments de f et g.
			 * Par exemple, pour f(x,a) et g(b, y), on créé l'ensemble
			 * d'équations {x = b, a = y}.
			 * 
			 */
			equation = new Equation(f.getArgs().get(i), g.getArgs().get(i));
			equations.add(equation);
		}

		return unifier(equations);
	}

	/**
	 * 
	 * @param equations,
	 *            liste d'équations dans laquelle va être appliquée la
	 *            substitution
	 * @param substituee,
	 *            la variable à substituer si rencontrée dans les équations de
	 *            la liste
	 * @param substitut,
	 *            terme par lequel substituer la variable
	 * 
	 * @return Applique la substitution sur l'ensemble des éléments de la liste
	 *         d'équations. Retourne vrai si au moins une des équations de la
	 *         liste a été modifiée par substitution et faux sinon.
	 */
	private static boolean eliminer(ArrayList<Equation> equations, Variable substituee, Terme substitut) {

		boolean aEteModifiee = false;

		Terme gaucheSubstitue = null;
		Terme droitSubstitue = null;

		// Application de la substitution sur toutes les équations
		// de la liste
		for (Equation e : equations) {

			/*
			 * Application de la substitution sur les deux branches de
			 * l'équation et récupération des résultats
			 */
			gaucheSubstitue = e.gauche.substituerVariable(substituee, substitut);
			droitSubstitue = e.droit.substituerVariable(substituee, substitut);

			/*
			 * Si la branche gauche a été modifiée par la substitution, on
			 * conserve la branche gauche substituée et on indique qu'une
			 * modification a eu lieue dans la liste d'équations
			 */
			if (!e.gauche.equals(gaucheSubstitue)) {
				e.gauche = gaucheSubstitue;
				aEteModifiee = true;
			}

			/*
			 * La même chose qu'au-dessus pour la branche droite
			 */
			if (!e.droit.equals(droitSubstitue)) {
				e.droit = droitSubstitue;
				aEteModifiee = true;
			}

		}

		return aEteModifiee;
	}

}
