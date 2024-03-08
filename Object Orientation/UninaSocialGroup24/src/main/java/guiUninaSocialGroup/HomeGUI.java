package guiUninaSocialGroup;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JLabel;
import classiDAO.CreatoreGruppo;
import classiDAO.Gruppo;
import controller.Controller;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;

public class HomeGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField barraDiRicercaTxtField;
	private JPanel risultatiRicercaPanel;
	private JPanel gruppiIscrittoPanel;
	private JPanel gruppiCreatiPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeGUI frame = new HomeGUI();
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
	public HomeGUI() {
		setTitle("UninaSocialGroup");
		setResizable(false);
		setBounds(100, 100, 1000, 642);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));


		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel navigazionePanel = new JPanel();
		navigazionePanel.setBackground(new Color(70, 105, 227));
		navigazionePanel.setForeground(new Color(0, 64, 128));
		navigazionePanel.setBounds(0, 0, 986, 53);
		contentPane.add(navigazionePanel);
		navigazionePanel.setLayout(null);
		
		JLabel logoUninaLabel = new JLabel("");
		logoUninaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		Image imgLogoUnina = new ImageIcon(this.getClass().getResource("/logoUnina.png")).getImage();
		logoUninaLabel.setIcon(new ImageIcon(imgLogoUnina));
		logoUninaLabel.setBounds(-41, 0, 245, 49);
		navigazionePanel.add(logoUninaLabel);
		
		barraDiRicercaTxtField = new JTextField();
		barraDiRicercaTxtField.setForeground(new Color(192, 192, 192));
		barraDiRicercaTxtField.setText("Barra di ricerca...");
		barraDiRicercaTxtField.setHorizontalAlignment(SwingConstants.LEFT);
		barraDiRicercaTxtField.setBounds(317, 14, 228, 28);
		navigazionePanel.add(barraDiRicercaTxtField);
		barraDiRicercaTxtField.setColumns(10);
		
		JButton ricercaGruppoButton = new JButton("Cerca");
		ricercaGruppoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				String ricerca = barraDiRicercaTxtField.getText();
				
				LinkedList<Gruppo> listaGruppiRicerca = new LinkedList<Gruppo>();
				listaGruppiRicerca = Controller.gruppoDAO.cercaGruppiPerNomeOTag(ricerca);
				
				aggiornaRisultati(listaGruppiRicerca);
			}
		});
		ricercaGruppoButton.setFont(new Font("Arial Black", Font.PLAIN, 10));
		ricercaGruppoButton.setBounds(607, 17, 75, 21);
		navigazionePanel.add(ricercaGruppoButton);
		
		barraDiRicercaTxtField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Rimuovi il testo predefinito quando si clicca sulla barra di ricerca
                if (barraDiRicercaTxtField.getText().equals("Barra di ricerca...")) {
                    barraDiRicercaTxtField.setText("");
                    barraDiRicercaTxtField.setForeground(Color.BLACK); 
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Se il campo di ricerca è vuoto, reimposta il testo predefinito
                if (barraDiRicercaTxtField.getText().isEmpty()) {
                    barraDiRicercaTxtField.setText("Barra di ricerca...");
                    barraDiRicercaTxtField.setForeground(Color.GRAY); 
                }
            }
			
        });
		
		
		JPanel esteticaPanel = new JPanel();
		esteticaPanel.setBackground(new Color(0, 0, 160));
		esteticaPanel.setBounds(0, 50, 986, 10);
		contentPane.add(esteticaPanel);
		
		
		JPanel utentePanel = new JPanel();
		utentePanel.setBackground(new Color(226, 235, 248));
		utentePanel.setBounds(0, 60, 216, 545);
		contentPane.add(utentePanel);
		utentePanel.setLayout(null);
		
		//***Immagine del profilo 
		
        String urlImmagineProfilo = Controller.utenteDAO.getUtenteFromArrayListById(Controller.myIdUtente).getUrlFotoProfilo(); 
        
        // Caricamento e impostazione dell'immagine del profilo
        
        ImageIcon profileImageIcon;
        if (urlImmagineProfilo != null) {
            try {
           
				URL url = new URL(urlImmagineProfilo);
                Image profileImage = ImageIO.read(url);
                profileImageIcon = new ImageIcon(profileImage);
            } catch (Exception e) {
                profileImageIcon = getDefaultProfileImageIcon();
            }
        } else {
            profileImageIcon = getDefaultProfileImageIcon();
        }
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(124, 176, 228));
		panel_3.setBounds(10, 254, 205, 281);
		utentePanel.add(panel_3);
		panel_3.setLayout(null);
		
		     
				
		JButton impostazioniButton = new JButton("Impostazioni");
		impostazioniButton.setBounds(20, 10, 165, 21);
		panel_3.add(impostazioniButton);
		impostazioniButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImpostazioniGUI impostazioniGUI = new ImpostazioniGUI();
					
					setVisible(false);
					impostazioniGUI.setVisible(true);
				       
				}
			});
		impostazioniButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		impostazioniButton.setOpaque(false);
			
		
		JButton NotificheButton = new JButton("Notifiche");
		NotificheButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NotificheGUI notificheGUI = new NotificheGUI();
				notificheGUI.setVisible(true);
			}
		});
		NotificheButton.setBounds(20, 41, 165, 21);
		panel_3.add(NotificheButton);
		NotificheButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		
		
		JButton creaGruppoButton = new JButton("Crea gruppo");
		creaGruppoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	                CreazioneGruppoGUI creazioneGruppoGUI = new CreazioneGruppoGUI();
	                creazioneGruppoGUI.setVisible(true);
	            
			}
		});
		creaGruppoButton.setBounds(20, 75, 165, 21);
		panel_3.add(creaGruppoButton);
		creaGruppoButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		
		
		JButton logOutButton = new JButton("Log out");
		logOutButton.setBounds(20, 250, 165, 21);
		panel_3.add(logOutButton);
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				loginGUI log = new loginGUI();;
				log.setVisible(true);
			}
		});
		logOutButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
				
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(124, 176, 228));
		panel_6.setBounds(10, 10, 205, 234);
		utentePanel.add(panel_6);
		panel_6.setLayout(null);
				
		JPanel sfondoDefaultPanel = new JPanel();
		sfondoDefaultPanel.setBounds(10, 10, 185, 185);
		panel_6.add(sfondoDefaultPanel);
		sfondoDefaultPanel.setBackground(new Color(255, 255, 255));
		sfondoDefaultPanel.setLayout(null);
		
			
		JLabel imgProfiloLabel = new JLabel("imgProfilo");
		imgProfiloLabel.setBounds(0, 0, 185, 185);
		imgProfiloLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sfondoDefaultPanel.add(imgProfiloLabel);
		imgProfiloLabel.setBackground(new Color(226, 235, 248));
		imgProfiloLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		imgProfiloLabel.setIcon(profileImageIcon);
				
    
		JLabel nicknameLabel = new JLabel(Controller.utenteDAO.getUtenteFromArrayListById(Controller.myIdUtente).getNickname());
		nicknameLabel.setBounds(20, 214, 165, 20);
		panel_6.add(nicknameLabel);
		nicknameLabel.setFont(new Font("Arial", Font.BOLD, 15));
		nicknameLabel.setForeground(Color.BLACK);
		nicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(226, 235, 248));
		panel_1.setBounds(207, 59, 444, 546);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(124, 176, 228));
		panel_4.setBounds(20, 10, 426, 269);
		panel_1.add(panel_4);
		panel_4.setLayout(null);
		
		gruppiIscrittoPanel = new JPanel();
		gruppiIscrittoPanel.setBounds(10, 42, 406, 217);
		panel_4.add(gruppiIscrittoPanel);
		gruppiIscrittoPanel.setBackground(new Color(226, 235, 248));
		gruppiIscrittoPanel.setLayout(new BoxLayout(gruppiIscrittoPanel, BoxLayout.Y_AXIS));
		
		
		JLabel lblElencoGruppiIscritti = new JLabel();
		lblElencoGruppiIscritti.setBounds(81, 10, 253, 20);
		panel_4.add(lblElencoGruppiIscritti);
		lblElencoGruppiIscritti.setHorizontalAlignment(SwingConstants.CENTER);
		lblElencoGruppiIscritti.setText("GRUPPI A CUI SEI ISCRITTO");
		lblElencoGruppiIscritti.setFont(new Font("Arial", Font.BOLD, 18));
		lblElencoGruppiIscritti.setBackground(new Color(148, 190, 233));
		
		JPanel panel_4_1 = new JPanel();
		panel_4_1.setLayout(null);
		panel_4_1.setBackground(new Color(124, 176, 228));
		panel_4_1.setBounds(20, 289, 426, 247);
		panel_1.add(panel_4_1);
		
		gruppiCreatiPanel = new JPanel();
		gruppiCreatiPanel.setBackground(new Color(226, 235, 248));
		gruppiCreatiPanel.setBounds(10, 42, 406, 195);
		gruppiCreatiPanel.setLayout(new BoxLayout(gruppiCreatiPanel, BoxLayout.Y_AXIS));
		panel_4_1.add(gruppiCreatiPanel);
		
		JLabel lblGruppiCreati = new JLabel();
		lblGruppiCreati.setText("GRUPPI CREATI");
		lblGruppiCreati.setHorizontalAlignment(SwingConstants.CENTER);
		lblGruppiCreati.setFont(new Font("Arial", Font.BOLD, 18));
		lblGruppiCreati.setBackground(new Color(148, 190, 233));
		lblGruppiCreati.setBounds(81, 10, 253, 20);
		panel_4_1.add(lblGruppiCreati);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(226, 235, 248));
		panel.setBounds(631, 58, 355, 547);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(124, 176, 228));
		panel_5.setBounds(30, 10, 315, 527);
		panel.add(panel_5);
		panel_5.setLayout(null);
		
		risultatiRicercaPanel = new JPanel();
		risultatiRicercaPanel.setBounds(10, 41, 295, 476);
		panel_5.add(risultatiRicercaPanel);
		risultatiRicercaPanel.setBackground(new Color(226, 235, 248));
		risultatiRicercaPanel.setLayout(new BoxLayout(risultatiRicercaPanel, BoxLayout.Y_AXIS));
		
		JLabel titoloRicercaLabel = new JLabel();
		titoloRicercaLabel.setBounds(80, 9, 158, 22);
		panel_5.add(titoloRicercaLabel);
		titoloRicercaLabel.setBackground(new Color(148, 190, 233));
		titoloRicercaLabel.setText("RICERCA GRUPPI");
		titoloRicercaLabel.setFont(new Font("Arial", Font.BOLD, 18));
		
		
		
		LinkedList<Gruppo> listaGruppiUtenteIscritto = Controller.getListaGruppiUtenteIscrittoById(Controller.myIdUtente);
		mostraGruppiIscritto(listaGruppiUtenteIscritto);
		 
		
		LinkedList<Gruppo> listaGruppiCreati = Controller.creatoreGruppoDAO.getListaGruppiCreatiFromArrayListByIdUtente(Controller.myIdUtente,Controller.gruppoDAO);
		mostraGruppiCreati(listaGruppiCreati);
		
		
		setLocationRelativeTo(null);
			
	}
	
	
	
	
	private ImageIcon getDefaultProfileImageIcon() {
	    
	    ImageIcon defaultProfileImageIcon = new ImageIcon(getClass().getResource("/defaultFotoProfilo3.jpg"));
	    return defaultProfileImageIcon;
	}
	
	
	
	
	private void aggiornaRisultati(LinkedList<Gruppo> listaGruppiRicerca) {
	  
	    risultatiRicercaPanel.removeAll();

	   
	    if (listaGruppiRicerca.isEmpty()) {
	    	JOptionPane.showMessageDialog(HomeGUI.this, "Nessun gruppo trovato.", "Ricerca gruppi", JOptionPane.WARNING_MESSAGE);
	    } else {
	   
	        JPanel panelGruppi = new JPanel();
	        panelGruppi.setLayout(new BoxLayout(panelGruppi, BoxLayout.Y_AXIS));
	        panelGruppi.setBackground(new Color(226, 235, 248));
	        

	        for (Gruppo gruppo : listaGruppiRicerca) {
	            JPanel panelGruppo = new JPanel();
	            panelGruppo.setLayout(new BoxLayout(panelGruppo, BoxLayout.Y_AXIS));
	            panelGruppo.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Aggiungi una linea al pannello del gruppo
	            panelGruppo.setBackground(new Color(226, 235, 248));
	            
	            JLabel labelNomeGruppo = new JLabel("Nome: " + gruppo.getNomeGruppo());

	            // Elenco dei tag
	            JLabel labelTag = new JLabel("Tag: " + String.join(", ", gruppo.getTagGruppo()));

	            
	            JLabel labelDescrizione = new JLabel("<html><div style='width: 300px; text-align: justify'>" + gruppo.getDescrizioneGruppo() + "</div></html>");
	           

	            JLabel labelIscritti = new JLabel("Iscritti: " + gruppo.getNumeroIscritti());
	            
	            CreatoreGruppo creatoreGruppo = Controller.creatoreGruppoDAO.getCreatoreGruppoFromArrayListByIdGruppo(gruppo.getIdGruppo());
	            JLabel labelCreatore = new JLabel("Creatore: " + creatoreGruppo.getNomeUtente()+" "+creatoreGruppo.getCognomeUtente());

	            panelGruppo.add(labelNomeGruppo);
	            panelGruppo.add(labelTag);
	            panelGruppo.add(labelDescrizione);
	            panelGruppo.add(labelIscritti);
	            panelGruppo.add(labelCreatore);

	   
	            JButton buttonIscriviti = new JButton("Iscriviti");
	            buttonIscriviti.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	               
	                }
	            });
	            panelGruppo.add(buttonIscriviti);

	            panelGruppi.add(panelGruppo);

	   
	        }

	        JScrollPane scrollPane = new JScrollPane(panelGruppi);
	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	        scrollPane.setOpaque(false);
	        scrollPane.setBackground(new Color(192, 192, 192, 150));

	       
	        risultatiRicercaPanel.removeAll();

	        risultatiRicercaPanel.add(scrollPane);
	    }

	
	    risultatiRicercaPanel.setBackground(new Color(226, 235, 248));

	    // Aggiorna visualizzazione GUI
	    risultatiRicercaPanel.revalidate();
	    risultatiRicercaPanel.repaint();
	}
	
	
	
	private void mostraGruppiIscritto(LinkedList<Gruppo> listaGruppiIscritto) {
        gruppiIscrittoPanel.removeAll();  
        
        JPanel panelGruppi = new JPanel();
        panelGruppi.setLayout(new BoxLayout(panelGruppi, BoxLayout.Y_AXIS));
        panelGruppi.setBackground(new Color(226, 235, 248));
        
        for (Gruppo gruppo : listaGruppiIscritto) {
            JPanel gruppoPanel = creaPannelloGruppoIscritto(gruppo);
            panelGruppi.add(gruppoPanel);
        }
        
        JScrollPane scrollPane = new JScrollPane(panelGruppi);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10, 42, 406, 217);
        
  
        gruppiIscrittoPanel.removeAll();
        gruppiIscrittoPanel.add(scrollPane);
        gruppiIscrittoPanel.revalidate();
        gruppiIscrittoPanel.repaint();
    }
	
	
	private JPanel creaPannelloGruppoIscritto(Gruppo g) {
        JPanel gruppoPanel = new JPanel();
        gruppoPanel.setBackground(new Color(226, 235, 248));
        gruppoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gruppoPanel.setLayout(new BoxLayout(gruppoPanel, BoxLayout.Y_AXIS));
        gruppoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Azioni da eseguire quando il pannello viene cliccato
                JOptionPane.showMessageDialog(null, "Hai cliccato sul pannello!");
            }
            
            public void mouseEntered(MouseEvent e) {
                // Cambia il colore del pannello quando il cursore del mouse entra
                gruppoPanel.setBackground(Color.WHITE);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                // Cambia il colore del pannello quando il cursore del mouse esce
                gruppoPanel.setBackground(new Color (226, 235, 248));
            }
        });
        
        CreatoreGruppo cg = Controller.creatoreGruppoDAO.getCreatoreGruppoFromArrayListByIdGruppo(g.getIdGruppo());
        String nickname = cg.getNickname();
        
        JLabel labelNome= new JLabel("Nome gruppo: " + g.getNomeGruppo());
        JLabel labelDescrizione = new JLabel("<html><p style='width:280px;'>" + g.getDescrizioneGruppo() + "</p></html>");
        JLabel labelCreatore = new JLabel("Gruppo creato da: "+ nickname);
        JLabel labelNumeroIscritti = new JLabel("Numero iscritti: " + g.getNumeroIscritti());
       
        gruppoPanel.add(labelNome);
        gruppoPanel.add(labelDescrizione);
        gruppoPanel.add(labelCreatore);
        gruppoPanel.add(labelNumeroIscritti);
        return gruppoPanel;
    }
	
	
	private void mostraGruppiCreati(LinkedList<Gruppo> listaGruppiCreati) {
        gruppiCreatiPanel.removeAll();  
        
        JPanel panelGruppi = new JPanel();
        panelGruppi.setLayout(new BoxLayout(panelGruppi, BoxLayout.Y_AXIS));
        panelGruppi.setBackground(new Color(226, 235, 248));
        
        for (Gruppo gruppo : listaGruppiCreati) {
            JPanel gruppoPanel = creaPannelloGruppoCreato(gruppo);
            panelGruppi.add(gruppoPanel);
        }
        
        JScrollPane scrollPane = new JScrollPane(panelGruppi);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10, 42, 406, 217);
        
  
        gruppiCreatiPanel.removeAll();
        gruppiCreatiPanel.add(scrollPane);
        gruppiCreatiPanel.revalidate();
        gruppiCreatiPanel.repaint();
    }
	
	
	private JPanel creaPannelloGruppoCreato(Gruppo g) {
        JPanel gruppoPanel = new JPanel();
        gruppoPanel.setBackground(new Color(226, 235, 248));
        gruppoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gruppoPanel.setLayout(new BoxLayout(gruppoPanel, BoxLayout.Y_AXIS));
        
        
        JLabel labelNome= new JLabel("Nome gruppo: " + g.getNomeGruppo());
        JLabel labelDescrizione = new JLabel("<html><p style='width:280px;'>" + g.getDescrizioneGruppo() + "</p></html>");
        JLabel labelNumeroIscritti = new JLabel("Numero iscritti: " + g.getNumeroIscritti());
       
        gruppoPanel.add(labelNome);
        gruppoPanel.add(labelDescrizione);
        gruppoPanel.add(labelNumeroIscritti);
        return gruppoPanel;
    }
	
		
}

