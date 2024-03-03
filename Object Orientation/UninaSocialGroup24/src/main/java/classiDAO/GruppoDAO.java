package classiDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class GruppoDAO {
	private Connection connessioneDB;
	private LinkedList<Gruppo> listaGruppi;
		
	
	public GruppoDAO(Connection conn) throws SQLException {
			String query="SELECT * FROM Gruppo";
			connessioneDB=conn;
			listaGruppi = new LinkedList<Gruppo>();
			
			try(Statement stmt=conn.createStatement()){
				ResultSet res=stmt.executeQuery(query);
				while(res.next()) {
					listaGruppi.add(new Gruppo (res.getInt("IdGruppo"),res.getString("nomeGruppo"),res.getString("tagGruppo"),res.getString("descrizioneGruppo"),
							                    res.getInt("numeroIscritti"),getIscrittiGruppoById(res.getInt("IdGruppo"))));
                }
			}
			catch(SQLException e) {
				e.printStackTrace();
			}	
	}
	
	
	
	public void insertNuovoGruppo(Gruppo g) {
    	String query = "INSERT INTO Gruppo (nomeGruppo, tagGruppo, descrizioneGruppo, numeroIscritti) VALUES (?,?,?,?)";
    	try(PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
    		pstmt.setString(1, g.getNomeGruppo());
    		pstmt.setString(2, g.getTagGruppo());
    		pstmt.setString(3, g.getDescrizioneGruppo());
    		pstmt.setInt(4, g.getNumeroIscritti());
    		
    		pstmt.executeUpdate();
    		listaGruppi.add(g);
    		pstmt.close();
    	}
    	catch(SQLException e) {
			e.printStackTrace();
		}
     }
	 
	 
	 
	 public void deleteGruppoById(Gruppo g) {
	    	String query = "DELETE FROM Gruppo WHERE idGruppo = ?";
	    	try(PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
	    		pstmt.setInt(1, g.getIdGruppo());
	    		
	    		pstmt.executeUpdate();
	    	}
	    	catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	 }
	 
	 
	 
	 public void updateDescrizioneGruppo(String descrizioneModificata, Gruppo g) {
		 String query = "UPDATE Gruppo SET descrizioneGruppo = ? WHERE idGruppo = ?";
		 try(PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
			 pstmt.setString(1, descrizioneModificata);
			 pstmt.setInt(2, g.getIdGruppo());
			 
			 pstmt.executeUpdate();
			 
			 for(Gruppo CurrGruppo : listaGruppi) {
				 if (CurrGruppo.getIdGruppo() == g.getIdGruppo()) {
		                CurrGruppo.setDescrizioneGruppo(descrizioneModificata);
		         }
			 }
		 }
		 catch(SQLException e) {
			 e.printStackTrace();
		 }
	 }
	 
	 public void updateTagGruppo(String tagModificato, Gruppo g) {
		 String query = "UPDATE Gruppo SET TagGruppo = '?' WHERE idGruppo = ?";
		 try(PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
			 pstmt.setString(1, tagModificato);
			 pstmt.setInt(2, g.getIdGruppo());
			 
			 pstmt.executeUpdate();
			 
			 for(Gruppo CurrGruppo : listaGruppi) {
				 if (CurrGruppo.getIdGruppo() == g.getIdGruppo()) {
		                CurrGruppo.setDescrizioneGruppo(tagModificato);
		         }
			 }
		 }
		 catch(SQLException e) {
			 e.printStackTrace();
		 }
	 }
     	
	 
	 
	 public int recuperaNumeroIscrittiById(int id) {
	       int numIscritti = 0;
	       String query ="SELECT numeroIscritti FROM Gruppo WHERE idGruppo = ?";
	       try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
	    	   pstmt.setInt(1,id);
	    	   
	    	   ResultSet res = pstmt.executeQuery();
	    	   if(res.next()) {
	    		   numIscritti = res.getInt("numeroIscritti");
	           }
	       }
	       catch(SQLException e) {
				e.printStackTrace();
			}
	       return numIscritti;
	 }
	 
	 
	 
     public List<Gruppo> cercaGruppiPerNomeOTag(String Ricerca) {
	        List<Gruppo> risultati = new LinkedList<>();
	        String query = "SELECT * FROM Gruppo WHERE nomeGruppo LIKE '%'|| ? ||'%' OR tagGruppo LIKE '%'|| ? ||'%'";
	        try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
	            pstmt.setString(1, Ricerca);
	            pstmt.setString(2, Ricerca);

	            ResultSet res = pstmt.executeQuery();
	            while (res.next()) {
	                int id = res.getInt("IdGruppo");
	                String nomeGruppo = res.getString("nomeGruppo");
	                String tagGruppo = res.getString("tagGruppo");
	                String descrizioneGruppo = res.getString("descrizioneGruppo");
	                int numeroIscritti = res.getInt("numeroIscritti");
	              
	                
	                Gruppo gruppo = new Gruppo(id, nomeGruppo, tagGruppo, descrizioneGruppo, numeroIscritti,getIscrittiGruppoById(id));
	                risultati.add(gruppo);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return risultati;
	 }

     
     
     public LinkedList<Utente> getIscrittiGruppoById(int idGruppo) {
    	    String query = "SELECT idUtente FROM Iscrizione WHERE idGruppo = ?";
    	    LinkedList<Utente> listaUtentiIscritti = new LinkedList<>();
    	   
    	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
    	        pstmt.setInt(1, idGruppo);
    	        ResultSet rs = pstmt.executeQuery();

    	        while (rs.next()) {
    	            int idUtente = rs.getInt("idUtente");
    	   
    	            UtenteDAO utenteDAO = new UtenteDAO(connessioneDB);
    	            Utente utente = utenteDAO.getUtenteFromArrayListById(idUtente);
    	            listaUtentiIscritti.add(utente);
    	        }

    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	    
    	    return listaUtentiIscritti;
    	}

     
     public void deleteUtenteDaGruppoById(Gruppo g,int idUtente) {
    	 String query = "DELETE FROM Iscrizione WHERE IdGruppo = ? AND IdUtente = "+idUtente;
    	 try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
    		 
    		 pstmt.setInt(1,g.getIdGruppo());
    		 pstmt.executeUpdate();
    		 
    		 g.removeUtenteDaListaIscrittiById(idUtente);
    		 
    		 pstmt.close();
    	 }
    	 catch(SQLException e) {
    		 e.printStackTrace();
    	 }
     }
       
     public Gruppo getGruppoFromArrayListById(int id) {
		for (Gruppo g : listaGruppi) {  
            if (g.getIdGruppo() == id){
                return g;
            }
        }
		return null;
	 }
	 
	 public LinkedList<Gruppo> getListaGruppi(){
		 return listaGruppi;
	 }
	 
}