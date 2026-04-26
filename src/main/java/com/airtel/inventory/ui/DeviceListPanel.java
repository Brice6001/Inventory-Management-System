package com.airtel.inventory.ui;

import com.airtel.inventory.entity.Device;
import com.airtel.inventory.service.InventoryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DeviceListPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InventoryService service;
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> typeFilter, statusFilter;

    public DeviceListPanel(InventoryService service) {
        this.service = service;
        setLayout(new BorderLayout());

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.add(new JLabel("Device Type:"));
        typeFilter = new JComboBox<>(new String[]{"All", "Laptop", "Desktop", "Mobile"});
        filterPanel.add(typeFilter);
        filterPanel.add(new JLabel("Status:"));
        statusFilter = new JComboBox<>(new String[]{"All", "Available", "Assigned", "Under Repair", "Retired"});
        filterPanel.add(statusFilter);
        JButton refreshBtn = new JButton("Refresh");
        filterPanel.add(refreshBtn);
        add(filterPanel, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Serial", "Type", "Brand", "Model", "Status"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshBtn.addActionListener(e -> refreshTable());
        // Use SwingUtilities.invokeLater to avoid blocking EDT
        SwingUtilities.invokeLater(() -> refreshTable());
    }

    private void refreshTable() {
        String type = (String) typeFilter.getSelectedItem();
        String status = (String) statusFilter.getSelectedItem();
        List<Device> devices = service.getFilteredDevices(type, status);
        model.setRowCount(0);
        for (Device d : devices) {
            model.addRow(new Object[]{d.getId(), d.getSerialNumber(), d.getDeviceType(), d.getBrand(), d.getModel(), d.getStatus()});
        }
    }
}