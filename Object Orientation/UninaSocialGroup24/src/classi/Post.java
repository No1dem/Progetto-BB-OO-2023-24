package ClassiDAO;
import java.time.LocalDate;
import java.time.LocalTime;

public class Post {
	private int idPost;
	private String Testo;
	private String urlImmagine;
	private LocalDate dataPubblicazione;
	private LocalTime oraPubblicazione;
	private int numeroLike;
	private int numeroCommenti;
	
	//Costruttore
	
	public Post(int idPost, String testo, String urlImmagine, LocalDate dataPubblicazione, LocalTime oraPubblicazione,int numeroLike, int numeroCommenti) {
		super();
		this.idPost = idPost;
		Testo = testo;
		this.urlImmagine = urlImmagine;
		this.dataPubblicazione = dataPubblicazione;
		this.oraPubblicazione = oraPubblicazione;
		this.numeroLike = numeroLike;
		this.numeroCommenti = numeroCommenti;
	}
	
	//Getters e Setters

	public int getIdPost() {
		return idPost;
	}

	public void setIdPost(int idPost) {
		this.idPost = idPost;
	}

	public String getTesto() {
		return Testo;
	}

	public void setTesto(String testo) {
		Testo = testo;
	}

	public String getUrlImmagine() {
		return urlImmagine;
	}

	public void setUrlImmagine(String urlImmagine) {
		this.urlImmagine = urlImmagine;
	}

	public LocalDate getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(LocalDate dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public LocalTime getOraPubblicazione() {
		return oraPubblicazione;
	}

	public void setOraPubblicazione(LocalTime oraPubblicazione) {
		this.oraPubblicazione = oraPubblicazione;
	}

	public int getNumeroLike() {
		return numeroLike;
	}

	public void setNumeroLike(int numeroLike) {
		this.numeroLike = numeroLike;
	}

	public int getNumeroCommenti() {
		return numeroCommenti;
	}

	public void setNumeroCommenti(int numeroCommenti) {
		this.numeroCommenti = numeroCommenti;
	}
		
}
