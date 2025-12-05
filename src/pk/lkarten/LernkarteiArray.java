package pk.lkarten;

import java.util.Random;

public class LernkarteiArray   { // Box für Karten 
	private Lernkarte[] karten;
	private int anzahl=0;
	
	
	public LernkarteiArray (int kapazitaet) {
		karten =new Lernkarte [kapazitaet];
		
	}
	
	public void hinzufuegen(Lernkarte karte) throws UngueltigeKarteException {
		karte.validiere();
		
		if( anzahl < karten.length ) {
			karten [anzahl] =karte;
			anzahl++;
		}else {
			System.err.print("Fehler: Lernkartei voll!");
		}
	}
	
	public void druckeAlleKarten () {

		for (int i = 0; i < anzahl; i++) {
			System.out.println(karten[i]);// karten[i] = toSting Methode aufrufen 
		}
	}
	
	public int gibAnzahlKarten () {
		int anz=0;
		for(int i=0;i<karten.length;i++) {
			if(karten[i]!=null) {
				anz++;
			}else{
				return anz;
			}
		}
		return anz;
		
	}
	
	public Lernkarte[] gibKartenZuKategorie(String kategorie) {
		if(anzahl == 0) {
			return new Lernkarte[0];
		}
		
		int zaehler=0; //Array grösse bestimmen für das neue Array 
		for (int i=0; i< anzahl; i++) {
			if(karten[i].getKategorie() .equals(kategorie)) {
				zaehler++;	
			}	
		}
		Lernkarte[] kArray = new Lernkarte[zaehler]; // Neue Array 
		int o =0;// Zähler für das neue Array 
		for(int y=0; y< anzahl; y++) { // zweiter prüfgang (Alte Array) + einfügen (Neue Array)
			if(karten[y].getKategorie() .equals(kategorie)) {
				kArray[o]=karten[y];
				o++;
			}
		}
		return kArray; 	//Neue Array mit Karten(gleiche Kategorie)
		}
	
	public String arraytostring(Lernkarte[] a) { //um ein Array zu einem String zu machen
		String array = "";
		for (Lernkarte k : a) { // das selbe wie => Lernkarte k = a[i];
	        array += k.toString() + "\n";
	    }
	    return array;
	}
	
	public Lernkarte[] erzeugeDeck(int anzahlKarten) { // Wie eine Test erstellen 
		Random r =new Random();
		
		Lernkarte[] deck = new Lernkarte[anzahlKarten];
		for(int j=0; j<anzahlKarten;j++) {
			int z=r.nextInt(anzahl); //r.nextInt(anzahl) gehört zu Random
			deck[j]=karten[z];
		}
		return deck;
	}


}

