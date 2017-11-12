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

		IlExiste ilexiste = new IlExiste(x,
				new Implication(
						new Predicat("P", x), 
						new Et(new Predicat("P", a), new Predicat("P", b))));

		System.out.println("La formule " + forall + " est-elle valide ? "
				+ Resolution.resoudre(forall.nier().skolemiser().clausifier()));
		
		System.out.println("La formule " + ilexiste + " est-elle valide ? "
				+ Resolution.resoudre(ilexiste.nier().skolemiser().clausifier()));
	}

}
