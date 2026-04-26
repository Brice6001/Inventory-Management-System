package com.airtel.inventory.integration;

import com.airtel.inventory.entity.Device;
import com.airtel.inventory.entity.Employee;
import com.airtel.inventory.repository.DeviceRepository;
import com.airtel.inventory.repository.EmployeeRepository;
import com.airtel.inventory.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.airtel.inventory.ui.MainFrame;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.show-sql=true",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
@Transactional

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // add this

class InventoryFlowIntegrationTest {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @MockBean
    private MainFrame mainFrame;  // prevents Swing from loading

    @BeforeEach
    void setupData() {
        Employee emp = new Employee();
        emp.setEmployeeId("INT001");
        emp.setName("Integration Tester");
        emp.setDepartment("QA");
        employeeRepository.save(emp);
    }

    @Test
    void testFullLifecycle_Register_Assign_Return() {
        Device device = new Device();
        device.setSerialNumber("INT001");
        device.setDeviceType("Mobile");
        device.setBrand("Samsung");
        device.setModel("Galaxy S21");
        inventoryService.registerDevice(device);

        Device savedDevice = deviceRepository.findBySerialNumber("INT001");
        assertNotNull(savedDevice);
        assertEquals("Available", savedDevice.getStatus());

        inventoryService.assignDevice("INT001", "INT001", "Good");

        Device assignedDevice = deviceRepository.findBySerialNumber("INT001");
        assertEquals("Assigned", assignedDevice.getStatus());

        inventoryService.returnDevice("INT001", "Excellent", "Test return");

        Device returnedDevice = deviceRepository.findBySerialNumber("INT001");
        assertEquals("Available", returnedDevice.getStatus());
    }

    @Test
    void testCannotAssignSameDeviceTwice() {
        Device device = new Device();
        device.setSerialNumber("INT002");
        inventoryService.registerDevice(device);

        inventoryService.assignDevice("INT002", "INT001", "Good");

        Exception exception = assertThrows(RuntimeException.class,
                () -> inventoryService.assignDevice("INT002", "INT001", "Good"));
        assertEquals("Device not available", exception.getMessage());
    }

    @Test
    void testReturnUnassignedDeviceFails() {
        Device device = new Device();
        device.setSerialNumber("INT003");
        inventoryService.registerDevice(device);

        Exception exception = assertThrows(RuntimeException.class,
                () -> inventoryService.returnDevice("INT003", "Good", "No assignment"));
        assertEquals("Device not currently assigned", exception.getMessage());
    }
}