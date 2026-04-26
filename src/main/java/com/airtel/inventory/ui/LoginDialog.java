package com.airtel.inventory.ui;

import com.airtel.inventory.service.InventoryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private boolean succeeded = false;
    private final InventoryService service;

    public LoginDialog(Frame parent, InventoryService service) {
        super(parent, "Login", true);

        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }

        this.service = service;

        setLayout(new BorderLayout());
        setSize(350, 180);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton cancelBtn = new JButton("Cancel");

        panel.add(loginBtn);
        panel.add(cancelBtn);

        add(panel, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> authenticate());

        cancelBtn.addActionListener(e -> {
            succeeded = false;
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                succeeded = false;
            }
        });

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void authenticate() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both username and password.");
            return;
        }

        try {
            System.out.println("Attempt login: " + username);

            boolean valid = service.authenticate(username, password);

            if (valid) {
                succeeded = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password.");
                usernameField.setText("");
                passwordField.setText("");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Login error: " + ex.getMessage());
        }
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}