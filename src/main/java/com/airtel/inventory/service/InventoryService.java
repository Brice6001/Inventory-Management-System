package com.airtel.inventory.service;

import com.airtel.inventory.entity.*;
import com.airtel.inventory.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryService {

    @Autowired private DeviceRepository deviceRepo;
    @Autowired private EmployeeRepository empRepo;
    @Autowired private AssignmentRepository assignRepo;
    @Autowired private ConditionLogRepository conditionRepo;
    @Autowired private AuditLogRepository auditRepo;
    @Autowired
    private SysUserRepository sysUserRepository;
    // ---------- Device Management ----------
    public boolean authenticate(String username, String password) {
        return sysUserRepository.findByUsernameAndPassword(username, password).isPresent();
    }
    @Transactional
    public Device registerDevice(Device device) {
        device.setStatus("Available");
        device.setPurchaseDate(LocalDate.now());
        Device saved = deviceRepo.save(device);
        audit("REGISTER", "Device " + device.getSerialNumber() + " registered", "admin");
        return saved;
    }

    public List<Device> getAllDevices() {
        return deviceRepo.findAll();
    }

    public List<Device> getFilteredDevices(String deviceType, String status) {
        if ((deviceType == null || deviceType.equals("All")) && (status == null || status.equals("All")))
            return deviceRepo.findAll();
        else if (!deviceType.equals("All") && status.equals("All"))
            return deviceRepo.findByDeviceType(deviceType);
        else if (deviceType.equals("All") && !status.equals("All"))
            return deviceRepo.findByStatus(status);
        else
            return deviceRepo.findByDeviceTypeAndStatus(deviceType, status);
    }

    // ---------- Employee ----------
    @Transactional
    public Employee addEmployee(Employee emp) {
        return empRepo.save(emp);
    }

    public List<Employee> getAllEmployees() {
        return empRepo.findAll();
    }

    // ---------- Assignment ----------
    @Transactional
    public void assignDevice(String serialNumber, String employeeId, String condition) {
        Device device = deviceRepo.findBySerialNumber(serialNumber);
        if (device == null) throw new RuntimeException("Device not found");
        if (!"Available".equals(device.getStatus())) throw new RuntimeException("Device not available");

        Employee emp = empRepo.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));

        Assignment assign = new Assignment();
        assign.setDevice(device);
        assign.setEmployee(emp);
        assign.setAssignedDate(LocalDate.now());
        assign.setConditionAtAssignment(condition);
        assignRepo.save(assign);

        device.setStatus("Assigned");
        deviceRepo.save(device);

        audit("ASSIGN", serialNumber + " assigned to " + employeeId, "admin");
    }

    // Get current assignment for a device (if any)
    public Assignment getActiveAssignment(String serialNumber) {
        List<Assignment> list = assignRepo.findByDeviceSerialNumberAndReturnedDateIsNull(serialNumber);
        return list.isEmpty() ? null : list.get(0);
    }

    // ---------- Return + Condition ----------
    @Transactional
    public void returnDevice(String serialNumber, String returnCondition, String remarks) {
        Device device = deviceRepo.findBySerialNumber(serialNumber);
        if (device == null) throw new RuntimeException("Device not found");

        Assignment active = getActiveAssignment(serialNumber);
        if (active == null) throw new RuntimeException("Device not currently assigned");

        active.setReturnedDate(LocalDate.now());
        assignRepo.save(active);

        // Log condition
        ConditionLog log = new ConditionLog();
        log.setDevice(device);
        log.setCheckDate(LocalDate.now());
        log.setDeviceCondition(returnCondition);
        log.setRemarks(remarks);
        conditionRepo.save(log);

        device.setStatus("Available");
        deviceRepo.save(device);

        audit("RETURN", serialNumber + " returned. Condition: " + returnCondition, "admin");
    }

    // ---------- Audit ----------
    private void audit(String action, String details, String user) {
        AuditLog log = new AuditLog();
        log.setTimestamp(LocalDateTime.now());
        log.setAction(action);
        log.setDetails(details);
        log.setPerformedBy(user);
        auditRepo.save(log);
    }

    public List<AuditLog> getAuditLogs() {
        return auditRepo.findAll();
    }
}