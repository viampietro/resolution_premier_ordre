package resolution.logger;

public enum LogMessageType {
	
	LAUNCH_RESOLUTION("Lancement de résolution"),
	UNIFIER_FOUND("Unificateur trouvé"),
	RESOLUTION_RESULT("Résultat de la résolution");
	
	private String name;
	
	LogMessageType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		
		return name;
	}
}
