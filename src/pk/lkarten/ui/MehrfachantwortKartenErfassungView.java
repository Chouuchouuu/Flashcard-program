package pk.lkarten.ui;

import pk.lkarten.DoppelteKarteException;
import pk.lkarten.Lernkartei;
import pk.lkarten.MehrfachantwortKarte;
import pk.lkarten.UngueltigeKarteException;
import pk.lkarten.ui.util.DialogUtil;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MehrfachantwortKartenErfassungView extends ErfassungView{
	
	private final Lernkartei kartei;
	
	private final TextField tfKategorie = new TextField();
	private final TextField tfTitel = new TextField();
	private final TextArea taFrage = new TextArea();

	private final TextField[] antwortFelder = new TextField[5];
	private final CheckBox[] cb = new CheckBox[5];

	 public MehrfachantwortKartenErfassungView(Stage owner, MehrfachantwortKarte karte, Lernkartei kartei) {
		 super(owner, "Mehrfachantwortkarte erfassen");
		 this.kartei = kartei;
		 
		 //höhe der TextAreas
	     taFrage.setPrefRowCount(1);

		 //Layout
	     GridPane form = new GridPane();
	     form.setHgap(10);
	     form.setVgap(10);
	     //Layout für Antworten
	     GridPane antwortGrid = new GridPane();
	     antwortGrid.setHgap(10);
	     antwortGrid.setVgap(8);
	     //Layout für ob richtige Antwort ist
	     antwortGrid.add(new Label("Richtig?"), 0, 0);
	     antwortGrid.add(new Label("Antwort"), 1, 0);
	     
	     //Eingabefelder
	     form.addRow(0, new Label("Kategorie:"), tfKategorie);
	     form.addRow(1, new Label("Titel:"), tfTitel);
	     form.addRow(2, new Label("Frage:"), taFrage);
	     
	     for (int i = 0; i < 5; i++) { //für jede Antwort jeweils...
	         cb[i] = new CheckBox(); //...eine CheckBox (ob richtig ist)
	         antwortFelder[i] = new TextField(); //...ein Textfelf für Antwort

	         antwortGrid.add(cb[i], 0, i + 1);
	         antwortGrid.add(antwortFelder[i], 1, i + 1);
	     }

	     HBox buttons = new HBox(10, ok, abbrechen);

	     VBox root = new VBox(12, form, new Separator(), antwortGrid, buttons);
	     root.setPadding(new Insets(15));

	     setScene(new Scene(root, 620, 430));
	     
		 //Wenn Karte exsistiert, dann  Eingabefelder mit vorhandenen Daten belegen
	     if (karte != null) {
	         tfKategorie.setText(karte.getKategorie());
	         tfTitel.setText(karte.getTitel());
	         taFrage.setText(karte.getFrage());
	         
	         //Daten aus Karte holen
	         String[] moeglich = karte.getMoeglicheAntworten();
	         int[] richtig = karte.getRichtigeAntworten();
	         
	         //in Set umwandeln
	         Set<Integer> richtigeIndizes = new HashSet<>();
	         if (richtig != null) {
	             for (int idx : richtig) richtigeIndizes.add(idx);
	         }
	         
	         //vorfüllen
	         for (int i = 0; i < 5; i++) {
	             if (moeglich != null && i < moeglich.length) {
	                 antwortFelder[i].setText(moeglich[i]);
	                 cb[i].setSelected(richtigeIndizes.contains(i));
	             } else {
	                 antwortFelder[i].setText("");
	                 cb[i].setSelected(false);
	             }
	         }
	     }
	     
	    ok.setOnAction(e->{
	    	try {
	    		String kategorie = tfKategorie.getText();
	    		String titel = tfTitel.getText();
	    		String frage = taFrage.getText();
	    		
	    		List<String> m = new ArrayList<>();
	    		List<Integer> r = new ArrayList<>();
	    		for(int i=0; i<5; i++) {
	    			String text= antwortFelder[i].getText();
	    			if (text != null && !text.trim().isEmpty()) {
	    				m.add(text);
	    				if (cb[i].isSelected()) {
                            r.add(m.size() - 1);
                        }
	    			}

	    		}
	    		String[] mAntworten = m.toArray(new String[0]);
	    		int[] rAntworten = r.stream().mapToInt(i->i).toArray();
	    		
	    		MehrfachantwortKarte neuK = new MehrfachantwortKarte(kategorie, titel, frage, mAntworten, rAntworten);
	    		kartei.hinzufuegen(neuK);
	    		
	    		close();
	    	} catch(UngueltigeKarteException ex) {
	    		DialogUtil.showErrorDialog("Ungueltige Eingabe",ex.getMessage());
	    	} catch (DoppelteKarteException ex) {
	    		DialogUtil.showErrorDialog("Fehler","Karte existiert bereits");
	    	}catch (java.sql.SQLException ex) {
	    		DialogUtil.showErrorDialog("Datenbankfehler","Fehler beim speichern: "+ ex.getMessage());
	    	}	    	
	    });
	 }

	
}