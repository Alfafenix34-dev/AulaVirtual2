package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet que gestiona el registro de nuevos usuarios
 * GET  → muestra el formulario de registro (registro.jsp)
 * POST → valida y guarda el usuario en la BD
 * AulaVirtual — GA7-220501096-AA2-EV02
 */
@WebServlet(name = "RegistroServlet", urlPatterns = {"/registro"})
public class RegistroServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // ── GET: mostrar formulario ─────────────────────────────
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/vistas/registro.jsp")
               .forward(request, response);
    }

    // ── POST: procesar registro ─────────────────────────────
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Recoger parámetros del formulario
        String rolStr         = request.getParameter("rol");
        String nombres        = request.getParameter("nombres");
        String apellidos      = request.getParameter("apellidos");
        String tipoDoc        = request.getParameter("tipo_documento");
        String numeroDoc      = request.getParameter("numero_documento");
        String fechaNacStr    = request.getParameter("fecha_nacimiento");
        String telefono       = request.getParameter("telefono");
        String ciudad         = request.getParameter("ciudad");
        String email          = request.getParameter("email");
        String password       = request.getParameter("password");
        String confirmPass    = request.getParameter("confirmar_password");
        String terminosStr    = request.getParameter("acepta_terminos");

        // ── Validaciones ────────────────────────────────────
        // Campos obligatorios
        if (nombres == null || nombres.trim().isEmpty()
                || apellidos == null || apellidos.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || numeroDoc == null || numeroDoc.trim().isEmpty()) {
            request.setAttribute("error", "Todos los campos obligatorios deben completarse.");
            request.getRequestDispatcher("/WEB-INF/vistas/registro.jsp")
                   .forward(request, response);
            return;
        }

        // Contraseñas coinciden
        if (!password.equals(confirmPass)) {
            request.setAttribute("error", "Las contraseñas no coinciden.");
            request.getRequestDispatcher("/WEB-INF/vistas/registro.jsp")
                   .forward(request, response);
            return;
        }

        // Email duplicado
        if (usuarioDAO.existeEmail(email.trim())) {
            request.setAttribute("error", "El correo ya está registrado en el sistema.");
            request.getRequestDispatcher("/WEB-INF/vistas/registro.jsp")
                   .forward(request, response);
            return;
        }

        // Documento duplicado
        if (usuarioDAO.existeDocumento(numeroDoc.trim())) {
            request.setAttribute("error", "El número de documento ya está registrado.");
            request.getRequestDispatcher("/WEB-INF/vistas/registro.jsp")
                   .forward(request, response);
            return;
        }

        // ── Construir objeto Usuario ─────────────────────────
        Usuario u = new Usuario();
        u.setNombres        (nombres.trim());
        u.setApellidos      (apellidos.trim());
        u.setTipoDocumento  (tipoDoc != null ? tipoDoc : "CC");
        u.setNumeroDocumento(numeroDoc.trim());
        u.setEmail          (email.trim());
        u.setPasswordHash   (password);        // el DAO usa SHA2 de MySQL
        u.setTelefono       (telefono != null ? telefono.trim() : "");
        u.setCiudad         (ciudad   != null ? ciudad.trim()   : "Bogotá D.C.");
        u.setAceptaTerminos (terminosStr != null ? 1 : 0);

        // Convertir rol a id_rol
        int idRol = 1; // estudiante por defecto
        if ("docente".equals(rolStr))        idRol = 2;
        else if ("administrador".equals(rolStr)) idRol = 3;
        u.setIdRol(idRol);

        // Fecha de nacimiento
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            u.setFechaNacimiento(sdf.parse(fechaNacStr));
        } catch (ParseException | NullPointerException e) {
            u.setFechaNacimiento(new java.util.Date());
        }

        // ── Guardar en BD ────────────────────────────────────
        boolean ok = usuarioDAO.registrar(u);

        if (ok) {
            request.setAttribute("exito", "¡Registro exitoso! Ya puedes iniciar sesión.");
            request.getRequestDispatcher("/WEB-INF/vistas/login.jsp")
                   .forward(request, response);
        } else {
            request.setAttribute("error", "Ocurrió un error al registrar. Intenta de nuevo.");
            request.getRequestDispatcher("/WEB-INF/vistas/registro.jsp")
                   .forward(request, response);
        }
    }
}