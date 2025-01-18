package com.connexion.poo_project;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SignIn extends JFrame {
    private JPanel mainPanel;
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField confirmEmailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public SignIn() {
        initComponents();
    }

    private void initComponents() {
        // Initialisation des composants
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Configuration de la fenêtre
        setTitle("Création de compte");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);

        // En-tête
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Formulaire
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Boutons
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Création de compte", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JButton exitButton = new JButton("Fermer");
        exitButton.setBackground(new Color(217, 83, 79));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        headerPanel.add(exitButton, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Champs du formulaire
        usernameField = createFormField(formPanel, "Nom d'utilisateur :", 0, gbc);
        emailField = createFormField(formPanel, "Adresse email :", 1, gbc);
        confirmEmailField = createFormField(formPanel, "Confirmation email :", 2, gbc);
        passwordField = createPasswordField(formPanel, "Mot de passe :", 3, gbc);
        confirmPasswordField = createPasswordField(formPanel, "Confirmation mot de passe :", 4, gbc);

        return formPanel;
    }

    private JTextField createFormField(JPanel panel, String labelText, int row, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField field = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);

        return field;
    }

    private JPasswordField createPasswordField(JPanel panel, String labelText, int row, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        JPasswordField field = new JPasswordField(20);

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);

        return field;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(240, 240, 245));

        JButton createAccountButton = new JButton("Créer le compte");
        createAccountButton.setBackground(new Color(51, 122, 183));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 14));
        createAccountButton.setFocusPainted(false);
        createAccountButton.addActionListener(e -> handleCreateAccount());

        JButton loginButton = new JButton("Retour à la connexion");
        loginButton.setBackground(new Color(51, 122, 183));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> {
            new Menu().setVisible(true);
            dispose();
        });

        buttonPanel.add(createAccountButton);
        buttonPanel.add(loginButton);

        return buttonPanel;
    }

    private void handleCreateAccount() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String confirmEmail = confirmEmailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validations
        if (!email.endsWith("@gmail.com")) {
            showError("L'adresse email doit se terminer par @gmail.com");
            return;
        }

        if (!isPasswordStrong(password)) {
            showError("Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial");
            return;
        }

        if (!email.equals(confirmEmail)) {
            showError("Les adresses email ne correspondent pas");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Les mots de passe ne correspondent pas");
            return;
        }

        if (isUserFound(username, password)) {
            showError("Ce compte existe déjà");
            return;
        }

        // Création du compte
        saveToCSV(username, password, email);
        new Materiel().setVisible(true);
        dispose();
    }

    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    private void saveToCSV(String username, String password, String email) {
        String filename = "src/main/java/com/connexion/poo_project/data.csv";
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.append(String.format("%s,%s,%s%n", username, password, email));
        } catch (IOException e) {
            showError("Erreur lors de l'enregistrement du compte");
        }
    }

    private boolean isUserFound(String username, String password) {
        String filename = "src/main/java/com/connexion/poo_project/data.csv";
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                if (fields[0].equals(username) && fields[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            showError("Erreur lors de la vérification du compte");
        }
        return false;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);

    }}