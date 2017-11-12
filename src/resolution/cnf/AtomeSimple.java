package resolution.cnf;

import resolution.formule.Predicat;

/*
 * CLASSE ATOME SIMPLE
 */
public class AtomeSimple extends Atome {

	boolean negation;
	Predicat predicat;

	/*
	 * CONSTRUCTORS
	 */
	public AtomeSimple(boolean negation, Predicat p) {
		super();
		this.negation = negation;
		this.predicat = p;
	}

	public AtomeSimple(AtomeSimple a) {
		this.negation = a.negation;
		this.predicat = new Predicat(a.getPredicat());
	}

	/*
	 * GETTERS AND SETTERS
	 */
	public boolean isNegation() {
		return negation;
	}

	public void setNegation(boolean negation) {
		this.negation = negation;
	}

	public boolean isAffirmation() {
		return !isNegation();
	}

	public Predicat getPredicat() {
		return predicat;
	}

	public void setPredicat(Predicat predicat) {
		this.predicat = predicat;
	}

	/*
	 * METHODS
	 */
	public String toString() {
		if (predicat != null)
			return (negation ? "!" : "") + predicat.toString();
		else
			return "null";
	}

	/**
	 * 
	 * @param atome,
	 *            l'atome compar� avec l'atome courant
	 * @return Vrai si les pr�dicats de l'atome courant et de l'atome en
	 *         param�tre ont le m�me nom et sont de polarit� contraire (l'un est
	 *         n�gatif et l'autre positif, ou inversement). Faux sinon.
	 */
	public boolean estLeContraireDe(AtomeSimple atome) {

		if (atome != null && atome.getPredicat() != null)
			return getPredicat().getNom().equals(atome.getPredicat().getNom())
					&& ((isNegation() && atome.isAffirmation()) || (isAffirmation() && atome.isNegation()));
		else
			return false;
	}

	/**
	 * 
	 * @param atome,
	 *            l'atome qui sera comparer avec l'atome courant pour appliquer
	 *            le renommage des termes.
	 * 
	 *            Modifie l'atome courant et l'atome en param�tre, tel qu'� la
	 *            fin de l'op�ration le pr�dicat de l'atome courant et le
	 *            pr�dicat de l'atome en param�tre ne poss�dent plus aucun terme
	 *            en commun.
	 */
	public void renommerAtomes(AtomeSimple atome) {
		throw new RuntimeException("NYI method AtomeSimple.renommerAtomes(AtomeSimple)");
	}

}