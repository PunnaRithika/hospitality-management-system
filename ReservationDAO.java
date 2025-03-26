package com.hospitality.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hospitality.entities.DBConnection;
import com.hospitality.entities.Reservation;

public class ReservationDAO {

    // Method to add a new reservation
    public static void addReservation(int guestId, int roomId, String checkIn, String checkOut) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Reservations (guest_id, room_id, check_in, check_out) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, guestId);
            stmt.setInt(2, roomId);
            stmt.setString(3, checkIn);
            stmt.setString(4, checkOut);
            stmt.executeUpdate();
            System.out.println("Reservation added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all reservations
    public static List<Reservation> getReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Reservations";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reservations.add(new Reservation(rs.getInt("reservation_id"), rs.getInt("guest_id"), rs.getInt("room_id"), rs.getDate("check_in"), rs.getDate("check_out")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // Method to update reservation details
    public static void updateReservation(int reservationId, int guestId, int roomId, String checkIn, String checkOut) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Reservations SET guest_id=?, room_id=?, check_in=?, check_out=? WHERE reservation_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, guestId);
            stmt.setInt(2, roomId);
            stmt.setString(3, checkIn);
            stmt.setString(4, checkOut);
            stmt.setInt(5, reservationId);
            stmt.executeUpdate();
            System.out.println("Reservation updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a reservation
    public static void deleteReservation(int reservationId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM Reservations WHERE reservation_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
            System.out.println("Reservation deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
