package resolution.formule;

public class CantSimplifyQuantifierException extends RuntimeException {
	
	/**
	 * Compatibilité avec l'interface Serializable
	 */
	private static final long serialVersionUID = 910406871743256881L;
	Formule quantifieur;

	public CantSimplifyQuantifierException(Formule quantifieur) {
		super();
		this.quantifieur = quantifieur;
	}
	
	@Override
	public String getMessage() {
		
		return "Tentative de simplification du quantifieur " + quantifieur;
	}
	
}
