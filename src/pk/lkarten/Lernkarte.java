package pk.lkarten;
import java.util.Objects;

public abstract class Lernkarte implements Comparable<Lernkarte>, ValidierbareKarte, CsvExportable { // einzelne Karten
	private int id;
	private String kategorie;
	private String titel;
	private String frage;
	
	public Lernkarte(String kategorie,String titel, String frage) {
		this.kategorie=kategorie;
		this.titel=titel;
		this.frage=frage;
	} //Konstruktor falls neue Karte ohne DB
	
	public Lernkarte(int id, String kategorie, String titel, String frage) {
        this.id = id;
        this.kategorie = kategorie;
        this.titel = titel;
        this.frage = frage;
    } //Konstruktor für neue Karte aus DB
	
	public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
	
	@Override
	public String exportiereAlsCsv() {
	   return getId() + "," + getKategorie() + "," + getTitel() + "," + getFrage();
	}
	
	@Override
	public int compareTo(Lernkarte other) {
	    if (this == other) {
	        return 0;
	    }
	    return Integer.compare(other.getId(), this.id); //absteigend
	}
	
	public void validiere() throws UngueltigeKarteException {
		if( getKategorie()==null || kategorie.isBlank()) {
			throw new UngueltigeKarteException("Kategorie darf nicht null sein");
		}
		if( getTitel()==null || titel.isBlank()) {
			throw new UngueltigeKarteException("Titel darf nicht null sein");
		}
		if( getFrage()==null || frage.isBlank()) {
			throw new UngueltigeKarteException("Frage darf nicht null sein");
		} 
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(frage, kategorie, titel);
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (!(obj instanceof Lernkarte other)) return false;

	    return Objects.equals(frage, other.frage)
	        && Objects.equals(kategorie, other.kategorie)
	        && Objects.equals(titel, other.titel);
	}
	
	public String getKategorie() {
		return kategorie;
	}

	public String getTitel() {
		return titel;
	}

	public String getFrage() {
		return frage;
	}
	
	public void zeigeVorderseite() {
		System.out.println("["+ getId() + ", "+ getKategorie() +"] "+ getTitel() + ":\n" + getFrage() );
	}
	
	public abstract void zeigeRueckseite ();
	
	public void druckeKarte() {
		zeigeVorderseite();
		zeigeRueckseite ();
	}
	
	@Override
	public String toString() {
        return "[" + id + ", " + kategorie + "] " + titel + ":\n" + frage + "\n";
    }
	
	public abstract String gibVorderseite();
	public abstract String gibRueckseite();
	
	
}
