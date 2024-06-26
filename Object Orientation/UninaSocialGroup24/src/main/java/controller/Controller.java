package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import classiDAO.Amministratore;
import classiDAO.AmministratoreDAO;
import classiDAO.CommentoDAO;
import classiDAO.CreatoreGruppo;
import classiDAO.CreatoreGruppoDAO;
import classiDAO.EnumStatiRichiesta;
import classiDAO.Gruppo;
import classiDAO.GruppoDAO;
import classiDAO.LikeDAO;
import classiDAO.NotificaDAO;
import classiDAO.Post;
import classiDAO.PostDAO;
import classiDAO.RichiestaDiAccesso;
import classiDAO.RichiestaDiAccessoDAO;
import classiDAO.Utente;
import classiDAO.UtenteDAO;
import dataBaseConnection.ConnectDB;
import guiUninaSocialGroup.CreazioneGruppoGUI;
import guiUninaSocialGroup.CreazionePostGUI;
import guiUninaSocialGroup.GruppoGUI;
import guiUninaSocialGroup.HomeGUI;
import guiUninaSocialGroup.ImpostazioniGUI;
import guiUninaSocialGroup.NotificheGUI;
import guiUninaSocialGroup.PasswordDimenticataGUI;
import guiUninaSocialGroup.StatisticheGruppoGUI;
import guiUninaSocialGroup.gestioneIscrittiGruppoGUI;
import guiUninaSocialGroup.loginGUI;
import guiUninaSocialGroup.registrazioneUtenteGUI;

public class Controller {
	
	public static loginGUI login;
	public static HomeGUI home;
	public static NotificheGUI notifiche;
	public static CreazioneGruppoGUI creazioneGruppo;
	public static ImpostazioniGUI impostazioni;
	public static registrazioneUtenteGUI registrazione;
	public static PasswordDimenticataGUI passwordDimenticata;
	public static GruppoGUI gruppo;
	public static StatisticheGruppoGUI Statistichegruppo;
	public static CreazionePostGUI creazionePost;
	public static gestioneIscrittiGruppoGUI gestioneIscritti;
	
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
    public static int idGruppoVisualizzato;

	public static void main(String[] args) {
		Connessione = ConnectDB.getConnection();	
		login = new loginGUI();
		registrazione = new registrazioneUtenteGUI();
		passwordDimenticata = new PasswordDimenticataGUI();
		login.setVisible(true);
		
	}

