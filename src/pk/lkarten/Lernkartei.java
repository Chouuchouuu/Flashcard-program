package pk.lkarten;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import pk.lkarten.db.LernkartenDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Lernkartei {
	private LernkartenDao lkD;
	private int anzahl=0;
		
	public Lernkartei (LernkartenDao lkD) {
		this.lkD = lkD;
	}
		
	public void exportiereEintraegeAlsCsv(Path datei) throws IOException, SQLException {
		try (BufferedWriter writer = Files.newBufferedWriter(datei)) {
			writer.write("ID,Kategorie,Titel,Frage,Antwort(en),Richtige Antwort(en)");
			writer.newLine();
			for (Lernkarte k : lkD.findAll()) {
				writer.write(k.exportiereAlsCsv());
				writer.newLine();
	        }
		}
	}
		
	public void exportiereEintraegeAlsCsvNio(Path datei) throws IOException, SQLException {
		List<String> zeilen = new ArrayList<>(); // Damit wir alle Zeile später hier haben 
			
		zeilen.add("ID,Kategorie,Titel,Frage,Antwort(en),Richtige Antwort(en)"); //Überschrift der Tabelle
			
		for (Lernkarte k : lkD.findAll()) { // alle Karten durch gehen und hinzufügen
			zeilen.add(k.exportiereAlsCsv());
		}
			Files.write(datei, zeilen);		
	}
	
	public int hinzufuegen(Lernkarte karte) throws SQLException, DoppelteKarteException, UngueltigeKarteException {
		karte.validiere();
		
		int neueID = 0;
		
		if (karte instanceof EinzelantwortKarte) {
			neueID = lkD.createEinzelantwortKarte((EinzelantwortKarte) karte);
	    } else if (karte instanceof MehrfachantwortKarte) {
			neueID = lkD.createMehrfachantwortKarte((MehrfachantwortKarte) karte);
	    }
		
		return neueID;
	}	
	
	public void druckeAlleKarten () throws SQLException {
		for (Lernkarte k : lkD.findAll()) {
			k.druckeKarte();
		}
	}
		
	public int gibAnzahlKarten () throws SQLException {
		return lkD.countLernkarten();
	}
		
	public Lernkarte[] gibKartenZuKategorie(String kategorie) throws SQLException {
		List<Lernkarte> liste = lkD.findByKategorie(kategorie);
		
		return liste.toArray(new Lernkarte[0]);
	}
		
	public String arraytostring(Lernkarte[] a) { //um ein Array zu einem String zu machen
		String array = "";
		for (Lernkarte k : a) { // das selbe wie => Lernkarte k = a[i];
			array += k.toString() + "\n";
		}
	    return array;
	}
		
	public Lernkarte[] erzeugeDeck(int anzahlKarten) throws SQLException {
		List<Lernkarte> alle = lkD.findAll();
		if (alle.isEmpty()) return new Lernkarte[0];
		
		Random r =new Random();
		
		Lernkarte[] deck = new Lernkarte[anzahlKarten];
			
		for (int i = 0; i < anzahlKarten; i++) {
			deck[i] = alle.get(r.nextInt(alle.size()));
		}
		return deck;
	}
	
	public Lernkarte[] gibAlleKarten() throws SQLException {
		List<Lernkarte> alleKarten = lkD.findAll();
		
		return alleKarten.toArray(new Lernkarte[0]);
	}

}
