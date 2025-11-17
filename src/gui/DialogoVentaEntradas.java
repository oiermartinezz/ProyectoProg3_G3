package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DialogoVentaEntradas extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private JList<String> listaDias;
    private JList<String> listaPeliculas;
    private JComboBox<String> comboHoras;
    private JSpinner spAdultos;
    private JSpinner spNinos;
    private JLabel lblTotal;
    private JButton btnSeleccionarAsientos;
    private JButton btnAnadirCarrito;

    //DATOS
    private DefaultListModel<String> modeloPeliculas;
    private final double PRECIO_ADULTO = 9.00;
    private final double PRECIO_NINO = 6.50;
    
    //ESTADO ASIENTOS
    private List<String> asientosElegidos = null;

    public DialogoVentaEntradas(JFrame parent) {
        super(parent, "Comprar Entradas", true);
        setSize(900, 600);
        setLocationRelativeTo(parent);
        
        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        //DIAS PARA ESCOGER
        JPanel panelIzq = new JPanel(new BorderLayout());
        panelIzq.setBorder(BorderFactory.createTitledBorder("1. Elige Fecha"));
        panelIzq.setPreferredSize(new Dimension(150, 0));
        
        String[] dias = {"Hoy (Lun)", "Mar 18", "Mié 19", "Jue 20", "Vie 21", "Sáb 22", "Dom 23"};
        listaDias = new JList<>(dias);
        listaDias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaDias.setSelectedIndex(0); //CON ESTO SE ESCOGE POR DEFECTO EL DIA DE HOY
        panelIzq.add(new JScrollPane(listaDias), BorderLayout.CENTER);


        
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBorder(BorderFactory.createTitledBorder("2. Elige Película"));
        
        modeloPeliculas = new DefaultListModel<>();
        
        cargarPeliculas("Hoy"); 
        
        listaPeliculas = new JList<>(modeloPeliculas);
        listaPeliculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // 
        panelCentro.add(new JScrollPane(listaPeliculas), BorderLayout.CENTER);


        
        JPanel panelDer = new JPanel(new GridBagLayout());
        panelDer.setBorder(BorderFactory.createTitledBorder("3. Entradas y Asientos"));
        panelDer.setPreferredSize(new Dimension(300, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        // Hora
        panelDer.add(new JLabel("Horario:"), gbc);
        gbc.gridy++;
        String[] horas = {"16:00", "18:30", "21:00", "23:00 (V.O)"};
        comboHoras = new JComboBox<>(horas);
        panelDer.add(comboHoras, gbc);

        // Spinners Entradas
        gbc.gridy++;
        panelDer.add(new JLabel("Adultos (" + PRECIO_ADULTO + "€):"), gbc);
        gbc.gridy++;
        spAdultos = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1));
        panelDer.add(spAdultos, gbc);

        gbc.gridy++;
        panelDer.add(new JLabel("Niños (" + PRECIO_NINO + "€):"), gbc);
        gbc.gridy++;
        spNinos = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        panelDer.add(spNinos, gbc);
        
        // Precio Total
        gbc.gridy++;
        lblTotal = new JLabel("Total: 9.00 €");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        panelDer.add(lblTotal, gbc);

        // Botón Asientos
        gbc.gridy++;
        btnSeleccionarAsientos = new JButton("Seleccionar Asientos");
        btnSeleccionarAsientos.setBackground(Color.ORANGE);
        btnSeleccionarAsientos.setEnabled(false); // Deshabilitado hasta elegir peli
        panelDer.add(btnSeleccionarAsientos, gbc);


        //carrito
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAnadirCarrito = new JButton("Añadir al Carrito");
        btnAnadirCarrito.setFont(new Font("Arial", Font.BOLD, 14));
        btnAnadirCarrito.setBackground(new Color(52, 152, 219));
        btnAnadirCarrito.setForeground(Color.WHITE);
        btnAnadirCarrito.setEnabled(false); 
        
        panelSur.add(btnAnadirCarrito);

       
        add(panelIzq, BorderLayout.WEST);
        add(panelCentro, BorderLayout.CENTER);
        add(panelDer, BorderLayout.EAST);
        add(panelSur, BorderLayout.SOUTH);
    }

    private void configurarEventos() {
        
        listaDias.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarPeliculas(listaDias.getSelectedValue());
            }
        });

        
        listaPeliculas.addListSelectionListener(e -> {
             btnSeleccionarAsientos.setEnabled(listaPeliculas.getSelectedValue() != null);
            
             asientosElegidos = null;
             btnAnadirCarrito.setEnabled(false);
             btnSeleccionarAsientos.setText("Seleccionar Asientos");
             btnSeleccionarAsientos.setBackground(Color.ORANGE);
        });

        // Recalcular precio al cambiar spinners
        spAdultos.addChangeListener(e -> calcularTotal());
        spNinos.addChangeListener(e -> calcularTotal());

        
        btnSeleccionarAsientos.addActionListener(e -> abrirSelectorAsientos());
        btnAnadirCarrito.addActionListener(e -> enviarAlCarrito());
    }

    private void cargarPeliculas(String dia) {
        modeloPeliculas.clear();
        // Simulación de BD
        if (dia != null && dia.contains("Mié")) {
            modeloPeliculas.addElement("Dune: Parte Dos (Sala IMAX)");
            modeloPeliculas.addElement("Kung Fu Panda 4");
        } else {
            modeloPeliculas.addElement("Dune: Parte Dos (Sala XL)");
            modeloPeliculas.addElement("Oppenheimer");
            modeloPeliculas.addElement("Barbie");
            modeloPeliculas.addElement("El Padrino");
        }
    }

    private void calcularTotal() {
        int numAdultos = (int) spAdultos.getValue();
        int numNinos = (int) spNinos.getValue();
        
       
        if (numAdultos + numNinos == 0) {
            btnSeleccionarAsientos.setEnabled(false);
        } else if (listaPeliculas.getSelectedValue() != null) {
            btnSeleccionarAsientos.setEnabled(true);
        }

        double total = (numAdultos * PRECIO_ADULTO) + (numNinos * PRECIO_NINO);
        lblTotal.setText(String.format("Total: %.2f €", total));
    }

    private void abrirSelectorAsientos() {
        int totalEntradas = (int) spAdultos.getValue() + (int) spNinos.getValue();
        String peliSeleccionada = listaPeliculas.getSelectedValue();
        
        // Detectamos si es sala XL por el nombre
        boolean esSalaXL = peliSeleccionada.contains("XL") || peliSeleccionada.contains("IMAX");

        DialogoSeleccionAsientos dialogo = new DialogoSeleccionAsientos(this, totalEntradas, esSalaXL);
        dialogo.setVisible(true);

       
        List<String> seleccion = dialogo.getAsientosSeleccionados();
        if (seleccion != null) {
            this.asientosElegidos = seleccion;
            btnSeleccionarAsientos.setText("Asientos: " + seleccion.toString());
            btnSeleccionarAsientos.setBackground(Color.GREEN);
            btnAnadirCarrito.setEnabled(true); 
        }
    }

    private void enviarAlCarrito() {
        //String para qye salga en el carrito
        String peli = listaPeliculas.getSelectedValue();
        String dia = listaDias.getSelectedValue();
        String hora = (String) comboHoras.getSelectedItem();
        String resumen = String.format("%s | %s a las %s | Asientos: %s | Precio: %s", 
                peli, dia, hora, asientosElegidos.toString(), lblTotal.getText());

        DialogoCarrito.agregarAlCarrito(resumen);

        JOptionPane.showMessageDialog(this, "Añadido al carrito correctamente.");
        dispose(); 
    }
}