package guiUninaSocialGroup;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.border.LineBorder;

import classiDAO.CreatoreGruppo;
import classiDAO.Gruppo;
import classiDAO.Post;
import classiDAO.Utente;
import controller.Controller;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

public class GruppoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel imgProfiloLabel;
	private JPanel navigazioneGruppoPanel;


	/**
	 * Create the frame.
	 */
	public GruppoGUI() {
		setResizable(false);
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
		logoUninaLabel.setBounds(345, 0, 281, 49);
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
		
		JButton homeButton = new JButton("Home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.tornaAllaHome();
			}
		});
		homeButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		homeButton.setBounds(20, 250, 165, 21);
		panel_3.add(homeButton);
		
			
		
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
		
		if (Controller.controlloUtenteÈCreatoreGruppo(Controller.idGruppoVisualizzato)) {
			
			JButton statisticheGruppoButton = new JButton("Statistiche");
			statisticheGruppoButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
			statisticheGruppoButton.setBounds(20, 72, 165, 21);
			panel_3.add(statisticheGruppoButton);
			
			
			JButton eliminaGruppoButton = new JButton("Elimina Gruppo");
			eliminaGruppoButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
			eliminaGruppoButton.setBounds(20, 133, 165, 21);
			panel_3.add(eliminaGruppoButton);
			
			
			JButton eliminaUtenteButton = new JButton("Elimina Utente");
			eliminaUtenteButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
			eliminaUtenteButton.setBounds(20, 102, 165, 21);
			panel_3.add(eliminaUtenteButton);
					
		}
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(226, 235, 248));
		panel.setBounds(224, 60, 762, 545);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(124, 176, 228));
		panel_1.setBounds(0, 10, 752, 525);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(226, 235, 248));
		panel_2.setBounds(10, 10, 732, 50);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		
		String nomeGruppo = Controller.gruppoDAO.getGruppoFromArrayListById(Controller.idGruppoVisualizzato).getNomeGruppo();
		
		JLabel nomeGruppoLabel = new JLabel(nomeGruppo);
		nomeGruppoLabel.setFont(new Font("Arial", Font.BOLD, 20));
		nomeGruppoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nomeGruppoLabel.setBounds(10, 10, 712, 30);
		panel_2.add(nomeGruppoLabel);
		
		
		navigazioneGruppoPanel = new JPanel();
		navigazioneGruppoPanel.setBackground(new Color(226, 235, 248));
		navigazioneGruppoPanel.setBounds(10, 70, 732, 445);
		navigazioneGruppoPanel.setLayout(new BoxLayout(navigazioneGruppoPanel, BoxLayout.Y_AXIS));
		
		LinkedList<Post> listaPostGruppo = Controller.getPostGruppoByIdGruppo(Controller.idGruppoVisualizzato);
		mostraPannelloPost(listaPostGruppo);
		
		panel_1.add(navigazioneGruppoPanel);
		
				
		setLocationRelativeTo(null);
	}
	
	
	public ImageIcon getDefaultImmagineProfilo() { 
	    ImageIcon defaultImmagineProfilo = new ImageIcon(getClass().getResource("/defaultFotoProfilo3.jpg"));
	    return defaultImmagineProfilo;
	}
	
	public ImageIcon getDefaultImmagineProfiloPiccola() { 
	    ImageIcon defaultImmagineProfilo = new ImageIcon(getClass().getResource("/defaultFotoProfilo3Small.jpg"));
	    return defaultImmagineProfilo;
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
                profileImageIcon = getDefaultImmagineProfilo();
                imgProfiloLabel.setIcon(profileImageIcon);
            }
        } else {
            profileImageIcon = getDefaultImmagineProfilo();
            imgProfiloLabel.setIcon(profileImageIcon);
        }
	}
	
	
	
	public void mostraPannelloPost(LinkedList<Post> listaPostGruppo) {
        navigazioneGruppoPanel.removeAll();  
        
        JPanel postGruppoPanel = new JPanel();
        postGruppoPanel.setLayout(new BoxLayout(postGruppoPanel, BoxLayout.Y_AXIS));
		postGruppoPanel.setBackground(new Color(226, 235, 248));
		
        
        for (Post post : listaPostGruppo) {
            JPanel postPanel = creaPannelloPost(post);
            postGruppoPanel.add(postPanel);
        }
        
        JScrollPane scrollPane = new JScrollPane(postGruppoPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10, 70, 732, 445);
       
  
        
        navigazioneGruppoPanel.removeAll();
        navigazioneGruppoPanel.add(scrollPane);
        navigazioneGruppoPanel.revalidate();
        navigazioneGruppoPanel.repaint();
    }
	
	
	private JPanel creaPannelloPost(Post p) {
        JPanel postPanel = new JPanel();
        postPanel.setBackground(new Color(226, 235, 248));
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        
        Utente autorePost = Controller.utenteDAO.getUtenteFromArrayListById(p.getIdUtente());
        
        // Autore post  --------------------
        JLabel autoreLabel = new JLabel();
        autoreLabel.setBackground(new Color(226, 235, 248));
        //autoreLabel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
//        autoreLabel.setPreferredSize(new Dimension(300, 35));
        

    
//        JLabel imgProfiloAutoreLabel = new JLabel();
//        imgProfiloAutoreLabel.setIcon(getImmagineProfiloScalata(autorePost.getUrlFotoProfilo()));
//        imgProfiloAutoreLabel.setPreferredSize(new Dimension(30, 30)); 
//        autoreLabel.add(imgProfiloAutoreLabel);

        JLabel labelNomeAutore = new JLabel(autorePost.getNickname());
        labelNomeAutore.setFont(new Font("Arial", Font.BOLD,15));
        autoreLabel.add(labelNomeAutore);

        
        //-----------------------------------
        
        LocalDate dataPubblicazione = p.getDataPubblicazione();
        LocalTime oraPubblicazione = p.getOraPubblicazione();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String oraFormattata = oraPubblicazione.format(formatter);
        
        JLabel labelDataOraPost = new JLabel(dataPubblicazione+" "+oraFormattata); 
        labelDataOraPost.setForeground(Color.GRAY);
        labelDataOraPost.setFont(new Font("Arial", Font.ITALIC, 10));
      
        String spaziatura = "&nbsp;&nbsp;&nbsp;&nbsp;";
        
        JLabel labelTestoPost = new JLabel("<html><p style='width:280px;'>"+spaziatura+p.getTesto() + "</p></html>");
        labelTestoPost.setFont(new Font("Arial", Font.PLAIN,13));
               
        postPanel.add(labelNomeAutore);
        postPanel.add(labelDataOraPost);
        postPanel.add(labelTestoPost);
        return postPanel;
    }
	
	
	private ImageIcon getImmagineProfiloScalata(String urlImmagineProfilo) {
        ImageIcon profileImageIcon = getDefaultImmagineProfiloPiccola();
        if (urlImmagineProfilo != null) {
            try {
                URL url = new URL(urlImmagineProfilo);
                BufferedImage originalImage = ImageIO.read(url);
                Image scaledImage = originalImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                profileImageIcon = new ImageIcon(scaledImage);
            } catch (IOException e) {
                e.printStackTrace();
                return profileImageIcon;
            }
        }
        return profileImageIcon;
    }
}
