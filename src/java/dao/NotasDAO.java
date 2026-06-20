package dao;

import modelo.Nota;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones sobre calificaciones
 * Estructura real: calificaciones(id_calificacion, id_estudiante, id_asignacion,
 *                  tipo_evaluacion, descripcion, nota, porcentaje, fecha_registro)
 * AulaVirtual — GA7-220501096-AA2-EV02
 */
public class NotasDAO {

    /**
     * Lista todas las calificaciones con nombre del estudiante y materia
     */
    public List<Nota> listarTodas() {
        List<Nota> lista = new ArrayList<>();
        String sql = "SELECT c.id_calificacion, c.id_estudiante, "
                   + "c.tipo_evaluacion AS tipo, c.descripcion, "
                   + "c.nota AS valor, c.porcentaje, c.fecha_registro, "
                   + "CONCAT(u.nombres,' ',u.apellidos) AS estudiante_nombre, "
                   + "ma.nombre AS curso_nombre, "
                   + "0 AS periodo "
                   + "FROM calificaciones c "
                   + "JOIN estudiantes e  ON c.id_estudiante = e.id_estudiante "
                   + "JOIN usuarios u     ON e.id_usuario    = u.id_usuario "
                   + "JOIN asignaciones a ON c.id_asignacion = a.id_asignacion "
                   + "JOIN materias ma    ON a.id_materia    = ma.id_materia "
                   + "ORDER BY c.fecha_registro DESC";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Nota n = new Nota();
                n.setIdNota          (rs.getInt      ("id_calificacion"));
                n.setIdEstudiante    (rs.getInt      ("id_estudiante"));
                n.setPeriodo         (rs.getInt      ("periodo"));
                n.setTipo            (rs.getString   ("tipo").toLowerCase());
                n.setDescripcion     (rs.getString   ("descripcion"));
                n.setValor           (rs.getDouble   ("valor"));
                n.setPorcentaje      (rs.getDouble   ("porcentaje"));
                n.setFechaRegistro   (rs.getTimestamp("fecha_registro"));
                n.setEstudianteNombre(rs.getString   ("estudiante_nombre"));
                n.setCursoNombre     (rs.getString   ("curso_nombre"));
                lista.add(n);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar notas: " + e.getMessage());
        } finally {
            Conexion.cerrar(con);
        }
        return lista;
    }

    /**
     * Lista calificaciones de un estudiante específico
     */
    public List<Nota> listarPorEstudiante(int idEstudiante) {
        List<Nota> lista = new ArrayList<>();
        String sql = "SELECT c.id_calificacion, c.id_estudiante, "
                   + "c.tipo_evaluacion AS tipo, c.descripcion, "
                   + "c.nota AS valor, c.porcentaje, c.fecha_registro, "
                   + "CONCAT(u.nombres,' ',u.apellidos) AS estudiante_nombre, "
                   + "ma.nombre AS curso_nombre, "
                   + "0 AS periodo "
                   + "FROM calificaciones c "
                   + "JOIN estudiantes e  ON c.id_estudiante = e.id_estudiante "
                   + "JOIN usuarios u     ON e.id_usuario    = u.id_usuario "
                   + "JOIN asignaciones a ON c.id_asignacion = a.id_asignacion "
                   + "JOIN materias ma    ON a.id_materia    = ma.id_materia "
                   + "WHERE c.id_estudiante = ? "
                   + "ORDER BY c.fecha_registro DESC";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idEstudiante);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Nota n = new Nota();
                n.setIdNota          (rs.getInt      ("id_calificacion"));
                n.setIdEstudiante    (rs.getInt      ("id_estudiante"));
                n.setPeriodo         (rs.getInt      ("periodo"));
                n.setTipo            (rs.getString   ("tipo").toLowerCase());
                n.setDescripcion     (rs.getString   ("descripcion"));
                n.setValor           (rs.getDouble   ("valor"));
                n.setPorcentaje      (rs.getDouble   ("porcentaje"));
                n.setFechaRegistro   (rs.getTimestamp("fecha_registro"));
                n.setEstudianteNombre(rs.getString   ("estudiante_nombre"));
                n.setCursoNombre     (rs.getString   ("curso_nombre"));
                lista.add(n);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar notas por estudiante: " + e.getMessage());
        } finally {
            Conexion.cerrar(con);
        }
        return lista;
    }

    /**
     * Registra una nueva calificación.
     * Busca o crea una asignacion para la materia seleccionada,
     * luego inserta en calificaciones.
     */
    public boolean registrar(Nota nota) {
        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false);

            // 1. Buscar asignacion existente para esa materia
            String sqlAsg = "SELECT id_asignacion FROM asignaciones "
                          + "WHERE id_materia = ? LIMIT 1";
            PreparedStatement psA = con.prepareStatement(sqlAsg);
            psA.setInt(1, nota.getIdCurso());
            ResultSet rs = psA.executeQuery();

            int idAsignacion;
            if (rs.next()) {
                idAsignacion = rs.getInt("id_asignacion");
            } else {
                // Crear asignación básica si no existe
                String sqlNuevaAsg = "INSERT INTO asignaciones "
                        + "(id_docente, id_materia, id_grupo, id_periodo) "
                        + "VALUES (1, ?, 1, 3)";
                PreparedStatement psNA = con.prepareStatement(sqlNuevaAsg,
                        Statement.RETURN_GENERATED_KEYS);
                psNA.setInt(1, nota.getIdCurso());
                psNA.executeUpdate();
                ResultSet keys = psNA.getGeneratedKeys();
                if (!keys.next()) { con.rollback(); return false; }
                idAsignacion = keys.getInt(1);
            }

            // 2. Buscar id_estudiante real en tabla estudiantes
            String sqlEst = "SELECT id_estudiante FROM estudiantes "
                          + "WHERE id_usuario = ? OR id_estudiante = ? LIMIT 1";
            PreparedStatement psE = con.prepareStatement(sqlEst);
            psE.setInt(1, nota.getIdEstudiante());
            psE.setInt(2, nota.getIdEstudiante());
            ResultSet rsE = psE.executeQuery();

            int idEstudiante;
            if (rsE.next()) {
                idEstudiante = rsE.getInt("id_estudiante");
            } else {
                con.rollback();
                System.err.println("No se encontró estudiante con id: " + nota.getIdEstudiante());
                return false;
            }

            // 3. Insertar calificación
            String sql = "INSERT INTO calificaciones "
                       + "(id_estudiante, id_asignacion, tipo_evaluacion, "
                       + "descripcion, nota, porcentaje, id_docente_reg) "
                       + "VALUES (?, ?, ?, ?, ?, ?, 1)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt   (1, idEstudiante);
            ps.setInt   (2, idAsignacion);
            ps.setString(3, nota.getTipo().toUpperCase());
            ps.setString(4, nota.getDescripcion());
            ps.setDouble(5, nota.getValor());
            ps.setDouble(6, nota.getPorcentaje());
            boolean ok = ps.executeUpdate() > 0;

            con.commit();
            return ok;

        } catch (SQLException e) {
            System.err.println("Error al registrar calificacion: " + e.getMessage());
            try { if (con != null) con.rollback(); } catch (SQLException ignored) {}
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (SQLException ignored) {}
            Conexion.cerrar(con);
        }
    }
}