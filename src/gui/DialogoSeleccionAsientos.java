package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DialogoSeleccionAsientos extends JDialog {

	private static final long serialVersionUID = 1L;
	private int cantidadEntradas;
    private JPanel panelButacas;
    private JButton btnConfirmar;
    private JLabel lblInfo;
    
    private List<String> asientosSeleccionados = new ArrayList<>();
    private boolean seleccionConfirmada = false;

    public DialogoSeleccionAsientos(JDialog parent, int cantidadEntradas, boolean salaXL) {
        super(parent, "Selecciona tus butacas", true);
        this.cantidadEntradas = cantidadEntradas;
        
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(Color.DARK_GRAY);
        JLabel lblPantalla = new JLabel("PANTALLA", SwingConstants.CENTER);
        lblPantalla.setForeground(Color.WHITE);
        lblPantalla.setPreferredSize(new Dimension(0, 40));
        panelNorte.add(lblPantalla, BorderLayout.CENTER);
        
        lblInfo = new JLabel("Selecciona " + cantidadEntradas + " butacas", SwingConstants.CENTER);
        lblInfo.setOpaque(true);
        lblInfo.setBackground(Color.YELLOW);
        panelNorte.add(lblInfo, BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);

        //butacas
        crearPanelButacas(salaXL);
        add(panelButacas, BorderLayout.CENTER);

        // Footer
        btnConfirmar = new JButton("Confirmar Selección");
        btnConfirmar.setEnabled(false);
        btnConfirmar.addActionListener(e -> {
            seleccionConfirmada = true;
            setVisible(false);
        });
        add(btnConfirmar, BorderLayout.SOUTH);
    }

    private void crearPanelButacas(boolean esXL) {
        int filas = esXL ? 8 : 5;
        int columnas = esXL ? 10 : 6;
        
        panelButacas = new JPanel(new GridLayout(filas, columnas, 5, 5));
        panelButacas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JToggleButton btn = new JToggleButton((i+1) + "-" + (j+1));
                btn.setBackground(new Color(144, 238, 144)); 

                btn.addActionListener(e -> gestionarClickAsiento(btn));
                panelButacas.add(btn);
            }
        }
    }

    private void gestionarClickAsiento(JToggleButton btn) {
        String idAsiento = btn.getText();

        if (btn.isSelected()) {
            
            if (asientosSeleccionados.size() < cantidadEntradas) {
                asientosSeleccionados.add(idAsiento);
                btn.setBackground(Color.DARK_GRAY);
                btn.setForeground(Color.WHITE);
            } else {
                
                btn.setSelected(false);
                JOptionPane.showMessageDialog(this, "Solo puedes seleccionar " + cantidadEntradas + " asientos.");
            }
        } else {
           
            asientosSeleccionados.remove(idAsiento);
            btn.setBackground(new Color(144, 238, 144)); 
            btn.setForeground(Color.BLACK);
        }

       
        int restantes = cantidadEntradas - asientosSeleccionados.size();
        if (restantes == 0) {
            lblInfo.setText("¡Selección completa!");
            lblInfo.setBackground(Color.GREEN);
            btnConfirmar.setEnabled(true);
        } else {
            lblInfo.setText("Faltan por seleccionar: " + restantes);
            lblInfo.setBackground(Color.YELLOW);
            btnConfirmar.setEnabled(false);
        }
    }

    public List<String> getAsientosSeleccionados() {
        return seleccionConfirmada ? asientosSeleccionados : null;
    }
}