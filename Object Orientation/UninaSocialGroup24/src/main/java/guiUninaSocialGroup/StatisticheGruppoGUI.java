package guiUninaSocialGroup;

import javax.swing.*;

import classiDAO.Gruppo;
import classiDAO.Post;
import classiDAO.Utente;
import controller.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StatisticheGruppoGUI extends JFrame {
    private JLabel groupNameLabel;
    private JTextField dataTextField;
    private JButton searchButton;
    private Gruppo gruppo;
    private JPanel subPanel1Top; 
    private JPanel subPanel2Top;
    private JPanel subPanel1Bottom;
    private JPanel subPanel2Bottom;
    
    private int mese;
    private int anno; 
    private int idpost;
    

    public StatisticheGruppoGUI() {
        setResizable(false);
        setTitle("Statistiche");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);                
            }
        });
        
        setLocationRelativeTo(null); // Centra la finestra
        
       
        
        gruppo= Controller.gruppoDAO.getGruppoFromArrayListById(Controller.idGruppoVisualizzato);
        
   
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(172, 202, 232)); 
        getContentPane().add(mainPanel);
        
        
        
        Image imgIcona = new ImageIcon(this.getClass().getResource("/Statistics.png")).getImage();
       
        JLabel lblIcona = new JLabel();
        lblIcona.setBounds(10, 0, 50, 50);
        lblIcona.setIcon(new ImageIcon(imgIcona)); 
        mainPanel.add(lblIcona); 
        
        
        
        groupNameLabel = new JLabel(gruppo.getNomeGruppo());
        groupNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        groupNameLabel.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        groupNameLabel.setBounds(44, 10, 331, 30);
        mainPanel.add(groupNameLabel);

       
        dataTextField = new JTextField();
        dataTextField.setBounds(385, 11, 111, 30);
        dataTextField.setForeground(Color.GRAY); 
        dataTextField.setText("mm/aaaa"); 
        mainPanel.add(dataTextField);

      
        JLabel LabelMediaGiornaliera = new JLabel("Media giornaliera:\r\n");
        LabelMediaGiornaliera.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        LabelMediaGiornaliera.setBounds(20, 52, 128, 32);
        mainPanel.add(LabelMediaGiornaliera);

       
        JPanel panel1Top = new JPanel();
        panel1Top.setBackground(new Color(124, 176, 228)); 
        panel1Top.setBounds(10, 81, 282, 130);
        mainPanel.add(panel1Top);
        panel1Top.setLayout(null);

        JLabel LabelMenoCommenti = new JLabel("Post con meno commenti:");
        LabelMenoCommenti.setBounds(69, 5, 177, 16);
        LabelMenoCommenti.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        panel1Top.add(LabelMenoCommenti);
         
        
        JPanel panel2Top = new JPanel();
        panel2Top.setBackground(new Color(124, 176, 228)); 
        panel2Top.setBounds(294, 81, 282, 130);
        mainPanel.add(panel2Top);
        panel2Top.setLayout(null);

        JLabel LabelMenoLike = new JLabel("Post con meno Like:");
        LabelMenoLike.setBounds(80, 5, 135, 16);
        LabelMenoLike.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        panel2Top.add(LabelMenoLike);
        
            
        // Pannelli inferiori
        JPanel panel1Bottom = new JPanel();
        panel1Bottom.setBackground(new Color(124, 176, 228)); 
        panel1Bottom.setBounds(10, 222, 282, 130);
        mainPanel.add(panel1Bottom);
        panel1Bottom.setLayout(null);

        JLabel LabelPiuCommenti = new JLabel("Post con più Commenti:");
        LabelPiuCommenti.setBounds(65, 5, 151, 16);
        LabelPiuCommenti.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        panel1Bottom.add(LabelPiuCommenti);
        
       
        JPanel panel2Bottom = new JPanel();
        panel2Bottom.setBackground(new Color(124, 176, 228)); 
        panel2Bottom.setBounds(294, 222, 282, 130);
        mainPanel.add(panel2Bottom);
        panel2Bottom.setLayout(null);

       
        JLabel LabelPiuLike = new JLabel("Post con più Like:");
        LabelPiuLike.setBounds(87, 5, 133, 16);
        LabelPiuLike.setFont(new Font("Arial Bold", Font.PLAIN, 13));
        panel2Bottom.add(LabelPiuLike);
        
        subPanel2Bottom = new JPanel();
        panel2Bottom.add(subPanel2Bottom);
        subPanel2Bottom.setBounds(10, 22, 262, 97);
        subPanel2Bottom.setBackground(new Color(226, 235, 248));
        subPanel2Bottom.setLayout(new BoxLayout(subPanel2Bottom, BoxLayout.Y_AXIS));
        
        subPanel1Bottom = new JPanel();
        panel1Bottom.add(subPanel1Bottom);
        subPanel1Bottom.setBounds(10, 22, 262, 97);
        subPanel1Bottom.setBackground(new Color(226, 235, 248));
        subPanel1Bottom.setLayout(new BoxLayout(subPanel1Bottom, BoxLayout.Y_AXIS));
        
        subPanel2Top = new JPanel();
        panel2Top.add(subPanel2Top);
        subPanel2Top.setBounds(10, 22, 262, 97);
        subPanel2Top.setBackground(new Color(225, 235, 248));
        subPanel2Top.setLayout(new BoxLayout(subPanel2Top, BoxLayout.Y_AXIS));
        
        subPanel1Top = new JPanel();
        panel1Top.add(subPanel1Top);
        subPanel1Top.setBounds(10, 22, 262, 97);
        subPanel1Top.setBackground(new Color(225, 235, 248));
        subPanel1Top.setLayout(new BoxLayout(subPanel1Top, BoxLayout.Y_AXIS));
          
        JLabel mediaLabel = new JLabel("");
        mediaLabel.setBounds(144, 61, 49, 14);
        
        searchButton = new JButton("Cerca");
        searchButton.setBounds(506, 11, 70, 30);
        searchButton.addActionListener(new ActionListener() {
       
        public void actionPerformed(ActionEvent e) {
        	
            String dataString = dataTextField.getText();
            String[] parti = dataString.split("/");
            
            if (parti.length == 2) { 
                String meseString = parti[0];
                String annoString = parti[1];
                
                try {
                    int mese = Integer.parseInt(meseString); 
                    int anno = Integer.parseInt(annoString); 
                    
                    float mediaP = Controller.postDAO.getMediaPostInUnMese(mese , anno , gruppo);
                    
                    mediaLabel.setText(""+mediaP);
                    mainPanel.add(mediaLabel);
                    
               
                    idpost = Controller.postDAO.getIDPostConPiuLikeGruppoInUnMese( mese,  anno, gruppo);
                    if( idpost==-1) {
                    	 JOptionPane.showMessageDialog(null, "Il gruppo non ha post nel periodo specificato.", "Nessun messaggio trovato", JOptionPane.INFORMATION_MESSAGE);
                    	 subPanel2Top.removeAll();
                    	 subPanel1Top.removeAll();
                    	 subPanel1Bottom.removeAll();
                    	 subPanel2Bottom.removeAll();                    	 
                    }else {
                    	Post postConPiùLike=Controller.postDAO.getPostFromArrayListById(idpost);
                    	
                    	idpost=	Controller.postDAO.getIDPostConPiuCommentiGruppoInUnMese( mese,  anno, gruppo);		
                    	Post postConPiùCommenti=Controller.postDAO.getPostFromArrayListById(idpost);
                    	
                    	idpost= Controller.postDAO.getIDPostConMenoLikeGruppoInUnMese( mese,  anno, gruppo);	
                        Post postConMenoLike=Controller.postDAO.getPostFromArrayListById(idpost);
                        
                        idpost=	Controller.postDAO.getIDPostConMenoCommentiGruppoInUnMese( mese,  anno, gruppo);
                        Post postConMenoCommenti=Controller.postDAO.getPostFromArrayListById(idpost);
                    	
                        mostraPannelloMenoLike(postConMenoLike);
                        mostraPannelloPiùCommenti(postConPiùCommenti);
                        mostraPannelloMenoCommenti(postConMenoCommenti);
                        mostraPannelloPiùLike(postConPiùLike);
                        
                        
                    }
                    
                    mainPanel.repaint();
                    mainPanel.revalidate();
                    
                } catch (NumberFormatException ex) {
                	JOptionPane.showMessageDialog(null, "Formato data non valido", "Errore", JOptionPane.ERROR_MESSAGE);
                   }
            }else {
            	 JOptionPane.showMessageDialog(null, "Formato data non valido", "Errore", JOptionPane.ERROR_MESSAGE);
            }
               
        }
        });
    
   
        mainPanel.add(searchButton);
    
    }
    
    private void mostraPannelloPiùLike(Post p) {
    	subPanel2Bottom.removeAll();
    	
    	JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
		postPanel.setBackground(new Color(226, 235, 248));
		
		JPanel postConPiùLike = creaPannelloPost(p);
		postPanel.add(postConPiùLike);
		
		
        JScrollPane scrollPanePiùLike = new JScrollPane(postPanel);
        scrollPanePiùLike.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanePiùLike.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanePiùLike.setBounds(10, 22, 262, 97);
                
        subPanel2Bottom.add(scrollPanePiùLike);
        subPanel2Bottom.revalidate();
        subPanel2Bottom.repaint();
    	
    }
    
    private void mostraPannelloMenoLike(Post p) {
    	subPanel2Top.removeAll();
    	
    	JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
		postPanel.setBackground(new Color(226, 235, 248));
		
		JPanel postConMenoLike = creaPannelloPost(p);
		postPanel.add(postConMenoLike);
		
		JScrollPane scrollPanePiùLike = new JScrollPane(postPanel);
        scrollPanePiùLike.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanePiùLike.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanePiùLike.setBounds(10, 22, 262, 97);
        
        subPanel2Top.add(scrollPanePiùLike);
        subPanel2Top.revalidate();
        subPanel2Top.repaint();
    	
    }
    
    private void mostraPannelloPiùCommenti(Post p) {
    	subPanel1Bottom.removeAll();
    	
    	JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
		postPanel.setBackground(new Color(226, 235, 248));
		
		JPanel postConPiùCommenti = creaPannelloPost(p);
		postPanel.add(postConPiùCommenti);
		
		
        JScrollPane scrollPanePiùCommenti = new JScrollPane(postPanel);
        scrollPanePiùCommenti.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanePiùCommenti.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanePiùCommenti.setBounds(10, 22, 262, 97);
                
        subPanel1Bottom.add(scrollPanePiùCommenti);
        subPanel1Bottom.revalidate();
        subPanel1Bottom.repaint();
    	
    }
    
    private void mostraPannelloMenoCommenti(Post p) {
    	subPanel1Top.removeAll();
    	
    	JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
		postPanel.setBackground(new Color(226, 235, 248));
		
		JPanel postConMenoCommenti = creaPannelloPost(p);
		postPanel.add(postConMenoCommenti);
		
		
        JScrollPane scrollPaneMenoCommenti = new JScrollPane(postPanel);
        scrollPaneMenoCommenti.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneMenoCommenti.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneMenoCommenti.setBounds(10, 22, 262, 97);
                
        subPanel1Top.add(scrollPaneMenoCommenti);
        subPanel1Top.revalidate();
        subPanel1Top.repaint();
    	
    }
    
    
    
    private JPanel creaPannelloPost(Post post) {
        JPanel panelPost = new JPanel();
        panelPost.setBackground(new Color(226, 235, 248));
        panelPost.setLayout(new BoxLayout(panelPost, BoxLayout.Y_AXIS));
        
        Utente autore = Controller.utenteDAO.getUtenteFromArrayListById(post.getIdUtente());
        JLabel labelAutore = new JLabel("" + autore.getNickname() );
        labelAutore.setFont(new Font("Arial",Font.BOLD,14));
        
        JLabel labelData = new JLabel("" + post.getDataPubblicazione());
        labelData.setFont(new Font("Arial",Font.ITALIC,10));
        
        JLabel labelText = new JLabel("<html><p style='width:170px;'>" + post.getTesto() + "</p></html>");
        labelText.setFont(new Font("Arial",Font.PLAIN,12));
       
        panelPost.add(labelAutore);
        panelPost.add(labelData);
        panelPost.add(labelText);
        return panelPost;
    }

    
	
}
