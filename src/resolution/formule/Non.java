package resolution.formule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import resolution.cnf.Atome;
import resolution.cnf.AtomeSimple;
import resolution.cnf.Clause;

/*
 * CLASSE NON
 */
public class Non extends Formule {

	private Formule cible;

	public Non(Formule cible) {
		super();
		this.cible = cible;
	}

	@Override
	public ArrayList<Clause> clausifier() {

		ArrayList<Clause> formeClausale = new ArrayList<Clause>();

		// Renvoie une liste avec une seule clause qui contient un atome negatif
		// si la proposition cible est un predicat
		if (cible instanceof Predicat) {

			// Construction d'un atome negatif
			Atome a = new AtomeSimple(true, (Predicat)cible);

			Map<String, Atome> atomes = new HashMap<String, Atome>();
			atomes.put(a.toString(), a);

			Clause c = new Clause(atomes);

			formeClausale.add(c);

		} else {

			// Applique le non sur la proposition cible avant de clausifier
			formeClausale = cible.nier().clausifier();
		}

		return formeClausale;
	}

	@Override
	public Formule nier() {
		return cible;
	}

	public Formule simplifier() {
		
		// On applique le non à la formule cible
		// pour la simplifier
		return cible.simplifier().nier();

	}
	
	@Override
	public void substituerVariable(Variable var, Terme t) {
		cible.substituerVariable(var, t);
	}
	

	@Override
	public void recolterVariables() {
		cible.recolterVariables();
	}

	@Override
	public Formule skolemiser() {
		return new Non(cible.herbrandiser());
	}

	@Override
	public Formule herbrandiser() {
		return new Non(cible.skolemiser());
	}
	
	@Override
	public String toString() {
		return "!" + cible.toString();
	}

	@Override
	public String toStringWithParenthesis() {
		return "(!" + cible.toStringWithParenthesis() + ")";
	}

}