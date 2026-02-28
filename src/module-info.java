module pk {
	 requires java.desktop;
	 requires java.sql;
	 requires javafx.controls;
	 requires javafx.graphics;
	 opens pk.lkarten.ui to javafx.graphics;
}
