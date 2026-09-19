package Mundo;

import com.mycompany.hospital.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan Eraso
 */
public class GestionarPacientes 
{
    
    /**
     * Método para listar los pacientes
     * @return misPacientes
     * @throws SQLException 
     */
    public List<Paciente> getPacientes(int pagina, int registrosPorPagina) throws SQLException
    {
        //Crear las variables
        ArrayList<Paciente> misPacientes = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        int offset = (pagina - 1) * registrosPorPagina;
        
        try
        {
            //Establece la conexion con MySQL
            conexion = ConexionBD.conectar();
            
            //Realiza la consulta
            consulta = conexion.prepareStatement("SELECT idPaciente, nombres, apellidos, fecha, celular, direccion FROM pacientes LIMIT ? OFFSET ?");
            consulta.setInt(1, registrosPorPagina);
            consulta.setInt(2, offset);
            
            //Ejecutar la consulta
            resultado = consulta.executeQuery();
            
            //Insertar los datos en un obj
            //E insertar este obj a una lista
            while(resultado.next())
            {
                //Crea el obj
                Paciente miPaciente = new Paciente();
                miPaciente.setIdPaciente(resultado.getInt("idPaciente"));
                miPaciente.setNombres(resultado.getString("nombres"));
                miPaciente.setApellidos(resultado.getString("apellidos"));
                miPaciente.setFecha(resultado.getString("fecha"));
                miPaciente.setCelular(resultado.getString("celular"));
                miPaciente.setDireccion(resultado.getString("direccion"));
                
                //Agrega el obj a la lista
                misPacientes.add(miPaciente);
            }
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        } 
        finally 
        {
            consulta.close();
            conexion.close();
        }
        
        return misPacientes;
    }
    
    /**
    * Método para obtener el total de pacientes registrados
    * @return total de pacientes
    * @throws SQLException 
    */
    public int getTotalPacientes() throws SQLException 
    {
        //Crear las variables
        int total = 0;
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultado = null;

        try 
        {
            //Establece la conexion con MySQL
            conexion = ConexionBD.conectar();

            //Realiza la consulta para contar todos los registros
            consulta = conexion.prepareStatement("SELECT COUNT(*) AS total FROM pacientes");

            //Ejecutar la consulta
            resultado = consulta.executeQuery();

            //Obtener el resultado
            if(resultado.next()) 
            {
                total = resultado.getInt("total");
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            //Cerrar recursos
            if(resultado != null && consulta != null && conexion != null)
            {
                resultado.close();
                consulta.close();
                conexion.close();
            }
        }

        return total;
    }

    public boolean AgregarPaciente(Paciente paciente) throws SQLException
    {
        //Crear las variables
        Connection conexion = null;
        PreparedStatement consulta = null;
        boolean agregado = false;
        
        try 
        {
            //Establece la conexion con MySQL
            conexion = ConexionBD.conectar();
            String sql = "insert into pacientes(nombres, apellidos, fecha, celular, direccion, created_at, updated_at)"
                        + "values(?, ?, ?, ?, ?, NOW(), NOW())";
            
            //Realiza la consulta
            consulta = conexion.prepareStatement(sql);
            consulta.setString(1, paciente.getNombres());
            consulta.setString(2, paciente.getApellidos());
            consulta.setString(3, paciente.getFecha());
            consulta.setString(4, paciente.getCelular());
            consulta.setString(5, paciente.getDireccion());
            
            int filasAfectadas = consulta.executeUpdate();
            
            if(filasAfectadas>0)
            {
                agregado = true;
            } 
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            if(consulta != null && conexion!=null)
            {
                consulta.close();
                conexion.close();
            }
        }
        return agregado;
    }
}