package tourism.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import tourism.DTO.DTOTouristAttraction;
import tourism.model.*;
import tourism.rowMappers.ByerRowMapper;
import tourism.rowMappers.TagsRowMapper;
import tourism.rowMappers.TouristAttractionRowMapper;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private List<OldTouristAttraction> attractions = new ArrayList<>();
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

    public List<OldTouristAttraction> getAttractions() {
        return attractions;
    }

    public List<OldTags> getTags(String name) {
        for (OldTouristAttraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                return attraction.getTags();
            }
        }
        return null;
    }

    public void addStarterAttractions() {
        attractions.add((new OldTouristAttraction("Tivoli", "Tivoli er den suverænt mest besøgte turistattraktion i Danmark med 2,3 mio. besøgende i 2021. Parken er Europas fjerdemest besøgte forlystelsespark", List.of(OldTags.FORLYSTELSESPARK, OldTags.UNDERHOLDNING, OldTags.KONCERT, OldTags.KULTUR, OldTags.RESTAURANT), OldByer.KØBENHAVN)));
        attractions.add((new OldTouristAttraction("DR Byen", "DR Byen er hovedkvarteret for Danmarks Radio (DR) og et imponerende mediehus i København. Bygningen består af fire segmenter, der huser DR's tv- og radioproduktion, nyheder og koncerthuset. DR Koncerthuset, designet af arkitekten Jean Nouvel, er en af Europas mest anerkendte koncertsale med fantastisk akustik og et futuristisk udseende.", List.of(OldTags.GRATIS, OldTags.KONCERT, OldTags.UNDERHOLDNING), OldByer.KØBENHAVN)));
        attractions.add((new OldTouristAttraction("Den Lille Havfrue", "Inspireret af H.C. Andersens eventyr, denne lille, men berømte bronzestatue sidder på en sten ved Langelinie. Selvom den ofte kaldes skuffende lille af turister, er den stadig et must-see og en af Københavns mest kendte vartegn.", List.of(OldTags.KUNST, OldTags.KULTUR, OldTags.GRATIS), OldByer.KØBENHAVN)));
        attractions.add((new OldTouristAttraction("Nyhavn", "Den ikoniske havnepromenade med farverige bygninger, hyggelige restauranter og gamle træskibe. Nyhavn var engang hjem for forfatteren H.C. Andersen og er i dag et af de mest fotograferede steder i København. Perfekt til en gåtur langs vandet eller en bådtur i kanalerne.", List.of(OldTags.GRATIS, OldTags.RESTAURANT, OldTags.BØRNEVENLIG), OldByer.KØBENHAVN)));
    }

    public OldTouristAttraction getAttractionsByName(String name) {
        for (OldTouristAttraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                return attraction;
            }
        }
        return null;
    }

    public void setAttractions(List<OldTouristAttraction> attractions) {
        this.attractions = attractions;
    }

    public OldTouristAttraction addAttractions(OldTouristAttraction touristAttraction) {
        attractions.add(touristAttraction);
        return touristAttraction;
    }


    public void updateAttraction(OldTouristAttraction nyAttraction) {
        for (OldTouristAttraction attraction : attractions) {
            if (attraction.getName().equals(nyAttraction.getName())) {
                attraction.setName(nyAttraction.getName());
                attraction.setBy(nyAttraction.getBy());
                attraction.setDescription(nyAttraction.getDescription());
                attraction.setTags(nyAttraction.getTags());
            }
        }
    }

    public OldTouristAttraction getAttractionByName(String name) {
        for (OldTouristAttraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                return attraction;
            }
        }
        return null;
    }

    public OldTouristAttraction removeAttraction(String name) {
        OldTouristAttraction tempAttraction = null;
        for (OldTouristAttraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                tempAttraction = attraction;
                attractions.remove(attraction);
                return tempAttraction;
            }
        }
        return tempAttraction;
    }


    //metoder til at hente og indsætte data i databasen


    public List<OldTouristAttraction> getAttractionsDatabase(){
        List<OldTouristAttraction> listOfAttractions = new ArrayList<>();


        SqlRowSet rowSet =jdbcTemplate.queryForRowSet("SELECT * FROM attractions");

        while(rowSet.next()){
            List<OldTags> listOfTags = new ArrayList<>();
            int attractionsID =rowSet.getInt("attractionsID");
            String name = rowSet.getString("name");
            String description = rowSet.getString("beskrivelse");
            String byerID = rowSet.getString("ByerID");


            String by = "" + jdbcTemplate.queryForRowSet("SELECT Byer FROM byer WHERE ByerID VALUES (?)", byerID);
            OldByer byName = OldByer.valueOf(by);

            SqlRowSet attractions_tags =jdbcTemplate.queryForRowSet("SELECT tagsID FROM attractions_tags WHERE AttractionsID VALUES(?)", attractionsID);
            while(attractions_tags.next()){
                int tagsID = attractions_tags.getInt("TagsID");
                SqlRowSet tags =jdbcTemplate.queryForRowSet("SELECT tags FROM tags WHERE tagsID VALUES(?)", tagsID);
                while(tags.next()){
                    String tagsName = tags.getString("tags");
                    OldTags oldTagsEnumName = OldTags.valueOf(tagsName);
                    listOfTags.add(oldTagsEnumName);
                }

            }

            listOfAttractions.add(new OldTouristAttraction(name, description, listOfTags, byName));

        }

        return listOfAttractions;
    }

    public void insertAttraction(String name, String description, OldByer by){
        String sql ="INSERT INTO attraction (name, description, by) VALUES ? ? ?";
        jdbcTemplate.update(sql, name, description, by);

    }

    public void deleteAttraction(int attractionId){
        String sql = "DELETE FROM attraction WHERE attractionId = ?";
        jdbcTemplate.update(sql, attractionId);
    }


    public void updateAttraction(String name, String description, OldByer by, int attractionId){
        String sql = "UPDATE attraction SET name = ?, description = ?, by = ? WHERE attractionId = ?";
        jdbcTemplate.update(sql, name, description, by, attractionId);
    }








    //NYE METODER, DISSE ER FORHÅBENTLIG BRUGBARE


    public List<TouristAttraction> getAllTouristAttractions(){
        String sql = "SELECT attractions.*, byer.byer " +
                "FROM attractions JOIN byer " +
                "ON attractions.ByerID = byer.byerID";
        return jdbcTemplate.query(sql, new TouristAttractionRowMapper());
    }


    public TouristAttraction getTouristAttractionById(int id){
        String sql ="SELECT attractions.*, byer.byer " +
                "FROM attractions JOIN byer " +
                "ON attractions.ByerID = byer.byerID" +
                "WHERE attractionID = ?";
        List<TouristAttraction> listOfTouristAttractions = jdbcTemplate.query(sql, new TouristAttractionRowMapper(), id);
        if(listOfTouristAttractions.isEmpty()){
            return null;
        } else {
            return listOfTouristAttractions.getFirst();
        }
    }


    public TouristAttraction getTouristAttractionByName(String name){
        String sql ="SELECT attractions.*, byer.byer " +
                "FROM attractions JOIN byer " +
                "ON attractions.ByerID = byer.byerID " +
                "WHERE name = ?";
        List<TouristAttraction> listOfTouristAttractions = jdbcTemplate.query(sql, new TouristAttractionRowMapper(), name);
        if(listOfTouristAttractions.isEmpty()){
            return null;
        }

        TouristAttraction touristAttraction = listOfTouristAttractions.getFirst();
        touristAttraction.setTags(getTagsForAttraction(touristAttraction.getId()));

        return touristAttraction;
    }



    /*
    public DTOTouristAttraction getDTOTouristAttractionByName(String name){
        String sql ="SELECT attractions.*, byer.byer " +
                "FROM attractions JOIN byer " +
                "ON attractions.ByerID = byer.byerID " +
                "WHERE name = ?";
        List<TouristAttraction> listOfTouristAttractions = jdbcTemplate.query(sql, new TouristAttractionRowMapper(), name);
        List<DTOTouristAttraction>tempDTO = new ArrayList<>();
        for(TouristAttraction i: listOfTouristAttractions){
            tempDTO.add(new DTOTouristAttraction(i.getName(), i.getDescription(), i.getBy().getByName(), ));

        }
        if(tempDTO.isEmpty()){
            return null;
        } else {
            return tempDTO.getFirst();
        }
    } */


    public void deleteTouristAttraction(String name){
        String sql = "DELETE FROM attractions WHERE attractionsID = ?";
        jdbcTemplate.update(sql, getTouristAttractionByName(name).getId());
    }

    public TouristAttraction updateTouristAttraction(TouristAttraction touristAttraction){
        String deleteTags = "DELETE FROM attractions_tags where attractionsID = ?";
        jdbcTemplate.update(deleteTags, touristAttraction.getId());

        String insertTags = "INSERT INTO attractions_tags (attractionsID, tagsID) VALUES (?, ?)";
        for(Tags i: touristAttraction.getTags()){
            jdbcTemplate.update(insertTags, touristAttraction.getId(), i.getTagId());
        }
        String sql = "UPDATE attractions SET name = ?, beskrivelse = ?, byerID = ? WHERE AttractionsID = ?";
        jdbcTemplate.update(sql, touristAttraction.getName(), touristAttraction.getDescription(), touristAttraction.getBy().getById(), touristAttraction.getId());
        return touristAttraction;
    }




    public DTOTouristAttraction updateDTOAttraction(DTOTouristAttraction dtoTouristAttraction){
        String sql ="UPDATE attractions SET(name, beskrivelse, byerID) VALUES (?, ?, ?) WHERE attractions id=?";
        int tempById = getById(dtoTouristAttraction.getName());
        jdbcTemplate.update(sql, dtoTouristAttraction.getName(), dtoTouristAttraction.getDescription(), tempById);
        return dtoTouristAttraction;
    }







    //Byer repository

    public List<Byer> getAllbyer(){
        String sql = "SELECT * FROM byer";
        return jdbcTemplate.query(sql, new ByerRowMapper());
    }

    public Byer getByerById(int byId){
        String sql ="SELECT * FROM byer WHERE ByerID = ?";
        List<Byer> listOfByer = jdbcTemplate.query(sql, new ByerRowMapper(), byId);
        if(listOfByer.isEmpty()){
            return null;
        } else {
            return listOfByer.getFirst();
        }
    }

    public int getById(String name){
        String sql = "SELECT ByerId from byer where byer = ?";
        List<Byer> listOfByer = jdbcTemplate.query(sql, new ByerRowMapper(), name);
        if(listOfByer.isEmpty()){
            return 0;
        } else {
            return listOfByer.getFirst().getById();
        }
    }


    public void deleteBy(int byId){
        String sql = "DELETE FROM byer WHERE ByerId = ?";
        jdbcTemplate.update(sql, byId);
    }

    public Byer insertBy(Byer by){
        String sql ="INSERT INTO byer(byer) VALUES(?)";
        jdbcTemplate.update(sql, by.getByName());
        return by;
    }

    public Byer updateBy(Byer by){
        String sql = "UPDATE byer SET (byer, byerId) VALUES(?, ?)";
        jdbcTemplate.update(sql, by.getByName(), by.getById());
        return by;
    }








    //Tags repository

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
        String sql= "SELECT tags.TagsID, tags.tags from attractions JOIN attractions_tags ON attractions.AttractionsID = attractions_tags.AttractionsID JOIN tags on tags.tagsid = attractions_tags.tagsid WHERE attractions.AttractionsID = ?";
        List<Tags> listOfTags = jdbcTemplate.query(sql, new TagsRowMapper(), id);
        return listOfTags;
    }


    public List<Tags> getTagsWithOldTags(List<OldTags> oldTags){
        List<String> tempList = new ArrayList<>();
        for(OldTags i: oldTags){
            tempList.add(i.getDisplayName());
        }
        String sql = "SELECT * FROM tags WHERE tags = ?";
        List<Tags> listOfTags = jdbcTemplate.query(sql, new TagsRowMapper(), tempList);
        return listOfTags;
    }



}
