
package pk.lkarten.ui;

import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class ErfassungView extends Stage{
	protected final Button ok = new Button("OK");
	protected final Button abbrechen = new Button("Abbrechen");
	
	protected ErfassungView(Stage owner /*Hauptfenster*/ , String titel) {
		setTitle(titel);

	    initOwner(owner); //verknüpft Dialog mit Hauptfenster
	    initModality(Modality.WINDOW_MODAL);
	    abbrechen.setOnAction(e-> close());
	}

	public void showView() {
		showAndWait();
	}
	
  }