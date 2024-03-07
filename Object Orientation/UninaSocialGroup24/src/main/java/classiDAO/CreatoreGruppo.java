package classiDAO;

import java.util.LinkedList;

public class CreatoreGruppo extends Amministratore{

	private int idCreatoreGruppo;



	public CreatoreGruppo(int idU, String NU, String CU, String E, String Nick, String Pass, String bio, String urlfp,
			int idAmministratore, Gruppo gruppoAmministrato, int idCreatoreGruppo) {
		super(idU, NU, CU, E, Nick, Pass, bio, urlfp, idAmministratore, gruppoAmministrato);
		this.idCreatoreGruppo = idCreatoreGruppo;
	}

	public int getIdCreatoreGruppo() {
		return idCreatoreGruppo;
	}

	public void setIdCreatoreGruppo(int idCreatoreGruppo) {
		this.idCreatoreGruppo = idCreatoreGruppo;
	}
	
	
	
	
	
}
