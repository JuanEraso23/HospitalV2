package Mundo;

/**
 * 
 * @author Juan Eraso
 */
public class Paciente {
    
    // Atributos de la clase
    private int idPaciente;      // Identificador único del paciente
    private String nombres;      // Nombres del paciente
    private String apellidos;    // Apellidos del paciente
    private String fecha;        // Fecha de nacimiento del paciente
    private String celular;      // Número de teléfono celular
    private String direccion;    // Dirección de residencia

    /**
     * Constructor por defecto de la clase Paciente.
     * Crea una instancia vacía de paciente.
     */
    public Paciente() {
    }

    /**
     * Constructor parametrizado de la clase Paciente.
     * Crea una instancia de paciente con todos sus datos.
     * 
     * @param idPaciente Identificador único del paciente
     * @param nombres Nombres completos del paciente
     * @param apellidos Apellidos completos del paciente
     * @param fecha Fecha de nacimiento del paciente en formato String
     * @param celular Número de teléfono celular del paciente
     * @param direccion Dirección de residencia del paciente
     */
    public Paciente(int idPaciente, String nombres, String apellidos, String fecha, String celular, String direccion) {
        this.idPaciente = idPaciente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha = fecha;
        this.celular = celular;
        this.direccion = direccion;
    }

    /**
     * Obtiene el ID del paciente.
     * 
     * @return El ID del paciente como entero
     */
    public int getIdPaciente() {
        return idPaciente;
    }

    /**
     * Establece el ID del paciente.
     * 
     * @param idPaciente Nuevo ID para el paciente
     */
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    /**
     * Obtiene los nombres del paciente.
     * 
     * @return Los nombres del paciente como String
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Establece los nombres del paciente.
     * 
     * @param nombres Nuevos nombres para el paciente
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene los apellidos del paciente.
     * 
     * @return Los apellidos del paciente como String
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del paciente.
     * 
     * @param apellidos Nuevos apellidos para el paciente
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene la fecha de nacimiento del paciente.
     * 
     * @return La fecha de nacimiento como String
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de nacimiento del paciente.
     * 
     * @param fecha Nueva fecha de nacimiento para el paciente
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el número de celular del paciente.
     * 
     * @return El número de celular como String
     */
    public String getCelular() {
        return celular;
    }

    /**
     * Establece el número de celular del paciente.
     * 
     * @param celular Nuevo número de celular para el paciente
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * Obtiene la dirección del paciente.
     * 
     * @return La dirección del paciente como String
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del paciente.
     * 
     * @param direccion Nueva dirección para el paciente
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Devuelve una representación en String del objeto Paciente.
     * Útil para depuración y registro de logs.
     * 
     * @return Representación en String del paciente
     */
    @Override
    public String toString() {
        return "Paciente{" +
                "idPaciente=" + idPaciente +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fecha='" + fecha + '\'' +
                ", celular='" + celular + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}