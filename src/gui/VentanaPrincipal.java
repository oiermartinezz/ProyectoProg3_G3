package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Corregido: VenatanaPrincipal -> VentanaPrincipal
public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    // --- Atributos de la clase ---
    private JPanel panelPrincipal;
    private JLabel lblEstado;

    // Botones (ahora como atributos para poder (des)habilitarlos)
    private JButton btnPeliculas;
    private JButton btnSalas;
    private JButton btnVentas;
    private JButton btnReportes;
    private JButton btnInicioSesion; // Nuevo botón

    private String usuarioLogueado = null; // Para guardar el nombre del usuario

    public VentanaPrincipal() {
        configurarVentana();
        inicializarComponentes();
        configurarEventos();
    }

    private void configurarVentana() {
        setTitle("CineManager - Gestion Cinematográfico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400));
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout());
        
        // --- 1. Panel de Cabecera (Norte) ---
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(41, 128, 185)); // Fondo azul

        // Título
        JLabel lblTitulo = new JLabel("CINEMANAGER");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelHeader.add(lblTitulo, BorderLayout.NORTH);

        // Panel para los botones del header
        JPanel panelBotonesHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotonesHeader.setOpaque(false); // Hacemos transparente para ver fondo azul

        // Creamos los botones
        btnInicioSesion = crearBoton("Iniciar Sesión", new Color(243, 156, 18)); // Naranja
        btnPeliculas = crearBoton("Gestión de Películas", new Color(52, 152, 219));
        btnSalas = crearBoton("Gestión de Salas", new Color(46, 204, 113));
        btnVentas = crearBoton("Venta de Entradas", new Color(231, 76, 60));
        btnReportes = crearBoton("Reportes", new Color(155, 89, 182));

        // Añadimos botones al panel de botones
        panelBotonesHeader.add(btnInicioSesion);
        panelBotonesHeader.add(btnPeliculas);
        panelBotonesHeader.add(btnSalas);
        panelBotonesHeader.add(btnVentas);
        panelBotonesHeader.add(btnReportes);
        
        panelHeader.add(panelBotonesHeader, BorderLayout.CENTER);
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // --- 2. Panel Central (Slideshow) ---
        // Aquí usamos la clase que creamos en el paso 1
        PanelFotos panelSlideshow = new PanelFotos();
        panelPrincipal.add(panelSlideshow, BorderLayout.CENTER);

        // --- 3. Panel de Estado (Sur) ---
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEstado.setBackground(Color.LIGHT_GRAY);
        panelEstado.setPreferredSize(new Dimension(0, 30));
        
        lblEstado = new JLabel("Sistema Listo - Por favor, inicie sesión.");
        panelEstado.add(lblEstado);
        panelPrincipal.add(panelEstado, BorderLayout.SOUTH);

        // --- Estado inicial de los botones ---
        habilitarBotones(false); // Los deshabilita todos menos el de login
        
        setContentPane(panelPrincipal);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14)); // Tamaño ajustado
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Corregido: mouseEtered -> mouseEntered
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Solo oscurece si el botón está habilitado
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

    private void configurarEventos() {
        
        // Evento para el nuevo botón de login
        btnInicioSesion.addActionListener(e -> solicitarInicioSesion());

        // Eventos para los botones de gestión
        btnPeliculas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (solicitarLoginAdmin()) {
					abrirGestionPeliculas();
				}
				//no hacemos else porque ya hemos definido que hacer sino en el propio metodo
			}
		});
        btnSalas.addActionListener(e -> abrirGestionSalas());
        btnVentas.addActionListener(e -> abrirVentas());
        btnReportes.addActionListener(e -> abrirReportes());
    }

    /**
     * Lógica de inicio de sesión (adaptada de tu NombreWindow)
     * ¡SIN CONEXIÓN A BD TODAVÍA!
     */
    private void solicitarInicioSesion() {
        String nombreCompleto = JOptionPane.showInputDialog(this, 
                "Introduce tu nombre y apellido para las reservas:", 
                "Inicio de Sesión", 
                JOptionPane.PLAIN_MESSAGE);

        // Verificamos que el usuario no ha pulsado "Cancelar" o ha dejado el campo vacío
        if (nombreCompleto != null && !nombreCompleto.trim().isEmpty()) {
            
            // Usamos tu misma lógica para validar nombre + apellido
            String[] partesNombre = nombreCompleto.trim().split("\\s+", 2);
            
            if (partesNombre.length == 2) {
                // Éxito
                this.usuarioLogueado = partesNombre[0]; // Guardamos el nombre
                
                JOptionPane.showMessageDialog(this, 
                    "¡Bienvenido/a, " + this.usuarioLogueado + "!", 
                    "Sesión Iniciada", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                lblEstado.setText("Usuario: " + nombreCompleto);
                habilitarBotones(true); // Habilita los botones de gestión

            } else {
                // Error: Formato incorrecto
                JOptionPane.showMessageDialog(this, 
                    "Por favor, introduce nombre y apellido separados por un espacio.", 
                    "Error de Formato", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } else if (nombreCompleto != null) {
            // Error: Campo vacío (solo espacios)
             JOptionPane.showMessageDialog(this, 
                    "El nombre no puede estar vacío.", 
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
        }
        // Si (nombreCompleto == null), el usuario pulsó "Cancelar", no hacemos nada.
    }

    /**
     * Habilita o deshabilita los botones de gestión.
     * @param habilitar true para habilitar, false para deshabilitar.
     */
    private void habilitarBotones(boolean habilitar) {
        btnPeliculas.setEnabled(habilitar);
        btnSalas.setEnabled(habilitar);
        btnVentas.setEnabled(habilitar);
        btnReportes.setEnabled(habilitar);

        // El botón de login tiene la lógica inversa
        btnInicioSesion.setEnabled(!habilitar);
    }
    
    // --- Métodos de acción (los que ya tenías) ---

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
         JOptionPane.showMessageDialog(this, 
                "Abriendo Venta de Entradas...", 
                "Venta de Entradas", 
                JOptionPane.INFORMATION_MESSAGE);
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
                return true; // Login correcto
            } else {
                // Error: Credenciales incorrectas
                JOptionPane.showMessageDialog(this, 
                    "Usuario o contraseña incorrectos.", 
                    "Acceso Denegado", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        return false; //Esto es solo si das acancelar
    }
}