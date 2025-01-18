package com.connexion.poo_project.model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThermostatFrame extends JFrame {
    private final List<Room> rooms;
    private Timer consumptionTimer;
    private long startTime;

    public ThermostatFrame() {
        super("Gestion des Radiateurs");
        this.rooms = initializeRooms();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changed to DISPOSE_ON_CLOSE
        setSize(1024, 768);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Contrôle des Radiateurs", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Retour");
        backButton.setBackground(new Color(51, 122, 183));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            dispose();
            stopConsumptionTimer();
        });
        headerPanel.add(backButton, BorderLayout.WEST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Rooms panel
        JPanel roomsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        roomsPanel.setBackground(new Color(240, 240, 245));
        for (Room room : rooms) {
            roomsPanel.add(new RoomPanel(room));
        }

        mainPanel.add(roomsPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Start consumption timer
        startConsumptionTimer();
    }

    private void startConsumptionTimer() {
        startTime = System.currentTimeMillis();
        consumptionTimer = new Timer(1000, e -> updateConsumption());
        consumptionTimer.start();
    }

    private void stopConsumptionTimer() {
        if (consumptionTimer != null) {
            consumptionTimer.stop();
        }
    }

    private void updateConsumption() {
        long currentTime = System.currentTimeMillis();
        double hoursElapsed = (currentTime - startTime) / 3600000.0;

        for (Room room : rooms) {
            if (room.isOn()) {
                room.updateConsumption(hoursElapsed);
            }
        }
        repaint();
    }

    private List<Room> initializeRooms() {
        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room("Salon", 21.0, true));
        roomList.add(new Room("Chambre", 19.0, false));
        roomList.add(new Room("Cuisine", 20.0, true));
        roomList.add(new Room("Salle de bain", 22.0, true));
        return roomList;
    }
}