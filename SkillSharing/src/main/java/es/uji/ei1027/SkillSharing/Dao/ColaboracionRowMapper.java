package es.uji.ei1027.SkillSharing.Dao;


import es.uji.ei1027.SkillSharing.Model.Colaboracion;
import es.uji.ei1027.SkillSharing.Model.Oferta;
import es.uji.ei1027.SkillSharing.Model.Solicitud;
import es.uji.ei1027.SkillSharing.Model.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ColaboracionRowMapper implements RowMapper<Colaboracion> {
    public Colaboracion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Colaboracion colaboracion = new Colaboracion();

        Solicitud solicitud=new Solicitud();
        Usuario usuario1=new Usuario();
        usuario1.setUserId(rs.getString("id_usuario"));
        usuario1.setNombre_completo(rs.getString("nombre2"));
        usuario1.setSaldo_horas(rs.getFloat("horas2"));
        Oferta oferta=new Oferta();
        oferta.setCodigo_oferta(rs.getString("codigo_oferta"));
        oferta.setFecha_inicio(rs.getObject("fecha_inicio", LocalDate.class));
        oferta.setFecha_fin(rs.getObject("fecha_fin", LocalDate.class));
        oferta.setNivel_habilidad(rs.getInt("nivel_habilidad"));
        oferta.setNombre_habilidad(rs.getString("nombre_habilidad"));


        oferta.setUsuario(usuario1);
        oferta.setTipo(rs.getBoolean("tipo"));

        solicitud.setCodigo_solicitud(rs.getString("codigo_solicitud"));
        solicitud.setOferta(oferta);
        Usuario usuario=new Usuario();
        usuario.setUserId(rs.getString("id_usuario_solicitante"));
        usuario.setNombre_completo(rs.getString("nombre1"));
        usuario.setSaldo_horas(rs.getFloat("horas1"));
        solicitud.setUsuario_solicitante(usuario);
        solicitud.setFecha_emision(rs.getDate("fecha_emision"));
        solicitud.setFecha_aceptacion(rs.getDate("fecha_aceptacion"));

        colaboracion.setCodigo_colaboracion(rs.getString("codigo_colaboracion"));
        colaboracion.setSolicitud(solicitud);
        colaboracion.setHoras(rs.getFloat("horas"));
        colaboracion.setEvaluacion(rs.getInt("evaluacion"));
        colaboracion.setEstado(rs.getBoolean("estado"));
        return colaboracion;
    }
}