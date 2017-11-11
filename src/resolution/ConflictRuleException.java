package resolution;

import resolution.formule.Terme;

public class ConflictRuleException extends Exception {
	
	/**
	 *  
	 */
	private static final long serialVersionUID = 4747713459133626523L;
	
	Terme first;
	Terme second;
	
	public ConflictRuleException(Terme first, Terme second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public String getMessage() {
		return "Conflit entre " + first + " et " + second;
	}
	
}
