package guiUninaSocialGroup;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dataBaseConnection.ConnectDB;

public class registrazioneUtenteGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldNome;
    private JTextField textFieldCognome;
    private JTextField textFieldNickname;
    private JPasswordField passwordField;
    private JButton btnRegistrati;
    private JTextField textFieldBiografia;
    private JTextField textFieldEmail;

 
    public registrazioneUtenteGUI() {
    	setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                resettaCampiRegistrazioneUtente();
            }
        });
        setBounds(100, 100, 496, 443);
        setTitle("Registrazione Utente");
        contentPane = new JPanel();
        contentPane.setBackground(new Color(172, 202, 232));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel registrazionePanel = new JPanel();
        registrazionePanel.setBackground(new Color(148, 190, 233));
        registrazionePanel.setBounds(10, 10, 461, 386);
        contentPane.add(registrazionePanel);
        registrazionePanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Nome");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel.setBounds(10, 111, 60, 20);
        registrazionePanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Cognome");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_1.setBounds(10, 144, 90, 20);
        registrazionePanel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Nickname");
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_2.setBounds(10, 183, 90, 20);
        registrazionePanel.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Password");
        lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_3.setBounds(10, 300, 90, 20);
        registrazionePanel.add(lblNewLabel_3);

        JLabel lblNewLabel_10 = new JLabel("Biografia");
        lblNewLabel_10.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_10.setBounds(10, 221, 74, 20);
        registrazionePanel.add(lblNewLabel_10);
        
        JLabel lblNewLabel_9 = new JLabel("Email");
        lblNewLabel_9.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_9.setBounds(10, 266, 46, 13);
        registrazionePanel.add(lblNewLabel_9);

        textFieldNome = new JTextField();
        textFieldNome.setBounds(150, 109, 156, 20);
        registrazionePanel.add(textFieldNome);
        textFieldNome.setColumns(10);

        textFieldCognome = new JTextField();
        textFieldCognome.setBounds(150, 146, 156, 20);
        registrazionePanel.add(textFieldCognome);
        textFieldCognome.setColumns(10);

        textFieldNickname = new JTextField();
        textFieldNickname.setBounds(150, 185, 156, 20);
        registrazionePanel.add(textFieldNickname);
        textFieldNickname.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 302, 156, 20);
        registrazionePanel.add(passwordField);

        textFieldBiografia = new JTextField();
        textFieldBiografia.setBounds(150, 223, 156, 20);
        registrazionePanel.add(textFieldBiografia);
        textFieldBiografia.setColumns(10);
        
        textFieldEmail = new JTextField();
        textFieldEmail.setBounds(150, 263, 156, 20);
        registrazionePanel.add(textFieldEmail);
        textFieldEmail.setColumns(10);

        btnRegistrati = new JButton("Registrati");
        btnRegistrati.setFont(new Font("Arial Black", Font.PLAIN, 12));
        btnRegistrati.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldNome.getText();
                String cognome = textFieldCognome.getText();
                String nickname = textFieldNickname.getText();
                String password = new String(passwordField.getPassword());
                String biografia = textFieldBiografia.getText();
                String email = textFieldEmail.getText();

                if (nome.isEmpty() || cognome.isEmpty() || nickname.isEmpty() || password.isEmpty() || biografia.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Inserisci tutti i dati necessari.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!nome.matches("^[a-zA-Z ]+$")) {
                    JOptionPane.showMessageDialog(contentPane, "Il nome può contenere solo caratteri e spazi.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!cognome.matches("^[a-zA-Z ]+$")) {
                    JOptionPane.showMessageDialog(contentPane, "Il cognome può contenere solo caratteri e spazi.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (nome.length() > 20) {
                    JOptionPane.showMessageDialog(contentPane, "Il nome può contenere al massimo 20 caratteri.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (cognome.length() > 20) {
                    JOptionPane.showMessageDialog(contentPane, "Il cognome può contenere al massimo 20 caratteri.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (nickname.length() > 20) {
                    JOptionPane.showMessageDialog(contentPane, "Il nickname può contenere massimo 20 caratteri.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (nickname.length() < 5) {
                    JOptionPane.showMessageDialog(contentPane, "Il nickname deve contenere almeno 5 caratteri.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (biografia.length() > 350) {
                    JOptionPane.showMessageDialog(contentPane, "La biografia può contenere al massimo 350 caratteri.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (email.length() > 50) {
                    JOptionPane.showMessageDialog(contentPane, "L'email può contenere al massimo 50 caratteri.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (email.length() < 10) {
                    JOptionPane.showMessageDialog(contentPane, "L'email deve contenere almeno 10 caratteri.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (password.length() > 30) {
                    JOptionPane.showMessageDialog(contentPane, "La password può contenere massimo 30 caratteri.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (password.length() < 6) {
                    JOptionPane.showMessageDialog(contentPane, "La password deve contenere almeno 6 caratteri.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                
                boolean registrazioneRiuscita = Controller.registraUtente(nome, cognome, nickname, biografia, email, password);

                if (registrazioneRiuscita) {
                    JOptionPane.showMessageDialog(contentPane, "Registrazione avvenuta con successo", "Conferma", JOptionPane.INFORMATION_MESSAGE);
                    Controller.chiudiRegistrazioneUtente();
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Errore durante la registrazione", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnRegistrati.setBounds(150, 353, 156, 23);
        registrazionePanel.add(btnRegistrati);

        JLabel lblNewLabel_4 = new JLabel("(Max 20 car.)");
        lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 10));
        lblNewLabel_4.setBounds(350, 149, 90, 13);
        registrazionePanel.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("(Max 20 car.)");
        lblNewLabel_5.setFont(new Font("Arial", Font.BOLD, 10));
        lblNewLabel_5.setBounds(350, 116, 90, 13);
        registrazionePanel.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("(Max 12 car.)");
        lblNewLabel_6.setFont(new Font("Arial", Font.BOLD, 10));
        lblNewLabel_6.setBounds(350, 188, 90, 13);
        registrazionePanel.add(lblNewLabel_6);

        JLabel lblNewLabel_7 = new JLabel("(Max 30 car.)");
        lblNewLabel_7.setFont(new Font("Arial", Font.BOLD, 10));
        lblNewLabel_7.setBounds(350, 305, 90, 13);
        registrazionePanel.add(lblNewLabel_7);

        JLabel lblNewLabel_8 = new JLabel("REGISTRAZIONE UTENTE");
        lblNewLabel_8.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_8.setBounds(134, 29, 198, 20);
        registrazionePanel.add(lblNewLabel_8);
        
        JLabel lblNewLabel_11 = new JLabel("(Max 350 car.)");
        lblNewLabel_11.setFont(new Font("Arial", Font.BOLD, 10));
        lblNewLabel_11.setBounds(350, 226, 90, 13);
        registrazionePanel.add(lblNewLabel_11);
        
        JLabel lblNewLabel_12 = new JLabel("(Max 50 car.)");
        lblNewLabel_12.setFont(new Font("Arial", Font.BOLD, 10));
        lblNewLabel_12.setBounds(350, 267, 74, 13);
        registrazionePanel.add(lblNewLabel_12);
        
        JLabel logoRegistrazione = new JLabel("");
        Image imgRegistrazione = new ImageIcon(this.getClass().getResource("/registrazione.png")).getImage();
		logoRegistrazione.setIcon(new ImageIcon(imgRegistrazione));
        logoRegistrazione.setBounds(10, 0, 74, 78);
        registrazionePanel.add(logoRegistrazione);

    }
    
    
    public void resettaCampiRegistrazioneUtente() {
    	textFieldNome.setText("");
    	textFieldCognome.setText("");
    	textFieldBiografia.setText("");
    	textFieldEmail.setText("");
    	passwordField.setText("");
    	textFieldNickname.setText("");	
    }
}
