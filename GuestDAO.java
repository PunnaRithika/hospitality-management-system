package com.hospitality.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hospitality.entities.DBConnection;
import com.hospitality.entities.Guest;

public class GuestDAO {
    
    public static void addGuest(String name, String email, String phone) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Guests (name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.executeUpdate();
            System.out.println("Guest added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Guest> getGuests() {
        List<Guest> guests = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Guests";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                guests.add(new Guest(rs.getInt("guest_id"), rs.getString("name"), rs.getString("email"), rs.getString("phone")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guests;
    }

    public static void updateGuest(int guestId, String name, String email, String phone) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Guests SET name=?, email=?, phone=? WHERE guest_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setInt(4, guestId);
            stmt.executeUpdate();
            System.out.println("Guest updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteGuest(int guestId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM Guests WHERE guest_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, guestId);
            stmt.executeUpdate();
            System.out.println("Guest deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
