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
	
	public RichiestaDiAccessoDAO (Connection conn,UtenteDAO utenteDAO,CreatoreGruppoDAO creatoreDAO ,
			GruppoDAO gruppoDAO,NotificaDAO notificaDAO) throws SQLException {
		String query="SELECT * FROM RichiestaDiAccesso";
		connessioneDB=conn;
		listaRichiesteDiAccesso = new LinkedList<RichiestaDiAccesso>();
		
		try(Statement stmt=conn.createStatement()){
			ResultSet res=stmt.executeQuery(query);
			
			while(res.next()) {
				Utente utenteRichiesta = utenteDAO.getUtenteFromArrayListById(res.getInt("IdUtenteRichiesta"));
				
				CreatoreGruppo creatoreGruppo = creatoreDAO.getCreatoreGruppoFromArrayListByIdCreatore(res.getInt("IdCreatore"));
				
				Gruppo gruppoRichiesta = gruppoDAO.getGruppoFromArrayListById(res.getInt("IdGruppoRichiesta"));
				
				Notifica notificaGenerata = notificaDAO.getNotificaFromArrayListById(res.getInt("IdNotificaGenerata"));
				
				String statoRichiestaString = res.getString("StatoRichiesta");
	            EnumStatiRichiesta statoRichiesta = EnumStatiRichiesta.valueOf(statoRichiestaString);
	            
			    listaRichiesteDiAccesso.add(new RichiestaDiAccesso(res.getInt("IdRichiesta"),statoRichiesta,
											utenteRichiesta,creatoreGruppo,gruppoRichiesta,notificaGenerata));  				
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertRichiestaDiAccesso(RichiestaDiAccesso rds) {
		String query = "INSERT INTO RichiestaDiAccesso (IdUtenteRichiesta,IdCreatore,IdGruppoRichiesta) VALUES (?, ?, ?)";
		
	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
	    	
	        pstmt.setInt(1, rds.getUtenteRichiesta().getIdUtente());
	        pstmt.setInt(2, rds.getCreatoreGruppoDiRichiesta().getIdCreatoreGruppo());
	        pstmt.setInt(3, rds.getGruppoAccesso().getIdGruppo());
	        
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
	
	public RichiestaDiAccesso getRichiestaFromArrayListByNotifica(Notifica notifica) {
	    for (RichiestaDiAccesso richiesta : listaRichiesteDiAccesso) {
	        if (richiesta.getNotificaGenerata().equals(notifica)) {
	            return richiesta;
	        }
	    }
	    return null;
	}

	
	
}
