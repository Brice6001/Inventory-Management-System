package com.airtel.inventory.ui;

import com.airtel.inventory.entity.AuditLog;
import com.airtel.inventory.service.InventoryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AuditLogPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InventoryService service;
    private DefaultTableModel model;

    public AuditLogPanel(InventoryService service) {
        this.service = service;
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"Timestamp", "Action", "Details", "Performed By"}, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        SwingUtilities.invokeLater(() -> refreshLogs());
    }

    private void refreshLogs() {
        List<AuditLog> logs = service.getAuditLogs();
        model.setRowCount(0);
        for (AuditLog log : logs) {
            model.addRow(new Object[]{log.getTimestamp(), log.getAction(), log.getDetails(), log.getPerformedBy()});
        }
    }
}