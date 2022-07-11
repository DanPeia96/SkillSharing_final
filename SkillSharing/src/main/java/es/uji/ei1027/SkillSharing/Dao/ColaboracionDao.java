package es.uji.ei1027.SkillSharing.Dao;

import es.uji.ei1027.SkillSharing.Model.Colaboracion;
import es.uji.ei1027.SkillSharing.Model.Oferta;
import es.uji.ei1027.SkillSharing.Model.Solicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ColaboracionDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private String crearId(){
        int cantidad = getCantidad()+1;
        int numeroCifras = Integer.toString(cantidad).length();
        return "c" + "0".repeat(Math.max(0, 5 - numeroCifras)) + cantidad;
    }

    private int getCantidad() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM colaboracion",Integer.class);
    }

    /* INSERT*/
    public void addColaboracion(Solicitud solicitud) {
        jdbcTemplate.update("INSERT INTO colaboracion VALUES (?, ?, ?, ?, 0, 0,true)",
                crearId(),
                solicitud.getCodigo_solicitud(),
                solicitud.getOferta().getFecha_inicio(),
                solicitud.getOferta().getFecha_fin());
    }

    /* UPDATE */
    public void updateColaboracion(Colaboracion colaboracion) {
        jdbcTemplate.update("UPDATE colaboracion SET horas=?, evaluacion=?" +
                " WHERE codigo_colaboracion=?",
                colaboracion.getHoras(), colaboracion.getEvaluacion(),
                colaboracion.getCodigo_colaboracion());
    }

    public void cancelarColaboracion(String codigo_colaboracion) {
        jdbcTemplate.update("UPDATE colaboracion SET estado=FALSE, fecha_fin=CURRENT_DATE WHERE codigo_colaboracion=?",
                    codigo_colaboracion);
    }
    /* SELECT Colaboracion */
    public Colaboracion getColaboracion(String codColaboracion) {
        try {
            return jdbcTemplate.queryForObject("SELECT c.codigo_colaboracion, c.horas, c.evaluacion, c.estado, c.codigo_solicitud," +
                            "                       s.codigo_oferta, s.id_usuario_solicitante, " +
                            "                       u1.nombre_completo as nombre1, u1.saldo_horas as horas1, " +
                            "                       o.fecha_inicio, o.fecha_fin, o.nivel_habilidad, o.nombre_habilidad, o.tipo, o.id_usuario, " +
                            "                       u2.nombre_completo as nombre2, u2.saldo_horas as horas2," +
                            "                       s.fecha_emision, s.fecha_aceptacion " +
                            "from colaboracion as c " +
                            "  join solicitud as s using(codigo_solicitud) " +
                            "  join usuario as u1 on u1.id_usuario=s.id_usuario_solicitante" +
                            "  join oferta as o on o.codigo_oferta=s.codigo_oferta " +
                            "  join usuario as u2 on o.id_usuario=u2.id_usuario "+
                            "WHERE codigo_colaboracion=?",
                    new ColaboracionRowMapper(), codColaboracion);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* SELECT lista Colaboracion */
    public List<Colaboracion> getMisColaboraciones(String idUsuario) {
        try {
            return jdbcTemplate.query("SELECT c.codigo_colaboracion, c.horas, c.evaluacion, c.estado, c.codigo_solicitud," +
                            "                       s.codigo_oferta, s.id_usuario_solicitante, " +
                            "                       u1.nombre_completo as nombre1, u1.saldo_horas as horas1, " +
                            "                       o.fecha_inicio, o.fecha_fin, o.nivel_habilidad, o.nombre_habilidad, o.tipo, o.id_usuario, " +
                            "                       u2.nombre_completo as nombre2, u2.saldo_horas as horas2," +
                            "                       s.fecha_emision, s.fecha_aceptacion " +
                            "from colaboracion as c " +
                            "  join solicitud as s using(codigo_solicitud) " +
                            "  join usuario as u1 on u1.id_usuario=s.id_usuario_solicitante" +
                            "  join oferta as o on o.codigo_oferta=s.codigo_oferta " +
                            "  join usuario as u2 on o.id_usuario=u2.id_usuario " +
                            "  where s.id_usuario_solicitante=? or o.id_usuario=?",
                    new ColaboracionRowMapper(),idUsuario,idUsuario);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Colaboracion>();
        }
    }

    public List<Colaboracion> getColaboraciones() {
        try {
            return jdbcTemplate.query("SELECT c.codigo_colaboracion, c.horas, c.evaluacion, c.estado, c.codigo_solicitud," +
                            "                       s.codigo_oferta, s.id_usuario_solicitante, " +
                            "                       u1.nombre_completo as nombre1, u1.saldo_horas as horas1, " +
                            "                       o.fecha_inicio, o.fecha_fin, o.nivel_habilidad, o.nombre_habilidad, o.tipo, o.id_usuario, " +
                            "                       u2.nombre_completo as nombre2, u2.saldo_horas as horas2," +
                            "                       s.fecha_emision, s.fecha_aceptacion " +
                            "from colaboracion as c " +
                            "  join solicitud as s using(codigo_solicitud) " +
                            "  join usuario as u1 on u1.id_usuario=s.id_usuario_solicitante" +
                            "  join oferta as o on o.codigo_oferta=s.codigo_oferta " +
                            "  join usuario as u2 on o.id_usuario=u2.id_usuario ",
                    new ColaboracionRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Colaboracion>();
        }
    }
}
