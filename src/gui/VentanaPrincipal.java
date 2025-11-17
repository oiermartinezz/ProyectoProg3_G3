package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel panelPrincipal;
    private JLabel lblEstado;

    private JButton btnPeliculas;
    private JButton btnSalas;
    private JButton btnVentas;
    private JButton btnReportes;
    private JButton btnInicioSesion; 
    private JButton btnVerCarrito; // Botón del carrito

    private String usuarioLogueado = null; 

    public VentanaPrincipal() {
        configurarVentana();
        inicializarComponentes();
        configurarEventos();
    }

    private void configurarVentana() {
        setTitle("CineManager - Gestion Cinematográfico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400));
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout());
        
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(41, 128, 185)); 

        JLabel lblTitulo = new JLabel("CINEMANAGER");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelHeader.add(lblTitulo, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelBotonesHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotonesHeader.setOpaque(false); 

        
        btnInicioSesion = crearBoton("Iniciar Sesión", new Color(243, 156, 18)); 
        btnPeliculas = crearBoton("Gestión de Películas", new Color(52, 152, 219));
        btnSalas = crearBoton("Gestión de Salas", new Color(46, 204, 113));
        btnVentas = crearBoton("Venta de Entradas", new Color(231, 76, 60));
        btnReportes = crearBoton("Reportes", new Color(155, 89, 182));
        
       
        btnVerCarrito = crearBotonIcono("/images/carrito.png");

        panelBotonesHeader.add(btnInicioSesion);
        panelBotonesHeader.add(btnPeliculas);
        panelBotonesHeader.add(btnSalas);
        panelBotonesHeader.add(btnVentas);
        panelBotonesHeader.add(btnReportes);
        panelBotonesHeader.add(btnVerCarrito); 
        
        panelHeader.add(panelBotonesHeader, BorderLayout.CENTER);
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

      
        PanelFotos panelSlideshow = new PanelFotos();
        panelPrincipal.add(panelSlideshow, BorderLayout.CENTER);

        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEstado.setBackground(Color.LIGHT_GRAY);
        panelEstado.setPreferredSize(new Dimension(0, 30));
        
        lblEstado = new JLabel("Sistema Listo - Por favor, inicie sesión.");
        panelEstado.add(lblEstado);
        panelPrincipal.add(panelEstado, BorderLayout.SOUTH);

        habilitarBotones(false); 
        
        setContentPane(panelPrincipal);
    }

    // Método para botones normales (Texto)
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14)); 
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (boton.isEnabled()) {
                    boton.setBackground(color.darker());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });
        return boton;
    }
    

    private JButton crearBotonIcono(String rutaImagen) {
        JButton boton = new JButton();
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaImagen));
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
        } catch (Exception e) {
            boton.setText("CARRITO"); 
            System.err.println("Error cargando imagen carrito: " + rutaImagen);
        }
        
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        
        boton.setToolTipText("Ver mi Carrito de Compra");
        
        return boton;
    }

    private void configurarEventos() {
        btnInicioSesion.addActionListener(e -> solicitarInicioSesion());

        btnPeliculas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (solicitarLoginAdmin()) {
                    abrirGestionPeliculas();
                }
            }
        });
        
        btnSalas.addActionListener(e -> abrirGestionSalas());
        btnVentas.addActionListener(e -> abrirVentas());
        btnReportes.addActionListener(e -> abrirReportes());
        btnVerCarrito.addActionListener(e -> abrirCarrito());
    }

    private void habilitarBotones(boolean habilitar) {
        btnPeliculas.setEnabled(habilitar);
        btnSalas.setEnabled(habilitar);
        btnVentas.setEnabled(habilitar);
        btnReportes.setEnabled(habilitar);
        btnVerCarrito.setEnabled(habilitar); 
        
        btnInicioSesion.setEnabled(!habilitar);
    }
    
    private void abrirCarrito() {     
        if (usuarioLogueado != null) {
            DialogoCarrito dialogo = new DialogoCarrito(this, usuarioLogueado);
            dialogo.setVisible(true);
        }
    }

    private void solicitarInicioSesion() {
        String nombreCompleto = JOptionPane.showInputDialog(this, 
                "Introduce tu nombre y apellido para las reservas:", 
                "Inicio de Sesión", 
                JOptionPane.PLAIN_MESSAGE);

        if (nombreCompleto != null && !nombreCompleto.trim().isEmpty()) {
            String[] partesNombre = nombreCompleto.trim().split("\\s+", 2);
            
            if (partesNombre.length == 2) {
                this.usuarioLogueado = partesNombre[0]; 
                JOptionPane.showMessageDialog(this, 
                    "¡Bienvenido/a, " + this.usuarioLogueado + "!", 
                    "Sesión Iniciada", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                lblEstado.setText("Usuario: " + nombreCompleto);
                habilitarBotones(true); 
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, introduce nombre y apellido separados por un espacio.", 
                    "Error de Formato", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } else if (nombreCompleto != null) {
             JOptionPane.showMessageDialog(this, 
                    "El nombre no puede estar vacío.", 
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void abrirGestionPeliculas() {
        DialogoPeliculas dialogo = new DialogoPeliculas(this);
        dialogo.setVisible(true);
    }

    private void abrirGestionSalas() {
         JOptionPane.showMessageDialog(this, 
                "Abriendo Gestión de Salas...", 
                "Gestión de Salas", 
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirVentas() {
        if (usuarioLogueado != null) {
            DialogoVentaEntradas dialogo = new DialogoVentaEntradas(this);
            dialogo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, inicia sesión para comprar entradas."); 
        }
    }
    
    private void abrirReportes() {
         JOptionPane.showMessageDialog(this, 
                "Abriendo Reportes...", 
                "Reportes y Estadísticas", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private boolean solicitarLoginAdmin() {
        JPanel panelLogin = new JPanel(new BorderLayout(5, 5));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelGrid = new JPanel(new GridLayout(2, 2, 5, 5));
        
        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField(20);
        JLabel lblPassword = new JLabel("Contraseña:");
        JPasswordField txtPassword = new JPasswordField();

        panelGrid.add(lblUsuario);
        panelGrid.add(txtUsuario);
        panelGrid.add(lblPassword);
        panelGrid.add(txtPassword);
        
        panelLogin.add(new JLabel("Acceso exclusivo de administrador"), BorderLayout.NORTH);
        panelLogin.add(panelGrid, BorderLayout.CENTER);
        
        SwingUtilities.invokeLater(() -> txtUsuario.requestFocusInWindow());
        
        int resultado = JOptionPane.showConfirmDialog(
                this, 
                panelLogin, 
                "Login de Administrador", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            String usuario = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());
            
            final String ADMIN_USER = "admin";
            final String ADMIN_PASS = "admin";
            
            if (usuario.equals(ADMIN_USER) && password.equals(ADMIN_PASS)) {
                return true; 
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Usuario o contraseña incorrectos.", 
                    "Acceso Denegado", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false; 
    }
}
//teniamos la idea de hacer el boton solo admin pero no sabiamos como llevarla a cabo