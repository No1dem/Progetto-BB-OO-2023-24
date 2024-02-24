package ClassiDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class NotificaDAO {

	private Connection connessioneDB; 
	private LinkedList<Notifica> listaNotifiche;
	
	// inizializzo la lista con tutte le notifiche presenti nella base di dati
	
	public void listaNotificheDao(Connection conn) {
		String query="SELECT * FROM Notifica";
		listaNotifiche = new LinkedList<Notifica>();
		
		try(Statement stmt=conn.createStatement()){
			ResultSet res=stmt.executeQuery(query);
			while(res.next()) {
				listaNotifiche.add(new Notifica(res.getInt("IdNotifica"),  res.getDate("dataInvio").toLocalDate(), res.getTime("oraInvio").toLocalTime(), res.getString("testoNotifica"),  EnumTipoNotifica.valueOf(res.getString("tipoNotifica"))));  				
			}
			connessioneDB=conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// INSERT
	
	public void insertNuovaNotifica(Notifica notifica) {
	    String query = "INSERT INTO Notifica (IdNotifica, DataInvio, OraInvio, TestoNotifica, TipoNotifica) VALUES (?, ?, ?, ?, ?)";
	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
	        pstmt.setInt(1, notifica.getIdNotifica());
	        pstmt.setDate(2, java.sql.Date.valueOf(notifica.getDataInvio()));
	        pstmt.setTime(3, java.sql.Time.valueOf(notifica.getOraInvio()));
	        pstmt.setString(4, notifica.getTestoNotifica());
	        pstmt.setString(5, notifica.getTipoNotifica().toString());

	        pstmt.executeUpdate();
	        pstmt.close();
	        listaNotifiche.add(notifica);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
}
