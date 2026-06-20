package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para gestionar la conexión a la BD aulavirtual_db
 * AulaVirtual — GA7-220501096-AA2-EV02
 */
public class Conexion {

    // ── Parámetros de conexión ──────────────────────────────
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL    = "jdbc:mysql://localhost:3306/aulavirtual_db"
                                       + "?useSSL=false&serverTimezone=America/Bogota"
                                       + "&allowPublicKeyRetrieval=true";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "A2B3casta*ju";   

    /**
     * Retorna una conexión activa a la base de datos.
     * @return Connection
     * @throws SQLException si no puede conectar
     */
    public static Connection getConexion() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado: " + e.getMessage());
        }
    }

    /**
     * Cierra una conexión de forma segura.
     * @param con conexión a cerrar
     */
    public static void cerrar(Connection con) {
        if (con != null) {
            try { con.close(); } catch (SQLException ignored) {}
        }
    }
}
