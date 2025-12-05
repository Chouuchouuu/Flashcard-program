package pk.lkarten;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class Menu {
	Lernkartei karten = new Lernkartei();
	
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
			System.out.print("Lernkarten-App" + "\n" +"1. Lernen!"+"\n" +"2. Einzelantwortkarte hinzufügen"+"\n" + "3. Drucke alle Karten"+"\n" +"4. Drucke Karten zu Kategorie"+"\n" +"5. Beenden"+"\n" +"Bitte Aktion wählen: ");
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
						System.out.println();
						karten.druckeAlleKarten();
						break;						
					case 4:
						druckeKartenZuKategorie();
						System.out.print("\n");
						break;
					case 5:
						
					case 6:
						System.out.print("System beendet");
					default:
						throw new InputMismatchException ();
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
		
		
	
	public void lernen() {
		if (karten.gibAnzahlKarten() < 5) {
			System.out.println("Zu wenig Karten vorhanden");
		}
		else {
			Lernkarte[] deck = karten.erzeugeDeck(5);
	
			for(int i=0; i<5; i++) {				
			deck[i].zeigeVorderseite();
			System.out.println("<Drücken Sie Enter, um die Rückseite der Karte zu sehen.>");
			warteAufEnter();
			
			deck[i].zeigeRueckseite();
			System.out.println("\n" + "<Drücken Sie Enter, um die nächste Karte zu sehen.>");
			warteAufEnter();
			}
			System.out.println("<Alle Karten betrachtet.> \n");
		}
	}
	
	public void einzelantwortkarteHinzufügen() {
		String kategorie = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Kategorie an");
		String titel = JOptionPane.showInputDialog(null, "Bitte geben Sie einen Titel an");
		String frage = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Frage an");
		String antwort = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Antwort an");
		
		EinzelantwortKarte karte1;
		
		try {
			karte1 = new EinzelantwortKarte(kategorie, titel, frage, antwort);
			karten.hinzufuegen(karte1);
		} catch (UngueltigeKarteException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Keine gültige Antwort gegeben!", JOptionPane.WARNING_MESSAGE);
			return; //zurück ins Menü
		  }
	}
	
	public void mehrfachantwortkarteHinzufügen() {
		String kategorie = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Kategorie an");
		String titel = JOptionPane.showInputDialog(null, "Bitte geben Sie einen Titel an");
		String frage = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Frage an");
		String antwort = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Antwort an");
		
		EinzelantwortKarte karte1;
		try {
			karte1 = new EinzelantwortKarte(kategorie, titel, frage, antwort);
			karten.hinzufuegen(karte1);
		} catch (UngueltigeKarteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void druckeKartenZuKategorie() {
		String kategorie = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Kategorie an");
		System.out.println("Karten der gegebenen Kategorie: " + "\n" + karten.arraytostring(karten.gibKartenZuKategorie(kategorie)));

	}
	
}
