package controlador;

import dao.UsuarioDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet para recuperación de contraseña en 3 pasos:
 * Paso 1 → Verificar que el email existe en BD
 * Paso 2 → Verificar código de recuperación
 * Paso 3 → Actualizar nueva contraseña
 * AulaVirtual — GA7-220501096-AA2-EV02
 */
@WebServlet(name = "RecuperarServlet", urlPatterns = {"/recuperar"})
public class RecuperarServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // ── GET: mostrar formulario ─────────────────────────────
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/vistas/recuperar.jsp")
               .forward(request, response);
    }

    // ── POST: procesar cada paso ────────────────────────────
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String paso = request.getParameter("paso");

        switch (paso != null ? paso : "1") {
            case "1": procesarPaso1(request, response); break;
            case "2": procesarPaso2(request, response); break;
            case "3": procesarPaso3(request, response); break;
            default:
                request.getRequestDispatcher("/WEB-INF/vistas/recuperar.jsp")
                       .forward(request, response);
        }
    }

    // ── PASO 1: Verificar que el email existe ───────────────
    private void procesarPaso1(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Por favor ingresa tu correo.");
            forward(request, response, "1");
            return;
        }

        if (!usuarioDAO.existeEmail(email.trim())) {
            request.setAttribute("error", "No existe una cuenta con ese correo.");
            forward(request, response, "1");
            return;
        }

        // Generar código de 6 dígitos y guardarlo en sesión
        String codigo = String.valueOf((int)(Math.random() * 900000) + 100000);
        HttpSession session = request.getSession(true);
        session.setAttribute("recuperar_email",  email.trim());
        session.setAttribute("recuperar_codigo", codigo);

        // En producción esto se enviaría por email.
        // Para desarrollo lo mostramos en pantalla.
        request.setAttribute("exito",
            "Código de verificación generado: " + codigo +
            " (En producción se enviaría al correo)");
        forward(request, response, "2");
    }

    // ── PASO 2: Verificar código ────────────────────────────
    private void procesarPaso2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String codigoIngresado = request.getParameter("codigo");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("recuperar_codigo") == null) {
            request.setAttribute("error", "La sesión expiró. Intenta de nuevo.");
            forward(request, response, "1");
            return;
        }

        String codigoCorrecto = (String) session.getAttribute("recuperar_codigo");

        if (!codigoCorrecto.equals(codigoIngresado)) {
            request.setAttribute("error", "Código incorrecto. Intenta de nuevo.");
            forward(request, response, "2");
            return;
        }

        request.setAttribute("exito", "Código verificado. Ingresa tu nueva contraseña.");
        forward(request, response, "3");
    }

    // ── PASO 3: Actualizar contraseña ───────────────────────
    private void procesarPaso3(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nuevaPassword    = request.getParameter("nueva_password");
        String confirmarPassword = request.getParameter("confirmar_password");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("recuperar_email") == null) {
            request.setAttribute("error", "La sesión expiró. Intenta de nuevo.");
            forward(request, response, "1");
            return;
        }

        if (nuevaPassword == null || nuevaPassword.trim().isEmpty()) {
            request.setAttribute("error", "La contraseña no puede estar vacía.");
            forward(request, response, "3");
            return;
        }

        if (!nuevaPassword.equals(confirmarPassword)) {
            request.setAttribute("error", "Las contraseñas no coinciden.");
            forward(request, response, "3");
            return;
        }

        if (nuevaPassword.length() < 6) {
            request.setAttribute("error", "La contraseña debe tener mínimo 6 caracteres.");
            forward(request, response, "3");
            return;
        }

        String email = (String) session.getAttribute("recuperar_email");
        boolean ok = usuarioDAO.actualizarPassword(email, nuevaPassword);

        if (ok) {
            // Limpiar sesión de recuperación
            session.removeAttribute("recuperar_email");
            session.removeAttribute("recuperar_codigo");
            request.setAttribute("exito",
                "¡Contraseña actualizada exitosamente! Ya puedes iniciar sesión.");
            request.getRequestDispatcher("/WEB-INF/vistas/login.jsp")
                   .forward(request, response);
        } else {
            request.setAttribute("error", "Error al actualizar la contraseña. Intenta de nuevo.");
            forward(request, response, "3");
        }
    }

    // ── Auxiliar: forward con paso ──────────────────────────
    private void forward(HttpServletRequest request, HttpServletResponse response, String paso)
            throws ServletException, IOException {
        request.setAttribute("pasoActual", paso);
        request.getRequestDispatcher("/WEB-INF/vistas/recuperar.jsp")
               .forward(request, response);
    }
}