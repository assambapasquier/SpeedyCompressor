


/**
 * code de la feuille dans l'arbre. elle contient un symbole
 */
public final class Feuille extends Noeud {
	
	public final int symbole;
	
	
	
	public Feuille(int symbole) {
		if (symbole < 0)
			throw new IllegalArgumentException("valeur de symbole inconnue");
		this.symbole = symbole;
	}
	
}
