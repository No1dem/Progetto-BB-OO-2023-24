package classiDAO;

public class Like {
	private int idLike;
	private Utente  utenteLike;
	private Commento commmentoConLike;
	private Post postConLike;
	
	public Like(int idLike, Utente utenteLike, Commento commmentoConLike, Post postConLike) {
		super();
		this.idLike = idLike;
		this.utenteLike = utenteLike;
		this.commmentoConLike = commmentoConLike;
		this.postConLike = postConLike;
	}

	
	
	public int getIdLike() {
		return idLike;
	}

	public void setIdLike(int idLike) {
		this.idLike = idLike;
	}

	public int getIdUtenteLike() {
		return utenteLike.getIdUtente();
	}

	public void setUtenteLike(Utente utenteLike) {
		this.utenteLike = utenteLike;
	}

	public int getIdCommmentoConLike() {
		return commmentoConLike.getIdCommento();
	}

	public void setCommmentoConLike(Commento commmentoConLike) {
		this.commmentoConLike = commmentoConLike;
	}

	public int getIdPostConLike() {
		return postConLike.getIdPost();
	}

	public void setPostConLike(Post postConLike) {
		this.postConLike = postConLike;
	}

	
}
