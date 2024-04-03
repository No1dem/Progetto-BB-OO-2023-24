package guiUninaSocialGroup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controller.Controller;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class CreazionePostGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextArea textAreaPost;
	
    public CreazionePostGUI() {
    	setResizable(false);
    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 573, 328);
		setTitle("Inserimento Post");
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Controller.chiudiCreazionePost();
            }
        });
		contentPane = new JPanel();
		contentPane.setBackground(new Color(172, 202, 232));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel creazionePanel = new JPanel();
		creazionePanel.setBackground(new Color(148, 190, 233));
		creazionePanel.setBounds(10, 10, 536, 271);
		contentPane.add(creazionePanel);
		creazionePanel.setLayout(null);
		
		JPanel testoPostPanel = new JPanel();
		testoPostPanel.setBackground(new Color(255, 255, 255));
		testoPostPanel.setBounds(67, 41, 412, 187);
		creazionePanel.add(testoPostPanel);
			
		textAreaPost = new JTextArea();
		textAreaPost.setLineWrap(true); 
		textAreaPost.setWrapStyleWord(true);
		
		JScrollPane scrollPane = new JScrollPane(textAreaPost);
		scrollPane.setBounds(67, 41, 412, 187);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
		creazionePanel.add(scrollPane);
		
		JLabel logoCreaGruppoLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/inserisciPost.png")).getImage();
		logoCreaGruppoLabel.setBounds(10, 32, 59, 60);
		creazionePanel.add(logoCreaGruppoLabel);
		logoCreaGruppoLabel.setIcon(new ImageIcon (img));
		
		
		JLabel lblNewLabel = new JLabel("SCRIVI UN POST");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel.setBounds(67, 12, 162, 23);
		creazionePanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("(Minimo 10 caratteri)");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 10));
		lblNewLabel_1.setBounds(375, 18, 104, 13);
		creazionePanel.add(lblNewLabel_1);
		
		JButton btnConferma = new JButton("Conferma");
		btnConferma.setFont(new Font("Arial Black", Font.PLAIN, 12));
		btnConferma.setBounds(375, 238, 105, 23);
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
    	
    	
    public void resettaCampoCreazionePost() {
    	textAreaPost.setText("");
    }
}
