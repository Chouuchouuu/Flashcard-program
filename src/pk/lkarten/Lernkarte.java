package pk.lkarten;

import java.util.Objects;

public abstract class Lernkarte implements Comparable<Lernkarte>, ValidierbareKarte { // einzelne Karten
	private int id;
	
    public int compareTo(Lernkarte other) {
		if (this == other) {
			return 0;
		}
        return Integer.compare(id, other.getId());
    }
	
	public static int naechterId=1;
	private String kategorie;
	private String titel;
	private String frage;
	
	public void validiere() throws UngueltigeKarteException {
			if( getKategorie()==null) {
				throw new UngueltigeKarteException("Kategorie darf nicht null sein");
			}
			if( getTitel()==null) {
				throw new UngueltigeKarteException("Titel darf nicht null sein");
			}
			if( getFrage()==null) {
				throw new UngueltigeKarteException("Frage darf nicht null sein");
			}
		 
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(frage, kategorie, titel);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lernkarte other = (Lernkarte) obj;
		return Objects.equals(frage, other.frage) && Objects.equals(kategorie, other.kategorie)
				&& Objects.equals(titel, other.titel);
	}
	
	public Lernkarte(String kategorie,String titel, String frage) {
		this.kategorie=kategorie;
		this.titel=titel;
		this.frage=frage;
		this.id=naechterId;
		naechterId++;
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

	public int getId() {

		return id;
	}
	
	public void zeigeVorderseite() {
		System.out.println("["+ getId()+ ", "+ getKategorie()+"] "+ getTitel()+ ":\n"+ getFrage() );
	}
	
	public abstract void zeigeRueckseite ();
	
	public void druckeKarte() {
		zeigeVorderseite();
		zeigeRueckseite ();
	}
	
	public String toString() {
        return "[" + id + ", " + kategorie + "] " + titel + ":\n"
             + frage + "\n";
    }
	
}
