package com.edocent.jhatpatbus.models;

/**
 * Created by ankursrivastava on 9/1/15.
 */
public class Bus {

    long busId;
    String busName;
    String busDate;
    String busTime;
    int totalSeats;
    int availableSeats;
    double price;

    public Bus(long busId, String busName, int totalSeats, int availableSeats, double price, String busTime, String busDate) {
        this.busId = busId;
        this.busName = busName;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.price = price;
        this.busTime = busTime;
        this.busDate = busDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(busName+" at "+busTime).append(System.lineSeparator()).append(System.lineSeparator()).
                append(availableSeats + " seats left ").append(System.lineSeparator());
        //return busName+" at "+busTime+" - "+availableSeats+" seats left "+" >";
        return sb.toString();
    }

    public long getBusId() {
        return busId;
    }

    public void setBusId(long busId) {
        this.busId = busId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBusTime() {
        return busTime;
    }

    public void setBusTime(String busTime) {
        this.busTime = busTime;
    }

    public String getBusDate() {
        return busDate;
    }

    public void setBusDate(String busDate) {
        this.busDate = busDate;
    }
}
