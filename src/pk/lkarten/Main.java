package pk.lkarten;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import pk.lkarten.db.Database;
import pk.lkarten.db.LernkartenDao;

public class Main {
	public static void main (String[] arrgs) {
		try (Connection conn = Database.getInstance().getConnection()) {
	        LernkartenDao dao = new LernkartenDao(conn);
	        Lernkartei kartei = new Lernkartei(dao);
	        
	        Menu menu = new Menu(kartei);
	        menu.zeigeMenue();
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "DB-Fehler:\n" + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
	      }
	}
		
}