package guiUninaSocialGroup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;

import javax.swing.border.LineBorder;

import controller.Controller;

import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;

public class GruppoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel imgProfiloLabel;


	/**
	 * Create the frame.
	 */
	public GruppoGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel navigazionePanel = new JPanel();
		navigazionePanel.setLayout(null);
		navigazionePanel.setForeground(new Color(0, 64, 128));
		navigazionePanel.setBackground(new Color(70, 105, 227));
		navigazionePanel.setBounds(0, 0, 986, 53);
		contentPane.add(navigazionePanel);
		
		JLabel logoUninaLabel = new JLabel("");
		logoUninaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		Image imgLogoUnina = new ImageIcon(this.getClass().getResource("/logoUnina.png")).getImage();
		logoUninaLabel.setIcon(new ImageIcon(imgLogoUnina));
		logoUninaLabel.setBounds(368, 0, 262, 49);
		navigazionePanel.add(logoUninaLabel);
		
		JPanel esteticaPanel = new JPanel();
		esteticaPanel.setBackground(new Color(0, 0, 160));
		esteticaPanel.setBounds(0, 51, 986, 10);
		contentPane.add(esteticaPanel);
		
		JPanel utentePanel = new JPanel();
		utentePanel.setLayout(null);
		utentePanel.setBackground(new Color(226, 235, 248));
		utentePanel.setBounds(0, 60, 225, 545);
		contentPane.add(utentePanel);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(new Color(124, 176, 228));
		panel_3.setBounds(10, 254, 205, 281);
		utentePanel.add(panel_3);
		
		JButton impostazioniButton = new JButton("Impostazioni Gruppo");
		impostazioniButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		impostazioniButton.setOpaque(false);
		impostazioniButton.setFont(new Font("Arial Black", Font.PLAIN, 11));
		impostazioniButton.setBounds(20, 72, 165, 21);
		panel_3.add(impostazioniButton);
		
		JButton NotificheButton = new JButton("Notifiche");
		NotificheButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.apriNotifiche();
			}
		});
		NotificheButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		NotificheButton.setBounds(20, 10, 165, 21);
		panel_3.add(NotificheButton);
		
		JButton scriviPostButton = new JButton("Scrivi Post");
		scriviPostButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		scriviPostButton.setBounds(20, 41, 165, 21);
		panel_3.add(scriviPostButton);
		
		JButton logOutButton = new JButton("Home");
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.tornaAllaHome();
			}
		});
		logOutButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		logOutButton.setBounds(20, 250, 165, 21);
		panel_3.add(logOutButton);
		
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(null);
		panel_6.setBackground(new Color(124, 176, 228));
		panel_6.setBounds(10, 10, 205, 234);
		utentePanel.add(panel_6);
		
		JPanel sfondoDefaultPanel = new JPanel();
		sfondoDefaultPanel.setLayout(null);
		sfondoDefaultPanel.setBackground(Color.WHITE);
		sfondoDefaultPanel.setBounds(10, 10, 185, 185);
		panel_6.add(sfondoDefaultPanel);
		
		imgProfiloLabel = new JLabel("");
		imgProfiloLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imgProfiloLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		imgProfiloLabel.setBackground(new Color(226, 235, 248));
		imgProfiloLabel.setBounds(0, 0, 185, 185);
		sfondoDefaultPanel.add(imgProfiloLabel);
		caricaImmagineDelProfilo();
		
		JLabel nicknameLabel = new JLabel(Controller.utenteDAO.getUtenteFromArrayListById(Controller.myIdUtente).getNickname());
		nicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nicknameLabel.setForeground(Color.BLACK);
		nicknameLabel.setFont(new Font("Arial", Font.BOLD, 15));
		nicknameLabel.setBounds(20, 204, 165, 20);
		panel_6.add(nicknameLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(226, 235, 248));
		panel.setBounds(224, 60, 762, 545);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(124, 176, 228));
		panel_1.setBounds(0, 10, 752, 525);
		panel.add(panel_1);
	}
	
	
	public ImageIcon getDefaultProfileImageIcon() { 
	    ImageIcon defaultProfileImageIcon = new ImageIcon(getClass().getResource("/defaultFotoProfilo3.jpg"));
	    return defaultProfileImageIcon;
	}
	
	
	public void caricaImmagineDelProfilo() { 
        String urlImmagineProfilo = Controller.utenteDAO.getUtenteFromArrayListById(Controller.myIdUtente).getUrlFotoProfilo(); 
        ImageIcon profileImageIcon;
        if (urlImmagineProfilo != null) {
            try {
				URL url = new URL(urlImmagineProfilo);
                Image profileImage = ImageIO.read(url);
                profileImageIcon = new ImageIcon(profileImage);
                imgProfiloLabel.setIcon(profileImageIcon);
               
            } catch (Exception e) {
                profileImageIcon = getDefaultProfileImageIcon();
                imgProfiloLabel.setIcon(profileImageIcon);
            }
        } else {
            profileImageIcon = getDefaultProfileImageIcon();
            imgProfiloLabel.setIcon(profileImageIcon);
        }
	}
}
