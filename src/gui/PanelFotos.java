package gui;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Panel que muestra un carrusel de imágenes (slideshow) usando un hilo de
 * ejecución.
 * Basado en tu código de BienvenidaWindow.
 */
public class PanelFotos extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel lblFoto;

    private String[] imagenes = {
    	"/images/cinesamaxcenter.jpg", 
        "/images/salas.jpg", 
        "/images/comida.jpg", 
        "/images/salaxl.jpg"  
    };
    private int indiceImagen = 0;

    public PanelFotos() {
        setLayout(new BorderLayout());

        lblFoto = new JLabel();
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        
        add(lblFoto, BorderLayout.CENTER);

       
        cambiarImagen();

       
        iniciarCambioDeImagen();
    }

    private void cambiarImagen() {
        try {
            // getClass().getResource() busca el recurso en el classpath
            ImageIcon icono = new ImageIcon(getClass().getResource(imagenes[indiceImagen]));
            
            // Re-escalamos la imagen para que se ajuste (puedes cambiar 600x440)
            Image imagen = icono.getImage().getScaledInstance(600, 440, Image.SCALE_SMOOTH);
            
            lblFoto.setIcon(new ImageIcon(imagen));
            
        } catch (NullPointerException e) {
            // Esto ocurre si no encuentra la imagen en la ruta
            lblFoto.setText("Error: No se encontró la imagen en la ruta: " + imagenes[indiceImagen]);
            System.err.println("Error al cargar imagen: " + imagenes[indiceImagen]);
        }
    }

    //Hilo para el cambiar imagenes cada 3 segundos
    private void iniciarCambioDeImagen() {
        Thread hiloImagenes = new Thread(() -> {
            while (true) {
                try {
                    // Espera 3 segundos
                    Thread.sleep(3000);

                   
                    indiceImagen = (indiceImagen + 1) % imagenes.length;

                 
                    SwingUtilities.invokeLater(() -> cambiarImagen());
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        //daemon el hilo acaba cuando se cierra la ventana
        hiloImagenes.setDaemon(true);
        hiloImagenes.start();
    }
}