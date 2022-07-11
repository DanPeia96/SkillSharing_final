package es.uji.ei1027.SkillSharing.Model;

import java.util.Date;

public class Solicitud {
    private String codigo_solicitud;

    private Oferta oferta;
    private Usuario usuario_solicitante;
    private Date fecha_emision;
    private Date fecha_aceptacion; //denegado: fecha_aceptacion==fecha_fin oferta, pendiente null, aceptado

    public Solicitud() {
    }

    public String getCodigo_solicitud() {
        return codigo_solicitud;
    }

    public void setCodigo_solicitud(String codigo_solicitud) {
        this.codigo_solicitud = codigo_solicitud;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public Usuario getUsuario_solicitante() {
        return usuario_solicitante;
    }

    public void setUsuario_solicitante(Usuario usuario_solicitante) {
        this.usuario_solicitante = usuario_solicitante;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }
    public void setFecha_emision(Date d) {
        this.fecha_emision = d;
    }

    public Date getFecha_aceptacion() {
        return fecha_aceptacion;
    }

    public void setFecha_aceptacion(Date d) {
        this.fecha_aceptacion = d;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "codigo_solicitud='" + codigo_solicitud + '\'' +
                ", oferta=" + oferta +
                ", usuario_solicitante=" + usuario_solicitante +
                ", fecha_emision=" + fecha_emision +
                ", fecha_aceptacion=" + fecha_aceptacion +
                '}';
    }
}
