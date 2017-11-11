package resolution.cnf;

import resolution.formule.Predicat;

/*
 * CLASSE ATOME SIMPLE
 */
public class AtomeSimple extends Atome {

	boolean negation;

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

	Predicat predicat;

	public AtomeSimple(boolean negation, Predicat p) {
		super();
		this.negation = negation;
		this.predicat = p;
	}

	public String toString() {
		return (negation ? "!" : "") + predicat.toString();
	}

	/**
	 * 
	 * @param atome,
	 *            l'atome comparé avec l'atome courant
	 * @return Vrai si les prédicats de l'atome courant et de l'atome en
	 *         paramètre ont le même nom et sont de polarité contraire (l'un est
	 *         négatif et l'autre positif, ou inversement).
	 *         Faux sinon.
	 */
	public boolean estLeContraireDe(AtomeSimple atome) {

		return getPredicat().getNom().equals(atome.getPredicat().getNom())
				&& ((isNegation() && atome.isAffirmation()) || (isAffirmation() && atome.isNegation()));
	}
}