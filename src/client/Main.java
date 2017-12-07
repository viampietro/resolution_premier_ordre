package client;

import java.util.ArrayList;
import java.util.logging.Logger;

import de.dominicscheurer.fol.parser.FOLParser;
import de.dominicscheurer.fol.parser.ParseException;
import resolution.formule.Formule;
import resolution.logger.LogMessage;
import resolution.logger.LoggerAspect;


public class Main {

	public static void main(String[] args) {

//		Variable x = new Variable("x");
//		Variable y = new Variable("y");
//		Variable z = new Variable("z");
//
//		Fonction a = new Fonction("a");
//		Fonction b = new Fonction("b");
//
//		PourTout forall = 
//		new PourTout(x, 
//			new Implication(
//				new Predicat("P", x),
//				new IlExiste(y, 
//		 			new Ou(new Predicat("P", y), new Predicat("Q", y)))));
//
//		IlExiste ilexiste = new IlExiste(x,
//				new Implication(
//						new Predicat("P", x), 
//						new Et(new Predicat("P", a), new Predicat("P", b))));
//
//		System.out.println("La formule " + forall + " est-elle valide ? "
//				+ Resolution.resoudre(forall.nier().skolemiser().clausifier()));
//		
//		System.out.println("La formule " + ilexiste + " est-elle valide ? "
//				+ Resolution.resoudre(ilexiste.nier().skolemiser().clausifier()));
		
		try {
			
			
			
			Formule f = FOLParser.parse("forall X. (p(X) -> exists Y. ((p(Y) | q(Y)) | bottom))");
			
			System.out.println(f + " valide ? " + f.resoudre());
			
			LoggerAspect logger = LoggerAspect.aspectOf();
			ArrayList<LogMessage> logs = logger.getLogMessages();
			
			logs.stream().forEach(m -> {
				System.out.println(m.toString());
			});
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
