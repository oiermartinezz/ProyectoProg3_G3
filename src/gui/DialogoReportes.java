package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import db.GestorBD;

public class DialogoReportes extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextArea areaTexto;
    private GestorBD gestorBD;

    public DialogoReportes(JFrame parent, GestorBD gestorBD) {
        super(parent, "Reporte de Accesos", true);
        this.gestorBD = gestorBD;
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Título
        JLabel lblTitulo = new JLabel("Historial de Logins", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        // Área de texto
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        // Botón Cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar, BorderLayout.SOUTH);

        cargarDatos();
    }

    private void cargarDatos() {
        // Aquí llamamos al método que acabas de crear en la Tarea 1
        List<String> logins = gestorBD.obtenerHistorialLogins();
        
        if (logins.isEmpty()) {
            areaTexto.setText("No hay registros.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String linea : logins) {
                sb.append(linea).append("\n");
            }
            areaTexto.setText(sb.toString());
        }
    }
}
