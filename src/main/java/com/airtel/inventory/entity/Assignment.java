package com.airtel.inventory.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Device device;
    @ManyToOne
    private Employee employee;
    private LocalDate assignedDate;
    private LocalDate returnedDate;   // null if still assigned
    private String conditionAtAssignment;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Device getDevice() { return device; }
    public void setDevice(Device device) { this.device = device; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public LocalDate getAssignedDate() { return assignedDate; }
    public void setAssignedDate(LocalDate assignedDate) { this.assignedDate = assignedDate; }
    public LocalDate getReturnedDate() { return returnedDate; }
    public void setReturnedDate(LocalDate returnedDate) { this.returnedDate = returnedDate; }
    public String getConditionAtAssignment() { return conditionAtAssignment; }
    public void setConditionAtAssignment(String conditionAtAssignment) { this.conditionAtAssignment = conditionAtAssignment; }
}