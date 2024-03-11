package guiUninaSocialGroup;

import javax.swing.border.EmptyBorder;

import javax.swing.text.AbstractDocument.Content;

import classiDAO.Utente;
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
	private int codiceSicurezza;
	private Utente ut;
	  
    public PasswordDimenticataGUI() {
    	
    	
        // Impostazioni finestra
        setTitle("Recupera Password");
        setSize(541, 360);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(140, 190, 233));
        getContentPane().setLayout(null);

        // Campo nickname
        JLabel lblNickname = new JLabel("Nickname:");
        lblNickname.setFont(new Font("Arial", Font.BOLD, 13));
        lblNickname.setBounds(92, 84, 100, 20);
        getContentPane().add(lblNickname);
        JTextField txtNickname = new JTextField();
        txtNickname.setBounds(202, 85, 221, 20);
        getContentPane().add(txtNickname);

        // Campo email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        lblEmail.setBounds(116, 114, 100, 20);
        getContentPane().add(lblEmail);
        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(202, 115, 221, 20);
        getContentPane().add(txtEmail);

        // Bottone richiedi codice di sicurezza
        JButton btnRichiediCodice = new JButton("Richiedi Codice di Sicurezza");
        btnRichiediCodice.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnRichiediCodice.setBounds(228, 145, 177, 30);
        getContentPane().add(btnRichiediCodice);
        btnRichiediCodice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
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
                	 		ut= Controller.utenteDAO.getUtenteFromArrayListByNickname(nickname);
                         codiceSicurezza = (int) (Math.random() * 900) + 100; 
                   
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
        txtCodiceSicurezza.setBounds(266, 201, 100, 20);
        getContentPane().add(txtCodiceSicurezza);
        
        Image imgNuovaPasswordLogo  = new ImageIcon(this.getClass().getResource("/nuovaPasswordLogo.png")).getImage();
        

        // Bottone recupera password
        JButton btnRecuperaPassword = new JButton("Recupera Password");
        btnRecuperaPassword.setBounds(228, 249, 177, 30);
        getContentPane().add(btnRecuperaPassword);
       
        
        btnRecuperaPassword.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
                
                 String codiceInserito = txtCodiceSicurezza.getText();
                 if (codiceInserito.isEmpty()) {
                     JOptionPane.showMessageDialog(getContentPane(), "Inserisci il codice di sicurezza.", "Errore", JOptionPane.ERROR_MESSAGE);
                     return;
                 }

                 if (codiceInserito.equals(String.valueOf(codiceSicurezza))) {
                     
                     String password = ut.getPassword(); 
                     JPanel panelPassword = new JPanel();
                     panelPassword.add(new JLabel("Password: " + password));
                     JOptionPane.showMessageDialog(getContentPane(), panelPassword, "Password Recuperata", JOptionPane.INFORMATION_MESSAGE);
                 } else {
                     
                     JOptionPane.showMessageDialog(getContentPane(), "Codice di sicurezza non corretto.", "Errore", JOptionPane.ERROR_MESSAGE);
                 }
             }
         });
     
            
        
    
    
          JLabel lblNuovaPasswordLogo = new JLabel();
          lblNuovaPasswordLogo.setBounds(10, 10, 72, 78);
          getContentPane().add(lblNuovaPasswordLogo);
          lblNuovaPasswordLogo.setIcon(new ImageIcon(imgNuovaPasswordLogo));
          
          
          // Campo password
          JLabel lblPassword = new JLabel("Password:");
          lblPassword.setBounds(116, 254, 100, 20);
          getContentPane().add(lblPassword);
          lblPassword.setFont(new Font("Arial", Font.BOLD, 13));
          
          JLabel lblNewLabel = new JLabel("RECUPERA PASSWORD");
          lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
          lblNewLabel.setBounds(167, 34, 183, 40);
          getContentPane().add(lblNewLabel);
        
        
     
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PasswordDimenticataGUI frame = new PasswordDimenticataGUI();
            frame.setVisible(true);
        });
    }
}