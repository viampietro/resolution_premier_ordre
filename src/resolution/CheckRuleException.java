package resolution;
public class CheckRuleException extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1045206426000811498L;
	
	Equation e;

	public CheckRuleException(Equation e) {
		super();
		this.e = e;
	}
	
	@Override
	public String getMessage() {
		return "Equation non unifiable : " + e;
	}
	
}
