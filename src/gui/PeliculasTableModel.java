package gui;



import javax.swing.table.AbstractTableModel;
import domain.Pelicula;
import db.GestorBD; // Importamos el gestor
import java.util.List;

public class PeliculasTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    
    private final String[] columnNames = {"ID", "Título", "Género", "Duración (min)", "Calificación"};
    private List<Pelicula> peliculas;
    private GestorBD gestorBD; // Referencia a la base de datos

    public PeliculasTableModel(GestorBD gestorBD) {
        this.gestorBD = gestorBD;
        // Al arrancar, cargamos la lista REAL desde la BD
        this.peliculas = gestorBD.obtenerPeliculas();
    }

    @Override
    public int getRowCount() {
        return peliculas.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pelicula p = peliculas.get(rowIndex);
        switch (columnIndex) {
            case 0: return p.getId();
            case 1: return p.getTitulo();
            case 2: return p.getGenero();
            case 3: return p.getDuracionMinutos();
            case 4: return p.getCalificacionEdad();
            default: return null;
        }
    }


    public void agregarPelicula(Pelicula p) {
        gestorBD.agregarPelicula(p);
        this.peliculas = gestorBD.obtenerPeliculas();
        fireTableDataChanged(); 
    }

    public void eliminarPelicula(int rowIndex) {
        Pelicula p = peliculas.get(rowIndex);
        gestorBD.borrarPelicula(p.getId());
        peliculas.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex); 
    }
    
    public void actualizarFila(int rowIndex) {

        Pelicula p = peliculas.get(rowIndex);
        gestorBD.actualizarPelicula(p);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public Pelicula getPeliculaAt(int rowIndex) {
        return peliculas.get(rowIndex);
    }
    public void refrescarDatos() {
        this.peliculas = gestorBD.obtenerPeliculas();
        fireTableDataChanged();
    }
}