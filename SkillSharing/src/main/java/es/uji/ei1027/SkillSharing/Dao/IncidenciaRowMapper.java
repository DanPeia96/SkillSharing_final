package es.uji.ei1027.SkillSharing.Dao;

import es.uji.ei1027.SkillSharing.Model.Incidencia;
import es.uji.ei1027.SkillSharing.Model.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class IncidenciaRowMapper implements RowMapper<Incidencia> {
    public Incidencia mapRow(ResultSet rs, int rowNum) throws SQLException {
        Incidencia incidencia = new Incidencia();
        Usuario usuario=new Usuario();
        usuario.setUserId(rs.getString("id_alumno"));
        usuario.setNombre_completo(rs.getString("nombre_completo"));
        incidencia.setUsuario(usuario);
        incidencia.setFecha(rs.getDate("fecha"));
        incidencia.setId_promotor(rs.getString("id_promotor"));
        incidencia.setDescripcion(rs.getString("descripcion"));

        return incidencia;
    }
}
