package controlador;

import dao.EstudianteDAO;
import modelo.Estudiante;
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
 * Servlet que gestiona el módulo de Estudiantes
 * GET  → lista todos los estudiantes
 * POST → registra un nuevo estudiante
 * AulaVirtual — GA7-220501096-AA2-EV02
 */
@WebServlet(name = "EstudianteServlet", urlPatterns = {"/estudiantes"})
public class EstudianteServlet extends HttpServlet {

    private final EstudianteDAO estudianteDAO = new EstudianteDAO();

    // ── GET: listar estudiantes ─────────────────────────────
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar sesión activa
        if (!tieneSesion(request)) {
            response.sendRedirect("login");
            return;
        }

        List<Estudiante> lista = estudianteDAO.listarTodos();
        request.setAttribute("estudiantes", lista);
        request.getRequestDispatcher("/WEB-INF/vistas/estudiantes.jsp")
               .forward(request, response);
    }

    // ── POST: registrar estudiante ──────────────────────────
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Verificar sesión activa
        if (!tieneSesion(request)) {
            response.sendRedirect("login");
            return;
        }

        // Recoger parámetros del formulario
        String nombres           = request.getParameter("nombres");
        String apellidos         = request.getParameter("apellidos");
        String tipoDoc           = request.getParameter("tipo_documento");
        String numeroDoc         = request.getParameter("numero_documento");
        String email             = request.getParameter("email");
        String telefono          = request.getParameter("telefono");
        String ciudad            = request.getParameter("ciudad");
        String codigoEstudiantil = request.getParameter("codigo_estudiantil");
        String acudienteNombre   = request.getParameter("acudiente_nombre");
        String acudienteTel      = request.getParameter("acudiente_telefono");

        // Validaciones básicas
        if (nombres == null || nombres.trim().isEmpty()
                || apellidos == null || apellidos.trim().isEmpty()
                || numeroDoc == null || numeroDoc.trim().isEmpty()
                || email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Los campos obligatorios no pueden estar vacíos.");
            request.setAttribute("estudiantes", estudianteDAO.listarTodos());
            request.getRequestDispatcher("/WEB-INF/vistas/estudiantes.jsp")
                   .forward(request, response);
            return;
        }

        // Construir objeto
        Estudiante e = new Estudiante();
        e.setNombres          (nombres.trim());
        e.setApellidos        (apellidos.trim());
        e.setTipoDocumento    (tipoDoc != null ? tipoDoc : "TI");
        e.setNumeroDocumento  (numeroDoc.trim());
        e.setEmail            (email.trim());
        e.setTelefono         (telefono  != null ? telefono.trim()  : "");
        e.setCiudad           (ciudad    != null ? ciudad.trim()    : "Bogotá D.C.");
        e.setCodigoEstudiantil(codigoEstudiantil != null ? codigoEstudiantil.trim() : "");
        e.setAcudienteNombre  (acudienteNombre   != null ? acudienteNombre.trim()   : "");
        e.setAcudienteTelefono(acudienteTel      != null ? acudienteTel.trim()      : "");

        boolean ok = estudianteDAO.registrar(e);

        if (ok) {
            request.setAttribute("exito", "Estudiante registrado correctamente.");
        } else {
            request.setAttribute("error", "Error al registrar el estudiante. Verifica que el documento o correo no estén duplicados.");
        }

        // Recargar lista
        request.setAttribute("estudiantes", estudianteDAO.listarTodos());
        request.getRequestDispatcher("/WEB-INF/vistas/estudiantes.jsp")
               .forward(request, response);
    }

    // ── Verificar sesión ────────────────────────────────────
    private boolean tieneSesion(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("usuario") != null;
    }
}