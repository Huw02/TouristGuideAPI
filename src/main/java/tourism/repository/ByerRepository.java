package tourism.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import tourism.model.Byer;
import tourism.rowMappers.ByerRowMapper;

import java.util.List;

public class ByerRepository {
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    public ByerRepository() {


        DriverManagerDataSource dataSource = new DriverManagerDataSource(
                System.getenv(dbUrl),
                System.getenv(username),
                System.getenv(password)
        );
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        this.jdbcTemplate =new JdbcTemplate(dataSource);
    }


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



}
