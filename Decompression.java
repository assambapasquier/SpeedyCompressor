

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;


public final class Decompression {
	
	public Decompression(String cmprF, String destF) throws IOException {

		File inputFile = new File(cmprF);
		File outputFile = new File(destF);
		
		InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
		OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));
		try {
			decompress(in, out);
		} finally {
			out.close();
			in.close();
		}
	}
	
	
	static void decompress(InputStream in, OutputStream out) throws IOException {
		int[] initFreqs = new int[257];
		Arrays.fill(initFreqs, 1);
		
		TableFreq freqTable = new TableFreq(initFreqs);
		HuffDecompression dec = new HuffDecompression(in);
		dec.arbre = freqTable.construireArbre();
		int count = 0;
		while (true) {
			int symbol = dec.read();
			if (symbol == 256) 
				break;
			out.write(symbol);
			
			freqTable.increment(symbol);
			count++;
			if (count < 262144 && isPowerOf2(count) || count % 262144 == 0)  // mise à jour de l'arbre
				dec.arbre = freqTable.construireArbre();
			if (count % 262144 == 0)  // la table des frequences à 0
				freqTable = new TableFreq(initFreqs);
		}
	}
	
	
	private static boolean isPowerOf2(int x) {
		return x > 0 && (x & -x) == x;
	}
	
}
