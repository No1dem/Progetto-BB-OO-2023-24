package guiUninaSocialGroup;

import javax.swing.*;

import classiDAO.EnumStatiRichiesta;
import classiDAO.EnumTipoNotifica;
import classiDAO.Notifica;
import classiDAO.RichiestaDiAccesso;
import controller.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NotificheGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel contentPanel_1;
    private JPanel titoloNotifichePanel;
    private JPanel titoloRichiesteDiAccessoPanel;
    private JPanel panelNotifiche;
    private JPanel panelRichiesteDiAccesso;

  
    public NotificheGUI() {
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	setResizable(false);
        setTitle("UninaSocialGroup");
        setSize(412, 573);
        getContentPane().setLayout(null);

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(148, 190, 233));
        mainPanel.setBounds(0, 0, 398, 542);
        mainPanel.setLayout(null);
        
        titoloNotifichePanel = new JPanel();
        titoloNotifichePanel.setBounds(10, 10, 378, 19);
        titoloNotifichePanel.setBackground(new Color(124, 176, 228));
        titoloNotifichePanel.setLayout(null);
        
        contentPanel = new JPanel();
        contentPanel.setBounds(0, 0, 0, 0);
        contentPanel.setBackground(new Color(124, 176, 228));
        
        titoloNotifichePanel.add(contentPanel);
        
        contentPanel.setLayout(null);
        
        mainPanel.add(titoloNotifichePanel);
        
        JLabel notificheLabel = new JLabel("NOTIFICHE");
        
        notificheLabel.setBounds(97, 0, 188, 19);
        
        titoloNotifichePanel.add(notificheLabel);
        
        notificheLabel.setBackground(new Color(124, 176, 228));
        notificheLabel.setFont(new Font("Arial", Font.BOLD, 16));
        notificheLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titoloRichiesteDiAccessoPanel = new JPanel();
        titoloRichiesteDiAccessoPanel.setBounds(10, 259, 378, 19);
        titoloRichiesteDiAccessoPanel.setBackground(new Color(124, 176, 228));
        titoloRichiesteDiAccessoPanel.setLayout(null);
        
        contentPanel_1 = new JPanel();
        contentPanel_1.setBounds(0, 0, 0, 0);
        titoloRichiesteDiAccessoPanel.add(contentPanel_1);
        
        contentPanel_1.setLayout(null);
        
        mainPanel.add(titoloRichiesteDiAccessoPanel);
        
        
       
        
        
        JLabel richiesteAccessoLabel = new JLabel("RICHIESTE DI ACCESSO");
        richiesteAccessoLabel.setBounds(58, 0, 269, 19);
        titoloRichiesteDiAccessoPanel.add(richiesteAccessoLabel);
        richiesteAccessoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        richiesteAccessoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        getContentPane().add(mainPanel);
       
        
        JScrollPane scrollPaneNotifiche = new JScrollPane();
        scrollPaneNotifiche.setBounds(10, 39, 378, 206);
        scrollPaneNotifiche.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneNotifiche.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPaneNotifiche); 

        
        panelNotifiche = new JPanel();
        panelNotifiche.setBackground(new Color(226, 235, 248));
        panelNotifiche.setBounds(10, 39, 378, 206);
        scrollPaneNotifiche.setViewportView(panelNotifiche);
        panelNotifiche.setLayout(new BoxLayout(panelNotifiche, BoxLayout.Y_AXIS));
        
        
        JScrollPane scrollPaneRichieste = new JScrollPane();
        scrollPaneRichieste.setBounds(10, 288, 378, 206);
        scrollPaneRichieste.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneRichieste.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPaneRichieste);
        
        
        panelRichiesteDiAccesso = new JPanel();
        panelRichiesteDiAccesso.setBackground(new Color(226, 235, 248));
        scrollPaneRichieste.setViewportView(panelRichiesteDiAccesso);
        panelRichiesteDiAccesso.setBounds(10, 288, 378, 206);
        

  
        
        panelRichiesteDiAccesso.setLayout(new BoxLayout(panelRichiesteDiAccesso, BoxLayout.Y_AXIS));
        
        JButton btnNewButton = new JButton("Aggiorna");
        btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 12));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LinkedList<Notifica> listaNotificheUtente = new LinkedList<>();
        		
                listaNotificheUtente = Controller.notificaDAO.getListaNotificheByIdUtente(Controller.myIdUtente);
                
        		mostraRichiesteAccesso(listaNotificheUtente);
        	}
        });
        btnNewButton.setBounds(285, 511, 103, 21);
        
        mainPanel.add(btnNewButton);

        
        LinkedList<Notifica> listaNotificheUtente = new LinkedList<>();
        listaNotificheUtente = Controller.notificaDAO.getListaNotificheByIdUtente(Controller.myIdUtente);

        mostraNotifiche(listaNotificheUtente);
        mostraRichiesteAccesso(listaNotificheUtente);
    }
   

    public void mostraRichiesteAccesso(List<Notifica> listaNotifiche) {
        panelRichiesteDiAccesso.removeAll();
        for (Notifica notifica : listaNotifiche) {
            if (notifica.getTipoNotifica() == EnumTipoNotifica.Accesso) {
            	RichiestaDiAccesso rda ;
                 rda = Controller.richiestaDiAccessoDAO.getRichiestaFromArrayListByNotifica(notifica);
            	if (rda.getStatoRichiesta()== EnumStatiRichiesta.In_attesa) {
                	JPanel panelNotifica = creaPannelloRichiestaAccesso(notifica);
                	panelRichiesteDiAccesso.add(panelNotifica);
            	}
            }
        }
    }

    public void mostraNotifiche(List<Notifica> listaNotifiche) {
        panelNotifiche.removeAll();  
        for (Notifica notifica : listaNotifiche) {
            if (notifica.getTipoNotifica() != EnumTipoNotifica.Accesso) {
                JPanel panelNotifica = creaPannelloNotifica(notifica);
                panelNotifiche.add(panelNotifica);
            }
             
        }
        panelNotifiche.revalidate();
        panelNotifiche.repaint();
    }

    private JPanel creaPannelloNotifica(Notifica notifica) {
        JPanel panelNotifica = new JPanel();
        panelNotifica.setBackground(new Color(226, 235, 248));
        panelNotifica.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelNotifica.setLayout(new BoxLayout(panelNotifica, BoxLayout.Y_AXIS));
        
        JLabel labelData = new JLabel("Data: " + notifica.getDataInvio());
        JLabel labelTipo = new JLabel("Ora:" + notifica.getOraInvio());
        JLabel labelText = new JLabel("<html><p style='width:250px;'>" + notifica.getTestoNotifica() + "</p></html>");
       
        panelNotifica.add(labelData);
        panelNotifica.add(labelTipo);
        panelNotifica.add(labelText);
        return panelNotifica;
    }
    
    
    private JPanel creaPannelloRichiestaAccesso(Notifica notifica) {
        JPanel panelRichiestaAccesso = new JPanel();
        panelRichiestaAccesso.setBackground(new Color(226, 235, 248));
        panelRichiestaAccesso.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelRichiestaAccesso.setLayout(new BoxLayout(panelRichiestaAccesso, BoxLayout.Y_AXIS));
        panelRichiestaAccesso.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        
        JLabel labelData = new JLabel("Data: " + notifica.getDataInvio());
        JLabel labelText = new JLabel("<html><p style='width:250px;'>" + notifica.getTestoNotifica() + "</p></html>");
        
        JPanel panelBottoni = new JPanel();
        panelBottoni.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelBottoni.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBottoni.setBackground(new Color(226, 235, 248));
        
        JButton accettaButton = new JButton("Accetta");
        panelBottoni.add(accettaButton);

        JButton rifiutaButton = new JButton("Rifiuta");
        panelBottoni.add(rifiutaButton);
        
        panelRichiestaAccesso.add(labelData);
        panelRichiestaAccesso.add(labelText);
        panelRichiestaAccesso.add(panelBottoni);

        return panelRichiestaAccesso;
    } 
}
