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
	private LinkedList<Utente> listaUtente;
	
	public void listaUtenteDao(Connection conn) {
		String query="SELECT * FROM Utente";
		listaUtente = new LinkedList<Utente>();
		try(Statement stmt=conn.createStatement()){
			ResultSet res=stmt.executeQuery(query);
			while(res.next()) {
				listaUtente.add(new Utente(res.getInt("IdUtente"),res.getString("NomeUtente"),res.getString("CognomeUtente"),res.getString("Nickname"),
										   res.getString("Email"),res.getString("Password"),res.getString("Biografia"),res.getString("URLFotoProfilo")));  				
			}
			connessioneDB=conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertNuovoUtente(Utente ut) {
		String query="INSERT INTO Utente(NomeUtente,CognomeUtente, Nickname, Email, Password, Biografia,UrlFotoProfilo) VALUES(?,?,?,?,?,?,?)";
		try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
			pstmt.setString(1,ut.getNomeUtente());
			pstmt.setString(2,ut.getCognomeUtente());
			pstmt.setString(3,ut.getNickname());
			pstmt.setString(4,ut.getEmail());
			pstmt.setString(5,ut.getPassword());
			pstmt.setString(6,ut.getBiografia());
			pstmt.setString(7,ut.getUrlFotoProfilo());
			pstmt.executeUpdate();
			listaUtente.add(ut);
			pstmt.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUtenteByNickname(Utente ut) {
		String query="DELETE FROM Utente WHERE Nickname='?'";
		try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
			pstmt.setString(1,ut.getNickname());
			pstmt.executeUpdate();		
				
			listaUtente.remove(ut);
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
			for (Utente ut : listaUtente) {  
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
			for (Utente ut : listaUtente) {  
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
		String query="UPDATE FROM Utente SET URLFotoProfilo='"+url+"' WHERE Nickname='"+nickname+"'";
		try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
			pstmt.executeUpdate();
			for (Utente ut : listaUtente) {  
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
		String query="UPDATE FROM Utente SET Password='"+password+"' WHERE Nickname='"+nickname+"'";
		try(PreparedStatement pstmt=connessioneDB.prepareStatement(query)){
			pstmt.executeUpdate();
			for (Utente ut : listaUtente) {  
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
	
	
	public Utente getUtenteFromArrayListByNickname(String nickname) {
		for (Utente ut : listaUtente) {  
            if (ut.getNickname().equals(nickname)){
                return ut;
            }
        }
		return null;
	}
	
	public Utente getUtenteFromArrayListByEmail(String Email) {
		for (Utente ut : listaUtente) {  
            if (ut.getEmail().equals(Email)){
                return ut;
            }
        }
		return null;
	}
	
	public Utente getUtenteFromArrayListById(int id) {
		for (Utente ut : listaUtente) {  
            if (ut.getIdUtente()==id){
                return ut;
            }
        }
		return null;
	}
	
	public boolean utenteExistInArrayList(Utente ut) {
		return listaUtente.contains(ut);
	}

	
}
