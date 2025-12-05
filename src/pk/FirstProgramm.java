package pk;

public class FirstProgramm {
	// Aufgabe 3.2
	public void maleTreppe(int hoehe, int stufentiefe) {
		int anzahl = stufentiefe;
		int leer = hoehe * stufentiefe- stufentiefe;
		
		for(int z=0; z<hoehe; z++) {
		for(int x= 0; x< leer; x++) {
			System.out.print(" ");
		}
			for(int y = 0; y < anzahl; y++) {
			System.out.print("*");
		}
		System.out.println();
		anzahl += stufentiefe;
		
		leer-=stufentiefe;
		}
	}
	public static void main(String[] args) {
	    FirstProgramm f = new FirstProgramm();
	    f.maleTreppe(8, 3);
	}	
}		
		
		
	// Aufgabe2 	
		/*for (int i = hoehe; i < 0; i--) {
			for (int j = 0; j < anzahl; j++) {
				System.out.print("*");
			}
			System.out.println();
			anzahl += stufentiefe;
		}
	}
	
	public static void main(String[] args) {
	    FirstProgramm f = new FirstProgramm();
	    f.maleTreppe(6, 2);
	} //Dies ist ein Kommentar zur FireProgramm-Klasse von Helin
}*/
