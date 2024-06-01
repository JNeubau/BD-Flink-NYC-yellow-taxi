package com.example.bigdata.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

public class TaxiData {
    // identyfikator przejazdu
    private Long tripID;
    //czy rozpoczęcie (0) czy zakończenie (1) przejazdu
    private int start_stop;
    // etykieta czasowa
    private Date timestamp;
    // identyfikator strefy taksówek
    private Long locationID;
    // liczba pasażerów
    private int passenger_count;
    // długość przejazdu (0 dla rozpoczęcia przejazdu)
    private Double trip_distance;
    // typ płatności (0 dla rozpoczęcia przejazdu)
    private int payment_type;
    // opłata za przejazd (0 dla rozpoczęcia przejazdu)
    private Double amount;
    // identyfikator korporacji
    private int VendorID;


//    public TaxiData(Long tripID, int start_stop, ZonedDateTime timestamp, Long locationID,
//                    int passenger_count, Double trip_distance, int payment_type,
//                    Double amount, int VendorID) {
//        this.tripID = tripID;
//        this.start_stop = start_stop;
//        this.timestamp = timestamp;
//        this.locationID = locationID;
//        this.passenger_count = passenger_count;
//        this.trip_distance = trip_distance;
//        this.payment_type = payment_type;
//        this.amount = amount;
//        this.VendorID = VendorID;
//    }

    public Long getTripID() {
        return tripID;
    }

    public void setTripID(Long tripID) {
        this.tripID = tripID;
    }

    public int getStart_stop() {
        return start_stop;
    }

    public void setStart_stop(int start_stop) {
        this.start_stop = start_stop;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getLocationID() {
        return locationID;
    }

    public void setLocationID(Long locationID) {
        this.locationID = locationID;
    }

    public int getPassenger_count() {
        return passenger_count;
    }

    public void setPassenger_count(int passenger_count) {
        this.passenger_count = passenger_count;
    }

    public Double getTrip_distance() {
        return trip_distance;
    }

    public void setTrip_distance(Double trip_distance) {
        this.trip_distance = trip_distance;
    }

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getVendorID() {
        return VendorID;
    }

    public void setVendorID(int vendorID) {
        VendorID = vendorID;
    }

    @Override
    public String toString() {
        return "TaxiData{" +
                "tripID=" + tripID +
                ", start_stop=" + start_stop +
                ", timestamp=" + timestamp +
                ", locationID=" + locationID +
                ", passenger_count=" + passenger_count +
                ", trip_distance=" + trip_distance +
                ", payment_type=" + payment_type +
                ", amount=" + amount +
                ", VendorID=" + VendorID +
                '}';
    }

    public static TaxiData fromString(String line) throws ParseException {
        String[] parts = line.split(",");
        if (parts.length == 9) {
            TaxiData taxiEvent = new TaxiData();
            taxiEvent.setTripID(Long.parseLong(parts[0]));
            taxiEvent.setStart_stop(Integer.parseInt(parts[1]));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            taxiEvent.setTimestamp(dateFormat.parse(parts[2]));
            taxiEvent.setLocationID(Long.parseLong(parts[3]));
            taxiEvent.setPassenger_count(Integer.parseInt(parts[4]));
            taxiEvent.setTrip_distance(Double.parseDouble(parts[5]));
            taxiEvent.setPayment_type(Integer.parseInt(parts[6]));
            taxiEvent.setAmount(Double.parseDouble(parts[7]));
            taxiEvent.setVendorID(Integer.parseInt(parts[8]));
            return taxiEvent;
        } else {
            throw new IllegalArgumentException("Malformed line: " + line);
        }
    }
}
