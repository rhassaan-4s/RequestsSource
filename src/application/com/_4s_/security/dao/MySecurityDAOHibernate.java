package com._4s_.security.dao;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Repository;

import com._4s_.common.dao.BaseDAOHibernate;
import com._4s_.common.model.Branch;
import com._4s_.common.model.Department;
import com._4s_.common.model.Employee;
import com._4s_.security.model.Fields;
import com._4s_.security.model.IPAddress;
import com._4s_.security.model.Imei;
import com._4s_.security.model.Roles;
import com._4s_.security.model.SecurityApplication;
import com._4s_.security.model.User;
import com._4s_.security.util.JwtUtil;

import io.jsonwebtoken.security.Keys;

//@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Repository
public class MySecurityDAOHibernate extends BaseDAOHibernate implements
MySecurityDAO {

	
	@Autowired AuthenticationSuccessHandler successHandler;
	@Autowired 
	AuthenticationManager authenticationManager;  
//	@Autowired 
//	@Qualifier("myFailureHandler")
//	AuthenticationFailureHandler failureHandler;
	

    private String secretKey;
    
    private JwtUtil jwtUtil;

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	
	public JwtUtil getJwtUtil() {
		return jwtUtil;
	}

	public void setJwtUtil(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
		this.secretKey = jwtUtil.getSecretKey();
		System.out.println("MySecurityDAOHibernate initialized with secretKey: " + (secretKey != null ? jwtUtil.getSecretKey() : "null"));
	}

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

//	public AuthenticationFailureHandler getFailureHandler() {
//		return failureHandler;
//	}
//
//	public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
//		this.failureHandler = failureHandler;
//	}

	public String getApplicationDefaultPage(Long applicationId) {
		SecurityApplication application = (SecurityApplication)getObject(SecurityApplication.class,applicationId);;
		return application.getDefaultPage();
	}

	public List getFields() {
		// TODO Auto-generated method stub

//		Criteria criteria = getCurrentSession().createCriteria(
//				Fields.class)
//				.add(Restrictions.eq("flag",new Boolean(true)));
//		return criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session	session = super.getCurrentSession();
		
		System.out.println("%%%%%%%%%%%% Current Session "+session);
		CriteriaQuery queryCriteria = builder.createQuery(Fields.class);
    	Root<Object> root = queryCriteria.from(Fields.class);
    	List<Predicate> predicates = new ArrayList<Predicate>();
    	Predicate activeRestriction = builder.equal(root.get("flag"), new Boolean(true));
    	predicates.add(activeRestriction);
    	queryCriteria.select(root).where(predicates.toArray(new Predicate[]{})).distinct(true);
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	
		List list = query.getResultList();
		return list;

	}

	public List getUsers() {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(User.class)
//				.setResultTransformer(
//						CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		CriteriaBuilder builder = super.getBuilder();
		Session	session = super.getCurrentSession();
		
		System.out.println("%%%%%%%%%%%% Current Session "+session);
		CriteriaQuery<User> queryCriteria = builder.createQuery(User.class);
    	Root<User> root = queryCriteria.from(User.class);
    	queryCriteria.select(root).distinct(true);
    	TypedQuery<User> query = session.createQuery(queryCriteria);
    	
		List list = query.getResultList();
		return list;
	}


	public User getUserByUserName(final String username) {
//		Criteria criteria = getCurrentSession().createCriteria(User.class);
//		criteria.add(Restrictions.eq("username",username));
//		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//		List list = criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session	session = super.getCurrentSession();
		
		System.out.println("%%%%%%%%%%%% Current Session "+session);
		CriteriaQuery queryCriteria = builder.createQuery(User.class);
    	Root<Object> root = queryCriteria.from(User.class);
    	List<Predicate> predicates = new ArrayList<Predicate>();
    	Predicate restrictions = builder.equal(root.get("username"), username);
    	predicates.add(restrictions);
    	queryCriteria.select(root).where(predicates.toArray(new Predicate[]{})).distinct(true);
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	
		List list = query.getResultList();
		if(list.size() > 0){
			return (User)list.iterator().next();
		}
		else{
			return null;
		}
	}

	public List getListOfRoles() {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(Roles.class)
//				.setResultTransformer(
//						CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session	session = super.getCurrentSession();
		
		System.out.println("%%%%%%%%%%%% Current Session "+session);
		CriteriaQuery queryCriteria = builder.createQuery(Roles.class);
    	Root<Object> root = queryCriteria.from(Roles.class);
    	queryCriteria.select(root).distinct(true);
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	
		List list = query.getResultList();
		return list;
	}


	public List getApplicationEmployees(final Long applicationId) {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(Employee.class)
//				.setResultTransformer(
//						CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//		criteria.createCriteria("user")
//		.createCriteria("roles")
//		.createCriteria("application")
//		.add(Restrictions.eq("id",applicationId));
//		return criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session	session = super.getCurrentSession();
		
		System.out.println("%%%%%%%%%%%% Current Session "+session);
		CriteriaQuery queryCriteria = builder.createQuery(Employee.class);
    	Root<Object> root = queryCriteria.from(Employee.class);
    	List<Predicate> predicates = new ArrayList<Predicate>();
    	Predicate restrictions = builder.equal(root.get("user").get("roles").get("application").get("id"), applicationId);
    	predicates.add(restrictions);
    	queryCriteria.select(root).where(predicates.toArray(new Predicate[]{})).distinct(true);
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	
		List list = query.getResultList();
		return list;
	}


	public List getEmployeesByBranchAndDepartmentAndStatus(final String branchId, final String departmentId, final String status) {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(User.class);
//		criteria.add(Restrictions.eq("isActive", true));
//		Criteria cr1 = criteria.createCriteria("employee");
//
//		if(branchId != null && !branchId.equals("")){
//			Branch branch = (Branch) getObject(Branch.class , branchId);
//			cr1.add(Restrictions.eq("branch" , branch));
//		}
//		if(departmentId != null && !departmentId.equals("")){
//			Department department = (Department) getObject(Department.class , new Long(departmentId));
//			cr1.add(Restrictions.eq("department" , department));
//		}
//		if(status != null && !status.equals("")){
//			cr1.add(Restrictions.eq("isEmployee" , new Boolean(status)));
//		}
//		cr1.addOrder(Property.forName("firstName").asc());
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session	session = super.getCurrentSession();
		
		System.out.println("%%%%%%%%%%%% Current Session "+session);
		CriteriaQuery queryCriteria = builder.createQuery(User.class);
    	Root<Object> root = queryCriteria.from(User.class);
    	List<Predicate> predicates = new ArrayList<Predicate>();
    	if (branchId != null && !branchId.equals("")) {
			Branch branch = (Branch) getObject(Branch.class , branchId);
			Predicate branchRestriction = builder.equal(root.get("employee").get("branch"), branch);
			predicates.add(branchRestriction);
		}
    	if (departmentId != null && !departmentId.equals("")) {
    		Department department = (Department) getObject(Department.class , new Long(departmentId));
    		Predicate departmentRestriction = builder.equal(root.get("employee").get("department"), department);
    		predicates.add(departmentRestriction);			
    	}
    	if (status != null && !status.equals("")) {
			Predicate statusRestriction = builder.equal(root.get("employee").get("isEmployee"), new Boolean(status));
			predicates.add(statusRestriction);			
		}
    	Predicate activeRestriction = builder.equal(root.get("isActive"), new Boolean(true));
    	predicates.add(activeRestriction);
    	queryCriteria.select(root).where(predicates.toArray(new Predicate[]{})).distinct(true);
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	
		List list = query.getResultList();
		return list;
	}

	public List getUserByEmail(final String email) {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(User.class);
//		Criteria cr1 = criteria.createCriteria("employee");
//
//		if(email != null && !email.equals("")){
//			cr1.add(Restrictions.eq("email" , email));
//		}
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
		
		CriteriaBuilder builder = super.getBuilder();
		Session	session = super.getCurrentSession();
		
		System.out.println("%%%%%%%%%%%% Current Session "+session);
		CriteriaQuery queryCriteria = builder.createQuery(User.class);
    	Root<Object> root = queryCriteria.from(User.class);
    	List<Predicate> predicates = new ArrayList<Predicate>();
    	if (email != null && !email.equals("")) {
    		Predicate emailRestriction = builder.equal(root.get("employee").get("email"), email);
    		predicates.add(emailRestriction);
    	}
    	queryCriteria.select(root).where(predicates.toArray(new Predicate[]{})).distinct(true);
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	
		List list = query.getResultList();
		return list;
	}

	public List getActiveApplications() {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(SecurityApplication.class);
//		criteria.add(Restrictions.eq("is_active", new Boolean(true)));
//		criteria.addOrder(Order.asc("position"));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
    	Session	session = super.getCurrentSession();
    	
    	System.out.println("%%%%%%%%%%%% Current Session "+session);
//    	Object object = session.get(clazz, id);
    	CriteriaBuilder builder = super.getBuilder();
    	if (builder ==null) {
    		builder = session.getCriteriaBuilder();
    	}
    	System.out.println("%%%%%%%%%%%% Current builder "+builder);
    	CriteriaQuery<SecurityApplication> queryCriteria = builder.createQuery(SecurityApplication.class);
    	Root<SecurityApplication> root = queryCriteria.from(SecurityApplication.class);
    	root.fetch("roles", JoinType.LEFT);
    	Predicate restrictions = builder.equal(root.get("is_active"), new Boolean(true));
    	queryCriteria.select(root);
    	queryCriteria.where(restrictions);
    	queryCriteria.orderBy(builder.asc(root.get("position")));
    	queryCriteria.distinct(true);
    	
    	
		TypedQuery<SecurityApplication> query = session.createQuery(queryCriteria);
		
		List result =  query.getResultList();
		return result;
	}

	
	public Map<String, Object> login(String tenantId) {
	    log.debug("login method in mysecuritydao");

	    UsernamePasswordAuthenticationToken token =
	            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

	    if (token == null || token.getPrincipal() == null) {
	        throw new RuntimeException("Authentication failed");
	    }
	    
	    

	    UserDetails userDet = (UserDetails) token.getPrincipal();
	    User user = getUser(userDet.getUsername());

	    // ✅ Generate JWT with tenantId
//	    String tenantId = TenantContext.getTenant();

	    System.out.println("Secret Key in MySecurityDAOHibernate: " + secretKey);
	    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	    System.out.println("Generating JWT for tenantId: " + tenantId + ", username: " + user.getUsername());
//	    String jwt = Jwts.builder()
//	            .setSubject(user.getUsername())
//	            .claim("tenantId", tenantId)
//	            .setIssuedAt(new Date())
//	            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
//	            .signWith(key,SignatureAlgorithm.HS256) // inject jwtSecret
//	            .compact();
	    
	 // extract only the role names
	    List<Roles> roleList = (List<Roles>) user.getRoles(); // or getSecurityApplication().getRoles()

	    List<String> roleNames = roleList.stream()
	                                     .map(Roles::getRolename)
	                                     .collect(Collectors.toList());
	    
	    String jwt = jwtUtil.generateToken(tenantId, user.getUsername(),roleNames);

	    // ✅ Return as JSON response
	    Map<String, Object> result = new HashMap<>();
	    result.put("token", jwt);
	    result.put("user", user);

	    return result;
	}
	
	
//	public User login() {
//		// TODO Auto-generated method stub
//		log.debug("login method in mysecuritydao");
////		 UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//////		    token.setDetails(new WebAuthenticationDetails(request));//if request is needed during authentication
////		    Authentication auth = null;
////		    log.debug("$$$$$$$$Trying to Authenticate: token = " + token);
////		    try {
////		    	log.debug("$$$$$$$$Trying to Authenticate");
////		        auth = authenticationManager.authenticate(token);
////		        log.debug("$$$$$$$$Authenticated");
////		    } catch (AuthenticationException e) {
////		        //if failureHandler exists  
//////		        try {
//////		            failureHandler.onAuthenticationFailure(request, response, e);
////		        	log.debug("$$$$$$$$Authentication Exception");
//////		        } catch (IOException se) {
//////		            //ignore
//////		        } catch (ServletException xe) {
//////		        	//ignore
//////		        }
//////		        throw e;
////		    } catch (Exception ex) {
////		    	log.debug("$$$$$$$$Exception to Authenticate");
//////		    	ex.printStackTrace();
////		    }
////		    SecurityContext securityContext = SecurityContextHolder.getContext();
////		    securityContext.setAuthentication(auth);
//		    
//		    
////		    successHandler.onAuthenticationSuccess(request, response, auth);//if successHandler exists  
//		    //if user has a http session you need to save context in session for subsequent requests
////		    HttpSession session = request.getSession(true);
////		    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//		log.debug("SecurityContextHolder.getContext() " + SecurityContextHolder.getContext());   
//		log.debug("SecurityContextHolder.getContext().getAuthentication() " + SecurityContextHolder.getContext().getAuthentication());
//		log.debug("SecurityContextHolder.getContext().getAuthentication() name " + SecurityContextHolder.getContext().getAuthentication().getName());
//		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
//		
//		UserDetails userDet = (UserDetails)token.getPrincipal();
//		return getUser(userDet.getUsername());
//	}

	public User getUser(String username) {
		log.debug("token details " + username);
//	    Criteria criteria = getCurrentSession().createCriteria(User.class);
//		criteria.add(Restrictions.eq("username", username));
//		criteria.add(Restrictions.eq("isActive", new Boolean(true)));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		CriteriaBuilder builder = super.getBuilder();
		Session	session = super.getCurrentSession();
		
		System.out.println("%%%%%%%%%%%% Current Session "+session);
		CriteriaQuery queryCriteria = builder.createQuery(User.class);
    	Root<Object> root = queryCriteria.from(User.class);
    	List<Predicate> predicates = new ArrayList<Predicate>();
    	Predicate restrictions = builder.equal(root.get("username"), username);
    	Predicate activeRestriction = builder.equal(root.get("isActive"), new Boolean(true));
    	predicates.add(restrictions);
    	predicates.add(activeRestriction);
    	queryCriteria.select(root).where(predicates.toArray(new Predicate[]{})).distinct(true);
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	
		List list = query.getResultList();
		if (list.size() > 0) {
			return (User)list.get(0);
		} else {
			return null;
		}
	}

	public Imei checkImei(String imei, User user) {
//		Criteria criteria = getCurrentSession().createCriteria(Imei.class);
//		criteria.add(Restrictions.eq("users", user));
//		criteria.add(Restrictions.eq("imei", imei));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = criteria.list();
		CriteriaBuilder builder = super.getBuilder();
		Session	session = super.getCurrentSession();
		
		System.out.println("%%%%%%%%%%%% Current Session "+session);
		CriteriaQuery queryCriteria = builder.createQuery(Imei.class);
    	Root<Object> root = queryCriteria.from(Imei.class);
    	List<Predicate> predicates = new ArrayList<Predicate>();
    	Predicate restrictions = builder.equal(root.get("users"), user);
    	Predicate rest2 = builder.equal(root.get("imei"), imei);
    	predicates.add(restrictions);
    	predicates.add(rest2);
    	queryCriteria.select(root).where(predicates.toArray(new Predicate[]{})).distinct(true);
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	
		List list = query.getResultList();
		if (list.size() > 0) {
			return (Imei)list.get(0);
		} else {
			return null;
		}
	}
	
	
	public IPAddress checkIP(String currentIP, User user){
//		Criteria criteria = getCurrentSession().createCriteria(IPAddress.class);
//		criteria.add(Restrictions.eq("users", user));
//		criteria.add(Restrictions.eq("ip", currentIP));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = criteria.list();
		CriteriaBuilder builder = super.getBuilder();
		Session	session = super.getCurrentSession();
		
		System.out.println("%%%%%%%%%%%% Current Session "+session);
		CriteriaQuery queryCriteria = builder.createQuery(IPAddress.class);
    	Root<Object> root = queryCriteria.from(IPAddress.class);
    	List<Predicate> predicates = new ArrayList<Predicate>();
    	Predicate restrictions = builder.equal(root.get("users"), user);
    	Predicate activeRestriction = builder.equal(root.get("ip"), currentIP);
    	predicates.add(restrictions);
    	predicates.add(activeRestriction);
    	queryCriteria.select(root).where(predicates.toArray(new Predicate[]{})).distinct(true);
    	TypedQuery<Object> query = session.createQuery(queryCriteria);
    	
		List list = query.getResultList();
		if (list.size() > 0) {
			return (IPAddress)list.get(0);
		} else {
			return null;
		}
	}
	
	public User getUserWithApplicationsAndRoles(String username) {
		Session	session = super.getCurrentSession();
	    return (User) session
	        .createQuery(
	          "select distinct u " +
	          "from User u " +
	          "left join fetch u.roles " +
	          "left join fetch u.defaultApplication da " +
	          "left join fetch da.roles " +
	          "where u.username = :username")
	        .setParameter("username", username)
	        .uniqueResult();
	}
	
	public SecurityApplication getApplicationById(Long id) {
		Session	session = super.getCurrentSession();
	    return (SecurityApplication) session
	        .createQuery(
	          "select distinct sa " +
	          "from SecurityApplication sa " +
	          "left join fetch sa.roles " +
	          "where sa.id = :id")
	        .setParameter("id", id)
	        .uniqueResult();
	}

}
//=======
//package com._4s_.security.dao;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequestWrapper;
//
//import org.hibernate.Criteria;
//import org.hibernate.criterion.CriteriaSpecification;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Property;
//import org.hibernate.criterion.Restrictions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com._4s_.common.dao.BaseDAOHibernate;
//import com._4s_.common.model.Branch;
//import com._4s_.common.model.Department;
//import com._4s_.common.model.Employee;
//import com._4s_.security.model.Fields;
//import com._4s_.security.model.IPAddress;
//import com._4s_.security.model.Imei;
//import com._4s_.security.model.Roles;
//import com._4s_.security.model.SecurityApplication;
//import com._4s_.security.model.User;
//
//@Transactional
//@Repository
//public class MySecurityDAOHibernate extends BaseDAOHibernate implements
//MySecurityDAO {
//
////	@Autowired
//	
//	@Autowired AuthenticationSuccessHandler successHandler;
//	@Autowired AuthenticationManager authenticationManager;  
//	@Autowired AuthenticationFailureHandler failureHandler;
//	
//
//	
//
//	public AuthenticationSuccessHandler getSuccessHandler() {
//		return successHandler;
//	}
//
//	public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
//		this.successHandler = successHandler;
//	}
//
//	public AuthenticationManager getAuthenticationManager() {
//		return authenticationManager;
//	}
//
//	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//		this.authenticationManager = authenticationManager;
//	}
//
//	public AuthenticationFailureHandler getFailureHandler() {
//		return failureHandler;
//	}
//
//	public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
//		this.failureHandler = failureHandler;
//	}
//
//	public String getApplicationDefaultPage(Long applicationId) {
//		SecurityApplication application = (SecurityApplication)getObject(SecurityApplication.class,applicationId);;
//		return application.getDefaultPage();
//	}
//
//	public List getFields() {
//		// TODO Auto-generated method stub
//
//		Criteria criteria = getCurrentSession().createCriteria(
//				Fields.class)
//				.add(Restrictions.eq("flag",new Boolean(true)));
//		return criteria.list();
//
//	}
//
//	public List getUsers() {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(User.class)
//				.setResultTransformer(
//						CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
//	}
//
//
//	public User getUserByUserName(final String username) {
//		Criteria criteria = getCurrentSession().createCriteria(User.class);
//		criteria.add(Restrictions.eq("username",username));
//		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//		List list = criteria.list();
//		if(list.size() > 0){
//			return (User)list.iterator().next();
//		}
//		else{
//			return null;
//		}
//	}
//
//	public List getListOfRoles() {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(Roles.class)
//				.setResultTransformer(
//						CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
//	}
//
//
//	public List getApplicationEmployees(final Long applicationId) {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(Employee.class)
//				.setResultTransformer(
//						CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//		criteria.createCriteria("user")
//		.createCriteria("roles")
//		.createCriteria("application")
//		.add(Restrictions.eq("id",applicationId));
//		return criteria.list();
//	}
//
//
//	public List getEmployeesByBranchAndDepartmentAndStatus(final String branchId, final String departmentId, final String status) {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(User.class);
//		criteria.add(Restrictions.eq("isActive", true));
//		Criteria cr1 = criteria.createCriteria("employee");
//
//		if(branchId != null && !branchId.equals("")){
//			Branch branch = (Branch) getObject(Branch.class , branchId);
//			cr1.add(Restrictions.eq("branch" , branch));
//		}
//		if(departmentId != null && !departmentId.equals("")){
//			Department department = (Department) getObject(Department.class , new Long(departmentId));
//			cr1.add(Restrictions.eq("department" , department));
//		}
//		if(status != null && !status.equals("")){
//			cr1.add(Restrictions.eq("isEmployee" , new Boolean(status)));
//		}
//		cr1.addOrder(Property.forName("firstName").asc());
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
//	}
//
//	public List getUserByEmail(final String email) {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(User.class);
//		Criteria cr1 = criteria.createCriteria("employee");
//
//		if(email != null && !email.equals("")){
//			cr1.add(Restrictions.eq("email" , email));
//		}
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
//	}
//
//	public List getActiveApplications() {
//		Criteria criteria = getCurrentSession()
//				.createCriteria(SecurityApplication.class);
//		criteria.add(Restrictions.eq("is_active", new Boolean(true)));
//		criteria.addOrder(Order.asc("position"));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return criteria.list();
//	}
//
//	public User login() {
//		// TODO Auto-generated method stub
//		log.debug("login method in mysecuritydao");
////		 UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//////		    token.setDetails(new WebAuthenticationDetails(request));//if request is needed during authentication
////		    Authentication auth = null;
////		    log.debug("$$$$$$$$Trying to Authenticate: token = " + token);
////		    try {
////		    	log.debug("$$$$$$$$Trying to Authenticate");
////		        auth = authenticationManager.authenticate(token);
////		        log.debug("$$$$$$$$Authenticated");
////		    } catch (AuthenticationException e) {
////		        //if failureHandler exists  
//////		        try {
//////		            failureHandler.onAuthenticationFailure(request, response, e);
////		        	log.debug("$$$$$$$$Authentication Exception");
//////		        } catch (IOException se) {
//////		            //ignore
//////		        } catch (ServletException xe) {
//////		        	//ignore
//////		        }
//////		        throw e;
////		    } catch (Exception ex) {
////		    	log.debug("$$$$$$$$Exception to Authenticate");
//////		    	ex.printStackTrace();
////		    }
////		    SecurityContext securityContext = SecurityContextHolder.getContext();
////		    securityContext.setAuthentication(auth);
//		    
//		    
////		    successHandler.onAuthenticationSuccess(request, response, auth);//if successHandler exists  
//		    //if user has a http session you need to save context in session for subsequent requests
////		    HttpSession session = request.getSession(true);
////		    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//		    
//		log.debug("SecurityContextHolder.getContext().getAuthentication().getClass() " + SecurityContextHolder.getContext().getAuthentication().getClass());
//		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
//		
//		UserDetails userDet = (UserDetails)token.getPrincipal();
//		return getUser(userDet.getUsername());
//	}
//
//	public User getUser(String username) {
//		log.debug("token details " + username);
//	    Criteria criteria = getCurrentSession().createCriteria(User.class);
//		criteria.add(Restrictions.eq("username", username));
//		criteria.add(Restrictions.eq("isActive", new Boolean(true)));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = criteria.list();
//		if (list.size() > 0) {
//			return (User)list.get(0);
//		} else {
//			return null;
//		}
//	}
//
//	public Imei checkImei(String imei, User user) {
//		Criteria criteria = getCurrentSession().createCriteria(Imei.class);
//		criteria.add(Restrictions.eq("users", user));
//		criteria.add(Restrictions.eq("imei", imei));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = criteria.list();
//		if (list.size() > 0) {
//			return (Imei)list.get(0);
//		} else {
//			return null;
//		}
//	}
//	public IPAddress checkIP(String currentIP, User user){
//		Criteria criteria = getCurrentSession().createCriteria(IPAddress.class);
//		criteria.add(Restrictions.eq("users", user));
//		criteria.add(Restrictions.eq("ip", currentIP));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List list = criteria.list();
//		if (list.size() > 0) {
//			return (IPAddress)list.get(0);
//		} else {
//			return null;
//		}
//	}
//	
//	
//
//}
//>>>>>>> master
