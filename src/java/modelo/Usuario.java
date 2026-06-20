package modelo;

import java.util.Date;

/**
 * Modelo que representa la tabla usuarios de aulavirtual_db
 * AulaVirtual — GA7-220501096-AA2-EV02
 */
public class Usuario {

    private int    idUsuario;
    private int    idRol;
    private String nombres;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
    private Date   fechaNacimiento;
    private String telefono;
    private String ciudad;
    private String email;
    private String passwordHash;
    private int    activo;
    private int    aceptaTerminos;
    private String rolNombre;   // campo extra para joins

    // ── Constructor vacío ───────────────────────────────────
    public Usuario() {}

    // ── Constructor completo ────────────────────────────────
    public Usuario(int idRol, String nombres, String apellidos,
                   String tipoDocumento, String numeroDocumento,
                   Date fechaNacimiento, String telefono,
                   String ciudad, String email, String passwordHash,
                   int aceptaTerminos) {
        this.idRol           = idRol;
        this.nombres         = nombres;
        this.apellidos       = apellidos;
        this.tipoDocumento   = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono        = telefono;
        this.ciudad          = ciudad;
        this.email           = email;
        this.passwordHash    = passwordHash;
        this.aceptaTerminos  = aceptaTerminos;
        this.activo          = 1;
    }

    // ── Getters y Setters ───────────────────────────────────
    public int    getIdUsuario()        { return idUsuario; }
    public void   setIdUsuario(int v)   { this.idUsuario = v; }

    public int    getIdRol()            { return idRol; }
    public void   setIdRol(int v)       { this.idRol = v; }

    public String getNombres()          { return nombres; }
    public void   setNombres(String v)  { this.nombres = v; }

    public String getApellidos()           { return apellidos; }
    public void   setApellidos(String v)   { this.apellidos = v; }

    public String getTipoDocumento()          { return tipoDocumento; }
    public void   setTipoDocumento(String v)  { this.tipoDocumento = v; }

    public String getNumeroDocumento()           { return numeroDocumento; }
    public void   setNumeroDocumento(String v)   { this.numeroDocumento = v; }

    public Date   getFechaNacimiento()          { return fechaNacimiento; }
    public void   setFechaNacimiento(Date v)    { this.fechaNacimiento = v; }

    public String getTelefono()          { return telefono; }
    public void   setTelefono(String v)  { this.telefono = v; }

    public String getCiudad()          { return ciudad; }
    public void   setCiudad(String v)  { this.ciudad = v; }

    public String getEmail()          { return email; }
    public void   setEmail(String v)  { this.email = v; }

    public String getPasswordHash()          { return passwordHash; }
    public void   setPasswordHash(String v)  { this.passwordHash = v; }

    public int    getActivo()          { return activo; }
    public void   setActivo(int v)     { this.activo = v; }

    public int    getAceptaTerminos()        { return aceptaTerminos; }
    public void   setAceptaTerminos(int v)   { this.aceptaTerminos = v; }

    public String getRolNombre()          { return rolNombre; }
    public void   setRolNombre(String v)  { this.rolNombre = v; }

    // ── Nombre completo (útil en JSP) ───────────────────────
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    @Override
    public String toString() {
        return "Usuario{id=" + idUsuario + ", email=" + email + ", rol=" + rolNombre + "}";
    }
}
