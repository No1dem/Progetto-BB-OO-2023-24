package controller;

import java.sql.Connection;
import java.sql.SQLException;

import classiDAO.Utente;
import classiDAO.UtenteDAO;

public class PwDimenticataController {
	
	
	 public boolean verificaEmailCorrispondente(String nickname, String email, Connection conn) {
	        try {
	        	
	        	UtenteDAO utenteDAO = new UtenteDAO(conn);
				Utente ut = utenteDAO.getUtenteFromArrayListByNickname(nickname);
	            
	            

	            
	            return ut != null && ut.getEmail().equals(email);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	

}
