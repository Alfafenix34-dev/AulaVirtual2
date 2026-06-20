<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Usuario"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AulaVirtual — Panel Docente</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        body { background-color: #f5f5f5; }
        .navbar { background-color: #1b5e20; }
        .welcome-banner {
            background: linear-gradient(135deg, #1b5e20, #388e3c);
            color: white; border-radius: 16px;
            padding: 30px; margin: 30px 0 25px;
        }
        .welcome-banner h4 { margin: 0 0 5px; font-weight: 700; }
        .welcome-banner p  { margin: 0; opacity: 0.85; font-size: 14px; }
        .menu-card {
            border-radius: 12px; cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
            text-align: center; padding: 25px 15px;
        }
        .menu-card:hover { transform: translateY(-4px); box-shadow: 0 8px 25px rgba(0,0,0,0.15); }
        .menu-card i  { font-size: 48px; color: #1b5e20; }
        .menu-card h6 { color: #1b5e20; font-weight: 700; margin: 10px 0 5px; }
        .menu-card p  { font-size: 12px; color: #666; margin: 0; }
        .stat-card {
            border-radius: 12px; text-align: center;
            padding: 25px 15px; color: white;
        }
        .stat-card i  { font-size: 48px; }
        .stat-card h3 { font-size: 42px; font-weight: 700; margin: 5px 0; }
        .stat-card p  { font-size: 13px; opacity: 0.9; margin: 0; }
        .bg-green  { background-color: #2e7d32; }
        .bg-blue   { background-color: #1565c0; }
        .bg-orange { background-color: #e65100; }
    </style>
</head>
<body>

<nav class="navbar">
    <div class="nav-wrapper container">
        <a href="#" class="brand-logo left">
            <i class="material-icons">school</i> AulaVirtual
        </a>
        <ul class="right">
            <li><a href="logout" title="Cerrar sesión">
                <i class="material-icons">exit_to_app</i>
            </a></li>
        </ul>
    </div>
</nav>

<div class="container">

    <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String nombre = (usuario != null) ? usuario.getNombres() : "Docente";
    %>
    <div class="welcome-banner">
        <h4><i class="material-icons" style="vertical-align:middle; font-size:36px;">person</i>
            Bienvenido, <%= nombre %>
        </h4>
        <p>Panel Docente — Sistema de Gestión Estudiantil AulaVirtual</p>
    </div>

    <!-- Estadísticas -->
    <div class="row">
        <div class="col s12 m4">
            <div class="card stat-card bg-green z-depth-2">
                <i class="material-icons">people</i>
                <h3>3</h3>
                <p>Mis estudiantes</p>
            </div>
        </div>
        <div class="col s12 m4">
            <div class="card stat-card bg-blue z-depth-2">
                <i class="material-icons">book</i>
                <h3>3</h3>
                <p>Mis materias</p>
            </div>
        </div>
        <div class="col s12 m4">
            <div class="card stat-card bg-orange z-depth-2">
                <i class="material-icons">grade</i>
                <h3>7</h3>
                <p>Notas registradas</p>
            </div>
        </div>
    </div>

    <!-- Menú -->
    <h6 style="color:#1b5e20; font-weight:700; margin-bottom:15px;">
        <i class="material-icons" style="vertical-align:middle">apps</i> Mis módulos
    </h6>
    <div class="row">
        <div class="col s12 m4">
            <a href="notas" style="text-decoration:none;">
                <div class="card menu-card z-depth-2">
                    <i class="material-icons">grade</i>
                    <h6>Calificaciones</h6>
                    <p>Registrar y consultar notas de mis estudiantes</p>
                </div>
            </a>
        </div>
        <div class="col s12 m4">
            <a href="estudiantes" style="text-decoration:none;">
                <div class="card menu-card z-depth-2">
                    <i class="material-icons">people</i>
                    <h6>Mis estudiantes</h6>
                    <p>Ver listado de estudiantes a cargo</p>
                </div>
            </a>
        </div>
        <div class="col s12 m4">
            <a href="logout" style="text-decoration:none;">
                <div class="card menu-card z-depth-2">
                    <i class="material-icons">exit_to_app</i>
                    <h6>Cerrar sesión</h6>
                    <p>Salir del sistema</p>
                </div>
            </a>
        </div>
    </div>

</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
</body>
</html>
