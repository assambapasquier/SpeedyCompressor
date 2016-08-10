

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;


public final class HuffDecompression {
	
	private InputStream input;
	public Arbre arbre;
	int nextBits;
	
	
	public HuffDecompression(InputStream in) {
		if (in == null)
			throw new NullPointerException("Argument null");
		input = in;
	}
	
	
	
	public int read() throws IOException {
		if (arbre == null)
			throw new NullPointerException("arbre null!");
		
		NoeudInterne currentNode = arbre.racine;
		while (true) {
			int temp = lireNonFin();
			Noeud nextNode;
			if      (temp == 0) nextNode = currentNode.filsG;
			else if (temp == 1) nextNode = currentNode.filsD;
			else throw new AssertionError();
			
			if (nextNode instanceof Feuille)
				return ((Feuille)nextNode).symbole;
			else if (nextNode instanceof NoeudInterne)
				currentNode = (NoeudInterne)nextNode;
			else
				throw new AssertionError();
		}
		
	}
	
	
	// toujours entre 0 et 7.
	private int numBitsRemaining = 0;
	
	private boolean isEndOfStream = false;
	
	//on lit le fichier en entrée
	public int lire() throws IOException {
		if (isEndOfStream)
			return -1;
		if (numBitsRemaining == 0) {
			nextBits = input.read();
			if (nextBits == -1) {
				isEndOfStream = true;
				return -1;
			}
			numBitsRemaining = 8;
		}
		numBitsRemaining--;
		return (nextBits >>> numBitsRemaining) & 1;
	}
	
	
	// retourne une exception si on est à la fin du fichier
	public int lireNonFin() throws IOException {
		int result = lire();
		if (result != -1)
			return result;
		else
			throw new EOFException("Fin du fichier!");
	}
	
}
