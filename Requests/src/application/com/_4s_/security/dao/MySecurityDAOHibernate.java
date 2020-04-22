package com._4s_.security.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequestWrapper;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.dao.BaseDAOHibernate;
import com._4s_.common.model.Branch;
import com._4s_.common.model.Department;
import com._4s_.common.model.Employee;
import com._4s_.security.model.Fields;
import com._4s_.security.model.Imei;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;
import com._4s_.security.model.User;

@Transactional
@Repository
public class MySecurityDAOHibernate extends BaseDAOHibernate implements
MySecurityDAO {

//	@Autowired
	
	@Autowired AuthenticationSuccessHandler successHandler;
	@Autowired AuthenticationManager authenticationManager;  
	@Autowired AuthenticationFailureHandler failureHandler;
	

	

	public AuthenticationSuccessHandler getSuccessHandler() {
		return successHandler;
	}

	public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
		this.successHandler = successHandler;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public AuthenticationFailureHandler getFailureHandler() {
		return failureHandler;
	}

	public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
		this.failureHandler = failureHandler;
	}

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

	public User login() {
		// TODO Auto-generated method stub
		log.debug("login method in mysecuritydao");
//		 UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
////		    token.setDetails(new WebAuthenticationDetails(request));//if request is needed during authentication
//		    Authentication auth = null;
//		    log.debug("$$$$$$$$Trying to Authenticate: token = " + token);
//		    try {
//		    	log.debug("$$$$$$$$Trying to Authenticate");
//		        auth = authenticationManager.authenticate(token);
//		        log.debug("$$$$$$$$Authenticated");
//		    } catch (AuthenticationException e) {
//		        //if failureHandler exists  
////		        try {
////		            failureHandler.onAuthenticationFailure(request, response, e);
//		        	log.debug("$$$$$$$$Authentication Exception");
////		        } catch (IOException se) {
////		            //ignore
////		        } catch (ServletException xe) {
////		        	//ignore
////		        }
////		        throw e;
//		    } catch (Exception ex) {
//		    	log.debug("$$$$$$$$Exception to Authenticate");
////		    	ex.printStackTrace();
//		    }
//		    SecurityContext securityContext = SecurityContextHolder.getContext();
//		    securityContext.setAuthentication(auth);
		    
		    
//		    successHandler.onAuthenticationSuccess(request, response, auth);//if successHandler exists  
		    //if user has a http session you need to save context in session for subsequent requests
//		    HttpSession session = request.getSession(true);
//		    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		    
		log.debug("SecurityContextHolder.getContext().getAuthentication().getClass() " + SecurityContextHolder.getContext().getAuthentication().getClass());
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		
		UserDetails userDet = (UserDetails)token.getPrincipal();
		return getUser(userDet.getUsername());
	}

	public User getUser(String username) {
		log.debug("token details " + username);
	    Criteria criteria = getCurrentSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("isActive", new Boolean(true)));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List list = criteria.list();
		if (list.size() > 0) {
			return (User)list.get(0);
		} else {
			return null;
		}
	}

	public Imei checkImei(String imei, User user) {
		Criteria criteria = getCurrentSession().createCriteria(Imei.class);
		criteria.add(Restrictions.eq("users", user));
		criteria.add(Restrictions.eq("imei", imei));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List list = criteria.list();
		if (list.size() > 0) {
			return (Imei)list.get(0);
		} else {
			return null;
		}
	}
	
	
	

}
