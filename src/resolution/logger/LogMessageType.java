package resolution.logger;

public enum LogMessageType {
	
	STARTING_CNF("Forme clausale de départ"),
	LAUNCH_RESOLUTION("Lancement de résolution"),
	UNIFIER_FOUND("Unificateur trouvé"),
	NO_UNIFIER("Unification impossible"),
	RESOLUTION_RESULT("Clause résultante"),
	EMPTY_SET_FOUND("Clause vide trouvée"),
	NO_MORE_CLAUSES("Plus de clauses à traiter");
	
	private String name;
	
	LogMessageType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		
		return name;
	}
}
