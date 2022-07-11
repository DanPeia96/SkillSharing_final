package es.uji.ei1027.SkillSharing.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Oferta {
    private String codigo_oferta;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fecha_inicio;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fecha_fin;
    private Usuario usuario;
    private Boolean tipo;
    private String nombre_habilidad;
    private int nivel_habilidad;
    private String descripcion;

    public Oferta() {
    }

    public String getCodigo_oferta() {
        return codigo_oferta;
    }
    public void setCodigo_oferta(String codigo) {
        this.codigo_oferta = codigo;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }
    public void setFecha_inicio(LocalDate d) {
        this.fecha_inicio = d;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }
    public void setFecha_fin(LocalDate d) {
        this.fecha_fin = d;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getTipo(){ return tipo;}
    public void setTipo(Boolean tipo){this.tipo = tipo;}

    public String getNombre_habilidad() {
        return nombre_habilidad;
    }
    public void setNombre_habilidad(String nombre) {
        this.nombre_habilidad = nombre;
    }

    public int getNivel_habilidad() {
        return nivel_habilidad;
    }

    public void setNivel_habilidad(int nivel_habilidad) {
        this.nivel_habilidad = nivel_habilidad;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String d) {
        this.descripcion = d;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "codigo_oferta='" + codigo_oferta + '\'' +
                ", fecha_inicio=" + fecha_inicio +
                ", fecha_fin=" + fecha_fin +
                ", usuario=" + usuario +
                ", tipo=" + tipo +
                ", nombre_habilidad='" + nombre_habilidad + '\'' +
                ", nivel_habilidad='" + nivel_habilidad + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
