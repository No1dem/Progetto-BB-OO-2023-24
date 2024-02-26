package classiDAO;

import java.sql.Connection;
import java.util.LinkedList;

public class LikeDAO {

	private Connection connessioneDB; 
	private LinkedList<Like> listaLikes;
	
	
	
	
	
	
	
	
	
	
	
	
	// dato in input un idLike restituisce l'oggetto Like se presente 
	public Like getLikeFromArrayListById(int idLike) {
	    for (Like like : listaLikes) {
	        if (like.getIdLike() == idLike) {
	            return like;
	        }
	    }
	    
	    return null;
	}


	
}
