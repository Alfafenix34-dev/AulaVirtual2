<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Nota, modelo.Estudiante, java.util.List"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AulaVirtual — Registro de Notas</title>
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
        table thead { background-color: #1a237e; }
        table thead th { color: white; padding: 12px 8px; }
        table tbody tr:hover { background-color: #e8eaf6; }
        .nota-alta  { color: #2e7d32; font-weight: 700; }
        .nota-media { color: #e65100; font-weight: 700; }
        .nota-baja  { color: #c62828; font-weight: 700; }
        .badge-tipo { padding: 2px 10px; border-radius: 20px; font-size: 11px; font-weight: 600; }
        .tipo-parcial  { background-color: #e3f2fd; color: #1565c0; }
        .tipo-examen   { background-color: #fce4ec; color: #880e4f; }
        .tipo-taller   { background-color: #e8f5e9; color: #1b5e20; }
        .tipo-proyecto { background-color: #fff8e1; color: #e65100; }
        .tipo-otro     { background-color: #f3e5f5; color: #4a148c; }
        .alert-error {
            background-color: #ffebee; border-left: 4px solid #f44336;
            padding: 10px 15px; border-radius: 4px; color: #c62828; font-size: 13px;
        }
        .alert-success {
            background-color: #e8f5e9; border-left: 4px solid #4caf50;
            padding: 10px 15px; border-radius: 4px; color: #2e7d32; font-size: 13px;
        }
        .info-estudiante {
            background-color: #e8f5e9; border-left: 5px solid #4caf50;
            padding: 15px 20px; border-radius: 8px; color: #2e7d32;
        }
    </style>
</head>
<body>

<%-- Obtener rol del usuario --%>
<% String rolActual = (String) request.getAttribute("rol");
   boolean esEstudiante = "estudiante".equals(rolActual); %>

<!-- Navbar -->
<nav class="navbar">
    <div class="nav-wrapper container">
        <a href="#" class="brand-logo left">
            <i class="material-icons">school</i> AulaVirtual
        </a>
        <ul class="right">
            <% if ("administrador".equals(rolActual)) { %>
                <li><a href="dashboard-admin"><i class="material-icons">dashboard</i></a></li>
            <% } else if ("docente".equals(rolActual)) { %>
                <li><a href="dashboard-docente"><i class="material-icons">dashboard</i></a></li>
            <% } else { %>
                <li><a href="dashboard-estudiante"><i class="material-icons">dashboard</i></a></li>
            <% } %>
            <li><a href="logout"><i class="material-icons">exit_to_app</i></a></li>
        </ul>
    </div>
</nav>

<div class="container">
    <h5 class="page-title">
        <i class="material-icons" style="vertical-align:middle">grade</i>
        <% if (esEstudiante) { %>
            Mis Calificaciones
        <% } else { %>
            Registro de Calificaciones
        <% } %>
    </h5>

    <%-- Mensajes --%>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert-error" style="margin-bottom:15px;">
            <i class="material-icons tiny">error</i> <%= request.getAttribute("error") %>
        </div>
    <% } %>
    <% if (request.getAttribute("exito") != null) { %>
        <div class="alert-success" style="margin-bottom:15px;">
            <i class="material-icons tiny">check_circle</i> <%= request.getAttribute("exito") %>
        </div>
    <% } %>

    <%-- FORMULARIO: solo visible para docentes y admins --%>
    <% if (!esEstudiante) { %>
    <div class="card card-form z-depth-2">
        <div class="card-content">
            <span class="card-title" style="color:#1a237e; font-weight:700;">
                <i class="material-icons left">add_circle</i>Registrar nueva calificación
            </span>
            <form action="notas" method="post">
                <p class="section-title">Selección de estudiante y materia</p>
                <div class="row">
                    <div class="col s12 m6 input-field">
                        <select name="id_estudiante" class="browser-default" required>
                            <option value="" disabled selected>Selecciona el estudiante</option>
                            <%
                                List<Estudiante> estudiantes = (List<Estudiante>) request.getAttribute("estudiantes");
                                if (estudiantes != null) {
                                    for (Estudiante est : estudiantes) {
                            %>
                                <option value="<%= est.getIdEstudiante() %>">
                                    <%= est.getNombreCompleto() %> — <%= est.getNumeroDocumento() %>
                                </option>
                            <%      }
                                }
                            %>
                        </select>
                    </div>
                    <div class="col s12 m6 input-field">
                        <select name="id_curso" class="browser-default" required>
                            <option value="" disabled selected>Selecciona la materia</option>
                            <option value="1">Matemáticas</option>
                            <option value="2">Programación Web</option>
                            <option value="3">Inglés Intermedio</option>
                            <option value="4">Ciencias Naturales</option>
                            <option value="5">Español y Literatura</option>
                        </select>
                    </div>
                </div>

                <p class="section-title">Datos de la calificación</p>
                <div class="row">
                    <div class="col s12 m3 input-field">
                        <select name="periodo" class="browser-default" required>
                            <option value="" disabled selected>Período</option>
                            <option value="1">Período 1</option>
                            <option value="2">Período 2</option>
                            <option value="3">Período 3</option>
                            <option value="4">Período 4</option>
                        </select>
                    </div>
                    <div class="col s12 m3 input-field">
                        <select name="tipo" class="browser-default" required>
                            <option value="" disabled selected>Tipo</option>
                            <option value="parcial">Parcial</option>
                            <option value="examen">Examen</option>
                            <option value="taller">Taller</option>
                            <option value="proyecto">Proyecto</option>
                            <option value="otro">Otro</option>
                        </select>
                    </div>
                    <div class="col s12 m3 input-field">
                        <input id="valor" name="valor" type="number" step="0.1" min="0" max="5" required>
                        <label for="valor">Nota (0.0 – 5.0) *</label>
                    </div>
                    <div class="col s12 m3 input-field">
                        <input id="porcentaje" name="porcentaje" type="number" step="0.1" min="0" max="100" required>
                        <label for="porcentaje">Porcentaje (%) *</label>
                    </div>
                    <div class="col s12 input-field">
                        <input id="descripcion" name="descripcion" type="text">
                        <label for="descripcion">Descripción (opcional)</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col s12">
                        <button class="btn btn-guardar waves-effect waves-light" type="submit">
                            <i class="material-icons left">save</i>Guardar calificación
                        </button>
                        <a href="notas" class="btn-flat" style="margin-left:10px;">Cancelar</a>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!-- Filtro por estudiante (solo docente y admin) -->
    <div class="card z-depth-1" style="border-radius:12px; margin-top:20px;">
        <div class="card-content" style="padding:15px 20px;">
            <form action="notas" method="get" style="display:flex; gap:15px; align-items:flex-end; flex-wrap:wrap;">
                <div class="input-field" style="flex:1; min-width:200px; margin:0;">
                    <select name="id_estudiante" class="browser-default">
                        <option value="">Todos los estudiantes</option>
                        <%
                            List<Estudiante> estsFiltro = (List<Estudiante>) request.getAttribute("estudiantes");
                            String filtroActual = (String) request.getAttribute("idEstFiltro");
                            if (estsFiltro != null) {
                                for (Estudiante est : estsFiltro) {
                                    String sel = (filtroActual != null &&
                                                  filtroActual.equals(String.valueOf(est.getIdEstudiante())))
                                                  ? "selected" : "";
                        %>
                            <option value="<%= est.getIdEstudiante() %>" <%= sel %>>
                                <%= est.getNombreCompleto() %>
                            </option>
                        <%      }
                            }
                        %>
                    </select>
                </div>
                <button class="btn waves-effect" style="background:#1a237e; border-radius:8px;" type="submit">
                    <i class="material-icons left">filter_list</i>Filtrar
                </button>
                <a href="notas" class="btn-flat">Ver todas</a>
            </form>
        </div>
    </div>

    <% } else { %>
    <%-- Mensaje informativo para estudiantes --%>
    <div class="info-estudiante" style="margin-bottom:20px;">
        <i class="material-icons tiny">info</i>
        Aquí puedes consultar todas tus calificaciones registradas por tus docentes.
    </div>
    <% } %>

    <!-- Tabla de notas -->
    <div class="card z-depth-2" style="border-radius:12px; margin-top:10px;">
        <div class="card-content">
            <span class="card-title" style="color:#1a237e; font-weight:700;">
                <i class="material-icons left">list</i>
                <% if (esEstudiante) { %>Mis calificaciones<% } else { %>Calificaciones registradas<% } %>
            </span>
            <table class="highlight responsive-table">
                <thead>
                    <tr>
                        <th>#</th>
                        <% if (!esEstudiante) { %><th>Estudiante</th><% } %>
                        <th>Materia</th>
                        <th>Período</th>
                        <th>Tipo</th>
                        <th>Descripción</th>
                        <th>Nota</th>
                        <th>%</th>
                        <th>Ponderado</th>
                        <th>Fecha</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    List<Nota> notas = (List<Nota>) request.getAttribute("notas");
                    if (notas != null && !notas.isEmpty()) {
                        int i = 1;
                        for (Nota n : notas) {
                            String claseNota = n.getValor() >= 3.0 ? "nota-alta"
                                             : n.getValor() >= 2.0 ? "nota-media" : "nota-baja";
                            String claseTipo = "badge-tipo tipo-" + n.getTipo();
                %>
                    <tr>
                        <td><%= i++ %></td>
                        <% if (!esEstudiante) { %>
                        <td><strong><%= n.getEstudianteNombre() %></strong></td>
                        <% } %>
                        <td><%= n.getCursoNombre() %></td>
                        <td style="text-align:center;">P<%= n.getPeriodo() %></td>
                        <td><span class="<%= claseTipo %>"><%= n.getTipo() %></span></td>
                        <td><%= n.getDescripcion() != null ? n.getDescripcion() : "-" %></td>
                        <td class="<%= claseNota %>" style="text-align:center;">
                            <%= String.format("%.1f", n.getValor()) %>
                        </td>
                        <td style="text-align:center;"><%= (int)n.getPorcentaje() %>%</td>
                        <td style="text-align:center;"><%= String.format("%.2f", n.getValorPonderado()) %></td>
                        <td style="font-size:12px; color:#666;">
                            <%= n.getFechaRegistro() != null ? n.getFechaRegistro().toString().substring(0,10) : "-" %>
                        </td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="10" style="text-align:center; color:#9e9e9e; padding:30px;">
                            <i class="material-icons">info</i><br>
                            <% if (esEstudiante) { %>No tienes calificaciones registradas aún.
                            <% } else { %>No hay calificaciones registradas aún.<% } %>
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

