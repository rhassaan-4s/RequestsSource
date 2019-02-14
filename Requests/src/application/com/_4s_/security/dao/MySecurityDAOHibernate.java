package com._4s_.security.dao;

import java.util.List;
import java.util.Map;

import net.sf.acegisecurity.Authentication;
import net.sf.acegisecurity.AuthenticationException;
import net.sf.acegisecurity.AuthenticationManager;
import net.sf.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import net.sf.acegisecurity.providers.encoding.PasswordEncoder;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hsqldb.lib.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.dao.BaseDAOHibernate;
import com._4s_.common.model.Branch;
import com._4s_.common.model.Department;
import com._4s_.common.model.Employee;
import com._4s_.security.model.Fields;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;
import com._4s_.security.model.User;

@Transactional
@Repository
public class MySecurityDAOHibernate extends BaseDAOHibernate implements
MySecurityDAO {

//	@Autowired
//	AuthenticationManager authenticationManager;
//
//	public AuthenticationManager getAuthenticationManager() {
//		return authenticationManager;
//	}
//
//	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//		this.authenticationManager = authenticationManager;
//	}

	public String getApplicationDefaultPage(Long applicationId) {
		SecurityApplication application = (SecurityApplication)getObject(SecurityApplication.class,applicationId);;
		return application.getDefaultPage();
	}

	public List getFields() {
		// TODO Auto-generated method stub

		Criteria criteria = getCurrentSession().createCriteria(
				Fields.class)
				.add(Restrictions.eq("flag",new Boolean(true)));
		return criteria.list();

	}

	public List getUsers() {
		Criteria criteria = getCurrentSession()
				.createCriteria(User.class)
				.setResultTransformer(
						CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}


	public User getUserByUserName(final String username) {
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username",username));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List list = criteria.list();
		if(list.size() > 0){
			return (User)list.iterator().next();
		}
		else{
			return null;
		}
	}

	public List getListOfRoles() {
		Criteria criteria = getCurrentSession()
				.createCriteria(Roles.class)
				.setResultTransformer(
						CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}


	public List getApplicationEmployees(final Long applicationId) {
		Criteria criteria = getCurrentSession()
				.createCriteria(Employee.class)
				.setResultTransformer(
						CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.createCriteria("user")
		.createCriteria("roles")
		.createCriteria("application")
		.add(Restrictions.eq("id",applicationId));
		return criteria.list();
	}


	public List getEmployeesByBranchAndDepartmentAndStatus(final String branchId, final String departmentId, final String status) {
		Criteria criteria = getCurrentSession()
				.createCriteria(User.class);
		criteria.add(Restrictions.eq("isActive", true));
		Criteria cr1 = criteria.createCriteria("employee");

		if(branchId != null && !branchId.equals("")){
			Branch branch = (Branch) getObject(Branch.class , branchId);
			cr1.add(Restrictions.eq("branch" , branch));
		}
		if(departmentId != null && !departmentId.equals("")){
			Department department = (Department) getObject(Department.class , new Long(departmentId));
			cr1.add(Restrictions.eq("department" , department));
		}
		if(status != null && !status.equals("")){
			cr1.add(Restrictions.eq("isEmployee" , new Boolean(status)));
		}
		cr1.addOrder(Property.forName("firstName").asc());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getUserByEmail(final String email) {
		Criteria criteria = getCurrentSession()
				.createCriteria(User.class);
		Criteria cr1 = criteria.createCriteria("employee");

		if(email != null && !email.equals("")){
			cr1.add(Restrictions.eq("email" , email));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List getActiveApplications() {
		Criteria criteria = getCurrentSession()
				.createCriteria(SecurityApplication.class);
		criteria.add(Restrictions.eq("is_active", new Boolean(true)));
		criteria.addOrder(Order.asc("position"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public User login(String username, String password) {
		// TODO Auto-generated method stub
//		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//		Authentication auth;
//		log.debug("authenticating " + token);
//		try {
//			auth = authenticationManager.authenticate(token);
//			log.debug("authenticated");
//		} catch (AuthenticationException  e) {
//			log.debug("not authenticated");
//		} 
		log.debug("authenticating ");
//		Map m = new java.util.HashMap();
//		m.put("j_username", username);
//		m.put("j_password", password);
//		
//		ModelAndView model = new ModelAndView("../security/j_acegi_security_check",m);//j_username="+username+"&j_password="+password);
//		log.debug("returned model " + model.getViewName());
//		return model;
		
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("password", password));
		criteria.add(Restrictions.eq("isActive", new Boolean(true)));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List list = criteria.list();
		if (list.size() > 0) {
			return (User)list.get(0);
		} else {
			return null;
		}
	}
	

}
