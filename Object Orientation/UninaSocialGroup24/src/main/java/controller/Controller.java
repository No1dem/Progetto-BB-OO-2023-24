package controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import classiDAO.AmministratoreDAO;
import classiDAO.CommentoDAO;
import classiDAO.CreatoreGruppo;
import classiDAO.CreatoreGruppoDAO;
import classiDAO.EnumStatiRichiesta;
import classiDAO.Gruppo;
import classiDAO.GruppoDAO;
import classiDAO.LikeDAO;
import classiDAO.NotificaDAO;
import classiDAO.PostDAO;
import classiDAO.RichiestaDiAccesso;
import classiDAO.RichiestaDiAccessoDAO;
import classiDAO.Utente;
import classiDAO.UtenteDAO;
import guiUninaSocialGroup.CreazioneGruppoGUI;
import guiUninaSocialGroup.HomeGUI;
import guiUninaSocialGroup.ImpostazioniGUI;
import guiUninaSocialGroup.NotificheGUI;
import guiUninaSocialGroup.loginGUI;

public class Controller {
	
	public static loginGUI login;
	public static HomeGUI home;
	public static NotificheGUI notifiche;
	public static CreazioneGruppoGUI creazioneGruppo;
	public static ImpostazioniGUI impostazioni;
	
	public static UtenteDAO utenteDAO;
	public static CreatoreGruppoDAO creatoreGruppoDAO;
    public static AmministratoreDAO amministratoreDAO;
    public static GruppoDAO gruppoDAO; 
    public static PostDAO postDAO ;
    public static NotificaDAO notificaDAO;
    public static LikeDAO likeDAO ;
    public static RichiestaDiAccessoDAO richiestaDiAccessoDAO;
    public static CommentoDAO commentoDAO;
    
    public static Connection Connessione;
    
    public static int myIdUtente;

	public static void main(String[] args) {
		login = new loginGUI();
		login.setVisible(true);
	}

	public static void checkDataBase(Connection conn) throws SQLException {
		Connessione=conn;
	    try {
	    	utenteDAO = new UtenteDAO(conn);
	        gruppoDAO = new GruppoDAO(conn);
	        gruppoDAO.stampaListaGruppi(gruppoDAO.getListaGruppi());
	        amministratoreDAO = new AmministratoreDAO(conn,gruppoDAO,utenteDAO);
	        creatoreGruppoDAO = new CreatoreGruppoDAO(conn,gruppoDAO,amministratoreDAO,utenteDAO);
	        postDAO = new PostDAO(conn,utenteDAO,gruppoDAO);
	        commentoDAO = new CommentoDAO(conn,postDAO);
	        likeDAO = new LikeDAO(conn,postDAO,commentoDAO,utenteDAO);
	        notificaDAO = new NotificaDAO(conn,postDAO,likeDAO,commentoDAO);
	        richiestaDiAccessoDAO = new RichiestaDiAccessoDAO(conn,utenteDAO,creatoreGruppoDAO,gruppoDAO,notificaDAO);
	              
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Errore durante la connessione al database. Riprova più tardi.", "Errore di connessione", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	
	public static void getMyIdUtenteByNickname(String Nickname) {
		myIdUtente = utenteDAO.getUtenteFromArrayListByNickname(Nickname).getIdUtente();
	}
	
	
	
	public static LinkedList<Gruppo> getListaGruppiUtenteIscrittoById(int idUtente){
		LinkedList<Gruppo> listaGruppiIscritto = new LinkedList<Gruppo>();
		String query = "SELECT IdGruppo FROM Iscrizione WHERE IdUtente = "+idUtente;
			
		try(Statement stmt=Connessione.createStatement()){
			ResultSet res=stmt.executeQuery(query);
			
			while(res.next()) {
				  	listaGruppiIscritto.add(gruppoDAO.getGruppoFromArrayListById(res.getInt("IdGruppo")));			
			}
			stmt.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return listaGruppiIscritto;
	}
	
	
	public static boolean creaGruppo(int idUtente, String nomeGruppo, String tagGruppo, String descrizioneGruppo) {
		String query = "SELECT CreaGruppo(?, ?, ?, ?)";		
		try(PreparedStatement stmt = Connessione.prepareStatement(query)){
			stmt.setInt(1, idUtente);
			stmt.setString(2, nomeGruppo);
			stmt.setString(3, tagGruppo);
			stmt.setString(4, descrizioneGruppo);
			stmt.execute();
			
			gruppoDAO = new GruppoDAO(Connessione);
			amministratoreDAO = new AmministratoreDAO(Connessione,gruppoDAO,utenteDAO);
	        creatoreGruppoDAO = new CreatoreGruppoDAO(Connessione,gruppoDAO,amministratoreDAO,utenteDAO);
	        
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
		
		
	public static boolean controlloEsistenzaIscrizioneGruppo(Gruppo g) {
		for (Utente utente : g.getListaUtentiIscritti()) {
			if (utente.getIdUtente() == myIdUtente) {
				return true;
			}
		}
		return false;	
	}
		
		
	
	public static boolean controlloEsistenzaRichiestaDiAccessoGruppoInAttesa (Gruppo g) {
		for (RichiestaDiAccesso rda : richiestaDiAccessoDAO.getListaRichiesteUtenteFromArrayListByIdUtente(myIdUtente)) {	
			if (rda.getGruppoAccesso().equals(g) && rda.getStatoRichiesta() == EnumStatiRichiesta.In_attesa)
				return true;
		}
		return false;
	}
	
	
	
	public static void tornaAllaSchermataLogin() {
		home.setVisible(false);
		login.setVisible(true);
		home = null;
	}
		
	
	
	public static void apriHome() {
		home = new HomeGUI();
		creazioneGruppo = new CreazioneGruppoGUI();
		notifiche = new NotificheGUI();
		impostazioni = new ImpostazioniGUI();
		home.setVisible(true);
		login.setVisible(false);
		
		
	}
	
	
	public static void aggiornaHome() {
		
		home.gruppiIscrittoPanel.revalidate();
		home.gruppiIscrittoPanel.repaint();
		home.gruppiCreatiPanel.removeAll();
		home.mostraGruppiIscritto(getListaGruppiUtenteIscrittoById(Controller.myIdUtente));
		home.mostraGruppiCreati(Controller.creatoreGruppoDAO.getListaGruppiCreatiFromArrayListByIdUtente(Controller.myIdUtente,Controller.gruppoDAO));
		
	}
	
	
	public static void apriCreazioneGruppo() {
		creazioneGruppo.setVisible(true);	
	}
	

	
	public static void apriNotifiche() {	
		notifiche.setVisible(true);
			
	}
	
	
	public static void aggiornaNotifiche() {
		notifiche.revalidate();
		notifiche.repaint();
	}
	
	
	public static void apriImpostazioni() {
		notifiche.setVisible(false);
		home.setVisible(false);
		impostazioni.setVisible(true);
	}
	
	
		
	public static void tornaAllaHome() {
		home.setVisible(true);
		impostazioni.setVisible(false);
		//gruppo
	}
	
}
	
	
	
	
	
	
		

