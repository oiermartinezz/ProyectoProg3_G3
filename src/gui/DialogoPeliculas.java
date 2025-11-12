package gui;

import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class DialogoPeliculas extends JDialog {
	private JTextField txtBusqueda;
	private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    
    public DialogoPeliculas( JFrame parent) {
    	super(parent, "Gestión de Películas", true);
        configurarDialogo();
        inicializarComponentes();
        configurarEventos();
    	
    }
    
    private void configurarDialogo() {
        setSize(600, 400);
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
        panelCentral.setBorder(BorderFactory.createTitledBorder("Lista de Películas"));
        
        JLabel lblPlaceholder = new JLabel("Aquí irá la JTable personalizada con las películas", 
                JLabel.CENTER);
        lblPlaceholder.setForeground(Color.GRAY);
        panelCentral.add(lblPlaceholder, BorderLayout.CENTER);
    	
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        JButton btnCerrar = new JButton("Cerrar");
        
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        
       
        panelPrincipal.add(panelBusqueda, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        setContentPane(panelPrincipal);
    }
    
    private void configurarEventos() {
        txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarPeliculas();
            }
        });
        
        
        btnAgregar.addActionListener(e -> agregarPelicula());
        btnEditar.addActionListener(e -> editarPelicula());
        btnEliminar.addActionListener(e -> eliminarPelicula());
    }
    
    private void buscarPeliculas() {
        String texto = txtBusqueda.getText();
        System.out.println("Buscando: " + texto);
    }
    
    private void agregarPelicula() {
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad: Agregar nueva película", 
            "Agregar Película", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void editarPelicula() {
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad: Editar película seleccionada", 
            "Editar Película", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void eliminarPelicula() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar la película seleccionada?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Película eliminada correctamente");
        }
    
    
    }
	

}
