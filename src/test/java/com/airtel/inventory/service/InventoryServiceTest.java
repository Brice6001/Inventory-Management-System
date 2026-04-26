package com.airtel.inventory.service;

import com.airtel.inventory.entity.Device;
import com.airtel.inventory.entity.Employee;
import com.airtel.inventory.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private ConditionLogRepository conditionLogRepository;

    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Device testDevice;
    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        testDevice = new Device();
        testDevice.setSerialNumber("MOCK001");
        testDevice.setDeviceType("Laptop");
        testDevice.setStatus("Available");

        testEmployee = new Employee();
        testEmployee.setEmployeeId("EMP001");
        testEmployee.setName("John Doe");
    }

    @Test
    void testRegisterDevice() {
        when(deviceRepository.save(any(Device.class))).thenReturn(testDevice);

        Device registered = inventoryService.registerDevice(testDevice);

        assertNotNull(registered);
        assertEquals("Available", registered.getStatus());
        verify(deviceRepository, times(1)).save(any(Device.class));
        verify(auditLogRepository, times(1)).save(any());
    }

    @Test
    void testAssignDevice_Success() {
        when(deviceRepository.findBySerialNumber("MOCK001")).thenReturn(testDevice);
        when(employeeRepository.findById("EMP001")).thenReturn(Optional.of(testEmployee));
        when(assignmentRepository.save(any())).thenReturn(null);
        when(deviceRepository.save(any())).thenReturn(testDevice);

        assertDoesNotThrow(() -> inventoryService.assignDevice("MOCK001", "EMP001", "Good"));

        verify(assignmentRepository, times(1)).save(any());
        verify(deviceRepository, times(1)).save(testDevice);
        assertEquals("Assigned", testDevice.getStatus());
    }

    @Test
    void testAssignDevice_DeviceNotFound() {
        when(deviceRepository.findBySerialNumber("UNKNOWN")).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class,
                () -> inventoryService.assignDevice("UNKNOWN", "EMP001", "Good"));
        assertEquals("Device not found", exception.getMessage());
    }

    @Test
    void testAssignDevice_DeviceNotAvailable() {
        testDevice.setStatus("Assigned");
        when(deviceRepository.findBySerialNumber("MOCK001")).thenReturn(testDevice);

        Exception exception = assertThrows(RuntimeException.class,
                () -> inventoryService.assignDevice("MOCK001", "EMP001", "Good"));
        assertEquals("Device not available", exception.getMessage());
    }
}