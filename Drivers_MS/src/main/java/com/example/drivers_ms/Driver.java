package com.example.drivers_ms;

import java.util.Date;

public class Driver {
    private String username;
    private String password;
    private String last_geolocation;
    private boolean isDriverTaken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLast_geolocation() {
        return last_geolocation;
    }

    public void setLast_geolocation(String last_geolocation) {
        this.last_geolocation = last_geolocation;
    }

    public boolean isDriverTaken() {
        return isDriverTaken;
    }

    public void setDriverTaken(boolean driverTaken) {
        isDriverTaken = driverTaken;
    }
}


