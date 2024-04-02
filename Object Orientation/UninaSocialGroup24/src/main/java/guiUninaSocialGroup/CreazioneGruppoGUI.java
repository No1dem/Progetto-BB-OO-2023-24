package guiUninaSocialGroup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashSet;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CreazioneGruppoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField tagField;
	private HashSet<String> tagSet = new HashSet<>(); 

	/**
	 * Create the frame.
	 */
	public CreazioneGruppoGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 570, 324);
		setTitle("Creazione Gruppo");
		contentPane = new JPanel();
		contentPane.setBackground(new Color(148, 190, 233));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel creazionePanel = new JPanel();
		creazionePanel.setBackground(new Color(124, 176, 228));
		creazionePanel.setBounds(10, 10, 536, 268);
		contentPane.add(creazionePanel);
		creazionePanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nome Gruppo");
		lblNewLabel.setBounds(10, 82, 114, 20);
		creazionePanel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));

		JLabel lblNewLabel_1 = new JLabel("Descrizione Gruppo");
		lblNewLabel_1.setBounds(10, 135, 157, 20);
		creazionePanel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 15));

		JLabel lblNewLabel_2 = new JLabel("Tag Gruppo");
		lblNewLabel_2.setBounds(10, 187, 90, 20);
		creazionePanel.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 15));

		textField = new JTextField();
		textField.setBounds(223, 84, 250, 20);
		creazionePanel.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("(Max 30 car.)");
		lblNewLabel_4.setBounds(223, 103, 114, 13);
		creazionePanel.add(lblNewLabel_4);
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 10));

		textField_1 = new JTextField();
		textField_1.setBounds(223, 137, 250, 20);
		creazionePanel.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("(Max 150 car.)");
		lblNewLabel_3.setBounds(223, 156, 90, 13);
		creazionePanel.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 10));

		tagField = new JTextField();
		tagField.setBounds(223, 187, 133, 22);
		creazionePanel.add(tagField);
		tagField.setColumns(10);

		JButton btnAggiungiTag = new JButton("Aggiungi");
		btnAggiungiTag.setBounds(376, 186, 97, 22);
		creazionePanel.add(btnAggiungiTag);
		btnAggiungiTag.setFont(new Font("Arial Black", Font.PLAIN, 12));

		JButton btnConferma = new JButton("Conferma");
		btnConferma.setBounds(421, 235, 105, 23);
		creazionePanel.add(btnConferma);
		btnConferma.setFont(new Font("Arial Black", Font.PLAIN, 12));

		JLabel tagLabel = new JLabel("Aggiungi un tag (Max 20 car.)");
		tagLabel.setBounds(223, 208, 162, 13);
		creazionePanel.add(tagLabel);
		tagLabel.setFont(new Font("Arial", Font.BOLD, 10));

		JLabel lblNewLabel_5 = new JLabel("CREAZIONE GRUPPO");
		lblNewLabel_5.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_5.setBounds(196, 25, 189, 34);
		creazionePanel.add(lblNewLabel_5);

		JLabel logoCreaGruppoLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/creaGruppo.png")).getImage();
		logoCreaGruppoLabel.setBounds(10, 10, 78, 64);
		logoCreaGruppoLabel.setIcon(new ImageIcon (img));
		creazionePanel.add(logoCreaGruppoLabel);
		
		
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomeGruppo = textField.getText();
				String descrizioneGruppo = textField_1.getText();
				StringBuilder tagBuilder = new StringBuilder();
				
				
				for (String tag : tagSet) {
					tagBuilder.append(tag).append(","); 
				}
				String tagGruppo = tagBuilder.toString();
				if (tagGruppo.endsWith(",")) {
					tagGruppo = tagGruppo.substring(0, tagGruppo.length() - 1); 
				}
				if (nomeGruppo.isEmpty() || descrizioneGruppo.isEmpty() ) {
					JOptionPane.showMessageDialog(contentPane, "Inserire tutti i dati necessari.", "Errore creazione", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else if (tagSet.isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "Inserire almeno un tag.", "Errore creazione", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (nomeGruppo.length() > 30) {
					JOptionPane.showMessageDialog(contentPane, "Il nome del gruppo può contenere solo 30 caratteri.", "Errore creazione", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (descrizioneGruppo.length() > 150) {
					JOptionPane.showMessageDialog(contentPane, "La descrizione del gruppo può contenere solo 150 caratteri.", "Errore creazione", JOptionPane.ERROR_MESSAGE);
					return;
				}

				boolean creazioneRiuscita = Controller.creaGruppo(Controller.myIdUtente, nomeGruppo, tagGruppo, descrizioneGruppo);
				tagSet.clear();

				if (creazioneRiuscita) {
					JOptionPane.showMessageDialog(contentPane, "Gruppo creato con successo", "Conferma", JOptionPane.INFORMATION_MESSAGE);
					Controller.aggiornaHome();;
				} else {
					JOptionPane.showMessageDialog(contentPane, "Errore durante la creazione del gruppo.", "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAggiungiTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nuovoTag = tagField.getText().toUpperCase(); 
				if (nuovoTag.isEmpty()) {
					if (tagSet.isEmpty()) {
						JOptionPane.showMessageDialog(contentPane, "Inserire almeno un tag.", "Errore creazione", JOptionPane.ERROR_MESSAGE);
						return;	
					}
				}
				
				if (!nuovoTag.matches("^.*$")) { 
					JOptionPane.showMessageDialog(contentPane, "Il tag deve contenere solo caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (nuovoTag.length() > 20 ) {
					JOptionPane.showMessageDialog(contentPane, "Il tag deve contenere massimo 20 caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (tagSet.contains(nuovoTag)) { 
					JOptionPane.showMessageDialog(contentPane, "Il tag è già stato inserito.", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				tagSet.add(nuovoTag); 
				tagField.setText("");
			}
		});
	}
}
