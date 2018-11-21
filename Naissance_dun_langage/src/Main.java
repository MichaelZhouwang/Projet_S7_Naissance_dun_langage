import systeme.Systeme;

public class Main {

	public static void main(String[] args) {

		System.out.println("Debut simulation");
		
		String fichierXml = args[0];
		
		Systeme.genererByXml(fichierXml);
		Systeme.routine();
		
		System.out.println("Fin simulation");
	}
}