package guiUninaSocialGroup;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Cursor;
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

import classiDAO.Commento;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

public class GruppoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel imgProfiloLabel;
	private JPanel navigazioneGruppoPanel;
	private JPanel commentiPanel;


	/**
	 * Create the frame.
	 */
	public GruppoGUI() {
		setTitle("UninaSocialGroup");
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
		scriviPostButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.apriCreazionePost();
			}
		});
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
			statisticheGruppoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Controller.apriStatisticheGruppo();
				}
			});
			panel_3.add(statisticheGruppoButton);
			
			
			JButton eliminaGruppoButton = new JButton("Elimina Gruppo");
			eliminaGruppoButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
			eliminaGruppoButton.setBounds(20, 133, 165, 21);
			eliminaGruppoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Object[] opzioni = {"Conferma", "Annulla"};
        	    	int conferma = JOptionPane.showOptionDialog(null, "Sei sicuro di voler eliminare il gruppo?", "Conferma eliminazione", 
        	    	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioni, opzioni[0]);
        	        if (conferma == JOptionPane.YES_OPTION) {
		                	Controller.eliminaGruppo(Controller.idGruppoVisualizzato);
		                	Controller.tornaAllaHome();
		                }
				}
			});
			panel_3.add(eliminaGruppoButton);
			
			
			JButton gestioneButton = new JButton("Gestione");
			gestioneButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
			gestioneButton.setBounds(20, 102, 165, 21);
			gestioneButton.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			    	Gruppo gruppoVisualizzato = Controller.gruppoDAO.getGruppoFromArrayListById(Controller.idGruppoVisualizzato);
			    	if(gruppoVisualizzato.getNumeroIscritti() > 1) {
			    		Controller.apriGestioneIscrittiGruppo();
			    	}
			    	else {
			    		JOptionPane.showMessageDialog(GruppoGUI.this, "Non sono presenti altri iscritti al gruppo.", "Messaggio", JOptionPane.INFORMATION_MESSAGE);			    	}
			    	}
			});
			panel_3.add(gestioneButton);
					
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
        
        ordinaListaPostPerDataEOrario(listaPostGruppo);
        
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
        JPanel autorePanel = new JPanel();
        autorePanel.setBackground(new Color(207, 222, 243));
        autorePanel.setLayout(new BoxLayout(autorePanel, BoxLayout.X_AXIS));
        autorePanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 0));

    
        JLabel imgProfiloAutoreLabel = new JLabel();
        imgProfiloAutoreLabel.setIcon(getImmagineProfiloScalata(autorePost.getUrlFotoProfilo()));
        imgProfiloAutoreLabel.setPreferredSize(new Dimension(30, 30)); 
        autorePanel.add(imgProfiloAutoreLabel);
        
        autorePanel.add(Box.createRigidArea(new Dimension(10,0)));

        JLabel labelNomeAutore = new JLabel(autorePost.getNickname());
        labelNomeAutore.setFont(new Font("Arial", Font.BOLD,15));
        autorePanel.add(labelNomeAutore);
        
        autorePanel.add(Box.createRigidArea(new Dimension(370,0)));
        
        JLabel likeLabel = new JLabel();
        Image imgLike = new ImageIcon(this.getClass().getResource("/like3.png")).getImage();
        likeLabel.setIcon(new ImageIcon(imgLike));
        autorePanel.add(likeLabel);
        
        autorePanel.add(Box.createRigidArea(new Dimension(2,0)));
        
        String testo = ""+p.getNumeroLike();
        System.out.print(testo);
        JLabel numeroLikeLabel = new JLabel(""+p.getNumeroLike());
        numeroLikeLabel.setFont(new Font("Arial",Font.BOLD,20));
        
        autorePanel.add(numeroLikeLabel);
        
        autorePanel.add(Box.createRigidArea(new Dimension(20,0)));
        
        JLabel commentoLabel = new JLabel();
        Image imgCommento = new ImageIcon(this.getClass().getResource("/commento2.png")).getImage();
        commentoLabel.setIcon(new ImageIcon(imgCommento));
        autorePanel.add(commentoLabel);
        
        
        autorePanel.add(Box.createRigidArea(new Dimension(2,0)));
        
        JLabel numeroCommentiLabel = new JLabel(""+p.getNumeroCommenti());
        numeroCommentiLabel.setFont(new Font("Arial",Font.BOLD,20));
        
        autorePanel.add(numeroCommentiLabel);
        
    
        autorePanel.add(Box.createHorizontalGlue());
        
        Gruppo gruppoVisualizzato = Controller.gruppoDAO.getGruppoFromArrayListById(Controller.idGruppoVisualizzato);
        Utente io = Controller.utenteDAO.getUtenteFromArrayListById(Controller.myIdUtente);
        if (Controller.controlloUtenteÈAmministratore(gruppoVisualizzato, io)) {
        	JLabel eliminaPostButton = new JLabel("");
        	eliminaPostButton.addMouseListener(new MouseAdapter() {
        	    @Override
        	    public void mouseEntered(MouseEvent e) {
        	        eliminaPostButton.setOpaque(true);
        	        eliminaPostButton.setBackground(Color.WHITE);
        	    }

        	    @Override
        	    public void mouseExited(MouseEvent e) {
        	        eliminaPostButton.setOpaque(false);
        	        eliminaPostButton.setBackground(new Color(226, 235, 248));
        	    }
        	    
        	    @Override
        	    public void mouseClicked(MouseEvent e) {
        	    	Object[] opzioni = {"Conferma", "Annulla"};
        	    	int conferma = JOptionPane.showOptionDialog(null, "Sei sicuro di voler eliminare questo post?", "Conferma eliminazione", 
        	    	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioni, opzioni[0]);
        	        if (conferma == JOptionPane.YES_OPTION) {
        	        	Controller.postDAO.deletePostById(p);
        	        	postPanel.removeAll();
        	        	Controller.aggiornaSchermataGruppo();
        	    	}
        	        return;
        	    }
        	});

        	ImageIcon icon = new ImageIcon(getClass().getResource("/cestino.png"));
        	Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_REPLICATE); 
        	ImageIcon scaledIcon = new ImageIcon(img);
        	eliminaPostButton.setIcon(scaledIcon);
        	eliminaPostButton.setPreferredSize(new Dimension(16, 16));
        	eliminaPostButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        	
        	
        	autorePanel.add(eliminaPostButton);
        }

        
        //Contenuto Post--------------------------
        JPanel contenutoPostPanel = new JPanel();
        contenutoPostPanel.setBackground(new Color(226, 235, 248));
        contenutoPostPanel.setLayout(new BoxLayout(contenutoPostPanel, BoxLayout.X_AXIS));
        
        JPanel dataTestoPanel = new JPanel();
        dataTestoPanel.setBackground(new Color(226, 235, 248));
        dataTestoPanel.setLayout(new BoxLayout(dataTestoPanel, BoxLayout.Y_AXIS));
        
        LocalDate dataPubblicazione = p.getDataPubblicazione();
        LocalTime oraPubblicazione = p.getOraPubblicazione();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String oraFormattata = oraPubblicazione.format(formatter);
        
        JLabel labelDataOraPost = new JLabel(dataPubblicazione+" "+oraFormattata); 
        labelDataOraPost.setForeground(Color.GRAY);
        labelDataOraPost.setFont(new Font("Arial", Font.ITALIC, 10));
      
          
        JLabel labelTestoPost = new JLabel("<html><p style='width:280px;'>"+p.getTesto()+ "</p></html>");
        labelTestoPost.setFont(new Font("Arial", Font.PLAIN,13));
        
        dataTestoPanel.add(labelDataOraPost);
        dataTestoPanel.add(labelTestoPost);
        
        contenutoPostPanel.add(Box.createRigidArea(new Dimension(75,0)));
        contenutoPostPanel.add(dataTestoPanel);
        contenutoPostPanel.add(Box.createHorizontalGlue());
        
        //Titolo Commenti------------------------ 
        
        JPanel titoloCommentoPanel = new JPanel();
        titoloCommentoPanel.setBackground(new Color(207, 222, 243));
        titoloCommentoPanel.setLayout(new BoxLayout(titoloCommentoPanel, BoxLayout.X_AXIS));
        titoloCommentoPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 0));
        
        JLabel titoloCommentoLabel = new JLabel("COMMENTI");
        titoloCommentoLabel.setFont(new Font("Arial Black",Font.PLAIN,13));
        
        titoloCommentoPanel.add(Box.createRigidArea(new Dimension(50,0)));
        titoloCommentoPanel.add(titoloCommentoLabel);
        titoloCommentoPanel.add(Box.createHorizontalGlue());
        
        titoloCommentoPanel.add(Box.createRigidArea(new Dimension(200,0)));
        
        JButton scriviCommentoButton = new JButton("Commenta");
        scriviCommentoButton.setFont(new Font("Arial Black", Font.PLAIN, 10));
        scriviCommentoButton.setPreferredSize(new Dimension(100,6));
        titoloCommentoPanel.add(scriviCommentoButton);
        
        titoloCommentoPanel.add(Box.createRigidArea(new Dimension(30,0)));
        
        JButton inserisciLikeButton = new JButton("Mi piace!");
        inserisciLikeButton.setFont(new Font("Arial Black",Font.PLAIN,10));
        inserisciLikeButton.setPreferredSize(new Dimension(100,6));
        titoloCommentoPanel.add(inserisciLikeButton);
        
                 
        postPanel.add(autorePanel);
        
        postPanel.add(contenutoPostPanel);
        
        postPanel.add(titoloCommentoPanel);
        
        if (p.getNumeroCommenti() == 0) {
        	JLabel noCommentiLabel = new JLabel("Non sono ancora presenti commenti per questo Post.");
        	noCommentiLabel.setFont(new Font("Arial",Font.ITALIC,13));
        	noCommentiLabel.setForeground(Color.GRAY);
        	postPanel.add(noCommentiLabel);
        }
        else {
        	LinkedList<Commento> listaCommentiPost = Controller.commentoDAO.getListaCommentiPost(p);
        	creaPannelloCommentiPost(p,listaCommentiPost);
        	postPanel.add(commentiPanel);
        }
        
        return postPanel;
    }
	
	
	private void creaPannelloCommentiPost(Post p,LinkedList<Commento> listaCommenti) {
		commentiPanel = new JPanel();
		commentiPanel.setLayout(new BoxLayout(commentiPanel,BoxLayout.Y_AXIS));
		commentiPanel.setBackground(new Color(226, 235, 248));
		
		for (Commento c : listaCommenti) {
			if (c.getPostRisposto().getIdPost() == p.getIdPost()) {
								
				Utente autore = Controller.utenteDAO.getUtenteFromArrayListById(c.getUtenteAutore().getIdUtente());
				String nickname = autore.getNickname();
				
				JPanel nicknameCommentoPanel = new JPanel();
				nicknameCommentoPanel.setLayout(new BoxLayout(nicknameCommentoPanel,BoxLayout.X_AXIS));
				nicknameCommentoPanel.setBackground(new Color(226, 235, 248));
						
				JLabel nicknameCommentoLabel = new JLabel(nickname+":");
				nicknameCommentoLabel.setFont(new Font("Arial",Font.BOLD,13));
				nicknameCommentoLabel.setBackground(new Color(226, 235, 248));
				
				nicknameCommentoPanel.add(Box.createRigidArea(new Dimension(90,0)));
				nicknameCommentoPanel.add(nicknameCommentoLabel);
				nicknameCommentoPanel.add(Box.createHorizontalGlue());
				
				JPanel testoCommentoPanel = new JPanel();
				testoCommentoPanel.setLayout(new BoxLayout(testoCommentoPanel,BoxLayout.X_AXIS));
				testoCommentoPanel.setBackground(new Color(226, 235, 248));
				testoCommentoPanel.add(Box.createRigidArea(new Dimension(130,0)));
				
				JLabel testoCommentoLabel = new JLabel("<html><p style='width:280px;'>"+c.getTestoCommento()+ "</p></html>");
				testoCommentoLabel.setFont(new Font("Arial",Font.PLAIN,12));
				testoCommentoLabel.setBackground(new Color(226, 235, 248));
				
				testoCommentoPanel.add(testoCommentoLabel);
				testoCommentoPanel.add(Box.createHorizontalGlue());

				commentiPanel.add(nicknameCommentoPanel);
				commentiPanel.add(testoCommentoPanel);
				
				
			}
		}
		
		
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
	
	
	private void ordinaListaPostPerDataEOrario(LinkedList<Post> listaPostGruppo) {
        Collections.sort(listaPostGruppo, new Comparator<Post>() {
            @Override
            public int compare(Post post1, Post post2) {
                
                int compareDate = post1.getDataPubblicazione().compareTo(post2.getDataPubblicazione());
                if (compareDate == 0) {
                   
                    return post1.getOraPubblicazione().compareTo(post2.getOraPubblicazione());
                }
                return compareDate;
            }
        });
        Collections.reverse(listaPostGruppo);

    }
}
