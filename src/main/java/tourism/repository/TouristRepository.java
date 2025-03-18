package tourism.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import tourism.model.*;
import tourism.rowMappers.TagsRowMapper;
import tourism.rowMappers.TouristAttractionRowMapper;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private List<OldTouristAttraction> attractions = new ArrayList<>();
    private JdbcTemplate jdbcTemplate;



    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate =new JdbcTemplate();
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
                "ON attractions.ByerID = byer.byerID" +
                "WHERE name = ?";
        List<TouristAttraction> listOfTouristAttractions = jdbcTemplate.query(sql, new TouristAttractionRowMapper(), name);
        if(listOfTouristAttractions.isEmpty()){
            return null;
        } else {
            return listOfTouristAttractions.getFirst();
        }
    }


    public void deleteTouristAttraction(String name){
        String sql = "DELETE FROM attractions WHERE attractionsID = ?";
        jdbcTemplate.update(sql, getTouristAttractionByName(name).getId());
    }

    public TouristAttraction updateTouristAttraction(TouristAttraction touristAttraction){
        String sql ="UPDATE attractions SET(name, beskrivelse, byerID) VALUES (?, ?, ?) WHERE attractions id=?";
        jdbcTemplate.update(sql, touristAttraction.getName(), touristAttraction.getDescription(), touristAttraction.getBy().getById(), touristAttraction.getId());
        return touristAttraction;

    }







}
