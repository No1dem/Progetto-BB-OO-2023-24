package guiUninaSocialGroup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
		setBackground(new Color(240, 240, 240));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 360);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(64, 64, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		nickTextField = new JTextField();
		nickTextField.setBounds(191, 92, 151, 20);
		contentPane.add(nickTextField);
		nickTextField.setColumns(10);
		
		JLabel NickLabel = new JLabel("Nickname");
		NickLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		NickLabel.setForeground(Color.LIGHT_GRAY);
		NickLabel.setBounds(107, 90, 91, 20);
		contentPane.add(NickLabel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(Color.LIGHT_GRAY);
		passwordLabel.setBackground(new Color(192, 192, 192));
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordLabel.setBounds(107, 153, 91, 20);
		contentPane.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(191, 155, 151, 20);
		contentPane.add(passwordField);
		
		JButton passwordDimenticataButton = new JButton("Password dimenticata");
		passwordDimenticataButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		passwordDimenticataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
			}
		});
		passwordDimenticataButton.setBounds(107, 220, 163, 23);
		contentPane.add(passwordDimenticataButton);
		
		JButton LoginButton = new JButton("Login");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String nickname = nickTextField.getText();
			     String password = new String(passwordField.getPassword());
			     
			     if (nickname.isEmpty() || password.isEmpty()) {
			            JOptionPane.showMessageDialog(contentPane, "Inserisci nickname e password.", "Errore di accesso", JOptionPane.ERROR_MESSAGE);
			            return; 
			     }
			     
			     new ConnectDB();
			     Connection conn = ConnectDB.getConnection();
			           
			     boolean loginCorretto = new LoginController().login(nickname, password, conn);
			        
			     if (loginCorretto) {
			    	 
			    	 try {	
			    		 	setVisible(false);
							
							HomeGUI home = new HomeGUI();
							home.setVisible(true);
			    		 
							Controller.checkDataBase(conn);
							
					 } catch (SQLException exc) {
							exc.printStackTrace();
					   }
		
			     } else {
			    	 JOptionPane.showMessageDialog(contentPane, "Credenziali non valide. Riprova.", "Errore di accesso", JOptionPane.ERROR_MESSAGE);
			       }
			}
		});
		LoginButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		LoginButton.setBounds(296, 221, 89, 23);
		contentPane.add(LoginButton);
		
		JLabel imgPWLabel = new JLabel("");
		Image imgPassword = new ImageIcon(this.getClass().getResource("/chiaveLucchetto.png")).getImage();
		imgPWLabel.setIcon(new ImageIcon(imgPassword));
		imgPWLabel.setBounds(65, 143, 32, 30);
		contentPane.add(imgPWLabel);
		
		JLabel imgUserLabel = new JLabel("");
		Image imgUser = new ImageIcon(this.getClass().getResource("/user.png")).getImage();
		imgUserLabel.setIcon(new ImageIcon(imgUser));
		imgUserLabel.setBounds(65, 83, 32, 29);
		contentPane.add(imgUserLabel);
	}
}
