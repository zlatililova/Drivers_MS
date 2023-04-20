package com.example.drivers_ms;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriversService {
    private DriversRepository driversRepository;

    public DriversService(DriversRepository driversRepository) {
        this.driversRepository = driversRepository;
    }

    public void registerDriver(AddDriversRequest addDriversRequest){
        Driver driver = new Driver();
        driver.setUsername(addDriversRequest.getUsername());
        driver.setPassword(addDriversRequest.getPassword());
        driver.setLast_geolocation("");
        driver.setDriverTaken(false);
        driversRepository.register(driver);
    }

    public Optional<Driver> loginDriver(AddDriversRequest addDriversRequest){
        Driver driver = new Driver();
        driver.setUsername(addDriversRequest.getUsername());
        driver.setPassword(addDriversRequest.getPassword());
        driver.setLast_geolocation("");
        driver.setDriverTaken(false);
        return driversRepository.login(driver);
    }
    public Optional<Driver> getDriver(int id){
        return driversRepository.findById(id);
    }

    public void postLastGeolocation(int id, String geolocation){
        Optional<Driver> isDriverReal = getDriver(id);
        if(isDriverReal.isPresent()){
            driversRepository.postGeolocation(geolocation, id);
        }
    }

    public boolean isDriverTaken(int id) {
        return driversRepository.isDriverTaken(id);
    }

    public void takeDriver(int id) {
        driversRepository.takeDriver(id);
    }
}
