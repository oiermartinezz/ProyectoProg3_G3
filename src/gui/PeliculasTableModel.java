package gui;

import javax.swing.table.AbstractTableModel;
import domain.Pelicula; 
import java.util.ArrayList;
import java.util.List;

public class PeliculasTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
    private final String[] columnNames = {"ID", "Título", "Género", "Duración (min)", "Calificación"};
    private List<Pelicula> peliculas;

    public PeliculasTableModel() {
        this.peliculas = new ArrayList<>();
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

    //Metodos para editar peliculas
    public void agregarPelicula(Pelicula p) {
        peliculas.add(p);
        fireTableRowsInserted(peliculas.size() - 1, peliculas.size() - 1); 
    }

    public void eliminarPelicula(int rowIndex) {
        peliculas.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex); 
    }

    public Pelicula getPeliculaAt(int rowIndex) {
        return peliculas.get(rowIndex);
    }
    
   
    public void actualizarFila(int rowIndex) {
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
}