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
	private Post Post;
	private Like Like;
	private Commento Commento;
	
	// Costruttore
	
	public Notifica(int idNotifica, LocalDate dataInvio, LocalTime oraInvio, String testoNotifica,
			EnumTipoNotifica tipoNotifica, Post post, Like like, Commento commento) {
		super();
		this.idNotifica = idNotifica;
		this.dataInvio = dataInvio;
		this.oraInvio = oraInvio;
		this.testoNotifica = testoNotifica;
		this.tipoNotifica = tipoNotifica;
		this.Post = post;
		this.Like = like;
		this.Commento = commento;
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

	public Post getPost() {
		return Post;
	}

	public void setPost(Post post) {
		Post = post;
	}

	public Like getLike() {
		return Like;
	}

	public void setLike(Like like) {
		Like = like;
	}

	public Commento getCommento() {
		return Commento;
	}

	public void setCommento(Commento commento) {
		Commento = commento;
	}
		
}
