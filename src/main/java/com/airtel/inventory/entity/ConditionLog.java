package com.airtel.inventory.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ConditionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Device device;
    
    private LocalDate checkDate;
    private String deviceCondition;   // renamed from "condition"
    private String remarks;
    
    // Constructors
    public ConditionLog() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Device getDevice() { return device; }
    public void setDevice(Device device) { this.device = device; }
    public LocalDate getCheckDate() { return checkDate; }
    public void setCheckDate(LocalDate checkDate) { this.checkDate = checkDate; }
    public String getDeviceCondition() { return deviceCondition; }
    public void setDeviceCondition(String deviceCondition) { this.deviceCondition = deviceCondition; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}