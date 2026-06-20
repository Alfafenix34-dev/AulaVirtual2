<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AulaVirtual — Recuperar Contraseña</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1a237e 0%, #283593 50%, #3949ab 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .card-recuperar {
            border-radius: 16px;
            padding: 20px;
            max-width: 420px;
            width: 100%;
        }
        .logo-area { text-align: center; padding: 20px 0 10px; }
        .logo-area i { font-size: 64px; color: #1a237e; }
        .logo-area h5 { color: #1a237e; font-weight: 700; margin: 8px 0 4px; }
        .logo-area p { color: #666; font-size: 13px; }
        .btn-recuperar {
            background-color: #1a237e;
            width: 100%;
            border-radius: 8px;
            height: 45px;
            font-size: 15px;
        }
        .btn-recuperar:hover { background-color: #283593; }
        .alert-error {
            background-color: #ffebee; border-left: 4px solid #f44336;
            padding: 10px 15px; border-radius: 4px;
            margin-bottom: 15px; color: #c62828; font-size: 13px;
        }
        .alert-success {
            background-color: #e8f5e9; border-left: 4px solid #4caf50;
            padding: 10px 15px; border-radius: 4px;
            margin-bottom: 15px; color: #2e7d32; font-size: 13px;
        }
        .paso { display: none; }
        .paso.activo { display: block; }
        .pasos-indicador {
            display: flex; justify-content: center;
            gap: 10px; margin-bottom: 20px;
        }
        .paso-dot {
            width: 12px; height: 12px;
            border-radius: 50%; background-color: #c5cae9;
        }
        .paso-dot.activo { background-color: #1a237e; }
    </style>
</head>
<body>
<div class="container">
    <div class="card card-recuperar z-depth-4">

        <div class="logo-area">
            <i class="material-icons">lock_reset</i>
            <h5>Recuperar contraseña</h5>
            <p>AulaVirtual — Sistema de Gestión Estudiantil</p>
        </div>

        <div class="card-content">

            <%-- Mensajes del servlet --%>
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

            <%-- Indicador de pasos --%>
            <div class="pasos-indicador">
                <div class="paso-dot activo" id="dot1"></div>
                <div class="paso-dot" id="dot2"></div>
                <div class="paso-dot" id="dot3"></div>
            </div>

            <%-- PASO 1: Ingresar correo --%>
            <div class="paso activo" id="paso1">
                <p style="color:#666; font-size:14px; text-align:center; margin-bottom:20px;">
                    Ingresa tu correo institucional y te enviaremos un código de verificación.
                </p>
                <form action="recuperar" method="post" id="formPaso1">
                    <input type="hidden" name="paso" value="1">
                    <div class="input-field">
                        <i class="material-icons prefix">email</i>
                        <input id="email" name="email" type="email" required>
                        <label for="email">Correo institucional</label>
                    </div>
                    <button class="btn btn-recuperar waves-effect waves-light" type="submit">
                        <i class="material-icons left">send</i>Enviar código
                    </button>
                </form>
            </div>

            <%-- PASO 2: Verificar código --%>
            <div class="paso" id="paso2">
                <p style="color:#666; font-size:14px; text-align:center; margin-bottom:20px;">
                    Ingresa el código de 6 dígitos enviado a tu correo.
                </p>
                <form action="recuperar" method="post" id="formPaso2">
                    <input type="hidden" name="paso" value="2">
                    <input type="hidden" name="email" id="emailPaso2">
                    <div class="input-field">
                        <i class="material-icons prefix">vpn_key</i>
                        <input id="codigo" name="codigo" type="text"
                               maxlength="6" pattern="[0-9]{6}" required>
                        <label for="codigo">Código de verificación</label>
                    </div>
                    <button class="btn btn-recuperar waves-effect waves-light" type="submit">
                        <i class="material-icons left">verified</i>Verificar código
                    </button>
                </form>
            </div>

            <%-- PASO 3: Nueva contraseña --%>
            <div class="paso" id="paso3">
                <p style="color:#666; font-size:14px; text-align:center; margin-bottom:20px;">
                    Ingresa tu nueva contraseña.
                </p>
                <form action="recuperar" method="post" id="formPaso3">
                    <input type="hidden" name="paso" value="3">
                    <input type="hidden" name="email" id="emailPaso3">
                    <input type="hidden" name="codigo" id="codigoPaso3">
                    <div class="input-field">
                        <i class="material-icons prefix">lock</i>
                        <input id="nueva_password" name="nueva_password"
                               type="password" minlength="6" required>
                        <label for="nueva_password">Nueva contraseña (mín. 6 caracteres)</label>
                    </div>
                    <div class="input-field">
                        <i class="material-icons prefix">lock_outline</i>
                        <input id="confirmar_password" name="confirmar_password"
                               type="password" minlength="6" required>
                        <label for="confirmar_password">Confirmar nueva contraseña</label>
                    </div>
                    <button class="btn btn-recuperar waves-effect waves-light" type="submit">
                        <i class="material-icons left">save</i>Guardar nueva contraseña
                    </button>
                </form>
            </div>

            <div style="text-align:center; margin-top:15px;">
                <a href="login" style="color:#1a237e; font-size:13px;">
                    <i class="material-icons tiny">arrow_back</i> Volver al inicio de sesión
                </a>
            </div>

        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script>
    // Detectar en qué paso estamos según parámetro de sesión
    const urlParams = new URLSearchParams(window.location.search);
    const pasoActual = urlParams.get('paso') || '1';

    function mostrarPaso(num) {
        document.querySelectorAll('.paso').forEach(p => p.classList.remove('activo'));
        document.querySelectorAll('.paso-dot').forEach(d => d.classList.remove('activo'));
        document.getElementById('paso' + num).classList.add('activo');
        document.getElementById('dot' + num).classList.add('activo');
    }

    mostrarPaso(pasoActual);

    // Pasar email entre pasos
    const emailInput = document.getElementById('email');
    if (emailInput) {
        emailInput.addEventListener('change', function() {
            const ep2 = document.getElementById('emailPaso2');
            const ep3 = document.getElementById('emailPaso3');
            if (ep2) ep2.value = this.value;
            if (ep3) ep3.value = this.value;
        });
    }
</script>
</body>
</html>
