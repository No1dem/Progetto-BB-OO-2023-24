package classiDAO;

import java.sql.Connection;
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
			    Amministratore amministratore = amministratoreDAO.getAmministratoreFromArrayListById();
			    
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
	
	
	
	public CreatoreGruppo getCreatoreGruppoFromArrayListById(int id) {
		for (CreatoreGruppo  cg : listaCreatoriGruppi) {  
            if (cg.getIdCreatoreGruppo()==id){
                return cg;
            }
        }
		return null;
	}

}
