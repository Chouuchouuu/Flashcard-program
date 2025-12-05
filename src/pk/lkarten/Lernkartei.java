package pk.lkarten;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Lernkartei {
	private HashSet <Lernkarte> karten;
	private int anzahl=0;
	
	public Lernkartei () {
		karten = new HashSet<>();
	}
	
	public void hinzufuegen(Lernkarte karte) {
		karten.add(karte);
		anzahl++;
	}
	
	public void druckeAlleKarten () {
		List<Lernkarte> sortiert = new ArrayList<>(karten);
	    sortiert.sort(Comparator.naturalOrder());
		
		for (Lernkarte k : sortiert) {
			System.out.println(k);
		}
	}
	
	public int gibAnzahlKarten () {
		return karten.size();	
	}
	
	public Lernkarte[] gibKartenZuKategorie(String kategorie) {
		int z = 0;
		Iterator<Lernkarte> it = karten.iterator();
		while (it.hasNext()) {
			Lernkarte karte = it.next();
			if (karte.getKategorie().equals((kategorie)))
			z++;
		}
		
		Lernkarte[] kArray = new Lernkarte[z]; // Neue Array 
		int o =0;// Zähler für das neue Array 
		it = karten.iterator(); // damit wir wieder bei der 1. Karte anfangen (altes it ist bei einer anden Karte)
		while (it.hasNext()) {
			Lernkarte karte = it.next();
			if (karte.getKategorie().equals((kategorie))) {
				kArray[o]= karte;
				o++;
			}
		}
		return kArray;
	}
	
	public String arraytostring(Lernkarte[] a) { //um ein Array zu einem String zu machen
		String array = "";
		for (Lernkarte k : a) { // das selbe wie => Lernkarte k = a[i];
	        array += k.toString() + "\n";
	    }
	    return array;
	}
	
	public Lernkarte[] erzeugeDeck(int anzahlKarten) {
		Random r =new Random();
		Lernkarte[] deck = new Lernkarte[anzahlKarten]; // neues Deck
		int s = karten.size(); //Anzahl an Karten in unserem Hash
		
		for (int j = 0; j < anzahlKarten; j++) {
			int z = r.nextInt(s); // generiert eine zufällige Zahl zwischen 0 bis s
			
			Iterator<Lernkarte> it = karten.iterator(); //holt Karte und speichert in it (irgendeine Karte aus dem Hash)
			for (int i = 0; i < z; i++ ) { //welche Elemente werden übersprungen
				it.next();
			}
			
			deck[j] = it.next();
		}
		return deck;
	}

}
