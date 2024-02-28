package guiUninaSocialGroup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

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
		contentPane.setBackground(Color.DARK_GRAY);
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
		
		JButton LoginButton = new JButton("login");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		LoginButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		LoginButton.setBounds(296, 221, 89, 23);
		contentPane.add(LoginButton);
		
		JLabel imgPWLabel = new JLabel("");
		imgPWLabel.setIcon(new ImageIcon("C:\\Users\\Utente\\Desktop\\git\\Progetto-BB-OO-2023-24\\Object Orientation\\UninaSocialGroup24\\loghiGUI\\chiaveLucchetto.png"));
		imgPWLabel.setBounds(65, 143, 32, 30);
		contentPane.add(imgPWLabel);
		
		JLabel imgUserLabel = new JLabel("");
		imgUserLabel.setIcon(new ImageIcon("C:\\Users\\Utente\\Desktop\\git\\Progetto-BB-OO-2023-24\\Object Orientation\\UninaSocialGroup24\\loghiGUI\\user.png"));
		imgUserLabel.setBounds(65, 83, 32, 29);
		contentPane.add(imgUserLabel);
	}
}
