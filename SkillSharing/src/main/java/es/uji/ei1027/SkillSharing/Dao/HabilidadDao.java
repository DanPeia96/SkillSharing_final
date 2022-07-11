package es.uji.ei1027.SkillSharing.Dao;


import es.uji.ei1027.SkillSharing.Model.Habilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HabilidadDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* INSERT*/
    public void addHabilidad(Habilidad habilidad) {
        jdbcTemplate.update("INSERT INTO habilidad VALUES (?, ?, ?)",
                habilidad.getNombre(), habilidad.getNivel(), habilidad.getDescripcion());
    }

    /* UPDATE */
    public void updateHabilidad(Habilidad habilidad) {
        jdbcTemplate.update("UPDATE habilidad SET descripcion=? WHERE nombre=? AND nivel=?",
                habilidad.getDescripcion(), habilidad.getNombre(), habilidad.getNivel());
    }

    public Habilidad activateHabilidad(String nombre, int nivel) {
        try {
            jdbcTemplate.update("UPDATE habilidad SET disponible=TRUE WHERE nombre=? AND nivel=?",
                    nombre, nivel);
            return jdbcTemplate.queryForObject("SELECT * FROM habilidad WHERE nombre=? AND nivel=? order by nombre",
                    new HabilidadRowMapper(), nombre, nivel);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Habilidad desactivateHabilidad(String nombre, int nivel) {
        try {
            jdbcTemplate.update("UPDATE habilidad SET disponible=FALSE WHERE nombre=? AND nivel=?",
                    nombre, nivel);
            return jdbcTemplate.queryForObject("SELECT * FROM habilidad WHERE nombre=? AND nivel=? order by nombre",
                    new HabilidadRowMapper(), nombre, nivel);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* SELECT Habilidad */
    public Habilidad getHabilidad(String nombre, int nivel) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM habilidad WHERE nombre=? AND nivel=?",
                    new HabilidadRowMapper(), nombre, nivel);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Habilidad getHabilidadPorNombre(String nombre) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM habilidad WHERE nombre=?",
                    new HabilidadRowMapper(), nombre);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Habilidad getHabilidadPorNivel(int nivel) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM habilidad WHERE nivel=?",
                    new HabilidadRowMapper(), nivel);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* SELECT lista Habilidad */
    public List<Habilidad> getHabilidades() {
        try {
            return jdbcTemplate.query("SELECT * FROM habilidad order by nombre, nivel", new HabilidadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Habilidad> getHabilidadesActivas() {
        try {
            return jdbcTemplate.query("SELECT * FROM habilidad WHERE disponible=TRUE order by nombre, nivel", new HabilidadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
