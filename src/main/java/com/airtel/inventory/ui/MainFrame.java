package com.airtel.inventory.ui;

import com.airtel.inventory.service.InventoryService;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private final InventoryService service;
    private final CountDownLatch closeLatch = new CountDownLatch(1);

    public MainFrame(InventoryService service) {
        if (service == null) {
            throw new IllegalArgumentException("InventoryService cannot be null");
        }
        this.service = service;
        initUI();
    }

    private void initUI() {
        setTitle("Airtel Inventory Management System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1300, 800);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        try {
            tabs.addTab("Register Device", new RegisterDevicePanel(service));
            tabs.addTab("Add Employee", new AddEmployeePanel(service));
            tabs.addTab("Assign Device", new AssignDevicePanel(service));
            tabs.addTab("Return Device", new ReturnDevicePanel(service));
            tabs.addTab("View All Devices", new DeviceListPanel(service));
            tabs.addTab("Audit Log", new AuditLogPanel(service));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading UI: " + e.getMessage());
        }

        add(tabs);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                closeLatch.countDown();
            }
        });

        setVisible(true);
    }

    public void waitForClose() throws InterruptedException {
        closeLatch.await();
    }
}