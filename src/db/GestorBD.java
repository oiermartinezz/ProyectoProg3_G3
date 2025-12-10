package db;

import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import domain.Pelicula;


public class GestorBD {
	
	private final String PROPERTIES_FILE = "resources/config/app.properties";
	
	private static Logger logger = Logger.getLogger(GestorBD.class.getName());
	private Properties properties;
	private String connectionString;
	
	public GestorBD() {
		try (FileInputStream fis = new FileInputStream("resources/config/logger.properties")){
			//Iniciamos el Logger
			LogManager.getLogManager().readConfiguration(fis);
			
			//Lectura del fichero properties
			
			properties = new Properties();
			properties.load(new FileReader(PROPERTIES_FILE));
			
			String driverName = properties.getProperty("driver");
			connectionString = properties.getProperty("connection");
			
			//cargamos el driver SQLIte
			Class.forName(driverName);
			
			crearBBDD();
		}catch(Exception ex) {
			logger.warning(String.format("Error de configuración inicial: %s", ex.getMessage()));
			ex.printStackTrace();
		}
	}
	
	public void crearBBDD() {
		String sqlPelicula = "CREATE TABLE IF NOT EXISTS Pelicula (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " titulo TEXT NOT NULL,\n"
                + " genero TEXT NOT NULL,\n"
                + " duracion INTEGER NOT NULL,\n"
                + " calificacion TEXT NOT NULL,\n" 
                + " sinopsis TEXT\n" 
                + ");";
		
		String sqlUsuario = "CREATE TABLE IF NOT EXISTS UsuarioLog (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " nombre TEXT NOT NULL,\n"
                + " fecha_acceso DATETIME DEFAULT CURRENT_TIMESTAMP\n"
                + ");";
		
		try (Connection con = DriverManager.getConnection(connectionString);
	             Statement stmt = con.createStatement()) {
	            
	            stmt.executeUpdate(sqlPelicula);
	            stmt.executeUpdate(sqlUsuario);
	            
	            logger.info("Tablas verificadas/creadas correctamente.");
	            
	        } catch (SQLException e) {
	            logger.severe("Error creando BBDD: " + e.getMessage());
	        }				
	}
	
	//Gestion de los usuarios 
	
	public void registrarInicioSesion(String nombreUsuario) {
		String sql = "INSERT INTO UsuarioLog (nombre) VALUES (?)";
		
		try ( Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1, nombreUsuario);
			pstmt.executeUpdate();
			logger.info("Inicio de sesión registrado: " + nombreUsuario);
			
		}catch(SQLException e) {
			logger.severe("Error registrando usuario: " + e.getMessage());
		}
	}
	
	//Gestion de las peliculas
	
	public List<Pelicula>obtenerPeliculas(){
		List<Pelicula> lista = new ArrayList<>();
		String sql = "SELECT * FROM Pelicula";
		try (Connection con = DriverManager.getConnection(connectionString);
	             Statement stmt = con.createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) { // Paso 4 y 5 del PDF

	            while (rs.next()) {
	                Pelicula p = new Pelicula();
	                p.setId(rs.getInt("id"));
	                p.setTitulo(rs.getString("titulo"));
	                p.setGenero(rs.getString("genero"));
	                p.setDuracionMinutos(rs.getInt("duracion"));
	                p.setCalificacionEdad(rs.getString("calificacion"));
	                p.setSinopsis(rs.getString("sinopsis"));
	                
	                lista.add(p);
	            }
	        } catch (SQLException e) {
	            logger.severe("Error obteniendo películas: " + e.getMessage());
	        }
	        return lista;
	}
	
	//Codigo para Insertar
	
	public void agregarPelicula(Pelicula p ) {
		String sql = "INSERT INTO Pelicula (titulo, genero, duracion, calificacion, sinopsis) VALUES (?, ?, ?, ?, ?)";
		try(Connection con = DriverManager.getConnection(connectionString);
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, p.getTitulo());
            pstmt.setString(2, p.getGenero());
            pstmt.setInt(3, p.getDuracionMinutos());
            pstmt.setString(4, p.getCalificacionEdad());
            pstmt.setString(5, p.getSinopsis());
            
            pstmt.executeUpdate();
            logger.info("Película insertada: " + p.getTitulo());
		}catch(SQLException e) {
			logger.severe("Error insertando película: " + e.getMessage());
		}
				
		
	}
	
	//Codigo para Actualizar
	
	public void actualizarPelicula(Pelicula p) {
		String sql = "UPDATE Pelicula SET titulo=?, genero=?, duracion=?, calificacion=?, sinopsis=? WHERE id=?";
		try (Connection con = DriverManager.getConnection(connectionString);
	             PreparedStatement pstmt = con.prepareStatement(sql)) {
	            
	            pstmt.setString(1, p.getTitulo());
	            pstmt.setString(2, p.getGenero());
	            pstmt.setInt(3, p.getDuracionMinutos());
	            pstmt.setString(4, p.getCalificacionEdad());
	            pstmt.setString(5, p.getSinopsis());
	            pstmt.setInt(6, p.getId()); // El WHERE id = ?
	            
	            pstmt.executeUpdate();
	            logger.info("Película actualizada id: " + p.getId());
	            
	        } catch (SQLException e) {
	            logger.severe("Error actualizando película: " + e.getMessage());
	        }
	}
	
	// Codigo para borrar
	
	public void borrarPelicula(int id) {
        String sql = "DELETE FROM Pelicula WHERE id = ?";
        
        try (Connection con = DriverManager.getConnection(connectionString);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info("Película eliminada id: " + id);
            
        } catch (SQLException e) {
            logger.severe("Error borrando película: " + e.getMessage());
        }
    }
	
	public void borrarTodo() {
    	try (Connection con = DriverManager.getConnection(connectionString);
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate("DELETE FROM Pelicula");
            stmt.executeUpdate("DELETE FROM UsuarioLog");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	// Método para obtener la lista de accesos para los reportes
	public List<String> obtenerHistorialLogins() {
        List<String> historial = new ArrayList<>();
        String sql = "SELECT nombre, fecha_acceso FROM UsuarioLog ORDER BY fecha_acceso DESC";
        
        System.out.println("DEBUG: Consultando historial en BD...");
        
        try (Connection con = DriverManager.getConnection(connectionString);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String fecha = rs.getString("fecha_acceso");
                System.out.println("DEBUG: Encontrado -> " + nombre);
                historial.add(fecha + " - " + nombre);
            }
        } catch (SQLException e) {
        	System.err.println("ERROR SQL LEYENDO HISTORIAL:");
            e.printStackTrace(); // Mejor printStackTrace para ver errores en consola
        }
        System.out.println("DEBUG: Total registros recuperados: " + historial.size());
        return historial;
	}
	
	

}
