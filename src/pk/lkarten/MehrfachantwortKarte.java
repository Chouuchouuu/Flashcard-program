package pk.lkarten;

import java.util.Arrays;

public class MehrfachantwortKarte extends Lernkarte{
	private String[] moeglicheAntworten;
	private String[] richtigeAntworten;
	
	public MehrfachantwortKarte(String kategorie, String titel, String frage, String[] antworten, String[] richtige) 
			throws UngueltigeKarteException {
		super(kategorie, titel, frage);
		this.moeglicheAntworten = antworten;
		this.richtigeAntworten = richtige;
		validiere();
	}
	
	@Override
	public void validiere() throws UngueltigeKarteException {
        super.validiere();
        
        if (moeglicheAntworten == null || moeglicheAntworten.length < 2) {
            throw new UngueltigeKarteException("Bitte mindestens 2 Antworten angeben!");
        }
        for (String a : moeglicheAntworten) {
        	if (a == null || a.equals("")) {
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
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MehrfachantwortKarte other = (MehrfachantwortKarte) obj;
		return Arrays.equals(moeglicheAntworten, other.moeglicheAntworten)
				&& Arrays.equals(richtigeAntworten, other.richtigeAntworten);
	}


	public String[] getRichtigeAntworten() {
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
		System.out.print("Die richtigen Antworten sind: " + "\n" + antworten);
	}
	
	public String toString() {
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
		
        return "[" + getId() + ", " + getKategorie() + "] " + getTitel() + ":\n"
             + getFrage() + antworten + "\n" + "(mehrere Antworten möglich)"+ "\n"+ "Die richtigen Antworten sind: "+rAntworten+"\n";
    }

}
