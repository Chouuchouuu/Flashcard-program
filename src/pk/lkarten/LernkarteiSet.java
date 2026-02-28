package pk.lkarten;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class LernkarteiSet {
	private HashSet <Lernkarte> karten;
	private int anzahl=0;
	
	public LernkarteiSet () {
		karten = new HashSet<>();
	}
	
	public void exportiereEintraegeAlsCsv(Path datei) throws IOException {
		try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(datei.toFile()),StandardCharsets.UTF_8))) {
			
			 // Kopfzeile
	        out.println("ID,Kategorie,Titel,Frage,Antwort(en),Richtige Antwort(en)");

	        // Einträge schreiben
	        for (Lernkarte k : karten) {
	            out.println(k.exportiereAlsCsv());
	        }
		}
		
	}
	
	public void exportiereEintraegeAlsCsvNio(Path datei) throws IOException {
		List<String> zeilen = new ArrayList<>(); // Damit wir alle Zeile später hier haben 
		
		zeilen.add("ID,Kategorie,Titel,Frage,Antwort(en),Richtige Antwort(en)"); //Überschrift der Tabelle
		
		for (Lernkarte k : karten) { // alle Karten durch gehen und hinzufügen
		    zeilen.add(k.exportiereAlsCsv());
		}
		
		Files.write(datei, zeilen);	
		
	}
	
	public void hinzufuegen(Lernkarte karte) {
		if (karte == null) {
            throw new IllegalArgumentException("Karte darf nicht null sein");
        }
		karten.add(karte);
	}
	
	public void druckeAlleKarten () {
		List<Lernkarte> sortiert = new ArrayList<>(karten);
        Collections.sort(sortiert);
		
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
			
            Lernkarte gezogeneKarte = it.next();
			deck[j] = gezogeneKarte;
		}
		return deck;
	}

}
