package es.uji.ei1027.SkillSharing.Dao;


import es.uji.ei1027.SkillSharing.Model.Solicitud;
import es.uji.ei1027.SkillSharing.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;


import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SolicitudDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private String crearId(){
        int cantidad = getCantidad()+1;
        int numeroCifras = Integer.toString(cantidad).length();
        return "s" + "0".repeat(Math.max(0, 5 - numeroCifras)) + cantidad;
    }

    private int getCantidad() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM solicitud",Integer.class);
    }

    /* INSERT*/
    public void addSolicitud(String idOferta, String idUsuario) {
        jdbcTemplate.update("INSERT INTO solicitud VALUES (?,?, ?, CURRENT_DATE, null)",
               crearId(), idOferta, idUsuario);
    }

    /* UPDATE: cambia la fecha de aceptación cuando se acepta una solicitud y le pone la actual*/
    public void aceptaOBorraSolicitud(String codigo_solicitud) {
        jdbcTemplate.update("UPDATE solicitud SET fecha_aceptacion=CURRENT_DATE WHERE codigo_solicitud=?",
                codigo_solicitud);
    }

    /* SELECT Solicitud */
    public Solicitud getSolicitud(String codigo_solicitud) {
        try {
            return jdbcTemplate.queryForObject("SELECT s.codigo_solicitud, s.codigo_oferta, o.fecha_inicio, o.fecha_fin, o.nivel_habilidad, " +
                            "o.nombre_habilidad, o.id_usuario, u2.nombre_completo as nombre2, u2.saldo_horas as horas2, s.id_usuario_solicitante, u1.nombre_completo, " +
                            "s.fecha_emision, s.fecha_aceptacion FROM solicitud as s join oferta as o " +
                            "using (codigo_oferta) join usuario as u1 on s.id_usuario_solicitante=u1.id_usuario " +
                            "join usuario as u2 on u2.id_usuario=o.id_usuario " +
                            "WHERE s.codigo_solicitud=?",
                    new SolicitudRowMapper(), codigo_solicitud);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Solicitud> getSolicitudesPedientes(String userId) {
        try {
            return jdbcTemplate.query("SELECT s.codigo_solicitud, s.codigo_oferta, o.fecha_inicio, o.fecha_fin, o.nivel_habilidad, " +
                            "o.nombre_habilidad, o.id_usuario, u2.nombre_completo as nombre2, u2.saldo_horas as horas2, s.id_usuario_solicitante, u1.nombre_completo, " +
                            "s.fecha_emision, s.fecha_aceptacion FROM solicitud as s join oferta as o " +
                            "using (codigo_oferta) join usuario as u1 on s.id_usuario_solicitante=u1.id_usuario " +
                            "join usuario as u2 on u2.id_usuario=o.id_usuario " +
                            "where s.fecha_aceptacion is null and (s.id_usuario_solicitante=? or o.id_usuario=?)",
                    new SolicitudRowMapper(), userId, userId);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
