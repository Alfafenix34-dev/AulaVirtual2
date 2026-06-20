<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Estudiante, java.util.List"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AulaVirtual — Estudiantes</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        body { background-color: #f5f5f5; }
        .navbar { background-color: #1a237e; }
        .page-title { color: #1a237e; font-weight: 700; margin: 30px 0 20px; }
        .card-form { border-radius: 12px; }
        .section-title {
            color: #1a237e; font-weight: 600; font-size: 13px;
            border-bottom: 2px solid #e8eaf6; padding-bottom: 5px;
            margin: 15px 0 10px; text-transform: uppercase;
        }
        .btn-guardar { background-color: #1a237e; border-radius: 8px; }
        .btn-guardar:hover { background-color: #283593; }
        .table-container { border-radius: 12px; overflow: hidden; }
        table thead { background-color: #1a237e; }
        table thead th { color: white; padding: 12px 8px; }
        table tbody tr:hover { background-color: #e8eaf6; }
        .badge-activo {
            background-color: #e8f5e9; color: #2e7d32;
            padding: 2px 10px; border-radius: 20px; font-size: 12px;
        }
        .alert-error {
            background-color: #ffebee; border-left: 4px solid #f44336;
            padding: 10px 15px; border-radius: 4px; color: #c62828; font-size: 13px;
        }
        .alert-success {
            background-color: #e8f5e9; border-left: 4px solid #4caf50;
            padding: 10px 15px; border-radius: 4px; color: #2e7d32; font-size: 13px;
        }
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
            <li><a href="logout"><i class="material-icons">exit_to_app</i></a></li>
        </ul>
    </div>
</nav>

<div class="container">
    <h5 class="page-title">
        <i class="material-icons" style="vertical-align:middle">people</i>
        Gestión de Estudiantes
    </h5>

    <%-- Mensajes --%>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert-error"><i class="material-icons tiny">error</i> <%= request.getAttribute("error") %></div>
    <% } %>
    <% if (request.getAttribute("exito") != null) { %>
        <div class="alert-success"><i class="material-icons tiny">check_circle</i> <%= request.getAttribute("exito") %></div>
    <% } %>

    <!-- Formulario de registro -->
    <div class="card card-form z-depth-2">
        <div class="card-content">
            <span class="card-title" style="color:#1a237e; font-weight:700;">
                <i class="material-icons left">person_add</i>Registrar nuevo estudiante
            </span>

            <form action="estudiantes" method="post">

                <p class="section-title">Datos personales</p>
                <div class="row">
                    <div class="col s12 m6 input-field">
                        <input id="nombres" name="nombres" type="text" required>
                        <label for="nombres">Nombres *</label>
                    </div>
                    <div class="col s12 m6 input-field">
                        <input id="apellidos" name="apellidos" type="text" required>
                        <label for="apellidos">Apellidos *</label>
                    </div>
                    <div class="col s12 m4 input-field">
                        <select name="tipo_documento" class="browser-default">
                            <option value="TI">Tarjeta de Identidad</option>
                            <option value="CC">Cédula de Ciudadanía</option>
                            <option value="CE">Cédula Extranjera</option>
                        </select>
                    </div>
                    <div class="col s12 m4 input-field">
                        <input id="numero_documento" name="numero_documento" type="text" required>
                        <label for="numero_documento">N° Documento *</label>
                    </div>
                    <div class="col s12 m4 input-field">
                        <input id="email" name="email" type="email" required>
                        <label for="email">Correo *</label>
                    </div>
                    <div class="col s12 m6 input-field">
                        <input id="telefono" name="telefono" type="tel">
                        <label for="telefono">Teléfono</label>
                    </div>
                    <div class="col s12 m6 input-field">
                        <input id="ciudad" name="ciudad" type="text" value="Bogotá D.C.">
                        <label for="ciudad" class="active">Ciudad</label>
                    </div>
                </div>

                <p class="section-title">Datos académicos y acudiente</p>
                <div class="row">
                    <div class="col s12 m4 input-field">
                        <input id="codigo_estudiantil" name="codigo_estudiantil" type="text">
                        <label for="codigo_estudiantil">Código estudiantil</label>
                    </div>
                    <div class="col s12 m4 input-field">
                        <input id="acudiente_nombre" name="acudiente_nombre" type="text">
                        <label for="acudiente_nombre">Nombre del acudiente</label>
                    </div>
                    <div class="col s12 m4 input-field">
                        <input id="acudiente_telefono" name="acudiente_telefono" type="tel">
                        <label for="acudiente_telefono">Teléfono acudiente</label>
                    </div>
                </div>

                <div class="row">
                    <div class="col s12">
                        <button class="btn btn-guardar waves-effect waves-light" type="submit">
                            <i class="material-icons left">save</i>Guardar estudiante
                        </button>
                        <a href="estudiantes" class="btn-flat" style="margin-left:10px;">Cancelar</a>
                    </div>
                </div>

            </form>
        </div>
    </div>

    <!-- Tabla de estudiantes -->
    <div class="card table-container z-depth-2" style="margin-top:20px;">
        <div class="card-content">
            <span class="card-title" style="color:#1a237e; font-weight:700;">
                <i class="material-icons left">list</i>Lista de estudiantes
            </span>
            <table class="highlight responsive-table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Nombre completo</th>
                        <th>Documento</th>
                        <th>Correo</th>
                        <th>Teléfono</th>
                        <th>Ciudad</th>
                        <th>Código</th>
                        <th>Acudiente</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    List<Estudiante> estudiantes = (List<Estudiante>) request.getAttribute("estudiantes");
                    if (estudiantes != null && !estudiantes.isEmpty()) {
                        int i = 1;
                        for (Estudiante est : estudiantes) {
                %>
                    <tr>
                        <td><%= i++ %></td>
                        <td><strong><%= est.getNombreCompleto() %></strong></td>
                        <td><%= est.getTipoDocumento() %>: <%= est.getNumeroDocumento() %></td>
                        <td><%= est.getEmail() %></td>
                        <td><%= est.getTelefono() != null ? est.getTelefono() : "-" %></td>
                        <td><%= est.getCiudad() != null ? est.getCiudad() : "-" %></td>
                        <td><%= est.getCodigoEstudiantil() != null ? est.getCodigoEstudiantil() : "-" %></td>
                        <td><%= est.getAcudienteNombre() != null ? est.getAcudienteNombre() : "-" %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="8" style="text-align:center; color:#9e9e9e; padding:30px;">
                            <i class="material-icons">info</i><br>No hay estudiantes registrados aún.
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>

</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('select');
        M.FormSelect.init(elems);
    });
</script>
</body>
</html>

