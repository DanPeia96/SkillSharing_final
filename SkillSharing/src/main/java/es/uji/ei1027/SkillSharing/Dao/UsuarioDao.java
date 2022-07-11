package es.uji.ei1027.SkillSharing.Dao;

import es.uji.ei1027.SkillSharing.Model.Habilidad;
import es.uji.ei1027.SkillSharing.Model.Usuario;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Usuario comprobarPassword(String username, String password) {
        try {
            BasicPasswordEncryptor passwordEncriptor = new BasicPasswordEncryptor();

            Usuario usuario = jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE id_usuario=?",
                    new UsuarioRowMapper(), username);
            if (usuario != null  && passwordEncriptor.checkPassword(password, usuario.getPassword())) return usuario;
            return null;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Usuario getTipo(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE id_usuario=?",
                    new UsuarioRowMapper(), username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void setPassword(String userId, String password) {
        jdbcTemplate.update("UPDATE usuario set password =? WHERE id_usuario=?", password,userId);
    }

    public Usuario getUsuarioPorID(String id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE id_usuario=?",
                    new UsuarioRowMapper(), id);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Usuario getUsuarioPorNombre(String nombre) {
        try {
            nombre=nombre.toUpperCase();
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE nombre=?",
                    new UsuarioRowMapper(), nombre);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    //UPDATE
    public Usuario activateUsuario(String id) {
        try {
            jdbcTemplate.update("UPDATE usuario SET activado=TRUE WHERE id_usuario=?", id);
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE id_usuario=?",
                    new UsuarioRowMapper(), id);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Usuario desactivateUsuario(String id) {
        try {
            jdbcTemplate.update("UPDATE usuario SET activado=FALSE WHERE id_usuario=?", id);
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE id_usuario=?",
                    new UsuarioRowMapper(), id);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* SELECT lista Usuarios */
    public List<Usuario> getUsuarios() {
        try {
            return jdbcTemplate.query("SELECT * FROM usuario WHERE admin=FALSE", new UsuarioRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Usuario>();
        }
    }

    public void setSaldo(float saldo, String id){
        jdbcTemplate.update("UPDATE usuario SET saldo_horas=? WHERE id_usuario=?",saldo,id);
    }
}