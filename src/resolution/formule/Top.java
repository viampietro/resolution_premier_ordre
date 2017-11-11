package resolution.formule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import resolution.cnf.Atome;
import resolution.cnf.AtomeTop;
import resolution.cnf.Clause;

/*
 * CLASSE TOP
 */
public class Top extends Formule {

	@Override
	public ArrayList<Clause> clausifier() {

		Atome a = new AtomeTop();

		Map<String, Atome> atomes = new HashMap<String, Atome>();
		atomes.put(a.toString(), a);

		Clause c = new Clause(atomes);

		ArrayList<Clause> formeClausale = new ArrayList<Clause>();
		formeClausale.add(c);

		return formeClausale;
	}

	@Override
	public Formule nier() {

		return new Bottom();
	}

	@Override
	public String toString() {

		return "Top";
	}

	@Override
	public String toStringWithParenthesis() {
		return "Top";
	}

	@Override
	public Formule simplifier() {

		return this;
	}

	@Override
	public Formule skolemiser() {
		return this;
	}

	@Override
	public Formule herbrandiser() {
		return this;
	}
	
	@Override
	public void substituerVariable(Variable var, Terme t) {
		return;
	}

	@Override
	public void recolterVariables() {
		return;
	}
	
	

}