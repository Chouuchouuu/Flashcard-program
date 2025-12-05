package pk.lkarten;

import java.util.Objects;

public class EinzelantwortKarte extends Lernkarte {
	private String antwort;
	
	public EinzelantwortKarte(String kategorie, String titel, String frage, String antwort) 
			throws UngueltigeKarteException {
		super(kategorie, titel, frage);
		this.antwort = antwort;
		validiere();
	}
	
	public void validiere() throws UngueltigeKarteException {
        super.validiere();
        if (antwort == null) {
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
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EinzelantwortKarte other = (EinzelantwortKarte) obj;
		return Objects.equals(antwort, other.antwort);
	}



	public void zeigeRueckseite() {
		System.out.print("\t" + getAntwort());
	}
	
	public String getAntwort() {
		return antwort;
	}
	
	public String toString() {
        return "[" + getId() + ", " + getKategorie() + "] " + getTitel() + ":\n"
             + getFrage() + "\n" + antwort + "\n" ;
    }
}
