package dao;

import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones de la tabla usuarios
 * AulaVirtual — GA7-220501096-AA2-EV02
 */
public class UsuarioDAO {

    /**
     * Verifica credenciales en la BD.
     */
    public Usuario login(String email, String password) {
        Usuario usuario = null;
        String sql = "SELECT u.id_usuario, u.id_rol, u.nombres, u.apellidos, "
                   + "u.email, u.activo, r.nombre AS rol_nombre "
                   + "FROM usuarios u "
                   + "JOIN roles r ON u.id_rol = r.id_rol "
                   + "WHERE u.email = ? AND u.password_hash = SHA2(?, 256) AND u.activo = 1";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario (rs.getInt   ("id_usuario"));
                usuario.setIdRol     (rs.getInt   ("id_rol"));
                usuario.setNombres   (rs.getString("nombres"));
                usuario.setApellidos (rs.getString("apellidos"));
                usuario.setEmail     (rs.getString("email"));
                usuario.setActivo    (rs.getInt   ("activo"));
                usuario.setRolNombre (rs.getString("rol_nombre"));
            }
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
        } finally {
            Conexion.cerrar(con);
        }
        return usuario;
    }

    /**
     * Verifica si un email ya existe en la BD.
     */
    public boolean existeEmail(String email) {
        String sql = "SELECT id_usuario FROM usuarios WHERE email = ?";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error verificando email: " + e.getMessage());
            return false;
        } finally {
            Conexion.cerrar(con);
        }
    }

    /**
     * Verifica si un número de documento ya existe en la BD.
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
     * Registra un nuevo usuario en la BD.
     */
    public boolean registrar(Usuario u) {
        String sql = "INSERT INTO usuarios "
                   + "(id_rol, nombres, apellidos, tipo_documento, numero_documento, "
                   + "fecha_nacimiento, telefono, ciudad, email, password_hash, "
                   + "activo, acepta_terminos) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, SHA2(?, 256), 1, ?)";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt   (1,  u.getIdRol());
            ps.setString(2,  u.getNombres());
            ps.setString(3,  u.getApellidos());
            ps.setString(4,  u.getTipoDocumento());
            ps.setString(5,  u.getNumeroDocumento());
            ps.setDate  (6,  new java.sql.Date(u.getFechaNacimiento().getTime()));
            ps.setString(7,  u.getTelefono());
            ps.setString(8,  u.getCiudad());
            ps.setString(9,  u.getEmail());
            ps.setString(10, u.getPasswordHash());
            ps.setInt   (11, u.getAceptaTerminos());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        } finally {
            Conexion.cerrar(con);
        }
    }

    /**
     * Lista todos los usuarios con su rol.
     */
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombres, u.apellidos, u.email, "
                   + "u.tipo_documento, u.numero_documento, u.telefono, "
                   + "u.ciudad, u.activo, r.nombre AS rol_nombre "
                   + "FROM usuarios u "
                   + "JOIN roles r ON u.id_rol = r.id_rol "
                   + "ORDER BY u.nombres";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario      (rs.getInt   ("id_usuario"));
                u.setNombres        (rs.getString ("nombres"));
                u.setApellidos      (rs.getString ("apellidos"));
                u.setEmail          (rs.getString ("email"));
                u.setTipoDocumento  (rs.getString ("tipo_documento"));
                u.setNumeroDocumento(rs.getString ("numero_documento"));
                u.setTelefono       (rs.getString ("telefono"));
                u.setCiudad         (rs.getString ("ciudad"));
                u.setActivo         (rs.getInt    ("activo"));
                u.setRolNombre      (rs.getString ("rol_nombre"));
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        } finally {
            Conexion.cerrar(con);
        }
        return lista;
    }

    /**
     * Activa o desactiva un usuario.
     */
    public boolean cambiarEstado(int idUsuario, boolean activar) {
        String sql = "UPDATE usuarios SET activo = ? WHERE id_usuario = ?";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, activar ? 1 : 0);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error cambiando estado: " + e.getMessage());
            return false;
        } finally {
            Conexion.cerrar(con);
        }
    }

    /**
     * Actualiza la contraseña de un usuario.
     */
    public boolean actualizarPassword(String email, String nuevaPassword) {
        String sql = "UPDATE usuarios SET password_hash = SHA2(?, 256) WHERE email = ?";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nuevaPassword);
            ps.setString(2, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizando password: " + e.getMessage());
            return false;
        } finally {
            Conexion.cerrar(con);
        }
    }
}