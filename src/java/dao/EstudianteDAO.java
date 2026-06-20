package dao;

import modelo.Estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones sobre estudiantes
 * AulaVirtual — GA7-220501096-AA2-EV02
 */
public class EstudianteDAO {

    /**
     * Lista todos los usuarios con rol estudiante (con o sin perfil en tabla estudiantes)
     */
    public List<Estudiante> listarTodos() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT u.id_usuario AS id_estudiante_ref, "
                   + "COALESCE(e.id_estudiante, u.id_usuario) AS id_estudiante, "
                   + "u.nombres, u.apellidos, u.email, "
                   + "u.tipo_documento, u.numero_documento, "
                   + "u.telefono, u.ciudad, "
                   + "e.codigo_estudiantil, "
                   + "e.acudiente_nombre, e.acudiente_telefono "
                   + "FROM usuarios u "
                   + "JOIN roles r ON u.id_rol = r.id_rol "
                   + "LEFT JOIN estudiantes e ON e.id_usuario = u.id_usuario "
                   + "WHERE r.nombre = 'estudiante' AND u.activo = 1 "
                   + "ORDER BY u.apellidos, u.nombres";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Estudiante e = new Estudiante();
                e.setIdEstudiante     (rs.getInt   ("id_estudiante"));
                e.setIdUsuario        (rs.getInt   ("id_estudiante_ref"));
                e.setNombres          (rs.getString("nombres"));
                e.setApellidos        (rs.getString("apellidos"));
                e.setEmail            (rs.getString("email"));
                e.setTipoDocumento    (rs.getString("tipo_documento"));
                e.setNumeroDocumento  (rs.getString("numero_documento"));
                e.setTelefono         (rs.getString("telefono"));
                e.setCiudad           (rs.getString("ciudad"));
                e.setCodigoEstudiantil(rs.getString("codigo_estudiantil"));
                e.setAcudienteNombre  (rs.getString("acudiente_nombre"));
                e.setAcudienteTelefono(rs.getString("acudiente_telefono"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar estudiantes: " + ex.getMessage());
        } finally {
            Conexion.cerrar(con);
        }
        return lista;
    }

    /**
     * Verifica si ya existe un estudiante con ese documento
     */
    public boolean existeDocumento(String documento) {
        String sql = "SELECT id_usuario FROM usuarios WHERE numero_documento = ?";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, documento);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error verificando documento: " + e.getMessage());
            return false;
        } finally {
            Conexion.cerrar(con);
        }
    }

    /**
     * Registra un nuevo estudiante:
     * 1. Inserta en usuarios con rol estudiante
     * 2. Inserta en estudiantes con el id_usuario generado
     */
    public boolean registrar(Estudiante e) {
        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false);

            // 1. Insertar en usuarios
            String sqlUsuario = "INSERT INTO usuarios "
                    + "(id_rol, nombres, apellidos, tipo_documento, numero_documento, "
                    + "email, telefono, ciudad, password_hash, activo, acepta_terminos) "
                    + "VALUES (1, ?, ?, ?, ?, ?, ?, ?, SHA2(?, 256), 1, 1)";
            PreparedStatement psU = con.prepareStatement(sqlUsuario,
                    Statement.RETURN_GENERATED_KEYS);
            psU.setString(1, e.getNombres());
            psU.setString(2, e.getApellidos());
            psU.setString(3, e.getTipoDocumento());
            psU.setString(4, e.getNumeroDocumento());
            psU.setString(5, e.getEmail());
            psU.setString(6, e.getTelefono());
            psU.setString(7, e.getCiudad());
            psU.setString(8, e.getNumeroDocumento()); // contraseña inicial = documento
            psU.executeUpdate();

            ResultSet keys = psU.getGeneratedKeys();
            if (!keys.next()) { con.rollback(); return false; }
            int idUsuario = keys.getInt(1);

            // 2. Insertar en estudiantes
            String sqlEst = "INSERT INTO estudiantes "
                    + "(id_usuario, codigo_estudiantil, acudiente_nombre, acudiente_telefono) "
                    + "VALUES (?, ?, ?, ?)";
            PreparedStatement psE = con.prepareStatement(sqlEst);
            psE.setInt   (1, idUsuario);
            psE.setString(2, e.getCodigoEstudiantil() != null ? e.getCodigoEstudiantil() : "");
            psE.setString(3, e.getAcudienteNombre()   != null ? e.getAcudienteNombre()   : "");
            psE.setString(4, e.getAcudienteTelefono() != null ? e.getAcudienteTelefono() : "");
            psE.executeUpdate();

            con.commit();
            return true;

        } catch (SQLException ex) {
            System.err.println("Error al registrar estudiante: " + ex.getMessage());
            try { if (con != null) con.rollback(); } catch (SQLException ignored) {}
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (SQLException ignored) {}
            Conexion.cerrar(con);
        }
    }
}