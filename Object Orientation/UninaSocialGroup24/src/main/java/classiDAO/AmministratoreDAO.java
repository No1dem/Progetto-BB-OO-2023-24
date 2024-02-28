package classiDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class AmministratoreDAO {
	
	private Connection connessioneDB; 
	private LinkedList<Amministratore> listaAmministratori;
	
	
	public void listaAmministratoriDao() {
        String query = "SELECT * FROM Amministratore";
        try (PreparedStatement pstmt = connessioneDB.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int idAmministratore = rs.getInt("idAmministratore");
                int idGruppo = rs.getInt("idGruppo");

                Gruppo gruppoAmministrato = new GruppoDAO().getGruppoFromArrayListById(idGruppo);
          
                
                int idUtente = rs.getInt("IdUtente");
                UtenteDAO utenteDAO = new UtenteDAO();
				Utente utente = utenteDAO.getUtenteFromArrayListById(idUtente);
                
                Amministratore amministratore = new Amministratore(utente.getIdUtente(),utente.getNomeUtente(),utente.getCognomeUtente(),
					    utente.getEmail(),utente.getNickname(),utente.getPassword(),utente.getBiografia(),
					    utente.getUrlFotoProfilo() , idAmministratore, gruppoAmministrato);
                
                listaAmministratori.add(amministratore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
	
	//INSERT        
	
	public void insertNuovoAmministratore(Amministratore amministratore) {
	    String query = "INSERT INTO Amministratore (idCreatore , idUtente, idGruppo) VALUES (?, ? ,?)";
	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
	    	
	    	CreatoreGruppoDAO creatoreDAO = new CreatoreGruppoDAO();
	    	CreatoreGruppo CreatoreGruppo = creatoreDAO.getCreatoreGruppoFromArrayListByIdGruppo(amministratore.getGruppoAmministrato());
	    	
	    	pstmt.setInt(1, CreatoreGruppo.getIdCreatoreGruppo());
	        pstmt.setInt(2, amministratore.getIdUtente());
	        pstmt.setInt(3, amministratore.getGruppoAmministrato());
	        pstmt.executeUpdate();
	        listaAmministratori.add(amministratore); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	
	//DELETE 
	
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
