package gui;

import javax.swing.*;
import java.awt.*;


public class VentanaCarga extends JWindow {

    private static final long serialVersionUID = 1L;
    private JProgressBar progressBar;

    public VentanaCarga() {
    
        setSize(400, 100);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        //CARGANDO
        JLabel lblCargando = new JLabel("Cargando CineManager...", SwingConstants.CENTER);
        lblCargando.setFont(new Font("Arial", Font.BOLD, 14));
        lblCargando.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(lblCargando, BorderLayout.NORTH);

        
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); 
        progressBar.setForeground(new Color(52, 152, 219)); 
        progressBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panel.add(progressBar, BorderLayout.CENTER);
        
        add(panel);
    }

    public void setProgress(int value) {
        progressBar.setValue(value);
    }
}
//Codigo adaptado de una correcion de codigo que teniamos hecha con Roberto.