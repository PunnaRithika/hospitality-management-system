package com.hospitality.entities;

public class Hotel {
    private int hotelId;
    private String hotelName;
    private String location;
    private String amenities;

    // Constructor
    public Hotel(int hotelId, String hotelName, String location, String amenities) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.location = location;
        this.amenities = amenities;
    }

    // Getters and Setters
    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }


    // Override toString() for easy debugging
    @Override
    public String toString() {
        return "Hotel ID: " + hotelId + ", Name: " + hotelName + ", Location: " + location + ", Amenities: " + amenities;
    }
}

