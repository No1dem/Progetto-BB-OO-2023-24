package classiDAO;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

public class Notifica {
	private int idNotifica;
	private LocalDate dataInvio;
	private LocalTime oraInvio;
	private String testoNotifica;
	private EnumTipoNotifica tipoNotifica;
	private int idPost;
	private int idLike;
	private int idCommento;
	
	// Costruttore
	
	public Notifica(int idNotifica, LocalDate dataInvio, LocalTime oraInvio, String testoNotifica,
			EnumTipoNotifica tipoNotifica, int post, int like, int commento) {
		super();
		this.idNotifica = idNotifica;
		this.dataInvio = dataInvio;
		this.oraInvio = oraInvio;
		this.testoNotifica = testoNotifica;
		this.tipoNotifica = tipoNotifica;
		this.idPost = post;
		this.idLike = like;
		this.idCommento = commento;
	}

	//Getters e Setters
	
	public int getIdNotifica() {
		return idNotifica;
	}

	public void setIdNotifica(int idNotifica) {
		this.idNotifica = idNotifica;
	}

	public LocalDate getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(LocalDate dataInvio) {
		this.dataInvio = dataInvio;
	}

	public LocalTime getOraInvio() {
		return oraInvio;
	}

	public void setOraInvio(LocalTime oraInvio) {
		this.oraInvio = oraInvio;
	}

	public String getTestoNotifica() {
		return testoNotifica;
	}

	public void setTestoNotifica(String testoNotifica) {
		this.testoNotifica = testoNotifica;
	}

	public EnumTipoNotifica getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(EnumTipoNotifica tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}

	public int getIdPost() {
		return idPost;
	}

	public void setIdPost(int idPost) {
		this.idPost = idPost;
	}

	public int getIdLike() {
		return idLike;
	}

	public void setIdLike(int idLike) {
		this.idLike = idLike;
	}

	public int getIdCommento() {
		return idCommento;
	}

	public void setIdCommento(int idCommento) {
		this.idCommento = idCommento;
	}

	


		
}
