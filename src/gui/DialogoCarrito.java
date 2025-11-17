package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class DialogoCarrito extends JDialog {
    
    private static final long serialVersionUID = 1L;
    private JTextArea carritoArea;  
    private List<String> itemsSeleccionados;  
    private JButton confirmarButton; 
    private JButton cerrarButton;
    private String nombreUsuario;
    private static List<String> carritoGlobal = new ArrayList<>();

    public DialogoCarrito(JFrame parent, String nombreUsuario) {
        super(parent, "Tu Carrito de Compra", true);
        this.nombreUsuario = nombreUsuario;
        this.itemsSeleccionados = carritoGlobal;
        
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        
        inicializarComponentes();
        actualizarCarrito();
    }
    
    private void inicializarComponentes() {
      
        carritoArea = new JTextArea();
        carritoArea.setEditable(false);
        carritoArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(carritoArea), BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel();
        
        confirmarButton = new JButton("Finalizar y Pagar");
        cerrarButton = new JButton("Seguir Comprando");
        
        confirmarButton.addActionListener(e -> confirmarReserva());
        cerrarButton.addActionListener(e -> dispose());
        
        panelBotones.add(confirmarButton);
        panelBotones.add(cerrarButton);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    //estático para añadir cosas desde fuera sin abrir la ventana
    public static void agregarAlCarrito(String item) {
        carritoGlobal.add(item);
    }

    private void actualizarCarrito() {
        if (itemsSeleccionados.isEmpty()) {
            carritoArea.setText("El carrito está vacío.");
            confirmarButton.setEnabled(false);
        } else {
            carritoArea.setText("Resumen del pedido para: " + nombreUsuario + "\n\n");
            //double total = 0; 
            for (String item : itemsSeleccionados) {
                carritoArea.append("• " + item + "\n");
            }
            confirmarButton.setEnabled(true);
        }
    }

    private void confirmarReserva() {
       //aqui se simula el exito pero en realidad se llamaria a la BD
        JOptionPane.showMessageDialog(this, 
            "¡Compra realizada con éxito a nombre de " + nombreUsuario + "!\n" +
            "Disfruta de la(s) película(s).", 
            "Pago Confirmado", 
            JOptionPane.INFORMATION_MESSAGE);
            
        itemsSeleccionados.clear(); //vaciar el carrito
        actualizarCarrito();
        dispose();
    }
}