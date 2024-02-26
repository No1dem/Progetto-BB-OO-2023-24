package classiDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


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
	
	
	public Gruppo getGruppoFromArrayListById(int id) {
		for (Gruppo g : listaGruppo) {  
            if (g.getIdGruppo() == id){
                return g;
            }
        }
		return null;
	}
}
