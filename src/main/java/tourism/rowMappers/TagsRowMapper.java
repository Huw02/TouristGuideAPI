package tourism.rowMappers;

import org.springframework.jdbc.core.RowMapper;
import tourism.model.Tags;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagsRowMapper implements RowMapper<Tags> {
    @Override
    public Tags mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Tags(rs.getString("tags"), rs.getInt("tagsId"));
    }
}
