package com.airtel.inventory.entity;

import javax.persistence.*;

@Entity
@Table(name = "sys_user")
public class SysUser {
    @Id
    private String username;
    private String password;
    private String role;

    // default constructor
    public SysUser() {}

    // getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}