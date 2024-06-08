package com.example.bigdata.model;

import java.util.Date;

public class ResultData {
    private String borough;
    private Date from;
    private Date to;
    private int departures;
    private int arrivals;
    private int totalPassengersArr;
    private int totalPassengersDep;

    public ResultData(String borough, Date from, Date to, int departures, int arrivals, int totalPassengersArr, int totalPassengersDep) {
        this.borough = borough;
        this.from = from;
        this.to = to;
        this.departures = departures;
        this.arrivals = arrivals;
        this.totalPassengersArr = totalPassengersArr;
        this.totalPassengersDep = totalPassengersDep;
    }

    // Getters and setters
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
    public int getDepartures() {
        return departures;
    }

    public void setDepartures(int departures) {
        this.departures = departures;
    }

    public int getArrivals() {
        return arrivals;
    }

    public void setArrivals(int arrivals) {
        this.arrivals = arrivals;
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

    @Override
    public String toString() {
        return "ResultData{" +
                "borough='" + borough + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", departures=" + departures +
                ", arrivals=" + arrivals +
                ", totalPassengersArr=" + totalPassengersArr +
                ", totalPassengersDep=" + totalPassengersDep +
                '}';
    }
}
