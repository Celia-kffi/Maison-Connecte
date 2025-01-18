package com.connexion.poo_project;

import com.connexion.poo_project.douche.TowelWarmerFrame;
import com.connexion.poo_project.model.ThermostatFrame;
import javax.swing.*;
import java.awt.*;

public class Materiel extends javax.swing.JFrame {

    public Materiel() {
        initComponents();
        setSize(1024, 768);
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        radiateur = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Configuration du panneau principal
        jPanel1.setBackground(new Color(240, 240, 245));
        jPanel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // En-tête
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));

        JLabel titleLabel = new JLabel("Gestion des Appareils", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Bouton de fermeture
        exitButton = new JButton("Fermer");
        exitButton.setBackground(new Color(217, 83, 79));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(evt -> System.exit(0));
        headerPanel.add(exitButton, BorderLayout.EAST);

        // Configuration des boutons
        radiateur.setText("Radiateur");
        radiateur.setFont(new Font("Arial", Font.BOLD, 16));
        radiateur.setBackground(new Color(51, 122, 183));
        radiateur.setForeground(Color.WHITE);
        radiateur.setFocusPainted(false);
        radiateur.addActionListener(evt -> new ThermostatFrame().setVisible(true));

        jButton2.setText("Sèche-Serviettes");
        jButton2.setFont(new Font("Arial", Font.BOLD, 16));
        jButton2.setBackground(new Color(51, 122, 183));
        jButton2.setForeground(Color.WHITE);
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(evt -> new TowelWarmerFrame().setVisible(true));

        // Layout
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setBackground(new Color(240, 240, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        buttonPanel.add(radiateur);
        buttonPanel.add(jButton2);

        // Assemblage final
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(headerPanel, BorderLayout.NORTH);
        jPanel1.add(buttonPanel, BorderLayout.CENTER);

        getContentPane().add(jPanel1);
    }

    private javax.swing.JButton radiateur;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton exitButton;
    private javax.swing.JPanel jPanel1;
}