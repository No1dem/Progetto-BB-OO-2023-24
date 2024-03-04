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
	public static UtenteDAO utenteDAO;
	public static CreatoreGruppoDAO creatoreGruppoDAO;
    public static AmministratoreDAO amministratoreDAO;
    public static GruppoDAO gruppoDAO; 
    public static PostDAO postDAO ;
    public static NotificaDAO notificaDAO;
    public static LikeDAO likeDAO ;
    public static RichiestaDiAccessoDAO rdaDAO;
    public static CommentoDAO commentoDAO;

	public static void main(String[] args) {
		loginGUI log = new loginGUI();
		log.setVisible(true);
	}

	
	
	public static void checkDataBase(Connection conn)throws SQLException {
	    try {
	        utenteDAO = new UtenteDAO(conn);
	        gruppoDAO = new GruppoDAO(conn);
	        amministratoreDAO = new AmministratoreDAO(conn,gruppoDAO,utenteDAO);
	        creatoreGruppoDAO = new CreatoreGruppoDAO(conn,gruppoDAO,amministratoreDAO,utenteDAO);
	        postDAO = new PostDAO(conn,utenteDAO,gruppoDAO);
	        commentoDAO = new CommentoDAO(conn,postDAO);
	        likeDAO = new LikeDAO(conn,postDAO,commentoDAO,utenteDAO);
	        notificaDAO = new NotificaDAO(conn,postDAO,likeDAO,commentoDAO);
	        rdaDAO = new RichiestaDiAccessoDAO(conn,utenteDAO,creatoreGruppoDAO,gruppoDAO,notificaDAO);
	              
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Errore durante la connessione al database. Riprova più tardi.", "Errore di connessione", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	
	
	
	
			
		
		
	
	
	
}
