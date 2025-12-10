package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import domain.Pelicula;
import db.GestorBD; // Importamos BD

public class DialogoPeliculas extends JDialog {
    
    private static final long serialVersionUID = 1L;
    private JTextField txtBusqueda;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JTable tablaPeliculas;
    private PeliculasTableModel modeloTabla;
    
    // Necesitamos el gestor aquí para pasárselo al modelo
    private GestorBD gestorBD; 
    
    public DialogoPeliculas(JFrame parent) {
        super(parent, "Gestión de Películas", true);
        this.gestorBD = new GestorBD();
        
        configurarDialogo();
        inicializarComponentes();
        configurarEventos();
    }
    
    private void configurarDialogo() {
        setSize(700, 500); 
        setLocationRelativeTo(getParent());
        setResizable(true);
    }
    
    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel (new BorderLayout());
        
        JPanel panelBusqueda = new JPanel (new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblBuscar = new JLabel("Buscar");
        txtBusqueda = new JTextField(20);
        btnAgregar = new JButton ("Agregar Pelicula");
        
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBusqueda);
        panelBusqueda.add(btnAgregar);
        
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createTitledBorder("Lista de Películas (BD)"));
        
        modeloTabla = new PeliculasTableModel(gestorBD);
        tablaPeliculas = new JTable(modeloTabla);
        
        JScrollPane scrollPane = new JScrollPane(tablaPeliculas);
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        JButton btnCerrar = new JButton("Cerrar");
        
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        
        btnCerrar.addActionListener(e -> dispose());
        
        panelPrincipal.add(panelBusqueda, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        setContentPane(panelPrincipal);
    }
    
    private void configurarEventos() {
        txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("Buscando: " + txtBusqueda.getText());
            }
        });
        
        btnAgregar.addActionListener(e -> agregarPelicula());
        btnEditar.addActionListener(e -> editarPelicula());
        btnEliminar.addActionListener(e -> eliminarPelicula());

        tablaPeliculas.getSelectionModel().addListSelectionListener(e -> {
            boolean haySeleccion = tablaPeliculas.getSelectedRow() != -1;
            btnEditar.setEnabled(haySeleccion);
            btnEliminar.setEnabled(haySeleccion);
        });
    }
    
    private void agregarPelicula() {
        DialogoFormularioPelicula formulario = new DialogoFormularioPelicula(this, null);
        formulario.setVisible(true);

        if (formulario.isGuardado()) {
            Pelicula nuevaPeli = formulario.getPelicula();
            modeloTabla.agregarPelicula(nuevaPeli);
        }
    }
    
    private void editarPelicula() {
        int fila = tablaPeliculas.getSelectedRow();
        if (fila == -1) return; 
        
        Pelicula peliSeleccionada = modeloTabla.getPeliculaAt(fila);

        DialogoFormularioPelicula formulario = new DialogoFormularioPelicula(this, peliSeleccionada);
        formulario.setVisible(true);
        
        if (formulario.isGuardado()) {
            modeloTabla.actualizarFila(fila);
        }
    }
    
    private void eliminarPelicula() {
        int fila = tablaPeliculas.getSelectedRow();
        if (fila == -1) return;

        Pelicula peli = modeloTabla.getPeliculaAt(fila);
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Seguro que quieres borrar '" + peli.getTitulo() + "' de la Base de Datos?",
            "Confirmar Borrado",
            JOptionPane.YES_NO_OPTION);
            
        if (confirmacion == JOptionPane.YES_OPTION) {
            modeloTabla.eliminarPelicula(fila);
        }
    }
}