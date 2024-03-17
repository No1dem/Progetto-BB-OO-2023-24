package guiUninaSocialGroup;

import javax.swing.*;

import classiDAO.Gruppo;
import classiDAO.Post;
import controller.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticheGruppoGUI extends JFrame {
    private JLabel groupNameLabel;
    private JTextField dataTextField;
    private JButton searchButton;
    private Gruppo gruppo;
    
    
    private int mese;
    private int anno; 
    private Post postConLike;
    private Post postCommenti;
    
    

    public StatisticheGruppoGUI() {
        setResizable(false);
        setTitle("Statistiche del Gruppo");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la finestra
        
        gruppo= Controller.gruppoDAO.getGruppoFromArrayListById(Controller.idGruppoVisualizzato);
        
   
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(148, 190, 223)); 
        getContentPane().add(mainPanel);
        
        
        
        Image imgIcona = new ImageIcon(this.getClass().getResource("/Satistics.png")).getImage();

        // l'icona 
        JLabel lblIcona = new JLabel();
        lblIcona.setBounds(10, 11, 50, 50);
        mainPanel.add(lblIcona); 
        lblIcona.setIcon(new ImageIcon(imgIcona)); 
  

        // Pannello superiore
        groupNameLabel = new JLabel(gruppo.getNomeGruppo());
        groupNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        groupNameLabel.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        groupNameLabel.setBounds(87, 11, 288, 30);
        mainPanel.add(groupNameLabel);

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
                        
                       
                    } catch (NumberFormatException ex) {
                        System.out.println("Formato data non valido");
                    }
                } else {
                    System.out.println("Formato data non valido");
                }
                
                
                
                
                
                
                
            }
        });
        
        
        mainPanel.add(searchButton);

        dataTextField = new JTextField();
        dataTextField.setBounds(385, 11, 111, 30);
        dataTextField.setForeground(Color.GRAY); 
        dataTextField.setText("mm/aaaa"); 
        mainPanel.add(dataTextField);

        // label su i panelli superiori
        JLabel pubblicatiLabel = new JLabel("Pubblicazioni del mese:\r\n");
        pubblicatiLabel.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        pubblicatiLabel.setBounds(20, 61, 210, 20);
        mainPanel.add(pubblicatiLabel);

        // Pannelli superiori
        JPanel topPanel1 = new JPanel();
        topPanel1.setBackground(new Color(180, 210, 253)); 
        topPanel1.setBounds(10, 92, 282, 74);
        mainPanel.add(topPanel1);
        topPanel1.setLayout(null);
        
        JLabel LabelNumeroPost = new JLabel("New label");
        LabelNumeroPost.setFont(new Font("Tahoma", Font.PLAIN, 13));
        LabelNumeroPost.setBounds(113, 32, 59, 31);
        topPanel1.add(LabelNumeroPost);

        JLabel postLabel = new JLabel("Numero di Post:");
        postLabel.setBounds(87, 5, 102, 16);
        postLabel.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        topPanel1.add(postLabel);

        JPanel topPanel2 = new JPanel();
        topPanel2.setBackground(new Color(180, 210, 253)); 
        topPanel2.setBounds(294, 92, 282, 74);
        mainPanel.add(topPanel2);
        topPanel2.setLayout(null);
        
        JLabel LabelNumeroCommenti = new JLabel("New label");
        LabelNumeroCommenti.setFont(new Font("Tahoma", Font.PLAIN, 13));
        LabelNumeroCommenti.setBounds(123, 32, 64, 31);
        topPanel2.add(LabelNumeroCommenti);

        JLabel likeLabel = new JLabel("Numero di commenti:");
        likeLabel.setBounds(80, 5, 135, 16);
        likeLabel.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        topPanel2.add(likeLabel);

        // Label sopra pannelli
        JLabel interazioniLabel = new JLabel("Interazioni del mese:");
        interazioniLabel.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        interazioniLabel.setBounds(10, 192, 210, 20);
        mainPanel.add(interazioniLabel);

        // Pannelli inferiori
        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.setBackground(new Color(180, 210, 253)); 
        bottomPanel1.setBounds(10, 222, 282, 113);
        mainPanel.add(bottomPanel1);
        bottomPanel1.setLayout(null);

        JLabel mostCommentsLabel = new JLabel("Post con più Commenti:");
        mostCommentsLabel.setBounds(65, 5, 151, 16);
        mostCommentsLabel.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        bottomPanel1.add(mostCommentsLabel);

        JPanel bottomPanel2 = new JPanel();
        bottomPanel2.setBackground(new Color(180, 210, 253)); 
        bottomPanel2.setBounds(294, 222, 282, 113);
        mainPanel.add(bottomPanel2);
        bottomPanel2.setLayout(null);

       

        JLabel mostLikesLabel = new JLabel("Post con più Like:");
        mostLikesLabel.setBounds(87, 5, 133, 16);
        mostLikesLabel.setFont(new Font("Arial Bold", Font.PLAIN, 13));
        bottomPanel2.add(mostLikesLabel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StatisticheGruppoGUI::new);
    }
}
