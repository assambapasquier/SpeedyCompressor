
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Pasquierase
 *
 *cette classe construit un arbre binaire de code et offre la possibilité de parcourir
 *cet arbre.
 */

public final class Arbre {
	
	public final NoeudInterne racine; 
	
	// Stocke les codes pour chaque symbole. et ressort ce code en liste. exple:[1, 0, 0, 0].
	private List<List<Integer>> codes;
	
	
	
	// Tous les symboles de l'arbre < à symbolLimit.
	public Arbre(NoeudInterne racine, int symbolLimit) {
		if (racine == null)
			throw new NullPointerException("Argument  null");
		this.racine = racine;
		
		codes = new ArrayList<List<Integer>>();  // initialement null
		for (int i = 0; i < symbolLimit; i++)
			codes.add(null);
		construire_list_code(racine, new ArrayList<Integer>());  // remplissage de la liste
	}
	
	
	private void construire_list_code(Noeud node, List<Integer> prefix) {
		if (node instanceof NoeudInterne) {
			NoeudInterne internalNode = (NoeudInterne)node;
			
			prefix.add(0);
			construire_list_code(internalNode.filsG , prefix);
			prefix.remove(prefix.size() - 1);
			
			prefix.add(1);
			construire_list_code(internalNode.filsD, prefix);
			prefix.remove(prefix.size() - 1);
			
		} else if (node instanceof Feuille) {
			Feuille leaf = (Feuille)node;
			if (leaf.symbole >= codes.size())
				throw new IllegalArgumentException("superieur à symbolLimit!");
			if (codes.get(leaf.symbole) != null)
				throw new IllegalArgumentException("un seul code par symbole!");
			codes.set(leaf.symbole, new ArrayList<Integer>(prefix));
			
		} else {
			throw new AssertionError("type de noeud inconnu");
		}
	}
	
	
	
	public List<Integer> getCode(int symbol) {
		if (symbol < 0)
			throw new IllegalArgumentException("symbole inconnu");
		else if (codes.get(symbol) == null)
			throw new IllegalArgumentException("code inexistant pour le symbole");
		else
			return codes.get(symbol);
	}
	
}
