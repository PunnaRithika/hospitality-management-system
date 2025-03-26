package com.hospitality.gui;

import com.hospitality.entities.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class HotelManagementGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hospitality Management System");

        JButton addHotelButton = new JButton("Add Hotel");
        JButton addRoomButton = new JButton("Add Room");
        JButton addGuestButton = new JButton("Add Guest");
        JButton addReservationButton = new JButton("Add Reservation");
        JButton retrieveDataButton = new JButton("Retrieve Data");

        addHotelButton.setBounds(50, 50, 200, 30);
        addRoomButton.setBounds(50, 100, 200, 30);
        addGuestButton.setBounds(50, 150, 200, 30);
        addReservationButton.setBounds(50, 200, 200, 30);
        retrieveDataButton.setBounds(50, 250, 200, 30);

        frame.add(addHotelButton);
        frame.add(addRoomButton);
        frame.add(addGuestButton);
        frame.add(addReservationButton);
        frame.add(retrieveDataButton);

        frame.setSize(320, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addHotelButton.addActionListener(e -> addHotel());
        addRoomButton.addActionListener(e -> addRoom());
        addGuestButton.addActionListener(e -> addGuest());
        addReservationButton.addActionListener(e -> addReservation());
        retrieveDataButton.addActionListener(e -> retrieveData());
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                DBConnection.closeConnection(); // Close DB connection when the application is closed
            }
        });
    }

    private static void addHotel() {
        String hotelName = JOptionPane.showInputDialog("Enter Hotel Name:");
        String location = JOptionPane.showInputDialog("Enter Location:");
        String amenities = JOptionPane.showInputDialog("Enter Amenities:");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Hotels (hotelName, location, amenities) VALUES (?, ?, ?)")) {
            
            stmt.setString(1, hotelName);
            stmt.setString(2, location);
            stmt.setString(3, amenities);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Hotel Added Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void addRoom() {
        String roomId = JOptionPane.showInputDialog("Enter Room ID:");
        String hotelId = JOptionPane.showInputDialog("Enter Hotel ID:");
        String roomNumber = JOptionPane.showInputDialog("Enter Room Number:");
        String roomType = JOptionPane.showInputDialog("Enter Room Type:");
        String price = JOptionPane.showInputDialog("Enter Price:");
        String status = JOptionPane.showInputDialog("Enter status:");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Rooms (room_id, hotel_id, room_number, room_type, price, status) VALUES (?, ?, ?, ?, ?, ?)")) {
            
            stmt.setInt(1, Integer.parseInt(roomId));
            stmt.setInt(2, Integer.parseInt(hotelId));
            stmt.setInt(3, Integer.parseInt(roomNumber));
            stmt.setString(4, roomType);
            stmt.setDouble(5, Double.parseDouble(price));
            stmt.setBoolean(6, Boolean.parseBoolean(status));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Room Added Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void addGuest() {
        String guestId = JOptionPane.showInputDialog("Enter Guest ID:");
        String fullName = JOptionPane.showInputDialog("Enter Full Name:");
        String email = JOptionPane.showInputDialog("Enter Email:");
        String phone = JOptionPane.showInputDialog("Enter Phone Number:");
        String address = JOptionPane.showInputDialog("Enter Address:");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Guests (guest_id, name, email, phone, address) VALUES (?, ?, ?, ?, ?)")) {
            
            stmt.setInt(1, Integer.parseInt(guestId));
            stmt.setString(2, fullName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Guest Added Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void addReservation() {
        String reservationId = JOptionPane.showInputDialog("Enter Reservation ID:");
        String guestId = JOptionPane.showInputDialog("Enter Guest ID:");
        String roomId = JOptionPane.showInputDialog("Enter Room ID:");
        String checkInDate = JOptionPane.showInputDialog("Enter Check-In Date (YYYY-MM-DD):");
        String checkOutDate = JOptionPane.showInputDialog("Enter Check-Out Date (YYYY-MM-DD):");
        String totalPrice = JOptionPane.showInputDialog("Enter Total Price:");
        String status = JOptionPane.showInputDialog("Enter Status:");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Reservations (reservation_id, guest_id, room_id, check_in, check_out, total_price, status) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            
            stmt.setInt(1, Integer.parseInt(reservationId));
            stmt.setInt(2, Integer.parseInt(guestId));
            stmt.setInt(3, Integer.parseInt(roomId));
            stmt.setDate(4, Date.valueOf(checkInDate));
            stmt.setDate(5, Date.valueOf(checkOutDate));
            stmt.setDouble(6, Double.parseDouble(totalPrice));
            stmt.setString(7, status);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Reservation Added Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding reservation: " + ex.getMessage());
        }
    }

    private static void retrieveData() {
        JFrame frame = new JFrame("Retrieve All Data");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 500);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Hotels", createTablePanel("Hotels"));
        tabbedPane.addTab("Rooms", createTablePanel("Rooms"));
        tabbedPane.addTab("Guests", createTablePanel("Guests"));
        tabbedPane.addTab("Reservations", createTablePanel("Reservations"));

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private static JPanel createTablePanel(String tableName) {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];

            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            tableModel.setColumnIdentifiers(columnNames);

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving data from " + tableName + ": " + e.getMessage());
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}
