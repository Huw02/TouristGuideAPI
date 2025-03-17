package tourism.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import tourism.model.Byer;
import tourism.model.Tags;
import tourism.model.TouristAttraction;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private List<TouristAttraction> attractions = new ArrayList<>();
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    public TouristRepository() {


        DriverManagerDataSource dataSource = new DriverManagerDataSource(
                System.getenv(dbUrl),
                System.getenv(username),
                System.getenv(password)
        );
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        this.jdbcTemplate =new JdbcTemplate(dataSource);
    }

    public List<TouristAttraction> getAttractions() {
        return attractions;
    }

    public List<Tags> getTags(String name) {
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                return attraction.getTags();
            }
        }
        return null;
    }

    public void addStarterAttractions() {
        attractions.add((new TouristAttraction("Tivoli", "Tivoli er den suverænt mest besøgte turistattraktion i Danmark med 2,3 mio. besøgende i 2021. Parken er Europas fjerdemest besøgte forlystelsespark", List.of(Tags.FORLYSTELSESPARK, Tags.UNDERHOLDNING, Tags.KONCERT, Tags.KULTUR, Tags.RESTAURANT), Byer.KØBENHAVN)));
        attractions.add((new TouristAttraction("DR Byen", "DR Byen er hovedkvarteret for Danmarks Radio (DR) og et imponerende mediehus i København. Bygningen består af fire segmenter, der huser DR's tv- og radioproduktion, nyheder og koncerthuset. DR Koncerthuset, designet af arkitekten Jean Nouvel, er en af Europas mest anerkendte koncertsale med fantastisk akustik og et futuristisk udseende.", List.of(Tags.GRATIS, Tags.KONCERT, Tags.UNDERHOLDNING), Byer.KØBENHAVN)));
        attractions.add((new TouristAttraction("Den Lille Havfrue", "Inspireret af H.C. Andersens eventyr, denne lille, men berømte bronzestatue sidder på en sten ved Langelinie. Selvom den ofte kaldes skuffende lille af turister, er den stadig et must-see og en af Københavns mest kendte vartegn.", List.of(Tags.KUNST, Tags.KULTUR, Tags.GRATIS), Byer.KØBENHAVN)));
        attractions.add((new TouristAttraction("Nyhavn", "Den ikoniske havnepromenade med farverige bygninger, hyggelige restauranter og gamle træskibe. Nyhavn var engang hjem for forfatteren H.C. Andersen og er i dag et af de mest fotograferede steder i København. Perfekt til en gåtur langs vandet eller en bådtur i kanalerne.", List.of(Tags.GRATIS, Tags.RESTAURANT, Tags.BØRNEVENLIG), Byer.KØBENHAVN)));
    }

    public TouristAttraction getAttractionsByName(String name) {
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                return attraction;
            }
        }
        return null;
    }

    public void setAttractions(List<TouristAttraction> attractions) {
        this.attractions = attractions;
    }

    public TouristAttraction addAttractions(TouristAttraction touristAttraction) {
        attractions.add(touristAttraction);
        return touristAttraction;
    }


    public void updateAttraction(TouristAttraction nyAttraction) {
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equals(nyAttraction.getName())) {
                attraction.setName(nyAttraction.getName());
                attraction.setBy(nyAttraction.getBy());
                attraction.setDescription(nyAttraction.getDescription());
                attraction.setTags(nyAttraction.getTags());
            }
        }
    }

    public TouristAttraction getAttractionByName(String name) {
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                return attraction;
            }
        }
        return null;
    }

    public TouristAttraction removeAttraction(String name) {
        TouristAttraction tempAttraction = null;
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                tempAttraction = attraction;
                attractions.remove(attraction);
                return tempAttraction;
            }
        }
        return tempAttraction;
    }


    //metoder til at hente og indsætte data i databasen


    public List<TouristAttraction> getAttractionsDatabase(){
        List<TouristAttraction> listOfAttractions = new ArrayList<>();


        SqlRowSet rowSet =jdbcTemplate.queryForRowSet("SELECT * FROM attractions");

        while(rowSet.next()){
            List<Tags> listOfTags = new ArrayList<>();
            int attractionsID =rowSet.getInt("attractionsID");
            String name = rowSet.getString("name");
            String description = rowSet.getString("beskrivelse");
            String byerID = rowSet.getString("ByerID");


            String by = "" + jdbcTemplate.queryForRowSet("SELECT Byer FROM byer WHERE ByerID VALUES (?)", byerID);
            Byer byName = Byer.valueOf(by);

            SqlRowSet attractions_tags =jdbcTemplate.queryForRowSet("SELECT tagsID FROM attractions_tags WHERE AttractionsID VALUES(?)", attractionsID);
            while(attractions_tags.next()){
                int tagsID = attractions_tags.getInt("TagsID");
                SqlRowSet tags =jdbcTemplate.queryForRowSet("SELECT tags FROM tags WHERE tagsID VALUES(?)", tagsID);
                while(tags.next()){
                    String tagsName = tags.getString("tags");
                    Tags tagsEnumName = Tags.valueOf(tagsName);
                    listOfTags.add(tagsEnumName);
                }

            }

            listOfAttractions.add(new TouristAttraction(name, description, listOfTags, byName));

        }




        return listOfAttractions;
    }

    public void insertAttraction(String name, String description, Byer by){
        String sql ="INSERT INTO attraction (name, description, by) VALUES ? ? ?";
        jdbcTemplate.update(sql, name, description, by);

    }

    public void deleteAttraction(int attractionId){
        String sql = "DELETE FROM attraction WHERE attractionId = ?";
        jdbcTemplate.update(sql, attractionId);
    }


    public void updateAttraction(String name, String description, Byer by, int attractionId){
        String sql = "UPDATE attraction SET name = ?, description = ?, by = ? WHERE attractionId = ?";
        jdbcTemplate.update(sql, name, description, by, attractionId);
    }

}
