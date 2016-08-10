

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;


/**
 * Action for performing decompression from GUI.
 * 
 * @author madhead
 * 
 */
public class DecompressAction  {

	/**
	 * Default no-arg constructor.
	 */
	public DecompressAction() {	
		JFileChooser sourceFileChooser = new JFileChooser();

		sourceFileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "les .apm";
			}

			@Override
			//affiche uniquement les .apm
			public boolean accept(File f) {
				if (f.getName().endsWith(".apm")) {
					return true;
				}
				return false;
			}
		});
		sourceFileChooser.setMultiSelectionEnabled(false);
		if (JFileChooser.APPROVE_OPTION == sourceFileChooser
				.showOpenDialog(null)) {
			final String fileName = sourceFileChooser.getSelectedFile()
					.getAbsolutePath();
			JFileChooser destFileChooser = new JFileChooser();
			if (JFileChooser.APPROVE_OPTION == destFileChooser
					.showSaveDialog(null)) {
				final String destFileName = destFileChooser.getSelectedFile()
						.getAbsolutePath();
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							
							Decompression dec = new Decompression(fileName, destFileName);

							JOptionPane.showMessageDialog(null,
									"Decompression reussie!",
									"Decompress",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (FileNotFoundException fileNotFoundException) {
							JOptionPane.showMessageDialog(null,
									"File not found!", "Decompress",
									JOptionPane.ERROR_MESSAGE);
						} catch (IOException ioException) {
							JOptionPane.showMessageDialog(null,
									"I/O exception while decompressing",
									"Decompress", JOptionPane.ERROR_MESSAGE);
						} catch (Exception unknownException) {
							JOptionPane.showMessageDialog(null,
									"Unknown exception while decompressing",
									"Decompress", JOptionPane.ERROR_MESSAGE);
						}
					}
				}).start();
			}
		}
	}

}
