package guiUninaSocialGroup;

import javax.swing.*;

import classiDAO.EnumStatiRichiesta;
import classiDAO.EnumTipoNotifica;
import classiDAO.Notifica;
import classiDAO.RichiestaDiAccesso;
import controller.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    	setResizable(false);
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("UninaSocialGroup");
        setSize(412, 555);
        getContentPane().setLayout(null);

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(148, 190, 233));
        mainPanel.setBounds(0, 0, 398, 526);
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

        
        LinkedList<Notifica> listaNotificheUtente = new LinkedList<>();
        listaNotificheUtente = Controller.notificaDAO.getListaNotificheByIdUtente(Controller.myIdUtente);

        mostraNotifiche(listaNotificheUtente);
        mostraRichiesteAccesso(listaNotificheUtente);
    }
   

    public void mostraRichiesteAccesso(LinkedList<Notifica> listaNotifiche) {
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

    public void mostraNotifiche(LinkedList<Notifica> listaNotifiche) {
        panelNotifiche.removeAll(); 
        ordinaListaNotifichePerDataEOrario(listaNotifiche);
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
        labelData.setFont(new Font("Arial",Font.BOLD,11));
        
        JLabel labelTipo = new JLabel("Ora:" + notifica.getOraInvio());
        labelTipo.setFont(new Font("Arial",Font.BOLD,11));
        
        JLabel labelText = new JLabel("<html><p style='width:250px;'>" + notifica.getTestoNotifica() + "</p></html>");
        labelText.setFont(new Font("Arial",Font.ITALIC,13));
       
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
        labelData.setFont(new Font("Arial",Font.BOLD,11));

        JLabel labelText = new JLabel("<html><p style='width:250px;'>" + notifica.getTestoNotifica() + "</p></html>");
        labelText.setFont(new Font("Arial",Font.ITALIC,13));

        
        JPanel panelBottoni = new JPanel();
        panelBottoni.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelBottoni.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBottoni.setBackground(new Color(226, 235, 248));
        
        JButton accettaButton = new JButton("Accetta");
        panelBottoni.add(accettaButton);
        accettaButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		RichiestaDiAccesso rda = Controller.richiestaDiAccessoDAO.getRichiestaFromArrayListByNotifica(notifica);
        		Controller.richiestaDiAccessoDAO.updateRichiestaDiAccesso(rda, EnumStatiRichiesta.Accettato,Controller.notificaDAO);
        		
        		panelRichiesteDiAccesso.remove(panelRichiestaAccesso);
               
                panelRichiesteDiAccesso.revalidate();
                panelRichiesteDiAccesso.repaint();
        	}
        });

        JButton rifiutaButton = new JButton("Rifiuta");
        panelBottoni.add(rifiutaButton);
        rifiutaButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		RichiestaDiAccesso rda = Controller.richiestaDiAccessoDAO.getRichiestaFromArrayListByNotifica(notifica);
        		Controller.richiestaDiAccessoDAO.updateRichiestaDiAccesso(rda, EnumStatiRichiesta.Rifiutato,Controller.notificaDAO);
        		
        		panelRichiesteDiAccesso.remove(panelRichiestaAccesso);
                
                panelRichiesteDiAccesso.revalidate();
                panelRichiesteDiAccesso.repaint();
        	}
        });
        
        panelRichiestaAccesso.add(labelData);
        panelRichiestaAccesso.add(labelText);
        panelRichiestaAccesso.add(panelBottoni);

        return panelRichiestaAccesso;
    } 
    
    
    private void ordinaListaNotifichePerDataEOrario(LinkedList<Notifica> listaNotifiche) {
        Collections.sort(listaNotifiche, new Comparator<Notifica>() {
            @Override
            public int compare(Notifica notifica1, Notifica notifica2) {

            	int compareDate = notifica1.getDataInvio().compareTo(notifica2.getDataInvio());
                if (compareDate == 0) {
                	
                    return notifica1.getOraInvio().compareTo(notifica2.getOraInvio());
                }
                return compareDate;
            }
        });
        
        Collections.reverse(listaNotifiche);
    }
}
