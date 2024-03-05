package guiUninaSocialGroup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.Controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImpostazioniGUI extends JFrame {

    private JPanel contentPane;
    private JTextField textField;

    public ImpostazioniGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 403, 534);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(226, 235, 248));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JTextField();
        textField.setBounds(75, 47, 199, 24);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton modificaImmagineButton = new JButton("Carica");
        modificaImmagineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
           
                String imageUrl = textField.getText();
                try {
                
                    URL url = new URL(imageUrl);
                    
                    String nicknameUtente = Controller.utenteDAO.getUtenteFromArrayListById(Controller.myIdUtente).getNickname(); 
                    
                    Controller.utenteDAO.updateUrlFotoProfiloByNickname(nicknameUtente, imageUrl);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ImpostazioniGUI.this, "Impossibile caricare l'immagine dall'URL fornito.\n "
                    		+ "Verifica la tua connessione ad internet.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        modificaImmagineButton.setBounds(75, 80, 199, 27);
        contentPane.add(modificaImmagineButton);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ImpostazioniGUI frame = new ImpostazioniGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
