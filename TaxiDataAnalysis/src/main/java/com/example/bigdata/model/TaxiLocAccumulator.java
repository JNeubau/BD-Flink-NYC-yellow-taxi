package com.example.bigdata.model;

public class TaxiLocAccumulator {
    private int departures;
    private int arrivals;
    private int totalPassengersArr;
    private int totalPassengersDep;
//    private double totalAmount;

    public void addDeparture(int passengers) {
        departures++;
        totalPassengersDep += passengers;
    }

    public void addArrival(int passengers) {
        arrivals++;
        totalPassengersArr += passengers;
    }

    public TaxiLocStats toStats() {
        return new TaxiLocStats(departures, arrivals, totalPassengersArr, totalPassengersDep);
    }

    public TaxiLocAccumulator merge(TaxiLocAccumulator other) {
        departures += other.departures;
        arrivals += other.arrivals;
        totalPassengersArr += other.totalPassengersArr;
        totalPassengersDep += other.totalPassengersDep;
        return this;
    }
}

