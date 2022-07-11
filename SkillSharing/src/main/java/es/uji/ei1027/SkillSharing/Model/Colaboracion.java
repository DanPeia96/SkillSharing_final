package es.uji.ei1027.SkillSharing.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Colaboracion {
    private String codigo_colaboracion;
    private Solicitud solicitud;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fecha_inicio;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fecha_fin;
    private float horas;
    private int evaluacion;
    private boolean estado;

    public Colaboracion() {
    }

    public String getCodigo_colaboracion() {
        return codigo_colaboracion;
    }
    public void setCodigo_colaboracion(String codigo) {
        this.codigo_colaboracion = codigo;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public boolean isEstado() {
        return estado;
    }

    public float getHoras() {
        return horas;
    }
    public void setHoras(float h) {
        this.horas = h;
    }

    public int getEvaluacion() {
        return evaluacion;
    }
    public void setEvaluacion(int e) {
        this.evaluacion = e;
    }

    public boolean getEstado(){ return this.estado; }
    public void setEstado(boolean estado) { this.estado=estado; }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    @Override
    public String toString() {
        return "Colaboracion{" +
                "codigo_colaboracion='" + codigo_colaboracion + '\'' +
                ", solicitud=" + solicitud +
                ", fecha_inicio=" + fecha_inicio +
                ", fecha_fin=" + fecha_fin +
                ", horas=" + horas +
                ", evaluacion=" + evaluacion +
                ", estado=" + estado +
                '}';
    }
}
