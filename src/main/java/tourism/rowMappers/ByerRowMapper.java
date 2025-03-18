package tourism.rowMappers;

import org.springframework.jdbc.core.RowMapper;
import tourism.model.Byer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ByerRowMapper implements RowMapper<Byer> {

    @Override
    public Byer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Byer(rs.getString("byer"), rs.getInt("byerID"));
    }
}
