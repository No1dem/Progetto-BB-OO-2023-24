package classiDAO;

import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
		String query="SELECT * FROM Post ORDER BY dataPubblicazione,oraPubblicazione ";
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
	
	
	

	public float getMediaPostInUnMese(LocalDate data,Gruppo g) {
		float media = 0.0f;
		int numeroGiorniMese = data.lengthOfMonth();
		String query = "SELECT count(*) AS numeroPostMese"
					 + "FROM Post P "
					 + "WHERE EXTRACT(YEAR FROM P.DataPubblicazione) = ? "
					 + "AND EXTRACT(MONTH FROM P.DataPubblicazione) = ? "
					 + "AND P.IdGruppo = ?";
		try(PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
			
			pstmt.setInt(1,data.getYear());
			pstmt.setInt(2,data.getMonthValue());
			pstmt.setInt(3,g.getIdGruppo());
			
			ResultSet res = pstmt.executeQuery();
			
			if(res.next()) {
				int numeroPostMese = res.getInt("numeroPostMese");
				media=(float) numeroPostMese/numeroGiorniMese;
			}
			
			pstmt.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return media;
			
	}
	
	
	
	public Post getPostConPiuLikeGruppoInUnMese(LocalDate dataRicerca,Gruppo g,UtenteDAO utenteDAO) {   
		String query = "SELECT * "
					 + "FROM Post P "
					 + "WHERE EXTRACT(YEAR FROM P.DataPubblicazione) = ? "
					 + "AND EXTRACT (MONTH FROM P.DataPubblicazione) = ? "
					 + "AND P.IdGruppo = ? "
					 + "ORDER BY P.NumeroLike DESC "
					 + "LIMIT 1";
	    Post postConPiuLike = null;
		try(PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
			
			pstmt.setInt(1,dataRicerca.getYear());
			pstmt.setInt(2,dataRicerca.getMonthValue());
			pstmt.setInt(3,g.getIdGruppo());
			
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				
				Date dataPost=res.getDate("DataPubblicazione");
				LocalDate localDataPost=dataPost.toLocalDate();
				Time oraPost=res.getTime("OraPubblicazione");
				LocalTime localOraPost=oraPost.toLocalTime();
				
				Utente utente = utenteDAO.getUtenteFromArrayListById(res.getInt("IdUtente"));                
                
				postConPiuLike = new Post(res.getInt("IdPost"),res.getString("Testo"),res.getString("URLImmagine"),localDataPost,localOraPost,
								          res.getInt("NumeroLike"),res.getInt("NumeroCommenti"),utente,g);
		   }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return postConPiuLike;
	}
	
	

	public Post getPostConMenoLikeGruppoInUnMese(LocalDate dataRicerca,Gruppo g,UtenteDAO utenteDAO) {
		String query = "SELECT * "
					 + "FROM Post P "
					 + "WHERE EXTRACT(YEAR FROM P.DataPubblicazione) = ? "
					 + "AND EXTRACT (MONTH FROM P.DataPubblicazione) = ? "
					 + "AND P.IdGruppo = ? "
					 + "ORDER BY P.NumeroLike ASC "
					 + "LIMIT 1";
	    Post postConMenoLike = null;
		try(PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
			
			pstmt.setInt(1,dataRicerca.getYear());
			pstmt.setInt(2,dataRicerca.getMonthValue());
			pstmt.setInt(3,g.getIdGruppo());
			
			ResultSet res=pstmt.executeQuery();
			if(res.next()) {
				
				Date dataPost = res.getDate("DataPubblicazione");
				LocalDate localDataPost = dataPost.toLocalDate();
				Time oraPost = res.getTime("OraPubblicazione");
				LocalTime localOraPost = oraPost.toLocalTime();
				
				Utente utente = utenteDAO.getUtenteFromArrayListById(res.getInt("IdUtente"));
                
                
                postConMenoLike = new Post(res.getInt("IdPost"),res.getString("Testo"),res.getString("URLImmagine"),localDataPost,localOraPost,
				          				   res.getInt("NumeroLike"),res.getInt("NumeroCommenti"),utente,g);
           
		   }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return postConMenoLike;
	}
	
	
	
	
	public Post getPostConPiuCommentiGruppoInUnMese(LocalDate dataRicerca,Gruppo g,UtenteDAO utenteDAO) {
		String query = "SELECT * "
					 + "FROM Post P "
					 + "WHERE EXTRACT(YEAR FROM P.DataPubblicazione) = ? "
					 + "AND EXTRACT (MONTH FROM P.DataPubblicazione) = ? "
					 + "AND P.IdGruppo = ? "
					 + "ORDER BY P.NumeroCommenti DESC "
					 + "LIMIT 1";
	    Post postConPiuCommenti = null;
		try(PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
			
			pstmt.setInt(1,dataRicerca.getYear());
			pstmt.setInt(2,dataRicerca.getMonthValue());
			pstmt.setInt(3,g.getIdGruppo());
			
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				
				Date dataPost = res.getDate("DataPubblicazione");
				LocalDate localDataPost = dataPost.toLocalDate();
				Time oraPost = res.getTime("OraPubblicazione");
				LocalTime localOraPost = oraPost.toLocalTime();
				Utente utente = utenteDAO.getUtenteFromArrayListById(res.getInt("IdUtente"));
                
                
                postConPiuCommenti = new Post(res.getInt("IdPost"),res.getString("Testo"),res.getString("URLImmagine"),localDataPost,localOraPost,
			          	  res.getInt("NumeroLike"),res.getInt("NumeroCommenti"),utente,g);
		   }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return postConPiuCommenti;
	}
	
	
	
	public Post getPostConMenoCommentiGruppoInUnMese(LocalDate dataRicerca,Gruppo g,UtenteDAO utenteDAO) {
		String query = "SELECT * "
					 + "FROM Post P "
					 + "WHERE EXTRACT(YEAR FROM P.DataPubblicazione) = ? "
					 + "AND EXTRACT (MONTH FROM P.DataPubblicazione) = ? "
					 + "AND P.IdGruppo = ? "
					 + "ORDER BY P.NumeroCommenti ASC "
					 + "LIMIT 1";
	    Post postConMenoCommenti = null;
		try(PreparedStatement pstmt = connessioneDB.prepareStatement(query)){
			
			pstmt.setInt(1,dataRicerca.getYear());
			pstmt.setInt(2,dataRicerca.getMonthValue());
			pstmt.setInt(3,g.getIdGruppo());
			
			ResultSet res = pstmt.executeQuery();
			if(res.next()) {
				
				Date dataPost = res.getDate("DataPubblicazione");
				LocalDate localDataPost = dataPost.toLocalDate();
				Time oraPost = res.getTime("OraPubblicazione");
				LocalTime localOraPost = oraPost.toLocalTime();
				Utente utente = utenteDAO.getUtenteFromArrayListById(res.getInt("IdUtente"));

                
                postConMenoCommenti = new Post(res.getInt("IdPost"),res.getString("Testo"),res.getString("URLImmagine"),localDataPost,localOraPost,
				          					   res.getInt("NumeroLike"),res.getInt("NumeroCommenti"),utente,g);
		   }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return postConMenoCommenti;
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

					
					
									