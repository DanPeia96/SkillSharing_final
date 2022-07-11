package es.uji.ei1027.SkillSharing.Dao;

import es.uji.ei1027.SkillSharing.Model.Oferta;
import es.uji.ei1027.SkillSharing.Model.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class OfertaRowMapper implements RowMapper<Oferta> {
    public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
        Oferta oferta = new Oferta();
        Usuario usuario=new Usuario();

        usuario.setUserId(rs.getString("id_usuario"));
        usuario.setNombre_completo(rs.getString("nombre_completo"));
        usuario.setSaldo_horas(rs.getFloat("saldo_horas"));

        oferta.setCodigo_oferta(rs.getString("codigo_oferta"));
        oferta.setFecha_inicio(rs.getObject("fecha_inicio", LocalDate.class));
        oferta.setFecha_fin(rs.getObject("fecha_fin", LocalDate.class));
        oferta.setUsuario(usuario);
        oferta.setTipo(rs.getBoolean("tipo"));
        oferta.setNombre_habilidad(rs.getString("nombre_habilidad"));
        oferta.setNivel_habilidad(rs.getInt("nivel_habilidad"));
        oferta.setDescripcion(rs.getString("descripcion"));

        return oferta;
    }
}
