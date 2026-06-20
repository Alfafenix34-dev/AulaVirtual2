package modelo;

/**
 * Modelo que representa un estudiante del sistema AulaVirtual
 * GA7-220501096-AA2-EV02
 */
public class Estudiante {

    private int    idEstudiante;
    private int    idUsuario;
    private String nombres;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
    private String email;
    private String telefono;
    private String ciudad;
    private String codigoEstudiantil;
    private String acudienteNombre;
    private String acudienteTelefono;
    private String grupoNombre;      // campo extra para joins

    // ── Constructor vacío ───────────────────────────────────
    public Estudiante() {}

    // ── Getters y Setters ───────────────────────────────────
    public int    getIdEstudiante()           { return idEstudiante; }
    public void   setIdEstudiante(int v)      { this.idEstudiante = v; }

    public int    getIdUsuario()              { return idUsuario; }
    public void   setIdUsuario(int v)         { this.idUsuario = v; }

    public String getNombres()                { return nombres; }
    public void   setNombres(String v)        { this.nombres = v; }

    public String getApellidos()              { return apellidos; }
    public void   setApellidos(String v)      { this.apellidos = v; }

    public String getTipoDocumento()          { return tipoDocumento; }
    public void   setTipoDocumento(String v)  { this.tipoDocumento = v; }

    public String getNumeroDocumento()           { return numeroDocumento; }
    public void   setNumeroDocumento(String v)   { this.numeroDocumento = v; }

    public String getEmail()                  { return email; }
    public void   setEmail(String v)          { this.email = v; }

    public String getTelefono()               { return telefono; }
    public void   setTelefono(String v)       { this.telefono = v; }

    public String getCiudad()                 { return ciudad; }
    public void   setCiudad(String v)         { this.ciudad = v; }

    public String getCodigoEstudiantil()          { return codigoEstudiantil; }
    public void   setCodigoEstudiantil(String v)  { this.codigoEstudiantil = v; }

    public String getAcudienteNombre()            { return acudienteNombre; }
    public void   setAcudienteNombre(String v)    { this.acudienteNombre = v; }

    public String getAcudienteTelefono()           { return acudienteTelefono; }
    public void   setAcudienteTelefono(String v)   { this.acudienteTelefono = v; }

    public String getGrupoNombre()            { return grupoNombre; }
    public void   setGrupoNombre(String v)    { this.grupoNombre = v; }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}
