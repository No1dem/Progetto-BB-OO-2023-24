package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import classiDAO.AmministratoreDAO;
import classiDAO.CommentoDAO;
import classiDAO.CreatoreGruppoDAO;
import classiDAO.GruppoDAO;
import classiDAO.LikeDAO;
import classiDAO.NotificaDAO;
import classiDAO.PostDAO;
import classiDAO.RichiestaDiAccessoDAO;
import classiDAO.Utente;
import classiDAO.UtenteDAO;
import dataBaseConnection.ConnectDB;
import guiUninaSocialGroup.loginGUI;

public class Controller {
	

	public static void main(String[] args) {
		loginGUI log = new loginGUI();
		log.setVisible(true);
	}

	
	
	public static void checkDataBase(Connection conn)throws SQLException {
	    try {
//	        UtenteDAO utenteDAO = new UtenteDAO(conn);
//	        CreatoreGruppoDAO creatoreGruppoDAO = new CreatoreGruppoDAO(conn);
//	        AmministratoreDAO amministratoreDAO = new AmministratoreDAO(conn);
//	        GruppoDAO gruppoDAO = new GruppoDAO(conn);
//	        PostDAO postDAO = new PostDAO(conn);
//	        NotificaDAO notificaDAO = new NotificaDAO(conn);
	        LikeDAO likeDAO = new LikeDAO(conn);
//	        RichiestaDiAccessoDAO rdaDAO = new RichiestaDiAccessoDAO(conn);
//	        CommentoDAO commentoDAO = new CommentoDAO(conn);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Errore durante la connessione al database. Riprova più tardi.", "Errore di connessione", JOptionPane.ERROR_MESSAGE);
	    }
	}

			
		
		
	
	
	
}
