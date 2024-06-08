package com.example.bigdata.model;

public class TaxiLocStats {
    private int departures;
    private int arrivals;
    private int totalPassengersArr;
    private int totalPassengersDep;
//    private double totalAmount;

    public TaxiLocStats(int departures, int arrivals, int totalPassengersArr, int totalPassengersDep) {
        this.departures = departures;
        this.arrivals = arrivals;
        this.totalPassengersArr = totalPassengersArr;
        this.totalPassengersDep = totalPassengersDep;
    }

    // Getters and setters
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

    public void setTotalPassengersArr(int totalPassengers) {
        this.totalPassengersArr = totalPassengers;
    }

    public int getTotalPassengersDep() {
        return totalPassengersDep;
    }

    public void setTotalPassengersDep(int totalPassengersDep) {
        this.totalPassengersDep = totalPassengersDep;
    }

    @Override
    public String toString() {
        return "TaxiLocStats{" +
                "departures=" + departures +
                ", arrivals=" + arrivals +
                ", totalPassengersArr=" + totalPassengersArr +
                ", totalPassengersDep=" + totalPassengersDep +
                '}';
    }
}

