package classiDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


public class NotificaDAO {

	private Connection connessioneDB; 
	private LinkedList<Notifica> listaNotifiche;
	
	
	public NotificaDAO(Connection conn,PostDAO postDAO ,LikeDAO likeDAO, CommentoDAO commentoDAO) throws SQLException {
		String query="SELECT * FROM Notifica";
		listaNotifiche = new LinkedList<Notifica>();
		
		try(Statement stmt=conn.createStatement()){
			ResultSet res=stmt.executeQuery(query);
			while(res.next()) {
				
//				CommentoDAO commentoDAO = new CommentoDAO(conn);
				Commento commento = commentoDAO.getCommentoFromArrayListById(res.getInt("IdNuovoCommento"));
				
//                PostDAO postDAO = new PostDAO(conn);
                Post post = postDAO.getPostFromArrayListById(res.getInt("IdNuovoPost"));
                
//                LikeDAO likeDAO = new LikeDAO(conn); 
                Like like = likeDAO.getLikeFromArrayListByIdLike(res.getInt("IdNuovoLike"));
				
				listaNotifiche.add(new Notifica(res.getInt("IdNotifica"), res.getDate("dataInvio").toLocalDate(), res.getTime("oraInvio").toLocalTime(),
								   				res.getString("testoNotifica"),EnumTipoNotifica.valueOf(res.getString("tipoNotifica")) ,post ,like ,commento ));  				
			}
			connessioneDB=conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void insertNuovaNotifica(Notifica notifica) {
	    String query = "INSERT INTO Notifica (IdNotifica, DataInvio, OraInvio, TestoNotifica, TipoNotifica, IdPost, IdLike, IdCommento) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
	        pstmt.setInt(1, notifica.getIdNotifica());
	        pstmt.setDate(2, java.sql.Date.valueOf(notifica.getDataInvio()));
	        pstmt.setTime(3, java.sql.Time.valueOf(notifica.getOraInvio()));
	        pstmt.setString(4, notifica.getTestoNotifica());
	        pstmt.setString(5, notifica.getTipoNotifica().toString());

	        
	        pstmt.setInt(6, notifica.getPost().getIdPost()); 
	        pstmt.setInt(7, notifica.getLike().getIdLike()); 
	        pstmt.setInt(8, notifica.getCommento().getIdCommento());
	        pstmt.executeUpdate();
	        listaNotifiche.add(notifica); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


		
	public Notifica getNotificaFromArrayListById(int idNotifica) {
	    for (Notifica notifica : listaNotifiche) {
	        if (notifica.getIdNotifica() == idNotifica) {
	            return notifica;
	        }
	    }
	    
	    return null;
	}


	
}
