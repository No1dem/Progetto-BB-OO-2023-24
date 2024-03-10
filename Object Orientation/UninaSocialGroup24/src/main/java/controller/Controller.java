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
import dataBaseConnection.ConnectDB;
import guiUninaSocialGroup.CreazioneGruppoGUI;
import guiUninaSocialGroup.HomeGUI;
import guiUninaSocialGroup.ImpostazioniGUI;
import guiUninaSocialGroup.NotificheGUI;
import guiUninaSocialGroup.loginGUI;
import guiUninaSocialGroup.registrazioneUtenteGUI;

public class Controller {
	
	public static loginGUI login;
	public static HomeGUI home;
	public static NotificheGUI notifiche;
	public static CreazioneGruppoGUI creazioneGruppo;
	public static ImpostazioniGUI impostazioni;
	public static registrazioneUtenteGUI registrazione;
	
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
		Connessione = ConnectDB.getConnection();	
		login = new loginGUI();
		registrazione = new registrazioneUtenteGUI();
		login.setVisible(true);
	}

	public static void checkDataBase(Connection conn) throws SQLException {
	    try {
	    	utenteDAO = new UtenteDAO(Connessione);
	    	utenteDAO.stampaListaUtenti();
	        gruppoDAO = new GruppoDAO(Connessione);
	        amministratoreDAO = new AmministratoreDAO(Connessione,gruppoDAO,utenteDAO);
	        creatoreGruppoDAO = new CreatoreGruppoDAO(Connessione,gruppoDAO,amministratoreDAO,utenteDAO);
	        postDAO = new PostDAO(Connessione,utenteDAO,gruppoDAO);
	        commentoDAO = new CommentoDAO(Connessione,postDAO);
	        likeDAO = new LikeDAO(Connessione,postDAO,commentoDAO,utenteDAO);
	        notificaDAO = new NotificaDAO(Connessione,postDAO,likeDAO,commentoDAO);
	        richiestaDiAccessoDAO = new RichiestaDiAccessoDAO(Connessione,utenteDAO,creatoreGruppoDAO,gruppoDAO,notificaDAO);
	              
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
	
	public static boolean registraUtente(String nomeUtente, String cognomeUtente, String nickname,
			String biografia, String email, String password) {
		try{
			UtenteDAO utenteDAO = new UtenteDAO(Connessione);
			utenteDAO.insertNuovoUtente(nomeUtente, cognomeUtente, nickname, email, password, biografia);
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
		notifiche.setVisible(false);
		creazioneGruppo.setVisible(false);
		impostazioni.setVisible(false);
		login.setVisible(true);
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
		home.caricaImmagineDelProfilo();
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
		notifiche.mostraNotifiche(notificaDAO.getListaNotificheByIdUtente(Controller.myIdUtente));
		notifiche.mostraRichiesteAccesso(notificaDAO.getListaNotificheByIdUtente(Controller.myIdUtente));
	}
	
	
	public static void apriImpostazioni() {
		notifiche.setVisible(false);
		home.setVisible(false);
		impostazioni.setVisible(true); 
		creazioneGruppo.setVisible(false);
	}
	
	
		
	public static void tornaAllaHome() {
		home.setVisible(true);
	    aggiornaHome();
		creazioneGruppo.setVisible(false);
		//gruppo.setVisible(false);
	}
	
	
	public static void apriRegistrazioneUtente() {
	    registrazione.setVisible(true);
	    login.setVisible(false);
	}
	
	
	public static void chiudiRegistrazioneUtente() {
		registrazione.setVisible(false);
		login.setVisible(true);
	}
	 
}
	
	
	

	
	
	
	
	
	
		

