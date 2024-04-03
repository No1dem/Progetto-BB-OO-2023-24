package classiDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;


public class UtenteDAO {
	private Connection connessioneDB; 
	private LinkedList<Utente> listaUtenti;
	
	
	public UtenteDAO(Connection conn) throws SQLException {
		String query="SELECT * FROM Utente";
		listaUtenti = new LinkedList<Utente>();
		
		
		try(Statement stmt=conn.createStatement()){
			ResultSet res=stmt.executeQuery(query);
			
			while(res.next()) {
				listaUtenti.add(new Utente(res.getInt("IdUtente"),res.getString("NomeUtente"),res.getString("CognomeUtente"),res.getString("Email"),
										   res.getString("Nickname"),res.getString("Password"),res.getString("Biografia"),res.getString("URLFotoProfilo")));  				
			}
			connessioneDB=conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertNuovoUtente(String nomeUtente, String cognomeUtente ,String nickname , 
			String email, String password , String biografia) throws SQLException {
		String query="INSERT INTO Utente(NomeUtente,CognomeUtente, Nickname, Email, Password, Biografia) VALUES(?,?,?,?,?,?)";
		try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
			pstmt.setString(1,nomeUtente);
			pstmt.setString(2,cognomeUtente);
			pstmt.setString(3,nickname);
			pstmt.setString(4,email);
			pstmt.setString(5,password);
			pstmt.setString(6,biografia);
	
			pstmt.executeUpdate();
			
			pstmt.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void deleteUtente(Utente ut) {
		String query="DELETE FROM Utente WHERE Nickname=?";
		try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
			pstmt.setString(1,ut.getNickname());
			pstmt.executeUpdate();		
				
			listaUtenti.remove(ut);
			pstmt.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateBiografiaByNickname(String nickname,String biografia){
		String query="UPDATE FROM Utente SET Biografia='"+biografia+"' WHERE Nickname='"+nickname+"'";
		try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
			pstmt.executeUpdate();
			for (Utente ut : listaUtenti) {  
	            if (ut.getNickname().equals(nickname)){
	                ut.setBiografia(biografia);
	                break;
	            }
	        }
			pstmt.close();
		}	
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateEmailByNickname(String nickname,String email) {
		String query="UPDATE FROM Utente SET Email='"+email+"' WHERE Nickname='"+nickname+"'";
		try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
			pstmt.executeUpdate();
			for (Utente ut : listaUtenti) {  
	            if (ut.getNickname().equals(nickname)){
	                ut.setEmail(email);
	                break;
	            }
	        }
			pstmt.close();
		}	
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateUrlFotoProfiloByNickname(String nickname,String url) {
		String query;
		if (url.length() == 0 ) {
			query="UPDATE Utente SET URLFotoProfilo = null WHERE Nickname='"+nickname+"'";
		}
		else {
			query="UPDATE Utente SET URLFotoProfilo='"+url+"' WHERE Nickname='"+nickname+"'";
		}
		try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
			pstmt.executeUpdate();
			for (Utente ut : listaUtenti) {  
	            if (ut.getNickname().equals(nickname)){
	                ut.setUrlFotoProfilo(url);
	                break;
	            }
	        }
			pstmt.close();
		}	
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void updatePasswordUtenteByNickname(String nickname,String password ) {
		String query="UPDATE Utente SET Password='"+password+"' WHERE Nickname='"+nickname+"'";
		try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
			pstmt.executeUpdate();
			for (Utente ut : listaUtenti) {  
	            if (ut.getNickname().equals(nickname)){
	                ut.setPassword(password);
	                break;
	            }
	        }
			pstmt.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateNicknameByIdUtente(String nickname, int idUtente){
		String query = "UPDATE Utente SET Nickname = '"+nickname+"' WHERE IdUtente ="+idUtente;
		try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
			pstmt.executeUpdate();
			for (Utente ut : listaUtenti) {  
	            if (ut.getIdUtente() == idUtente){
	                ut.setNickname(nickname);
	                break;
	            }
	        }
			pstmt.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public Utente getUtenteFromArrayListByNickname(String nickname) {
		for (Utente ut : listaUtenti) {  
            if (ut.getNickname().equals(nickname)){
                return ut;
            }
        }
		return null;
	}
	
	public Utente getUtenteFromArrayListByEmail(String Email) {
		for (Utente ut : listaUtenti) {  
            if (ut.getEmail().equals(Email)){
                return ut;
            }
        }
		return null;
	}
	
	public Utente getUtenteFromArrayListById(int id) {
		for (Utente ut : listaUtenti) {  
            if (ut.getIdUtente()==id){
                return ut;
            }
        }
		return null;
	}
	
	
	public LinkedList<Utente> getListaUtenti(){
		return listaUtenti;
	}
	
	
	public boolean utenteExistInArrayList(Utente ut) {
		return listaUtenti.contains(ut);
	}
	
	

	
	
}
