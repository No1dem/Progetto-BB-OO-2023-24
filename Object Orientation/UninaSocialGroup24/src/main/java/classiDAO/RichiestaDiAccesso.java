package classiDAO;

public class RichiestaDiAccesso {
	private int idRichiesta;
	private EnumStatiRichiesta statoRichiesta;
	private int idUtenteRichiesta;
	private int idCreatoreGruppo;
	private int idGruppoAccesso;
	private int idNotificaGenerata;
	
	
	public RichiestaDiAccesso(int idRichiesta, EnumStatiRichiesta statoRichiesta, int utenteRichiesta,
			int creatoreGruppo, int gruppoAccesso, int  idNG) {
		super();
		this.idRichiesta = idRichiesta;
		this.statoRichiesta = statoRichiesta;
		this.idUtenteRichiesta = utenteRichiesta;
		this.idCreatoreGruppo = creatoreGruppo;
		this.idGruppoAccesso = gruppoAccesso;
		this.idNotificaGenerata = idNG;
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



	public int getIdCreatoreGruppo() {
		return idCreatoreGruppo;
	}


	public void setCreatoreGruppo(int idCreatoreGruppo) {
		this.idCreatoreGruppo = idCreatoreGruppo;
	}


	public int getIdGruppoAccesso() {
		return idGruppoAccesso;
	}


	public void setGruppoAccesso(int idGruppoAccesso) {
		this.idGruppoAccesso = idGruppoAccesso;
	}


	public int getIdNotificaGenerata() {
		return idNotificaGenerata;
	}


	public void setIdNotificaGenerata(int idNotificaGenerata) {
		this.idNotificaGenerata = idNotificaGenerata;
	}

	public int getIdUtenteRichiesta() {
		return idUtenteRichiesta;
	}


	public void setIdUtenteRichiesta(int utenteRichiesta) {
		this.idUtenteRichiesta = utenteRichiesta;
	}


	
}
