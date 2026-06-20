<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AulaVirtual — Iniciar Sesión</title>
    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <!-- Material Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1a237e 0%, #283593 50%, #3949ab 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-card {
            border-radius: 16px;
            padding: 20px;
            max-width: 420px;
            width: 100%;
        }
        .logo-area {
            text-align: center;
            padding: 20px 0 10px;
        }
        .logo-area i {
            font-size: 64px;
            color: #1a237e;
        }
        .logo-area h5 {
            color: #1a237e;
            font-weight: 700;
            margin: 8px 0 4px;
        }
        .logo-area p {
            color: #666;
            font-size: 13px;
        }
        .btn-login {
            background-color: #1a237e;
            width: 100%;
            border-radius: 8px;
            height: 45px;
            font-size: 15px;
            letter-spacing: 1px;
        }
        .btn-login:hover { background-color: #283593; }
        .links-area {
            text-align: center;
            margin-top: 15px;
        }
        .links-area a {
            color: #1a237e;
            font-size: 13px;
            margin: 0 8px;
        }
        .alert-error {
            background-color: #ffebee;
            border-left: 4px solid #f44336;
            padding: 10px 15px;
            border-radius: 4px;
            margin-bottom: 15px;
            color: #c62828;
            font-size: 13px;
        }
        .alert-success {
            background-color: #e8f5e9;
            border-left: 4px solid #4caf50;
            padding: 10px 15px;
            border-radius: 4px;
            margin-bottom: 15px;
            color: #2e7d32;
            font-size: 13px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="card login-card z-depth-4">

        <!-- Logo -->
        <div class="logo-area">
            <i class="material-icons">school</i>
            <h5>AulaVirtual</h5>
            <p>Sistema de Gestión Estudiantil</p>
        </div>

        <div class="card-content">

            <%-- Mensajes de error y éxito desde los Servlets --%>
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert-error">
                    <i class="material-icons tiny">error</i>
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            <% if (request.getAttribute("exito") != null) { %>
                <div class="alert-success">
                    <i class="material-icons tiny">check_circle</i>
                    <%= request.getAttribute("exito") %>
                </div>
            <% } %>

            <%-- Formulario — action apunta al LoginServlet (POST) --%>
            <form action="login" method="post">

                <div class="input-field">
                    <i class="material-icons prefix">email</i>
                    <input id="email" name="email" type="email" required
                           value="<%= request.getAttribute("emailPrevio") != null
                                      ? request.getAttribute("emailPrevio") : "" %>">
                    <label for="email">Correo institucional</label>
                </div>

                <div class="input-field">
                    <i class="material-icons prefix">lock</i>
                    <input id="password" name="password" type="password" required>
                    <label for="password">Contraseña</label>
                </div>

                <button class="btn btn-login waves-effect waves-light" type="submit">
                    <i class="material-icons left">login</i>
                    Iniciar Sesión
                </button>

            </form>

            <div class="links-area">
                <a href="registro">¿No tienes cuenta? Regístrate</a>
                <br>
                <a href="recuperar">¿Olvidaste tu contraseña?</a>
            </div>

        </div>
    </div>
</div>

<!-- Materialize JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

</body>
</html>
