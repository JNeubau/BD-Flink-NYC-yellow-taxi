package com.example.bigdata.model;

public class ZoneData {
    // identyfikator strefy
    private Long locationID;
    // dzielnica
    private String borough;
    // nazwa strefy
    private String zone;
    // nazwa strefy serwisowej
    private String service_zone;


    // Constructors
    public ZoneData() {
        this(-1L, "", "", "");
    }

    public ZoneData(Long locationID, String borough, String zone,
                    String service_zone) {
        this.locationID = locationID;
        this.borough = borough;
        this.zone = zone;
        this.service_zone = service_zone;
    }

    public Long getLocationID() {
        return locationID;
    }

    public void setLocationID(Long locationID) {
        this.locationID = locationID;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getService_zone() {
        return service_zone;
    }

    public void setService_zone(String service_zone) {
        this.service_zone = service_zone;
    }

    @Override
    public String toString() {
        return "ZoneData{" +
                "locationID=" + locationID +
                ", borough=" + borough +
                ", zone=" + zone +
                ", service_zone=" + service_zone +
                '}';
    }
}
