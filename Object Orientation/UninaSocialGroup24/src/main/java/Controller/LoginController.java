package Controller;

import ClassiDAO.Utente;
import ClassiDAO.UtenteDAO;

public class LoginController {
	
	public boolean login(String Email, String password){
        Utente ut = new UtenteDAO().getUtenteFromArrayListByEmail(Email);
        if (ut== null) 
        	return false;
        else
        	if(ut.getPassword().equals(password))
        		return true;
        	else
        		return false;      
    }
}
