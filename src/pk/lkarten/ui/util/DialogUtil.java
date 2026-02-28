package pk.lkarten.ui.util;

import java.io.File;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DialogUtil {

	/**
	 * Oeffnet einen Nachfragedialog mit den Buttons "Ja" und "Nein".
	 * 
	 * @param titel     Der Titel des Dialoges
	 * @param nachricht Die Nachricht, die auf dem Dialog angezeigt wird
	 *                  (Nachfrage).
	 * @return {@code true} wenn der Benutzer "Ja" geklickt hat, {@code false} sonst
	 */
	public static boolean showConfirmDialog(String titel, String nachricht) {
		Alert dialog = new Alert(AlertType.CONFIRMATION, nachricht, ButtonType.YES, ButtonType.NO);
		dialog.setTitle(titel);
		Optional<ButtonType> result = dialog.showAndWait();
		return result.get() == ButtonType.YES;
	}

	/**
	 * Oeffnet einen Dialog, der eine Nachricht anzeigt.
	 * 
	 * @param titel     Der Titel des Dialoges
	 * @param nachricht Die Nachricht, die auf dem Dialog angezeigt wird.
	 */
	public static void showMessageDialog(String titel, String nachricht) {
		Alert dialog = new Alert(AlertType.INFORMATION);
		dialog.setTitle(titel);
		dialog.setContentText(nachricht);
		dialog.showAndWait();
	}

	/**
	 * Oeffnet einen Dialog, der eine Fehlermeldung anzeigt.
	 * 
	 * @param titel     Der Titel des Dialoges
	 * @param nachricht Die Fehlermeldung, die auf dem Dialog angezeigt wird.
	 */
	public static void showErrorDialog(String titel, String nachricht) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titel);
		alert.setContentText(nachricht);
		alert.showAndWait();
	}

	/**
	 * Oeffnet einen Dialog, der eine Texteingabe vom Benutzer anfordert.
	 * 
	 * @param titel     Der Titel des Dialoges
	 * @param nachricht Die Nachricht, die auf dem Dialog angezeigt wird.
	 * @return Der vom Benutzer eingegebene Text oder ein leerer String, falls der
	 *         Benutzer nichts eingegeben bzw. "Abbrechen" geklickt hat.
	 */
	public static String showInputDialog(String titel, String nachricht) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(titel);
		dialog.setContentText(nachricht);

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
		}
		return "";
	}

	/**
	 * Oeffnet einen Dialog mit konfigurierbaren Inhalten und einem Button.
	 * 
	 * @param titel      Der Titel des Dialoges
	 * @param kopfText   Text im Kopfbereich des Dialoges
	 * @param inhalt     Text fuer den Inhaltsbereich des Dialogen
	 * @param buttonText Text des Buttons
	 */
	public static void showTextDialog(String titel, String kopfText, String inhalt, String buttonText) {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle(titel);
		dialog.setHeaderText(kopfText);
		ButtonType button = new ButtonType(buttonText, ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(button);
		dialog.getDialogPane().setContent(new Label(inhalt));
		dialog.showAndWait();
	}

	/**
	 * Oeffnet einen Dialog zur Auswahl einer Datei aus dem lokalen Dateisystem.
	 * 
	 * @param titel Der Titel des Dialoges
	 * @param stage Die uebergeordnete Stage
	 * @return Die von der Benutzerin ausgewaehlte Datei
	 */
	public static File showFileChooser(String titel, Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(titel);
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		return fileChooser.showOpenDialog(stage);

	}
}
