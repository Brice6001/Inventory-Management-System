package com.airtel.inventory.ui;

import com.airtel.inventory.service.InventoryService;
import javax.swing.*;
import java.awt.*;

public class ReturnDevicePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InventoryService service;
    private JTextField serialField;
    private JComboBox<String> conditionCombo;
    private JTextArea remarksArea;

    public ReturnDevicePanel(InventoryService service) {
        this.setService(service);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.add(new JLabel("Device Serial Number:"));
        serialField = new JTextField();
        form.add(serialField);

        form.add(new JLabel("Condition on return:"));
        conditionCombo = new JComboBox<>(new String[]{"Excellent", "Good", "Damaged", "Missing Parts"});
        form.add(conditionCombo);

        form.add(new JLabel("Remarks:"));
        remarksArea = new JTextArea(3, 20);
        form.add(new JScrollPane(remarksArea));

        JButton returnBtn = new JButton("Return Device");

        add(form, BorderLayout.CENTER);
        add(returnBtn, BorderLayout.SOUTH);

        returnBtn.addActionListener(e -> {
            String serial = serialField.getText().trim();
            String condition = (String) conditionCombo.getSelectedItem();
            String remarks = remarksArea.getText();
            try {
                service.returnDevice(serial, condition, remarks);
                JOptionPane.showMessageDialog(this, "Device returned and condition logged");
                serialField.setText("");
                remarksArea.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Return failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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