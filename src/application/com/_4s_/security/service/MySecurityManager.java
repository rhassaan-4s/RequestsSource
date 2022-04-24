package com._4s_.security.service;

import java.util.List;

import com._4s_.common.service.BaseManager;
import com._4s_.security.model.Fields;
import com._4s_.security.model.IPAddress;
import com._4s_.security.model.User;


public interface MySecurityManager extends BaseManager{
 public List getUsers();
 public List getListOfRoles();
 public List getFields();
 public void compareLists(Fields field,List allRoles, List selectedRoles);
 public String getApplicationDefaultPage(Long applicationId);
 public User getUserByUserName(String username);
 
 public List getApplicationEmployees(final Long applicationId);
 
 public boolean userHasAuthority(final String authority); 
 public abstract List getEmployeesByBranchAndDepartmentAndStatus(String branchId , String departmentId, String status );
 public abstract List getUserByEmail(String email);
 public List getActiveApplications();
public abstract List getApplicationsByUser(User user);
public IPAddress checkIP(String currentIP, User user);
}
