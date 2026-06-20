package controlador;

import dao.UsuarioDAO;
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
 * Servlet para gestión de usuarios (solo administrador)
 * GET  → lista todos los usuarios
 * POST → activa o desactiva un usuario
 * AulaVirtual — GA7-220501096-AA2-EV02
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuarios"})
public class UsuarioServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // ── GET: listar usuarios ────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!esAdmin(request)) {
            response.sendRedirect("login");
            return;
        }

        List<Usuario> lista = usuarioDAO.listarTodos();
        request.setAttribute("usuarios", lista);
        request.getRequestDispatcher("/WEB-INF/vistas/usuarios.jsp")
               .forward(request, response);
    }

    // ── POST: activar/desactivar usuario ────────────────────
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!esAdmin(request)) {
            response.sendRedirect("login");
            return;
        }

        String accion     = request.getParameter("accion");
        String idUsuarStr = request.getParameter("id_usuario");

        if (idUsuarStr != null && accion != null) {
            int idUsuario = Integer.parseInt(idUsuarStr);
            boolean activar = "activar".equals(accion);
            usuarioDAO.cambiarEstado(idUsuario, activar);
        }

        response.sendRedirect("usuarios");
    }

    // ── Verificar que sea admin ─────────────────────────────
    private boolean esAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;
        Usuario u = (Usuario) session.getAttribute("usuario");
        return u != null && "administrador".equals(u.getRolNombre());
    }
}