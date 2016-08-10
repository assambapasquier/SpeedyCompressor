
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/** cette classe presente la fenêtre permettant à l'utilisateur de choisir
 * le fichier à compresser par des clics
 
 * @author Pasquierase
 *
 */


public  class CompressAction {

	public CompressAction() {
		
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setMultiSelectionEnabled(false);
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
			final String fileName = fileChooser.getSelectedFile()
					.getAbsolutePath();
			final String destFileName = fileName+".apm"; //l'extension sera apm
			new Thread(new Runnable() {

				public void run() {
					try {
						
						//on appele la méthode compression avec nos deux fichiers!
						new Compression(fileName, destFileName);
						
						JOptionPane.showMessageDialog(null,"La compression a reussi!","Compression OK", JOptionPane.INFORMATION_MESSAGE);
					} catch (FileNotFoundException fileNotFoundException) {
						JOptionPane.showMessageDialog(null, "File not found!","Compression KO", JOptionPane.ERROR_MESSAGE);
					} catch (IOException ioException) {
						JOptionPane.showMessageDialog(null,"IO Exception while compressing", "Compression KO",JOptionPane.ERROR_MESSAGE);
					} catch (Exception unknownException) {
						JOptionPane.showMessageDialog(null,"Unknown exception while compressing","compression KO", JOptionPane.ERROR_MESSAGE);
					}
				}
			}).start();
		}
	}

	

	
}
