package guiUninaSocialGroup;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

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
    private JTextField textField;
    private JTextField textField_1;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    registrazioneUtenteGUI frame = new registrazioneUtenteGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public registrazioneUtenteGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 496, 443);
        setTitle("Registrazione Utente");
        contentPane = new JPanel();
        contentPane.setBackground(new Color(148, 190, 233));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel registrazionePanel = new JPanel();
        registrazionePanel.setBackground(new Color(124, 176, 228));
        registrazionePanel.setBounds(10, 10, 461, 386);
        contentPane.add(registrazionePanel);
        registrazionePanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Nome");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel.setBounds(10, 75, 60, 20);
        registrazionePanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Cognome");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_1.setBounds(10, 124, 90, 20);
        registrazionePanel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Nickname");
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_2.setBounds(10, 169, 90, 20);
        registrazionePanel.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Password");
        lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_3.setBounds(10, 300, 90, 20);
        registrazionePanel.add(lblNewLabel_3);

        JLabel lblNewLabel_10 = new JLabel("Biografia");
        lblNewLabel_10.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_10.setBounds(10, 223, 74, 13);
        registrazionePanel.add(lblNewLabel_10);
        
        JLabel lblNewLabel_9 = new JLabel("Email");
        lblNewLabel_9.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_9.setBounds(10, 266, 46, 13);
        registrazionePanel.add(lblNewLabel_9);

        textFieldNome = new JTextField();
        textFieldNome.setBounds(150, 77, 156, 20);
        registrazionePanel.add(textFieldNome);
        textFieldNome.setColumns(10);

        textFieldCognome = new JTextField();
        textFieldCognome.setBounds(150, 126, 156, 20);
        registrazionePanel.add(textFieldCognome);
        textFieldCognome.setColumns(10);

        textFieldNickname = new JTextField();
        textFieldNickname.setBounds(150, 171, 156, 20);
        registrazionePanel.add(textFieldNickname);
        textFieldNickname.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 300, 156, 20);
        registrazionePanel.add(passwordField);

        textField = new JTextField();
        textField.setBounds(150, 220, 156, 19);
        registrazionePanel.add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setBounds(150, 264, 156, 19);
        registrazionePanel.add(textField_1);
        textField_1.setColumns(10);

        btnRegistrati = new JButton("Registrati");
        btnRegistrati.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldNome.getText();
                String cognome = textFieldCognome.getText();
                String nickname = textFieldNickname.getText();
                String password = new String(passwordField.getPassword());
                String biografia = textField.getText();
                String email = textField_1.getText();

                if (nome.isEmpty() || cognome.isEmpty() || nickname.isEmpty() || password.isEmpty() || biografia.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Inserisci tutti i dati necessari.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (nickname.length() > 20) {
                    JOptionPane.showMessageDialog(contentPane, "Il nickname può contenere massimo 20 caratteri.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (password.length() > 30) {
                    JOptionPane.showMessageDialog(contentPane, "La password può contenere massimo 30 caratteri.", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                boolean registrazioneRiuscita = Controller.registraUtente(nome, cognome, nickname, biografia, email, password);

                if (registrazioneRiuscita) {
                    JOptionPane.showMessageDialog(contentPane, "Registrazione avvenuta con successo", "Conferma", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Chiude la finestra di registrazione dopo il successo
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Errore durante la registrazione", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnRegistrati.setBounds(150, 353, 156, 23);
        registrazionePanel.add(btnRegistrati);

        JLabel lblNewLabel_4 = new JLabel("(Max 20 car.)");
        lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 10));
        lblNewLabel_4.setBounds(350, 80, 90, 13);
        registrazionePanel.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("(Max 20 car.)");
        lblNewLabel_5.setFont(new Font("Arial", Font.BOLD, 10));
        lblNewLabel_5.setBounds(350, 129, 90, 13);
        registrazionePanel.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("(Max 20 car.)");
        lblNewLabel_6.setFont(new Font("Arial", Font.BOLD, 10));
        lblNewLabel_6.setBounds(350, 174, 90, 13);
        registrazionePanel.add(lblNewLabel_6);

        JLabel lblNewLabel_7 = new JLabel("(Max 30 car.)");
        lblNewLabel_7.setFont(new Font("Arial", Font.BOLD, 10));
        lblNewLabel_7.setBounds(350, 305, 90, 13);
        registrazionePanel.add(lblNewLabel_7);

        JLabel lblNewLabel_8 = new JLabel("REGISTRAZIONE UTENTE");
        lblNewLabel_8.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_8.setBounds(136, 10, 198, 20);
        registrazionePanel.add(lblNewLabel_8);
        
        JLabel lblNewLabel_11 = new JLabel("(Max 350 car.)");
        lblNewLabel_11.setFont(new Font("Arial", Font.BOLD, 10));
        lblNewLabel_11.setBounds(350, 224, 90, 13);
        registrazionePanel.add(lblNewLabel_11);
        
        JLabel lblNewLabel_12 = new JLabel("(Max 50 car.)");
        lblNewLabel_12.setFont(new Font("Arial", Font.BOLD, 10));
        lblNewLabel_12.setBounds(350, 267, 74, 13);
        registrazionePanel.add(lblNewLabel_12);

    }
}
