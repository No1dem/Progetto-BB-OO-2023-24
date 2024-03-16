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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JButton;

public class CreazionePostGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreazionePostGUI frame = new CreazionePostGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CreazionePostGUI() {
		setBounds(100, 100, 542, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textAreaPost = new JTextArea();
		textAreaPost.setBounds(36, 41, 447, 256);
		contentPane.add(textAreaPost);
		
		JButton btnConferma = new JButton("Conferma");
		btnConferma.setBounds(387, 307, 96, 21);
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String testo = textAreaPost.getText();
				LocalDate data = LocalDate.now();
				LocalTime ora = LocalTime.now();
				
				if(testo.length() < 10) {
					JOptionPane.showMessageDialog(contentPane, "Il post deve contenere almeno 10 caratteri", "Errore creazione post", JOptionPane.ERROR_MESSAGE);
                    return;
				}
				
				boolean creazionePostRiuscita = Controller.aggiungiPost(testo,data,ora,Controller.myIdUtente,Controller.idGruppoVisualizzato);
				 if (creazionePostRiuscita) {
	                    JOptionPane.showMessageDialog(contentPane, "Post creato con successo", "Conferma", JOptionPane.INFORMATION_MESSAGE);
	                    Controller.chiudiCreazionePost();
	                } else {
	                    JOptionPane.showMessageDialog(contentPane, "Errore durante la creazione del post", "Errore", JOptionPane.ERROR_MESSAGE);
	                }
			}
		});
		contentPane.add(btnConferma);
		
		JLabel lblNewLabel = new JLabel("Inserisci il testo del tuo post:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(36, 10, 195, 21);
		contentPane.add(lblNewLabel);
	}
}
