<%@page import="Mundo.GestionarPacientes"%>
<%@page import="Mundo.Paciente"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // ================================================================
    // 1. CONFIGURACIÓN DE PAGINACIÓN
    // ================================================================
    
    // Página actual (por defecto 1)
    int pagina = 1;
    // Número de registros por página
    int registrosPorPagina = 12;
    
    // Si el usuario envió un parámetro "pagina", usarlo
    if(request.getParameter("pagina") != null) {
        pagina = Integer.parseInt(request.getParameter("pagina"));
    }
    
    // ================================================================
    // 2. OBTENER DATOS DE LA BASE DE DATOS
    // ================================================================
    
    // Instanciar la clase de gestión de pacientes
    GestionarPacientes gp = new GestionarPacientes();
    
    // Obtener la lista de pacientes para la página actual
    List<Paciente> pacientes = gp.getPacientes(pagina, registrosPorPagina);
    
    // Obtener el total de registros para calcular la última página
    int totalRegistros = gp.getTotalPacientes();
    
    // Calcular la última página disponible
    // Math.ceil redondea hacia arriba (ej: 25/12 = 2.08 → 3 páginas)
    int ultimaPagina = (int) Math.ceil((double) totalRegistros / registrosPorPagina);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hospital - Gestión de Pacientes</title>
    
    <!-- ================================================================
         LIBRERÍAS Y ESTILOS
    ================================================================ -->
    
    <!-- Tailwind CSS: Framework CSS para diseño moderno y responsive -->
    <script src="https://cdn.tailwindcss.com"></script>
    
    <!-- Font Awesome: Librería de iconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <!-- Estilos personalizados -->
    <style>
        /* Efecto hover para los botones de acción (Ver, Editar, Eliminar) */
        .action-btn {
            transition: all 0.2s ease;
        }
        .action-btn:hover {
            transform: scale(1.1);
        }
        
        /* Efecto hover para los botones de paginación */
        .pagination-btn:not(.disabled):hover {
            background-color: #e2e8f0;
        }
        
        /* Estilos para el modal (ventana emergente) */
        .modal-overlay {
            transition: opacity 0.3s ease;
        }
        .modal-content {
            transition: all 0.3s ease;
            transform: translateY(-20px);
        }
        .modal-active .modal-overlay {
            opacity: 1;
        }
        .modal-active .modal-content {
            transform: translateY(0);
        }
    </style>
