package Servlets;

import Mundo.GestionarPacientes;
import Mundo.Paciente;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Juan Eraso
 */
@WebServlet(name = "SV_Agregar", urlPatterns = {"/SV_Agregar"})
public class SV_Agregar extends HttpServlet {
    
    /**
     * Logger para registrar eventos y errores del servlet.
     * Permite un seguimiento adecuado de las operaciones realizadas.
     */
    private static final Logger LOGGER = Logger.getLogger(SV_Agregar.class.getName());

    /**
     * Procesa las solicitudes HTTP POST para agregar un nuevo paciente.
     * 
     * El método realiza las siguientes operaciones:
     * 1. Recibe los datos del paciente desde el formulario
     * 2. Valida que los campos obligatorios estén completos
     * 3. Crea un objeto Paciente con los datos recibidos
     * 4. Invoca el método de negocio para guardar en la base de datos
     * 5. Redirige al usuario según el resultado de la operación
     * 
     * @param request  Objeto HttpServletRequest que contiene la solicitud del cliente
     * @param response Objeto HttpServletResponse para enviar la respuesta al cliente
     * @throws ServletException Si ocurre un error específico del servlet
     * @throws IOException      Si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // ========== 1. OBTENER PARÁMETROS DEL FORMULARIO ==========
            // Recuperar los datos enviados desde el formulario JSP
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String fecha = request.getParameter("fecha");
            String celular = request.getParameter("celular");
            String direccion = request.getParameter("direccion");
            
            // ========== 2. VALIDAR CAMPOS OBLIGATORIOS ==========
            // Verificar que los campos requeridos no estén vacíos
            if (nombres == null || nombres.trim().isEmpty() ||
                apellidos == null || apellidos.trim().isEmpty()) {
                
                // Redirigir al formulario con mensaje de error
                response.sendRedirect("agregar_paciente.jsp?error=campos_requeridos");
                return; // Detener la ejecución
            }
            
            // ========== 3. CREAR OBJETO PACIENTE ==========
            // Instanciar y llenar el objeto con los datos recibidos
            Paciente paciente = new Paciente();
            paciente.setNombres(nombres.trim());      // Eliminar espacios en blanco
            paciente.setApellidos(apellidos.trim());  // Eliminar espacios en blanco
            paciente.setFecha(fecha);
            paciente.setCelular(celular);
            paciente.setDireccion(direccion);
            
            // ========== 4. GUARDAR EN BASE DE DATOS ==========
            // Invocar la capa de negocio para persistir el paciente
            GestionarPacientes gestionar = new GestionarPacientes();
            boolean creado = gestionar.AgregarPaciente(paciente);
            
            // Registrar el resultado de la operación
            LOGGER.info("Resultado del registro: " + creado);
            
            // ========== 5. REDIRIGIR SEGÚN RESULTADO ==========
            if (creado) {
                // Éxito: redirigir al listado principal con indicador de éxito
                response.sendRedirect("index.jsp?success=true");
            } else {
                // Falla: redirigir al formulario con mensaje de error
                response.sendRedirect("agregar_paciente.jsp?error=registro_fallido");
            }
            
        } catch (Exception e) {
            // ========== 6. MANEJO DE EXCEPCIONES ==========
            // Capturar cualquier excepción no controlada y registrarla
            LOGGER.log(Level.SEVERE, "Error en el servlet al agregar paciente", e);
            response.sendRedirect("agregar_paciente.jsp?error=exception");
        }
    }

    /**
     * Procesa las solicitudes HTTP GET.
     * Este método redirige las solicitudes GET al método POST para evitar
     * accesos directos sin datos.
     * 
     * @param request  Objeto HttpServletRequest que contiene la solicitud
     * @param response Objeto HttpServletResponse para enviar la respuesta
     * @throws ServletException Si ocurre un error específico del servlet
     * @throws IOException      Si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigir a la página de agregar paciente en caso de acceso GET
        response.sendRedirect("agregar_paciente.jsp");
    }
    
    /**
     * Obtiene información sobre el servlet.
     * Método sobrescrito para proporcionar una breve descripción del servlet.
     * 
     * @return Cadena con información del servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet encargado de gestionar el registro de nuevos pacientes en el sistema hospitalario.";
    }
}