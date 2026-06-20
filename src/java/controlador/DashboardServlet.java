package controlador;

import modelo.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet que maneja los dashboards según el rol del usuario
 * AulaVirtual — GA7-220501096-AA2-EV02
 */
@WebServlet(name = "DashboardServlet", urlPatterns = {
    "/dashboard-admin",
    "/dashboard-docente",
    "/dashboard-estudiante"
})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String uri = request.getRequestURI();

        // Redirigir según la URL solicitada
        if (uri.contains("dashboard-admin")) {
            // Solo el admin puede entrar al panel admin
            if (!"administrador".equals(usuario.getRolNombre())) {
                response.sendRedirect("login");
                return;
            }
            request.getRequestDispatcher("/WEB-INF/vistas/dashboard-admin.jsp")
                   .forward(request, response);

        } else if (uri.contains("dashboard-docente")) {
            if (!"docente".equals(usuario.getRolNombre())) {
                response.sendRedirect("login");
                return;
            }
            request.getRequestDispatcher("/WEB-INF/vistas/dashboard-docente.jsp")
                   .forward(request, response);

        } else if (uri.contains("dashboard-estudiante")) {
            if (!"estudiante".equals(usuario.getRolNombre())) {
                response.sendRedirect("login");
                return;
            }
            request.getRequestDispatcher("/WEB-INF/vistas/dashboard-estudiante.jsp")
                   .forward(request, response);
        }
    }
}