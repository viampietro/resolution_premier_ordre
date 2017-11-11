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
			 * Si la clause courante est une clause vide, vrai est retourn�
			 */
			if (clauseCourante.isEmpty())
				return true;

			/*
			 * Si la clause courante n'est ni une tautologie, ni incluse dans
			 * l'ensemble sat, alors le calcul des r�solvants est lanc�
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

			// La clause courante est ajout�e � l'ensemble sat
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
			 * aEteModifie est mis � faux afin de quitter la boucle si plus
			 * aucune modification n'est applicable � l'ensemble des �quations.
			 * Auquel cas, une substitution unificatrice a �t� trouv�e.
			 */
			aEteModifiee = false;

			/*
			 * Boucle � l'envers, pour ne pas �tre d�rang� par les d�l�tions sur
			 * l'ensemble des �quations.
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
						 * On sp�cifie qu'une modification a eu lieue pour
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
					 * On sp�cifie qu'une modification a eu lieue pour continuer
					 * de boucler
					 */
					aEteModifiee = true;

					//////////// ELIMINATE \\\\\\\\\\\\\\
				} else if (eqCourante.gauche instanceof Variable) {

					/*
					 * On proc�de � l'�limination si le variable � gauche de
					 * l'�quation n'est pas incluse dans la partie droite
					 */
					if (!eqCourante.gauche.estInclusDans(eqCourante.droit)) {
						/*
						 * Enleve l'equation courante de la liste pendant la
						 * substitution
						 */
						equations.remove(i);

						/*
						 * aEteModifiee est � vrai si la methode eliminer a
						 * engendr�e des changements dans la liste d'�quations
						 */
						aEteModifiee = aEteModifiee
								|| eliminer(equations, (Variable) eqCourante.gauche, eqCourante.droit);

						/*
						 * Ajout de l'�quation pr�c�demment enlev�e de la liste
						 * pour proc�der � la substitution
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
	 *            un fonction poss�dant le m�me nom et le m�me nombre
	 *            d'arguments que la fonction g.
	 * @param g,
	 *            un fonction poss�dant le m�me nom et le m�me nombre
	 *            d'arguments que la fonction f.
	 * 
	 * @return Retourne une liste d'�quations correspondant � la mise en
	 *         �quation de chaque couple d'arguments de f et g. Par exemple,
	 *         pour f(x,a) et g(b, y), on cr�� l'ensemble d'�quations {x = b, a
	 *         = y} puis cet ensemble est unifi� avant d'�tre retourn�.
	 */
	private static ArrayList<Equation> decomposer(Fonction f, Fonction g)
			throws ConflictRuleException, CheckRuleException {
		/*
		 * On part du principe que f et g possèdent le même nombre d'arguments
		 * de la même manière qu'elles possèdent le même nom.
		 */

		ArrayList<Equation> equations = new ArrayList<>();
		Equation equation = null;

		for (int i = 0; i < f.getArgs().size(); i++) {

			/*
			 * On cr�� une �quatiopn pour chaque couple d'arguments de f et g.
			 * Par exemple, pour f(x,a) et g(b, y), on cr�� l'ensemble
			 * d'�quations {x = b, a = y}.
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
	 *            liste d'�quations dans laquelle va �tre appliqu�e la
	 *            substitution
	 * @param substituee,
	 *            la variable � substituer si rencontr�e dans les �quations de
	 *            la liste
	 * @param substitut,
	 *            terme par lequel substituer la variable
	 * 
	 * @return Applique la substitution sur l'ensemble des �l�ments de la liste
	 *         d'�quations. Retourne vrai si au moins une des �quations de la
	 *         liste a �t� modifi�e par substitution et faux sinon.
	 */
	private static boolean eliminer(ArrayList<Equation> equations, Variable substituee, Terme substitut) {

		boolean aEteModifiee = false;

		Terme gaucheSubstitue = null;
		Terme droitSubstitue = null;

		// Application de la substitution sur toutes les �quations
		// de la liste
		for (Equation e : equations) {

			/*
			 * Application de la substitution sur les deux branches de
			 * l'�quation et r�cup�ration des r�sultats
			 */
			gaucheSubstitue = e.gauche.substituerVariable(substituee, substitut);
			droitSubstitue = e.droit.substituerVariable(substituee, substitut);

			/*
			 * Si la branche gauche a �t� modifi�e par la substitution, on
			 * conserve la branche gauche substitu�e et on indique qu'une
			 * modification a eu lieue dans la liste d'�quations
			 */
			if (!e.gauche.equals(gaucheSubstitue)) {
				e.gauche = gaucheSubstitue;
				aEteModifiee = true;
			}

			/*
			 * La m�me chose qu'au-dessus pour la branche droite
			 */
			if (!e.droit.equals(droitSubstitue)) {
				e.droit = droitSubstitue;
				aEteModifiee = true;
			}

		}

		return aEteModifiee;
	}

}
