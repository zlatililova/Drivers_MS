package com.example.drivers_ms;

import java.util.Date;

public class AddDriversRequest {
    private String username;
    private String password;
    private String last_geolocation;
    private boolean isDriverTaken;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLast_geolocation() {
        return last_geolocation;
    }

    public boolean isDriverTaken() {
        return isDriverTaken;
    }
}
