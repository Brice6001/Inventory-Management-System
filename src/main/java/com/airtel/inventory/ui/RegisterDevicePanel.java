package com.airtel.inventory.ui;

import com.airtel.inventory.entity.Device;
import com.airtel.inventory.service.InventoryService;
import javax.swing.*;
import java.awt.*;

public class RegisterDevicePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField serialField, typeField, brandField, modelField, specsField;

    public RegisterDevicePanel(InventoryService service) {
        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Serial Number:"));
        serialField = new JTextField();
        add(serialField);

        add(new JLabel("Device Type (Laptop/Desktop/Mobile):"));
        typeField = new JTextField();
        add(typeField);

        add(new JLabel("Brand:"));
        brandField = new JTextField();
        add(brandField);

        add(new JLabel("Model:"));
        modelField = new JTextField();
        add(modelField);

        add(new JLabel("Specifications (RAM/Storage/OS):"));
        specsField = new JTextField();
        add(specsField);

        JButton saveBtn = new JButton("Register Device");
        add(new JLabel()); // empty cell
        add(saveBtn);

        saveBtn.addActionListener(e -> {
            Device d = new Device();
            d.setSerialNumber(serialField.getText().trim());
            d.setDeviceType(typeField.getText().trim());
            d.setBrand(brandField.getText().trim());
            d.setModel(modelField.getText().trim());
            d.setSpecifications(specsField.getText().trim());
            try {
                service.registerDevice(d);
                JOptionPane.showMessageDialog(this, "Device registered successfully!");
                serialField.setText("");
                typeField.setText("");
                brandField.setText("");
                modelField.setText("");
                specsField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}