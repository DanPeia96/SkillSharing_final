package es.uji.ei1027.SkillSharing.Dao;


import es.uji.ei1027.SkillSharing.Model.Oferta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
//import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;



import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OfertaDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private int getCantidad() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM oferta",Integer.class);
    }

    private String crearId(){
        int cantidad = getCantidad()+1;
        int numeroCifras = Integer.toString(cantidad).length();
        return "o" + "0".repeat(Math.max(0, 5 - numeroCifras)) + cantidad;
    }

    /* INSERT*/
    public void addOferta(Oferta oferta) {
        jdbcTemplate.update("INSERT INTO oferta VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                crearId(), oferta.getFecha_inicio(), oferta.getFecha_fin(), oferta.getUsuario().getUserId(), oferta.getTipo(),
                oferta.getNombre_habilidad(), oferta.getNivel_habilidad(), oferta.getDescripcion());
    }



    /* UPDATE */
    public void updateOferta(Oferta oferta) {
        jdbcTemplate.update("UPDATE oferta SET fecha_inicio=?, fecha_fin=?, id_usuario=?, tipo=?, " +
                "nombre_habilidad=?, nivel_habilidad=?, descripcion=? WHERE codigo_oferta=?",
                oferta.getFecha_inicio(), oferta.getFecha_fin(), oferta.getUsuario().getUserId(), oferta.getTipo(),
                oferta.getNombre_habilidad(), oferta.getNivel_habilidad(), oferta.getDescripcion(), oferta.getCodigo_oferta());
    }

    /* SELECT Oferta */
    public Oferta getOferta(String nombreOferta) {
        try {
            return jdbcTemplate.queryForObject("SELECT o.*, u.nombre_completo, u.saldo_horas FROM oferta as o join usuario as u using (id_usuario)" +
                            "WHERE o.codigo_oferta=?",
                    new OfertaRowMapper(), nombreOferta);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Oferta> getOfertasByUser(String userId) {
        try {
            return jdbcTemplate.query("SELECT o.*, u.nombre_completo, u.saldo_horas FROM oferta as o join usuario as u using (id_usuario)" +
                            "WHERE o.id_usuario=? and o.fecha_fin>CURRENT_DATE",
                    new OfertaRowMapper(), userId);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Oferta> getOfertasByUser2(String userId) {
        try {
            return jdbcTemplate.query("SELECT o.*, u.nombre_completo, u.saldo_horas FROM oferta as o join usuario as u using (id_usuario)" +
                            "WHERE o.tipo=true and o.id_usuario=? and o.fecha_fin>CURRENT_DATE",
                    new OfertaRowMapper(), userId);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Oferta> getDemandasByUser(String userId) {
        try {
            return jdbcTemplate.query("SELECT o.*, u.nombre_completo, u.saldo_horas FROM oferta as o join usuario as u using (id_usuario)" +
                            "WHERE o.tipo=false and o.id_usuario=? and o.fecha_fin>CURRENT_DATE",
                    new OfertaRowMapper(), userId);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Oferta> getOfertasByHabilidad(String nombre, int nivel, boolean tipo) {
        try {
            return jdbcTemplate.query("SELECT o.*, u.nombre_completo, u.saldo_horas FROM oferta as o join usuario as u using (id_usuario)" +
                            "WHERE o.nombre_habilidad=? and o.nivel_habilidad=? and tipo=? and o.fecha_fin>CURRENT_DATE",
                    new OfertaRowMapper(), nombre,nivel, !tipo);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* DELETE */
    public void deleteOferta(String id) {
        jdbcTemplate.update("UPDATE oferta SET fecha_inicio=CURRENT_DATE, fecha_fin=CURRENT_DATE WHERE codigo_oferta=?", id);
    }

    /* SELECT lista Oferta */
    public List<Oferta> getOfertas() {
        try {
            return jdbcTemplate.query("SELECT o.*, u.nombre_completo, u.saldo_horas FROM oferta as o join usuario as u using (id_usuario)" +
                    "where o.fecha_fin>CURRENT_DATE and ((o.tipo=false and u.saldo_horas>=-20) or o.tipo=true)", new OfertaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Oferta> getOfertas2() {
        try {
            return jdbcTemplate.query("SELECT o.*, u.nombre_completo, u.saldo_horas FROM oferta as o join usuario as u using (id_usuario)" +
                    "where o.tipo=true and o.fecha_fin>CURRENT_DATE", new OfertaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Oferta> getDemandas() {
        try {
            return jdbcTemplate.query("SELECT o.*, u.nombre_completo, u.saldo_horas FROM oferta as o join usuario as u using (id_usuario)" +
                    "where o.tipo=false and o.fecha_fin>CURRENT_DATE and u.saldo_horas>=-20", new OfertaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
