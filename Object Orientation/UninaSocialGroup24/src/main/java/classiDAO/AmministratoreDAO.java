package classiDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class AmministratoreDAO {
	
	private Connection connessioneDB; 
	private LinkedList<Amministratore> listaAmministratori;

	
	public AmministratoreDAO(Connection conn, GruppoDAO gruppoDAO, UtenteDAO utenteDAO) throws SQLException{
        String query = "SELECT * FROM Amministratore";
        listaAmministratori = new LinkedList<Amministratore>();

        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
        	
            while (rs.next()) {
                int idAmministratore = rs.getInt("idAmministratore");
                int idGruppo = rs.getInt("idGruppo");
                int idUtente = rs.getInt("IdUtente");

                Gruppo gruppoAmministrato = gruppoDAO.getGruppoFromArrayListById(idGruppo);
          
                
				Utente utente = utenteDAO.getUtenteFromArrayListById(idUtente);
                
                              
                listaAmministratori.add(new Amministratore(utente.getIdUtente(),utente.getNomeUtente(),utente.getCognomeUtente(),
					    							 	   utente.getEmail(),utente.getNickname(),utente.getPassword(),utente.getBiografia(),
					    								   utente.getUrlFotoProfilo(),idAmministratore, gruppoAmministrato));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
    
	
	public void insertNuovoAmministratore(Amministratore amministratore,CreatoreGruppoDAO creatoreDAO) {
	    String query = "INSERT INTO Amministratore (idCreatore , idUtente, idGruppo) VALUES (?, ? ,?)";
	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
	    	
	    	CreatoreGruppo CreatoreGruppo = creatoreDAO.getCreatoreGruppoFromArrayListByIdGruppo(amministratore.getIdGruppoAmministrato());
	    	
	    	pstmt.setInt(1, CreatoreGruppo.getIdCreatoreGruppo());
	        pstmt.setInt(2, amministratore.getIdUtente());
	        pstmt.setInt(3, amministratore.getIdGruppoAmministrato());
	        pstmt.executeUpdate();
	        listaAmministratori.add(amministratore); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}



	public void deleteAmministratoreByNickname(Amministratore amministratore) {
	    String query = "DELETE FROM Amministratore WHERE idAmministratore IN (SELECT idAmministratore FROM Utente WHERE Nickname = ?)";
	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
	        pstmt.setString(1, amministratore.getNickname());
	        pstmt.executeUpdate();
	        listaAmministratori.remove(amministratore);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
		
	public Amministratore getAmministratoreFromArrayListById(int idAmministratore) {
	    for (Amministratore admin : listaAmministratori) {
	        if (admin.getIdAmministratore() == idAmministratore) {
	            return admin;
	        }
	    }
	    return null;
	}

	

}
