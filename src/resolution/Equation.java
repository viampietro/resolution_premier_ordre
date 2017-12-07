package resolution;

import java.util.ArrayList;

import resolution.formule.Fonction;
import resolution.formule.Terme;
import resolution.formule.Variable;

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
						 * aEteModifiee est à vrai si la methode eliminer() a
						 * engendrée des changements dans la liste d'équations
						 */
						aEteModifiee = aEteModifiee
								|| eliminer(equations, (Variable) eqCourante.gauche, eqCourante.droit);

						/*
						 * Ajout de l'équation précédemment enlevée de la liste
						 * pour permettre la substitution
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
	 *         pour f(x,a) et f(b, y), on crée l'ensemble d'équations {x = b, a
	 *         = y} puis cet ensemble est unifié avant d'être retourné.
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
			 * On crée une équatiopn pour chaque couple d'arguments de f et g.
			 * Par exemple, pour f(x,a) et f(b, y), on crée l'ensemble
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

		// Application de la substitution sur toutes les �quations
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

	@Override
	public String toString() {

		return gauche.toString() + " = " + droit.toString();
	}

}