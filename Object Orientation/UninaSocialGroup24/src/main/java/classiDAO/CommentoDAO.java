package classiDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class CommentoDAO {

	private Connection connessioneDB; 
	private LinkedList<Commento> listaCommenti;
	
	
	public CommentoDAO(Connection conn,PostDAO postDAO,UtenteDAO utenteDAO) throws SQLException{
		String query = "SELECT * FROM Commento";
		listaCommenti = new LinkedList<Commento>();
		try(Statement stmt = conn.createStatement()){
			ResultSet res = stmt.executeQuery(query);
			while(res.next()) {
				
				Post post = postDAO.getPostFromArrayListById(res.getInt("IdPostCommentato"));
				Commento commento = getCommentoFromArrayListById(res.getInt("idCommentoRisp"));
				Utente utente = utenteDAO.getUtenteFromArrayListById(res.getInt("IdUtente"));
				
				listaCommenti.add(new Commento (res.getInt("idCommento"),res.getString("testoCommento"),res.getInt("numeroLike"),
												commento,post,utente));
			}
			connessioneDB = conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public Commento getCommentoFromArrayListById(int idCommento) {
	    for (Commento commento : listaCommenti) {
	        if (commento.getIdCommento() == idCommento) {
	            return commento;
	        }
	    }
	   
	    return null;
	}

	public LinkedList<Commento> getListaCommentiPost (Post p ){
		LinkedList<Commento> listaCommentiPost = new LinkedList<Commento>();
		
		for (Commento c : listaCommenti) {
			if (c.getPostRisposto().getIdPost() == p.getIdPost())
				listaCommentiPost.add(c);
		}
		return listaCommentiPost;
	}


}
