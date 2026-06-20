package modelo;

import java.util.Date;

/**
 * Modelo que representa una calificación en AulaVirtual
 * GA7-220501096-AA2-EV02
 */
public class Nota {

    private int    idNota;
    private int    idEstudiante;
    private int    idCurso;
    private int    periodo;
    private String tipo;
    private String descripcion;
    private double valor;
    private double porcentaje;
    private Date   fechaRegistro;

    // Campos extra para joins (mostrar en JSP)
    private String estudianteNombre;
    private String cursoNombre;

    // ── Constructor vacío ───────────────────────────────────
    public Nota() {}

    // ── Getters y Setters ───────────────────────────────────
    public int    getIdNota()               { return idNota; }
    public void   setIdNota(int v)          { this.idNota = v; }

    public int    getIdEstudiante()         { return idEstudiante; }
    public void   setIdEstudiante(int v)    { this.idEstudiante = v; }

    public int    getIdCurso()              { return idCurso; }
    public void   setIdCurso(int v)         { this.idCurso = v; }

    public int    getPeriodo()              { return periodo; }
    public void   setPeriodo(int v)         { this.periodo = v; }

    public String getTipo()                 { return tipo; }
    public void   setTipo(String v)         { this.tipo = v; }

    public String getDescripcion()          { return descripcion; }
    public void   setDescripcion(String v)  { this.descripcion = v; }

    public double getValor()                { return valor; }
    public void   setValor(double v)        { this.valor = v; }

    public double getPorcentaje()           { return porcentaje; }
    public void   setPorcentaje(double v)   { this.porcentaje = v; }

    public Date   getFechaRegistro()        { return fechaRegistro; }
    public void   setFechaRegistro(Date v)  { this.fechaRegistro = v; }

    public String getEstudianteNombre()          { return estudianteNombre; }
    public void   setEstudianteNombre(String v)  { this.estudianteNombre = v; }

    public String getCursoNombre()          { return cursoNombre; }
    public void   setCursoNombre(String v)  { this.cursoNombre = v; }

    /** Calcula el valor ponderado de la nota */
    public double getValorPonderado() {
        return (valor * porcentaje) / 100.0;
    }
}