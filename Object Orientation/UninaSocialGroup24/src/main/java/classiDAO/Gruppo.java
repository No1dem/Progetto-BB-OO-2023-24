package classiDAO;

public class Gruppo {
	private int idGruppo;
	private String nomeGruppo;
	private String tagGruppo;
	private String descrizioneGruppo;
	private int numeroIscritti;
	
	// Costruttore
	
	public Gruppo(int IdG, String NomeG, String TagG, String DescrG, int NumI) {
		super();
		idGruppo = IdG;
		nomeGruppo = NomeG;
		tagGruppo = TagG;
		descrizioneGruppo = DescrG;
		numeroIscritti = NumI;
	}
	
	// Getters e Setters

	public int getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(int idGruppo) {
		this.idGruppo = idGruppo;
	}

	public String getNomeGruppo() {
		return nomeGruppo;
	}

	public void setNomeGruppo(String nomeGruppo) {
		this.nomeGruppo = nomeGruppo;
	}

	public String getTagGruppo() {
		return tagGruppo;
	}

	public void setTagGruppo(String tagGruppo) {
		this.tagGruppo = tagGruppo;
	}

	public String getDescrizioneGruppo() {
		return descrizioneGruppo;
	}

	public void setDescrizioneGruppo(String descrizioneGruppo) {
		this.descrizioneGruppo = descrizioneGruppo;
	}

	public int getNumeroIscritti() {
		return numeroIscritti;
	}

	public void setNumeroIscritti(int numeroIscritti) {
		this.numeroIscritti = numeroIscritti;
	}
	

		
}
