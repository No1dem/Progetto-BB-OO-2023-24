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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class PasswordDimenticataGUI extends JFrame {
	private int codiceSicurezza;
	private Utente ut;
	private JTextField txtNickname;
	private JTextField txtEmail;
	private JTextField txtCodiceSicurezza;
	  
    public PasswordDimenticataGUI() {
    	
    	
        // Impostazioni finestra
        setTitle("Recupera Password");
        setSize(541, 360);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                resettaCampiPasswordDimenticata();
                
            }
        });
        getContentPane().setBackground(new Color(140, 190, 233));
        getContentPane().setLayout(null);

        // Campo nickname
        JLabel lblNickname = new JLabel("Nickname:");
        lblNickname.setFont(new Font("Arial", Font.BOLD, 13));
        lblNickname.setBounds(87, 84, 88, 20);
        getContentPane().add(lblNickname);
        txtNickname = new JTextField();
        txtNickname.setBounds(202, 85, 221, 20);
        getContentPane().add(txtNickname);

        // Campo email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        lblEmail.setBounds(116, 114, 100, 20);
        getContentPane().add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(202, 115, 221, 20);
        getContentPane().add(txtEmail);

        // Bottone richiedi codice di sicurezza
        JButton btnRichiediCodice = new JButton("Richiedi ");
        btnRichiediCodice.setFont(new Font("Arial Black", Font.PLAIN, 12));
        btnRichiediCodice.setBounds(257, 145, 116, 20);
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
        txtCodiceSicurezza = new JTextField();
        txtCodiceSicurezza.setBounds(266, 201, 100, 20);
        getContentPane().add(txtCodiceSicurezza);
        
        Image imgNuovaPasswordLogo  = new ImageIcon(this.getClass().getResource("/nuovaPasswordLogo.png")).getImage();
        

        // Bottone recupera password
        JButton btnRecuperaPassword = new JButton("Recupera Password");
        btnRecuperaPassword.setFont(new Font("Arial Black", Font.PLAIN, 12));
        btnRecuperaPassword.setBounds(235, 245, 170, 20);
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
          
          JLabel lblNewLabel = new JLabel("RECUPERA PASSWORD");
          lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
          lblNewLabel.setBounds(170, 34, 183, 40);
          getContentPane().add(lblNewLabel);
          
          JLabel lblNewLabel_1 = new JLabel("Codice di sicurezza:");
          lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
          lblNewLabel_1.setBounds(29, 150, 133, 13);
          getContentPane().add(lblNewLabel_1);
        
        
     
    }

    public void resettaCampiPasswordDimenticata() {
    	txtNickname.setText("");
        txtEmail.setText("");
        txtCodiceSicurezza.setText("");
    }
}