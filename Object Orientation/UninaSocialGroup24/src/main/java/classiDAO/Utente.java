package classiDAO;

import java.util.LinkedList;

public class Utente {
	private int idUtente;
	private String nomeUtente;
	private String cognomeUtente;
	private String email;
	private String nickname;
	private String password;
	private String urlFotoProfilo;
	private String biografia;
	
	
	//Costruttori 
	
	public Utente(int idU,String NU,String CU,String E,String Nick,String Pass,String bio,String urlfp) {
		idUtente=idU;
		nomeUtente=NU;
		cognomeUtente=CU;
		email=E;
		nickname=Nick;
		password=Pass;
		biografia=bio;
		urlFotoProfilo=urlfp;
		
	}
	
	//Getters e Setters

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getCognomeUtente() {
		return cognomeUtente;
	}

	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrlFotoProfilo() {
		return urlFotoProfilo;
	}

	public void setUrlFotoProfilo(String urlFotoProfilo) {
		this.urlFotoProfilo = urlFotoProfilo;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	
		
}
