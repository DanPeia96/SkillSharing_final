package es.uji.ei1027.SkillSharing.Model;

import java.util.Date;

public class Incidencia {
    private Usuario usuario;
    private Date fecha;
    private String id_promotor;
    private String descripcion;

    public Incidencia() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha(){return fecha;}

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getId_promotor() {
        return id_promotor;
    }
    public void setId_promotor(String id) {
        this.id_promotor = id;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String d) {
        this.descripcion = d;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "usuario=" + usuario +
                ", fecha=" + fecha +
                ", id_promotor='" + id_promotor + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
