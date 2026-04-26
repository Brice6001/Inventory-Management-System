package com.airtel.inventory.ui;

import com.airtel.inventory.service.InventoryService;
import javax.swing.*;
import java.awt.*;

public class AssignDevicePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InventoryService service;
    private JTextField serialField, empIdField;
    private JComboBox<String> conditionCombo;

    public AssignDevicePanel(InventoryService service) {
        this.setService(service);
        setLayout(new GridLayout(4, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Device Serial Number:"));
        serialField = new JTextField();
        add(serialField);

        add(new JLabel("Employee ID:"));
        empIdField = new JTextField();
        add(empIdField);

        add(new JLabel("Condition at assignment:"));
        conditionCombo = new JComboBox<>(new String[]{"Good", "Fair", "New"});
        add(conditionCombo);

        JButton assignBtn = new JButton("Assign Device");
        add(new JLabel());
        add(assignBtn);

        assignBtn.addActionListener(e -> {
            String serial = serialField.getText().trim();
            String empId = empIdField.getText().trim();
            String condition = (String) conditionCombo.getSelectedItem();
            try {
                service.assignDevice(serial, empId, condition);
                JOptionPane.showMessageDialog(this, "Device assigned successfully");
                serialField.setText("");
                empIdField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Assignment failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

	public InventoryService getService() {
		return service;
	}

	public void setService(InventoryService service) {
		this.service = service;
	}
}