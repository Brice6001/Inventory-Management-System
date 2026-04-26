package com.airtel.inventory.repository;

import com.airtel.inventory.entity.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")   // loads application-test.properties (with MySQL)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // ← critical!


class DeviceRepositoryTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    void testSaveDevice() {
        Device device = new Device();
        device.setSerialNumber("SN001");
        device.setDeviceType("Laptop");
        device.setBrand("Dell");
        device.setModel("XPS 15");
        device.setSpecifications("16GB RAM, 512GB SSD");
        device.setStatus("Available");

        Device saved = deviceRepository.save(device);

        assertNotNull(saved.getId());
        assertEquals("SN001", saved.getSerialNumber());
        assertEquals("Available", saved.getStatus());
    }

    @Test
    void testFindBySerialNumber() {
        Device device = new Device();
        device.setSerialNumber("SN002");
        device.setDeviceType("Desktop");
        deviceRepository.save(device);

        Device found = deviceRepository.findBySerialNumber("SN002");
        assertNotNull(found);
        assertEquals("Desktop", found.getDeviceType());
    }

    @Test
    void testFindByStatus() {
        Device d1 = new Device(); d1.setSerialNumber("A1"); d1.setStatus("Available");
        Device d2 = new Device(); d2.setSerialNumber("A2"); d2.setStatus("Assigned");
        deviceRepository.save(d1);
        deviceRepository.save(d2);

        List<Device> availableDevices = deviceRepository.findByStatus("Available");
        assertEquals(1, availableDevices.size());
        assertEquals("A1", availableDevices.get(0).getSerialNumber());
    }

    @Test
    void testDeleteDevice() {
        Device device = new Device();
        device.setSerialNumber("SN003");
        Device saved = deviceRepository.save(device);
        Long id = saved.getId();

        deviceRepository.deleteById(id);
        boolean exists = deviceRepository.findById(id).isPresent();
        assertFalse(exists);
    }
}