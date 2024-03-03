package classiDAO;

public class Commento {
	private int idCommento;
	private String testoCommento;
	private int numeroLike;
	private int idCommentoRisposto;
	private int idPostRisposto;
	
	//Costruttore
	
	public Commento(int idCommento, String testoCommento, int numeroLike, int idCommRisp, int idPostRisp) {
		super();
		this.idCommento = idCommento;
		this.testoCommento = testoCommento;
		this.numeroLike = numeroLike;
		this.idCommentoRisposto = idCommRisp;
		this.idPostRisposto = idPostRisp;
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

	public int getIdCommentoRisposto() {
		return idCommentoRisposto;
	}

	public void setIdCommentoRisposto(int idCommentoRisposto) {
		this.idCommentoRisposto = idCommentoRisposto;
	}

	public int getIdPostRisposto() {
		return idPostRisposto;
	}

	public void setIdPostRisposto(int idPostRisposto) {
		this.idPostRisposto = idPostRisposto;
	}
					
}
