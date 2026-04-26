package com.airtel.inventory.repository;

import com.airtel.inventory.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SysUserRepository extends JpaRepository<SysUser, String> {
    Optional<SysUser> findByUsernameAndPassword(String username, String password);
}