</head>
<body class="bg-blue-50 min-h-screen">
    <div class="container mx-auto px-4 py-8">
        
        <!-- ================================================================
             HEADER: Título y Botón Agregar
        ================================================================ -->
        <div class="flex justify-between items-center mb-8">
            <!-- Título principal con icono de hospital -->
            <h1 class="text-3xl font-bold text-indigo-700">
                <i class="fas fa-hospital mr-2"></i>Gestión Hospitalaria
            </h1>
            
            <!-- Botón para abrir el modal de agregar paciente -->
            <button onclick="toggleModal('agregarPacienteModal')" 
                    class="bg-green-400 hover:bg-green-500 text-white font-medium py-2 px-4 rounded-lg shadow-md transition duration-300 ease-in-out">
                <i class="fas fa-plus mr-2"></i>Agregar Paciente
            </button>
        </div>

        <!-- ================================================================
             TABLA DE PACIENTES
        ================================================================ -->
        <div class="bg-white rounded-xl shadow-md overflow-hidden">
            <!-- Contenedor con scroll horizontal para pantallas pequeñas -->
            <div class="overflow-x-auto">
                <table class="min-w-full divide-y divide-gray-200">
                    <!-- Cabecera de la tabla con fondo índigo pastel -->
                    <thead class="bg-indigo-100">
                        <tr>
                            <th class="px-6 py-3 text-left text-xs font-medium text-indigo-700 uppercase tracking-wider">ID</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-indigo-700 uppercase tracking-wider">Nombres</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-indigo-700 uppercase tracking-wider">Apellidos</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-indigo-700 uppercase tracking-wider">Fecha Nac.</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-indigo-700 uppercase tracking-wider">Celular</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-indigo-700 uppercase tracking-wider">Dirección</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-indigo-700 uppercase tracking-wider">Acciones</th>
                        </tr>
                    </thead>
                    
                    <!-- Cuerpo de la tabla con los datos -->
                    <tbody class="bg-white divide-y divide-gray-200">
                        <% 
                            // Recorrer la lista de pacientes y mostrar cada uno en una fila
                            for (Paciente p: pacientes) { 
                        %>
                        <tr class="hover:bg-blue-50 transition-colors duration-150">
                            <!-- ID del paciente -->
                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                <%=p.getIdPaciente()%>
                            </td>
                            <!-- Nombres -->
                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                <%=p.getNombres()%>
                            </td>
                            <!-- Apellidos -->
                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                <%=p.getApellidos()%>
                            </td>
                            <!-- Fecha de Nacimiento -->
                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                <%=p.getFecha()%>
                            </td>
                            <!-- Celular -->
                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                <%=p.getCelular()%>
                            </td>
                            <!-- Dirección -->
                            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                <%=p.getDireccion()%>
                            </td>
                            <!-- Acciones (Ver, Editar, Eliminar) -->
                            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                <div class="flex space-x-2">
                                    <!-- Botón Ver -->
                                    <button onclick="toggleModal('verPacienteModal<%=p.getIdPaciente()%>')" 
                                            class="action-btn text-blue-400 hover:text-blue-600" 
                                            title="Ver detalles del paciente">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                    <!-- Botón Editar -->
                                    <button onclick="toggleModal('editarPacienteModal<%=p.getIdPaciente()%>')" 
                                            class="action-btn text-yellow-400 hover:text-yellow-600" 
                                            title="Editar información del paciente">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                    <!-- Botón Eliminar (enlace directo al servlet) -->
                                    <a href="SV_Eliminar?id=<%=p.getIdPaciente()%>" 
                                       class="action-btn text-red-400 hover:text-red-600" 
                                       title="Eliminar paciente (acción irreversible)">
                                        <i class="fas fa-trash"></i>
                                    </a>
                                </div>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>

            <!-- ================================================================
                 PAGINACIÓN SIMPLIFICADA
            ================================================================ -->
            <div class="flex items-center justify-between px-6 py-4 bg-indigo-50 border-t border-gray-200">
                <!-- Indicador de página actual -->
                <div class="text-sm text-gray-700">
                    Página <%= pagina %> de <%= ultimaPagina %>
                </div>
                
                <!-- Botones de navegación -->
                <div class="flex space-x-2">
                    <!-- Botón Primera Página -->
                    <a href="index.jsp?pagina=1" 
                       class="pagination-btn px-4 py-2 rounded-md bg-white border border-gray-300 text-gray-700 <%= pagina == 1 ? "opacity-50 cursor-not-allowed" : "" %>"
                       title="Ir a la primera página">
                        <i class="fas fa-angle-double-left mr-1"></i> Primera
                    </a>
                    
                    <!-- Botón Página Anterior -->
                    <a href="index.jsp?pagina=<%=pagina > 1 ? pagina - 1 : 1%>" 
                       class="pagination-btn px-4 py-2 rounded-md bg-white border border-gray-300 text-gray-700 <%= pagina == 1 ? "opacity-50 cursor-not-allowed" : "" %>"
                       title="Ir a la página anterior">
                        <i class="fas fa-chevron-left mr-1"></i> Anterior
                    </a>
                    
                    <!-- Botón Página Siguiente -->
                    <a href="index.jsp?pagina=<%=pagina < ultimaPagina ? pagina + 1 : ultimaPagina%>" 
                       class="pagination-btn px-4 py-2 rounded-md bg-white border border-gray-300 text-gray-700 <%= pagina == ultimaPagina ? "opacity-50 cursor-not-allowed" : "" %>"
                       title="Ir a la página siguiente">
                        Siguiente <i class="fas fa-chevron-right ml-1"></i>
                    </a>
                    
                    <!-- Botón Última Página -->
                    <a href="index.jsp?pagina=<%=ultimaPagina%>" 
                       class="pagination-btn px-4 py-2 rounded-md bg-white border border-gray-300 text-gray-700 <%= pagina == ultimaPagina ? "opacity-50 cursor-not-allowed" : "" %>"
                       title="Ir a la última página">
                        Última <i class="fas fa-angle-double-right ml-1"></i>
                    </a>
                </div>
            </div>
        </div>

        <!-- ================================================================
             MODAL: AGREGAR PACIENTE
        ================================================================ -->
        <div id="agregarPacienteModal" class="fixed inset-0 z-50 flex items-center justify-center hidden">
            <!-- Fondo semitransparente -->
            <div class="modal-overlay absolute inset-0 bg-gray-900 opacity-50"></div>
            
            <!-- Contenido del modal -->
            <div class="modal-content bg-white rounded-lg shadow-xl w-full max-w-md relative z-50">
                <div class="p-6">
                    <!-- Cabecera del modal -->
                    <div class="flex justify-between items-center mb-4">
                        <h3 class="text-xl font-bold text-indigo-700">
                            <i class="fas fa-user-plus mr-2"></i>Nuevo Paciente
                        </h3>
                        <!-- Botón cerrar -->
                        <button onclick="toggleModal('agregarPacienteModal')" 
                                class="text-gray-500 hover:text-gray-700">
                            <i class="fas fa-times"></i>
                        </button>
                    </div>
                    
                    <!-- Formulario de registro -->
                    <form action="SV_Agregar" method="POST" class="space-y-4">
                        <!-- Campo: Nombres -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-1">Nombres:</label>
                            <input type="text" name="nombres" 
                                   class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500" 
                                   placeholder="Ingrese los nombres" required>
                        </div>
                        
                        <!-- Campo: Apellidos -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-1">Apellidos:</label>
                            <input type="text" name="apellidos" 
                                   class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500" 
                                   placeholder="Ingrese los apellidos" required>
                        </div>
                        
                        <!-- Campo: Fecha Nacimiento -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-1">Fecha Nacimiento:</label>
                            <input type="date" name="fecha" 
                                   class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500" required>
                        </div>
                        
                        <!-- Campo: Celular -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-1">Celular:</label>
                            <input type="text" name="celular" 
                                   class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500" 
                                   placeholder="Ingrese el número de celular" required>
                        </div>
                        
                        <!-- Campo: Dirección -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-1">Dirección:</label>
                            <input type="text" name="direccion" 
                                   class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500" 
                                   placeholder="Ingrese la dirección" required>
                        </div>
                        
                        <!-- Botones del formulario -->
                        <div class="flex justify-end space-x-3 pt-4">
                            <button type="button" onclick="toggleModal('agregarPacienteModal')" 
                                    class="px-4 py-2 border rounded-md text-gray-700 hover:bg-gray-100">
                                Cancelar
                            </button>
                            <button type="submit" class="px-4 py-2 bg-green-400 text-white rounded-md hover:bg-green-500">
                                <i class="fas fa-save mr-2"></i>Guardar
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- ================================================================
         JAVASCRIPT: FUNCIONES DE INTERACCIÓN
    ================================================================ -->
    <script>
        /**
         * Alterna la visibilidad de un modal (ventana emergente).
         * Agrega/remueve las clases 'hidden' y 'modal-active' para mostrar/ocultar.
         * 
         * @param {string} modalId - ID del elemento modal a mostrar/ocultar
         */
        function toggleModal(modalId) {
            const modal = document.getElementById(modalId);
            modal.classList.toggle('hidden');
            modal.classList.toggle('modal-active');
        }
    </script>
</body>
</html>