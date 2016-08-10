

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


public final class HuffCompression {
	
	private OutputStream output;
	

	public Arbre arbre;
	int octetCourant = 0;
	int numBitDansOctetCourt = 0;
	
	
	public HuffCompression(OutputStream out) {
		output = out;
	}
	
	
	
	public void write(int symbol) throws IOException {
		if (arbre == null)
			throw new NullPointerException("le code de l'arbre est nul");
		
		List<Integer> bits = arbre.getCode(symbol);
		for (int b : bits){
			if (!(b == 0 || b == 1))
				throw new IllegalArgumentException("Hé 0 ou 1!!!");
			octetCourant = octetCourant << 1 | b;
			numBitDansOctetCourt++;
			if (numBitDansOctetCourt == 8) {
				output.write(octetCourant);
				numBitDansOctetCourt = 0;
			}
		}//fin for
		
		
	}
	
}
