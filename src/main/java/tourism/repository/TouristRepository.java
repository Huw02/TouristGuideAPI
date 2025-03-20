package tourism.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tourism.model.*;
import tourism.rowMappers.ByerRowMapper;
import tourism.rowMappers.TagsRowMapper;
import tourism.rowMappers.TouristAttractionRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TouristRepository {
    private JdbcTemplate jdbcTemplate;


    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;


    public TouristRepository() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
                System.getenv("PROD_DATABASE_URL"),
                System.getenv("PROD_USERNAME"),
                System.getenv("PROD_PASSWORD")
        );

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //NYE METODER, DISSE ER FORHÅBENTLIG BRUGBARE


    public List<TouristAttraction> getAllTouristAttractions() {
        String sql = "SELECT attractions.*, byer.byer " +
                "FROM attractions JOIN byer " +
                "ON attractions.ByerID = byer.byerID";
        return jdbcTemplate.query(sql, new TouristAttractionRowMapper());
    }


    public TouristAttraction getTouristAttractionById(int id) {
        String sql = "SELECT attractions.*, byer.byer " +
                "FROM attractions JOIN byer " +
                "ON attractions.ByerID = byer.byerID" +
                "WHERE attractionID = ?";
        List<TouristAttraction> listOfTouristAttractions = jdbcTemplate.query(sql, new TouristAttractionRowMapper(), id);
        if (listOfTouristAttractions.isEmpty()) {
            return null;
        } else {
            return listOfTouristAttractions.getFirst();
        }
    }


    public TouristAttraction getTouristAttractionByName(String name) {
        String sql = "SELECT attractions.*, byer.byer " +
                "FROM attractions JOIN byer " +
                "ON attractions.ByerID = byer.byerID " +
                "WHERE name = ?";
        List<TouristAttraction> listOfTouristAttractions = jdbcTemplate.query(sql, new TouristAttractionRowMapper(), name);
        if (listOfTouristAttractions.isEmpty()) {
            return null;
        }

        TouristAttraction touristAttraction = listOfTouristAttractions.getFirst();
        touristAttraction.setTags(getTagsForAttraction(touristAttraction.getId()));

        return touristAttraction;
    }


    public void deleteTouristAttraction(String name) {
        String tagsSql = " DELETE FROM attractions_tags WHERE attractionsID = ? ";
        jdbcTemplate.update(tagsSql, getTouristAttractionByName(name).getId());

        String sql = "DELETE FROM attractions WHERE attractionsID = ?";
        jdbcTemplate.update(sql, getTouristAttractionByName(name).getId());
    }


    public TouristAttraction updateTouristAttraction(TouristAttraction touristAttraction) {
        String deleteTags = "DELETE FROM attractions_tags where attractionsID = ?";
        jdbcTemplate.update(deleteTags, touristAttraction.getId());

        String insertTags = "INSERT INTO attractions_tags (attractionsID, tagsID) VALUES (?, ?)";
        for (Tags i : touristAttraction.getTags()) {
            jdbcTemplate.update(insertTags, touristAttraction.getId(), i.getTagId());
        }
        String sql = "UPDATE attractions SET name = ?, beskrivelse = ?, byerID = ? WHERE AttractionsID = ?";
        jdbcTemplate.update(sql, touristAttraction.getName(), touristAttraction.getDescription(), touristAttraction.getBy().getById(), touristAttraction.getId());
        return touristAttraction;
    }

    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) {
        String sql = "INSERT INTO attractions (name, beskrivelse, byerID) VALUES (?, ?, ?) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, touristAttraction.getName());
            ps.setString(2, touristAttraction.getDescription());
            ps.setInt(3, touristAttraction.getBy().getById());
            return ps;
        }, keyHolder);

        int touristId = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

        if (touristId != -1) {
            touristAttraction.setId(touristId);
        }

        String sqlTags = "INSERT INTO attractions_tags (attractionsID, tagsID) VALUES (?, ?)";
        for (Tags i : touristAttraction.getTags()) {
            jdbcTemplate.update(sqlTags, touristId, i.getTagId());
        }

        return touristAttraction;
    }


    //Byer repository

    public List<Byer> getAllbyer() {
        String sql = "SELECT * FROM byer";
        return jdbcTemplate.query(sql, new ByerRowMapper());
    }

    public Byer getByerById(int byId) {
        String sql = "SELECT * FROM byer WHERE ByerID = ?";
        List<Byer> listOfByer = jdbcTemplate.query(sql, new ByerRowMapper(), byId);
        if (listOfByer.isEmpty()) {
            return null;
        } else {
            return listOfByer.getFirst();
        }
    }

    public Byer getByWithName(String name) {
        String sql = "SELECT * FROM byer WHERE byerID = ?";
        int temp = getById(name);
        jdbcTemplate.query(sql, new ByerRowMapper(), temp);
        return null;
    }


    public int getById(String name) {
        String sql = "SELECT ByerId from byer where byer = ?";
        List<Byer> listOfByer = jdbcTemplate.query(sql, new ByerRowMapper(), name);
        if (listOfByer.isEmpty()) {
            return 0;
        } else {
            return listOfByer.getFirst().getById();
        }
    }


    public void deleteBy(int byId) {
        String sql = "DELETE FROM byer WHERE ByerId = ?";
        jdbcTemplate.update(sql, byId);
    }

    public Byer insertBy(Byer by) {
        String sql = "INSERT INTO byer(byer) VALUES(?)";
        jdbcTemplate.update(sql, by.getByName());
        return by;
    }

    public Byer updateBy(Byer by) {
        String sql = "UPDATE byer SET (byer, byerId) VALUES(?, ?)";
        jdbcTemplate.update(sql, by.getByName(), by.getById());
        return by;
    }


    //Tags repository

    public List<Tags> getAllTags() {
        String sql = "SELECT * FROM tags";
        return jdbcTemplate.query(sql, new TagsRowMapper());
    }

    public Tags getTagById(int tagId) {
        String sql = "SELECT * FROM tags WHERE tagsID = ?";
        List<Tags> listOfTags = jdbcTemplate.query(sql, new TagsRowMapper(), tagId);
        if (listOfTags.isEmpty()) {
            return null;
        } else {
            return listOfTags.getFirst();
        }
    }

    public void deleteTag(int tagId) {
        String sql = "DELETE FROM tags WHERE tagsID = ?";
        jdbcTemplate.update(sql, tagId);
    }


    public Tags insertTag(Tags tag) {
        String sql = "INSERT INTO tags(tags) VALUES(?)";
        jdbcTemplate.update(sql, tag.getTagName());
        return tag;
    }


    public Tags updateTag(Tags tag) {
        String sql = "UPDATE tags SET(tagsID, Tags) VALUES (?, ?)";
        jdbcTemplate.update(sql, tag.getTagId(), tag.getTagName());
        return tag;
    }

    public List<Tags> getTagsForAttraction(int id) {
        String sql = "SELECT tags.TagsID, tags.tags from attractions JOIN attractions_tags ON attractions.AttractionsID = attractions_tags.AttractionsID JOIN tags on tags.tagsid = attractions_tags.tagsid WHERE attractions.AttractionsID = ?";
        List<Tags> listOfTags = jdbcTemplate.query(sql, new TagsRowMapper(), id);
        return listOfTags;
    }


}
