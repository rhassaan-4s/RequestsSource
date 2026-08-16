package com._4s_.requestsApproval.web.util;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAccessLevelsDTO {

    private Long employeeId;
    private String employeeName;

    private Long groupId;
    private String groupName;

    private List<String> authorities;

    public EmployeeAccessLevelsDTO() {
        authorities = new ArrayList<String>();
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(String authority) {
        this.authorities.add(authority);
    }
}