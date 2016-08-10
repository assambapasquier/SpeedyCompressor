import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;


public final class Compression {
	

	public Compression(String fN, String destN) throws IOException{
		
		File inputFile = new File(fN);
		File outputFile = new File(destN);
		
		InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
		OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));
		
		try {
			compression(in, out);
		} finally {
			out.close();
			in.close();
		}
	}	
	static void compression(InputStream in, OutputStream out) throws IOException {
		int[] initFreqs = new int[257];
		Arrays.fill(initFreqs, 1); //initialise avec des 1
		
		TableFreq table = new TableFreq(initFreqs);
		HuffCompression enc = new HuffCompression(out);
		enc.arbre = table.construireArbre(); 
		int count = 0;
		while (true) {
			int b = in.read();
			if (b == -1)
				break;
			enc.write(b);
			
			table.increment(b);
			count++;
			if (count < 262144 && isPowerOf2(count) || count % 262144 == 0)  // on met a jour l'arbre des codes
				enc.arbre = table.construireArbre();
			if (count % 262144 == 0) 
				table = new TableFreq(initFreqs);
		}
		enc.write(256);  // EOF
	}
	
	
	private static boolean isPowerOf2(int x) {
		return x > 0 && (x & -x) == x;
	}
	
}
