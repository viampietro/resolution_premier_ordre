package client;

import java.util.ArrayList;
import java.util.HashMap;

import resolution.CheckRuleException;
import resolution.ConflictRuleException;
import resolution.Equation;
import resolution.Resolution;
import resolution.cnf.AtomeSimple;
import resolution.cnf.Clause;
import resolution.formule.Et;
import resolution.formule.Fonction;
import resolution.formule.Formule;
import resolution.formule.IlExiste;
import resolution.formule.Implication;
import resolution.formule.Non;
import resolution.formule.Ou;
import resolution.formule.PourTout;
import resolution.formule.Predicat;
import resolution.formule.Top;
import resolution.formule.Variable;

public class Main {

	public static void main(String[] args) {

		Variable x = new Variable("x");
		Variable y = new Variable("y");
		Variable z = new Variable("z");

		Fonction a = new Fonction("a");
		Fonction b = new Fonction("b");

		PourTout forall = new PourTout(x, new Implication(new Predicat("P", x),
				new IlExiste(y, new Ou(new Predicat("P", y), new Predicat("Q", y)))));

		Ou ou = new Ou(new Predicat("P", x), forall);
		Non n = new Non(ou);

		// n.recolterVariables();
		// System.out.println(n.skolemiser().clausifier());
		
		Clause c = new Clause();
		c.getAtomes().put("P(x)", new AtomeSimple(false, new Predicat("P", x)));
		c.getAtomes().put("!P(x)", new AtomeSimple(true, new Predicat("P", x)));
		
		Clause c1 = new Clause();
		c1.getAtomes().put("!P(x)", new AtomeSimple(true, new Predicat("P", x)));
		c1.getAtomes().put("P(x)", new AtomeSimple(false, new Predicat("P", x)));
		
		System.out.println("C is a tautology : " + c.estUneTautologie());
		
		ArrayList<Clause> clauses = new ArrayList<>();
		clauses.add(c1);
		
		System.out.println("C is included in clauses : " + c.estIncluseDans(clauses));
		System.out.println("Clause set is unsatifiable : " + Resolution.resoudre(clauses));
		
	}

}
