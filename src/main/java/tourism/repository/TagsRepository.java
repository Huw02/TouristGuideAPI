package tourism.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import tourism.model.Tags;
import tourism.rowMappers.TagsRowMapper;

import java.util.List;

@Repository
public class TagsRepository {
    private JdbcTemplate jdbcTemplate;



    public TagsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate =new JdbcTemplate();
    }


    public List<Tags> getAllTags(){
        String sql = "SELECT * FROM tags";
        return jdbcTemplate.query(sql, new TagsRowMapper());
    }


    public Tags getTagById(int tagId){
        String sql = "SELECT * FROM tags WHERE tagsID = ?";
        List<Tags> listOfTags = jdbcTemplate.query(sql, new TagsRowMapper(), tagId);
        if(listOfTags.isEmpty()){
            return null;
        } else {
            return listOfTags.getFirst();
        }
    }

    public void deleteTag(int tagId){
        String sql = "DELETE FROM tags WHERE tagsID = ?";
        jdbcTemplate.update(sql, tagId);
    }


    public Tags insertTag(Tags tag){
        String sql = "INSERT INTO tags(tags) VALUES(?)";
        jdbcTemplate.update(sql, tag.getTagName());
        return tag;
    }


    public Tags updateTag(Tags tag){
        String sql = "UPDATE tags SET(tagsID, Tags) VALUES (?, ?)";
        jdbcTemplate.update(sql, tag.getTagId(), tag.getTagName());
        return tag;
    }

    public List<Tags> getTagsForAttraction(int id){
        String sql= "SELECT tags.TagsID, tags.tags from attractions" +
                "JOIN attractions_tags ON attractions.AttractionsID = attractions_tags.AttractionsID" +
                "JOIN tags on tags.tagsid = attractions_tags.tagsid" +
                "WHERE attractions.AttractionsID = ?";
        List<Tags> listOfTags = jdbcTemplate.query(sql, new TagsRowMapper(), id);
        return listOfTags;
    }


}
