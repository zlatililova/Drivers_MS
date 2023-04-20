package com.example.drivers_ms;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/drivers")
public class DriversController {
    private DriversService driversService;

    public DriversController(DriversService driversService) {
        this.driversService = driversService;
    }

    @PostMapping("/register")
    public void registerDriver(@RequestBody AddDriversRequest addUserRequest){
        driversService.registerDriver(addUserRequest);
    }

    @PostMapping("/login")
    public void loginDriver(@RequestBody AddDriversRequest addUserRequest){
        driversService.loginDriver(addUserRequest);
    }

    @GetMapping("/{id}")
    public Optional<Driver> getDriver(@PathVariable int id){
        return driversService.getDriver(id);
    }

    @PostMapping("/{id}/geolocation/{location}")
    public void postLastGeolocation(@PathVariable int id, @PathVariable String location) {
        driversService.postLastGeolocation(id, location);
    }

    @GetMapping("/driver/{id}")
    public boolean isDriverTaken(@PathVariable int id){
        return driversService.isDriverTaken(id);
    }

    @PostMapping("driver/{id}")
    public void takeDriver(@PathVariable int id){
        driversService.takeDriver(id);
    }

}