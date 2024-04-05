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
        connessioneDB = conn;

        
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

	
    
	
	public void insertNuovoAmministratore(Utente utente,Gruppo gruppo,CreatoreGruppoDAO creatoreDAO) {
	    String query = "INSERT INTO Amministratore (idCreatore , idUtente, idGruppo) VALUES (?, ? ,?)";
	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){
	    	
	    	CreatoreGruppo CreatoreGruppo = creatoreDAO.getCreatoreGruppoFromArrayListByIdGruppo(gruppo.getIdGruppo());
	    	
	    	pstmt.setInt(1, CreatoreGruppo.getIdCreatoreGruppo());
	        pstmt.setInt(2, utente.getIdUtente());
	        pstmt.setInt(3, gruppo.getIdGruppo());
	        pstmt.executeUpdate();
	        
	        ResultSet generatedKeys = pstmt.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            int idAmministratore = generatedKeys.getInt(1);
	      
	            listaAmministratori.add(new Amministratore(utente.getIdUtente(),utente.getNomeUtente(),utente.getCognomeUtente(),
					 	   utente.getEmail(),utente.getNickname(),utente.getPassword(),utente.getBiografia(),
						   utente.getUrlFotoProfilo(),idAmministratore, gruppo));
	            
	            stampaLista(listaAmministratori);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}



	public void deleteAmministratore(Utente utente,Gruppo gruppo) {
	    String query = "DELETE FROM Amministratore WHERE idUtente = ? AND idGruppo = ?";
	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
	        pstmt.setInt(1, utente.getIdUtente());
	        pstmt.setInt(2, gruppo.getIdGruppo());
	        pstmt.executeUpdate();
	        
	      
		    Amministratore amministratore = getAmministratoreFromArrayListByIdGruppoIdUtente(gruppo.getIdGruppo(),utente.getIdUtente());
		    listaAmministratori.remove(amministratore);
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
		
	public Amministratore getAmministratoreFromArrayListByIdAmministratore(int idAmministratore) {
	    for (Amministratore admin : listaAmministratori) {
	        if (admin.getIdAmministratore() == idAmministratore) {
	            return admin;
	        }
	    }
	    return null;
	}
	
	public Amministratore getAmministratoreFromArrayListByIdGruppoIdUtente(int idGruppo,int idUtente) {
	    for (Amministratore admin : listaAmministratori) {
	        if (admin.getIdUtente() == idUtente && admin.getIdGruppoAmministrato() == idGruppo) {
	            return admin;
	        }
	    }
	    return null;
	}

	
	public void stampaLista(LinkedList<Amministratore> listaAmministratori) {
	    for (Amministratore admin : listaAmministratori) {
	        System.out.println("ID Amministratore: " + admin.getIdAmministratore());
	        System.out.println("ID Utente: " + admin.getIdUtente());
	        System.out.println("ID Gruppo Amministrato: " + admin.getIdGruppoAmministrato());
	   
	        System.out.println("------------------------------------------");
	    }
	}
	

}
