package com.example.bigdata.model;

import java.util.Date;

public class DeparturesAnomaly {
    private String borough;
    private Date from;
    private Date to;
    private int totalPassengersDep;
    private int totalPassengersArr;
    private int difference;

    public DeparturesAnomaly(String borough, Date from, Date to, int totalPassengersArr, int totalPassengersDep, int difference) {
        this.borough = borough;
        this.from = from;
        this.to = to;
        this.totalPassengersArr = totalPassengersArr;
        this.totalPassengersDep = totalPassengersDep;
//        this.difference = totalPassengersDep - totalPassengersArr;
        this.difference = difference;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public int getTotalPassengersArr() {
        return totalPassengersArr;
    }

    public void setTotalPassengersArr(int totalPassengersArr) {
        this.totalPassengersArr = totalPassengersArr;
    }

    public int getTotalPassengersDep() {
        return totalPassengersDep;
    }

    public void setTotalPassengersDep(int totalPassengersDep) {
        this.totalPassengersDep = totalPassengersDep;
    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }

    @Override
    public String toString() {
        return "DeparturesAnomaly{" +
                "borough='" + borough + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", totalPassengersDeparted=" + totalPassengersDep +
                ", totalPassengersArrived=" + totalPassengersArr +
                ", difference=" + difference +
                '}';
    }
}
