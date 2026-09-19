package com.mycompany.hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Juan Eraso
 */
public class ConexionBD {
    
    // ========== 1. CONSTANTES Y ATRIBUTOS ESTÁTICOS ==========
    
    /**
     * Logger para registrar eventos y errores de conexión.
     * Permite un seguimiento adecuado de las operaciones de base de datos.
     */
    private static final Logger LOGGER = Logger.getLogger(ConexionBD.class.getName());
    
    /**
     * URL de conexión a la base de datos MySQL.
     * Incluye parámetros de configuración para:
     * - useSSL=false: Deshabilita SSL para conexiones locales
     * - serverTimezone=UTC: Configura la zona horaria
     * - allowPublicKeyRetrieval=true: Permite recuperación de clave pública
     */
    private static final String URL = "jdbc:mysql://localhost:3306/Hospital?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    
    /**
     * Usuario de la base de datos.
     * Por defecto 'root' para entornos de desarrollo local.
     */
    private static final String USER = "root";
    
    /**
     * Contraseña de la base de datos.
     * Vacía por defecto para entornos de desarrollo local.
     * En producción, esta contraseña debe ser configurada de forma segura.
     */
    private static final String PASSWORD = "";
    
    // ========== 2. BLOQUE ESTÁTICO DE INICIALIZACIÓN ==========
    
    /**
     * Bloque estático que carga el driver JDBC de MySQL.
     * Se ejecuta cuando la clase es cargada por primera vez por el ClassLoader.
     * 
     * El driver debe estar presente en el classpath (dependencia en pom.xml).
     * Si falla la carga, se registra el error para diagnóstico.
     */
    static {
        try {
            // Cargar el driver de MySQL dinámicamente
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.info("Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            // Error crítico: el driver no está disponible
            LOGGER.log(Level.SEVERE, "Error al cargar el driver MySQL. "
                    + "Verificar que la dependencia 'mysql-connector-java' esté en el pom.xml", e);
        }
    }
    
    // ========== 3. MÉTODOS PÚBLICOS ==========
    
    /**
     * Establece una conexión con la base de datos MySQL.
     * 
     * Este método utiliza DriverManager para obtener una conexión
     * con los parámetros configurados en las constantes de la clase.
     * 
     * Ejemplo de uso:
     * <pre>
     * try (Connection conexion = ConexionBD.conectar()) {
     *     // Realizar operaciones con la base de datos
     * } catch (SQLException e) {
     *     // Manejar error de conexión
     * }
     * </pre>
     * 
     * @return Objeto Connection activo para interactuar con la base de datos
     * @throws SQLException Si ocurre un error al establecer la conexión
     *                      (ej. credenciales incorrectas, servidor caído)
     */
    public static Connection conectar() throws SQLException {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            LOGGER.info("Conexión establecida exitosamente a la base de datos");
            return conexion;
        } catch (SQLException e) {
            // Registrar el error con detalles para diagnóstico
            LOGGER.log(Level.SEVERE, "Error al conectar con la base de datos. "
                    + "URL: " + URL + ", Usuario: " + USER, e);
            throw e; // Re-lanzar la excepción para que el llamador la maneje
        }
    }
    
    /**
     * Verifica si la conexión a la base de datos es válida.
     * Útil para comprobar el estado de la conexión antes de usarla.
     * 
     * @param connection Conexión a verificar
     * @param timeoutSeconds Tiempo máximo de espera en segundos
     * @return true si la conexión es válida, false en caso contrario
     */
    public static boolean validarConexion(Connection connection, int timeoutSeconds) {
        if (connection == null) {
            return false;
        }
        try {
            return connection.isValid(timeoutSeconds);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error al validar la conexión", e);
            return false;
        }
    }
    
    /**
     * Cierra una conexión de forma segura.
     * Maneja posibles excepciones y evita fugas de recursos.
     * 
     * @param connection Conexión a cerrar (puede ser null)
     */
    public static void cerrarConexion(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    LOGGER.fine("Conexión cerrada correctamente");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar la conexión", e);
            }
        }
    }
    
    /**
     * Obtiene la URL de conexión configurada.
     * Útil para propósitos de diagnóstico y logging.
     * 
     * @return URL de conexión como String
     */
    public static String getUrl() {
        return URL;
    }
    
    /**
     * Obtiene el nombre de usuario configurado.
     * 
     * @return Usuario de la base de datos como String
     */
    public static String getUser() {
        return USER;
    }
    
    // ========== 4. MÉTODO MAIN PARA PRUEBAS ==========
    
    /**
     * Método principal para probar la conexión de forma independiente.
     * Útil para diagnóstico rápido sin necesidad de desplegar la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        System.out.println("=== PROBANDO CONEXIÓN A BASE DE DATOS ===");
        System.out.println("URL: " + URL);
        System.out.println("Usuario: " + USER);
        System.out.println("Contraseña: " + (PASSWORD.isEmpty() ? "[vacía]" : "******"));
        
        try (Connection conexion = conectar()) {
            if (conexion != null) {
                System.out.println("✅¡Conexión exitosa!");
                System.out.println("Base de datos: " + conexion.getCatalog());
                System.out.println("Versión de MySQL: " + conexion.getMetaData().getDatabaseProductVersion());
                System.out.println("Driver: " + conexion.getMetaData().getDriverName());
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión:");
            System.err.println("Código: " + e.getErrorCode());
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("Estado SQL: " + e.getSQLState());
        }
    }
}