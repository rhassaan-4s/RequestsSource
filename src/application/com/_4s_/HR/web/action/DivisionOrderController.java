package com._4s_.HR.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com._4s_.HR.model.HRInternalDivision;
import com._4s_.HR.service.HRManager;
import com._4s_.HR.web.command.ChildrenOrderCommand;
import com._4s_.common.web.action.BaseSimpleFormController;
import com.jenkov.prizetags.tree.itf.ITree;

public class DivisionOrderController  extends BaseSimpleFormController {
	private HRManager hrManager = null;
		
		public HRManager getHrManager() {
			return hrManager;
		}


		public void setHrManager(HRManager hrManager) {
			this.hrManager = hrManager;
		}

		
		protected Object formBackingObject(HttpServletRequest request) throws ServletException 
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

			HRInternalDivision result = null;
			String orderId = request.getParameter("orderId");
			String formType=request.getParameter("formType");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> orderId"+orderId);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> formType"+formType);
			ChildrenOrderCommand orderCommand=null;
			if(formType.equals("internal"))
			{
				orderCommand=new ChildrenOrderCommand();
				 if(orderId!=null && !orderId.equals(""))
				   {
					 orderCommand.setDivisionObjects( hrManager.getLeafsByParentId(new Long(orderId),"HRInternalDivision"));
				   }
			}
			else if(formType.equals("qualification"))
			{
				
			}
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End formBackingObject: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		   return orderCommand;
		}
	//**************************************** referenceData ***********************************************\\
		protected Map referenceData(HttpServletRequest request,Object command,Errors errors)throws ServletException
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>> Starting referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		   //	HRInternalDivision result=(HRInternalDivision)command;
			Map model = new HashMap();
			
			String orderId = request.getParameter("orderId");
			String formType=request.getParameter("formType");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> orderId"+orderId);
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> formType"+formType);
			model.put("orderId", orderId);
			model.put("formType", formType);
			ITree divisionTree=null;
			if(formType.equals("internal"))
			{
			 divisionTree = hrManager.createTreeIfNotFound(request,"HRInternalDivision");
			  model.put("internalDivisionTree",divisionTree);
			}
		
				
//			if(formType.equals("internal"))
//			{
//			  ITree internalDivisionTree = hrManager.createTreeIfNotFound(request,"HRInternalDivision");
//			   model.put("internalDivisionTree",internalDivisionTree);
//			   List childrenList=new ArrayList();
//			   if(orderId!=null && !orderId.equals(""))
//			   {
//				   childrenList= hrManager.getLeafsByParentId(new Long(orderId),"HRInternalDivision");
//			   }
//			}
//	        
              
				log.debug(">>>>>>>>>>>>>>>>>>>>>>> End of referenceData: >>>>>>>>>>>>>>>>>>>>>>>>>>>");

			return model;
		}
		
		//**************************************** onBind ***********************************************\\	
		protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBind >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
		
	//**************************************** onBindAndValidate ***********************************************\\		
		protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> End of onBindAndValidate >>>>>>>>>>>>>>>>>>>>>>>>>>>");

			} 

			
		
	

		
		
		//**************************************** onSubmit ***********************************************\\	
		public ModelAndView onSubmit(HttpServletRequest request,
				HttpServletResponse response, Object command, BindException errors)throws Exception 
		{
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			ChildrenOrderCommand result=(ChildrenOrderCommand )command;
			String formType=request.getParameter("formType");
			if(formType.equals("internal"))
			{
				for (int m = 0; m <result.getDivisionObjects().size() - 1; m++) {
					HRInternalDivision division=(HRInternalDivision)result.getDivisionObjects().get(m);
					for (int n = 0; n < result.getDivisionObjects().size() - m - 1; n++) {
						HRInternalDivision div=(HRInternalDivision)result.getDivisionObjects().get(n);
						HRInternalDivision div1=(HRInternalDivision)result.getDivisionObjects().get(n+1);
						if (div1.getOrder().intValue()< div.getOrder().intValue()) {
							HRInternalDivision tempDiv = (HRInternalDivision)result.getDivisionObjects().get(n);
							String smaller=div1.getLongCode();
							String larger=tempDiv.getLongCode();
							div1.setLongCode(larger);
							tempDiv.setLongCode(smaller);
							result.getDivisionObjects().set(n, result.getDivisionObjects().get(n + 1));
							result.getDivisionObjects().set(n + 1, tempDiv);
						}
					}
				}
				
				request.getSession().removeAttribute("internalDivTree");
				hrManager.createTreeIfNotFound(request, "HRInternalDivision");
				
			}
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> end onSubmit: >>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return new ModelAndView(new RedirectView("internalDivisionTree.html"));
		}
		

	
	
	        
		


	

}
