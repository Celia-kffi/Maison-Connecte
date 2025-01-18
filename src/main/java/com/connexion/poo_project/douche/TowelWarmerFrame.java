package com.connexion.poo_project.douche;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TowelWarmerFrame extends JFrame {
    private final List<TowelWarmer> towelWarmers;
    private Timer consumptionTimer;
    private long startTime;

    public TowelWarmerFrame() {
        super("Gestion des Sèche-serviettes");
        this.towelWarmers = initializeTowelWarmers();

        // Configuration de la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);

        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // En-tête avec bouton retour
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));

        JButton backButton = new JButton("Retour");
        backButton.setBackground(new Color(51, 122, 183));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            stopConsumptionTimer();
            dispose();
        });
        headerPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Contrôle des Sèche-serviettes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panneau des sèche-serviettes
        JPanel warmersPanel = new JPanel(new GridLayout(2, 1, 15, 15));
        warmersPanel.setBackground(new Color(240, 240, 245));
        for (TowelWarmer warmer : towelWarmers) {
            warmersPanel.add(new TowelWarmerPanel(warmer));
        }

        mainPanel.add(warmersPanel, BorderLayout.CENTER);
        add(mainPanel);

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

        for (TowelWarmer warmer : towelWarmers) {
            warmer.updateConsumption(hoursElapsed);
        }
        repaint();
    }

    private List<TowelWarmer> initializeTowelWarmers() {
        List<TowelWarmer> warmers = new ArrayList<>();
        warmers.add(new TowelWarmer("Salle de Bain Principale"));
        warmers.add(new TowelWarmer("Salle de Bain Enfants"));
        return warmers;
    }
}