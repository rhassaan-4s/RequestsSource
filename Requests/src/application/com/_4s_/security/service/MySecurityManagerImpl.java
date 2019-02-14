package com._4s_.security.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;

import com._4s_.common.service.BaseManager;
import com._4s_.common.service.BaseManagerImpl;
import com._4s_.security.dao.MySecurityDAO;
import com._4s_.security.model.Fields;
import com._4s_.security.model.Permissions;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;
import com._4s_.security.model.User;

public class MySecurityManagerImpl extends BaseManagerImpl implements
		MySecurityManager {
	private MySecurityDAO securityDAO = null;

	private BaseManager baseManager = null;

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}

	public List getListOfRoles() {
		// TODO Auto-generated method stub
		return securityDAO.getListOfRoles();
	}

	public MySecurityDAO getSecurityDAO() {
		return securityDAO;
	}

	public void setSecurityDAO(MySecurityDAO securityDAO) {
		this.securityDAO = securityDAO;
	}

	public List getUsers() {
		// TODO Auto-generated method stub
		return securityDAO.getUsers();
	}

	public List getFields() {
		// TODO Auto-generated method stub
		return securityDAO.getFields();
	}

	public void compareLists(Fields field, List allRoles, List selectedRoles) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>.start compareLists()");
		String flag = "unassign";
		Iterator itr = allRoles.iterator();
		// Iterator innerItr = selectedRoles.iterator();
		while (itr.hasNext()) {
			Roles role = ((Roles) itr.next());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>...role==========" + role);
			// Roles selectedRole = ((Roles) innerItr.next());
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>...selectedRole=========="
					+ selectedRoles);
			Iterator itr2 = field.getPermissions().iterator();
			if (!selectedRoles.isEmpty()) {
				if (selectedRoles.contains(role)) {
					flag = "assign";
					while (itr2.hasNext()) {
						Permissions permission = ((Permissions) itr2.next());
						if (flag.equals("assign")) {
							log.debug(">>>>>>>>>>>>.>>>assignField");
							assignPermission(permission, role);
						} else {
							log.debug(">>>>>>>>>>>>>>..unassignField");
							unassignPermission(permission, role);
						}
					}
					log
							.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>selectedRoles contains role= "
									+ role);
					openParent(field, role);

					findChildren(field, role, flag);
				} else {
					log
							.debug(">>>>>>>>>>>>>>>>>>>>>>>.selectedRoles doesn't contain this role= "
									+ role);
					flag = "unassign";
					while (itr2.hasNext()) {
						Permissions permission = ((Permissions) itr2.next());
						if (flag.equals("assign")) {
							log.debug(">>>>>>>>>>>>.>>>assignField");
							assignPermission(permission, role);
						} else {
							log.debug(">>>>>>>>>>>>>>..unassignField");
							unassignPermission(permission, role);
						}
					}
					findChildren(field, role, flag);
					closeParent(field, role);
				}

			}
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>end compareLists()");
	}

	public void openParent(Fields field, Roles role) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>openParent()");
		Fields parentId = field.getParentField();
		if (parentId != null) {
			log.debug(">>>>>>>>>>>>>>>>>>>>parentId != null");
			List permissions = parentId.getPermissions();
			Iterator itr = permissions.iterator();
			while (itr.hasNext()) {
				log.debug(">>>>>>>>>>>>>>>>>permissionParent");
				Permissions permission = ((Permissions) itr.next());
				assignPermission(permission, role);
			}
			openParent(parentId, role);
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>.end openParent()");

	}

	public void unassignPermission(Permissions permission, Roles role) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>.start unassignPermission()");
		Set sRole = new HashSet();
		sRole.add(role);
		permission.getRoles().remove(role);
		log.debug(">>>>>>>>>>>>>>>>>>permission= " + permission);
		Set sPermission = new HashSet();
		sPermission.add(permission);
		role.getPermissions().remove(permission);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>role = " + role);
		baseManager.saveObject(role);
		baseManager.saveObject(permission);
		Iterator itr2 = permission.getRoles().iterator();
		while (itr2.hasNext()) {
			Roles rPermission = ((Roles) itr2.next());
			log
					.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>rPermissions<<<<<<<<<<<<<<<,"
							+ rPermission);
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>.end unassignPermission()");
	}

	public void assignPermission(Permissions permission, Roles role) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>.start assignPermission()");
		permission.getRoles().add(role);
		log.debug(">>>>>>>>>>>>>>>>>>permission= " + permission);
		role.getPermissions().add(permission);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>role = " + role);
		if (!permission.getRoles().contains(role)
				|| !role.getPermissions().contains(role)) {
			baseManager.saveObject(role);
			baseManager.saveObject(permission);
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>.end assignPermission()");
	}

	public void findChildren(Fields field, Roles role, String flag) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>.start findChildren");
		List children = field.getChildFields();
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>..getChilfield " + children);
		if (!children.isEmpty()) {
			Iterator itr = children.iterator();
			log.debug(">>>>>>>>>>>>>children!=null");
			while (itr.hasNext()) {
				Fields child = ((Fields) itr.next());
				log.debug(">>>>>>>>>>>>>>>>itrChild");
				Iterator innerItr = child.getPermissions().iterator();
				while (innerItr.hasNext()) {
					log.debug(">>>>>>>>>>>>permissionChild");
					Permissions permission = ((Permissions) innerItr.next());
					log.debug(">>>>>>>>>>>>>>>>>>>permissionssss");
					if (flag.equals("assign")) {
						log.debug(">>>>>>>>>>>>.>>>assign");
						assignPermission(permission, role);
					} else {
						log.debug(">>>>>>>>>>>>>>..unassign");
						unassignPermission(permission, role);
					}
				}
				findChildren(child, role, flag);
			}

		}

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>.end findChildren");
	}

	public void closeParent(Fields field, Roles role) {
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>.start closeParent()");
		int flag = 0;
		Fields parent = field.getParentField();
		log.debug(">>>>>>>>>>>>>>parent " + parent);
		if (parent != null) {
			List children = parent.getChildFields();
			log.debug(">>>>>>>>>>>>>>>>>>>>children of parent " + children);
			if (!children.isEmpty()) {
				log
						.debug(">>>>>>>>>>>>>>>>>>>>children isn't empty<<<<<<<<<<<<<<<");
				Iterator itr = children.iterator();
				while (itr.hasNext()) {

					Fields child = ((Fields) itr.next());
					log
							.debug(">>>>>>>>>>>>>>>>>>>>>>itr haseNext()CLoseParent()<<<<<<<<<<<<<<<<<<<<<<< "
									+ child);
					Iterator innerItr = child.getPermissions().iterator();
					while (innerItr.hasNext()) {
						Permissions permission = ((Permissions) innerItr.next());
						log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>permission "
								+ permission);
						// log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>permission.getRoles().contains(role)
						// "
						// +
						// permission.getRoles().contains(role)+"<<<<<<<<<<<<<<<<role>>>>>>>>>>>>>>>>>>"+role);
						if (permission.getRoles().contains(role)
								&& child != field) {
							log.debug(">>>>>>>>>>>>>>>contain role"
									+ permission.getRoles().contains(role));
							log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>permission "
									+ permission);
							log
									.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>role= "
											+ role);
							flag = 1;
						}
					}

				}
				if (flag == 0) {
					log.debug(">>>>>>>>>>>>>>>>>>>>>>>>remove role: " + role);

					parent.getPermissions().remove(role);
					Iterator i = parent.getPermissions().iterator();
					while (i.hasNext()) {
						Permissions perm = ((Permissions) i.next());
						role.getPermissions().remove(perm);
					}

				}
			}
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>.end closeParent()");
	}

	public String getApplicationDefaultPage(Long applicationId) {
		// TODO Auto-generated method stub
		return securityDAO.getApplicationDefaultPage(applicationId);
	}

	public User getUserByUserName(String username) {
		return securityDAO.getUserByUserName(username);
	}

	public List getApplicationEmployees(Long applicationId) {
		return securityDAO.getApplicationEmployees(applicationId);
	}

	public boolean userHasAuthority(String authority) {
		SecurityContext sc = (SecurityContext) (SecurityContextHolder.getContext());
		GrantedAuthority[] allUserAuthorities =  sc.getAuthentication().getAuthorities();
		for(int i = 0 ; i < allUserAuthorities.length ; i++){
			String authority_i =allUserAuthorities[i].getAuthority(); 
//			log.fatal("allUserAuthorities["+i+"] : " + authority_i);
			if(authority_i.equals(authority)){
				return true;
			}
		}
		return false;
	}

	public List getEmployeesByBranchAndDepartmentAndStatus(String branchId, String departmentId, String status) {
		return securityDAO.getEmployeesByBranchAndDepartmentAndStatus(branchId , departmentId, status);
	}

	public List getUserByEmail(String email) {
		return securityDAO.getUserByEmail(email);
	}
	
	public List getActiveApplications(){
		return securityDAO.getActiveApplications();
	}

	
	public List getApplicationsByUser(User user) {
		List activeApp = getActiveApplications();
		List app = new ArrayList();
		Set appSet = new HashSet();
		Iterator itr = activeApp.iterator();
		while (itr.hasNext()) {
			SecurityApplication ap = (SecurityApplication)itr.next();
			Iterator inner_itr = user.getRoles().iterator();
			while(inner_itr.hasNext()) {
				Roles role = (Roles)inner_itr.next();
				if(ap.getRoles().contains(role)) {
					if(app.contains(role.getApplication())==false) {
						app.add(role.getApplication());
					}
				}
			}
		}
		return app;
	}

	
}
