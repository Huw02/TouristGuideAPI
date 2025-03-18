package tourism.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import tourism.model.Byer;
import tourism.rowMappers.ByerRowMapper;

import java.util.List;

@Repository
public class ByerRepository {
    private JdbcTemplate jdbcTemplate;

    public ByerRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate =new JdbcTemplate();
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
