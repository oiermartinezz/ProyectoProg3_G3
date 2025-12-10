package Main; 

import javax.swing.SwingUtilities;
import db.GestorBD;
import gui.VentanaCarga;
import gui.VentanaPrincipal;

public class CineManagerApp {

    public static void main(String[] args) {


        VentanaCarga ventanaCarga = new VentanaCarga();
        ventanaCarga.setVisible(true);

        Thread hiloCarga = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                	
                	System.out.println("Iniciando conexión con Base de Datos...");
                	new GestorBD();
                	System.out.println("Base de Datos cargada correctamente.");
                    
                    for (int i = 0; i <= 100; i++) {
                        
                      
                        Thread.sleep(25); 

                       
                        final int progreso = i;
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                ventanaCarga.setProgress(progreso);
                            }
                        });
                    }

                  
                    Thread.sleep(500);

                   
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            ventanaCarga.setVisible(false);
                            ventanaCarga.dispose(); 
                        }
                    });

                   
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new VentanaPrincipal().setVisible(true);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

       
        hiloCarga.start();
    }
}