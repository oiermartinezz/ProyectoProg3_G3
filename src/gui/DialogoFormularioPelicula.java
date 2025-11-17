package gui;

import javax.swing.*;
import java.awt.*;
import domain.Pelicula;

public class DialogoFormularioPelicula extends JDialog {


	private static final long serialVersionUID = 1L;
	private JTextField txtTitulo;
    private JTextField txtGenero;
    private JSpinner spDuracion;
    private JComboBox<String> Calificacion;
    
    private boolean guardado = false;
    private Pelicula pelicula;

    public DialogoFormularioPelicula(JDialog parent, Pelicula peliculaeditar) {
        super(parent, "Datos de la Película", true);
        
        if (peliculaeditar == null) {
            this.pelicula = new Pelicula(); 
            this.pelicula.setId((int)(Math.random() * 1000)); //ID ALEATORIO
        } else {
            this.pelicula = peliculaeditar;
        }

        cargarventana();
        
        // Si estamos editando, rellenamos los campos con los datos existentes
        if (peliculaeditar != null) cargarDatos();
    }

    private void cargarventana() {
        setLayout(new GridLayout(5, 2, 10, 10));
        setSize(300, 250);
        setLocationRelativeTo(getParent());

        add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        add(txtTitulo);

        add(new JLabel("Género:"));
        txtGenero = new JTextField();
        add(txtGenero);

        add(new JLabel("Duración (min):"));
        spDuracion = new JSpinner(new SpinnerNumberModel(90, 1, 300, 1));
        add(spDuracion);

        add(new JLabel("Calificación:"));
        String[] calif = {"TP", "PG-13", "R", "NC-17"};
        Calificacion = new JComboBox<>(calif);
        add(Calificacion);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        add(btnGuardar);
    }

    private void cargarDatos() {
        txtTitulo.setText(pelicula.getTitulo());
        txtGenero.setText(pelicula.getGenero());
        spDuracion.setValue(pelicula.getDuracionMinutos());
        Calificacion.setSelectedItem(pelicula.getCalificacionEdad());
    }

    private void guardar() {
     
        if (txtTitulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título es obligatorio");
            return;
        }

        pelicula.setTitulo(txtTitulo.getText());
        pelicula.setGenero(txtGenero.getText());
        pelicula.setDuracionMinutos((int) spDuracion.getValue());
        pelicula.setCalificacionEdad((String) Calificacion.getSelectedItem());
        
        guardado = true;
        dispose();
    }

    //por si acaso para sbaer si ha guardado
    public boolean isGuardado() {
        return guardado;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }
}