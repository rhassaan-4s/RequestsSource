package com._4s_.security.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.dao.BaseDAO;
import com._4s_.security.model.IPAddress;
import com._4s_.security.model.Imei;
import com._4s_.security.model.User;

@Transactional
public interface MySecurityDAO extends BaseDAO{
public List getUsers();
public List getListOfRoles();
public List getFields();
public String getApplicationDefaultPage(Long applicationId);
public User getUserByUserName(String username);
public List getApplicationEmployees(final Long applicationId);
public abstract List getEmployeesByBranchAndDepartmentAndStatus(String branchId, String departmentId, String status);
public abstract List getUserByEmail(String email);
public List getActiveApplications();
public User login();
public User getUser(String username);
public Imei checkImei(String imei, User user);
public IPAddress checkIP(String currentIP, User user);
}
