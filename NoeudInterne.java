/**
 * 
 * @author Pasquierase
 *
 *cette classe définie un noeud interne. i.e un noeud ayant deux fils.
 */
public final class NoeudInterne extends Noeud {
	
	public final Noeud filsG;  //Non null
	
	public final Noeud filsD;  // Non null
	
	
	
	public NoeudInterne(Noeud filsG, Noeud filsD) {
		if (filsG == null || filsD == null)
			throw new NullPointerException("Pas d'argument!");
		this.filsG = filsG;
		this.filsD = filsD;
	}
	
}
