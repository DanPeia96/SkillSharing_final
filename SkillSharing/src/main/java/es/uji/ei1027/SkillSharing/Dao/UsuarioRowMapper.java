package es.uji.ei1027.SkillSharing.Dao;

import es.uji.ei1027.SkillSharing.Model.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRowMapper implements RowMapper<Usuario> {
    public Usuario mapRow(ResultSet rs, int rowNum)throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setUserId(rs.getString("id_usuario"));
        usuario.setNombre_completo(rs.getString("nombre_completo"));
        usuario.setPassword(rs.getString("password"));
        usuario.setEmail(rs.getString("email"));
        usuario.setActivado(rs.getBoolean("activado"));
        usuario.setSaldo_horas(rs.getFloat("saldo_horas"));
        usuario.setAdmin(rs.getBoolean("admin"));
        return usuario;
    }
}