package classiDAO;

public class RichiestaDiAccesso {
	private int idRichiesta;
	private EnumStatiRichiesta statoRichiesta;
	private Utente UtenteRichiesta;
	private CreatoreGruppo CreatoreGruppoDiRichiesta;
	private Gruppo GruppoAccesso;
	private Notifica NotificaGenerata;
	
	
	public RichiestaDiAccesso(int idRichiesta, EnumStatiRichiesta statoRichiesta, Utente utenteRichiesta,
		CreatoreGruppo creatoreGruppo, Gruppo gruppoAccesso, Notifica  notifGen) {
		super();
		this.idRichiesta = idRichiesta;
		this.statoRichiesta = statoRichiesta;
		this.UtenteRichiesta = utenteRichiesta;
		this.CreatoreGruppoDiRichiesta = creatoreGruppo;
		this.GruppoAccesso = gruppoAccesso;
		this.NotificaGenerata = notifGen;
	}


	public int getIdRichiesta() {
		return idRichiesta;
	}


	public void setIdRichiesta(int idRichiesta) {
		this.idRichiesta = idRichiesta;
	}


	public EnumStatiRichiesta getStatoRichiesta() {
		return statoRichiesta;
	}


	public void setStatoRichiesta(EnumStatiRichiesta statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	}


	public Utente getUtenteRichiesta() {
		return UtenteRichiesta;
	}


	public void setUtenteRichiesta(Utente utenteRichiesta) {
		UtenteRichiesta = utenteRichiesta;
	}


	public CreatoreGruppo getCreatoreGruppoDiRichiesta() {
		return CreatoreGruppoDiRichiesta;
	}


	public void setCreatoreGruppoDiRichiesta(CreatoreGruppo creatoreGruppoDiRichiesta) {
		CreatoreGruppoDiRichiesta = creatoreGruppoDiRichiesta;
	}


	public Gruppo getGruppoAccesso() {
		return GruppoAccesso;
	}


	public void setGruppoAccesso(Gruppo gruppoAccesso) {
		GruppoAccesso = gruppoAccesso;
	}


	public Notifica getNotificaGenerata() {
		return NotificaGenerata;
	}


	public void setNotificaGenerata(Notifica notificaGenerata) {
		NotificaGenerata = notificaGenerata;
	}
	
}
