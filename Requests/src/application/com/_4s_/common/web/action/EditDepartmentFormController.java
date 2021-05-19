/**
 * 
 */
package com._4s_.common.web.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.common.model.Department;

/**
 * @author mragab
 *
 */
public class EditDepartmentFormController extends BaseSimpleFormController {
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response,
			Object command, BindException errors)
	throws Exception{
		
		log.debug("Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
		String deleteButton = request.getParameter("delete");
		String saveButton = request.getParameter("save"); 
		
		if ((deleteButton!=null)&&(deleteButton.length()>0)) {

			log.debug("deleting object :"+command);
			baseManager.removeObject(command);
		
		} else if ((saveButton!=null)&&(saveButton.length()>0)) {
			Department department = (Department)command;
			String isDefault = request.getParameter("default");
			if (isDefault != null && isDefault.equals("true")){
				Department defaultDepartment = (Department)baseManager.getDefaultObject(Department.class);
				log.debug(">>>>>>>>>>>>>>>>>>>defaultDepartment "+defaultDepartment);
				if (defaultDepartment != null){
					defaultDepartment.setIsDefault(new Boolean(false));
				}
				department.setIsDefault(new Boolean(true));
			}else{
				department.setIsDefault(new Boolean(false));
			}
			baseManager.saveObject(department);
			log.debug("saving object :"+department);
		
		}
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< End onSubmit");
		
		return new ModelAndView(new RedirectView(getSuccessView()));
	
	}

	protected Object formBackingObject (HttpServletRequest request)
	throws ServletException{
		
		log.debug("Start formBackingObject >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		Department department = new Department();
		
		String departmentId = request.getParameter("departmentId");
		log.debug(">>>>>>>>>>> department Id :" + departmentId);

		if ((departmentId!=null)&&(departmentId.length()>0)) {
			Object obj = baseManager.getObject(Department.class,new Long(departmentId));
			if (obj!=null) {
				department = (Department)obj;
			} else {
				log.warn("!!! No object found for departmentId:"+departmentId);
			}
		}
		
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<< Ending FormBackingObject");
		
		return department;
	}
}
