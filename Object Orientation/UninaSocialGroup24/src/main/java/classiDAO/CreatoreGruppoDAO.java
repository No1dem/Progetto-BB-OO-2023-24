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
	
	public void listaUtenteDao(Connection conn) {
		String query="SELECT * FROM CreatoreGruppo";
		listaCreatoriGruppi = new LinkedList<CreatoreGruppo>();
		try(Statement stmt=conn.createStatement()){
			ResultSet res=stmt.executeQuery(query);
			while(res.next()) {
				int idCreatore = res.getInt("IdCreatore");
			    int idUtente = res.getInt("IdUtente");
			    int idGruppo = res.getInt("IdGruppo");

			    UtenteDAO utenteDAO = new UtenteDAO();
			    Utente utente = utenteDAO.getUtenteFromArrayListById(idUtente);
			    
			    
			    GruppoDAO gruppoDAO = new GruppoDAO();
			    Gruppo gruppo = gruppoDAO.getGruppoFromArrayListById(idGruppo);
			    
			    AmministratoreDAO amministratoreDAO = new AmministratoreDAO();
			    Amministratore amministratore = amministratoreDAO.getAmministratoreFromArrayListById(idGruppo);
			    
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
	
	public void deleteCreatoreGruppo(CreatoreGruppo cg) {
		String query = "DELETE FROM CreatoreGruppo WHERE idCreatore = ? ";
		try(PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
			
			pstmt.setInt(1,cg.getIdCreatoreGruppo());
			pstmt.execute(query);
			
			listaCreatoriGruppi.remove(cg);
			
			GruppoDAO gruppoDAO = new GruppoDAO();
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
	
	
	public CreatoreGruppo getCreatoreGruppoFromArrayListById(int id) {
		for (CreatoreGruppo  cg : listaCreatoriGruppi) {  
            if (cg.getIdCreatoreGruppo()==id){
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
	
}
