package guiUninaSocialGroup;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import classiDAO.Commento;
import classiDAO.Gruppo;
import classiDAO.Post;
import classiDAO.Utente;
import controller.Controller;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JOptionPane;

public class gestioneIscrittiGruppoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel mainPanel;

	public gestioneIscrittiGruppoGUI() {
		setTitle("Gestione Iscritti");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            Controller.chiudiGestioneIscrittiGruppo();
	        }
	    });
		
		setBounds(100, 100, 597, 509);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(172, 202, 232));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(226, 234, 248));
		mainPanel.setBounds(10, 10, 563, 452);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		contentPane.add(mainPanel);
			
		Gruppo gruppoVisualizzato = Controller.gruppoDAO.getGruppoFromArrayListById(Controller.idGruppoVisualizzato);
		mostraPannelloIscrittiGruppo(gruppoVisualizzato);
	}
	
	public void mostraPannelloIscrittiGruppo(Gruppo gruppo) {
		mainPanel.removeAll();
		
		LinkedList<Utente> listaIscrittiGruppo = gruppo.getListaUtentiIscritti();
		
		JPanel iscrittiPanel = new JPanel();
        iscrittiPanel.setLayout(new BoxLayout(iscrittiPanel, BoxLayout.Y_AXIS));
		iscrittiPanel.setBackground(new Color(207, 222, 243));
		
		for (Utente u : listaIscrittiGruppo) {
			if ( u.getIdUtente() != Controller.myIdUtente) {
				JPanel utentePanel = creaPannelloIscrittoGruppo(u,gruppo);
				iscrittiPanel.add(utentePanel);
			}	
		}
		
		JScrollPane scrollPane = new JScrollPane(iscrittiPanel);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollPane.setBounds(10, 10, 419, 452);
		
		mainPanel.removeAll();
        mainPanel.add(scrollPane);
        mainPanel.revalidate();
        mainPanel.repaint();
	}
	
	private JPanel creaPannelloIscrittoGruppo(Utente u,Gruppo g) {		
		JPanel utentePanel = new JPanel();
		utentePanel.setLayout(new BoxLayout(utentePanel,BoxLayout.X_AXIS));
		utentePanel.setBackground(new Color(226, 235, 248));
		
		int borderWidth = 7; 
	    Color borderColor = new Color(207, 222, 243); // Colore del bordo
	    Border border = BorderFactory.createLineBorder(borderColor, borderWidth);
	    utentePanel.setBorder(border);
						
		String nickname = u.getNickname();
		JLabel nicknameLabel = new JLabel(""+nickname);
		nicknameLabel.setFont(new Font("Arial",Font.BOLD,14));
				
		utentePanel.add(nicknameLabel);
		utentePanel.add(Box.createRigidArea(new Dimension(5,0)));
		
				
		if (Controller.controlloUtenteÈAmministratore(g, u)) {
			JLabel amministratoreLabel = new JLabel("Amministratore");
			amministratoreLabel.setFont(new Font("Arial",Font.PLAIN,12));
			amministratoreLabel.setBackground(new Color(226, 235, 248));
			
			utentePanel.add(amministratoreLabel);
				
		}
			
		utentePanel.add(Box.createHorizontalGlue());
				
		JButton nominaAmministratoreButton = new JButton("Nomina Amministratore");
		JButton rimuoviAmministratoreButton = new JButton("Rimuovi Amministratore");

		if (!Controller.controlloUtenteÈAmministratore(g, u)) {
	        nominaAmministratoreButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	Controller.amministratoreDAO.insertNuovoAmministratore(u,g,Controller.creatoreGruppoDAO);
	            	Controller.aggiornaGestioneIscrittiGruppo(g);
	            }
	        });
	        
	        utentePanel.add(nominaAmministratoreButton);
		}
		else {
			rimuoviAmministratoreButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	Controller.amministratoreDAO.deleteAmministratore(u, g);
	            	Controller.aggiornaGestioneIscrittiGruppo(g);
	            }
	        });
	        
	        utentePanel.add(rimuoviAmministratoreButton);
		}
		
		utentePanel.add(Box.createRigidArea(new Dimension(10,0)));
		
		JButton eliminaButton = new JButton("Elimina");
        
		eliminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                JOptionPane.showMessageDialog(gestioneIscrittiGruppoGUI.this, "Hai cliccato su Elimina per l'utente: " + nickname);
            }
        });
		
		utentePanel.add(eliminaButton);		
		return utentePanel;
	}
}
