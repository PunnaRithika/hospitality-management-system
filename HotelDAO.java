package com.hospitality.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hospitality.entities.DBConnection;
import com.hospitality.entities.Hotel;

public class HotelDAO {
    
    public static void addHotel( int hotelId, String hotelName, String location, String amenities) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Hotels (hotelName, location, amenities) VALUES ( ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, hotelName);
            stmt.setString(2, location);
            stmt.setString(3, amenities);
            stmt.executeUpdate();
            System.out.println("Hotel added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Hotel> getHotels() {
        List<Hotel> hotels = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Hotels";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                hotels.add(new Hotel(rs.getInt("hotelId"), rs.getString("hotelName"), rs.getString("location"), rs.getString("amenities")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public static void updateHotel(int hotelId, String hotelName, String location, String amenities) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Hotels SET hotelName=?, location=?, amenities=? WHERE hotelId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, hotelId);
            stmt.setString(2, hotelName);
            stmt.setString(3, location);
            stmt.setString(4, amenities);
            stmt.executeUpdate();
            System.out.println("Hotel updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteHotel(int hotelId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM Hotels WHERE hotelId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, hotelId);
            stmt.executeUpdate();
            System.out.println("Hotel deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
