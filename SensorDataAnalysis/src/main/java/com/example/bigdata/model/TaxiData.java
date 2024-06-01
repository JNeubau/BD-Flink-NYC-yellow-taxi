package com.example.bigdata.model;

import java.time.ZonedDateTime;

public class TaxiData {
    // identyfikator przejazdu
    private Long tripID;
    //czy rozpoczęcie (0) czy zakończenie (1) przejazdu
    private int start_stop;
    // etykieta czasowa
    private ZonedDateTime timestamp;
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
}
