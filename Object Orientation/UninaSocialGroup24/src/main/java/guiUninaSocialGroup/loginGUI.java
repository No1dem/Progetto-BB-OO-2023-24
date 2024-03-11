package guiUninaSocialGroup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classiDAO.UtenteDAO;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;

import controller.Controller;
import controller.LoginController;
import dataBaseConnection.ConnectDB;

public class loginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nickTextField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			
				try {
					loginGUI frame = new loginGUI();
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
	public loginGUI() {
		setResizable(false);		
		setTitle("UninaSocialGroup-LogIn");
		setBackground(new Color(240, 240, 240));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 360);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(148, 190, 233));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton passwordDimenticataButton = new JButton("Modifica Password");
		passwordDimenticataButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		passwordDimenticataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.apriPasswordDimenticata();
			}
		});
		
		passwordDimenticataButton.setBounds(10, 290, 167, 23);
		contentPane.add(passwordDimenticataButton);
		Image imgPassword = new ImageIcon(this.getClass().getResource("/chiaveLucchetto.png")).getImage();
		Image imgUser = new ImageIcon(this.getClass().getResource("/user.png")).getImage();
		
		JButton registrazioneButton = new JButton("Registrati");
		registrazioneButton.setToolTipText("Clicca qui se non sei iscritto ad Unina Social Group");
		registrazioneButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		registrazioneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.apriRegistrazioneUtente();
			}
		});
		registrazioneButton.setBounds(404, 290, 112, 23);
		contentPane.add(registrazioneButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(70, 105, 227));
		panel.setBounds(0, 0, 526, 54);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel logoUninaLabel = new JLabel();
		Image imgLogoUnina = new ImageIcon(this.getClass().getResource("/logoUnina.png")).getImage();
		logoUninaLabel.setIcon(new ImageIcon(imgLogoUnina));
		logoUninaLabel.setBounds(111, 0, 258, 54);
		panel.add(logoUninaLabel);
		
		JLabel titoloPasswordDimenticataLabel = new JLabel("Password dimenticata ?");
		titoloPasswordDimenticataLabel.setFont(new Font("Arial", Font.BOLD, 10));
		titoloPasswordDimenticataLabel.setBounds(10, 276, 143, 13);
		contentPane.add(titoloPasswordDimenticataLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(148, 190, 233));
		panel_1.setBounds(89, 85, 340, 170);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel imgUserLabel = new JLabel("");
		imgUserLabel.setBounds(10, 10, 32, 29);
		panel_1.add(imgUserLabel);
		imgUserLabel.setIcon(new ImageIcon(imgUser));
		
		JLabel imgPWLabel = new JLabel("");
		imgPWLabel.setBounds(10, 74, 32, 30);
		panel_1.add(imgPWLabel);
		imgPWLabel.setIcon(new ImageIcon(imgPassword));
		
		JLabel NickLabel = new JLabel("Nickname");
		NickLabel.setBounds(52, 19, 73, 20);
		panel_1.add(NickLabel);
		NickLabel.setBackground(new Color(0, 0, 0));
		NickLabel.setFont(new Font("Arial", Font.BOLD, 15));
		NickLabel.setForeground(new Color(0, 0, 0));
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(52, 84, 91, 20);
		panel_1.add(passwordLabel);
		passwordLabel.setForeground(new Color(0, 0, 0));
		passwordLabel.setBackground(new Color(192, 192, 192));
		passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));
		
		nickTextField = new JTextField();
		nickTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		nickTextField.setBounds(158, 21, 151, 20);
		panel_1.add(nickTextField);
		nickTextField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
		passwordField.setBounds(158, 84, 151, 20);
		panel_1.add(passwordField);
		
		JButton LoginButton = new JButton("Login");
		LoginButton.setBounds(210, 126, 99, 23);
		panel_1.add(LoginButton);
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String nickname = nickTextField.getText();
			     String password = new String(passwordField.getPassword());
			     
			     if (nickname.isEmpty() || password.isEmpty()) {
			            JOptionPane.showMessageDialog(contentPane, "Inserisci nickname e password.", "Errore di accesso", JOptionPane.ERROR_MESSAGE);
			            return; 
			     }
			     
			     
			           
			     boolean loginCorretto = new LoginController().login(nickname, password, Controller.Connessione);
			        
			     if (loginCorretto) {
			    	 
			    	 try {	
			    		 
			    		 	nickTextField.setText("");
			    		 	passwordField.setText("");
			    		 	
			    		 	
			    		 	Controller.checkDataBase(Controller.Connessione);
			    		 	Controller.getMyIdUtenteByNickname(nickname);
			    		 	
							Controller.apriHome();
								
					 } catch (SQLException exc) {
							exc.printStackTrace();
					   }
		
			     } else {
			    	 JOptionPane.showMessageDialog(contentPane, "Credenziali non valide. Riprova.", "Errore di accesso", JOptionPane.ERROR_MESSAGE);
			       }
			}
		});
		LoginButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 0, 160));
		panel_2.setBounds(0, 51, 526, 10);
		contentPane.add(panel_2);
		
		
		
		setLocationRelativeTo(null);   //Schermata al centro
		
	}
}
