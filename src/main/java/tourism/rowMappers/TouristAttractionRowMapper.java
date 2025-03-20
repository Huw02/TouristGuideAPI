package tourism.rowMappers;

import org.springframework.jdbc.core.RowMapper;
import tourism.model.Byer;
import tourism.model.Tags;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TouristAttractionRowMapper implements RowMapper<TouristAttraction> {



    @Override
    public TouristAttraction mapRow(ResultSet rs, int rowNum) throws SQLException {

        TouristAttraction touristAttraction = new TouristAttraction();
        touristAttraction.setId(rs.getInt("attractionsID"));
        touristAttraction.setName(rs.getString("name"));
        touristAttraction.setDescription(rs.getString("beskrivelse"));


        Byer by = new Byer(rs.getString("byer"), rs.getInt("byerID"));

        touristAttraction.setBy(by);



        return touristAttraction;
    }
}
