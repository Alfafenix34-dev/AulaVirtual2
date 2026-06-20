<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AulaVirtual — Registro</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg, #1a237e, #3949ab); min-height: 100vh; padding: 30px 0; }
        .registro-card { border-radius: 16px; padding: 10px; max-width: 700px; margin: auto; }
        .card-title-area { text-align: center; padding: 20px 0 5px; }
        .card-title-area i { font-size: 48px; color: #1a237e; }
        .card-title-area h5 { color: #1a237e; font-weight: 700; }
        .section-title {
            color: #1a237e; font-weight: 600; font-size: 14px;
            border-bottom: 2px solid #e8eaf6; padding-bottom: 5px;
            margin: 20px 0 10px; text-transform: uppercase; letter-spacing: 1px;
        }
        .btn-registro { background-color: #1a237e; border-radius: 8px; }
        .btn-registro:hover { background-color: #283593; }
        .alert-error {
            background-color: #ffebee; border-left: 4px solid #f44336;
            padding: 10px 15px; border-radius: 4px; margin-bottom: 15px;
            color: #c62828; font-size: 13px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="card registro-card z-depth-4">

        <div class="card-title-area">
            <i class="material-icons">person_add</i>
            <h5>Crear cuenta</h5>
            <p style="color:#666; font-size:13px;">Completa los datos para registrarte en AulaVirtual</p>
        </div>

        <div class="card-content">

            <% if (request.getAttribute("error") != null) { %>
                <div class="alert-error">
                    <i class="material-icons tiny">error</i>
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>

            <%-- action apunta al RegistroServlet (POST) --%>
            <form action="registro" method="post">

                <!-- SECCIÓN 1: Tipo de usuario -->
                <p class="section-title"><i class="material-icons tiny">badge</i> Tipo de usuario</p>
                <div class="row">
                    <div class="col s12 m6 input-field">
                        <select name="rol" id="rol" class="browser-default" required>
                            <option value="" disabled selected>Selecciona tu rol</option>
                            <option value="estudiante">Estudiante</option>
                            <option value="docente">Docente</option>
                        </select>
                    </div>
                    <div class="col s12 m3 input-field">
                        <select name="tipo_documento" class="browser-default">
                            <option value="TI">Tarjeta de Identidad</option>
                            <option value="CC">Cédula de Ciudadanía</option>
                            <option value="CE">Cédula Extranjera</option>
                            <option value="PEP">PEP</option>
                        </select>
                    </div>
                    <div class="col s12 m3 input-field">
                        <input id="numero_documento" name="numero_documento" type="text" required>
                        <label for="numero_documento">N° Documento *</label>
                    </div>
                </div>

                <!-- SECCIÓN 2: Datos personales -->
                <p class="section-title"><i class="material-icons tiny">person</i> Datos personales</p>
                <div class="row">
                    <div class="col s12 m6 input-field">
                        <input id="nombres" name="nombres" type="text" required>
                        <label for="nombres">Nombres *</label>
                    </div>
                    <div class="col s12 m6 input-field">
                        <input id="apellidos" name="apellidos" type="text" required>
                        <label for="apellidos">Apellidos *</label>
                    </div>
                    <div class="col s12 m6 input-field">
                        <input id="fecha_nacimiento" name="fecha_nacimiento" type="date">
                        <label for="fecha_nacimiento" class="active">Fecha de nacimiento</label>
                    </div>
                    <div class="col s12 m6 input-field">
                        <input id="telefono" name="telefono" type="tel">
                        <label for="telefono">Teléfono</label>
                    </div>
                    <div class="col s12 input-field">
                        <input id="ciudad" name="ciudad" type="text" value="Bogotá D.C.">
                        <label for="ciudad" class="active">Ciudad</label>
                    </div>
                </div>

                <!-- SECCIÓN 3: Acceso -->
                <p class="section-title"><i class="material-icons tiny">lock</i> Datos de acceso</p>
                <div class="row">
                    <div class="col s12 input-field">
                        <input id="email" name="email" type="email" required>
                        <label for="email">Correo institucional *</label>
                    </div>
                    <div class="col s12 m6 input-field">
                        <input id="password" name="password" type="password" required minlength="6">
                        <label for="password">Contraseña * (mín. 6 caracteres)</label>
                    </div>
                    <div class="col s12 m6 input-field">
                        <input id="confirmar_password" name="confirmar_password" type="password" required>
                        <label for="confirmar_password">Confirmar contraseña *</label>
                    </div>
                </div>

                <!-- Términos -->
                <div class="row">
                    <div class="col s12">
                        <label>
                            <input type="checkbox" name="acepta_terminos" required/>
                            <span>Acepto los <a href="#" style="color:#1a237e">términos y condiciones</a></span>
                        </label>
                    </div>
                </div>

                <div class="row" style="margin-top:10px;">
                    <div class="col s12 m6">
                        <button class="btn btn-registro waves-effect waves-light" type="submit" style="width:100%">
                            <i class="material-icons left">how_to_reg</i>Registrarme
                        </button>
                    </div>
                    <div class="col s12 m6" style="text-align:center; padding-top:12px;">
                        <a href="login" style="color:#1a237e; font-size:13px;">¿Ya tienes cuenta? Inicia sesión</a>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
</body>
</html>
