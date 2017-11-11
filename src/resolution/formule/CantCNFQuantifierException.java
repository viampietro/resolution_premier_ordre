package resolution.formule;

public class CantCNFQuantifierException extends RuntimeException {
	
	/**
	 * Pour la compatibilité lors de la sérialisation
	 */
	private static final long serialVersionUID = -3410925252396862156L;
	
	Formule quantifieur;
	
	public CantCNFQuantifierException(Formule quantifieur) {
		this.quantifieur = quantifieur;
	}
	
	@Override
	public String getMessage() {
	
		return "Tentative de clausification du quantifieur " + quantifieur.toString();
	}
}
