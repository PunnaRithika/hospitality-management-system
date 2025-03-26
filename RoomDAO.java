package com.hospitality.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hospitality.entities.DBConnection;
import com.hospitality.entities.Room;

public class RoomDAO {
    
    public static void addRoom(int roomId, int hotelId, String roomNumber, String roomType, double price, String status) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Rooms (roomId, hotel_id, room_number, roomType, price, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, roomId);
            stmt.setInt(2, hotelId);
            stmt.setString(3, roomNumber);
            stmt.setString(4, roomType);
            stmt.setDouble(5, price);
            stmt.setString(6, status);
            stmt.executeUpdate();
            System.out.println("Room added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Rooms";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                rooms.add(new Room(rs.getInt("room_id"), rs.getInt("hotel_id"), rs.getString("room_number"), rs.getString("roomType"), rs.getDouble("price"), rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public static void updateRoomStatus(int roomId, String status) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Rooms SET status=? WHERE room_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, roomId);
            stmt.executeUpdate();
            System.out.println("Room status updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRoom(int roomId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM Rooms WHERE room_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, roomId);
            stmt.executeUpdate();
            System.out.println("Room deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
