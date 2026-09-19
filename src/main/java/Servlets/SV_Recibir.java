package Servlets;

import com.mycompany.hospital.ConexionBD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Juan Eraso
 */
@WebServlet(name = "SV_Recibir", urlPatterns = {"/SV_Recibir"})
public class SV_Recibir extends HttpServlet {

    /**
     * Procesa las solicitudes HTTP POST para probar la conexión a la base de datos.
     * 
     * Este método intenta establecer una conexión con la base de datos MySQL
     * y registra el resultado en la consola del servidor. Es útil para:
     * - Verificar que la configuración de conexión sea correcta
     * - Diagnosticar problemas de conectividad
     * - Validar que los drivers JDBC estén correctamente configurados
     * 
     * @param request  Objeto HttpServletRequest que contiene la solicitud del cliente
     * @param response Objeto HttpServletResponse para enviar la respuesta al cliente
     * @throws ServletException Si ocurre un error específico del servlet
     * @throws IOException      Si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // ========== 1. PROBAR CONEXIÓN A BASE DE DATOS ==========
        // Usar try-with-resources para asegurar el cierre automático de la conexión
        try (Connection conexion = ConexionBD.conectar()) {
            
            // Verificar si la conexión se estableció correctamente
            if (conexion != null) {
                System.out.println("Conexión Exitosa a la base de datos");
                System.out.println("Base de datos: " + conexion.getCatalog());
                System.out.println("URL: " + conexion.getMetaData().getURL());
                System.out.println("Usuario: " + conexion.getMetaData().getUserName());
            } else {
                System.out.println("La conexión devolvió null. Verificar configuración.");
            }
            
        } catch (SQLException e) {
            // ========== 2. MANEJO DE ERRORES DE CONEXIÓN ==========
            // Registrar el error detallado para diagnóstico
            System.err.println("Error de conexión a la base de datos:");
            System.err.println("Código de error: " + e.getErrorCode());
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("Estado SQL: " + e.getSQLState());
            System.err.println("Causa: " + e.getCause());
        } catch (Exception e) {
            // Capturar cualquier otra excepción inesperada
            System.err.println("Error inesperado al probar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Procesa las solicitudes HTTP GET.
     * Este método redirige las solicitudes GET al método POST.
     * 
     * @param request  Objeto HttpServletRequest que contiene la solicitud
     * @param response Objeto HttpServletResponse para enviar la respuesta
     * @throws ServletException Si ocurre un error específico del servlet
     * @throws IOException      Si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigir a la página principal en caso de acceso GET
        response.sendRedirect("index.jsp");
    }

    /**
     * Obtiene información descriptiva sobre el servlet.
     * 
     * @return Cadena con información del servlet y su propósito
     */
    @Override
    public String getServletInfo() {
        return "Servlet de diagnóstico para verificar la conexión a la base de datos MySQL. "
                + "Útil para pruebas y validación de configuración.";
    }
}