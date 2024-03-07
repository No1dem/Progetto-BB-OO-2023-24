package classiDAO;

import java.util.LinkedList;

public class Amministratore extends Utente{

	private int idAmministratore;
	private Gruppo gruppoAmministrato;
	

	public Amministratore(int idU, String NU, String CU, String E, String Nick, String Pass, String bio, String urlfp, 
			int idAmministratore, Gruppo gruppoAmministrato) {
		super(idU, NU, CU, E, Nick, Pass, bio, urlfp);
		this.idAmministratore = idAmministratore;
		this.gruppoAmministrato = gruppoAmministrato;
	}


	public int getIdAmministratore() {
		return idAmministratore;
	}


	public void setIdAmministratore(int idAmministratore) {
		this.idAmministratore = idAmministratore;
	}


	public int getIdGruppoAmministrato() {
		return gruppoAmministrato.getIdGruppo();
	}


	public void setGruppoAmministrato(Gruppo gruppoAmministrato) {
		this.gruppoAmministrato = gruppoAmministrato;
	}


}
