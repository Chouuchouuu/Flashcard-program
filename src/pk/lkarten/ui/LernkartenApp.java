package pk.lkarten.ui;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pk.lkarten.Lernkarte;
import pk.lkarten.Lernkartei;
import pk.lkarten.db.Database;
import pk.lkarten.db.LernkartenDao;
import pk.lkarten.ui.util.DialogUtil;

public class LernkartenApp extends Application {	
	@Override
	public void start(Stage primaryStage) throws SQLException {
		Connection conn = Database.getInstance().getConnection();
		ListView<Lernkarte> lV = new ListView<>();

		//für Kontakt mit DB
		LernkartenDao dao = new LernkartenDao(conn); //Objekt das mit DB spricht
        Lernkartei kartei = new Lernkartei(dao); //DAO um Karten in DB zu speichern
        
        //Layout
        BorderPane root = new BorderPane();
        
        //Menüleiste erstellen
        MenuBar menuBar = new MenuBar();
        
        //Schaltfläche Datei in der Menüleiste
        Menu menuDatei = new Menu("Datei");
        MenuItem itemExport = new MenuItem("CSV-Export"); //Inhalt der Schaltfläche Datei
        MenuItem itemExit = new MenuItem("Beenden"); //Inhalt der Schaltfläche Datei
        
        //Schaltfläche Lernkartei in der Menüleiste
        Menu menuKartei = new Menu("Lernkartei");
        MenuItem itemAddEinzel = new MenuItem("Einzelantwortkarte hinzufügen");
        MenuItem itemAddMehrfach = new MenuItem("Mehrfachantwortkarte hinzufügen");
        
        //Inhaltliche Schaltflächen einsetzen
        menuDatei.getItems().addAll(itemExport, itemExit);
        menuKartei.getItems().addAll(itemAddEinzel, itemAddMehrfach);
        
        //Menüs in Menüleiste
        menuBar.getMenus().addAll(menuDatei, menuKartei);
        
        //Menüleiste in Layout platzieren
        root.setTop(menuBar);
        
        //Hauptfenster zeigen
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("LernkartenApp");
        primaryStage.setScene(scene);
		primaryStage.show();
		
		//Funktionen der Schaltflächen bei Datei
		itemExit.setOnAction(e -> { 
			Platform.exit();
		});
		itemExport.setOnAction(e -> { 
			FileChooser fileChooser = new FileChooser(); 
			fileChooser.setTitle("Lernkarten exportieren");
		    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Dateien", "*.csv"));
		    File file = fileChooser.showSaveDialog(primaryStage);
		    if (file != null) {
		        try { 
		        	kartei.exportiereEintraegeAlsCsv(file.toPath());
		        	DialogUtil.showInputDialog("Export erfolgreich", "Die Daten wurden erfolgreich nach " + file.getName() + " exportiert.");
		        } catch (Exception ex) {
		            DialogUtil.showErrorDialog("Export fehlgeschlagen", "Fehler beim Exportieren: " + ex.getMessage());
		          }
		    }
		});
		
		//Funktionen der Schaltflächen bei Lernkartei
		itemAddMehrfach.setOnAction(e -> {
		    MehrfachantwortKartenErfassungView view = new MehrfachantwortKartenErfassungView(primaryStage, null, kartei);
		    view.showView();
		});
		root.setCenter(lV);
		Runnable listeAktualisieren = () -> {
			try {
				lV.getItems().clear();
				lV.getItems().addAll(kartei.gibAlleKarten());
			} catch (SQLException ex) {
				DialogUtil.showErrorDialog("Datenbank Fehler", "Karte konnte nicht geladen werden: " + ex.getMessage());
			}
		};
		listeAktualisieren.run();
		
		itemAddEinzel.setOnAction(e -> {
			EinzelantwortErfassungView view = new EinzelantwortErfassungView(primaryStage, null, kartei);
			view.showView();
			listeAktualisieren.run();
		});
		
		Button lernen = new Button("Lernen!"); 
		Spinner<Integer> sp = new Spinner<>(5, 15, 5);
		HBox bottomBox = new HBox (10, lernen, sp);
		bottomBox.setPadding(new Insets(10));
		bottomBox.setAlignment(Pos.CENTER);
		root.setBottom(bottomBox);
		
		lernen.setOnAction(e -> {
			try {
				int anzahl = sp.getValue();
				Lernkarte[] deck = kartei.erzeugeDeck(anzahl);
				
				for(Lernkarte karte: deck ) {
					DialogUtil.showTextDialog(karte.getTitel(), karte.getKategorie(), karte.gibVorderseite(), "Rückseite zeigen");
					DialogUtil.showTextDialog(karte.getTitel(), karte.getKategorie(), karte.gibRueckseite(), "Nächte Karte zeigen");
				}
				
			} catch (SQLException ex) {
				DialogUtil.showErrorDialog("Datenbankfehler", ex.getMessage());
			}
		});
	}
	
	public static void main(String[] args) {
	    launch(args);
	}
}