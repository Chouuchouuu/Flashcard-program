package pk.lkarten;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class Menu {
    private final Lernkartei karten;
    public Menu(Lernkartei karten) {
        this.karten = karten;
    }
	
	private void warteAufEnter() {
		try {
			System.in.read(new byte[2]);
		} catch (IOException e) {
			System.err.println("Fehler: " + e.getMessage());
			}
		}
	
	public void zeigeMenue () {
		int x=0;
		Scanner sc =new Scanner(System.in);
		
		while(x!=6){
			System.out.print("Lernkarten-App" + "\n" +"1. Lernen!"+"\n" +"2. Einzelantwortkarte hinzufügen"+"\n" + "3. Drucke alle Karten"+"\n" +"4. Drucke Karten zu Kategorie"+"\n"+"5. CSV-Export"+"\n" +"6. Beenden"+"\n" +"Bitte Aktion wählen: ");
			try {
				x =sc.nextInt();
				
				if (x < 1) {
			        throw new KleineZahl("Zahl ist zu klein.\nBitte eine Zahl von 1 bis 5 eingeben!\n");
			    }
			    if (x > 6) {
			        throw new GrosseZahl("Zahl ist zu groß.\nBitte eine Zahl von 1 bis 5 eingeben!\n");
			    }
			    
			    switch (x) {
					case 1:					
						lernen();
						System.out.print("\n");
						break;
					case 2:
						einzelantwortkarteHinzufügen();
						System.out.print("\n");
						break;					
					case 3:
						try {
							karten.druckeAlleKarten();
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, "DB-Fehler:\n" + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
						  }
						break;						
					case 4:
						druckeKartenZuKategorie();
						System.out.print("\n");
						break;
					case 5:
						csvExport();     // Die Methode, die den Export wirklich macht
					    System.out.print("\n");
						break;	
					case 6:
						System.out.print("System beendet");
						break;
			    }
					
			} catch (KleineZahl | GrosseZahl exp) {
				 	System.err.println(exp.getMessage());
			 } 
			 catch (InputMismatchException exp) {
				System.err.println("Bitte NUR Zahlen eingeben!\n");
				sc.nextLine();  
			 }
		}
	}
	
	private void csvExport() {
	    String dateiname = JOptionPane.showInputDialog(null, "Bitte Dateiname für den CSV-Export eingeben:");
	    
	    if (dateiname == null || dateiname.isBlank()) { // Prüfen ob der Benutzer einfach auf „OK“ geklickt hat/ Leerzeichen eingegeben hat
	        JOptionPane.showMessageDialog(null, "Der Dateiname darf nicht leer sein!"); 
	        return; // zurück ins Menü
	    }
	    
	    if (!dateiname.endsWith(".csv")) { // ".csv " anhängen, wenn nicht vorhanden

	        dateiname = dateiname + ".csv";
	    }
	    Path pfad = Path.of(dateiname);
	    
	    if (Files.exists(pfad)) { // prüfen ob Datei existiert
	    	int antwort = JOptionPane.showConfirmDialog(null, "Die Datei existiert bereits. Möchten Sie sie überschreiben?", "Datei überschreiben?", JOptionPane.YES_NO_OPTION);
	    	if (antwort == JOptionPane.NO_OPTION) {	// nichts überschreiben
	    		
	    		return; //Wenn der Benutzer NEIN klickt → dann soll der Methode abgebrochen werden 
	    	} //OHNE return → läuft der Export trotzdem weiter
	    }
	    try {
	        karten.exportiereEintraegeAlsCsv(pfad);
	        JOptionPane.showMessageDialog(null, "Export erfolgreich!");
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "Datei-Fehler:\n" + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "DB-Fehler:\n" + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
	    }
	}    

	
	public void lernen() {
		try {
			if (karten.gibAnzahlKarten() < 5) {
	            System.out.println("Zu wenig Karten vorhanden");
	            return;
			}
			
			Lernkarte[] deck = karten.erzeugeDeck(5);
			
	        for (int i = 0; i < deck.length; i++) {
	            deck[i].zeigeVorderseite();
	            System.out.println("\n<Drücken Sie Enter, um die Antwort zu sehen.>");
	            warteAufEnter();
	            
	            deck[i].zeigeRueckseite();
	            System.out.println("\n<Drücken Sie Enter, um die nächste Karte zu sehen.>");
	            warteAufEnter();
			}
	        
			System.out.println("<Alle Karten betrachtet.> \n");
		} catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "DB-Fehler:\n" + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void einzelantwortkarteHinzufügen() {
		String kategorie = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Kategorie an");
		String titel = JOptionPane.showInputDialog(null, "Bitte geben Sie einen Titel an");
		String frage = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Frage an");
		String antwort = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Antwort an");
		if (kategorie == null || titel == null || frage == null || antwort == null) return;

		try {
			EinzelantwortKarte karte1 = new EinzelantwortKarte(kategorie, titel, frage, antwort);
			karten.hinzufuegen(karte1);
		} catch (UngueltigeKarteException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Keine gültige Antwort gegeben!", JOptionPane.WARNING_MESSAGE);
		} catch (DoppelteKarteException e) {
	        JOptionPane.showMessageDialog(null, e.getMessage(), "Doppelte Karte", JOptionPane.WARNING_MESSAGE);
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "DB-Fehler:\n" + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void mehrfachantwortkarteHinzufügen() throws SQLException, DoppelteKarteException {
		String kategorie = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Kategorie an");
		String titel = JOptionPane.showInputDialog(null, "Bitte geben Sie einen Titel an");
		String frage = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Frage an");
		if (kategorie == null || titel == null || frage == null) return;

		try {
			String moeglicheAntwStr = JOptionPane.showInputDialog(null, "Wie viele Antwortmöglichkeiten gibt es?"); //es wird gefragt wv mögliche Antworten es gibt
			int moeglicheAntwInt = Integer.parseInt(moeglicheAntwStr); //die eingabe für mögliche Antworten wird in ein Int gemacht
	    
			String[] moeglicheAntworten = new String[moeglicheAntwInt];
			for (int i = 0; i < moeglicheAntwInt; i++) {
				moeglicheAntworten[i] = JOptionPane.showInputDialog(null, "Mögliche Antwort " + (i + 1)); 
			} //hier werden die möglichen Antworten dann eingegeben
			
		String richtigeAntwStr = JOptionPane.showInputDialog(null, "Wie viele davon sind richtig?"); //es wird gefragt wv richtige Antworten es sind
	    int richtigeAntwInt = Integer.parseInt(richtigeAntwStr); //die eingabe für die richtigen Antworten wird in ein Int gemacht
	    
	    int[] richtigeAntworten = new int[richtigeAntwInt];

	    for (int i = 0; i < richtigeAntwInt; i++) {
	    	String eingabe = JOptionPane.showInputDialog(null, "Nummer der Richtigen Antwort (1 -  " + moeglicheAntwInt + "):");
	    	int index = Integer.parseInt(eingabe) - 1;
	    	
	    	if (index < 0 || index >= moeglicheAntwInt) {
	            throw new NumberFormatException("Index außerhalb des gültigen Bereichs");
	        }
	    	
	    	richtigeAntworten[i] = index;
	    }
	    MehrfachantwortKarte karte = new MehrfachantwortKarte(kategorie, titel, frage, moeglicheAntworten, richtigeAntworten);
	    karten.hinzufuegen(karte);
	    JOptionPane.showMessageDialog(null, "Mehrfachantwortkarte erfolgreich hinzugefügt!");
	    
	    } catch (UngueltigeKarteException e) {
	        JOptionPane.showMessageDialog(null, e.getMessage(), "Keine gültige Mehrfachantwortkarte!", JOptionPane.WARNING_MESSAGE);
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Bitte gültige Zahlen eingeben!", "Fehler", JOptionPane.ERROR_MESSAGE);
	    } catch (DoppelteKarteException e) {
	        JOptionPane.showMessageDialog(null, e.getMessage(), "Doppelte Karte", JOptionPane.WARNING_MESSAGE);
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "DB-Fehler:\n" + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void druckeKartenZuKategorie() {
		String kategorie = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Kategorie an");
	    try {
	        System.out.println("Karten der gegebenen Kategorie:\n" + karten.arraytostring(karten.gibKartenZuKategorie(kategorie)));
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "DB-Fehler:\n" + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
	    }
	}

}
