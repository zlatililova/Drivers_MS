package com.example.drivers_ms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@EnableKafka
@Repository
public class DriversRepository {

    private KafkaTemplate<String, String> kafkaTemplate;
    private String serverId = "";

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    final JdbcTemplate jdbcTemplate;

    public DriversRepository(JdbcTemplate jdbcTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.jdbcTemplate = jdbcTemplate;
        serverId = "kafka:9092";
    }

    public void register(Driver driver){
        String sql = "INSERT INTO drivers (username, password, last_geolocation, is_driver_taken) VALUES (?, ?, ?, ?)";
        Object[] params = {driver.getUsername(), driver.getPassword(), driver.getLast_geolocation(), driver.isDriverTaken()};
        jdbcTemplate.update(sql, params);
    }

    public Optional<Driver> login(Driver driver){
        String sql = "SELECT * FROM drivers WHERE username = ? AND password = ?";
        Object[] params = {driver.getUsername(), driver.getPassword()};
        try {
            Driver user =  jdbcTemplate.queryForObject(sql, params,
                    BeanPropertyRowMapper.newInstance(Driver.class));
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Driver> findById(int id){
        String sql = "SELECT * FROM drivers WHERE id = ?";
        Object[] params = {id};
        try {
            Driver user =  jdbcTemplate.queryForObject(sql, params,
                    BeanPropertyRowMapper.newInstance(Driver.class));
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }



    public void postGeolocation(String location, int id){
        HashMap<String, String> message = new HashMap<>();
        message.put("username", Integer.toString(id));
        message.put("geolocation", location);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        kafkaTemplate.send("test", jsonString);
    }


    public boolean isDriverTaken(int id) {
        String sql = "SELECT * FROM drivers WHERE id = ?";
        Object[] params = {id};
        try {
            Driver user =  jdbcTemplate.queryForObject(sql, params,
                    BeanPropertyRowMapper.newInstance(Driver.class));
            return user.isDriverTaken();
        } catch (EmptyResultDataAccessException e) {
            return true;
        }
    }


    public void takeDriver(int id) {
        String sql = "UPDATE drivers SET is_driver_taken = ? WHERE id = ?";
        Object[] params = {true, id};
        jdbcTemplate.update(sql, params);
    }
}
