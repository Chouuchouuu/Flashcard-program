package pk.lkarten.ui;

import pk.lkarten.DoppelteKarteException;
import pk.lkarten.EinzelantwortKarte;
import pk.lkarten.Lernkartei;
import pk.lkarten.UngueltigeKarteException;
import pk.lkarten.ui.util.DialogUtil;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EinzelantwortErfassungView extends ErfassungView{
	
	private final Lernkartei kartei;
	private final TextField tfKategorie = new TextField();
	private final TextField tfTitel = new TextField();
	private final TextArea taFrage = new TextArea();
	private final TextArea taAntwort = new TextArea();

	public EinzelantwortErfassungView(Stage owner, EinzelantwortKarte karte, Lernkartei kartei) {
		super(owner, "Einzelantwortkarte erfassen");
		this.kartei = kartei;
		 
		//höhe der TextAreas
		taFrage.setPrefRowCount(1);
		taAntwort.setPrefRowCount(5);
		 
		//Layout
	    GridPane form = new GridPane();
	    form.setHgap(10);
	    form.setVgap(10);
	     
	    //Eingabefelder
	    form.addRow(0, new Label("Kategorie:"), tfKategorie);
	    form.addRow(1, new Label("Titel:"), tfTitel);
	    form.addRow(2, new Label("Frage:"), taFrage);
	    form.addRow(3, new Label("Antwort:"), taAntwort);
	     
	    //Buttons ordnen
	    HBox buttons = new HBox(10, ok, abbrechen);
	    
	    //Aufbau ordnen
	    VBox root = new VBox(12, form, buttons);
	    root.setPadding(new Insets(15));
	    
	    //scene bauen
	    setScene(new Scene(root, 520, 360));
	    
	    //Wenn Karte exsistiert, dann  Eingabefelder mit vorhandenen Daten belegen
	    if (karte != null) {
	    	tfKategorie.setText(karte.getKategorie());
	        tfTitel.setText(karte.getTitel());
	        taFrage.setText(karte.getFrage());
	        taAntwort.setText(karte.getAntwort());
	    }
	    
	    ok.setOnAction(e-> {
	    	try {
	    		String kategorie = tfKategorie.getText();
	    		String titel = tfTitel.getText();
	    		String frage = taFrage.getText();
	    		String antwort = taAntwort.getText();
	    		
	    		//Neue Karte erstellen
	    		EinzelantwortKarte neuK =new EinzelantwortKarte(kategorie, titel, frage, antwort);
	    		
	    		//Karte Speichern
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