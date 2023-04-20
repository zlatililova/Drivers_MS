package com.example.drivers_ms;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Properties;

@Repository
public class DriversRepository {

    private String serverId = "";
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
    final JdbcTemplate jdbcTemplate;

    public DriversRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        String channelName = "geolocation-driver-" + Integer.toString(id);

        Properties properties = new Properties();
        properties.put("bootstrap.servers", serverId);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        ProducerRecord producerRecord = new ProducerRecord(channelName, "location", location);

        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        kafkaProducer.send(producerRecord);
        kafkaProducer.close();

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
        try {
            Driver user =  jdbcTemplate.queryForObject(sql, params,
                    BeanPropertyRowMapper.newInstance(Driver.class));
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
    }
}
