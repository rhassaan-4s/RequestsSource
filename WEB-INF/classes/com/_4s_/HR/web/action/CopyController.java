package com._4s_.HR.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com._4s_.HR.model.HRGeographicalDivision;
import com._4s_.HR.model.HRGeographicalLevel;
import com._4s_.HR.model.HRInternalDivision;
import com._4s_.HR.model.HRInternalLevel;
import com._4s_.HR.model.HRQualificationDivision;
import com._4s_.HR.model.HRQualificationLevel;
import com._4s_.HR.model.HRSpecialtyDivision;
import com._4s_.HR.model.HRSpecialtyLevel;
import com._4s_.HR.service.HRManager;
import com._4s_.common.web.action.BaseController;
import com.jenkov.prizetags.tree.itf.ITree;

public class CopyController extends BaseController {

	 private HRManager hrManager = null;
		
		public HRManager getHrManager() {
			return hrManager;
		}


		public void setHrManager(HRManager hrManager) {
			this.hrManager = hrManager;
		}
	public ModelAndView handleRequest(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.start handleRequest()");

			Map model = new HashMap();
			
			String copyId=request.getParameter("copyId");
			String copyTo=request.getParameter("copyTo");
			String className=request.getParameter("className");
			log.debug("className>>>>>>>>>"+className);
		    model.put("copyId",copyId);
		    model.put("className",className );
		    
     
			//get divisions with higher levels
			List higherDivisionsList=new ArrayList();
			if(className!=null && !className.equals(""))
			{
				if(className.equals("HRInternalDivision"))
				{
					log.debug("inside className=HRInternalDivision");
					HRInternalDivision intDivision=(HRInternalDivision)hrManager.getObject(HRInternalDivision.class, new Long(copyId));
					if(intDivision.getDivisionLevel()==null)
					{
						HRInternalLevel level=(HRInternalLevel)hrManager.getObject(HRInternalLevel.class,new Long(intDivision.getCode()));
						higherDivisionsList=hrManager.getHigherDivisions(HRInternalDivision.class, new Long(copyId),level.getLevelNo());
					}
					else
					{
						higherDivisionsList=hrManager.getHigherDivisions(HRInternalDivision.class, new Long(copyId),intDivision.getDivisionLevel().getLevelNo());
					}
					
					ITree internalDivisionTree = hrManager.createTreeIfNotFoundForCopy(request,"HRInternalDivision",intDivision);
					internalDivisionTree.expandAll();
					 model.put("internalDivisionTree",internalDivisionTree);
				}
				
				else if(className.equals("HRQualificationDivision"))
				{
					HRQualificationDivision qualDivision=(HRQualificationDivision)hrManager.getObject(HRQualificationDivision.class, new Long(copyId));
					if(qualDivision.getDivisionLevel()==null)
					{
						HRQualificationLevel level=(HRQualificationLevel)hrManager.getObject(HRQualificationLevel.class,new Long(qualDivision.getCode()));
						higherDivisionsList=hrManager.getHigherDivisions(HRQualificationDivision.class, new Long(copyId),level.getLevelNo());
					}
					else
					{
						higherDivisionsList=hrManager.getHigherDivisions(HRQualificationDivision.class, new Long(copyId),qualDivision.getDivisionLevel().getLevelNo());
					}
					
					ITree qualificationDivisionTree = hrManager.createTreeIfNotFound(request,"HRQualificationDivision");
					qualificationDivisionTree.expandAll();
					model.put("qualificationDivisionTree",qualificationDivisionTree);
				}
				
				else if(className.equals("HRGeographicalDivision"))
				{
					HRGeographicalDivision geoDivision=(HRGeographicalDivision)hrManager.getObject(HRGeographicalDivision.class, new Long(copyId));
					if(geoDivision.getDivisionLevel()==null)
					{
						HRGeographicalLevel level=(HRGeographicalLevel)hrManager.getObject(HRGeographicalLevel.class,new Long(geoDivision.getCode()));
						higherDivisionsList=hrManager.getHigherDivisions(HRGeographicalDivision.class, new Long(copyId),level.getLevelNo());
					}
					else
					{
						higherDivisionsList=hrManager.getHigherDivisions(HRGeographicalDivision.class, new Long(copyId),geoDivision.getDivisionLevel().getLevelNo());	
					}
					
					ITree geographicalDivisionTree = hrManager.createTreeIfNotFound(request,"HRGeographicalDivision");
					geographicalDivisionTree.expandAll();
					model.put("geographicalDivisionTree",geographicalDivisionTree);
			
				}
				else if(className.equals("HRSpecialtyDivision"))
				{
					HRSpecialtyDivision spDivision=(HRSpecialtyDivision)hrManager.getObject(HRSpecialtyDivision.class, new Long(copyId));
					if(spDivision.getDivisionLevel()==null)
					{
						HRSpecialtyLevel level=(HRSpecialtyLevel)hrManager.getObject(HRSpecialtyLevel.class,new Long(spDivision.getCode()));
						higherDivisionsList=hrManager.getHigherDivisions(HRSpecialtyDivision.class, new Long(copyId),level.getLevelNo());
					}
					else
					{
						higherDivisionsList=hrManager.getHigherDivisions(HRSpecialtyDivision.class, new Long(copyId),spDivision.getDivisionLevel().getLevelNo());	
					}
					

					ITree specialtyDivisionTree = hrManager.createTreeIfNotFound(request,"HRSpecialtyDivision");
					specialtyDivisionTree.expandAll();
					model.put("specialtyDivisionTree",specialtyDivisionTree);
			
				}
			}
			log.debug("higherDivisionsList.size()::::::::::::"+higherDivisionsList.size());
			model.put("higherDivisions", higherDivisionsList);
			
			if(copyTo!=null && !copyTo.equals(""))
			{
				
				model.put("copyTo", copyTo);
				
			}
		
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.end handleRequest()");
			//	********************************************************************************
			return new ModelAndView("copyController",model);
	}
	
}
