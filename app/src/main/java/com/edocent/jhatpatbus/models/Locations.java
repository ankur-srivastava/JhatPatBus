package com.edocent.jhatpatbus.models;

/**
 * Created by ankursrivastava on 9/2/15.
 */
public class Locations {

    public static Locations[] fromLocationsArray = {
            new Locations(0, "From"),
            new Locations(1, "Rohini"),
            new Locations(2, "Gurgaon"),
            new Locations(3, "Airport")
    };

    public static Locations[] toLocationsArray = {
            new Locations(0, "To"),
            new Locations(1, "Rohini"),
            new Locations(2, "Gurgaon"),
            new Locations(3, "Airport")
    };

    private int locationId;
    private String locationName;

    public Locations(int locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    @Override
    public String toString() {
        return locationName;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
