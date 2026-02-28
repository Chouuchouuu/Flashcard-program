package pk.lkarten;

import java.util.Arrays;

public class MehrfachantwortKarte extends Lernkarte{
	private String[] moeglicheAntworten;
	private int[] richtigeAntworten;
	
	public MehrfachantwortKarte(int id, String kategorie, String titel, String frage, String[] antworten, int[] richtigeArr) {
		 super(id, kategorie, titel, frage);
		 this.moeglicheAntworten = antworten;
		 this.richtigeAntworten = richtigeArr;
	 }
	 
	public MehrfachantwortKarte(String kategorie, String titel, String frage, String[] moeglicheAntworten, int[] richtigeAntworten) {
		super(kategorie, titel, frage);
		this.moeglicheAntworten = moeglicheAntworten;
		this.richtigeAntworten = richtigeAntworten;
	}
	
	@Override
	public String exportiereAlsCsv() {
		String moegAntStr = Arrays.toString(moeglicheAntworten); // Array in Text umwandeln (toString(arr) von Java-Standardbibliothek)
		String richAntStr = Arrays.toString(richtigeAntworten);
		
		return super.exportiereAlsCsv() + ",\"" + moegAntStr + "\",\"" + richAntStr + "\"";
	}
	
	@Override
	public void validiere() throws UngueltigeKarteException {
	    super.validiere();

	    if (moeglicheAntworten == null || moeglicheAntworten.length < 2) {
	        throw new UngueltigeKarteException("Bitte mindestens 2 mögliche Antworten angeben!");
	    }
	    for (String a : moeglicheAntworten) {
	        if (a == null || a.equals("")) {
	            throw new UngueltigeKarteException("Mögliche Antworten dürfen nicht leer sein!");
	        }
	    }
	    
	    if (richtigeAntworten == null || richtigeAntworten.length == 0) {
	        throw new UngueltigeKarteException("Mindestens eine richtige Antwort muss angegeben werden!");
	    }
	    for (String a : moeglicheAntworten) {
	        if (a == null || a.isBlank()) {
	            throw new UngueltigeKarteException("Mögliche Antworten dürfen nicht leer sein!");
	        }
	    }
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(moeglicheAntworten);
		result = prime * result + Arrays.hashCode(richtigeAntworten);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (!(obj instanceof MehrfachantwortKarte other)) return false;
	    if (!super.equals(other)) return false;

	    return Arrays.equals(moeglicheAntworten, other.moeglicheAntworten)
	        && Arrays.equals(richtigeAntworten, other.richtigeAntworten);
	}
	
	@Override
	public void zeigeVorderseite() {
	    System.out.println("[" + getId() + ", " + getKategorie() + "] " + getTitel() + ":");
	    System.out.println(getFrage());
	    
	    for (int i = 0; i < moeglicheAntworten.length; i++) {
	        System.out.println((i + 1) + ": " + moeglicheAntworten[i]);
	    }

	    if (richtigeAntworten.length > 1) {
	        System.out.println("(mehrere Antworten möglich)");
	    }
	}

	public int[] getRichtigeAntworten() {
		return richtigeAntworten;
	}
	
	public String[] getMoeglicheAntworten() {
		return moeglicheAntworten;
	}
	
	public void setMoeglicheAntworten(String[] mAntworten) {
		this.moeglicheAntworten = mAntworten;
	}

	public void zeigeRueckseite() {
		String antworten = "";
		int z=1;
		for(int i = 0; i < richtigeAntworten.length; i++) {
			antworten += "\n " + z +": " + richtigeAntworten[i];
			z++;
		}
		System.out.println("Die richtigen Antworten sind:\n" + antworten);
	}
	
	public String toString() {  //Objekt in Text umwandeln
		
		String antworten = ""; //String mit möglichen Antworten 
		int z=1;
		for(int i = 0; i < moeglicheAntworten.length; i++) {
			antworten += "\n " + z +": " + moeglicheAntworten[i];
			z++;
		}//Geht durch das Array mit möglichen Antworten 
		
		String rAntworten =""; //String mit richtigen Antworten 
		int x=1;
		for(int j = 0; j < richtigeAntworten.length; j++) {
			rAntworten += "\n " + x +": " + richtigeAntworten[j];
			x++;
		}//Geht durch das Array mit richtigen Antworten 
		
        return super.toString() + antworten + "\n" + "(mehrere Antworten möglich)"+ "\n"+ "Die richtigen Antworten sind: "+rAntworten+"\n";
    }
	
	@Override
	public String gibVorderseite() {
		String ausgabe = super.toString();
		String[] antworten = getMoeglicheAntworten();
		
		for (int i = 0; i < antworten.length; i++) ausgabe += (i + 1) + ": " + antworten[i] + "\n";
		
		return ausgabe + "\n(mehrere Antworten möglich)";
	}
	
	@Override 
	public String gibRueckseite() {
		String antworten = "";

		for(int i = 0; i < richtigeAntworten.length; i++) {
			int index = richtigeAntworten[i];
			antworten += (i + 1) + ": " + moeglicheAntworten[index] + "\n";
		}
		return "Die richtigen Antworten sind:\n" + antworten;
	}
}
