package pk.lkarten.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Hilfsklasse zum Verwalten der Datenbankverbindung.
 */
public class Database implements AutoCloseable {

	private static Database instance;
	private Connection conn;

	/**
	 * Konstruktor ist privat, um zu verhindern, dass mehrere Instanzen der Klasse
	 * existieren. Verwenden Sie stattdessen die Methode getInstance(), um eine
	 * Instanz zu erhalten.
	 */
	private Database() {
	}

	/**
	 * Gibt eine Instanz der Database-Klasse zurueck. Das hier verwendete
	 * Entwurfsmuster nennt sich "Singleton". Es stellt sicher, dass stets nur eine
	 * einzige Instanz der Database-Klasse existiert.
	 * 
	 * @return Instanz der Database-Klasse
	 * @throws SQLException wenn ein Fehler bei der Herstellung der
	 *                      Datenbankverbindung auftritt
	 */
	public static Database getInstance() throws SQLException {
		if (instance == null) {
			instance = new Database();
			instance.connect();
		}
		return instance;
	}

	/**
	 * Stellt die Verbindung zur Datenbank her. WICHTIG: Die Datei "lernkarten.db"
	 * muss auf oberster Ebene in Ihrem Projekt liegen (neben "src" und "lib").
	 * 
	 * @throws SQLException wenn ein Fehler bei der Herstellung der
	 *                      Datenbankverbindung auftritt
	 */
	private void connect() throws SQLException {
		conn = DriverManager.getConnection("jdbc:sqlite:lernkarten.db");
		try (Statement st = conn.createStatement()) {
			st.execute("PRAGMA foreign_keys = ON");
		}
	}

	/**
	 * Gibt die Datenbankverbindung zurueck.
	 * 
	 * @return Datenbankverbindung
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 * Schließt die Datenbankverbindung.
	 * 
	 * @throws SQLException wenn ein Fehler beim Schließen der Datenbankverbindung
	 *                      auftritt
	 */
	@Override
	public void close() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}
}
