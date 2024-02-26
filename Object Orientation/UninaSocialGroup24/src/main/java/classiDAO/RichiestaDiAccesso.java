package classiDAO;

public class RichiestaDiAccesso {
	private int idRichiesta;
	private EnumStatiRichiesta statoRichiesta;
	private Utente utenteRichiesta;
	private CreatoreGruppo creatoreGruppo;
	private Gruppo gruppoAccesso;
	private Notifica notificaGenerata;
	
	
	public RichiestaDiAccesso(int idRichiesta, EnumStatiRichiesta statoRichiesta, Utente utenteRichiesta,
			CreatoreGruppo creatoreGruppo, Gruppo gruppoAccesso, Notifica notificaGenerata) {
		super();
		this.idRichiesta = idRichiesta;
		this.statoRichiesta = statoRichiesta;
		this.utenteRichiesta = utenteRichiesta;
		this.creatoreGruppo = creatoreGruppo;
		this.gruppoAccesso = gruppoAccesso;
		this.notificaGenerata = notificaGenerata;
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


	public int getIdUtenteRichiesta() {
		return utenteRichiesta.getIdUtente();
	}


	public void setUtenteRichiesta(Utente utenteRichiesta) {
		this.utenteRichiesta = utenteRichiesta;
	}


	public int getIdCreatoreGruppo() {
		return creatoreGruppo.getIdCreatoreGruppo();
	}


	public void setCreatoreGruppo(CreatoreGruppo creatoreGruppo) {
		this.creatoreGruppo = creatoreGruppo;
	}


	public int getIdGruppoAccesso() {
		return gruppoAccesso.getIdGruppo();
	}


	public void setGruppoAccesso(Gruppo gruppoAccesso) {
		this.gruppoAccesso = gruppoAccesso;
	}


	public int getIdNotificaGenerata() {
		return notificaGenerata.getIdNotifica();
	}


	public void setNotificaGenerata(Notifica notificaGenerata) {
		this.notificaGenerata = notificaGenerata;
	}
	
}
