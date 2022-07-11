package es.uji.ei1027.SkillSharing.Dao;

import es.uji.ei1027.SkillSharing.Model.Oferta;
import es.uji.ei1027.SkillSharing.Model.Solicitud;
import es.uji.ei1027.SkillSharing.Model.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public final class SolicitudRowMapper implements RowMapper<Solicitud> {
    public Solicitud mapRow(ResultSet rs, int rowNum) throws SQLException {
        Solicitud solicitud=new Solicitud();

        Usuario usuario2=new Usuario();
        usuario2.setUserId(rs.getString("id_usuario"));
        usuario2.setNombre_completo(rs.getString("nombre2"));
        usuario2.setSaldo_horas(rs.getFloat("horas2"));
        Oferta oferta=new Oferta();
        oferta.setCodigo_oferta(rs.getString("codigo_oferta"));
        oferta.setFecha_inicio(rs.getObject("fecha_inicio", LocalDate.class));
        oferta.setFecha_fin(rs.getObject("fecha_fin", LocalDate.class));
        oferta.setNivel_habilidad(rs.getInt("nivel_habilidad"));
        oferta.setNombre_habilidad(rs.getString("nombre_habilidad"));
        oferta.setUsuario(usuario2);

        solicitud.setOferta(oferta);
        Usuario usuario=new Usuario();
        usuario.setUserId(rs.getString("id_usuario_solicitante"));
        usuario.setNombre_completo(rs.getString("nombre_completo"));
        solicitud.setUsuario_solicitante(usuario);
        solicitud.setCodigo_solicitud(rs.getString("codigo_solicitud"));
        solicitud.setFecha_emision(rs.getDate("fecha_emision"));
        solicitud.setFecha_aceptacion(rs.getDate("fecha_aceptacion"));

        return solicitud;
    }
}
