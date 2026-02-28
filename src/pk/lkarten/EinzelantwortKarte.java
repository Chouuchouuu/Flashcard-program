package pk.lkarten;

import java.util.Objects;

public class EinzelantwortKarte extends Lernkarte {
	private String antwort;
	
	public EinzelantwortKarte(String kategorie, String titel, String frage, String antwort) {
		super(kategorie, titel, frage);
		this.antwort = antwort;
	}
	
	public EinzelantwortKarte(int id, String kategorie, String titel, String frage, String antwort){
        super(id, kategorie, titel, frage);
        this.antwort = antwort;
    }
	
	public String exportiereAlsCsv() { 
		return super.exportiereAlsCsv() + "," + getAntwort();
	}
	
	public void validiere() throws UngueltigeKarteException {
        super.validiere();
        if (antwort == null || antwort.isBlank()) {
            throw new UngueltigeKarteException("Antwort darf nicht null sein");
        }
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(antwort);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (!(obj instanceof EinzelantwortKarte other)) return false;
	    if (!super.equals(other)) return false;

	    return Objects.equals(antwort, other.antwort);
	}

	public void zeigeRueckseite() {
		System.out.println("\t" + getAntwort());
	}
	
	public String getAntwort() {
		return antwort;
	}
	
	public String toString() {
        return super.toString() + antwort + "\n" ;
    }
	
	@Override
	public String gibVorderseite() {
		return super.toString() + "\n";
	}
	
	@Override 
	public String gibRueckseite() {
		return antwort + "\n";
	}
}
