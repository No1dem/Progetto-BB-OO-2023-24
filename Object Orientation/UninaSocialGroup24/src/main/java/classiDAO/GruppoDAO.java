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
	private LinkedList<Gruppo> listaGruppo;
		
	
	public void listaGruppiDao(Connection conn) {
			String query="SELECT * FROM Gruppo";
			listaGruppo = new LinkedList<Gruppo>();
			try(Statement stmt=conn.createStatement()){
				ResultSet res=stmt.executeQuery(query);
				while(res.next()) {
					listaGruppo.add(new Gruppo (res.getInt("IdGruppo"),res.getString("nomeGruppo"),res.getString("tagGruppo"),res.getString("descrizioneGruppo"),
							                    res.getInt("numeroIscritti")));
                }
			    connessioneDB=conn;
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
    		
    		listaGruppo.add(g);
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
	    	}
	    	catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	 }
     	
	 
	 
	 public int recuperaNumeroIscritti(int id) {
	       int numIscritti = 0;
	       String query ="SELECT numeroIscritti FROM Gruppo WHERE idGruppo = ?";
	       try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
	    	   pstmt.setInt(1,id);
	    	   
	    	   ResultSet res = pstmt.executeQuery();
	    	   if(res.next()) {
	    		   numIscritti = res.getInt("numIscritti");
	           }
	       }
	       catch(SQLException e) {
				e.printStackTrace();
			}
	       return numIscritti;
	 }
	 
	 
	 
     public List<Gruppo> cercaGruppiPerNomeOtag(String Ricerca) {
	        List<Gruppo> risultati = new LinkedList<>();
	        String query = "SELECT * FROM Gruppo WHERE nomeGruppo LIKE % ? % OR tagGruppo LIKE % ? %";
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

	                Gruppo gruppo = new Gruppo(id, nomeGruppo, tagGruppo, descrizioneGruppo, numeroIscritti);
	                risultati.add(gruppo);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return risultati;
	 }

     
     
	 public Gruppo getGruppoFromArrayListById(int id) {
		for (Gruppo g : listaGruppo) {  
            if (g.getIdGruppo() == id){
                return g;
            }
        }
		return null;
	 }
	 
	 
	 
}