package classiDAO;

import java.sql.Connection;
import java.util.LinkedList;

public class CommentoDAO {

	private Connection connessioneDB; 
	private LinkedList<Commento> listaCommenti;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// dato in input un idCommento restituisce l'oggetto commento se presente 
	public Commento getCommentoFromArrayListById(int idCommento) {
	    for (Commento commento : listaCommenti) {
	        if (commento.getIdCommento() == idCommento) {
	            return commento;
	        }
	    }
	   
	    return null;
	}




}
