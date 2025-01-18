package com.connexion.poo_project.douche;

import javax.swing.*;
import java.awt.*;

public class TowelWarmerPanel extends JPanel {
    private final TowelWarmer warmer;
    private final JLabel temperatureLabel;
    private final JLabel consumptionLabel;
    private final JToggleButton powerButton;
    private final JSlider temperatureSlider;

    public TowelWarmerPanel(TowelWarmer warmer) {
        this.warmer = warmer;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        setBackground(Color.WHITE);

        // En-tête avec le nom
        JLabel nameLabel = new JLabel(warmer.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(new Color(70, 130, 180));
        add(nameLabel, BorderLayout.NORTH);

        // Panneau central
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        centerPanel.setBackground(Color.WHITE);

        // Affichage de la température
        temperatureLabel = new JLabel(String.format("%.1f°C", warmer.getTemperature()));
        temperatureLabel.setFont(new Font("Arial", Font.BOLD, 24));
        temperatureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(temperatureLabel);

        // Affichage de la consommation
        consumptionLabel = new JLabel(String.format("%.2f kWh", warmer.getTotalConsumption()));
        consumptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        consumptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(consumptionLabel);

        // Bouton marche/arrêt
        powerButton = new JToggleButton(warmer.isOn() ? "ALLUMÉ" : "ÉTEINT");
        powerButton.setSelected(warmer.isOn());
        powerButton.setBackground(warmer.isOn() ? new Color(92, 184, 92) : new Color(217, 83, 79));
        powerButton.setForeground(Color.WHITE);
        powerButton.setFocusPainted(false);
        powerButton.addActionListener(e -> togglePower());
        centerPanel.add(powerButton);

        add(centerPanel, BorderLayout.CENTER);

        // Slider de température
        temperatureSlider = new JSlider(JSlider.HORIZONTAL, 150, 300, (int)(warmer.getTemperature() * 10));
        temperatureSlider.setMajorTickSpacing(50);
        temperatureSlider.setMinorTickSpacing(10);
        temperatureSlider.setPaintTicks(true);
        temperatureSlider.setPaintLabels(true);
        temperatureSlider.setBackground(Color.WHITE);
        temperatureSlider.addChangeListener(e -> updateTemperature());
        add(temperatureSlider, BorderLayout.SOUTH);

        // Timer pour mettre à jour la consommation
        Timer updateTimer = new Timer(1000, e -> updateConsumptionLabel());
        updateTimer.start();
    }

    private void togglePower() {
        warmer.togglePower();
        powerButton.setText(warmer.isOn() ? "ALLUMÉ" : "ÉTEINT");
        powerButton.setBackground(warmer.isOn() ? new Color(92, 184, 92) : new Color(217, 83, 79));
        temperatureSlider.setEnabled(warmer.isOn());
        updateConsumptionLabel();
    }

    private void updateTemperature() {
        double temp = temperatureSlider.getValue() / 10.0;
        warmer.setTemperature(temp);
        temperatureLabel.setText(String.format("%.1f°C", temp));
        updateConsumptionLabel();
    }

    private void updateConsumptionLabel() {
        consumptionLabel.setText(String.format("Consommation: %.2f kWh", warmer.getTotalConsumption()));
        repaint();
    }
}