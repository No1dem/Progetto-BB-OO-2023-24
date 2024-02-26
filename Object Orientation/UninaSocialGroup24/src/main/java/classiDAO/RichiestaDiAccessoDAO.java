package classiDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class RichiestaDiAccessoDAO {
	private Connection connessioneDB;
	private LinkedList<RichiestaDiAccesso> listaRichiesteDiAccesso;
	
	public void listaRichiesteDiAccessoDao (Connection conn) {
		String query="SELECT * FROM RichiestaDiAccesso";
		listaRichiesteDiAccesso = new LinkedList<RichiestaDiAccesso>();
		try(Statement stmt=conn.createStatement()){
			ResultSet res=stmt.executeQuery(query);
			while(res.next()) {
				Utente utenteRichiesta = new UtenteDAO().getUtenteFromArrayListById(res.getInt("IdUtenteRichiesta"));
				CreatoreGruppo creatoreGruppo = new CreatoreGruppoDAO().getCreatoreGruppoFromArrayListById(res.getInt("IdCreatore"));
				Gruppo gruppoRichiesta = new GruppoDAO().getGruppoFromArrayListById(res.getInt("IdGruppoRichiesta"));
				Notifica notificaRichiesta = new NotificaDAO().getNotificaFromArrayListById(res.getInt("IdNotificaGenerata"));
				
				String statoRichiestaString = res.getString("StatoRichiesta");
	            EnumStatiRichiesta statoRichiesta = EnumStatiRichiesta.valueOf(statoRichiestaString);
	            
			    listaRichiesteDiAccesso.add(new RichiestaDiAccesso(res.getInt("IdRichiesta"),statoRichiesta,
											utenteRichiesta,creatoreGruppo,gruppoRichiesta,notificaRichiesta));  				
			}
			connessioneDB=conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertRichiestaDiAccesso(RichiestaDiAccesso rds) {
		String query = "INSERT INTO RichiestaDiAccesso (IdUtenteRichiesta,IdCreatore,IdGruppoRichiesta) VALUES (?, ?, ?)";
	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
	        pstmt.setInt(1, rds.getIdUtenteRichiesta());
	        pstmt.setInt(2, rds.getIdCreatoreGruppo()); 
	        pstmt.setInt(3, rds.getIdGruppoAccesso());
	        
	        pstmt.executeUpdate();
	        
	        listaRichiesteDiAccesso.add(rds); 
	        
	        pstmt.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void updateRichiestaDiAccesso(RichiestaDiAccesso rds,EnumStatiRichiesta statoRichiesta) {
		String query = "UPDATE FROM RichiestaDiAccesso SET StatoRichiesta='?' WHERE IdRichiesta = ?";
	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
	    
	        pstmt.setString(1,statoRichiesta.toString());
	        pstmt.setInt(2,rds.getIdRichiesta());
	        
	        pstmt.executeUpdate();
	        
	        for ( RichiestaDiAccesso currRds : listaRichiesteDiAccesso) {  
	            if (currRds.getIdRichiesta() == rds.getIdRichiesta()){
	                if (statoRichiesta == EnumStatiRichiesta.Rifiutato)
	                	listaRichiesteDiAccesso.remove(rds);
	                else if (statoRichiesta == EnumStatiRichiesta.Accettato)
	                	rds.setStatoRichiesta(statoRichiesta);
	                break;
	            }
	        }
	        pstmt.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
}
