package resolution.cnf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Clause {

	/**
	 * atomes, une Map d'atomes avec le nom des atomes comme cles et les objets
	 * Atome comme valeur.
	 */
	Map<String, Atome> atomes;

	/**
	 * CONSTRUCTORS
	 */
	public Clause() {
		atomes = new HashMap<String, Atome>();
	}

	public Clause(Map<String, Atome> atomes) {
		super();
		this.atomes = atomes;
	}

	public Clause(Clause c) {
		atomes = new HashMap<String, Atome>(c.atomes);
	}

	/**
	 * 
	 * @return Retourne vrai si la liste d'atomes de l'objet courant est vide,
	 *         faux sinon
	 */
	public boolean isEmpty() {
		return atomes.size() == 0;
	}

	/**
	 * 
	 * @param c,
	 *            la Clause qui va etre fusionnee avec la Clause courante
	 * @return Retourne l'objet courant dont la liste d'atomes est le resultat
	 *         de la fusion entre sa precendente liste et la liste d'atomes de
	 *         la Clause c
	 */
	public Clause fusionner(Clause c) {

		for (String key : c.getAtomes().keySet())
			if (!atomes.containsKey(key))
				atomes.put(key, c.getAtomes().get(key));

		return this;
	}

	public Map<String, Atome> getAtomes() {
		return atomes;
	}

	public void setAtomes(Map<String, Atome> atomes) {
		this.atomes = atomes;
	}

	public String toString() {
		return atomes.values().toString();
	}

	/**
	 * 
	 * @return Retourne vrai si la clause courante est une tautologie, c'est a
	 *         dire, si elle contient une atome Top ou si elle contient un atome
	 *         est son contraire. Retourne faux sinon.
	 */
	public boolean estUneTautologie() {

		/*
		 * Si la clause courante contient un atome Top, alors c'est une
		 * tautologie
		 */
		if (atomes.containsKey("Top"))
			return true;
		else {
			/*
			 * Sinon, pour chaque atome de la clause courante, son contraire est
			 * recherche.
			 */
			for (Atome a : atomes.values())

				if (a instanceof AtomeSimple) {

					AtomeSimple atome = (AtomeSimple) a;

					// Si l'atome est une negation, son equivalent sans negation
					// est recherche
					if (atome.isNegation() && atomes.containsKey(atome.getPredicat().toString()))
						return true;
					// Si l'atome n'est pas une negation, son equivalent avec
					// negation est recherche
					else if (!atome.isNegation()
							&& atomes.containsKey("!" + ((AtomeSimple) a).getPredicat().toString()))
						return true;
				}
		}

		return false;
	}

	/**
	 * 
	 * @param c,
	 *            la clause comparee avec la clause courante.
	 * @return Retourne vrai si tous les atomes de c sont contenus dans les
	 *         atomes de la clause courante et inversement. Retourne faux sinon.
	 */
	public boolean estEgaleA(Clause c) {

		boolean estEgale = false;

		if (c.atomes.size() == atomes.size()) {

			for (String atome : c.atomes.keySet()) {
				if (!atomes.containsKey(atome))
					return estEgale;
			}

			estEgale = true;

		}

		return estEgale;
	}

	/**
	 * 
	 * @param clauses,
	 *            l'ensemble de Clause dans lequel la clause courante va etre
	 *            recherchee.
	 * @return Retourne vrai si il existe une clause c1 appartenant à clauses
	 *         qui est égale à la clause courante. Retourne faux, si une telle
	 *         clause c1 n'existe pas.
	 */
	public boolean estIncluseDans(ArrayList<Clause> clauses) {

		for (Clause c : clauses) {

			/*
			 * Si une des clauses est egale a la clause courante, on retourne
			 * vrai
			 */
			if (estEgaleA(c))
				return true;

		}

		return false;
	}

	/**
	 * 
	 * @post la clause courante et la clause en paramètre n'ont pas été
	 *       modifiées durant l'opération.
	 * @param clauseCandid,
	 *            la clause avec laquelle la clause courante va être résolue
	 * @return La clause resolvante de la règle de résolution, si cette règle a
	 *         pu s'appliquer entre la clause en parametre et la clause
	 *         courante. null, si la règle de résolution n'était pas applicable.
	 * 
	 */
	public Clause resoudreAvec(Clause clause) {

		/*
		 * Construction de deux nouvelles clauses par recopie pour éviter la
		 * modification de la clause courante et de la clause en paramètre
		 */
		Clause clauseCourante = new Clause(this);
		Clause clauseCandidate = new Clause(clause);

		ArrayList<AtomeSimple> paireAtomesContraires = clauseCourante.selectionnerAtomesContraires(clauseCandidate);
		if (paireAtomesContraires != null && paireAtomesContraires.size() == 2) {

			AtomeSimple premierAtome = paireAtomesContraires.get(0);
			AtomeSimple deuxiemeAtome = paireAtomesContraires.get(1);
			premierAtome.renommerAtomes(deuxiemeAtome);

			StackTraceElement[] ste = Thread.currentThread().getStackTrace();
			throw new RuntimeException(
					"NYI " + getClass().getSimpleName() + "." + ste[ste.length - 2].getMethodName() + "()");
		}

		/*
		 * Si les deux clauses à résoudre ne possède pas d'atomes contraires,
		 * pas la peine de chercher à unifier. La fonction retourne null.
		 * 
		 */
		else
			return null;

	}

	/**
	 * 
	 * @param clause,
	 *            la clause qui sera utilisée pour chercher un atome contraire à
	 *            un des atomes de la clause courante.
	 * 
	 * @return Retourne la première paire d'atomes contraires trouvés tel que le
	 *         premier atome de la paire appartient à la clause courante et le
	 *         deuxième atome de la paire appartient à la clause en paramètre.
	 *         Retourne null si aucune paire d'atomes contraires n'existe entre
	 *         la clause courante et la clause en paramètre.
	 */
	public ArrayList<AtomeSimple> selectionnerAtomesContraires(Clause clause) {

		ArrayList<AtomeSimple> paireAtomesContraires = null;

		for (Atome atomeCCourante : getAtomes().values())
			for (Atome atomeCArgument : clause.getAtomes().values())

				/*
				 * Si les deux atomes atomeCCourante et atomeCArgument sont des
				 * atomes simples et si l'un est le contraire de l'autre, alors
				 * ils vont constituer la paire d'atomes contraires qui sera
				 * retournée.
				 */
				if (atomeCCourante instanceof AtomeSimple && atomeCArgument instanceof AtomeSimple
						&& ((AtomeSimple) atomeCCourante).estLeContraireDe((AtomeSimple) atomeCArgument)) {

					paireAtomesContraires = new ArrayList<>();

					/*
					 * Utilisation du constructeur par recopie pour déréférencer
					 * atomeCCourante et atomeCArgument qui ne doivent pas être
					 * modifiés
					 */
					paireAtomesContraires.add((AtomeSimple) atomeCCourante);
					paireAtomesContraires.add((AtomeSimple) atomeCArgument);

					return paireAtomesContraires;
				}

		return paireAtomesContraires;
	}
}
