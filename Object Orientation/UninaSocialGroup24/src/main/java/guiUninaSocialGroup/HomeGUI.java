package guiUninaSocialGroup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import classiDAO.CreatoreGruppo;
import classiDAO.Gruppo;
import controller.Controller;
import net.coobird.thumbnailator.Thumbnails;

import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Window.Type;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.border.LineBorder;

public class HomeGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField barraDiRicercaTxtField;
	private JPanel risultatiRicercaPanel;

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
		setBounds(100, 100, 950, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));


		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel navigazionePanel = new JPanel();
		navigazionePanel.setBackground(new Color(70, 105, 227));
		navigazionePanel.setForeground(new Color(0, 64, 128));
		navigazionePanel.setBounds(0, 0, 937, 53);
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
		barraDiRicercaTxtField.setBounds(281, 17, 228, 21);
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
		ricercaGruppoButton.setBounds(532, 17, 75, 21);
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
		esteticaPanel.setBounds(0, 50, 937, 10);
		contentPane.add(esteticaPanel);
		
		
		JPanel utentePanel = new JPanel();
		utentePanel.setBackground(new Color(148, 190, 233));
		utentePanel.setBounds(0, 60, 202, 503);
		contentPane.add(utentePanel);
		utentePanel.setLayout(null);
		
		
		JLabel imgProfiloLabel = new JLabel("imgProfilo");
		imgProfiloLabel.setBackground(new Color(226, 235, 248));
		imgProfiloLabel.setBounds(10, 5, 185, 185);
		imgProfiloLabel.setBorder(BorderFactory.createLineBorder(new Color (0, 0, 160), 1));
		utentePanel.add(imgProfiloLabel);
		
		 
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
        imgProfiloLabel.setIcon(profileImageIcon);
        
    
        JLabel nicknameLabel = new JLabel(Controller.utenteDAO.getUtenteFromArrayListById(Controller.myIdUtente).getNickname());
        nicknameLabel.setForeground(Color.BLACK);
        nicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nicknameLabel.setBounds(10, 195, 185, 20);
        utentePanel.add(nicknameLabel);

     
		
		JButton impostazioniButton = new JButton("Impostazioni");
		impostazioniButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImpostazioniGUI impostazioniGUI = new ImpostazioniGUI();
				
				setVisible(false);
				impostazioniGUI.setVisible(true);
		        
			}
		});
		impostazioniButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		impostazioniButton.setBounds(10, 246, 185, 21);
		impostazioniButton.setOpaque(false);
		utentePanel.add(impostazioniButton);
		
		JButton NotificheButton = new JButton("Notifiche");
		NotificheButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		NotificheButton.setBounds(10, 277, 185, 21);
		utentePanel.add(NotificheButton);
		
		JPanel sfondoDefaultPanel = new JPanel();
		sfondoDefaultPanel.setBackground(new Color(226, 235, 248));
		sfondoDefaultPanel.setBounds(10, 5, 185, 185);
		utentePanel.add(sfondoDefaultPanel);
		
		JButton logOutButton = new JButton("Log out");
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				loginGUI log = new loginGUI();;
				log.setVisible(true);
			}
		});
		logOutButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		logOutButton.setBounds(10, 472, 182, 21);
		utentePanel.add(logOutButton);
		
		JButton creaGruppoButton = new JButton("Crea gruppo");
		creaGruppoButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
		creaGruppoButton.setBounds(10, 308, 185, 21);
		utentePanel.add(creaGruppoButton);
		
		JPanel gruppiIscrittiPanel = new JPanel();
		gruppiIscrittiPanel.setBackground(new Color(226, 235, 248));
		gruppiIscrittiPanel.setBorder(new LineBorder(new Color(0, 0, 160), 0));
		gruppiIscrittiPanel.setBounds(201, 59, 433, 504);
		contentPane.add(gruppiIscrittiPanel);
		gruppiIscrittiPanel.setLayout(null);
		
		JPanel esteticaPanel2 = new JPanel();
		esteticaPanel2.setBackground(new Color(0, 0, 160));
		esteticaPanel2.setBounds(425, 0, 9, 504);
		gruppiIscrittiPanel.add(esteticaPanel2);
		
		risultatiRicercaPanel = new JPanel();
		risultatiRicercaPanel.setBackground(new Color(226, 235, 248));
		risultatiRicercaPanel.setBounds(635, 91, 297, 472);
		contentPane.add(risultatiRicercaPanel);
		risultatiRicercaPanel.setLayout(new BoxLayout(risultatiRicercaPanel, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(148, 190, 233));
		panel.setBounds(631, 58, 306, 33);
		contentPane.add(panel);
		
		JLabel titoloRicercaLabel = new JLabel();
		panel.add(titoloRicercaLabel);
		titoloRicercaLabel.setBackground(new Color(148, 190, 233));
		titoloRicercaLabel.setText(" Risultato ricerca :");
		titoloRicercaLabel.setFont(new Font("Arial", Font.BOLD, 18));
		
		setLocationRelativeTo(null); 
		
		
	}
	
	private ImageIcon getDefaultProfileImageIcon() {
	    
	    ImageIcon defaultProfileImageIcon = new ImageIcon(getClass().getResource("/defaultFotoProfilo3.jpg"));
	    return defaultProfileImageIcon;
	}
	
	private void aggiornaRisultati(LinkedList<Gruppo> listaGruppiRicerca) {
	  
	    risultatiRicercaPanel.removeAll();

	   
	    if (listaGruppiRicerca.isEmpty()) {
	 			
	        JLabel labelMessaggio = new JLabel("Non sono stati trovati gruppi.");
	        labelMessaggio.setForeground(Color.BLACK); // Imposta il colore del testo
	        risultatiRicercaPanel.add(labelMessaggio);
	    } else {
	    	
	   
	        JPanel panelGruppi = new JPanel();
	        panelGruppi.setLayout(new BoxLayout(panelGruppi, BoxLayout.Y_AXIS));
	        

	        for (Gruppo gruppo : listaGruppiRicerca) {
	            JPanel panelGruppo = new JPanel();
	            panelGruppo.setLayout(new BoxLayout(panelGruppo, BoxLayout.Y_AXIS));
	            panelGruppo.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Aggiungi una linea al pannello del gruppo

	            JLabel labelNomeGruppo = new JLabel("Nome: " + gruppo.getNomeGruppo());

	            // Elenco dei tag
	            JLabel labelTag = new JLabel("Tag: " + String.join(", ", gruppo.getTagGruppo()));

	            JLabel labelDescrizione = new JLabel("Descrizione: " + gruppo.getDescrizioneGruppo());
	            labelDescrizione.setPreferredSize(new Dimension(300, labelDescrizione.getPreferredSize().height));
	            labelDescrizione.setMaximumSize(new Dimension(300, labelDescrizione.getPreferredSize().height));

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

	            // riga di separazione
	            panelGruppi.add(Box.createRigidArea(new Dimension(0, 10))); // Spazio vuoto verticale per separare i gruppi
	            panelGruppi.add(new JSeparator(SwingConstants.HORIZONTAL));
	            panelGruppi.add(Box.createRigidArea(new Dimension(0, 10))); // Spazio vuoto verticale dopo la riga di separazione
	        }

	        JScrollPane scrollPane = new JScrollPane(panelGruppi);
	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	       
	        risultatiRicercaPanel.removeAll();

	        risultatiRicercaPanel.add(scrollPane);
	    }

	
	    risultatiRicercaPanel.setBackground(new Color(226, 235, 248));

	    // Aggiorna visualizzazione GUI
	    risultatiRicercaPanel.revalidate();
	    risultatiRicercaPanel.repaint();
	}
}

