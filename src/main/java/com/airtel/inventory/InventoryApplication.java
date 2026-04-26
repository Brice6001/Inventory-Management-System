package com.airtel.inventory;

import com.airtel.inventory.ui.LoginDialog;
import com.airtel.inventory.ui.MainFrame;
import com.airtel.inventory.service.InventoryService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class InventoryApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(InventoryApplication.class)
                        .headless(false)
                        .run(args);

        SwingUtilities.invokeLater(() -> {
            try {
                InventoryService service = context.getBean(InventoryService.class);

                LoginDialog loginDialog = new LoginDialog(null, service);
                loginDialog.setVisible(true);

                System.out.println("Login result: " + loginDialog.isSucceeded());

                if (loginDialog.isSucceeded()) {
                    new MainFrame(service); // constructor handles UI
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Login cancelled or failed.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Startup error: " + e.getMessage());
            }
        });
    }
}