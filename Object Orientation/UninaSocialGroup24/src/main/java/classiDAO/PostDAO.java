package classiDAO;

import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import controller.Controller;

public class PostDAO {
	private Connection connessioneDB; 
	private LinkedList<Post> listaPost;
	
	
	public PostDAO(Connection conn,UtenteDAO utenteDAO,GruppoDAO gruppoDAO) throws SQLException{
		String query="SELECT * FROM Post";
		listaPost = new LinkedList<Post>();
		
		try(Statement stmt=conn.createStatement()){
			ResultSet res=stmt.executeQuery(query);
			
			while(res.next()) {
	            java.sql.Date sqlDate = res.getDate("dataPubblicazione");
	            LocalDate dataPubblicazione = sqlDate.toLocalDate();
	            
	            java.sql.Time sqlTime = res.getTime("oraPubblicazione");
	            LocalTime oraPubblicazione = sqlTime.toLocalTime();
	            
	            Utente utente = utenteDAO.getUtenteFromArrayListById(res.getInt("IdUtente"));
                Gruppo gruppo = gruppoDAO.getGruppoFromArrayListById(res.getInt("IdGruppo"));
               
               
                       
				listaPost.add(new Post(res.getInt("idPost"),res.getString("testo"),res.getString("urlImmagine"),dataPubblicazione,oraPubblicazione,
									   res.getInt("NumeroLike"),res.getInt("NumeroCommenti"),utente,gruppo));  				
			}
			connessioneDB=conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
		
	
	public void insertNuovoPost(String testo, int IdUtente, int IdGruppo) throws SQLException{
		String query = "INSERT INTO Post (testo,idUtente,idGruppo) VALUES (?,?,?)";
		try(PreparedStatement pstmt = connessioneDB.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){
			
			pstmt.setString(1,testo);
			pstmt.setInt(2, IdUtente);
			pstmt.setInt(3, IdGruppo);
			
			int postInserito = pstmt.executeUpdate();	
			int idPost;
			
			if (postInserito > 0) {

	            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    idPost = generatedKeys.getInt(1);
	                    System.out.println(idPost);
	                    pstmt.close();
	        			try(Statement stmt = connessioneDB.createStatement()){
	        				String query2 = "SELECT * FROM Post WHERE idPost = "+idPost;
	    
	        				ResultSet res = stmt.executeQuery(query2);
	        				
	        				if (res.next()) {
	        					java.sql.Date sqlDate = res.getDate("dataPubblicazione");
	        		            LocalDate dataPubblicazione = sqlDate.toLocalDate();
	        		            
	        		            java.sql.Time sqlTime = res.getTime("oraPubblicazione");
	        		            LocalTime oraPubblicazione = sqlTime.toLocalTime();
	        					
	        					Utente utente = Controller.utenteDAO.getUtenteFromArrayListById(IdUtente);
	        					Gruppo gruppo = Controller.gruppoDAO.getGruppoFromArrayListById(IdGruppo);
	        					
	        					listaPost.add(new Post(res.getInt("idPost"),res.getString("testo"),res.getString("urlImmagine"),dataPubblicazione,oraPubblicazione,
										   res.getInt("NumeroLike"),res.getInt("NumeroCommenti"),utente,gruppo));
	        				}
	        			}
	                }
	            }
	            
	        }
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
 
	public void deletePostById(Post p) {
		String query = "DELETE FROM Post WHERE idPost = ? ";
		try(PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
			
			pstmt.setInt(1,p.getIdPost());
			pstmt.executeUpdate();
			
			listaPost.remove(p);
			
			pstmt.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
		
	public void updateTestoPost(String modificaTesto,Post p) {
		String query = "UPDATE Testo FROM Post WHERE idPost = ? ";
		try(PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
			
			pstmt.setInt(1,p.getIdPost());
			pstmt.execute(query);
			
			for(Post currPost : listaPost) {
		            if(currPost.getIdPost() == p.getIdPost()) {
		                currPost.setTesto(modificaTesto);
		                break;
		            }
		    }
			pstmt.close();
		}
		catch(SQLException e ) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public float getMediaPostInUnMese(int mese, int anno, Gruppo g) {
	    float media = 0.0f;
	    int numeroGiorniMese = YearMonth.of(anno, mese).lengthOfMonth();
	    String query = "SELECT COUNT(*) AS numeroPostMese " +
	                   "FROM Post P " +
	                   "WHERE EXTRACT(YEAR FROM P.DataPubblicazione) = ? " +
	                   "AND EXTRACT(MONTH FROM P.DataPubblicazione) = ? " +
	                   "AND P.IdGruppo = ?";
	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
	        pstmt.setInt(1, anno);
	        pstmt.setInt(2, mese);
	        pstmt.setInt(3, g.getIdGruppo());

	        ResultSet res = pstmt.executeQuery();

	        if (res.next()) {
	            int numeroPostMese = res.getInt("numeroPostMese");
	            media = (float) numeroPostMese / numeroGiorniMese;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return media;
	}

	
	
	
	
	
	
	public int getIDPostConPiuLikeGruppoInUnMese(int mese, int anno, Gruppo g) {
	    String query = "SELECT P.IdPost " +
	                   "FROM Post P " +
	                   "WHERE EXTRACT(YEAR FROM P.DataPubblicazione) = ? " +
	                   "AND EXTRACT(MONTH FROM P.DataPubblicazione) = ? " +
	                   "AND P.IdGruppo = ? " +
	                   "ORDER BY P.NumeroLike DESC " +
	                   "LIMIT 1";

	    int idPostConPiuLike = -1; 

	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
	        pstmt.setInt(1, anno);
	        pstmt.setInt(2, mese);
	        pstmt.setInt(3, g.getIdGruppo());

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            idPostConPiuLike = rs.getInt("IdPost");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return idPostConPiuLike;
	}
	
	
	

	public int getIDPostConMenoLikeGruppoInUnMese(int mese, int anno, Gruppo g) {
	    String query = "SELECT P.IdPost " +
	                   "FROM Post P " +
	                   "WHERE EXTRACT(YEAR FROM P.DataPubblicazione) = ? " +
	                   "AND EXTRACT(MONTH FROM P.DataPubblicazione) = ? " +
	                   "AND P.IdGruppo = ? " +
	                   "ORDER BY P.NumeroLike ASC " +
	                   "LIMIT 1";

	    int idPostConMenoLike = -1; 

	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
	        pstmt.setInt(1, anno);
	        pstmt.setInt(2, mese);
	        pstmt.setInt(3, g.getIdGruppo());

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            idPostConMenoLike = rs.getInt("IdPost");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return idPostConMenoLike;
	}

	
	
	public int getIDPostConPiuCommentiGruppoInUnMese(int mese, int anno, Gruppo g) {
	    String query = "SELECT P.IdPost " +
	                   "FROM Post P " +
	                   "WHERE EXTRACT(YEAR FROM P.DataPubblicazione) = ? " +
	                   "AND EXTRACT(MONTH FROM P.DataPubblicazione) = ? " +
	                   "AND P.IdGruppo = ? " +
	                   "ORDER BY P.NumeroCommenti DESC " +
	                   "LIMIT 1";

	    int idPostConPiuCommenti = -1; 

	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
	        pstmt.setInt(1, anno);
	        pstmt.setInt(2, mese);
	        pstmt.setInt(3, g.getIdGruppo());

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            idPostConPiuCommenti = rs.getInt("IdPost");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return idPostConPiuCommenti;
	}

	
	
	
	public int getIDPostConMenoCommentiGruppoInUnMese(int mese, int anno, Gruppo g) {
	    String query = "SELECT P.IdPost " +
	                   "FROM Post P " +
	                   "WHERE EXTRACT(YEAR FROM P.DataPubblicazione) = ? " +
	                   "AND EXTRACT(MONTH FROM P.DataPubblicazione) = ? " +
	                   "AND P.IdGruppo = ? " +
	                   "ORDER BY P.NumeroCommenti ASC " +
	                   "LIMIT 1";

	    int idPostConMenoCommenti = -1; 

	    try (PreparedStatement pstmt = connessioneDB.prepareStatement(query)) {
	        pstmt.setInt(1, anno);
	        pstmt.setInt(2, mese);
	        pstmt.setInt(3, g.getIdGruppo());

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            idPostConMenoCommenti = rs.getInt("IdPost");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return idPostConMenoCommenti;
	}


		
	public Post getPostFromArrayListById(int idPost) {
	    for (Post post : listaPost) {
	        if (post.getIdPost() == idPost) {
	            return post;
	        }
	    }
	    return null;
	}
	
	
	public LinkedList<Post> getListaPost (){
		return listaPost;
	}

	
	
}

					
					
									