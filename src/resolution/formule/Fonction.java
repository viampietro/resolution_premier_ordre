package resolution.formule;

import java.util.ArrayList;
import java.util.HashMap;

public class Fonction extends Terme {

	String nom;
	ArrayList<Terme> args;

	/*
	 * contribuer a generer des nouveaux symboles de fonctions a la demande.
	 */
	private static int compteur = 0;
	
	/************************************************************************
	 *                          CONSTRUCTEURS
	 ************************************************************************/
	
	public Fonction(String nom) {
		this.nom = nom;
		this.args = new ArrayList<Terme>();
	}
	
	public Fonction(String nom, Terme... args) {
		super();
		this.nom = nom;
		this.args = new ArrayList<Terme>();

		for (Terme t : args) {
			this.args.add(t);
		}
	}

	/**
	 * Constructeur seuleument accessible dans le package resolution.formule.
	 * Utilis� pour la cr�ation de symboles de fonctions pseudo-al�atoires lors
	 * de la skolemisation ou de l'hebrandisation des quantificateurs universels
	 * ou existentiels.
	 * 
	 * @param args
	 */
	Fonction(Terme... args) {

		this.nom = "f$" + compteur;

		/*
		 * la valeur du compteur est augment� apr�s nommage pour s'assurer de
		 * l'unicit� des noms de fonctions.
		 */
		compteur++;

		this.args = new ArrayList<Terme>();

		for (Terme t : args) {
			this.args.add(t);
		}
	}

	Fonction(ArrayList<Terme> args) {
		this.nom = "f$" + compteur;

		/*
		 * la valeur du compteur est augmenté après nommage pour s'assurer de
		 * l'unicité des noms de fonctions.
		 */
		compteur++;

		this.args = new ArrayList<Terme>(args);
	}
	
	public Fonction(String nom, ArrayList<Terme> termes) {
		this.nom = nom;
		this.args = new ArrayList<Terme>(termes);
		
	}

	/************************************************************************
	 *                          GETTERS & SETTERS
	 ************************************************************************/
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public ArrayList<Terme> getArgs() {
		return args;
	}

	public void setArgs(ArrayList<Terme> args) {
		this.args = args;
	}

	
	/************************************************************************
	 *                          METHODES REDEFINIES
	 ************************************************************************/
	@Override
	public HashMap<String, Variable> getVariables() {

		HashMap<String, Variable> vars = new HashMap<>();

		for (Terme t : args) {
			vars.putAll(t.getVariables());
		}

		return vars;
	}

	@Override
	public Terme substituerVariable(Variable var, Terme t) {

		ArrayList<Terme> nouveauxArgs = new ArrayList<>();

		for (Terme terme : args)
			nouveauxArgs.add(terme.substituerVariable(var, t));

		args = nouveauxArgs;

		return this;
	}
	
	

	@Override
	public boolean estInclusDans(Terme t) {
		
		boolean estInclus = false;
		
		if (t instanceof Fonction) {
			
			Fonction f = (Fonction) t;
			estInclus = equals(t); 
		
			for (Terme arg : f.getArgs())
				estInclus = estInclus || estInclusDans(arg);
		
		}
		
		return estInclus;
	}

	@Override
	public String toString() {
		
		if(args.isEmpty())
			return nom;
		
		String argsToString = "(";

		for (int i = 0; i < args.size(); i++) {

			if (i == args.size() - 1)
				argsToString += args.get(i);
			else
				argsToString += args.get(i) + ",";
		}

		return nom + argsToString + ")";
	}

}