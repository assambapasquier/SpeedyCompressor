

/**
 * @author Pasquierase
 *
 */
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
 
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class MainFrame extends JFrame {
	
	
	MainFrame(){
		super("COMPRESSION/DECOMPRESSION");
		setSize(500,300);
		initialiserLesMenus();
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent e){
			quitter();
		}
		});
		setVisible(true);
	}
	
	
	private void initialiserLesMenus(){
		
		JPanel container = new Panneau();
		 
        final JComboBox combo = new JComboBox();
        JButton bouton = new JButton("Lancer");
        JButton bouton_quiter = new JButton("Quitter");
        JButton bouton_aide = new JButton("?");
        JLabel label = new JLabel("Auteur: ASSAMBA Pasquier. Projet 3GI ENSP 2013/2014. Assigné par Dr Djotio");
        
        container.setBackground(Color.white);
        container.setLayout(new BorderLayout());
        
        combo.setPreferredSize(new Dimension(200,30));
        combo.addItem("COMPRESSION...");
        combo.addItem("DECOMPRESSION...");
        
                
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        //top.add(label);
        top.add(combo);
        top.add(bouton);
        top.add(bouton_quiter);
        top.add(bouton_aide);
        bottom.add(label);
        
        bouton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			if(combo.getSelectedItem() == "COMPRESSION..."){
    				try {
						compresserFichier();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			}
    			if(combo.getSelectedItem() == "DECOMPRESSION..."){
    				decompresserFichier();
    			}
    		}
    		});
        
        bouton_quiter.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			
    			int option = JOptionPane.showConfirmDialog(null, "Voulez-vous quitter ?", "Arrêt de l'application", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    			
    			if(option != JOptionPane.NO_OPTION && option != JOptionPane.CANCEL_OPTION && option != JOptionPane.CLOSED_OPTION)
    			{
    				quitter();
    			}

    		
    		}
    		});
        
        bouton_aide.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			
    			//le code ici
    			JOptionPane.showMessageDialog( null, 
    			" comment utiliser ce logiciel?\n  "+
    			"----------------------------------------\n"+
    			"1) sélectionner votre action (compression/décompression)\n"+
    			"2) puis cliquez sur Lancer\n"+
    			"NB: le fichier compressé est par défaut stocké dans le même répertoire que son "+ 
    			"fichier origine et porte lextension .apm\n"+
    			"par contre, vous avez la latitude de choisir l'emplacement de votre fichier pendant "+
    			"la décompression\n\n"+
    			"Assamba Pasquier 3GI Polytechnique Yaoundé",
    			"Aide ", 
    			JOptionPane.INFORMATION_MESSAGE );   

    		
    		}
    		});
        
        container.add(top, BorderLayout.NORTH);
        container.add(bottom, BorderLayout.SOUTH);
        this.setContentPane(container);

	}


	
	
		private void compresserFichier() throws IOException{
			new CompressAction();
		}
		private void decompresserFichier(){
			new DecompressAction();
		}
		
		private void quitter(){
			System.exit(0);
			}
		
}
