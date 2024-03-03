package controller;

import java.sql.Connection;
import java.sql.SQLException;

import classiDAO.Utente;
import classiDAO.UtenteDAO;

public class LoginController {
	
	public boolean login(String Nickname, String password , Connection conn){
		try {
			UtenteDAO utenteDAO = new UtenteDAO(conn);
			utenteDAO.stampaListaUtenti();
			Utente ut = utenteDAO.getUtenteFromArrayListByNickname(Nickname);
			
			if (ut == null) {
	        	return false;
			}
	        else
	        	if(ut.getPassword().equals(password))
	        		return true;
	        	else
	        		return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}     
    }
}
