package classiDAO;

public class Commento {
	private int idCommento;
	private String testoCommento;
	private int numeroLike;
	private Commento CommentoRisposto;
	private Post PostRisposto;
	
	//Costruttore
	
	public Commento(int idCommento, String testoCommento, int numeroLike, Commento CommRisp, Post PostRisp) {
		super();
		this.idCommento = idCommento;
		this.testoCommento = testoCommento;
		this.numeroLike = numeroLike;
		this.CommentoRisposto = CommRisp;
		this.PostRisposto = PostRisp;
	}

	//Getters e Setters
	
	public int getIdCommento() {
		return idCommento;
	}

	public void setIdCommento(int idCommento) {
		this.idCommento = idCommento;
	}

	public String getTestoCommento() {
		return testoCommento;
	}

	public void setTestoCommento(String testoCommento) {
		this.testoCommento = testoCommento;
	}

	public int getNumeroLike() {
		return numeroLike;
	}

	public void setNumeroLike(int numeroLike) {
		this.numeroLike = numeroLike;
	}

	public Commento getCommentoRisposto() {
		return CommentoRisposto;
	}

	public void setCommentoRisposto(Commento commentoRisposto) {
		CommentoRisposto = commentoRisposto;
	}

	public Post getPostRisposto() {
		return PostRisposto;
	}

	public void setPostRisposto(Post postRisposto) {
		PostRisposto = postRisposto;
	}

	
					
}
