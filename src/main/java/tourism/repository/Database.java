package tourism.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import tourism.model.Byer;

public class Database {

    private JdbcTemplate jdbcTemplate;

    public Database(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
                System.getenv("DB_URL"),
                System.getenv("DB_USERNAME"),
                System.getenv("DB_PASSWORD")
        );
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        this.jdbcTemplate =new JdbcTemplate(dataSource);
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
