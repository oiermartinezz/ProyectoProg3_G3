package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VenatanaPrincipal extends JFrame {
	 private JPanel panelPrincipal;
	 private JButton btnPeliculas;
	 private JButton btnSalas;
	 private JButton btnVentas;
	 private JButton btnReportes;
	 
	 public  VenatanaPrincipal() {
		 configurarVentana();
		 inicializarComponentes();
		 configurarEventos(); 
	 }
	 
	private void configurarVentana() {
		setTitle("CineManager - Gestion Cinematográfico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(600,400));
	}
	
	private void inicializarComponentes() {
		panelPrincipal = new JPanel(new BorderLayout());
		
		JPanel panelTitulo = new JPanel();
		panelTitulo.setBackground(new Color(41, 128, 185));
		panelTitulo.setPreferredSize(new Dimension(0,80));
		
		JLabel lblTitulo = new JLabel("CINEMANAGER");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitulo.setForeground(Color.WHITE);
		panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
		
		JPanel panelBotones = new JPanel(new GridLayout(2, 2, 20, 20));
		panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		panelBotones.setBackground(new Color(236, 240, 241));
		
		btnPeliculas = crearBoton("Gestion de Peliculas", new Color (52,152,219));
        btnSalas = crearBoton(" Gestión de Salas", new Color(46, 204, 113));
        btnVentas = crearBoton("Venta de Entradas", new Color(231, 76, 60));
        btnReportes = crearBoton("Reportes y Estadísticas", new Color(155, 89, 182));
		
        panelBotones.add(btnPeliculas);
        panelBotones.add(btnSalas);
        panelBotones.add(btnVentas);
        panelBotones.add(btnReportes);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEstado.setBackground(Color.LIGHT_GRAY);
        panelEstado.setPreferredSize(new Dimension(0, 30));
        
        JLabel lblEstado = new JLabel("Sistema Listo - Bienvenido");
        panelEstado.add(lblEstado);
        panelPrincipal.add(panelEstado, BorderLayout.SOUTH);
        
        setContentPane(panelPrincipal);
	}
	
	private JButton crearBoton(String texto, Color color) {
		JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
        	public void mouseEtered(java.awt.event.MouseEvent evt) {
        		boton.setBackground(color.darker());
        	}
        	public void mouseExited(java.awt.event.MouseEvent evt) {
        		boton.setBackground(color);
        	}
        });
        
        return boton;	
	}
	
	private void configurarEventos() {
		btnPeliculas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				abrirGestionPeliculas();
			}
			
		});
		
		btnSalas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				abrirGestionSalas();
			}
			
		});
		
		btnReportes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				abrirReportes();	
			}
			
		});
	}
	
	private void abrirGestionPelicula() {
		JOptionPane.showMessageDialog(this, 
				"Abriendo gestion de películas ....",
				"Gestión de películas",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void abrirGestionSalas() {
		 JOptionPane.showMessageDialog(this, 
		            "Abriendo Gestión de Salas...)", 
		            "Gestión de Salas", 
		            JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void abrirVentas() {
        JOptionPane.showMessageDialog(this, 
                "Abriendo Venta de Entradas...)", 
                "Venta de Entradas", 
                JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void abrirReportes() {
        JOptionPane.showMessageDialog(this, 
                "Abriendo Reportes...)", 
                "Reportes y Estadísticas", 
                JOptionPane.INFORMATION_MESSAGE);
        }
	
	private void abrirGestionPeliculas() {
	    DialogoPeliculas dialogo = new DialogoPeliculas(this);
	    dialogo.setVisible(true);
	    
	}



	 
	

}
