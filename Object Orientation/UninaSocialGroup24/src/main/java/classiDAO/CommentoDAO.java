package classiDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class CommentoDAO {

	private Connection connessioneDB; 
	private LinkedList<Commento> listaCommenti;
	
	
	
	
	public void ListaCommentiDAO(Connection conn) {
		String query = "SELECT * FROM Commento";
		listaCommenti = new LinkedList<Commento>();
		try(Statement stmt = conn.createStatement()){
			ResultSet res = stmt.executeQuery(query);
			while(res.next()) {
				listaCommenti.add(new Commento (res.getInt("idCommento"),res.getString("testoCommento"),res.getInt("numeroLike")));
			}
			connessioneDB = conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
		
	// dato in input un idCommento restituisce l'oggetto commento se presente 
	public Commento getCommentoFromArrayListById(int idCommento) {
	    for (Commento commento : listaCommenti) {
	        if (commento.getIdCommento() == idCommento) {
	            return commento;
	        }
	    }
	   
	    return null;
	}




}
