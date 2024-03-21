package guiUninaSocialGroup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class CreazionePostGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
    public CreazionePostGUI() {
		setBounds(100, 100, 573, 331);
		setTitle("Inserimento Post");
		contentPane = new JPanel();
		contentPane.setBackground(new Color(124, 190, 228));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel creazionePanel = new JPanel();
		creazionePanel.setBackground(new Color(124, 176, 228));
		creazionePanel.setBounds(10, 10, 536, 268);
		contentPane.add(creazionePanel);
		creazionePanel.setLayout(null);
		
		JTextArea textAreaPost = new JTextArea();
		textAreaPost.setLineWrap(true); 
		textAreaPost.setWrapStyleWord(true);
		textAreaPost.setBounds(134, 81, 296, 121);
		creazionePanel.add(textAreaPost);
		
		JLabel logoCreaGruppoLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("")).getImage();
		logoCreaGruppoLabel.setBounds(10, 10, 78, 64);
		creazionePanel.add(logoCreaGruppoLabel);
		logoCreaGruppoLabel.setIcon(new ImageIcon (img));
		
		
		JLabel lblNewLabel = new JLabel("INSERIMENTO POST");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel.setBounds(196, 25, 189, 34);
		creazionePanel.add(lblNewLabel);

		
		JLabel lblNewLabel_2 = new JLabel("Testo post");
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_2.setBounds(10, 82, 114, 20);
		creazionePanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("(min 10 car.)");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 10));
		lblNewLabel_1.setBounds(363, 212, 67, 13);
		creazionePanel.add(lblNewLabel_1);
		
		JButton btnConferma = new JButton("Conferma");
		btnConferma.setFont(new Font("Arial Black", Font.PLAIN, 12));
		btnConferma.setBounds(421, 235, 105, 23);
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String testo = textAreaPost.getText();
			
				if(testo.length() < 10) {
					JOptionPane.showMessageDialog(contentPane, "Il post deve contenere almeno 10 caratteri", "Errore creazione post", JOptionPane.ERROR_MESSAGE);
                    return;
				}
				
				boolean creazionePostRiuscita = Controller.aggiungiPost(testo,Controller.myIdUtente,Controller.idGruppoVisualizzato);
				 if (creazionePostRiuscita) {
	                    JOptionPane.showMessageDialog(contentPane, "Post creato con successo", "Conferma", JOptionPane.INFORMATION_MESSAGE);
	                    Controller.chiudiCreazionePost();
	                    Controller.aggiornaSchermataGruppo();
	                } else {
	                    JOptionPane.showMessageDialog(contentPane, "Errore durante la creazione del post", "Errore", JOptionPane.ERROR_MESSAGE);
	                }
			}
		});
		creazionePanel.add(btnConferma);

	}
}
