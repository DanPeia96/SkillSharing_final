package es.uji.ei1027.SkillSharing.Dao;

import es.uji.ei1027.SkillSharing.Model.Estadistica;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EstadisticasRowMapper implements RowMapper<Estadistica> {
    @Override
    public Estadistica mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Estadistica(rs.getString(1), rs.getString(2),rs.getInt(3));
    }
}
