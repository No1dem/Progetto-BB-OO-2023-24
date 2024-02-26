package classiDAO;

public class Commento {
	private int idCommento;
	private String testoCommento;
	private int numeroLike;
	
	//Costruttore
	
	public Commento(int idCommento, String testoCommento, int numeroLike) {
		super();
		this.idCommento = idCommento;
		this.testoCommento = testoCommento;
		this.numeroLike = numeroLike;
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
	
}
