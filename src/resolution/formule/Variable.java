package resolution.formule;

import java.util.HashMap;

public class Variable extends Terme {

	String nom;

	public Variable(String nom) {
		super();
		this.nom = nom;
	}

	@Override
	public HashMap<String, Variable> getVariables() {

		HashMap<String, Variable> vars = new HashMap<String, Variable>();
		vars.put(toString(), this);
		
		return vars;
	}
	
	/**
	 * Si la variable courante à le même nom que la variable en argument
	 * alors le terme t est renvoyé (var est substituée par t).
	 * Sinon c'est la variable courante qui est renvoyée.
	 */
	@Override
	public Terme substituerVariable(Variable var, Terme t) {
		
		if (nom == var.nom)
			return t;
	
		return this;
	}

	@Override
	public boolean estInclusDans(Terme t) {
		
		boolean estInclus = false;
		
		if (t instanceof Variable)
			estInclus = equals(t);
		else {
			Fonction f = (Fonction) t;
			
			for (Terme arg : f.getArgs())
				estInclus = estInclus || estInclusDans(arg);
	
		}
		
		return estInclus;
	}

	@Override
	public String toString() {
		return nom;
	}

}