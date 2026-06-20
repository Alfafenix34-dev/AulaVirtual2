package controlador;

import dao.NotasDAO;
import dao.EstudianteDAO;
import modelo.Nota;
import modelo.Usuario;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet que gestiona el módulo de Notas (proceso principal)
 * GET  → lista las notas registradas
 * POST → registra una nueva nota (solo docentes y admins)
 * AulaVirtual — GA7-220501096-AA2-EV02
 */
@WebServlet(name = "NotasServlet", urlPatterns = {"/notas"})
public class NotasServlet extends HttpServlet {

    private final NotasDAO      notasDAO      = new NotasDAO();
    private final EstudianteDAO estudianteDAO = new EstudianteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!tieneSesion(request)) { response.sendRedirect("login"); return; }

        Usuario usuario = getUsuario(request);
        String rol = usuario.getRolNombre();

        List<Nota> lista;
        String idEstStr = request.getParameter("id_estudiante");

        // Estudiante solo ve sus propias notas
        if ("estudiante".equals(rol)) {
            lista = notasDAO.listarPorEstudiante(usuario.getIdUsuario());
        } else if (idEstStr != null && !idEstStr.isEmpty()) {
            lista = notasDAO.listarPorEstudiante(Integer.parseInt(idEstStr));
            request.setAttribute("idEstFiltro", idEstStr);
        } else {
            lista = notasDAO.listarTodas();
        }

        request.setAttribute("notas",       lista);
        request.setAttribute("estudiantes", estudianteDAO.listarTodos());
        request.setAttribute("rol",         rol);
        request.getRequestDispatcher("/WEB-INF/vistas/notas.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        if (!tieneSesion(request)) { response.sendRedirect("login"); return; }

        // Bloquear estudiantes
        Usuario usuario = getUsuario(request);
        if ("estudiante".equals(usuario.getRolNombre())) {
            response.sendRedirect("dashboard-estudiante");
            return;
        }

        String idEstStr      = request.getParameter("id_estudiante");
        String idCursoStr    = request.getParameter("id_curso");
        String periodoStr    = request.getParameter("periodo");
        String tipo          = request.getParameter("tipo");
        String descripcion   = request.getParameter("descripcion");
        String valorStr      = request.getParameter("valor");
        String porcentajeStr = request.getParameter("porcentaje");

        if (idEstStr == null || idCursoStr == null || valorStr == null
                || periodoStr == null || tipo == null) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
            cargarVista(request, response); return;
        }

        double valor, porcentaje;
        try {
            valor      = Double.parseDouble(valorStr);
            porcentaje = Double.parseDouble(porcentajeStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "La nota y el porcentaje deben ser números válidos.");
            cargarVista(request, response); return;
        }

        if (valor < 0.0 || valor > 5.0) {
            request.setAttribute("error", "La nota debe estar entre 0.0 y 5.0.");
            cargarVista(request, response); return;
        }

        Nota nota = new Nota();
        nota.setIdEstudiante(Integer.parseInt(idEstStr));
        nota.setIdCurso     (Integer.parseInt(idCursoStr));
        nota.setPeriodo     (Integer.parseInt(periodoStr));
        nota.setTipo        (tipo);
        nota.setDescripcion (descripcion != null ? descripcion.trim() : "");
        nota.setValor       (valor);
        nota.setPorcentaje  (porcentaje);

        boolean ok = notasDAO.registrar(nota);
        request.setAttribute(ok ? "exito" : "error",
                ok ? "Nota registrada correctamente."
                   : "Error al registrar la nota. Intenta de nuevo.");
        cargarVista(request, response);
    }

    private void cargarVista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("rol",         getUsuario(request).getRolNombre());
        request.setAttribute("notas",       notasDAO.listarTodas());
        request.setAttribute("estudiantes", estudianteDAO.listarTodos());
        request.getRequestDispatcher("/WEB-INF/vistas/notas.jsp").forward(request, response);
    }

    private boolean tieneSesion(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("usuario") != null;
    }

    private Usuario getUsuario(HttpServletRequest request) {
        return (Usuario) request.getSession(false).getAttribute("usuario");
    }
}