	public static void checkDataBase(Connection conn) throws SQLException {
	    try {
	    	utenteDAO = new UtenteDAO(Connessione);
	        gruppoDAO = new GruppoDAO(Connessione);
	        amministratoreDAO = new AmministratoreDAO(Connessione,gruppoDAO,utenteDAO);
	        creatoreGruppoDAO = new CreatoreGruppoDAO(Connessione,gruppoDAO,amministratoreDAO,utenteDAO);
	        postDAO = new PostDAO(Connessione,utenteDAO,gruppoDAO);
	        commentoDAO = new CommentoDAO(Connessione,postDAO,utenteDAO);
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
	
	public static void eliminaGruppo(int idGruppo) {
		LinkedList<Gruppo> listaGruppi = gruppoDAO.getListaGruppi();
		CreatoreGruppo creatoreDaEliminare = creatoreGruppoDAO.getCreatoreGruppoFromArrayListByIdGruppo(idGruppo);
		
		LinkedList<CreatoreGruppo> listaCreatoriGruppo = creatoreGruppoDAO.getListaCreatori();
		Gruppo gruppoDaEliminare = gruppoDAO.getGruppoFromArrayListById(idGruppo);
		

		if (gruppoDaEliminare != null) {
			listaGruppi.remove(gruppoDaEliminare);
			listaCreatoriGruppo.remove(creatoreDaEliminare);
			gruppoDAO.deleteGruppo(gruppoDaEliminare);
			JOptionPane.showMessageDialog(null, "Eliminazione avvenuta con successo.", "Eliminazione gruppo", JOptionPane.INFORMATION_MESSAGE);
		}
		else
			 JOptionPane.showMessageDialog(null, "Errore eliminazione gruppo.", "Errore", JOptionPane.ERROR_MESSAGE);
		
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
	
	
	public static LinkedList<Post> getPostGruppoByIdGruppo(int idGruppo){
		LinkedList<Post> listaPostGruppo = new LinkedList<Post>();
		
		for (Post p : postDAO.getListaPost()) {
			if (p.getIdGruppo() == idGruppo) {
				listaPostGruppo.add(p);
			}
		}
		return listaPostGruppo;
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
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
		
	public static boolean controlloEsistenzaIscrizioneGruppo(Gruppo g) {
		for (Utente utente : g.getListaUtentiIscritti()) {
			if (utente.getIdUtente() == myIdUtente) {
				return true;
			}
		}
		return false;	
	}
		
	
	public static boolean controlloEsistenzaNickname (String nickname) {
		for (Utente u : utenteDAO.getListaUtenti()) {
			if (u.getNickname().equals(nickname))
				return true;
		}
		return false;
	}
	
	
	public static boolean controlloUtenteÈCreatoreGruppo (int idGruppo) {
		int idUtenteCreatoreGruppo = creatoreGruppoDAO.getCreatoreGruppoFromArrayListByIdGruppo(idGruppo).getIdUtente();
		if (idUtenteCreatoreGruppo == Controller.myIdUtente)
			return true;
		return false;
	}
	
	
	
	public static boolean controlloUtenteÈAmministratore (Gruppo g , Utente u) {
		int idUtenteCreatoreGruppo = creatoreGruppoDAO.getCreatoreGruppoFromArrayListByIdGruppo(g.getIdGruppo()).getIdUtente();
		Amministratore amministratore = amministratoreDAO.getAmministratoreFromArrayListByIdGruppoIdUtente(g.getIdGruppo(), u.getIdUtente());
		if (idUtenteCreatoreGruppo == u.getIdUtente() || amministratore != null  )
			return true;
		return false;
	}
	
	
	
	public static boolean aggiungiPost(String testo, int IdUtente, int IdGruppo) {
		try {
			postDAO.insertNuovoPost(testo,IdUtente, IdGruppo);
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void tornaAllaSchermataLogin() {
		home.setVisible(false);
		chiudiNotifiche();
		chiudiCreazioneGruppo();
		chiudiImpostazioni();
		login.setVisible(true);
	}
		
	
	
	public static void apriHome() {
		home = new HomeGUI();
		creazioneGruppo = new CreazioneGruppoGUI();
		notifiche = new NotificheGUI();
		impostazioni = new ImpostazioniGUI();
		chiudiPasswordDimenticata();
		chiudiRegistrazioneUtente();
		home.setVisible(true);
		login.setVisible(false);
	}
	
	
	public static void aggiornaHome() {
		home.caricaImmagineDelProfilo();
		home.resettaRicercaGruppi();
		home.mostraGruppiIscritto(getListaGruppiUtenteIscrittoById(Controller.myIdUtente));
		home.mostraGruppiCreati(Controller.creatoreGruppoDAO.getListaGruppiCreatiFromArrayListByIdUtente(Controller.myIdUtente,Controller.gruppoDAO));	
	}
	
	
	public static void apriCreazioneGruppo() {
		creazioneGruppo.setVisible(true);	
	}
	
	public static void chiudiCreazioneGruppo() {
		creazioneGruppo.setVisible(false);	
		creazioneGruppo.resettaCampiCreazioneGruppo();
	}

	
	public static void apriNotifiche() {	
		notifiche.setVisible(true);	
	}
	
	public static void chiudiNotifiche() {	
		notifiche.setVisible(false);	
	}
	
	
	
	public static void apriImpostazioni() {
		impostazioni.setVisible(true); 
	}
	
	
	public static void chiudiImpostazioni() {
		impostazioni.setVisible(false);
		impostazioni.resettaCampiImpostazioni();
	}
	
	
		
	public static void tornaAllaHome() {
		home.setVisible(true);
	    aggiornaHome();
	    chiudiStatisticheGruppo();
	    chiudiCreazioneGruppo();
	    chiudiCreazionePost();
	    chiudiGestioneIscrittiGruppo();
		gruppo.setVisible(false);
	}
	
	
	public static void apriRegistrazioneUtente() {
	    registrazione.setVisible(true);
	}
	
	
	public static void chiudiRegistrazioneUtente() {
		registrazione.setVisible(false);
		registrazione.resettaCampiRegistrazioneUtente();
	}

	
	public static void apriPasswordDimenticata() {
		passwordDimenticata.setVisible(true);	
	}
	
	public static void chiudiPasswordDimenticata() {
		passwordDimenticata.setVisible(false);
		passwordDimenticata.resettaCampiPasswordDimenticata();
	}
	
	public static void apriStatisticheGruppo() {
		Statistichegruppo.setVisible(true);	
	}
	
	public static void chiudiStatisticheGruppo() {
		Statistichegruppo.setVisible(false);	
	}
	
	
	public static void apriSchermataGruppo() {
		home.setVisible(false);
		chiudiImpostazioni();
		chiudiCreazioneGruppo();
		
		gruppo = new GruppoGUI();
		gruppo.setVisible(true);
		creazionePost = new CreazionePostGUI();
		Statistichegruppo = new StatisticheGruppoGUI();
		gestioneIscritti = new gestioneIscrittiGruppoGUI();
		
	}
	
	public static void apriCreazionePost() {
		creazionePost.setVisible(true);
	}
	
	public static void aggiornaSchermataGruppo() {
		gruppo.mostraPannelloPost(getPostGruppoByIdGruppo(idGruppoVisualizzato));
	}
	
	public static void chiudiCreazionePost() {
		creazionePost.setVisible(false);
		creazionePost.resettaCampoCreazionePost();
	}
	
	public static void apriGestioneIscrittiGruppo() {
		gestioneIscritti.setVisible(true);
	}
	
	public static void chiudiGestioneIscrittiGruppo() {
		gestioneIscritti.setVisible(false);
		aggiornaSchermataGruppo();
	}
	
	public static void aggiornaGestioneIscrittiGruppo(Gruppo g) {
		gestioneIscritti.mostraPannelloIscrittiGruppo(g);
	}
	
}
	
	
	

	
	
	
	
	
	
		

