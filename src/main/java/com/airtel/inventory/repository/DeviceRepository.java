package com.airtel.inventory.repository;

import com.airtel.inventory.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByStatus(String status);
    List<Device> findByDeviceType(String deviceType);
    Device findBySerialNumber(String serialNumber);
    List<Device> findByDeviceTypeAndStatus(String deviceType, String status);
}