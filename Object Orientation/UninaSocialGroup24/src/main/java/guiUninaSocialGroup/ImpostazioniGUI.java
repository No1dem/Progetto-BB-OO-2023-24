package guiUninaSocialGroup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import classiDAO.Utente;
import controller.Controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import java.awt.Font;

public class ImpostazioniGUI extends JFrame {

    private JPanel contentPane;
    private JTextField urlFotoProfiloTextField;
    private JTextField nuovoNickTextField;
    private JPasswordField passwordModificaNickField;
    private JTextField nickEliminaAccountTextField;
    private JPasswordField passwordEliminaAccountField;

    public ImpostazioniGUI() {
    	setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                resettaCampiImpostazioni();
                
            }
        });
        setBounds(100, 100, 403, 535);
        setTitle("Impostazioni Utente");
        contentPane = new JPanel();
        contentPane.setBackground(new Color(226, 235, 248));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(148, 190, 233));
        panel.setBounds(26, 170, 325, 162);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JButton modificaNicknameButton = new JButton("Modifica Nickname");
        modificaNicknameButton.setBounds(66, 129, 200, 24);
        panel.add(modificaNicknameButton);
        modificaNicknameButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
        
        nuovoNickTextField = new JTextField();
        nuovoNickTextField.setColumns(10);
        nuovoNickTextField.setBounds(132, 42, 169, 19);
        panel.add(nuovoNickTextField);
        
        JLabel lblModificaNickname = new JLabel("MODIFICA NICKNAME");
        lblModificaNickname.setHorizontalAlignment(SwingConstants.CENTER);
        lblModificaNickname.setFont(new Font("Arial", Font.BOLD, 15));
        lblModificaNickname.setBounds(26, 10, 275, 18);
        panel.add(lblModificaNickname);
        
        JLabel lbNewLabel_7 = new JLabel("Nuovo Nickname:");
        lbNewLabel_7.setFont(new Font("Arial", Font.BOLD, 12));
        lbNewLabel_7.setBounds(16, 45, 106, 13);
        panel.add(lbNewLabel_7);
        
        JLabel lblNewLabel_2 = new JLabel("Password:");
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 12));
        lblNewLabel_2.setBounds(66, 85, 60, 13);
        panel.add(lblNewLabel_2);
        
        passwordModificaNickField = new JPasswordField();
        passwordModificaNickField.setBounds(132, 82, 169, 19);
        panel.add(passwordModificaNickField);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(148, 190, 233));
        panel_1.setBounds(26, 10, 325, 138);
        contentPane.add(panel_1);
        panel_1.setLayout(null);
        
        urlFotoProfiloTextField = new JTextField();
        urlFotoProfiloTextField.setBounds(22, 38, 275, 19);
        panel_1.add(urlFotoProfiloTextField);
        urlFotoProfiloTextField.setColumns(10);
        
        JButton modificaImmagineButton = new JButton("Carica");
        modificaImmagineButton.setBounds(197, 67, 100, 27);
        panel_1.add(modificaImmagineButton);
        modificaImmagineButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
        modificaImmagineButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                   
                        String imageUrl = urlFotoProfiloTextField.getText();
                        try {
                        
                            URL url = new URL(imageUrl);
                            
                            String nicknameUtente = Controller.utenteDAO.getUtenteFromArrayListById(Controller.myIdUtente).getNickname(); 
                            
                            Controller.utenteDAO.updateUrlFotoProfiloByNickname(nicknameUtente, imageUrl);
                            
                            JOptionPane.showMessageDialog(ImpostazioniGUI.this, "Immagine caricata con successo.","Caricamento Immagine", JOptionPane.INFORMATION_MESSAGE);
                            
                            Controller.aggiornaHome();
                            
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(ImpostazioniGUI.this, "Impossibile caricare l'immagine dall'URL fornito.\n "
                            		+ "Verifica la tua connessione ad internet.", "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
        
        JLabel titoloModificaUrlLabel = new JLabel("MODIFICA IMMAGINE DEL PROFILO :");
        titoloModificaUrlLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titoloModificaUrlLabel.setBounds(22, 10, 275, 18);
        panel_1.add(titoloModificaUrlLabel);
        titoloModificaUrlLabel.setFont(new Font("Arial", Font.BOLD, 15));
        
        JButton eliminaImmagineProfiloButton = new JButton("Elimina");
        eliminaImmagineProfiloButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		String Nickname = Controller.utenteDAO.getUtenteFromArrayListById(Controller.myIdUtente).getNickname();
        		Controller.utenteDAO.updateUrlFotoProfiloByNickname(Nickname,"");
                JOptionPane.showMessageDialog(ImpostazioniGUI.this, "Eliminazione avvenuta con successo", "Eliminazione foto profilo", JOptionPane.INFORMATION_MESSAGE);
                Controller.aggiornaHome();
        	}
        });
        eliminaImmagineProfiloButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
        eliminaImmagineProfiloButton.setBounds(197, 104, 100, 25);
        panel_1.add(eliminaImmagineProfiloButton);
        
        JLabel lblNewLabel = new JLabel("Cancella la foto profilo:");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 12));
        lblNewLabel.setBounds(57, 111, 130, 13);
        panel_1.add(lblNewLabel);
        
        JLabel lblNewLabel_3 = new JLabel("Carica l'URL:");
        lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 12));
        lblNewLabel_3.setBounds(113, 75, 74, 13);
        panel_1.add(lblNewLabel_3);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(148, 190, 233));
        panel_2.setBounds(26, 355, 326, 132);
        contentPane.add(panel_2);
        panel_2.setLayout(null);
        
        JLabel titoloEliminaAccountLabel = new JLabel("ELIMINA ACCOUNT");
        titoloEliminaAccountLabel.setFont(new Font("Arial", Font.BOLD, 15));
        titoloEliminaAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titoloEliminaAccountLabel.setBounds(88, 10, 157, 13);
        panel_2.add(titoloEliminaAccountLabel);
        
        nickEliminaAccountTextField = new JTextField();
        nickEliminaAccountTextField.setBounds(136, 33, 165, 19);
        panel_2.add(nickEliminaAccountTextField);
        nickEliminaAccountTextField.setColumns(10);
        
        passwordEliminaAccountField = new JPasswordField();
        passwordEliminaAccountField.setBounds(136, 62, 165, 19);
        panel_2.add(passwordEliminaAccountField);
        
        JLabel lblNewLabel_4 = new JLabel("Nickname:");
        lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 12));
        lblNewLabel_4.setBounds(61, 36, 65, 13);
        panel_2.add(lblNewLabel_4);
        
        JLabel lblNewLabel_5 = new JLabel("Password:");
        lblNewLabel_5.setFont(new Font("Arial", Font.BOLD, 12));
        lblNewLabel_5.setBounds(61, 65, 65, 13);
        panel_2.add(lblNewLabel_5);
        
        JButton eliminaImmagineProfiloButton_1 = new JButton("Elimina");
        eliminaImmagineProfiloButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String nickname = nickEliminaAccountTextField.getText();
        		String password = new String (passwordEliminaAccountField.getPassword());
        		Utente utente = Controller.utenteDAO.getUtenteFromArrayListById(Controller.myIdUtente);
        		
        		if (nickname.isEmpty() || password.isEmpty()) {
		            JOptionPane.showMessageDialog(contentPane, "Inserisci nickname e password.", "Errore di accesso", JOptionPane.ERROR_MESSAGE);
		            return; 
        		}
        		
        		if (nickname.equals(utente.getNickname())&& password.equals(utente.getPassword())) {
		            JOptionPane.showMessageDialog(contentPane, "Utente eliminato con successo", "Eliminazione utente", JOptionPane.INFORMATION_MESSAGE);
        			Controller.utenteDAO.deleteUtente(utente);
        			Controller.tornaAllaSchermataLogin();
        		}
        		else {
		            JOptionPane.showMessageDialog(contentPane, "I dati inseriti non sono corretti.", "Errore eliminazione", JOptionPane.ERROR_MESSAGE);
        		}
        			
        	}
        });
        eliminaImmagineProfiloButton_1.setFont(new Font("Arial Black", Font.PLAIN, 12));
        eliminaImmagineProfiloButton_1.setBounds(112, 97, 100, 25);
        panel_2.add(eliminaImmagineProfiloButton_1);
        modificaNicknameButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String nuovoNick = nuovoNickTextField.getText();
        		String password = new String (passwordModificaNickField.getPassword());
        		Utente utente = Controller.utenteDAO.getUtenteFromArrayListById(Controller.myIdUtente);        		
        	
        		if (nuovoNick.isEmpty() || password.isEmpty()) {
        			JOptionPane.showMessageDialog(ImpostazioniGUI.this, "Inserire tutti i dati", "Errore inserimento", JOptionPane.ERROR_MESSAGE);
                    return;
        		}
        		
        		if (nuovoNick.length() > 20) {
                    JOptionPane.showMessageDialog(ImpostazioniGUI.this, "Il nickname deve contenere massimo 20 caratteri", "Errore lunghezza Nickname", JOptionPane.ERROR_MESSAGE);
                    return;
        		}
        		
        		if (nuovoNick.length() < 5) {
                    JOptionPane.showMessageDialog(ImpostazioniGUI.this, "Il nickname deve contenere almeno 5 caratteri", "Errore lunghezza Nickname", JOptionPane.ERROR_MESSAGE);
                    return;
        		}
        		
        		if (password.equals(utente.getPassword())){
        				if (!Controller.controlloEsistenzaNickname(nuovoNick)) {
        					Controller.utenteDAO.updateNicknameByIdUtente(nuovoNick, Controller.myIdUtente);
        					JOptionPane.showMessageDialog(ImpostazioniGUI.this, "Il nickname è stato modificato con successo.\nVerrai reindirizzato alla schermata di login.", "Modifica effettuata Nickname", JOptionPane.INFORMATION_MESSAGE);
            				Controller.tornaAllaSchermataLogin();
        				}
        				else
        					JOptionPane.showMessageDialog(ImpostazioniGUI.this, "Il nickname appartiene già ad un utente.", "Errore modifica Nickname", JOptionPane.ERROR_MESSAGE);

         		}
        		else
                    JOptionPane.showMessageDialog(ImpostazioniGUI.this, "Password errata!", "Errore inserimento", JOptionPane.ERROR_MESSAGE);

        		
        		 
        			
        	}
        });
               
        setLocationRelativeTo(null); 
    }
    
    
    public void resettaCampiImpostazioni() {
        urlFotoProfiloTextField.setText("");
        nuovoNickTextField.setText("");
        passwordModificaNickField.setText("");
        nickEliminaAccountTextField.setText("");
        passwordEliminaAccountField.setText("");
    }

    
}
