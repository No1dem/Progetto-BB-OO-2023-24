package guiUninaSocialGroup;

import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument.Content;

import controller.Controller;
import controller.PwDimenticataController;
import dataBaseConnection.ConnectDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class PasswordDimenticataGUI extends JFrame {
    public PasswordDimenticataGUI() {
        // Impostazioni finestra
        setTitle("Recupera Password");
        setSize(541, 360);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(140, 190, 233));
        getContentPane().setLayout(null);

        // Campo nickname
        JLabel lblNickname = new JLabel("Nickname:");
        lblNickname.setFont(new Font("Arial", Font.BOLD, 13));
        lblNickname.setBounds(50, 50, 100, 20);
        getContentPane().add(lblNickname);
        JTextField txtNickname = new JTextField();
        txtNickname.setBounds(118, 51, 221, 20);
        getContentPane().add(txtNickname);

        // Campo email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        lblEmail.setBounds(50, 100, 100, 20);
        getContentPane().add(lblEmail);
        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(118, 100, 221, 20);
        getContentPane().add(txtEmail);

        // Bottone richiedi codice di sicurezza
        JButton btnRichiediCodice = new JButton("Richiedi Codice di Sicurezza");
        btnRichiediCodice.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnRichiediCodice.setBounds(162, 145, 177, 30);
        getContentPane().add(btnRichiediCodice);
        btnRichiediCodice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ottieni il nickname e l'email inseriti dall'utente
                String nickname = txtNickname.getText();
                String email = txtEmail.getText();

                
                if (nickname.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(getContentPane(), "Inserisci nickname ed email.", "Errore di accesso", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                
                 new ConnectDB();
			     Connection conn = ConnectDB.getConnection();
			           
			     boolean utenteEsiste = PwDimenticataController.verificaEsistenzadelUtente (nickname , email , conn);
                
               

                
                if (utenteEsiste) {
                	 try {	
			    		
			    		 	
			    		 	Controller.checkDataBase(conn);
			    		 	
								
					 } catch (SQLException exc) {
							exc.printStackTrace();
					   }
                	
                    int codiceSicurezza = (int) (Math.random() * 900) + 100; 
                    JOptionPane.showMessageDialog(getContentPane(), "Il codice di sicurezza è: " + codiceSicurezza, "Codice di Sicurezza", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(getContentPane(), "Utente non trovato.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    

        // Campo per inserire il codice di sicurezza
        JLabel lblCodiceSicurezza = new JLabel("Inserisci Codice di Sicurezza:");
        lblCodiceSicurezza.setFont(new Font("Arial", Font.BOLD, 13));
        lblCodiceSicurezza.setBounds(50, 200, 200, 20);
        getContentPane().add(lblCodiceSicurezza);
        JTextField txtCodiceSicurezza = new JTextField();
        txtCodiceSicurezza.setBounds(239, 201, 100, 20);
        getContentPane().add(txtCodiceSicurezza);
        
        
        // Campo password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 13));
        lblPassword.setBounds(50, 292, 100, 20);
        getContentPane().add(lblPassword);
        
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        getContentPane().add(panel);
        
        Image imgNuovaPasswordLogo  = new ImageIcon(this.getClass().getResource("/nuovaPasswordLogo.png")).getImage();
      
        JLabel lblNuovaPasswordLogo = new JLabel();
        lblNuovaPasswordLogo.setBounds(8, 280, 32, 32);
        panel.add(lblNuovaPasswordLogo);
        lblNuovaPasswordLogo.setIcon(new ImageIcon(imgNuovaPasswordLogo)); 

        // Bottone recupera password
        JButton btnRecuperaPassword = new JButton("Recupera Password");
        btnRecuperaPassword.setBounds(162, 247, 177, 30);
        getContentPane().add(btnRecuperaPassword);
        
        
     
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PasswordDimenticataGUI frame = new PasswordDimenticataGUI();
            frame.setVisible(true);
        });
    }
}