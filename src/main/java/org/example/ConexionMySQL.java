package org.example;

import java.sql.*;

public class ConexionMySQL {
    // Parametros de conexion
    private static final String url = "jdbc:mysql://localhost:3306/prueba";
    private static final String USER ="root";
    private static final String PASSWORD="root";

    private Connection connection;

    public Connection conectar(){
        try{
            // Verificar si ya existe una conexion activa
            if(connection == null || connection.isClosed()){
                connection = DriverManager.getConnection(url,USER,PASSWORD);
                System.out.println("Conexion exitosa a la base de datos");
            }
        }catch (SQLException e){
            System.err.println("Error al conectar a la base de datos: "+e.getMessage());
        }
        return connection;
    }

    // Metodo para cerrar la conexion
    public void desconectar(){
        try {
            if (connection != null && !connection.isClosed()){
                connection.isClosed();
                System.out.println("Conexion cerrada exitosamente.");
            }
        }catch (SQLException e){
            System.err.println("Error al cerrar la conexion: "+ e.getMessage());
        }
    }

    // Metodo para verificar credenciales
    public boolean verificarCredenciales(String usuario, String contra){
        boolean valido = false;

        try (Connection conn = conectar()){
            if(conn != null){
                String query = "SELECT 1 FROM usuarios WHERE username = ? AND password = ?";
                try (PreparedStatement statement = conn.prepareStatement(query)){
                    statement.setString(1,usuario);
                    statement.setString(2,contra);
                    try (ResultSet resultSet = statement.executeQuery()){
                        valido = resultSet.next();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar credenciales: "+e.getMessage());
        } finally {
            desconectar();
        }
        return valido;
    }

    public  boolean insertarEstudiante(String cedula, String nombre, double materia1,
                                       double materia2,double materia3 ,double materia4 ,
                                       double materia5){
        conectar();
        boolean exito = false;

        String query = "INSERT INTO estudiantes (cedula, nombre,materia1,materia2,materia3,materia4,materia5)" +
                " VALUES (?,?,?,?,?,?,?)";
        try(PreparedStatement statement= connection.prepareStatement(query)){
            statement.setString(1,cedula);
            statement.setString(2,nombre);
            statement.setDouble(3,materia1);
            statement.setDouble(4,materia2);
            statement.setDouble(5,materia3);
            statement.setDouble(6,materia4);
            statement.setDouble(7,materia5);

            int filasInsertadas = statement.executeUpdate();
            if(filasInsertadas > 0){
                exito = true;
                System.out.println("Estudiante insertado exitosamente");
            }

        }catch (SQLException e){
            System.err.println("Error al insertar estudiante: "+e.getMessage());
        } finally {
            desconectar();
        }
        return  exito;
    }
}
