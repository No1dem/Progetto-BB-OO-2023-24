package classiDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class CreatoreGruppoDAO {
	private Connection connessioneDB; 
	private LinkedList<CreatoreGruppo> listaCreatoriGruppi;
	
	public CreatoreGruppoDAO(Connection conn,GruppoDAO gruppoDAO , AmministratoreDAO amministratoreDAO, UtenteDAO utenteDAO ) throws SQLException {
		String query="SELECT * FROM CreatoreGruppo";
		listaCreatoriGruppi = new LinkedList<CreatoreGruppo>();
		
		try(Statement stmt=conn.createStatement()){
			ResultSet res=stmt.executeQuery(query);
			
			while(res.next()) {
				int idCreatore = res.getInt("IdCreatore");
			    int idUtente = res.getInt("IdUtente");
			    int idGruppo = res.getInt("IdGruppo");

			    Utente utente = utenteDAO.getUtenteFromArrayListById(idUtente);
			    
	
			    Gruppo gruppo = gruppoDAO.getGruppoFromArrayListById(idGruppo);
			    
		
			    Amministratore amministratore = amministratoreDAO.getAmministratoreFromArrayListByIdGruppoIdUtente(idGruppo,idUtente);
			    
			    listaCreatoriGruppi.add(new CreatoreGruppo (utente.getIdUtente(),utente.getNomeUtente(),utente.getCognomeUtente(),
			    										    utente.getEmail(),utente.getNickname(),utente.getPassword(),utente.getBiografia(),
			    										    utente.getUrlFotoProfilo(),amministratore.getIdAmministratore(),gruppo,idCreatore));
			}
			connessioneDB=conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void insertCreatoreGruppo(CreatoreGruppo cg) {
		listaCreatoriGruppi.add(cg);
	}
	
	
	public void deleteCreatoreGruppo(CreatoreGruppo cg) {
		String query = "DELETE FROM CreatoreGruppo WHERE idCreatore = ? ";
		try(PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
			
			pstmt.setInt(1,cg.getIdCreatoreGruppo());
			pstmt.execute(query);
			
			listaCreatoriGruppi.remove(cg);
			
			GruppoDAO gruppoDAO = new GruppoDAO(connessioneDB);
			Gruppo gruppo = gruppoDAO.getGruppoFromArrayListById(cg.getIdGruppoAmministrato());
			
			if (gruppo != null) {
	            gruppoDAO.getListaGruppi().remove(gruppo);
	        }
			
			pstmt.close();
		}
		catch(SQLException e ) {
			e.printStackTrace();
		}
	}
	
	
	public CreatoreGruppo getCreatoreGruppoFromArrayListByIdCreatore(int idCreatore) {
		for (CreatoreGruppo  cg : listaCreatoriGruppi) {  
            if (cg.getIdCreatoreGruppo()==idCreatore){
                return cg;
            }
        }
		return null;
	}



	public  CreatoreGruppo getCreatoreGruppoFromArrayListByIdGruppo(int idGruppo) {
		for (CreatoreGruppo creatoreGruppo : listaCreatoriGruppi) {
		        if (creatoreGruppo.getIdGruppoAmministrato() == idGruppo) {
		            return creatoreGruppo;
		        }
		    }
		return null;
	}
	
	public LinkedList<Gruppo> getListaGruppiCreatiFromArrayListByIdUtente (int idUtente,GruppoDAO gruppoDAO) {
		LinkedList<Gruppo> listaGruppiCreati = new LinkedList<>();
		
		for (CreatoreGruppo  cg : listaCreatoriGruppi) {  
            if (cg.getIdUtente()== idUtente){
                Gruppo gruppo = gruppoDAO.getGruppoFromArrayListById(cg.getIdGruppoAmministrato());
                listaGruppiCreati.add(gruppo);
            }
        }
		return listaGruppiCreati;
	}
	
	public LinkedList<CreatoreGruppo> getListaCreatori() {
		return listaCreatoriGruppi;
	}
	
	public void stampaLista(LinkedList<CreatoreGruppo> listaCreatori) {
	    for (CreatoreGruppo cg : listaCreatori) {
	        System.out.println("ID Creatore Gruppo: " + cg.getIdCreatoreGruppo());
	        System.out.println("ID Utente: " + cg.getIdUtente());
	        System.out.println("ID Gruppo Amministrato: " + cg.getIdGruppoAmministrato());
	        System.out.println("------------------------------------------");
	    }
	}
	
}
