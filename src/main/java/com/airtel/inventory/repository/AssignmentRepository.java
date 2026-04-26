package com.airtel.inventory.repository;

import com.airtel.inventory.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByEmployeeEmployeeIdAndReturnedDateIsNull(String employeeId);
    List<Assignment> findByDeviceSerialNumberAndReturnedDateIsNull(String serialNumber);
}