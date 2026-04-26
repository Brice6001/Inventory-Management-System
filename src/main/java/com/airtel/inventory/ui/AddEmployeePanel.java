package com.airtel.inventory.ui;

import com.airtel.inventory.entity.Employee;
import com.airtel.inventory.service.InventoryService;
import javax.swing.*;
import java.awt.*;

public class AddEmployeePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InventoryService service;
    private JTextField idField, nameField, deptField, emailField;

    public AddEmployeePanel(InventoryService service) {
        this.setService(service);
        setLayout(new GridLayout(5, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Employee ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Department:"));
        deptField = new JTextField();
        add(deptField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        JButton saveBtn = new JButton("Add Employee");
        add(new JLabel());
        add(saveBtn);

        saveBtn.addActionListener(e -> {
            Employee emp = new Employee();
            emp.setEmployeeId(idField.getText().trim());
            emp.setName(nameField.getText().trim());
            emp.setDepartment(deptField.getText().trim());
            emp.setEmail(emailField.getText().trim());
            try {
                service.addEmployee(emp);
                JOptionPane.showMessageDialog(this, "Employee added");
                idField.setText("");
                nameField.setText("");
                deptField.setText("");
                emailField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
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