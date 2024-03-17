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
                        
                        
                        float mediaP = Controller.postDAO.getMediaPostInUnMese(mese , anno , gruppo);
                        
                        JLabel mediaLabel = new JLabel("mediaP");
                        mediaLabel.setBounds(144, 61, 49, 14);
                        mainPanel.add(mediaLabel);
                          
                       
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
        JLabel LabelMediaGiornaliera = new JLabel("Media giornaliera:\r\n");
        LabelMediaGiornaliera.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        LabelMediaGiornaliera.setBounds(20, 52, 128, 32);
        mainPanel.add(LabelMediaGiornaliera);

        // Pannelli superiori
        JPanel topPanel1 = new JPanel();
        topPanel1.setBackground(new Color(180, 210, 253)); 
        topPanel1.setBounds(10, 95, 282, 116);
        mainPanel.add(topPanel1);
        topPanel1.setLayout(null);

        JLabel LabelMenoCommenti = new JLabel("Post con meno commenti:");
        LabelMenoCommenti.setBounds(69, 5, 177, 16);
        LabelMenoCommenti.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        topPanel1.add(LabelMenoCommenti);
        
        JScrollPane scrollPaneMenoCommenti = new JScrollPane();
        scrollPaneMenoCommenti.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneMenoCommenti.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneMenoCommenti.setBounds(10, 22, 262, 83);
        topPanel1.add(scrollPaneMenoCommenti);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(180, 210, 253));
        scrollPaneMenoCommenti.setViewportView(panel);

        JPanel topPanel2 = new JPanel();
        topPanel2.setBackground(new Color(180, 210, 253)); 
        topPanel2.setBounds(294, 95, 282, 116);
        mainPanel.add(topPanel2);
        topPanel2.setLayout(null);

        JLabel LabelMenoLike = new JLabel("Post con meno Like:");
        LabelMenoLike.setBounds(80, 5, 135, 16);
        LabelMenoLike.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        topPanel2.add(LabelMenoLike);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10, 24, 262, 81);
        topPanel2.add(scrollPane);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(180, 210, 253));
        scrollPane.setViewportView(panel_2);

        // Pannelli inferiori
        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.setBackground(new Color(180, 210, 253)); 
        bottomPanel1.setBounds(10, 222, 282, 130);
        mainPanel.add(bottomPanel1);
        bottomPanel1.setLayout(null);

        JLabel LabelPiuCommenti = new JLabel("Post con più Commenti:");
        LabelPiuCommenti.setBounds(65, 5, 151, 16);
        LabelPiuCommenti.setFont(new Font("Arial Bold", Font.PLAIN, 13)); 
        bottomPanel1.add(LabelPiuCommenti);
        
        JScrollPane scrollPanePiùCommenti = new JScrollPane();
        scrollPanePiùCommenti.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanePiùCommenti.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanePiùCommenti.setBounds(10, 25, 262, 94);
        bottomPanel1.add(scrollPanePiùCommenti);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(180, 210, 253));
        scrollPanePiùCommenti.setViewportView(panel_1);

        JPanel bottomPanel2 = new JPanel();
        bottomPanel2.setBackground(new Color(180, 210, 253)); 
        bottomPanel2.setBounds(294, 222, 282, 130);
        mainPanel.add(bottomPanel2);
        bottomPanel2.setLayout(null);

       

        JLabel LabelPiuLike = new JLabel("Post con più Like:");
        LabelPiuLike.setBounds(87, 5, 133, 16);
        LabelPiuLike.setFont(new Font("Arial Bold", Font.PLAIN, 13));
        bottomPanel2.add(LabelPiuLike);
        
        JScrollPane scrollPanePiùLike = new JScrollPane();
        scrollPanePiùLike.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanePiùLike.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanePiùLike.setBounds(10, 21, 262, 98);
        bottomPanel2.add(scrollPanePiùLike);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(new Color(180, 210, 253));
        scrollPanePiùLike.setViewportView(panel_3);
        
        JLabel mediaLabel = new JLabel("");
        mediaLabel.setBounds(144, 61, 49, 14);
        mainPanel.add(mediaLabel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StatisticheGruppoGUI::new);
    }
}
