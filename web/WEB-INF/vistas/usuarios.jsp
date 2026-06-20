<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Usuario, java.util.List"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AulaVirtual — Gestión de Usuarios</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        body { background-color: #f5f5f5; }
        .navbar { background-color: #1a237e; }
        .page-title { color: #1a237e; font-weight: 700; margin: 30px 0 20px; }
        table thead { background-color: #1a237e; }
        table thead th { color: white; padding: 12px 8px; }
        table tbody tr:hover { background-color: #e8eaf6; }
        .badge-rol {
            padding: 3px 12px; border-radius: 20px;
            font-size: 11px; font-weight: 700;
        }
        .rol-administrador { background-color: #fce4ec; color: #880e4f; }
        .rol-docente       { background-color: #e8f5e9; color: #1b5e20; }
        .rol-estudiante    { background-color: #e3f2fd; color: #1565c0; }
        .badge-estado {
            padding: 3px 12px; border-radius: 20px;
            font-size: 11px; font-weight: 700;
        }
        .estado-activo   { background-color: #e8f5e9; color: #2e7d32; }
        .estado-inactivo { background-color: #ffebee; color: #c62828; }
        .btn-accion { border-radius: 20px; height: 28px; line-height: 28px; padding: 0 12px; font-size: 12px; }
        .card-tabla { border-radius: 12px; }
        .resumen-card {
            border-radius: 12px; text-align: center;
            padding: 20px 10px; color: white;
        }
        .resumen-card h4 { font-size: 36px; font-weight: 700; margin: 5px 0; }
        .resumen-card p  { font-size: 13px; opacity: 0.9; margin: 0; }
        .bg-pink   { background-color: #880e4f; }
        .bg-green  { background-color: #2e7d32; }
        .bg-blue   { background-color: #1565c0; }
        .bg-grey   { background-color: #546e7a; }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar">
    <div class="nav-wrapper container">
        <a href="#" class="brand-logo left">
            <i class="material-icons">school</i> AulaVirtual
        </a>
        <ul class="right">
            <li><a href="dashboard-admin"><i class="material-icons">dashboard</i></a></li>
            <li><a href="estudiantes"><i class="material-icons">people</i></a></li>
            <li><a href="notas"><i class="material-icons">grade</i></a></li>
            <li><a href="logout"><i class="material-icons">exit_to_app</i></a></li>
        </ul>
    </div>
</nav>

<div class="container">
    <h5 class="page-title">
        <i class="material-icons" style="vertical-align:middle">manage_accounts</i>
        Gestión de Usuarios
    </h5>

    <%
        List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
        int totalAdmin = 0, totalDocente = 0, totalEst = 0, totalInactivo = 0;
        if (usuarios != null) {
            for (Usuario u : usuarios) {
                if ("administrador".equals(u.getRolNombre())) totalAdmin++;
                else if ("docente".equals(u.getRolNombre()))  totalDocente++;
                else totalEst++;
                if (u.getActivo() == 0) totalInactivo++;
            }
        }
    %>

    <!-- Tarjetas resumen -->
    <div class="row">
        <div class="col s12 m3">
            <div class="card resumen-card bg-pink z-depth-2">
                <i class="material-icons">admin_panel_settings</i>
                <h4><%= totalAdmin %></h4>
                <p>Administradores</p>
            </div>
        </div>
        <div class="col s12 m3">
            <div class="card resumen-card bg-green z-depth-2">
                <i class="material-icons">person</i>
                <h4><%= totalDocente %></h4>
                <p>Docentes</p>
            </div>
        </div>
        <div class="col s12 m3">
            <div class="card resumen-card bg-blue z-depth-2">
                <i class="material-icons">face</i>
                <h4><%= totalEst %></h4>
                <p>Estudiantes</p>
            </div>
        </div>
        <div class="col s12 m3">
            <div class="card resumen-card bg-grey z-depth-2">
                <i class="material-icons">block</i>
                <h4><%= totalInactivo %></h4>
                <p>Inactivos</p>
            </div>
        </div>
    </div>

    <!-- Botón nuevo usuario -->
    <div style="margin-bottom: 15px;">
        <a href="registro" class="btn waves-effect" style="background-color:#1a237e; border-radius:8px;">
            <i class="material-icons left">person_add</i>Nuevo usuario
        </a>
    </div>

    <!-- Tabla de usuarios -->
    <div class="card card-tabla z-depth-2">
        <div class="card-content">
            <span class="card-title" style="color:#1a237e; font-weight:700;">
                <i class="material-icons left">list</i>Lista de usuarios registrados
            </span>
            <table class="highlight responsive-table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Nombre completo</th>
                        <th>Documento</th>
                        <th>Correo</th>
                        <th>Ciudad</th>
                        <th>Rol</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    if (usuarios != null && !usuarios.isEmpty()) {
                        int i = 1;
                        for (Usuario u : usuarios) {
                            String claseRol = "badge-rol rol-" + u.getRolNombre();
                            String claseEst = u.getActivo() == 1 ? "badge-estado estado-activo"
                                                                  : "badge-estado estado-inactivo";
                            String textoEst = u.getActivo() == 1 ? "Activo" : "Inactivo";
                %>
                    <tr>
                        <td><%= i++ %></td>
                        <td><strong><%= u.getNombreCompleto() %></strong></td>
                        <td><%= u.getTipoDocumento() %>: <%= u.getNumeroDocumento() %></td>
                        <td><%= u.getEmail() %></td>
                        <td><%= u.getCiudad() != null ? u.getCiudad() : "-" %></td>
                        <td><span class="<%= claseRol %>"><%= u.getRolNombre() %></span></td>
                        <td><span class="<%= claseEst %>"><%= textoEst %></span></td>
                        <td>
                            <% if (u.getActivo() == 1) { %>
                                <form action="usuarios" method="post" style="display:inline;">
                                    <input type="hidden" name="id_usuario" value="<%= u.getIdUsuario() %>">
                                    <input type="hidden" name="accion" value="desactivar">
                                    <button class="btn red lighten-1 btn-accion waves-effect" type="submit"
                                            onclick="return confirm('¿Desactivar este usuario?')">
                                        <i class="material-icons tiny">block</i> Desactivar
                                    </button>
                                </form>
                            <% } else { %>
                                <form action="usuarios" method="post" style="display:inline;">
                                    <input type="hidden" name="id_usuario" value="<%= u.getIdUsuario() %>">
                                    <input type="hidden" name="accion" value="activar">
                                    <button class="btn green btn-accion waves-effect" type="submit"
                                            onclick="return confirm('¿Activar este usuario?')">
                                        <i class="material-icons tiny">check_circle</i> Activar
                                    </button>
                                </form>
                            <% } %>
                        </td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="8" style="text-align:center; color:#9e9e9e; padding:30px;">
                            <i class="material-icons">info</i><br>No hay usuarios registrados.
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
</body>
</html>

