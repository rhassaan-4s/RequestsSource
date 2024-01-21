package com._4s_.security.web.action;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com._4s_.common.model.Branch;
import com._4s_.common.model.Department;
import com._4s_.common.service.CommonManager;
import com._4s_.common.web.action.BaseController;
import com._4s_.security.model.IPAddress;
import com._4s_.security.model.Imei;
import com._4s_.security.model.User;
import com._4s_.security.service.MySecurityManager;

@Controller
public class UserController implements Comparator{//extends BaseController
	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	private MySecurityManager mgr = null;
	@Autowired
	public CommonManager commonManager;

	public MySecurityManager getMgr() {
		return mgr;
	}

	public void setMgr(MySecurityManager mgr) {
		this.mgr = mgr;
	}
	
	public CommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}

	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		User u1 = (User)o1;
		User u2 = (User)o2;
		return u1.getUsername().compareTo(u2.getUsername());
	}

	@RequestMapping("/userDetails.html")
	public String handleRequest(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>> handleRequest()<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
//		Collections.sort(users,new UserController());

		///////////////////////////////test//////////////////////////////////

/*		Item i=new Item();
		i.setName("S Item int in the nightmare category");
		i.setCategory((Category)mgr.getObject(Category.class,new Long(1)));
		mgr.saveObject(i);
*/		////deletion test 
/*		Category prnt = (Category)mgr.getObject(Category.class,new Long(31));
		mgr.removeObject(prnt);
*/		
		////addition test
/*		Category c1=new Category();
		c1.setName("asd");
		List <Category> lst=new ArrayList<Category>();
		Category c2=new Category();
		Category c3=new Category();
		c2.setName("c2");
		c3.setName("c3");
		c2.setParentCategory(c1);
		c3.setParentCategory(c1);
		lst.add(c2);
		lst.add(c2);
		c1.setChildCategories(lst);
		mgr.saveObject(c1);	
*/		
		/////////////////////////////////////////////////////////////////////
//		Map model = new HashMap();
		
		String deleteId = request.getParameter("deleteId");
		if(deleteId != null && !deleteId.equals("")) {
			Long deleteIdLong = Long.parseLong(deleteId);
			Object o = mgr.getObjectByParameter(User.class, "id", deleteIdLong);
			if(o != null) {
				User u = (User)o;
				u.setIsActive(false);
				mgr.saveObject(u);
			}
		}
		
		String confirmDeleteImei = request.getParameter("confirmDeleteImei");
		List users = mgr.getEmployeesByBranchAndDepartmentAndStatus(request.getParameter("councilBranch"), request.getParameter("department"), request.getParameter("isEmployee"));
		if (confirmDeleteImei!= null && confirmDeleteImei.equals("true")) {
//			Iterator itr = users.iterator();
//			while(itr.hasNext()) {
//				User user = (User)itr.next();
				List imeis = mgr.getObjects(Imei.class);
				Iterator imeiItr = imeis.iterator();
				while (imeiItr.hasNext()) {
					Imei imei = (Imei)imeiItr.next();
					mgr.removeObject(imei);
				}
					
//			}
		}
		
		String confirmDeleteIP = request.getParameter("confirmDeleteIP");
		if (confirmDeleteIP!= null && confirmDeleteIP.equals("true")) {
				List ips = mgr.getObjects(IPAddress.class);
				Iterator ipItr = ips.iterator();
				while (ipItr.hasNext()) {
					IPAddress ip = (IPAddress)ipItr.next();
					mgr.removeObject(ip);
				}
					
//			}
		}
		
		
		List departments = mgr.getObjects(Department.class);		
//		List branches = commonManager.getBranchesRelatedByCompany(new Long(1)); // SCFHS
		List branches= commonManager.getObjects(Branch.class);
		model.addAttribute("users", users);
		model.addAttribute("branches", branches);
		model.addAttribute("departments", departments);
		model.addAttribute("councilBranch", request.getParameter("councilBranch"));
		model.addAttribute("department", request.getParameter("department"));
		model.addAttribute("isEmployee", request.getParameter("isEmployee"));
//		return new ModelAndView("userDetails", model);
		return "userDetails";
	}
	
}
