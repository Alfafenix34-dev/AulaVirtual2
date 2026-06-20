<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AulaVirtual — Sistema de Gestión Estudiantil</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        body { margin: 0; padding: 0; }
        .hero {
            background: linear-gradient(135deg, #1a237e 0%, #283593 60%, #3949ab 100%);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .navbar-hero { background-color: transparent; box-shadow: none; }
        .hero-content {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            color: white;
            padding: 40px 20px;
        }
        .hero-icon { font-size: 96px; opacity: 0.9; }
        .hero-title {
            font-size: 48px;
            font-weight: 800;
            margin: 10px 0;
            letter-spacing: 2px;
        }
        .hero-subtitle {
            font-size: 18px;
            opacity: 0.85;
            margin-bottom: 40px;
        }
        .btn-hero {
            background-color: white;
            color: #1a237e;
            border-radius: 30px;
            padding: 0 40px;
            height: 50px;
            line-height: 50px;
            font-size: 16px;
            font-weight: 700;
            margin: 0 10px 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
        }
        .btn-hero:hover {
            background-color: #e8eaf6;
            color: #1a237e;
        }
        .btn-hero-outline {
            border: 2px solid white;
            color: white;
            border-radius: 30px;
            padding: 0 40px;
            height: 50px;
            line-height: 46px;
            font-size: 16px;
            font-weight: 700;
            margin: 0 10px 15px;
            display: inline-block;
            text-decoration: none;
            transition: all 0.3s;
        }
        .btn-hero-outline:hover {
            background-color: white;
            color: #1a237e;
        }
        /* Sección de características */
        .features { padding: 60px 0; background-color: #f5f5f5; }
        .feature-card {
            border-radius: 16px;
            text-align: center;
            padding: 30px 20px;
            height: 100%;
        }
        .feature-card i    { font-size: 56px; color: #1a237e; }
        .feature-card h5   { color: #1a237e; font-weight: 700; }
        .feature-card p    { color: #666; font-size: 14px; }
        /* Footer */
        .footer-av {
            background-color: #1a237e;
            color: white;
            text-align: center;
            padding: 20px;
            font-size: 13px;
        }
        .footer-av a { color: #90caf9; }
    </style>
</head>
<body>

<!-- Hero Section -->
<div class="hero">
    <nav class="navbar-hero">
        <div class="nav-wrapper container">
            <a href="#" class="brand-logo" style="color:white;">
                <i class="material-icons">school</i> AulaVirtual
            </a>
            <ul class="right">
                <li><a href="login" style="color:white;">Iniciar sesión</a></li>
                <li><a href="registro" style="color:white;">Registrarse</a></li>
            </ul>
        </div>
    </nav>

    <div class="hero-content">
        <div>
            <i class="material-icons hero-icon">school</i>
            <h1 class="hero-title">AulaVirtual</h1>
            <p class="hero-subtitle">Sistema de Gestión Estudiantil<br>
                Gestiona estudiantes, docentes, cursos y calificaciones</p>
            <div>
                <a href="login" class="btn btn-hero waves-effect">
                    <i class="material-icons left">login</i>Iniciar sesión
                </a>
                <a href="registro" class="btn-hero-outline waves-effect">
                    <i class="material-icons left">person_add</i>Registrarse
                </a>
            </div>
        </div>
    </div>
</div>

<!-- Características -->
<div class="features">
    <div class="container">
        <h4 style="text-align:center; color:#1a237e; font-weight:700; margin-bottom:40px;">
            ¿Qué puedes hacer en AulaVirtual?
        </h4>
        <div class="row">
            <div class="col s12 m6 l3">
                <div class="card feature-card z-depth-2">
                    <i class="material-icons">people</i>
                    <h5>Estudiantes</h5>
                    <p>Registra y gestiona la información de todos los estudiantes del sistema.</p>
                </div>
            </div>
            <div class="col s12 m6 l3">
                <div class="card feature-card z-depth-2">
                    <i class="material-icons">grade</i>
                    <h5>Calificaciones</h5>
                    <p>Registra notas por período, tipo de evaluación y materia fácilmente.</p>
                </div>
            </div>
            <div class="col s12 m6 l3">
                <div class="card feature-card z-depth-2">
                    <i class="material-icons">book</i>
                    <h5>Cursos</h5>
                    <p>Administra los cursos, materias y asignaciones de docentes.</p>
                </div>
            </div>
            <div class="col s12 m6 l3">
                <div class="card feature-card z-depth-2">
                    <i class="material-icons">security</i>
                    <h5>Roles y acceso</h5>
                    <p>Control de acceso por roles: administrador, docente y estudiante.</p>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<div class="footer-av">
    <p>© 2025 AulaVirtual — Sistema de Gestión Estudiantil |
        GA7-220501096-AA2-EV02 |
        <a href="login">Iniciar sesión</a>
    </p>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
</body>
</html>
