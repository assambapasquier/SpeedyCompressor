import java.util.PriorityQueue;
import java.util.Queue;

/** cette classe construit un arbre optimal à partir 
 * des frequences collectées 
 */

public final class TableFreq {
	
	private int[] lesFrequences;
	
	
	
	public TableFreq(int[] frequence) {
		if (frequence == null)
			throw new NullPointerException("Pas d'arguments");
		if (frequence.length < 2)
			throw new IllegalArgumentException("au moins deux symboles!");
		lesFrequences = frequence.clone(); 
		for (int x : lesFrequences) {
			if (x < 0)
				throw new IllegalArgumentException("frequence négative");
		}
	}
	
	
	
	public int getSymbolLimit() {
		return lesFrequences.length;
	}
	
	
	public int get(int symbol) {
		if (symbol < 0 || symbol >= lesFrequences.length)
			throw new IllegalArgumentException("Symbole débordant!");
		return lesFrequences[symbol];
	}
	
	
	public void set(int symbol, int freq) {
		if (symbol < 0 || symbol >= lesFrequences.length)
			throw new IllegalArgumentException("Symbole débordant!");
		lesFrequences[symbol] = freq;
	}
	
	
	public void increment(int symbol) {
		if (symbol < 0 || symbol >= lesFrequences.length)
			throw new IllegalArgumentException("Symbole débordant!");
		if (lesFrequences[symbol] == Integer.MAX_VALUE)
			throw new RuntimeException("Arithmetic overflow");
		lesFrequences[symbol]++;
	}
	
	
	// Returns a string showing all the symbols and frequencies. The format is subject to change. Useful for debugging.
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lesFrequences.length; i++)
			sb.append(String.format("%d\t%d%n", i, lesFrequences[i]));
		return sb.toString();
	}
	
	
	// Returns a code tree that is optimal for these frequencies. Always contains at least 2 symbols, to avoid degenerate trees.
	public Arbre construireArbre() {
		// Note that if two nodes have the same frequency, then the tie is broken by which tree contains the lowest symbol. Thus the algorithm is not dependent on how the queue breaks ties.
		Queue<NodeWithFrequency> pqueue = new PriorityQueue<NodeWithFrequency>(); //une FIFO
		
		// Add leaves for symbols with non-zero frequency
		for (int i = 0; i < lesFrequences.length; i++) {
			if (lesFrequences[i] > 0)
				pqueue.add(new NodeWithFrequency(new Feuille(i), i, lesFrequences[i]));
		}
		
		// Pad with zero-frequency symbols until queue has at least 2 items
		for (int i = 0; i < lesFrequences.length && pqueue.size() < 2; i++) {
			if (i >= lesFrequences.length || lesFrequences[i] == 0)
				pqueue.add(new NodeWithFrequency(new Feuille(i), i, 0));
		}
		if (pqueue.size() < 2)
			throw new AssertionError();
		
		// Repeatedly tie together two nodes with the lowest frequency
		while (pqueue.size() > 1) {
			NodeWithFrequency nf1 = pqueue.remove();
			NodeWithFrequency nf2 = pqueue.remove();
			
			pqueue.add(new NodeWithFrequency(
					new NoeudInterne(nf1.node, nf2.node),
					Math.min(nf1.minSymbole, nf2.minSymbole),
					nf1.frequency + nf2.frequency));
		}
		
		// Retourne le noeud restant
		return new Arbre((NoeudInterne)pqueue.remove().node, lesFrequences.length);
	}
	
	
	
	private static class NodeWithFrequency implements Comparable<NodeWithFrequency> {
		
		public final Noeud node;
		
		public final int minSymbole;
		
		public final long frequency; 
		
		
		public NodeWithFrequency(Noeud node, int minSymbole, long freq) {
			this.node = node;
			this.minSymbole = minSymbole;
			this.frequency = freq;
		}
		
		
		public int compareTo(NodeWithFrequency other) {
			if (frequency < other.frequency)
				return -1;
			else if (frequency > other.frequency)
				return 1;
			else if (minSymbole < other.minSymbole)
				return -1;
			else if (minSymbole > other.minSymbole)
				return 1;
			else
				return 0;
		}
		
	}
	
}
