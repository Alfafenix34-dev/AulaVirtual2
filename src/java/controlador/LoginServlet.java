package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet que gestiona el inicio de sesión
 * GET  → muestra el formulario de login (login.jsp)
 * POST → valida credenciales y redirige según el rol
 * AulaVirtual — GA7-220501096-AA2-EV02
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // ── GET: mostrar formulario ─────────────────────────────
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Si ya hay sesión activa, redirigir al dashboard
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            redirigirPorRol((Usuario) session.getAttribute("usuario"), response);
            return;
        }
        // Mostrar página de login
        request.getRequestDispatcher("/WEB-INF/vistas/login.jsp")
               .forward(request, response);
    }

    // ── POST: procesar credenciales ─────────────────────────
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email    = request.getParameter("email");
        String password = request.getParameter("password");

        // Validación básica de campos vacíos
        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Por favor ingresa tu correo y contraseña.");
            request.getRequestDispatcher("/WEB-INF/vistas/login.jsp")
                   .forward(request, response);
            return;
        }

        // Verificar credenciales en la BD
        Usuario usuario = usuarioDAO.login(email.trim(), password);

        if (usuario == null) {
            // Credenciales incorrectas
            request.setAttribute("error", "Correo o contraseña incorrectos.");
            request.getRequestDispatcher("/WEB-INF/vistas/login.jsp")
                   .forward(request, response);
            return;
        }

        // Credenciales correctas → crear sesión
        HttpSession session = request.getSession(true);
        session.setAttribute("usuario", usuario);
        session.setAttribute("rol",     usuario.getRolNombre());
        session.setMaxInactiveInterval(30 * 60); // 30 minutos

        // Redirigir según el rol
        redirigirPorRol(usuario, response);
    }

    // ── Método auxiliar: redirigir según rol ────────────────
    private void redirigirPorRol(Usuario usuario, HttpServletResponse response)
            throws IOException {
        switch (usuario.getRolNombre()) {
            case "administrador":
                response.sendRedirect("dashboard-admin");
                break;
            case "docente":
                response.sendRedirect("dashboard-docente");
                break;
            case "estudiante":
            default:
                response.sendRedirect("dashboard-estudiante");
                break;
        }
    }
}
