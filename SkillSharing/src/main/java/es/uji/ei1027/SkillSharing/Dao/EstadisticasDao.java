package es.uji.ei1027.SkillSharing.Dao;

import es.uji.ei1027.SkillSharing.Model.Estadistica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EstadisticasDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){ jdbcTemplate= new JdbcTemplate(dataSource);}

    public List<Estadistica> estudiantesConMasColaboraciones(){
        try{
            return jdbcTemplate.query("select o.id_usuario, u.nombre_completo, count(*) as count from colaboracion as c join solicitud as s using(codigo_solicitud) join oferta as o using(codigo_oferta) join usuario as u using(id_usuario) group by o.id_usuario, u.nombre_completo order by count desc fetch first 3 rows only",new EstadisticasRowMapper());
        } catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    public List<Estadistica> estudiantesConMasOfertas(){
        try{
            return jdbcTemplate.query("select o.id_usuario, u.nombre_completo, count(*) as count from oferta as o join usuario as u using(id_usuario) group by id_usuario, u.nombre_completo order by count desc fetch first 3 rows only",new EstadisticasRowMapper());
        } catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    public List<Estadistica> estudiantesConMasIncidencias(){
        try{
            return jdbcTemplate.query("select i.id_alumno, u.nombre_completo, count(*) as count from incidencia as i join usuario as u on i.id_alumno=u.id_usuario group by id_alumno, u.nombre_completo order by count desc fetch first 3 rows only",new EstadisticasRowMapper());
        } catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}
