package com.connexion.poo_project.model;

import javax.swing.*;
import java.awt.*;

public class RoomPanel extends JPanel {
    private final Room room;
    private final JLabel temperatureLabel;
    private final JLabel consumptionLabel;
    private final JToggleButton powerButton;
    private final JSlider temperatureSlider;

    public RoomPanel(Room room) {
        this.room = room;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        setBackground(Color.WHITE);

        // Room name header
        JLabel nameLabel = new JLabel(room.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(new Color(70, 130, 180));
        add(nameLabel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        centerPanel.setBackground(Color.WHITE);

        // Temperature display
        temperatureLabel = new JLabel(String.format("%.1f°C", room.getTemperature()));
        temperatureLabel.setFont(new Font("Arial", Font.BOLD, 24));
        temperatureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(temperatureLabel);

        // Consumption display
        consumptionLabel = new JLabel(String.format("%.2f kWh", room.getTotalConsumption()));
        consumptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        consumptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(consumptionLabel);

        // Power button
        powerButton = new JToggleButton(room.isOn() ? "ALLUMÉ" : "ÉTEINT");
        powerButton.setSelected(room.isOn());
        powerButton.setBackground(room.isOn() ? new Color(92, 184, 92) : new Color(217, 83, 79));
        powerButton.setForeground(Color.WHITE);
        powerButton.setFocusPainted(false);
        powerButton.addActionListener(e -> togglePower());
        centerPanel.add(powerButton);

        add(centerPanel, BorderLayout.CENTER);

        // Temperature slider
        temperatureSlider = new JSlider(JSlider.HORIZONTAL, 150, 300, (int)(room.getTemperature() * 10));
        temperatureSlider.setMajorTickSpacing(50);
        temperatureSlider.setMinorTickSpacing(10);
        temperatureSlider.setPaintTicks(true);
        temperatureSlider.setPaintLabels(true);
        temperatureSlider.setBackground(Color.WHITE);
        temperatureSlider.addChangeListener(e -> updateTemperature());
        add(temperatureSlider, BorderLayout.SOUTH);

        Timer updateTimer = new Timer(1000, e -> updateConsumptionLabel());
        updateTimer.start();
    }

    private void togglePower() {
        room.togglePower();
        powerButton.setText(room.isOn() ? "ALLUMÉ" : "ÉTEINT");
        powerButton.setBackground(room.isOn() ? new Color(92, 184, 92) : new Color(217, 83, 79));
        temperatureSlider.setEnabled(room.isOn());
        updateConsumptionLabel();
    }

    private void updateTemperature() {
        double temp = temperatureSlider.getValue() / 10.0;
        room.setTemperature(temp);
        temperatureLabel.setText(String.format("%.1f°C", temp));
        updateConsumptionLabel();
    }

    private void updateConsumptionLabel() {
        consumptionLabel.setText(String.format("Consommation: %.2f kWh", room.getTotalConsumption()));
        repaint();
    }
}