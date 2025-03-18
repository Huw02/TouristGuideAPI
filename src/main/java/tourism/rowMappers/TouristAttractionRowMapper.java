package tourism.rowMappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import tourism.model.Byer;
import tourism.model.TouristAttraction;
import tourism.repository.TagsRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TouristAttractionRowMapper implements RowMapper<TouristAttraction> {

    private TagsRepository tagsRepository;

    @Override
    public TouristAttraction mapRow(ResultSet rs, int rowNum) throws SQLException {

        TouristAttraction touristAttraction = new TouristAttraction();
        touristAttraction.setId(rs.getInt("attractionsID"));
        touristAttraction.setName(rs.getString("name"));
        touristAttraction.setDescription(rs.getString("beskrivelse"));


        Byer by = new Byer(rs.getString("byer"), rs.getInt("byerID"));

        touristAttraction.setBy(by);

        touristAttraction.setTags(tagsRepository.getTagsForAttraction(rs.getInt(touristAttraction.getId())));
        return touristAttraction;
    }
}
