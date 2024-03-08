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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JTextField;
import javax.swing.JButton;

public class CreazioneGruppoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreazioneGruppoGUI frame = new CreazioneGruppoGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreazioneGruppoGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 570, 371);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(148, 190, 233));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Nome Gruppo");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel.setBounds(25, 30, 114, 20);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Descrizione Gruppo");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_1.setBounds(25, 70, 157, 20);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Tag Gruppo");
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_2.setBounds(25, 110, 90, 20);
        contentPane.add(lblNewLabel_2);

        textField = new JTextField();
        textField.setBounds(237, 30, 250, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(237, 70, 250, 20);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(237, 112, 250, 20);
        contentPane.add(textField_2);
        textField_2.setColumns(10);

        JButton btnConferma = new JButton("Conferma");
        btnConferma.setFont(new Font("Arial", Font.PLAIN, 13));
        btnConferma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomeGruppo = textField.getText();
                String descrizioneGruppo = textField_1.getText();
                String tagGruppo = textField_2.getText();
                if (nomeGruppo.isEmpty() || descrizioneGruppo.isEmpty() || tagGruppo.isEmpty()) {
		            JOptionPane.showMessageDialog(contentPane, "Inserire tutti i dati necessari.", "Errore creazione", JOptionPane.ERROR_MESSAGE);
		            return; 
		     }
                
			    
                boolean creazioneRiuscita = Controller.creaGruppo(Controller.myIdUtente, nomeGruppo, tagGruppo, descrizioneGruppo);
                
                if (creazioneRiuscita) {
                    JOptionPane.showMessageDialog(contentPane, "Gruppo creato con successo", "Conferma", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } 
                else {
                    JOptionPane.showMessageDialog(contentPane, "Errore durante la creazione del gruppo.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnConferma.setBounds(450, 301, 96, 23);
        contentPane.add(btnConferma);
    }
}
