package com.example.bigdata.model;

public class TaxiDataAgg {
    private int departures;
    private int arrivals;
    private int totalPassengers;
    private double totalAmount;

    public void addDeparture() {
        departures++;
    }

    public void addArrival(int passengers, double amount) {
        arrivals++;
        totalPassengers += passengers;
        totalAmount += amount;
    }

    public TaxiZoneStats toStats() {
        return new TaxiZoneStats(departures, arrivals, totalPassengers, totalAmount);
    }

    public TaxiDataAgg merge(TaxiDataAgg other) {
        departures += other.departures;
        arrivals += other.arrivals;
        totalPassengers += other.totalPassengers;
        totalAmount += other.totalAmount;
        return this;
    }
}

