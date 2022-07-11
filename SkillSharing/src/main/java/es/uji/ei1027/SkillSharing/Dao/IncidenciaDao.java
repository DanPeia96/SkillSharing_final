package es.uji.ei1027.SkillSharing.Dao;


import es.uji.ei1027.SkillSharing.Model.Incidencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class IncidenciaDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* INSERT*/
    public void addIncidencia(Incidencia incidencia) {
        jdbcTemplate.update("INSERT INTO incidencia VALUES (?, CURRENT_DATE, ?, ?)",
               incidencia.getUsuario().getUserId(), incidencia.getId_promotor(), incidencia.getDescripcion());
    }

    /* UPDATE */
    public void updateIncidencia(Incidencia incidencia) {
        jdbcTemplate.update("UPDATE incidencia SET id_alumno=?, fecha=?,id_promotor=?, descripcion=? " +
                "WHERE id_alumno=? AND fecha=?",
                incidencia.getUsuario().getUserId(),incidencia.getFecha(), incidencia.getId_promotor(), incidencia.getDescripcion(),
                incidencia.getUsuario().getUserId(), incidencia.getFecha());
    }

    /* SELECT Incidencia */
    public Incidencia getIncidencia(String codigoEstudiante, Date fecha) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM incidencia as i join usuario as u on i.id_alumno=u.id_usuario " +
                                                    "WHERE i.id_alumno=? AND i.fecha=?",
                    new IncidenciaRowMapper(), codigoEstudiante, fecha);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* SELECT lista Incidencia */
    public List<Incidencia> getIncidencias() {
        try {
            return jdbcTemplate.query("SELECT * FROM incidencia as i join usuario as u on i.id_alumno=u.id_usuario " +
                                            "order BY fecha",
                    new IncidenciaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
